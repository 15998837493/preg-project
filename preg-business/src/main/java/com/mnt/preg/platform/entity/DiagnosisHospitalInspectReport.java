
package com.mnt.preg.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 患者报告单管理
 * 
 * @author mlq
 * 
 *         变更履历： 2018-06-21 mlq
 */
@Entity
@Table(name = "user_diagnosis_hospital_inspect_report")
public class DiagnosisHospitalInspectReport extends MappedEntity {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 检验报告id */
    private String reportId;

    /** 检验报告名称 */
    @UpdateAnnotation
    private String reportName;

    /** 检验日期 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportDate;

    /** 孕周数 */
    @UpdateAnnotation
    private String gestationalWeeks;

    /** 问诊id */
    private String diagnosisId;

    /** 患者id */
    private String custId;

    /**
     * 状态:
     * 1. 对医生可见,对助理不可见
     * 2. 对医生可见不可操作,对助理手可见
     */
    @UpdateAnnotation
    private Integer reportStatus;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "report_id")
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Column(name = "report_name")
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @Column(name = "report_date")
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Column(name = "gestational_weeks")
    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "report_status")
    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

}
