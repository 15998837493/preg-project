
package com.mnt.preg.platform.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.entity.Customer;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.customer.service.PregAdjustRecordService;
import com.mnt.preg.examitem.service.PregInbodyService;
import com.mnt.preg.examitem.service.QuestionAnswerServiceImpl;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.master.pojo.items.InspectItemPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.service.items.InspectItemService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.entity.DiagnosisBooking;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.entity.PregDiagnosisItems;
import com.mnt.preg.platform.entity.PregDiagnosisObstetrical;
import com.mnt.preg.platform.form.PregDiagnosisObstetricalForm;
import com.mnt.preg.platform.form.RegistrationForm;
import com.mnt.preg.platform.mapping.PlatFormPageName;
import com.mnt.preg.platform.mapping.PlatformMapping;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisAnalysisPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.UserInspectItemPojo;
import com.mnt.preg.platform.service.DiagnosisHospitalItemService;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.platform.service.UserInspectItemService;
import com.mnt.preg.system.pojo.ReferralDoctorPojo;
import com.mnt.preg.system.service.ReferralDoctorService;
import com.mnt.preg.system.service.UserAssistantService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.DMDietPdf;
import com.mnt.preg.web.pdf.DiaryPdf;
import com.mnt.preg.web.pdf.WBSPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 接诊信息Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-13 zcq 初版
 */
@Controller
public class DiagnosisController extends BaseController {

    @Resource
    private CustomerService customerService;

    @Resource
    private InspectItemService inspectItemService;

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private PregArchivesService archivesService;

    @Resource
    private PregInbodyService inbodyService;

    @Resource
    private PregAdjustRecordService adjustRecordService;

    @Resource
    private QuestionAnswerServiceImpl questionAnswerService;

    @Resource
    private QuestionService questionService;

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private PregPlanService planService;

    @Resource
    private PregDiagnosisDiseaseService diagnosisDiseaseService;

    @Resource
    private ReferralDoctorService referralDoctorService;

    @Resource
    private UserInspectItemService userInspectItemService;

    @Resource
    private UserAssistantService userAssistantService;

    @Autowired
    private DiagnosisHospitalItemService itemService;

    /** 妊娠日记--长沙市妇幼保健院妊娠糖尿病饮食记录表 */
    private String dMDietPdfCode = "P07001";

    /** 妊娠日记--长沙市妇幼保健院血糖、体重监测表 */
    private String wBSPdfCode = "P06001";

    /** 妊娠日记--协和医院_妊娠日记_模板 */
    private String diaryPdf = "P05001";

    // ***************************************【接诊平台一览页】**********************************************

