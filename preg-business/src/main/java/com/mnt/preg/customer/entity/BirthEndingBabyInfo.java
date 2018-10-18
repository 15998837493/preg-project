
package com.mnt.preg.customer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

@Entity
@Table(name = "cus_birthending_babyinfo")
public class BirthEndingBabyInfo extends MappedEntity {

    /** [属性说明] */
    private static final long serialVersionUID = -5602580404486651951L;

    /**
     * @备注: 主键
     * @字段:baby_id VARCHAR(64)
     */
    private String babyId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    @UpdateAnnotation
    private String birthId;

    /**
     * @备注: 新生儿性别（1，男2，女3，不明）
     * @字段:baby_gender VARCHAR(12)
     */
    @UpdateAnnotation
    private String babyGender;

    /**
     * @备注: 出生时间
     * @字段:baby_birthTime DATE()
     */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date babyBirthTime;

    /**
     * @备注: 出生时间(时)
     * @字段:baby_birthTime_hour
     */
    @QueryFieldAnnotation
    private Integer babyBirthTimeHour;

    /**
     * @备注: 出生时间（分）
     * @字段:baby_birthTime_minutes
     */
    @QueryFieldAnnotation
    private Integer babyBirthTimeMinutes;

    /**
     * @备注: 身长
     * @字段:baby_length DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyLength;

    /**
     * @备注:
     * @字段:baby_weight DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyWeight;

    /**
     * @备注: 头围
     * @字段:baby_headCircum DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyHeadCircum;

    /**
     * @备注: 胸围
     * @字段:baby_bust DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyBust;

    /**
     * @备注: 1分钟阿氏评分
     * @字段:baby_Ashes_oneMinute DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyAshesOneMinute;

    /**
     * @备注: 5分钟阿氏评分
     * @字段:baby_Ashes_fiveMinutes DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyAshesFiveMinutes;

    /**
     * @备注: 10分钟阿氏评分
     * @字段:baby_Ashes_tenMinutes DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyAshesTenMinutes;

    /**
     * @备注: 窒息 （分钟）
     * @字段:baby_stifle DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyStifle;

    /**
     * @备注: 缺陷
     * @字段:baby_defect VARCHAR(1000)
     */
    @UpdateAnnotation
    private String babyDefect;

    /**
     * @备注: 抢救（1,有 0,无）
     * @字段:baby_rescue INT(11)
     */
    @UpdateAnnotation
    private Integer babyRescue;

    /**
     * @备注: 并发症
     * @字段:baby_complication VARCHAR(255)
     */
    @UpdateAnnotation
    private String babyComplication;

    /**
     * @备注: 指导
     * @字段:baby_guid VARCHAR(1000)
     */
    @UpdateAnnotation
    private String babyGuid;

    /**
     * @备注: 胎盘重
     * @字段:baby_placenta_weight DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyPlacentaWeight;

    /**
     * @备注: 胎盘长
     * @字段:baby_placenta_length DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyPlacentaLength;

    /**
     * @备注: 胎盘宽
     * @字段:baby_placenta_width DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyPlacentaWidth;

    /**
     * @备注: 胎盘厚
     * @字段:baby__placenta_thick DECIMAL(7,1)
     */
    @UpdateAnnotation
    private BigDecimal babyPlacentaThick;

    /**
     * @备注: 羊水量 （1：少；2：中；3：多）
     * @字段:baby_amnioticFluid_vol DECIMAL(7,1)
     */
    @UpdateAnnotation
    private Integer babyAmnioticFluidVol;

    /**
     * @备注: 羊水性状（1：清；2：浊）
     * @字段:baby_amnioticFluid_traits DECIMAL(7,1)
     */
    @UpdateAnnotation
    private Integer babyAmnioticFluidTraits;

    /**
     * @备注: 新生儿是否死亡（0：存活；1：死亡）
     * @字段:baby_isDeath INT(10)
     */
    @UpdateAnnotation
    private Integer babyIsDeath;

    /**
     * @备注: 死亡时间
     * @字段:baby_deathTime DATE()
     */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date babyDeathTime;

    /**
     * @备注: 死亡时间 （时）
     * @字段:baby_deathTime_hour
     */
    @QueryFieldAnnotation
    private Integer babyDeathTimeHour;

