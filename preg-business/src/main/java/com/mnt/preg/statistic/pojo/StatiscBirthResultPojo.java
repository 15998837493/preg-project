
package com.mnt.preg.statistic.pojo;

import java.io.Serializable;

/**
 * 分娩结局统计报表
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.3 2017-12-28 dhs 初版
 */
public class StatiscBirthResultPojo implements Serializable {

    private static final long serialVersionUID = 3085795622720263776L;

    /** 入院总数 */
    private String numRuYuan;

    /** 初产 */
    private String numBirthChuChan;

    /** 经产 */
    private String numBirthJingChan;

    /** 危重孕妇 */
    private String numDangerPreg;

    /** 死亡孕妇 */
    private String numDeadPreg;

    /** <28周 */
    private String numPregWeek28;

    /** 28-36 */
    private String numPregWeek2836;

    /** 37-41 */
    private String numPregWeek3741;

    /** >42周 */
    private String numPregWeek42;

    /** 本院 */
    private String numPregFromMy;

    /** 外院 */
    private String numPregFromOther;

    /** 高危转诊 */
    private String numPregFromGaowei;

    /** 无产前 */
    private String numPregFromNone;

    /** 完整 */
    private String numPerinaeumFull;

    /** Ⅰ°裂伤 */
    private String numPerinaeumOne;

    /** Ⅱ°裂伤 */
    private String numPerinaeumTwo;

    /** Ⅲ°裂伤 */
    private String numPerinaeumThree;

    /** 切开 */
    private String numPerinaeumCut;

    /** 自然分娩 */
    private String numBirthTypeAuto;

    /** 吸引 */
    private String numBirthTypePull;

    /** 产钳 */
    private String numBirthTypeForceps;

    /** 臀助产 */
    private String numBirthTypeHip;

    /** 剖宫产 */
    private String numBirthTypeDissect;

    /** 其他 */
    private String numBirthTypeOther;

    /** 无 */
    private String numPullTypeNone;

    /** 改良药物 */
    private String numPullTypeDrug;

    /** 剥膜 */
    private String numPullTypeMembrane;

    /** 点滴 */
    private String numPullTypeBottle;

    /** 破膜 */
    private String numPullTypeMembraneHarm;

    /** 其他 */
    private String numPullTypeOther;

    /** 产后刮宫 */
    private String numHelpOprationPostpartumCurettage;

    /** 手转胎头 */
    private String numHelpOprationTurnHead;

    /** 手剥 */
    private String numPlacentaHand;

    /** 沾水 */
    private String numPlacentaWater;

    /** 自然脱落 */
    private String numPlacentaAuto;

    /** 轻度 */
    private String numPregHighLittle;

    /** 中度 */
    private String numPregHighMiddle;

    /** 重度 */
    private String numPregHighSerious;

    /** 子痫 */
    private String numPregHighEclampsia;

    /** 50g糖筛异常 */
    private String numSugarAbnormal50g;

    /** 糖耐量异常 */
    private String numSugarAbnormalPatience;

    /** 妊娠期糖尿病 */
    private String numSugarAbnormalPregDiabetes;

    /** 糖尿病合并妊娠 */
    private String numSugarAbnormalDiabetesPreg;

    /** ABO血型不合 */
    private String numBloodABO;

    /** Rh血型不合 */
    private String numBloodRH;

    /** 其他 */
    private String numBloodOther;

    /** 出口 */
    private String numPelvicExit;

    /** 中骨盆 */
    private String numPelvicMiddle;

    /** 入口 */
    private String numPelvicEntrance;

    /** 其他 */
    private String numPelvicOther;

    /** 畸形 */
    private String numDeformity;

    /** 妇科肿瘤 */
    private String numTumor;

    /** 孕史不良 */
    private String numPregHistoryBad;

    /** 产史不良 */
    private String numBirthHistoryBad;

    /** 剖宫产史 */
    private String numCSHistory;

    /** 初产头浮 */
    private String numFloatHead;

    /** 高龄初产 */
    private String numBigAgePreg;

    /** 胎膜早破 */
    private String numCaulEarlyHarm;

    /** 双胎多胎 */
    private String numTwoManyBirth;

    /** 早产 */
    private String numEarlyBirth;

    /** 过期妊娠 */
    private String numTimeoutPreg;

    /** 手术史 */
    private String numOperationHistory;

    /** ITP */
    private String numITP;

    /** 胎心 */
    private String numFetalDistressHeart;

    /** 羊水 */
    private String numFetalDistressWater;

    /** 胎心+羊水 */
    private String numFetalDistressHeartWater;

    /** 臀位 */
    private String numAbnormalPositionHip;

    /** 枕横位 */
    private String numAbnormalPositionOccipitotransverse;

