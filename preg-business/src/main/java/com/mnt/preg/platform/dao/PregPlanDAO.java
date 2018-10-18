
package com.mnt.preg.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.platform.condition.IntervenePlanCondition;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;
import com.mnt.preg.platform.pojo.PregPlanCoursePojo;
import com.mnt.preg.platform.pojo.PregPlanDietPojo;
import com.mnt.preg.platform.pojo.PregPlanIntakeDetailPojo;
import com.mnt.preg.platform.pojo.PregPlanPointsPojo;
import com.mnt.preg.platform.pojo.PregUserIntakeDetailPojo;
import com.mnt.preg.platform.pojo.PregUserIntakePojo;

/**
 * 诊疗DAO
 *
 * @author zcq
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2016-6-14 zcq 初版
 */
@Repository
public class PregPlanDAO extends HibernateTemplate {

    /**
     * 条件检索膳食方案信息
     *
     * @param condition
     * @return
     * @author zcq
     */
    public List<PregIntervenePlanPojo> queryIntervenePlan(IntervenePlanCondition condition) {
        if (condition == null) {
            condition = new IntervenePlanCondition();
        }
        // 查询个人膳食方案记录
        if (StringUtils.isNotEmpty(condition.getCustId())) {
            String sql = "SELECT diagnosis_id FROM user_diagnosis AS diagnosis WHERE diagnosis.diagnosis_customer=:custId";
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("custId", condition.getCustId());
            List<String> diagnosisIdList = this.SQLQuery(sql, paramsMap);
            condition.setDiagnosisIdList(diagnosisIdList);
        }
        // 条件检索列表
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "IntervenePlanVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregIntervenePlanPojo.class, "IntervenePlanVo")
                + "        FROM user_plan AS IntervenePlanVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregIntervenePlanPojo.class);
    }

