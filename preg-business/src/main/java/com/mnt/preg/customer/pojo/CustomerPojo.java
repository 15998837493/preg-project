
package com.mnt.preg.customer.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 客户信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
public class CustomerPojo implements Serializable {

    private static final long serialVersionUID = 7799809087456530773L;

    @QueryFieldAnnotation
    private String custId;// 主键

    @QueryFieldAnnotation
    private String custCode;// 客户编码

    @QueryFieldAnnotation
    private String custPassword;// 用户密码

    @QueryFieldAnnotation
    private String custName;// 姓名

    @QueryFieldAnnotation
    private String custSex;// 性别

    @QueryFieldAnnotation
    private String custIcard;// 身份证号码

    @QueryFieldAnnotation
    private String custPhone;// 手机号码

    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custBirthday;// 出生日期

    @QueryFieldAnnotation
    private String custNation;// 民族

    @QueryFieldAnnotation
    private String custJob;// 职业

    @QueryFieldAnnotation
    private String custMarriage;// 婚姻状况

    @QueryFieldAnnotation
    private String custEducation;// 学历

    @QueryFieldAnnotation
    private String custIncome;// 收入水平

    @QueryFieldAnnotation
    private String custPlevel;// 身体水平

    @QueryFieldAnnotation
    private BigDecimal custHeight;// 身高

    @QueryFieldAnnotation
    private BigDecimal custWeight;// 体重

    @QueryFieldAnnotation
    private BigDecimal custBirthWeight;// 出生体重

    @QueryFieldAnnotation
    private BigDecimal custWaistline;// 腰围

    @QueryFieldAnnotation
    private BigDecimal custBmi;

    @QueryFieldAnnotation
    private Date custFirstVisit;

    @QueryFieldAnnotation
    private String custCountry;// 所属国家

    @QueryFieldAnnotation
    private String custProvince;// 所属省

    @QueryFieldAnnotation
    private String custCity;// 所属市

    @QueryFieldAnnotation
    private String custDistrict;// 所属区

    @QueryFieldAnnotation
    private String custAddress;// 地址

    @QueryFieldAnnotation
    private String custPatientId;// ID

    @QueryFieldAnnotation
    private String custSikeId;// 病案号

    private Integer custAge; // 客户年龄

    /** 营养治疗项目名称 */
    private String diagnosisDiseaseNames;

    /** 营养筛查项目名称 */
    private String diagnosisInspectTemplateName;

    /** 检查结果 */
    private String diagnosisConclusion;

    /** 诊疗次数 */
    private Object diagnosisCount;

    /** 注册登记接诊id */
    private String diagnosisId;

    /** 末次月经 */
    private Date lmp;

    /** 预产期 */
    private Date pregnancyDueDate;

    /** 分娩日期 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;
    
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustPassword() {
        return custPassword;
    }

    public void setCustPassword(String custPassword) {
        this.custPassword = custPassword;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    public String getCustIcard() {
        return custIcard;
    }

    public void setCustIcard(String custIcard) {
        this.custIcard = custIcard;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    public String getCustNation() {
        return custNation;
    }

    public void setCustNation(String custNation) {
        this.custNation = custNation;
    }

    public String getCustJob() {
        return custJob;
    }

    public void setCustJob(String custJob) {
        this.custJob = custJob;
    }

    public String getCustMarriage() {
        return custMarriage;
    }

    public void setCustMarriage(String custMarriage) {
        this.custMarriage = custMarriage;
    }

    public String getCustEducation() {
        return custEducation;
    }

    public void setCustEducation(String custEducation) {
        this.custEducation = custEducation;
    }

    public String getCustIncome() {
        return custIncome;
    }

    public void setCustIncome(String custIncome) {
        this.custIncome = custIncome;
    }

    public String getCustPlevel() {
        return custPlevel;
    }

    public void setCustPlevel(String custPlevel) {
        this.custPlevel = custPlevel;
    }

    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    public BigDecimal getCustWeight() {
        return custWeight;
    }

    public void setCustWeight(BigDecimal custWeight) {
        this.custWeight = custWeight;
    }

    public BigDecimal getCustWaistline() {
        return custWaistline;
    }

    public void setCustWaistline(BigDecimal custWaistline) {
        this.custWaistline = custWaistline;
    }

    public BigDecimal getCustBmi() {
        return custBmi;
    }

    public void setCustBmi(BigDecimal custBmi) {
        this.custBmi = custBmi;
    }

    public Date getCustFirstVisit() {
        return custFirstVisit;
    }

    public void setCustFirstVisit(Date custFirstVisit) {
        this.custFirstVisit = custFirstVisit;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustProvince() {
        return custProvince;
    }

    public void setCustProvince(String custProvince) {
        this.custProvince = custProvince;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustDistrict() {
        return custDistrict;
    }

    public void setCustDistrict(String custDistrict) {
        this.custDistrict = custDistrict;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public String getDiagnosisDiseaseNames() {
        return diagnosisDiseaseNames;
    }

    public void setDiagnosisDiseaseNames(String diagnosisDiseaseNames) {
        this.diagnosisDiseaseNames = diagnosisDiseaseNames;
    }

    public String getDiagnosisInspectTemplateName() {
        return diagnosisInspectTemplateName;
    }

    public void setDiagnosisInspectTemplateName(String diagnosisInspectTemplateName) {
        this.diagnosisInspectTemplateName = diagnosisInspectTemplateName;
    }

    public String getDiagnosisConclusion() {
        return diagnosisConclusion;
    }

    public void setDiagnosisConclusion(String diagnosisConclusion) {
        this.diagnosisConclusion = diagnosisConclusion;
    }

    public Object getDiagnosisCount() {
        return diagnosisCount;
    }

    public void setDiagnosisCount(Object diagnosisCount) {
        this.diagnosisCount = diagnosisCount;
    }

    public Date getLmp() {
        return lmp;
    }

    public void setLmp(Date lmp) {
        this.lmp = lmp;
    }

    public Date getPregnancyDueDate() {
        return pregnancyDueDate;
    }

    public void setPregnancyDueDate(Date pregnancyDueDate) {
        this.pregnancyDueDate = pregnancyDueDate;
    }

    public BigDecimal getCustBirthWeight() {
        return custBirthWeight;
    }

    public void setCustBirthWeight(BigDecimal custBirthWeight) {
        this.custBirthWeight = custBirthWeight;
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

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    
    public Date getBirthDate() {
        return birthDate;
    }

    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
