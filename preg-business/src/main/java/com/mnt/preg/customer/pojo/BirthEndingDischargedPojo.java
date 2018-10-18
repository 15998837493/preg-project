
package com.mnt.preg.customer.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.main.enums.Flag;

public class BirthEndingDischargedPojo implements Serializable {

    /** [属性说明] */
    private static final long serialVersionUID = 6929840007466217088L;

    /**
     * @备注: 主键
     * @字段:dis_id VARCHAR(64)
     */
    @QueryFieldAnnotation
    private String disId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    @QueryFieldAnnotation
    private String birthId;

    /**
     * @备注: 母亲出院诊断
     * @字段:dis_mother_disagnosis VARCHAR(1000)
     */
    @QueryFieldAnnotation
    private String disMotherDisagnosis;

    /**
     * @备注: 母亲产后血红蛋白
     * @字段:dis_hemoglobin DECIMAL(7,1)
     */
    @QueryFieldAnnotation
    private BigDecimal disHemoglobin;

    /**
     * @备注: 新生儿出院诊断
     * @字段:dis_baby_diagnosis VARCHAR(1000)
     */
    @QueryFieldAnnotation
    private String disBabyDiagnosis;

    /**
     * @备注: 新生儿血糖
     * @字段:dis_baby_bloodSuger DECIMAL(7,1)
     */
    @QueryFieldAnnotation
    private BigDecimal disBabyBloodSuger;

    /**
     * @备注: 新生儿Id
     * @字段:baby_id VARCHAR(64)
     */
    @QueryFieldAnnotation
    private String babyId;

    /**
     * @备注: 备注
     * @字段:dis_remark VARCHAR(1000)
     */
    @QueryFieldAnnotation
    private String disRemark;

    /** 创建时间 */
    @QueryFieldAnnotation
    private Date createTime;

    /** 更新时间 */
    @QueryFieldAnnotation
    private Date updateTime;

    /** 创建者 */
    @QueryFieldAnnotation
    private String createUserId;

    /** 更新者 */
    @QueryFieldAnnotation
    private String updateUserId;

    /** 逻辑删除标识 */
    @QueryFieldAnnotation
    private Integer flag = Flag.normal.getValue();

    /** 所属机构 */
    @QueryFieldAnnotation
    private String createInsId;

    public BirthEndingDischargedPojo() {
    }

    public BirthEndingDischargedPojo(
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

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

}