    /**
     * 根据接诊编码获取干预方案信息
     *
     * @param diagnosisId
     * @return
     * @author zcq
     */
    public PregIntervenePlanPojo getIntervenePlanByDiagnosis(String diagnosisId) {
        IntervenePlanCondition condition = new IntervenePlanCondition();
        condition.setDiagnosisId(diagnosisId);
        List<PregIntervenePlanPojo> list = this.queryIntervenePlan(condition);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 查询干预方案--摄入量模版信息
     *
     * @param planId
     * @return
     * @author zcq
     */
    public List<PregPlanIntakeDetailPojo> queryPlanIntakeDetailByPlanId(String planId) {
        if (StringUtils.isEmpty(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregPlanIntakeDetailPojo.class, "PlanIntakeDetailVo")
                + "   FROM user_plan_intake_detail AS PlanIntakeDetailVo"
                + "   WHERE PlanIntakeDetailVo.plan_id=:planId"
                + "       AND PlanIntakeDetailVo.flag=:flag";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("planId", planId);
        paramsMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramsMap, PregPlanIntakeDetailPojo.class);
    }

    /**
     * 查询干预方案--食谱信息
     *
     * @param planId
     * @return
     * @author zcq
     */
    public List<PregPlanDietPojo> queryPlanDietDetailsByPlanId(String planId) {
        if (StringUtils.isBlank(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregPlanDietPojo.class, "PlanDietVo")
                + "   FROM user_plan_diet AS PlanDietVo"
                + "   WHERE PlanDietVo.plan_id=:planId"
                + "       AND PlanDietVo.flag=:flag"
                + "   ORDER BY PlanDietVo.food_day ASC,PlanDietVo.food_meal ASC";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("planId", planId);
        queryMap.put("flag", 1);
        return this.SQLQueryAlias(sql, queryMap, PregPlanDietPojo.class);
    }

    /**
     * 查询一天食谱明细
     *
     * @param planId
     * @param foodDay
     * @return java.util.List<com.mnt.preg.platform.pojo.PregPlanDietPojo>
     * @author zcq
     * @date 2018/10/17 9:53
     */
    public List<PregPlanDietPojo> queryPlanDietDetailsByPlanIdAndFoodDay(String planId, String foodDay) {
        if (StringUtils.isBlank(planId) || StringUtils.isBlank(foodDay)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregPlanDietPojo.class, "PlanDietVo")
                + "   FROM user_plan_diet AS PlanDietVo"
                + "   WHERE PlanDietVo.plan_id=:planId"
                + "       AND PlanDietVo.food_day=:foodDay"
                + "       AND PlanDietVo.flag=:flag"
                + "   ORDER BY PlanDietVo.food_day ASC,PlanDietVo.food_meal ASC";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("planId", planId);
        queryMap.put("foodDay", foodDay);
        queryMap.put("flag", 1);
        return this.SQLQueryAlias(sql, queryMap, PregPlanDietPojo.class);
    }

    /**
     * 查询干预方案--饮食原则
     *
     * @param planId
     * @return
     * @author zcq
     */
    public List<PregPlanPointsPojo> queryPlanPointsByPlanId(String planId) {
        if (StringUtils.isEmpty(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregPlanPointsPojo.class, "PlanPointsVo")
                + "   FROM user_plan_points AS PlanPointsVo"
                + "   WHERE PlanPointsVo.plan_id=:planId"
                + "       AND PlanPointsVo.flag=:flag"
                + "   ORDER BY PlanPointsVo.point_order ASC";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("planId", planId);
        queryMap.put("flag", 1);
        return this.SQLQueryAlias(sql, queryMap, PregPlanPointsPojo.class);
    }

    /**
     * 查询干预方案--基础课程/诊断课程
     *
     * @param planId
     * @param pregType 1=基础课程，2=诊断课程
     * @return
     * @author zcq
     */
    public List<PregPlanCoursePojo> queryPlanCourseByPlanIdAndPregType(String planId, String pregType) {
        if (StringUtils.isEmpty(planId) || StringUtils.isEmpty(pregType)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String addSql = "";
        if (!("0").equals(pregType)) {
            addSql = "       AND PlanCourseVo.preg_type=:pregType";
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregPlanCoursePojo.class, "PlanCourseVo")
                + "   FROM user_plan_course AS PlanCourseVo"
                + "   WHERE PlanCourseVo.plan_id=:planId"
                + addSql
                + "       AND PlanCourseVo.flag=:flag";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("planId", planId);
        if (!("0").equals(pregType)) {
            queryMap.put("pregType", pregType);
        }
        queryMap.put("flag", 1);
        return this.SQLQueryAlias(sql, queryMap, PregPlanCoursePojo.class);
    }

    /**
     * 删除膳食方案--饮食原则
     *
     * @param planId
     * @author zcq
     */
    public void deletePlanPoints(String planId) {
        if (StringUtils.isEmpty(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("planId", planId);
        // 删除饮食原则表（user_plan_points）
        this.executeSQL("DELETE FROM user_plan_points WHERE plan_id=:planId", paramsMap);
    }

    /**
     * 删除膳食方案--课程
     *
     * @param planId
     * @param pregType
     * @author zcq
     */
    public void deletePlanCourse(String planId, String pregType) {
        if (StringUtils.isEmpty(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("planId", planId);
        paramsMap.put("pregType", pregType);
        // 删除课程表（user_plan_course）
        this.executeSQL("DELETE FROM user_plan_course WHERE plan_id=:planId AND preg_type=:pregType", paramsMap);
    }

    /**
     * 删除膳食方案--食谱
     *
     * @param planId
     * @author zcq
     */
    public void deletePlanDiet(String planId) {
        if (StringUtils.isEmpty(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("planId", planId);
        // 删除食谱表（user_plan_diet）
        this.executeSQL("DELETE FROM user_plan_diet WHERE plan_id=:planId", paramsMap);
    }

    /**
     * 删除膳食方案--食谱中的菜谱
     *
     * @param planId
     * @param foodMeal
     * @param foodId
     * @return void
     * @author zcq
     * @date 2018/10/17 14:55
     */
    public void deletePlanDietFood(String planId, String foodMeal, String foodId) {
        if (StringUtils.isBlank(planId) || StringUtils.isBlank(foodMeal) || StringUtils.isBlank(foodId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("planId", planId);
        paramsMap.put("foodMeal", foodMeal);
        paramsMap.put("foodId", foodId);
        // 删除食谱表（user_plan_diet）
        this.executeSQL("DELETE FROM user_plan_diet WHERE plan_id=:planId AND food_meal=:foodMeal AND food_id=:foodId", paramsMap);
    }

    /**
     * 删除膳食方案--膳食明细
     *
     * @param planId
     * @author zcq
     */
    public void deletePlanIntakeDetail(String planId) {
        if (StringUtils.isEmpty(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("planId", planId);
        // 删除摄入量表（user_plan_intake_detail）
        this.executeSQL("DELETE FROM user_plan_intake_detail WHERE plan_id=:planId", paramsMap);
    }

    /**
     * 物理删除干预方案相关信息
     *
     * @return
     * @author zcq
     */
    public void deleteIntervenePlan(String planId) {
        if (StringUtils.isEmpty(planId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("planId", planId);
        // 删除饮食原则表（user_plan_points）
        // this.executeSQL("DELETE FROM user_plan_points WHERE plan_id=:planId", paramsMap);
        // 删除课程表（user_plan_course）
        // this.executeSQL("DELETE FROM user_plan_course WHERE plan_id=:planId", paramsMap);
        // 删除食谱表（user_plan_diet）
        // this.executeSQL("DELETE FROM user_plan_diet WHERE plan_id=:planId", paramsMap);
        // 删除摄入量表（user_plan_intake_detail）
        this.executeSQL("DELETE FROM user_plan_intake_detail WHERE plan_id=:planId", paramsMap);
    }

    /**
     * 查询个人膳食模板
     *
     * @param userId
     * @return
     * @author zcq
     */
    public List<PregUserIntakePojo> queryUserIntakeByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregUserIntakePojo.class, "PregUserIntakePojo")
                + "   FROM user_intake AS PregUserIntakePojo"
                + "   WHERE PregUserIntakePojo.create_user_id=:userId"
                + "       AND PregUserIntakePojo.flag=:flag"
                + "   ORDER BY PregUserIntakePojo.create_time DESC";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("userId", userId);
        queryMap.put("flag", 1);
        return this.SQLQueryAlias(sql, queryMap, PregUserIntakePojo.class);
    }

    /**
     * 获取个人膳食模板明细
     *
     * @param intakeId
     * @return
     * @author zcq
     */
    public List<PregUserIntakeDetailPojo> queryUserIntakeDetailByIntakeId(String intakeId) {
        if (StringUtils.isEmpty(intakeId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregUserIntakeDetailPojo.class, "PregUserIntakeDetailPojo")
                + "   FROM user_intake_detail AS PregUserIntakeDetailPojo"
                + "   WHERE PregUserIntakeDetailPojo.intake_id=:intakeId"
                + "       AND PregUserIntakeDetailPojo.flag=:flag";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("intakeId", intakeId);
        queryMap.put("flag", 1);
        return this.SQLQueryAlias(sql, queryMap, PregUserIntakeDetailPojo.class);
    }

    /**
     * 删除个人膳食模板
     *
     * @param intakeId
     * @author zcq
     */
    public void deletePersonIntake(String intakeId) {
        if (StringUtils.isEmpty(intakeId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("intakeId", intakeId);
        // 删除摄入量表（user_intake）
        this.executeSQL("DELETE FROM user_intake WHERE intake_id=:intakeId", paramsMap);
    }

    /**
     * 删除个人膳食模板明细
     *
     * @param intakeId
     * @author zcq
     */
    public void deleteUserIntakeDetail(String intakeId) {
        if (StringUtils.isEmpty(intakeId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("intakeId", intakeId);
        // 删除摄入量表（user_intake_detail）
        this.executeSQL("DELETE FROM user_intake_detail WHERE intake_id=:intakeId", paramsMap);
    }

    /**
     * 校验跟人膳食模板名称是否可用
     *
     * @param intakeName
     * @return
     * @author zcq
     */
    public Integer validatePersonIntakeName(String intakeName) {
        String countSQL = "SELECT COUNT(intake_id) FROM user_intake WHERE intake_name=:intakeName AND flag=:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("intakeName", intakeName);
        paramMap.put("flag", 1);
        return this.SQLCount(countSQL, paramMap);
    }

}
