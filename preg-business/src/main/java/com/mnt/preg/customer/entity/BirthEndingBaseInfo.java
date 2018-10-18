
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

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

@Entity
@Table(name = "cus_birthending_baseinfo")
public class BirthEndingBaseInfo extends MappedEntity {

    private static final long serialVersionUID = 1L;

    /**
     * @备注: 主键
     * @字段:base_id VARCHAR(64)
     */
    private String baseId;

    /**
     * @备注: 主表id
     * @字段:birth_id VARCHAR(64)
     */
    @UpdateAnnotation
    private String birthId;

    /**
     * @备注: 分娩年龄
     * @字段:birth_id VARCHAR(64)
     */
    @UpdateAnnotation
    private Integer birthAge;

    /**
     * @备注: 分娩时间
     * @字段:base_time DATETIME(19)
     */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date baseTime;
    
    /**
     * @备注: 分娩时间_时
     * @字段:base_time DATETIME(19)
     */
    @UpdateAnnotation
    private Integer baseTimeHour;

    /**
     * @备注: 分娩时间_时_分
     * @字段:base_time DATETIME(19)
     */
    @UpdateAnnotation
    private Integer baseTimeMinuters;

    /**
     * @备注: 分娩孕周
     * @字段:base_weeks VARCHAR(20)
     */
    @UpdateAnnotation
    private String baseWeeks;

    /**
     * @备注: 是否重危孕产妇 （1：是；0：否）
     * @字段:base_iscritical INT(10)
     */
    @UpdateAnnotation
    private Integer baseIscritical;

    /**
     * @备注: 麻醉类型（1：局部麻醉；2：全身麻醉；3：椎管内麻醉）
     * @字段:base_hocusType INT(10)
     */
    @UpdateAnnotation
    private Integer baseHocusType;

    /**
     * @备注: 分娩方式 （1：自然分娩；2：吸引；3：产钳:；4：臀助产；:5：剖宫产:；6：其他___）
     * @字段:base_birthType INT(10)
     */
    @UpdateAnnotation
    private String baseBirthType;

    /**
     * @备注:分娩方式选择其他 的内容
     * @字段:base_birthType INT(10)
     */
    @UpdateAnnotation
    private String baseBirthType2;

    /**
     * @备注: 剖宫产指征
     * @字段:base_pgc_indication VARCHAR(100)
     */
    @UpdateAnnotation
    private String basePgcIndication;

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
    @UpdateAnnotation
    private String baseBirthDirection;

    /**
     * @备注: 分娩前体重
     * @字段:base_weight_beforeBirth INT(10)
     */
    @UpdateAnnotation
    private BigDecimal baseWeightBeforeBirth;

    /**
     * @备注: 分娩后体重
     * @字段:base_weight_afterBirth INT(10)
     */
    @UpdateAnnotation
    private BigDecimal baseWeightAfterBirth;

    /**
     * @备注: 孕期增长体重
     * @字段:base_weight_afterBirth INT(10)
     */
    @UpdateAnnotation
    private BigDecimal baseGrowthPregnancying;

    /**
     * @备注: 第一产程时间（x时x分）
     * @字段:base_childBirth_fist VARCHAR(10)
     */
    @UpdateAnnotation
    private String baseChildBirthFist;

    /**
     * @备注: 第二产程时间
     * @字段:base_childBirth_second VARCHAR(10)
     */
    @UpdateAnnotation
    private String baseChildBirthSecond;

    /**
     * @备注: 第三产程时间
     * @字段:base_childBirth_thrid VARCHAR(10)
     */
    @UpdateAnnotation
    private String baseChildBirthThrid;

    /**
     * @备注: 总产程时间
     * @字段:base_childBirth_sum VARCHAR(10)
     */
    @UpdateAnnotation
    private String baseChildBirthSum;

    /**
     * @备注: 产时出血量
     * @字段:base_bloodVol_birthing DECIMAL(7)
     */
    @UpdateAnnotation
    private BigDecimal baseBloodVolBirthing;

    /**
     * @备注: 产后两小时出血量
     * @字段:base_bloodVol_afterBirth DECIMAL(7)
     */
    @UpdateAnnotation
    private BigDecimal baseBloodVolAfterBirth;

    /**
     * @备注: 总出血量
     * @字段:base_bloodVol_sum DECIMAL(7)
     */
    @UpdateAnnotation
    private BigDecimal baseBloodVolSum;

