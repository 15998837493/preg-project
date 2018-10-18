
package com.mnt.preg.platform.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.JacksonUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.entity.QuesAllocation;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.examitem.service.QuestionAnswerService;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceCondition;
import com.mnt.preg.master.condition.items.InterveneDiseaseCondition;
import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.pojo.items.DiseaseItemDeduceGroupPojo;
import com.mnt.preg.master.pojo.items.DiseaseItemDeducePojo;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InspectItemPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseaseGroupPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.master.service.items.DiseaseItemDeduceService;
import com.mnt.preg.master.service.items.HospitalInspectPayService;
import com.mnt.preg.master.service.items.InspectItemService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisHospitalInspectReportCondition;
import com.mnt.preg.platform.condition.DiagnosisHospitalItemCondition;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.condition.DiseaseOftenCondition;
import com.mnt.preg.platform.entity.DiagnosisHospitalInspectPay;
import com.mnt.preg.platform.entity.DiagnosisHospitalInspectReport;
import com.mnt.preg.platform.entity.DiagnosisHospitalItem;
import com.mnt.preg.platform.entity.DiseaseOften;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.entity.PregDiagnosisItems;
import com.mnt.preg.platform.mapping.PlatFormPageName;
import com.mnt.preg.platform.mapping.PlatformMapping;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregPlanJiezhenPojo;
import com.mnt.preg.platform.pojo.UserInspectItemPojo;
import com.mnt.preg.platform.service.DiagnosisHospitalItemService;
import com.mnt.preg.platform.service.DiseaseOftenService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.platform.service.UserInspectItemService;
import com.mnt.preg.system.pojo.UserPojo;
import com.mnt.preg.system.service.UserAssistantService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.utils.WsPool;

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
public class DiagnosisReceiveController extends BaseController {

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
    private DiagnosisHospitalItemService diagnosisHospitalItemService;

    @Resource
    private PregPlanService pregPlanService;

    @Resource
    private ItemService itemService;

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

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private UserAssistantService userAssistantService;

    @Resource
    private PregPlanService planService;

    // ***************************************【问诊页面引导】**********************************************
    /**
     * 获取问诊主页面
     *
     * @author zcq
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = PlatformMapping.PLATFORM_RECEIVE_MAIN)
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
        List<PregDiagnosisDisease> diagnosisDiseaseNow = diagnosisDiseaseService
                .queryDiagnosisDiseaseByDiagnosisId(diagnosisId);
        model.addAttribute("diagnosisDiseaseNow", NetJsonUtils.listToJsonArray(diagnosisDiseaseNow));
        PregDiagnosisPojo diagnosisNow = jiezhenVo.getDiagnosis();
        model.addAttribute("diagnosis", diagnosisNow);
        model.addAttribute("diagnosisJson", NetJsonUtils.objectToJson(diagnosisNow));
        // 所有的诊断项目集合
        List<InterveneDiseaseGroupPojo> diseases = this.getInitInterveneDisease(null);
        model.addAttribute("diseaseList", diseases);
        // 接诊主页面--系统营养评价信息（张传强）
        DiagnosisItemsCondition diCondition = new DiagnosisItemsCondition();
        diCondition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisItemsVo> diagnosisItemList = diagnosisService.queryDiagnosisItem(diCondition);
        model.addAttribute("diagnosisItemList", NetJsonUtils.listToJsonArray(diagnosisItemList));
        List<InspectItemPojo> inspectAllList = inspectItemService.queryInspectItem(null);
        model.addAttribute("inspectAllListJson", NetJsonUtils.listToJsonArray(inspectAllList));
        List<UserInspectItemPojo> userInspectList = userInspectItemService.queryUserInspectByType("vist");
        model.addAttribute("userInspectListJson", NetJsonUtils.listToJsonArray(userInspectList));

        // 接诊主页面--指标录入信息
        getCheckItemsView(diagnosisId, custId, model);

        return PlatFormPageName.PLATFORM_RECEIVE_MAIN;
    }

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
     * 初始化诊断所有
     *
     * @author zcq
     * @param condition
     * @return
     */
    private List<InterveneDiseaseGroupPojo> getInitInterveneDisease(InterveneDiseaseCondition condition) {
        List<InterveneDiseasePojo> interveneDiseaseList = interveneDiseaseService.queryInterveneDisease(condition);
        List<InterveneDiseaseGroupPojo> resultList = new ArrayList<InterveneDiseaseGroupPojo>();
        Map<String, List<InterveneDiseasePojo>> diseaseMap = new TreeMap<String, List<InterveneDiseasePojo>>();
        if (CollectionUtils.isNotEmpty(interveneDiseaseList)) {
            for (InterveneDiseasePojo planDisease : interveneDiseaseList) {
                String type = planDisease.getDiseaseType();
                if (!diseaseMap.containsKey(type)) {
                    diseaseMap.put(type, new ArrayList<InterveneDiseasePojo>());
                }
                diseaseMap.get(type).add(planDisease);
            }
            for (String key : diseaseMap.keySet()) {
                InterveneDiseaseGroupPojo diseaseGroup = new InterveneDiseaseGroupPojo();
                diseaseGroup.setType(key.toString());
                diseaseGroup.setInterveneDiseaseList(diseaseMap.get(key));
                resultList.add(diseaseGroup);
            }
        }
        return resultList;
    }

