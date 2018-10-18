
package com.mnt.preg.examitem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 膳食评估饮食明细
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Entity
@Table(name = "cus_food_details")
public class PregFoodDetails extends MappedEntity {

    private static final long serialVersionUID = 3072445668040737697L;

    /** 主键 */
    private String id;

    /** 评估编号 */
    private String foodRecordId;

    /** 用餐餐次 */
    private String mealsId;

    /** 食物主键 */
    private String foodId;

    /** 用餐感受 */
    private Integer foodAmount;

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

    @Column(name = "food_id")
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Column(name = "food_amount")
    public Integer getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(Integer foodAmount) {
        this.foodAmount = foodAmount;
    }

}
