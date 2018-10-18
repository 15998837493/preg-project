
package com.mnt.preg.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 营养处方
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-2 zcq 初版
 */
@Entity
@Table(name = "user_diagnosis_prescription")
public class DiagnosisPrescription extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    private String id;

    /** 产品主键 */
    @UpdateAnnotation
    private String productId;

    /** 产品图片 */
    @UpdateAnnotation
    private String productPic;

    /** 商品代号 */
    @UpdateAnnotation
    private String productCode;

    /** 产品名称 */
    @UpdateAnnotation
    private String productName;

    /** 产品单位 */
    @UpdateAnnotation
    private String productUnit;

    /** 单次剂量 */
    @UpdateAnnotation
    private BigDecimal productDosage;

    /** 频次 */
    @UpdateAnnotation
    private String productFrequency;

    /** 一天频次 */
    @UpdateAnnotation
    private String productDayFrequency;

    /** 疗程 */
    @UpdateAnnotation
    private String productTreatment;

    /** 剂量说明 */
    @UpdateAnnotation
    private String productUsage;

    /** 用法 */
    @UpdateAnnotation
    private String productUsageMethod;

    /** 用药量 */
    @UpdateAnnotation
    private BigDecimal productAmount;

    /** 剂量说明 */
    @UpdateAnnotation
    private String productDosageDesc;

    /** 所属门诊号 */
    @UpdateAnnotation
    private String diagnosisId;

    /** 整包单位 */
    @UpdateAnnotation
    private String productPackageUnit;

    /** 商品规格 */
    @UpdateAnnotation
    private String productStandard;

    /** 可用天数 */
    @UpdateAnnotation
    private String productDays;

    /** 规律服用天数 */
    @UpdateAnnotation
    private String regularDays;

    /** 开始服用日期 */
    @UpdateAnnotation
    private Date startDate;

    /** 结束服用日期 */
    @UpdateAnnotation
    private Date endDate;

    /** 服用状态 1=新增，2=继服，3=停用 */
    @UpdateAnnotation
    private Integer status;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "product_unit")
    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    @Column(name = "product_dosage")
    public BigDecimal getProductDosage() {
        return productDosage;
    }

    public void setProductDosage(BigDecimal productDosage) {
        this.productDosage = productDosage;
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

    @Column(name = "product_treatment")
    public String getProductTreatment() {
        return productTreatment;
    }

    public void setProductTreatment(String productTreatment) {
        this.productTreatment = productTreatment;
    }

    @Column(name = "product_usage")
    public String getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(String productUsage) {
        this.productUsage = productUsage;
    }

    @Column(name = "product_usage_method")
    public String getProductUsageMethod() {
        return productUsageMethod;
    }

    public void setProductUsageMethod(String productUsageMethod) {
        this.productUsageMethod = productUsageMethod;
    }

    @Column(name = "product_amount")
    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    @Column(name = "product_dosage_desc")
    public String getProductDosageDesc() {
        return productDosageDesc;
    }

    public void setProductDosageDesc(String productDosageDesc) {
        this.productDosageDesc = productDosageDesc;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "product_code")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "product_package_unit")
    public String getProductPackageUnit() {
        return productPackageUnit;
    }

    public void setProductPackageUnit(String productPackageUnit) {
        this.productPackageUnit = productPackageUnit;
    }

    @Column(name = "product_standard")
    public String getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }

    @Column(name = "product_days")
    public String getProductDays() {
        return productDays;
    }

    public void setProductDays(String productDays) {
        this.productDays = productDays;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "product_pic")
    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    @Column(name = "regular_days")
    public String getRegularDays() {
        return regularDays;
    }

    public void setRegularDays(String regularDays) {
        this.regularDays = regularDays;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
