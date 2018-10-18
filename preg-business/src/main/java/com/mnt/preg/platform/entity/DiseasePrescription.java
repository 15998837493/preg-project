/*
 * MasPrescription.java    1.0  2017-7-18
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 诊断营养处方
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-18 scd 初版
 */
@Entity
@Table(name = "user_disease_prescription")
public class DiseasePrescription extends MappedEntity {

    private static final long serialVersionUID = -5457773378532481350L;

    /** 编号 */
    @UpdateAnnotation
    private String prescriptionId;

    /** 诊断编码 */
    @UpdateAnnotation
    private String diseaseId;

    /** 单词剂量 */
    @UpdateAnnotation
    private BigDecimal productDosage;

    /** 产品主键 */
    @UpdateAnnotation
    private String productId;

    /** 产品编码 */
    @UpdateAnnotation
    private String productCode;

    /** 用药频次 */
    @UpdateAnnotation
    private String productFrequency;

    /** 每日用药频率 */
    @UpdateAnnotation
    private String productDayFrequency;

    /** 剂量 */
    @UpdateAnnotation
    private String productUsage;

    /** 数量 */
    @UpdateAnnotation
    private BigDecimal productAmount;

    /** 用药疗程 */
    @UpdateAnnotation
    private String productTreatment;

    /** 商品规格 */
    @UpdateAnnotation
    private String productStandard;

    /** 剂量说明 */
    @UpdateAnnotation
    private String productDosageDesc;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "prescription_id")
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @Column(name = "disease_id")
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "product_code")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "product_frequency")
    public String getProductFrequency() {
        return productFrequency;
    }

    public void setProductFrequency(String productFrequency) {
        this.productFrequency = productFrequency;
    }

    @Column(name = "product_day_frequency")
    public String getProductDayFrequency() {
        return productDayFrequency;
    }

    public void setProductDayFrequency(String productDayFrequency) {
        this.productDayFrequency = productDayFrequency;
    }

    @Column(name = "product_usage")
    public String getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(String productUsage) {
        this.productUsage = productUsage;
    }

    @Column(name = "product_amount")
    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    @Column(name = "product_treatment")
    public String getProductTreatment() {
        return productTreatment;
    }

    public void setProductTreatment(String productTreatment) {
        this.productTreatment = productTreatment;
    }

    @Column(name = "product_standard")
    public String getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }

    @Column(name = "product_dosage_desc")
    public String getProductDosageDesc() {
        return productDosageDesc;
    }

    public void setProductDosageDesc(String productDosageDesc) {
        this.productDosageDesc = productDosageDesc;
    }

    @Column(name = "product_dosage")
    public BigDecimal getProductDosage() {
        return productDosage;
    }

    public void setProductDosage(BigDecimal productDosage) {
        this.productDosage = productDosage;
    }

}
