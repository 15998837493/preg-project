
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.Schedule;
import com.mnt.preg.system.pojo.SchedulePojo;

/**
 * 一周课程配置表DAO（主表：mas_pregnancy_course_schedule）
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zy 初版
 */
@Repository
public class ScheduleDao extends HibernateTemplate {

    /**
     * 条件查询一周配置表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<SchedulePojo> queryScheduleByCondition(Schedule condition) {
        if (condition == null) {
            condition = new Schedule();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "schedulePojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(SchedulePojo.class, "schedulePojo")
                + "   FROM mas_pregnancy_course_schedule AS schedulePojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), SchedulePojo.class);
    }

    /**
     * 删除一周课程配置信息(逻辑删除)
     * 
     * @author zcq
     * @param codeIdList
     * @return
     */
    public Integer deleteSchedules(String scheduleId) {
        String sql = "UPDATE mas_pregnancy_course_schedule SET flag= :flag where schedule_id = :scheduleId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("scheduleId", scheduleId);
        params.put("flag", 0);
        return this.executeSQL(sql, params);
    }

}
