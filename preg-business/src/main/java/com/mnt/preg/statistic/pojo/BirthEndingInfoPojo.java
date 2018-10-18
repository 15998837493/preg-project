/*
 * BirthEndingInfoPojo.java    1.0  2018年8月13日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;

/**
 * [分娩结局]
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月13日 mlq 初版
 */
public class BirthEndingInfoPojo implements Serializable {

    private static final long serialVersionUID = 176636932040035485L;

    // 基础信息
    /** id */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String birthId;

    /** 患者id */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String custId;

    /** 建档id */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String archivesId;

    /** 出生日期 */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_birthday")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date custBirthday;

    /** 年龄 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo", fieldName = "birth_age")
    private Integer custAge;

    /** 身高 */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_height")
    private BigDecimal custHeight;

    /** 胎数 */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_tires_number")
    private String embryoNum;

    /** 受孕方式（1：自然受孕；2：辅助生殖） */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_tires_type")
    private Integer birthTiresType;

    /** 受孕方式（1：意外妊娠；2：计划妊娠） 只有当自然受孕时，才保存这个字段 */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_tires_type2")
    private Integer encyesisSituation;

    /** 孕前体重 */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_weight")
    private BigDecimal weight;

    /** 孕前BMI */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "cust_bmi")
    private BigDecimal bmi;

    /** 孕次 */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_preg_number")
    private Integer pregnancyNum;

    /** 产次 */
    @QueryFieldAnnotation(aliasName = "conditionPojo", fieldName = "birth_born_number")
    private Integer birthNum;

    /** 住院日期 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthHospitalDate;

    /** 住院号 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String birthPatientId;

    /** 产检医院 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String birthPregHospital;

    /** 分娩医院 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String birthHospital;

    /** 入院诊断 */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String birthDiagnosis;

    /** 分娩日期 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo", fieldName = "base_time")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    /** 分娩时间_时 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseTimeHour;

    /** 分娩时间_分 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseTimeMinuters;

    /** 分娩孕周数 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo", fieldName = "base_weeks")
    private String birthWeeks;

    // 分表1
    /** 危重孕产妇（1：是；0：否） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseIscritical;

    /** 麻醉方式 （1：局部麻醉；2：全身麻醉；3：椎管内麻醉） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseHocusType;

    /** 分娩方式（1：自然分娩；2：吸引；3：产钳:；4：臀助产；:5：剖宫产:；6：其他___） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseBirthType;

    /** 剖宫产指征 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String basePgcIndication;

    /**
     * 分娩方位 （枕先露六种胎位
     * 左枕前（LOA） 左枕横（LOT） 左枕后（LOP） 右枕前（ROA） 右枕横（ROT） 右枕后（ROP）
     * 面先露六种胎位
     * 左颏前（LMA） 左颏横（LMT） 左颏后（LMP） 右颏前（RMA） 右颏横（RMT） 右颏后（RMP）
     * 臀先露六种胎位
     * 左骶前（LSA） 左骶横（LST） 左骶后（LSP） 右骶前（RSA） 右骶横（RST） 右骶后（RSP）
     * 肩先露四种胎位
     * 左肩前（LScA） 左肩后（LScP） 右肩前（RScA） 右肩后(RScP)）
     */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseBirthDirection;

    /** 孕期总体重增长 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private BigDecimal baseGrowthPregnancying;

    /** 分娩前体重 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private BigDecimal baseWeightBeforeBirth;

    /** 分娩后体重 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private BigDecimal baseWeightAfterBirth;

    // 产程
    /** 第一产程 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseChildBirthFist;

    /** 第二产程 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseChildBirthSecond;

    /** 第三产程 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseChildBirthThrid;

    /** 总产程 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseChildBirthSum;

    // 出血量
    /** 产时出血量 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private BigDecimal baseBloodVolBirthing;

    /** 产后两小时出血量 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private BigDecimal baseBloodVolAfterBirth;

    /** 总出血量 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private BigDecimal baseBloodVolSum;

    // 会阴胎盘
    /** 阴检 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer basePerineumCheckTimes;

    /** 会阴 （1：完整；2：裂伤；3：切开） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer basePerineumState;

    /** 会阴裂伤程度（1：Ⅰ°、2：Ⅱ°:3：Ⅲ°）（裂伤时才显示） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer basePerineumHurt;

    /** 胎盘 （1：手剥；2：沾水；3：自然脱落） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer basePerineumPlacenta;

    // 手术及并发症
    /** 手术（多选） （1：引产:2：产后刮宫:3：手转胎头:4：点滴加强，5：其他） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseSurgeryType;

    /** 手术详细（引产详情）（单选）（1：改良药物:2：剥膜:3：点滴:、4：破膜、5：其他） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseSurgeryDetail;

    /** 手术详细（其他详情）（输入框） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseSurgeryDetail2;

    /** 产前检查 （1：有；0：无） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseComplicationPrenatalCal;

    /** 中度贫血HB小于90g/L (1：是；0否) */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseComplicationAnemia;

    /** 妊娠高血压疾病 （1：是；0：否） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseComplicationHypertension;

    /** 产前合并症 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseComplicationPrenatal;

    /** 产时并发症 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseComplicationBirthing;

    /** 产后并发症 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseComplicationAfterBirthing;

    /** 内科合并症 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseComplicationsMedical;

    /** 传染病检测情况 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseComplicationDisease;

    // 产后情况
    /** 产后收缩压 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseAfterBirthingSsy;

    /** 产后舒张压 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseAfterBirthingSzy;

    /** 开奶时间 （产后x时分） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseAfterBirthingBreastMilkl;

    /** 产后死亡（1：产时；2：产后） */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseBirthBirthingDetail;

    /** 活产数 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseBirthEndingLiveBirths;

    /** 死胎数 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseBirthEndingDeathBabys;

    /** 死产数 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseBirthEndingDeathBirths;

    /** 围产儿数 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseBirthEndingPerinatal;

    /** 孕28周前双/多胎宫内死亡胎数 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseDeathBefor28;

    /** 孕28周前双/多胎宫内死亡原因 */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private Integer baseDeathReasonBefor28;

    // 新生儿数据集合
    private List<BirthEndingBabyInfoPojo> babyList;

    // 出入院情况
    /** 入院诊断（同上） */

    /** 出院诊断/母 */
    @QueryFieldAnnotation(aliasName = "dischargedPojo")
    private String disMotherDisagnosis;

    /** 出院诊断/婴儿 (新生儿数据集合中显示) */

    /** 新生儿血糖/mg/dl (新生儿数据集合中显示) */

    /** 产后血红蛋白/g/L */
    @QueryFieldAnnotation(aliasName = "dischargedPojo")
    private BigDecimal disHemoglobin;

    // 1.7.1新加显示字段（2018.09.13）
    /** 备注(基础数据) */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String birthBaseRemark;

    /** 备注(分娩信息) */
    @QueryFieldAnnotation(aliasName = "baseinfoPojo")
    private String baseRemark;

    /** 备注(新生儿) 新生儿数据集合中查找 */

    /** 备注(入院诊断) */
    @QueryFieldAnnotation(aliasName = "conditionPojo")
    private String birthDiagRemark;

    /** 备注(出院诊断) */
    @QueryFieldAnnotation(aliasName = "dischargedPojo")
    private String disRemark;

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

    public String getBirthWeeks() {
        return birthWeeks;
    }

    public void setBirthWeeks(String birthWeeks) {
        this.birthWeeks = birthWeeks;
    }

    public Integer getBaseIscritical() {
        return baseIscritical;
    }

    public void setBaseIscritical(Integer baseIscritical) {
        this.baseIscritical = baseIscritical;
    }

    public Integer getBaseHocusType() {
        return baseHocusType;
    }

    public void setBaseHocusType(Integer baseHocusType) {
        this.baseHocusType = baseHocusType;
    }

    public String getBaseBirthType() {
        return baseBirthType;
    }

    public void setBaseBirthType(String baseBirthType) {
        this.baseBirthType = baseBirthType;
    }

    public String getBasePgcIndication() {
        return basePgcIndication;
    }

    public void setBasePgcIndication(String basePgcIndication) {
        this.basePgcIndication = basePgcIndication;
    }

    public String getBaseBirthDirection() {
        return baseBirthDirection;
    }

    public void setBaseBirthDirection(String baseBirthDirection) {
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

    public String getBaseChildBirthFist() {
        return baseChildBirthFist;
    }

    public void setBaseChildBirthFist(String baseChildBirthFist) {
        this.baseChildBirthFist = baseChildBirthFist;
    }

    public String getBaseChildBirthSecond() {
        return baseChildBirthSecond;
    }

    public void setBaseChildBirthSecond(String baseChildBirthSecond) {
        this.baseChildBirthSecond = baseChildBirthSecond;
    }

    public String getBaseChildBirthThrid() {
        return baseChildBirthThrid;
    }

    public void setBaseChildBirthThrid(String baseChildBirthThrid) {
        this.baseChildBirthThrid = baseChildBirthThrid;
    }

    public String getBaseChildBirthSum() {
        return baseChildBirthSum;
    }

    public void setBaseChildBirthSum(String baseChildBirthSum) {
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

    public Integer getBasePerineumCheckTimes() {
        return basePerineumCheckTimes;
    }

    public void setBasePerineumCheckTimes(Integer basePerineumCheckTimes) {
        this.basePerineumCheckTimes = basePerineumCheckTimes;
    }

    public Integer getBasePerineumState() {
        return basePerineumState;
    }

    public void setBasePerineumState(Integer basePerineumState) {
        this.basePerineumState = basePerineumState;
    }

    public Integer getBasePerineumPlacenta() {
        return basePerineumPlacenta;
    }

    public void setBasePerineumPlacenta(Integer basePerineumPlacenta) {
        this.basePerineumPlacenta = basePerineumPlacenta;
    }

    public String getBaseSurgeryType() {
        return baseSurgeryType;
    }

    public void setBaseSurgeryType(String baseSurgeryType) {
        this.baseSurgeryType = baseSurgeryType;
    }

    public Integer getBaseComplicationPrenatalCal() {
        return baseComplicationPrenatalCal;
    }

    public void setBaseComplicationPrenatalCal(Integer baseComplicationPrenatalCal) {
        this.baseComplicationPrenatalCal = baseComplicationPrenatalCal;
    }

    public Integer getBaseComplicationAnemia() {
        return baseComplicationAnemia;
    }

    public void setBaseComplicationAnemia(Integer baseComplicationAnemia) {
        this.baseComplicationAnemia = baseComplicationAnemia;
    }

    public Integer getBaseComplicationHypertension() {
        return baseComplicationHypertension;
    }

    public void setBaseComplicationHypertension(Integer baseComplicationHypertension) {
        this.baseComplicationHypertension = baseComplicationHypertension;
    }

    public String getBaseComplicationPrenatal() {
        return baseComplicationPrenatal;
    }

    public void setBaseComplicationPrenatal(String baseComplicationPrenatal) {
        this.baseComplicationPrenatal = baseComplicationPrenatal;
    }

    public String getBaseComplicationBirthing() {
        return baseComplicationBirthing;
    }

    public void setBaseComplicationBirthing(String baseComplicationBirthing) {
        this.baseComplicationBirthing = baseComplicationBirthing;
    }

    public String getBaseComplicationAfterBirthing() {
        return baseComplicationAfterBirthing;
    }

    public void setBaseComplicationAfterBirthing(String baseComplicationAfterBirthing) {
        this.baseComplicationAfterBirthing = baseComplicationAfterBirthing;
    }

    public String getBaseComplicationsMedical() {
        return baseComplicationsMedical;
    }

    public void setBaseComplicationsMedical(String baseComplicationsMedical) {
        this.baseComplicationsMedical = baseComplicationsMedical;
    }

    public String getBaseComplicationDisease() {
        return baseComplicationDisease;
    }

    public void setBaseComplicationDisease(String baseComplicationDisease) {
        this.baseComplicationDisease = baseComplicationDisease;
    }

    public Integer getBaseAfterBirthingSsy() {
        return baseAfterBirthingSsy;
    }

    public void setBaseAfterBirthingSsy(Integer baseAfterBirthingSsy) {
        this.baseAfterBirthingSsy = baseAfterBirthingSsy;
    }

    public Integer getBaseAfterBirthingSzy() {
        return baseAfterBirthingSzy;
    }

    public void setBaseAfterBirthingSzy(Integer baseAfterBirthingSzy) {
        this.baseAfterBirthingSzy = baseAfterBirthingSzy;
    }

    public String getBaseAfterBirthingBreastMilkl() {
        return baseAfterBirthingBreastMilkl;
    }

    public void setBaseAfterBirthingBreastMilkl(String baseAfterBirthingBreastMilkl) {
        this.baseAfterBirthingBreastMilkl = baseAfterBirthingBreastMilkl;
    }

    public Integer getBaseBirthEndingLiveBirths() {
        return baseBirthEndingLiveBirths;
    }

    public void setBaseBirthEndingLiveBirths(Integer baseBirthEndingLiveBirths) {
        this.baseBirthEndingLiveBirths = baseBirthEndingLiveBirths;
    }

    public Integer getBaseBirthEndingDeathBabys() {
        return baseBirthEndingDeathBabys;
    }

    public void setBaseBirthEndingDeathBabys(Integer baseBirthEndingDeathBabys) {
        this.baseBirthEndingDeathBabys = baseBirthEndingDeathBabys;
    }

    public Integer getBaseBirthEndingDeathBirths() {
        return baseBirthEndingDeathBirths;
    }

    public void setBaseBirthEndingDeathBirths(Integer baseBirthEndingDeathBirths) {
        this.baseBirthEndingDeathBirths = baseBirthEndingDeathBirths;
    }

    public Integer getBaseBirthEndingPerinatal() {
        return baseBirthEndingPerinatal;
    }

    public void setBaseBirthEndingPerinatal(Integer baseBirthEndingPerinatal) {
        this.baseBirthEndingPerinatal = baseBirthEndingPerinatal;
    }

    public Integer getBaseDeathBefor28() {
        return baseDeathBefor28;
    }

    public void setBaseDeathBefor28(Integer baseDeathBefor28) {
        this.baseDeathBefor28 = baseDeathBefor28;
    }

    public Integer getBaseDeathReasonBefor28() {
        return baseDeathReasonBefor28;
    }

    public void setBaseDeathReasonBefor28(Integer baseDeathReasonBefor28) {
        this.baseDeathReasonBefor28 = baseDeathReasonBefor28;
    }

    public BigDecimal getBaseGrowthPregnancying() {
        return baseGrowthPregnancying;
    }

    public void setBaseGrowthPregnancying(BigDecimal baseGrowthPregnancying) {
        this.baseGrowthPregnancying = baseGrowthPregnancying;
    }

    public Integer getBaseBirthBirthingDetail() {
        return baseBirthBirthingDetail;
    }

    public void setBaseBirthBirthingDetail(Integer baseBirthBirthingDetail) {
        this.baseBirthBirthingDetail = baseBirthBirthingDetail;
    }

    public String getDisMotherDisagnosis() {
        return disMotherDisagnosis;
    }

    public void setDisMotherDisagnosis(String disMotherDisagnosis) {
        this.disMotherDisagnosis = disMotherDisagnosis;
    }

    public BigDecimal getDisHemoglobin() {
        return disHemoglobin;
    }

    public void setDisHemoglobin(BigDecimal disHemoglobin) {
        this.disHemoglobin = disHemoglobin;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<BirthEndingBabyInfoPojo> getBabyList() {
        return babyList;
    }

    public void setBabyList(List<BirthEndingBabyInfoPojo> babyList) {
        this.babyList = babyList;
    }

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

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public Date getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(Date custBirthday) {
        this.custBirthday = custBirthday;
    }

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    public String getEmbryoNum() {
        return embryoNum;
    }

    public void setEmbryoNum(String embryoNum) {
        this.embryoNum = embryoNum;
    }

    public Integer getEncyesisSituation() {
        return encyesisSituation;
    }

    public void setEncyesisSituation(Integer encyesisSituation) {
        this.encyesisSituation = encyesisSituation;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public Integer getPregnancyNum() {
        return pregnancyNum;
    }

    public void setPregnancyNum(Integer pregnancyNum) {
        this.pregnancyNum = pregnancyNum;
    }

    public Integer getBirthNum() {
        return birthNum;
    }

    public void setBirthNum(Integer birthNum) {
        this.birthNum = birthNum;
    }

    public Integer getBirthTiresType() {
        return birthTiresType;
    }

    public void setBirthTiresType(Integer birthTiresType) {
        this.birthTiresType = birthTiresType;
    }

    public Integer getBasePerineumHurt() {
        return basePerineumHurt;
    }

    public void setBasePerineumHurt(Integer basePerineumHurt) {
        this.basePerineumHurt = basePerineumHurt;
    }

    public Integer getBaseSurgeryDetail() {
        return baseSurgeryDetail;
    }

    public void setBaseSurgeryDetail(Integer baseSurgeryDetail) {
        this.baseSurgeryDetail = baseSurgeryDetail;
    }

    public String getBaseSurgeryDetail2() {
        return baseSurgeryDetail2;
    }

    public void setBaseSurgeryDetail2(String baseSurgeryDetail2) {
        this.baseSurgeryDetail2 = baseSurgeryDetail2;
    }

    public String getBirthBaseRemark() {
        return birthBaseRemark;
    }

    public void setBirthBaseRemark(String birthBaseRemark) {
        this.birthBaseRemark = birthBaseRemark;
    }

    public String getBaseRemark() {
        return baseRemark;
    }

    public void setBaseRemark(String baseRemark) {
        this.baseRemark = baseRemark;
    }

    public String getBirthDiagRemark() {
        return birthDiagRemark;
    }

    public void setBirthDiagRemark(String birthDiagRemark) {
        this.birthDiagRemark = birthDiagRemark;
    }

    public String getDisRemark() {
        return disRemark;
    }

    public void setDisRemark(String disRemark) {
        this.disRemark = disRemark;
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

}
