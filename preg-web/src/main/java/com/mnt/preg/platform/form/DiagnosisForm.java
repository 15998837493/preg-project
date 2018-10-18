
package com.mnt.preg.platform.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 代码表表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-11 zcq 初版
 */
public class DiagnosisForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 门诊号 */
    private String diagnosisId;

    /** 日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisDate;

    /** 预约日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisDateOld;

    /** 客户编码 */
    private String diagnosisCustomer;

    /** 客户姓名 */
    private String diagnosisCustName;

    /** 客户性别 */
    private String diagnosisCustSex;

    /** 客户年龄 */
    private Integer diagnosisCustAge;

    /** 客户身高 */
    private BigDecimal diagnosisCustHeight;

    /** 一周前体重 */
    private BigDecimal diagnosisCustWeightPreweek;

    /** 客户体重 */
    private BigDecimal diagnosisCustWeight;

    /** 客户腰围 */
    private BigDecimal diagnosisCustWaistline;

    /** 孕周 */
    private String diagnosisGestationalWeeks;

    /** 孕期阶段 */
    private String diagnosisPregnantStage;

    /** 客户体力水平 */
    private String diagnosisCustPlevel;

    /** 医生 */
    private String diagnosisUser;

    /** 医生 */
    private String diagnosisUserName;

    /** 干预方案PDF路径 */
    private String diagnosisPlanPdf;

    /** 就诊来源 */
    private String diagnosisOrg;

    /** 状态 */
    private Integer diagnosisStatus;

    /** 备注 */
    private String diagnosisRemark;

    /** 病案号 */
    private String diagnosisCustSikeId;

    /** ID */
    private String diagnosisCustPatientId;

    /** 调整后的孕周数 */
    private String gestationalWeeks;

    /** 调整后的孕周天数 */
    private String gestationalDays;

    /** 调整后的预产期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dueDateNew;

    /** 调整的理由 */
    private String adjustReason;

    /** 收缩压 */
    private BigDecimal diagnosisSystolic;

    /** 舒张压 */
    private BigDecimal diagnosisDiastolic;

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public Date getDiagnosisDateOld() {
        return diagnosisDateOld;
    }

    public void setDiagnosisDateOld(Date diagnosisDateOld) {
        this.diagnosisDateOld = diagnosisDateOld;
    }

    public String getDiagnosisCustomer() {
        return diagnosisCustomer;
    }

    public void setDiagnosisCustomer(String diagnosisCustomer) {
        this.diagnosisCustomer = diagnosisCustomer;
    }

    public String getDiagnosisCustName() {
        return diagnosisCustName;
    }

    public void setDiagnosisCustName(String diagnosisCustName) {
        this.diagnosisCustName = diagnosisCustName;
    }

    public String getDiagnosisCustSex() {
        return diagnosisCustSex;
    }

    public void setDiagnosisCustSex(String diagnosisCustSex) {
        this.diagnosisCustSex = diagnosisCustSex;
    }

    public Integer getDiagnosisCustAge() {
        return diagnosisCustAge;
    }

    public void setDiagnosisCustAge(Integer diagnosisCustAge) {
        this.diagnosisCustAge = diagnosisCustAge;
    }

    public BigDecimal getDiagnosisCustHeight() {
        return diagnosisCustHeight;
    }

    public void setDiagnosisCustHeight(BigDecimal diagnosisCustHeight) {
        this.diagnosisCustHeight = diagnosisCustHeight;
    }

    public BigDecimal getDiagnosisCustWeightPreweek() {
        return diagnosisCustWeightPreweek;
    }

    public void setDiagnosisCustWeightPreweek(BigDecimal diagnosisCustWeightPreweek) {
        this.diagnosisCustWeightPreweek = diagnosisCustWeightPreweek;
    }

    public BigDecimal getDiagnosisCustWeight() {
        return diagnosisCustWeight;
    }

    public void setDiagnosisCustWeight(BigDecimal diagnosisCustWeight) {
        this.diagnosisCustWeight = diagnosisCustWeight;
    }

    public BigDecimal getDiagnosisCustWaistline() {
        return diagnosisCustWaistline;
    }

    public void setDiagnosisCustWaistline(BigDecimal diagnosisCustWaistline) {
        this.diagnosisCustWaistline = diagnosisCustWaistline;
    }

    public String getDiagnosisGestationalWeeks() {
        return diagnosisGestationalWeeks;
    }

    public void setDiagnosisGestationalWeeks(String diagnosisGestationalWeeks) {
        this.diagnosisGestationalWeeks = diagnosisGestationalWeeks;
    }

    public String getDiagnosisPregnantStage() {
        return diagnosisPregnantStage;
    }

    public void setDiagnosisPregnantStage(String diagnosisPregnantStage) {
        this.diagnosisPregnantStage = diagnosisPregnantStage;
    }

    public String getDiagnosisCustPlevel() {
        return diagnosisCustPlevel;
    }

    public void setDiagnosisCustPlevel(String diagnosisCustPlevel) {
        this.diagnosisCustPlevel = diagnosisCustPlevel;
    }

    public String getDiagnosisUser() {
        return diagnosisUser;
    }

    public void setDiagnosisUser(String diagnosisUser) {
        this.diagnosisUser = diagnosisUser;
    }

    public String getDiagnosisUserName() {
        return diagnosisUserName;
    }

    public void setDiagnosisUserName(String diagnosisUserName) {
        this.diagnosisUserName = diagnosisUserName;
    }

    public String getDiagnosisPlanPdf() {
        return diagnosisPlanPdf;
    }

    public void setDiagnosisPlanPdf(String diagnosisPlanPdf) {
        this.diagnosisPlanPdf = diagnosisPlanPdf;
    }

    public String getDiagnosisOrg() {
        return diagnosisOrg;
    }

    public void setDiagnosisOrg(String diagnosisOrg) {
        this.diagnosisOrg = diagnosisOrg;
    }

    public Integer getDiagnosisStatus() {
        return diagnosisStatus;
    }

    public void setDiagnosisStatus(Integer diagnosisStatus) {
        this.diagnosisStatus = diagnosisStatus;
    }

    public String getDiagnosisRemark() {
        return diagnosisRemark;
    }

    public void setDiagnosisRemark(String diagnosisRemark) {
        this.diagnosisRemark = diagnosisRemark;
    }

    public String getDiagnosisCustSikeId() {
        return diagnosisCustSikeId;
    }

    public void setDiagnosisCustSikeId(String diagnosisCustSikeId) {
        this.diagnosisCustSikeId = diagnosisCustSikeId;
    }

    public String getDiagnosisCustPatientId() {
        return diagnosisCustPatientId;
    }

    public void setDiagnosisCustPatientId(String diagnosisCustPatientId) {
        this.diagnosisCustPatientId = diagnosisCustPatientId;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public String getGestationalDays() {
        return gestationalDays;
    }

    public void setGestationalDays(String gestationalDays) {
        this.gestationalDays = gestationalDays;
    }

    public Date getDueDateNew() {
        return dueDateNew;
    }

    public void setDueDateNew(Date dueDateNew) {
        this.dueDateNew = dueDateNew;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    public BigDecimal getDiagnosisSystolic() {
        return diagnosisSystolic;
    }

    public void setDiagnosisSystolic(BigDecimal diagnosisSystolic) {
        this.diagnosisSystolic = diagnosisSystolic;
    }

    public BigDecimal getDiagnosisDiastolic() {
        return diagnosisDiastolic;
    }

    public void setDiagnosisDiastolic(BigDecimal diagnosisDiastolic) {
        this.diagnosisDiastolic = diagnosisDiastolic;
    }

}
