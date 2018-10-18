
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 干预方案--课程
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-2 zcq 初版
 */
public class PregPlanCoursePojo implements Serializable {

    private static final long serialVersionUID = 2151736719360249269L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 干预方案主键 */
    @QueryFieldAnnotation
    private String planId;

    /** 课程类型 */
    @QueryFieldAnnotation
    private String pregType;

    /** 课程主键 */
    @QueryFieldAnnotation
    private String pregId;

    /** 课程类别名称 */
    @QueryFieldAnnotation
    private String pregName;

    /** 课程码 */
    @QueryFieldAnnotation
    private String pregDeCode;

    /** 课程名称 */
    @QueryFieldAnnotation
    private String pregDeName;

    /** 顺序 */
    @QueryFieldAnnotation
    private Integer pregDeOrder;

    /** 课程介绍 */
    @QueryFieldAnnotation
    private String courseDesc;

    /** 诊断项目 */
    @QueryFieldAnnotation
    private String diseaseName;

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

    public String getPregType() {
        return pregType;
    }

    public void setPregType(String pregType) {
        this.pregType = pregType;
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId;
    }

    public String getPregName() {
        return pregName;
    }

    public void setPregName(String pregName) {
        this.pregName = pregName;
    }

    public String getPregDeCode() {
        return pregDeCode;
    }

    public void setPregDeCode(String pregDeCode) {
        this.pregDeCode = pregDeCode;
    }

    public String getPregDeName() {
        return pregDeName;
    }

    public void setPregDeName(String pregDeName) {
        this.pregDeName = pregDeName;
    }

    public Integer getPregDeOrder() {
        return pregDeOrder;
    }

    public void setPregDeOrder(Integer pregDeOrder) {
        this.pregDeOrder = pregDeOrder;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

}
