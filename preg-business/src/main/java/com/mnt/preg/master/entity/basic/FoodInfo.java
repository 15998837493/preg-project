/*
 * FoodInfo.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 食物信息表
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
@Entity
@Table(name = "mas_food")
public class FoodInfo extends MappedEntity {

    private static final long serialVersionUID = 2151736719360249269L;

    // 主键
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodId;

    // 食物名称
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String foodName;

    // 食品别名,号分割
    @UpdateAnnotation
    private String foodAlias;

    // 食物全拼音
    @UpdateAnnotation
    private String foodASpell;

    // 食物缩写拼音food_sspell
    @UpdateAnnotation
    private String foodSSpell;

    // 条码
    @UpdateAnnotation
    private String foodBarcode;

    // 资料来源
    @UpdateAnnotation
    private String foodOrigin;

    // 图片
    @UpdateAnnotation
    private String foodPic;

    // 功效
    @UpdateAnnotation
    private String foodEfficacy;

    // 菜肴做法
    @UpdateAnnotation
    private String foodMake;

    // 口味
    @UpdateAnnotation
    private String foodTaste;

    // 所属食谱类型
    @UpdateAnnotation
    private String foodType;

    // 单位
    @UpdateAnnotation
    private String foodUnit;

    // 烹饪方式
    @UpdateAnnotation
    private String foodCuisine;

    // 适合餐类-数据','分隔
    @UpdateAnnotation
    private String foodMealType;

    // 适合饮食的标签-数据','分隔
    @UpdateAnnotation
    private String foodBenefit;

    // 不好的饮食标签-数据','分隔
    @UpdateAnnotation
    private String foodHarm;

    // 是否主食
    @UpdateAnnotation
    private Integer foodStaple;

    // 应用次数
    @UpdateAnnotation
    private Integer foodCounts;

    // 食谱类型主键
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodTreeType;
    
    // 菜系
    private String foodStyleCook;
        
    // 过敏源
    private String foodAllergen;
    
    // 类别 （肉类、蛋类等）
    private String foodSort;
    
    // 类别 （荤素菜）
    private String foodMeatVegetable;
    

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
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

    @Column(name = "food_alias")
    public String getFoodAlias() {
        return foodAlias;
    }

    public void setFoodAlias(String foodAlias) {
        this.foodAlias = foodAlias;
    }

    @Column(name = "food_aspell")
    public String getFoodASpell() {
        return foodASpell;
    }

    public void setFoodASpell(String foodASpell) {
        this.foodASpell = foodASpell;
    }

    @Column(name = "food_sspell")
    public String getFoodSSpell() {
        return foodSSpell;
    }

    public void setFoodSSpell(String foodSSpell) {
        this.foodSSpell = foodSSpell;
    }

    @Column(name = "food_barcode")
    public String getFoodBarcode() {
        return foodBarcode;
    }

    public void setFoodBarcode(String foodBarcode) {
        this.foodBarcode = foodBarcode;
    }

    @Column(name = "food_unit")
    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    @Column(name = "food_origin")
    public String getFoodOrigin() {
        return foodOrigin;
    }

    public void setFoodOrigin(String foodOrigin) {
        this.foodOrigin = foodOrigin;
    }

    @Column(name = "food_pic")
    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }

    @Column(name = "food_efficacy")
    public String getFoodEfficacy() {
        return foodEfficacy;
    }

    public void setFoodEfficacy(String foodEfficacy) {
        this.foodEfficacy = foodEfficacy;
    }

    @Column(name = "food_make")
    public String getFoodMake() {
        return foodMake;
    }

    public void setFoodMake(String foodMake) {
        this.foodMake = foodMake;
    }

    @Column(name = "food_taste")
    public String getFoodTaste() {
        return foodTaste;
    }

    public void setFoodTaste(String foodTaste) {
        this.foodTaste = foodTaste;
    }

    @Column(name = "food_type")
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Column(name = "food_meal_type")
    public String getFoodMealType() {
        return foodMealType;
    }

    public void setFoodMealType(String foodMealType) {
        this.foodMealType = foodMealType;
    }

    @Column(name = "food_benefit")
    public String getFoodBenefit() {
        return foodBenefit;
    }

    public void setFoodBenefit(String foodBenefit) {
        this.foodBenefit = foodBenefit;
    }

    @Column(name = "food_harm")
    public String getFoodHarm() {
        return foodHarm;
    }

    public void setFoodHarm(String foodHarm) {
        this.foodHarm = foodHarm;
    }

    @Column(name = "food_staple")
    public Integer getFoodStaple() {
        return foodStaple;
    }

    public void setFoodStaple(Integer foodStaple) {
        this.foodStaple = foodStaple;
    }

    @Column(name = "food_cuisine")
    public String getFoodCuisine() {
        return foodCuisine;
    }

    public void setFoodCuisine(String foodCuisine) {
        this.foodCuisine = foodCuisine;
    }

    @Column(name = "food_counts")
    public Integer getFoodCounts() {
        return foodCounts;
    }

    public void setFoodCounts(Integer foodCounts) {
        this.foodCounts = foodCounts;
    }

    @Column(name = "food_tree_type")
    public String getFoodTreeType() {
        return foodTreeType;
    }

    public void setFoodTreeType(String foodTreeType) {
        this.foodTreeType = foodTreeType;
    }

    @Column(name = "food_style_cook")
    public String getFoodStyleCook() {
        return foodStyleCook;
    }

    
    public void setFoodStyleCook(String foodStyleCook) {
        this.foodStyleCook = foodStyleCook;
    }

    @Column(name = "food_allergen")
    public String getFoodAllergen() {
        return foodAllergen;
    }

    
    public void setFoodAllergen(String foodAllergen) {
        this.foodAllergen = foodAllergen;
    }

    @Column(name = "food_sort")
    public String getFoodSort() {
        return foodSort;
    }

    
    public void setFoodSort(String foodSort) {
        this.foodSort = foodSort;
    }

    @Column(name = "food_meat_vegetable")
    public String getFoodMeatVegetable() {
        return foodMeatVegetable;
    }

    
    public void setFoodMeatVegetable(String foodMeatVegetable) {
        this.foodMeatVegetable = foodMeatVegetable;
    }
    
}
