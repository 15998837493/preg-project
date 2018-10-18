
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 患者检验项目管理
 * 
 * @author mlq
 * 
 *         变更履历： 2018-06-21 mlq
 */
@Entity
@Table(name = "user_diagnosis_hospital_item")
public class DiagnosisHospitalItem extends MappedEntity {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    private String id;

    /** 客户主键 */
    private String custId;

    /** 接诊id */
    private String diagnosisId;

    /** 检验报告id */
    private String reportId;

    /** 收费项目id */
    private String inspectId;

    /** 检验项目id */
    private String itemId;

    /** 检验项目编码 */
    private String itemCode;

    /** 检验项目名称 */
    private String itemName;

    /** 检验项目结果 */
    @UpdateAnnotation
    private String itemValue;

    /** 检验项目单位 */
    private String itemUnit;

    /** 检验项目结论 */
    @UpdateAnnotation
    private String itemResult;

    /** 检验项目参考范围 */
    private String itemRefValue;

    @UpdateAnnotation
    private Integer itemOrder;

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

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "report_id")
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Column(name = "inspect_id")
    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    @Column(name = "item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name = "item_value")
    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    @Column(name = "item_unit")
    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    @Column(name = "item_result")
    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    @Column(name = "item_ref_value")
    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    @Column(name = "item_order")
    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

}
