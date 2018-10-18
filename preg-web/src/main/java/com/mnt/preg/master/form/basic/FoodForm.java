
package com.mnt.preg.master.form.basic;

import java.util.List;

/**
 * 食物信息表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-2-5 zcq 初版
 */
public class FoodForm {

    /** 食物ID */
    private String foodId;

    /** 食物名称 */
    private String foodName;

    /** 食品别名,号分割 */
    private String foodAlias;

    /** 条码 */
    private String foodBarcode;

    /** 资料来源 */
    private String foodOrigin;

    /** 图片 */
    private String foodPic;

    /** 图片原始名称 */
    private String foodPicOld;

    /** 功效 */
    private String foodEfficacy;

    /** 菜肴做法 */
    private String foodMake;

    /** 口味 */
    private String foodTaste;

    /** 所属食谱类型 */
    private String foodType;

    /** 单位 */
    private String foodUnit;

    /** 烹饪方式 */
    private String foodCuisine;

    /** 适合餐类-数据','分隔 */
    private String foodMealType;

    /** 是否主食 */
    private Integer foodStaple;

    /** 是否推荐 */
    private String foodIsMake;

    /** 有益成分 */
    private List<String> foodBenefitList;

    /** 有害成分 */
    private List<String> foodHarmList;

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

    public String getFoodAlias() {
        return foodAlias;
    }

    public void setFoodAlias(String foodAlias) {
        this.foodAlias = foodAlias;
    }

    public String getFoodBarcode() {
        return foodBarcode;
    }

    public void setFoodBarcode(String foodBarcode) {
        this.foodBarcode = foodBarcode;
    }

    public String getFoodOrigin() {
        return foodOrigin;
    }

    public void setFoodOrigin(String foodOrigin) {
        this.foodOrigin = foodOrigin;
    }

    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }

    public String getFoodPicOld() {
        return foodPicOld;
    }

    public void setFoodPicOld(String foodPicOld) {
        this.foodPicOld = foodPicOld;
    }

    public String getFoodEfficacy() {
        return foodEfficacy;
    }

    public void setFoodEfficacy(String foodEfficacy) {
        this.foodEfficacy = foodEfficacy;
    }

    public String getFoodMake() {
        return foodMake;
    }

    public void setFoodMake(String foodMake) {
        this.foodMake = foodMake;
    }

    public String getFoodTaste() {
        return foodTaste;
    }

    public void setFoodTaste(String foodTaste) {
        this.foodTaste = foodTaste;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public String getFoodCuisine() {
        return foodCuisine;
    }

    public void setFoodCuisine(String foodCuisine) {
        this.foodCuisine = foodCuisine;
    }

    public String getFoodMealType() {
        return foodMealType;
    }

    public void setFoodMealType(String foodMealType) {
        this.foodMealType = foodMealType;
    }

    public Integer getFoodStaple() {
        return foodStaple;
    }

    public void setFoodStaple(Integer foodStaple) {
        this.foodStaple = foodStaple;
    }

    public List<String> getFoodBenefitList() {
        return foodBenefitList;
    }

    public void setFoodBenefitList(List<String> foodBenefitList) {
        this.foodBenefitList = foodBenefitList;
    }

    public List<String> getFoodHarmList() {
        return foodHarmList;
    }

    public void setFoodHarmList(List<String> foodHarmList) {
        this.foodHarmList = foodHarmList;
    }

    public String getFoodIsMake() {
        return foodIsMake;
    }

    public void setFoodIsMake(String foodIsMake) {
        this.foodIsMake = foodIsMake;
    }

}
