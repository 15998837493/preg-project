/*
 * StatisticaPojo.java    1.0  2018-2-5
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.account.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 业务数据scd部分
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-2-5 scd 初版
 */
public class StatisticaListPojo implements Serializable {

    private static final long serialVersionUID = 176636932040035485L;

    /** 接诊号 */
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diagnosisDate;

    /** 病案号 */
    @QueryFieldAnnotation
    private String diagnosisCustSikeId;

    /** 客户姓名 */
    @QueryFieldAnnotation
    private String diagnosisCustName;

    /** 孕周 */
    @QueryFieldAnnotation
    private String diagnosisGestationalWeeks;

    /** 客户身高 */
    @QueryFieldAnnotation
    private BigDecimal diagnosisCustHeight;

    /** 孕前体重 */
    @QueryFieldAnnotation(aliasName = "archivesPojo")
    private BigDecimal weight;

    /** 现体重 */
    @QueryFieldAnnotation
    private BigDecimal diagnosisCustWeight;

    /** 医师名称 */
    @QueryFieldAnnotation
    private String diagnosisUserName;

    /** 诊断 */
    private String names;

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDiagnosisCustSikeId() {
        return diagnosisCustSikeId;
    }

    public void setDiagnosisCustSikeId(String diagnosisCustSikeId) {
        this.diagnosisCustSikeId = diagnosisCustSikeId;
    }

    public String getDiagnosisCustName() {
        return diagnosisCustName;
    }

    public void setDiagnosisCustName(String diagnosisCustName) {
        this.diagnosisCustName = diagnosisCustName;
    }

    public String getDiagnosisGestationalWeeks() {
        return diagnosisGestationalWeeks;
    }

    public void setDiagnosisGestationalWeeks(String diagnosisGestationalWeeks) {
        this.diagnosisGestationalWeeks = diagnosisGestationalWeeks;
    }

    public BigDecimal getDiagnosisCustHeight() {
        return diagnosisCustHeight;
    }

    public void setDiagnosisCustHeight(BigDecimal diagnosisCustHeight) {
        this.diagnosisCustHeight = diagnosisCustHeight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getDiagnosisCustWeight() {
        return diagnosisCustWeight;
    }

    public void setDiagnosisCustWeight(BigDecimal diagnosisCustWeight) {
        this.diagnosisCustWeight = diagnosisCustWeight;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDiagnosisUserName() {
        return diagnosisUserName;
    }

    public void setDiagnosisUserName(String diagnosisUserName) {
        this.diagnosisUserName = diagnosisUserName;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
