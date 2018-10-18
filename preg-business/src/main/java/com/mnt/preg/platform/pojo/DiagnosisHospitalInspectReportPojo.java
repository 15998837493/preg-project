
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 患者报告单管理
 * 
 * @author mlq
 * 
 *         变更履历： 2018-06-21 mlq
 */
public class DiagnosisHospitalInspectReportPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 检验报告id */
    @QueryFieldAnnotation
    private String reportId;

    /** 检验报告名称 */
    @QueryFieldAnnotation
    private String reportName;

    /** 检验日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportDate;

    /** 孕周数 */
    @QueryFieldAnnotation
    private String gestationalWeeks;

    /** 问诊id */
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 患者id */
    @QueryFieldAnnotation
    private String custId;

    /**
     * 状态:
     * 1. 对医生可见,对助理不可见
     * 2. 对医生可见不可操作,对助理手可见
     */
    @QueryFieldAnnotation
    private Integer reportStatus;

    /** 收费项目集合 */
    private List<DiagnosisHospitalInspectPayPojo> inspectPayList;

    /** 检验项目集合 */
    private List<DiagnosisHospitalItemPojo> itemList;

    /** 接诊日期 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisDate;

    /** 接诊医生 */
    private String diagnosisDoctor;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public List<DiagnosisHospitalInspectPayPojo> getInspectPayList() {
        return inspectPayList;
    }

    public void setInspectPayList(List<DiagnosisHospitalInspectPayPojo> inspectPayList) {
        this.inspectPayList = inspectPayList;
    }

    public List<DiagnosisHospitalItemPojo> getItemList() {
        return itemList;
    }

    public void setItemList(List<DiagnosisHospitalItemPojo> itemList) {
        this.itemList = itemList;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDiagnosisDoctor() {
        return diagnosisDoctor;
    }

    public void setDiagnosisDoctor(String diagnosisDoctor) {
        this.diagnosisDoctor = diagnosisDoctor;
    }

}
