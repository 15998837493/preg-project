
package com.mnt.preg.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 孕期建档表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 gss 初版
 */
@Entity
@Table(name = "cus_pregnancy_archives")
public class PregArchives extends MappedEntity {

    private static final long serialVersionUID = 6374057429745991593L;

    /** 编码 */
    private String id;

    /** 客户 */
    @UpdateAnnotation
    private String custId;

    /** 客户年龄 */
    @UpdateAnnotation
    private Integer custAge;

    /** 末次月经 */
    @UpdateAnnotation
    private Date lmp;

    /** 孕前身高 */
    @UpdateAnnotation
    private BigDecimal height;

    /** 孕前体重 */
    @UpdateAnnotation
    private BigDecimal weight;

    /** 孕前腰围 */
    @UpdateAnnotation
    private BigDecimal waistline;

    /** 孕前BMI */
    @UpdateAnnotation
    private BigDecimal bmi;

    /** 预产期 */
    @UpdateAnnotation
    private Date pregnancyDueDate;

    /** 建档地点 */
    @UpdateAnnotation
    private String createLocale;

    /** 建档日期 */
    private Date createDate;

    /** 建档日期 */
    private Date createDatetime;

    /** 妊娠情况 */
    @UpdateAnnotation
    private String encyesisSituation;

    /** 妊娠胚胎数 */
    @UpdateAnnotation
    private String embryoNum;

    /** 孕前叶酸服用情况 */
    @UpdateAnnotation
    private String folicSituation;

    /** 初潮年龄 */
    @UpdateAnnotation
    private String menarcheAge;

    /** 孕早期存在情况 */
    @UpdateAnnotation
    private String firstTrimesterSituation;

    /** 平均每次月经持续天数 */
    @UpdateAnnotation
    private String mensesDays;

    /** 月经周期 */
    @UpdateAnnotation
    private String mensesCycle;

    /** 每次的月经量 */
    @UpdateAnnotation
    private String mensesVolume;

    /** 痛经的程度 */
    @UpdateAnnotation
    private String dysmenorrheaExtent;

    /** 病史 */
    @UpdateAnnotation
    private String diseaseHistory;

    /** 家族病史 */
    @UpdateAnnotation
    private String familyHistory;

    /** 用药史 */
    @UpdateAnnotation
    private String medicineHistory;

    /** 过敏史 */
    @UpdateAnnotation
    private String allergyHistory;

    /** 手术等治疗历史 */
    @UpdateAnnotation
    private String treatmentHistory;

    /** 糖尿病病史或存在糖尿病前期状态 */
    @UpdateAnnotation
    private String diabetesRelevant;

    /** 孕次 */
    @UpdateAnnotation
    private String pregnancyNum;

    /** 产次 */
    @UpdateAnnotation
    private String birthNum;

    /** 自然流产 */
    @UpdateAnnotation
    private String abortionNum;

    /** 人工流产 */
    @UpdateAnnotation
    private String inducedAbortionNum;

    /** 胎停育 */
    @UpdateAnnotation
    private String childStopNum;

    /** 早产 */
    @UpdateAnnotation
    private String pretermNum;

    /** 中期引产 */
    @UpdateAnnotation
    private String odinopoeiaNum;

    /** 足月分娩 */
    @UpdateAnnotation
    private String childbirthNum;

    /** 巨大儿分娩 */
    @UpdateAnnotation
    private String giantBabyNum;

    /** 剖宫产 */
    @UpdateAnnotation
    private String planePalaceNum;

    /** 畸形 */
    @UpdateAnnotation
    private String malformationNum;

    /** 高碘或造影剂接触史 */
    @UpdateAnnotation
    private String iodineNum;

    /** 既往妊娠并发症 */
    @UpdateAnnotation
    private String pregnancyComplications;

    /** 登记编码 */
    @UpdateAnnotation
    private String diagnosisId;

    /** 问卷分配号 */
    @UpdateAnnotation
    private String questionAllocId;

