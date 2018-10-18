
package com.mnt.preg.examitem.service;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

import com.mnt.preg.examitem.pojo.PregDietAnalysePojo;

/**
 * 膳食分析报告事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public interface PregDietFoodAnalyseService {

    /**
     * 计算一天的饮食中每种营养物的含量
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    PregDietAnalysePojo getElementSum(@NotBlank String foodRecordId);

    /**
     * 计算一天中每餐的供能量
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    PregDietAnalysePojo getEachMealEnergy(@NotBlank String foodRecordId);

    /**
     * 计算不同类型的蛋白质和脂肪重量
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    PregDietAnalysePojo getProtidAndFatType(@NotBlank String foodRecordId);

    /**
     * 计算孕期DRIs值
     * 
     * @author zcq
     * @param pregnancy
     * @param height
     * @return
     */
    PregDietAnalysePojo getPregnancyDRIs(@NotBlank String pregnancy, double height);

    /**
     * 计算孕期DRIs值(24小时膳食回顾用)
     * 
     * @author scd
     * @param pregnancy
     * @param height
     * @return
     */
    PregDietAnalysePojo getDietaryReviewPregnancyDRIs(@NotBlank String pregnancy, BigDecimal height, BigDecimal weight);

    /**
     * 计算gl值
     * 
     * @param foodRecordId
     * @return DietReportVo
     */
    PregDietAnalysePojo getGL(@NotBlank String foodRecordId);

}
