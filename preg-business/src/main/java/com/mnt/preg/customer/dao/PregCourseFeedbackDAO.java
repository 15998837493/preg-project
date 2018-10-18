
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.customer.entity.PregCourseFeedback;
import com.mnt.preg.customer.pojo.PregCourseFeedbackPojo;

/**
 * 
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 xdc 初版
 */
@Repository
public class PregCourseFeedbackDAO extends HibernateTemplate {

    /**
     * 根据条件查询预约课程
     * 
     * @author xdc
     * @param conditon
     * @return
     */
    public List<PregCourseFeedbackPojo> queryCourseFeedback(PregCourseFeedback condition) {
        if (condition == null) {
            condition = new PregCourseFeedback();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregCourseFeedbackPojo");

        String querySQL = "SELECT" + DaoUtils.getSQLFields(PregCourseFeedbackPojo.class, "PregCourseFeedbackPojo")
                + "        FROM user_course_feedback AS PregCourseFeedbackPojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregCourseFeedbackPojo.class);
    }

    /**
     * 根据id获取预约课程
     * 
     * @author xdc
     * @param id
     * @return
     */
    public PregCourseFeedbackPojo getPregCourseFeedbackById(String id) {
        String querySQL = "SELECT" + DaoUtils.getSQLFields(PregCourseFeedbackPojo.class, "PregCourseFeedbackPojo")
                + "        FROM user_course_feedback AS PregCourseFeedbackPojo"
                + "        WHERE PregCourseFeedbackPojo.feed_id=:id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        return this.SQLQueryAliasFirst(querySQL, queryParams, PregCourseFeedbackPojo.class);
    }

}
