
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.preg.examitem.util.DietaryReviewPregnancyDRIs;
import com.mnt.preg.examitem.util.PregnancyDRIs;

/**
 * 每日摄入营养素明细表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-12 zcq 初版
 */
public class PregDietAnalysePojo implements Serializable {

    private static final long serialVersionUID = 2308332578985459229L;

    // ------------【饮食中每种营养素的含量】-----------
    /** 餐次编码 */
    private String mealsId;

    /** 能量_kcal */
    private BigDecimal foodEnergy;

    /** 脂肪g */
    private String foodFat;

    /** 蛋白质g */
    private BigDecimal foodProtid;

    /** 碳水化合物(g) */
    private String foodCbr;

    /** 膳食纤维(g) */
    private BigDecimal foodDf;

    /** 维生素A(μg) */
    private BigDecimal foodVa;

    /** 灰分(g) */
    private BigDecimal foodAshc;

    /** 胡萝卜素(μg) */
    private BigDecimal foodCarotin;

    /** 维生素B1(mg) */
    private BigDecimal foodVb1;

    /** 维生素B2(mg) */
    private BigDecimal foodVb2;

    /** 烟酸(mg) */
    private BigDecimal foodAf;

    /** 维生素C */
    private BigDecimal foodVc;

    /** 维生素E */
    private BigDecimal foodVe;

    /** 钙(mg) */
    private BigDecimal foodEca;

    /** 磷(mg) */
    private BigDecimal foodEp;

    /** 钾(mg) */
    private BigDecimal foodEk;

    /** 钠(mg) */
    private BigDecimal foodEna;

    /** 镁(mg) */
    private BigDecimal foodEmg;

    /** 铁(mg) */
    private BigDecimal foodEfe;

    /** 锌(mg) */
    private BigDecimal foodEzn;

    /** 硒(μg) */
    private BigDecimal foodEse;

    /** 铜 */
    private BigDecimal foodEcu;

    /** 锰(mg) */
    private BigDecimal foodEmn;

    /** 胆固醇(mg) */
    private BigDecimal foodCho;

    // ------------【饮食中每餐的供能量】-----------
    /** 早餐能量（含加餐） */
    private BigDecimal energyBreak;

    /** 午餐能量（含加餐） */
    private BigDecimal energyLunch;

    /** 晚餐能量（含加餐） */
    private BigDecimal energySupper;

    // ------------【饮食中每餐三大营养素的供能比】-----------
    /** 脂肪供能比例 */
    private BigDecimal foodFatPercent;

    /** 蛋白质供能比例 */
    private BigDecimal foodProtidPercent;

    /** 碳水化合物供能比例 */
    private BigDecimal foodCbrPercent;

    // ------------【饮食中每餐明细】-----------
    /** 食物ID */
    private String foodId;

    /** 食物名称 */
    private String foodName;

    /** 食物数量 */
    private BigDecimal foodAmount;

    // ------------【饮食中每餐的GL值】-----------
    /** GL值 */
    private BigDecimal gl;

    /** 早餐GL（含加餐） */
    private Integer breakGl;

    /** 午餐GL（含加餐） */
    private Integer lunchGl;

    /** 晚餐GL（含加餐） */
    private Integer supperGl;

    // ------------【饮食中蛋白质和脂肪重量】-----------
    /** 是否优质蛋白质 */
    private String fmProtidFlag;

    /** 油脂类型 */
    private String fmFatType;

    /** 蛋白质重量 */
    private BigDecimal protidAmount;

    /** 油脂重量 */
    private BigDecimal fatAmount;

    /** 优质蛋白质重量 */
    private BigDecimal foodProtidYes;

    /** 非优质蛋白质重量 */
    private BigDecimal foodProtidNo;

    /** 动物脂肪重量 */
    private BigDecimal foodFatAnimal;

    /** 植物脂肪重量 */
    private BigDecimal foodFatPlan;

    // ------------【孕期标准值】-----------
    /** 孕期标准值 */
    private PregnancyDRIs dris;

    /** 孕期标准值(24小时膳食回顾用) */
    private DietaryReviewPregnancyDRIs RPDris;

    public String getMealsId() {
        return mealsId;
    }

