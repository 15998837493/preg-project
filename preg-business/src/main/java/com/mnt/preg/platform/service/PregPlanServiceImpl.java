
package com.mnt.preg.platform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mnt.preg.platform.pojo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.dao.CustomerDAO;
import com.mnt.preg.customer.dao.PregCourseBookingDAO;
import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.examitem.condition.ExamResultRecordCondition;
import com.mnt.preg.examitem.dao.DiagnosisQuotaItemDAO;
import com.mnt.preg.examitem.dao.ExamResultRecordDAO;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.condition.preg.DietTemplateDetailCondition;
import com.mnt.preg.master.condition.preg.PregnancyCourseCondition;
import com.mnt.preg.master.dao.basic.ProductDAO;
import com.mnt.preg.master.dao.preg.PregCourseDAO;
import com.mnt.preg.master.dao.preg.PregDietTemplateDAO;
import com.mnt.preg.master.dao.preg.PregIntakeTypeDAO;
import com.mnt.preg.master.dao.preg.PregIntervenePointsDAO;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplateDetailPojo;
import com.mnt.preg.master.pojo.preg.PregIntervenePointsPojo;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.condition.IntervenePlanCondition;
import com.mnt.preg.platform.dao.PregArchivesDAO;
import com.mnt.preg.platform.dao.PregDiagnosisDAO;
import com.mnt.preg.platform.dao.PregDiagnosisExtenderDAO;
import com.mnt.preg.platform.dao.PregDiagnosisItemDAO;
import com.mnt.preg.platform.dao.PregPlanDAO;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.entity.PregIntervenePlan;
import com.mnt.preg.platform.entity.PregPlanCourse;
import com.mnt.preg.platform.entity.PregPlanDiet;
import com.mnt.preg.platform.entity.PregPlanIntakeDetail;
import com.mnt.preg.platform.entity.PregPlanPoints;
import com.mnt.preg.platform.entity.PregUserIntake;
import com.mnt.preg.platform.entity.PregUserIntakeDetail;

/**
 * 诊疗服务
 *
 * @author zcq
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2016-3-21 zcq 初版
 */
@Service
public class PregPlanServiceImpl extends BaseService implements PregPlanService {

    @Resource
    private PregDietTemplateDAO pregDietTemplateDAO;

    @Resource
    private PregDiagnosisDAO pregDiagnosisDAO;

    @Resource
    private PregPlanDAO pregPlanDAO;

    @Resource
    private PregDiagnosisExtenderDAO pregDiagnosisExtenderDAO;

    @Resource
    private PregArchivesDAO pregArchivesDAO;

    @Resource
    private CustomerDAO customerDAO;// 患者DAO

    @Resource
    private ProductDAO productDAO;

    @Resource
    private PregIntakeTypeDAO pregIntakeTypeDAO;

    @Resource
    private PregIntervenePointsDAO pregIntervenePointsDAO;

    @Resource
    private PregCourseDAO pregCourseDAO;

    @Resource
    private PregCourseBookingDAO pregCourseBookingDAO;

    @Resource
    private PregDiagnosisItemDAO pregDiagnosisItemDAO;

    @Resource
    private DiagnosisQuotaItemDAO diagnosisQuotaItemDAO;

    @Resource
    private ExamResultRecordDAO examResultRecordDAO;

    // ********************************************【膳食方案】******************************************