    /**
     * 获取接诊评价项目
     *
     * @author zcq
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_INSPECT_QUERY)
    public @ResponseBody
    WebResult<List<PregDiagnosisItemsVo>> queryDiagnosisInspects(String diagnosisId) {
        WebResult<List<PregDiagnosisItemsVo>> webResult = new WebResult<List<PregDiagnosisItemsVo>>();
        DiagnosisItemsCondition diCondition = new DiagnosisItemsCondition();
        diCondition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisItemsVo> diagnosisItemList = diagnosisService.queryDiagnosisItem(diCondition);
        webResult.setSuc(diagnosisItemList);
        return webResult;
    }

    // ***************************************【问诊页面引导】**********************************************
    /**
     * 曲线图获取数据（体重，收缩压，舒张压）
     *
     * @author dhs
     * @param diagnosisId
     *            sign：不为空说明是体重在调用，为空则是收缩压和舒张压在调用
     *            textVal：现体重
     *            custWeight：孕前体重
     * @return WebResult<List<PregDiagnosisPojo>>
     */
    @RequestMapping(value = PlatformMapping.ECHARTS_PICTURE_GET_DATA)
    public @ResponseBody
    WebResult<List<PregDiagnosisPojo>> getDataWeightPicture(String diagnosisId, String sign, String textVal,
            String custWeight) {
        WebResult<List<PregDiagnosisPojo>> webResult = new WebResult<List<PregDiagnosisPojo>>();
        if (sign == null) {
            // sign为空，说明此时调用的是收缩压或者舒张压，按日期正序
            List<PregDiagnosisPojo> list = this.getDiagnosisesById(diagnosisId, 2);
            List<PregDiagnosisPojo> arrayList = new ArrayList<PregDiagnosisPojo>();
            for (int x = list.size() - 1; x >= 0; x--) {
                arrayList.add(list.get(x));
            }
            if (arrayList.size() > 0) {
                for (PregDiagnosisPojo diagnosisPojo : arrayList) {
                    PregDiagnosisObstetricalPojo obsPojo = diagnosisService.getObstetricalByDiagnosisId(diagnosisPojo
                            .getDiagnosisId());
                    if (obsPojo != null) {
                        diagnosisPojo.setDiagnosisDiastolic(obsPojo.getObstetricalDiagnosisDiastolic());
                        diagnosisPojo.setDiagnosisSystolic(obsPojo.getObstetricalDiagnosisSystolic());
                    }
                }
            }
            webResult.setSuc(arrayList);
        } else {
            // sign不为空，说明此时调用的是体重
            // 根据diagnosisId查询该用户的基本信息(获取allowId)
            PregDiagnosisPojo diagnosiss = diagnosisService.getDiagnosis(diagnosisId);// 登记信息
            QuesAllocation quesAllow = new QuesAllocation();// 分配表
            quesAllow.setUserId(this.getLoginUser().getUserPojo().getUserId());// 用户id
            quesAllow.setCustId(diagnosiss.getDiagnosisCustomer());// 患者id
            quesAllow.setQuestionId("C000000CA00000000006");
            String allowId = questionService.getOnceAllocationID(diagnosiss.getDiagnosisCustomer(),
                    diagnosiss.getDiagnosisDate());
            if (StringUtils.isEmpty(allowId)) {
                allowId = questionService.addAllocation(quesAllow);// 分配号
            }
            // 将用户填入的体重，存入user_diagnosis之中
            if (textVal.trim().isEmpty()) {
                diagnosisService.updateDiagnosisWeight(diagnosisId);
            } else {
                PregDiagnosis pre = new PregDiagnosis();
                BigDecimal bd = new BigDecimal(textVal);
                pre.setDiagnosisCustWeight(bd);
                pre.setDiagnosisId(diagnosisId);
                diagnosisService.updateDiagnosis(pre);
            }
            String add_weight = "";
            String add_week = "";
            // 查询接诊表(里面的体重可能为空)
            List<PregDiagnosisPojo> listData = this.getDiagnosisesById(diagnosisId, 2);
            // 过滤之后的接诊表数据（里面体重必须有值）
            List<PregDiagnosisPojo> list = new ArrayList<PregDiagnosisPojo>();
            // 如果其中weight为空，那么曲线图就不显示这个点
            if (listData.size() > 0) {
                for (PregDiagnosisPojo pre : listData) {
                    if (pre.getDiagnosisCustWeight() != null) {
                        list.add(pre);
                    }
                }
            }
            // 查询问题表
            List<QuestionAnswerPojo> list_question = questionAnswerService.queryQuestionByProblemId(
                    "402880b35ec68aac015ec7b3dea9013a", allowId);
            if (list_question.size() > 0) {// 其中：一条孕周，一条体重
                for (QuestionAnswerPojo question : list_question) {
                    if ("11000022017092800034".equals(question.getProblemOptionId())) {// 体重
                        add_weight = question.getAnswerContent();
                    }
                    if ("11000022017092800035".equals(question.getProblemOptionId())) {// 孕周
                        add_week = question.getAnswerContent() + "+2";// 因为只给周数，为了符合格式方便下面截取，所以随便给个+2
                    }
                }
                if (add_weight != null && !add_weight.trim().isEmpty() && add_week != null
                        && !add_week.trim().isEmpty()) {
                    PregDiagnosisPojo pred = new PregDiagnosisPojo();
                    pred.setDiagnosisGestationalWeeks(add_week);// 孕周
                    pred.setDiagnosisCustWeight(new BigDecimal(add_weight));
                    list.add(pred);
                }
            }
            // 0点连线，显示孕前体重
            PregDiagnosisPojo pred = new PregDiagnosisPojo();
            pred.setDiagnosisGestationalWeeks("0+2");// 孕周（主要是0，+2是随便给的）
            pred.setDiagnosisCustWeight(new BigDecimal(custWeight));
            list.add(pred);
            // 按孕周正序 diagnosisGestationalWeeks "17+2"
            for (int x = 0; x < list.size() - 1; x++) {
                for (int y = 0; y < list.size() - 1 - x; y++) {
                    if (Integer.parseInt(list.get(y).getDiagnosisGestationalWeeks().split("\\+")[0]) > Integer
                            .parseInt(list.get(y + 1).getDiagnosisGestationalWeeks().split("\\+")[0])) {
                        PregDiagnosisPojo temp = list.get(y);
                        list.set(y, list.get(y + 1));
                        list.set(y + 1, temp);
                    }
                }
            }
            webResult.setSuc(list);
        }
        return webResult;
    }

