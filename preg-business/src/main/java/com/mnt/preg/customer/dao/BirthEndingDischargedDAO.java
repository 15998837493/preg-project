
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.customer.pojo.BirthEndingDischargedPojo;

/**
 * 出院诊断DAO
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-07 wjc 初版
 */
@Repository
public class BirthEndingDischargedDAO extends HibernateTemplate {

    /**
     * 查询新生儿诊断信息
     * 
     * @author mlq
     * @param birthId
     * @return
     */
    public List<BirthEndingDischargedPojo> getDischargedByBirthById(String birthId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(BirthEndingDischargedPojo.class, "BirthEndingDischargedPojo")
                + "   FROM cus_birthending_discharged AS BirthEndingDischargedPojo "
                + "   WHERE BirthEndingDischargedPojo.birth_id=:birthId "
                + "   AND BirthEndingDischargedPojo.flag=:flag ORDER BY BirthEndingDischargedPojo.baby_id asc";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("birthId", birthId);
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, BirthEndingDischargedPojo.class);
    }

    public BirthEndingDischargedPojo getByDisId(String disId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(BirthEndingDischargedPojo.class, "BirthEndingDischargedPojo")
                + "   FROM cus_birthending_discharged AS BirthEndingDischargedPojo "
                + "   WHERE BirthEndingDischargedPojo.dis_id=:dis_id ";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("dis_id", disId);
        return this.SQLQueryAliasFirst(sql, queryParams, BirthEndingDischargedPojo.class);
    }

    /**
     * 删除诊断信息（新生儿id）
     * 
     * @author mlq
     * @param babyId
     * @return
     */
    public void deleteDischargedByBabyId(String babyId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("babyId", babyId);
        this.executeSQL("DELETE FROM cus_birthending_discharged WHERE baby_id=:babyId", paramsMap);
    }

    /**
     * 删除诊断信息（分娩id）
     * 
     * @author mlq
     * @param birthId
     * @return
     */
    public void deleteDischargedByBirthId(String birthId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("birthId", birthId);
        this.executeSQL("DELETE FROM cus_birthending_discharged WHERE birth_id=:birthId", paramsMap);
    }

}