    /** 枕后位 */
    private String numAbnormalPositionOccipitoposterior;

    /** 脐带缠绕 */
    private String numCordEntanglement;

    /** 过长 */
    private String numCordLong;

    /** 过短 */
    private String numCordShort;

    /** 脱垂 */
    private String numCordProlapse;

    /** 先露 */
    private String numCordPresent;

    /** 过多 */
    private String numWaterMany;

    /** 过少 */
    private String numWaterLittle;

    /** 低置 */
    private String numPlacentaLow;

    /** 前置 */
    private String numPlacentaPreposition;

    /** 胎盘早剥 */
    private String numPlacentaEarlyPeeling;

    /** 胎盘胎膜残留 */
    private String numPlacentaCaulRemain;

    /** 胎盘植入 */
    private String numPlacentaImplantation;

    /** 子宫破裂 */
    private String numHysterorrhexis;

    /** 产间发热 */
    private String numBirthFever;

    /** 羊水栓塞 */
    private String numWaterEmbolism;

    /** 胎盘异常 */
    private String numPlacentaAbnormal;

    /** 滞产 */
    private String numBirthBradytoia;

    /** 二产程 */
    private String numBirthSecond;

    /** VD */
    private String numFloodingVD;

    /** CS */
    private String numFloodingCS;

    /** 产后感染 */
    private String numBirthInfection;

    /** 产后低热 */
    private String numBirthLowHeat;

    /** 尿潴留 */
    private String numUroschesis;

    /** 伤口感染 */
    private String numWoundInfection;

    /** 伤口水肿 */
    private String numWoundEdema;

    /** 晚期出血 */
    private String numLateBlood;

    /** 伤口脂肪液化 */
    private String numWoundFatLiquefaction;

    /** 哮喘 */
    private String numAsthma;

    /** 肾病 */
    private String numNephropathy;

    /** 神经系统疾病 */
    private String numNervousDisease;

    /** 心脏病 */
    private String numHeartDisease;

    /** 心功能异常 */
    private String numHeartAbnormal;

    /** 血液病 */
    private String numHematopathy;

    /** 贫血 */
    private String numAnemia;

    /** 肝炎 */
    private String numHepatitis;

    /** 澳抗阳性 */
    private String numAusAntiPositive;

    /** 病毒感染 */
    private String numViralInfection;

    /** 甲状腺 */
    private String numThyroid;

    /** SLE */
    private String numSLE;

    /** 其他内科并发症 */
    private String numOtherInternalComplication;

    /** 男 */
    private String numNewBirthSexMale;

    /** 女 */
    private String numNewBirthSexFemale;

    /** 不明 */
    private String numUnknow;

    /** <1000 */
    private String numNewBirthWeight1000;

    /** 1000-1499 */
    private String numNewBirthWeight10001499;

    /** 1500-1999 */
    private String numNewBirthWeight15001999;

    /** 2000-2499 */
    private String numNewBirthWeight20002499;

    /** 2500-2999 */
    private String numNewBirthWeight25002999;

    /** 3000-3499 */
    private String numNewBirthWeight30003499;

    /** 3500-3999 */
    private String numNewBirthWeight35003999;

    /** 4000-4499 */
    private String numNewBirthWeight40004499;

    /** >4500 */
    private String numNewBirthWeight4500;

    /** <2500 */
    private String numLowWeight2500;

    /** <1500 */
    private String numLowWeight1500;

    /** 轻度 */
    private String numAsphyxiaLight;

    /** 重度 */
    private String numAsphyxiaSerious;

    /** SGA */
    private String numSGA;

    /** RDS */
    private String numRDS;

    /** 肺炎 */
    private String numPneumonia;

    /** 畸形 */
    private String numMalformation;

    /** 感染 */
    private String numInfection;

    /** 产伤 */
    private String numBirthHarm;

    /** 颅内出血 */
    private String numBrainBlood;

    /** 遗传病 */
    private String numHereditaryDisease;

    /** 死胎 */
    private String numDeadFetus;

    /** 死产 */
    private String numDeadBirth;

    /** 新生儿死亡 */
    private String numNewBirthDead;

    public String getNumBirthJingChan() {
        return numBirthJingChan;
    }

    public void setNumBirthJingChan(String numBirthJingChan) {
        this.numBirthJingChan = numBirthJingChan;
    }

    public String getNumDangerPreg() {
        return numDangerPreg;
    }

    public void setNumDangerPreg(String numDangerPreg) {
        this.numDangerPreg = numDangerPreg;
    }

    public String getNumDeadPreg() {
        return numDeadPreg;
    }

    public void setNumDeadPreg(String numDeadPreg) {
        this.numDeadPreg = numDeadPreg;
    }

    public String getNumPregWeek28() {
        return numPregWeek28;
    }

