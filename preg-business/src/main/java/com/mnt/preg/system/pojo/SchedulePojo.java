
package com.mnt.preg.system.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 一周课程配置
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-11-2 gss 初版
 */
public class SchedulePojo implements Serializable {

    private static final long serialVersionUID = 4549548202006811594L;

    /** 主键 */
    @QueryFieldAnnotation
    private String scheduleId;

    /** 周 */
    @QueryFieldAnnotation
    private String scheduleWeek;

    /** 上/下午 */
    @QueryFieldAnnotation
    private String scheduleNoon;

    /** 课程允许最大人数 */
    @QueryFieldAnnotation
    private Integer scheduleMaxPerson;

    /** 课程内容 */
    @QueryFieldAnnotation
    private String scheduleContent;

    /** 参加该课程的人数--第一周 */
    private Object coursePresonFirst;

    /** 参加该课程的人数--第二周 */
    private Object coursePresonSecond;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleWeek() {
        return scheduleWeek;
    }

    public void setScheduleWeek(String scheduleWeek) {
        this.scheduleWeek = scheduleWeek;
    }

    public String getScheduleNoon() {
        return scheduleNoon;
    }

    public void setScheduleNoon(String scheduleNoon) {
        this.scheduleNoon = scheduleNoon;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    public Integer getScheduleMaxPerson() {
        return scheduleMaxPerson;
    }

    public void setScheduleMaxPerson(Integer scheduleMaxPerson) {
        this.scheduleMaxPerson = scheduleMaxPerson;
    }

    public Object getCoursePresonFirst() {
        return coursePresonFirst;
    }

    public void setCoursePresonFirst(Object coursePresonFirst) {
        this.coursePresonFirst = coursePresonFirst;
    }

    public Object getCoursePresonSecond() {
        return coursePresonSecond;
    }

    public void setCoursePresonSecond(Object coursePresonSecond) {
        this.coursePresonSecond = coursePresonSecond;
    }

}
