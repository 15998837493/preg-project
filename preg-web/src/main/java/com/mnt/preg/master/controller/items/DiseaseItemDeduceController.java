
package com.mnt.preg.master.controller.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceCondition;
import com.mnt.preg.master.condition.items.DiseaseItemDeduceGroupCondition;
import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.entity.items.DiseaseItemDeduce;
import com.mnt.preg.master.entity.items.DiseaseItemDeduceGroup;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.mapping.items.ItemsMapping;
import com.mnt.preg.master.pojo.items.DiseaseItemDeduceGroupPojo;
import com.mnt.preg.master.pojo.items.DiseaseItemDeducePojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.master.service.basic.ProductService;
import com.mnt.preg.master.service.items.DiseaseItemDeduceService;
import com.mnt.preg.master.service.items.HospitalInspectPayService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 指标推断疾病Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-18 zcq 初版
 */
@Controller
public class DiseaseItemDeduceController extends BaseController {

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private DiseaseItemDeduceService diseaseItemDeduceService;

    @Resource
    private ItemService itemService;

    @Resource
    private ProductService productService;

    @Resource
    private HospitalInspectPayService hospitalInspectPayService;

    /**
     * 跳转到出诊疾病配置页面
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_QUOTA_VIEW)
    public String toDiseaseQuotaView(Model model) {
        // 获取诊断项目
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getInterveneDiseaseTreeList()));
        // 获取指标
        ItemCondition condition = new ItemCondition();
        List<ItemPojo> itemList = itemService.queryItemByCondition(condition);
        model.addAttribute("itemList", NetJsonUtils.listToJsonArray(itemList));
        return MasterPageName.DISEASE_QUOTA_VIEW;
    }

    /**
     * 获取干预疾病树
     * 
     * @author xdc
     * @return
     */
    private List<ZTree<InterveneDiseasePojo>> getInterveneDiseaseTreeList() {
        List<ZTree<InterveneDiseasePojo>> treeList = new ArrayList<ZTree<InterveneDiseasePojo>>();
        treeList.add(new ZTree<InterveneDiseasePojo>("0000", "诊断项目", true, "home", true));
        List<InterveneDiseasePojo> interveneList = interveneDiseaseService.queryInterveneDisease(null);
        if (CollectionUtils.isNotEmpty(interveneList)) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (InterveneDiseasePojo intervene : interveneList) {
                String type = intervene.getDiseaseType();
                if (!map.containsKey(type) && type != null && !type.equals("")) {
                    ZTree<InterveneDiseasePojo> tree = new ZTree<InterveneDiseasePojo>();
                    tree.setId(type);
                    tree.setpId("0000");
                    tree.setName(transCode("diseaseType", type));
                    tree.setValue(intervene);
                    tree.setIconSkin("mulu");
                    treeList.add(tree);
                    map.put(type, 1);
                }
                if (type == null || type.equals("")) {
                    type = "0000";
                }
                ZTree<InterveneDiseasePojo> tree = new ZTree<InterveneDiseasePojo>();
                tree.setId(intervene.getDiseaseCode());
                tree.setpId(type);
                tree.setName(intervene.getDiseaseName());
                tree.setValue(intervene);
                tree.setIconSkin("dept");
                treeList.add(tree);
            }
        }
        return treeList;
    }

    /**
     * 跳转到出诊疾病配置页面
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_DISEASE_QUOTA_VIEW)
    public String toInsepctDiseaseQuotaView(Model model) {
        // 获取诊断项目
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getInterveneDiseaseTreeList()));
        // 获取指标
        ItemCondition condition = new ItemCondition();
        List<ItemPojo> itemList = itemService.queryItemByCondition(condition);
        model.addAttribute("itemList", NetJsonUtils.listToJsonArray(itemList));
        return MasterPageName.INSPECT_DISEASE_QUOTA_VIEW;
    }

    /**
     * 检索疾病检测指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSEPCT_ITEM_DISEASE_QUERY)
    public @ResponseBody
    WebResult<List<DiseaseItemDeducePojo>> queryDiseaseItemDeduce(DiseaseItemDeduceCondition condition) {
        WebResult<List<DiseaseItemDeducePojo>> webResult = new WebResult<List<DiseaseItemDeducePojo>>();
        List<DiseaseItemDeducePojo> list = diseaseItemDeduceService.queryDiseaseItemDeduce(condition);
        webResult.setData(list);

        return webResult;
    }

    /**
     * 添加疾病检测指标
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECT_ITEM_DISEASE_ADD)
    public @ResponseBody
    WebResult<ItemPojo> addDiseaseItemDeduce(DiseaseItemDeduce diseaseItemDeduce) {
        WebResult<ItemPojo> webResult = new WebResult<ItemPojo>();
        String id = diseaseItemDeduceService.addDiseaseItemDeduce(diseaseItemDeduce);
        ItemPojo itemPojo = diseaseItemDeduceService.getDeduceItem(id);
        webResult.setSuc(itemPojo);
        return webResult;
    }

    /**
     * 修改推断指标信息
     * 
     * @author mlq
     * @param items
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_ITEM_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateDiseaseItemDeduce(String diseaseId, String itemId, String items) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 添加配置信息
        if (!StringUtils.isBlank(items)) {
            // 删除之前的配置信息
            diseaseItemDeduceService.deleteDiseaseItemDeduce(diseaseId, itemId);
            String[] jsonMore = items.split("###");
            for (String jsonStr : jsonMore) {
                JSONObject itemJson = JSONObject.fromObject(jsonStr);
                DiseaseItemDeduce item = (DiseaseItemDeduce) JSONObject.toBean(itemJson, DiseaseItemDeduce.class);
                diseaseItemDeduceService.addDiseaseItemDeduce(item);
            }
        }
        // diseaseItemDeduceService.updateDiseaseItemDeduce(diseaseItemDeduce);
        return webResult.setSuc(true);
    }

    /**
     * 删除疾病检测指标
     * 
     * @author zcq
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSEPCT_ITEM_DISEASE_DELETE)
    public @ResponseBody
    WebResult<Boolean> deleteDiseaseItemDeduce(String diseaseId, String itemId, String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 删除推断指标(根据主键)
        if (StringUtils.isNotBlank(id)) {
            DiseaseItemDeduceCondition condition = new DiseaseItemDeduceCondition();
            condition.setDiseaseId(diseaseId);
            condition.setItemId(itemId);
            List<DiseaseItemDeducePojo> list = diseaseItemDeduceService.queryDiseaseItemDeduce(condition);
            if (CollectionUtils.isNotEmpty(list) && list.size() == 1) {// 保留最后一条，清空配置数据
                diseaseItemDeduceService.deleteDiseaseItemDeduceById(id);
                DiseaseItemDeduce diseaseItemDeduce = new DiseaseItemDeduce();
                diseaseItemDeduce.setDiseaseId(diseaseId);
                diseaseItemDeduce.setItemId(itemId);
                diseaseItemDeduceService.addDiseaseItemDeduce(diseaseItemDeduce);
            } else {
                diseaseItemDeduceService.deleteDiseaseItemDeduceById(id);
            }
        } else if (StringUtils.isNotBlank(diseaseId) && StringUtils.isNotBlank(itemId)) {
            // 删除推断指标(根据诊断+检验项目)
            diseaseItemDeduceService.deleteDiseaseItemDeduce(diseaseId, itemId);
            // 删除推断指标组合(根据诊断+检验项目)
            diseaseItemDeduceService.deleteDiseaseItemDeduceGroupByItemId(itemId);
        }
        return webResult.setSuc(true);
    }

    /**
     * 检索推断指标组合列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_ITEM_GROUP_QUERY)
    public @ResponseBody
    WebResult<List<DiseaseItemDeduceGroupPojo>> queryDiseaseItemDeduceGroup(DiseaseItemDeduceGroupCondition condition) {
        WebResult<List<DiseaseItemDeduceGroupPojo>> webResult = new WebResult<List<DiseaseItemDeduceGroupPojo>>();
        webResult.setSuc(diseaseItemDeduceService.queryDiseaseItemDeduceGroup(condition));
        return webResult;
    }

    /**
     * 添加推断指标组合
     * 
     * @author zcq
     * @param diseaseItemGroup
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_ITEM_GROUP_ADD)
    public @ResponseBody
    WebResult<String> saveDiseaseItemDeduceGroup(DiseaseItemDeduceGroup diseaseItemDeduceGroup) {
        WebResult<String> webResult = new WebResult<String>();
        diseaseItemDeduceService.deleteDiseaseItemDeduceGroupByitemNames(diseaseItemDeduceGroup.getItemNames(),
                diseaseItemDeduceGroup.getDiseaseId());
        webResult.setSuc(diseaseItemDeduceService.addDiseaseItemDeduceGroup(diseaseItemDeduceGroup));
        return webResult;
    }

    /**
     * 删除推断指标组合
     * 
     * @author zcq
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_ITEM_GROUP_DELETE)
    public @ResponseBody
    WebResult<Boolean> deleteDiseaseItemDeduceGroupByitemNames(String itemNames, String diseaseId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        diseaseItemDeduceService.deleteDiseaseItemDeduceGroupByitemNames(itemNames, diseaseId);
        return webResult.setSuc(true);
    }

}
