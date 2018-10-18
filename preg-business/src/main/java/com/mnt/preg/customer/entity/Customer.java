
package com.mnt.preg.customer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 客户信息Bo
 * 
 * @author 王鑫
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-09 王鑫 初版
 */
@Entity
@Table(name = "cus_customer")
public class Customer extends MappedEntity {

    private static final long serialVersionUID = -7633301084244987903L;

    private String custId;// 主键

    private String custCode;// 客户编码

    @UpdateAnnotation
    private String custPassword;// 用户密码

    @UpdateAnnotation
    private String custName;// 姓名

    @UpdateAnnotation
    private String custSex;// 性别

    @UpdateAnnotation
    private String custIcard;// 身份证号码

    @UpdateAnnotation
    private String custPhone;// 手机号码

    @UpdateAnnotation
    private Date custBirthday;// 出生日期

    @UpdateAnnotation
    private String custNation;// 民族

    @UpdateAnnotation
    private String custJob;// 职业

    @UpdateAnnotation
    private String custMarriage;// 婚姻状况

    @UpdateAnnotation
    private String custEducation;// 学历

    @UpdateAnnotation
    private String custIncome;// 收入水平

    @UpdateAnnotation
    private String custPlevel;// 身体水平

    @UpdateAnnotation
    private BigDecimal custHeight;// 身高

    @UpdateAnnotation
    private BigDecimal custWeight;// 体重

    @UpdateAnnotation
    private BigDecimal custBirthWeight;// 出生体重

    @UpdateAnnotation
    private BigDecimal custWaistline;// 腰围

    @UpdateAnnotation
    private BigDecimal custBmi;

    @UpdateAnnotation
    private Date custFirstVisit;

    @UpdateAnnotation
    private String custCountry;// 所属国家

    @UpdateAnnotation
    private String custProvince;// 所属省

    @UpdateAnnotation
    private String custCity;// 所属市

    @UpdateAnnotation
    private String custDistrict;// 所属区

    @UpdateAnnotation
    private String custAddress;// 地址

    @UpdateAnnotation
    private String custPatientId;// ID

    @UpdateAnnotation
    private String custSikeId;// 病案号

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "cust_id", insertable = false, updatable = false)
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "cust_code")
    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    @Column(name = "cust_password")
    public String getCustPassword() {
        return custPassword;
    }

    public void setCustPassword(String custPassword) {
        this.custPassword = custPassword;
    }

    @Column(name = "cust_name")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Column(name = "cust_sex")
    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    @Column(name = "cust_phone")
    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    @Column(name = "cust_icard")
    public String getCustIcard() {
        return custIcard;
    }

    public void setCustIcard(String custIcard) {
        this.custIcard = custIcard;
    }

    @Column(name = "cust_weight")
    public BigDecimal getCustWeight() {
        return custWeight;
    }

    public void setCustWeight(BigDecimal custWeight) {
        this.custWeight = custWeight;
    }

    @Column(name = "cust_height")
    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    @Column(name = "cust_waistline")
    public BigDecimal getCustWaistline() {
        return custWaistline;
    }

    public void setCustWaistline(BigDecimal custWaistline) {
        this.custWaistline = custWaistline;
    }

    @Column(name = "cust_address")
    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    @Column(name = "cust_district")
    public String getCustDistrict() {
        return custDistrict;
    }

    public void setCustDistrict(String custDistrict) {
        this.custDistrict = custDistrict;
    }

    @Column(name = "cust_job")
    public String getCustJob() {
        return custJob;
    }

    public void setCustJob(String custJob) {
        this.custJob = custJob;
    }

    @Column(name = "cust_marriage")
    public String getCustMarriage() {
        return custMarriage;
    }

    public void setCustMarriage(String custMarriage) {
        this.custMarriage = custMarriage;
    }

    @Column(name = "cust_education")
    public String getCustEducation() {
        return custEducation;
    }

    public void setCustEducation(String custEducation) {
        this.custEducation = custEducation;
    }

    @Column(name = "cust_nation")
    public String getCustNation() {
        return custNation;
    }

    public void setCustNation(String custNation) {
        this.custNation = custNation;
    }

    @Column(name = "cust_income")
    public String getCustIncome() {
        return custIncome;
    }

    public void setCustIncome(String custIncome) {
        this.custIncome = custIncome;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "cust_birthday")
    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    @Column(name = "cust_country")
    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    @Column(name = "cust_province")
    public String getCustProvince() {
        return custProvince;
    }

    public void setCustProvince(String custProvince) {
        this.custProvince = custProvince;
    }

    @Column(name = "cust_city")
    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    @Column(name = "cust_plevel")
    public String getCustPlevel() {
        return custPlevel;
    }

    public void setCustPlevel(String custPlevel) {
        this.custPlevel = custPlevel;
    }

    @Column(name = "cust_bmi")
    public BigDecimal getCustBmi() {
        return custBmi;
    }

    public void setCustBmi(BigDecimal custBmi) {
        this.custBmi = custBmi;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cust_first_visit")
    public Date getCustFirstVisit() {
        return custFirstVisit;
    }

    public void setCustFirstVisit(Date custFirstVisit) {
        this.custFirstVisit = custFirstVisit;
    }

    @Column(name = "cust_birth_weight")
    public BigDecimal getCustBirthWeight() {
        return custBirthWeight;
    }

    public void setCustBirthWeight(BigDecimal custBirthWeight) {
        this.custBirthWeight = custBirthWeight;
    }

    @Column(name = "cust_patient_id")
    public String getCustPatientId() {
        return custPatientId;
    }

    public void setCustPatientId(String custPatientId) {
        this.custPatientId = custPatientId;
    }

    @Column(name = "cust_sike_id")
    public String getCustSikeId() {
        return custSikeId;
    }

    public void setCustSikeId(String custSikeId) {
        this.custSikeId = custSikeId;
    }

}
