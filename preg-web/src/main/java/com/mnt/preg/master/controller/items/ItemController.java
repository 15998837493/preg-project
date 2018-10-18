
package com.mnt.preg.master.controller.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.items.ItemCondition;
import com.mnt.preg.master.form.items.ItemForm;
import com.mnt.preg.master.mapping.items.ItemPageName;
import com.mnt.preg.master.mapping.items.ItemsMapping;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 体检项目字典Controller
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
@Controller
public class ItemController extends BaseController {

    @Resource
    private ItemService itemService;

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    /**
     * 
     * 跳转检查项目配置页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = ItemsMapping.HOSPITAL_INSPECT_VIEW)
    public String initAddInspectItem(Model model) {
        List<ItemPojo> itemList = itemService.queryItemType();
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getItemTypeList(itemList)));
        return ItemPageName.HOSPITAL_INSPECT_VIEW;
    }

    /**
     * 获取指标分类
     * 
     * 
     * @author xdc
     * @return
     */
    private List<ZTree<String>> getItemTypeList(List<ItemPojo> itemList) {
        List<ZTree<String>> treeList = new ArrayList<ZTree<String>>();
        treeList.add(new ZTree<String>("000", "实验室", true, "home"));
        List<String> itemExistType = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(itemList)) {
            for (ItemPojo itemPojo : itemList) {
                if (!itemExistType.contains(itemPojo.getItemType())) {
                    ZTree<String> treeP = new ZTree<String>();
                    treeP.setId(itemPojo.getItemType());
                    treeP.setpId("000");
                    treeP.setName(itemPojo.getItemType());
                    treeP.setValue(itemPojo.getItemType());
                    treeP.setIsParent(true);
                    treeP.setIconSkin("mulu");
                    treeList.add(treeP);
                }
                ZTree<String> tree = new ZTree<String>();
                tree.setId(itemPojo.getItemClassify());
                tree.setpId(itemPojo.getItemType());
                tree.setName(itemPojo.getItemClassify());
                tree.setValue(itemPojo.getItemClassify());
                tree.setIconSkin("menu");
                treeList.add(tree);
                itemExistType.add(itemPojo.getItemType());
            }
        }
        return treeList;
    }

    /**
     * 
     * 验证编码重复
     * 
     * @author xdc
     * @param itemCode
     * @param itemId
     * @param inspectId
     * @return
     */
    @RequestMapping(value = ItemsMapping.ITEMNAME_VALID)
    public @ResponseBody
    boolean checkItemName(String itemName, String itemId, String itemType, String itemClassify) {
        return this.check(itemName, itemId, itemType, itemClassify, "name");
    }

    /**
     * 
     * 一步获取指标信息
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.ITEM_QUERY)
    public @ResponseBody
    WebResult<ItemPojo> queryItem(ItemCondition condition) {
        WebResult<ItemPojo> webResult = new WebResult<ItemPojo>();
        webResult.setData(itemService.queryItemByCondition(condition));
        return webResult;
    }

    /**
     * 
     * 删除指标
     * 
     * @author scd
     * @param request
     * @param itemId
     * @return
     */
    @RequestMapping(value = ItemsMapping.ITEM_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeItem(HttpServletRequest request, @RequestParam String itemId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (!StringUtils.isEmpty(itemId)) {
            try {
                itemService.removeItem(itemId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            webResult.setSuc(true, WebMsgConstant.success_message);
        } else {
            webResult.setError(WebMsgConstant.fail_message, false);
        }

        return webResult;
    }

    /**
     * 
     * 添加指标
     * 
     * @param request
     * @param itemForm
     * @return
     */
    @RequestMapping(value = ItemsMapping.ITEM_ADD)
    public @ResponseBody
    WebResult<ItemPojo> addItem(HttpServletRequest request, ItemForm itemForm) {
        WebResult<ItemPojo> webResult = new WebResult<ItemPojo>();
        // 自动生成编码
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        itemForm.setItemCode(itemService.getMaxItemCode(insId));
        ItemPojo itemDto = TransformerUtils.transformerProperties(ItemPojo.class, itemForm);
        String itemId = itemService.saveItem(itemDto);
        itemDto = itemService.getItemByItemId(itemId);
        webResult.setSuc(itemDto, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 修改指标
     * 
     * @author scd
     * @param request
     * @param itemForm
     * @return
     */
    @RequestMapping(value = ItemsMapping.ITEM_UPDATE)
    public @ResponseBody
    WebResult<ItemPojo> itemUpdate(HttpServletRequest request, ItemForm itemForm) {
        WebResult<ItemPojo> webResult = new WebResult<ItemPojo>();
        if (StringUtils.isNotEmpty(itemForm.getItemId())) {
            ItemPojo itemDto = TransformerUtils.transformerProperties(ItemPojo.class, itemForm);
            itemService.updateItem(itemDto, itemForm.getItemId());
            itemDto = itemService.getItemByItemId(itemForm.getItemId());
            webResult.setSuc(itemDto, WebMsgConstant.success_message);
        } else {
            webResult.setError(WebMsgConstant.fail_message);
        }
        return webResult;
    }

    /**
     * 指标排序
     * 
     * @author xdc
     * @param itemIds
     * @return
     */
    @RequestMapping(value = ItemsMapping.ITEM_ORDER)
    public @ResponseBody
    WebResult<Boolean> itemOrder(String itemIds) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        itemService.editItemOrder(itemIds);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 指标表类别修改
     * 
     * @author xdc
     * @param itemIds
     * @return
     */
    @RequestMapping(value = ItemsMapping.UPDATE_ITEM_TYPE)
    public @ResponseBody
    WebResult<Boolean> itemOrder(String oldType, String newType, String type, String pType) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        itemService.updateItemType(oldType, newType, type, pType);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 指标表类别修改
     * 
     * @author xdc
     * @param itemIds
     * @return
     */
    @RequestMapping(value = ItemsMapping.REMOVE_ITEM_BY_TYPE)
    public @ResponseBody
    WebResult<Boolean> removeItemByType(String type, String itemType, String itemClassify) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        itemService.removeItemByType(type, itemType, itemClassify);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 验证编码重复
     * 
     * @author dhs
     * @param value
     *            (需要验证的值)
     * @param id
     *            (itemId)
     * @param type
     *            (目前只有编码和名称，如果再有别的需要验证唯一，完善type即可)
     * @return 不重复返回true，重复返回false
     */
    private boolean check(String value, String id, String itemType, String itemClassify, String type) {
        boolean flag;
        int index = 0;
        if ("code".equals(type)) {
            index = itemService.checkItemCode(value.trim());
        } else if ("name".equals(type)) {
            index = itemService.checkItemName(value.trim(), itemType, itemClassify);
        }
        if (id.isEmpty()) {// 添加
            if (index == 0) {
                flag = true;
            } else {
                flag = false;
            }
        } else {// 修改
            ItemCondition condition = new ItemCondition();
            condition.setItemId(id);
            String oldValue = null;
            if ("code".equals(type)) {
                oldValue = itemService.queryItemByCondition(condition).get(0).getItemCode();
            } else if ("name".equals(type)) {
                oldValue = itemService.queryItemByCondition(condition).get(0).getItemName();
            }
            if (oldValue.equals(value.trim())) {
                flag = true;
            } else {
                if (index == 0) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }
}
