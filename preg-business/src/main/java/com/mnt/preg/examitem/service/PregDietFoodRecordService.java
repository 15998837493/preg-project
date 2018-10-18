
package com.mnt.preg.examitem.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.mnt.preg.examitem.entity.PregFoodDetails;
import com.mnt.preg.examitem.entity.PregFoodFeelTime;
import com.mnt.preg.examitem.entity.PregFoodFrequency;
import com.mnt.preg.examitem.entity.PregFoodRecord;
import com.mnt.preg.examitem.entity.PregHabit;
import com.mnt.preg.examitem.pojo.PregFoodDetailsPojo;
import com.mnt.preg.examitem.pojo.PregFoodFeelTimePojo;
import com.mnt.preg.examitem.pojo.PregFoodFrequencyPojo;
import com.mnt.preg.examitem.pojo.PregFoodRecordPojo;
import com.mnt.preg.examitem.pojo.PregHabitPojo;

/**
 * 膳食评估记录事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public interface PregDietFoodRecordService {

    /**
     * 查询膳食评估记录信息--根据【主键】
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    PregFoodRecordPojo getFoodRecord(@NotBlank String foodRecordId);

    /**
     * 根据报告日期查询报告基本信息
     * 
     * @author gss
     * @param reportDate
     * @return
     */
    List<PregFoodRecordPojo> getFoodRecordByReportDate(@NotBlank String reportDate);

    /**
     * 查询用餐时间和感受--根据【膳食评估编号】
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    List<PregFoodFeelTimePojo> queryFoodFeelTime(@NotBlank String foodRecordId);

    /**
     * 查询用餐明细--根据【膳食评估编号】
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    List<PregFoodDetailsPojo> queryFoodDetails(@NotBlank String foodRecordId);

    /**
     * 查询饮食习惯--根据【膳食评估编号】（孕期）
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    PregHabitPojo getPregnancyHabit(@NotBlank String foodRecordId);

    /**
     * 查询饮食频率--根据【膳食评估编号】
     * 
     * @author zcq
     * @param foodRecordId
     * @return
     */
    List<PregFoodFrequencyPojo> queryFoodFrequency(@NotBlank String foodRecordId);

    /**
     * 添加膳食评估记录
     * 
     * @author zcq
     * @param foodRecord
     * @return
     */
    String addFoodRecord(@Valid @NotNull PregFoodRecord foodRecord);

    /**
     * 修改膳食评估记录
     * 
     * @author zcq
     * @param foodRecord
     */
    void updateFoodRecord(@Valid @NotNull PregFoodRecord foodRecord);

    /**
     * 删除膳食评估记录
     * 
     * @author zcq
     * @param foodRecordId
     */
    void removeFoodRecord(@NotBlank String foodRecordId);

    /**
     * 添加膳食评估三餐时间和感受
     * 
     * @author zcq
     * @param foodFeelTimeList
     * @param foodRecordId
     * @return
     */
    void addFoodFeelTime(List<PregFoodFeelTime> foodFeelTimeList, @NotBlank String foodRecordId);

    /**
     * 添加膳食评估饮食明细
     * 
     * @author zcq
     * @param foodDetailsList
     * @param foodRecordId
     * @return
     */
    void addFoodDetails(List<PregFoodDetails> foodDetailsList, @NotBlank String foodRecordId);

    /**
     * 添加膳食评估饮食习惯（孕期）
     * 
     * @author zcq
     * @param pregnancyHabit
     */
    void addPregnancyHabit(@Valid @NotNull PregHabit pregnancyHabit);

    /**
     * 添加膳食评估饮食频率
     * 
     * @author zcq
     * @param foodFrequencyList
     * @param foodRecordId
     */
    void addFoodFrequency(List<PregFoodFrequency> foodFrequencyList, @NotBlank String foodRecordId);

}
