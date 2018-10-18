
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 营养处方
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-21 zcq 初版
 */
public class DiagnosisPrescriptionPojo implements Serializable {

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

    /** 单次剂量 */
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

    /** 服用状态 */
    @QueryFieldAnnotation
    private Integer status;

    /** 数量最大数（统计） */
    private Object productCount = "";

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

    public String getRegularDays() {
        return regularDays;
    }

    public void setRegularDays(String regularDays) {
        this.regularDays = regularDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getProductCount() {
        return productCount;
    }

    public void setProductCount(Object productCount) {
        this.productCount = productCount;
    }

}
