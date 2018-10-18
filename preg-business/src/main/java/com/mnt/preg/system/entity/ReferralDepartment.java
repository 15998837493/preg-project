/*
 * ReferralDepartment.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 转诊科室
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
@Entity
@Table(name = "sys_referral_dept")
public class ReferralDepartment extends MappedEntity {

    private static final long serialVersionUID = -3737388070252533375L;

    /** 主键 */
    private String referralId;

    /** 科室编码 */
    @UpdateAnnotation
    private String referralCode;

    /** 科室名称 */
    @UpdateAnnotation
    private String referralName;

    /** 科室备注 */
    @UpdateAnnotation
    private String referralPs;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "referral_id")
    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    @Column(name = "referral_code")
    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Column(name = "referral_name")
    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    @Column(name = "referral_ps")
    public String getReferralPs() {
        return referralPs;
    }

    public void setReferralPs(String referralPs) {
        this.referralPs = referralPs;
    }

}
