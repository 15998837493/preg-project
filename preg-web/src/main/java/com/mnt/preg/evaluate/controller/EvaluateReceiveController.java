
package com.mnt.preg.evaluate.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.evaluate.mapping.EvaluateMapping;
import com.mnt.preg.evaluate.mapping.EvaluatePageName;
import com.mnt.preg.examitem.service.QuestionAnswerService;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InspectItemPojo;
import com.mnt.preg.master.service.items.DiseaseItemDeduceService;
import com.mnt.preg.master.service.items.HospitalInspectPayService;
import com.mnt.preg.master.service.items.InspectItemService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregPlanJiezhenPojo;
import com.mnt.preg.platform.pojo.UserInspectItemPojo;
import com.mnt.preg.platform.service.DiagnosisHospitalItemService;
import com.mnt.preg.platform.service.DiseaseOftenService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.platform.service.UserInspectItemService;
import com.mnt.preg.web.controller.BaseController;

/**
 * 接诊平台--问诊页面
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-18 xdc 初版
 */
@Controller
public class EvaluateReceiveController extends BaseController {

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private InspectItemService inspectItemService;

    @Resource
    private HospitalInspectPayService hospitalInspectPayService;

    @Resource
    private DiseaseItemDeduceService diseaseItemDeduceService;

    @Resource
    private PregPlanService pregPlanService;

    @Resource
    private DiseaseOftenService diseaseOftenService;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    @Resource
    private PregDiagnosisDiseaseService diagnosisDiseaseService;

    @Resource
    private UserInspectItemService userInspectItemService;

    @Autowired
    private DiagnosisHospitalItemService itemService;

    // ***************************************【问诊页面引导】**********************************************
    /**
     * 获取问诊主页面
     * 
     * @author zcq
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = EvaluateMapping.EVALUATE_RECEIVE_MAIN)
    public String getReceiveMainView(String diagnosisId, String custId, Model model) {
        // 接诊历史记录
        DiagnosisCondition hisCondition = new DiagnosisCondition();
        hisCondition.setDiagnosisStatus(2);
        hisCondition.setDiagnosisCustomer(custId);
        hisCondition.setEndDate(JodaTimeTools.toDate(JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6)));
        List<PregDiagnosisPojo> historyList = diagnosisService.queryDiagnosis(hisCondition);
        model.addAttribute("historyList", historyList);

        // 接诊主页面--接诊信息
        PregPlanJiezhenPojo jiezhenVo = this.getInitPlanJiezhen(diagnosisId);
        // 患者信息
        CustomerPojo customerDto = jiezhenVo.getCustomer();
        // 诊断项目
        PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(custId);

        model.addAttribute("diagnosis", jiezhenVo.getDiagnosis());
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("preArchive", preArchive);
        model.addAttribute("customerInfo", customerDto);
        model.addAttribute("custId", customerDto.getCustId());
        model.addAttribute("obstetrical", diagnosisService.getObstetricalByDiagnosisId(diagnosisId));

        // 不包含今天接诊的信息
        DiagnosisCondition dCondition = new DiagnosisCondition();
        dCondition.setDiagnosisCustomer(customerDto.getCustId());
        String dateEnd = JodaTimeTools.toString(JodaTimeTools.before_current_day(0), JodaTimeTools.FORMAT_6);
        dCondition.setEndDate(JodaTimeTools.toDate(dateEnd));
        dCondition.setDiagnosisStatus(2);
        List<PregDiagnosisPojo> diagnosisHisList = diagnosisDiseaseService.queryDiagnosisDiseaseByCondition(dCondition);
        model.addAttribute("diagnosisHisList", NetJsonUtils.listToJsonArray(diagnosisHisList));

        // 接诊主页面--系统营养评价信息（张传强）
        DiagnosisItemsCondition diCondition = new DiagnosisItemsCondition();
        diCondition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisItemsVo> diagnosisItemList = diagnosisService.queryDiagnosisItem(diCondition);
        model.addAttribute("diagnosisItemList", NetJsonUtils.listToJsonArray(diagnosisItemList));
        List<InspectItemPojo> inspectAllList = inspectItemService.queryInspectItem(null);
        model.addAttribute("inspectAllListJson", NetJsonUtils.listToJsonArray(inspectAllList));
        List<UserInspectItemPojo> userInspectList = userInspectItemService.queryUserInspectByType(jiezhenVo
                .getDiagnosis().getDiagnosisUser(), "vist");
        model.addAttribute("userInspectListJson", NetJsonUtils.listToJsonArray(userInspectList));
        model.addAttribute("loginUserId", this.getLoginUserId());

        // 接诊主页面--指标录入信息
        getCheckItemsView(diagnosisId, custId, model);

        return EvaluatePageName.EVALUATE_RECEIVE_MAIN;
    }

    /************************************************** 工具方法 ******************************************************/

    /**
     * 获取接诊的基本信息
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    private PregPlanJiezhenPojo getInitPlanJiezhen(String diagnosisId) {
        PregPlanJiezhenPojo group = new PregPlanJiezhenPojo();
        // 设置接诊登记信息
        PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);
        group.setDiagnosis(diagnosis);
        // 设置患者信息
        group.setCustomer(customerService.getCustomer(diagnosis.getDiagnosisCustomer()));

        return group;
    }

    /**
     * 检验项目录入页面
     * 
     * @author mlq
     * @param diagnosisId
     * @param custId
     * @param model
     */
    private void getCheckItemsView(String diagnosisId, String custId, Model model) {
        // 本次接诊的报告单信息
        List<DiagnosisHospitalInspectReportPojo> reportList = itemService.queryDiagnosisReports(diagnosisId);
        model.addAttribute("reportList", NetJsonUtils.listToJsonArray(reportList));
        // 接诊的辅助项目信息
        PregDiagnosisPojo lastDiagnosis = diagnosisService.getLastDiagnosis(custId);
        List<DiagnosisQuotaItemVo> lastQuotaList = new ArrayList<DiagnosisQuotaItemVo>();
        if (lastDiagnosis != null) {
            DiagnosisQuotaItemCondition fuzhuCondition = new DiagnosisQuotaItemCondition();
            fuzhuCondition.setDiagnosisId(lastDiagnosis.getDiagnosisId());
            lastQuotaList = diagnosisService.queryDiagnosisQuotaItem(fuzhuCondition);
        }
        model.addAttribute("lastQuotaList", NetJsonUtils.listToJsonArray(lastQuotaList));
        // 获取所有医院收费项目
        List<HospitalInspectPayPojo> hospitalInspectList = hospitalInspectPayService
                .queryInspectPayAndItemByCondition(null);
        model.addAttribute("hospitalInspectPayList", NetJsonUtils.listToJsonArray(hospitalInspectList));
        // 获取所有的诊断项目
        model.addAttribute("interveneDiseaseList",
                NetJsonUtils.listToJsonArray(interveneDiseaseService.queryInterveneDisease(null)));

        model.addAttribute("diagnosisId", diagnosisId);
    }
}
