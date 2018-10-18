
package com.mnt.preg.customer.condition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

public class BirthEndingBabyInfoCondition extends PageCondition implements Serializable {

    /** [属性说明] */
    private static final long serialVersionUID = -618195485375576985L;

    /**
     * @备注: 主键
     * @字段:baby_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String birthId;

    /**
     * @备注: 主表id集合
     * @字段:birth_id VARCHAR(64)
     */
    @QueryConditionAnnotation(name = "birthId", symbol = SymbolConstants.IN)
    private List<String> birthIds;

    /**
     * @备注: 新生儿性别（1，男2，女3，不明）
     * @字段:baby_gender VARCHAR(12)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyGender;

    /**
     * @备注: 出生时间
     * @字段:baby_birthTime DATE()
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Date babyBirthTime;

    /**
     * @备注: 出生时间(时)
     * @字段:baby_birthTime_hour
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer babyBirthTimeHour;

    /**
     * @备注: 出生时间（分）
     * @字段:baby_birthTime_minutes
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer babyBirthTimeMinutes;

    /**
     * @备注: 身长
     * @字段:baby_length DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyLength;

    /**
     * @备注:
     * @字段:baby_weight DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyWeight;

    /**
     * @备注: 头围
     * @字段:baby_headCircum DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyHeadCircum;

    /**
     * @备注: 胸围
     * @字段:baby_bust DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyBust;

    /**
     * @备注: 1分钟阿氏评分
     * @字段:baby_Ashes_oneMinute DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyAshesOneMinute;

    /**
     * @备注: 5分钟阿氏评分
     * @字段:baby_Ashes_fiveMinutes DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyAshesFiveMinutes;

    /**
     * @备注: 10分钟阿氏评分
     * @字段:baby_Ashes_tenMinutes DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyAshesTenMinutes;

    /**
     * @备注: 窒息 （分钟）
     * @字段:baby_stifle DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyStifle;

    /**
     * @备注: 缺陷
     * @字段:baby_defect VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyDefect;

    /**
     * @备注: 抢救（1,有 0,无）
     * @字段:baby_rescue INT(11)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer babyRescue;

    /**
     * @备注: 并发症
     * @字段:baby_complication VARCHAR(255)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyComplication;

    /**
     * @备注: 指导
     * @字段:baby_guid VARCHAR(1000)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyGuid;

    /**
     * @备注: 胎盘重
     * @字段:baby_placenta_weight DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyPlacentaWeight;

    /**
     * @备注: 胎盘长
     * @字段:baby_placenta_length DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyPlacentaLength;

    /**
     * @备注: 胎盘宽
     * @字段:baby_placenta_width DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyPlacentaWidth;

    /**
     * @备注: 胎盘厚
     * @字段:baby__placenta_thick DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal babyPlacentaThick;

    /**
     * @备注: 羊水量 （1：多；2：中；3：少）
     * @字段:baby_amnioticFluid_vol DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer babyAmnioticFluidVol;

    /**
     * @备注: 羊水性状（1：清；2：浊）
     * @字段:baby_amnioticFluid_traits DECIMAL(7,1)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer babyAmnioticFluidTraits;

    /**
     * @备注: 新生儿是否死亡（1：是；0：否）
     * @字段:baby_isDeath INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private Integer babyIsDeath;

    /**
     * @备注: 死亡时间
     * @字段:baby_deathTime DATE()
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Date babyDeathTime;

    /**
     * @备注: 死亡时间 （时）
     * @字段:baby_deathTime_hour
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer babyDeathTimeHour;

    /**
     * @备注: 死亡时间 （分）
     * @字段:baby_deathTime_minutes
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer babyDeathTimeMinutes;

    /**
     * @备注: 死亡原因
     * @字段:baby_amnioticFluid_deathReason VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyAmnioticFluidDeathReason;

    /**
     * @备注: 备注
     * @字段:baby_remark VARCHAR(1000)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String babyRemark;

    // 标识
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public BirthEndingBabyInfoCondition() {
    }

    public BirthEndingBabyInfoCondition(
            String babyId) {
        this.babyId = babyId;
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

    public Date getBabyDeathTime() {
        return babyDeathTime;
    }

    public void setBabyDeathTime(Date babyDeathTime) {
        this.babyDeathTime = babyDeathTime;
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

    public String getBabyId() {
        return babyId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<String> getBirthIds() {
        return birthIds;
    }

    public void setBirthIds(List<String> birthIds) {
        this.birthIds = birthIds;
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
