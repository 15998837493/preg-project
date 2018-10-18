
package com.mnt.preg.master.controller.preg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.preg.DietTemplateCondition;
import com.mnt.preg.master.condition.preg.DietTemplateDetailCondition;
import com.mnt.preg.master.entity.basic.FoodTree;
import com.mnt.preg.master.entity.preg.PregDietTemplate;
import com.mnt.preg.master.entity.preg.PregDietTemplateDetail;
import com.mnt.preg.master.form.preg.DietTemplateDetailForm;
import com.mnt.preg.master.form.preg.DietTemplateForm;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.basic.FoodMaterialListInfoPojo;
import com.mnt.preg.master.pojo.basic.TreePojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplateDetailPojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplatePojo;
import com.mnt.preg.master.service.basic.TreeService;
import com.mnt.preg.master.service.preg.PregDietTemplateService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 
 * 食谱模板
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-27 wsy 初版
 */
@Controller
public class DietTemplateController extends BaseController {

    @Resource
    private PregDietTemplateService pregDietTemplateService;

    @Resource
    private TreeService treeService;

    final String treeKind = "food";

    /**
     * 验证模板名称是否重复
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.CHECK_DIET_NAME_VALID)
    @ResponseBody
    public boolean vaildCheckdietName(String dietName, String editFormType) {
        if ("update".equals(editFormType)) {
            return true;
        }
        int index = pregDietTemplateService.validCode(dietName);
        if (index > 0) {
            return false;
        }
        return true;
    }

    /**
     * 移除foodDay
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.REMOVE_FOODDAY_BY_DIETIDANDFOODDAY)
    public @ResponseBody
    WebResult<List<PregDietTemplateDetailPojo>> removeFoodDay(String dietId, String foodDay) {
        WebResult<List<PregDietTemplateDetailPojo>> webResult = new WebResult<List<PregDietTemplateDetailPojo>>();
        pregDietTemplateService.removeFoodDayByCondition(dietId, foodDay);
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 加载代量食物模板天数（foodDay）
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_FOODDAY_BY_DIETID)
    public @ResponseBody
    WebResult<List<PregDietTemplateDetailPojo>> queryDietTemplateDetailNamesByDietId(String dietId) {
        WebResult<List<PregDietTemplateDetailPojo>> webResult = new WebResult<List<PregDietTemplateDetailPojo>>();
        webResult.setSuc(pregDietTemplateService.queryDietTemplateDetailNamesByDietId(dietId));
        return webResult;
    }

    /**
     * 代量食物模板明细
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_FOODDAY_BY_DIETIDANDFOODDAY)
    public @ResponseBody
    WebResult<List<PregDietTemplateDetailPojo>> queryDietTemplateDetailByDietIdAndFoodDay(String dietId, String foodDay) {
        WebResult<List<PregDietTemplateDetailPojo>> webResult = new WebResult<List<PregDietTemplateDetailPojo>>();
        DietTemplateDetailCondition condition = new DietTemplateDetailCondition();
        List<PregDietTemplateDetailPojo> list = new ArrayList<PregDietTemplateDetailPojo>();
        if (StringUtils.isEmpty(dietId) || StringUtils.isEmpty(foodDay)) {
            list = null;
        } else {
            condition.setDietId(dietId);
            condition.setFoodDay(foodDay);
            list = pregDietTemplateService.queryDietTemplateDetailByCondition(condition);
        }
        webResult.setSuc(list);
        return webResult;
    }

    /**
     * 分页检索食物模版
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATE_QUERY)
    public @ResponseBody
    WebResult<Boolean> queryDietTemplate(DietTemplateCondition condition) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<PregDietTemplatePojo> list = pregDietTemplateService.queryDietTemplate(condition);
        webResult.setData(list);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 获取食物模版信息
     * 
     * @author zcq
     * @param dietId
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATE_GET)
    public @ResponseBody
    WebResult<PregDietTemplatePojo> getDietTemplate(String dietId) {
        WebResult<PregDietTemplatePojo> webResult = new WebResult<PregDietTemplatePojo>();
        webResult.setSuc(pregDietTemplateService.getDietTemplate(dietId));
        return webResult;
    }

    /**
     * 添加食物模版
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATE_ADD)
    public @ResponseBody
    WebResult<PregDietTemplatePojo> addDietTemplate(DietTemplateForm form) {
        WebResult<PregDietTemplatePojo> webResult = new WebResult<PregDietTemplatePojo>();
        PregDietTemplate dietTemplate = TransformerUtils.transformerProperties(PregDietTemplate.class, form);
        PregDietTemplatePojo dto = pregDietTemplateService.addDietTemplate(dietTemplate);
        webResult.setSuc(dto);
        return webResult;
    }

    /**
     * 更新食物模版
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATE_UPDATE)
    public @ResponseBody
    WebResult<PregDietTemplatePojo> updateDietTemplate(DietTemplateForm form) {
        WebResult<PregDietTemplatePojo> webResult = new WebResult<PregDietTemplatePojo>();
        PregDietTemplate dietTemplate = TransformerUtils.transformerProperties(PregDietTemplate.class, form);
        PregDietTemplatePojo dto = pregDietTemplateService.updateDietTemplate(dietTemplate);
        webResult.setSuc(dto);
        return webResult;
    }

    /**
     * 删除食物模版
     * 
     * @author zcq
     *         dietIdokbookId
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeDietTemplate(String dietId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregDietTemplateService.removeDietTemplate(dietId);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 食物模版列表明细初始化
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATEDETAIL_INIT)
    public String initDietTemplateDetail(String dietId, String dietName, Model model) {
        model.addAttribute("dietId", dietId);
        model.addAttribute("dietName", dietName);

        // 查询食谱类别
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getMaterialNodes()));
        return MasterPageName.PLAN_DIETTEMPLATE_DETAIL;
    }

    /**
     * 食物模版列表明细
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATEDETAIL_QUERY)
    public @ResponseBody
    WebResult<List<PregDietTemplateDetailPojo>> queryDietTemplateDetail(DietTemplateDetailCondition condition) {
        WebResult<List<PregDietTemplateDetailPojo>> webResult = new WebResult<List<PregDietTemplateDetailPojo>>();
        webResult.setSuc(pregDietTemplateService.queryDietTemplateDetails(condition));
        return webResult;
    }

    /**
     * 获取食物模版列表明细
     * 
     * @author zcq
     * @param dietId
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATEDETAIL_GET)
    public @ResponseBody
    WebResult<PregDietTemplateDetailPojo> getDietTemplateDetail(String id) {
        WebResult<PregDietTemplateDetailPojo> webResult = new WebResult<PregDietTemplateDetailPojo>();
        webResult.setSuc(pregDietTemplateService.getDietTemplateDetail(id));
        return webResult;
    }

    /**
     * 添加食物模版明细
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATEDETAIL_ADD)
    public @ResponseBody
    WebResult<List<PregDietTemplateDetailPojo>> addDietTemplateDetail(DietTemplateDetailForm form) {
        WebResult<List<PregDietTemplateDetailPojo>> webResult = new WebResult<List<PregDietTemplateDetailPojo>>();
        PregDietTemplateDetail dietTemplateDetail = TransformerUtils.transformerProperties(PregDietTemplateDetail.class, form);

        if (StringUtils.isNotBlank(dietTemplateDetail.getFoodId())) {
            List<FoodMaterialListInfoPojo> list = foodService.queryFoodMaterialListByFoodId(dietTemplateDetail.getFoodId());
            // 查询菜谱对应食材(逐条添加)
            if (!CollectionUtils.isEmpty(list)) {
                for (FoodMaterialListInfoPojo foodMaterialListInfoPojo : list) {
                    dietTemplateDetail = TransformerUtils.transformerProperties(PregDietTemplateDetail.class, form);
                    dietTemplateDetail.setFmId(foodMaterialListInfoPojo.getFmId());
                    dietTemplateDetail.setFoodMaterialName(foodMaterialListInfoPojo.getFmName());
                    dietTemplateDetail.setFlag(1);
                    pregDietTemplateService.addDietTemplateDetail(dietTemplateDetail);
                }
            } else {// 添加单一菜品
                dietTemplateDetail.setFlag(1);
                pregDietTemplateService.addDietTemplateDetail(dietTemplateDetail);
            }
        }

        List<PregDietTemplateDetailPojo> list = new ArrayList<PregDietTemplateDetailPojo>();
        // DietTemplateDetailCondition condition = new DietTemplateDetailCondition();
        // if (StringUtils.isEmpty(form.getDietId()) || StringUtils.isEmpty(form.getFoodDay())) {
        // list = null;
        // } else {
        // condition.setDietId(form.getDietId());
        // condition.setFoodDay(form.getFoodDay());
        // list = pregDietTemplateService.queryDietTemplateDetailByCondition(condition);
        // }
        webResult.setSuc(list);
        return webResult;
    }

    /**
     * 更新食物模版明细
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATEDETAIL_UPDATE)
    public @ResponseBody
    WebResult<List<PregDietTemplateDetailPojo>> updateDietTemplateDetail(DietTemplateDetailForm form) {
        WebResult<List<PregDietTemplateDetailPojo>> webResult = new WebResult<List<PregDietTemplateDetailPojo>>();

        // 修改能量范围
        if (StringUtils.isBlank(form.getId())) {
            DietTemplateDetailCondition condition = new DietTemplateDetailCondition();
            condition.setDietId(form.getDietId());
            condition.setFoodDay(form.getFoodDay());
            condition.setFlag(1);
            List<PregDietTemplateDetailPojo> list = pregDietTemplateService.queryDietTemplateDetailByCondition(condition);
            if (!CollectionUtils.isEmpty(list)) {
                for (PregDietTemplateDetailPojo pojo : list) {
                    PregDietTemplateDetail diet = TransformerUtils.transformerProperties(PregDietTemplateDetail.class, pojo);
                    diet.setAmountLevel(form.getAmountLevel());
                    pregDietTemplateService.updateDietTemplateDetail(diet);
                }
            }
        } else { // 修改用量
            PregDietTemplateDetail diet = TransformerUtils.transformerProperties(PregDietTemplateDetail.class, form);
            pregDietTemplateService.updateDietTemplateDetail(diet);
        }

        List<PregDietTemplateDetailPojo> list = new ArrayList<PregDietTemplateDetailPojo>();
        // DietTemplateDetailCondition condition = new DietTemplateDetailCondition();
        // if (StringUtils.isEmpty(form.getDietId()) || StringUtils.isEmpty(form.getFoodDay())) {
        // list = null;
        // } else {
        // condition.setDietId(form.getDietId());
        // condition.setFoodDay(form.getFoodDay());
        // list = pregDietTemplateService.queryDietTemplateDetailByCondition(condition);
        // }
        webResult.setSuc(list);
        return webResult;
    }

    /**
     * 删除食物模版明细
     * 
     * @author zcq
     * @param dietId
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_DIETTEMPLATEDETAIL_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeDietTemplateDetail(DietTemplateDetailForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 查询菜谱对应的数据
        DietTemplateDetailCondition condition = TransformerUtils.transformerProperties(DietTemplateDetailCondition.class, form);
        List<PregDietTemplateDetailPojo> list = pregDietTemplateService.queryDietTemplateDetails(condition);
        if (!CollectionUtils.isEmpty(list)) {
            for (PregDietTemplateDetailPojo pojo : list) {
                pregDietTemplateService.removeDietTemplateDetail(pojo.getId());
            }
        }
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 获取食谱类别
     * 
     * @author mlq
     * @return
     */
    private List<ZTree<TreePojo>> getMaterialNodes() {
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        treeList.add(new ZTree<TreePojo>("000", "食谱类别", true, "home"));
        FoodTree condition = new FoodTree();
        condition.setTreeKind("food");
        List<TreePojo> menuList = treeService.queryTreeByCondition(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        return treeList;
    }

    /**
     * 获取树
     * 
     * @param menuList
     * @return
     */
    protected List<ZTree<TreePojo>> getTrees(List<TreePojo> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        for (TreePojo productCatalog : menuList) {
            String catalogId = productCatalog.getTreeId();
            ZTree<TreePojo> tree = new ZTree<TreePojo>();
            tree.setId(catalogId);
            tree.setpId(productCatalog.getParentTreeId());
            tree.setName(productCatalog.getTreeName());
            tree.setValue(productCatalog);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                tree.setIsParent(true);
                tree.setIconSkin("mulu");
            } else {
                tree.setIconSkin("menu");
            }
            treeList.add(tree);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                treeList.addAll(this.getTrees(productCatalog.getChildList()));
            }
        }
        return treeList;
    }
}
