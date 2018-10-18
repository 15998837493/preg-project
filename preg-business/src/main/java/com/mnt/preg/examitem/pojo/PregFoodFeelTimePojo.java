
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 用餐时间和感受
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public class PregFoodFeelTimePojo implements Serializable {

    private static final long serialVersionUID = 2308332578985459229L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 评估编号 */
    @QueryFieldAnnotation
    private String foodRecordId;

    /** 用餐餐次 */
    @QueryFieldAnnotation
    private String mealsId;

    /** 用餐感受 */
    @QueryFieldAnnotation
    private String mealsFeel;

    /** 用餐时间 */
    @QueryFieldAnnotation
    private String mealsTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    public String getMealsId() {
        return mealsId;
    }

    public void setMealsId(String mealsId) {
        this.mealsId = mealsId;
    }

    public String getMealsFeel() {
        return mealsFeel;
    }

    public void setMealsFeel(String mealsFeel) {
        this.mealsFeel = mealsFeel;
    }

    public String getMealsTime() {
        return mealsTime;
    }

    public void setMealsTime(String mealsTime) {
        this.mealsTime = mealsTime;
    }

}