    /**
     * @备注: 阴检次数
     * @字段:base_perineum_checkTimes INT(10)
     */
    @UpdateAnnotation
    private Integer basePerineumCheckTimes;

    /**
     * @备注: 会阴情况（1：完整；2：裂伤；3：切开）
     * @字段:base_perineum_state INT(10)
     */
    @UpdateAnnotation
    private Integer basePerineumState;

    /**
     * @备注: 会阴裂伤程度（1：Ⅰ°、2：Ⅱ°:3：Ⅲ°）
     * @字段:base_perineum_hurt INT(10)
     */
    @UpdateAnnotation
    private Integer basePerineumHurt;

    /**
     * @备注: 缝合
     * @字段:base_perineum_stitching INT(10)
     */
    @UpdateAnnotation
    private Integer basePerineumStitching;

    /**
     * @备注: 胎盘（1：手剥；2：沾水；3：自然脱落）
     * @字段:base_perineum_placenta INT(10)
     */
    @UpdateAnnotation
    private Integer basePerineumPlacenta;

    /**
     * @备注: 手术 （1：引产:2：产后刮宫:3：手转胎头:4：点滴加强，5：其他）
     * @字段:base_surgery_type INT(10)
     */
    @UpdateAnnotation
    private String baseSurgeryType;

    /**
     * @备注: 手术 引产详细（1：改良药物:2：剥膜:3：点滴:、4：破膜、5：其他）
     * @字段:base_surgery_detail INT(10)
     */
    @UpdateAnnotation
    private Integer baseSurgeryDetail;

    /**
     * @备注: 手术 其他 内容
     * @字段:base_surgery_detail2 VARCHAR
     */
    @UpdateAnnotation
    private String baseSurgeryDetail2;

    /**
     * @备注: 产前检查（1：有；0：无）
     * @字段:base_complication_prenatalCal INT(10)
     */
    @UpdateAnnotation
    private Integer baseComplicationPrenatalCal;

    /**
     * @备注: 中度贫血HB小于90g/L(1：是；0否)
     * @字段:base_complication_anemia INT(10)
     */
    @UpdateAnnotation
    private Integer baseComplicationAnemia;

    /**
     * @备注: 妊娠高血压疾病（1：是；0：否）
     * @字段:base_complication_hypertension INT(10)
     */
    @UpdateAnnotation
    private Integer baseComplicationHypertension;

    /**
     * @备注: 产前合并症
     * @字段:base_complication_prenatal VARCHAR(100)
     */
    @UpdateAnnotation
    private String baseComplicationPrenatal;

    /**
     * @备注: 产中并发症
     * @字段:base_complication_birthing VARCHAR(100)
     */
    @UpdateAnnotation
    private String baseComplicationBirthing;

    /**
     * @备注: 产后并发症
     * @字段:base_complication_afterBirthing VARCHAR(100)
     */
    @UpdateAnnotation
    private String baseComplicationAfterBirthing;

    /**
     * @备注: 内科合并症
     * @字段:base_complication_afterBirthing VARCHAR(100)
     */
    @UpdateAnnotation
    private String baseComplicationsMedical;

    /**
     * @备注: 传染病监测情况
     * @字段:base_complication_disease VARCHAR(100)
     */
    @UpdateAnnotation
    private String baseComplicationDisease;

    /**
     * @备注: 收缩压
     * @字段:base_afterBirthing_ssy INT(10)
     */
    @UpdateAnnotation
    private Integer baseAfterBirthingSsy;

    /**
     * @备注: 舒张压
     * @字段:base_afterBirthing_szy INT(10)
     */
    @UpdateAnnotation
    private Integer baseAfterBirthingSzy;

    /**
     * @备注: 开奶时间（产后x时分）
     * @字段:base_afterBirthing_breastMilkl VARCHAR(20)
     */
    @UpdateAnnotation
    private String baseAfterBirthingBreastMilkl;

    /**
     * @备注: 产妇结局（ 1：存活；2：死亡）
     * @字段:base_materEnding_liveOrDeath INT(10)
     */
    @UpdateAnnotation
    private Integer baseMaterEndingLiveOrDeath;

    /**
     * @备注: 死亡（1：产时；2：产后）
     * @字段:base_birthBirthing_detail INT(10)
     */
    @UpdateAnnotation
    private Integer baseBirthBirthingDetail;

