
package com.mnt.preg.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.system.entity.Menu;
import com.mnt.preg.system.form.MenuForm;
import com.mnt.preg.system.mapping.MenuMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.service.MenuService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 
 * 功能菜单
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-4 zy 初版
 */
@Controller
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    /**
     * 跳转到功能菜单页面
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_VIEW)
    public String toMenuView(Model model) {
        List<ZTree<MenuPojo>> treeList = new ArrayList<ZTree<MenuPojo>>();
        treeList.add(new ZTree<MenuPojo>("000", "功能菜单", true, "home"));
        List<MenuPojo> menuList = menuService.queryMenu();
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (MenuPojo menu : menuList) {
                String menuId = menu.getMenuId();
                ZTree<MenuPojo> tree = new ZTree<MenuPojo>();
                tree.setId(menuId);
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
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        return SystemPageName.MENU_VIEW;
    }

    /**
     * 检索功能菜单
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_QUERY)
    public @ResponseBody
    JSONArray queryMenu() {
        List<ZTree<MenuPojo>> treeList = new ArrayList<ZTree<MenuPojo>>();
        List<MenuPojo> menuList = menuService.queryMenu();
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (MenuPojo menu : menuList) {
                String menuId = menu.getMenuId();
                ZTree<MenuPojo> tree = new ZTree<MenuPojo>();
                tree.setId(menuId);
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
        return NetJsonUtils.listToJsonArray(treeList);
    }

    /**
     * 获取功能菜单信息
     * 
     * @author zcq
     * @param menuId
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_GET)
    public @ResponseBody
    WebResult<MenuPojo> getMenu(String menuId) {
        WebResult<MenuPojo> webResult = new WebResult<MenuPojo>();
        return webResult.setSuc(menuService.getMenu(menuId));
    }

    /**
     * 添加功能菜单
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_ADD)
    public @ResponseBody
    WebResult<ZTree<Menu>> addMenu(MenuForm form) {
        WebResult<ZTree<Menu>> webResult = new WebResult<ZTree<Menu>>();
        Menu menuBo = TransformerUtils.transformerProperties(Menu.class, form);
        menuService.addMenu(menuBo);
        ZTree<Menu> tree = new ZTree<Menu>();
        tree.setId(menuBo.getMenuId());
        tree.setpId(menuBo.getMenuParent());
        tree.setName(menuBo.getMenuName());
        tree.setValue(menuBo);
        if (menuBo.getMenuType() == 1) {
            tree.setIsParent(true);
            tree.setIconSkin("mulu");
        } else {
            tree.setIconSkin("menu");
        }
        return webResult.setSuc(tree);
    }

    /**
     * 修改功能菜单
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateMenu(MenuForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        Menu menuBo = TransformerUtils.transformerProperties(Menu.class, form);
        menuService.updateMenu(menuBo);
        return webResult.setSuc(true);
    }

    /**
     * 修改功能菜单排序
     * 
     * @author zcq
     * @param menuIdList
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_UPDATE_ORDER)
    public @ResponseBody
    WebResult<Boolean> updateMenuOrder(String menuIds) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        menuService.editMenuOrder(Arrays.asList(menuIds.split(",")));
        return webResult.setSuc(true);
    }

    /**
     * 删除功能菜单
     * 
     * @author zcq
     * @param menuId
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeMenu(String menuId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        menuService.deleteMenu(menuId);
        return webResult.setSuc(true);

    }

    /**
     * 校验菜单名称是否可用
     * 
     * @author zcq
     * @param menuName
     * @param type
     * @return
     */
    @RequestMapping(value = MenuMapping.MENU_NAME_VALID)
    public @ResponseBody
    boolean checkMenuNameValid(String menuName, String menuNameOld, String operateType) {
        boolean flag = true;
        int count = menuService.checkMenuNameValid(menuName);
        if ("add".equals(operateType) && count > 0) {
            flag = false;
        }
        if ("update".equals(operateType) && !menuName.equals(menuNameOld) && count > 0) {
            flag = false;
        }
        return flag;
    }

}
