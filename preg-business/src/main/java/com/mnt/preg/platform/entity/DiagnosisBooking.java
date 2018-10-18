
package com.mnt.preg.platform.entity;

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

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 复诊预约
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.3 2017-12-18 dhs 初版
 */
@Entity
@Table(name = "user_diagnosis_booking")
public class DiagnosisBooking extends MappedEntity {

    private static final long serialVersionUID = 2549853846872563425L;

    /** 主键id */
    private String bookingId;

    /** 就诊时间（周xx午） */
    @UpdateAnnotation
    private String bookingVisitTime;

    /** 复诊建议体重 */
    @UpdateAnnotation
    private String bookingSuggest;

    /** 其他复诊建议 */
    @UpdateAnnotation
    private String bookingRemark;

    /** 预约日期 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bookingDate;

    /** 接诊id */
    @UpdateAnnotation
    private String diagnosisId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "booking_id")
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    @Column(name = "booking_visit_time")
    public String getBookingVisitTime() {
        return bookingVisitTime;
    }

    public void setBookingVisitTime(String bookingVisitTime) {
        this.bookingVisitTime = bookingVisitTime;
    }

    @Column(name = "booking_suggest")
    public String getBookingSuggest() {
        return bookingSuggest;
    }

    public void setBookingSuggest(String bookingSuggest) {
        this.bookingSuggest = bookingSuggest;
    }

    @Column(name = "booking_remark")
    public String getBookingRemark() {
        return bookingRemark;
    }

    public void setBookingRemark(String bookingRemark) {
        this.bookingRemark = bookingRemark;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_date")
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

}
