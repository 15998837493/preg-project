
package com.mnt.preg.examitem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 膳食评估三餐时间和感受
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Entity
@Table(name = "cus_food_feel_time")
public class PregFoodFeelTime extends MappedEntity {

    private static final long serialVersionUID = 3072445668040737697L;

    /** 主键 */
    private String id;

    /** 评估编号 */
    private String foodRecordId;

    /** 用餐餐次 */
    private String mealsId;

    /** 用餐感受 */
    private String mealsFeel;

    /** 用餐时间 */
    private String mealsTime;

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

    @Column(name = "food_record_id")
    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    @Column(name = "meals_id")
    public String getMealsId() {
        return mealsId;
    }

    public void setMealsId(String mealsId) {
        this.mealsId = mealsId;
    }

    @Column(name = "meals_feel")
    public String getMealsFeel() {
        return mealsFeel;
    }

    public void setMealsFeel(String mealsFeel) {
        this.mealsFeel = mealsFeel;
    }

    @Column(name = "meals_time")
    public String getMealsTime() {
        return mealsTime;
    }

    public void setMealsTime(String mealsTime) {
        this.mealsTime = mealsTime;
    }

}
