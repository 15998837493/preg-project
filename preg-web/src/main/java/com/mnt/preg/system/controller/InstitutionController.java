/*
 * InstitutionController.java	 1.0   2014-12-30
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.system.condition.InstitutionCondition;
import com.mnt.preg.system.entity.Institution;
import com.mnt.preg.system.form.InstitutionForm;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.InstitutionPojo;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 机构管理Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-30 zcq 初版
 */
@Controller
public class InstitutionController extends BaseController {

    /**
     * 跳转到公司列表页面并初始化菜单
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.INS_VIEW)
    public String toInsView(Model model) {
        List<ZTree<MenuPojo>> treeList = new ArrayList<ZTree<MenuPojo>>();
        treeList.add(new ZTree<MenuPojo>("000", "功能菜单", true, "home"));
        List<MenuPojo> menuList = menuService.getCatalogMenu();
        if (CollectionUtils.isNotEmpty(menuList)) {
            setAllMenu(menuList, treeList);
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        model.addAttribute("printListMap", printService.getPrintListMap());
        return SystemPageName.INS_VIEW;
    }

    /**
     * 检索机构信息列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.INS_QUERY)
    public @ResponseBody
    WebResult<List<InstitutionPojo>> queryIns(InstitutionCondition condition) {
        WebResult<List<InstitutionPojo>> webResult = new WebResult<List<InstitutionPojo>>();
        List<InstitutionPojo> list = institutionService.queryIns(condition);
        webResult.setData(list);
        return webResult;
    }

    /**
     * 添加机构加盟商
     * 
     * @param request
     * @param instForm
     * @param model
     * @return
     * @throws IOException
     * 
     */
    @RequestMapping(value = SystemMapping.INS_ADD)
    public @ResponseBody
    WebResult<Boolean> addIns(InstitutionForm insForm, Model model) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 保存
        Institution insBo = new Institution();
        BeanUtils.copyProperties(insForm, insBo);
        try {
            String insLogo = insForm.getInsLogo();
            String insId = insForm.getInsId();
            if (StringUtils.isNotEmpty(insLogo)) {
                // 设置图片信息
                insBo.setInsLogo(saveImage(insId, insLogo));
            }
            institutionService.addIns(insBo);
            // 复制默认图片
            String tempLogoPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_logo;
            String newFilePath = tempLogoPath + insId;
            String oldPatht = publicConfig.getResourceAbsolutePath() + PathConstant.template_logo +
                    "defaultlogo/front-cover-logo.png";
            String oldPathb = publicConfig.getResourceAbsolutePath() + PathConstant.template_logo +
                    "defaultlogo/back-cover-logo.png";
            String destb = newFilePath + "/front-cover-logo.png";
            String destt = newFilePath + "/back-cover-logo.png";
            FileUtils.copyFile(new File(oldPatht), new File(destb));
            FileUtils.copyFile(new File(oldPathb), new File(destt));
            webResult.setSuc(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webResult;
    }

    /**
     * 保存图片信息
     * 
     * @author zcq
     * @param insId
     * @param insLogo
     * @return picName
     * @throws IOException
     */
    private String saveImage(String insId, String insLogo) throws IOException {
        // 图片名称
        String picName = insId + "_logo" + insLogo.substring(insLogo.lastIndexOf("."));
        // 临时文件绝对路径
        String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                + insLogo.substring(insLogo.lastIndexOf("/") + 1);
        // 目标文件路径
        String destPath = publicConfig.getResourceAbsolutePath() + PathConstant.ins_logo_image + picName;
        // 删除原目录下的logo图片
        FileUtils.deleteQuietly(new File(destPath));
        // 移动新logo图片到目标文件夹下
        FileUtils.moveFile(new File(fromPath), new File(destPath));

        return picName;
    }

    /**
     * 初始化机构信息
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.INS_UPDATE_INIT)
    public String initEditIns(String insId, Model model) {
        InstitutionPojo insDto = institutionService.getIns(insId);
        model.addAttribute("insVo", insDto);
        return SystemPageName.INS_UPDATE;
    }

    /**
     * 修改机构配置
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.INS_CONFIG)
    public String initEditIns(Model model) {
        InstitutionPojo insDto = institutionService.getIns(this.getLoginUser().getUserPojo().getCreateInsId());
        model.addAttribute("insVo", insDto);
        return SystemPageName.INS_CONFIG;
    }

    /**
     * 更改机构加盟商
     * 
     * @param request
     * @param instForm
     * @param model
     * @return
     * @throws ParseException
     * 
     */
    @RequestMapping(value = SystemMapping.INS_UPDATE)
    public @ResponseBody
    WebResult<InstitutionPojo> editIns(InstitutionForm insForm, Model model) throws ParseException {
        WebResult<InstitutionPojo> webResult = new WebResult<InstitutionPojo>();
        Institution insBo = new Institution();
        BeanUtils.copyProperties(insForm, insBo);
        try {
            String insLogo = insForm.getInsLogo();
            String insId = insForm.getInsId();
            if (StringUtils.isNotEmpty(insLogo) && insLogo.indexOf("/") > -1) {
                // 设置图片信息
                insBo.setInsLogo(saveImage(insId, insLogo));
            }
            institutionService.editIns(insBo);
            InstitutionPojo insDto = institutionService.getIns(insId);
            webResult.setSuc(insDto, WebMsgConstant.success_message);
        } catch (IOException e) {
            e.printStackTrace();
            webResult.setError("修改机构失败 ！");
        }
        return webResult;
    }

    /**
     * 删除机构加盟商
     * 
     * @param request
     * @param insCode
     * @param model
     * @return
     * 
     */
    @RequestMapping(value = SystemMapping.INS_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeIns(String insId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        Institution insInfo = new Institution();
        insInfo.setInsId(insId);
        insInfo.setFlag(0);
        institutionService.editIns(insInfo);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 更改机构运行状态
     * 
     * @author zcq
     * @param insId
     * @param insState
     * @return
     */
    @RequestMapping(value = SystemMapping.INS_STATE)
    public @ResponseBody
    WebResult<Boolean> editInsState(String insId, Integer insState) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        Institution insBo = new Institution();
        insBo.setInsId(insId);
        insBo.setInsState(insState);
        institutionService.editIns(insBo);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 获取机构功能菜单
     * 
     * @param request
     * @param condition
     * @return
     * 
     */
    @RequestMapping(value = SystemMapping.INS_MENU)
    public @ResponseBody
    WebResult<List<MenuPojo>> queryInsMenu(String insId) {
        WebResult<List<MenuPojo>> webResult = new WebResult<List<MenuPojo>>();
        webResult.setSuc(institutionService.queryInsMenu(insId));
        return webResult;
    }

    /**
     * 获取机构功能菜单
     * 
     * @param request
     * @param condition
     * @return
     * 
     */
    @RequestMapping(value = SystemMapping.INS_MENU_SAVE)
    public @ResponseBody
    WebResult<Boolean> saveInsMenu(String insId, String menuIdStr) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isEmpty(menuIdStr)) {
            webResult.setError("没有选择功能菜单！");
            return webResult;
        }
        institutionService.saveInsMenu(insId, Arrays.asList(menuIdStr.split(",")));
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 校验机构编码是否存在
     * 
     * @author zcq
     * @param insId
     * @return
     */
    @RequestMapping(value = SystemMapping.INSID_CHECK)
    public @ResponseBody
    boolean checkInsId(String insId) {
        boolean flag = true;
        Integer count = institutionService.checkInsIdValid(insId);
        if (count > 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 设置可用功能菜单
     * 
     * @author zcq
     * @param menuList
     * @param treeList
     */
    private void setAllMenu(List<MenuPojo> menuList, List<ZTree<MenuPojo>> treeList) {
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (MenuPojo menu : menuList) {
                setAllMenu(menu.getChildList(), treeList);
                ZTree<MenuPojo> tree = new ZTree<MenuPojo>();
                tree.setId(menu.getMenuId());
                tree.setpId(menu.getMenuParent());
                tree.setName(menu.getMenuName());
                tree.setValue(menu);
                if (menu.getMenuType() == 1) {
                    tree.setIsParent(true);
                    tree.setIconSkin("mulu");
                } else {
                    tree.setIconSkin("menu");
                }
                treeList.add(tree);
            }
        }
    }

    /**
     * 校验机构名称是否可用
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.INSNAME_CHECK)
    public @ResponseBody
    boolean checkInsNameValid(String operateType, InstitutionForm form) {
        boolean flag = true;
        Institution insBo = new Institution();
        insBo.setInsName(form.getInsName());
        Integer count = institutionService.checkInsNameValid(insBo.getInsName());
        if (count > 0) {
            if ("add".equals(operateType)) {
                flag = false;
            }
            if ("update".equals(operateType) && !form.getInsName().equals(form.getInsNameOld())) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 查询机构打印选项配置
     * 
     * @author zcq
     * @param insId
     * @return
     */
    @RequestMapping(value = SystemMapping.INS_PRINT)
    public @ResponseBody
    WebResult<List<String>> queryInsPrint(String insId) {
        WebResult<List<String>> webResult = new WebResult<List<String>>();
        webResult.setSuc(institutionService.queryInsPrintIds(insId));
        return webResult;
    }

    /**
     * 保存机构打印选项配置
     * 
     * @author zcq
     * @param insId
     * @param printIdStr
     * @return
     */
    @RequestMapping(value = SystemMapping.INS_PRINT_SAVE)
    public @ResponseBody
    WebResult<Boolean> saveInsPrint(String insId, String printIdStr) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isBlank(printIdStr)) {
            webResult.setError("没有选择打印选项！");
            return webResult;
        }
        institutionService.saveInsPrint(insId, Arrays.asList(printIdStr.split(",")));
        webResult.setSuc(true);
        return webResult;
    }

}
