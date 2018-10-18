
package com.mnt.preg.master.service.items;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.items.DiseaseItemDeduceCondition;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceGroupCondition;
import com.mnt.preg.master.entity.items.DiseaseItemDeduce;
import com.mnt.preg.master.entity.items.DiseaseItemDeduceGroup;
import com.mnt.preg.master.pojo.items.DiseaseItemDeduceGroupPojo;
import com.mnt.preg.master.pojo.items.DiseaseItemDeducePojo;
import com.mnt.preg.master.pojo.items.ItemPojo;

/**
 * 
 * 干预疾病接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-4-5 gss 初版
 */
@Validated
public interface DiseaseItemDeduceService {

    /**
     * 条件检索推断指标信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<DiseaseItemDeducePojo> queryDiseaseItemDeduce(DiseaseItemDeduceCondition condition);

    /**
     * 添加推断指标
     * 
     * @author zcq
     * @param diseaseItemDeduce
     * @return
     */
    String addDiseaseItemDeduce(DiseaseItemDeduce diseaseItemDeduce);

    /**
     * 修改疾病推断指标信息
     * 
     * @author zcq
     * @param diseaseItem
     */
    void updateDiseaseItemDeduce(DiseaseItemDeduce diseaseItemDeduce);

    /**
     * 获取推断指标信息
     * 
     * @author zcq
     * @return
     */
    DiseaseItemDeduce getDiseaseItemDeduce(String id);

    /**
     * 获取疾病指标信息
     * 
     * @author zcq
     * @param id
     * @return
     */
    ItemPojo getDeduceItem(String id);

    /**
     * 删除疾病检测指标
     * 
     * @author zcq
     * @param id
     */
    void deleteDiseaseItemDeduceById(String id);

    /**
     * 删除疾病检测指标(诊断+检验)
     * 
     * @author mlq
     * @param diseaseId
     * @param itemId
     */
    void deleteDiseaseItemDeduce(String diseaseId, String itemId);

    /**
     * 检索推断指标列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<DiseaseItemDeduceGroupPojo> queryDiseaseItemDeduceGroup(DiseaseItemDeduceGroupCondition condition);

    /**
     * 添加推断指标组合
     * 
     * @author zcq
     * @param diseaseItem
     * @return
     */
    String addDiseaseItemDeduceGroup(DiseaseItemDeduceGroup diseaseItemDeduceGroup);

    /**
     * 删除推断指标组合
     * 
     * @author zcq
     * @param id
     */
    void deleteDiseaseItemDeduceGroupById(String id);

    /**
     * 删除推断指标组合
     * 
     * @author zcq
     * @param itemId
     */
    void deleteDiseaseItemDeduceGroupByItemId(String itemId);

    /**
     * 根据标签名称删除推断指标组合
     * 
     * @author zcq
     * @param itemNames
     */
    void deleteDiseaseItemDeduceGroupByitemNames(String itemNames, String diseaseId);

    /**
     * 初步检索符合条件的组合
     * 
     * @author zcq
     * @param itemIdList
     * @return
     */
    List<DiseaseItemDeduceGroupPojo> queryFitDiseaseItemDeduceGroup(List<String> itemIdList);

}
