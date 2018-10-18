
package com.mnt.preg.customer.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

@Entity
@Table(name = "cus_birthending_discharged")
public class BirthEndingDischarged extends MappedEntity {

    /** [属性说明] */
    private static final long serialVersionUID = 6704880383090581491L;

    /**
     * @备注: 主键
     * @字段:dis_id VARCHAR(64)
     */
    private String disId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    @UpdateAnnotation
    private String birthId;

    /**
     * @备注: 母亲出院诊断
     * @字段:dis_mother_disagnosis VARCHAR(1000)
     */
    @UpdateAnnotation
    private String disMotherDisagnosis;

    /**
     * @备注: 母亲产后血红蛋白
     * @字段:dis_hemoglobin DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal disHemoglobin;

    /**
     * @备注: 新生儿出院诊断
     * @字段:dis_baby_diagnosis VARCHAR(1000)
     */
    @UpdateAnnotation
    private String disBabyDiagnosis;

    /**
     * @备注: 新生儿血糖
     * @字段:dis_baby_bloodSuger DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal disBabyBloodSuger;

    /**
     * @备注: 新生儿Id
     * @字段:baby_id VARCHAR(64)
     */
    @UpdateAnnotation
    private String babyId;

    /**
     * @备注: 备注
     * @字段:dis_remark VARCHAR(1000)
     */
    @UpdateAnnotation
    private String disRemark;

    public BirthEndingDischarged() {
    }

    public BirthEndingDischarged(String disId) {
        this.disId = disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
    }

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "dis_id", insertable = false, updatable = false)
    public String getDisId() {
        return this.disId;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    @Column(name = "birth_id")
    public String getBirthId() {
        return this.birthId;
    }

    public void setDisMotherDisagnosis(String disMotherDisagnosis) {
        this.disMotherDisagnosis = disMotherDisagnosis;
    }

    @Column(name = "dis_mother_disagnosis")
    public String getDisMotherDisagnosis() {
        return this.disMotherDisagnosis;
    }

    public void setDisHemoglobin(BigDecimal disHemoglobin) {
        this.disHemoglobin = disHemoglobin;
    }

    @Column(name = "dis_hemoglobin")
    public BigDecimal getDisHemoglobin() {
        return this.disHemoglobin;
    }

    public void setDisBabyDiagnosis(String disBabyDiagnosis) {
        this.disBabyDiagnosis = disBabyDiagnosis;
    }

    @Column(name = "dis_baby_diagnosis")
    public String getDisBabyDiagnosis() {
        return this.disBabyDiagnosis;
    }

    public void setDisBabyBloodSuger(BigDecimal disBabyBloodSuger) {
        this.disBabyBloodSuger = disBabyBloodSuger;
    }

    @Column(name = "dis_baby_blood_suger")
    public BigDecimal getDisBabyBloodSuger() {
        return this.disBabyBloodSuger;
    }

    public void setDisRemark(String disRemark) {
        this.disRemark = disRemark;
    }

    @Column(name = "dis_remark")
    public String getDisRemark() {
        return this.disRemark;
    }

    @Column(name = "baby_id")
    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

}
