
package com.mnt.preg.customer.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 分娩结局Bo
 * 
 * @author 王健超
 * @version 1.0
 * 
 *          变更履历： v1.0 2018-08-01 王健超 初版
 */
public class BirthEndingPojo implements Serializable {

    /** [属性说明] */
    private static final long serialVersionUID = 95356492692176367L;

    /**
     * @备注: 主键
     * @字段:birth_id VARCHAR(64)
     */
    @QueryFieldAnnotation
    private String birthId;

    /**
     * @备注: customer_id
     * @字段:cust_id VARCHAR(64)
     */
    @QueryFieldAnnotation
    private String custId;

    /**
     * @备注: customer_name
     * @字段:cust_id VARCHAR(64)
     */
    @QueryFieldAnnotation
    private String custName;

    /**
     * @备注: customer_bmi
     * @字段:cust_id VARCHAR(64)
     */
    @QueryFieldAnnotation
    private BigDecimal custBmi;

    /**
     * @备注: 生日
     * @字段:birth_birthday DATE(10)
     */
    @QueryFieldAnnotation
    private Date birthBirthday;

    /**
     * @备注: 身高
     * @字段:birth_height DECIMAL(7)
     */
    @QueryFieldAnnotation
    private BigDecimal birthHeight;

    /**
     * @备注: 孕前体重
     * @字段:birth_weight DECIMAL(7)
     */
    @QueryFieldAnnotation
    private BigDecimal birthWeight;

    /**
     * @备注: 住院日期
     * @字段:birth_hospital_date DATE(10)
     */
    @QueryFieldAnnotation
    private Date birthHospitalDate;

    /**
     * @备注: 住院号
     * @字段:birth_patient_date VARCHAR(20)
     */
    @QueryFieldAnnotation
    private String birthPatientId;

    /**
     * @备注: 分娩日期
     * @字段:birth_date DATE(10)
     */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    /**
     * @备注: 分娩时间_时
     * @字段:base_time DATETIME(19)
     */
    private Integer baseTimeHour;

    /**
     * @备注: 分娩时间_时_分
     * @字段:base_time DATETIME(19)
     */
    private Integer baseTimeMinuters;

    /**
     * @备注: 分娩孕周
     * @字段:birth_weeks VARCHAR(15)
     */
    private String birthWeeks;

    /**
     * @备注: 胎数
     * @字段:birth_tires_number INT(10)
     */
    @QueryFieldAnnotation
    private String birthTiresNumber;

    /**
     * @备注: 孕
     * @字段:birth_preg_number INT(10)
     */
    @QueryFieldAnnotation
    private Integer birthPregNumber;

    /**
     * @备注: 产
     * @字段:birth_born_number INT(10)
     */
    @QueryFieldAnnotation
    private Integer birthBornNumber;

    /**
     * @备注: 受孕方式（1：自然受孕；2：辅助生殖）
     * @字段:birth_tires_type INT(10)
     */
    @QueryFieldAnnotation
    private Integer birthTiresType;

    /**
     * @备注: 受孕方式（1：意外怀孕；2：计划妊娠） 只有当自然受孕时，才保存这个字段
     * @字段:birth_tires_type2 INT(10)
     */
    @QueryFieldAnnotation
    private Integer birthTiresType2;

    /**
     * @备注: 产检医院
     * @字段:birth_preg_hospital VARCHAR(50)
     */
    @QueryFieldAnnotation
    private String birthPregHospital;

    /**
     * @备注: 产检医院 是否本院
     * @字段:birth_preg_hospital VARCHAR(50)
     */
    @QueryFieldAnnotation
    private Integer birthIsPregHospital;

    /**
     * @备注: 分娩医院 是否本院
     * @字段:birth_hospital VARCHAR(50)
     */
    @QueryFieldAnnotation
    private Integer birthIsThisHospital;

    /**
     * @备注: 分娩医院
     * @字段:birth_hospital VARCHAR(50)
     */
    @QueryFieldAnnotation
    private String birthHospital;

    /**
     * @备注: 入院诊断
     * @字段:birth_diagnosis VARCHAR(60)
     */
    @QueryFieldAnnotation
    private String birthDiagnosis;

    /**
     * @备注: 基础数据备注
     * @字段:birth_base_remark VARCHAR(200)
     */
    @QueryFieldAnnotation
    private String birthBaseRemark;

    /**
     * @备注: 入院诊断备注
     * @字段:birth_diag_remark VARCHAR(200)
     */
    @QueryFieldAnnotation
    private String birthDiagRemark;