    public void setMealsId(String mealsId) {
        this.mealsId = mealsId;
    }

    public BigDecimal getFoodEnergy() {
        return foodEnergy;
    }

    public void setFoodEnergy(BigDecimal foodEnergy) {
        this.foodEnergy = foodEnergy;
    }

    public String getFoodFat() {
        return foodFat;
    }

    public void setFoodFat(String foodFat) {
        this.foodFat = foodFat;
    }

    public BigDecimal getFoodProtid() {
        return foodProtid;
    }

    public void setFoodProtid(BigDecimal foodProtid) {
        this.foodProtid = foodProtid;
    }

    public String getFoodCbr() {
        return foodCbr;
    }

    public void setFoodCbr(String foodCbr) {
        this.foodCbr = foodCbr;
    }

    public BigDecimal getFoodDf() {
        return foodDf;
    }

    public void setFoodDf(BigDecimal foodDf) {
        this.foodDf = foodDf;
    }

    public BigDecimal getFoodVa() {
        return foodVa;
    }

    public void setFoodVa(BigDecimal foodVa) {
        this.foodVa = foodVa;
    }

    public BigDecimal getFoodAshc() {
        return foodAshc;
    }

    public void setFoodAshc(BigDecimal foodAshc) {
        this.foodAshc = foodAshc;
    }

    public BigDecimal getFoodCarotin() {
        return foodCarotin;
    }

    public void setFoodCarotin(BigDecimal foodCarotin) {
        this.foodCarotin = foodCarotin;
    }

    public BigDecimal getFoodVb1() {
        return foodVb1;
    }

    public void setFoodVb1(BigDecimal foodVb1) {
        this.foodVb1 = foodVb1;
    }

    public BigDecimal getFoodVb2() {
        return foodVb2;
    }

    public void setFoodVb2(BigDecimal foodVb2) {
        this.foodVb2 = foodVb2;
    }

    public BigDecimal getFoodAf() {
        return foodAf;
    }

    public void setFoodAf(BigDecimal foodAf) {
        this.foodAf = foodAf;
    }

    public BigDecimal getFoodVc() {
        return foodVc;
    }

    public void setFoodVc(BigDecimal foodVc) {
        this.foodVc = foodVc;
    }

    public BigDecimal getFoodVe() {
        return foodVe;
    }

    public void setFoodVe(BigDecimal foodVe) {
        this.foodVe = foodVe;
    }

    public BigDecimal getFoodEca() {
        return foodEca;
    }

    public void setFoodEca(BigDecimal foodEca) {
        this.foodEca = foodEca;
    }

    public BigDecimal getFoodEp() {
        return foodEp;
    }

    public void setFoodEp(BigDecimal foodEp) {
        this.foodEp = foodEp;
    }

    public BigDecimal getFoodEk() {
        return foodEk;
    }

    public void setFoodEk(BigDecimal foodEk) {
        this.foodEk = foodEk;
    }

    public BigDecimal getFoodEna() {
        return foodEna;
    }

    public void setFoodEna(BigDecimal foodEna) {
        this.foodEna = foodEna;
    }

    public BigDecimal getFoodEmg() {
        return foodEmg;
    }

    public void setFoodEmg(BigDecimal foodEmg) {
        this.foodEmg = foodEmg;
    }

    public BigDecimal getFoodEfe() {
        return foodEfe;
    }

    public void setFoodEfe(BigDecimal foodEfe) {
        this.foodEfe = foodEfe;
    }

    public BigDecimal getFoodEzn() {
        return foodEzn;
    }

    public void setFoodEzn(BigDecimal foodEzn) {
        this.foodEzn = foodEzn;
    }

    public BigDecimal getFoodEse() {
        return foodEse;
    }

    public void setFoodEse(BigDecimal foodEse) {
        this.foodEse = foodEse;
    }

    public BigDecimal getFoodEcu() {
        return foodEcu;
    }

    public void setFoodEcu(BigDecimal foodEcu) {
        this.foodEcu = foodEcu;
    }

    public BigDecimal getFoodEmn() {
        return foodEmn;
    }

