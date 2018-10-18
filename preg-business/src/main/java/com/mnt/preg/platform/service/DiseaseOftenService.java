/*
 * DiseaseOftenService.java    1.0  2017-11-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.platform.condition.DiseaseOftenCondition;
import com.mnt.preg.platform.entity.DiseaseOften;

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
public interface DiseaseOftenService {

    /**
     * 
     * 条件检索常用诊断项目
     * 
     * @author scd
     * @param diseaseOften
     * @return
     */
    List<DiseaseOften> queryDiseaseOften(DiseaseOftenCondition diseaseOften);

    /**
     * 
     * 添加常用诊断项目
     * 
     * @author scd
     * @param diseaseOften
     * @return
     */
    String addDiseaseOften(DiseaseOften diseaseOften);

    /**
     * 
     * 根据主键获取常用诊断项目
     * 
     * @author scd
     * @param diseaseId
     * @return
     */
    DiseaseOften getDiseaseOften(String diseaseId);

    /**
     * 
     * 删除常用诊断项目
     * 
     * @author scd
     * @param diseaseId
     * @return
     */
    Boolean removeDiseaseOften(String diseaseId);

    /**
     * 
     * 根据机构ID用户ID获取diseaseCode
     * 
     * @author scd
     * @param insId
     * @param userId
     * @return
     */
    String getDiseaseCode(String insId, String userId);

    /**
     * 
     * 验证添加的常用诊断项目名称是否重复
     * 
     * @author scd
     * @param diseaseOften
     * @return
     */
    Boolean checkDiseaseOftenName(String diseaseName);
}