    /**
     * 保存体重曲线图下面的结论
     *
     * @author dhs
     * @param
     * @return WebResult<Boolean>
     */
    @RequestMapping(value = PlatformMapping.SAVE_ECHARTS_WEIGHT_RESULT)
    public @ResponseBody
    WebResult<Boolean> saveEchartsWeightResult(String diagnosisId, String oneText, String twoText,
            String type) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // type为结论类型
        // rise_yunqi： 建议整体孕期体重适宜增长范围
        // rise_present：建议目前体重增长至
        // rise_week：建议每周体重适宜增长范围
        oneText = oneText.trim();
        twoText = twoText.trim();
        PregDiagnosis diagnosis = new PregDiagnosis();
        diagnosis.setDiagnosisId(diagnosisId);
        if ("rise_yunqi".equals(type)) {
            if (oneText.isEmpty() && twoText.isEmpty()) {
                diagnosis.setDiagnosisRiseYunqi("");
            } else {
                diagnosis.setDiagnosisRiseYunqi(oneText + "-" + twoText);// "-"不显示在页面，用于分割
            }
        } else if ("rise_present".equals(type)) {
            if (oneText.isEmpty() && twoText.isEmpty()) {
                diagnosis.setDiagnosisRisePresent("");
            } else {
                diagnosis.setDiagnosisRisePresent(oneText + "-" + twoText);// "-"不显示在页面，用于分割
            }
        } else if ("rise_week".equals(type)) {
            if (oneText.isEmpty() && twoText.isEmpty()) {
                diagnosis.setDiagnosisRiseWeek("");
            } else {
                diagnosis.setDiagnosisRiseWeek(oneText + "-" + twoText);// "-"不显示在页面，用于分割
            }
        }
        diagnosisService.updateDiagnosis(diagnosis);// 保存
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 查询体重曲线图下面的结论
     *
     * @author dhs
     * @param
     * @return WebResult<Boolean>
     */
    @RequestMapping(value = PlatformMapping.QUERY_ECHARTS_WEIGHT_RESULT)
    public @ResponseBody
    WebResult<PregDiagnosisPojo> queryEchartsWeightResult(String diagnosisId) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        webResult.setSuc(diagnosisService.getDiagnosis(diagnosisId));
        return webResult;
    }

    // ***************************************【检查项目信息】************************************************

    /**
     * 重新评估检测项目
     *
     * @author zcq
     * @param inspectId
     * @return
     */
    @RequestMapping(value = PlatformMapping.USER_INPSECT_RESET)
    public @ResponseBody
    WebResult<List<PregDiagnosisItemsVo>> resetDiagnosisItem(String id) {
        WebResult<List<PregDiagnosisItemsVo>> webResult = new WebResult<List<PregDiagnosisItemsVo>>();
        webResult.setSuc(diagnosisService.resetDiagnosisItem(id));
        return webResult;
    }

    /**
     * JSP页面--yizhu.jsp 检测项目完成后 更新 检测状态 以及 分析结果
     *
     * @author zcq
     * @param diagnosisItemBo
     * @return
     */
    @RequestMapping(value = PlatformMapping.PLAN_INSPECTSTATUS_UPDATE)
    public @ResponseBody
    WebResult<PregDiagnosisItemsVo> updateInspectStatus(PregDiagnosisItems diagnosisItem) {
        WebResult<PregDiagnosisItemsVo> webResult = new WebResult<PregDiagnosisItemsVo>();
        // 修改状态
        Integer inspectStatus = diagnosisItem.getInspectStatus();
        if (inspectStatus != null && (inspectStatus == 3 || inspectStatus == 4)) {
            diagnosisItem.setInspectUser(this.getLoginUserId());
            diagnosisItem.setInspectUserName(this.getLoginUser().getUserPojo().getUserName());
            diagnosisItem.setInspectDatetime(new Date());
        }
        diagnosisService.updateDiagnosisItem(diagnosisItem);
        // 刷新列表
        webResult.setSuc(diagnosisService.getDiagnosisItem(diagnosisItem.getId()));
        return webResult;
    }

    /**
     * 删除接诊评价项目
     *
     * @author zcq
     * @param inspectCodeList
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_INSPECT_DELETE)
    public @ResponseBody
    WebResult<Boolean> deleteDiagnosisInspect(String inspectCodeListStr, String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isNotBlank(inspectCodeListStr)) {
            List<String> inspectCodeList = Arrays.asList(inspectCodeListStr.split(","));
            // 删除营养评价项目结论
            if (CollectionUtils.isNotEmpty(inspectCodeList)) {
                for (String inspectCode : inspectCodeList) {
                    PregDiagnosisItemsVo item = diagnosisService.getDiagnosisItemsByDiagnosisIdAndInspectItem(
                            diagnosisId,
                            inspectCode);
                    // 对剂量评估的特殊处理
                    if ("B00004".equals(item.getInspectCode())) {
                        // 删除剂量评估数据
                        diagnosisService.deleteDiagnosisExtender(diagnosisId);
                    }

                    if (StringUtils.isNotBlank(item.getResultCode())) {
                        examResultRecordUtil.deleteExamResultRecordByExamCodeAndExamCategory(item.getResultCode(),
                                inspectCode);
                    }
                }
            }
            // 删除接诊营养评价项目
            diagnosisService.deleteDiagnosisInspect(inspectCodeList, diagnosisId);
            // 刷新结论
            diagnosisService.saveDiagnosisItemResult(diagnosisId);
        }
        return webResult.setSuc(true);
    }

    /**
     * 发送接诊评价项目
     *
     * @author zcq
     * @param inspectListJson
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_INSPECT_SEND)
    public @ResponseBody
    WebResult<Boolean> sendDiagnosisInspect(String info, String infoType, String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<UserPojo> userPojoList = userAssistantService.queryDoctorOrAssistant(this.getLoginUserId());
        if (CollectionUtils.isEmpty(userPojoList)) {
            return webResult.setError("请先配置医生助理！");
        }
        String userType = this.getLoginUser().getUserPojo().getUserType();
        String sendCode = "";
        if ("assistant".equals(userType)) {
            if (StringUtils.isNotBlank(diagnosisId)) {
                PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);
                sendCode = diagnosis.getDiagnosisUser() + diagnosisId;
            }
        } else {
            sendCode = userPojoList.get(0).getUserId() + diagnosisId;
        }
        if ("inspect".equals(infoType)) {
            if (StringUtils.isNotBlank(info)) {
                List<PregDiagnosisItems> inspectList = NetJsonUtils.jsonArrayToList(info, PregDiagnosisItems.class);
                for (PregDiagnosisItems inspect : inspectList) {
                    diagnosisService.updateDiagnosisItem(inspect);
                }
                WsPool.sendMessageToUser(sendCode, WsPool.getMessageJson("营养评价项目有更新！", infoType));
                updateDiagnosisStatusWs(sendCode, diagnosisId);
            } else {
                return webResult.setError("参数不能为空！");
            }
        } else if ("diagnosisInfo".equals(infoType)) {
            WsPool.sendMessageToUser(sendCode, WsPool.getMessageJson(info, infoType));
            updateDiagnosisStatusWs(sendCode, diagnosisId);
        } else if ("refreshDiagnosisInspect".equals(infoType)) {
            WsPool.sendMessageToUser(sendCode, WsPool.getMessageJson(info, infoType));
            updateDiagnosisStatusWs(sendCode, diagnosisId);
        }

        return webResult.setSuc(true);
    }

    /**
     * 发送评价项目更改状态
     *
     * @author zcq
     * @param inspectCodeList
     * @param inspectStatus
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSISITEM_INSPECTSTATUS_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateDiagnosisItemInspectStatus(List<String> inspectCodeList, String inspectStatus,
            String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 修改状态
        diagnosisService.updateDiagnosisItemInspectStatus(inspectCodeList, inspectStatus, diagnosisId);
        return webResult.setSuc(true);
    }

    /**
     * 获取评价项目结论
     *
     * @author zcq
     * @param diagnosisId
     * @param custId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_INSPECT_RESULT)
    public @ResponseBody
    WebResult<Map<String, Object>> getDiagnosisSummary(String diagnosisId, String custId) {
        WebResult<Map<String, Object>> webResult = new WebResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 孕期建档结论
        PregArchivesPojo pregArchive = pregArchivesService.getLastPregnancyArchive(custId);
        if (pregArchive != null && StringUtils.isNotEmpty(pregArchive.getPregnancyResult())) {
            map.put("pregArchive", pregArchive.getPregnancyResult());
        }
        // 检查项目结论
        Map<String, ExamResultRecordPojo> resultMap = diagnosisService.getDiseaseInspectResult(diagnosisId);
        map.put("checkItem", resultMap);

        return webResult.setSuc(map);
    }

    /**
     * 添加接诊评价项目
     *
     * @author zcq
     * @param diagnosisItemJson
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_INSPECT_ADD)
    public @ResponseBody
    WebResult<String> addDiagnosisInspectItem(String diagnosisItemJson) {
        WebResult<String> webResult = new WebResult<String>();
        if (StringUtils.isNotBlank(diagnosisItemJson)) {
            PregDiagnosisItems diagnosisItem = (PregDiagnosisItems) NetJsonUtils.jsonToObject(diagnosisItemJson,
                    PregDiagnosisItems.class);
            webResult.setSuc(diagnosisService.addDiagnosisItem(diagnosisItem));
        }
        return webResult;
    }

    // ***************************************【实验室指标信息】**********************************************
    /**
     * 异步查询报告单信息
     *
     * @author mlq
     * @param custId
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_REPORTS_QUERY)
    public @ResponseBody
    WebResult<List<DiagnosisHospitalInspectReportPojo>> queryReports(String custId, String diagnosisId) {
        WebResult<List<DiagnosisHospitalInspectReportPojo>> webResult = new WebResult<List<DiagnosisHospitalInspectReportPojo>>();
        if (diagnosisId != null) {
            webResult.setSuc(diagnosisHospitalItemService.queryDiagnosisReports(diagnosisId));
        }
        return webResult;
    }

    /**
     * 添加报告单
     *
     * @author mlq
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_REPORT_ADD)
    public @ResponseBody
    WebResult<DiagnosisHospitalInspectReportPojo> addReport(String diagnosisId, String reportName,
            String custId,
            String inspects, String items) {
        WebResult<DiagnosisHospitalInspectReportPojo> webResult = new WebResult<DiagnosisHospitalInspectReportPojo>();
        DiagnosisHospitalInspectReport report = new DiagnosisHospitalInspectReport();
        report.setCustId(custId);
        report.setDiagnosisId(diagnosisId);
        report.setFlag(Flag.normal.getValue());
        // 报告单状态
        int status = 1;
        String userType = this.getLoginUser().getUserPojo().getUserType();
        if ("assistant".equals(userType)) {
            status = 2;
        }
        report.setReportName(reportName);
        report.setReportStatus(status);
        DiagnosisHospitalInspectReportPojo reportPojo = diagnosisHospitalItemService.addReport(report);

        // 添加收费信息
        if (!StringUtils.isBlank(inspects)) {
            List<DiagnosisHospitalInspectPay> inspectList = new ArrayList<DiagnosisHospitalInspectPay>();
            String[] jsonMore = inspects.split("###");
            for (String jsonStr : jsonMore) {
                JSONObject inspectJson = JSONObject.fromObject(jsonStr);
                DiagnosisHospitalInspectPay inspect = (DiagnosisHospitalInspectPay) JSONObject.toBean(inspectJson,
                        DiagnosisHospitalInspectPay.class);
                inspectList.add(inspect);
                diagnosisHospitalItemService.addPregDiagnosisInspectPays(inspectList, reportPojo.getReportId());
            }
        }

        // 添加检验项目
        if (!StringUtils.isBlank(items)) {
            List<DiagnosisHospitalItem> itemList = new ArrayList<DiagnosisHospitalItem>();
            String[] jsonMore = items.split("###");
            for (String jsonStr : jsonMore) {
                JSONObject itemJson = JSONObject.fromObject(jsonStr);
                DiagnosisHospitalItem item = (DiagnosisHospitalItem) JSONObject
                        .toBean(itemJson, DiagnosisHospitalItem.class);
                item.setDiagnosisId(diagnosisId);// 添加接诊信息
                itemList.add(item);
            }
            diagnosisHospitalItemService.addPregDiagnosisItems(itemList, reportPojo.getReportId());
        }

        // 重新查询报告单收费+检验项目信息
        DiagnosisHospitalInspectReportPojo reportPojoNew = diagnosisHospitalItemService
                .queryDiagnosisReportById(reportPojo.getReportId());
        reportPojo.setInspectPayList(reportPojoNew.getInspectPayList());
        reportPojo.setItemList(reportPojoNew.getItemList());
        // 查询报告单信息
        webResult.setSuc(reportPojo);
        return webResult;
    }

    /**
     * 修改报告单
     *
     * @author mlq
     * @param report
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_REPORT_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateReport(String report) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        DiagnosisHospitalInspectReport reportNew = (DiagnosisHospitalInspectReport) JacksonUtils.json2pojo(report,
                DiagnosisHospitalInspectReport.class);
        diagnosisHospitalItemService.updateDiagnosisHospitalItemReport(reportNew);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 添加检验项目
     *
     * @author mlq
     * @param diagnosisId
     * @param reportId
     * @param inspect
     * @param item
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ITEM_ADD)
    public @ResponseBody
    WebResult<List<DiagnosisHospitalItemPojo>> addItems(String diagnosisId, String reportId,
            String inspect, String items) {
        WebResult<List<DiagnosisHospitalItemPojo>> webResult = new WebResult<List<DiagnosisHospitalItemPojo>>();
        // 添加收费信息
        if (!StringUtils.isBlank(inspect)) {
            JSONObject inspectJson = JSONObject.fromObject(inspect);
            List<DiagnosisHospitalInspectPay> payList = new ArrayList<DiagnosisHospitalInspectPay>();
            payList.add((DiagnosisHospitalInspectPay) JSONObject.toBean(inspectJson, DiagnosisHospitalInspectPay.class));
            diagnosisHospitalItemService.addPregDiagnosisInspectPays(payList, reportId);
        }

        // 添加检验项目
        List<DiagnosisHospitalItem> itemList = new ArrayList<DiagnosisHospitalItem>();
        String[] jsonMore = items.split("###");
        for (String jsonStr : jsonMore) {
            JSONObject itemJson = JSONObject.fromObject(jsonStr);
            DiagnosisHospitalItem item = (DiagnosisHospitalItem) JSONObject
                    .toBean(itemJson, DiagnosisHospitalItem.class);
            item.setDiagnosisId(diagnosisId);// 添加接诊信息
            itemList.add(item);
        }
        diagnosisHospitalItemService.addPregDiagnosisItems(itemList, reportId);
        // 查询报告单下所有检验项目
        webResult.setSuc(diagnosisHospitalItemService.queryDiagnosisItems(reportId));
        return webResult;
    }

    /**
     * 检验项目更新（单条更新）
     *
     * @author mlq
     * @param reportId
     * @param item
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ITEM_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateItem(String reportId, String item) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        JSONObject itemJson = JSONObject.fromObject(item);
        DiagnosisHospitalItem itemEntity = (DiagnosisHospitalItem) JSONObject
                .toBean(itemJson, DiagnosisHospitalItem.class);
        itemEntity.setFlag(Flag.normal.getValue());
        diagnosisHospitalItemService.updateDiagnosisHospitalItem(itemEntity);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 检验项目更新（发送检验报告）
     *
     * @author mlq
     * @param custId
     * @param ids
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ITEMS_SEND)
    public @ResponseBody
    WebResult<Boolean> sendItems(String custId, String diagnosisId, String ids) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();

        // 删除未勾选的检验项目(只删除助理端权限的数据 status=2)
        if (!StringUtils.isBlank(custId) && !StringUtils.isBlank(diagnosisId) && !StringUtils.isBlank(ids)) {
            diagnosisHospitalItemService.deleteDiagnosisHospitalItems(custId, diagnosisId, ids);
        }

        // 删除空的收费项目
        if (!StringUtils.isBlank(custId) && !StringUtils.isBlank(diagnosisId)) {
            diagnosisHospitalItemService.deleteDiagnosisHospitalEmptyPay(custId, diagnosisId);
        }

        // 删除空的报告单
        if (!StringUtils.isBlank(diagnosisId)) {
            diagnosisHospitalItemService.deleteDiagnosisHospitalEmptyReport(diagnosisId);
        }

        // 更改报告单状态
        int status = 2;
        String userType = this.getLoginUser().getUserPojo().getUserType();
        if ("assistant".equals(userType)) {
            status = 1;
        }
        diagnosisHospitalItemService.updateDiagnosisHospitalItemReportByDiagnosisId(diagnosisId, status);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 删除检验项目信息
     *
     * @author mlq
     * @param reportId
     * @param inspectId
     * @param itemId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ITEM_DELETE)
    public @ResponseBody
    WebResult<List<DiagnosisHospitalItemPojo>> deleteQuotaItems(String reportId, String inspectId,
            String itemId) {
        WebResult<List<DiagnosisHospitalItemPojo>> webResult = new WebResult<List<DiagnosisHospitalItemPojo>>();
        if (!StringUtils.isBlank(reportId)) {
            // 删除报告单信息
            if (StringUtils.isBlank(inspectId) && StringUtils.isBlank(itemId)) {
                diagnosisHospitalItemService.deleteDiagnosisHospitalInspectReport(reportId);
                diagnosisHospitalItemService.deleteDiagnosisHospitalInspectPay(reportId, inspectId);
                diagnosisHospitalItemService.deleteDiagnosisHospitalItem(reportId, inspectId, itemId);
            }

            // 删除收费项目信息
            if (!StringUtils.isBlank(inspectId) && StringUtils.isBlank(itemId)) {
                diagnosisHospitalItemService.deleteDiagnosisHospitalInspectPay(reportId, inspectId);
                diagnosisHospitalItemService.deleteDiagnosisHospitalItem(reportId, inspectId, itemId);
            }

            // 删除检验项目信息
            if (StringUtils.isBlank(inspectId) && !StringUtils.isBlank(itemId)) {
                diagnosisHospitalItemService.deleteDiagnosisHospitalItem(reportId, inspectId, itemId);
            }
        }

        // 查询报告单下所有检验项目
        webResult.setSuc(diagnosisHospitalItemService.queryDiagnosisItems(reportId));
        return webResult;
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
        List<DiagnosisHospitalInspectReportPojo> reportList = diagnosisHospitalItemService
                .queryDiagnosisReports(diagnosisId);
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

    /**
     * 根据接诊id获取上一次的检查指标
     *
     * @author xdc
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_LAST_ITEM_LIST)
    public @ResponseBody
    WebResult<Boolean> getLastItemList(String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisId);
        PregDiagnosisPojo lastPregDiagnosis = diagnosisService.getLastDiagnosis(diagnosisPojo.getDiagnosisCustomer());
        if (lastPregDiagnosis != null) {
            List<ItemPojo> itemPojoList = diagnosisService.queryQuotaItemByDiagnosisId(lastPregDiagnosis
                    .getDiagnosisId());
            if (CollectionUtils.isNotEmpty(itemPojoList)) {
                for (ItemPojo itemPojo : itemPojoList) {
                    itemPojo.setItemDatetime(lastPregDiagnosis.getDiagnosisDate());
                }
                webResult.setData(itemPojoList);
            } else {
                webResult.setError("上次指标未找到");
            }
        } else {
            webResult.setError("上次指标未找到");
        }
        return webResult;
    }

    /**
     * 检索符合条件的推断指标组合
     *
     * @author mlq
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DISEASE_ITEM_GROUP_FIT)
    public @ResponseBody
    WebResult<List<InterveneDiseasePojo>> queryFitDiseaseItemGroup(String diagnosisId) {
        WebResult<List<InterveneDiseasePojo>> webResult = new WebResult<List<InterveneDiseasePojo>>();
        List<DiagnosisHospitalItemPojo> examItemList = diagnosisHospitalItemService
                .queryDiagnosisItemsByDiagnosisId(diagnosisId);
        if (examItemList != null) {
            Iterator<DiagnosisHospitalItemPojo> it = examItemList.iterator();
            while (it.hasNext()) {
                DiagnosisHospitalItemPojo item = it.next();
                if (item == null || StringUtils.isEmpty(item.getItemCode())) {
                    it.remove();
                }
            }
        }
        // 记录最终推断出的疾病名称
        List<String> diseaseIdList = new ArrayList<String>();

        if (CollectionUtils.isNotEmpty(examItemList)) {
            // 获取检查指标列表
            Map<String, DiagnosisHospitalItemPojo> recordMap = new HashMap<String, DiagnosisHospitalItemPojo>();
            List<String> itemCodeList = new ArrayList<String>();
            for (DiagnosisHospitalItemPojo examItem : examItemList) {
                itemCodeList.add(examItem.getItemCode());
                recordMap.put(examItem.getItemCode(), examItem);
            }

            // 根据编码查询指标列表
            Map<String, DiagnosisHospitalItemPojo> examItemMap = new HashMap<String, DiagnosisHospitalItemPojo>();
            List<String> itemIdList = new ArrayList<String>();
            ItemCondition itemCondition = new ItemCondition();
            itemCondition.setItemCodeList(itemCodeList);
            List<ItemPojo> masItemList = itemService.queryItem(itemCondition);
            if (CollectionUtils.isNotEmpty(masItemList)) {
                for (ItemPojo item : masItemList) {
                    itemIdList.add(item.getItemId());
                    examItemMap.put(item.getItemId(), recordMap.get(item.getItemCode()));
                }
            }

            // 检索所有符合条件的推断指标组合
            List<DiseaseItemDeduceGroupPojo> groupList = diseaseItemDeduceService.queryFitDiseaseItemDeduceGroup(itemIdList);

            // 检索推断指标基础数据列表
            DiseaseItemDeduceCondition condition = new DiseaseItemDeduceCondition();
            condition.setItemIdList(itemIdList);
            List<DiseaseItemDeducePojo> itemList = diseaseItemDeduceService.queryDiseaseItemDeduce(condition);
            Map<String, List<DiseaseItemDeducePojo>> map = new HashMap<String, List<DiseaseItemDeducePojo>>();
            String keyStr = "";// key值
            List<DiseaseItemDeducePojo> diseaseItemList = null;// 诊断下的检验项目map（存在多个配置）
            for (DiseaseItemDeducePojo item : itemList) {
                keyStr = item.getItemId() + item.getDiseaseId();
                if (map.containsKey(keyStr)) {
                    diseaseItemList = map.get(keyStr);
                } else {
                    diseaseItemList = new ArrayList<DiseaseItemDeducePojo>();
                }
                diseaseItemList.add(item);
                map.put(keyStr, diseaseItemList);
            }

            // 遍历筛选符合条件的疾病
            boolean flag = true;
            if (!CollectionUtils.isEmpty(groupList)) {
                for (DiseaseItemDeduceGroupPojo group : groupList) {
                    OUT: for (String id : group.getItemIds().split(",")) {
                        diseaseItemList = map.get(id + group.getDiseaseId());

                        if (CollectionUtils.isEmpty(diseaseItemList)) {
                            flag = false;
                            break;
                        }
                        for (DiseaseItemDeducePojo masItemResult : diseaseItemList) {
                            String algorithm = masItemResult.getAlgorithm();// 结论类型--基础数据
                            String content = masItemResult.getContent();// 结论内容--基础数据
                            DiagnosisHospitalItemPojo examItemResult = examItemMap.get(id);
                            if (examItemResult == null) {
                                flag = false;
                                break OUT;
                            }
                            String itemString = examItemResult.getItemValue();// 结果内容--检测数据
                            // 条件：1、检查指标列表存在该指标，2、结论类型不为空，3、结论内容不为空，4、检测结论内容不为空，5、结论内容为异常
                            if (StringUtils.isEmpty(itemString) || StringUtils.isEmpty(algorithm)
                                    || StringUtils.isEmpty(content) || !itemIdList.contains(id)) {
                                flag = false;
                                break OUT;
                            } else if (("GE".equals(algorithm) && (!isNumeric(itemString) || // 超标
                                    (isNumeric(itemString) && Double.valueOf(itemString) < Double.valueOf(content))))
                                    || ("LT".equals(algorithm) && (!isNumeric(itemString) || // 低标
                                    (isNumeric(itemString) && Double.valueOf(itemString) > Double.valueOf(content))))
                                    || ("RANGE".equals(algorithm) && ( // 范围
                                    !isNumeric(itemString) || (isNumeric(itemString)
                                            && Double.valueOf(itemString) < Double.valueOf(content.split("#")[0])
                                            && Double.valueOf(itemString) > Double.valueOf(content.split("#")[1]))))
                                    || ("LIKE".equals(algorithm) && !content.equals(itemString))) {// 异常结果
                                flag = false;
                                break OUT;
                            }
                        }
                    }
                    if (flag && !diseaseIdList.contains(group.getDiseaseId())) {
                        diseaseIdList.add(group.getDiseaseId());
                    }
                    flag = true;
                }
            }
        }

        // 检索推断疾病信息
        List<InterveneDiseasePojo> diseaseList = null;
        if (CollectionUtils.isNotEmpty(diseaseIdList)) {
            InterveneDiseaseCondition diseaseCondition = new InterveneDiseaseCondition();
            diseaseCondition.setDiseaseIdList(diseaseIdList);
            diseaseList = interveneDiseaseService.queryInterveneDisease(diseaseCondition);
        }

        return webResult.setSuc(diseaseList);
    }

    /**
     * 判断内容是否是数字
     *
     * @author xdc
     * @param str
     * @return
     */
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[1-9]\\d*\\.?\\d*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 发送实验室指标
     *
     * @author zcq
     * @param inspectCodeListStr
     * @param inspectStatus
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ITEM_SEND)
    public @ResponseBody
    WebResult<Boolean> sendDiagnosisClinicalItem(String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<UserPojo> userPojoList = userAssistantService.queryDoctorOrAssistant(this.getLoginUserId());
        if (CollectionUtils.isNotEmpty(userPojoList)) {
            String userType = this.getLoginUser().getUserPojo().getUserType();
            String sendCode = "";
            if ("assistant".equals(userType)) {
                if (StringUtils.isNotBlank(diagnosisId)) {
                    PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);
                    sendCode = diagnosis.getDiagnosisUser() + diagnosisId;
                }
            } else {
                sendCode = userPojoList.get(0).getUserId() + diagnosisId;
            }
            WsPool.sendMessageToUser(sendCode, WsPool.getMessageJson("实验室指标有更新！", "diagnosisQuota"));
            updateDiagnosisStatusWs(sendCode, diagnosisId);
        } else {
            return webResult.setError("请先配置医生助理！");
        }
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 条件检索历史检验项目
     *
     * @author zcq
     * @param disease
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_HOSPITAL_ITEM_QUERY)
    public @ResponseBody
    WebResult<List<DiagnosisHospitalItemPojo>> queryDiagnosisHospitalItemByCondition(
            DiagnosisHospitalItemCondition condition) {
        WebResult<List<DiagnosisHospitalItemPojo>> webResult = new WebResult<List<DiagnosisHospitalItemPojo>>();
        return webResult.setSuc(diagnosisHospitalItemService.queryDiagnosisHospitalItemByCondition(condition));
    }

    /**
     * 条件检索历史检验报告
     *
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_HOSPITAL_REPORT_QUERY)
    public @ResponseBody
    WebResult<List<DiagnosisHospitalInspectReportPojo>> queryDiagnosisHospitalInspectReportByCondition(
            DiagnosisHospitalInspectReportCondition condition) {
        WebResult<List<DiagnosisHospitalInspectReportPojo>> webResult = new WebResult<List<DiagnosisHospitalInspectReportPojo>>();
        return webResult.setSuc(diagnosisHospitalItemService.queryDiagnosisHospitalInspectReportByCondition(condition));
    }

    // ***************************************【诊断项目信息】************************************************

    /**
     * 添加诊断项目
     *
     * @author xdc
     * @param pojo
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_DISEASE_ADD)
    public @ResponseBody
    WebResult<Boolean> addDiagnosisDisease(String disease) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregDiagnosisDisease diseaseObj = (PregDiagnosisDisease) NetJsonUtils.jsonToObject(disease,
                PregDiagnosisDisease.class);
        if (diagnosisDiseaseService.addDiagnosisDisease(diseaseObj)) {
            webResult.setSuc(true);
            updateUserPlan(diseaseObj.getDiagnosisId());
        } else {
            webResult.setSuc(false);
        }
        return webResult;
    }

    /**
     * 删除诊断项目
     *
     * @author xdc
     * @param pojo
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_DISEASE_DELETE)
    public @ResponseBody
    WebResult<Boolean> deleteDiagnosisDisease(String diagnosisId, String diseaseName) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        diagnosisDiseaseService.deleteDiagnosisDisease(diagnosisId, diseaseName);
        webResult.setSuc(true);
        updateUserPlan(diagnosisId);
        return webResult;
    }

    /**
     * 更新诊断项目
     *
     * @author xdc
     * @param pojo
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_DISEASE_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateDiagnosisDisease(String diagnosisId, String diseaseName) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        diagnosisDiseaseService.updateDiagnosisDisease(diagnosisId, diseaseName);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 通过历史记录添加诊断项目
     *
     * @author xdc
     * @param pojo
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_DISEASE_BATCH_SAVE)
    public @ResponseBody
    WebResult<Integer> getDiagnosisDisease(String diagnosisOldId, String diagnosisId) {
        WebResult<Integer> webResult = new WebResult<Integer>();
        List<PregDiagnosisDisease> diseaseList = diagnosisDiseaseService
                .queryDiagnosisDiseaseByDiagnosisId(diagnosisOldId);
        // 返回成功添加的个数
        for (PregDiagnosisDisease disease : diseaseList) {
            disease.setDiagnosisId(diagnosisId);
        }
        webResult.setData(diseaseList);
        List<PregDiagnosisDisease> diseaseReList = diagnosisDiseaseService.saveDiagnosisDisease(diseaseList);
        webResult.setMessage("成功添加" + diseaseReList.size() + "个诊断项目，有" + (diseaseList.size() - diseaseReList.size())
                + "个已存在");
        webResult.setData(diseaseReList);
        webResult.setSuc(diseaseReList.size());
        updateUserPlan(diagnosisId);
        return webResult;
    }

    /**
     * 获取接诊数据
     *
     * @author xdc
     * @param pojo
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_DISEASE_GET)
    public @ResponseBody
    WebResult<Boolean> getDiagnosisDisease(String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<PregDiagnosisDisease> diseaseList = diagnosisDiseaseService
                .queryDiagnosisDiseaseByDiagnosisId(diagnosisId);
        webResult.setData(diseaseList);
        webResult.setSuc(true);
        updateUserPlan(diagnosisId);
        return webResult;
    }

    // ***************************************【常用诊断项目】************************************************
    /**
     * 异步获取个人常用诊断项目
     *
     * @author scd
     * @param diseaseOften
     * @return
     */
    @RequestMapping(value = PlatformMapping.DISEASE_OFTEN_QUERY)
    public @ResponseBody
    WebResult<DiseaseOften> queryDiseaseOften(DiseaseOftenCondition diseaseOften) {
        WebResult<DiseaseOften> webResult = new WebResult<DiseaseOften>();
        String userId = this.getLoginUser().getUserPojo().getUserId();
        diseaseOften.setCreateUserId(userId);// 常用诊断项目按账号区分
        webResult.setData(diseaseOftenService.queryDiseaseOften(diseaseOften));
        return webResult;
    }

    /**
     * 添加个人常用诊断项目
     *
     * @author scd
     * @param diseaseOften
     * @return
     */
    @RequestMapping(value = PlatformMapping.DISEASE_OFTEN_ADD)
    public @ResponseBody
    WebResult<DiseaseOften> addDiseaseOften(String disease) {
        WebResult<DiseaseOften> webResult = new WebResult<DiseaseOften>();
        DiseaseOften diseaseOften = (DiseaseOften) NetJsonUtils.jsonToObject(disease, DiseaseOften.class);

        UserPojo userPojo = this.getLoginUser().getUserPojo();
        String diseaseCode = diseaseOftenService.getDiseaseCode(userPojo.getCreateInsId(), userPojo.getUserId());
        diseaseOften.setDiseaseCode(diseaseCode);

        String diseaseId = diseaseOftenService.addDiseaseOften(diseaseOften);
        webResult.setSuc(diseaseOftenService.getDiseaseOften(diseaseId));
        return webResult;
    }

    /**
     * 删除个人常用诊断项目
     *
     * @author scd
     * @param diseaseOften
     * @return
     */
    @RequestMapping(value = PlatformMapping.DISEASE_OFTEN_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeDiseaseOften(String diseaseId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        webResult.setSuc(diseaseOftenService.removeDiseaseOften(diseaseId));
        return webResult;
    }

    /**
     *
     * 验证常用诊断项目名称是否重复
     *
     * @author scd
     * @param diseaseName
     * @return
     */
    @RequestMapping(value = PlatformMapping.DISEASE_OFTEN_CHECK_NAME)
    public @ResponseBody
    WebResult<Boolean> checkDiseaseOftenName(String diseaseName) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        webResult.setSuc(diseaseOftenService.checkDiseaseOftenName(diseaseName));
        return webResult;
    }

    // ***************************************【自定义工具方法】**********************************************
    /**
     * 通过diagnosisId获取同一个custId下的一组接诊信息
     *
     * @author dhs
     * @param diagnosisId
     * @return List<PregDiagnosisPojo>
     */
    private List<PregDiagnosisPojo> getDiagnosisesById(String diagnosisId, Integer status) {
        PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);
        String cusId = diagnosis.getDiagnosisCustomer();
        DiagnosisCondition diagnosisCondition = new DiagnosisCondition();
        diagnosisCondition.setDiagnosisStatus(status);
        diagnosisCondition.setDiagnosisCustomer(cusId);
        // 人体成分首次同步并且现体重为空时动态刷新曲线图
        List<PregDiagnosisPojo> list = diagnosisService.queryDiagnosis(diagnosisCondition);
        if (diagnosis.getDiagnosisStatus() == 1 && diagnosis.getDiagnosisCustWeight() != null
                && diagnosis.getDiagnosisCustWeight().compareTo(BigDecimal.ZERO) > 0) {
            list.add(diagnosis);
        }
        return list;
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

    /**
     * 推送患者在助理端进度到医生端
     *
     * @author dhs
     * @param
     */
    private void updateDiagnosisStatusWs(String sendCode, String diagnosisId) {
        // 判断指标和诊断项目是否都完成,如果都完成给状态2（完成），如果有未完成的给状态1（诊疗），没添加指标算完成（之前逻辑这么写的）
        DiagnosisCondition diagnosisCondition = new DiagnosisCondition();
        diagnosisCondition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisPojo> list = diagnosisService.queryDiagnosisMoreEvaluate(diagnosisCondition);
        if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(list.get(0).getStatusSync())) {
            String statusSync = list.get(0).getStatusSync();
            // websocket 推送用户
            String wsUser = "diagnosisView" + list.get(0).getDiagnosisUser();
            PregDiagnosis diangosisUpdate = new PregDiagnosis();
            diangosisUpdate.setDiagnosisId(diagnosisId);
            if ("1".equals(statusSync)) {// 未完成
                diangosisUpdate.setDiagnosisAssistantStatus(2);
                diagnosisService.updateDiagnosis(diangosisUpdate);
                WsPool.sendMessageToUser(wsUser, WsPool.getMessageJson("患者的完成进度有更新！", "diagnosisView"));
            } else if ("2".equals(statusSync)) {// 已完成
                diangosisUpdate.setDiagnosisAssistantStatus(3);
                diagnosisService.updateDiagnosis(diangosisUpdate);
                WsPool.sendMessageToUser(wsUser, WsPool.getMessageJson("患者的完成进度有更新！", "diagnosisView"));
            }
        }
    }
}
