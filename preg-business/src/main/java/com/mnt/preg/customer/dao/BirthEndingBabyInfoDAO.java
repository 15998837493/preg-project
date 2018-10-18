
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.customer.condition.BirthEndingBabyInfoCondition;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;

/**
 * 新生儿DAO
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-07 wjc 初版
 */
@Repository
public class BirthEndingBabyInfoDAO extends HibernateTemplate {

    /**
     * 获取新生儿信息
     * 
     * @author mlq
     * @param birthId
     * @return
     */
    public List<BirthEndingBabyInfoPojo> getBabyListByBirthId(String birthId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(BirthEndingBabyInfoPojo.class, "BirthEndingBabyInfoPojo")
                + "   FROM cus_birthending_babyinfo AS BirthEndingBabyInfoPojo "
                + "   WHERE BirthEndingBabyInfoPojo.birth_id=:birthId "
                + "   AND BirthEndingBabyInfoPojo.flag=:flag ORDER BY babyIsDeath,BirthEndingBabyInfoPojo.create_time,babyId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("birthId", birthId);
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, BirthEndingBabyInfoPojo.class);
    }

    /**
     * 获取新生儿信息
     * 
     * @author mlq
     * @param babyId
     * @return
     */
    public BirthEndingBabyInfoPojo getById(String babyId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(BirthEndingBabyInfoPojo.class, "BirthEndingBabyInfoPojo")
                + "   FROM cus_birthending_babyinfo AS BirthEndingBabyInfoPojo "
                + "   WHERE BirthEndingBabyInfoPojo.baby_id=:babyId "
                + "   AND BirthEndingBabyInfoPojo.flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("babyId", babyId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, BirthEndingBabyInfoPojo.class);
    }

    /**
     * 删除新生儿信息（主键）
     * 
     * @author mlq
     * @param babyId
     * @return
     */
    public void deleteBabyById(String babyId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("babyId", babyId);
        this.executeSQL("DELETE FROM cus_birthending_babyinfo WHERE baby_id=:babyId", paramsMap);
    }

    /**
     * 删除新生儿信息（分娩id）
     * 
     * @author mlq
     * @param birthId
     * @return
     */
    public void deleteBabyByBirthId(String birthId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("birthId", birthId);
        this.executeSQL("DELETE FROM cus_birthending_babyinfo WHERE birth_id=:birthId", paramsMap);
    }

    /**
     * 查询新生儿列表信息（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<BirthEndingBabyInfoPojo> getBabyListByCondition(BirthEndingBabyInfoCondition condition) {
        if (condition == null) {
            condition = new BirthEndingBabyInfoCondition();
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "birthEndingBabyInfoPojo");
        String sql = "SELECT "
                + DaoUtils.getSQLFields(BirthEndingBabyInfoPojo.class, "birthEndingBabyInfoPojo")
                + "   ,dischargedPojo.dis_baby_diagnosis AS disBabyDiagnosis "
                + "   ,dischargedPojo.dis_baby_blood_suger AS disBabyBloodSuger "
                + "   FROM cus_birthending_babyinfo AS birthEndingBabyInfoPojo "
                + "   LEFT JOIN cus_birthending_discharged AS dischargedPojo ON dischargedPojo.baby_id = birthEndingBabyInfoPojo.baby_id"
                + "   AND dischargedPojo.birth_id = birthEndingBabyInfoPojo.birth_id"
                + queryCondition.getQueryString()
                + "   GROUP BY birthEndingBabyInfoPojo.baby_id"
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), BirthEndingBabyInfoPojo.class);
    }

    /**
     * 查询新生儿数量最大值
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public BirthEndingBabyInfoPojo getMaxBabyListByCondition(BirthEndingBabyInfoCondition condition) {
        if (condition == null) {
            condition = new BirthEndingBabyInfoCondition();
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "birthEndingBabyInfoPojo");
        // sql语法
        String sql = "SELECT "
                + DaoUtils.getSQLFields(BirthEndingBabyInfoPojo.class, "birthEndingBabyInfoPojo")
                + "  ,COUNT(birthEndingBabyInfoPojo.baby_id) AS babyCount"
                + "   FROM cus_birthending_babyinfo AS birthEndingBabyInfoPojo "
                + queryCondition.getQueryString()
                + " GROUP BY birthEndingBabyInfoPojo.birth_id"
                + " ORDER BY babyCount DESC limit 1";
        return this.SQLQueryAliasFirst(sql, queryCondition.getQueryParams(), BirthEndingBabyInfoPojo.class);
    }
}
