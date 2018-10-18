/*
 * DiseaseOftenCondition.java    1.0  2017-11-28
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.condition;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.enums.Flag;

/**
 * 常用诊断项目检索条件
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-28 scd 初版
 */
public class DiseaseOftenCondition implements Serializable {

    private static final long serialVersionUID = -3429411474856448394L;

    /** 关联疾病主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseId;

    /** 关联疾病编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseCode;

    /** 关联疾病名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseName;

    /** 疾病ICD10编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseIcd10;

    /** 干预疾病类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseType;

    /** 流行病学 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseEpidemiology;

    /** 伤害 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseHarm;

    /** 概述 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseDescription;

    /** 状态 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String status;

    /** 诊断 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseDiagnosis;

    /** 是否治疗项目 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseTreatmentItem;

    /** 是否营养状态诊断(上次诊断) 0/是否营养相关疾病诊断(永久诊断) 1 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisIsPermanent;

    /** 创建者 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String createUserId;

    /** 逻辑删除标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private Integer flag = Flag.normal.getValue();

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

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

    public String getDiseaseIcd10() {
        return diseaseIcd10;
    }

    public void setDiseaseIcd10(String diseaseIcd10) {
        this.diseaseIcd10 = diseaseIcd10;
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

    public String getDiagnosisIsPermanent() {
        return diagnosisIsPermanent;
    }

    public void setDiagnosisIsPermanent(String diagnosisIsPermanent) {
        this.diagnosisIsPermanent = diagnosisIsPermanent;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
