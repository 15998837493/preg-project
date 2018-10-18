
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 代量食谱模板明细表
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-3 xdc 初版
 */
public class PregDietTemplateDetailPojo implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 模版编码 */
    @QueryFieldAnnotation
    private String dietId;

    /** 天数 */
    @QueryFieldAnnotation
    private String foodDay;

    /** 餐次 */
    @QueryFieldAnnotation
    private String foodMeal;

    /** 食物编码 */
    @QueryFieldAnnotation
    private String foodId;

    /** 食物名称 */
    @QueryFieldAnnotation
    private String foodName;

    /** 食材 id */
    @QueryFieldAnnotation
    private String fmId;

    /** 食材名称 */
    @QueryFieldAnnotation
    private String foodMaterialName;

    /** 餐次名称 */
    private String foodMealName;

    /** 食物成分用量 */
    @QueryFieldAnnotation
    private String foodMaterialAmount;

    /** 能量范围 */
    @QueryFieldAnnotation
    private String amountLevel;

    /** 食物成分名称，用量 */
    private List<PregDietTemplateDetailPojo> materialList;

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

    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    public String getFoodMaterialName() {
        return foodMaterialName;
    }

    public void setFoodMaterialName(String foodMaterialName) {
        this.foodMaterialName = foodMaterialName;
    }

    public String getFoodMealName() {
        return foodMealName;
    }

    public void setFoodMealName(String foodMealName) {
        this.foodMealName = foodMealName;
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

    public List<PregDietTemplateDetailPojo> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<PregDietTemplateDetailPojo> materialList) {
        this.materialList = materialList;
    }
}