    public void setNumPregWeek28(String numPregWeek28) {
        this.numPregWeek28 = numPregWeek28;
    }

    public String getNumPregWeek2836() {
        return numPregWeek2836;
    }

    public void setNumPregWeek2836(String numPregWeek2836) {
        this.numPregWeek2836 = numPregWeek2836;
    }

    public String getNumPregWeek3741() {
        return numPregWeek3741;
    }

    public void setNumPregWeek3741(String numPregWeek3741) {
        this.numPregWeek3741 = numPregWeek3741;
    }

    public String getNumPregWeek42() {
        return numPregWeek42;
    }

    public void setNumPregWeek42(String numPregWeek42) {
        this.numPregWeek42 = numPregWeek42;
    }

    public String getNumPregFromMy() {
        return numPregFromMy;
    }

    public void setNumPregFromMy(String numPregFromMy) {
        this.numPregFromMy = numPregFromMy;
    }

    public String getNumPregFromOther() {
        return numPregFromOther;
    }

    public void setNumPregFromOther(String numPregFromOther) {
        this.numPregFromOther = numPregFromOther;
    }

    public String getNumPregFromNone() {
        return numPregFromNone;
    }

    public void setNumPregFromNone(String numPregFromNone) {
        this.numPregFromNone = numPregFromNone;
    }

    public String getNumPerinaeumFull() {
        return numPerinaeumFull;
    }

    public void setNumPerinaeumFull(String numPerinaeumFull) {
        this.numPerinaeumFull = numPerinaeumFull;
    }

    public String getNumPerinaeumOne() {
        return numPerinaeumOne;
    }

    public void setNumPerinaeumOne(String numPerinaeumOne) {
        this.numPerinaeumOne = numPerinaeumOne;
    }

    public String getNumPerinaeumTwo() {
        return numPerinaeumTwo;
    }

    public void setNumPerinaeumTwo(String numPerinaeumTwo) {
        this.numPerinaeumTwo = numPerinaeumTwo;
    }

    public String getNumPerinaeumThree() {
        return numPerinaeumThree;
    }

    public void setNumPerinaeumThree(String numPerinaeumThree) {
        this.numPerinaeumThree = numPerinaeumThree;
    }

    public String getNumPerinaeumCut() {
        return numPerinaeumCut;
    }

    public void setNumPerinaeumCut(String numPerinaeumCut) {
        this.numPerinaeumCut = numPerinaeumCut;
    }

    public String getNumBirthTypeAuto() {
        return numBirthTypeAuto;
    }

    public void setNumBirthTypeAuto(String numBirthTypeAuto) {
        this.numBirthTypeAuto = numBirthTypeAuto;
    }

    public String getNumBirthTypePull() {
        return numBirthTypePull;
    }

    public void setNumBirthTypePull(String numBirthTypePull) {
        this.numBirthTypePull = numBirthTypePull;
    }

    public String getNumBirthTypeForceps() {
        return numBirthTypeForceps;
    }

    public void setNumBirthTypeForceps(String numBirthTypeForceps) {
        this.numBirthTypeForceps = numBirthTypeForceps;
    }

    public String getNumBirthTypeHip() {
        return numBirthTypeHip;
    }

    public void setNumBirthTypeHip(String numBirthTypeHip) {
        this.numBirthTypeHip = numBirthTypeHip;
    }

    public String getNumBirthTypeDissect() {
        return numBirthTypeDissect;
    }

    public void setNumBirthTypeDissect(String numBirthTypeDissect) {
        this.numBirthTypeDissect = numBirthTypeDissect;
    }

    public String getNumBirthTypeOther() {
        return numBirthTypeOther;
    }

    public void setNumBirthTypeOther(String numBirthTypeOther) {
        this.numBirthTypeOther = numBirthTypeOther;
    }

    public String getNumPullTypeNone() {
        return numPullTypeNone;
    }

    public void setNumPullTypeNone(String numPullTypeNone) {
        this.numPullTypeNone = numPullTypeNone;
    }

    public String getNumPullTypeDrug() {
        return numPullTypeDrug;
    }

    public void setNumPullTypeDrug(String numPullTypeDrug) {
        this.numPullTypeDrug = numPullTypeDrug;
    }

    public String getNumPullTypeMembrane() {
        return numPullTypeMembrane;
    }

    public void setNumPullTypeMembrane(String numPullTypeMembrane) {
        this.numPullTypeMembrane = numPullTypeMembrane;
    }

    public String getNumPullTypeBottle() {
        return numPullTypeBottle;
    }

    public void setNumPullTypeBottle(String numPullTypeBottle) {
        this.numPullTypeBottle = numPullTypeBottle;
    }

    public String getNumPullTypeMembraneHarm() {
        return numPullTypeMembraneHarm;
    }