    /**
     * @备注: 活产数
     * @字段:base_birthEnding_liveBirths INT(10)
     */
    @UpdateAnnotation
    private Integer baseBirthEndingLiveBirths;

    /**
     * @备注: 死胎数
     * @字段:base_birthEnding_deathBabys INT(10)
     */
    @UpdateAnnotation
    private Integer baseBirthEndingDeathBabys;

    /**
     * @备注: 死产数
     * @字段:base_birthEnding_deathBirths INT(10)
     */
    @UpdateAnnotation
    private Integer baseBirthEndingDeathBirths;

    /**
     * @备注: 围产儿
     * @字段:base_birthEnding_perinatal INT(10)
     */
    @UpdateAnnotation
    private Integer baseBirthEndingPerinatal;

    /**
     * @备注: 孕28周前双/多胎宫内死亡胎数
     * @字段:base_death_befor28 INT(10)
     */
    @UpdateAnnotation
    private Integer baseDeathBefor28;

    /**
     * @备注: 孕28周前双/多胎宫内死亡原因
     * @字段:base_deathReason_befor28 INT(10)
     */
    @UpdateAnnotation
    private Integer baseDeathReasonBefor28;

    /**
     * @备注: 末次月经
     * @字段:base_remark VARCHAR(200)
     */
    @UpdateAnnotation
    private String baseLmp;

    /**
     * @备注: 备注
     * @字段:base_remark VARCHAR(200)
     */
    @UpdateAnnotation
    private String baseRemark;

    public BirthEndingBaseInfo() {
    }

    public BirthEndingBaseInfo(
            java.lang.String baseId) {
        this.baseId = baseId;
    }

