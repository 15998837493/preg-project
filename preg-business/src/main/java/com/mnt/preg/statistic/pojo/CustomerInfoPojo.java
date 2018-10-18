/*
 * CustomerInfoPojo.java    1.0  2018年8月13日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * [基础信息]
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月13日 mlq 初版
 */
public class CustomerInfoPojo implements Serializable {

    private static final long serialVersionUID = 176636932040035485L;

    /** ID */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String custId;

    /** 最近一次建档ID */
    @QueryFieldAnnotation(aliasName = "archivesPojo", fieldName = "id")
    private String archivesId;

    /** 患者ID */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String custPatientId;

    /** 病案号 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String custSikeId;

    /** 姓名 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String custName;

    /** 出生日期 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date custBirthday;

    /** 年龄 */
    private Integer custAge;

    /** 身高 */
    @QueryFieldAnnotation(aliasName = "archivesPojo", fieldName = "height")
    private BigDecimal custHeight;

    /** 胎数 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String embryoNum;

    /** 受孕方式 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String encyesisSituation;

    /** 预产期 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pregnancyDueDate;

    /** 孕前体重 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private BigDecimal weight;

    /** 孕前BMI */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private BigDecimal bmi;

    /** 孕前病史 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String diseaseHistory;

    /** 家族史 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String familyHistory;

    /** 用药史 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String medicineHistory;

    /** 过敏史 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String allergyHistory;

    /** 手术史 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String treatmentHistory;

    /** 高碘接触史 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String iodineNum;

    /** 糖尿病史或糖尿病前期状态 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String diabetesRelevant;

    /** 孕次 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String pregnancyNum;

    /** 产次 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String birthNum;

    /** 人工流产 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String inducedAbortionNum;

    /** 自然流产 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String abortionNum;

    /** 胎停育 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String childStopNum;

    /** 早产 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String pretermNum;

    /** 中晚期引产 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String odinopoeiaNum;

    /** 巨大儿分娩史 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String giantBabyNum;

    /** 畸形 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String malformationNum;

    /** 既往妊娠并发症 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private String pregnancyComplications;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustSikeId() {
        return custSikeId;
    }

    public void setCustSikeId(String custSikeId) {
        this.custSikeId = custSikeId;
    }

    public String getCustPatientId() {
        return custPatientId;
    }

    public void setCustPatientId(String custPatientId) {
        this.custPatientId = custPatientId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    public String getEmbryoNum() {
        return embryoNum;
    }

    public void setEmbryoNum(String embryoNum) {
        this.embryoNum = embryoNum;
    }

    public String getEncyesisSituation() {
        return encyesisSituation;
    }

    public void setEncyesisSituation(String encyesisSituation) {
        this.encyesisSituation = encyesisSituation;
    }

    public Date getPregnancyDueDate() {
        return pregnancyDueDate;
    }

    public void setPregnancyDueDate(Date pregnancyDueDate) {
        this.pregnancyDueDate = pregnancyDueDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
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

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    public String getTreatmentHistory() {
        return treatmentHistory;
    }

    public void setTreatmentHistory(String treatmentHistory) {
        this.treatmentHistory = treatmentHistory;
    }

    public String getIodineNum() {
        return iodineNum;
    }

    public void setIodineNum(String iodineNum) {
        this.iodineNum = iodineNum;
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

    public String getInducedAbortionNum() {
        return inducedAbortionNum;
    }

    public void setInducedAbortionNum(String inducedAbortionNum) {
        this.inducedAbortionNum = inducedAbortionNum;
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

    public String getGiantBabyNum() {
        return giantBabyNum;
    }

    public void setGiantBabyNum(String giantBabyNum) {
        this.giantBabyNum = giantBabyNum;
    }

    public String getMalformationNum() {
        return malformationNum;
    }

    public void setMalformationNum(String malformationNum) {
        this.malformationNum = malformationNum;
    }

    public String getPregnancyComplications() {
        return pregnancyComplications;
    }

    public void setPregnancyComplications(String pregnancyComplications) {
        this.pregnancyComplications = pregnancyComplications;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }
}
