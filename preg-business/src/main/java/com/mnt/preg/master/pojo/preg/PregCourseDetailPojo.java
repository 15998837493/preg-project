
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 孕期课程明细
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */
public class PregCourseDetailPojo implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 主表id */
    @QueryFieldAnnotation
    private String pregId;

    /** 类别名称 */
    @QueryFieldAnnotation
    private String pregDeName;

    /** 编码 */
    @QueryFieldAnnotation
    private String pregDeCode;

    /** 排序 */
    @QueryFieldAnnotation
    private Integer pregDeOrder;

    /** 课程介绍 */
    private String courseDesc;

    /** 诊断项目 */
    private String diseaseName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId;
    }

    public String getPregDeName() {
        return pregDeName;
    }

    public void setPregDeName(String pregDeName) {
        this.pregDeName = pregDeName;
    }

    public String getPregDeCode() {
        return pregDeCode;
    }

    public void setPregDeCode(String pregDeCode) {
        this.pregDeCode = pregDeCode;
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
