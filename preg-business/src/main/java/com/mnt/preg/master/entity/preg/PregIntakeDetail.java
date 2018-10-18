
package com.mnt.preg.master.entity.preg;

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
 * 摄入量明细表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
@Entity
@Table(name = "mas_intake_detail")
public class PregIntakeDetail extends MappedEntity {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 主键 */
    private String id;

    /** 摄入量模版主键 */
    @UpdateAnnotation
    private String intakeId;

    /** 餐次 */
    @UpdateAnnotation
    private String intakeMealtype;

    /** 摄入类型 */
    @UpdateAnnotation
    private String intakeType;

    /** 摄入份数 */
    @UpdateAnnotation
    private BigDecimal intakeCount;

    /** 描述 */
    @UpdateAnnotation
    private String intakeDesc;

    /** 膳食食物类型intake、product */
    @UpdateAnnotation
    private String intakeFoodType;

    /** 摄入类型/商品名称 */
    @UpdateAnnotation
    private String intakeTypeName;

    @Column(name = "intake_type_name")
    public String getIntakeTypeName() {
        return intakeTypeName;
    }

    public void setIntakeTypeName(String intakeTypeName) {
        this.intakeTypeName = intakeTypeName;
    }

    @Column(name = "intake_food_type")
    public String getIntakeFoodType() {
        return intakeFoodType;
    }

    public void setIntakeFoodType(String intakeFoodType) {
        this.intakeFoodType = intakeFoodType;
    }

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

    @Column(name = "intake_id")
    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    @Column(name = "intake_mealtype")
    public String getIntakeMealtype() {
        return intakeMealtype;
    }

    public void setIntakeMealtype(String intakeMealtype) {
        this.intakeMealtype = intakeMealtype;
    }

    @Column(name = "intake_count")
    public BigDecimal getIntakeCount() {
        return intakeCount;
    }

    public void setIntakeCount(BigDecimal intakeCount) {
        this.intakeCount = intakeCount;
    }

    @Column(name = "intake_type")
    public String getIntakeType() {
        return intakeType;
    }

    public void setIntakeType(String intakeType) {
        this.intakeType = intakeType;
    }

    @Column(name = "intake_desc")
    public String getIntakeDesc() {
        return intakeDesc;
    }

    public void setIntakeDesc(String intakeDesc) {
        this.intakeDesc = intakeDesc;
    }

}
