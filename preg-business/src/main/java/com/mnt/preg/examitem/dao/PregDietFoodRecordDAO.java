
package com.mnt.preg.examitem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.examitem.pojo.PregFoodDetailsPojo;
import com.mnt.preg.examitem.pojo.PregFoodFeelTimePojo;
import com.mnt.preg.examitem.pojo.PregFoodFrequencyPojo;
import com.mnt.preg.examitem.pojo.PregFoodRecordPojo;
import com.mnt.preg.examitem.pojo.PregHabitPojo;

/**
 * 膳食评估记录DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
@Repository
public class PregDietFoodRecordDAO extends HibernateTemplate {

    /**
     * 查询膳食记录信息
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    public PregFoodRecordPojo getFoodRecord(String foodRecordId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregFoodRecordPojo.class, "FoodRecordVo")
                + "   FROM cus_food_record AS FoodRecordVo"
                + "        JOIN cus_customer AS cust ON cust.cust_id=FoodRecordVo.cust_id"
                + "   WHERE FoodRecordVo.food_record_id=:foodRecordId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("foodRecordId", foodRecordId);
        return this.SQLQueryAliasFirst(sql, paramMap, PregFoodRecordPojo.class);
    }

    /**
     * 根据报告日期查询报告基本信息
     * 
     * @author gss
     * @param reportDate
     * @return
     */
    public List<PregFoodRecordPojo> getFoodRecordByReportDate(String reportDate) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregFoodRecordPojo.class, "FoodRecordVo")
                + "   FROM cus_food_record AS FoodRecordVo"
                + "   WHERE FoodRecordVo.record_datetime=:reportDate";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reportDate", "'%" + reportDate + "%'");
        return this.SQLQueryAlias(sql, paramMap, PregFoodRecordPojo.class);
    }

    /**
     * 查询用餐时间和感受--根据【膳食评估编号】
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    public List<PregFoodFeelTimePojo> queryFoodFeelTime(String foodRecordId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregFoodFeelTimePojo.class, "FoodFeelTimeVo")
                + "   FROM cus_food_feel_time AS FoodFeelTimeVo"
                + "   WHERE FoodFeelTimeVo.food_record_id=:foodRecordId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, paramMap, PregFoodFeelTimePojo.class);
    }

    /**
     * 查询用餐明细--根据【膳食评估编号】
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    public List<PregFoodDetailsPojo> queryFoodDetails(String foodRecordId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregFoodDetailsPojo.class, "FoodDetailsVo") + ","
                + "        mf.food_name AS foodName"
                + "   FROM cus_food_details AS FoodDetailsVo"
                + "        JOIN mas_food AS mf ON mf.food_id=FoodDetailsVo.food_id"
                + "   WHERE FoodDetailsVo.food_record_id=:foodRecordId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, paramMap, PregFoodDetailsPojo.class);
    }

    /**
     * 查询饮食习惯--根据【膳食评估编号】（孕期）
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    public PregHabitPojo getPregnancyHabit(String foodRecordId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregHabitPojo.class, "PregnancyHabitVo")
                + "   FROM cus_food_pregnancy_habit AS PregnancyHabitVo"
                + "   WHERE PregnancyHabitVo.food_record_id=:foodRecordId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("foodRecordId", foodRecordId);
        return this.SQLQueryAliasFirst(sql, paramMap, PregHabitPojo.class);
    }

    /**
     * 查询饮食频率--根据【膳食评估编号】
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    public List<PregFoodFrequencyPojo> queryFoodFrequency(String foodRecordId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregFoodFrequencyPojo.class, "FoodFrequencyVo")
                + "   FROM cus_food_frequency AS FoodFrequencyVo"
                + "   WHERE FoodFrequencyVo.food_record_id=:foodRecordId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("foodRecordId", foodRecordId);
        return this.SQLQueryAlias(sql, paramMap, PregFoodFrequencyPojo.class);
    }

    /**
     * 删除膳食评估饮食明细
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    public Integer deleteFoodDetails(String foodRecordId) {
        String sql = "DELETE FROM cus_food_details WHERE food_record_id=:foodRecordId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("foodRecordId", foodRecordId);
        return this.executeSQL(sql, paramMap);
    }

    /**
     * 删除膳食评估三餐时间和感受
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    public Integer deleteFoodFeelTime(String foodRecordId) {
        String sql = "DELETE FROM cus_food_feel_time WHERE food_record_id=:foodRecordId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("foodRecordId", foodRecordId);
        return this.executeSQL(sql, paramMap);
    }

}
