
package com.mnt.preg.platform.service;

import java.util.List;
import java.util.Map;

import com.mnt.preg.platform.entity.*;
import com.mnt.preg.platform.pojo.*;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.preg.PregnancyCourseCondition;
import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;
import com.mnt.preg.platform.condition.IntervenePlanCondition;

/**
 * 诊疗服务
 *
 * @author zcq
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2016-6-14 zcq 初版
 */
@Validated
public interface PregPlanService {

    // *******************************************【膳食方案】******************************************

    /**
     * 查询干预方案列表--条件检索
     *
     * @param condition
     * @return
     * @author zcq
     */
    List<PregIntervenePlanPojo> queryIntervenePlan(IntervenePlanCondition condition);

    /**
     * 获取干预方案信息
     *
     * @param planId
     * @return
     * @author zcq
     */
    PregIntervenePlanPojo getIntervenePlan(String planId);

    /**
     * 修改干预方案信息
     *
     * @param plan
     * @author zcq
     */
    void updateIntervenePlan(PregIntervenePlan plan);

    /**
     * 物理删除干预方案所有相关信息
     *
     * @param planId
     * @author zcq
     */
    void deleteIntervenPlan(String planId);

    /**
     * 获取干预方案初始化信息
     *
     * @param diagnosisId
     * @return
     * @author zcq
     */
    PregInitPlanGroupPojo getInitPlanGroup(String diagnosisId);

    /**
     * 获取干预方案信息--根据接诊号
     *
     * @param diagnosisId
     * @return IntervenePlanVo
     * @author zcq
     */
    PregIntervenePlanPojo getIntervenePlanByDiagnosisId(String diagnosisId);

    /**
     * 获取膳食方案清单--根据接诊号
     *
     * @param diagnosisId
     * @return
     * @author zcq
     */
    List<PregPlanIntakeDetailPojo> getPlanIntakeDetailByDiagnosisId(String diagnosisId);

    /**
     * 查询一天食谱明细
     *
     * @param planId
     * @param foodDay
     * @return java.util.List<com.mnt.preg.platform.pojo.PregPlanDietPojo>
     * @author zcq
     * @date 2018/10/17 9:53
     */
    List<PregPlanDietPojo> queryPlanDietDetailsByPlanIdAndFoodDay(String planId, String foodDay);

    /**
     * 一天食谱中添加菜谱
     *
     * @param planDiet
     * @return java.lang.String
     * @author zcq
     * @date 2018/10/17 11:42
     */
    String addPlanDietDetail(PregPlanDiet planDiet);

    /**
     * 一天食谱中修改菜谱
     *
     * @param planDiet
     * @return void
     * @author zcq
     * @date 2018/10/17 13:53
     */
    void updatePlanDietDetail(PregPlanDiet planDiet);

    /**
     * 一天食谱中删除菜谱
     *
     * @param planId
     * @param foodMeal
     * @param foodId
     * @return void
     * @author zcq
     * @date 2018/10/17 14:53
     */
    void deletePlanDietFood(String planId, String foodMeal, String foodId);

    /**
     * 保存饮食原则
     *
     * @param diagnosisId
     * @return
     * @author zcq
     */
    String saveDiagnosisPoints(String diagnosisId);

    /**
     * 保存教育课程
     *
     * @param diagnosisId
     * @return
     * @author zcq
     */
    String saveDiagnosisCourse(String diagnosisId);

    /**
     * 创建干预方案
     *
     * @param intakeDetailList
     * @return
     * @author zcq
     */
    String saveIntervenePlanGroup(PregIntervenePlan plan, List<PregPlanIntakeDetail> intakeDetailList);

    /**
     * 重置膳食方案
     *
     * @param plan
     * @return
     * @author zcq
     */
    String resetIntervenePlan(PregIntervenePlan plan);

    /**
     * 保存膳食方案--摄入量明细
     *
     * @param plan
     * @param intakeDetailList
     * @return
     * @author zcq
     */
    String savePlanIntakeDetail(PregIntervenePlan plan, List<PregPlanIntakeDetail> intakeDetailList);

    /**
     * 保存膳食方案--食谱
     *
     * @param plan
     * @return
     * @author zcq
     */
    String savePlanDiet(PregIntervenePlan plan);

    /**
     * 保存膳食方案--课程
     *
     * @param diagnosisId
     * @param planId
     * @param pregType    1=基础课程，2=诊断课程
     * @author zcq
     */
    void savePlanCourse(String diagnosisId, String planId, String pregType);

    // -------------------------------------------【膳食方案】------------------------------------------

    // *******************************************【个人膳食方案】******************************************

    /**
     * 查询个人膳食模板
     *
     * @param userId
     * @return
     * @author zcq
     */
    List<PregUserIntakePojo> queryUserIntakeByUserId(String userId);

    /**
     * 获取个人膳食模板明细
     *
     * @param intakeId
     * @return
     * @author zcq
     */
    List<PregUserIntakeDetailPojo> queryUserIntakeDetailByIntakeId(String intakeId);

    /**
     * 保存个人膳食模板
     *
     * @param userIntake
     * @param intakeDetailList
     * @return
     * @author zcq
     */
    String saveUserIntake(PregUserIntake userIntake, List<PregUserIntakeDetail> intakeDetailList);

    /**
     * 删除个人膳食模板
     *
     * @param intakeId
     * @author zcq
     */
    void deletePersonIntake(String intakeId);

    /**
     * 校验跟人膳食模板名称是否可用
     *
     * @param intakeName
     * @return
     * @author zcq
     */
    Integer validatePersonIntakeName(String intakeName);

    // -------------------------------------------【个人膳食方案】------------------------------------------

    // *******************************************【课程方案】******************************************

    /**
     * 查询孕期课程
     *
     * @param condition
     * @return
     * @author zcq
     */
    List<PregCourseDetailPojo> queryPlanCourse(PregnancyCourseCondition condition);

    /**
     * 查询诊断项目课程
     *
     * @param diagnosisId
     * @return
     * @author zcq
     */
    List<PregCourseDetailPojo> queryDiseaseCourse(String diagnosisId);

    // -------------------------------------------【课程方案】------------------------------------------

    // *******************************************【干预计划--组合数据】******************************************

    /**
     * 查询膳食方案膳食明细
     *
     * @param planId
     * @return
     * @author zcq
     */
    List<PregPlanIntakeDetailPojo> queryPlanIntakeDetailByPlanId(String planId);

    /**
     * 查询干预方案展示信息
     *
     * @param diagnosisId
     * @return
     * @author zcq
     */
    PregIntervenePlanGroupPojo getIntervenePlanGroup(String diagnosisId);

    // -------------------------------------------【干预计划--组合数据】------------------------------------------

    // *******************************************【妊娠日记模板】******************************************

    /**
     * 分析妊娠日记pdf所需数据(随诊次数，体重变化)
     *
     * @param diagnosisId
     * @return
     * @author dhs
     */
    PregDiagnosisPojo analysisDiary(String diagnosisId);

    // -------------------------------------------【妊娠日记模板】------------------------------------------

    // *******************************************【营养病例--打印】******************************************

    /**
     * 检验pdf报告是否有内容
     *
     * @param diagnosisId archId
     * @return
     * @author dhs
     */
    Map<String, Boolean> validPdf(String diagnosisId, String archId);

    // -------------------------------------------【营养病例--打印】------------------------------------------

}
