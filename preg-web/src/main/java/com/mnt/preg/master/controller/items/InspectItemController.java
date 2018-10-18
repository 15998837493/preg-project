
package com.mnt.preg.master.controller.items;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.master.condition.items.InspectItemCondition;
import com.mnt.preg.master.form.items.InspectItemForm;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.mapping.items.ItemsMapping;
import com.mnt.preg.master.pojo.items.InspectItemPojo;
import com.mnt.preg.master.service.items.InspectItemService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.mapping.CommonPageName;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 检查项目相关Controller
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-6-12 gss 初版
 */
@Controller
public class InspectItemController extends BaseController {

    @Resource
    private InspectItemService inspectItemService;

    @Resource
    private ItemService itemService;

    /**
     * 
     * 检查项目增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = ItemsMapping.ADD_INIT_INSPECTITEM)
    public String initAddInspectItem(HttpServletRequest request, Model model) {
        return MasterPageName.INSPECTITEM_INIT_ADD;
    }

    /**
     * 
     * 异步获取检查项目信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECTITEM_QUERY)
    public @ResponseBody
    WebResult<InspectItemPojo> queryInspectItem(InspectItemCondition condition) {
        WebResult<InspectItemPojo> webResult = new WebResult<InspectItemPojo>();
        webResult.setData(inspectItemService.queryInspectItem(condition));
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 添加检查项目信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = ItemsMapping.ADD_INSPECTITEM)
    public @ResponseBody
    WebResult<InspectItemPojo> addInspectItem(InspectItemForm form) {
        WebResult<InspectItemPojo> webResult = new WebResult<InspectItemPojo>();
        InspectItemPojo inspectItemDto = TransformerUtils.transformerProperties(InspectItemPojo.class, form);
        inspectItemDto.setInspectQuanpin(HanyuToPinyin.getInstance().getFullStringPinYin(form.getInspectName()));
        String abbr = HanyuToPinyin.getInstance().getAbbrStringPinYin(form.getInspectName());
        // 如果缩写的长度仍旧超过abbr数据库最大长度，截取30个字符。
        if (abbr.length() > 30) {
            abbr = abbr.substring(0, 30);
        }
        inspectItemDto.setInspectAcronym(abbr);
        String inspectId = inspectItemService.saveInspectItem(inspectItemDto);
        webResult.setSuc(inspectItemService.getInspectItemById(inspectId));
        return webResult;
    }

    /**
     * 
     * 检查项目修改初始化
     * 
     * @param request
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = ItemsMapping.UPDATE_INIT_INSPECTITEM)
    public String updateInspectItemInit(HttpServletRequest request, @RequestParam String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            model.addAttribute("error_msg", "主键不能为空");
            return CommonPageName.ERROR_PAGE;
        }
        InspectItemPojo inspectItemDto = new InspectItemPojo();
        inspectItemDto = inspectItemService.getInspectItemById(id);

        if (inspectItemDto == null) {
            inspectItemDto = new InspectItemPojo();
        }
        InspectItemForm inspectItemForm = TransformerUtils
                .transformerProperties(InspectItemForm.class, inspectItemDto);
        model.addAttribute("inspectItemForm", inspectItemForm);
        return MasterPageName.INSPECTITEM_INIT_UPDATE;
    }

    /**
     * 配置项目修改初始化
     * 
     * @param request
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = ItemsMapping.CONFIG_INIT_INSPECTITEM)
    public String configInspectItemInit(HttpServletRequest request, @RequestParam String inspectId, Model model) {
        if (StringUtils.isEmpty(inspectId)) {
            model.addAttribute("error_msg", "主键不能为空");
            return CommonPageName.ERROR_PAGE;
        }
        InspectItemPojo inspectItemDto = new InspectItemPojo();
        inspectItemDto = inspectItemService.getInspectItemById(inspectId);

        if (inspectItemDto == null) {
            inspectItemDto = new InspectItemPojo();
        }
        InspectItemForm inspectItemForm = TransformerUtils
                .transformerProperties(InspectItemForm.class, inspectItemDto);
        model.addAttribute("inspectItemForm", inspectItemForm);
        return MasterPageName.INSPECTITEM_INIT_CONFIG;
    }

    /**
     * 修改检查项目信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECTITEM_UPDATE)
    public @ResponseBody
    WebResult<InspectItemPojo> updateInspectItem(InspectItemForm form) {
        WebResult<InspectItemPojo> webResult = new WebResult<InspectItemPojo>();
        InspectItemPojo inspectItemDto = TransformerUtils.transformerProperties(InspectItemPojo.class, form);
        inspectItemDto.setInspectQuanpin(HanyuToPinyin.getInstance().getFullStringPinYin(form.getInspectName()));
        inspectItemDto.setInspectAcronym(HanyuToPinyin.getInstance().getAbbrStringPinYin(form.getInspectName()));
        inspectItemService.updateInspectItem(inspectItemDto, form.getInspectId());
        webResult.setSuc(inspectItemDto);
        return webResult;
    }

    /**
     * 
     * 删除检查项目信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.REMOVE_INSPECTITEM)
    public @ResponseBody
    WebResult<Boolean> removeInspectItem(String inspectId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        inspectItemService.removeInspectItem(inspectId);
        webResult.setSuc(true, "成功");
        return webResult;
    }

    /**
     * 
     * 验证主键是否重复
     * 
     * @author gss
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.INSPECTITEM_VALIDATE)
    public @ResponseBody
    boolean inspectItemCodeValidate(String inspectCode, String inspectOldCode, String type) {
        boolean typeIndex = "add".equals(type);
        if (!typeIndex && inspectCode.equals(inspectOldCode)) {
            return true;
        }
        int index = inspectItemService.checkInspctItemCodeValid(inspectCode);
        if (index < 1) {
            return true;
        }
        return false;
    }

}
