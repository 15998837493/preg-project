
package com.mnt.preg.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.entity.PregAdjustRecord;

@Validated
public interface PregAdjustRecordService {

    /**
     * 查询孕周调整的历史记录
     * 
     * @author xdc
     * @param condition
     * @return
     */
    List<PregAdjustRecord> queryPregAdjustRecords(PregAdjustRecord condition);

    /**
     * 查询孕周调整的历史记录
     * 
     * @author xdc
     * @param condition
     * @return
     */
    PregAdjustRecord getPregAdjustRecordsByDiagnosisId(String diagnosisId);

    /**
     * 根据id删除诊断记录
     * 
     * @author xdc
     * @param diagnosisId
     */
    void deletePregAdjustResords(String diagnosisId);

    /**
     * 更新调整记录
     * 
     * @author xdc
     * @param pregAdjustRecord
     */
    void updatePregAdjustResords(PregAdjustRecord pregAdjustRecord);

    /**
     * 添加调整记录
     * 
     * @author xdc
     * @param pregAdjustRecord
     */
    void addPregAdjustResords(PregAdjustRecord pregAdjustRecord);
}
