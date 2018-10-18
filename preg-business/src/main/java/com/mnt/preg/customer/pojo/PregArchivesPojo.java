
package com.mnt.preg.customer.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 孕期建档表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 gss 初版
 */
public class PregArchivesPojo implements Serializable {

    private static final long serialVersionUID = 6374057429745991593L;

    /** 编码 */
    @QueryFieldAnnotation
    private String id;

    /** 客户 */
    @QueryFieldAnnotation
    private String custId;

    /** 末次月经 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lmp;

    /** 孕前身高 */
    @QueryFieldAnnotation
    private BigDecimal height;

    /** 孕前体重 */
    @QueryFieldAnnotation
    private BigDecimal weight;

    /** 孕前腰围 */
    @QueryFieldAnnotation
    private BigDecimal waistline;

    /** 孕前BMI */
    @QueryFieldAnnotation
    private BigDecimal bmi;

    /** 预产期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pregnancyDueDate;

    /** 建档地点 */
    @QueryFieldAnnotation
    private String createLocale;

    /** 建档日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    /** 建档日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDatetime;

    /** 患者姓名 */
    private String custName;

    /** 妊娠情况 */
    @QueryFieldAnnotation
    private String encyesisSituation;

    /** 妊娠胚胎数 */
    @QueryFieldAnnotation
    private String embryoNum;

    /** 孕前叶酸服用情况 */
    @QueryFieldAnnotation
    private String folicSituation;

    /** 初潮年龄 */
    @QueryFieldAnnotation
    private String menarcheAge;

    /** 孕早期存在情况 */
    @QueryFieldAnnotation
    private String firstTrimesterSituation;

    /** 平均每次月经持续天数 */
    @QueryFieldAnnotation
    private String mensesDays;

    /** 月经周期 */
    @QueryFieldAnnotation
    private String mensesCycle;

    /** 每次的月经量 */
    @QueryFieldAnnotation
    private String mensesVolume;

    /** 痛经的程度 */
    @QueryFieldAnnotation
    private String dysmenorrheaExtent;

    /** 病史 */
    @QueryFieldAnnotation
    private String diseaseHistory;

    /** 家族病史 */
    @QueryFieldAnnotation
    private String familyHistory;

    /** 用药史 */
    @QueryFieldAnnotation
    private String medicineHistory;

    /** 过敏史 */
    @QueryFieldAnnotation
    private String allergyHistory;

    /** 手术等治疗历史 */
    @QueryFieldAnnotation
    private String treatmentHistory;

    /** 糖尿病病史或存在糖尿病前期状态 */
    @QueryFieldAnnotation
    private String diabetesRelevant;

    /** 孕次 */
    @QueryFieldAnnotation
    private String pregnancyNum;

    /** 产次 */
    @QueryFieldAnnotation
    private String birthNum;

    /** 自然流产 */
    @QueryFieldAnnotation
    private String abortionNum;

    /** 胎停育 */
    @QueryFieldAnnotation
    private String childStopNum;

    /** 早产 */
    @QueryFieldAnnotation
    private String pretermNum;

    /** 中期引产 */
    @QueryFieldAnnotation
    private String odinopoeiaNum;

    /** 足月分娩 */
    @QueryFieldAnnotation
    private String childbirthNum;

    /** 巨大儿分娩 */
    @QueryFieldAnnotation
    private String giantBabyNum;

    /** 剖宫产 */
    @QueryFieldAnnotation
    private String planePalaceNum;

    /** 畸形 */
    @QueryFieldAnnotation
    private String malformationNum;

    /** 高碘或造影剂接触史 */
    @QueryFieldAnnotation
    private String iodineNum;

    /** 既往妊娠并发症 */
    @QueryFieldAnnotation
    private String pregnancyComplications;

    /** 登记编码 */
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 问卷分配号 */
    @QueryFieldAnnotation
    private String questionAllocId;

    /** 问卷分配号 */
    @QueryFieldAnnotation
    private String permanentDisease;

    /** 问卷结论 */
    @QueryFieldAnnotation
    private String pregnancyResult;

    private String createDateString;

    /** 是否创建了引导单pdf报告 */
    @QueryFieldAnnotation
    private boolean createPdf;

    /** 医生编码 */
    @QueryFieldAnnotation
    private String userId;

    /** 医生姓名 */
    @QueryFieldAnnotation
    private String userName;

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

    public Date getLmp() {
        return lmp;
    }

