
package com.mnt.preg.examitem.util;

/**
 * 孕期DRIs算法类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-25 zcq 初版
 */
public class PregnancyDRIs {

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

    // -----------饮食相关-------------
    /** 饮水 */
    private String water;

    /** 谷类 */
    private String gulei;

    /** 薯类 */
    private String shulei;

    /** 蔬菜 */
    private String shucai;

    /** 水果 */
    private String shuiguo;

    /** 鱼肉蛋 */
    private String yuroudan;

    /** 大豆 */
    private String dadou;

    /** 坚果 */
    private String jianguo;

    /** 乳制品 */
    private String ruzhipin;

    public PregnancyDRIs() {
        super();
    }

    // 膳食营养素推荐摄入量DRIs包括估计平均需要量（ERA）、推荐膳食供给量或推荐摄人量（RNI）、适宜摄人量（AI）和可耐受最高摄人量（UL）
    // 能量，饮食
    public PregnancyDRIs(String pregnancy) {
        if (pregnancy.equals("pregnancy_pre")) {// 孕前期
            this.setVals("1800", "450~630", "630~810", "450~630", "55", "27.5", "1500~1700",
                    "200~250#4~5", "50#0.5", "300~500#3~5", "200~350#2~3.5", "120~200#2~4", "15#0.75", "10#1",
                    "300~500#1.5");
        } else if (pregnancy.equals("pregnancy_mid")) {// 孕中期
            this.setVals("2100", "525~735", "735~945", "525~735", "70", "35",
                    "1500~1700", "200~250#4~5", "50#0.5", "300~500#3~5", "200~400#2~4", "150~200#3~4", "15#0.75",
                    "10#1", "300~500#1.5~2.5");
        } else if (pregnancy.equals("pregnancy_late")) {// 孕后期
            this.setVals("2250", "562.5~787.5", "787.5~1012.5", "562.5~787.5", "85", "42.5", "1500~1700",
                    "200~250#4~5",
                    "50#0.5", "300~500#3~5", "200~400#2~4", "200~250#4~5", "15#0.75", "10#1", "300~500#1.5~2.5");
        }
    }

    public void setVals(String foodEnergy, String energyBreak, String energyLunch, String energySupper,
            String foodProtid, String foodProtidYes, String water, String gulei, String shulei, String shucai,
            String shuiguo, String yuroudan, String dadou, String jianguo, String ruzhipin) {
        // 能量
        this.foodEnergy = foodEnergy;
        this.energyBreak = energyBreak;
        this.energyLunch = energyLunch;
        this.energySupper = energySupper;
        this.foodProtid = foodProtid;
        this.foodProtidYes = foodProtidYes;
        // 饮食
        this.water = water;
        this.gulei = gulei;
        this.shulei = shulei;
        this.shucai = shucai;
        this.shuiguo = shuiguo;
        this.yuroudan = yuroudan;
        this.dadou = dadou;
        this.jianguo = jianguo;
        this.ruzhipin = ruzhipin;
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

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getGulei() {
        return gulei;
    }

    public void setGulei(String gulei) {
        this.gulei = gulei;
    }

    public String getShulei() {
        return shulei;
    }

    public void setShulei(String shulei) {
        this.shulei = shulei;
    }

    public String getShucai() {
        return shucai;
    }

    public void setShucai(String shucai) {
        this.shucai = shucai;
    }

    public String getShuiguo() {
        return shuiguo;
    }

    public void setShuiguo(String shuiguo) {
        this.shuiguo = shuiguo;
    }

    public String getYuroudan() {
        return yuroudan;
    }

    public void setYuroudan(String yuroudan) {
        this.yuroudan = yuroudan;
    }

    public String getDadou() {
        return dadou;
    }

    public void setDadou(String dadou) {
        this.dadou = dadou;
    }

    public String getJianguo() {
        return jianguo;
    }

    public void setJianguo(String jianguo) {
        this.jianguo = jianguo;
    }

    public String getRuzhipin() {
        return ruzhipin;
    }

    public void setRuzhipin(String ruzhipin) {
        this.ruzhipin = ruzhipin;
    }

}