    public void setNumPullTypeMembraneHarm(String numPullTypeMembraneHarm) {
        this.numPullTypeMembraneHarm = numPullTypeMembraneHarm;
    }

    public String getNumPullTypeOther() {
        return numPullTypeOther;
    }

    public void setNumPullTypeOther(String numPullTypeOther) {
        this.numPullTypeOther = numPullTypeOther;
    }

    public String getNumHelpOprationPostpartumCurettage() {
        return numHelpOprationPostpartumCurettage;
    }

    public void setNumHelpOprationPostpartumCurettage(String numHelpOprationPostpartumCurettage) {
        this.numHelpOprationPostpartumCurettage = numHelpOprationPostpartumCurettage;
    }

    public String getNumHelpOprationTurnHead() {
        return numHelpOprationTurnHead;
    }

    public void setNumHelpOprationTurnHead(String numHelpOprationTurnHead) {
        this.numHelpOprationTurnHead = numHelpOprationTurnHead;
    }

    public String getNumPlacentaHand() {
        return numPlacentaHand;
    }

    public void setNumPlacentaHand(String numPlacentaHand) {
        this.numPlacentaHand = numPlacentaHand;
    }

    public String getNumPlacentaWater() {
        return numPlacentaWater;
    }

    public void setNumPlacentaWater(String numPlacentaWater) {
        this.numPlacentaWater = numPlacentaWater;
    }

    public String getNumPlacentaAuto() {
        return numPlacentaAuto;
    }

    public void setNumPlacentaAuto(String numPlacentaAuto) {
        this.numPlacentaAuto = numPlacentaAuto;
    }

    public String getNumPregHighLittle() {
        return numPregHighLittle;
    }

    public void setNumPregHighLittle(String numPregHighLittle) {
        this.numPregHighLittle = numPregHighLittle;
    }

    public String getNumPregHighMiddle() {
        return numPregHighMiddle;
    }

    public void setNumPregHighMiddle(String numPregHighMiddle) {
        this.numPregHighMiddle = numPregHighMiddle;
    }

    public String getNumPregHighSerious() {
        return numPregHighSerious;
    }

    public void setNumPregHighSerious(String numPregHighSerious) {
        this.numPregHighSerious = numPregHighSerious;
    }

    public String getNumPregHighEclampsia() {
        return numPregHighEclampsia;
    }

    public void setNumPregHighEclampsia(String numPregHighEclampsia) {
        this.numPregHighEclampsia = numPregHighEclampsia;
    }

    public String getNumSugarAbnormal50g() {
        return numSugarAbnormal50g;
    }

    public void setNumSugarAbnormal50g(String numSugarAbnormal50g) {
        this.numSugarAbnormal50g = numSugarAbnormal50g;
    }

    public String getNumSugarAbnormalPatience() {
        return numSugarAbnormalPatience;
    }

    public void setNumSugarAbnormalPatience(String numSugarAbnormalPatience) {
        this.numSugarAbnormalPatience = numSugarAbnormalPatience;
    }

    public String getNumSugarAbnormalPregDiabetes() {
        return numSugarAbnormalPregDiabetes;
    }

    public void setNumSugarAbnormalPregDiabetes(String numSugarAbnormalPregDiabetes) {
        this.numSugarAbnormalPregDiabetes = numSugarAbnormalPregDiabetes;
    }

    public String getNumSugarAbnormalDiabetesPreg() {
        return numSugarAbnormalDiabetesPreg;
    }

    public void setNumSugarAbnormalDiabetesPreg(String numSugarAbnormalDiabetesPreg) {
        this.numSugarAbnormalDiabetesPreg = numSugarAbnormalDiabetesPreg;
    }

    public String getNumBloodABO() {
        return numBloodABO;
    }

    public void setNumBloodABO(String numBloodABO) {
        this.numBloodABO = numBloodABO;
    }

    public String getNumBloodRH() {
        return numBloodRH;
    }

    public void setNumBloodRH(String numBloodRH) {
        this.numBloodRH = numBloodRH;
    }

    public String getNumBloodOther() {
        return numBloodOther;
    }

    public void setNumBloodOther(String numBloodOther) {
        this.numBloodOther = numBloodOther;
    }

    public String getNumPelvicExit() {
        return numPelvicExit;
    }

    public void setNumPelvicExit(String numPelvicExit) {
        this.numPelvicExit = numPelvicExit;
    }

    public String getNumPelvicMiddle() {
        return numPelvicMiddle;
    }

    public void setNumPelvicMiddle(String numPelvicMiddle) {
        this.numPelvicMiddle = numPelvicMiddle;
    }

    public String getNumPelvicEntrance() {
        return numPelvicEntrance;
    }

