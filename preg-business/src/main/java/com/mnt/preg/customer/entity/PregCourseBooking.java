
package com.mnt.preg.customer.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
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
@Table(name = "user_course_booking")
public class PregCourseBooking extends MappedEntity {

    private static final long serialVersionUID = 1692587108762892001L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 建档主键 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String archivesId;

    /** 课程主键 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String scheduleId;

    /** 预约时间 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private Date bookingDate;

    /** 课程时间 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String courseTime;

    /** 课程内容 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String courseContent;

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

    @Column(name = "archives_id")
    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_date")
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Column(name = "course_time")
    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    @Column(name = "course_content")
    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    @Column(name = "schedule_id")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}
