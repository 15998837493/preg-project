
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;

/**
 * 接诊信息POJO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-26 zcq 初版
 */
public class PregDiagnosisPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 门诊号 */
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 门诊类型：1=首诊；2=复诊；3=随诊 */
    @QueryFieldAnnotation
    private Integer diagnosisType;

    /** 接诊日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisDate;

    /** ID */
    @QueryFieldAnnotation
    private String diagnosisCustPatientId;

    /** 病案号 */
    @QueryFieldAnnotation
    private String diagnosisCustSikeId;

    /** 客户ID */
    @QueryFieldAnnotation
    private String diagnosisCustomer;

    /** 客户姓名 */
    @QueryFieldAnnotation
    private String diagnosisCustName;

    /** 客户年龄 */
    @QueryFieldAnnotation
    private Integer diagnosisCustAge;

    /** 客户身高 */
    @QueryFieldAnnotation
    private BigDecimal diagnosisCustHeight;

    /** 客户体重 */
    @QueryFieldAnnotation
    private BigDecimal diagnosisCustWeight;

    /** 客户腰围 */
    @QueryFieldAnnotation
    private BigDecimal diagnosisCustWaistline;

    /** 客户体力水平 */
    @QueryFieldAnnotation
    private String diagnosisCustPlevel;

    /** 客户所属人群 */
    @QueryFieldAnnotation
    private String diagnosisCustStages;

    /** 收缩压 */
    private BigDecimal diagnosisSystolic;

    /** 舒张压 */
    private BigDecimal diagnosisDiastolic;

    /** 孕周 */
    @QueryFieldAnnotation
    private String diagnosisGestationalWeeks;

    /** 孕期阶段 */
    @QueryFieldAnnotation
    private String diagnosisPregnantStage;

    /** 干预方案PDF路径 */
    @QueryFieldAnnotation
    private String diagnosisPlanPdf;

    /** 就诊状态 */
    @QueryFieldAnnotation
    private Integer diagnosisStatus;

    /** 助理端就诊状态：1=等待接诊，2=接诊，3=完成 */
    @QueryFieldAnnotation
    private Integer diagnosisAssistantStatus;

    /** 患者来源 */
    @QueryFieldAnnotation
    private String diagnosisOrg;

    /** 接诊医生 */
    @QueryFieldAnnotation
    private String diagnosisUser;

    /** 接诊医生 */
    @QueryFieldAnnotation
    private String diagnosisUserName;

    /** 转诊医生 */
    @QueryFieldAnnotation
    private String diagnosisReferralDoctor;

    /** 转诊医生 */
    @QueryFieldAnnotation
    private String diagnosisReferralDoctorName;

    /** 检测项目结论 */
    @QueryFieldAnnotation
    private String diagnosisInspectResult;

    /** 营养处方备注 */
    @QueryFieldAnnotation
    private String diagnosisExtenderDesc;

    /** 膳食处方备注 */
    @QueryFieldAnnotation
    private String diagnosisDietRemark;

    /** 就诊备注 */
    @QueryFieldAnnotation
    private String diagnosisRemark;

    /** 建议整体孕期体重适宜增长范围 */
    @QueryFieldAnnotation
    private String diagnosisRiseYunqi;

    /** 建议目前体重增长至 */
    @QueryFieldAnnotation
    private String diagnosisRisePresent;

    /** 建议每周体重适宜增长范围 */
    @QueryFieldAnnotation
    private String diagnosisRiseWeek;

    /** 建档ID */
    @QueryFieldAnnotation
    private String archivesId;

    /** 创建时间(登记时间) */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    private Date createTime;

    /** 修改时间(接诊时间) */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    private Date updateTime;

    /** 创建者ID */
    @QueryFieldAnnotation
    private String createUserId;

    /** 修改者ID */
    @QueryFieldAnnotation
    private String updateUserId;

    /** 机构号 */
    @QueryFieldAnnotation
    private String createInsId;

    /** 妊娠风险级别 */
    @QueryFieldAnnotation
    private String gestationLevel;

    // ------------------------------------------------------------------------------------

    /** 孕前体重 */
    private BigDecimal diagnosisArchiveWeight;

    /** 诊疗次数 */
    private Object diagnosisCount;

    /** 末次月经 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisLmp;

    /** 预产期 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisDueDate;

    /** 人体成分分析结果 */
    private ExamResultRecordPojo inbodyResult;

    /** 膳食及生活方式分析结果 */
    private ExamResultRecordPojo dietLifeResult;

    /** 膳食回顾结果 */
    private ExamResultRecordPojo dietReviewResult;

    /** 补充剂结果 */
    private ExamResultRecordPojo extenderResult;

    /** 诊断项目名称 */
    private String diagnosisDiseases;

    /** 诊断项目编码 */
    private String diagnosisDiseaseCodes;

    /** 机构号 */
    private String insId;

    private String statusSync;

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDiagnosisCustPatientId() {
        return diagnosisCustPatientId;
    }

    public void setDiagnosisCustPatientId(String diagnosisCustPatientId) {
        this.diagnosisCustPatientId = diagnosisCustPatientId;
    }

    public String getDiagnosisCustSikeId() {
        return diagnosisCustSikeId;
    }

    public void setDiagnosisCustSikeId(String diagnosisCustSikeId) {
        this.diagnosisCustSikeId = diagnosisCustSikeId;
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

    public String getDiagnosisCustPlevel() {
        return diagnosisCustPlevel;
    }

    public void setDiagnosisCustPlevel(String diagnosisCustPlevel) {
        this.diagnosisCustPlevel = diagnosisCustPlevel;
    }

    public String getDiagnosisCustStages() {
        return diagnosisCustStages;
    }

    public void setDiagnosisCustStages(String diagnosisCustStages) {
        this.diagnosisCustStages = diagnosisCustStages;
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

    public String getDiagnosisPlanPdf() {
        return diagnosisPlanPdf;
    }

    public void setDiagnosisPlanPdf(String diagnosisPlanPdf) {
        this.diagnosisPlanPdf = diagnosisPlanPdf;
    }

    public Integer getDiagnosisStatus() {
        return diagnosisStatus;
    }

    public void setDiagnosisStatus(Integer diagnosisStatus) {
        this.diagnosisStatus = diagnosisStatus;
    }

    public String getDiagnosisOrg() {
        return diagnosisOrg;
    }

    public void setDiagnosisOrg(String diagnosisOrg) {
        this.diagnosisOrg = diagnosisOrg;
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

    public String getDiagnosisReferralDoctor() {
        return diagnosisReferralDoctor;
    }

    public void setDiagnosisReferralDoctor(String diagnosisReferralDoctor) {
        this.diagnosisReferralDoctor = diagnosisReferralDoctor;
    }

    public String getDiagnosisReferralDoctorName() {
        return diagnosisReferralDoctorName;
    }

    public void setDiagnosisReferralDoctorName(String diagnosisReferralDoctorName) {
        this.diagnosisReferralDoctorName = diagnosisReferralDoctorName;
    }

    public String getDiagnosisInspectResult() {
        return diagnosisInspectResult;
    }

    public void setDiagnosisInspectResult(String diagnosisInspectResult) {
        this.diagnosisInspectResult = diagnosisInspectResult;
    }

    public String getDiagnosisExtenderDesc() {
        return diagnosisExtenderDesc;
    }

    public void setDiagnosisExtenderDesc(String diagnosisExtenderDesc) {
        this.diagnosisExtenderDesc = diagnosisExtenderDesc;
    }

    public String getDiagnosisDietRemark() {
        return diagnosisDietRemark;
    }

    public void setDiagnosisDietRemark(String diagnosisDietRemark) {
        this.diagnosisDietRemark = diagnosisDietRemark;
    }

    public String getDiagnosisRemark() {
        return diagnosisRemark;
    }

    public void setDiagnosisRemark(String diagnosisRemark) {
        this.diagnosisRemark = diagnosisRemark;
    }

    public String getDiagnosisRiseYunqi() {
        return diagnosisRiseYunqi;
    }

    public void setDiagnosisRiseYunqi(String diagnosisRiseYunqi) {
        this.diagnosisRiseYunqi = diagnosisRiseYunqi;
    }

    public String getDiagnosisRisePresent() {
        return diagnosisRisePresent;
    }

    public void setDiagnosisRisePresent(String diagnosisRisePresent) {
        this.diagnosisRisePresent = diagnosisRisePresent;
    }

    public String getDiagnosisRiseWeek() {
        return diagnosisRiseWeek;
    }

    public void setDiagnosisRiseWeek(String diagnosisRiseWeek) {
        this.diagnosisRiseWeek = diagnosisRiseWeek;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getDiagnosisArchiveWeight() {
        return diagnosisArchiveWeight;
    }

    public void setDiagnosisArchiveWeight(BigDecimal diagnosisArchiveWeight) {
        this.diagnosisArchiveWeight = diagnosisArchiveWeight;
    }

    public Object getDiagnosisCount() {
        return diagnosisCount;
    }

    public void setDiagnosisCount(Object diagnosisCount) {
        this.diagnosisCount = diagnosisCount;
    }

    public Date getDiagnosisLmp() {
        return diagnosisLmp;
    }

    public void setDiagnosisLmp(Date diagnosisLmp) {
        this.diagnosisLmp = diagnosisLmp;
    }

    public Date getDiagnosisDueDate() {
        return diagnosisDueDate;
    }

    public void setDiagnosisDueDate(Date diagnosisDueDate) {
        this.diagnosisDueDate = diagnosisDueDate;
    }

    public ExamResultRecordPojo getInbodyResult() {
        return inbodyResult;
    }

    public void setInbodyResult(ExamResultRecordPojo inbodyResult) {
        this.inbodyResult = inbodyResult;
    }

    public ExamResultRecordPojo getDietLifeResult() {
        return dietLifeResult;
    }

    public void setDietLifeResult(ExamResultRecordPojo dietLifeResult) {
        this.dietLifeResult = dietLifeResult;
    }

    public ExamResultRecordPojo getDietReviewResult() {
        return dietReviewResult;
    }

    public void setDietReviewResult(ExamResultRecordPojo dietReviewResult) {
        this.dietReviewResult = dietReviewResult;
    }

    public ExamResultRecordPojo getExtenderResult() {
        return extenderResult;
    }

    public void setExtenderResult(ExamResultRecordPojo extenderResult) {
        this.extenderResult = extenderResult;
    }

    public String getDiagnosisDiseases() {
        return diagnosisDiseases;
    }

    public void setDiagnosisDiseases(String diagnosisDiseases) {
        this.diagnosisDiseases = diagnosisDiseases;
    }

    public String getDiagnosisDiseaseCodes() {
        return diagnosisDiseaseCodes;
    }

    public void setDiagnosisDiseaseCodes(String diagnosisDiseaseCodes) {
        this.diagnosisDiseaseCodes = diagnosisDiseaseCodes;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(String statusSync) {
        this.statusSync = statusSync;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

    public Integer getDiagnosisAssistantStatus() {
        return diagnosisAssistantStatus;
    }

    public void setDiagnosisAssistantStatus(Integer diagnosisAssistantStatus) {
        this.diagnosisAssistantStatus = diagnosisAssistantStatus;
    }

    public String getGestationLevel() {
        return gestationLevel;
    }

    public void setGestationLevel(String gestationLevel) {
        this.gestationLevel = gestationLevel;
    }

}