    public void setNumPelvicEntrance(String numPelvicEntrance) {
        this.numPelvicEntrance = numPelvicEntrance;
    }

    public String getNumPelvicOther() {
        return numPelvicOther;
    }

    public void setNumPelvicOther(String numPelvicOther) {
        this.numPelvicOther = numPelvicOther;
    }

    public String getNumDeformity() {
        return numDeformity;
    }

    public void setNumDeformity(String numDeformity) {
        this.numDeformity = numDeformity;
    }

    public String getNumTumor() {
        return numTumor;
    }

    public void setNumTumor(String numTumor) {
        this.numTumor = numTumor;
    }

    public String getNumPregHistoryBad() {
        return numPregHistoryBad;
    }

    public void setNumPregHistoryBad(String numPregHistoryBad) {
        this.numPregHistoryBad = numPregHistoryBad;
    }

    public String getNumBirthHistoryBad() {
        return numBirthHistoryBad;
    }

    public void setNumBirthHistoryBad(String numBirthHistoryBad) {
        this.numBirthHistoryBad = numBirthHistoryBad;
    }

    public String getNumCSHistory() {
        return numCSHistory;
    }

    public void setNumCSHistory(String numCSHistory) {
        this.numCSHistory = numCSHistory;
    }

    public String getNumFloatHead() {
        return numFloatHead;
    }

    public void setNumFloatHead(String numFloatHead) {
        this.numFloatHead = numFloatHead;
    }

    public String getNumBigAgePreg() {
        return numBigAgePreg;
    }

    public void setNumBigAgePreg(String numBigAgePreg) {
        this.numBigAgePreg = numBigAgePreg;
    }

    public String getNumCaulEarlyHarm() {
        return numCaulEarlyHarm;
    }

    public void setNumCaulEarlyHarm(String numCaulEarlyHarm) {
        this.numCaulEarlyHarm = numCaulEarlyHarm;
    }

    public String getNumTwoManyBirth() {
        return numTwoManyBirth;
    }

    public void setNumTwoManyBirth(String numTwoManyBirth) {
        this.numTwoManyBirth = numTwoManyBirth;
    }

    public String getNumEarlyBirth() {
        return numEarlyBirth;
    }

    public void setNumEarlyBirth(String numEarlyBirth) {
        this.numEarlyBirth = numEarlyBirth;
    }

    public String getNumTimeoutPreg() {
        return numTimeoutPreg;
    }

    public void setNumTimeoutPreg(String numTimeoutPreg) {
        this.numTimeoutPreg = numTimeoutPreg;
    }

    public String getNumOperationHistory() {
        return numOperationHistory;
    }

    public void setNumOperationHistory(String numOperationHistory) {
        this.numOperationHistory = numOperationHistory;
    }

    public String getNumITP() {
        return numITP;
    }

    public void setNumITP(String numITP) {
        this.numITP = numITP;
    }

    public String getNumFetalDistressHeart() {
        return numFetalDistressHeart;
    }

    public void setNumFetalDistressHeart(String numFetalDistressHeart) {
        this.numFetalDistressHeart = numFetalDistressHeart;
    }

    public String getNumFetalDistressWater() {
        return numFetalDistressWater;
    }

    public void setNumFetalDistressWater(String numFetalDistressWater) {
        this.numFetalDistressWater = numFetalDistressWater;
    }

    public String getNumFetalDistressHeartWater() {
        return numFetalDistressHeartWater;
    }

    public void setNumFetalDistressHeartWater(String numFetalDistressHeartWater) {
        this.numFetalDistressHeartWater = numFetalDistressHeartWater;
    }

    public String getNumAbnormalPositionHip() {
        return numAbnormalPositionHip;
    }

    public void setNumAbnormalPositionHip(String numAbnormalPositionHip) {
        this.numAbnormalPositionHip = numAbnormalPositionHip;
    }

    public String getNumAbnormalPositionOccipitotransverse() {
        return numAbnormalPositionOccipitotransverse;
    }

    public void setNumAbnormalPositionOccipitotransverse(String numAbnormalPositionOccipitotransverse) {
        this.numAbnormalPositionOccipitotransverse = numAbnormalPositionOccipitotransverse;
    }

    public String getNumAbnormalPositionOccipitoposterior() {
        return numAbnormalPositionOccipitoposterior;
    }

    public void setNumAbnormalPositionOccipitoposterior(String numAbnormalPositionOccipitoposterior) {
        this.numAbnormalPositionOccipitoposterior = numAbnormalPositionOccipitoposterior;
    }

    public String getNumCordEntanglement() {
        return numCordEntanglement;
    }

    public void setNumCordEntanglement(String numCordEntanglement) {
        this.numCordEntanglement = numCordEntanglement;
    }

    public String getNumCordLong() {
        return numCordLong;
    }

