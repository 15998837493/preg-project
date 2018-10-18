
package com.mnt.preg.platform.condition;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 检验报告单检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-7-2 zcq 初版
 */
public class DiagnosisHospitalInspectReportCondition implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 检验报告id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String reportId;

    /** 检验报告名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String reportName;

    /** 检验日期 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String reportDate;

    /** 问诊id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 患者主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custId;

    /**
     * 状态:
     * 1. 对医生可见,对助理不可见
     * 2. 对医生可见不可操作,对助理手可见
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer reportStatus;

    /** 标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 接诊日期 */
    private String diagnosisDate;

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

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

}
