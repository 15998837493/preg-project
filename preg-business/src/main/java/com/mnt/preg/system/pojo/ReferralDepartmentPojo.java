/*
 * ReferralDepartmentPojo.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 转诊科室Pojo
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
public class ReferralDepartmentPojo {

    /** 主键 */
    @QueryFieldAnnotation
    private String referralId;

    /** 科室编码 */
    @QueryFieldAnnotation
    private String referralCode;

    /** 科室名称 */
    @QueryFieldAnnotation
    private String referralName;

    /** 科室备注 */
    @QueryFieldAnnotation
    private String referralPs;

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getReferralPs() {
        return referralPs;
    }

    public void setReferralPs(String referralPs) {
        this.referralPs = referralPs;
    }

}
