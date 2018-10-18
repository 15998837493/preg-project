
package com.mnt.preg.master.condition.items;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 干预计划疾病信息检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-27 zcq 初版
 */
public class InterveneDiseaseCondition implements Serializable {

    private static final long serialVersionUID = -5320454504070927944L;

    /** 关联疾病主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String diseaseId;

    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "diseaseId")
    private List<String> diseaseIdList;

    /** 关联疾病主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String diseaseCode;

    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "diseaseCode")
    private List<String> diseaseCodeList;

    /** 干预疾病类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseType;

    /** 干预疾病名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diseaseName;

    /** 干预疾病名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, name = "diseaseName")
    private String planDisease;

    /** 是否治疗项目 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseTreatmentItem;

    /** 是否营养状态诊断(上次诊断)/是否营养相关疾病诊断(永久诊断) */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisIsPermanent;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public List<String> getDiseaseIdList() {
        return diseaseIdList;
    }

    public void setDiseaseIdList(List<String> diseaseIdList) {
        this.diseaseIdList = diseaseIdList;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public List<String> getDiseaseCodeList() {
        return diseaseCodeList;
    }

    public void setDiseaseCodeList(List<String> diseaseCodeList) {
        this.diseaseCodeList = diseaseCodeList;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getPlanDisease() {
        return planDisease;
    }

    public void setPlanDisease(String planDisease) {
        this.planDisease = planDisease;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
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

}