    public void setNumCordLong(String numCordLong) {
        this.numCordLong = numCordLong;
    }

    public String getNumCordShort() {
        return numCordShort;
    }

    public void setNumCordShort(String numCordShort) {
        this.numCordShort = numCordShort;
    }

    public String getNumCordProlapse() {
        return numCordProlapse;
    }

    public void setNumCordProlapse(String numCordProlapse) {
        this.numCordProlapse = numCordProlapse;
    }

    public String getNumCordPresent() {
        return numCordPresent;
    }

    public void setNumCordPresent(String numCordPresent) {
        this.numCordPresent = numCordPresent;
    }

    public String getNumWaterMany() {
        return numWaterMany;
    }

    public void setNumWaterMany(String numWaterMany) {
        this.numWaterMany = numWaterMany;
    }

    public String getNumWaterLittle() {
        return numWaterLittle;
    }

    public void setNumWaterLittle(String numWaterLittle) {
        this.numWaterLittle = numWaterLittle;
    }

    public String getNumPlacentaLow() {
        return numPlacentaLow;
    }

    public void setNumPlacentaLow(String numPlacentaLow) {
        this.numPlacentaLow = numPlacentaLow;
    }

    public String getNumPlacentaPreposition() {
        return numPlacentaPreposition;
    }

    public void setNumPlacentaPreposition(String numPlacentaPreposition) {
        this.numPlacentaPreposition = numPlacentaPreposition;
    }

    public String getNumPlacentaEarlyPeeling() {
        return numPlacentaEarlyPeeling;
    }

    public void setNumPlacentaEarlyPeeling(String numPlacentaEarlyPeeling) {
        this.numPlacentaEarlyPeeling = numPlacentaEarlyPeeling;
    }

    public String getNumPlacentaCaulRemain() {
        return numPlacentaCaulRemain;
    }

    public void setNumPlacentaCaulRemain(String numPlacentaCaulRemain) {
        this.numPlacentaCaulRemain = numPlacentaCaulRemain;
    }

    public String getNumPlacentaImplantation() {
        return numPlacentaImplantation;
    }

    public void setNumPlacentaImplantation(String numPlacentaImplantation) {
        this.numPlacentaImplantation = numPlacentaImplantation;
    }

    public String getNumHysterorrhexis() {
        return numHysterorrhexis;
    }

    public void setNumHysterorrhexis(String numHysterorrhexis) {
        this.numHysterorrhexis = numHysterorrhexis;
    }

    public String getNumBirthFever() {
        return numBirthFever;
    }

    public void setNumBirthFever(String numBirthFever) {
        this.numBirthFever = numBirthFever;
    }

    public String getNumWaterEmbolism() {
        return numWaterEmbolism;
    }

    public void setNumWaterEmbolism(String numWaterEmbolism) {
        this.numWaterEmbolism = numWaterEmbolism;
    }

    public String getNumPlacentaAbnormal() {
        return numPlacentaAbnormal;
    }

    public void setNumPlacentaAbnormal(String numPlacentaAbnormal) {
        this.numPlacentaAbnormal = numPlacentaAbnormal;
    }

    public String getNumBirthBradytoia() {
        return numBirthBradytoia;
    }

    public void setNumBirthBradytoia(String numBirthBradytoia) {
        this.numBirthBradytoia = numBirthBradytoia;
    }

    public String getNumBirthSecond() {
        return numBirthSecond;
    }

    public void setNumBirthSecond(String numBirthSecond) {
        this.numBirthSecond = numBirthSecond;
    }

    public String getNumFloodingVD() {
        return numFloodingVD;
    }

    public void setNumFloodingVD(String numFloodingVD) {
        this.numFloodingVD = numFloodingVD;
    }

    public String getNumFloodingCS() {
        return numFloodingCS;
    }

    public void setNumFloodingCS(String numFloodingCS) {
        this.numFloodingCS = numFloodingCS;
    }

    public String getNumBirthInfection() {
        return numBirthInfection;
    }

    public void setNumBirthInfection(String numBirthInfection) {
        this.numBirthInfection = numBirthInfection;
    }

    public String getNumBirthLowHeat() {
        return numBirthLowHeat;
    }

    public void setNumBirthLowHeat(String numBirthLowHeat) {
        this.numBirthLowHeat = numBirthLowHeat;
    }

    public String getNumUroschesis() {
        return numUroschesis;
    }

    public void setNumUroschesis(String numUroschesis) {
        this.numUroschesis = numUroschesis;
    }

    public String getNumWoundInfection() {
        return numWoundInfection;
    }

    public void setNumWoundInfection(String numWoundInfection) {
        this.numWoundInfection = numWoundInfection;
    }

    public String getNumWoundEdema() {
        return numWoundEdema;
    }

