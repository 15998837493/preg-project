/*
 * DiseaseOftenService.java    1.0  2017-11-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 常用诊断项目Service
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-21 scd 初版
 */
@Validated
public interface PregDiagnosisDiseaseService {

    /**
     * 添加诊断项目
     * 
     * @author xdc
     * @param disease
     */
    Boolean addDiagnosisDisease(PregDiagnosisDisease disease);

    /**
     * 删除接诊诊断项目
     * 
     * @author xdc
     * @param diagnosisId
     * @param diseaseName
     */
    void deleteDiagnosisDisease(String diagnosisId, String diseaseName);

    /**
     * 更新接诊诊断项目
     * 
     * @author xdc
     * @param diagnosisId
     * @param diseaseName
     */
    void updateDiagnosisDisease(String diagnosisId, String diseaseName);

    /**
     * 根据接诊id获取诊断项目
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    List<PregDiagnosisDisease> queryDiagnosisDiseaseByDiagnosisId(String diagnosisId);

    /**
     * 根据条件查询诊断项目
     * 
     * @author xdc
     * @param condtion
     * @return
     */
    List<PregDiagnosisPojo> queryDiagnosisDiseaseByCondition(DiagnosisCondition condition);

    /**
     * 批量添加诊断项目
     * 
     * @author xdc
     * @param diseaseList
     * @return
     */
    List<PregDiagnosisDisease> saveDiagnosisDisease(List<PregDiagnosisDisease> diseaseList);

    /**
     * 根据条件查询诊断项目(统计)
     * 
     * @author mlq
     * @param condtion
     * @return
     */
    List<PregDiagnosisPojo> queryStatisticDiseaseByCondition(DiagnosisCondition condition);
}
