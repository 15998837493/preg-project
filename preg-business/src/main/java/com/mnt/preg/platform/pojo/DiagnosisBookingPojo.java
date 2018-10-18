
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 复诊预约表
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.3 2017-12-28 dhs 初版
 */
public class DiagnosisBookingPojo implements Serializable {

    private static final long serialVersionUID = 138535334835660335L;

    /** 主键id */
    @QueryFieldAnnotation
    private String bookingId;

    /** 就诊时间（周xx午） */
    @QueryFieldAnnotation
    private String bookingVisitTime;

    /** 复诊建议体重 */
    @QueryFieldAnnotation
    private String bookingSuggest;

    /** 其他复诊建议 */
    @QueryFieldAnnotation
    private String bookingRemark;

    /** 预约日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date bookingDate;

    /** 接诊id */
    @QueryFieldAnnotation
    private String diagnosisId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingVisitTime() {
        return bookingVisitTime;
    }

    public void setBookingVisitTime(String bookingVisitTime) {
        this.bookingVisitTime = bookingVisitTime;
    }

    public String getBookingSuggest() {
        return bookingSuggest;
    }

    public void setBookingSuggest(String bookingSuggest) {
        this.bookingSuggest = bookingSuggest;
    }

    public String getBookingRemark() {
        return bookingRemark;
    }

    public void setBookingRemark(String bookingRemark) {
        this.bookingRemark = bookingRemark;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
