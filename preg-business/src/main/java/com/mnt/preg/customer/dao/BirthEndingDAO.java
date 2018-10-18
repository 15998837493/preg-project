
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.customer.condition.BirthEndingCondition;
import com.mnt.preg.customer.pojo.BirthEndingPojo;

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
public class BirthEndingDAO extends HibernateTemplate {

    public List<BirthEndingPojo> queryCustomer(BirthEndingCondition condition) {

        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "BirthEndingPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(BirthEndingPojo.class, "BirthEndingPojo")
                + "   ,BirthEndingBase.base_time as birthDate"
                + "   ,BirthEndingBase.base_time_hour as baseTimeHour"
                + "   ,BirthEndingBase.base_time_minuters as baseTimeMinuters"
                + "   ,BirthEndingBase.base_Weeks as birthWeeks"
                + "   FROM cus_birthending AS BirthEndingPojo "
                + "   LEFT JOIN cus_birthending_baseinfo as BirthEndingBase"
                + "   ON BirthEndingPojo.birth_id = BirthEndingBase.birth_id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), BirthEndingPojo.class);
    }

    public BirthEndingPojo getBirthById(String birthId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(BirthEndingPojo.class, "BirthEndingPojo")
                + "   ,BirthEndingBase.base_time as birthDate"
                + "   ,BirthEndingBase.base_Weeks as birthWeeks"
                + "   FROM cus_birthending AS BirthEndingPojo "
                + "   LEFT JOIN cus_birthending_baseinfo as BirthEndingBase"
                + "   ON BirthEndingPojo.birth_id = BirthEndingBase.birth_id"
                + "   WHERE BirthEndingPojo.birth_id=:birthId "
                + "   AND BirthEndingPojo.flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("birthId", birthId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, BirthEndingPojo.class);
    }

    public void deleteResords(String birthId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("birthId", birthId);
        this.executeSQL("DELETE FROM cus_birthending WHERE birth_id=:birthId", paramsMap);
    }
}
