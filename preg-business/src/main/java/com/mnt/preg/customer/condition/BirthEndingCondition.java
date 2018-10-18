
package com.mnt.preg.customer.condition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 分娩结局Bo
 * 
 * @author 王健超
 * @version 1.0
 * 
 *          变更履历： v1.0 2018-08-01 王健超 初版
 */
public class BirthEndingCondition extends PageCondition implements Serializable {

    /** [属性说明] */
    private static final long serialVersionUID = 3562701993221291866L;

    /**
     * @备注: 主键
     * @字段:birth_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthId;

    /**
     * @备注: customer_name
     * @字段:cust_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String custName;

    /**
     * @备注: customer_bmi
     * @字段:cust_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal custBmi;

    /**
     * @备注: customer_id
     * @字段:cust_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String custId;

    /**
     * @备注: 生日
     * @字段:birth_birthday DATE(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.util.Date birthBirthday;

    /**
     * @备注: 身高
     * @字段:birth_height DECIMAL(7)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal birthHeight;

    /**
     * @备注: 孕前体重
     * @字段:birth_weight DECIMAL(7)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal birthWeight;

    /**
     * @备注: 住院日期
     * @字段:birth_hospital_date DATE(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.util.Date birthHospitalDate;

    /**
     * @备注: 住院号
     * @字段:birth_patient_date VARCHAR(20)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthPatientId;

    /**
     * @备注: 分娩日期
     * @字段:birth_date DATE(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, aliasName = "BirthEndingBase", name = "base_time",
            order = OrderConstants.DESC)
    private java.util.Date birthDate;

    /**
     * @备注: 分娩孕周
     * @字段:birth_weeks VARCHAR(15)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthWeeks;

    /**
     * @备注: 胎数
     * @字段:birth_tires_number INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthTiresNumber;

    /**
     * @备注: 孕
     * @字段:birth_preg_number INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer birthPregNumber;

    /**
     * @备注: 产
     * @字段:birth_born_number INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer birthBornNumber;

    /**
     * @备注: 受孕方式（1：自然受孕；2：辅助生殖）
     * @字段:birth_tires_type INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer birthTiresType;

    /**
     * @备注: 受孕方式（1：意外怀孕；2：计划妊娠） 只有当自然受孕时，才保存这个字段
     * @字段:birth_tires_type2 INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer birthTiresType2;

    /**
     * @备注: 产检医院
     * @字段:birth_preg_hospital VARCHAR(50)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthPregHospital;

    /**
     * @备注: 产检医院 是否本院
     * @字段:birth_preg_hospital VARCHAR(50)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer birthIsPregHospital;

    /**
     * @备注: 分娩医院 是否本院
     * @字段:birth_hospital VARCHAR(50)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer birthIsThisHospital;

    /**
     * @备注: 分娩医院
     * @字段:birth_hospital VARCHAR(50)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthHospital;

    /**
     * @备注: 入院诊断
     * @字段:birth_diagnosis VARCHAR(60)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthDiagnosis;

    /**
     * @备注: 基础数据备注
     * @字段:birth_base_remark VARCHAR(200)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthBaseRemark;

    /**
     * @备注: 入院诊断备注
     * @字段:birth_diag_remark VARCHAR(200)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthDiagRemark;

    /**
     * @备注: 建档id
     * @字段:archives_id
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String archivesId;

    /**
     * 登记时间
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Date createTime;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Date updateTime;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String createUserId;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String createUserName;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String updateUserId;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String createInsId;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /**
     * @备注: 分娩结局id集合
     */
    @QueryConditionAnnotation(name = "birthId", symbol = SymbolConstants.IN)
    private List<String> birthIds;

    public java.lang.String getBirthId() {
        return birthId;
    }

    public void setBirthId(java.lang.String birthId) {
        this.birthId = birthId;
    }

    public java.lang.String getCustId() {
        return custId;
    }

    public void setCustId(java.lang.String custId) {
        this.custId = custId;
    }

    public java.util.Date getBirthBirthday() {
        return birthBirthday;
    }

