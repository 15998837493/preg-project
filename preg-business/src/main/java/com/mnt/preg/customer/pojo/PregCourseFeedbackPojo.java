
package com.mnt.preg.customer.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 客户信息Bo
 * 
 * @author 王鑫
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-09 王鑫 初版
 */
public class PregCourseFeedbackPojo extends MappedEntity {

    private static final long serialVersionUID = -4433373765028192634L;

    /** 主键 */
    @QueryFieldAnnotation
    private String feedId;

    /** 建档主键 */
    @QueryFieldAnnotation
    private String bookingId;

    /** 上课时间 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date courseDate;

    @QueryFieldAnnotation
    private String joinCourse;

    /** 餐前血糖 */
    @QueryFieldAnnotation
    private BigDecimal preprandialBooldGlucose;

    /** 餐后血糖 */
    @QueryFieldAnnotation
    private BigDecimal postprandialBooldGlucose;

    /** 心率 */
    @QueryFieldAnnotation
    private Integer heatRate;

    /** 粗杂粮 */
    @QueryFieldAnnotation
    private String grainFood;

    /** 运动反馈 */
    @QueryFieldAnnotation
    private String sprotFeedback;

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public BigDecimal getPreprandialBooldGlucose() {
        return preprandialBooldGlucose;
    }

    public void setPreprandialBooldGlucose(BigDecimal preprandialBooldGlucose) {
        this.preprandialBooldGlucose = preprandialBooldGlucose;
    }

    public BigDecimal getPostprandialBooldGlucose() {
        return postprandialBooldGlucose;
    }

    public void setPostprandialBooldGlucose(BigDecimal postprandialBooldGlucose) {
        this.postprandialBooldGlucose = postprandialBooldGlucose;
    }

    public Integer getHeatRate() {
        return heatRate;
    }

    public void setHeatRate(Integer heatRate) {
        this.heatRate = heatRate;
    }

    public String getGrainFood() {
        return grainFood;
    }

    public void setGrainFood(String grainFood) {
        this.grainFood = grainFood;
    }

    public String getSprotFeedback() {
        return sprotFeedback;
    }

    public void setSprotFeedback(String sprotFeedback) {
        this.sprotFeedback = sprotFeedback;
    }

    public String getJoinCourse() {
        return joinCourse;
    }

    public void setJoinCourse(String joinCourse) {
        this.joinCourse = joinCourse;
    }

}
