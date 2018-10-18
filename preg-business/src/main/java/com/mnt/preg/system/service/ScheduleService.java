
package com.mnt.preg.system.service;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.entity.Schedule;
import com.mnt.preg.system.pojo.SchedulePojo;

@Validated
public interface ScheduleService {

    /**
     * 条件检索一周课程配置基础数据
     * 
     * @author dhs
     * @param condition
     * @return
     */
    List<SchedulePojo> queryScheduleByCondition(Schedule condition);

    /**
     * 
     * 增加记录
     * 
     * @author gss
     * @param nutrient
     * @return NutrientVo
     */
    SchedulePojo saveSchedule(Schedule schedule);

    /**
     * 
     * 修改记录
     * 
     * @author gss
     * @param nutrientVo
     * @param nutrientId
     *            主键
     */
    SchedulePojo updateSchedule(Schedule schedule);

    /**
     * 
     * 根据主键删除记录
     * 
     * @author gss
     * @param id
     */
    void removeSchedule(@NotBlank String id);
}
