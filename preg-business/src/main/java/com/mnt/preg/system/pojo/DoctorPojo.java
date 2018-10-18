
package com.mnt.preg.system.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;

/**
 * 医师出诊排班
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-11-2 gss 初版
 */
public class DoctorPojo implements Serializable {

    private static final long serialVersionUID = 7362541246385115147L;

    /** 主键 */
    @QueryFieldAnnotation
    private String scheduleId;

    /** 用户ID */
    @QueryFieldAnnotation
    private String userId;

    /** 日期 （yyyy-MM-dd） */
    private String data;

    /** 周 */
    @QueryFieldAnnotation
    private String scheduleWeek;

    /** 可预约人数 */
    @QueryFieldAnnotation
    private Integer scheduleMaxPerson;

    /** 已预约人数 */
    private Integer scheduleRealPerson;

    /** 复诊预约 */
    private List<DiagnosisBookingPojo> bookingList;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScheduleWeek() {
        return scheduleWeek;
    }

    public void setScheduleWeek(String scheduleWeek) {
        this.scheduleWeek = scheduleWeek;
    }

    public Integer getScheduleMaxPerson() {
        return scheduleMaxPerson;
    }

    public void setScheduleMaxPerson(Integer scheduleMaxPerson) {
        this.scheduleMaxPerson = scheduleMaxPerson;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getScheduleRealPerson() {
        return scheduleRealPerson;
    }

    public void setScheduleRealPerson(Integer scheduleRealPerson) {
        this.scheduleRealPerson = scheduleRealPerson;
    }

    public List<DiagnosisBookingPojo> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<DiagnosisBookingPojo> bookingList) {
        this.bookingList = bookingList;
    }

}