    public void setFoodEmn(BigDecimal foodEmn) {
        this.foodEmn = foodEmn;
    }

    public BigDecimal getFoodCho() {
        return foodCho;
    }

    public void setFoodCho(BigDecimal foodCho) {
        this.foodCho = foodCho;
    }

    public BigDecimal getEnergyBreak() {
        return energyBreak;
    }

    public void setEnergyBreak(BigDecimal energyBreak) {
        this.energyBreak = energyBreak;
    }

    public BigDecimal getEnergyLunch() {
        return energyLunch;
    }

    public void setEnergyLunch(BigDecimal energyLunch) {
        this.energyLunch = energyLunch;
    }

    public BigDecimal getEnergySupper() {
        return energySupper;
    }

    public void setEnergySupper(BigDecimal energySupper) {
        this.energySupper = energySupper;
    }

    public BigDecimal getFoodFatPercent() {
        return foodFatPercent;
    }

    public void setFoodFatPercent(BigDecimal foodFatPercent) {
        this.foodFatPercent = foodFatPercent;
    }

    public BigDecimal getFoodProtidPercent() {
        return foodProtidPercent;
    }

    public void setFoodProtidPercent(BigDecimal foodProtidPercent) {
        this.foodProtidPercent = foodProtidPercent;
    }

    public BigDecimal getFoodCbrPercent() {
        return foodCbrPercent;
    }

    public void setFoodCbrPercent(BigDecimal foodCbrPercent) {
        this.foodCbrPercent = foodCbrPercent;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public BigDecimal getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(BigDecimal foodAmount) {
        this.foodAmount = foodAmount;
    }

    public BigDecimal getGl() {
        return gl;
    }

    public void setGl(BigDecimal gl) {
        this.gl = gl;
    }

    public Integer getBreakGl() {
        return breakGl;
    }

    public void setBreakGl(Integer breakGl) {
        this.breakGl = breakGl;
    }

    public Integer getLunchGl() {
        return lunchGl;
    }

    public void setLunchGl(Integer lunchGl) {
        this.lunchGl = lunchGl;
    }

    public Integer getSupperGl() {
        return supperGl;
    }

    public void setSupperGl(Integer supperGl) {
        this.supperGl = supperGl;
    }

    public String getFmProtidFlag() {
        return fmProtidFlag;
    }

    public void setFmProtidFlag(String fmProtidFlag) {
        this.fmProtidFlag = fmProtidFlag;
    }

    public String getFmFatType() {
        return fmFatType;
    }

    public void setFmFatType(String fmFatType) {
        this.fmFatType = fmFatType;
    }

    public BigDecimal getProtidAmount() {
        return protidAmount;
    }

    public void setProtidAmount(BigDecimal protidAmount) {
        this.protidAmount = protidAmount;
    }

    public BigDecimal getFatAmount() {
        return fatAmount;
    }

    public void setFatAmount(BigDecimal fatAmount) {
        this.fatAmount = fatAmount;
    }

    public BigDecimal getFoodProtidYes() {
        return foodProtidYes;
    }

    public void setFoodProtidYes(BigDecimal foodProtidYes) {
        this.foodProtidYes = foodProtidYes;
    }

    public BigDecimal getFoodProtidNo() {
        return foodProtidNo;
    }

    public void setFoodProtidNo(BigDecimal foodProtidNo) {
        this.foodProtidNo = foodProtidNo;
    }

    public BigDecimal getFoodFatAnimal() {
        return foodFatAnimal;
    }

    public void setFoodFatAnimal(BigDecimal foodFatAnimal) {
        this.foodFatAnimal = foodFatAnimal;
    }

    public BigDecimal getFoodFatPlan() {
        return foodFatPlan;
    }

    public void setFoodFatPlan(BigDecimal foodFatPlan) {
        this.foodFatPlan = foodFatPlan;
    }

    public PregnancyDRIs getDris() {
        return dris;
    }

    public void setDris(PregnancyDRIs dris) {
        this.dris = dris;
    }

    public DietaryReviewPregnancyDRIs getRPDris() {
        return RPDris;
    }

    public void setRPDris(DietaryReviewPregnancyDRIs rPDris) {
        RPDris = rPDris;
    }

}