    public void setNumWoundEdema(String numWoundEdema) {
        this.numWoundEdema = numWoundEdema;
    }

    public String getNumLateBlood() {
        return numLateBlood;
    }

    public void setNumLateBlood(String numLateBlood) {
        this.numLateBlood = numLateBlood;
    }

    public String getNumWoundFatLiquefaction() {
        return numWoundFatLiquefaction;
    }

    public void setNumWoundFatLiquefaction(String numWoundFatLiquefaction) {
        this.numWoundFatLiquefaction = numWoundFatLiquefaction;
    }

    public String getNumAsthma() {
        return numAsthma;
    }

    public void setNumAsthma(String numAsthma) {
        this.numAsthma = numAsthma;
    }

    public String getNumNephropathy() {
        return numNephropathy;
    }

    public void setNumNephropathy(String numNephropathy) {
        this.numNephropathy = numNephropathy;
    }

    public String getNumNervousDisease() {
        return numNervousDisease;
    }

    public void setNumNervousDisease(String numNervousDisease) {
        this.numNervousDisease = numNervousDisease;
    }

    public String getNumHeartDisease() {
        return numHeartDisease;
    }

    public void setNumHeartDisease(String numHeartDisease) {
        this.numHeartDisease = numHeartDisease;
    }

    public String getNumHeartAbnormal() {
        return numHeartAbnormal;
    }

    public void setNumHeartAbnormal(String numHeartAbnormal) {
        this.numHeartAbnormal = numHeartAbnormal;
    }

    public String getNumHematopathy() {
        return numHematopathy;
    }

    public void setNumHematopathy(String numHematopathy) {
        this.numHematopathy = numHematopathy;
    }

    public String getNumAnemia() {
        return numAnemia;
    }

    public void setNumAnemia(String numAnemia) {
        this.numAnemia = numAnemia;
    }

    public String getNumHepatitis() {
        return numHepatitis;
    }

    public void setNumHepatitis(String numHepatitis) {
        this.numHepatitis = numHepatitis;
    }

    public String getNumAusAntiPositive() {
        return numAusAntiPositive;
    }

    public void setNumAusAntiPositive(String numAusAntiPositive) {
        this.numAusAntiPositive = numAusAntiPositive;
    }

    public String getNumViralInfection() {
        return numViralInfection;
    }

    public void setNumViralInfection(String numViralInfection) {
        this.numViralInfection = numViralInfection;
    }

    public String getNumThyroid() {
        return numThyroid;
    }

    public void setNumThyroid(String numThyroid) {
        this.numThyroid = numThyroid;
    }

    public String getNumSLE() {
        return numSLE;
    }

    public void setNumSLE(String numSLE) {
        this.numSLE = numSLE;
    }

    public String getNumOtherInternalComplication() {
        return numOtherInternalComplication;
    }

    public void setNumOtherInternalComplication(String numOtherInternalComplication) {
        this.numOtherInternalComplication = numOtherInternalComplication;
    }

    public String getNumNewBirthSexMale() {
        return numNewBirthSexMale;
    }

    public void setNumNewBirthSexMale(String numNewBirthSexMale) {
        this.numNewBirthSexMale = numNewBirthSexMale;
    }

    public String getNumNewBirthSexFemale() {
        return numNewBirthSexFemale;
    }

    public void setNumNewBirthSexFemale(String numNewBirthSexFemale) {
        this.numNewBirthSexFemale = numNewBirthSexFemale;
    }

    public String getNumUnknow() {
        return numUnknow;
    }

    public void setNumUnknow(String numUnknow) {
        this.numUnknow = numUnknow;
    }

    public String getNumNewBirthWeight1000() {
        return numNewBirthWeight1000;
    }

    public void setNumNewBirthWeight1000(String numNewBirthWeight1000) {
        this.numNewBirthWeight1000 = numNewBirthWeight1000;
    }

    public String getNumNewBirthWeight10001499() {
        return numNewBirthWeight10001499;
    }

    public void setNumNewBirthWeight10001499(String numNewBirthWeight10001499) {
        this.numNewBirthWeight10001499 = numNewBirthWeight10001499;
    }

    public String getNumNewBirthWeight15001999() {
        return numNewBirthWeight15001999;
    }

    public void setNumNewBirthWeight15001999(String numNewBirthWeight15001999) {
        this.numNewBirthWeight15001999 = numNewBirthWeight15001999;
    }

    public String getNumNewBirthWeight20002499() {
        return numNewBirthWeight20002499;
    }

    public void setNumNewBirthWeight20002499(String numNewBirthWeight20002499) {
        this.numNewBirthWeight20002499 = numNewBirthWeight20002499;
    }

    public String getNumNewBirthWeight25002999() {
        return numNewBirthWeight25002999;
    }

