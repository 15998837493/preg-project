
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.Doctor;
import com.mnt.preg.system.pojo.DoctorPojo;

/**
 * 医师出诊排班表DAO（主表：mas_user_schedule）
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zy 初版
 */
@Repository
public class DoctorDao extends HibernateTemplate {

    /**
     * 条件查询医师出诊排班表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<DoctorPojo> queryDoctorByCondition(Doctor condition) {
        if (condition == null) {
            condition = new Doctor();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "schedulePojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(DoctorPojo.class, "schedulePojo")
                + "   FROM mas_user_schedule AS schedulePojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DoctorPojo.class);
    }

    /**
     * 删除医师诊断配置信息(逻辑删除)
     * 
     * @author zcq
     * @param codeIdList
     * @return
     */
    public Integer deleteDoctor(String scheduleId) {
        String sql = "UPDATE mas_user_schedule SET flag= :flag where schedule_id = :scheduleId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("scheduleId", scheduleId);
        params.put("flag", 0);
        return this.executeSQL(sql, params);
    }
    
    /**
     * 删除一周课程配置信息(物理删除)
     * 
     * @author zcq
     * @param codeIdList
     * @return
     */
    public Integer deleteDoctorByUserId(String userId) {
        String sql = "DELETE FROM mas_user_schedule WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.executeSQL(sql, params);
    }
}
