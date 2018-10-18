/*
 * ReferralDepartmentDAO.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.condition.ReferralDepartmentCondition;
import com.mnt.preg.system.pojo.ReferralDepartmentPojo;

/**
 * 转诊科室DAO
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
@Repository
public class ReferralDepartmentDAO extends HibernateTemplate {

    /**
     * 
     * 条件检索转诊科室
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<ReferralDepartmentPojo> queryReferralDepartment(ReferralDepartmentCondition condition) {
        if (condition == null) {
            condition = new ReferralDepartmentCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ReferralDepartmentPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ReferralDepartmentPojo.class, "ReferralDepartmentPojo")
                + "   FROM sys_referral_dept AS ReferralDepartmentPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ReferralDepartmentPojo.class);
    }

    /**
     * 
     * 根据主键获取转诊科室
     * 
     * @author scd
     * @param referralId
     * @return
     */
    public ReferralDepartmentPojo getReferralDepartmentById(String referralId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(ReferralDepartmentPojo.class, "ReferralDepartmentPojo")
                + "   FROM sys_referral_dept AS ReferralDepartmentPojo "
                + "   WHERE ReferralDepartmentPojo.referral_id=:referralId ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("referralId", referralId);
        return this.SQLQueryAliasFirst(sql, paramMap, ReferralDepartmentPojo.class);
    }

    /**
     * 
     * 删除转诊科室
     * 
     * @author scd
     * @param referralId
     * @return
     */
    public int removeReferralDepartment(String referralId) {
        String sql = "UPDATE sys_referral_dept SET flag= :flag WHERE referral_id =:referralId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("referralId", referralId);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 
     * 获取转诊科室最大编码
     * 
     * @author scd
     * @return
     */
    public String getReferralDepartmentMaxCode() {
        String sql = "SELECT MAX(referral_code)"
                + "  FROM sys_referral_dept";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return this.SQLQueryFirst(sql, paramMap);
    }

    /**
     * 
     * 根据转诊科室名称或编码检索
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<ReferralDepartmentPojo> queryReferralByContent(ReferralDepartmentCondition condition) {
        if (condition == null) {
            condition = new ReferralDepartmentCondition();
        }
        String queryString = "  referralPojo.referral_name LIKE:referralName OR "
                + "             referralPojo.referral_code LIKE:referraCode";
        String querySQL = "SELECT " + DaoUtils.getSQLFields(ReferralDepartmentPojo.class, "referralPojo")
                + "        FROM sys_referral_dept AS referralPojo WHERE referralPojo.flag=:flag";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(condition.getContent())) {
            querySQL = querySQL + " AND" + queryString;
            paramMap.put("referralName", "%" + condition.getContent() + "%");
            paramMap.put("referraCode", "%" + condition.getContent() + "%");
        }
        paramMap.put("flag", 1);
        return this.SQLQueryAlias(querySQL, paramMap, ReferralDepartmentPojo.class);
    }
}
