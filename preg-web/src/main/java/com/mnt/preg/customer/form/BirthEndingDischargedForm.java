
package com.mnt.preg.customer.form;

import java.math.BigDecimal;

public class BirthEndingDischargedForm {

    /**
     * @备注: 主键
     * @字段:dis_id VARCHAR(64)
     */
    private String disId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    private String birthId;

    /**
     * @备注: 母亲出院诊断
     * @字段:dis_mother_disagnosis VARCHAR(1000)
     */
    private String disMotherDisagnosis;

    /**
     * @备注: 母亲产后血红蛋白
     * @字段:dis_hemoglobin DECIMAL(7,1)
     */
    private BigDecimal disHemoglobin;

    /**
     * @备注: 新生儿出院诊断
     * @字段:dis_baby_diagnosis VARCHAR(1000)
     */
    private String disBabyDiagnosis;

    /**
     * @备注: 新生儿血糖
     * @字段:dis_baby_bloodSuger DECIMAL(7,1)
     */
    private BigDecimal disBabyBloodSuger;

    /**
     * @备注: 新生儿Id
     * @字段:baby_id VARCHAR(64)
     */
    private String babyId;

    /**
     * @备注: 备注
     * @字段:dis_remark VARCHAR(1000)
     */
    private String disRemark;

    public BirthEndingDischargedForm() {
    }

    public BirthEndingDischargedForm(String disId) {
        this.disId = disId;
    }

    public String getDisId() {
        return disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
    }

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
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

}
