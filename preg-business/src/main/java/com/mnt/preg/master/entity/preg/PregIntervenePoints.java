
package com.mnt.preg.master.entity.preg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 健康要点信息表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-29 zcq 初版
 */
@Entity
@Table(name = "mas_intervene_points")
public class PregIntervenePoints extends MappedEntity {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    private String pointId;

    /** 大类 */
    @UpdateAnnotation
    private String pointType;

    /** 小类 */
    @UpdateAnnotation
    private String pointSubclass;

    /** 要点描述 */
    @UpdateAnnotation
    private String pointDesc;

    /** 方法举例 */
    @UpdateAnnotation
    private String pointExample;

    /** 异常返回类型 */
    @UpdateAnnotation
    private Integer pointOrder;

    /** 孕期 */
    @UpdateAnnotation
    private String pregStage;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
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

    @Column(name = "preg_stage")
    public String getPregStage() {
        return pregStage;
    }

    public void setPregStage(String pregStage) {
        this.pregStage = pregStage;
    }

}
