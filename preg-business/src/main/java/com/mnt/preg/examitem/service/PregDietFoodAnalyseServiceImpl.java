
package com.mnt.preg.examitem.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.examitem.dao.PregDietFoodAnalysisDAO;
import com.mnt.preg.examitem.dao.PregDietFoodRecordDAO;
import com.mnt.preg.examitem.pojo.PregDietAnalysePojo;
import com.mnt.preg.examitem.util.DietaryReviewPregnancyDRIs;
import com.mnt.preg.examitem.util.FoodScoreUtil;
import com.mnt.preg.examitem.util.PregnancyDRIs;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.util.FoodsFormulaUtil;

/**
 * 膳食分析报告事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-14 zcq 初版
 */
@Service
public class PregDietFoodAnalyseServiceImpl extends BaseService implements PregDietFoodAnalyseService {

    @Resource
    private PregDietFoodRecordDAO pregDietFoodRecordDAO;// 膳食评估记录DAO

    @Resource
    private PregDietFoodAnalysisDAO pregDietFoodAnalysisDAO;// 膳食评估报告DAO

    @Override
    @Transactional(readOnly = true)
    public PregDietAnalysePojo getElementSum(String foodRecordId) {
        PregDietAnalysePojo dietReportVo = new PregDietAnalysePojo();
        List<PregDietAnalysePojo> dietReportList = pregDietFoodAnalysisDAO.getFoodElementSum(foodRecordId);
        if (CollectionUtils.isNotEmpty(dietReportList)) {
            BigDecimal zero = BigDecimal.ZERO;

            dietReportVo = dietReportList.get(0);
            BigDecimal foodFat = BigDecimal.valueOf(Double.valueOf(dietReportVo.getFoodFat()));
            BigDecimal foodProtid = dietReportVo.getFoodProtid();
            BigDecimal foodCbr = BigDecimal.valueOf(Double.valueOf(dietReportVo.getFoodCbr()));
            // 计算三大营养素占比
            BigDecimal foodEnergy = BigDecimal.ZERO;
            foodEnergy = dietReportVo.getFoodEnergy();
            BigDecimal sum = foodFat.multiply(new BigDecimal(9)).add(foodProtid.multiply(new BigDecimal(4)))
                    .add(foodCbr.multiply(new BigDecimal(4)));
            BigDecimal foodFatP = foodFat.multiply(new BigDecimal(9)).divide(sum, 3, BigDecimal.ROUND_HALF_UP);
            BigDecimal foodProtidP = foodProtid.multiply(new BigDecimal(4))
                    .divide(sum, 3, BigDecimal.ROUND_HALF_UP);

            BigDecimal hundred = new BigDecimal(100);
            BigDecimal fatP = zero;
            BigDecimal protidP = zero;
            BigDecimal cbrP = zero;

            fatP = foodFatP.multiply(hundred);
            protidP = foodProtidP.multiply(hundred);
            cbrP = hundred.subtract(fatP.add(protidP));
            // 设置实际摄入能量和三大营养素的各自占比（脂肪，蛋白质，碳水化合物）
            dietReportVo.setFoodEnergy(foodEnergy.setScale(2, BigDecimal.ROUND_HALF_UP));
            dietReportVo.setFoodFatPercent(fatP.setScale(1, BigDecimal.ROUND_HALF_UP));
            dietReportVo.setFoodProtidPercent(protidP.setScale(1, BigDecimal.ROUND_HALF_UP));
            dietReportVo.setFoodCbrPercent(cbrP.setScale(1, BigDecimal.ROUND_HALF_UP));
        }

        return dietReportVo;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDietAnalysePojo getEachMealEnergy(String foodRecordId) {
        PregDietAnalysePojo dietReportVo = new PregDietAnalysePojo();
        List<PregDietAnalysePojo> mealEnergyList = pregDietFoodAnalysisDAO.getEachMealEnergy(foodRecordId);
        if (null != mealEnergyList) {
            // 餐次供能量
            BigDecimal zero = BigDecimal.ZERO;
            BigDecimal energySum = zero;
            BigDecimal breakEnergy = zero;
            BigDecimal breakAddEnergy = zero;
            BigDecimal lunchEnergy = zero;
            BigDecimal lunchAddEnergy = zero;
            BigDecimal supperEnergy = zero;
            BigDecimal supperAddEnergy = zero;
            // 设置每餐次的能量，并计算总能量值
            for (PregDietAnalysePojo energy : mealEnergyList) {
                String mealsId = energy.getMealsId();
                BigDecimal perEnergy = energy.getFoodEnergy();
                energySum = energySum.add(perEnergy);
                if (FoodScoreUtil.BREAKFAST_MEALS_CODE.equals(mealsId)) {
                    breakEnergy = perEnergy;
                } else if (FoodScoreUtil.MORNING_EXTRA_MEALS_CODE.equals(mealsId)) {
                    breakAddEnergy = perEnergy;
                } else if (FoodScoreUtil.LUNCH_MEALS_CODE.equals(mealsId)) {
                    lunchEnergy = perEnergy;
                } else if (FoodScoreUtil.AFTERNOON_EXTRA_MEALS_CODE.equals(mealsId)) {
                    lunchAddEnergy = perEnergy;
                } else if (FoodScoreUtil.SUPPER_MEALS_CODE.equals(mealsId)) {
                    supperEnergy = perEnergy;
                } else if (FoodScoreUtil.NIGHT_EXTRE_MEALS_CODE.equals(mealsId)) {
                    supperAddEnergy = perEnergy;
                }
            }
            dietReportVo.setEnergyBreak(breakEnergy.add(breakAddEnergy));// 早餐+上午加餐
            dietReportVo.setEnergyLunch(lunchEnergy.add(lunchAddEnergy));// 午餐+下午加餐
            dietReportVo.setEnergySupper(supperEnergy.add(supperAddEnergy));// 晚餐+晚上加餐
            dietReportVo.setFoodEnergy(energySum);// 总能量
        }
        return dietReportVo;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDietAnalysePojo getProtidAndFatType(String foodRecordId) {
        PregDietAnalysePojo dietReportVo = new PregDietAnalysePojo();
        BigDecimal zero = BigDecimal.ZERO;
        // 设置蛋白质
        List<PregDietAnalysePojo> protidList = pregDietFoodAnalysisDAO.getFoodProtidSum(foodRecordId);
        BigDecimal protidY = zero;
        BigDecimal protidN = zero;
        if (CollectionUtils.isNotEmpty(protidList)) {
            for (PregDietAnalysePojo protid : protidList) {
                String protidFlag = protid.getFmProtidFlag();
                if ("yes".equals(protidFlag)) {
                    protidY = protidY.add(protid.getProtidAmount());
                } else if ("no".equals(protidFlag)) {
                    protidN = protidN.add(protid.getProtidAmount());
                }
            }
            dietReportVo.setFoodProtidYes(protidY);// 优质蛋白
            dietReportVo.setFoodProtidNo(protidN);// 非优质蛋白
        }
        // 设置脂肪
        List<PregDietAnalysePojo> fatList = pregDietFoodAnalysisDAO.getFoodFatSum(foodRecordId);
        BigDecimal animalFat = zero;
        BigDecimal plantFat = zero;
        if (fatList != null && fatList.size() > 0) {
            for (PregDietAnalysePojo fatVo : fatList) {
                String fatType = fatVo.getFmFatType();
                if ("animal".equals(fatType)) {
                    animalFat = animalFat.add(fatVo.getFatAmount());
                } else if ("plant".equals(fatType)) {
                    plantFat = plantFat.add(fatVo.getFatAmount());

                }
            }
            dietReportVo.setFoodFatAnimal(animalFat);// 动物脂肪
            dietReportVo.setFoodFatPlan(plantFat);// 植物脂肪
        }
        return dietReportVo;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDietAnalysePojo getPregnancyDRIs(String pregnancy, double height) {
        PregDietAnalysePojo dietReportVo = new PregDietAnalysePojo();
        PregnancyDRIs dirs = FoodsFormulaUtil.getPregnancyDRIs(pregnancy);
        Double energy = FoodsFormulaUtil.getPregnancyEnergy(pregnancy, height);
        // 四舍五入取整数
        long round = Math.round(energy);
        dirs.setFoodEnergy(String.valueOf(round));
        dirs.setEnergyBreak(FoodsFormulaUtil.getPregnancyEnergyBreak(energy));
        dirs.setEnergyLunch(FoodsFormulaUtil.getPregnancyEnergyLunch(energy));
        dirs.setEnergySupper(FoodsFormulaUtil.getPregnancyEnergySupper(energy));
        dietReportVo.setDris(dirs);
        return dietReportVo;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDietAnalysePojo getDietaryReviewPregnancyDRIs(String pregnancy, BigDecimal height, BigDecimal weight) {
        PregDietAnalysePojo dietReportVo = new PregDietAnalysePojo();
        DietaryReviewPregnancyDRIs dirs = FoodsFormulaUtil.getDietaryReviewPregnancyDRIs(pregnancy, height, weight);
        Double energy = FoodsFormulaUtil.getPregnancyEnergy(pregnancy, height.doubleValue());
        // 四舍五入取整数
        long round = Math.round(energy);
        dirs.setFoodEnergy(String.valueOf(round));
        dirs.setEnergyBreak(FoodsFormulaUtil.getPregnancyEnergyBreak(energy));
        dirs.setEnergyLunch(FoodsFormulaUtil.getPregnancyEnergyLunch(energy));
        dirs.setEnergySupper(FoodsFormulaUtil.getPregnancyEnergySupper(energy));
        dietReportVo.setRPDris(dirs);
        return dietReportVo;
    }

    @Override
    @Transactional(readOnly = true)
    public PregDietAnalysePojo getGL(String foodRecordId) {
        PregDietAnalysePojo dietReportVo = new PregDietAnalysePojo();
        List<PregDietAnalysePojo> glList = pregDietFoodAnalysisDAO.getGL(foodRecordId);
        if (CollectionUtils.isNotEmpty(glList)) {
            BigDecimal zero = BigDecimal.ZERO;
            BigDecimal breakGL = zero;// 早餐GL
            BigDecimal breakAddGL = zero;// 上午加餐GL
            BigDecimal lunchGL = zero;// 午餐GL
            BigDecimal lunchAddGL = zero;// 下午加餐GL
            BigDecimal supperGL = zero;// 晚餐GL
            BigDecimal supperAddGL = zero;// 晚上加餐GL
            for (PregDietAnalysePojo mealsGL : glList) {
                String mealsId = mealsGL.getMealsId();
                BigDecimal gl = mealsGL.getGl();
                if (FoodScoreUtil.BREAKFAST_MEALS_CODE.equals(mealsId)) {
                    breakGL = gl;
                } else if (FoodScoreUtil.MORNING_EXTRA_MEALS_CODE.equals(mealsId)) {
                    breakAddGL = gl;
                } else if (FoodScoreUtil.LUNCH_MEALS_CODE.equals(mealsId)) {
                    lunchGL = gl;
                } else if (FoodScoreUtil.AFTERNOON_EXTRA_MEALS_CODE.equals(mealsId)) {
                    lunchAddGL = gl;
                } else if (FoodScoreUtil.SUPPER_MEALS_CODE.equals(mealsId)) {
                    supperGL = gl;
                } else if (FoodScoreUtil.NIGHT_EXTRE_MEALS_CODE.equals(mealsId)) {
                    supperAddGL = gl;
                }
            }

            dietReportVo.setBreakGl(breakGL.add(breakAddGL).intValue());// 早餐GL+上午加餐GL
            dietReportVo.setLunchGl(lunchGL.add(lunchAddGL).intValue());// 午餐GL+下午加餐GL
            dietReportVo.setSupperGl(supperGL.add(supperAddGL).intValue());// 晚餐GL+晚上加餐GL
        }
        return dietReportVo;
    }

}
