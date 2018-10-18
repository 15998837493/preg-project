
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 医师出诊排班表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "mas_user_schedule")
public class Doctor extends MappedEntity {

    private static final long serialVersionUID = 1474075545493595641L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @NotBlank
    private String scheduleId;

    /** 用户ID(永不重复) */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @NotBlank
    private String userId;

    /** 周 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String scheduleWeek;

    /** 课程允许最大人数 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private Integer scheduleMaxPerson;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "schedule_id")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "schedule_week")
    public String getScheduleWeek() {
        return scheduleWeek;
    }

    public void setScheduleWeek(String scheduleWeek) {
        this.scheduleWeek = scheduleWeek;
    }

    @Column(name = "schedule_max_person")
    public Integer getScheduleMaxPerson() {
        return scheduleMaxPerson;
    }

    public void setScheduleMaxPerson(Integer scheduleMaxPerson) {
        this.scheduleMaxPerson = scheduleMaxPerson;
    }

}
