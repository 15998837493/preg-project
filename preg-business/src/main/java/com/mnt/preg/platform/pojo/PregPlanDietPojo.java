
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 干预方案--食谱模版明细
 *
 * @author zcq
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2015-8-1 zcq 初版
 */
public class PregPlanDietPojo implements Serializable {

    private static final long serialVersionUID = 2151736719360249269L;

    /**
     * 主键
     */
    @QueryFieldAnnotation
    private String id;

    /**
     * 干预方案主键
     */
    @QueryFieldAnnotation
    private String planId;

    /**
     * 食谱
     */
    @QueryFieldAnnotation
    private String dietId;

    /**
     * 天数
     */
    @QueryFieldAnnotation
    private String foodDay;

    /**
     * 餐次
     */
    @QueryFieldAnnotation
    private String foodMeal;

    /**
     * 餐次
     */
    @QueryFieldAnnotation
    private String foodMealName;

    /**
     * 食物主键
     */
    @QueryFieldAnnotation
    private String foodId;

    /**
     * 食物名称
     */
    @QueryFieldAnnotation
    private String foodName;

    /**
     * 食材 id
     */
    @QueryFieldAnnotation
    private String fmId;
    /**
     * 食物成分
     */
    @QueryFieldAnnotation
    private String foodMaterialName;

    /**
     * 食物成分重量
     */
    @QueryFieldAnnotation
    private String foodMaterialAmount;

    /**
     * 推荐热量
     */
    @QueryFieldAnnotation
    private String amountLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
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

    public String getFoodMealName() {
        return foodMealName;
    }

    public void setFoodMealName(String foodMealName) {
        this.foodMealName = foodMealName;
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

    public String getFoodMaterialAmount() {
        return foodMaterialAmount;
    }

    public void setFoodMaterialAmount(String foodMaterialAmount) {
        this.foodMaterialAmount = foodMaterialAmount;
    }

    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    public String getAmountLevel() {
        return amountLevel;
    }

    public void setAmountLevel(String amountLevel) {
        this.amountLevel = amountLevel;
    }
}
