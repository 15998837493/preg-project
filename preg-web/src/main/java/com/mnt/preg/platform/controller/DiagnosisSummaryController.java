
package com.mnt.preg.platform.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.files.FileUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.pdf.PdfUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.customer.service.PregCourseBookingService;
import com.mnt.preg.examitem.condition.ExamItemCondition;
import com.mnt.preg.examitem.condition.InbodyCondition;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.examitem.pojo.ExtenderReportPojo;
import com.mnt.preg.examitem.pojo.GuideListReportPojo;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;
import com.mnt.preg.examitem.pojo.PregDiagnosisSummaryReportPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.examitem.service.GuideListService;
import com.mnt.preg.examitem.service.NutritiousSurveyService;
import com.mnt.preg.examitem.service.PregDietFoodRecordService;
import com.mnt.preg.examitem.service.PregInbodyService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.entity.PregArchives;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.mapping.PlatFormPageName;
import com.mnt.preg.platform.mapping.PlatformMapping;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanGroupPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;
import com.mnt.preg.platform.service.DiagnosisHospitalItemService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.system.condition.PrintCondition;
import com.mnt.preg.system.pojo.InstitutionPojo;
import com.mnt.preg.system.pojo.PrintPojo;
import com.mnt.preg.web.constants.ConstantPath;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.DMDietPdf;
import com.mnt.preg.web.pdf.DiaryPdf;
import com.mnt.preg.web.pdf.DietaryFrequencyPdf;
import com.mnt.preg.web.pdf.DietaryReviewPdf;
import com.mnt.preg.web.pdf.ExtenderAssessPdf;
import com.mnt.preg.web.pdf.GuideListPdf;
import com.mnt.preg.web.pdf.InBodyPdf;
import com.mnt.preg.web.pdf.NutritiousPdf;
import com.mnt.preg.web.pdf.PlanSummaryPdf;
import com.mnt.preg.web.pdf.PlanTreatmentPdf;
import com.mnt.preg.web.pdf.PregLifeStyleSurveyPdf;
import com.mnt.preg.web.pdf.PregnancyLifeStylePdf;
import com.mnt.preg.web.pdf.WBSPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 营养病例
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-19 xdc 初版
 */
@Controller
public class DiagnosisSummaryController extends BaseController {

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private PregPlanService pregPlanService;

    @Resource
    private PregCourseBookingService courseBookingService;

    @Resource
    public ExamItemUtil examItemUtil;

    @Resource
    public PregInbodyService pregInbodyService;

    @Resource
    public ExamResultRecordUtil examResultRecordUtil;

    @Resource
    public GuideListService guideListService;

    @Resource
    public NutritiousSurveyService nutritiousSurveyService;

    @Resource
    public PregDietFoodRecordService pregDietFoodRecordService;

    @Resource
    private DiagnosisHospitalItemService itemService;

