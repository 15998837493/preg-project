
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 干预方案一日饮食清单表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
@Entity
@Table(name = "user_plan_points")
public class PregPlanPoints extends MappedEntity {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 主键 */
    private String id;

    /** 干预方案主键 */
    private String planId;

    /** 干预要点 */
    private String pointId;

    /** 要点类型 */
    @UpdateAnnotation
    private String pointType;

    /** 要点归类 */
    @UpdateAnnotation
    private String pointSubclass;

    /** 要点描述 */
    @UpdateAnnotation
    private String pointDesc;

    /** 要点举例 */
    @UpdateAnnotation
    private String pointExample;

    /** 内容顺序 */
    @UpdateAnnotation
    private Integer pointOrder;

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

    @Column(name = "point_id")
    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    @Column(name = "point_type")
    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    @Column(name = "point_subclass")
    public String getPointSubclass() {
        return pointSubclass;
    }

    public void setPointSubclass(String pointSubclass) {
        this.pointSubclass = pointSubclass;
    }

    @Column(name = "point_desc")
    public String getPointDesc() {
        return pointDesc;
    }

    public void setPointDesc(String pointDesc) {
        this.pointDesc = pointDesc;
    }

    @Column(name = "point_example")
    public String getPointExample() {
        return pointExample;
    }

    public void setPointExample(String pointExample) {
        this.pointExample = pointExample;
    }

    @Column(name = "point_order")
    public Integer getPointOrder() {
        return pointOrder;
    }

    public void setPointOrder(Integer pointOrder) {
        this.pointOrder = pointOrder;
    }

}
