
package com.mnt.preg.evaluate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.customer.entity.PregAdjustRecord;
import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.customer.service.PregAdjustRecordService;
import com.mnt.preg.customer.service.PregCourseBookingService;
import com.mnt.preg.evaluate.mapping.EvaluateMapping;
import com.mnt.preg.evaluate.mapping.EvaluatePageName;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.examitem.service.QuestionAnswerService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;
import com.mnt.preg.platform.service.DiagnosisHospitalItemService;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.pojo.PrintPojo;
import com.mnt.preg.web.controller.BaseController;

/**
 * 接诊工具栏信息Controller
 * 第一部分：接诊平台引导页
 * 第二部分：问诊页面主要方法
 * 第三部分：营养病例页面的方法
 * 第四部分：查询历史记录的方法
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-13 zcq 初版
 */
@Controller
public class EvaluateToolbarController extends BaseController {

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private PregArchivesService pregArchivesService;

    @Resource
    private CustomerService customerService;

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private ExamItemUtil examItemUtil;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private PregDiagnosisService pregDiagnosisService;

    @Resource
    private PregPlanService pregPlanService;

    @Resource
    private ItemService itemService;

    @Resource
    private PregAdjustRecordService adjustRecordService;

    @Resource
    private PregCourseBookingService courseBookingService;

    @Resource
    private DiagnosisHospitalItemService hospitalItemService;

    @Resource
    private PregDiagnosisDiseaseService diagnosisDiseaseService;

