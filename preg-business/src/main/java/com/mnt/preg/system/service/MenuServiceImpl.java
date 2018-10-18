
package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.system.dao.MenuDao;
import com.mnt.preg.system.dao.RoleDao;
import com.mnt.preg.system.entity.Menu;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.pojo.MenuPojo;

/**
 * 功能菜单事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 zcq 初版
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public List<MenuPojo> queryMenu() {
        return menuDao.queryMenu();
    }

    @Override
    @Transactional(readOnly = true)
    public MenuPojo getMenu(String menuId) {
        return menuDao.getTransformerBean(menuId, Menu.class, MenuPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkMenuIdValid(String menuId) {
        return menuDao.checkMenuIdValid(menuId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkMenuNameValid(String menuName) {
        return menuDao.checkMenuNameValid(menuName);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addMenu(Menu menu) {
        // 主键：三位一级，父级编码+排序（补充到三位），例如 一级=001，二级=001001
        menu.setMenuId(createMenuId(menu.getMenuParent()));
        if (menuDao.checkMenuIdValid(menu.getMenuId()) > 0) {
            throw new ServiceException(ResultCode.ERROR_90002);
        }
        return (String) menuDao.save(menu);
    }

    /**
     * 生成功能菜单主键
     * 
     * @author zcq
     * @param menuParent
     * @return
     */
    private String createMenuId(String menuParent) {
        String maxMenuId = (String) menuDao.getSonMaxMenuId(menuParent);
        String code = StringUtils.isEmpty(maxMenuId) ? "001" : String.valueOf(Integer.valueOf(maxMenuId.substring(
                maxMenuId.length() - 3, maxMenuId.length())) + 1);
        int length = code.length();
        if (length < 3) {
            for (int i = 0; i < 3 - length; i++) {
                code = "0" + code;
            }
        }
        return "000".equals(menuParent) ? code : (menuParent + code);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateMenu(Menu menu) {
        String menuId = menu.getMenuId();
        // 设置检索条件
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("menuId", HQLSymbol.EQ.toString(), menuId));
        menuDao.updateHQL(menu, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteMenu(String menuId) {
        menuDao.deleteMenu(menuId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void editMenuOrder(List<String> menuIdList) {
        if (CollectionUtils.isNotEmpty(menuIdList)) {
            for (int i = 1; i <= menuIdList.size(); i++) {
                menuDao.updateMenuOrder(menuIdList.get(i - 1), i);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuPojo> getCatalogMenu() {
        return getMenuTreeList(menuDao.queryMenu());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuPojo> getCatalogMenuByLoginUser(LoginUser loginUser) {
        String roleId = loginUser.getUserPojo().getRoleId();
        String insId = loginUser.getUserPojo().getCreateInsId();
        List<MenuPojo> menuList = null;
        if ("admin".equals(loginUser.getUserPojo().getUserCode())) {
            menuList = menuDao.queryMenu();// 超级管理员
        } else {
            menuList = roleDao.queryMenuByRoleId(roleId, insId);// 普通职员
        }
        return getMenuTreeList(menuList);
    }

    /**
     * 把所有菜单按父子级分类
     * 
     * @author zcq
     * @return
     */
    private List<MenuPojo> getMenuTreeList(List<MenuPojo> menuList) {
        // 把所有菜单按父子级分类
        Map<String, List<MenuPojo>> menuTreeMap = new LinkedHashMap<String, List<MenuPojo>>();
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (MenuPojo menu : menuList) {
                String parentCode = menu.getMenuParent();
                if (!menuTreeMap.containsKey(parentCode)) {
                    menuTreeMap.put(parentCode, new ArrayList<MenuPojo>());
                }
                menuTreeMap.get(parentCode).add(menu);
            }
        }
        // 获取一级目录
        List<MenuPojo> oneMenuList = menuTreeMap.get("000");
        // 逐级填充完善菜单信息
        if (CollectionUtils.isNotEmpty(oneMenuList)) {
            // 递归填充childList
            for (MenuPojo oneMenu : oneMenuList) {
                menuRecursion(oneMenu, menuTreeMap);
            }
            // 去除没有子级的目录
            checkMenu(oneMenuList);
        }
        return oneMenuList;
    }

    /**
     * 递归填充childList
     * 
     * @author zcq
     * @param menu
     * @param menuMap
     */
    private void menuRecursion(MenuPojo menu, Map<String, List<MenuPojo>> menuMap) {
        if (menu.getMenuType() == 1) {
            List<MenuPojo> menuList = menuMap.get(menu.getMenuId());
            if (CollectionUtils.isNotEmpty(menuList)) {
                for (MenuPojo nextGradeMenu : menuList) {
                    menuRecursion(nextGradeMenu, menuMap);
                }
                menu.setChildList(menuList);
            }
        }
    }

    /**
     * 去除没有子级的目录
     * 
     * @author zcq
     * @param menuList
     */
    private void checkMenu(List<MenuPojo> menuList) {
        if (CollectionUtils.isNotEmpty(menuList)) {
            List<MenuPojo> tempList = new ArrayList<MenuPojo>();
            tempList.addAll(menuList);
            for (MenuPojo menu : menuList) {
                List<MenuPojo> childList = menu.getChildList();
                checkMenu(childList);
                if (menu.getMenuType() == 1 && CollectionUtils.isEmpty(childList)) {
                    tempList.remove(menu);
                }
            }
            menuList.removeAll(menuList);
            menuList.addAll(tempList);
        }
    }

}
