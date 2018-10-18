
package com.mnt.preg.examitem.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 膳食评估饮食频率
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-15 zcq 初版
 */
@Entity
@Table(name = "cus_food_frequency")
public class PregFoodFrequency extends MappedEntity {

    private static final long serialVersionUID = 3072445668040737697L;

    /** 主键 */
    private String id;

    /** 评估编号 */
    private String foodRecordId;

    /** 项目 */
    private String foodType;

    /** 周期 */
    private String foodCycle;

    /** 平均次数 */
    private Integer averageNumber;

    /** 平均摄入量类型 */
    private String averageIntakeType;

    /** 平均摄入量 */
    private BigDecimal averageIntake;

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

    @Column(name = "food_type")
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Column(name = "food_cycle")
    public String getFoodCycle() {
        return foodCycle;
    }

    public void setFoodCycle(String foodCycle) {
        this.foodCycle = foodCycle;
    }

    @Column(name = "average_number")
    public Integer getAverageNumber() {
        return averageNumber;
    }

    public void setAverageNumber(Integer averageNumber) {
        this.averageNumber = averageNumber;
    }

    @Column(name = "average_intake_type")
    public String getAverageIntakeType() {
        return averageIntakeType;
    }

    public void setAverageIntakeType(String averageIntakeType) {
        this.averageIntakeType = averageIntakeType;
    }

    @Column(name = "average_intake")
    public BigDecimal getAverageIntake() {
        return averageIntake;
    }

    public void setAverageIntake(BigDecimal averageIntake) {
        this.averageIntake = averageIntake;
    }

}
