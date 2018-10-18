
package com.mnt.preg.master.service.preg;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.preg.DietTemplateCondition;
import com.mnt.preg.master.condition.preg.DietTemplateDetailCondition;
import com.mnt.preg.master.entity.preg.PregDietTemplate;
import com.mnt.preg.master.entity.preg.PregDietTemplateDetail;
import com.mnt.preg.master.pojo.preg.PregDietTemplateDetailPojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplatePojo;

/**
 * 膳食干预方案-食谱模板Service
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-22 wsy 初版
 */
@Validated
public interface PregDietTemplateService {

    /**
     * 验证模板名称
     * 
     * @author xdc
     * @param filedValue
     * @return
     */
    Integer validCode(String filedValue);

    /**
     * 添加食物模版
     * 
     * @author zcq
     * @param dietTemplate
     * @return
     */
    PregDietTemplatePojo addDietTemplate(PregDietTemplate dietTemplate);

    /**
     * 检索食物模版
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PregDietTemplatePojo> queryDietTemplate(DietTemplateCondition condition);

    /**
     * 获取食物模版
     * 
     * @author zcq
     * @param dietId
     * @return
     */
    PregDietTemplatePojo getDietTemplate(@NotBlank String dietId);

    /**
     * 修改食物模版
     * 
     * @author zcq
     * @param dietTemplate
     */
    PregDietTemplatePojo updateDietTemplate(PregDietTemplate dietTemplate);

    /**
     * 删除食物模版
     * 
     * @author zcq
     * @param dietIdId
     */
    void removeDietTemplate(String dietId);

    /**
     * 条件检索代量食谱模板
     * 
     * @author gss
     * @param condition
     * @return
     */
    List<PregDietTemplatePojo> queryDietTemplateByCondition(DietTemplateCondition condition);

    /**
     * 模板id查询模板种类名称
     * 
     * @author xdc
     * @param templateId
     * @return
     */
    List<PregDietTemplateDetailPojo> queryDietTemplateDetailNamesByDietId(String dietId);

    /**
     * 根据查询条件查询名称对应的明细
     * 
     * @author xdc
     * @param condition
     * @return
     */
    List<PregDietTemplateDetailPojo> queryDietTemplateDetailByCondition(DietTemplateDetailCondition condition);

    /**
     * 移除foodDay
     * 
     * @author xdc
     * @param condition
     */
    void removeFoodDayByCondition(String dietId, String foodDay);

    /**
     * 检索食物模版列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<PregDietTemplateDetailPojo> queryDietTemplateDetails(DietTemplateDetailCondition condition);

    /**
     * 获取食物模版列表
     * 
     * @author zcq
     * @param dietId
     * @return
     */
    PregDietTemplateDetailPojo getDietTemplateDetail(@NotBlank String detailId);

    /**
     * 添加食物模版列表
     * 
     * @author zcq
     * @param dietTemplate
     * @param detailList
     * @return
     */
    String addDietTemplateDetail(PregDietTemplateDetail dietTemplateDetail);

    /**
     * 修改食物模版列表
     * 
     * @author zcq
     * @param dietTemplate
     * @param detailList
     */
    void updateDietTemplateDetail(PregDietTemplateDetail dietTemplateDetail);

    /**
     * 删除食物模版列表
     * 
     * @author zcq
     * @param dietId
     */
    void removeDietTemplateDetail(String detailId);

}
