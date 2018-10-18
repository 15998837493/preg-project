/*
 * ReferralDepartmentController.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.system.condition.ReferralDepartmentCondition;
import com.mnt.preg.system.entity.ReferralDepartment;
import com.mnt.preg.system.mapping.ReferralDepartmentMapping;
import com.mnt.preg.system.pojo.ReferralDepartmentPojo;
import com.mnt.preg.system.service.ReferralDepartmentService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 转诊科室Controller
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
@Controller
public class ReferralDepartmentController extends BaseController {

    @Resource
    private ReferralDepartmentService referralDepartmentService;

    /**
     * 
     * 异步获取医院收费项目
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = ReferralDepartmentMapping.REFERRAL_QUERY)
    public @ResponseBody
    WebResult<ReferralDepartmentPojo> queryReferralDepartment(ReferralDepartmentCondition condition) {
        WebResult<ReferralDepartmentPojo> webResult = new WebResult<ReferralDepartmentPojo>();
        webResult.setData(referralDepartmentService.queryReferralDepartment(condition));
        return webResult;
    }

    /**
     * 
     * 根据名称或编码检索转诊科室
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = ReferralDepartmentMapping.REFERRAL_QUERY_BY_CONTENT)
    public @ResponseBody
    WebResult<ReferralDepartmentPojo> queryReferralByContent(ReferralDepartmentCondition condition) {
        WebResult<ReferralDepartmentPojo> webResult = new WebResult<ReferralDepartmentPojo>();
        webResult.setData(referralDepartmentService.queryReferralByContent(condition));
        return webResult;
    }

    /**
     * 
     * 添加转诊科室
     * 
     * @author scd
     * @param referralDepartment
     * @return
     */
    @RequestMapping(value = ReferralDepartmentMapping.REFERRAL_SAVE)
    public @ResponseBody
    WebResult<ReferralDepartmentPojo> saveReferralDepartment(ReferralDepartment referralDepartment) {
        WebResult<ReferralDepartmentPojo> webResult = new WebResult<ReferralDepartmentPojo>();
        // 生成转诊科室编码
        referralDepartment.setReferralCode(referralDepartmentService.getReferralDepartmentCode());
        String referralId = referralDepartmentService.saveReferralDepartment(referralDepartment);
        webResult.setSuc(referralDepartmentService.getReferralDepartment(referralId));
        return webResult;
    }

    /**
     * 
     * 修改转诊科室
     * 
     * @author scd
     * @param referralDepartment
     * @return
     */
    @RequestMapping(value = ReferralDepartmentMapping.REFERRAL_UPDATE)
    public @ResponseBody
    WebResult<ReferralDepartmentPojo> updateReferralDepartment(ReferralDepartment referralDepartment) {
        WebResult<ReferralDepartmentPojo> webResult = new WebResult<ReferralDepartmentPojo>();
        referralDepartmentService.updateReferralDepartment(referralDepartment);
        webResult.setSuc(referralDepartmentService.getReferralDepartment(referralDepartment.getReferralId()));
        return webResult;
    }

    /**
     * 
     * 删除转诊科室
     * 
     * @author scd
     * @param referralId
     * @return
     */
    @RequestMapping(value = ReferralDepartmentMapping.REFERRAL_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeReferralDepartment(String referralId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        referralDepartmentService.removeReferralDepartment(referralId);
        webResult.setSuc(true, "成功");
        return webResult;
    }

    /**
     * 
     * 验证转诊科室名称是否重复
     * 
     * @author scd
     * @param referralName
     * @param referralOldName
     * @param type
     * @return
     */
    @RequestMapping(value = ReferralDepartmentMapping.REFERRAL_CHEACK_NAME)
    public @ResponseBody
    boolean checkReferralDepartmentName(String referralName, String referralOldName, String type) {
        boolean typeIndex = "add".equals(type);
        if (!typeIndex && referralName.equals(referralOldName)) {
            return true;
        }
        int index = referralDepartmentService.checkReferralDepartmentName(referralName);
        if (index < 1) {
            return true;
        }
        return false;
    }

}
