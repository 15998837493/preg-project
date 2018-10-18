
package com.mnt.preg.master.form.items;

import java.io.Serializable;

/**
 * 干预重点表单
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-11 gss 初版
 */
public class InterveneDiseaseForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 关联疾病主键 */
    private String diseaseId;

    /** 关联疾病主键 */
    private String diseaseCode;

    /** 关联疾病名称 */
    private String diseaseName;

    /** ICD10编码 */
    private String diseaseIcd10;

    /** 干预疾病类型 */
    private String diseaseType;

    /** 流行病学 */
    private String diseaseEpidemiology;

    /** 伤害 */
    private String diseaseHarm;

    /** 概述 */
    private String diseaseDescription;

    /** 状态 */
    private String status;

    /** 诊断 */
    private String diseaseDiagnosis;

    /** 是否治疗项目 */
    private String diseaseTreatmentItem;

    /** 是否营养状态诊断(上次诊断)/是否营养相关疾病诊断(永久诊断) */
    private String diagnosisIsPermanent;

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getDiseaseEpidemiology() {
        return diseaseEpidemiology;
    }

    public void setDiseaseEpidemiology(String diseaseEpidemiology) {
        this.diseaseEpidemiology = diseaseEpidemiology;
    }

    public String getDiseaseHarm() {
        return diseaseHarm;
    }

    public void setDiseaseHarm(String diseaseHarm) {
        this.diseaseHarm = diseaseHarm;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiseaseDiagnosis() {
        return diseaseDiagnosis;
    }

    public void setDiseaseDiagnosis(String diseaseDiagnosis) {
        this.diseaseDiagnosis = diseaseDiagnosis;
    }

    public String getDiseaseTreatmentItem() {
        return diseaseTreatmentItem;
    }

    public void setDiseaseTreatmentItem(String diseaseTreatmentItem) {
        this.diseaseTreatmentItem = diseaseTreatmentItem;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseIcd10() {
        return diseaseIcd10;
    }

    public void setDiseaseIcd10(String diseaseIcd10) {
        this.diseaseIcd10 = diseaseIcd10;
    }

    public String getDiagnosisIsPermanent() {
        return diagnosisIsPermanent;
    }

    public void setDiagnosisIsPermanent(String diagnosisIsPermanent) {
        this.diagnosisIsPermanent = diagnosisIsPermanent;
    }

}