    public void setBaseId(java.lang.String baseId) {
        this.baseId = baseId;
    }

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "base_id", insertable = false, updatable = false)
    public java.lang.String getBaseId() {
        return this.baseId;
    }

    public void setBirthId(java.lang.String birthId) {
        this.birthId = birthId;
    }

    @Column(name = "birth_id")
    public java.lang.String getBirthId() {
        return this.birthId;
    }

    public void setBaseTime(java.util.Date baseTime) {
        this.baseTime = baseTime;
    }

    @Column(name = "base_time")
    public java.util.Date getBaseTime() {
        return this.baseTime;
    }

    public void setBaseWeeks(java.lang.String baseWeeks) {
        this.baseWeeks = baseWeeks;
    }

    @Column(name = "base_weeks")
    public java.lang.String getBaseWeeks() {
        return this.baseWeeks;
    }

    @Column(name = "base_iscritical")
    public java.lang.Integer getBaseIscritical() {
        return baseIscritical;
    }

    public void setBaseIscritical(java.lang.Integer baseIscritical) {
        this.baseIscritical = baseIscritical;
    }

    public void setBaseHocusType(java.lang.Integer baseHocusType) {
        this.baseHocusType = baseHocusType;
    }

    @Column(name = "base_hocus_type")
    public java.lang.Integer getBaseHocusType() {
        return this.baseHocusType;
    }

    public void setBaseBirthType(java.lang.String baseBirthType) {
        this.baseBirthType = baseBirthType;
    }

    @Column(name = "base_birth_type")
    public java.lang.String getBaseBirthType() {
        return this.baseBirthType;
    }

    @Column(name = "base_birth_type2")
    public java.lang.String getBaseBirthType2() {
        return baseBirthType2;
    }

    public void setBaseBirthType2(java.lang.String baseBirthType2) {
        this.baseBirthType2 = baseBirthType2;
    }

    public void setBasePgcIndication(java.lang.String basePgcIndication) {
        this.basePgcIndication = basePgcIndication;
    }

    @Column(name = "base_pgc_indication")
    public java.lang.String getBasePgcIndication() {
        return this.basePgcIndication;
    }

    public void setBaseBirthDirection(java.lang.String baseBirthDirection) {
        this.baseBirthDirection = baseBirthDirection;
    }

    @Column(name = "base_birth_direction")
    public java.lang.String getBaseBirthDirection() {
        return this.baseBirthDirection;
    }

    public void setBaseWeightBeforeBirth(BigDecimal baseWeightBeforeBirth) {
        this.baseWeightBeforeBirth = baseWeightBeforeBirth;
    }

    @Column(name = "base_weight_before_birth")
    public BigDecimal getBaseWeightBeforeBirth() {
        return this.baseWeightBeforeBirth;
    }

    public void setBaseWeightAfterBirth(BigDecimal baseWeightAfterBirth) {
        this.baseWeightAfterBirth = baseWeightAfterBirth;
    }

    @Column(name = "base_weight_after_birth")
    public BigDecimal getBaseWeightAfterBirth() {
        return this.baseWeightAfterBirth;
    }

    public void setBaseChildBirthFist(java.lang.String baseChildBirthFist) {
        this.baseChildBirthFist = baseChildBirthFist;
    }

    @Column(name = "base_child_birth_fist")
    public java.lang.String getBaseChildBirthFist() {
        return this.baseChildBirthFist;
    }

    public void setBaseChildBirthSecond(java.lang.String baseChildBirthSecond) {
        this.baseChildBirthSecond = baseChildBirthSecond;
    }

    @Column(name = "base_child_birth_second")
    public java.lang.String getBaseChildBirthSecond() {
        return this.baseChildBirthSecond;
    }

    public void setBaseChildBirthThrid(java.lang.String baseChildBirthThrid) {
        this.baseChildBirthThrid = baseChildBirthThrid;
    }

    @Column(name = "base_child_birth_thrid")
    public java.lang.String getBaseChildBirthThrid() {
        return this.baseChildBirthThrid;
    }

    public void setBaseChildBirthSum(java.lang.String baseChildBirthSum) {
        this.baseChildBirthSum = baseChildBirthSum;
    }

    @Column(name = "base_child_birth_sum")
    public java.lang.String getBaseChildBirthSum() {
        return this.baseChildBirthSum;
    }

    public void setBaseBloodVolBirthing(BigDecimal baseBloodVolBirthing) {
        this.baseBloodVolBirthing = baseBloodVolBirthing;
    }

    @Column(name = "base_blood_vol_birthing")
    public BigDecimal getBaseBloodVolBirthing() {
        return this.baseBloodVolBirthing;
    }

    public void setBaseBloodVolAfterBirth(BigDecimal baseBloodVolAfterBirth) {
        this.baseBloodVolAfterBirth = baseBloodVolAfterBirth;
    }

    @Column(name = "base_blood_vol_after_birth")
    public BigDecimal getBaseBloodVolAfterBirth() {
        return this.baseBloodVolAfterBirth;
    }

    public void setBaseBloodVolSum(BigDecimal baseBloodVolSum) {
        this.baseBloodVolSum = baseBloodVolSum;
    }

    @Column(name = "base_blood_vol_sum")
    public BigDecimal getBaseBloodVolSum() {
        return this.baseBloodVolSum;
    }

    public void setBasePerineumCheckTimes(java.lang.Integer basePerineumCheckTimes) {
        this.basePerineumCheckTimes = basePerineumCheckTimes;
    }

    @Column(name = "base_perineum_check_times")
    public java.lang.Integer getBasePerineumCheckTimes() {
        return this.basePerineumCheckTimes;
    }

    public void setBasePerineumState(java.lang.Integer basePerineumState) {
        this.basePerineumState = basePerineumState;
    }

    @Column(name = "base_perineum_state")
    public java.lang.Integer getBasePerineumState() {
        return this.basePerineumState;
    }

    public void setBasePerineumHurt(java.lang.Integer basePerineumHurt) {
        this.basePerineumHurt = basePerineumHurt;
    }

    @Column(name = "base_perineum_hurt")
    public java.lang.Integer getBasePerineumHurt() {
        return this.basePerineumHurt;
    }

    public void setBasePerineumStitching(java.lang.Integer basePerineumStitching) {
        this.basePerineumStitching = basePerineumStitching;
    }

    @Column(name = "base_perineum_stitching")
    public java.lang.Integer getBasePerineumStitching() {
        return this.basePerineumStitching;
    }

    public void setBasePerineumPlacenta(java.lang.Integer basePerineumPlacenta) {
        this.basePerineumPlacenta = basePerineumPlacenta;
    }

    @Column(name = "base_perineum_placenta")
    public java.lang.Integer getBasePerineumPlacenta() {
        return this.basePerineumPlacenta;
    }

    public void setBaseSurgeryType(java.lang.String baseSurgeryType) {
        this.baseSurgeryType = baseSurgeryType;
    }

    @Column(name = "base_surgery_type")
    public java.lang.String getBaseSurgeryType() {
        return this.baseSurgeryType;
    }

    public void setBaseSurgeryDetail(java.lang.Integer baseSurgeryDetail) {
        this.baseSurgeryDetail = baseSurgeryDetail;
    }

    @Column(name = "base_surgery_detail")
    public java.lang.Integer getBaseSurgeryDetail() {
        return this.baseSurgeryDetail;
    }

    public void setBaseComplicationPrenatalCal(java.lang.Integer baseComplicationPrenatalCal) {
        this.baseComplicationPrenatalCal = baseComplicationPrenatalCal;
    }

    @Column(name = "base_complication_prenatal_cal")
    public java.lang.Integer getBaseComplicationPrenatalCal() {
        return this.baseComplicationPrenatalCal;
    }

    @Column(name = "base_surgery_detail2")
    public java.lang.String getBaseSurgeryDetail2() {
        return baseSurgeryDetail2;
    }

    public void setBaseSurgeryDetail2(java.lang.String baseSurgeryDetail2) {
        this.baseSurgeryDetail2 = baseSurgeryDetail2;
    }

    public void setBaseComplicationAnemia(java.lang.Integer baseComplicationAnemia) {
        this.baseComplicationAnemia = baseComplicationAnemia;
    }

    @Column(name = "base_complication_anemia")
    public java.lang.Integer getBaseComplicationAnemia() {
        return this.baseComplicationAnemia;
    }

    public void setBaseComplicationHypertension(java.lang.Integer baseComplicationHypertension) {
        this.baseComplicationHypertension = baseComplicationHypertension;
    }

    @Column(name = "base_complication_hypertension")
    public java.lang.Integer getBaseComplicationHypertension() {
        return this.baseComplicationHypertension;
    }

    public void setBaseComplicationPrenatal(java.lang.String baseComplicationPrenatal) {
        this.baseComplicationPrenatal = baseComplicationPrenatal;
    }

    @Column(name = "base_complication_prenatal")
    public java.lang.String getBaseComplicationPrenatal() {
        return this.baseComplicationPrenatal;
    }

    public void setBaseComplicationBirthing(java.lang.String baseComplicationBirthing) {
        this.baseComplicationBirthing = baseComplicationBirthing;
    }

    @Column(name = "base_complication_birthing")
    public java.lang.String getBaseComplicationBirthing() {
        return this.baseComplicationBirthing;
    }

    public void setBaseComplicationAfterBirthing(java.lang.String baseComplicationAfterBirthing) {
        this.baseComplicationAfterBirthing = baseComplicationAfterBirthing;
    }

    @Column(name = "base_complication_after_birthing")
    public java.lang.String getBaseComplicationAfterBirthing() {
        return this.baseComplicationAfterBirthing;
    }

    public void setBaseComplicationDisease(java.lang.String baseComplicationDisease) {
        this.baseComplicationDisease = baseComplicationDisease;
    }

    @Column(name = "base_complication_disease")
    public java.lang.String getBaseComplicationDisease() {
        return this.baseComplicationDisease;
    }

    @Column(name = "base_complications_medical")
    public java.lang.String getBaseComplicationsMedical() {
        return baseComplicationsMedical;
    }

    public void setBaseComplicationsMedical(java.lang.String baseComplicationsMedical) {
        this.baseComplicationsMedical = baseComplicationsMedical;
    }

    public void setBaseAfterBirthingSsy(java.lang.Integer baseAfterBirthingSsy) {
        this.baseAfterBirthingSsy = baseAfterBirthingSsy;
    }

    @Column(name = "base_after_birthing_ssy")
    public java.lang.Integer getBaseAfterBirthingSsy() {
        return this.baseAfterBirthingSsy;
    }

    public void setBaseAfterBirthingSzy(java.lang.Integer baseAfterBirthingSzy) {
        this.baseAfterBirthingSzy = baseAfterBirthingSzy;
    }

    @Column(name = "base_after_birthing_szy")
    public java.lang.Integer getBaseAfterBirthingSzy() {
        return this.baseAfterBirthingSzy;
    }

    public void setBaseAfterBirthingBreastMilkl(java.lang.String baseAfterBirthingBreastMilkl) {
        this.baseAfterBirthingBreastMilkl = baseAfterBirthingBreastMilkl;
    }

    @Column(name = "base_after_birthing_breast_milkl")
    public java.lang.String getBaseAfterBirthingBreastMilkl() {
        return this.baseAfterBirthingBreastMilkl;
    }

    public void setBaseMaterEndingLiveOrDeath(java.lang.Integer baseMaterEndingLiveOrDeath) {
        this.baseMaterEndingLiveOrDeath = baseMaterEndingLiveOrDeath;
    }

    @Column(name = "base_mater_ending_live_or_death")
    public java.lang.Integer getBaseMaterEndingLiveOrDeath() {
        return this.baseMaterEndingLiveOrDeath;
    }

    public void setBaseBirthBirthingDetail(java.lang.Integer baseBirthBirthingDetail) {
        this.baseBirthBirthingDetail = baseBirthBirthingDetail;
    }

    @Column(name = "base_birth_birthing_detail")
    public java.lang.Integer getBaseBirthBirthingDetail() {
        return this.baseBirthBirthingDetail;
    }

    public void setBaseBirthEndingLiveBirths(java.lang.Integer baseBirthEndingLiveBirths) {
        this.baseBirthEndingLiveBirths = baseBirthEndingLiveBirths;
    }

    @Column(name = "base_birth_ending_live_births")
    public java.lang.Integer getBaseBirthEndingLiveBirths() {
        return this.baseBirthEndingLiveBirths;
    }

    public void setBaseBirthEndingDeathBabys(java.lang.Integer baseBirthEndingDeathBabys) {
        this.baseBirthEndingDeathBabys = baseBirthEndingDeathBabys;
    }

    @Column(name = "base_birth_ending_death_babys")
    public java.lang.Integer getBaseBirthEndingDeathBabys() {
        return this.baseBirthEndingDeathBabys;
    }

    public void setBaseBirthEndingDeathBirths(java.lang.Integer baseBirthEndingDeathBirths) {
        this.baseBirthEndingDeathBirths = baseBirthEndingDeathBirths;
    }

    @Column(name = "base_birth_ending_death_births")
    public java.lang.Integer getBaseBirthEndingDeathBirths() {
        return this.baseBirthEndingDeathBirths;
    }

    public void setBaseBirthEndingPerinatal(java.lang.Integer baseBirthEndingPerinatal) {
        this.baseBirthEndingPerinatal = baseBirthEndingPerinatal;
    }

    @Column(name = "base_birth_ending_perinatal")
    public java.lang.Integer getBaseBirthEndingPerinatal() {
        return this.baseBirthEndingPerinatal;
    }

    public void setBaseDeathBefor28(java.lang.Integer baseDeathBefor28) {
        this.baseDeathBefor28 = baseDeathBefor28;
    }

    @Column(name = "base_death_befor28")
    public java.lang.Integer getBaseDeathBefor28() {
        return this.baseDeathBefor28;
    }

    public void setBaseDeathReasonBefor28(java.lang.Integer baseDeathReasonBefor28) {
        this.baseDeathReasonBefor28 = baseDeathReasonBefor28;
    }

    @Column(name = "base_death_reason_befor28")
    public java.lang.Integer getBaseDeathReasonBefor28() {
        return this.baseDeathReasonBefor28;
    }

    public void setBaseRemark(java.lang.String baseRemark) {
        this.baseRemark = baseRemark;
    }

    @Column(name = "base_remark")
    public java.lang.String getBaseRemark() {
        return this.baseRemark;
    }

    @Column(name = "base_growth_pregnancying")
    public BigDecimal getBaseGrowthPregnancying() {
        return baseGrowthPregnancying;
    }

    public void setBaseGrowthPregnancying(BigDecimal baseGrowthPregnancying) {
        this.baseGrowthPregnancying = baseGrowthPregnancying;
    }

    @Column(name = "base_lmp")
    public java.lang.String getBaseLmp() {
        return baseLmp;
    }

    public void setBaseLmp(java.lang.String baseLmp) {
        this.baseLmp = baseLmp;
    }

    @Column(name = "birth_age")
    public java.lang.Integer getBirthAge() {
        return birthAge;
    }

    public void setBirthAge(java.lang.Integer birthAge) {
        this.birthAge = birthAge;
    }

    @Column(name = "base_time_hour")
    public Integer getBaseTimeHour() {
        return baseTimeHour;
    }

    
    public void setBaseTimeHour(Integer baseTimeHour) {
        this.baseTimeHour = baseTimeHour;
    }

    @Column(name = "base_time_minuters")
    public Integer getBaseTimeMinuters() {
        return baseTimeMinuters;
    }

    
    public void setBaseTimeMinuters(Integer baseTimeMinuters) {
        this.baseTimeMinuters = baseTimeMinuters;
    }

}
