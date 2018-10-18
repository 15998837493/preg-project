/*
 * HospitalInspectPayTemplateService.java    1.0  2017-7-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.platform.entity.HospitalInspectPayTemplate;

/**
 * 辅助检查项目套餐
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-20 scd 初版
 */
@Validated
public interface HospitalInspectPayTemplateService {

    /**
     * 
     * 条件检索辅助检查项目套餐
     * 
     * @author scd
     * @param condition
     * @return
     */
    List<HospitalInspectPayTemplate> queryAuxiliaryCheck(HospitalInspectPayTemplate condition);

    /**
     * 
     * 添加辅助检查套餐
     * 
     * @author scd
     * @param auxiliary
     */
    String addAuxiliaryCheck(HospitalInspectPayTemplate auxiliary);

    /**
     * 
     * 检查辅助检查套餐名称是否重复
     * 
     * @author scd
     * @param templetName
     * @return
     */
    int checktempletNameValid(String templetName);

    /**
     * 
     * 删除辅助项目套餐
     * 
     * @author scd
     * @param templetId
     */
    void removeTemplet(String templetId);
}
