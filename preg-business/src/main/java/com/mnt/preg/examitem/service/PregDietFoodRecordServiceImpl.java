
package com.mnt.preg.examitem.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.examitem.dao.PregDietFoodAnalysisDAO;
import com.mnt.preg.examitem.dao.PregDietFoodRecordDAO;
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
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.basic.FoodDAO;

/**
 * 膳食评估记录事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
@Service
public class PregDietFoodRecordServiceImpl extends BaseService implements PregDietFoodRecordService {

    @Resource
    private PregDietFoodRecordDAO pregDietFoodRecordDAO;// 膳食评估记录DAO

    @Resource
    private PregDietFoodAnalysisDAO pregDietFoodAnalysisDAO;// 膳食评估报告DAO

    /** 食物管理 */
    @Resource
    private FoodDAO foodDAO;

    @Override
    @Transactional(readOnly = true)
    public PregFoodRecordPojo getFoodRecord(String foodRecordId) {
        return pregDietFoodRecordDAO.getFoodRecord(foodRecordId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregFoodFeelTimePojo> queryFoodFeelTime(String foodRecordId) {
        return pregDietFoodRecordDAO.queryFoodFeelTime(foodRecordId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregFoodDetailsPojo> queryFoodDetails(String foodRecordId) {
        return pregDietFoodRecordDAO.queryFoodDetails(foodRecordId);
    }

    @Override
    @Transactional(readOnly = true)
    public PregHabitPojo getPregnancyHabit(String foodRecordId) {
        return pregDietFoodRecordDAO.getPregnancyHabit(foodRecordId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregFoodFrequencyPojo> queryFoodFrequency(String foodRecordId) {
        return pregDietFoodRecordDAO.queryFoodFrequency(foodRecordId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addFoodRecord(PregFoodRecord foodRecord) {
        return (String) pregDietFoodRecordDAO.save(foodRecord);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateFoodRecord(PregFoodRecord foodRecord) {
        String foodRecordId = foodRecord.getFoodRecordId();
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("foodRecordId", HQLSymbol.EQ.toString(), foodRecordId));
        pregDietFoodRecordDAO.updateHQL(foodRecord, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeFoodRecord(String foodRecordId) {
        // 删除膳食评估记录
        PregFoodRecord foodRecord = new PregFoodRecord();
        foodRecord.setFoodRecordId(foodRecordId);
        foodRecord.setFlag(0);
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("foodRecordId", HQLSymbol.EQ.toString(), foodRecordId));
        pregDietFoodRecordDAO.updateHQL(foodRecord, conditionParams);
        // 删除三餐时间和感受
        pregDietFoodRecordDAO.deleteFoodFeelTime(foodRecordId);
        // 删除饮食明细
        pregDietFoodRecordDAO.deleteFoodDetails(foodRecordId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addFoodFeelTime(List<PregFoodFeelTime> foodFeelTimeList, String foodRecordId) {
        if (CollectionUtils.isNotEmpty(foodFeelTimeList)) {
            for (PregFoodFeelTime foodFeelTime : foodFeelTimeList) {
                foodFeelTime.setFoodRecordId(foodRecordId);
                pregDietFoodRecordDAO.save(foodFeelTime);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addFoodDetails(List<PregFoodDetails> foodDetailsList, String foodRecordId) {
        if (CollectionUtils.isNotEmpty(foodDetailsList)) {
            for (PregFoodDetails foodDetails : foodDetailsList) {
                // 添加膳食明细
                foodDetails.setFoodRecordId(foodRecordId);
                pregDietFoodRecordDAO.save(foodDetails);
                // 记录应用频率
                foodDAO.updateFoodUseRecord(foodDetails.getFoodId());
            }
        }
    }

    @Override
    public void addPregnancyHabit(PregHabit pregnancyHabit) {
        pregDietFoodRecordDAO.save(pregnancyHabit);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addFoodFrequency(List<PregFoodFrequency> foodFrequencyList, String foodRecordId) {
        if (CollectionUtils.isNotEmpty(foodFrequencyList)) {
            for (PregFoodFrequency foodFrequency : foodFrequencyList) {
                foodFrequency.setFoodRecordId(foodRecordId);
                pregDietFoodRecordDAO.save(foodFrequency);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregFoodRecordPojo> getFoodRecordByReportDate(String reportDate) {
        if (StringUtils.isEmpty(reportDate)) {
            return null;
        }
        return pregDietFoodRecordDAO.getFoodRecordByReportDate(reportDate);
    }

}
