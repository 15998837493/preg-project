/*
 * ReferralDepartmentCondition.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 转诊科室检索条件
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
public class ReferralDepartmentCondition implements Serializable {

    private static final long serialVersionUID = -4333217967150876338L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String referralId;

    /** 科室编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String referralCode;

    /** 科室名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String referralName;

    /** 检索内容 */
    private String content;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
