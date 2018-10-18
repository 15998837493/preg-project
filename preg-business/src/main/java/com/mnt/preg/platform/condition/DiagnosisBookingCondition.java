
package com.mnt.preg.platform.condition;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 复诊预约表检索条件
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.3 2017-12-28 dhs 初版
 */
public class DiagnosisBookingCondition implements Serializable {

    private static final long serialVersionUID = -7581425262936130322L;

    /** 主键id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String bookingId;

    /** 就诊时间（周xx午） */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String bookingVisitTime;

    /** 复诊建议体重 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String bookingSuggest;

    /** 其他复诊建议 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String bookingRemark;

    /** 预约日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC)
    private Date bookingDate;

    /** 接诊id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    public DiagnosisBookingCondition() {

    }

    public DiagnosisBookingCondition(String bookingId) {
        this.bookingId = bookingId;
    }

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

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}