    /**
     * 诊疗平台一览页
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_PLATFORM_VIEW)
    public String toPlatFormCustomerView(Model model) {
        // 转诊医生信息
        List<ReferralDoctorPojo> referralList = referralDoctorService.queryDoctors(null);
        model.addAttribute("referralList", referralList);
        model.addAttribute("referralListJson", NetJsonUtils.listToJsonArray(referralList));
        // 评价项目信息
        List<InspectItemPojo> inspectList = inspectItemService.queryInspectItem(null);
        model.addAttribute("inspectListJson", NetJsonUtils.listToJsonArray(inspectList));
        // 疾病项目信息
        List<InterveneDiseasePojo> diseaseList = interveneDiseaseService.queryInterveneDisease(null);
        model.addAttribute("diseaseListJson", NetJsonUtils.listToJsonArray(diseaseList));
        // 个人默认评价项目信息
        List<UserInspectItemPojo> userInspectList = userInspectItemService.queryUserInspectByType("first");
        model.addAttribute("userInspectListJson", NetJsonUtils.listToJsonArray(userInspectList));
        // websocket 用户
        model.addAttribute("wsUser", "diagnosisView" + this.getLoginUserId());

        return PlatFormPageName.PLATFORM_CUSTOMER_VIEW;
    }

    /**
     * 保存个人默认评价项目
     * 
     * @author zcq
     * @param userInspectItemList
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PlatformMapping.USER_INSPECT_ITEM_SAVE)
    public @ResponseBody WebResult<List<UserInspectItemPojo>> saveUserInspectItems(String userInspectItemListJson,
            String inspectType) {
        WebResult<List<UserInspectItemPojo>> webResult = new WebResult<List<UserInspectItemPojo>>();
        if (StringUtils.isNotBlank(userInspectItemListJson)) {
            List<UserInspectItemPojo> list = NetJsonUtils.jsonArrayToList(userInspectItemListJson,
                    UserInspectItemPojo.class);
            userInspectItemService.saveUserInspectItems(list, inspectType);
        }
        List<UserInspectItemPojo> userInspectList = userInspectItemService.queryUserInspectByType(inspectType);
        return webResult.setSuc(userInspectList);
    }

    /**
     * 检索当天未接诊的会员列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_QUERY_MORE)
    public @ResponseBody WebResult<List<PregDiagnosisPojo>> queryDiagnosisMore(DiagnosisCondition condition) {
        WebResult<List<PregDiagnosisPojo>> webResult = new WebResult<List<PregDiagnosisPojo>>();
        Date today = JodaTimeTools.toDate(JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6));
        condition.setStartDate(today);
        condition.setEndDate(JodaTimeTools.after_day(today, 1));
        webResult.setData(diagnosisService.queryDiagnosisMore(condition));
        return webResult;
    }

    /**
     * 营养评价平台
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_QUERY_MORE_EVALUATE)
    public @ResponseBody WebResult<List<PregDiagnosisPojo>> queryDiagnosisMoreEvaluate(DiagnosisCondition condition) {
        WebResult<List<PregDiagnosisPojo>> webResult = new WebResult<List<PregDiagnosisPojo>>();
        Date today = JodaTimeTools.toDate(JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6));
        condition.setStartDate(today);
        condition.setEndDate(JodaTimeTools.after_day(today, 1));
        webResult.setData(diagnosisService.queryDiagnosisMoreEvaluateByOrder(condition));
        return webResult;
    }

    /**
     * 检索上一次接诊诊断信息
     * 
     * @author zcq
     * @param custId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_LAST_DISEASE)
    public @ResponseBody WebResult<List<PregDiagnosisDisease>> queryLastDiagnosisDiseaseByCustId(String custId) {
        WebResult<List<PregDiagnosisDisease>> webResult = new WebResult<List<PregDiagnosisDisease>>();
        PregDiagnosisPojo lastDiagnosis = diagnosisService.getLastDiagnosis(custId);
        List<PregDiagnosisDisease> disList = null;
        if (lastDiagnosis != null) {
            disList = diagnosisDiseaseService.queryDiagnosisDiseaseByDiagnosisId(lastDiagnosis.getDiagnosisId());
        }
        return webResult.setSuc(disList);
    }

    /**
     * 检索上一次接诊诊断信息（接诊医生信息）
     * 
     * @author mlq
     * @param custId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_LAST_INFO)
    public @ResponseBody WebResult<PregDiagnosisPojo> queryLastDiagnosisInfoByCustId(String custId) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        PregDiagnosisPojo lastDiagnosis = diagnosisService.getLastDiagnosis(custId);
        return webResult.setSuc(lastDiagnosis);
    }

    /**
     * 添加诊疗登记
     * 
     * @author zcq
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ADD)
    public @ResponseBody WebResult<PregDiagnosisPojo> addDiagnosis(String diagnosisJson) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        PregDiagnosis diagnosis = (PregDiagnosis) NetJsonUtils.jsonToObject(diagnosisJson, PregDiagnosis.class);
        String diagnosisId = diagnosisService.addDiagnosis(diagnosis);
        JSONObject jsonObj = JSONObject.fromObject(diagnosisJson);
        String custId = (String) jsonObj.get("diagnosisCustomer");
        String diagnosisType = (String) jsonObj.get("diagnosisType");
        if ("3".equals(diagnosisType)) { // 随诊
            // 添加评价项目
            if (StringUtils.isBlank(diagnosis.getDiagnosisUser())) {
                diagnosis.setDiagnosisUser(this.getLoginUser().getUserPojo().getUserId());
            }

            // 角色权限区分
            int status = 1;
            String userType = this.getLoginUser().getUserPojo().getUserType();
            if ("assistant".equals(userType)) {
                status = 2;
            }
            List<UserInspectItemPojo> userInspectList = userInspectItemService.queryUserInspectByType(
                    diagnosis.getDiagnosisUser(), "vist");
            if (CollectionUtils.isNotEmpty(userInspectList)) {
                for (UserInspectItemPojo userInspect : userInspectList) {
                    PregDiagnosisItems diagnosisItem = new PregDiagnosisItems();
                    diagnosisItem.setInspectCode(userInspect.getInspectCode());
                    diagnosisItem.setInspectStatus(status);
                    diagnosisItem.setDiagnosisId(diagnosisId);
                    diagnosisService.addDiagnosisItem(diagnosisItem);
                }
            }
            // 添加诊断项目
            PregDiagnosisPojo lastDiagnosis = diagnosisService.getLastDiagnosis(custId);
            if (lastDiagnosis != null) {
                List<PregDiagnosisDisease> disList = diagnosisDiseaseService
                        .queryDiagnosisDiseaseByDiagnosisId(lastDiagnosis.getDiagnosisId());
                for (PregDiagnosisDisease disease : disList) {
                    disease.setDiagnosisId(diagnosisId);
                }
                diagnosisDiseaseService.saveDiagnosisDisease(disList);
            }
        } else {// 复诊
            // 添加评价项目
            String inspectListJson = (String) jsonObj.get("inspectListJson");
            if (StringUtils.isNotBlank(inspectListJson)) {
                List<PregDiagnosisItems> diagnosisItemList = NetJsonUtils.jsonArrayToList(inspectListJson,
                        PregDiagnosisItems.class);
                for (PregDiagnosisItems inspect : diagnosisItemList) {
                    inspect.setDiagnosisId(diagnosisId);
                    diagnosisService.addDiagnosisItem(inspect);
                }
            }
            // 添加诊断项目
            String diseaseListJson = (String) jsonObj.get("diseaseListJson");
            if (StringUtils.isNotBlank(diseaseListJson)) {
                List<PregDiagnosisDisease> diseaseList = NetJsonUtils
                        .jsonArrayToList(diseaseListJson, PregDiagnosisDisease.class);
                for (PregDiagnosisDisease disease : diseaseList) {
                    disease.setDiagnosisId(diagnosisId);
                    diagnosisDiseaseService.addDiagnosisDisease(disease);
                }
            }
        }

        // 获取最近一次的接诊信息
        PregDiagnosisPojo lastDiagnosis = diagnosisService.getLastDiagnosis(custId);
        if (lastDiagnosis != null) {
            // 同步最近一次的报告单到本次
            List<DiagnosisHospitalInspectReportPojo> itemLately = itemService
                    .queryDiagnosisReports(lastDiagnosis.getDiagnosisId());
            if (CollectionUtils.isNotEmpty(itemLately)) {
                itemService.addPregDiagnosisInspectReports(itemLately, diagnosisId);
            }
        }

        webResult.setSuc(diagnosisService.getDiagnosis(diagnosisId));
        return webResult;
    }

    /**
     * 重新接诊
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_RESET)
    public @ResponseBody WebResult<PregDiagnosisPojo> resetDiagnosis(String diagnosisId) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        webResult.setSuc(this.resetDiagnosisBusiness(diagnosisId));
        return webResult;
    }

    /**
     * 更新接诊信息
     * 
     * @author dhs
     * @param form
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_UPDATE)
    public @ResponseBody WebResult<PregDiagnosisPojo> updateDiagnosis(PregDiagnosis diagnosisBo) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        diagnosisService.updateDiagnosis(diagnosisBo);
        webResult.setSuc(diagnosisService.getDiagnosis(diagnosisBo.getDiagnosisId()));
        return webResult;
    }

    /**
     * 更新产科信息
     * 
     * @author dhs
     * @param
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_OBSTETRICAL_UPDATE)
    public @ResponseBody WebResult<PregDiagnosisObstetricalPojo> updateDiagnosisObstetrical(PregDiagnosisObstetricalForm form) {
        WebResult<PregDiagnosisObstetricalPojo> webResult = new WebResult<PregDiagnosisObstetricalPojo>();
        PregDiagnosisObstetrical pregDiagnosisObstetrical = TransformerUtils.transformerProperties(
                PregDiagnosisObstetrical.class, form);
        // 更改现体重
        PregDiagnosis preg = new PregDiagnosis();
        preg.setDiagnosisId(pregDiagnosisObstetrical.getDiagnosisId());
        preg.setDiagnosisCustWeight(form.getObstetricalDiagnosisWeight());
        preg.setGestationLevel(form.getObstetricalGestationLevel());
        diagnosisService.updateDiagnosis(preg);
        // 查看有没有这条接诊的产科信息，如果有就在原基础上修改，如果没有就直接添加一条
        PregDiagnosisObstetricalPojo obstetricalPojo = diagnosisService
                .getObstetricalByDiagnosisId(pregDiagnosisObstetrical.getDiagnosisId());
        if (obstetricalPojo == null) {// 添加
            diagnosisService.addDiagnosisObstetrical(pregDiagnosisObstetrical);
        } else {// 修改
            diagnosisService.updateDiagnosisObstetrical(pregDiagnosisObstetrical);
            diagnosisService.updateDiagnosisObstetricalNull(pregDiagnosisObstetrical);
        }
        synWeightResult(pregDiagnosisObstetrical.getDiagnosisId());
        webResult.setSuc(diagnosisService.getObstetricalByDiagnosisId(pregDiagnosisObstetrical.getDiagnosisId()));
        return webResult;
    }

    // ***************************************【接诊平台的公共方法】**********************************************

    /**
     * 校验初诊建档数据是否存在
     * 
     * @author zcq
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_INITAL_CHECK)
    public @ResponseBody WebResult<Boolean> checkDiagnosisInital(String diagnosisId) {
        /*
         * 1. 根据接诊号获取用户id
         * 2. 根据用户id获取最后一次初诊建档信息
         * 3. 判断有无初诊建档信息
         * 3.1 如果有初诊建档信息，判断此次接诊日期与建档信息中的末次月经日期 是否大于42周
         */
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        webResult.setValue(true);
        PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);
        PregArchivesPojo pregnancyArchive = pregArchivesService.getLastPregnancyArchive(diagnosis
                .getDiagnosisCustomer());
        if (pregnancyArchive == null) {
            webResult.setValue(false);
        } else {
            Date dueDate = JodaTimeTools
                    .toDate(JodaTimeTools.toString(pregnancyArchive.getPregnancyDueDate(), JodaTimeTools.FORMAT_6));
            Date diagnosisdate = JodaTimeTools
                    .toDate(JodaTimeTools.toString(diagnosis.getDiagnosisDate(), JodaTimeTools.FORMAT_6));
            // 如果预产期小于当前的接诊日期，需要重新建档
            int index = JodaTimeTools.compareDate(dueDate, diagnosisdate);
            if (index < 0) {
                webResult.setValue(false);
            }
        }
        // 修改接诊医生
        // PregDiagnosisPojo diagnosisPojo = diagnosisService.getDiagnosis(diagnosisId);
        // UserPojo loginUser = this.getLoginUser().getUserPojo();
        // if ("assistant".equals(loginUser.getUserType())) {
        // List<UserPojo> assistantList = userAssistantService.queryDoctorOrAssistant(this.getLoginUser().getUserPojo()
        // .getUserId());
        // if (CollectionUtils.isNotEmpty(assistantList)) {
        // diagnosisPojo.setDiagnosisUser(assistantList.get(0).getUserId());
        // diagnosisPojo.setDiagnosisUserName(assistantList.get(0).getUserName());
        // } else {
        // throw new ServiceException(ResultCode.ERROR_80014);
        // }
        // } else {
        // diagnosisPojo.setDiagnosisUser(loginUser.getUserId());
        // diagnosisPojo.setDiagnosisUserName(loginUser.getUserName());
        // }
        // PregDiagnosis diagnosisUpdate = TransformerUtils.transformerProperties(PregDiagnosis.class, diagnosisPojo);
        // diagnosisService.updateDiagnosis(diagnosisUpdate);
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 查询患者登记及档案信息
     * 
     * @author gss
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ARCHIVE_GET)
    public @ResponseBody WebResult<PregDiagnosisAnalysisPojo> getDiagnosisArchiveByDiagnosisId(String diagnosisId) {
        WebResult<PregDiagnosisAnalysisPojo> webResult = new WebResult<PregDiagnosisAnalysisPojo>();
        PregDiagnosisAnalysisPojo diagnosisArchiveBo = this.getDiagnosisArchiveByDiagnosisIdBusiness(
                diagnosisId);
        webResult.setSuc(diagnosisArchiveBo);
        return webResult;
    }

    // ***************************************【结束】**********************************************
    /**
     * 添加接诊的预约信息
     * 
     * @author dhs
     * @param DiagnosisBooking
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_ADD_BOOKDATE)
    public @ResponseBody WebResult<DiagnosisBookingPojo> AddDiagnosisBookDate(DiagnosisBooking diagnosisBooking) {
        WebResult<DiagnosisBookingPojo> webResult = new WebResult<DiagnosisBookingPojo>();
        DiagnosisBookingCondition condition = new DiagnosisBookingCondition();
        condition.setDiagnosisId(diagnosisBooking.getDiagnosisId());
        condition.setBookingDate(diagnosisBooking.getBookingDate());
        List<DiagnosisBookingPojo> list = diagnosisService.queryDiagnosisBookings(condition);
        if (list.size() > 0) {
            webResult.setError("该日期已经预约");
        } else {
            // 预约表添加一条预约信息
            String bookingId = diagnosisService.addDiagnosisBookDate(diagnosisBooking);
            webResult.setSuc(diagnosisService.queryDiagnosisBooking(bookingId));
        }
        return webResult;
    }

    /**
     * 更新接诊的预约信息
     * 
     * @author dhs
     * @param diagnosisBooking
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_UPDATE_BOOKDATE)
    public @ResponseBody WebResult<DiagnosisBookingPojo> updateDiagnosisBookDate(DiagnosisBooking diagnosisBooking) {
        WebResult<DiagnosisBookingPojo> webResult = new WebResult<DiagnosisBookingPojo>();
        DiagnosisBookingPojo pojo = diagnosisService.queryDiagnosisBooking(diagnosisBooking.getBookingId());
        int Isequals = diagnosisBooking.getBookingDate().compareTo(pojo.getBookingDate());
        if (Isequals == 0) {
            diagnosisService.updateDiagnosisBookDate(diagnosisBooking);
            webResult.setSuc(diagnosisService.queryDiagnosisBooking(diagnosisBooking.getBookingId()));
        } else {
            DiagnosisBookingCondition condition = new DiagnosisBookingCondition();
            condition.setDiagnosisId(diagnosisBooking.getDiagnosisId());
            condition.setBookingDate(diagnosisBooking.getBookingDate());
            List<DiagnosisBookingPojo> list = diagnosisService.queryDiagnosisBookings(condition);
            if (list.size() > 0) {
                webResult.setError("该日期已经预约");
            } else {
                // 预约表修改一条预约信息
                diagnosisService.updateDiagnosisBookDate(diagnosisBooking);
                webResult.setSuc(diagnosisService.queryDiagnosisBooking(diagnosisBooking.getBookingId()));
            }
        }
        return webResult;
    }

    /**
     * 删除接诊的预约信息
     * 
     * @author dhs
     * @param bookingId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_DELETE_BOOKDATE)
    public @ResponseBody WebResult<Boolean> deleteDiagnosis(String bookingId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 预约表删除一条预约信息
        diagnosisService.deleteBooking(bookingId);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 检索接诊的预约信息
     * 
     * @author dhs
     * @param bookingId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_QUERY_BOOKDATE)
    public @ResponseBody WebResult<List<DiagnosisBookingPojo>> queryDiagnosisBookingDate(DiagnosisBookingCondition condition) {
        WebResult<List<DiagnosisBookingPojo>> webResult = new WebResult<List<DiagnosisBookingPojo>>();
        webResult.setData(diagnosisService.queryDiagnosisBookings(condition));
        return webResult;
    }

    /**
     * 首诊登记
     * 
     * @author zcq
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PlatformMapping.REGISTRATION_ADD)
    public @ResponseBody WebResult<PregDiagnosisPojo> addRegistration(RegistrationForm form) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        // 第一步：注册客户基本信息
        Customer customer = TransformerUtils.transformerProperties(Customer.class, form);
        CustomerPojo custPojo = customerService.addCustomer(customer);
        // 第二步：保存接诊信息
        PregDiagnosis diagnosis = TransformerUtils.transformerProperties(PregDiagnosis.class, form);
        diagnosis.setDiagnosisCustomer(custPojo.getCustId());
        diagnosis.setDiagnosisAssistantStatus(1);// 等待接诊
        String diagnosisId = diagnosisService.addDiagnosis(diagnosis);
        // 第三步：添加评价项目
        String inspectListJson = form.getInspectListJson();
        if (StringUtils.isNotBlank(inspectListJson)) {
            List<PregDiagnosisItems> diagnosisItemList = NetJsonUtils.jsonArrayToList(inspectListJson,
                    PregDiagnosisItems.class);
            for (PregDiagnosisItems inspect : diagnosisItemList) {
                inspect.setDiagnosisId(diagnosisId);
                diagnosisService.addDiagnosisItem(inspect);
            }
        }
        // 第四步：添加诊断项目
        String diseaseListJson = form.getDiseaseListJson();
        if (StringUtils.isNotBlank(diseaseListJson)) {
            List<PregDiagnosisDisease> diseaseList = NetJsonUtils.jsonArrayToList(diseaseListJson,
                    PregDiagnosisDisease.class);
            for (PregDiagnosisDisease disease : diseaseList) {
                disease.setDiagnosisId(diagnosisId);
                diagnosisDiseaseService.addDiagnosisDisease(disease);
            }
        }
        // 第五步：返回页面添加信息
        DiagnosisCondition condition = new DiagnosisCondition();
        condition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisPojo> diagnosisList = diagnosisService.queryDiagnosisMore(condition);
        if (CollectionUtils.isNotEmpty(diagnosisList)) {
            webResult.setSuc(diagnosisList.get(0));
        }
        return webResult;
    }

    /**
     * 查询用户全部诊疗数据
     * 
     * @author dhs
     * @param
     * @return WebResult<Boolean>
     */
    @RequestMapping(value = PlatformMapping.QUERY_DIAGNOSISES)
    public @ResponseBody WebResult<List<PregDiagnosisPojo>> queryDiagnosis(String diagnosisId) {
        WebResult<List<PregDiagnosisPojo>> webResult = new WebResult<List<PregDiagnosisPojo>>();
        List<PregDiagnosisPojo> list = this.getDiagnosisesById(diagnosisId, 2);
        // 按接诊日期正序排序
        for (int x = 0; x < list.size() - 1; x++) {
            for (int y = 0; y < list.size() - 1 - x; y++) {
                int count = list.get(y).getDiagnosisDate().compareTo(list.get(y + 1).getDiagnosisDate());
                if (count > 0) {
                    PregDiagnosisPojo temp = list.get(y);
                    list.set(y, list.get(y + 1));
                    list.set(y + 1, temp);
                }
            }
        }
        webResult.setSuc(list);
        return webResult;
    }

    /**
     * 
     * 预览妊娠日记pdf报告
     * 
     * @author scd
     * @param diagnosisId
     * @param printId
     * @return
     */
    @RequestMapping(value = PlatformMapping.SHOW_DIARYPDF)
    public @ResponseBody WebResult<String> showDiaryPdf(String diagnosisId, String printId) {
        WebResult<String> webResult = new WebResult<String>();
        if (diaryPdf.equals(printId)) {
            webResult.setSuc(getDiaryPdf(diagnosisId));
        } else if (dMDietPdfCode.equals(printId)) {
            webResult.setSuc(createDMDietPdf(diagnosisId));
        } else if (wBSPdfCode.equals(printId)) {
            webResult.setSuc(createWBSPdf(diagnosisId));
        }
        return webResult;
    }

    /**
     * 
     * 协和医院_妊娠日记_模板
     * 
     * @author scd
     * @param diagnosisId
     * @return
     */
    public String getDiaryPdf(String diagnosisId) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        ReportForm diagnosisForm = new ReportForm();
        diagnosisForm.setInsId(insId);
        diagnosisForm.setReportCode(diagnosisId);
        diagnosisForm.setReportItem("P05001");
        DiaryPdf pdf = new DiaryPdf(){

            @Override
            public PregDiagnosisPojo beforeCreatePdf(ReportForm reportForm) {
                PregDiagnosisPojo po = planService.analysisDiary(reportForm.getReportCode());
                PregDiagnosisPojo pregDiagnosisPojo = diagnosisService.getDiagnosis(reportForm.getReportCode());
                // 查询孕期建档信息
                PregArchivesPojo preArchive = pregArchivesService.getLastPregnancyArchive(pregDiagnosisPojo
                        .getDiagnosisCustomer());
                pregDiagnosisPojo.setDiagnosisRiseYunqi(po.getDiagnosisRiseYunqi());// 随诊次数
                pregDiagnosisPojo.setDiagnosisRiseWeek(po.getDiagnosisRiseWeek());// 体重变化
                pregDiagnosisPojo.setDiagnosisArchiveWeight(preArchive.getWeight());
                return pregDiagnosisPojo;
            }
        };
        return pdf.create(diagnosisForm);
    }

    /**
     * 
     * 妊娠日记--长沙市妇幼保健院妊娠糖尿病饮食记录表
     * 
     * @author scd
     * @param diagnosisId
     * @return
     */
    private String createDMDietPdf(String diagnosisId) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        ReportForm diagnosisForm = new ReportForm();
        diagnosisForm.setInsId(insId);
        diagnosisForm.setReportCode(diagnosisId);
        diagnosisForm.setReportItem("P07001");
        DMDietPdf dMDietPdf = new DMDietPdf(){

            @Override
            public Map<String, String> beforeCreatePdf(ReportForm reportForm) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("insId", getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return map;
            }
        };
        return dMDietPdf.create(diagnosisForm);
    }

    /**
     * 
     * 妊娠日记--长沙市妇幼保健院血糖、体重监测表
     * 
     * @author scd
     * @param diagnosisId
     * @return
     */
    private String createWBSPdf(String diagnosisId) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        ReportForm diagnosisForm = new ReportForm();
        diagnosisForm.setInsId(insId);
        diagnosisForm.setReportCode(diagnosisId);
        diagnosisForm.setReportItem("P06001");
        WBSPdf wBSPdf = new WBSPdf(){

            @Override
            public Map<String, String> beforeCreatePdf(ReportForm reportForm) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("insId", getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return map;
            }
        };
        return wBSPdf.create(diagnosisForm);
    }

    /**
     * 保存检测项目结论
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_SAVE_INSPECT_RESULT)
    public @ResponseBody WebResult<PregDiagnosisPojo> saveDiagnosisInspectResult(String diagnosisId) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        webResult.setSuc(diagnosisService.saveDiagnosisItemResult(diagnosisId));
        return webResult;
    }

    /**
     * 更新接诊状态
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.UPDATE_DIAGNOSIS_STATUS)
    public @ResponseBody WebResult<Boolean> updateDiagnosisStatus(String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregDiagnosisPojo diagPojo = diagnosisService.getDiagnosis(diagnosisId);
        diagPojo.setDiagnosisStatus(2);
        diagnosisService.updateDiagnosis(TransformerUtils.transformerProperties(PregDiagnosis.class, diagPojo));
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 更新助理状态
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = PlatformMapping.UPDATE_DIAGNOSIS_ASSISSTENT_STATUS)
    public @ResponseBody WebResult<Boolean> updateDiagnosisAssisstentStatus(String diagnosisId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        DiagnosisCondition diagnosisCondition = new DiagnosisCondition();
        diagnosisCondition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisPojo> list = diagnosisService.queryDiagnosisMoreEvaluate(diagnosisCondition);
        if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(list.get(0).getStatusSync())) {
            String statusSync = list.get(0).getStatusSync();
            PregDiagnosis diangosisUpdate = new PregDiagnosis();
            diangosisUpdate.setDiagnosisId(diagnosisId);
            if ("1".equals(statusSync)) {// 未完成
                diangosisUpdate.setDiagnosisAssistantStatus(2);
                diagnosisService.updateDiagnosis(diangosisUpdate);
            } else if ("2".equals(statusSync)) {// 已完成
                diangosisUpdate.setDiagnosisAssistantStatus(3);
                diagnosisService.updateDiagnosis(diangosisUpdate);
            }
        }
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 
     * 更新接诊信息
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = PlatformMapping.DISEASE_INFO_UPDATE)
    public @ResponseBody WebResult<PregDiagnosisPojo> updateDiagnosisInfo(PregDiagnosis condition) {
        WebResult<PregDiagnosisPojo> webResult = new WebResult<PregDiagnosisPojo>();
        diagnosisService.updateDiagnosis(condition);
        webResult.setSuc(diagnosisService.getDiagnosis(condition.getDiagnosisId()));
        return webResult;
    }

    /***************************************************** 原business方法 **************************************/

    private PregDiagnosisPojo resetDiagnosisBusiness(String diagnosisId) {
        // 获取原来信息
        PregDiagnosisPojo diagnosisVo = diagnosisService.getDiagnosis(diagnosisId);
        // 删除人体成分数据
        PregDiagnosisItemsVo itemVo = diagnosisService.getDiagnosisItemsByDiagnosisIdAndInspectItem(diagnosisId,
                "B00003");
        if (itemVo != null && itemVo.getInspectStatus() == 3) {
            String bcaId = itemVo.getResultCode();
            inbodyService.deleteInbodyBcaById(bcaId);
        }

        // 创建新的接诊信息
        PregDiagnosis pregDiagnosisBo = new PregDiagnosis();
        pregDiagnosisBo.setDiagnosisOrg(diagnosisVo.getDiagnosisOrg());
        pregDiagnosisBo.setDiagnosisReferralDoctor(diagnosisVo.getDiagnosisReferralDoctor());
        pregDiagnosisBo.setDiagnosisReferralDoctorName(diagnosisVo.getDiagnosisReferralDoctorName());
        pregDiagnosisBo.setDiagnosisType(diagnosisVo.getDiagnosisType());
        pregDiagnosisBo.setDiagnosisStatus(2);
        pregDiagnosisBo.setDiagnosisCustomer(diagnosisVo.getDiagnosisCustomer());
        String diagnosisIdNew = diagnosisService.addDiagnosis(pregDiagnosisBo);

        // 添加检查项目
        DiagnosisItemsCondition itemCondition = new DiagnosisItemsCondition();
        itemCondition.setDiagnosisId(diagnosisId);
        List<PregDiagnosisItemsVo> diagnosisItemList = diagnosisService.queryDiagnosisItem(itemCondition);
        if (CollectionUtils.isNotEmpty(diagnosisItemList)) {
            List<String> list = new ArrayList<String>();
            for (PregDiagnosisItemsVo item : diagnosisItemList) {
                list.add(item.getInspectCode());
                if (StringUtils.isNotBlank(item.getResultCode()) && StringUtils.isNotBlank(item.getInspectCode())) {
                    examResultRecordUtil.deleteExamResultRecordByExamCodeAndExamCategory(item.getResultCode(),
                            item.getInspectCode());
                }
            }
            diagnosisService.saveDiagnosisItems(diagnosisIdNew, list);
        }
        PregDiagnosisPojo reDiagnosisVo = diagnosisService.getDiagnosis(diagnosisIdNew);

        // 删除接诊相关信息
        diagnosisService.deleteDiagnosisRelation(diagnosisId);

        return reDiagnosisVo;
    }

    private PregDiagnosisAnalysisPojo getDiagnosisArchiveByDiagnosisIdBusiness(String diagnosisId) {
        if (StringUtils.isEmpty(diagnosisId)) {
            return null;
        }
        PregDiagnosisAnalysisPojo archiveBo = new PregDiagnosisAnalysisPojo();
        PregDiagnosisPojo diagnosisVo = diagnosisService.getDiagnosis(diagnosisId);
        CustomerPojo customerVo = customerService.getCustomer(diagnosisVo.getDiagnosisCustomer());
        if (customerVo == null) {
            customerVo = new CustomerPojo();
        }
        PregArchivesPojo archivesVo = archivesService.getLastPregnancyArchive(customerVo.getCustId());
        if (archivesVo == null) {
            archivesVo = new PregArchivesPojo();
        }
        // 设置孕产期
        Date lmpDate = archivesVo.getLmp();
        if (lmpDate != null) {
            Date lmp = JodaTimeTools.after_day(lmpDate, 280);
            diagnosisVo.setDiagnosisDueDate(lmp);
        }
        archivesVo.setCreateDateString(JodaTimeTools.toString(archivesVo.getCreateDatetime(), JodaTimeTools.FORMAT_6));
        archiveBo.setPregArchivesVo(archivesVo);
        archiveBo.setCustomerVo(customerVo);
        archiveBo.setDiagnosisVo(diagnosisVo);
        return archiveBo;
    }

    /***************************************************** 工具方法 **************************************/

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
        if (status > 0) {
            diagnosisCondition.setDiagnosisStatus(status);
        }
        diagnosisCondition.setDiagnosisCustomer(cusId);
        return diagnosisService.queryDiagnosis(diagnosisCondition);
    }

    /**
     * 通过预约日期计算孕周
     * 
     * @author dhs
     * @param custId
     * @param bookingDate
     * @return
     */
    private String reckonWeek(String custId, Date bookingDate) {
        PregArchivesPojo preArchrive = archivesService.getLastPregnancyArchive(custId);
        if (preArchrive != null && preArchrive.getLmp() != null) {
            // 计算还有多少天到预产期--使用预产期计算孕周
            String dateString = JodaTimeTools.toString(bookingDate, JodaTimeTools.FORMAT_6);
            Date date = JodaTimeTools.toDate(dateString);// 格式化的日期 ，不含时分秒
            int intervalDays = JodaTimeTools.getDays(date, preArchrive.getPregnancyDueDate());
            // 计算已经度过的天数--使用预产期计算孕周
            int pregnantDays = 280 - intervalDays;
            int pregnantWeeks = pregnantDays / 7;
            int plusDays = pregnantDays % 7;
            return pregnantWeeks + "+" + plusDays;
        } else {
            return null;
        }
    }

    /**
     * 现体重与医嘱_复诊建议体重显示联动修改
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    private void synWeightResult(String diagnosisId) {
        PregDiagnosisPojo diagnosis = diagnosisService.getDiagnosis(diagnosisId);
        String cusId = diagnosis.getDiagnosisCustomer();
        CustomerPojo customer = customerService.getCustomer(cusId);
        // 需求：现体重与医嘱_复诊建议体重显示联动修改
        if (StringUtils.isEmpty(diagnosis.getDiagnosisRiseWeek())) {// 如果数据库中每周增长范围没有数据，那么就根据bmi计算
            BigDecimal bmi = customer.getCustBmi();
            float bmif = bmi.floatValue();
            if (bmif < 18.5) {
                diagnosis.setDiagnosisRiseWeek("0.44-0.58");
            } else if (bmif >= 18.5 && bmif <= 24.9) {
                diagnosis.setDiagnosisRiseWeek("0.35-0.50");
            } else if (bmif >= 25.0 && bmif <= 29.9) {
                diagnosis.setDiagnosisRiseWeek("0.23-0.33");
            } else if (bmif >= 30.0) {
                diagnosis.setDiagnosisRiseWeek("0.17-0.27");
            }
        }
        DiagnosisBookingCondition condition = new DiagnosisBookingCondition();
        condition.setDiagnosisId(diagnosisId);
        List<DiagnosisBookingPojo> diagnosisBookingList = diagnosisService.queryDiagnosisBookings(condition);
        if (diagnosisBookingList.size() > 0) {// 已预约
            for (DiagnosisBookingPojo booking : diagnosisBookingList) {
                if (diagnosis.getDiagnosisCustWeight() == null) { // 现体重为空
                    DiagnosisBooking book = new DiagnosisBooking();
                    book.setBookingId(booking.getBookingId());
                    book.setBookingSuggest("");
                    book.setBookingDate(booking.getBookingDate());
                    diagnosisService.updateDiagnosisBookDate(book);
                } else { // 现体重不为空
                    // 现体重
                    Double weight_present = Double.parseDouble(diagnosis.getDiagnosisCustWeight().toString());
                    // 现孕周
                    Integer week_present = Integer.parseInt(diagnosis.getDiagnosisGestationalWeeks().split(
                            "\\+")[0]);
                    // 随诊时孕周
                    Integer week_this = Integer
                            .parseInt(this.reckonWeek(cusId, booking.getBookingDate()).split("\\+")[0]);
                    // 每周增长范围
                    Double add_one = Double.parseDouble(diagnosis.getDiagnosisRiseWeek().split("-")[0]);
                    Double add_two = Double.parseDouble(diagnosis.getDiagnosisRiseWeek().split("-")[1]);
                    DiagnosisBooking book = new DiagnosisBooking();
                    if (week_this == week_present) {
                        book.setBookingSuggest("建议每周体重适宜增长范围：" + add_one + "-" + add_two + "公斤");
                    } else {
                        String result = "建议下次复诊时体重增长至："
                                + (Math.round((weight_present + (week_this - week_present) * add_one) * 100) / 100.0)
                                + "-"
                                + (Math.round((weight_present + (week_this - week_present) * add_two) * 100) / 100.0)
                                + "公斤";
                        book.setBookingSuggest(result);
                    }
                    book.setBookingDate(booking.getBookingDate());
                    book.setBookingId(booking.getBookingId());
                    diagnosisService.updateDiagnosisBookDate(book);
                }
            }
        }
    }

    /**
     * 根据问诊id查询报告单信息
     * 
     * @author mlq
     * @param diagnosisId
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_REPORT_INFO)
    private @ResponseBody WebResult<List<DiagnosisHospitalInspectReportPojo>> queryDiagnosisReports(String diagnosisId) {
        WebResult<List<DiagnosisHospitalInspectReportPojo>> webResult = new WebResult<List<DiagnosisHospitalInspectReportPojo>>();
        List<DiagnosisHospitalInspectReportPojo> reportPojoList = new ArrayList<DiagnosisHospitalInspectReportPojo>();
        if (StringUtils.isBlank(diagnosisId)) {
            reportPojoList = itemService.queryDiagnosisReports(diagnosisId);
        }
        return webResult.setSuc(reportPojoList);
    }
    // /**
    // * 保存实验室指标
    // *
    // * @author xdc
    // * @param detailList
    // * @param diagnosisId
    // * @return
    // */
    // public String saveQuotaItems(List<PregDiagnosisClinical> detailList, String diagnosisId) {
    // // 保存表user_diagnosis_clinical，删除原有的指标
    // clinicalService.deleteDiagnosisClinicalByExamCode(diagnosisId);
    // if (CollectionUtils.isNotEmpty(detailList)) {
    // // 过滤指标结果为空的数据，不保存
    // Iterator<PregDiagnosisClinical> it = detailList.iterator();
    // while (it.hasNext()) {
    // PregDiagnosisClinical x = it.next();
    // if (StringUtils.isEmpty(x.getItemString())) {
    // it.remove();
    // }
    // }
    // // 保存指标列表，addExamItems方法是公用方法
    // clinicalService.addPregDiagnosisClinical(detailList);
    // }
    // return "success";
    // }

}
