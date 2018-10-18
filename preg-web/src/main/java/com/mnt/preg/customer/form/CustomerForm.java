
package com.mnt.preg.customer.form;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 会员信息
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-9 zy 初版
 */
public class CustomerForm {

    private String id;// 主键

    private String custId;// 主键

    private String custCode;// 客户编码

    private String custPassword;// 用户密码

    private String custName;// 姓名

    private String custSex;// 性别

    private String custIcard;// 身份证号码

    private String custPhone;// 手机号码

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custBirthday;// 出生日期

    private String custNation;// 民族

    private String custJob;// 职业

    private String custMarriage;// 婚姻状况

    private String custEducation;// 学历

    private String custIncome;// 收入水平

    private String custPlevel;// 身体水平

    private BigDecimal custHeight;// 身高

    private BigDecimal custWeight;// 体重

    private BigDecimal custBirthWeight;// 出生体重

    private BigDecimal custWaistline;// 腰围

    private BigDecimal custBmi;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date custFirstVisit;

    private String custCountry;// 所属国家

    private String custProvince;// 所属省

    private String custCity;// 所属市

    private String custDistrict;// 所属区

    private String custAddress;// 地址

    private String custPatientId;// ID

    private String custSikeId;// 病案号

    // 建档信息
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lmp;// 末次月经期

    /** 建档日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    /** 孕期建册地点 */
    private String createLocale;

    /** 预产期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pregnancyDueDate;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getCustPlevel() {
        return custPlevel;
    }

    public void setCustPlevel(String custPlevel) {
        this.custPlevel = custPlevel;
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

    public String getCustJob() {
        return custJob;
    }

    public void setCustJob(String custJob) {
        this.custJob = custJob;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
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

    public String getCustNation() {
        return custNation;
    }

    public void setCustNation(String custNation) {
        this.custNation = custNation;
    }

    public String getCustMarriage() {
        return custMarriage;
    }

    public void setCustMarriage(String custMarriage) {
        this.custMarriage = custMarriage;
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

    public Date getLmp() {
        return lmp;
    }

    public void setLmp(Date lmp) {
        this.lmp = lmp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateLocale() {
        return createLocale;
    }

    public void setCreateLocale(String createLocale) {
        this.createLocale = createLocale;
    }

    public Date getPregnancyDueDate() {
        return pregnancyDueDate;
    }

    public void setPregnancyDueDate(Date pregnancyDueDate) {
        this.pregnancyDueDate = pregnancyDueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
