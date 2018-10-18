
package com.mnt.preg.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.system.entity.Right;
import com.mnt.preg.system.form.RightForm;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.RightPojo;
import com.mnt.preg.system.service.MenuService;
import com.mnt.preg.system.service.RightService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 权限管理Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-26 zcq 初版
 */
@Controller
public class RightController extends BaseController {

    @Resource
    private RightService rightService;

    @Resource
    private MenuService menuService;

    /**
     * 跳转到权限一览页面
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.RIGHT_QUERY_VIEW)
    public String toRightView(Model model) {
        List<ZTree<MenuPojo>> treeList = new ArrayList<ZTree<MenuPojo>>();

        treeList.add(new ZTree<MenuPojo>("000", "功能菜单", true, "home"));
        List<MenuPojo> menuList = institutionService.queryInsMenu(this.getLoginUser().getUserPojo().getCreateInsId());
        if (CollectionUtils.isNotEmpty(menuList)) {
            setAllMenu(menuList, treeList);
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        return SystemPageName.RIGHT_VIEW;
    }

    /**
     * 检索权限
     * 
     * @param request
     * @param condition
     *            检索条件
     * @return
     */
    @RequestMapping(value = SystemMapping.RIGHT_QUERY)
    public @ResponseBody
    WebResult<RightPojo> queryRight(Right right) {
        WebResult<RightPojo> webResult = new WebResult<RightPojo>();
        webResult.setData(rightService.queryRight(right));
        return webResult;
    }

    /**
     * 权限组增加页面初始化
     * 
     * @param request
     *            请求
     * @param model
     * @return 跳转页
     */
    @RequestMapping(value = SystemMapping.RIGHT_INIT_ADD)
    public String initAddRight(Model model) {
        List<ZTree<MenuPojo>> treeList = new ArrayList<ZTree<MenuPojo>>();
        treeList.add(new ZTree<MenuPojo>("000", "功能菜单", true, "home"));
        List<MenuPojo> menuList = menuService.getCatalogMenu();
        if (CollectionUtils.isNotEmpty(menuList)) {
            setAllMenu(menuList, treeList);
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        return SystemPageName.RIGHT_ADD;
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
     * 增加权限组请求
     * 
     * @param rightForm
     *            权限表单
     * @param model
     * @return WebResult
     */
    @RequestMapping(value = SystemMapping.RIGHT_ADD)
    public @ResponseBody
    WebResult<RightPojo> addRight(RightForm rightForm, Model model) {
        WebResult<RightPojo> webResult = new WebResult<RightPojo>();
        String menuIdStr = rightForm.getMenuIdStr();
        if (StringUtils.isEmpty(menuIdStr)) {
            webResult.setError("未选择功能菜单！");
            return webResult;
        }
        Right right = new Right();
        BeanUtils.copyProperties(rightForm, right);
        List<String> list = Arrays.asList(menuIdStr.split(","));
        String rightId = rightService.addRight(right, list);
        RightPojo rightDto = rightService.getRight(rightId);
        webResult.setSuc(rightDto, "操作成功");
        return webResult;
    }

    /**
     * 修改页面初始化
     * 
     * @param rightId
     *            权限组ID
     * @param model
     * @return 跳转页
     */
    @RequestMapping(value = SystemMapping.RIGHT_INIT_UPDATE)
    public @ResponseBody
    WebResult<RightPojo> initUpdateRight(String rightId) {
        WebResult<RightPojo> webResult = new WebResult<RightPojo>();
        webResult.setSuc(rightService.getRight(rightId));
        return webResult;
    }

    /**
     * 更新操作
     * 
     * @param rightForm
     *            权限表单
     * @return WebResult
     */
    @RequestMapping(value = SystemMapping.RIGHT_UPDATE)
    public @ResponseBody
    WebResult<RightPojo> updateRight(RightForm rightForm) {
        WebResult<RightPojo> webResult = new WebResult<RightPojo>();
        String menuIdStr = rightForm.getMenuIdStr();
        if (StringUtils.isEmpty(menuIdStr)) {
            webResult.setError("未选择功能菜单！");
            return webResult;
        }
        Right right = new Right();
        BeanUtils.copyProperties(rightForm, right);
        rightService.updateRight(right, Arrays.asList(menuIdStr.split(",")));
        webResult.setSuc(rightService.getRight(rightForm.getRightId()));
        return webResult;
    }

    /**
     * 删除操作
     * 
     * @author zcq
     * @param rightId
     * @return
     */
    @RequestMapping(value = SystemMapping.RIGHT_DEL)
    public @ResponseBody
    WebResult<Boolean> removeRight(String rightId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        rightService.removeRight(rightId);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 校验权限名称是否可用
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.RIGHT_NAME_CHECK)
    public @ResponseBody
    boolean checkRightNameValid(String operateType, String rightName, String rightNameOld) {
        boolean flag = true;
        Integer count = rightService.checkRightNameValid(rightName);
        if (count > 0) {
            if ("add".equals(operateType)) {
                flag = false;
            }
            if ("update".equals(operateType) && !rightName.equals(rightNameOld)) {
                flag = false;
            }
        }
        return flag;
    }
}