    /** 永久诊断 */
    @UpdateAnnotation
    private String permanentDisease;

    /** 问卷结论 */
    @UpdateAnnotation
    private String pregnancyResult;

    /** 是否创建了引导单pdf报告 */
    @UpdateAnnotation
    private boolean createPdf;

    /** 医生编码 */
    @UpdateAnnotation
    private String userId;

    /** 医生姓名 */
    @UpdateAnnotation
    private String userName;

    @Id
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

    @Column(name = "cust_age")
    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    @Column(name = "lmp")
    public Date getLmp() {
        return lmp;
    }

    public void setLmp(Date lmp) {
        this.lmp = lmp;
    }

    @Column(name = "height")
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Column(name = "weight")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "waistline")
    public BigDecimal getWaistline() {
        return waistline;
    }

    public void setWaistline(BigDecimal waistline) {
        this.waistline = waistline;
    }

    @Column(name = "bmi")
    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    @Column(name = "pregnancy_due_date")
    public Date getPregnancyDueDate() {
        return pregnancyDueDate;
    }

    public void setPregnancyDueDate(Date pregnancyDueDate) {
        this.pregnancyDueDate = pregnancyDueDate;
    }

    @Column(name = "create_locale")
    public String getCreateLocale() {
        return createLocale;
    }

    public void setCreateLocale(String createLocale) {
        this.createLocale = createLocale;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "create_datetime")
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name = "embryo_num")
    public String getEmbryoNum() {
        return embryoNum;
    }

    public void setEmbryoNum(String embryoNum) {
        this.embryoNum = embryoNum;
    }

    @Column(name = "folic_situation")
    public String getFolicSituation() {
        return folicSituation;
    }

    public void setFolicSituation(String folicSituation) {
        this.folicSituation = folicSituation;
    }

    @Column(name = "menarche_age")
    public String getMenarcheAge() {
        return menarcheAge;
    }

    public void setMenarcheAge(String menarcheAge) {
        this.menarcheAge = menarcheAge;
    }

    @Column(name = "first_trimester_situation")
    public String getFirstTrimesterSituation() {
        return firstTrimesterSituation;
    }

    public void setFirstTrimesterSituation(String firstTrimesterSituation) {
        this.firstTrimesterSituation = firstTrimesterSituation;
    }

    @Column(name = "menses_days")
    public String getMensesDays() {
        return mensesDays;
    }

    public void setMensesDays(String mensesDays) {
        this.mensesDays = mensesDays;
    }

    @Column(name = "menses_cycle")
    public String getMensesCycle() {
        return mensesCycle;
    }

    public void setMensesCycle(String mensesCycle) {
        this.mensesCycle = mensesCycle;
    }

    @Column(name = "menses_volume")
    public String getMensesVolume() {
        return mensesVolume;
    }

    public void setMensesVolume(String mensesVolume) {
        this.mensesVolume = mensesVolume;
    }

    @Column(name = "dysmenorrhea_extent")
    public String getDysmenorrheaExtent() {
        return dysmenorrheaExtent;
    }

    public void setDysmenorrheaExtent(String dysmenorrheaExtent) {
        this.dysmenorrheaExtent = dysmenorrheaExtent;
    }

    @Column(name = "disease_history")
    public String getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(String diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    @Column(name = "family_history")
    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    @Column(name = "medicine_history")
    public String getMedicineHistory() {
        return medicineHistory;
    }

    public void setMedicineHistory(String medicineHistory) {
        this.medicineHistory = medicineHistory;
    }

    @Column(name = "treatment_history")
    public String getTreatmentHistory() {
        return treatmentHistory;
    }

    public void setTreatmentHistory(String treatmentHistory) {
        this.treatmentHistory = treatmentHistory;
    }

    @Column(name = "diabetes_relevant")
    public String getDiabetesRelevant() {
        return diabetesRelevant;
    }

    public void setDiabetesRelevant(String diabetesRelevant) {
        this.diabetesRelevant = diabetesRelevant;
    }

    @Column(name = "pregnancy_num")
    public String getPregnancyNum() {
        return pregnancyNum;
    }

    public void setPregnancyNum(String pregnancyNum) {
        this.pregnancyNum = pregnancyNum;
    }

    @Column(name = "birth_num")
    public String getBirthNum() {
        return birthNum;
    }

    public void setBirthNum(String birthNum) {
        this.birthNum = birthNum;
    }

    @Column(name = "abortion_num")
    public String getAbortionNum() {
        return abortionNum;
    }

    public void setAbortionNum(String abortionNum) {
        this.abortionNum = abortionNum;
    }

    @Column(name = "induced_abortion_num")
    public String getInducedAbortionNum() {
        return inducedAbortionNum;
    }

    public void setInducedAbortionNum(String inducedAbortionNum) {
        this.inducedAbortionNum = inducedAbortionNum;
    }

    @Column(name = "child_stop_num")
    public String getChildStopNum() {
        return childStopNum;
    }

    public void setChildStopNum(String childStopNum) {
        this.childStopNum = childStopNum;
    }

    @Column(name = "preterm_num")
    public String getPretermNum() {
        return pretermNum;
    }

    public void setPretermNum(String pretermNum) {
        this.pretermNum = pretermNum;
    }

    @Column(name = "odinopoeia_num")
    public String getOdinopoeiaNum() {
        return odinopoeiaNum;
    }

    public void setOdinopoeiaNum(String odinopoeiaNum) {
        this.odinopoeiaNum = odinopoeiaNum;
    }

    @Column(name = "childbirth_num")
    public String getChildbirthNum() {
        return childbirthNum;
    }

    public void setChildbirthNum(String childbirthNum) {
        this.childbirthNum = childbirthNum;
    }

    @Column(name = "giant_baby_num")
    public String getGiantBabyNum() {
        return giantBabyNum;
    }

    public void setGiantBabyNum(String giantBabyNum) {
        this.giantBabyNum = giantBabyNum;
    }

    @Column(name = "plane_palace_num")
    public String getPlanePalaceNum() {
        return planePalaceNum;
    }

    public void setPlanePalaceNum(String planePalaceNum) {
        this.planePalaceNum = planePalaceNum;
    }

    @Column(name = "malformation_num")
    public String getMalformationNum() {
        return malformationNum;
    }

    public void setMalformationNum(String malformationNum) {
        this.malformationNum = malformationNum;
    }

    @Column(name = "iodine_num")
    public String getIodineNum() {
        return iodineNum;
    }

    public void setIodineNum(String iodineNum) {
        this.iodineNum = iodineNum;
    }

    @Column(name = "pregnancy_complications")
    public String getPregnancyComplications() {
        return pregnancyComplications;
    }

    public void setPregnancyComplications(String pregnancyComplications) {
        this.pregnancyComplications = pregnancyComplications;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "question_alloc_id")
    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

    @Column(name = "encyesis_situation")
    public String getEncyesisSituation() {
        return encyesisSituation;
    }

    public void setEncyesisSituation(String encyesisSituation) {
        this.encyesisSituation = encyesisSituation;
    }

    @Column(name = "allergy_history")
    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    @Column(name = "pregnancy_result")
    public String getPregnancyResult() {
        return pregnancyResult;
    }

    public void setPregnancyResult(String pregnancyResult) {
        this.pregnancyResult = pregnancyResult;
    }

    @Column(name = "permanent_disease")
    public String getPermanentDisease() {
        return permanentDisease;
    }

    public void setPermanentDisease(String permanentDisease) {
        this.permanentDisease = permanentDisease;
    }

    @Column(name = "create_pdf")
    public boolean isCreatePdf() {
        return createPdf;
    }

    public void setCreatePdf(boolean createPdf) {
        this.createPdf = createPdf;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