    /**
     * @备注: 建档id
     * @字段:archives_id
     */
    @QueryFieldAnnotation
    private String archivesId;

    /**
     * 登记时间
     */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @QueryFieldAnnotation
    private Date updateTime;

    @QueryFieldAnnotation
    private String createUserId;

    @QueryFieldAnnotation
    private String createUserName;

    @QueryFieldAnnotation
    private String updateUserId;

    @QueryFieldAnnotation
    private String createInsId;

    @QueryFieldAnnotation
    private Integer flag = 1;

    public String getBirthId() {
        return birthId;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getBirthBirthday() {
        return birthBirthday;
    }

    public void setBirthBirthday(Date birthBirthday) {
        this.birthBirthday = birthBirthday;
    }

    public BigDecimal getBirthHeight() {
        return birthHeight;
    }

    public void setBirthHeight(BigDecimal birthHeight) {
        this.birthHeight = birthHeight;
    }

    public BigDecimal getBirthWeight() {
        return birthWeight;
    }

    public void setBirthWeight(BigDecimal birthWeight) {
        this.birthWeight = birthWeight;
    }

    public Date getBirthHospitalDate() {
        return birthHospitalDate;
    }

    public void setBirthHospitalDate(Date birthHospitalDate) {
        this.birthHospitalDate = birthHospitalDate;
    }

    public String getBirthPatientId() {
        return birthPatientId;
    }

    public void setBirthPatientId(String birthPatientId) {
        this.birthPatientId = birthPatientId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getBaseTimeHour() {
        return baseTimeHour;
    }

    public void setBaseTimeHour(Integer baseTimeHour) {
        this.baseTimeHour = baseTimeHour;
    }

    public Integer getBaseTimeMinuters() {
        return baseTimeMinuters;
    }

    public void setBaseTimeMinuters(Integer baseTimeMinuters) {
        this.baseTimeMinuters = baseTimeMinuters;
    }

    public String getBirthWeeks() {
        return birthWeeks;
    }

    public void setBirthWeeks(String birthWeeks) {
        this.birthWeeks = birthWeeks;
    }

    public String getBirthTiresNumber() {
        return birthTiresNumber;
    }

    public void setBirthTiresNumber(String birthTiresNumber) {
        this.birthTiresNumber = birthTiresNumber;
    }

    public Integer getBirthPregNumber() {
        return birthPregNumber;
    }

    public void setBirthPregNumber(Integer birthPregNumber) {
        this.birthPregNumber = birthPregNumber;
    }

    public Integer getBirthBornNumber() {
        return birthBornNumber;
    }

    public void setBirthBornNumber(Integer birthBornNumber) {
        this.birthBornNumber = birthBornNumber;
    }

    public Integer getBirthTiresType() {
        return birthTiresType;
    }

    public void setBirthTiresType(Integer birthTiresType) {
        this.birthTiresType = birthTiresType;
    }

    public Integer getBirthTiresType2() {
        return birthTiresType2;
    }

    public void setBirthTiresType2(Integer birthTiresType2) {
        this.birthTiresType2 = birthTiresType2;
    }

    public String getBirthPregHospital() {
        return birthPregHospital;
    }

    public void setBirthPregHospital(String birthPregHospital) {
        this.birthPregHospital = birthPregHospital;
    }

    public String getBirthHospital() {
        return birthHospital;
    }

    public void setBirthHospital(String birthHospital) {
        this.birthHospital = birthHospital;
    }

    public String getBirthDiagnosis() {
        return birthDiagnosis;
    }

    public void setBirthDiagnosis(String birthDiagnosis) {
        this.birthDiagnosis = birthDiagnosis;
    }

    public String getBirthBaseRemark() {
        return birthBaseRemark;
    }

    public void setBirthBaseRemark(String birthBaseRemark) {
        this.birthBaseRemark = birthBaseRemark;
    }

    public String getBirthDiagRemark() {
        return birthDiagRemark;
    }

    public void setBirthDiagRemark(String birthDiagRemark) {
        this.birthDiagRemark = birthDiagRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getBirthIsPregHospital() {
        return birthIsPregHospital;
    }

    public void setBirthIsPregHospital(Integer birthIsPregHospital) {
        this.birthIsPregHospital = birthIsPregHospital;
    }

    public Integer getBirthIsThisHospital() {
        return birthIsThisHospital;
    }

    public void setBirthIsThisHospital(Integer birthIsThisHospital) {
        this.birthIsThisHospital = birthIsThisHospital;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public BigDecimal getCustBmi() {
        return custBmi;
    }

    public void setCustBmi(BigDecimal custBmi) {
        this.custBmi = custBmi;
    }

}
