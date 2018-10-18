
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
 * 干预方案课程
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-2 zcq 初版
 */
@Entity
@Table(name = "user_plan_course")
public class PregPlanCourse extends MappedEntity {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 主键 */
    private String id;

    /** 干预方案主键 */
    private String planId;

    /** 课程类型 */
    private String pregType;

    /** 课程主键 */
    private String pregId;

    /** 课程类别名称 */
    @UpdateAnnotation
    private String pregName;

    /** 课程码 */
    @UpdateAnnotation
    private String pregDeCode;

    /** 课程名称 */
    @UpdateAnnotation
    private String pregDeName;

    /** 顺序 */
    @UpdateAnnotation
    private Integer pregDeOrder;

    /** 课程介绍 */
    private String courseDesc;

    /** 诊断项目 */
    private String diseaseName;

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

    @Column(name = "preg_type")
    public String getPregType() {
        return pregType;
    }

    public void setPregType(String pregType) {
        this.pregType = pregType;
    }

    @Column(name = "preg_id")
    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId;
    }

    @Column(name = "preg_name")
    public String getPregName() {
        return pregName;
    }

    public void setPregName(String pregName) {
        this.pregName = pregName;
    }

    @Column(name = "preg_de_code")
    public String getPregDeCode() {
        return pregDeCode;
    }

    public void setPregDeCode(String pregDeCode) {
        this.pregDeCode = pregDeCode;
    }

    @Column(name = "preg_de_name")
    public String getPregDeName() {
        return pregDeName;
    }

    public void setPregDeName(String pregDeName) {
        this.pregDeName = pregDeName;
    }

    @Column(name = "preg_de_order")
    public Integer getPregDeOrder() {
        return pregDeOrder;
    }

    public void setPregDeOrder(Integer pregDeOrder) {
        this.pregDeOrder = pregDeOrder;
    }

    @Column(name = "course_desc")
    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    @Column(name = "disease_name")
    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

}
