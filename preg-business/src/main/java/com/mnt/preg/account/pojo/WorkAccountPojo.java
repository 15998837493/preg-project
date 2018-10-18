
package com.mnt.preg.account.pojo;

import java.io.Serializable;

/**
 * 查询工作量统计
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-2-5 xdc 初版
 */
public class WorkAccountPojo implements Serializable {

    private static final long serialVersionUID = 106369253982973435L;

    /** 医师ID */
    private String diagnosisUser;

    /** 医师姓名 */
    private String diagnosisUserName;

    /** 月份 */
    private String month;

    /** 就诊人数 */
    private Object diagnosisNum;

    /** 就诊人次 */
    private Object diagnosisRen;

    /** 初诊人数 */
    private Object diagnosisFirst;

    /** 复诊率 */
    private Object subsequentRate;

    /** 孕早期人数 */
    private Object pregStagePre;

    /** 孕早期比率 */
    private Object pregStagePreRate;

    /** 孕中期人数 */
    private Object pregStageMid;

    /** 孕中期比率 */
    private Object pregStageMidRate;

    /** 孕晚期人数 */
    private Object pregStageLate;

    /** 孕晚期比率 */
    private Object pregStageLateRate;

    /** 诊断频次 */
    private Object diseaseFrequency;

    /** 诊断ID */
    private String diseaseId;

    /** 诊断名称 */
    private String diseaseName;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDiagnosisUser() {
        return diagnosisUser;
    }

    public void setDiagnosisUser(String diagnosisUser) {
        this.diagnosisUser = diagnosisUser;
    }

    public String getDiagnosisUserName() {
        return diagnosisUserName;
    }

    public void setDiagnosisUserName(String diagnosisUserName) {
        this.diagnosisUserName = diagnosisUserName;
    }

    public Object getDiagnosisNum() {
        return diagnosisNum;
    }

    public void setDiagnosisNum(Object diagnosisNum) {
        this.diagnosisNum = diagnosisNum;
    }

    public Object getDiagnosisRen() {
        return diagnosisRen;
    }

    public void setDiagnosisRen(Object diagnosisRen) {
        this.diagnosisRen = diagnosisRen;
    }

    public Object getDiagnosisFirst() {
        return diagnosisFirst;
    }

    public void setDiagnosisFirst(Object diagnosisFirst) {
        this.diagnosisFirst = diagnosisFirst;
    }

    public Object getSubsequentRate() {
        return subsequentRate;
    }

    public void setSubsequentRate(Object subsequentRate) {
        this.subsequentRate = subsequentRate;
    }

    public Object getPregStagePre() {
        return pregStagePre;
    }

    public void setPregStagePre(Object pregStagePre) {
        this.pregStagePre = pregStagePre;
    }

    public Object getPregStagePreRate() {
        return pregStagePreRate;
    }

    public void setPregStagePreRate(Object pregStagePreRate) {
        this.pregStagePreRate = pregStagePreRate;
    }

    public Object getPregStageMid() {
        return pregStageMid;
    }

    public void setPregStageMid(Object pregStageMid) {
        this.pregStageMid = pregStageMid;
    }

    public Object getPregStageMidRate() {
        return pregStageMidRate;
    }

    public void setPregStageMidRate(Object pregStageMidRate) {
        this.pregStageMidRate = pregStageMidRate;
    }

    public Object getPregStageLate() {
        return pregStageLate;
    }

    public void setPregStageLate(Object pregStageLate) {
        this.pregStageLate = pregStageLate;
    }

    public Object getPregStageLateRate() {
        return pregStageLateRate;
    }

    public void setPregStageLateRate(Object pregStageLateRate) {
        this.pregStageLateRate = pregStageLateRate;
    }

    public Object getDiseaseFrequency() {
        return diseaseFrequency;
    }

    public void setDiseaseFrequency(Object diseaseFrequency) {
        this.diseaseFrequency = diseaseFrequency;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    @Override
    public String toString() {
        return month + "," + diagnosisNum + "," + diagnosisRen + "," + diagnosisFirst + ","
                + subsequentRate
                + "," + pregStagePre + "," + pregStagePreRate + "," + pregStageMid + "," + pregStageMidRate + ","
                + pregStageLate + "," + pregStageLateRate;
    }
}
