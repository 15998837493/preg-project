
package com.mnt.preg.master.dao.preg;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.entity.preg.IntakeType;
import com.mnt.preg.master.pojo.preg.IntakeTypePojo;

/**
 * 膳食干预方案-摄入类型DAO
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历： v1.0 2017-3-23 wsy 初版
 */
@Repository
public class PregIntakeTypeDAO extends HibernateTemplate {

    /**
     * 条件检索摄入量类型
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<IntakeTypePojo> queryIntakeType(IntakeType condition) {
        if (condition == null) {
            condition = new IntakeType();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "IntakeTypePojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(IntakeTypePojo.class, "IntakeTypePojo")
                + "   FROM mas_intake_type AS IntakeTypePojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), IntakeTypePojo.class);
    }
}
