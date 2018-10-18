
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 患者检验项目管理
 * 
 * @author mlq
 * 
 *         变更履历： 2018-06-21 mlq
 */
public class DiagnosisHospitalItemPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 客户主键 */
    @QueryFieldAnnotation
    private String custId;

    /** 接诊主键 */
    private String diagnosisId;

    /** 检验报告id */
    @QueryFieldAnnotation
    private String reportId;

    /** 收费项目id */
    @QueryFieldAnnotation
    private String inspectId;

    /** 检验项目id */
    @QueryFieldAnnotation
    private String itemId;

    /** 检验项目编码 */
    @QueryFieldAnnotation
    private String itemCode;

    /** 检验项目名称 */
    @QueryFieldAnnotation
    private String itemName;

    /** 检验项目结果 */
    @QueryFieldAnnotation
    private String itemValue;

    /** 检验项目单位 */
    @QueryFieldAnnotation
    private String itemUnit;

    /** 检验项目结论 */
    @QueryFieldAnnotation
    private String itemResult;

    /** 检验项目参考范围 */
    @QueryFieldAnnotation
    private String itemRefValue;

    /** 本次孕周数 */
    private String gestationalWeeks;

    /** 上次孕周（作废） */
    private String lastGestationalWeeks;

    /** 上次结果 */
    private String lastItemValue;

    /** 上结论 */
    private String lastItemResult;

    /** 检验日期 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportDate;

    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private Integer itemOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public String getLastGestationalWeeks() {
        return lastGestationalWeeks;
    }

    public void setLastGestationalWeeks(String lastGestationalWeeks) {
        this.lastGestationalWeeks = lastGestationalWeeks;
    }

    public String getLastItemValue() {
        return lastItemValue;
    }

    public void setLastItemValue(String lastItemValue) {
        this.lastItemValue = lastItemValue;
    }

    public String getLastItemResult() {
        return lastItemResult;
    }

    public void setLastItemResult(String lastItemResult) {
        this.lastItemResult = lastItemResult;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
