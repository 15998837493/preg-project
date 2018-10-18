
package com.mnt.preg.customer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 分娩结局Bo
 * 
 * @author 王健超
 * @version 1.0
 * 
 *          变更履历： v1.0 2018-08-01 王健超 初版
 */
@Entity
@Table(name = "cus_birthending")
public class BirthEnding extends MappedEntity {

    private static final long serialVersionUID = 1L;

    /**
     * @备注: 主键
     * @字段:birth_id VARCHAR(64)
     */
    private String birthId;

    /**
     * @备注: customer_id
     * @字段:cust_id VARCHAR(64)
     */
    @UpdateAnnotation
    private String custId;

    /**
     * @备注: customer_name
     * @字段:cust_id VARCHAR(64)
     */
    @UpdateAnnotation
    private String custName;

    /**
     * @备注: customer_bmi
     * @字段:cust_id VARCHAR(64)
     */
    @UpdateAnnotation
    private BigDecimal custBmi;

    /**
     * @备注: 生日
     * @字段:birth_birthday DATE(10)
     */
    @UpdateAnnotation
    private Date birthBirthday;

    /**
     * @备注: 身高
     * @字段:birth_height DECIMAL(7)
     */
    @UpdateAnnotation
    private BigDecimal birthHeight;

    /**
     * @备注: 孕前体重
     * @字段:birth_weight DECIMAL(7)
     */
    @UpdateAnnotation
    private BigDecimal birthWeight;

    /**
     * @备注: 住院日期
     * @字段:birth_hospital_date DATE(10)
     */
    @UpdateAnnotation
    private Date birthHospitalDate;

    /**
     * @备注: 住院号
     * @字段:birth_patient_date VARCHAR(20)
     */
    @UpdateAnnotation
    private String birthPatientId;

    /**
     * @备注: 胎数
     * @字段:birth_tires_number INT(10)
     */
    @UpdateAnnotation
    private String birthTiresNumber;

    /**
     * @备注: 孕
     * @字段:birth_preg_number INT(10)
     */
    @UpdateAnnotation
    private Integer birthPregNumber;

    /**
     * @备注: 产
     * @字段:birth_born_number INT(10)
     */
    @UpdateAnnotation
    private Integer birthBornNumber;

    /**
     * @备注: 受孕方式（1：自然受孕；2：辅助生殖）
     * @字段:birth_tires_type INT(10)
     */
    @UpdateAnnotation
    private Integer birthTiresType;

    /**
     * @备注: 受孕方式（1：意外怀孕；2：计划妊娠） 只有当自然受孕时，才保存这个字段
     * @字段:birth_tires_type2 INT(10)
     */
    @UpdateAnnotation
    private Integer birthTiresType2;

    /**
     * @备注: 产检医院
     * @字段:birth_preg_hospital VARCHAR(50)
     */
    @UpdateAnnotation
    private String birthPregHospital;

    /**
     * @备注: 产检医院 是否本院
     * @字段:birth_preg_hospital VARCHAR(50)
     */
    @UpdateAnnotation
    private Integer birthIsPregHospital;

    /**
     * @备注: 分娩医院
     * @字段:birth_hospital VARCHAR(50)
     */
    @UpdateAnnotation
    private String birthHospital;

    /**
     * @备注: 分娩医院 是否本院
     * @字段:birth_hospital VARCHAR(50)
     */
    @UpdateAnnotation
    private Integer birthIsThisHospital;

    /**
     * @备注: 入院诊断
     * @字段:birth_diagnosis VARCHAR(60)
     */
    @UpdateAnnotation
    private String birthDiagnosis;

    /**
     * @备注: 基础数据备注
     * @字段:birth_base_remark VARCHAR(200)
     */
    @UpdateAnnotation
    private String birthBaseRemark;

    /**
     * @备注: 入院诊断备注
     * @字段:birth_diag_remark VARCHAR(200)
     */
    @UpdateAnnotation
    private String birthDiagRemark;

    /**
     * @备注: 登记人员姓名
     * @字段:birth_diag_remark VARCHAR(200)
     */
    @UpdateAnnotation
    private String createUserName;

    /**
     * @备注: 建档id
     * @字段:archives_id
     */
    @UpdateAnnotation
    private String archivesId;

    public BirthEnding() {
    }

    public BirthEnding(
            String birthId) {
        this.birthId = birthId;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "birth_id", insertable = false, updatable = false)
    public String getBirthId() {
        return this.birthId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "cust_id")
    public String getCustId() {
        return this.custId;
    }

