
package com.mnt.preg.examitem.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.examitem.condition.InbodyCondition;
import com.mnt.preg.examitem.entity.PregInbodyBca;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.examitem.pojo.PregInBodyPojo;
import com.mnt.preg.statistic.pojo.DiagnosisInfoPojo;

/**
 * 诊疗服务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-14 zcq 初版
 */
@Validated
public interface PregInbodyService {

    /**
     * 检索人体成分分析
     * 
     * @author zcq
     * @param condition
     * @return
     */
    PregInBodyBcaPojo getInbodyByCondition(InbodyCondition condition);

    /**
     * 检索人体成分分析历史记录
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PregInBodyBcaPojo> queryInbodyHistory(InbodyCondition condition);

    /**
     * 检索已同步的人体成分
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<String> queryInbodyDatetimes(InbodyCondition condition);

    /**
     * 同步添加人体成分分析数据
     * 
     * @author zcq
     * @param groupBo
     * @return
     */
    String addInbdoy(PregInBodyPojo groupBo);

    /**
     * 同步添加人体成分分析数据
     * 
     * @author zcq
     * @param groupBo
     * @return
     */
    String addInbdoyInfo(PregInBodyPojo groupBo);

    /***
     * 修改人体成分分析主表
     * 
     * @author zcq
     * @param bca
     */
    void updateInbodyBca(PregInbodyBca bca);

    /**
     * 根据id删除人体成分记录
     * 
     * @author xdc
     * @param bcaId
     */
    void deleteInbodyBcaById(String bcaId);

    /**
     * 检索人体成分数据（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    List<PregInBodyBcaPojo> getStatisticInbodyByCondition(InbodyCondition condition);

    /**
     * 检索人体成分主表参数数据（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    List<DiagnosisInfoPojo> queryBaseInbodyByConsition(InbodyCondition inbodyCondition);
}
