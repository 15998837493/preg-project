/*
 * FoodCondition.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.condition;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 食物检索条件
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
public class FoodCondition extends PageCondition {

    private static final long serialVersionUID = 1876656860359626335L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodId;

    /** 食物名称 */
    private String foodName;

    /** 烹饪方式 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodCuisine;

    /** 食品别名,号分割 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String foodAlias;

    /** 食物全拼音 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String foodASpell;

    /** 食物缩写拼音 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String foodSSpell;

    /** 食物适宜餐次 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String foodMealType;

    /** 食材名称 */
    private String fmName;

    /** 食材类型主键 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodTreeType;

    /** 食材类型主键模糊查询条件 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE_AFTER, name = "foodTreeType")
    private String foodTreeTypeLike;

    /** 删除标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public String getFoodCuisine() {
        return foodCuisine;
    }

    public void setFoodCuisine(String foodCuisine) {
        this.foodCuisine = foodCuisine;
    }

    public String getFoodAlias() {
        return foodAlias;
    }

    public void setFoodAlias(String foodAlias) {
        this.foodAlias = foodAlias;
    }

    public String getFoodASpell() {
        return foodASpell;
    }

    public void setFoodASpell(String foodASpell) {
        this.foodASpell = foodASpell;
    }

    public String getFoodSSpell() {
        return foodSSpell;
    }

    public void setFoodSSpell(String foodSSpell) {
        this.foodSSpell = foodSSpell;
    }

    public String getFoodMealType() {
        return foodMealType;
    }

    public void setFoodMealType(String foodMealType) {
        this.foodMealType = foodMealType;
    }

    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getFoodTreeType() {
        return foodTreeType;
    }

    public void setFoodTreeType(String foodTreeType) {
        this.foodTreeType = foodTreeType;
    }

    public String getFoodTreeTypeLike() {
        return foodTreeTypeLike;
    }

    public void setFoodTreeTypeLike(String foodTreeTypeLike) {
        this.foodTreeTypeLike = foodTreeTypeLike;
    }

}