    public void setLmp(Date lmp) {
        this.lmp = lmp;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWaistline() {
        return waistline;
    }

    public void setWaistline(BigDecimal waistline) {
        this.waistline = waistline;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public Date getPregnancyDueDate() {
        return pregnancyDueDate;
    }

    public void setPregnancyDueDate(Date pregnancyDueDate) {
        this.pregnancyDueDate = pregnancyDueDate;
    }

    public String getCreateLocale() {
        return createLocale;
    }

    public void setCreateLocale(String createLocale) {
        this.createLocale = createLocale;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEmbryoNum() {
        return embryoNum;
    }

    public void setEmbryoNum(String embryoNum) {
        this.embryoNum = embryoNum;
    }

    public String getFolicSituation() {
        return folicSituation;
    }

    public void setFolicSituation(String folicSituation) {
        this.folicSituation = folicSituation;
    }

    public String getMenarcheAge() {
        return menarcheAge;
    }

    public void setMenarcheAge(String menarcheAge) {
        this.menarcheAge = menarcheAge;
    }

    public String getFirstTrimesterSituation() {
        return firstTrimesterSituation;
    }

    public void setFirstTrimesterSituation(String firstTrimesterSituation) {
        this.firstTrimesterSituation = firstTrimesterSituation;
    }

    public String getMensesDays() {
        return mensesDays;
    }

    public void setMensesDays(String mensesDays) {
        this.mensesDays = mensesDays;
    }

    public String getMensesCycle() {
        return mensesCycle;
    }

    public void setMensesCycle(String mensesCycle) {
        this.mensesCycle = mensesCycle;
    }

    public String getMensesVolume() {
        return mensesVolume;
    }

    public void setMensesVolume(String mensesVolume) {
        this.mensesVolume = mensesVolume;
    }

    public String getDysmenorrheaExtent() {
        return dysmenorrheaExtent;
    }

    public void setDysmenorrheaExtent(String dysmenorrheaExtent) {
        this.dysmenorrheaExtent = dysmenorrheaExtent;
    }

    public String getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(String diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getMedicineHistory() {
        return medicineHistory;
    }

    public void setMedicineHistory(String medicineHistory) {
        this.medicineHistory = medicineHistory;
    }

    public String getTreatmentHistory() {
        return treatmentHistory;
    }

    public void setTreatmentHistory(String treatmentHistory) {
        this.treatmentHistory = treatmentHistory;
    }

    public String getDiabetesRelevant() {
        return diabetesRelevant;
    }

    public void setDiabetesRelevant(String diabetesRelevant) {
        this.diabetesRelevant = diabetesRelevant;
    }

    public String getPregnancyNum() {
        return pregnancyNum;
    }

    public void setPregnancyNum(String pregnancyNum) {
        this.pregnancyNum = pregnancyNum;
    }

    public String getBirthNum() {
        return birthNum;
    }

    public void setBirthNum(String birthNum) {
        this.birthNum = birthNum;
    }

    public String getAbortionNum() {
        return abortionNum;
    }

    public void setAbortionNum(String abortionNum) {
        this.abortionNum = abortionNum;
    }

    public String getChildStopNum() {
        return childStopNum;
    }

    public void setChildStopNum(String childStopNum) {
        this.childStopNum = childStopNum;
    }

    public String getPretermNum() {
        return pretermNum;
    }

    public void setPretermNum(String pretermNum) {
        this.pretermNum = pretermNum;
    }

    public String getOdinopoeiaNum() {
        return odinopoeiaNum;
    }

    public void setOdinopoeiaNum(String odinopoeiaNum) {
        this.odinopoeiaNum = odinopoeiaNum;
    }

    public String getChildbirthNum() {
        return childbirthNum;
    }

    public void setChildbirthNum(String childbirthNum) {
        this.childbirthNum = childbirthNum;
    }

    public String getGiantBabyNum() {
        return giantBabyNum;
    }

    public void setGiantBabyNum(String giantBabyNum) {
        this.giantBabyNum = giantBabyNum;
    }

    public String getPlanePalaceNum() {
        return planePalaceNum;
    }

    public void setPlanePalaceNum(String planePalaceNum) {
        this.planePalaceNum = planePalaceNum;
    }

    public String getMalformationNum() {
        return malformationNum;
    }

    public void setMalformationNum(String malformationNum) {
        this.malformationNum = malformationNum;
    }

    public String getIodineNum() {
        return iodineNum;
    }

    public void setIodineNum(String iodineNum) {
        this.iodineNum = iodineNum;
    }

    public String getPregnancyComplications() {
        return pregnancyComplications;
    }

    public void setPregnancyComplications(String pregnancyComplications) {
        this.pregnancyComplications = pregnancyComplications;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

    public String getEncyesisSituation() {
        return encyesisSituation;
    }

    public void setEncyesisSituation(String encyesisSituation) {
        this.encyesisSituation = encyesisSituation;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDateString() {
        return createDateString;
    }

    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
    }

    public String getPregnancyResult() {
        return pregnancyResult;
    }

    public void setPregnancyResult(String pregnancyResult) {
        this.pregnancyResult = pregnancyResult;
    }

    public String getPermanentDisease() {
        return permanentDisease;
    }

    public void setPermanentDisease(String permanentDisease) {
        this.permanentDisease = permanentDisease;
    }

    public boolean isCreatePdf() {
        return createPdf;
    }

    public void setCreatePdf(boolean createPdf) {
        this.createPdf = createPdf;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
