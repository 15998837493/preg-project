
package com.mnt.preg.evaluate.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.customer.service.PregAdjustRecordService;
import com.mnt.preg.evaluate.mapping.EvaluateMapping;
import com.mnt.preg.evaluate.mapping.EvaluatePageName;
import com.mnt.preg.examitem.service.PregInbodyService;
import com.mnt.preg.examitem.service.QuestionAnswerServiceImpl;
import com.mnt.preg.master.pojo.items.InspectItemPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.service.items.InspectItemService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.pojo.UserInspectItemPojo;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.platform.service.UserInspectItemService;
import com.mnt.preg.system.condition.DoctorAssistantCondition;
import com.mnt.preg.system.pojo.ReferralDoctorPojo;
import com.mnt.preg.system.pojo.UserAssistantPojo;
import com.mnt.preg.system.service.ReferralDoctorService;
import com.mnt.preg.system.service.UserAssistantService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 营养评价平台
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-4-4 zcq 初版
 */
@Controller
public class EvaluateController extends BaseController {

    @Resource
    private CustomerService customerService;

    @Resource
    private InspectItemService inspectItemService;

    @Resource
    private PregDiagnosisService diagnosisService;

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
    private UserAssistantService userAssistantService;

    @Resource
    private UserInspectItemService userInspectItemService;

    /**
     * 评价平台一览页
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = EvaluateMapping.DIAGNOSIS_EVALUATE_VIEW)
    public String toPlatFormCustomerView(Model model) {
        // 接诊医生信息
        DoctorAssistantCondition condition = new DoctorAssistantCondition();
        String assistantId = this.getLoginUser().getUserPojo().getUserId();
        condition.setAssistantId(assistantId);
        List<UserAssistantPojo> userAssistantList = userAssistantService.queryDoctorByCondition(condition);
        model.addAttribute("userAssistantList", userAssistantList);
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
        model.addAttribute("loginUserId", this.getLoginUserId());
        return EvaluatePageName.EVALUATE_CUSTOMER_VIEW;
    }

    /**
     * 获取所选医生默认评价项目
     * 
     * @author mlq
     * @param doctorId
     * @param inspectType
     * @return
     */
    @RequestMapping(value = EvaluateMapping.DOCTOR_INSPECT_LIST)
    public @ResponseBody
    WebResult<List<UserInspectItemPojo>> queryDoctorInspectListByDoctorId(String doctorId, String inspectType) {
        WebResult<List<UserInspectItemPojo>> webResult = new WebResult<List<UserInspectItemPojo>>();
        List<UserInspectItemPojo> userInspectFirstList = userInspectItemService.queryUserInspectByType(doctorId,
                inspectType);
        return webResult.setSuc(userInspectFirstList);
    }
}
