
package com.mnt.preg.customer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 客户信息Bo
 * 
 * @author 王鑫
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-09 王鑫 初版
 */
@Entity
@Table(name = "user_course_feedback")
public class PregCourseFeedback extends MappedEntity {

    private static final long serialVersionUID = -4433373765028192634L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String feedId;

    /** 建档主键 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String bookingId;

    /** 建档主键 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String joinCourse;

    /** 上课时间 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Date courseDate;

    /** 餐前血糖 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal preprandialBooldGlucose;

    /** 餐后血糖 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal postprandialBooldGlucose;

    /** 心率 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer heatRate;

    /** 粗杂粮 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String grainFood;

    /** 运动反馈 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String sprotFeedback;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "feed_id")
    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    @Column(name = "booking_id")
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "course_date")
    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    @Column(name = "preprandial_boold_glucose")
    public BigDecimal getPreprandialBooldGlucose() {
        return preprandialBooldGlucose;
    }

    public void setPreprandialBooldGlucose(BigDecimal preprandialBooldGlucose) {
        this.preprandialBooldGlucose = preprandialBooldGlucose;
    }

    @Column(name = "postprandial_boold_glucose")
    public BigDecimal getPostprandialBooldGlucose() {
        return postprandialBooldGlucose;
    }

    public void setPostprandialBooldGlucose(BigDecimal postprandialBooldGlucose) {
        this.postprandialBooldGlucose = postprandialBooldGlucose;
    }

    @Column(name = "heat_rate")
    public Integer getHeatRate() {
        return heatRate;
    }

    public void setHeatRate(Integer heatRate) {
        this.heatRate = heatRate;
    }

    @Column(name = "grain_food")
    public String getGrainFood() {
        return grainFood;
    }

    public void setGrainFood(String grainFood) {
        this.grainFood = grainFood;
    }

    @Column(name = "sprot_feedback")
    public String getSprotFeedback() {
        return sprotFeedback;
    }

    public void setSprotFeedback(String sprotFeedback) {
        this.sprotFeedback = sprotFeedback;
    }

    @Column(name = "join_course")
    public String getJoinCourse() {
        return joinCourse;
    }

    public void setJoinCourse(String joinCourse) {
        this.joinCourse = joinCourse;
    }

}
