
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.customer.pojo.BirthEndingBaseInfoPojo;

/**
 * 分娩信息DAO
 * 
 * @author dhs
 * @version 1.7
 * 
 *          变更履历：
 *          v1.7 2018-09-18 dhs 重构
 */
@Repository
public class BirthEndingBaseInfoDAO extends HibernateTemplate {

    public BirthEndingBaseInfoPojo getBaseByBirthById(String birthId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(BirthEndingBaseInfoPojo.class, "BirthEndingBaseInfoPojo")
                + "   FROM cus_birthending_baseinfo AS BirthEndingBaseInfoPojo "
                + "   WHERE BirthEndingBaseInfoPojo.birth_id=:birthId "
                + "   AND BirthEndingBaseInfoPojo.flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("birthId", birthId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, BirthEndingBaseInfoPojo.class);
    }

    public void deleteResords(String birthId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("birthId", birthId);
        this.executeSQL("DELETE FROM cus_birthending_baseinfo WHERE birth_id=:birthId", paramsMap);
    }

}
