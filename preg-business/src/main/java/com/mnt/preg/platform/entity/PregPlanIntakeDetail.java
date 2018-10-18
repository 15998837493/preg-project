
package com.mnt.preg.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 干预方案摄入量明细表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
@Entity
@Table(name = "user_plan_intake_detail")
public class PregPlanIntakeDetail extends MappedEntity {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 主键 */
    private String id;

    /** 方案主键 */
    private String planId;

    /** 餐次 */
    @UpdateAnnotation
    private String intakeMealtype;

    /** 餐次 */
    @UpdateAnnotation
    private String intakeMealtypeName;

    /** 摄入类型 */
    @UpdateAnnotation
    private String intakeType;

    /** 摄入类型 */
    @UpdateAnnotation
    private String intakeTypeName;

    /** 摄入份数 */
    @UpdateAnnotation
    private BigDecimal intakeCount;

    /** 换算摄入量 */
    @UpdateAnnotation
    private BigDecimal intakeAmount;

    /** 其它 */
    @UpdateAnnotation
    private String intakeDesc;

    /** 食品类型 */
    @UpdateAnnotation
    private String intakeFoodType;

    /** 单位 */
    @UpdateAnnotation
    private String intakeUnit;

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

    @Column(name = "plan_id")
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Column(name = "intake_mealtype")
    public String getIntakeMealtype() {
        return intakeMealtype;
    }

    public void setIntakeMealtype(String intakeMealtype) {
        this.intakeMealtype = intakeMealtype;
    }

    @Column(name = "intake_mealtype_name")
    public String getIntakeMealtypeName() {
        return intakeMealtypeName;
    }

    public void setIntakeMealtypeName(String intakeMealtypeName) {
        this.intakeMealtypeName = intakeMealtypeName;
    }

    @Column(name = "intake_type")
    public String getIntakeType() {
        return intakeType;
    }

    public void setIntakeType(String intakeType) {
        this.intakeType = intakeType;
    }

    @Column(name = "intake_type_name")
    public String getIntakeTypeName() {
        return intakeTypeName;
    }

    public void setIntakeTypeName(String intakeTypeName) {
        this.intakeTypeName = intakeTypeName;
    }

    @Column(name = "intake_count")
    public BigDecimal getIntakeCount() {
        return intakeCount;
    }

    public void setIntakeCount(BigDecimal intakeCount) {
        this.intakeCount = intakeCount;
    }

    @Column(name = "intake_amount")
    public BigDecimal getIntakeAmount() {
        return intakeAmount;
    }

    public void setIntakeAmount(BigDecimal intakeAmount) {
        this.intakeAmount = intakeAmount;
    }

    @Column(name = "intake_desc")
    public String getIntakeDesc() {
        return intakeDesc;
    }

    public void setIntakeDesc(String intakeDesc) {
        this.intakeDesc = intakeDesc;
    }

    @Column(name = "intake_food_type")
    public String getIntakeFoodType() {
        return intakeFoodType;
    }

    public void setIntakeFoodType(String intakeFoodType) {
        this.intakeFoodType = intakeFoodType;
    }

    @Column(name = "intake_unit")
    public String getIntakeUnit() {
        return intakeUnit;
    }

    public void setIntakeUnit(String intakeUnit) {
        this.intakeUnit = intakeUnit;
    }

}
