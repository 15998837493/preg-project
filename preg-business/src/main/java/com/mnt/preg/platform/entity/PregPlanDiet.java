
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 干预方案食谱模版明细
 *
 * @author zcq
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2015-8-1 zcq 初版
 */
@Entity
@Table(name = "user_plan_diet")
public class PregPlanDiet extends MappedEntity {

    private static final long serialVersionUID = 2151736719360249269L;

    /**
     * 主键
     */
    private String id;

    /**
     * 干预方案主键
     */
    private String planId;

    /**
     * 食谱
     */
    @UpdateAnnotation
    private String dietId;

    /**
     * 天数
     */
    private String foodDay;

    /**
     * 餐次
     */
    private String foodMeal;

    /**
     * 餐次
     */
    private String foodMealName;

    /**
     * 食物主键
     */
    private String foodId;

    /**
     * 食物名称
     */
    private String foodName;

    /**
     * 食材 id
     */
    private String fmId;

    /**
     * 食物成分
     */
    private String foodMaterialName;

    /**
     * 食物成分重量
     */
    @UpdateAnnotation
    private String foodMaterialAmount;

    /**
     * 推荐热量
     */
    private String amountLevel;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "plan_id")
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Column(name = "food_id")
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Column(name = "food_name")
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    @Column(name = "diet_id")
    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    @Column(name = "food_day")
    public String getFoodDay() {
        return foodDay;
    }

    public void setFoodDay(String foodDay) {
        this.foodDay = foodDay;
    }

    @Column(name = "food_meal")
    public String getFoodMeal() {
        return foodMeal;
    }

    public void setFoodMeal(String foodMeal) {
        this.foodMeal = foodMeal;
    }

    @Column(name = "food_material_name")
    public String getFoodMaterialName() {
        return foodMaterialName;
    }

    public void setFoodMaterialName(String foodMaterialName) {
        this.foodMaterialName = foodMaterialName;
    }

    @Column(name = "food_material_amount")
    public String getFoodMaterialAmount() {
        return foodMaterialAmount;
    }

    public void setFoodMaterialAmount(String foodMaterialAmount) {
        this.foodMaterialAmount = foodMaterialAmount;
    }

    @Column(name = "food_meal_name")
    public String getFoodMealName() {
        return foodMealName;
    }

    public void setFoodMealName(String foodMealName) {
        this.foodMealName = foodMealName;
    }

    @Column(name = "fm_id")
    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    @Column(name = "amount_level")
    public String getAmountLevel() {
        return amountLevel;
    }

    public void setAmountLevel(String amountLevel) {
        this.amountLevel = amountLevel;
    }
}