    @Override
    @Transactional(readOnly = true)
    public List<PregIntervenePlanPojo> queryIntervenePlan(IntervenePlanCondition condition) {
        return pregPlanDAO.queryIntervenePlan(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PregIntervenePlanPojo getIntervenePlan(String planId) {
        return pregPlanDAO.getTransformerBean(planId, PregIntervenePlan.class, PregIntervenePlanPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateIntervenePlan(PregIntervenePlan plan) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("planId", HQLSymbol.EQ.toString(), plan.getPlanId()));
        pregPlanDAO.updateHQL(plan, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteIntervenPlan(String planId) {
        pregPlanDAO.deleteIntervenePlan(planId);
    }

    @Override
    @Transactional(readOnly = true)
    public PregInitPlanGroupPojo getInitPlanGroup(String diagnosisId) {
        PregDiagnosisPojo diagnosisVo = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        PregInitPlanGroupPojo group = new PregInitPlanGroupPojo();
        // 历次方案
        IntervenePlanCondition planCondition = new IntervenePlanCondition();
        planCondition.setCustId(diagnosisVo.getDiagnosisCustomer());
        planCondition.setCreateUserId(this.getLoginUser().getUserPojo().getUserId());
        planCondition.setEndDate(JodaTimeTools.toDate(JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6)));
        group.setHistoryPlanList(pregPlanDAO.queryIntervenePlan(planCondition));
        // 摄入类型
        group.setIntakeTypeList(pregIntakeTypeDAO.queryIntakeType(null));
        // 过滤产品列表
        ProductCondition productCondition = new ProductCondition();
        group.setProductList(this.setProductElement(productCondition));
        // 设置食谱列表
        group.setDietList(pregDietTemplateDAO.queryDietTemplate(null));
        // 设置食物推荐
        group.setFoodRecommend(this.getParamValue("plan_remark"));

        return group;
    }

    @Override
    @Transactional(readOnly = true)
    public PregIntervenePlanPojo getIntervenePlanByDiagnosisId(String diagnosisId) {
        IntervenePlanCondition condition = new IntervenePlanCondition();
        condition.setDiagnosisId(diagnosisId);
        List<PregIntervenePlanPojo> list = pregPlanDAO.queryIntervenePlan(condition);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregPlanIntakeDetailPojo> getPlanIntakeDetailByDiagnosisId(String diagnosisId) {
        PregIntervenePlanPojo planVo = this.getIntervenePlanByDiagnosisId(diagnosisId);
        if (planVo == null) {
            return null;
        }
        return pregPlanDAO.queryPlanIntakeDetailByPlanId(planVo.getPlanId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregPlanDietPojo> queryPlanDietDetailsByPlanIdAndFoodDay(String planId, String foodDay) {
        return pregPlanDAO.queryPlanDietDetailsByPlanIdAndFoodDay(planId, foodDay);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addPlanDietDetail(PregPlanDiet planDiet) {
        return (String) pregPlanDAO.save(planDiet);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updatePlanDietDetail(PregPlanDiet planDiet) {
        pregPlanDAO.updateHQL(planDiet);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deletePlanDietFood(String planId, String foodMeal, String foodId) {
        pregPlanDAO.deletePlanDietFood(planId, foodMeal, foodId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveDiagnosisPoints(String diagnosisId) {
        PregDiagnosisPojo diagnosis = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        PregIntervenePlanPojo planPojo = pregPlanDAO.getIntervenePlanByDiagnosis(diagnosisId);
        String planId = "";
        if (planPojo == null) {
            PregIntervenePlan plan = new PregIntervenePlan();
            plan.setDiagnosisId(diagnosisId);
            plan.setStatus("3");// 执行状态：1=未完成，3=已完成
            plan.setUserId(this.getLoginUser().getUserPojo().getUserId());
            plan.setUserName(this.getLoginUser().getUserPojo().getUserName());
            plan.setCreateDate(new Date());
            planId = (String) pregPlanDAO.save(plan);
        } else {
            planId = planPojo.getPlanId();
        }

        // 删除旧数据
        pregPlanDAO.deletePlanPoints(planId);
        // 第一部分：保存饮食原则
        List<String> diseaseCodeList = new ArrayList<String>();
        String diagDiseaseStr = diagnosis.getDiagnosisDiseaseCodes();
        if (StringUtils.isNotEmpty(diagDiseaseStr)) {
            diseaseCodeList = Arrays.asList(diagDiseaseStr.split(","));
        }
        List<PregIntervenePointsPojo> pointList = pregIntervenePointsDAO.queryIntervenePointsByPlanDiseaseCodes(
                diseaseCodeList,
                "10", diagnosis.getDiagnosisPregnantStage());
        if (CollectionUtils.isNotEmpty(pointList)) {
            for (PregIntervenePointsPojo point : pointList) {
                PregPlanPoints planPoints = TransformerUtils.transformerProperties(PregPlanPoints.class, point);
                planPoints.setPlanId(planId);
                String pregStage = point.getPregStage();
                if (StringUtils.isNotEmpty(pregStage)) {
                    planPoints.setPointType("preg");// 孕期原则
                } else {
                    planPoints.setPointType("diagnosis");// 诊断原则
                }
                pregPlanDAO.save(planPoints);
            }
        }
        return planId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveDiagnosisCourse(String diagnosisId) {
        PregDiagnosisPojo diagnosis = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        PregIntervenePlanPojo planPojo = pregPlanDAO.getIntervenePlanByDiagnosis(diagnosisId);
        String planId = "";
        if (planPojo == null) {
            PregIntervenePlan plan = new PregIntervenePlan();
            plan.setDiagnosisId(diagnosisId);
            plan.setStatus("3");// 执行状态：1=未完成，3=已完成
            plan.setUserId(this.getLoginUser().getUserPojo().getUserId());
            plan.setUserName(this.getLoginUser().getUserPojo().getUserName());
            plan.setCreateDate(new Date());
            planId = (String) pregPlanDAO.save(plan);
        } else {
            planId = planPojo.getPlanId();
        }

        // 删除旧数据
        pregPlanDAO.deletePlanCourse(planId, "1");
        pregPlanDAO.deletePlanCourse(planId, "2");
        // 第二部分：保存基础课程
        String pregWeekday = diagnosis.getDiagnosisGestationalWeeks();
        if (StringUtils.isNotEmpty(pregWeekday)) {
            Integer week = Integer.valueOf(pregWeekday.split("\\+")[0]);
            PregnancyCourseCondition courseCondition = new PregnancyCourseCondition();
            courseCondition.setPregWeekBegin(week);
            courseCondition.setPregWeekEnd(week);
            List<PregCourseDetailPojo> pregCourseList = pregCourseDAO.queryPregnancyCourseDetail(courseCondition);
            if (CollectionUtils.isNotEmpty(pregCourseList)) {
                for (PregCourseDetailPojo course : pregCourseList) {
                    PregPlanCourse planCourse = TransformerUtils.transformerProperties(PregPlanCourse.class, course);
                    planCourse.setPlanId(planId);
                    planCourse.setPregType("1");
                    pregPlanDAO.save(planCourse);
                }
            }
        }
        // 第三部分：保存诊断课程
        List<String> diseaseCodeList = new ArrayList<String>();
        String diagDiseaseStr = diagnosis.getDiagnosisDiseaseCodes();
        if (StringUtils.isNotBlank(diagDiseaseStr)) {
            diseaseCodeList = Arrays.asList(diagDiseaseStr.split(","));
        }
        List<PregCourseDetailPojo> diseaseCourseList = pregIntervenePointsDAO.queryDisesaseCourse(diseaseCodeList);
        if (CollectionUtils.isNotEmpty(diseaseCourseList)) {
            for (PregCourseDetailPojo course : diseaseCourseList) {
                PregPlanCourse planCourse = TransformerUtils.transformerProperties(PregPlanCourse.class, course);
                planCourse.setPlanId(planId);
                planCourse.setPregType("2");
                pregPlanDAO.save(planCourse);
            }
        }
        return planId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveIntervenePlanGroup(PregIntervenePlan plan, List<PregPlanIntakeDetail> intakeDetailList) {
        // 保存干预方案主表信息
        String planId = plan.getPlanId();
        if (StringUtils.isEmpty(planId)) {
            plan.setStatus("3");// 执行状态：1=未完成，3=已完成
            plan.setUserId(this.getLoginUser().getUserPojo().getUserId());
            plan.setUserName(this.getLoginUser().getUserPojo().getUserName());
            plan.setCreateDate(new Date());
            planId = (String) pregPlanDAO.save(plan);
        } else {
            // 修改主表信息
            this.updateIntervenePlan(plan);
        }
        // 删除旧数据
        pregPlanDAO.deleteIntervenePlan(planId);
        // 第四部分：保存执行清单
        if (CollectionUtils.isNotEmpty(intakeDetailList)) {
            for (PregPlanIntakeDetail intakeDetail : intakeDetailList) {
                PregPlanIntakeDetail detail = TransformerUtils.transformerProperties(PregPlanIntakeDetail.class,
                        intakeDetail);
                detail.setPlanId(planId);
                pregPlanDAO.save(detail);
            }
        }
        // 第五部分：保存食谱
//        String dietId = plan.getDietId();
//        String foodDays = plan.getFoodDays();
//        if (StringUtils.isNotEmpty(dietId) && StringUtils.isNotEmpty(foodDays)) {
//            List<String> foodDayList = Arrays.asList(plan.getFoodDays().split(","));
//            DietTemplateDetailCondition condition = new DietTemplateDetailCondition();
//            condition.setDietId(plan.getDietId());
//            condition.setFoodDayList(foodDayList);
//            List<PregDietTemplateDetailPojo> list = pregDietTemplateDAO.queryDietTemplateDetail(condition);
//            if (CollectionUtils.isNotEmpty(list)) {
//                for (PregDietTemplateDetailPojo dietDetail : list) {
//                    PregPlanDiet planDiet = TransformerUtils.transformerProperties(PregPlanDiet.class, dietDetail);
//                    planDiet.setFoodMaterialAmount(this.getFoodMaterAmount(plan.getIntakeCaloric(), dietDetail));
//                    planDiet.setPlanId(planId);
//                    pregPlanDAO.save(planDiet);
//                }
//            }
//        }
        return planId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String resetIntervenePlan(PregIntervenePlan plan) {
        String diagnosisId = plan.getDiagnosisId();
        PregDiagnosisPojo diagnosis = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        // 保存干预方案主表信息
        String planId = plan.getPlanId();
        if (StringUtils.isEmpty(planId)) {
            plan.setStatus("3");// 执行状态：1=未完成，3=已完成
            plan.setUserId(this.getLoginUser().getUserPojo().getUserId());
            plan.setUserName(this.getLoginUser().getUserPojo().getUserName());
            plan.setCreateDate(new Date());
            planId = (String) pregPlanDAO.save(plan);
        } else {
            // 修改主表信息
            this.updateIntervenePlan(plan);
        }
        // 删除饮食原则
        pregPlanDAO.deletePlanPoints(planId);
        // 删除教育课程
        pregPlanDAO.deletePlanCourse(planId, "1");// 基础课程
        pregPlanDAO.deletePlanCourse(planId, "2");// 诊断课程
        // 第一部分：保存饮食原则
        List<String> diseaseCodeList = new ArrayList<String>();
        String diagDiseaseStr = diagnosis.getDiagnosisDiseaseCodes();
        if (StringUtils.isNotBlank(diagDiseaseStr)) {
            diseaseCodeList = Arrays.asList(diagDiseaseStr.split(","));
        }
        List<PregIntervenePointsPojo> pointList = pregIntervenePointsDAO.queryIntervenePointsByPlanDiseaseCodes(
                diseaseCodeList, "10", diagnosis.getDiagnosisPregnantStage());
        if (CollectionUtils.isNotEmpty(pointList)) {
            for (PregIntervenePointsPojo point : pointList) {
                PregPlanPoints planPoints = TransformerUtils.transformerProperties(PregPlanPoints.class, point);
                planPoints.setPlanId(planId);
                String pregStage = point.getPregStage();
                if (StringUtils.isNotEmpty(pregStage)) {
                    planPoints.setPointType("preg");// 孕期原则
                } else {
                    planPoints.setPointType("diagnosis");// 诊断原则
                }
                pregPlanDAO.save(planPoints);
            }
        }
        // 第二部分：保存基础课程
        String pregWeekday = diagnosis.getDiagnosisGestationalWeeks();
        if (StringUtils.isNotEmpty(pregWeekday)) {
            Integer week = Integer.valueOf(pregWeekday.split("\\+")[0]);
            PregnancyCourseCondition courseCondition = new PregnancyCourseCondition();
            courseCondition.setPregWeekBegin(week);
            courseCondition.setPregWeekEnd(week);
            List<PregCourseDetailPojo> pregCourseList = pregCourseDAO.queryPregnancyCourseDetail(courseCondition);
            if (CollectionUtils.isNotEmpty(pregCourseList)) {
                for (PregCourseDetailPojo course : pregCourseList) {
                    PregPlanCourse planCourse = TransformerUtils.transformerProperties(PregPlanCourse.class, course);
                    planCourse.setPlanId(planId);
                    planCourse.setPregType("1");
                    pregPlanDAO.save(planCourse);
                }
            }
        }
        // 第三部分：保存诊断课程
        List<PregCourseDetailPojo> diseaseCourseList = pregIntervenePointsDAO.queryDisesaseCourse(diseaseCodeList);
        if (CollectionUtils.isNotEmpty(diseaseCourseList)) {
            for (PregCourseDetailPojo course : diseaseCourseList) {
                PregPlanCourse planCourse = TransformerUtils.transformerProperties(PregPlanCourse.class, course);
                planCourse.setPlanId(planId);
                planCourse.setPregType("2");
                pregPlanDAO.save(planCourse);
            }
        }
        return planId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String savePlanIntakeDetail(PregIntervenePlan plan, List<PregPlanIntakeDetail> intakeDetailList) {
        // 保存干预方案主表信息
        String planId = plan.getPlanId();
        if (StringUtils.isEmpty(planId)) {
            plan.setStatus("3");// 执行状态：1=未完成，3=已完成
            plan.setUserId(this.getLoginUser().getUserPojo().getUserId());
            plan.setUserName(this.getLoginUser().getUserPojo().getUserName());
            plan.setCreateDate(new Date());
            planId = (String) pregPlanDAO.save(plan);
        } else {
            // 修改主表信息
            this.updateIntervenePlan(plan);
        }
        // 删除膳食方案明细
        pregPlanDAO.deletePlanIntakeDetail(planId);
        // 保存执行清单
        if (CollectionUtils.isNotEmpty(intakeDetailList)) {
            for (PregPlanIntakeDetail intakeDetail : intakeDetailList) {
                PregPlanIntakeDetail detail = TransformerUtils.transformerProperties(PregPlanIntakeDetail.class,
                        intakeDetail);
                detail.setPlanId(planId);
                pregPlanDAO.save(detail);
            }
        }
        return planId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String savePlanDiet(PregIntervenePlan plan) {
        // 保存干预方案主表信息
        String planId = plan.getPlanId();
        if (StringUtils.isEmpty(planId)) {
            plan.setStatus("3");// 执行状态：1=未完成，3=已完成
            plan.setUserId(this.getLoginUser().getUserPojo().getUserId());
            plan.setUserName(this.getLoginUser().getUserPojo().getUserName());
            plan.setCreateDate(new Date());
            planId = (String) pregPlanDAO.save(plan);
        } else {
            // 修改主表信息
            this.updateIntervenePlan(plan);
        }
        // 删除膳食方案食谱
        pregPlanDAO.deletePlanDiet(planId);
        // 保存膳食方案食谱
        String dietId = plan.getDietId();
        String foodDays = plan.getFoodDays();
        if (StringUtils.isNotEmpty(dietId) && StringUtils.isNotEmpty(foodDays)) {
            List<String> foodDayList = Arrays.asList(plan.getFoodDays().split(","));
            DietTemplateDetailCondition condition = new DietTemplateDetailCondition();
            condition.setDietId(plan.getDietId());
            condition.setFoodDayList(foodDayList);
            List<PregDietTemplateDetailPojo> list = pregDietTemplateDAO.queryDietTemplateDetail(condition);
            PregIntervenePlanPojo planVo = this.getIntervenePlan(planId);
            if (CollectionUtils.isNotEmpty(list)) {
                for (PregDietTemplateDetailPojo dietDetail : list) {
                    PregPlanDiet planDiet = TransformerUtils.transformerProperties(PregPlanDiet.class, dietDetail);
                    planDiet.setFoodMaterialAmount(this.getFoodMaterAmount(planVo.getIntakeCaloric(), dietDetail));
                    planDiet.setPlanId(planId);
                    pregPlanDAO.save(planDiet);
                }
            }
        }
        return planId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void savePlanCourse(String diagnosisId, String planId, String pregType) {
        if (StringUtils.isNotEmpty(diagnosisId) && StringUtils.isNotEmpty(planId)) {
            PregDiagnosisPojo diagnosis = pregDiagnosisDAO.getDiagnosis(diagnosisId);
            List<String> diseaseCodeList = new ArrayList<String>();
            String diagDiseaseStr = diagnosis.getDiagnosisDiseaseCodes();
            if (StringUtils.isNotEmpty(diagDiseaseStr)) {
                diseaseCodeList = Arrays.asList(diagDiseaseStr.split(","));
            }
            // 基础课程
            if ("1".equals(pregType)) {
                // 删除教育课程
                pregPlanDAO.deletePlanCourse(planId, "1");
                // 保存基础课程
                String pregWeekday = diagnosis.getDiagnosisGestationalWeeks();
                if (StringUtils.isNotEmpty(pregWeekday)) {
                    Integer week = Integer.valueOf(pregWeekday.split("\\+")[0]);
                    PregnancyCourseCondition courseCondition = new PregnancyCourseCondition();
                    courseCondition.setPregWeekBegin(week);
                    courseCondition.setPregWeekEnd(week);
                    List<PregCourseDetailPojo> pregCourseList = pregCourseDAO
                            .queryPregnancyCourseDetail(courseCondition);
                    if (CollectionUtils.isNotEmpty(pregCourseList)) {
                        for (PregCourseDetailPojo course : pregCourseList) {
                            PregPlanCourse planCourse = TransformerUtils
                                    .transformerProperties(PregPlanCourse.class, course);
                            planCourse.setPlanId(planId);
                            planCourse.setPregType("1");
                            pregPlanDAO.save(planCourse);
                        }
                    }
                }
            }
            // 诊断课程
            if ("2".equals(pregType)) {
                // 删除诊断课程
                pregPlanDAO.deletePlanCourse(planId, "2");
                // 保存诊断课程
                List<PregCourseDetailPojo> diseaseCourseList = pregIntervenePointsDAO
                        .queryDisesaseCourse(diseaseCodeList);
                if (CollectionUtils.isNotEmpty(diseaseCourseList)) {
                    for (PregCourseDetailPojo course : diseaseCourseList) {
                        PregPlanCourse planCourse = TransformerUtils
                                .transformerProperties(PregPlanCourse.class, course);
                        planCourse.setPlanId(planId);
                        planCourse.setPregType("2");
                        pregPlanDAO.save(planCourse);
                    }
                }
            }
            // 删除饮食原则
            pregPlanDAO.deletePlanPoints(planId);
            // 保存饮食原则
            List<PregIntervenePointsPojo> pointList = pregIntervenePointsDAO.queryIntervenePointsByPlanDiseaseCodes(
                    diseaseCodeList, "10", diagnosis.getDiagnosisPregnantStage());
            if (CollectionUtils.isNotEmpty(pointList)) {
                for (PregIntervenePointsPojo point : pointList) {
                    PregPlanPoints planPoints = TransformerUtils.transformerProperties(PregPlanPoints.class, point);
                    planPoints.setPlanId(planId);
                    String pregStage = point.getPregStage();
                    if (StringUtils.isNotEmpty(pregStage)) {
                        planPoints.setPointType("preg");// 孕期原则
                    } else {
                        planPoints.setPointType("diagnosis");// 诊断原则
                    }
                    pregPlanDAO.save(planPoints);
                }
            }
        }
    }

    // -------------------------------------------【膳食方案】------------------------------------------

    // *******************************************【个人膳食方案】******************************************
    @Override
    @Transactional(readOnly = true)
    public List<PregUserIntakePojo> queryUserIntakeByUserId(String userId) {
        return pregPlanDAO.queryUserIntakeByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregUserIntakeDetailPojo> queryUserIntakeDetailByIntakeId(String intakeId) {
        return pregPlanDAO.queryUserIntakeDetailByIntakeId(intakeId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveUserIntake(PregUserIntake userIntake, List<PregUserIntakeDetail> intakeDetailList) {
        String intakeId = userIntake.getIntakeId();
        // 保存个人膳食模板主表
        if (StringUtils.isNotEmpty(intakeId)) {
            List<HQLConditionParam> updateParams = new ArrayList<HQLConditionParam>();
            updateParams.add(new HQLConditionParam("intakeId", HQLSymbol.EQ.toString(), intakeId));
            pregPlanDAO.updateHQL(userIntake, updateParams);
        } else {
            intakeId = (String) pregPlanDAO.save(userIntake);
        }
        // 删除个人模板明细旧数据
        pregPlanDAO.deleteUserIntakeDetail(intakeId);
        // 保存个人膳食模板明细表
        if (CollectionUtils.isNotEmpty(intakeDetailList)) {
            for (PregUserIntakeDetail userIntakeDetail : intakeDetailList) {
                userIntakeDetail.setIntakeId(intakeId);
                pregPlanDAO.save(userIntakeDetail);
            }
        }

        return intakeId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deletePersonIntake(String intakeId) {
        pregPlanDAO.deletePersonIntake(intakeId);
        pregPlanDAO.deleteUserIntakeDetail(intakeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer validatePersonIntakeName(String intakeName) {
        return pregPlanDAO.validatePersonIntakeName(intakeName);
    }

    // -------------------------------------------【个人膳食方案】------------------------------------------

    // *******************************************【课程方案】******************************************
    @Override
    @Transactional(readOnly = true)
    public List<PregCourseDetailPojo> queryPlanCourse(PregnancyCourseCondition condition) {
        return pregCourseDAO.queryPregnancyCourseDetail(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregCourseDetailPojo> queryDiseaseCourse(String diagnosisId) {
        PregDiagnosisPojo diagnosis = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        List<String> diseaseCodeList = new ArrayList<String>();
        String diagDiseaseStr = diagnosis.getDiagnosisDiseaseCodes();
        if (StringUtils.isNotEmpty(diagDiseaseStr)) {
            diseaseCodeList.addAll(Arrays.asList(diagDiseaseStr.split(",")));
        }
        return pregIntervenePointsDAO.queryDisesaseCourse(diseaseCodeList);
    }

    // -------------------------------------------【课程方案】------------------------------------------

    // *******************************************【干预计划--组合数据】******************************************

    @Override
    @Transactional(readOnly = true)
    public List<PregPlanIntakeDetailPojo> queryPlanIntakeDetailByPlanId(String planId) {
        return pregPlanDAO.queryPlanIntakeDetailByPlanId(planId);
    }

    @Override
    @Transactional(readOnly = true)
    public PregIntervenePlanGroupPojo getIntervenePlanGroup(String diagnosisId) {
        // 获取接诊记录信息
        PregDiagnosisPojo diagnosisVo = pregPlanDAO.getTransformerBean(diagnosisId, PregDiagnosis.class,
                PregDiagnosisPojo.class);
        PregIntervenePlanGroupPojo planGroupVo = new PregIntervenePlanGroupPojo();
        planGroupVo.setDiagnosis(diagnosisVo);
        // 获取干预方案主表信息
        PregIntervenePlanPojo planVo = getIntervenePlanByDiagnosisId(diagnosisId);
        if (planVo != null) {
            String planId = planVo.getPlanId();
            planGroupVo.setPlanVo(planVo);
            // 摄入量模版明细
            planGroupVo.setPlanIntakeDetailList(pregPlanDAO.queryPlanIntakeDetailByPlanId(planId));
            // 一周食谱信息
            planGroupVo.setPlanDietList(pregPlanDAO.queryPlanDietDetailsByPlanId(planId));
            // 饮食原则
            planGroupVo.setPlanPointList(pregPlanDAO.queryPlanPointsByPlanId(planId));
            // 诊断课程
            planGroupVo.setPlanDiseaseCourseList(pregPlanDAO.queryPlanCourseByPlanIdAndPregType(planId, "2"));
            // 教育课程
            planGroupVo.setPlanPregCourseList(pregPlanDAO.queryPlanCourseByPlanIdAndPregType(planId, "1"));
        }
        // 获取处方
        planGroupVo.setExtenderList(pregDiagnosisExtenderDAO.queryDiagnosisPrescriptionByDiagnosisId(diagnosisId));
        // 获取干预会员信息
        planGroupVo.setCustomerVo(customerDAO.getCustomer(diagnosisVo.getDiagnosisCustomer()));
        // 孕期建档信息
        planGroupVo.setPregArchivesVo(pregArchivesDAO.getLastPregnancyArchive(diagnosisVo.getDiagnosisCustomer()));

        return planGroupVo;
    }

    // -------------------------------------------【干预计划--组合数据】------------------------------------------

    // **********************************************【妊娠日记模板】**********************************************

    @Override
    @Transactional(readOnly = true)
    public PregDiagnosisPojo analysisDiary(String diagnosisId) {
        String change = "";// 体重变化
        PregDiagnosisPojo pregDiagnosisPojo = pregDiagnosisDAO.getDiagnosis(diagnosisId);// 本次接诊信息
        String cusId = pregDiagnosisPojo.getDiagnosisCustomer();
        DiagnosisCondition diagnosisCondition = new DiagnosisCondition();
        diagnosisCondition.setDiagnosisStatus(2);
        diagnosisCondition.setDiagnosisCustomer(cusId);
        List<PregDiagnosisPojo> list = pregDiagnosisDAO.queryDiagnosis(diagnosisCondition);
        // 按接诊日期正序排序
        for (int x = 0; x < list.size() - 1; x++) {
            for (int y = 0; y < list.size() - 1 - x; y++) {
                int count = list.get(y).getDiagnosisDate().compareTo(list.get(y + 1).getDiagnosisDate());
                if (count > 0) {
                    PregDiagnosisPojo temp = list.get(y);
                    list.set(y, list.get(y + 1));
                    list.set(y + 1, temp);
                }
            }
        }
        if (list.size() > 1) {
            if (list.get(list.size() - 1).getDiagnosisCustWeight() != null
                    && pregDiagnosisPojo.getDiagnosisCustWeight() != null) {
                BigDecimal lastWeight = new BigDecimal(0);// 上一次体重
                Date lastDate = null;// 上一次接诊日期
                // 如果最后一个是今天的体重，那上一次体重就是倒数第二个。
                if (pregDiagnosisPojo.getDiagnosisCustWeight().equals(
                        list.get(list.size() - 1).getDiagnosisCustWeight())) {
                    if (list.get(list.size() - 2).getDiagnosisCustWeight() != null) {
                        lastWeight = list.get(list.size() - 2).getDiagnosisCustWeight();
                        lastDate = list.get(list.size() - 2).getDiagnosisDate();
                    }
                } else {// 如果最后一个不是，那上一次体重就是最后一个
                    lastWeight = list.get(list.size() - 1).getDiagnosisCustWeight();
                    lastDate = list.get(list.size() - 1).getDiagnosisDate();
                }
                if (lastDate != null) {
                    BigDecimal change_weight = pregDiagnosisPojo.getDiagnosisCustWeight().subtract(lastWeight);
                    int changeDays = this.differentDays(lastDate, pregDiagnosisPojo.getDiagnosisDate());// 两个日期相差天数
                    if (changeDays > 0) {// 一天不可能接诊两次，只能接诊一次，一周按照7天算
                        String one = "";// 第一部分：+15kg
                        String two = "";// 第二部分：\1W
                        String three = "";// 第三部分：+1(右角标)
                        if (change_weight.compareTo(BigDecimal.ZERO) > 0) {// 如果体重大于0要给加号
                            one = "+" + change_weight;
                        } else {
                            one = change_weight.toString();
                        }
                        if (changeDays >= 7) {// 相差天数大于等于7天
                            two = String.valueOf(changeDays / 7);
                            three = "+" + String.valueOf(changeDays % 7);
                        } else {// 相差天数小于7天
                            two = "0";
                            three = "+" + changeDays;
                        }
                        change = one + "kg\\" + two + "W" + three;
                    }

                }
            }
        }
        pregDiagnosisPojo.setDiagnosisRiseYunqi(String.valueOf(list.size()));// 查询接诊次数（已过滤预约的数据）为了传参,借用字段
        pregDiagnosisPojo.setDiagnosisRiseWeek(change);// 体重变化（为了传参,借用字段 ）
        return pregDiagnosisPojo;
    }

    // -------------------------------------------【妊娠日记模板】------------------------------------------

    // **********************************************【营养病例--打印】**********************************************

    @Override
    @Transactional(readOnly = true)
    public Map<String, Boolean> validPdf(String diagnosisId, String archId) {
        // 返回一个map<code,boolean>来表示各个pdf是否有内容：false(没内容)/true(有内容)
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        // 营养制剂处方（user_diagnosis_prescription）
        List<DiagnosisPrescriptionPojo> extenderList = pregDiagnosisExtenderDAO
                .queryDiagnosisPrescriptionByDiagnosisId(diagnosisId);
        if (CollectionUtils.isNotEmpty(extenderList)) {
            map.put("P03001", true);
        } else {
            map.put("P03001", false);
        }

        // 饮食原则（user_plan_points）
        IntervenePlanCondition intervenePlanCondition = new IntervenePlanCondition();
        intervenePlanCondition.setDiagnosisId(diagnosisId);
        List<PregIntervenePlanPojo> intervenePlanList = pregPlanDAO.queryIntervenePlan(intervenePlanCondition);
        if (CollectionUtils.isNotEmpty(intervenePlanList)) {
            if (pregPlanDAO.queryPlanPointsByPlanId(intervenePlanList.get(0).getPlanId()).size() > 0) {
                map.put("P03002", true);
            } else {
                map.put("P03002", false);
            }
        } else {
            map.put("P03002", false);
        }

        // 食物选择（user_plan_intake_detail）
        if (CollectionUtils.isNotEmpty(intervenePlanList)) {
            if (pregPlanDAO.queryPlanIntakeDetailByPlanId(intervenePlanList.get(0).getPlanId()).size() > 0) {
                map.put("P03003", true);
            } else {
                map.put("P03003", false);
            }
        } else {
            map.put("P03003", false);
        }

        // 餐次分配（user_plan）
        if (CollectionUtils.isNotEmpty(intervenePlanList)) {
            map.put("P03004", true);
        } else {
            map.put("P03004", false);
        }

        // 食谱举例（user_plan_diet）
        if (CollectionUtils.isNotEmpty(intervenePlanList)) {
            if (pregPlanDAO.queryPlanDietDetailsByPlanId(intervenePlanList.get(0).getPlanId()).size() > 0) {
                map.put("P03005", true);
            } else {
                map.put("P03005", false);
            }
        } else {
            map.put("P03005", false);
        }

        // 复检复查提醒(user_diagnosis user_diagnosis_quota_item)
        DiagnosisBookingCondition diagnosisBookingCondition = new DiagnosisBookingCondition();
        diagnosisBookingCondition.setDiagnosisId(diagnosisId);
        List<DiagnosisBookingPojo> diagnosisBookingPojoList = pregDiagnosisDAO
                .queryDiagnosisBookings(diagnosisBookingCondition);
        DiagnosisQuotaItemCondition diagnosisQuotaItemCondition = new DiagnosisQuotaItemCondition();
        diagnosisQuotaItemCondition.setDiagnosisId(diagnosisId);
        List<DiagnosisQuotaItemVo> diagnosisQuotaItemList = diagnosisQuotaItemDAO
                .queryDiagnosisQuotaItem(diagnosisQuotaItemCondition);// 辅助检查项目
        if (CollectionUtils.isNotEmpty(diagnosisBookingPojoList) || CollectionUtils.isNotEmpty(diagnosisQuotaItemList)) {
            map.put("P03007", true);
        } else {
            map.put("P03007", false);
        }

        // 教育课程（user_plan_course）
        if (CollectionUtils.isNotEmpty(intervenePlanList)) {
            if (pregPlanDAO.queryPlanCourseByPlanIdAndPregType(intervenePlanList.get(0).getPlanId(), "0").size() > 0) {
                map.put("P03006", true);
            } else {
                map.put("P03006", false);
            }
        } else {
            map.put("P03006", false);
        }

        // 课程预约提醒（user_course_booking）
        PregDiagnosisPojo diagnosisPojo = pregDiagnosisDAO.getDiagnosis(diagnosisId);
        PregCourseBooking pregCourseBooking = new PregCourseBooking();
        pregCourseBooking.setArchivesId(archId);
        List<PregCourseBookingPojo> pregCourseBookingList = pregCourseBookingDAO.queryCourseBooking(pregCourseBooking);
        if (CollectionUtils.isNotEmpty(pregCourseBookingList)) {
            boolean courseFlag = false;
            for (PregCourseBookingPojo pojo : pregCourseBookingList) {
                if (pojo.getBookingDate().compareTo(diagnosisPojo.getDiagnosisDate()) == 1) {
                    courseFlag = true;
                    break;
                }
            }
            map.put("P03008", courseFlag);
        } else {
            map.put("P03008", false);
        }

        // 膳食及生活方式评价（user_diagnosis_inspect）
        DiagnosisItemsCondition foodCondition = new DiagnosisItemsCondition();
        foodCondition.setDiagnosisId(diagnosisId);
        foodCondition.setInspectStatus(3);// 3为已做完的pdf状态
        foodCondition.setInspectCode("B00002");
        List<PregDiagnosisItemsVo> foodList = pregDiagnosisItemDAO.queryDiagnosisItem(foodCondition);
        if (CollectionUtils.isNotEmpty(foodList)) {
            map.put("P02001", true);
        } else {
            map.put("P02001", false);
        }

        // 人体成分（user_diagnosis_inspect）
        DiagnosisItemsCondition personCondition = new DiagnosisItemsCondition();
        personCondition.setDiagnosisId(diagnosisId);
        personCondition.setInspectCode("B00003");
        List<PregDiagnosisItemsVo> personList = pregDiagnosisItemDAO.queryDiagnosisItem(personCondition);
        if (CollectionUtils.isNotEmpty(personList)) {
            if (personList.get(0).getInspectStatus() == 3) {
                map.put("P02002", true);
            } else {
                map.put("P02002", false);
            }
        } else {
            map.put("P02002", null);
        }

        // 营养素安全剂量评估（user_diagnosis_inspect）
        DiagnosisItemsCondition extenderAssessCondition = new DiagnosisItemsCondition();
        extenderAssessCondition.setDiagnosisId(diagnosisId);
        extenderAssessCondition.setInspectStatus(3);// 3为已做完的pdf状态
        extenderAssessCondition.setInspectCode("B00004");
        List<PregDiagnosisItemsVo> extenderAssessList = pregDiagnosisItemDAO
                .queryDiagnosisItem(extenderAssessCondition);
        if (CollectionUtils.isNotEmpty(extenderAssessList)) {
            map.put("P02003", true);
        } else {
            map.put("P02003", false);
        }

        // 快速营养调查问卷（cus_result_nutritious）
        DiagnosisItemsCondition condition = new DiagnosisItemsCondition();
        condition.setDiagnosisId(diagnosisId);
        condition.setInspectStatus(3);// 3为已做完的pdf状态
        condition.setInspectCode("B00006");
        List<PregDiagnosisItemsVo> inspectItemsVos = pregDiagnosisItemDAO.queryDiagnosisItem(condition);
        if (CollectionUtils.isEmpty(inspectItemsVos) || StringUtils.isEmpty(inspectItemsVos.get(0).getResultCode())) {
            map.put("P02004", false);
        } else {
            PregDiagnosisItemsVo diagnosisItem = inspectItemsVos.get(0);
            ExamResultRecordCondition condition2 = new ExamResultRecordCondition();
            condition2.setExamCode(diagnosisItem.getResultCode());
            condition2.setExamCategory("B00006");
            List<ExamResultRecordPojo> list = examResultRecordDAO.queryExamResultRecord(condition2);
            if (CollectionUtils.isNotEmpty(list)) {
                map.put("P02004", true);
            } else {
                map.put("P02004", false);
            }
        }

        // 24小时膳食回顾（user_diagnosis_inspect）
        DiagnosisItemsCondition deitCondition = new DiagnosisItemsCondition();
        deitCondition.setDiagnosisId(diagnosisId);
        deitCondition.setInspectStatus(3);// 3为已做完的pdf状态
        deitCondition.setInspectCode("B00005");
        List<PregDiagnosisItemsVo> deitList = pregDiagnosisItemDAO.queryDiagnosisItem(deitCondition);
        if (CollectionUtils.isNotEmpty(deitList)) {
            map.put("P02005", true);
        } else {
            map.put("P02005", false);
        }

        // 膳食频率调查（user_diagnosis_inspect）
        DiagnosisItemsCondition frequencyCondition = new DiagnosisItemsCondition();
        frequencyCondition.setDiagnosisId(diagnosisId);
        frequencyCondition.setInspectStatus(3);// 3为已做完的pdf状态
        frequencyCondition.setInspectCode("B00008");
        List<PregDiagnosisItemsVo> frequencyList = pregDiagnosisItemDAO.queryDiagnosisItem(frequencyCondition);
        if (CollectionUtils.isNotEmpty(frequencyList)) {
            map.put("P02006", true);
        } else {
            map.put("P02006", false);
        }

        // 孕期生活方式调查（user_diagnosis_inspect）
        DiagnosisItemsCondition lifrStyleCondition = new DiagnosisItemsCondition();
        lifrStyleCondition.setDiagnosisId(diagnosisId);
        lifrStyleCondition.setInspectStatus(3);// 3为已做完的pdf状态
        lifrStyleCondition.setInspectCode("B00007");
        List<PregDiagnosisItemsVo> lifrStyleList = pregDiagnosisItemDAO.queryDiagnosisItem(lifrStyleCondition);
        if (CollectionUtils.isNotEmpty(lifrStyleList)) {
            map.put("P02007", true);
        } else {
            map.put("P02007", false);
        }

        return map;
    }

    // -------------------------------------------【营养病例--打印】------------------------------------------

    // **********************************************【自定义方法】**********************************************

    /**
     * 根据热量获取食物成分重量
     *
     * @param energyLevel
     * @param dietDetail
     * @return
     * @author zcq
     */
    private String getFoodMaterAmount(String energyLevel, PregDietTemplateDetailPojo dietDetail) {
        String result = "";
        if (StringUtils.isEmpty(energyLevel)) {
            return result;
        }

        if (dietDetail.getFoodMaterialAmount() != null) {
            result = String.valueOf(dietDetail.getFoodMaterialAmount());
        }
        return result;
    }

    /**
     * 为产品设置元素
     *
     * @param condition
     * @return
     * @author zcq
     */
    private List<ProductPojo> setProductElement(ProductCondition condition) {
        List<ProductPojo> productList = productDAO.queryProduct(condition);
        for (ProductPojo vo : productList) {
            List<NutrientAmountPojo> elementList = productDAO.queryProductElementByProductId(vo.getProductId());
            vo.setProductElementList(elementList);
            // 获取三大营养素及热量
            for (NutrientAmountPojo eleVo : elementList) {
                BigDecimal dosage = eleVo.getNutrientDosage();
                String nutrientId = eleVo.getNutrientId();
                // 能量
                if ("E00001".equals(nutrientId)) {
                    vo.setUnitEnergy(dosage);
                }
                // 蛋白质
                if ("E00002".equals(nutrientId)) {
                    vo.setUnitProtein(dosage);
                }
                // 脂肪
                if ("E00003".equals(nutrientId)) {
                    vo.setUnitFat(dosage);
                }
                // 碳水化合物
                if ("E00004".equals(nutrientId)) {
                    vo.setUnitCbr(dosage);
                }
            }
        }
        return productList;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     * @author dhs
     */
    private int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) // 同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
                {
                    timeDistance += 366;
                } else // 不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else // 不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }
    // ----------------------------------------------【自定义方法】----------------------------------------------

}