    // ***************************************【JSP页面--guide_page.jsp】**********************************************
    /**
     * JSP页面--guide_page.jsp 诊疗平台引导页
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = EvaluateMapping.EVALUATE_GUIDE_PAGE)
    public String toGuidePage(String diagnosisId, Model model) {
        // 接诊信息
        PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisId);
        String custId = diagnosisPojo.getDiagnosisCustomer();
        // 查询患者信息
        CustomerPojo customerInfo = customerService.getCustomer(custId);
        // 查询孕期建档信息
        PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(custId);
        // 查看孕周调整记录
        PregAdjustRecord record = adjustRecordService.getPregAdjustRecordsByDiagnosisId(diagnosisId);

        // 上次接诊诊断信息
        PregDiagnosisPojo lastDiagnosisPojo = diagnosisService.getLastDiagnosis(custId);
        if (lastDiagnosisPojo != null) {
            List<PregDiagnosisDisease> lastDiseaseList = diagnosisDiseaseService
                    .queryDiagnosisDiseaseByDiagnosisId(lastDiagnosisPojo.getDiagnosisId());
            if (CollectionUtils.isNotEmpty(lastDiseaseList)) {
                List<String> nameList = new ArrayList<String>();
                for (PregDiagnosisDisease disease : lastDiseaseList) {
                    nameList.add(disease.getDiseaseName());
                }
                model.addAttribute("lastDiagnosisDisease", StringUtils.join(nameList, "、"));
            }
        }

        model.addAttribute("diagnosis", diagnosisPojo);
        model.addAttribute("diagnosisJson", NetJsonUtils.objectToJson(diagnosisPojo));
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("preArchive", preArchive);
        model.addAttribute("preArchiveJson", (preArchive == null) ? "null" : NetJsonUtils.objectToJson(preArchive));
        model.addAttribute("recordJson", (record == null) ? "null" : NetJsonUtils.objectToJson(record));
        model.addAttribute("title", "评价平台--" + customerInfo.getCustName());
        model.addAttribute("loginUserId", this.getLoginUserId() + diagnosisId);
        return EvaluatePageName.EVALUATE_GUIDE_PAGE;
    }

    /**
     * JSP页面--guide_top.jsp 营养病历页面
     * 
     * @author zcq
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = EvaluateMapping.EVALUATE_SUMMARY_PAGE)
    public String toDiagnosisSummaryPage(String diagnosisId, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();

        // 第一部分：基础信息：接诊信息、患者信息、孕期建档信息
        PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisId);
        String custId = diagnosisPojo.getDiagnosisCustomer();
        CustomerPojo customerPojo = customerService.getCustomer(custId);
        PregArchivesPojo preArchivePojo = pregArchivesService.getLastPregnancyArchive(custId);
        diagnosisPojo.setDiagnosisInspectResult(getInspectResult(diagnosisId, preArchivePojo));
        map.put("diagnosis", diagnosisPojo);
        map.put("preArchive", preArchivePojo);
        map.put("customer", customerPojo);

        // 第二部分：辅助检查指标信息
        // PregDiagnosisClinicalCondition condition = new PregDiagnosisClinicalCondition();
        // condition.setDiagnosisId(diagnosisId);
        // 查询指标
        // condition.setQueryNotNull(true);// 不检索检测日期和检测结果为空的指标
        // List<PregDiagnosisClinicalPojo> examItemList = clinicalService.queryDiagnosisClinical(condition);
        // map.put("clinicalItemList", examItemList);
        List<DiagnosisHospitalInspectReportPojo> examItemList = hospitalItemService.queryDiagnosisReports(diagnosisId);
        map.put("clinicalItemList", examItemList);

        // 第三部分：营养处方信息
        List<DiagnosisPrescriptionPojo> extenderList = diagnosisService.queryDiagnosisPrescriptionByDiagnosisId(
                diagnosisId);
        map.put("extenderList", extenderList);

        // 第四部分：膳食方案信息
        PregIntervenePlanPojo planPojo = pregPlanService.getIntervenePlanByDiagnosisId(diagnosisId);
        map.put("planPojo", planPojo);

        // 第五部分：预约接诊信息、预约检查项目信息
        DiagnosisBookingCondition bookingCondition = new DiagnosisBookingCondition();
        bookingCondition.setDiagnosisId(diagnosisId);
        List<DiagnosisBookingPojo> diagnosisBookingList = diagnosisService.queryDiagnosisBookings(bookingCondition);
        map.put("bookingList", diagnosisBookingList);

        DiagnosisQuotaItemCondition fuzhuCondition = new DiagnosisQuotaItemCondition();
        fuzhuCondition.setDiagnosisId(diagnosisId);
        List<DiagnosisQuotaItemVo> fuzhuList = diagnosisService.queryDiagnosisQuotaItem(fuzhuCondition);
        map.put("fuzhuList", fuzhuList);

        // 第六部分：课程预约显示
        PregArchivesPojo archivesPojo = pregArchivesService.getLastPregnancyArchive(diagnosisPojo
                .getDiagnosisCustomer());
        PregCourseBooking courseCondition = new PregCourseBooking();
        courseCondition.setArchivesId(archivesPojo.getId());
        List<PregCourseBookingPojo> courseBooking = courseBookingService.queryCourseBooking(courseCondition);
        List<PregCourseBookingPojo> courseBookingFinal = new ArrayList<PregCourseBookingPojo>();
        if (CollectionUtils.isNotEmpty(courseBooking)) {
            for (PregCourseBookingPojo pojo : courseBooking) {
                if (pojo.getBookingDate().compareTo(diagnosisPojo.getDiagnosisDate()) == 1) {
                    courseBookingFinal.add(pojo);
                }
            }
        }
        map.put("courseBookList", courseBookingFinal);
        map.put("obstetrical", diagnosisService.getObstetricalByDiagnosisId(diagnosisId));

        // 设置结果
        model.addAttribute("resultMap", NetJsonUtils.objectToJson(map));

        // 设置打印内容
        Map<String, List<PrintPojo>> printListMap = institutionService.getInsPrintListMap(this.getLoginUser()
                .getUserPojo()
                .getCreateInsId());
        model.addAttribute("printListMap", printListMap);

        // 设置妊娠日记内容
        PrintCondition printCondition = new PrintCondition();
        printCondition.setInsId(this.getLoginUser().getUserPojo().getCreateInsId());
        printCondition.setPrintPreview(1);// 预览标志
        List<PrintPojo> previewList = institutionService.queryInsPrint(printCondition);
        model.addAttribute("previewList", previewList);

        return EvaluatePageName.DISGNOSIS_SUMMARY_VIEW;
    }

    /**
     * 获取检查项目结论，并且格式化
     * 
     * @author xdc
     * @param diagnosisId
     * @param preArchivePojo
     * @return
     */
    private String getInspectResult(String diagnosisId, PregArchivesPojo preArchivePojo) {
        Map<String, ExamResultRecordPojo> resultMap = diagnosisService.getDiseaseInspectResult(diagnosisId);
        List<String> inspectResultList = new ArrayList<String>();
        if (preArchivePojo != null && StringUtils.isNotBlank(preArchivePojo.getPregnancyResult())) {
            inspectResultList.add("孕期建档：" + preArchivePojo.getPregnancyResult());
        }
        for (String inspectCode : resultMap.keySet()) {
            String result = resultMap.get(inspectCode).getDiagnosisResultNames();
            if (StringUtils.isBlank(result)) {
                continue;
            }
            // 膳食回顾
            if ("A00001".equals(inspectCode)) {
                inspectResultList.add("膳食日记回顾：" + result);
            }
            // 膳食及生活方式分析结果
            if ("B00002".equals(inspectCode)) {
                inspectResultList.add("膳食及生活方式分析结果：" + result.replaceAll(";", ""));
            }
            // 人体成分分析结果
            if ("B00003".equals(inspectCode)) {
                inspectResultList.add("人体成分分析结果：" + result);
            }
            // 膳食回顾
            if ("B00005".equals(inspectCode)) {
                inspectResultList.add("24小时膳食回顾：" + result);
            }
            // 快速营养调查
            if ("B00006".equals(inspectCode)) {
                inspectResultList.add("快速营养调查：" + result);
            }
            // 生活方式
            if ("B00007".equals(inspectCode)) {
                inspectResultList.add("孕期生活方式调查：" + result);
            }
            // 膳食频率调查
            if ("B00008".equals(inspectCode)) {
                inspectResultList.add("孕期膳食频率调查：" + result);
            }
        }
        return StringUtils.join(inspectResultList, "\n");
    }

}
