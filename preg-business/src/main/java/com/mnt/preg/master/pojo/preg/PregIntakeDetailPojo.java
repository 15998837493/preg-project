
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 摄入量明细表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
public class PregIntakeDetailPojo implements Serializable {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 摄入量模版主键 */
    @QueryFieldAnnotation
    private String intakeId;

    /** 餐次 */
    @QueryFieldAnnotation
    private String intakeMealtype;

    /** 摄入类型 */
    @QueryFieldAnnotation
    private String intakeType;

    /** 摄入份数 */
    @QueryFieldAnnotation
    private BigDecimal intakeCount;

    /** 餐次名称 */
    @QueryFieldAnnotation(aliasName = "codeVo", fieldName = "code_name")
    private String intakeMealtypeName;

    /** 膳食类型名称 */
    @QueryFieldAnnotation
    private String intakeTypeName;

    /** 描述 */
    @QueryFieldAnnotation
    private String intakeDesc;

    /** 膳食食物类型intake、product */
    @QueryFieldAnnotation
    private String intakeFoodType;

    public String getIntakeFoodType() {
        return intakeFoodType;
    }

    public void setIntakeFoodType(String intakeFoodType) {
        this.intakeFoodType = intakeFoodType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeMealtype() {
        return intakeMealtype;
    }

    public void setIntakeMealtype(String intakeMealtype) {
        this.intakeMealtype = intakeMealtype;
    }

    public String getIntakeType() {
        return intakeType;
    }

    public void setIntakeType(String intakeType) {
        this.intakeType = intakeType;
    }

    public BigDecimal getIntakeCount() {
        return intakeCount;
    }

    public void setIntakeCount(BigDecimal intakeCount) {
        this.intakeCount = intakeCount;
    }

    public String getIntakeMealtypeName() {
        return intakeMealtypeName;
    }

    public void setIntakeMealtypeName(String intakeMealtypeName) {
        this.intakeMealtypeName = intakeMealtypeName;
    }

    public String getIntakeTypeName() {
        return intakeTypeName;
    }

    public void setIntakeTypeName(String intakeTypeName) {
        this.intakeTypeName = intakeTypeName;
    }

    public String getIntakeDesc() {
        return intakeDesc;
    }

    public void setIntakeDesc(String intakeDesc) {
        this.intakeDesc = intakeDesc;
    }
}