    public void setNumNewBirthWeight25002999(String numNewBirthWeight25002999) {
        this.numNewBirthWeight25002999 = numNewBirthWeight25002999;
    }

    public String getNumNewBirthWeight30003499() {
        return numNewBirthWeight30003499;
    }

    public void setNumNewBirthWeight30003499(String numNewBirthWeight30003499) {
        this.numNewBirthWeight30003499 = numNewBirthWeight30003499;
    }

    public String getNumNewBirthWeight35003999() {
        return numNewBirthWeight35003999;
    }

    public void setNumNewBirthWeight35003999(String numNewBirthWeight35003999) {
        this.numNewBirthWeight35003999 = numNewBirthWeight35003999;
    }

    public String getNumNewBirthWeight40004499() {
        return numNewBirthWeight40004499;
    }

    public void setNumNewBirthWeight40004499(String numNewBirthWeight40004499) {
        this.numNewBirthWeight40004499 = numNewBirthWeight40004499;
    }

    public String getNumNewBirthWeight4500() {
        return numNewBirthWeight4500;
    }

    public void setNumNewBirthWeight4500(String numNewBirthWeight4500) {
        this.numNewBirthWeight4500 = numNewBirthWeight4500;
    }

    public String getNumLowWeight2500() {
        return numLowWeight2500;
    }

    public void setNumLowWeight2500(String numLowWeight2500) {
        this.numLowWeight2500 = numLowWeight2500;
    }

    public String getNumLowWeight1500() {
        return numLowWeight1500;
    }

    public void setNumLowWeight1500(String numLowWeight1500) {
        this.numLowWeight1500 = numLowWeight1500;
    }

    public String getNumAsphyxiaLight() {
        return numAsphyxiaLight;
    }

    public void setNumAsphyxiaLight(String numAsphyxiaLight) {
        this.numAsphyxiaLight = numAsphyxiaLight;
    }

    public String getNumAsphyxiaSerious() {
        return numAsphyxiaSerious;
    }

    public void setNumAsphyxiaSerious(String numAsphyxiaSerious) {
        this.numAsphyxiaSerious = numAsphyxiaSerious;
    }

    public String getNumSGA() {
        return numSGA;
    }

    public void setNumSGA(String numSGA) {
        this.numSGA = numSGA;
    }

    public String getNumRDS() {
        return numRDS;
    }

    public void setNumRDS(String numRDS) {
        this.numRDS = numRDS;
    }

    public String getNumPneumonia() {
        return numPneumonia;
    }

    public void setNumPneumonia(String numPneumonia) {
        this.numPneumonia = numPneumonia;
    }

    public String getNumMalformation() {
        return numMalformation;
    }

    public void setNumMalformation(String numMalformation) {
        this.numMalformation = numMalformation;
    }

    public String getNumInfection() {
        return numInfection;
    }

    public void setNumInfection(String numInfection) {
        this.numInfection = numInfection;
    }

    public String getNumBirthHarm() {
        return numBirthHarm;
    }

    public void setNumBirthHarm(String numBirthHarm) {
        this.numBirthHarm = numBirthHarm;
    }

    public String getNumBrainBlood() {
        return numBrainBlood;
    }

    public void setNumBrainBlood(String numBrainBlood) {
        this.numBrainBlood = numBrainBlood;
    }

    public String getNumHereditaryDisease() {
        return numHereditaryDisease;
    }

    public void setNumHereditaryDisease(String numHereditaryDisease) {
        this.numHereditaryDisease = numHereditaryDisease;
    }

    public String getNumDeadFetus() {
        return numDeadFetus;
    }

    public void setNumDeadFetus(String numDeadFetus) {
        this.numDeadFetus = numDeadFetus;
    }

    public String getNumDeadBirth() {
        return numDeadBirth;
    }

    public void setNumDeadBirth(String numDeadBirth) {
        this.numDeadBirth = numDeadBirth;
    }

    public String getNumNewBirthDead() {
        return numNewBirthDead;
    }

    public void setNumNewBirthDead(String numNewBirthDead) {
        this.numNewBirthDead = numNewBirthDead;
    }

    public String getNumRuYuan() {
        return numRuYuan;
    }

    public void setNumRuYuan(String numRuYuan) {
        this.numRuYuan = numRuYuan;
    }

    public String getNumBirthChuChan() {
        return numBirthChuChan;
    }

    public void setNumBirthChuChan(String numBirthChuChan) {
        this.numBirthChuChan = numBirthChuChan;
    }

    public String getNumPregFromGaowei() {
        return numPregFromGaowei;
    }

    public void setNumPregFromGaowei(String numPregFromGaowei) {
        this.numPregFromGaowei = numPregFromGaowei;
    }

}
