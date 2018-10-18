
package com.mnt.preg.platform.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.entity.PregAdjustRecord;
import com.mnt.preg.customer.entity.QuesAllocation;
import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.customer.service.PregAdjustRecordService;
import com.mnt.preg.examitem.service.QuestionAnswerService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.master.pojo.question.QuestionPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemsPojo;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.entity.PregArchives;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.form.DiagnosisForm;
import com.mnt.preg.platform.mapping.PlatFormPageName;
import com.mnt.preg.platform.mapping.PlatformMapping;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

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
public class DiagnosisToolbarController extends BaseController {

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
    private PregPlanService planService;

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
    @RequestMapping(value = PlatformMapping.PLAN_GUIDE_PAGE)
    public String toGuidePage(String diagnosisId, Model model) {
        // 接诊信息
        PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisId);
        String custId = diagnosisPojo.getDiagnosisCustomer();
        // 查询患者信息
        CustomerPojo customerInfo = customerService.getCustomer(custId);
        // 查询孕期建档信息
        PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(custId);
        // 更改接诊状态
        Integer diagnosisStatus = diagnosisPojo.getDiagnosisStatus();
        if (diagnosisStatus == null || diagnosisStatus == 1) {
            PregDiagnosis diagnosis = new PregDiagnosis();
            diagnosis.setDiagnosisId(diagnosisId);
            // diagnosis.setDiagnosisStatus(2);// 1=未接诊,2=已接诊,
            diagnosisService.updateDiagnosis(diagnosis);
        }
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

        // 初始化饮食原则和教育课程
        updateUserPlan(diagnosisId);

