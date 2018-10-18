
package com.mnt.preg.platform.condition;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 历史检验项目查询检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-7-2 zcq 初版
 */
public class DiagnosisHospitalItemCondition implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 客户主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custId;

    /** 检验报告id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String reportId;

    /** 收费项目id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectId;

    /** 检验项目id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemId;

    /** 检验项目编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemCode;

    /** 检验项目名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemName;

    /** 检验项目排序 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    private String itemOrder;

    /** 接诊日期 */
    private String diagnosisDate;

    /** 标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 检验项目id集合 */
    @QueryConditionAnnotation(name = "itemId", symbol = SymbolConstants.IN)
    private List<String> itemIds;

    /** 接诊id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 接诊id集合 */
    @QueryConditionAnnotation(name = "diagnosisId", symbol = SymbolConstants.IN)
    private List<String> diagnosisIds;

    /** 患者id集合 */
    @QueryConditionAnnotation(name = "custId", symbol = SymbolConstants.IN)
    private List<String> custIds;

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

    public String getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<String> getDiagnosisIds() {
        return diagnosisIds;
    }

    public void setDiagnosisIds(List<String> diagnosisIds) {
        this.diagnosisIds = diagnosisIds;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public List<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public List<String> getCustIds() {
        return custIds;
    }

    public void setCustIds(List<String> custIds) {
        this.custIds = custIds;
    }

}