    /**
     * @备注: 死亡时间 （分）
     * @字段:baby_deathTime_minutes
     */
    @QueryFieldAnnotation
    private Integer babyDeathTimeMinutes;

    /**
     * @备注: 死亡原因
     * @字段:baby_amnioticFluid_deathReason VARCHAR(1000)
     */
    @UpdateAnnotation
    private String babyAmnioticFluidDeathReason;

    /**
     * @备注: 备注
     * @字段:baby_remark VARCHAR(1000)
     */
    @UpdateAnnotation
    private String babyRemark;

    public BirthEndingBabyInfo() {
    }

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "baby_id", insertable = false, updatable = false)
    public String getBabyId() {
        return this.babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    @Column(name = "birth_id")
    public String getBirthId() {
        return this.birthId;
    }

    public void setBabyGender(String babyGender) {
        this.babyGender = babyGender;
    }

    @Column(name = "baby_gender")
    public String getBabyGender() {
        return this.babyGender;
    }

    public void setBabyBirthTime(Date babyBirthTime) {
        this.babyBirthTime = babyBirthTime;
    }

    @Column(name = "baby_birth_time")
    public Date getBabyBirthTime() {
        return this.babyBirthTime;
    }

    public void setBabyLength(BigDecimal babyLength) {
        this.babyLength = babyLength;
    }

    @Column(name = "baby_length")
    public BigDecimal getBabyLength() {
        return this.babyLength;
    }

    public void setBabyWeight(BigDecimal babyWeight) {
        this.babyWeight = babyWeight;
    }

    @Column(name = "baby_weight")
    public BigDecimal getBabyWeight() {
        return this.babyWeight;
    }

    public void setBabyHeadCircum(BigDecimal babyHeadCircum) {
        this.babyHeadCircum = babyHeadCircum;
    }

    @Column(name = "baby_head_circum")
    public BigDecimal getBabyHeadCircum() {
        return this.babyHeadCircum;
    }

    public void setBabyBust(BigDecimal babyBust) {
        this.babyBust = babyBust;
    }

    @Column(name = "baby_bust")
    public BigDecimal getBabyBust() {
        return this.babyBust;
    }

    public void setBabyAshesOneMinute(BigDecimal babyAshesOneMinute) {
        this.babyAshesOneMinute = babyAshesOneMinute;
    }

    @Column(name = "baby_Ashes_one_minute")
    public BigDecimal getBabyAshesOneMinute() {
        return this.babyAshesOneMinute;
    }

    public void setBabyAshesFiveMinutes(BigDecimal babyAshesFiveMinutes) {
        this.babyAshesFiveMinutes = babyAshesFiveMinutes;
    }

    @Column(name = "baby_Ashes_five_minutes")
    public BigDecimal getBabyAshesFiveMinutes() {
        return this.babyAshesFiveMinutes;
    }

    public void setBabyAshesTenMinutes(BigDecimal babyAshesTenMinutes) {
        this.babyAshesTenMinutes = babyAshesTenMinutes;
    }

    @Column(name = "baby_Ashes_ten_minutes")
    public BigDecimal getBabyAshesTenMinutes() {
        return this.babyAshesTenMinutes;
    }

    public void setBabyStifle(BigDecimal babyStifle) {
        this.babyStifle = babyStifle;
    }

    @Column(name = "baby_stifle")
    public BigDecimal getBabyStifle() {
        return this.babyStifle;
    }

    public void setBabyDefect(String babyDefect) {
        this.babyDefect = babyDefect;
    }

    @Column(name = "baby_defect")
    public String getBabyDefect() {
        return this.babyDefect;
    }

    public void setBabyRescue(Integer babyRescue) {
        this.babyRescue = babyRescue;
    }

    @Column(name = "baby_rescue")
    public Integer getBabyRescue() {
        return this.babyRescue;
    }

    public void setBabyComplication(String babyComplication) {
        this.babyComplication = babyComplication;
    }

    @Column(name = "baby_complication")
    public String getBabyComplication() {
        return this.babyComplication;
    }

    public void setBabyGuid(String babyGuid) {
        this.babyGuid = babyGuid;
    }

    @Column(name = "baby_guid")
    public String getBabyGuid() {
        return this.babyGuid;
    }

    public void setBabyPlacentaWeight(BigDecimal babyPlacentaWeight) {
        this.babyPlacentaWeight = babyPlacentaWeight;
    }

    @Column(name = "baby_placenta_weight")
    public BigDecimal getBabyPlacentaWeight() {
        return this.babyPlacentaWeight;
    }

    public void setBabyPlacentaLength(BigDecimal babyPlacentaLength) {
        this.babyPlacentaLength = babyPlacentaLength;
    }

    @Column(name = "baby_placenta_length")
    public BigDecimal getBabyPlacentaLength() {
        return this.babyPlacentaLength;
    }

    public void setBabyPlacentaWidth(BigDecimal babyPlacentaWidth) {
        this.babyPlacentaWidth = babyPlacentaWidth;
    }

    @Column(name = "baby_placenta_width")
    public BigDecimal getBabyPlacentaWidth() {
        return this.babyPlacentaWidth;
    }

    public void setBabyPlacentaThick(BigDecimal babyPlacentaThick) {
        this.babyPlacentaThick = babyPlacentaThick;
    }

    @Column(name = "baby_placenta_thick")
    public BigDecimal getBabyPlacentaThick() {
        return this.babyPlacentaThick;
    }

    public void setBabyAmnioticFluidVol(Integer babyAmnioticFluidVol) {
        this.babyAmnioticFluidVol = babyAmnioticFluidVol;
    }

    @Column(name = "baby_amniotic_fluid_vol")
    public Integer getBabyAmnioticFluidVol() {
        return this.babyAmnioticFluidVol;
    }

    public void setBabyAmnioticFluidTraits(Integer babyAmnioticFluidTraits) {
        this.babyAmnioticFluidTraits = babyAmnioticFluidTraits;
    }

    @Column(name = "baby_amniotic_fluid_traits")
    public Integer getBabyAmnioticFluidTraits() {
        return this.babyAmnioticFluidTraits;
    }

    public void setBabyIsDeath(Integer babyIsDeath) {
        this.babyIsDeath = babyIsDeath;
    }

    @Column(name = "baby_is_death")
    public Integer getBabyIsDeath() {
        return this.babyIsDeath;
    }

    public void setBabyDeathTime(Date babyDeathTime) {
        this.babyDeathTime = babyDeathTime;
    }

    @Column(name = "baby_death_time")
    public Date getBabyDeathTime() {
        return this.babyDeathTime;
    }

    public void setBabyAmnioticFluidDeathReason(String babyAmnioticFluidDeathReason) {
        this.babyAmnioticFluidDeathReason = babyAmnioticFluidDeathReason;
    }

    @Column(name = "baby_amniotic_fluid_death_reason")
    public String getBabyAmnioticFluidDeathReason() {
        return this.babyAmnioticFluidDeathReason;
    }

    public void setBabyRemark(String babyRemark) {
        this.babyRemark = babyRemark;
    }

    @Column(name = "baby_remark")
    public String getBabyRemark() {
        return this.babyRemark;
    }

    @Column(name = "baby_birth_time_hour")
    public Integer getBabyBirthTimeHour() {
        return babyBirthTimeHour;
    }

    public void setBabyBirthTimeHour(Integer babyBirthTimeHour) {
        this.babyBirthTimeHour = babyBirthTimeHour;
    }

    @Column(name = "baby_birth_time_minutes")
    public Integer getBabyBirthTimeMinutes() {
        return babyBirthTimeMinutes;
    }

    public void setBabyBirthTimeMinutes(Integer babyBirthTimeMinutes) {
        this.babyBirthTimeMinutes = babyBirthTimeMinutes;
    }

    @Column(name = "baby_death_time_hour")
    public Integer getBabyDeathTimeHour() {
        return babyDeathTimeHour;
    }

    public void setBabyDeathTimeHour(Integer babyDeathTimeHour) {
        this.babyDeathTimeHour = babyDeathTimeHour;
    }

    @Column(name = "baby_death_time_minutes")
    public Integer getBabyDeathTimeMinutes() {
        return babyDeathTimeMinutes;
    }

    public void setBabyDeathTimeMinutes(Integer babyDeathTimeMinutes) {
        this.babyDeathTimeMinutes = babyDeathTimeMinutes;
    }

}
