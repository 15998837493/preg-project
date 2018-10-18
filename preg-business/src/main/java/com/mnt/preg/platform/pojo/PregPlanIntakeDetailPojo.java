
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 干预方案摄入量模版信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-2 zcq 初版
 */
public class PregPlanIntakeDetailPojo implements Serializable {

    private static final long serialVersionUID = -6446844509322954240L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 方案主键 */
    @QueryFieldAnnotation
    private String planId;

    /** 餐次 */
    @QueryFieldAnnotation
    private String intakeMealtype;

    /** 餐次 */
    @QueryFieldAnnotation
    private String intakeMealtypeName;

    /** 摄入类型 */
    @QueryFieldAnnotation
    private String intakeType;

    /** 摄入类型 */
    @QueryFieldAnnotation
    private String intakeTypeName;

    /** 摄入份数 */
    @QueryFieldAnnotation
    private BigDecimal intakeCount;

    /** 换算摄入量 */
    @QueryFieldAnnotation
    private BigDecimal intakeAmount;

    /** 其它 */
    @QueryFieldAnnotation
    private String intakeDesc;

    /** 食品类型 */
    @QueryFieldAnnotation
    private String intakeFoodType;

    /** 单位 */
    @QueryFieldAnnotation
    private String intakeUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getIntakeMealtype() {
        return intakeMealtype;
    }

    public void setIntakeMealtype(String intakeMealtype) {
        this.intakeMealtype = intakeMealtype;
    }

    public String getIntakeMealtypeName() {
        return intakeMealtypeName;
    }

    public void setIntakeMealtypeName(String intakeMealtypeName) {
        this.intakeMealtypeName = intakeMealtypeName;
    }

    public String getIntakeType() {
        return intakeType;
    }

    public void setIntakeType(String intakeType) {
        this.intakeType = intakeType;
    }

    public String getIntakeTypeName() {
        return intakeTypeName;
    }

    public void setIntakeTypeName(String intakeTypeName) {
        this.intakeTypeName = intakeTypeName;
    }

    public BigDecimal getIntakeCount() {
        return intakeCount;
    }

    public void setIntakeCount(BigDecimal intakeCount) {
        this.intakeCount = intakeCount;
    }

    public BigDecimal getIntakeAmount() {
        return intakeAmount;
    }

    public void setIntakeAmount(BigDecimal intakeAmount) {
        this.intakeAmount = intakeAmount;
    }

    public String getIntakeDesc() {
        return intakeDesc;
    }

    public void setIntakeDesc(String intakeDesc) {
        this.intakeDesc = intakeDesc;
    }

    public String getIntakeFoodType() {
        return intakeFoodType;
    }

    public void setIntakeFoodType(String intakeFoodType) {
        this.intakeFoodType = intakeFoodType;
    }

    public String getIntakeUnit() {
        return intakeUnit;
    }

    public void setIntakeUnit(String intakeUnit) {
        this.intakeUnit = intakeUnit;
    }

}
