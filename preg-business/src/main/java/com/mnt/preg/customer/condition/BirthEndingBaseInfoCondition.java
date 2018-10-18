
package com.mnt.preg.customer.condition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

public class BirthEndingBaseInfoCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @备注: 主键
     * @字段:base_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String birthId;

    /**
     * @备注: 分娩时间
     * @字段:base_time DATETIME(19)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.util.Date baseTime;

    /**
     * @备注: 分娩时间_时
     * @字段:base_time DATETIME(19)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseTimeHour;

    /**
     * @备注: 分娩时间_时_分
     * @字段:base_time DATETIME(19)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseTimeMinuters;

    /**
     * @备注: 分娩孕周
     * @字段:base_weeks VARCHAR(20)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseWeeks;

    /**
     * @备注: 是否重危孕产妇 （1：是；0：否）
     * @字段:base_iscritical INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseiscritical;

    /**
     * @备注: 麻醉类型（1：局部麻醉；2：全身麻醉；3：椎管内麻醉）
     * @字段:base_hocusType INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseHocusType;

    /**
     * @备注: 分娩方式 （1：自然分娩；2：吸引；3：产钳:；4：臀助产；:5：剖宫产:；6：其他___）
     * @字段:base_birthType INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseBirthType;

    /**
     * @备注: 分娩方式 2 当分娩方式为其他时，填写本字段
     * @字段:base_birthType INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseBirthType2;

    /**
     * @备注: 剖宫产指征
     * @字段:base_pgc_indication VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String basePgcIndication;

    /**
     * @备注: 分娩方位（枕先露六种胎位
     *      左枕前（LOA） 左枕横（LOT） 左枕后（LOP） 右枕前（ROA） 右枕横（ROT） 右枕后（ROP）
     *      面先露六种胎位
     *      左颏前（LMA） 左颏横（LMT） 左颏后（LMP） 右颏前（RMA） 右颏横（RMT） 右颏后（RMP）
     *      臀先露六种胎位
     *      左骶前（LSA） 左骶横（LST） 左骶后（LSP） 右骶前（RSA） 右骶横（RST） 右骶后（RSP）
     *      肩先露四种胎位
     *      左肩前（LScA） 左肩后（LScP） 右肩前（RScA） 右肩后(RScP)）
     * @字段:base_birth_direction VARCHAR(30)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseBirthDirection;

    /**
     * @备注: 分娩前体重
     * @字段:base_weight_beforeBirth INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal baseWeightBeforeBirth;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal baseGrowthPregnancying;

    /**
     * @备注: 分娩后体重
     * @字段:base_weight_afterBirth INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal baseWeightAfterBirth;

    /**
     * @备注: 第一产程时间（x时x分）
     * @字段:base_childBirth_fist VARCHAR(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseChildBirthFist;

    /**
     * @备注: 第二产程时间
     * @字段:base_childBirth_second VARCHAR(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseChildBirthSecond;

    /**
     * @备注: 第三产程时间
     * @字段:base_childBirth_thrid VARCHAR(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseChildBirthThrid;

    /**
     * @备注: 总产程时间
     * @字段:base_childBirth_sum VARCHAR(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseChildBirthSum;

    /**
     * @备注: 产时出血量
     * @字段:base_bloodVol_birthing DECIMAL(7)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal baseBloodVolBirthing;

    /**
     * @备注: 产后两小时出血量
     * @字段:base_bloodVol_afterBirth DECIMAL(7)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal baseBloodVolAfterBirth;

    /**
     * @备注: 总出血量
     * @字段:base_bloodVol_sum DECIMAL(7)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal baseBloodVolSum;

    /**
     * @备注: 阴检次数
     * @字段:base_perineum_checkTimes INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer basePerineumCheckTimes;

    /**
     * @备注: 会阴情况（1：完整；2：裂伤；3：切开）
     * @字段:base_perineum_state INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer basePerineumState;

    /**
     * @备注: 会阴裂伤程度（1：Ⅰ°、2：Ⅱ°:3：Ⅲ°）
     * @字段:base_perineum_hurt INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer basePerineumHurt;

    /**
     * @备注: 缝合
     * @字段:base_perineum_stitching INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer basePerineumStitching;

    /**
     * @备注: 胎盘（1：手剥；2：沾水；3：自然脱落）
     * @字段:base_perineum_placenta INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer basePerineumPlacenta;

    /**
     * @备注: 手术 （1：引产:2：产后刮宫:3：手转胎头:4：点滴加强，5：其他）
     * @字段:base_surgery_type INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseSurgeryType;

    /**
     * @备注: 手术详细（1：改良药物:2：剥膜:3：点滴:、4：破膜、5：其他）
     * @字段:base_surgery_detail INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseSurgeryDetail;

    /**
     * @备注: 手术详细手术为其他时输入本字段
     * @字段:base_surgery_detail INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseSurgeryDetail2;

    /**
     * @备注: 产前检查（1：有；0：无）
     * @字段:base_complication_prenatalCal INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseComplicationPrenatalCal;

    /**
     * @备注: 中度贫血HB小于90g/L(1：是；0否)
     * @字段:base_complication_anemia INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseComplicationAnemia;

    /**
     * @备注: 妊娠高血压疾病（1：是；0：否）
     * @字段:base_complication_hypertension INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseComplicationHypertension;

    /**
     * @备注: 妊娠并发症
     * @字段:base_complication_prenatal VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseComplicationPrenatal;

    /**
     * @备注: 产中并发症
     * @字段:base_complication_birthing VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseComplicationBirthing;

    /**
     * @备注: 产后并发症
     * @字段:base_complication_afterBirthing VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseComplicationAfterBirthing;

    /**
     * @备注: 内科合并症
     * @字段:base_complication_afterBirthing VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseComplicationsMedical;

    /**
     * @备注: 传染病监测情况
     * @字段:base_complication_disease VARCHAR(100)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseComplicationDisease;

    /**
     * @备注: 收缩压
     * @字段:base_afterBirthing_ssy INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseAfterBirthingSsy;

    /**
     * @备注: 舒张压
     * @字段:base_afterBirthing_szy INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseAfterBirthingSzy;

    /**
     * @备注: 开奶时间（产后x时分）
     * @字段:base_afterBirthing_breastMilkl VARCHAR(20)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseAfterBirthingBreastMilkl;

    /**
     * @备注: 产妇结局（ 1：存货；2：死亡）
     * @字段:base_materEnding_liveOrDeath INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseMaterEndingLiveOrDeath;

    /**
     * @备注: 死亡（1：产时；2：产后）
     * @字段:base_birthBirthing_detail INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseBirthBirthingDetail;

    /**
     * @备注: 活产数
     * @字段:base_birthEnding_liveBirths INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseBirthEndingLiveBirths;

    /**
     * @备注: 死胎数
     * @字段:base_birthEnding_deathBabys INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseBirthEndingDeathBabys;

    /**
     * @备注: 死产数
     * @字段:base_birthEnding_deathBirths INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseBirthEndingDeathBirths;

    /**
     * @备注: 围产儿
     * @字段:base_birthEnding_perinatal INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseBirthEndingPerinatal;

    /**
     * @备注: 孕28周前双/多胎宫内死亡胎数
     * @字段:base_death_befor28 INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseDeathBefor28;

    /**
     * @备注: 孕28周前双/多胎宫内死亡原因
     * @字段:base_deathReason_befor28 INT(10)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.Integer baseDeathReasonBefor28;

    /**
     * @备注: 末次月经
     * @字段:base_remark VARCHAR(200)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseLmp;

    /**
     * @备注: 备注
     * @字段:base_remark VARCHAR(200)
     */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private java.lang.String baseRemark;

    @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Date createTime;

    // 标识
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public BirthEndingBaseInfoCondition() {
    }

    public BirthEndingBaseInfoCondition(
            java.lang.String baseId) {
        this.baseId = baseId;
    }

    public java.lang.String getBaseId() {
        return baseId;
    }

    public void setBaseId(java.lang.String baseId) {
        this.baseId = baseId;
    }

    public java.lang.String getBirthId() {
        return birthId;
    }

    public void setBirthId(java.lang.String birthId) {
        this.birthId = birthId;
    }

    public java.util.Date getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(java.util.Date baseTime) {
        this.baseTime = baseTime;
    }

    public java.lang.String getBaseWeeks() {
        return baseWeeks;
    }

    public void setBaseWeeks(java.lang.String baseWeeks) {
        this.baseWeeks = baseWeeks;
    }

    public java.lang.Integer getBaseiscritical() {
        return baseiscritical;
    }

    public void setBaseiscritical(java.lang.Integer baseiscritical) {
        this.baseiscritical = baseiscritical;
    }

    public java.lang.Integer getBaseHocusType() {
        return baseHocusType;
    }

    public void setBaseHocusType(java.lang.Integer baseHocusType) {
        this.baseHocusType = baseHocusType;
    }

    public java.lang.String getBaseBirthType() {
        return baseBirthType;
    }

    public void setBaseBirthType(java.lang.String baseBirthType) {
        this.baseBirthType = baseBirthType;
    }

    public java.lang.String getBasePgcIndication() {
        return basePgcIndication;
    }

    public void setBasePgcIndication(java.lang.String basePgcIndication) {
        this.basePgcIndication = basePgcIndication;
    }

    public java.lang.String getBaseBirthDirection() {
        return baseBirthDirection;
    }

    public void setBaseBirthDirection(java.lang.String baseBirthDirection) {
        this.baseBirthDirection = baseBirthDirection;
    }

    public BigDecimal getBaseWeightBeforeBirth() {
        return baseWeightBeforeBirth;
    }

    public void setBaseWeightBeforeBirth(BigDecimal baseWeightBeforeBirth) {
        this.baseWeightBeforeBirth = baseWeightBeforeBirth;
    }

    public BigDecimal getBaseWeightAfterBirth() {
        return baseWeightAfterBirth;
    }

    public void setBaseWeightAfterBirth(BigDecimal baseWeightAfterBirth) {
        this.baseWeightAfterBirth = baseWeightAfterBirth;
    }

    public java.lang.String getBaseChildBirthFist() {
        return baseChildBirthFist;
    }

    public void setBaseChildBirthFist(java.lang.String baseChildBirthFist) {
        this.baseChildBirthFist = baseChildBirthFist;
    }

    public java.lang.String getBaseChildBirthSecond() {
        return baseChildBirthSecond;
    }

    public void setBaseChildBirthSecond(java.lang.String baseChildBirthSecond) {
        this.baseChildBirthSecond = baseChildBirthSecond;
    }

    public java.lang.String getBaseChildBirthThrid() {
        return baseChildBirthThrid;
    }

    public void setBaseChildBirthThrid(java.lang.String baseChildBirthThrid) {
        this.baseChildBirthThrid = baseChildBirthThrid;
    }

    public java.lang.String getBaseChildBirthSum() {
        return baseChildBirthSum;
    }

    public void setBaseChildBirthSum(java.lang.String baseChildBirthSum) {
        this.baseChildBirthSum = baseChildBirthSum;
    }

    public BigDecimal getBaseBloodVolBirthing() {
        return baseBloodVolBirthing;
    }

    public void setBaseBloodVolBirthing(BigDecimal baseBloodVolBirthing) {
        this.baseBloodVolBirthing = baseBloodVolBirthing;
    }

    public BigDecimal getBaseBloodVolAfterBirth() {
        return baseBloodVolAfterBirth;
    }

    public void setBaseBloodVolAfterBirth(BigDecimal baseBloodVolAfterBirth) {
        this.baseBloodVolAfterBirth = baseBloodVolAfterBirth;
    }

    public BigDecimal getBaseBloodVolSum() {
        return baseBloodVolSum;
    }

    public void setBaseBloodVolSum(BigDecimal baseBloodVolSum) {
        this.baseBloodVolSum = baseBloodVolSum;
    }

    public java.lang.Integer getBasePerineumCheckTimes() {
        return basePerineumCheckTimes;
    }

    public void setBasePerineumCheckTimes(java.lang.Integer basePerineumCheckTimes) {
        this.basePerineumCheckTimes = basePerineumCheckTimes;
    }

    public java.lang.Integer getBasePerineumState() {
        return basePerineumState;
    }

    public void setBasePerineumState(java.lang.Integer basePerineumState) {
        this.basePerineumState = basePerineumState;
    }

    public java.lang.Integer getBasePerineumHurt() {
        return basePerineumHurt;
    }

    public void setBasePerineumHurt(java.lang.Integer basePerineumHurt) {
        this.basePerineumHurt = basePerineumHurt;
    }

    public java.lang.Integer getBasePerineumStitching() {
        return basePerineumStitching;
    }

    public void setBasePerineumStitching(java.lang.Integer basePerineumStitching) {
        this.basePerineumStitching = basePerineumStitching;
    }

    public java.lang.Integer getBasePerineumPlacenta() {
        return basePerineumPlacenta;
    }

    public void setBasePerineumPlacenta(java.lang.Integer basePerineumPlacenta) {
        this.basePerineumPlacenta = basePerineumPlacenta;
    }

    public java.lang.String getBaseSurgeryType() {
        return baseSurgeryType;
    }

    public void setBaseSurgeryType(java.lang.String baseSurgeryType) {
        this.baseSurgeryType = baseSurgeryType;
    }

    public java.lang.Integer getBaseSurgeryDetail() {
        return baseSurgeryDetail;
    }

    public void setBaseSurgeryDetail(java.lang.Integer baseSurgeryDetail) {
        this.baseSurgeryDetail = baseSurgeryDetail;
    }

    public java.lang.Integer getBaseComplicationPrenatalCal() {
        return baseComplicationPrenatalCal;
    }

    public void setBaseComplicationPrenatalCal(java.lang.Integer baseComplicationPrenatalCal) {
        this.baseComplicationPrenatalCal = baseComplicationPrenatalCal;
    }

    public java.lang.Integer getBaseComplicationAnemia() {
        return baseComplicationAnemia;
    }

    public void setBaseComplicationAnemia(java.lang.Integer baseComplicationAnemia) {
        this.baseComplicationAnemia = baseComplicationAnemia;
    }

    public java.lang.Integer getBaseComplicationHypertension() {
        return baseComplicationHypertension;
    }

    public void setBaseComplicationHypertension(java.lang.Integer baseComplicationHypertension) {
        this.baseComplicationHypertension = baseComplicationHypertension;
    }

    public java.lang.String getBaseComplicationPrenatal() {
        return baseComplicationPrenatal;
    }

    public void setBaseComplicationPrenatal(java.lang.String baseComplicationPrenatal) {
        this.baseComplicationPrenatal = baseComplicationPrenatal;
    }

    public java.lang.String getBaseComplicationBirthing() {
        return baseComplicationBirthing;
    }

    public void setBaseComplicationBirthing(java.lang.String baseComplicationBirthing) {
        this.baseComplicationBirthing = baseComplicationBirthing;
    }

    public java.lang.String getBaseComplicationAfterBirthing() {
        return baseComplicationAfterBirthing;
    }

    public void setBaseComplicationAfterBirthing(java.lang.String baseComplicationAfterBirthing) {
        this.baseComplicationAfterBirthing = baseComplicationAfterBirthing;
    }

    public java.lang.String getBaseComplicationDisease() {
        return baseComplicationDisease;
    }

    public void setBaseComplicationDisease(java.lang.String baseComplicationDisease) {
        this.baseComplicationDisease = baseComplicationDisease;
    }

    public java.lang.Integer getBaseAfterBirthingSsy() {
        return baseAfterBirthingSsy;
    }

    public void setBaseAfterBirthingSsy(java.lang.Integer baseAfterBirthingSsy) {
        this.baseAfterBirthingSsy = baseAfterBirthingSsy;
    }

    public java.lang.Integer getBaseAfterBirthingSzy() {
        return baseAfterBirthingSzy;
    }

    public void setBaseAfterBirthingSzy(java.lang.Integer baseAfterBirthingSzy) {
        this.baseAfterBirthingSzy = baseAfterBirthingSzy;
    }

    public java.lang.String getBaseAfterBirthingBreastMilkl() {
        return baseAfterBirthingBreastMilkl;
    }

    public void setBaseAfterBirthingBreastMilkl(java.lang.String baseAfterBirthingBreastMilkl) {
        this.baseAfterBirthingBreastMilkl = baseAfterBirthingBreastMilkl;
    }

    public java.lang.Integer getBaseMaterEndingLiveOrDeath() {
        return baseMaterEndingLiveOrDeath;
    }

    public void setBaseMaterEndingLiveOrDeath(java.lang.Integer baseMaterEndingLiveOrDeath) {
        this.baseMaterEndingLiveOrDeath = baseMaterEndingLiveOrDeath;
    }

    public java.lang.Integer getBaseBirthBirthingDetail() {
        return baseBirthBirthingDetail;
    }

    public void setBaseBirthBirthingDetail(java.lang.Integer baseBirthBirthingDetail) {
        this.baseBirthBirthingDetail = baseBirthBirthingDetail;
    }

    public java.lang.Integer getBaseBirthEndingLiveBirths() {
        return baseBirthEndingLiveBirths;
    }

    public void setBaseBirthEndingLiveBirths(java.lang.Integer baseBirthEndingLiveBirths) {
        this.baseBirthEndingLiveBirths = baseBirthEndingLiveBirths;
    }

    public java.lang.Integer getBaseBirthEndingDeathBabys() {
        return baseBirthEndingDeathBabys;
    }

    public void setBaseBirthEndingDeathBabys(java.lang.Integer baseBirthEndingDeathBabys) {
        this.baseBirthEndingDeathBabys = baseBirthEndingDeathBabys;
    }

    public java.lang.Integer getBaseBirthEndingDeathBirths() {
        return baseBirthEndingDeathBirths;
    }

    public void setBaseBirthEndingDeathBirths(java.lang.Integer baseBirthEndingDeathBirths) {
        this.baseBirthEndingDeathBirths = baseBirthEndingDeathBirths;
    }

    public java.lang.Integer getBaseBirthEndingPerinatal() {
        return baseBirthEndingPerinatal;
    }

    public void setBaseBirthEndingPerinatal(java.lang.Integer baseBirthEndingPerinatal) {
        this.baseBirthEndingPerinatal = baseBirthEndingPerinatal;
    }

    public java.lang.Integer getBaseDeathBefor28() {
        return baseDeathBefor28;
    }

    public void setBaseDeathBefor28(java.lang.Integer baseDeathBefor28) {
        this.baseDeathBefor28 = baseDeathBefor28;
    }

    public java.lang.Integer getBaseDeathReasonBefor28() {
        return baseDeathReasonBefor28;
    }

    public void setBaseDeathReasonBefor28(java.lang.Integer baseDeathReasonBefor28) {
        this.baseDeathReasonBefor28 = baseDeathReasonBefor28;
    }

    public java.lang.String getBaseRemark() {
        return baseRemark;
    }

    public void setBaseRemark(java.lang.String baseRemark) {
        this.baseRemark = baseRemark;
    }

    public java.lang.String getBaseBirthType2() {
        return baseBirthType2;
    }

    public void setBaseBirthType2(java.lang.String baseBirthType2) {
        this.baseBirthType2 = baseBirthType2;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public java.lang.String getBaseComplicationsMedical() {
        return baseComplicationsMedical;
    }

    public void setBaseComplicationsMedical(java.lang.String baseComplicationsMedical) {
        this.baseComplicationsMedical = baseComplicationsMedical;
    }

    public BigDecimal getBaseGrowthPregnancying() {
        return baseGrowthPregnancying;
    }

    public void setBaseGrowthPregnancying(BigDecimal baseGrowthPregnancying) {
        this.baseGrowthPregnancying = baseGrowthPregnancying;
    }

    public java.lang.Integer getBaseTimeHour() {
        return baseTimeHour;
    }

    public void setBaseTimeHour(java.lang.Integer baseTimeHour) {
        this.baseTimeHour = baseTimeHour;
    }

    public java.lang.Integer getBaseTimeMinuters() {
        return baseTimeMinuters;
    }

    public void setBaseTimeMinuters(java.lang.Integer baseTimeMinuters) {
        this.baseTimeMinuters = baseTimeMinuters;
    }

    public java.lang.String getBaseSurgeryDetail2() {
        return baseSurgeryDetail2;
    }

    public void setBaseSurgeryDetail2(java.lang.String baseSurgeryDetail2) {
        this.baseSurgeryDetail2 = baseSurgeryDetail2;
    }

    public java.lang.String getBaseLmp() {
        return baseLmp;
    }

    public void setBaseLmp(java.lang.String baseLmp) {
        this.baseLmp = baseLmp;
    }

}
