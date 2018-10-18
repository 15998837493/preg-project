
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 常用诊断项目
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-26 scd 初版
 */
@Entity
@Table(name = "user_disease_often")
public class DiseaseOften extends MappedEntity {

    private static final long serialVersionUID = 7692318920843956166L;

    /** 关联疾病主键 */
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseId;

    /** 关联疾病编码 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseCode;

    /** 关联疾病名称 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseName;

    /** 疾病ICD10编码 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseIcd10;

    /** 干预疾病类型 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseType;

    /** 流行病学 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseEpidemiology;

    /** 伤害 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseHarm;

    /** 概述 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseDescription;

    /** 状态 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String status;

    /** 诊断 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseDiagnosis;

    /** 是否治疗项目 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseTreatmentItem;

    /** 是否营养状态诊断(上次诊断) 0/是否营养相关疾病诊断(永久诊断) 1 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisIsPermanent;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "disease_id")
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Column(name = "disease_code")
    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    @Column(name = "disease_name")
    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    @Column(name = "disease_epidemiology")
    public String getDiseaseEpidemiology() {
        return diseaseEpidemiology;
    }

    public void setDiseaseEpidemiology(String diseaseEpidemiology) {
        this.diseaseEpidemiology = diseaseEpidemiology;
    }

    @Column(name = "disease_harm")
    public String getDiseaseHarm() {
        return diseaseHarm;
    }

    public void setDiseaseHarm(String diseaseHarm) {
        this.diseaseHarm = diseaseHarm;
    }

    @Column(name = "disease_description")
    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    @Column(name = "disease_type")
    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "disease_diagnosis")
    public String getDiseaseDiagnosis() {
        return diseaseDiagnosis;
    }

    public void setDiseaseDiagnosis(String diseaseDiagnosis) {
        this.diseaseDiagnosis = diseaseDiagnosis;
    }

    @Column(name = "disease_treatment_item")
    public String getDiseaseTreatmentItem() {
        return diseaseTreatmentItem;
    }

    public void setDiseaseTreatmentItem(String diseaseTreatmentItem) {
        this.diseaseTreatmentItem = diseaseTreatmentItem;
    }

    @Column(name = "disease_icd10")
    public String getDiseaseIcd10() {
        return diseaseIcd10;
    }

    public void setDiseaseIcd10(String diseaseIcd10) {
        this.diseaseIcd10 = diseaseIcd10;
    }

    @Column(name = "diagnosis_is_permanent")
    public String getDiagnosisIsPermanent() {
        return diagnosisIsPermanent;
    }

    public void setDiagnosisIsPermanent(String diagnosisIsPermanent) {
        this.diagnosisIsPermanent = diagnosisIsPermanent;
    }

}
