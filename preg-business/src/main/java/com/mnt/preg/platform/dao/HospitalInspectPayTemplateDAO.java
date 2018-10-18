/*
 * HospitalInspectPayTemplateDAO.java    1.0  2017-7-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.platform.entity.HospitalInspectPayTemplate;

/**
 * 辅助检查套餐DAO
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-20 scd 初版
 */
@Repository
public class HospitalInspectPayTemplateDAO extends HibernateTemplate {

    /**
     * 
     * 条件检索辅助检查项目套餐
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<HospitalInspectPayTemplate> queryAuxiliaryCheckByCondition(HospitalInspectPayTemplate condition) {
        if (condition == null) {
            condition = new HospitalInspectPayTemplate();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "AuxiliaryCheck");

        String sql = "SELECT "
                + DaoUtils.getSQLFields(HospitalInspectPayTemplate.class, "AuxiliaryCheck")
                + "   FROM user_hospital_inspect_pay_template AS AuxiliaryCheck"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), HospitalInspectPayTemplate.class);
    }
}
