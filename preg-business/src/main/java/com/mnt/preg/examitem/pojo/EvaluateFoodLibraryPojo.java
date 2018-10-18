/*
 * EvaluateFoodLibraryVo.java	 1.0   2017-3-8 
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;


/**
 * 
 * 营养评估实物库信息
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-8 mnt_zhangjing 初版
 */
public class EvaluateFoodLibraryPojo implements Serializable {

    private static final long serialVersionUID = 7866480267650672593L;

    /** 主键 */
    @QueryFieldAnnotation
    private String foodId;

    /** 食物名称 */
    @QueryFieldAnnotation
    private String foodName;

    /** 食物备注 */
    @QueryFieldAnnotation
    private String foodMark;

    /** 能量_kcal */
    @QueryFieldAnnotation
    private BigDecimal foodEnergy;

    /** 脂肪g */
    @QueryFieldAnnotation
    private BigDecimal foodFat;

    /** 蛋白质g */
    @QueryFieldAnnotation
    private BigDecimal foodProtid;

    /** 碳水化合物(g) */
    @QueryFieldAnnotation
    private BigDecimal foodCbr;

    /** 饱和脂肪酸 */
    @QueryFieldAnnotation
    private BigDecimal foodSfa;

    /** 单不饱和脂肪酸 */
    @QueryFieldAnnotation
    private BigDecimal foodMfa;

    /** 多不饱和脂肪酸 */
    @QueryFieldAnnotation
    private BigDecimal foodPfa;

    /** 膳食纤维(g) */
    @QueryFieldAnnotation
    private BigDecimal foodDf;

    /** 维生素A(ug) */
    @QueryFieldAnnotation
    private BigDecimal foodVa;

    /** 灰分(g) */
    @QueryFieldAnnotation
    private BigDecimal foodAshc;

    /** 胆固醇 */
    @QueryFieldAnnotation
    private BigDecimal foodCho;

    /** 胡萝卜素(μg) */
    @QueryFieldAnnotation
    private BigDecimal foodCarotin;

    /** 维生素B1(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodVb1;

    /** 维生素B2(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodVb2;

    /** 维生素B6(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodVb6;

    /** 维生素B12(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodVb12;

    /** 维生素B9(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodVb9;

    /** 烟酸(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodAf;

    /** 维生素C */
    @QueryFieldAnnotation
    private BigDecimal foodVc;

    /** 维生素E */
    @QueryFieldAnnotation
    private BigDecimal foodVe;

    /** 钙(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEca;

    /** 磷(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEp;

    /** 钾(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEk;

    /** 钠(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEna;

    /** 镁(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEmg;

    /** 铁(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEfe;

    /** 锌(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEzn;

    /** 硒(μg) */
    @QueryFieldAnnotation
    private BigDecimal foodEse;

    /** 铜 */
    @QueryFieldAnnotation
    private BigDecimal foodEcu;

    /** 锰(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodEmn;

    /** 碘(mg) */
    @QueryFieldAnnotation
    private BigDecimal foodIodine;

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

    public String getFoodMark() {
        return foodMark;
    }

    public void setFoodMark(String foodMark) {
        this.foodMark = foodMark;
    }

    public BigDecimal getFoodEnergy() {
        return foodEnergy;
    }

    public void setFoodEnergy(BigDecimal foodEnergy) {
        this.foodEnergy = foodEnergy;
    }

    public BigDecimal getFoodFat() {
        return foodFat;
    }

    public void setFoodFat(BigDecimal foodFat) {
        this.foodFat = foodFat;
    }

    public BigDecimal getFoodProtid() {
        return foodProtid;
    }

    public void setFoodProtid(BigDecimal foodProtid) {
        this.foodProtid = foodProtid;
    }

    public BigDecimal getFoodCbr() {
        return foodCbr;
    }

    public void setFoodCbr(BigDecimal foodCbr) {
        this.foodCbr = foodCbr;
    }

    public BigDecimal getFoodSfa() {
        return foodSfa;
    }

    public void setFoodSfa(BigDecimal foodSfa) {
        this.foodSfa = foodSfa;
    }

    public BigDecimal getFoodMfa() {
        return foodMfa;
    }

    public void setFoodMfa(BigDecimal foodMfa) {
        this.foodMfa = foodMfa;
    }

    public BigDecimal getFoodPfa() {
        return foodPfa;
    }

    public void setFoodPfa(BigDecimal foodPfa) {
        this.foodPfa = foodPfa;
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

    public BigDecimal getFoodCho() {
        return foodCho;
    }

    public void setFoodCho(BigDecimal foodCho) {
        this.foodCho = foodCho;
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

    public BigDecimal getFoodVb6() {
        return foodVb6;
    }

    public void setFoodVb6(BigDecimal foodVb6) {
        this.foodVb6 = foodVb6;
    }

    public BigDecimal getFoodVb12() {
        return foodVb12;
    }

    public void setFoodVb12(BigDecimal foodVb12) {
        this.foodVb12 = foodVb12;
    }

    public BigDecimal getFoodVb9() {
        return foodVb9;
    }

    public void setFoodVb9(BigDecimal foodVb9) {
        this.foodVb9 = foodVb9;
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

    public BigDecimal getFoodIodine() {
        return foodIodine;
    }

    public void setFoodIodine(BigDecimal foodIodine) {
        this.foodIodine = foodIodine;
    }

}