    public void setBirthBirthday(Date birthBirthday) {
        this.birthBirthday = birthBirthday;
    }

    @Column(name = "birth_birthday")
    public Date getBirthBirthday() {
        return this.birthBirthday;
    }

    public void setBirthHeight(BigDecimal birthHeight) {
        this.birthHeight = birthHeight;
    }

    @Column(name = "birth_height")
    public BigDecimal getBirthHeight() {
        return this.birthHeight;
    }

    public void setBirthWeight(BigDecimal birthWeight) {
        this.birthWeight = birthWeight;
    }

    @Column(name = "birth_weight")
    public BigDecimal getBirthWeight() {
        return this.birthWeight;
    }

    public void setBirthHospitalDate(Date birthHospitalDate) {
        this.birthHospitalDate = birthHospitalDate;
    }

    @Column(name = "birth_hospital_date")
    public Date getBirthHospitalDate() {
        return this.birthHospitalDate;
    }

    @Column(name = "birth_patient_id")
    public String getBirthPatientId() {
        return birthPatientId;
    }

    public void setBirthPatientId(String birthPatientId) {
        this.birthPatientId = birthPatientId;
    }

    public void setBirthTiresNumber(String birthTiresNumber) {
        this.birthTiresNumber = birthTiresNumber;
    }

    @Column(name = "birth_tires_number")
    public String getBirthTiresNumber() {
        return this.birthTiresNumber;
    }

    public void setBirthPregNumber(Integer birthPregNumber) {
        this.birthPregNumber = birthPregNumber;
    }

    @Column(name = "birth_preg_number")
    public Integer getBirthPregNumber() {
        return this.birthPregNumber;
    }

    public void setBirthBornNumber(Integer birthBornNumber) {
        this.birthBornNumber = birthBornNumber;
    }

    @Column(name = "birth_born_number")
    public Integer getBirthBornNumber() {
        return this.birthBornNumber;
    }

    public void setBirthTiresType(Integer birthTiresType) {
        this.birthTiresType = birthTiresType;
    }

    @Column(name = "birth_tires_type")
    public Integer getBirthTiresType() {
        return this.birthTiresType;
    }

    public void setBirthTiresType2(Integer birthTiresType2) {
        this.birthTiresType2 = birthTiresType2;
    }

    @Column(name = "birth_tires_type2")
    public Integer getBirthTiresType2() {
        return this.birthTiresType2;
    }

    public void setBirthPregHospital(String birthPregHospital) {
        this.birthPregHospital = birthPregHospital;
    }

    @Column(name = "birth_preg_hospital")
    public String getBirthPregHospital() {
        return this.birthPregHospital;
    }

    public void setBirthHospital(String birthHospital) {
        this.birthHospital = birthHospital;
    }

    @Column(name = "birth_hospital")
    public String getBirthHospital() {
        return this.birthHospital;
    }

    public void setBirthDiagnosis(String birthDiagnosis) {
        this.birthDiagnosis = birthDiagnosis;
    }

    @Column(name = "birth_diagnosis")
    public String getBirthDiagnosis() {
        return this.birthDiagnosis;
    }

    public void setBirthBaseRemark(String birthBaseRemark) {
        this.birthBaseRemark = birthBaseRemark;
    }

    @Column(name = "birth_base_remark")
    public String getBirthBaseRemark() {
        return this.birthBaseRemark;
    }

    public void setBirthDiagRemark(String birthDiagRemark) {
        this.birthDiagRemark = birthDiagRemark;
    }

    @Column(name = "birth_diag_remark")
    public String getBirthDiagRemark() {
        return this.birthDiagRemark;
    }

    @Column(name = "birth_is_preg_hospital")
    public Integer getBirthIsPregHospital() {
        return birthIsPregHospital;
    }

    public void setBirthIsPregHospital(Integer birthIsPregHospital) {
        this.birthIsPregHospital = birthIsPregHospital;
    }

    @Column(name = "birth_is_this_hospital")
    public Integer getBirthIsThisHospital() {
        return birthIsThisHospital;
    }

    public void setBirthIsThisHospital(Integer birthIsThisHospital) {
        this.birthIsThisHospital = birthIsThisHospital;
    }

    @Column(name = "create_user_name")
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Column(name = "archives_id")
    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    @Column(name = "cust_name")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Column(name = "cust_bmi")
    public BigDecimal getCustBmi() {
        return custBmi;
    }

    public void setCustBmi(BigDecimal custBmi) {
        this.custBmi = custBmi;
    }

}
