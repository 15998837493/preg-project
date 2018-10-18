
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.condition.ReferralDoctorCondition;
import com.mnt.preg.system.entity.ReferralDoctor;
import com.mnt.preg.system.pojo.ReferralDoctorPojo;

/**
 * 转诊医生配置DAO（主表：referral_doctor）
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：
 *          v1.5 2018-03-21 dhs 初版
 */
@Repository
public class ReferralDoctorDAO extends HibernateTemplate {

    /**
     * 查询医生
     * 
     * @author dhs
     * @param condition
     * @return List<ReferralDoctorPojo>
     */
    public List<ReferralDoctorPojo> queryReferralDoctor(ReferralDoctorCondition condition) {
        if (condition == null) {
            condition = new ReferralDoctorCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ReferralDoctorPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ReferralDoctorPojo.class, "ReferralDoctorPojo")
                + "   FROM sys_referral_doctor AS ReferralDoctorPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ReferralDoctorPojo.class);
    }

    /**
     * 逻辑删除医生
     * 
     * @author dhs
     * @param id
     *            医生主键
     * @return Integer
     */
    public Integer removeDoctor(String id) {
        String sql = "UPDATE sys_referral_doctor SET flag = 0 WHERE id=:id";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 检索全部转诊医生以及该转诊医生所在的科室
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<ReferralDoctorPojo> queryReferralDoctorDept(ReferralDoctor condition) {
        if (condition == null) {
            condition = new ReferralDoctor();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "doctorPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ReferralDoctorPojo.class, "doctorPojo")
                + "   FROM sys_referral_doctor AS doctorPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ReferralDoctorPojo.class);
    }
}
