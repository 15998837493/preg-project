/*
 * MasPrescriptionPojo.java    1.0  2017-7-18
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 诊断营养处方
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-18 scd 初版
 */
public class DiseasePrescriptionPojo implements Serializable {

    private static final long serialVersionUID = 5398142453444782865L;

    /** 编号 */
    @QueryFieldAnnotation
    private String prescriptionId;

    /** 诊断编码 */
    @QueryFieldAnnotation
    private String diseaseId;

    /** 产品主键 */
    @QueryFieldAnnotation
    private String productId;

    /** 产品编码 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productCode;

    /** 单次剂量 */
    @QueryFieldAnnotation(aliasName = "product")
    private BigDecimal productDosage;

    /** 用药频次 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productFrequency;

    /** 每日用药频率 */
    @QueryFieldAnnotation
    private String productDayFrequency;

    /** 剂量 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productUsage;

    /** 数量 */
    @QueryFieldAnnotation
    private BigDecimal productAmount;

    /** 用药疗程 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productTreatment;

    /** 商品规格 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productStandard;

    /** 剂量说明 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productDosageDesc;

    /** 产品品名 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productName;

    /** 剂量单位 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productUnit;

    /** 用药方法 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productUsageMethod;

    /** 整包单位 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productPackageUnit;

    /** 商品名称（用于销售显示） */
    @QueryFieldAnnotation(aliasName = "product")
    private String productGoodsName;

    /** 通用名称 */
    @QueryFieldAnnotation(aliasName = "product")
    private String productCommonName;

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getProductDosage() {
        return productDosage;
    }

    public void setProductDosage(BigDecimal productDosage) {
        this.productDosage = productDosage;
    }

    public String getProductFrequency() {
        return productFrequency;
    }

    public void setProductFrequency(String productFrequency) {
        this.productFrequency = productFrequency;
    }

    public String getProductDayFrequency() {
        return productDayFrequency;
    }

    public void setProductDayFrequency(String productDayFrequency) {
        this.productDayFrequency = productDayFrequency;
    }

    public String getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(String productUsage) {
        this.productUsage = productUsage;
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductTreatment() {
        return productTreatment;
    }

    public void setProductTreatment(String productTreatment) {
        this.productTreatment = productTreatment;
    }

    public String getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }

    public String getProductDosageDesc() {
        return productDosageDesc;
    }

    public void setProductDosageDesc(String productDosageDesc) {
        this.productDosageDesc = productDosageDesc;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductUsageMethod() {
        return productUsageMethod;
    }

    public void setProductUsageMethod(String productUsageMethod) {
        this.productUsageMethod = productUsageMethod;
    }

    public String getProductPackageUnit() {
        return productPackageUnit;
    }

    public void setProductPackageUnit(String productPackageUnit) {
        this.productPackageUnit = productPackageUnit;
    }

    public String getProductGoodsName() {
        return productGoodsName;
    }

    public void setProductGoodsName(String productGoodsName) {
        this.productGoodsName = productGoodsName;
    }

    public String getProductCommonName() {
        return productCommonName;
    }

    public void setProductCommonName(String productCommonName) {
        this.productCommonName = productCommonName;
    }

}
