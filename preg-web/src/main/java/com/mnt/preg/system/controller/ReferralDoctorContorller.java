
package com.mnt.preg.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.system.condition.ReferralDepartmentCondition;
import com.mnt.preg.system.condition.ReferralDoctorCondition;
import com.mnt.preg.system.entity.ReferralDoctor;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.pojo.ReferralDepartmentPojo;
import com.mnt.preg.system.pojo.ReferralDoctorPojo;
import com.mnt.preg.system.service.ReferralDepartmentService;
import com.mnt.preg.system.service.ReferralDoctorService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 转诊医生配置Controller
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：
 *          v1.5 2018-03-21 dhs 初版
 */
@Controller
public class ReferralDoctorContorller extends BaseController {

    @Resource
    private ReferralDoctorService referralDoctorService;

    @Resource
    private ReferralDepartmentService referralDepartmentService;

    /**
     * 初始化页面
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.REFERRAL_DOCTOR_VIEW)
    public @ResponseBody
    WebResult<ReferralDoctorPojo> queryReferralDoctorView(ReferralDoctorCondition condition) {
        WebResult<ReferralDoctorPojo> webResult = new WebResult<ReferralDoctorPojo>();
        webResult.setData(referralDoctorService.queryDoctors(condition));
        return webResult;
    }

    /**
     * 添加
     * 
     * @author dhs
     * @param nutrientBo
     * @return
     */
    @RequestMapping(value = SystemMapping.REFERRAL_DOCTOR_ADD)
    public @ResponseBody
    WebResult<ReferralDoctorPojo> addNutrient(ReferralDoctor doctor) {
        WebResult<ReferralDoctorPojo> webResult = new WebResult<ReferralDoctorPojo>();
        ReferralDepartmentCondition condition = new ReferralDepartmentCondition();
        condition.setReferralId(doctor.getDoctorDepartmentId());
        List<ReferralDepartmentPojo> list = referralDepartmentService.queryReferralDepartment(condition);
        doctor.setDoctorDepartmentName(list.get(0).getReferralName());
        webResult.setSuc(referralDoctorService.saveReferralDoctor(doctor), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 修改
     * 
     * @author dhs
     * @param nutrientBo
     * @return
     */
    @RequestMapping(value = SystemMapping.REFERRAL_DOCTOR_UPDATE)
    public @ResponseBody
    WebResult<ReferralDoctorPojo> updateNutrient(ReferralDoctor doctor) {
        WebResult<ReferralDoctorPojo> webResult = new WebResult<ReferralDoctorPojo>();
        ReferralDepartmentCondition condition = new ReferralDepartmentCondition();
        condition.setReferralId(doctor.getDoctorDepartmentId());
        List<ReferralDepartmentPojo> list = referralDepartmentService.queryReferralDepartment(condition);
        doctor.setDoctorDepartmentName(list.get(0).getReferralName());
        webResult.setSuc(referralDoctorService.updateReferralDoctor(doctor), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 删除
     * 
     * @author dhs
     * @param id
     * @return
     */
    @RequestMapping(value = SystemMapping.REFERRAL_DOCTOR_DELETE)
    public @ResponseBody
    WebResult<Boolean> removeNutrient(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        referralDoctorService.removeReferralDoctor(id);;
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 验证是否重复
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.REFERRAL_DOCTOR_ICARD_VALIDATE)
    @ResponseBody
    public boolean vaildCheckdietName(String value, String editFormType, String property) {
        if ("update".equals(editFormType)) {
            return true;
        }
        int index = referralDoctorService.validCode(value, property);
        if (index > 0) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 检索转诊医生以及该转诊医生所在的科室
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.REFERRAL_DOCTOR_DEPT)
    public @ResponseBody
    WebResult<ReferralDoctorPojo> queryReferralDoctorDept(ReferralDoctor condition) {
        WebResult<ReferralDoctorPojo> webResult = new WebResult<ReferralDoctorPojo>();
        webResult.setData(referralDoctorService.queryDoctorDept(condition));
        return webResult;
    }
}
