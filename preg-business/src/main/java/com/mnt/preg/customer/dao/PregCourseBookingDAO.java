
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.system.entity.Schedule;
import com.mnt.preg.system.pojo.SchedulePojo;

/**
 * 客户DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
@Repository
public class PregCourseBookingDAO extends HibernateTemplate {

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
        String sql = "SELECT DISTINCT "
                + DaoUtils.getSQLFields(SchedulePojo.class, "schedulePojo")
                + "       ,(SELECT COUNT(schedule_id) "
                + "         FROM user_course_booking AS cb "
                + "         WHERE cb.schedule_id = schedulePojo.schedule_id "
                + "           AND cb.booking_date BETWEEN DATE_ADD(CURDATE(),INTERVAL 1 DAY) AND DATE_ADD(CURDATE(),INTERVAL 6 DAY)) AS coursePresonFirst "
                + "       ,(SELECT COUNT(schedule_id) "
                + "         FROM user_course_booking AS cb "
                + "         WHERE cb.schedule_id = schedulePojo.schedule_id "
                + "           AND cb.booking_date BETWEEN DATE_ADD(CURDATE(),INTERVAL 7 DAY) AND DATE_ADD(CURDATE(),INTERVAL 14 DAY)) AS coursePresonSecond "
                + "   FROM mas_pregnancy_course_schedule AS schedulePojo "
                + "   LEFT JOIN user_course_booking AS booking ON booking.schedule_id = schedulePojo.schedule_id "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), SchedulePojo.class);
    }

    /**
     * 根据条件查询预约课程
     * 
     * @author xdc
     * @param conditon
     * @return
     */
    public List<PregCourseBookingPojo> queryCourseBooking(PregCourseBooking condition) {
        if (condition == null) {
            condition = new PregCourseBooking();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregCourseBookingPojo");

        String querySQL = "SELECT"
                + DaoUtils.getSQLFields(PregCourseBookingPojo.class, "PregCourseBookingPojo")
                + "        FROM user_course_booking AS PregCourseBookingPojo "
                + "             LEFT JOIN user_course_feedback AS feedback ON feedback.booking_id = PregCourseBookingPojo.id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregCourseBookingPojo.class);
    }

    /**
     * 根据id获取预约课程
     * 
     * @author xdc
     * @param id
     * @return
     */
    public PregCourseBookingPojo getCourseBookingById(String id) {
        String querySQL = "SELECT" + DaoUtils.getSQLFields(PregCourseBookingPojo.class, "PregCourseBookingPojo")
                + "        FROM user_course_booking AS PregCourseBookingPojo"
                + "        WHERE id=:id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        return this.SQLQueryAliasFirst(querySQL, queryParams, PregCourseBookingPojo.class);
    }

    /**
     * 根据id删除预约课程
     * 
     * @author xdc
     * @param id
     */
    public void deleteCourseBookingById(String id) {
        String sql = "DELETE FROM user_course_booking WHERE id=:id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        this.executeSQL(sql, queryParams);
    }

}
