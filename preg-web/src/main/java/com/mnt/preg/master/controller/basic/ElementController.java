
package com.mnt.preg.master.controller.basic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.master.entity.basic.Element;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.basic.ElementPojo;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 元素Controller
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-11 gss 初版
 */
@Controller
public class ElementController extends BaseController {

    /**
     * 
     * 元素增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.INIT_NUTRIENT)
    public String initAddNutrient(HttpServletRequest request, Model model) {
        List<CodePojo> nutrientTypes = codeCache.getCodeListCache("NUTRIENTTYPE", "NUTRIENTTYPE");
        model.addAttribute("nutrientTypes", nutrientTypes);
        List<CodePojo> productUnits = codeCache.getCodeListCache("PRODUCTUNIT", "PRODUCTUNIT");
        model.addAttribute("productUnits", productUnits);
        List<CodePojo> torfs = codeCache.getCodeListCache("TRUEORFALSE", "TRUEORFALSE");
        model.addAttribute("torfs", torfs);
        return MasterPageName.NUTRIENT_VIEW;
    }

    /**
     * 添加元素信息
     * 
     * @author gss
     * @param nutrientBo
     * @return
     */
    @RequestMapping(value = MasterMapping.ADD_NUTRIENT)
    public @ResponseBody
    WebResult<ElementPojo> addNutrient(Element element) {
        WebResult<ElementPojo> webResult = new WebResult<ElementPojo>();
        webResult.setSuc(elementService.saveNutrient(element), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 修改元素信息
     * 
     * @author gss
     * @param nutrientBo
     * @return
     */
    @RequestMapping(value = MasterMapping.UPDATE_NUTRIENT)
    public @ResponseBody
    WebResult<ElementPojo> updateNutrient(Element nutrient) {
        WebResult<ElementPojo> webResult = new WebResult<ElementPojo>();
        webResult.setSuc(elementService.updateNutrient(nutrient), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 删除元素信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.REMOVE_NUTRIENT)
    public @ResponseBody
    WebResult<Boolean> removeNutrient(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        elementService.removeNutrient(id);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 检索元素信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_NUTRIENT)
    public @ResponseBody
    WebResult<List<ElementPojo>> queryNutrient(Element condition) {
        WebResult<List<ElementPojo>> webResult = new WebResult<List<ElementPojo>>();
        List<ElementPojo> elementList = elementService.queryNutrient(condition);
        webResult.setData(elementList);
        webResult.setSuc(elementList);
        return webResult;
    }

    /**
     * 
     * 验证主键是否重复
     * 
     * @author gss
     * @param nutrientId
     * @return
     */
    @RequestMapping(value = MasterMapping.NUTRIENT_VALIDATE)
    public @ResponseBody
    boolean nutrientValidate(String nutrientId) {
        ElementPojo nutrient = elementService.getNutrientByNutrientId(nutrientId);
        if (nutrient == null) {
            return true;
        } else {
            return false;
        }

    }
}
