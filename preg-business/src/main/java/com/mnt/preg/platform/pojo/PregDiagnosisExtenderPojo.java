
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 诊疗一日饮食清单表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
public class PregDiagnosisExtenderPojo implements Serializable, Cloneable {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 商品序号①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳ */
    private String indexName;

    /** 商品代号 */
    @QueryFieldAnnotation
    private String productCode;

    /** 产品主键 */
    @QueryFieldAnnotation
    private String productId;

    /** 产品图片 */
    @QueryFieldAnnotation
    private String productPic;

    /** 产品名称 */
    @QueryFieldAnnotation
    private String productName;

    /** 产品单位 */
    @QueryFieldAnnotation
    private String productUnit;

    /** 单次剂量 */
    @QueryFieldAnnotation
    private BigDecimal productDosage;

    /** 频次 */
    @QueryFieldAnnotation
    private String productFrequency;

    /** 一天频次 */
    @QueryFieldAnnotation
    private String productDayFrequency;

    /** 疗程 */
    @QueryFieldAnnotation
    private String productTreatment;

    /** 剂量说明 */
    @QueryFieldAnnotation
    private String productUsage;

    /** 用法 */
    @QueryFieldAnnotation
    private String productUsageMethod;

    /** 用药量 */
    @QueryFieldAnnotation
    private BigDecimal productAmount;

    /** 剂量说明 */
    @QueryFieldAnnotation
    private String productDosageDesc;

    /** 所属门诊号 */
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 可用天数 */
    @QueryFieldAnnotation
    private String productDays;

    /** 整包单位 */
    @QueryFieldAnnotation
    private String productPackageUnit;

    /** 商品规格 */
    @QueryFieldAnnotation
    private String productStandard;

    /** 规律服用天数 */
    @QueryFieldAnnotation
    private String regularDays;

    /** 开始服用日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    /** 结束服用日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /** 服用规律等级 */
    @QueryFieldAnnotation
    private String regularity;

    /** 孕期 */
    @QueryFieldAnnotation
    private String pregnancy;

    /** 服用周期 */
    @QueryFieldAnnotation
    private String takingCycle;

    /** 服用周期开始 */
    private String cycleStart;

    /** 服用周期结束 */
    private String cycleEnd;

    public String getRegularDays() {
        return regularDays;
    }

    public void setRegularDays(String regularDays) {
        this.regularDays = regularDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getProductTreatment() {
        return productTreatment;
    }

    public void setProductTreatment(String productTreatment) {
        this.productTreatment = productTreatment;
    }

    public String getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(String productUsage) {
        this.productUsage = productUsage;
    }

    public String getProductUsageMethod() {
        return productUsageMethod;
    }

    public void setProductUsageMethod(String productUsageMethod) {
        this.productUsageMethod = productUsageMethod;
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductDosageDesc() {
        return productDosageDesc;
    }

    public void setProductDosageDesc(String productDosageDesc) {
        this.productDosageDesc = productDosageDesc;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDays() {
        return productDays;
    }

    public void setProductDays(String productDays) {
        this.productDays = productDays;
    }

    public String getProductPackageUnit() {
        return productPackageUnit;
    }

    public void setProductPackageUnit(String productPackageUnit) {
        this.productPackageUnit = productPackageUnit;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getRegularity() {
        return regularity;
    }

    public void setRegularity(String regularity) {
        this.regularity = regularity;
    }

    public String getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(String pregnancy) {
        this.pregnancy = pregnancy;
    }

    public String getTakingCycle() {
        return takingCycle;
    }

    public void setTakingCycle(String takingCycle) {
        this.takingCycle = takingCycle;
    }

    public String getCycleStart() {
        return cycleStart;
    }

    public void setCycleStart(String cycleStart) {
        this.cycleStart = cycleStart;
    }

    public String getCycleEnd() {
        return cycleEnd;
    }

    public void setCycleEnd(String cycleEnd) {
        this.cycleEnd = cycleEnd;
    }

    @Override
    public Object clone() {
        PregDiagnosisExtenderPojo stu = null;
        try {
            stu = (PregDiagnosisExtenderPojo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }

}
