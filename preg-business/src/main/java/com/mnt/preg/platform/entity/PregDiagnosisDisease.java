
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
 * 接诊诊断项目表
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-26 xdc 初版
 */
@Entity
@Table(name = "user_diagnosis_disease")
public class PregDiagnosisDisease extends MappedEntity {

    private static final long serialVersionUID = -2610235828461475422L;

    /** 主键 */
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 接诊主键 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 诊断项目主键 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseId;

    /** 接诊编号 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseCode;

    /** 接诊编号 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseName;

    /** 诊断项目icd10编码 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseIcd10;

    /** 诊断项目icd10编码 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseStatus;

    /** 诊断项目类别（确诊为0，待确诊为1，推断待确诊为2） */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String type;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

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

    @Column(name = "disease_icd10")
    public String getDiseaseIcd10() {
        return diseaseIcd10;
    }

    public void setDiseaseIcd10(String diseaseIcd10) {
        this.diseaseIcd10 = diseaseIcd10;
    }

    @Column(name = "disease_status")
    public String getDiseaseStatus() {
        return diseaseStatus;
    }

    public void setDiseaseStatus(String diseaseStatus) {
        this.diseaseStatus = diseaseStatus;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
