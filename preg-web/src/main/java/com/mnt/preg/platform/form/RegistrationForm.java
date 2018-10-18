
package com.mnt.preg.platform.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 首诊登记信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-28 zcq 初版
 */
public class RegistrationForm {

    /** ID */
    private String custPatientId;

    /** 病案号 */
    private String custSikeId;

    /** 姓名 */
    private String custName;

    /** 身份证号 */
    private String custIcard;

    /** 出生日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custBirthday;

    /** 患者性别 */
    private String custSex;

    /** 客户编码 */
    private String diagnosisCustomer;

    /** 就诊来源 */
    private String diagnosisOrg;

    /** 状态 */
    private Integer diagnosisStatus;

    /** 接诊医生 */
    private String diagnosisUser;

    /** 接诊医生名称 */
    private String diagnosisUserName;

    /** 转诊医生 */
    private String diagnosisReferralDoctor;

    /** 转诊医生 */
    private String diagnosisReferralDoctorName;

    /** 接诊类型 */
    private Integer diagnosisType;

    /** 评价项目列表json */
    private String inspectListJson;

    /** 诊断项目列表json */
    private String diseaseListJson;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustIcard() {
        return custIcard;
    }

    public void setCustIcard(String custIcard) {
        this.custIcard = custIcard;
    }

    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    public String getDiagnosisOrg() {
        return diagnosisOrg;
    }

    public void setDiagnosisOrg(String diagnosisOrg) {
        this.diagnosisOrg = diagnosisOrg;
    }

    public String getDiagnosisCustomer() {
        return diagnosisCustomer;
    }

    public void setDiagnosisCustomer(String diagnosisCustomer) {
        this.diagnosisCustomer = diagnosisCustomer;
    }

    public Integer getDiagnosisStatus() {
        return diagnosisStatus;
    }

    public void setDiagnosisStatus(Integer diagnosisStatus) {
        this.diagnosisStatus = diagnosisStatus;
    }

    public String getCustPatientId() {
        return custPatientId;
    }

    public void setCustPatientId(String custPatientId) {
        this.custPatientId = custPatientId;
    }

    public String getCustSikeId() {
        return custSikeId;
    }

    public void setCustSikeId(String custSikeId) {
        this.custSikeId = custSikeId;
    }

    public String getDiagnosisReferralDoctor() {
        return diagnosisReferralDoctor;
    }

    public void setDiagnosisReferralDoctor(String diagnosisReferralDoctor) {
        this.diagnosisReferralDoctor = diagnosisReferralDoctor;
    }

    public String getDiagnosisReferralDoctorName() {
        return diagnosisReferralDoctorName;
    }

    public void setDiagnosisReferralDoctorName(String diagnosisReferralDoctorName) {
        this.diagnosisReferralDoctorName = diagnosisReferralDoctorName;
    }

    public Integer getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Integer diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public String getInspectListJson() {
        return inspectListJson;
    }

    public void setInspectListJson(String inspectListJson) {
        this.inspectListJson = inspectListJson;
    }

    public String getDiseaseListJson() {
        return diseaseListJson;
    }

    public void setDiseaseListJson(String diseaseListJson) {
        this.diseaseListJson = diseaseListJson;
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

}
