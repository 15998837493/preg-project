/*
 * MasPrescriptionCondition.java    1.0  2017-7-18
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.condition.items;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 诊断营养处方检索条件
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-18 scd 初版
 */
public class MasPrescriptionCondition implements Serializable {

    private static final long serialVersionUID = 6422797861006437555L;

    /** 编号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String prescriptionId;

    /** 诊断主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseId;

    /** 产品主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productId;

    /** 产品编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productCode;

    /** 单次剂量 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal productDosage;

    /** 用药频次 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productFrequency;

    /** 每日用药频率 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productDayFrequency;

    /** 剂量 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productUsage;

    /** 数量 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal productAmount;

    /** 剂量说明 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productDosageDesc;

    /** 删除标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 接诊id集合 */
    @QueryConditionAnnotation(name = "diagnosisId", symbol = SymbolConstants.EQ)
    private List<String> diagnosisIds;

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

    public String getProductDosageDesc() {
        return productDosageDesc;
    }

    public void setProductDosageDesc(String productDosageDesc) {
        this.productDosageDesc = productDosageDesc;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public BigDecimal getProductDosage() {
        return productDosage;
    }

    public void setProductDosage(BigDecimal productDosage) {
        this.productDosage = productDosage;
    }

    public List<String> getDiagnosisIds() {
        return diagnosisIds;
    }

    public void setDiagnosisIds(List<String> diagnosisIds) {
        this.diagnosisIds = diagnosisIds;
    }

}