        model.addAttribute("diagnosis", diagnosisPojo);
        model.addAttribute("diagnosisJson", NetJsonUtils.objectToJson(diagnosisPojo));
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("preArchive", preArchive);
        model.addAttribute("preArchiveJson", (preArchive == null) ? "null" : NetJsonUtils.objectToJson(preArchive));
        model.addAttribute("recordJson", (record == null) ? "null" : NetJsonUtils.objectToJson(record));
        model.addAttribute("title", "诊疗平台--" + customerInfo.getCustName());
        model.addAttribute("loginUserId", this.getLoginUserId() + diagnosisId);
        return PlatFormPageName.PLAN_GUIDE_PAGE;
    }

    // ***************************************【个人信息的相关方法】**********************************************
    /**
     * 更新接诊信息，更新建档信息，更新调整记录
     * 
     * @author xdc
     * @param form
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_UPDATE_ARCHIVES)
    public @ResponseBody
    WebResult<PregDiagnosisPojo> updateDiagnosisArchives(DiagnosisForm diagnosisForm) {
        PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisForm.getDiagnosisId());
        String custId = diagnosisPojo.getDiagnosisCustomer();
        PregArchivesPojo archivesVo = pregArchivesService.getLastPregnancyArchive(custId);
        // 1.更新调整记录表中的信息
        String dueDateNewString = JodaTimeTools.toString(diagnosisForm.getDueDateNew(), JodaTimeTools.FORMAT_6);
        String dueDateOldString = JodaTimeTools.toString(archivesVo.getPregnancyDueDate(), JodaTimeTools.FORMAT_6);
        // 如果新的预产期和原来的不一样就说明调整了
        String gestationalWeeksNew = "";
        if (!dueDateOldString.equals(dueDateNewString) || StringUtils.isNotEmpty(diagnosisForm.getAdjustReason())) {
            PregAdjustRecord adjustPojo = adjustRecordService.getPregAdjustRecordsByDiagnosisId(diagnosisForm
                    .getDiagnosisId());
            // 计算孕周
            String dateString = JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_6);
            Date date = JodaTimeTools.toDate(dateString);// 格式化的日期 ，不含时分秒
            int intervalDays = JodaTimeTools.getDays(date, diagnosisForm.getDueDateNew());
            int pregnantDays = 280 - intervalDays;
            int pregnantWeeks = pregnantDays / 7;
            int plusDays = pregnantDays % 7;
            gestationalWeeksNew = pregnantWeeks + "+" + plusDays;
            // 计算末次月经
            Date lmpDate = JodaTimeTools.before_day(diagnosisForm.getDueDateNew(), 280);
            // 判断是否有调整记录
            if (adjustPojo == null) {
                adjustPojo = new PregAdjustRecord();
                adjustPojo.setDiagnosisId(diagnosisForm.getDiagnosisId());
                adjustPojo.setArchivesId(archivesVo.getId());
                // 原末次月经、原预产期、原孕周
                adjustPojo.setLmpDateOld(archivesVo.getLmp());
                adjustPojo.setDueDateOld(archivesVo.getPregnancyDueDate());
                adjustPojo.setGestationalWeeksOld(diagnosisPojo.getDiagnosisGestationalWeeks());
                // 新末次月经、新预产期、新孕周、调整原因
                adjustPojo.setLmpDateNew(lmpDate);
                adjustPojo.setDueDateNew(diagnosisForm.getDueDateNew());
                adjustPojo.setGestationalWeeksNew(gestationalWeeksNew);
                adjustPojo.setAdjustReason(diagnosisForm.getAdjustReason());
                adjustPojo.setAdjustDate(new Date());
                adjustRecordService.addPregAdjustResords(adjustPojo);
            } else {
                // 新末次月经、新预产期、新孕周、调整理由：更新时原有末次月经、预产期、孕周不更新
                adjustPojo.setLmpDateNew(JodaTimeTools.before_day(diagnosisForm.getDueDateNew(), 280));
                adjustPojo.setDueDateNew(diagnosisForm.getDueDateNew());
                adjustPojo.setGestationalWeeksNew(gestationalWeeksNew);
                adjustPojo.setAdjustReason(diagnosisForm.getAdjustReason());
                adjustRecordService.updatePregAdjustResords(adjustPojo);
            }

            // 2.更新建档登记表中的信息--如果更新了，就需要更新建档表中的信息
            archivesVo.setPregnancyDueDate(diagnosisForm.getDueDateNew());// 更新建档表中的预产期信息
            archivesVo.setLmp(lmpDate);
            PregArchives archives = TransformerUtils.transformerProperties(PregArchives.class, archivesVo);
            pregArchivesService.updatePregnancyArchives(archives);
        }
        // 3.更新接着表中的信息
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        PregDiagnosis diagnosis = TransformerUtils.transformerProperties(PregDiagnosis.class, diagnosisForm);
        if (StringUtils.isNotEmpty(gestationalWeeksNew)) {
            diagnosis.setDiagnosisGestationalWeeks(gestationalWeeksNew);
            int pregnantWeeks = Integer.valueOf(gestationalWeeksNew.split("[+]")[0]);
            String pregnantStage = "";
            if (pregnantWeeks < 12) {
                pregnantStage = "pregnancy_pre";
            } else if (12 <= pregnantWeeks && pregnantWeeks < 27) {
                pregnantStage = "pregnancy_mid";
            } else if (pregnantWeeks >= 27) {
                pregnantStage = "pregnancy_late";
            }
            diagnosis.setDiagnosisPregnantStage(pregnantStage);
        }
        diagnosisService.updateDiagnosis(diagnosis);
        // 如果已经创建了干预方案，就更新基础课程
        PregIntervenePlanPojo planPojo = pregPlanService.getIntervenePlanByDiagnosisId(diagnosisForm.getDiagnosisId());
        if (planPojo != null) {
            pregPlanService.savePlanCourse(diagnosisForm.getDiagnosisId(), planPojo.getPlanId(), "1");
        }
        webResult.setSuc(diagnosisService.getDiagnosis(diagnosis.getDiagnosisId()));

        updateUserPlan(diagnosisForm.getDiagnosisId());
        return webResult;
    }

    /**
     * 获取初诊建档页面
     * 
     * @author zcq
     * @param diagnosisId
     * @param view
     * @return
     */
    @RequestMapping(value = CustomerMapping.DIAGNOSIS_INITAL_VIEW_GET)
    public String getNewlyDiagnosedView(String diagnosisId, String openerType, Model model) {
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("openerType", openerType);
        return PlatFormPageName.NEW_DIAGNOSIS_VIEW;
    }

    /**
     * 获取初诊建档页面初始化数据
     * 
     * @author zcq
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_INITAL_VIEW_INIT)
    public @ResponseBody
    WebResult<QuestionPojo> initNewlyDiagnosedQuestion(String diagnosisId) {
        WebResult<QuestionPojo> webResult = new WebResult<QuestionPojo>();
        QuestionPojo questionData = new QuestionPojo();
        questionData = this.getNewlyDiagnosedView(diagnosisId);
        webResult.setSuc(questionData);
        return webResult;
    }

    /**
     * 查询调整历史
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.ADJUST_QUERY_HISTORY)
    public @ResponseBody
    WebResult<PregAdjustRecord> getAdjustQueryHistory(PregAdjustRecord condition) {
        WebResult<PregAdjustRecord> webResult = new WebResult<PregAdjustRecord>();
        List<PregAdjustRecord> adjustList = adjustRecordService.queryPregAdjustRecords(condition);
        webResult.setData(adjustList);
        return webResult;
    }

    // **************************************************【自定义辅助方法】**************************************************

    /**
     * 创建及编辑初诊建档
     * 
     * @author gss
     * @param diagnosisId
     */
    private QuestionPojo getNewlyDiagnosedView(String diagnosisId) {
        // 根据diagnosisId查询该用户的基本信息
        PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);// 登记信息

        CustomerPojo customer = customerService.getCustomer(diagnosis.getDiagnosisCustomer());
        PregArchivesPojo archivesVo =
                pregArchivesService.getLastPregnancyArchive(diagnosis.getDiagnosisCustomer());

        QuesAllocation quesAllow = new QuesAllocation();// 分配表
        quesAllow.setUserId(this.getLoginUser().getUserPojo().getUserId());// 用户id
        quesAllow.setCustId(diagnosis.getDiagnosisCustomer());// 患者id
        quesAllow.setQuestionId("C000000CA00000000006");
        // TODO:无法确保唯一性，需要重构 zcq
        String allowId =
                questionService.getOnceAllocationID(diagnosis.getDiagnosisCustomer(), diagnosis.getDiagnosisDate());
        if (StringUtils.isEmpty(allowId)) {
            allowId = questionService.addAllocation(quesAllow);// 分配号
        }
        // 患者登记时，没有性别，默认为女
        QuestionPojo questionVo = this.initQuestionInfo("C000000CA00000000006", "female");
        questionVo.setAllowId(allowId);
        // 获取答案信息
        // 根据id号查询孕期档案信息
        // PregArchivesVo archivesVo = pregArchivesService.getPregnancyArchiveBydiagnosisId(diagnosisId);
        List<QuestionAnswerPojo> answerVos = questionAnswerService.queryQuestionAnswer(allowId);
        if (CollectionUtils.isNotEmpty(answerVos)) {
            // 402880f45b2212c8015b230848550010 身高
            // 402880f45b2212c8015b230c05930015 体重
            // 402880f45b2212c8015b231b644a0019 出生日期
            // 402880f45b2212c8015b231d74520020 末次月经
            for (QuestionAnswerPojo answer : answerVos) {
                String proId = answer.getProblemId();
                if ("402880f45b2212c8015b230848550010".equals(proId) && customer.getCustHeight() != null) {
                    answer.setAnswerContent(customer.getCustHeight().toString());
                } else if ("402880f45b2212c8015b230c05930015".equals(proId) && customer.getCustWeight() != null) {
                    answer.setAnswerContent(customer.getCustWeight().toString());
                } else if ("402880f45b2212c8015b231b644a0019".equals(proId) && customer.getCustBirthday() != null) {
                    answer.setAnswerContent(JodaTimeTools.toString(customer.getCustBirthday(), JodaTimeTools.FORMAT_6));
                } else if ("402880f45b2212c8015b231d74520020".equals(proId) && archivesVo != null
                        && archivesVo.getLmp() != null) {
                    answer.setAnswerContent(JodaTimeTools.toString(archivesVo.getLmp(), JodaTimeTools.FORMAT_6));
                }
            }
        } else {
            QuestionAnswerPojo answerVo = new QuestionAnswerPojo();
            if (customer.getCustHeight() != null) {
                answerVo.setProblemId("402880f45b2212c8015b230848550010");
                answerVo.setProblemOptionId("11000022017072800008");
                answerVo.setAnswerContent(customer.getCustHeight().toString());
                answerVos.add(answerVo);
            }
            if (customer.getCustWeight() != null) {
                answerVo.setProblemId("402880f45b2212c8015b230c05930015");
                answerVo.setProblemOptionId("11000022017072800009");
                answerVo.setAnswerContent(customer.getCustWeight().toString());
                answerVos.add(answerVo);
            }
            if (customer.getCustBirthday() != null) {
                answerVo.setProblemId("402880f45b2212c8015b231b644a0019");
                answerVo.setProblemOptionId("110000200000259");
                answerVo.setAnswerContent(JodaTimeTools.toString(customer.getCustBirthday(), JodaTimeTools.FORMAT_6));
                answerVos.add(answerVo);
            }
            if (archivesVo != null && archivesVo.getLmp() != null && archivesVo.getPregnancyDueDate() != null
                    && JodaTimeTools.compareDate(archivesVo.getPregnancyDueDate(), new Date()) >= 0) {
                answerVo.setProblemId("402880f45b2212c8015b231d74520020");
                answerVo.setProblemOptionId("110000200000258");
                answerVo.setAnswerContent(JodaTimeTools.toString(archivesVo.getLmp(), JodaTimeTools.FORMAT_6));
                answerVos.add(answerVo);
            }
        }
        questionVo.setAnswerVos(answerVos);
        questionVo.setDiagnosisVo(diagnosis);
        // 根据档案中的问卷分配号查询答案信息
        return questionVo;
    }

    private QuestionPojo initQuestionInfo(String questionId, String sex) {
        QuestionPojo questionVo = questionService.getQuestion(questionId);// 查询问卷信息
        if (questionVo == null) {
            return null;
        }
        List<QuestionProblemsPojo> problemVos = questionAnswerService.queryProblemByQuesId(questionId, sex);// 问题信息
        // Map<String, QuestionProblemsVo> problemM = this.getproblemMap(problemVos);// 问题map集合
        if (CollectionUtils.isNotEmpty(problemVos)) {
            for (QuestionProblemsPojo problemVo : problemVos) {
                if (problemVo == null) {
                    continue;
                }

                problemVo.setOrderNo(problemVo.getProblemOrder().toString());// 设置问题的序号
                String parentId = problemVo.getProblemParentId();// 父节点的id
                if (StringUtils.isNotEmpty(parentId) && !("0".equals(parentId))) {// 判断是否有父节点，如果有序号变为：
                    problemVo.setOrderNo("");// 设置序号为：xx_xx
                    problemVo.setChild(true);
                }

                // 查询问题下的答案选项
                List<QuestionProblemOptionsPojo> optionVos = questionService.queryOptionByProId(
                        problemVo.getProblemId(), sex);
                Map<String, QuestionProblemOptionsPojo> optionM = this.getOptionMap(optionVos);// 选项集合
                if (1 == problemVo.getProblemIsChildren()) {
                    // 我要知道哪个字节点引用这个父节点-根据父节点的id查询子节点
                    List<QuestionProblemsPojo> childProblems = questionAnswerService.queryProblemByParentId(problemVo
                            .getProblemId());
                    if (CollectionUtils.isNotEmpty(childProblems)) {
                        for (QuestionProblemsPojo childVo : childProblems) {
                            if (childVo == null) {
                                continue;
                            }
                            // 选项规则
                            String rule = childVo.getProblemRule();
                            if (StringUtils.isEmpty(rule)) {
                                continue;
                            }
                            String[] ruleArrays = rule.split(",");
                            for (String s : ruleArrays) {
                                QuestionProblemOptionsPojo childOption = optionM.get(s.trim());
                                if (childOption == null) {
                                    continue;
                                }
                                // 然后根据子节点的关联编码设置父节点的选项中的子节点的id
                                String childIds = childOption.getChildProblemIds();
                                if (StringUtils.isEmpty(childIds)) {
                                    childOption.setChildProblemIds(childVo.getProblemId());
                                } else {
                                    childOption.setChildProblemIds(childOption.getChildProblemIds() + ","
                                            + childVo.getProblemId());
                                }
                            }
                        }

                    }
                    optionVos.clear();
                    for (Map.Entry<String, QuestionProblemOptionsPojo> entry : optionM.entrySet()) {
                        optionVos.add(entry.getValue());
                    }
                }
                Collections.sort(optionVos, new Comparator<QuestionProblemOptionsPojo>(){

                    @Override
                    public int compare(QuestionProblemOptionsPojo o1, QuestionProblemOptionsPojo o2) {
                        int i = o1.getOptionOrder() - o2.getOptionOrder();
                        if (i == 0) {
                            return o1.getOptionOrder() - o2.getOptionOrder();
                        }
                        return i;
                    }
                });
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

    /**
     * 
     * 得到option的map集合
     * 
     * @author gss
     * @return
     */
    private Map<String, QuestionProblemOptionsPojo> getOptionMap(List<QuestionProblemOptionsPojo> optionVos) {
        Map<String, QuestionProblemOptionsPojo> optionM = new HashMap<String, QuestionProblemOptionsPojo>();
        if (CollectionUtils.isEmpty(optionVos)) {
            return null;
        }
        for (QuestionProblemOptionsPojo option : optionVos) {
            if (option == null) {
                continue;
            }
            optionM.put(option.getProblemOptionId(), option);
        }
        return optionM;
    }

    /**
     * 更新营养病例的内容
     * 
     * @author xdc
     * @param diagnosisId
     */
    private void updateUserPlan(String diagnosisId) {
        planService.saveDiagnosisPoints(diagnosisId);
        planService.saveDiagnosisCourse(diagnosisId);
    }
}
