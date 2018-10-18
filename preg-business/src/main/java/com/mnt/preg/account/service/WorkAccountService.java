
package com.mnt.preg.account.service;

import java.util.List;

import com.mnt.preg.account.condition.StatisticaListCondition;
import com.mnt.preg.account.condition.WorkAccountCondition;
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
public interface WorkAccountService {

    // --------------------------------------------------【许道川】--------------------------------------------------
    /**
     * 工作量统计
     * 
     * @author xdc
     * @param condition
     * @return
     */
    List<WorkAccountPojo> queryWorkAccount(WorkAccountCondition condition);

    // --------------------------------------------------【许道川】--------------------------------------------------

    // --------------------------------------------------【尚成达】--------------------------------------------------
    /**
     * 
     * 统计列表数据
     * 
     * @author scd
     * @param condition
     * @return
     */
    List<StatisticaListPojo> queryStatisticsData(StatisticaListCondition condition);

    /**
     * 
     * 检索门诊量
     * 
     * @author scd
     * @param condition
     * @return
     */
    List<Object[]> queryOutpatientCount(WorkAccountCondition condition);

    /**
     * 
     * 获取日期区间（获取日期区间方法充DAO中移到Util后这个接口会删除）
     * 
     * @author scd
     * @param startDate
     * @param endDate
     * @return
     */
    List<String> getOutpatientDate(String startDate, String endDate);

    /**
     * 
     * 获取孕期门诊分布
     * 
     * @author scd
     * @param condition
     * @return
     */
    List<WorkAccountPojo> gestationDistribution(WorkAccountCondition condition);

    // --------------------------------------------------【尚成达】--------------------------------------------------

    // --------------------------------------------------【董宏生】--------------------------------------------------
    /**
     * 查询在一段日期范围内符合条件的数量(个人)
     * 
     * @author dhs
     * @param
     * @return
     */
    Integer queryCountDiagnosisScope(WorkAccountCondition condition);

    /**
     * 查询在一段日期范围内符合条件的数量(部门)
     * 
     * @author dhs
     * @param
     * @return
     */
    List<WorkAccountPojo> queryCountDiagnosisScopeDept(WorkAccountCondition condition);

    // --------------------------------------------------【董宏生】--------------------------------------------------

    // --------------------------------------------------【张传强】--------------------------------------------------
    /**
     * 工作量统计--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    Integer countDiseaseFrequency(WorkAccountCondition condition);

    /**
     * 工作量对比--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<WorkAccountPojo> countCompareDiseaseFrequency(WorkAccountCondition condition);
    // --------------------------------------------------【张传强】--------------------------------------------------

}
