
package com.mnt.preg.customer.form;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BirthEndingBabyInfoForm {

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    private String birthId;

    /**
     * @备注: 主键
     * @字段:baby_id VARCHAR(64)
     */
    private String babyId;

    /**
     * @备注: 新生儿性别（1，男2，女3，不明）
     * @字段:baby_gender VARCHAR(12)
     */
    private String babyGender;

    /**
     * @备注: 出生时间
     * @字段:baby_birthTime DATE()
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date babyBirthTime;

    /**
     * @备注: 出生时间(时)
     * @字段:baby_birthTime_hour
     */
    private Integer babyBirthTimeHour;

    /**
     * @备注: 出生时间（分）
     * @字段:baby_birthTime_minutes
     */
    private Integer babyBirthTimeMinutes;

    /**
     * @备注: 身长cm
     * @字段:baby_length DECIMAL(7,1)
     */
    private BigDecimal babyLength;

    /**
     * @备注: 体重cm
     * @字段:baby_weight DECIMAL(7,1)
     */
    private BigDecimal babyWeight;

    /**
     * @备注: 头围cm
     * @字段:baby_headCircum DECIMAL(7,1)
     */
    private BigDecimal babyHeadCircum;

    /**
     * @备注: 胸围cm
     * @字段:baby_bust DECIMAL(7,1)
     */
    private BigDecimal babyBust;

    /**
     * @备注: 1分钟阿氏评分(分)
     * @字段:baby_Ashes_oneMinute DECIMAL(7,1)
     */
    private BigDecimal babyAshesOneMinute;

    /**
     * @备注: 5分钟阿氏评分(分)
     * @字段:baby_Ashes_fiveMinutes DECIMAL(7,1)
     */
    private BigDecimal babyAshesFiveMinutes;

    /**
     * @备注: 10分钟阿氏评分(分)
     * @字段:baby_Ashes_tenMinutes DECIMAL(7,1)
     */
    private BigDecimal babyAshesTenMinutes;

    /**
     * @备注: 窒息 （分钟）
     * @字段:baby_stifle DECIMAL(7,1)
     */
    private BigDecimal babyStifle;

    /**
     * @备注: 缺陷
     * @字段:baby_defect VARCHAR(100)
     */
    private String babyDefect;

    /**
     * @备注: 抢救（1,有 0,无）
     * @字段:baby_rescue INT(11)
     */
    private Integer babyRescue;

    /**
     * @备注: 并发症
     * @字段:baby_complication VARCHAR(255)
     */
    private String babyComplication;

    /**
     * @备注: 新生儿指导
     * @字段:baby_guid VARCHAR(1000)
     */
    private String babyGuid;

    /**
     * @备注: 胎盘重(cm)
     * @字段:baby_placenta_weight DECIMAL(7,1)
     */
    private BigDecimal babyPlacentaWeight;

    /**
     * @备注: 胎盘长(cm)
     * @字段:baby_placenta_length DECIMAL(7,1)
     */
    private BigDecimal babyPlacentaLength;

    /**
     * @备注: 胎盘宽(cm)
     * @字段:baby_placenta_width DECIMAL(7,1)
     */
    private BigDecimal babyPlacentaWidth;

    /**
     * @备注: 胎盘厚(cm)
     * @字段:baby__placenta_thick DECIMAL(7,1)
     */
    private BigDecimal babyPlacentaThick;

    /**
     * @备注: 羊水量 （1：少；2：中；3：多）
     * @字段:baby_amnioticFluid_vol DECIMAL(7,1)
     */
    private Integer babyAmnioticFluidVol;

    /**
     * @备注: 羊水性状（1：清；2：浊）
     * @字段:baby_amnioticFluid_traits DECIMAL(7,1)
     */
    private Integer babyAmnioticFluidTraits;

    /**
     * @备注: 新生儿是否死亡（0：存活；1：死亡）
     * @字段:baby_isDeath INT(10)
     */
    private Integer babyIsDeath;

    /**
     * @备注: 死亡时间
     * @字段:baby_deathTime DATE()
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date babyDeathTime;

    /**
     * @备注: 死亡时间 （时）
     * @字段:baby_deathTime_hour
     */
    private Integer babyDeathTimeHour;

    /**
     * @备注: 死亡时间 （分）
     * @字段:baby_deathTime_minutes
     */
    private Integer babyDeathTimeMinutes;

    /**
     * @备注: 死亡原因
     * @字段:baby_amnioticFluid_deathReason VARCHAR(100)
     */
    private String babyAmnioticFluidDeathReason;

    /**
     * @备注: 备注
     * @字段:baby_remark VARCHAR(1000)
     */
    private String babyRemark;

    public BirthEndingBabyInfoForm() {
    }

    public BirthEndingBabyInfoForm(String babyId, String birthId, String babyGender, Date babyBirthTime,
            BigDecimal babyLength, BigDecimal babyWeight,
            BigDecimal babyHeadCircum, BigDecimal babyBust, BigDecimal babyAshesOneMinute,
            BigDecimal babyAshesFiveMinutes, BigDecimal babyAshesTenMinutes, BigDecimal babyStifle, String babyDefect,
            Integer babyRescue, String babyComplication, String babyGuid, BigDecimal babyPlacentaWeight,
            BigDecimal babyPlacentaLength, BigDecimal babyPlacentaWidth, BigDecimal babyPlacentaThick,
            Integer babyAmnioticFluidVol, Integer babyAmnioticFluidTraits, Integer babyIsDeath,
            Date babyDeathTime, String babyAmnioticFluidDeathReason, String babyRemark) {
        super();
        this.babyId = babyId;
        this.birthId = birthId;
        this.babyGender = babyGender;
        this.babyBirthTime = babyBirthTime;
        this.babyLength = babyLength;
        this.babyWeight = babyWeight;
        this.babyHeadCircum = babyHeadCircum;
        this.babyBust = babyBust;
        this.babyAshesOneMinute = babyAshesOneMinute;
        this.babyAshesFiveMinutes = babyAshesFiveMinutes;
        this.babyAshesTenMinutes = babyAshesTenMinutes;
        this.babyStifle = babyStifle;
        this.babyDefect = babyDefect;
        this.babyRescue = babyRescue;
        this.babyComplication = babyComplication;
        this.babyGuid = babyGuid;
        this.babyPlacentaWeight = babyPlacentaWeight;
        this.babyPlacentaLength = babyPlacentaLength;
        this.babyPlacentaWidth = babyPlacentaWidth;
        this.babyPlacentaThick = babyPlacentaThick;
        this.babyAmnioticFluidVol = babyAmnioticFluidVol;
        this.babyAmnioticFluidTraits = babyAmnioticFluidTraits;
        this.babyIsDeath = babyIsDeath;
        this.babyDeathTime = babyDeathTime;
        this.babyAmnioticFluidDeathReason = babyAmnioticFluidDeathReason;
        this.babyRemark = babyRemark;
    }

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public String getBirthId() {
        return birthId;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    public String getBabyGender() {
        return babyGender;
    }

    public void setBabyGender(String babyGender) {
        this.babyGender = babyGender;
    }

    public Date getBabyBirthTime() {
        return babyBirthTime;
    }

    public void setBabyBirthTime(Date babyBirthTime) {
        this.babyBirthTime = babyBirthTime;
    }

    public BigDecimal getBabyLength() {
        return babyLength;
    }

    public void setBabyLength(BigDecimal babyLength) {
        this.babyLength = babyLength;
    }

    public BigDecimal getBabyWeight() {
        return babyWeight;
    }

    public void setBabyWeight(BigDecimal babyWeight) {
        this.babyWeight = babyWeight;
    }

    public BigDecimal getBabyHeadCircum() {
        return babyHeadCircum;
    }

    public void setBabyHeadCircum(BigDecimal babyHeadCircum) {
        this.babyHeadCircum = babyHeadCircum;
    }

    public BigDecimal getBabyBust() {
        return babyBust;
    }

    public void setBabyBust(BigDecimal babyBust) {
        this.babyBust = babyBust;
    }

    public BigDecimal getBabyAshesOneMinute() {
        return babyAshesOneMinute;
    }

    public void setBabyAshesOneMinute(BigDecimal babyAshesOneMinute) {
        this.babyAshesOneMinute = babyAshesOneMinute;
    }

    public BigDecimal getBabyAshesFiveMinutes() {
        return babyAshesFiveMinutes;
    }

    public void setBabyAshesFiveMinutes(BigDecimal babyAshesFiveMinutes) {
        this.babyAshesFiveMinutes = babyAshesFiveMinutes;
    }

    public BigDecimal getBabyAshesTenMinutes() {
        return babyAshesTenMinutes;
    }

    public void setBabyAshesTenMinutes(BigDecimal babyAshesTenMinutes) {
        this.babyAshesTenMinutes = babyAshesTenMinutes;
    }

    public BigDecimal getBabyStifle() {
        return babyStifle;
    }

    public void setBabyStifle(BigDecimal babyStifle) {
        this.babyStifle = babyStifle;
    }

    public String getBabyDefect() {
        return babyDefect;
    }

    public void setBabyDefect(String babyDefect) {
        this.babyDefect = babyDefect;
    }

    public Integer getBabyRescue() {
        return babyRescue;
    }

    public void setBabyRescue(Integer babyRescue) {
        this.babyRescue = babyRescue;
    }

    public String getBabyComplication() {
        return babyComplication;
    }

    public void setBabyComplication(String babyComplication) {
        this.babyComplication = babyComplication;
    }

    public String getBabyGuid() {
        return babyGuid;
    }

    public void setBabyGuid(String babyGuid) {
        this.babyGuid = babyGuid;
    }

    public BigDecimal getBabyPlacentaWeight() {
        return babyPlacentaWeight;
    }

    public void setBabyPlacentaWeight(BigDecimal babyPlacentaWeight) {
        this.babyPlacentaWeight = babyPlacentaWeight;
    }

    public BigDecimal getBabyPlacentaLength() {
        return babyPlacentaLength;
    }

    public void setBabyPlacentaLength(BigDecimal babyPlacentaLength) {
        this.babyPlacentaLength = babyPlacentaLength;
    }

    public BigDecimal getBabyPlacentaWidth() {
        return babyPlacentaWidth;
    }

    public void setBabyPlacentaWidth(BigDecimal babyPlacentaWidth) {
        this.babyPlacentaWidth = babyPlacentaWidth;
    }

    public BigDecimal getBabyPlacentaThick() {
        return babyPlacentaThick;
    }

    public void setBabyPlacentaThick(BigDecimal babyPlacentaThick) {
        this.babyPlacentaThick = babyPlacentaThick;
    }

    public Integer getBabyAmnioticFluidVol() {
        return babyAmnioticFluidVol;
    }

    public void setBabyAmnioticFluidVol(Integer babyAmnioticFluidVol) {
        this.babyAmnioticFluidVol = babyAmnioticFluidVol;
    }

    public Integer getBabyAmnioticFluidTraits() {
        return babyAmnioticFluidTraits;
    }

    public void setBabyAmnioticFluidTraits(Integer babyAmnioticFluidTraits) {
        this.babyAmnioticFluidTraits = babyAmnioticFluidTraits;
    }

    public Integer getBabyIsDeath() {
        return babyIsDeath;
    }

    public void setBabyIsDeath(Integer babyIsDeath) {
        this.babyIsDeath = babyIsDeath;
    }

    public String getBabyAmnioticFluidDeathReason() {
        return babyAmnioticFluidDeathReason;
    }

    public void setBabyAmnioticFluidDeathReason(String babyAmnioticFluidDeathReason) {
        this.babyAmnioticFluidDeathReason = babyAmnioticFluidDeathReason;
    }

    public String getBabyRemark() {
        return babyRemark;
    }

    public void setBabyRemark(String babyRemark) {
        this.babyRemark = babyRemark;
    }

    public Date getBabyDeathTime() {
        return babyDeathTime;
    }

    public void setBabyDeathTime(Date babyDeathTime) {
        this.babyDeathTime = babyDeathTime;
    }

    public Integer getBabyBirthTimeHour() {
        return babyBirthTimeHour;
    }

    public void setBabyBirthTimeHour(Integer babyBirthTimeHour) {
        this.babyBirthTimeHour = babyBirthTimeHour;
    }

    public Integer getBabyBirthTimeMinutes() {
        return babyBirthTimeMinutes;
    }

    public void setBabyBirthTimeMinutes(Integer babyBirthTimeMinutes) {
        this.babyBirthTimeMinutes = babyBirthTimeMinutes;
    }

    public Integer getBabyDeathTimeHour() {
        return babyDeathTimeHour;
    }

    public void setBabyDeathTimeHour(Integer babyDeathTimeHour) {
        this.babyDeathTimeHour = babyDeathTimeHour;
    }

    public Integer getBabyDeathTimeMinutes() {
        return babyDeathTimeMinutes;
    }

    public void setBabyDeathTimeMinutes(Integer babyDeathTimeMinutes) {
        this.babyDeathTimeMinutes = babyDeathTimeMinutes;
    }

}
