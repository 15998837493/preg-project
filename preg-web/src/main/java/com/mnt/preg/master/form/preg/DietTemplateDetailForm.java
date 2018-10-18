
package com.mnt.preg.master.form.preg;

/**
 * 食物模版信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-21 zcq 初版
 */
public class DietTemplateDetailForm {

    /** 主键 */
    private String id;

    /** 模版编码 */
    private String dietId;

    /** 食物天数 */
    private String foodDay;

    /** 餐次 */
    private String foodMeal;

    /** 食物编码 */
    private String foodId;

    /** 食物名称 */
    private String foodName;

    /** 食物成分名称 */
    private String foodMaterialName;

    /** 食物成分用量 */
    private String foodMaterialAmount;

    /** 能量范围 */
    private String amountLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
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

    public String getFoodMaterialName() {
        return foodMaterialName;
    }

    public void setFoodMaterialName(String foodMaterialName) {
        this.foodMaterialName = foodMaterialName;
    }

    public String getFoodDay() {
        return foodDay;
    }

    public void setFoodDay(String foodDay) {
        this.foodDay = foodDay;
    }

    public String getFoodMeal() {
        return foodMeal;
    }

    public void setFoodMeal(String foodMeal) {
        this.foodMeal = foodMeal;
    }

    public String getFoodMaterialAmount() {
        return foodMaterialAmount;
    }

    public void setFoodMaterialAmount(String foodMaterialAmount) {
        this.foodMaterialAmount = foodMaterialAmount;
    }

    public String getAmountLevel() {
        return amountLevel;
    }

    public void setAmountLevel(String amountLevel) {
        this.amountLevel = amountLevel;
    }

}
