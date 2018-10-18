
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 饮食频率
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-15 zcq 初版
 */
public class PregFoodFrequencyPojo implements Serializable {

    private static final long serialVersionUID = -205607526597567742L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 评估编号 */
    @QueryFieldAnnotation
    private String foodRecordId;

    /** 项目 */
    @QueryFieldAnnotation
    private String foodType;

    /** 周期 */
    @QueryFieldAnnotation
    private String foodCycle;

    /** 平均次数 */
    @QueryFieldAnnotation
    private Integer averageNumber;

    /** 平均摄入量类型 */
    @QueryFieldAnnotation
    private String averageIntakeType;

    /** 平均摄入量 */
    @QueryFieldAnnotation
    private BigDecimal averageIntake;

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

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodCycle() {
        return foodCycle;
    }

    public void setFoodCycle(String foodCycle) {
        this.foodCycle = foodCycle;
    }

    public Integer getAverageNumber() {
        return averageNumber;
    }

    public void setAverageNumber(Integer averageNumber) {
        this.averageNumber = averageNumber;
    }

    public String getAverageIntakeType() {
        return averageIntakeType;
    }

    public void setAverageIntakeType(String averageIntakeType) {
        this.averageIntakeType = averageIntakeType;
    }

    public BigDecimal getAverageIntake() {
        return averageIntake;
    }

    public void setAverageIntake(BigDecimal averageIntake) {
        this.averageIntake = averageIntake;
    }

}
