
package com.mnt.preg.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.account.condition.StatisticaListCondition;
import com.mnt.preg.account.condition.WorkAccountCondition;
import com.mnt.preg.account.dao.WorkAccountDAO;
import com.mnt.preg.account.pojo.StatisticaListPojo;
import com.mnt.preg.account.pojo.WorkAccountPojo;

/**
 * 工作量统计
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-2-5 xdc 初版
 */
@Service
public class WorkAccountServiceImpl implements WorkAccountService {

    @Resource
    private WorkAccountDAO workAccountDAO;

    // --------------------------------------------------【许道川】--------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public List<WorkAccountPojo> queryWorkAccount(WorkAccountCondition condition) {
        List<WorkAccountPojo> list = workAccountDAO.queryWorkAccount(condition);
        // 防止孕期阶段占比之和大于100%，所以做如下处理
        for (WorkAccountPojo workAccountPojo : list) {
            Integer pregStagePreRate = Integer.valueOf((String) workAccountPojo.getPregStagePreRate());
            Integer pregStageMidRate = Integer.valueOf((String) workAccountPojo.getPregStageMidRate());
            Integer pregStageLateRate = Integer.valueOf((String) workAccountPojo.getPregStageLateRate());
            if (pregStagePreRate != 0 || pregStageMidRate != 0 || pregStageLateRate != 0) {
                if (pregStagePreRate + pregStageMidRate > 100) {
                    // 如果前两个比例之和大于100，那么对孕中期取值做处理
                    pregStageMidRate = 100 - pregStagePreRate;
                    workAccountPojo.setPregStageMidRate(pregStageMidRate);
                }
                // 设置孕晚期的值为其余两项值差
                workAccountPojo.setPregStageLateRate(100 - pregStagePreRate - pregStageMidRate);
            }
        }
        return list;
    }

    // --------------------------------------------------【许道川】--------------------------------------------------

    // --------------------------------------------------【尚成达】--------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<StatisticaListPojo> queryStatisticsData(StatisticaListCondition condition) {
        return workAccountDAO.queryStatisticsData(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> queryOutpatientCount(WorkAccountCondition condition) {
        List<Object[]> list = new ArrayList<Object[]>();
        List<String> users = condition.getDiagnosisUserList();
        if (CollectionUtils.isNotEmpty(users)) {
            for (String userId : users) {
                // 获取结果
                condition.setDiagnosisUser(userId);
                List<Object[]> resultList = workAccountDAO.queryOutpatientCount(condition);
                list.add(resultList.get(0));
            }
        }
        return list;
    }

    @Override
    public List<String> getOutpatientDate(String startDate, String endDate) {
        return workAccountDAO.getMonthBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkAccountPojo> gestationDistribution(WorkAccountCondition condition) {
        List<WorkAccountPojo> workAccountList = new ArrayList<WorkAccountPojo>();
        List<String> userList = condition.getDiagnosisUserList();
        // 如果userList集合不为空则视为部门操作，反之视为个人操作
        if (CollectionUtils.isNotEmpty(userList)) {
            for (String userId : userList) {
                condition.setDiagnosisUser(userId);
                List<WorkAccountPojo> resutl = workAccountDAO.gestationDistribution(condition);
                if (CollectionUtils.isNotEmpty(resutl)) {
                    workAccountList.add(resutl.get(0));
                }
            }
        } else {
            workAccountList = workAccountDAO.gestationDistribution(condition);
        }
        return workAccountList;
    }

    // --------------------------------------------------【尚成达】--------------------------------------------------

    // --------------------------------------------------【董宏生】--------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Integer queryCountDiagnosisScope(WorkAccountCondition condition) {
        return workAccountDAO.queryCountDiagnosisScope(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkAccountPojo> queryCountDiagnosisScopeDept(WorkAccountCondition condition) {
        return workAccountDAO.queryCountDiagnosisScopeDept(condition);
    }

    // --------------------------------------------------【董宏生】--------------------------------------------------

    // --------------------------------------------------【张传强】--------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Integer countDiseaseFrequency(WorkAccountCondition condition) {
        return workAccountDAO.countDiseaseFrequency(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkAccountPojo> countCompareDiseaseFrequency(WorkAccountCondition condition) {
        return workAccountDAO.countCompareDiseaseFrequency(condition);
    }
    // --------------------------------------------------【张传强】--------------------------------------------------

}
