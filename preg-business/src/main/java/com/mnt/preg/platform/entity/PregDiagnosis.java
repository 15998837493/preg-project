
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
import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 诊疗登记
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-14 zcq 初版
 */
@Entity
@Table(name = "user_diagnosis")
public class PregDiagnosis extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 门诊号 */
    private String diagnosisId;

    /** 门诊类型：1=首诊；2=复诊；3=随诊 */
    @UpdateAnnotation
    private Integer diagnosisType;

    /** 接诊日期 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date diagnosisDate;

    /** ID */
    @UpdateAnnotation
    private String diagnosisCustPatientId;

    /** 病案号 */
    @UpdateAnnotation
    private String diagnosisCustSikeId;

    /** 客户ID */
    private String diagnosisCustomer;

    /** 客户姓名 */
    @UpdateAnnotation
    private String diagnosisCustName;

    /** 客户年龄 */
    @UpdateAnnotation
    private Integer diagnosisCustAge;

    /** 客户身高 */
    @UpdateAnnotation
    private BigDecimal diagnosisCustHeight;

    /** 客户体重 */
    @UpdateAnnotation
    private BigDecimal diagnosisCustWeight;

    /** 客户腰围 */
    @UpdateAnnotation
    private BigDecimal diagnosisCustWaistline;

    /** 客户体力水平 */
    @UpdateAnnotation
    private String diagnosisCustPlevel;

    /** 客户所属人群 */
    @UpdateAnnotation
    private String diagnosisCustStages;

    /** 孕周 */
    @UpdateAnnotation
    private String diagnosisGestationalWeeks;

    /** 孕期阶段 */
    @UpdateAnnotation
    private String diagnosisPregnantStage;

    /** 干预方案PDF路径 */
    @UpdateAnnotation
    private String diagnosisPlanPdf;

    /** 就诊状态：1=未接诊，2=已接诊 */
    @UpdateAnnotation
    private Integer diagnosisStatus;

    /** 助理端就诊状态：1=等待接诊，2=接诊，3=完成 */
    @UpdateAnnotation
    private Integer diagnosisAssistantStatus;

    /** 患者来源 */
    @UpdateAnnotation
    private String diagnosisOrg;

    /** 接诊医生 */
    @UpdateAnnotation
    private String diagnosisUser;

    /** 接诊医生 */
    @UpdateAnnotation
    private String diagnosisUserName;

    /** 转诊医生 */
    @UpdateAnnotation
    private String diagnosisReferralDoctor;

    /** 转诊医生 */
    @UpdateAnnotation
    private String diagnosisReferralDoctorName;

    /** 检测项目结论 */
    @UpdateAnnotation
    private String diagnosisInspectResult;

    /** 营养处方备注 */
    @UpdateAnnotation
    private String diagnosisExtenderDesc;

    /** 膳食处方备注 */
    @UpdateAnnotation
    private String diagnosisDietRemark;

    /** 就诊备注 */
    @UpdateAnnotation
    private String diagnosisRemark;

    /** 建议整体孕期体重适宜增长范围 */
    @UpdateAnnotation
    private String diagnosisRiseYunqi;

    /** 建议目前体重增长至 */
    @UpdateAnnotation
    private String diagnosisRisePresent;

    /** 建议每周体重适宜增长范围 */
    @UpdateAnnotation
    private String diagnosisRiseWeek;

    /** 建档ID */
    @UpdateAnnotation
    private String archivesId;

    /** 妊娠风险级别 */
    @UpdateAnnotation
    private String gestationLevel;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "diagnosis_date")
    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    @Column(name = "diagnosis_customer")
    public String getDiagnosisCustomer() {
        return diagnosisCustomer;
    }

    public void setDiagnosisCustomer(String diagnosisCustomer) {
        this.diagnosisCustomer = diagnosisCustomer;
    }

    @Column(name = "diagnosis_cust_name")
    public String getDiagnosisCustName() {
        return diagnosisCustName;
    }

    public void setDiagnosisCustName(String diagnosisCustName) {
        this.diagnosisCustName = diagnosisCustName;
    }

    @Column(name = "diagnosis_cust_age")
    public Integer getDiagnosisCustAge() {
        return diagnosisCustAge;
    }

    public void setDiagnosisCustAge(Integer diagnosisCustAge) {
        this.diagnosisCustAge = diagnosisCustAge;
    }

    @Column(name = "diagnosis_cust_height")
    public BigDecimal getDiagnosisCustHeight() {
        return diagnosisCustHeight;
    }

    public void setDiagnosisCustHeight(BigDecimal diagnosisCustHeight) {
        this.diagnosisCustHeight = diagnosisCustHeight;
    }

    @Column(name = "diagnosis_cust_weight")
    public BigDecimal getDiagnosisCustWeight() {
        return diagnosisCustWeight;
    }

    public void setDiagnosisCustWeight(BigDecimal diagnosisCustWeight) {
        this.diagnosisCustWeight = diagnosisCustWeight;
    }

    @Column(name = "diagnosis_cust_waistline")
    public BigDecimal getDiagnosisCustWaistline() {
        return diagnosisCustWaistline;
    }

    public void setDiagnosisCustWaistline(BigDecimal diagnosisCustWaistline) {
        this.diagnosisCustWaistline = diagnosisCustWaistline;
    }

    @Column(name = "diagnosis_cust_stages")
    public String getDiagnosisCustStages() {
        return diagnosisCustStages;
    }

    public void setDiagnosisCustStages(String diagnosisCustStages) {
        this.diagnosisCustStages = diagnosisCustStages;
    }

    @Column(name = "diagnosis_gestational_weeks")
    public String getDiagnosisGestationalWeeks() {
        return diagnosisGestationalWeeks;
    }

    public void setDiagnosisGestationalWeeks(String diagnosisGestationalWeeks) {
        this.diagnosisGestationalWeeks = diagnosisGestationalWeeks;
    }

    @Column(name = "diagnosis_pregnant_stage")
    public String getDiagnosisPregnantStage() {
        return diagnosisPregnantStage;
    }

    public void setDiagnosisPregnantStage(String diagnosisPregnantStage) {
        this.diagnosisPregnantStage = diagnosisPregnantStage;
    }

    @Column(name = "diagnosis_cust_plevel")
    public String getDiagnosisCustPlevel() {
        return diagnosisCustPlevel;
    }

    public void setDiagnosisCustPlevel(String diagnosisCustPlevel) {
        this.diagnosisCustPlevel = diagnosisCustPlevel;
    }

    @Column(name = "diagnosis_user")
    public String getDiagnosisUser() {
        return diagnosisUser;
    }

    public void setDiagnosisUser(String diagnosisUser) {
        this.diagnosisUser = diagnosisUser;
    }

    @Column(name = "diagnosis_user_name")
    public String getDiagnosisUserName() {
        return diagnosisUserName;
    }

    public void setDiagnosisUserName(String diagnosisUserName) {
        this.diagnosisUserName = diagnosisUserName;
    }

    @Column(name = "diagnosis_status")
    public Integer getDiagnosisStatus() {
        return diagnosisStatus;
    }

    public void setDiagnosisStatus(Integer diagnosisStatus) {
        this.diagnosisStatus = diagnosisStatus;
    }

    @Column(name = "diagnosis_assistant_status")
    public Integer getDiagnosisAssistantStatus() {
        return diagnosisAssistantStatus;
    }

    public void setDiagnosisAssistantStatus(Integer diagnosisAssistantStatus) {
        this.diagnosisAssistantStatus = diagnosisAssistantStatus;
    }

    @Column(name = "diagnosis_remark")
    public String getDiagnosisRemark() {
        return diagnosisRemark;
    }

    public void setDiagnosisRemark(String diagnosisRemark) {
        this.diagnosisRemark = diagnosisRemark;
    }

    @Column(name = "diagnosis_plan_pdf")
    public String getDiagnosisPlanPdf() {
        return diagnosisPlanPdf;
    }

    public void setDiagnosisPlanPdf(String diagnosisPlanPdf) {
        this.diagnosisPlanPdf = diagnosisPlanPdf;
    }

    @Column(name = "diagnosis_org")
    public String getDiagnosisOrg() {
        return diagnosisOrg;
    }

    public void setDiagnosisOrg(String diagnosisOrg) {
        this.diagnosisOrg = diagnosisOrg;
    }

    @Column(name = "diagnosis_cust_sike_id")
    public String getDiagnosisCustSikeId() {
        return diagnosisCustSikeId;
    }

    public void setDiagnosisCustSikeId(String diagnosisCustSikeId) {
        this.diagnosisCustSikeId = diagnosisCustSikeId;
    }

    @Column(name = "diagnosis_cust_patient_id")
    public String getDiagnosisCustPatientId() {
        return diagnosisCustPatientId;
    }

    public void setDiagnosisCustPatientId(String diagnosisCustPatientId) {
        this.diagnosisCustPatientId = diagnosisCustPatientId;
    }

    @Column(name = "diagnosis_extender_desc")
    public String getDiagnosisExtenderDesc() {
        return diagnosisExtenderDesc;
    }

    public void setDiagnosisExtenderDesc(String diagnosisExtenderDesc) {
        this.diagnosisExtenderDesc = diagnosisExtenderDesc;
    }

    @Column(name = "diagnosis_diet_remark")
    public String getDiagnosisDietRemark() {
        return diagnosisDietRemark;
    }

    public void setDiagnosisDietRemark(String diagnosisDietRemark) {
        this.diagnosisDietRemark = diagnosisDietRemark;
    }

    @Column(name = "diagnosis_inspect_result")
    public String getDiagnosisInspectResult() {
        return diagnosisInspectResult;
    }

    public void setDiagnosisInspectResult(String diagnosisInspectResult) {
        this.diagnosisInspectResult = diagnosisInspectResult;
    }

    @Column(name = "diagnosis_rise_yunqi")
    public String getDiagnosisRiseYunqi() {
        return diagnosisRiseYunqi;
    }

    public void setDiagnosisRiseYunqi(String diagnosisRiseYunqi) {
        this.diagnosisRiseYunqi = diagnosisRiseYunqi;
    }

    @Column(name = "diagnosis_rise_present")
    public String getDiagnosisRisePresent() {
        return diagnosisRisePresent;
    }

    public void setDiagnosisRisePresent(String diagnosisRisePresent) {
        this.diagnosisRisePresent = diagnosisRisePresent;
    }

    @Column(name = "diagnosis_rise_week")
    public String getDiagnosisRiseWeek() {
        return diagnosisRiseWeek;
    }

    public void setDiagnosisRiseWeek(String diagnosisRiseWeek) {
        this.diagnosisRiseWeek = diagnosisRiseWeek;
    }

    @Column(name = "diagnosis_type")
    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    @Column(name = "diagnosis_referral_doctor")
    public String getDiagnosisReferralDoctor() {
        return diagnosisReferralDoctor;
    }

    public void setDiagnosisReferralDoctor(String diagnosisReferralDoctor) {
        this.diagnosisReferralDoctor = diagnosisReferralDoctor;
    }

    @Column(name = "diagnosis_referral_doctor_name")
    public String getDiagnosisReferralDoctorName() {
        return diagnosisReferralDoctorName;
    }

    public void setDiagnosisReferralDoctorName(String diagnosisReferralDoctorName) {
        this.diagnosisReferralDoctorName = diagnosisReferralDoctorName;
    }

    @Column(name = "archives_id")
    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    @Column(name = "gestation_level")
    public String getGestationLevel() {
        return gestationLevel;
    }

    public void setGestationLevel(String gestationLevel) {
        this.gestationLevel = gestationLevel;
    }

}
