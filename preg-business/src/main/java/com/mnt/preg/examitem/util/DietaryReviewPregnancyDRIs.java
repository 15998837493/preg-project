
package com.mnt.preg.examitem.util;

import java.math.BigDecimal;

/**
 * 孕期DRIs算法类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-25 zcq 初版
 */
public class DietaryReviewPregnancyDRIs {

    // -----------能量相关-------------
    /** 一日能量（EER） */
    private String foodEnergy;

    /** 早餐能量（EER） */
    private String energyBreak;

    /** 午餐能量（EER） */
    private String energyLunch;

    /** 晚餐能量（EER） */
    private String energySupper;

    /** 总蛋白质重量 */
    private String foodProtid;

    /** 优质蛋白质重量 */
    private String foodProtidYes;

    public DietaryReviewPregnancyDRIs() {
        super();
    }

    public DietaryReviewPregnancyDRIs(String pregnancy, BigDecimal height, BigDecimal weight) {
        // 总蛋白质重量
        this.foodProtid = weight.multiply(new BigDecimal(1)) + "~" + weight.multiply(new BigDecimal(2));

        if (pregnancy.equals("pregnancy_pre")) {// 孕前期
            // 优质蛋白质重量
            this.foodProtidYes = "27.5";
        } else if (pregnancy.equals("pregnancy_mid")) {// 孕中期
            // 优质蛋白质重量
            this.foodProtidYes = "35";
        } else if (pregnancy.equals("pregnancy_late")) {// 孕后期
            // 优质蛋白质重量
            this.foodProtidYes = "42.5";
        }
    }

    public String getFoodEnergy() {
        return foodEnergy;
    }

    public void setFoodEnergy(String foodEnergy) {
        this.foodEnergy = foodEnergy;
    }

    public String getEnergyBreak() {
        return energyBreak;
    }

    public void setEnergyBreak(String energyBreak) {
        this.energyBreak = energyBreak;
    }

    public String getEnergyLunch() {
        return energyLunch;
    }

    public void setEnergyLunch(String energyLunch) {
        this.energyLunch = energyLunch;
    }

    public String getEnergySupper() {
        return energySupper;
    }

    public void setEnergySupper(String energySupper) {
        this.energySupper = energySupper;
    }

    public String getFoodProtid() {
        return foodProtid;
    }

    public void setFoodProtid(String foodProtid) {
        this.foodProtid = foodProtid;
    }

    public String getFoodProtidYes() {
        return foodProtidYes;
    }

    public void setFoodProtidYes(String foodProtidYes) {
        this.foodProtidYes = foodProtidYes;
    }

}
