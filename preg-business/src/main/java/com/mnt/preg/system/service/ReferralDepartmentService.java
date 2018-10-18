/*
 * ReferralDepartmentService.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.condition.ReferralDepartmentCondition;
import com.mnt.preg.system.entity.ReferralDepartment;
import com.mnt.preg.system.pojo.ReferralDepartmentPojo;

/**
 * 转诊科室Serivice
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
@Validated
public interface ReferralDepartmentService {

    /**
     * 
     * 根据主键获取转诊科室
     * 
     * @author scd
     * @param referralId
     * @return
     */
    ReferralDepartmentPojo getReferralDepartment(String referralId);

    /**
     * 
     * 检索转诊科室
     * 
     * @author scd
     * @param condition
     * @return
     */
    List<ReferralDepartmentPojo> queryReferralDepartment(ReferralDepartmentCondition condition);

    /**
     * 
     * 添加转诊门诊
     * 
     * @author scd
     * @param referralDepartment
     * @return
     */
    String saveReferralDepartment(@Valid @NotNull ReferralDepartment referralDepartment);

    /**
     * 
     * 修改转诊门诊
     * 
     * @author scd
     * @param referralDepartment
     * @return
     */
    void updateReferralDepartment(@Valid @NotNull ReferralDepartment referralDepartment);

    /**
     * 
     * 删除转诊门诊
     * 
     * @author scd
     * @param referralId
     */
    void removeReferralDepartment(@NotBlank String referralId);

    /**
     * 
     * 验证转诊科室名称是否重复
     * 
     * @author scd
     * @param referralName
     * @return
     */
    Integer checkReferralDepartmentName(@NotBlank String referralName);

    /**
     * 
     * 生成转诊科室编码
     * 
     * @author scd
     * @param insId
     * @return
     */
    String getReferralDepartmentCode();

    /**
     * 
     * 根据转诊科室名称或编码检索
     * 
     * @author scd
     * @param condition
     * @return
     */
    List<ReferralDepartmentPojo> queryReferralByContent(ReferralDepartmentCondition condition);
}
