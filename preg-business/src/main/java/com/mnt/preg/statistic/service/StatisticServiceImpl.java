
package com.mnt.preg.statistic.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.statistic.condition.SearchCountCondition;
import com.mnt.preg.statistic.condition.StatisticForm;
import com.mnt.preg.statistic.dao.StatisticDAO;
import com.mnt.preg.statistic.pojo.BirthEndingInfoPojo;
import com.mnt.preg.statistic.pojo.CustomerInfoPojo;
import com.mnt.preg.statistic.pojo.DiagnosisInfoPojo;
import com.mnt.preg.statistic.pojo.StatiscBirthResultPojo;
import com.mnt.preg.statistic.pojo.StatisticCustomerPojo;

/**
 * 数据统计平台
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-14 mlq 初版
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Resource
    private StatisticDAO statisticDAO;

    @Override
    @Transactional(readOnly = true)
    public List<StatisticCustomerPojo> queryCustomerList(StatisticForm condition) {
        return statisticDAO.queryCustomerList(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerInfoPojo> queryCustomerInfoByCustIds(SearchCountCondition condition) {
        return statisticDAO.queryCustomerInfoByCustIds(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisInfoPojo> queryDiagnosisInfoByCustIds(SearchCountCondition condition) {
        return statisticDAO.queryDiagnosisInfoByCustIds(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BirthEndingInfoPojo> queryBirthEndingInfoByCustIds(SearchCountCondition condition) {
        return statisticDAO.queryBirthEndingInfoByCustIds(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public StatiscBirthResultPojo queryConfinedAccount(String resultStartDate, String resultEndDate) {
        return statisticDAO.queryConfinedAccount(resultStartDate, resultEndDate);
    }

    /**
     * 
     * [导出excel时查到的数据暂时放在缓存]
     *
     * @author lipeng
     * @param key
     * @param object
     * @return
     */
    @CachePut(value = "excelCache", key = "#key")
    public Object addExcelDataCache(String key, Object object) {
        return object;
    }

    /**
     * 
     * [从缓存中获取数据]
     *
     * @author lipeng
     * @param key
     * @return
     */
    @Cacheable(value = "excelCache", key = "#key")
    public Object getExcelDataCache(String key) {
        return null;
    }

    /**
     * 
     * [清除相应的缓存]
     *
     * @author lipeng
     * @param key
     */
    @CacheEvict(value = "excelCache", key = "#key")
    public void removeExcelDataCache(String key) {
    }
}
