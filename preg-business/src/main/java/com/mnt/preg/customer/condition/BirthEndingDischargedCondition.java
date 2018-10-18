
package com.mnt.preg.customer.condition;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.core.utils.SymbolConstants;

public class BirthEndingDischargedCondition extends PageCondition implements Serializable {

    /** [属性说明] */
    private static final long serialVersionUID = -4126428866123462L;

    /**
     * @备注: 主键
     * @字段:dis_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String disId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String birthId;

    /**
     * @备注: 母亲出院诊断
     * @字段:dis_mother_disagnosis VARCHAR(1000)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String disMotherDisagnosis;

    /**
     * @备注: 母亲产后血红蛋白
     * @字段:dis_hemoglobin DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal disHemoglobin;

    /**
     * @备注: 新生儿出院诊断
     * @字段:dis_baby_diagnosis VARCHAR(1000)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String disBabyDiagnosis;

    /**
     * @备注: 新生儿血糖
     * @字段:dis_baby_bloodSuger DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal disBabyBloodSuger;

    /**
     * @备注: 新生儿Id
     * @字段:baby_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyId;

    /**
     * @备注: 备注
     * @字段:dis_remark VARCHAR(1000)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String disRemark;

    // 标识
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public BirthEndingDischargedCondition() {
    }

    public BirthEndingDischargedCondition(
            String disId) {
        this.disId = disId;
    }

    public String getDisId() {
        return disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
    }

    public String getBirthId() {
        return birthId;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    public String getDisMotherDisagnosis() {
        return disMotherDisagnosis;
    }

    public void setDisMotherDisagnosis(String disMotherDisagnosis) {
        this.disMotherDisagnosis = disMotherDisagnosis;
    }

    public BigDecimal getDisHemoglobin() {
        return disHemoglobin;
    }

    public void setDisHemoglobin(BigDecimal disHemoglobin) {
        this.disHemoglobin = disHemoglobin;
    }

    public String getDisBabyDiagnosis() {
        return disBabyDiagnosis;
    }

    public void setDisBabyDiagnosis(String disBabyDiagnosis) {
        this.disBabyDiagnosis = disBabyDiagnosis;
    }

    public BigDecimal getDisBabyBloodSuger() {
        return disBabyBloodSuger;
    }

    public void setDisBabyBloodSuger(BigDecimal disBabyBloodSuger) {
        this.disBabyBloodSuger = disBabyBloodSuger;
    }

    public String getDisRemark() {
        return disRemark;
    }

    public void setDisRemark(String disRemark) {
        this.disRemark = disRemark;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

}
