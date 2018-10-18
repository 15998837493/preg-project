/*
 * FoodBaseVo.java	 1.0   2015-1-21
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 食物基本信息
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-21 zy 初版
 */
public class FoodBasePojo implements Serializable {

    private static final long serialVersionUID = 1929953730270576486L;

    /** 主键 */
    @QueryFieldAnnotation
    private String foodId;

    /** 食物名称 */
    @QueryFieldAnnotation
    private String foodName;

    /** 图片 */
    @QueryFieldAnnotation
    private String foodPic;

    /** 食物重量 */
    @QueryFieldAnnotation(aliasName = "FoodExt")
    private Integer foodAmount;

    /** 食物级别 */
    @QueryFieldAnnotation(aliasName = "FoodExt")
    private String foodLevel;

    /** 能量_kcal */
    @QueryFieldAnnotation(aliasName = "FoodExt")
    private BigDecimal foodEnergy;

    /** 蛋白质g */
    @QueryFieldAnnotation(aliasName = "FoodExt")
    private BigDecimal foodProtid;

    /** 脂肪g */
    @QueryFieldAnnotation(aliasName = "FoodExt")
    private BigDecimal foodFat;

    /** 碳水化合物(g) */
    @QueryFieldAnnotation(aliasName = "FoodExt")
    private BigDecimal foodCbr;

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

    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }

    public Integer getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(Integer foodAmount) {
        this.foodAmount = foodAmount;
    }

    public String getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(String foodLevel) {
        this.foodLevel = foodLevel;
    }

    public BigDecimal getFoodEnergy() {
        return foodEnergy;
    }

    public void setFoodEnergy(BigDecimal foodEnergy) {
        this.foodEnergy = foodEnergy;
    }

    public BigDecimal getFoodProtid() {
        return foodProtid;
    }

    public void setFoodProtid(BigDecimal foodProtid) {
        this.foodProtid = foodProtid;
    }

    public BigDecimal getFoodFat() {
        return foodFat;
    }

    public void setFoodFat(BigDecimal foodFat) {
        this.foodFat = foodFat;
    }

    public BigDecimal getFoodCbr() {
        return foodCbr;
    }

    public void setFoodCbr(BigDecimal foodCbr) {
        this.foodCbr = foodCbr;
    }

}