    // ***************************************【营养病例】**********************************************
    /**
     * JSP页面--guide_top.jsp 营养病历页面
     * 
     * @author zcq
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_SUMMARY_PAGE)
    public String toDiagnosisSummaryPage(String diagnosisId, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();

        // 第一部分：基础信息：接诊信息、患者信息、孕期建档信息
        PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisId);
        String custId = diagnosisPojo.getDiagnosisCustomer();
        CustomerPojo customerPojo = customerService.getCustomer(custId);
        PregArchivesPojo preArchivePojo = pregArchivesService.getLastPregnancyArchive(custId);
        // 获取营养评价结论
        diagnosisPojo.setDiagnosisInspectResult(getInspectResult(diagnosisId, preArchivePojo));
        map.put("diagnosis", diagnosisPojo);
        map.put("preArchive", preArchivePojo);
        map.put("customer", customerPojo);

        // 第二部分：辅助检查指标信息
        // PregDiagnosisClinicalCondition condition = new PregDiagnosisClinicalCondition();
        // condition.setDiagnosisId(diagnosisId);
        // condition.setQueryNotNull(true);// 不检索检测日期和检测结果为空的指标
        // 查询指标
        // condition.setQueryNotNull(true);
        // List<PregDiagnosisClinicalPojo> examItemList = clinicalService.queryDiagnosisClinical(condition);
        // map.put("clinicalItemList", examItemList);
        List<DiagnosisHospitalInspectReportPojo> examItemList = itemService.queryDiagnosisReports(diagnosisId);
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

        return PlatFormPageName.DISGNOSIS_SUMMARY_VIEW;
    }

    /**
     * 检验pdf报告是否有内容
     * 
     * @author dhs
     * @param diagnosisId
     *            archId
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = PlatformMapping.PDF_ISOK)
    public @ResponseBody WebResult<Map<String, Boolean>> pdfIsok(String diagnosisId, String archId) {
        WebResult<Map<String, Boolean>> webResult = new WebResult<Map<String, Boolean>>();
        webResult.setSuc(pregPlanService.validPdf(diagnosisId, archId));
        return webResult;
    }

    /**
     * 干预方案--打印PDF报告
     * 
     * @author zcq
     * @param planId
     * @param model
     */
    @RequestMapping(value = PlatformMapping.RECEIVE_CREATE_PDF)
    public @ResponseBody WebResult<String> createDiagnosisPDF(String diagnosisId, String codes) {
        WebResult<String> webResult = new WebResult<String>();

        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisItemsVo> diagnosisItemList = diagnosisService.queryDiagnosisItem(condition);
        Map<String, String> itemMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(diagnosisItemList)) {
            for (PregDiagnosisItemsVo item : diagnosisItemList) {
                if (item.getInspectStatus() == 3) {
                    itemMap.put(item.getInspectCode(), item.getId());
                }
            }
        }
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        String[] files = new String[14];
        String fileName = diagnosisId + "_diagnosis.pdf";
        String diagnosisPath = ConstantPath.PDF_PATH + insId + "/P00003/"
                + JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_6) + "/";
        new File(publicConfig.getResourceAbsolutePath() + diagnosisPath).mkdirs();

        int index = 0;

        /** 第一部分：指标PDF报告 */
        // createDiagnosisItemPdf(diagnosisId, codes, files, index++);
        /** 第二部分：膳食及生活方式PDF报告 */
        createDietLifePdf(itemMap, codes, files, index++);
        /** 第三部分：人体成分PDF报告 */
        createInbodyPdf(itemMap, codes, files, index++);
        /** 第四部分：剂量评估PDF报告 */
        createExtenderPdf(itemMap, codes, files, index++);
        /** 第五部分：膳食PDF报告 */
        createDietPlanPdf(codes, diagnosisId, files, index++);
        /** 第六部分：快速营养调查问卷PDF报告 */
        createNutritiousPdf(codes, diagnosisId, files, index++);
        /** 第七部分：24小时膳食回顾PDF报告 */
        createDietaryReviewPdf(itemMap, codes, files, index++);
        /** 第八部分：病历小结PDF报告 */
        createPlanSummaryPdf(codes, diagnosisId, files, index++);
        /** 第九部分：妊娠日记PDF报告 */
        createDiaryPdf(codes, diagnosisId, files, index++);
        /** 第十部分：妊娠日记--长沙市妇幼保健院体重监测、血糖监测表PDF报告 */
        createWBSPdf(codes, diagnosisId, files, index++);
        /** 第十一部分：长沙市妇幼保健院妊娠糖尿病饮食记录表 */
        createDMDietPdf(codes, diagnosisId, files, index++);
        /** 第十二部分：引导单PDF报告 */
        createGuideListPdf(codes, diagnosisId, files, index++);
        /** 第十三部分：膳食频率PDF报告 */
        createDietaryFrequencyPdf(itemMap, codes, files, index++);
        /** 第十四部分：生活方式调查问卷PDF报告 */
        createLifeStylePdf(itemMap, codes, files, index++);
        // 复制PDF作为模板
        boolean isHasfile = false;
        for (String file : files) {
            if (StringUtils.isNotEmpty(file)) {
                FileUtils.fileChannelCopy(new File(file), new File(publicConfig.getResourceAbsolutePath()
                        + diagnosisPath + fileName));
                isHasfile = true;
                break;
            }
        }
        // 合并PDF
        if (isHasfile) {
            PdfUtils.getIns().mergePdfFiles(files, publicConfig.getResourceAbsolutePath() + diagnosisPath + fileName);
            webResult.setSuc(diagnosisPath + fileName);
        } else {
            webResult.setError("没有报告！");
        }

        return webResult;
    }

    // /**
    // * 指标PDF报告
    // *
    // * @author zcq
    // * @param diagnosisId
    // * @param codes
    // * @param files
    // * @param index
    // */
    // private void createDiagnosisItemPdf(String diagnosisId, String codes, String[] files, int index) {
    // String insId = this.getLoginUser().getUserPojo().getCreateInsId();
    // if (StringUtils.isNotEmpty(codes) && codes.indexOf("P01") > -1) {
    // ReportForm quotaForm = new ReportForm();
    // quotaForm.setInsId(insId);
    // quotaForm.setReportCode(diagnosisId);
    // quotaForm.setReportItem("P00002");
    // if (StringUtils.isNotEmpty(codes)) {
    // quotaForm.setCodeList(Arrays.asList(codes.split(",")));
    // }
    // QuotaItemPdf itemPdf = new QuotaItemPdf(){
    //
    // @Override
    // public PregQuotaReportPojo beforeCreatePdf(ReportForm reportForm) {
    // // 获取膳食方案记录
    // PregQuotaReportPojo quotaReportVo = examItemUtil
    // .getPregQuotaReportInfo(reportForm.getReportCode());
    // quotaReportVo.setCodeItemList(reportForm.getCodeList());
    //
    // return quotaReportVo;
    // }
    // };
    // files[index] = publicConfig.getResourceAbsolutePath() + itemPdf.create(quotaForm);
    // }
    // }

    /**
     * 膳食评价及生活方式PDF报告
     * 
     * @author zcq
     * @param itemMap
     * @param codes
     * @param files
     * @param index
     */
    private void createDietLifePdf(Map<String, String> itemMap, String codes, String[] files, int index) {
        String lifeId = itemMap.get(ExamItemConstant.EXAM_ITEM_CODE.B00002);
        if (StringUtils.isNotEmpty(lifeId) && codes.indexOf("P02001") > -1) {
            files[index] = reportB00002(lifeId);
        }
    }

    /**
     * 膳食评价及生活方式PDF报告
     *
     * @author zcq
     * @param inspectId
     * @return
     */
    private String reportB00002(String inspectId) {
        PregnancyLifeStylePdf lifePdf = new PregnancyLifeStylePdf(){

            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm
                        .getReportCode());
                String resultCode = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                dietReportVo.setDiagnosis(diagnosis);
                dietReportVo.setDiagnosisItem(diagnosisItem);
                dietReportVo.setCustomer(customerService.getCustomer(diagnosis.getDiagnosisCustomer()));
                dietReportVo.setPregnancyArchives(pregArchivesService.getLastPregnancyArchive(diagnosis
                        .getDiagnosisCustomer()));
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00002, resultCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };
        return publicConfig.getResourceAbsolutePath() + lifePdf.create(inspectId);
    }

    /**
     * 体成分PDF报告
     * 
     * @author zcq
     * @param itemMap
     * @param codes
     * @param files
     * @param index
     */
    private void createInbodyPdf(Map<String, String> itemMap, String codes, String[] files, int index) {
        String inbodyId = itemMap.get(ExamItemConstant.EXAM_ITEM_CODE.B00003);
        if (StringUtils.isNotEmpty(inbodyId) && codes.indexOf("P02002") > -1) {
            files[index] = reportB00003(inbodyId);
        }
    }

    /**
     * 体成分PDF报告
     *
     * @author zcq
     * @param inspectId
     * @return
     */
    private String reportB00003(String inspectId) {
        InBodyPdf inbodyPdf = new InBodyPdf(){

            @Override
            public PregInBodyBcaPojo beforeCreatePdf(ReportForm reportForm) {
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm
                        .getReportCode());
                String bcaId = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                InbodyCondition condition = new InbodyCondition();
                condition.setBcaId(bcaId);
                PregInBodyBcaPojo inBodyVo = pregInbodyService.getInbodyByCondition(condition);

                inBodyVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00003, bcaId));// 获取分析指标结果
                InbodyCondition histroyCondition = new InbodyCondition();
                histroyCondition.setCustId(inBodyVo.getCustId());
                inBodyVo.setHistoryList(pregInbodyService.queryInbodyHistory(histroyCondition)); // 历史记录
                PregArchivesPojo pregVo = pregArchivesService.getLastPregnancyArchive(inBodyVo.getCustId());
                if (pregVo == null) {
                    pregVo = new PregArchivesPojo();
                }
                inBodyVo.setPregVo(pregVo); // 获取患者孕前信息
                CustomerPojo custVo = customerService.getCustomer(inBodyVo.getCustId());
                inBodyVo.setCustomerVo(custVo);// 患者基本信息
                inBodyVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                inBodyVo.setDiagnosis(diagnosis);
                inBodyVo.setDiagnosisItem(diagnosisItem);
                return inBodyVo;
            }
        };
        return publicConfig.getResourceAbsolutePath() + inbodyPdf.create(inspectId);
    }

    /**
     * 计量评估PDF报告
     * 
     * @author zcq
     * @param itemMap
     * @param codes
     * @param files
     * @param index
     */
    private void createExtenderPdf(Map<String, String> itemMap, String codes, String[] files, int index) {
        String extenderId = itemMap.get(ExamItemConstant.EXAM_ITEM_CODE.B00004);
        ReportForm reportForm = new ReportForm();
        reportForm.setInsId(this.getLoginUser().getUserPojo().getCreateInsId());
        reportForm.setReportCode(extenderId);
        reportForm.setReportItem(ExamItemConstant.EXAM_ITEM_CODE.B00004);
        if (StringUtils.isNotEmpty(extenderId) && codes.indexOf("P02003") > -1) {
            ExtenderAssessPdf assessPdf = new ExtenderAssessPdf(){

                @Override
                public ExtenderReportPojo beforeCreatePdf(ReportForm reportForm) {
                    // 获取分析结果信息
                    PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm
                            .getReportCode());
                    String resultCode = diagnosisItem.getResultCode();
                    PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                    ExtenderReportPojo reportVo = new ExtenderReportPojo();
                    ExamItemCondition examCondition = new ExamItemCondition();
                    examCondition.setTableName(ExamItemConstant.EXAM_ITEM_TABLE.B00004);
                    examCondition.setResultCode(resultCode);
                    reportVo.setExamItemList(examItemUtil.queryExamItem(examCondition));// 指标结果
                    reportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                    reportVo.setDiagnosis(diagnosis);
                    reportVo.setDiagnosisItem(diagnosisItem);
                    return reportVo;
                }

                @Override
                public void afterCreatePdf(ReportForm reportForm) {
                    // 完善结果主表PDF报告路径
                    PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm
                            .getReportCode());
                    String resultCode = diagnosisItem.getResultCode();
                    ExamResultRecord examResultRecord = new ExamResultRecord();
                    examResultRecord.setExamCode(resultCode);
                    examResultRecord.setExamPdf(reportForm.getReportPath());
                    examResultRecordUtil.updateExamResultRecord(examResultRecord);
                }
            };
            files[index] = publicConfig.getResourceAbsolutePath() + assessPdf.create(reportForm);
        }
    }

    /**
     * 饮食管理方案
     * 
     * @author zcq
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    private void createDietPlanPdf(String codes, String diagnosisId, String[] files, int index) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        if (codes.indexOf("P03") > -1) {
            ReportForm planForm = new ReportForm();
            planForm.setInsId(insId);
            planForm.setReportCode(diagnosisId);
            planForm.setReportItem("P00001");
            if (StringUtils.isNotEmpty(codes)) {
                planForm.setCodeList(Arrays.asList(codes.split(",")));
            }
            PlanTreatmentPdf treatmentPdf = new PlanTreatmentPdf(){

                @Override
                public PregIntervenePlanGroupPojo beforeCreatePdf(ReportForm reportForm) {

                    // 获取膳食方案记录
                    PregIntervenePlanGroupPojo planGroupVo = pregPlanService
                            .getIntervenePlanGroup(reportForm.getReportCode());
                    planGroupVo.setInsInfo(institutionService.getIns(reportForm.getInsId()));
                    planGroupVo.setDietItemList(reportForm.getCodeList());
                    // 机构信息
                    InstitutionPojo insInfo = institutionService.getIns(reportForm.getInsId());
                    planGroupVo.setInsInfo(insInfo);
                    // 复检复查信息
                    PregDiagnosisPojo diagnosisPojo = planGroupVo.getDiagnosis();

                    DiagnosisBookingCondition bookingCondition = new DiagnosisBookingCondition();
                    bookingCondition.setDiagnosisId(diagnosisPojo.getDiagnosisId());
                    planGroupVo.setDiagnosisBookingList(diagnosisService.queryDiagnosisBookings(bookingCondition));

                    DiagnosisQuotaItemCondition fuzhuCondition = new DiagnosisQuotaItemCondition();
                    fuzhuCondition.setDiagnosisId(reportForm.getReportCode());
                    planGroupVo.setFuzhuList(diagnosisService.queryDiagnosisQuotaItem(fuzhuCondition));
                    // 课程预约信息
                    PregArchivesPojo archivesPojo = pregArchivesService.getLastPregnancyArchive(diagnosisPojo
                            .getDiagnosisCustomer());
                    PregCourseBooking courseCondition = new PregCourseBooking();
                    courseCondition.setArchivesId(archivesPojo.getId());
                    List<PregCourseBookingPojo> courseBookingList = courseBookingService
                            .queryCourseBooking(courseCondition);
                    planGroupVo.setCourseBookingList(courseBookingList);
                    planGroupVo.setDiagnosis(diagnosisPojo);
                    return planGroupVo;
                }

                @Override
                public void afterCreatePdf(ReportForm reportForm) {
                    // 修改完善登记表
                    PregDiagnosis pregDiagnosis = new PregDiagnosis();
                    pregDiagnosis.setDiagnosisId(reportForm.getReportCode());
                    pregDiagnosis.setDiagnosisPlanPdf(reportForm.getReportPath());
                    diagnosisService.updateDiagnosis(pregDiagnosis);
                }
            };
            files[index] = publicConfig.getResourceAbsolutePath() + treatmentPdf.create(planForm);
        }
    }

    /**
     * 病历小结PDF报告
     * 
     * @author zcq
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    private void createPlanSummaryPdf(String codes, String diagnosisId, String[] files, int index) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        if (codes.indexOf("P04001") > -1) {
            ReportForm diagnosisForm = new ReportForm();
            diagnosisForm.setInsId(insId);
            diagnosisForm.setReportCode(diagnosisId);
            diagnosisForm.setReportItem("P00004");
            if (StringUtils.isNotEmpty(codes)) {
                diagnosisForm.setCodeList(Arrays.asList(codes.split(",")));
            }
            PlanSummaryPdf summaryPdf = new PlanSummaryPdf(){

                @Override
                public PregDiagnosisSummaryReportPojo beforeCreatePdf(ReportForm reportForm) {
                    // 第一部分：基础信息：接诊信息、患者信息、孕期建档信息
                    PregDiagnosisSummaryReportPojo summaryVo = new PregDiagnosisSummaryReportPojo();
                    String diagnosisId = reportForm.getReportCode();
                    PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisId);
                    String custId = diagnosisPojo.getDiagnosisCustomer();
                    PregArchivesPojo preArchivePojo = pregArchivesService.getLastPregnancyArchive(custId);
                    // 设置检查项目结论
                    diagnosisPojo.setDiagnosisInspectResult(getInspectResult(diagnosisPojo.getDiagnosisId(),
                            preArchivePojo));
                    summaryVo.setDiagnosisPojo(diagnosisPojo);
                    summaryVo.setCustomerPojo(customerService.getCustomer(custId));
                    summaryVo.setPreArchivePojo(pregArchivesService.getLastPregnancyArchive(custId));
                    // 第二部分：辅助检查指标信息
                    // PregDiagnosisClinicalCondition examItemCondition = new PregDiagnosisClinicalCondition();
                    // examItemCondition.setDiagnosisId(diagnosisId);
                    // examItemCondition.setQueryNotNull(true);
                    // summaryVo.setExamItemList(clinicalService.queryDiagnosisClinical(examItemCondition));
                    summaryVo.setExamItemList(itemService.queryDiagnosisReports(diagnosisId));
                    // 第三部分：营养处方信息
                    summaryVo.setExtenderList(diagnosisService
                            .queryDiagnosisPrescriptionByDiagnosisId(diagnosisId));
                    // 第四部分：膳食方案信息
                    summaryVo.setPlanPojo(pregPlanService.getIntervenePlanByDiagnosisId(diagnosisId));
                    // 第五部分：预约接诊信息、预约检查项目信息
                    DiagnosisBookingCondition condition = new DiagnosisBookingCondition();
                    condition.setDiagnosisId(diagnosisId);
                    summaryVo.setDiagnosisList(diagnosisService.queryDiagnosisBookings(condition));
                    DiagnosisQuotaItemCondition fuzhuCondition = new DiagnosisQuotaItemCondition();
                    fuzhuCondition.setDiagnosisId(diagnosisId);
                    summaryVo.setFuzhuList(diagnosisService.queryDiagnosisQuotaItem(fuzhuCondition));
                    // 第六部分：课程预约信息
                    PregArchivesPojo archivesPojo = pregArchivesService.getLastPregnancyArchive(diagnosisPojo
                            .getDiagnosisCustomer());
                    PregCourseBooking courseCondition = new PregCourseBooking();
                    courseCondition.setArchivesId(archivesPojo.getId());
                    List<PregCourseBookingPojo> pregCourseBookingList = courseBookingService
                            .queryCourseBooking(courseCondition);
                    List<PregCourseBookingPojo> pregCourseBookingFinalList = new ArrayList<PregCourseBookingPojo>();
                    if (CollectionUtils.isNotEmpty(pregCourseBookingList)) {
                        for (PregCourseBookingPojo pojo : pregCourseBookingList) {
                            if (pojo.getBookingDate().compareTo(diagnosisPojo.getDiagnosisDate()) == 1) {
                                pregCourseBookingFinalList.add(pojo);
                            }
                        }
                    }
                    summaryVo.setCourseBookingList(pregCourseBookingFinalList);
                    PregDiagnosisObstetricalPojo pregDiagnosisObstetricalPojo = diagnosisService
                            .getObstetricalByDiagnosisId(diagnosisId);
                    summaryVo
                            .setDiagnosisObstetricalPojo(pregDiagnosisObstetricalPojo == null ? new PregDiagnosisObstetricalPojo()
                                    : pregDiagnosisObstetricalPojo);
                    summaryVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                    return summaryVo;
                }
            };
            files[index] = publicConfig.getResourceAbsolutePath() + summaryPdf.create(diagnosisForm);
        }
    }

    /**
     * 妊娠日记PDF报告
     * 
     * @author dhs
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    private void createDiaryPdf(String codes, String diagnosisId, String[] files, int index) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        if (codes.indexOf("P05001") > -1) {
            ReportForm diagnosisForm = new ReportForm();
            diagnosisForm.setInsId(insId);
            diagnosisForm.setReportCode(diagnosisId);
            diagnosisForm.setReportItem("P00005");
            if (StringUtils.isNotEmpty(codes)) {
                diagnosisForm.setCodeList(Arrays.asList(codes.split(",")));
            }
            DiaryPdf summaryPdf = new DiaryPdf(){

                @Override
                public PregDiagnosisPojo beforeCreatePdf(ReportForm reportForm) {
                    PregDiagnosisPojo pregDiagnosisPojo = diagnosisService.getDiagnosis(reportForm.getReportCode());
                    // 查询孕期建档信息
                    PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(pregDiagnosisPojo
                            .getDiagnosisCustomer());
                    PregDiagnosisPojo preg = pregPlanService.analysisDiary(reportForm.getReportCode());
                    preg.setDiagnosisArchiveWeight(preArchive.getWeight());// 孕前体重
                    preg.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                    return preg;
                }
            };
            files[index] = publicConfig.getResourceAbsolutePath() + summaryPdf.create(diagnosisForm);
        }
    }

    /**
     * 孕期快速营养调查
     * 
     * @author dhs
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    private void createNutritiousPdf(String codes, String diagnosisId, String[] files, int index) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        if (codes.indexOf("P02004") > -1) {
            ReportForm diagnosisForm = new ReportForm();
            diagnosisForm.setInsId(insId);
            diagnosisForm.setReportCode(diagnosisId);
            diagnosisForm.setReportItem("P02004");
            if (StringUtils.isNotEmpty(codes)) {
                diagnosisForm.setCodeList(Arrays.asList(codes.split(",")));
            }
            files[index] = reportB00006(diagnosisForm);
        }
    }

    /**
     * 孕期快速营养调查
     *
     * @author zcq
     * @param reportForm
     * @return
     */
    private String reportB00006(ReportForm reportForm) {
        NutritiousPdf pdf = new NutritiousPdf(){

            @Override
            public NutritiousReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDiagnosisPojo pregDiagnosisPojo = diagnosisService.getDiagnosis(reportForm.getReportCode());
                PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(pregDiagnosisPojo
                        .getDiagnosisCustomer());
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItemsByDiagnosisIdAndInspectItem(
                        reportForm.getReportCode(), ExamItemConstant.EXAM_ITEM_CODE.B00006);
                Map<String, ExamItemPojo> map = examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00006, diagnosisItem.getResultCode());
                NutritiousReportPojo pojo = new NutritiousReportPojo();
                pojo.setEats(map.get("1").getItemString());
                pojo.setFoodPreference(map.get("2").getItemString());
                pojo.setFoodIntake(map.get("3").getItemString());
                pojo.setBadDietary(map.get("4").getItemString());
                pojo.setBadHabits(map.get("5").getItemString());
                pojo.setStaminaRup(map.get("6").getItemString());
                pojo.setSportRup(map.get("7").getItemString());
                pojo.setFoodRisk(map.get("8").getItemString());
                pojo.setIllRisk(map.get("9").getItemString());
                pojo.setNutritionalRequirements(map.get("10").getItemString());
                pojo.setDiagnosis(pregDiagnosisPojo);
                pojo.setCustomer(customerService.getCustomer(pregDiagnosisPojo.getDiagnosisCustomer()));
                pojo.setPregnancyArchives(preArchive);
                pojo.setDiagnosisItem(diagnosisItem);
                pojo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return pojo;
            }
        };
        return publicConfig.getResourceAbsolutePath() + pdf.create(reportForm);
    }

    /**
     * 24小时膳食回顾
     *
     * @author zcq
     * @param itemMap
     * @param codes
     * @param files
     * @param index
     */
    private void createDietaryReviewPdf(Map<String, String> itemMap, String codes, String[] files, int index) {
        String lifeId = itemMap.get(ExamItemConstant.EXAM_ITEM_CODE.B00005);
        if (codes.indexOf("P02005") > -1) {
            files[index] = reportB00005(lifeId);
        }
    }

    /**
     * 24小时膳食回顾
     *
     * @author zcq
     * @param inspectId
     * @return
     */
    private String reportB00005(String inspectId) {
        // 第一步：实例化对象
        DietaryReviewPdf dietReportPdf = new DietaryReviewPdf(){

            // 诊断项目
            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm
                        .getReportCode());
                String examCode = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                // 接诊信息
                dietReportVo.setDiagnosis(diagnosis);
                dietReportVo.setDiagnosisItem(diagnosisItem);
                // 建档信息
                dietReportVo.setPregnancyArchives(pregArchivesService.getLastPregnancyArchive(diagnosis
                        .getDiagnosisCustomer()));
                // 分析结果
                dietReportVo.setFoodRecord(pregDietFoodRecordService.getFoodRecord(examCode));
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00005, examCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };
        return publicConfig.getResourceAbsolutePath() + dietReportPdf.create(inspectId);
    }

    /**
     * 
     * 妊娠日记--长沙市妇幼保健院体重监测、血糖监测表PDF报告
     * 
     * @author scd
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    private void createWBSPdf(String codes, String diagnosisId, String[] files, int index) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        if (codes.indexOf("P06001") > -1) {
            ReportForm diagnosisForm = new ReportForm();
            diagnosisForm.setInsId(insId);
            diagnosisForm.setReportCode(diagnosisId);
            diagnosisForm.setReportItem("P00006");
            if (StringUtils.isNotEmpty(codes)) {
                diagnosisForm.setCodeList(Arrays.asList(codes.split(",")));
            }
            WBSPdf wBSPdf = new WBSPdf(){

                @Override
                public Map<String, String> beforeCreatePdf(ReportForm reportForm) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("insId", getLoginUser().getUserPojo().getCreateInsId());// 机构号
                    return map;
                }
            };
            files[index] = publicConfig.getResourceAbsolutePath() + wBSPdf.create(diagnosisForm);
        }
    }

    /**
     * 
     * 长沙市妇幼保健院
     * 妊娠糖尿病饮食记录表
     * 
     * @author scd
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    private void createDMDietPdf(String codes, String diagnosisId, String[] files, int index) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        if (codes.indexOf("P07001") > -1) {
            ReportForm diagnosisForm = new ReportForm();
            diagnosisForm.setInsId(insId);
            diagnosisForm.setReportCode(diagnosisId);
            diagnosisForm.setReportItem("P00007");
            if (StringUtils.isNotEmpty(codes)) {
                diagnosisForm.setCodeList(Arrays.asList(codes.split(",")));
            }
            DMDietPdf dMDietPdf = new DMDietPdf(){

                @Override
                public Map<String, String> beforeCreatePdf(ReportForm reportForm) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("insId", getLoginUser().getUserPojo().getCreateInsId());// 机构号
                    return map;
                }
            };
            files[index] = publicConfig.getResourceAbsolutePath() + dMDietPdf.create(diagnosisForm);
        }
    }

    /**
     * 引导单
     * 
     * @author dhs
     * @param codes
     * @param diagnosisId
     * @param files
     * @param index
     */
    private void createGuideListPdf(String codes, String diagnosisId, String[] files, int index) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        if (codes.indexOf("P08001") > -1) {
            ReportForm diagnosisForm = new ReportForm();
            diagnosisForm.setInsId(insId);
            diagnosisForm.setReportCode(diagnosisId);
            diagnosisForm.setReportItem("P08001");
            if (StringUtils.isNotEmpty(codes)) {
                diagnosisForm.setCodeList(Arrays.asList(codes.split(",")));
            }
            GuideListPdf pdf = new GuideListPdf(){

                @Override
                public GuideListReportPojo beforeCreatePdf(ReportForm reportForm) {
                    PregDiagnosisPojo pregDiagnosisPojo = diagnosisService.getDiagnosis(reportForm.getReportCode());
                    PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(pregDiagnosisPojo
                            .getDiagnosisCustomer());
                    // 查询引导单记录表是否有记录
                    Map<String, ExamItemPojo> map_guide = examItemUtil.queryExamItemMapByExamId(
                            ExamItemConstant.EXAM_ITEM_TABLE.guide, preArchive.getId());
                    boolean createPdf = false;
                    if (map_guide.size() > 0) {// 不是首次
                        createPdf = true;
                    }
                    PregArchives pregnancyArchives = new PregArchives();
                    pregnancyArchives.setCreatePdf(createPdf);
                    pregnancyArchives.setId(preArchive.getId());
                    pregArchivesService.updatePregnancyArchives(pregnancyArchives);// 更改引导单pdf状态
                    PregDiagnosisItemsVo item = diagnosisService.getDiagnosisItemsByDiagnosisIdAndInspectItem(
                            reportForm.getReportCode(), ExamItemConstant.EXAM_ITEM_CODE.B00003);// 人体成分
                    NutritiousReportPojo nutritiousReportPojo = new NutritiousReportPojo();// 快速营养调查问卷数据
                    PregDiagnosisItemsVo itemNutritious = diagnosisService
                            .getDiagnosisItemsByDiagnosisIdAndInspectItem(
                                    reportForm.getReportCode(), ExamItemConstant.EXAM_ITEM_CODE.B00006);
                    if (itemNutritious == null || StringUtils.isEmpty(itemNutritious.getResultCode())) {// 如果当前没有做快速营养调查问卷，就去取引导单记录表中快速营养调查问卷的数据
                        // 如果inspectCode为空，createPdf就一定会为true(map_guide不会为空)，否则也进入不到这个方法中，如果出问题，问题就在jsp打印置灰控制中
                        nutritiousReportPojo.setStaminaRup(map_guide.get("A00006").getItemString());// 体能消耗问题
                        nutritiousReportPojo.setFoodRisk(map_guide.get("A00007").getItemString());// 膳食结构风险
                        nutritiousReportPojo.setIllRisk(map_guide.get("A00008").getItemString());// 致病饮食风险
                        nutritiousReportPojo.setFoodQuestion(map_guide.get("A00011").getItemString());// 膳食问题
                        nutritiousReportPojo.setNutritionalRequirements(map_guide.get("A00009").getItemString());// 营养咨询需求
                    } else {// 如果做了快速营养调查问卷，就现分析数据
                        nutritiousReportPojo = nutritiousSurveyService.analyseNutritious(itemNutritious
                                .getResultCode());
                        // 将体能消耗问题和运动消耗问题合并，都是必填项没有为空的情况
                        nutritiousReportPojo.setStaminaRup(nutritiousReportPojo.getStaminaRup() + ";"
                                + nutritiousReportPojo.getSportRup());
                    }
                    Map<String, ExamItemPojo> map = null;
                    if (item != null && StringUtils.isNotBlank(item.getResultCode())) {
                        map = examItemUtil.queryExamItemMapByResultCode(ExamItemConstant.EXAM_ITEM_TABLE.B00003,
                                item.getResultCode());// 人体成分明细
                    }
                    GuideListReportPojo pojo = guideListService.analyseGuideList(reportForm.getReportCode(),
                            preArchive.getQuestionAllocId(), item,
                            map, nutritiousReportPojo);
                    pojo.setDiagnosis(pregDiagnosisPojo);
                    pojo.setCustomer(customerService.getCustomer(pregDiagnosisPojo.getDiagnosisCustomer()));
                    pojo.setPregnancyArchives(preArchive);
                    pojo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                    // 保存到结果表
                    List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();// 结果表明细
                    if (StringUtils.isBlank(pojo.getInbody())) {
                        pojo.setInbody("无");
                    }
                    examItemUtil.deleteExamItem(ExamItemConstant.EXAM_ITEM_TABLE.guide, preArchive.getId());// 清空本次接诊下的结果表
                    detailList.add(getExamDataDetail("A00001", "代谢异常风险因素_病史", null, pojo.getMetabolicBingshi(), null,
                            null, null));
                    detailList.add(getExamDataDetail("A00002", "代谢异常风险因素_既往妊娠并发症及分娩史", null,
                            pojo.getMetabolicHistory(), null, null, null));
                    detailList.add(getExamDataDetail("A00003", "营养不良风险因素_病史", null, pojo.getNutritiousBingshi(), null,
                            null, null));
                    detailList.add(getExamDataDetail("A00004", "营养不良风险因素_既往妊娠并发症及分娩史", null,
                            pojo.getNutritiousHistory(), null, null, null));
                    detailList.add(getExamDataDetail("A00005", "人体成分结论", null, pojo.getInbody(), null, null, null));
                    detailList.add(getExamDataDetail("A00006", "体能消耗情况", null, pojo.getRup(), null, null, null));
                    detailList.add(getExamDataDetail("A00007", "膳食结构风险", null, pojo.getFoodRisk(), null, null, null));
                    detailList.add(getExamDataDetail("A00008", "致病饮食风险", null, pojo.getIllRisk(), null, null, null));
                    detailList.add(getExamDataDetail("A00009", "营养咨询需求", null, pojo.getNutritionalRequirements(), null,
                            null, null));
                    detailList.add(getExamDataDetail("A00010", "过敏情况", null, pojo.getAllergic(), null, null, null));
                    detailList.add(getExamDataDetail("A00011", "妊娠期膳食摄入问题 ", null, pojo.getFoodQuestion(), null, null,
                            null));
                    detailList.add(getExamDataDetail("A00012", "转诊建议", null, pojo.getSuggest(), null, null, null));
                    examItemUtil.addExamItems(ExamItemConstant.EXAM_ITEM_TABLE.guide, preArchive.getId(), detailList);// 保存
                    pojo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                    return pojo;
                }
            };
            files[index] = publicConfig.getResourceAbsolutePath() + pdf.create(diagnosisForm);
        }
    }

    /**
     * 膳食频率PDF报告
     * 
     * @author scd
     * @param itemMap
     * @param codes
     * @param files
     * @param index
     */
    public void createDietaryFrequencyPdf(Map<String, String> itemMap, String codes, String[] files, int index) {
        String inspeictId = itemMap.get(ExamItemConstant.EXAM_ITEM_CODE.B00008);
        if (codes.indexOf("P02006") > -1) {
            files[index] = reportB00008(inspeictId);
        }
    }

    /**
     * 膳食频率PDF报告
     *
     * @author zcq
     * @param inspectId
     * @return
     */
    private String reportB00008(String inspectId) {
        // 第一步：实例化对象
        DietaryFrequencyPdf DietaryFrequencyPdf = new DietaryFrequencyPdf(){

            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm.getReportCode());
                String inspectCode = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                dietReportVo.setDiagnosis(diagnosis);
                dietReportVo.setDiagnosisItem(diagnosisItem);
                dietReportVo.setCustomer(customerService.getCustomer(diagnosis.getDiagnosisCustomer()));
                dietReportVo.setPregnancyArchives(pregArchivesService.getLastPregnancyArchive(diagnosis
                        .getDiagnosisCustomer()));
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00008, inspectCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };
        // 第二步：创建PDF
        return publicConfig.getResourceAbsolutePath() + DietaryFrequencyPdf.create(inspectId);
    }

    /**
     * 生活方式调查问卷PDF
     * 
     * @author scd
     * @param itemMap
     * @param codes
     * @param files
     * @param index
     */
    public void createLifeStylePdf(Map<String, String> itemMap, String codes, String[] files, int index) {
        String lifeId = itemMap.get(ExamItemConstant.EXAM_ITEM_CODE.B00007);
        if (codes.indexOf("P02007") > -1) {

            files[index] = reportB00007(lifeId);
        }
    }

    /**
     * 生活方式调查问卷PDF
     *
     * @author zcq
     * @param inspectId
     * @return
     */
    private String reportB00007(String inspectId) {
        // 第一步：实例化对象
        PregLifeStyleSurveyPdf lifePdf = new PregLifeStyleSurveyPdf(){

            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm.getReportCode());
                String inspectCode = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                dietReportVo.setDiagnosis(diagnosis);
                dietReportVo.setCustomer(customerService.getCustomer(diagnosis.getDiagnosisCustomer()));
                dietReportVo.setPregnancyArchives(pregArchivesService.getLastPregnancyArchive(diagnosis
                        .getDiagnosisCustomer()));
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00007, inspectCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                dietReportVo.setDiagnosisItem(diagnosisItem);
                return dietReportVo;
            }
        };
        return publicConfig.getResourceAbsolutePath() + lifePdf.create(inspectId);
    }

    /************************************************************* 工具方法 ********************************************************/
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

    /**
     * 分配结果表明细
     * 
     * @author dhs
     * @param
     * @return
     */
    private ExamItemPojo getExamDataDetail(String itemCode, String itemName, String itemRefValue, String itemString,
            String itemResult, String itemCompare, String itemUnit) {
        ExamItemPojo detail = new ExamItemPojo();
        detail.setItemCode(itemCode);
        detail.setItemName(itemName);
        detail.setItemRefValue(itemRefValue);
        detail.setItemString(itemString);
        detail.setItemResult(itemResult);
        detail.setItemCompare(itemCompare);
        detail.setItemUnit(itemUnit);
        return detail;
    }

    /**
     * 重新生成PDF报告
     *
     * @author zcq
     * @param examId
     * @return
     */
    @RequestMapping(value = PlatformMapping.REGENERATE_REPORT)
    @ResponseBody
    public WebResult<String> regenerateReport(String examId) {
        WebResult<String> webResult = new WebResult<String>();

        ExamResultRecordPojo examRecord = examResultRecordUtil.getExamResultRecordByExamId(examId);
        if (examRecord == null) {
            return webResult.setError("没找到该记录的信息！");
        }
        String examCategory = examRecord.getExamCategory();
        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setInspectCode(examCategory);
        condition.setResultCode(examRecord.getExamCode());
        List<PregDiagnosisItemsVo> diagnosisItemList = diagnosisService.queryDiagnosisItem(condition);
        String inspectId = "";
        String diagnosisId = "";
        if (CollectionUtils.isNotEmpty(diagnosisItemList)) {
            PregDiagnosisItemsVo itemPojo = diagnosisItemList.get(0);
            if (itemPojo != null && itemPojo.getInspectStatus() == 3) {
                inspectId = itemPojo.getId();
                diagnosisId = itemPojo.getDiagnosisId();
            }
        }
        String diagnosisPath = examRecord.getExamPdf();
        if (StringUtils.isBlank(inspectId)) {
            return webResult.setError("没找到该记录的信息！");
        }
        // 孕期膳食频率调查：B00008
        if ("B00008".equals(examCategory)) {
            reportB00008(inspectId);
        }
        // 孕期生活方式调查：B00007
        if ("B00007".equals(examCategory)) {
            reportB00007(inspectId);
        }
        // 孕期人体成分：B00003
        if ("B00003".equals(examCategory)) {
            reportB00003(inspectId);
        }
        // 孕期快速营养调查：B00006
        if ("B00006".equals(examCategory)) {
            String insId = this.getLoginUser().getUserPojo().getCreateInsId();
            ReportForm diagnosisForm = new ReportForm();
            diagnosisForm.setInsId(insId);
            diagnosisForm.setReportCode(diagnosisId);
            diagnosisForm.setReportItem("P02004");
            diagnosisForm.setReportPath(diagnosisPath);
            reportB00006(diagnosisForm);
        }
        // 孕期24小时膳食回顾：B00005
        if ("B00005".equals(examCategory)) {
            reportB00005(inspectId);
        }
        // 孕期膳食及生活方式评估：B00002
        if ("B00002".equals(examCategory)) {
            reportB00002(inspectId);
        }

        return webResult.setSuc(diagnosisPath);
    }

}
