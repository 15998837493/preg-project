
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 干预方案--要点信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-2 zcq 初版
 */
public class PregPlanPointsPojo implements Serializable {

    private static final long serialVersionUID = 2151736719360249269L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 干预方案主键 */
    @QueryFieldAnnotation
    private String planId;

    /** 干预要点 */
    @QueryFieldAnnotation
    private String pointId;

    /** 要点类型 */
    @QueryFieldAnnotation
    private String pointType;

    /** 要点归类 */
    @QueryFieldAnnotation
    private String pointSubclass;

    /** 要点描述 */
    @QueryFieldAnnotation
    private String pointDesc;

    /** 要点举例 */
    @QueryFieldAnnotation
    private String pointExample;

    /** 内容顺序 */
    @QueryFieldAnnotation
    private Integer pointOrder;

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

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getPointSubclass() {
        return pointSubclass;
    }

    public void setPointSubclass(String pointSubclass) {
        this.pointSubclass = pointSubclass;
    }

    public String getPointDesc() {
        return pointDesc;
    }

    public void setPointDesc(String pointDesc) {
        this.pointDesc = pointDesc;
    }

    public String getPointExample() {
        return pointExample;
    }

    public void setPointExample(String pointExample) {
        this.pointExample = pointExample;
    }

    public Integer getPointOrder() {
        return pointOrder;
    }

    public void setPointOrder(Integer pointOrder) {
        this.pointOrder = pointOrder;
    }

}
