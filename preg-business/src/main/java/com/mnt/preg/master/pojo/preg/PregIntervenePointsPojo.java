
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 健康要点信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-29 zcq 初版
 */
public class PregIntervenePointsPojo implements Serializable {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    @QueryFieldAnnotation
    private String pointId;

    /** 大类 */
    @QueryFieldAnnotation
    private String pointType;

    /** 小类 */
    @QueryFieldAnnotation
    private String pointSubclass;

    /** 要点描述 */
    @QueryFieldAnnotation
    private String pointDesc;

    /** 要点方法举例 */
    @QueryFieldAnnotation
    private String pointExample;

    /** 要点排序 */
    @QueryFieldAnnotation
    private Integer pointOrder;

    /** 孕期阶段 */
    @QueryFieldAnnotation
    private String pregStage;

    /** 干预疾病id */
    private String interveneDiseaseIds;

    /** 干预疾病id */
    private String interveneDiseaseNames;

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

    public String getInterveneDiseaseIds() {
        return interveneDiseaseIds;
    }

    public void setInterveneDiseaseIds(String interveneDiseaseIds) {
        this.interveneDiseaseIds = interveneDiseaseIds;
    }

    public String getPregStage() {
        return pregStage;
    }

    public void setPregStage(String pregStage) {
        this.pregStage = pregStage;
    }

    public String getInterveneDiseaseNames() {
        return interveneDiseaseNames;
    }

    public void setInterveneDiseaseNames(String interveneDiseaseNames) {
        this.interveneDiseaseNames = interveneDiseaseNames;
    }

}