    public void setBirthBirthday(java.util.Date birthBirthday) {
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

    public java.util.Date getBirthHospitalDate() {
        return birthHospitalDate;
    }

    public void setBirthHospitalDate(java.util.Date birthHospitalDate) {
        this.birthHospitalDate = birthHospitalDate;
    }

    public java.lang.String getBirthPatientId() {
        return birthPatientId;
    }

    public void setBirthPatientId(java.lang.String birthPatientId) {
        this.birthPatientId = birthPatientId;
    }

    public java.util.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.util.Date birthDate) {
        this.birthDate = birthDate;
    }

    public java.lang.String getBirthWeeks() {
        return birthWeeks;
    }

    public void setBirthWeeks(java.lang.String birthWeeks) {
        this.birthWeeks = birthWeeks;
    }

    public java.lang.String getBirthTiresNumber() {
        return birthTiresNumber;
    }

    public void setBirthTiresNumber(java.lang.String birthTiresNumber) {
        this.birthTiresNumber = birthTiresNumber;
    }

    public java.lang.Integer getBirthPregNumber() {
        return birthPregNumber;
    }

    public void setBirthPregNumber(java.lang.Integer birthPregNumber) {
        this.birthPregNumber = birthPregNumber;
    }

    public java.lang.Integer getBirthBornNumber() {
        return birthBornNumber;
    }

    public void setBirthBornNumber(java.lang.Integer birthBornNumber) {
        this.birthBornNumber = birthBornNumber;
    }

    public java.lang.Integer getBirthTiresType() {
        return birthTiresType;
    }

    public void setBirthTiresType(java.lang.Integer birthTiresType) {
        this.birthTiresType = birthTiresType;
    }

    public java.lang.Integer getBirthTiresType2() {
        return birthTiresType2;
    }

    public void setBirthTiresType2(java.lang.Integer birthTiresType2) {
        this.birthTiresType2 = birthTiresType2;
    }

    public java.lang.String getBirthPregHospital() {
        return birthPregHospital;
    }

    public void setBirthPregHospital(java.lang.String birthPregHospital) {
        this.birthPregHospital = birthPregHospital;
    }

    public java.lang.String getBirthHospital() {
        return birthHospital;
    }

    public void setBirthHospital(java.lang.String birthHospital) {
        this.birthHospital = birthHospital;
    }

    public java.lang.String getBirthDiagnosis() {
        return birthDiagnosis;
    }

    public void setBirthDiagnosis(java.lang.String birthDiagnosis) {
        this.birthDiagnosis = birthDiagnosis;
    }

    public java.lang.String getBirthBaseRemark() {
        return birthBaseRemark;
    }

    public void setBirthBaseRemark(java.lang.String birthBaseRemark) {
        this.birthBaseRemark = birthBaseRemark;
    }

    public java.lang.String getBirthDiagRemark() {
        return birthDiagRemark;
    }

    public void setBirthDiagRemark(java.lang.String birthDiagRemark) {
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

    public java.lang.String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(java.lang.String createUserName) {
        this.createUserName = createUserName;
    }

    public java.lang.Integer getBirthIsPregHospital() {
        return birthIsPregHospital;
    }

    public void setBirthIsPregHospital(java.lang.Integer birthIsPregHospital) {
        this.birthIsPregHospital = birthIsPregHospital;
    }

    public java.lang.Integer getBirthIsThisHospital() {
        return birthIsThisHospital;
    }

    public void setBirthIsThisHospital(java.lang.Integer birthIsThisHospital) {
        this.birthIsThisHospital = birthIsThisHospital;
    }

    public java.lang.String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(java.lang.String archivesId) {
        this.archivesId = archivesId;
    }

    public java.lang.String getCustName() {
        return custName;
    }

    public void setCustName(java.lang.String custName) {
        this.custName = custName;
    }

    public BigDecimal getCustBmi() {
        return custBmi;
    }

    public void setCustBmi(BigDecimal custBmi) {
        this.custBmi = custBmi;
    }

    public List<String> getBirthIds() {
        return birthIds;
    }

    public void setBirthIds(List<String> birthIds) {
        this.birthIds = birthIds;
    }

}
