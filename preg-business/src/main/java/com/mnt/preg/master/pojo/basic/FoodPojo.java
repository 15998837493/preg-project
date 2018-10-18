/*
 * FoodVo.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.basic;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 食物基础信息
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
public class FoodPojo implements Serializable {

    private static final long serialVersionUID = -7087946314398055495L;

    /** 主键 */
    @QueryFieldAnnotation
    private String foodId;

    /** 食物名称 */
    @QueryFieldAnnotation
    private String foodName;

    /** 食品别名,号分割 */
    @QueryFieldAnnotation
    private String foodAlias;

    /** 条码 */
    @QueryFieldAnnotation
    private String foodBarcode;

    /** 资料来源 */
    @QueryFieldAnnotation
    private String foodOrigin;

    /** 图片 */
    @QueryFieldAnnotation
    private String foodPic;

    /** 功效 */
    @QueryFieldAnnotation
    private String foodEfficacy;

    /** 菜肴做法 */
    @QueryFieldAnnotation
    private String foodMake;

    /** 口味 */
    @QueryFieldAnnotation
    private String foodTaste;

    /** 所属食谱类型 */
    @QueryFieldAnnotation
    private String foodType;

    /** 单位 */
    @QueryFieldAnnotation
    private String foodUnit;

    /** 烹饪方式 */
    @QueryFieldAnnotation
    private String foodCuisine;

    /** 适合餐类-数据','分隔 */
    @QueryFieldAnnotation
    private String foodMealType;

    /** 适合饮食的标签-数据','分隔 */
    @QueryFieldAnnotation
    private String foodBenefit;

    /** 不好的饮食标签-数据','分隔 */
    @QueryFieldAnnotation
    private String foodHarm;

    /** 是否主食 */
    @QueryFieldAnnotation
    private Integer foodStaple;

    /** 食物元素 */
    private FoodExtPojo foodExt;

    /** 食物的食材组成 */
    private List<FoodMaterialListInfoPojo> fmlVoList;

    /** 食谱类型主键 */
    @QueryFieldAnnotation
    private String foodTreeType;

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

    public String getFoodBenefit() {
        return foodBenefit;
    }

    public void setFoodBenefit(String foodBenefit) {
        this.foodBenefit = foodBenefit;
    }

    public String getFoodHarm() {
        return foodHarm;
    }

    public void setFoodHarm(String foodHarm) {
        this.foodHarm = foodHarm;
    }

    public Integer getFoodStaple() {
        return foodStaple;
    }

    public void setFoodStaple(Integer foodStaple) {
        this.foodStaple = foodStaple;
    }

    public FoodExtPojo getFoodExt() {
        return foodExt;
    }

    public void setFoodExt(FoodExtPojo foodExt) {
        this.foodExt = foodExt;
    }

    public List<FoodMaterialListInfoPojo> getFmlVoList() {
        return fmlVoList;
    }

    public void setFmlVoList(List<FoodMaterialListInfoPojo> fmlVoList) {
        this.fmlVoList = fmlVoList;
    }

    public String getFoodTreeType() {
        return foodTreeType;
    }

    public void setFoodTreeType(String foodTreeType) {
        this.foodTreeType = foodTreeType;
    }

}
