
package com.mnt.preg.examitem.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.preg.customer.entity.QuesAllocation;
import com.mnt.preg.customer.entity.QuestionAnswer;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.QuestionRecordPojo;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.form.InitQuestionForm;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.examitem.service.LifeStyleSurveyService;
import com.mnt.preg.examitem.service.NutritiousSurveyService;
import com.mnt.preg.examitem.service.QuestionAnswerService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.examitem.util.QuestionConstant;
import com.mnt.preg.master.mapping.QuestionMapping;
import com.mnt.preg.master.mapping.QuestionPageName;
import com.mnt.preg.master.pojo.question.QuestionPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemsPojo;
import com.mnt.preg.master.pojo.question.QuestionRecordBo;
import com.mnt.preg.master.service.question.ProblemService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.NutritiousPdf;
import com.mnt.preg.web.pdf.PregLifeStyleSurveyPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 
 * 问卷调查相关操作
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-8 mnt_zhangjing 初版
 */
@Controller
public class QuestionAnswerController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerController.class);

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private ExamItemUtil examItemUtil;

    @Resource
    private ProblemService problemService;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    @Resource
    private NutritiousSurveyService nutritiousSurveyService;

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private LifeStyleSurveyService lifeStyleSurveyService;

    /**
     * 分配问卷
     * 
     * @author mnt_zhangjing
     * @param form
     * @param model
     * @return
     */
    @RequestMapping(value = QuestionMapping.ALLOCATION_QUESTION)
    public String allocaQuestionInfo(InitQuestionForm form, Model model) {
        CustomerPojo customerPojo = customerService.getCustomer(form.getCustId());
        QuesAllocation quesAllocationBo = TransformerUtils.transformerProperties(QuesAllocation.class, form);
        quesAllocationBo.setAllocDatetime(new Date());
        quesAllocationBo.setUserId(this.getLoginUser().getUserPojo().getUserId());
        String questionAllocId = "";
        questionAllocId = questionService.addAllocation(quesAllocationBo);
        model.addAttribute("questionAllocId", questionAllocId);
        model.addAttribute("questionId", form.getQuestionId());
        model.addAttribute("diagnosisId", form.getDiagnosisId());
        model.addAttribute("inspectId", form.getId());
        model.addAttribute("problemNo",
                problemService.getProblemNumByQuestionId(form.getQuestionId(), customerPojo.getCustSex()));

        if (customerPojo == null || StringUtils.isEmpty(customerPojo.getCustSex())) {
            LOGGER.error("[问卷调查]分配的用户不存在或者用户的性别为空");
            model.addAttribute("sex", "");
        } else {
            model.addAttribute("sex", customerPojo.getCustSex());
        }

        QuestionPojo questionPojo = questionService.getQuestionById(form.getQuestionId());
        if (questionPojo == null || StringUtils.isEmpty(questionPojo.getQuestionName())) {
            LOGGER.error("[问卷调查]分配的问卷不存在或者问卷的名称为空");
            model.addAttribute("questionName", "");
        } else {
            model.addAttribute("questionName", questionPojo.getQuestionName());
        }

        return QuestionPageName.QUESTION_VIEW;
    }

    @RequestMapping(value = QuestionMapping.INIT_QUESTION)
    public @ResponseBody
    WebResult<QuestionPojo> initQuestionInfo(String questionId, String sex) {
        WebResult<QuestionPojo> webResult = new WebResult<QuestionPojo>();
        QuestionPojo questionDto = this.initQuestion(questionId, sex);
        webResult.setSuc(questionDto);
        return webResult;
    }

    @RequestMapping(value = QuestionMapping.SAVE_QUESTION)
    public @ResponseBody
    WebResult<QuestionRecordPojo> saveQuestionInfo(String allocId, String diagnosisId, String questionAnswers) {
        WebResult<QuestionRecordPojo> webResult = new WebResult<QuestionRecordPojo>();
        if (StringUtils.isEmpty(allocId)) {
            webResult.setError(WebMsgConstant.fail_message);
            LOGGER.error("[问卷调查]保存问卷信息异常,分配编号为空");
            return webResult;
        }
        Gson gson = new Gson();
        List<QuestionAnswer> questionAnswerBos = gson.fromJson(questionAnswers,
                new TypeToken<List<QuestionAnswer>>(){}.getType());

        QuestionRecordBo questionRecordBo = new QuestionRecordBo();
        questionRecordBo.setQuestionAllocId(allocId);
        questionRecordBo.setDiagnosisId(diagnosisId);
        questionRecordBo.setSubmitDate(new Date());
        QuestionRecordPojo questionRecordDto = this.saveQuestionRecord(questionRecordBo, questionAnswerBos);
        webResult.setSuc(questionRecordDto, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 查看pdf报告
     * 
     * @author dhs
     * @param
     * @return
     */
    @RequestMapping(value = QuestionMapping.NUTRITIOUS_SURVEY_REPOER)
    public @ResponseBody
    WebResult<String> getNutritiousServeyReport(String id) {
        WebResult<String> webResult = new WebResult<String>();
        // 第一步：实例化对象
        NutritiousPdf pdf = new NutritiousPdf(){

            @Override
            public NutritiousReportPojo beforeCreatePdf(ReportForm reportForm) {
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm.getReportCode());
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                PregArchivesPojo archive = pregArchivesService
                        .getLastPregnancyArchive(diagnosis.getDiagnosisCustomer());
                ExamResultRecordPojo record = examResultRecordUtil.getExamResultRecordByExamCodeAndExamCategory(
                        diagnosisItem.getResultCode(), ExamItemConstant.EXAM_ITEM_CODE.B00006);
                Map<String, ExamItemPojo> map = examItemUtil.queryExamItemMapByExamId(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00006, record.getExamId());
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
                pojo.setDiagnosis(diagnosis);
                pojo.setCustomer(customerService.getCustomer(diagnosis.getDiagnosisCustomer()));
                pojo.setPregnancyArchives(archive);
                pojo.setDiagnosisItem(diagnosisItem);
                pojo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return pojo;
            }
        };

        // 第二步：创建PDF
        String pdfpath = pdf.create(id);
        webResult.setSuc(pdfpath);
        return webResult;
    }

    /**
     * 查看生活方式pdf报告
     * 
     * @author dhs
     * @param
     * @return
     */
    @RequestMapping(value = QuestionMapping.LIFE_STYLE_SERVEY_REPORT)
    public @ResponseBody
    WebResult<String> getLifeStyleServeyReport(String id) {
        WebResult<String> webResult = new WebResult<String>();
        // 第一步：实例化对象
        PregLifeStyleSurveyPdf pdf = new PregLifeStyleSurveyPdf(){

            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = diagnosisService.getDiagnosisItem(reportForm.getReportCode());
                String resultCode = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                dietReportVo.setDiagnosis(diagnosis);
                dietReportVo.setDiagnosisItem(diagnosisItem);
                dietReportVo.setCustomer(customerService.getCustomer(diagnosis.getDiagnosisCustomer()));
                dietReportVo.setPregnancyArchives(pregArchivesService.getLastPregnancyArchive(diagnosis
                        .getDiagnosisCustomer()));
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00007, resultCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };

        // 第二步：创建PDF
        String pdfpath = pdf.create(id);
        webResult.setSuc(pdfpath);
        return webResult;
    }

    public QuestionPojo initQuestion(String questionId, String sex) {
        // 目前只考虑打个问卷，如果是多个问卷时需要进行去重处理
        // 1 查询问卷信息
        QuestionPojo questionVo = questionService.getQuestion(questionId);
        // 2 查询问卷下的问题
        List<QuestionProblemsPojo> problemVos = questionAnswerService.queryProblemByQuesId(questionId, sex);
        if (CollectionUtils.isNotEmpty(problemVos)) {
            for (QuestionProblemsPojo problemVo : problemVos) {
                if (problemVo.getProblemIsChildren() == QuestionConstant.HAS_CHILDREN) {
                    problemVo.setName(problemVo.getProblemContent());
                }
                // 3 查询问题下的答案选项
                List<QuestionProblemOptionsPojo> optionVos = questionService.queryOptionByProId(
                        problemVo.getProblemId(), sex);
                // 4 设置问题下的答案选项
                problemVo.setOptionVos(optionVos);
            }
        }
        if (questionVo != null) {
            // 5 设置问卷下的问题
            questionVo.setProblemVos(problemVos);
        }
        return questionVo;
    }

    public QuestionRecordPojo saveQuestionRecord(QuestionRecordBo questionRecordBo,
            List<QuestionAnswer> questionAnswerBos) {
        List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();// 结果表明细
        String diagnosisId = questionRecordBo.getDiagnosisId();// 接诊编号
        String questionAllocId = questionRecordBo.getQuestionAllocId();// 问卷分配编号
        String examCategory = "";// 项目编码
        String examTable = "";
        String diagnosisResultNames = "";// 结论
        // 1 问卷--查询分配信息
        QuesAllocation quesAllocationVo = questionService.getAllocation(questionAllocId);
        if (quesAllocationVo == null) {
            LOGGER.warn("【提交问卷】问卷分配明细未能查询到");
        }
        String questionId = quesAllocationVo.getQuestionId();// 问卷编号
        // 2 问卷--保存操作记录
        if (questionAnswerService.getQuestionnaireInfo(questionAllocId) != null) {
            LOGGER.warn("【提交问卷】分配编号已经被使用过了");
        }
        questionRecordBo.setState(QuestionConstant.QUESTION_STATUS_FINISH);// 设置问卷状态
        questionRecordBo.setCreateInsId(this.getLoginUser().getUserPojo().getCreateInsId());// 设置所属机构
        questionRecordBo.setAllocDatetime(quesAllocationVo.getAllocDatetime());
        questionRecordBo.setCustId(quesAllocationVo.getCustId());
        questionRecordBo.setExpiryDate(quesAllocationVo.getExpiryDate());
        questionRecordBo.setQuestionId(questionId);
        questionRecordBo.setUserId(quesAllocationVo.getUserId());
        QuestionRecordPojo questionRecordVo = questionAnswerService.saveQuestionnaire(questionRecordBo);
        // 3 问卷--保存答案记录
        if (questionRecordVo != null && StringUtils.isNotEmpty(questionAllocId)
                && CollectionUtils.isNotEmpty(questionAnswerBos)) {
            for (QuestionAnswer questionAnswerBo : questionAnswerBos) {
                questionAnswerBo.setQuestionAllocId(questionAllocId);
                questionAnswerService.saveQuestionAnswer(questionAnswerBo);
            }
        }
        // 4 问卷--删除分配表中的信息
        questionService.remove(questionAllocId);

        // 5 数据分析
        // 快速营养调查问卷
        if (StringUtils.isNotEmpty(questionId) && ("C00000011000022018010900005").equals(questionId) &&
                StringUtils.isNotEmpty(diagnosisId)) {
            NutritiousReportPojo nutritiousPojo = nutritiousSurveyService.analyseNutritious(questionAllocId);
            detailList.add(getExamDataDetail("1", "食欲食量", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getEats(), null, null, null));
            detailList.add(getExamDataDetail("2", "膳食偏好", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getFoodPreference(), null, null, null));
            detailList.add(getExamDataDetail("3", "膳食摄入", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getFoodIntake(), null, null, null));
            detailList.add(getExamDataDetail("4", "不良饮食喜好", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getBadDietary(), null, null, null));
            detailList.add(getExamDataDetail("5", "不良用餐习惯", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getBadHabits(), null, null, null));
            detailList.add(getExamDataDetail("6", "体能消耗问题", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getStaminaRup(), null, null, null));
            detailList.add(getExamDataDetail("7", "运动消耗问题", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getSportRup(), null, "", null));
            detailList.add(getExamDataDetail("8", "膳食结构风险", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getFoodRisk(), null, null, null));
            detailList.add(getExamDataDetail("9", "致病饮食风险", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getIllRisk(), null, null, null));
            detailList.add(getExamDataDetail("10", "营养咨询需求", null, ExamItemConstant.EXAM_ITEM_TABLE.B00006,
                    nutritiousPojo.getNutritionalRequirements(), null, null, null));
            examCategory = ExamItemConstant.EXAM_ITEM_CODE.B00006;
            examTable = ExamItemConstant.EXAM_ITEM_TABLE.B00006;
            if (StringUtils.isNotEmpty(this.replaceString(nutritiousPojo.getStaminaRup()))) {
                diagnosisResultNames += (this.replaceString(nutritiousPojo.getStaminaRup())) + "、";
            }
            if (StringUtils.isNotEmpty(this.replaceString(nutritiousPojo.getSportRup()))) {
                diagnosisResultNames += (this.replaceString(nutritiousPojo.getSportRup())) + "、";
            }
            if (StringUtils.isNotEmpty(this.replaceString(nutritiousPojo.getFoodRisk()))) {
                diagnosisResultNames += (this.replaceString(nutritiousPojo.getFoodRisk())) + "、";
            }
            if (StringUtils.isNotEmpty(this.replaceString(nutritiousPojo.getIllRisk()))) {
                diagnosisResultNames += (this.replaceString(nutritiousPojo.getIllRisk()));
            }
            if ("、".equals(diagnosisResultNames.substring(diagnosisResultNames.length() - 1))) {
                diagnosisResultNames = diagnosisResultNames.substring(0, diagnosisResultNames.length() - 1);
            }
        } else if (StringUtils.isNotEmpty(questionId) && ("C00000011000022018011200076").equals(questionId) &&
                StringUtils.isNotEmpty(diagnosisId)) {
            detailList = lifeStyleSurveyService.analyseLifestyleServey(questionAllocId);
            examCategory = ExamItemConstant.EXAM_ITEM_CODE.B00007;
            examTable = ExamItemConstant.EXAM_ITEM_TABLE.B00007;
        }

        // 6 记录主表
        ExamResultRecord examResultRecord = TransformerUtils.transformerProperties(ExamResultRecord.class,
                customerService.getCustomer(questionRecordBo.getCustId()));
        examResultRecord.setExamCategory(examCategory);
        examResultRecord.setExamCode(questionAllocId);
        examResultRecord.setExamDate(new Date());
        examResultRecord.setDiagnosisResultNames(diagnosisResultNames);// 结论
        String examId = examResultRecordUtil.addExamResultRecord(examResultRecord);

        // 7 记录明细
        examItemUtil.addExamItems(examTable, examId, detailList);
        // 8 更新结论
        examResultRecord.setExamId(examId);
        examResultRecordUtil.updateExamResultRecordForExam(examResultRecord, examTable);

        return questionRecordVo;
    }

    /************************************************************* 工具方法 ********************************************************/

    /**
     * 分配结果表明细
     * 
     * @author dhs
     * @param
     * @return
     */
    private ExamItemPojo getExamDataDetail(String itemCode, String itemName, String itemRefValue,
            String itemType, String itemString, String itemResult, String itemCompare, String itemUnit) {
        ExamItemPojo detail = new ExamItemPojo();
        detail.setItemCode(itemCode);
        detail.setItemName(itemName);
        detail.setItemRefValue(itemRefValue);
        detail.setItemType(itemType);
        detail.setItemString(itemString);
        detail.setItemResult(itemResult);
        detail.setItemCompare(itemCompare);
        detail.setItemUnit(itemUnit);
        return detail;
    }

    /**
     * 替换中英文模式下的；为、 如果入参为空或空字符串则返回空字符串 返回参数末尾不带分号或顿号
     * 
     * @author dhs
     * @param String
     * @return
     */
    private String replaceString(String str) {
        if (StringUtils.isNotBlank(str) && !"无".equals(str)) {
            String str1 = str.replaceAll(";", "、");
            String str2 = str1.replaceAll("；", "、");
            if ("、".equals(str2.substring(str2.length() - 1, str2.length()))) {
                return str2.substring(0, str2.length() - 1);
            } else {
                return str2;
            }
        } else {
            return "";
        }
    }

}
