
package com.mnt.preg.customer.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
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
public class PregCourseBookingPojo extends MappedEntity {

    private static final long serialVersionUID = 1692587108762892001L;

    /** 主键 */
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 建档主键 */
    @QueryFieldAnnotation
    private String archivesId;

    /** 课程id */
    @QueryFieldAnnotation
    private String scheduleId;

    /** 预约时间 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date bookingDate;

    /** 课程时间 */
    @QueryFieldAnnotation
    private String courseTime;

    /** 课程内容 */
    @QueryFieldAnnotation
    private String courseContent;

    /** 反馈id */
    @QueryFieldAnnotation(aliasName = "feedback")
    private String feedId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

}
