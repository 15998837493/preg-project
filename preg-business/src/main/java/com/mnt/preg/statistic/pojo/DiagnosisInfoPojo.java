/*
 * DiagnosisInfoPojo.java    1.0  2018年8月13日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;

/**
 * [接诊信息]
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月13日 mlq 初版
 */
public class DiagnosisInfoPojo implements Serializable {

    private static final long serialVersionUID = 176636932040035485L;

    // 接诊信息
    /** 患者id */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "diagnosis_customer")
    private String custId;

    /** 接诊id */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String diagnosisId;

    /** 建档id */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String archivesId;

    /** 就诊次数 # */
    private Object diagnosisCount;

    /** 营养门诊接诊时间 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisDate;

    /** 一日门诊（暂不考虑） */
    /** MDT门诊（暂不考虑） */
    /** 营养门诊（默认为是）# */
    private String diagnosisName;

    /** 营养医师 #营养门诊医生 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String diagnosisUserName;

    /** 产科医师 #转诊医生 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String diagnosisReferralDoctorName;

    /** 孕周数 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String diagnosisGestationalWeeks;

    /** 当前体重 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private BigDecimal diagnosisCustWeight;

    /** 风险级别 （代码表维护）1绿、2黄、3橙、4红、5紫 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String gestationLevel;

    // 人体成分信息
    /** 检测时间 */
    private Date examDate;

    /** 现体重 */
    private BigDecimal wt;

    /** 体脂肪 */
    private BigDecimal bfm;

    /** 体脂百分比 */
    private String pbf;

    /** 骨骼肌 */
    private BigDecimal smm;

    /** 蛋白质 */
    private BigDecimal protein;

    /** 无机盐 */
    private BigDecimal mineral;

    /** 细胞内水分 */
    private BigDecimal icw;

    /** 细胞外水分 */
    private BigDecimal ecw;

    /** 细胞外水分比率 */
    private BigDecimal wed;

    /** 相位角 */
    private String wbpa50;

    // 孕期检验记录
    /** 本次诊断 */
    private String diseaseNames;

    /** 诊断项目集合 */
    private List<PregDiagnosisDisease> diseaseList;

    /** 检验项目集合 */
    private List<DiagnosisHospitalItemPojo> items;

    // 孕期医嘱
    /** 继服营养制剂 */
    private String currentProductNames;

    /** 新增营养制剂 */
    private String increasedProductNames;

    /** 停服营养制剂 */
    private String stopProductNames;

    // 营养制剂信息
    private List<DiagnosisPrescriptionPojo> productList;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Object getDiagnosisCount() {
        return diagnosisCount;
    }

    public void setDiagnosisCount(Object diagnosisCount) {
        this.diagnosisCount = diagnosisCount;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDiagnosisName() {
        this.diagnosisName = "是";
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getDiagnosisUserName() {
        return diagnosisUserName;
    }

    public void setDiagnosisUserName(String diagnosisUserName) {
        this.diagnosisUserName = diagnosisUserName;
    }

    public String getDiagnosisReferralDoctorName() {
        return diagnosisReferralDoctorName;
    }

    public void setDiagnosisReferralDoctorName(String diagnosisReferralDoctorName) {
        this.diagnosisReferralDoctorName = diagnosisReferralDoctorName;
    }

    public String getDiagnosisGestationalWeeks() {
        return diagnosisGestationalWeeks;
    }

    public void setDiagnosisGestationalWeeks(String diagnosisGestationalWeeks) {
        this.diagnosisGestationalWeeks = diagnosisGestationalWeeks;
    }

    public BigDecimal getDiagnosisCustWeight() {
        return diagnosisCustWeight;
    }

    public void setDiagnosisCustWeight(BigDecimal diagnosisCustWeight) {
        this.diagnosisCustWeight = diagnosisCustWeight;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public BigDecimal getWt() {
        return wt;
    }

    public void setWt(BigDecimal wt) {
        this.wt = wt;
    }

    public BigDecimal getBfm() {
        return bfm;
    }

    public void setBfm(BigDecimal bfm) {
        this.bfm = bfm;
    }

    public String getPbf() {
        return pbf;
    }

    public void setPbf(String pbf) {
        this.pbf = pbf;
    }

    public BigDecimal getSmm() {
        return smm;
    }

    public void setSmm(BigDecimal smm) {
        this.smm = smm;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getMineral() {
        return mineral;
    }

    public void setMineral(BigDecimal mineral) {
        this.mineral = mineral;
    }

    public BigDecimal getIcw() {
        return icw;
    }

    public void setIcw(BigDecimal icw) {
        this.icw = icw;
    }

    public BigDecimal getEcw() {
        return ecw;
    }

    public void setEcw(BigDecimal ecw) {
        this.ecw = ecw;
    }

    public BigDecimal getWed() {
        return wed;
    }

    public void setWed(BigDecimal wed) {
        this.wed = wed;
    }

    public String getWbpa50() {
        return wbpa50;
    }

    public void setWbpa50(String wbpa50) {
        this.wbpa50 = wbpa50;
    }

    public String getDiseaseNames() {
        return diseaseNames;
    }

    public void setDiseaseNames(String diseaseNames) {
        this.diseaseNames = diseaseNames;
    }

    public String getGestationLevel() {
        return gestationLevel;
    }

    public void setGestationLevel(String gestationLevel) {
        this.gestationLevel = gestationLevel;
    }

    public List<DiagnosisHospitalItemPojo> getItems() {
        return items;
    }

    public void setItems(List<DiagnosisHospitalItemPojo> items) {
        this.items = items;
    }

    public String getCurrentProductNames() {
        return currentProductNames;
    }

    public void setCurrentProductNames(String currentProductNames) {
        this.currentProductNames = currentProductNames;
    }

    public String getIncreasedProductNames() {
        return increasedProductNames;
    }

    public void setIncreasedProductNames(String increasedProductNames) {
        this.increasedProductNames = increasedProductNames;
    }

    public String getStopProductNames() {
        return stopProductNames;
    }

    public void setStopProductNames(String stopProductNames) {
        this.stopProductNames = stopProductNames;
    }

    public List<PregDiagnosisDisease> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(List<PregDiagnosisDisease> diseaseList) {
        this.diseaseList = diseaseList;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public List<DiagnosisPrescriptionPojo> getProductList() {
        return productList;
    }

    public void setProductList(List<DiagnosisPrescriptionPojo> productList) {
        this.productList = productList;
    }

}
