
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.system.pojo.MenuPojo;

/**
 * 功能菜单DAO（主表：sys_menu）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-3 zcq 初版
 */
@Repository
public class MenuDao extends HibernateTemplate {

    /**
     * 查询功能菜单信息
     * 
     * @return 功能菜单信息
     */
    public List<MenuPojo> queryMenu() {
        String sql = "SELECT " + DaoUtils.getSQLFields(MenuPojo.class, "menu")
                + "   FROM sys_menu AS menu "
                + "   WHERE menu.flag=:flag"
                + "   ORDER BY menu.menu_order";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, MenuPojo.class);
    }

    /**
     * 逻辑删除功能菜单
     * 
     * @author zcq
     * @param menuId
     */
    public void deleteMenu(String menuId) {
        String executeSQL = "UPDATE sys_menu SET flag=:flag WHERE menu_id LIKE:menuId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("menuId", menuId + "%");
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 校验功能菜单主键
     * 
     * @author zcq
     * @param menuId
     * @return
     */
    public Integer checkMenuIdValid(String menuId) {
        String countSQL = "SELECT COUNT(menu_id) FROM sys_menu WHERE menu_id=:menuId AND flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("menuId", menuId);
        queryParams.put("flag", 1);
        return this.SQLCount(countSQL, queryParams);
    }

    /**
     * 获取子级最大ID
     * 
     * @author zcq
     * @param menuId
     * @return
     */
    public String getSonMaxMenuId(String menuParent) {
        String sql = "SELECT MAX(menu_id) FROM sys_menu WHERE menu_parent=:menuParent";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("menuParent", menuParent);
        return this.SQLQueryFirst(sql, queryParams);
    }

    /**
     * 校验功能菜单主键
     * 
     * @author zcq
     * @param menuId
     * @return
     */
    public Integer checkMenuNameValid(String menuName) {
        String countSQL = "SELECT COUNT(menu_id) FROM sys_menu WHERE menu_name=:menuName AND flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("menuName", menuName);
        queryParams.put("flag", 1);
        return this.SQLCount(countSQL, queryParams);
    }

    /**
     * 修改功能菜单排序
     * 
     * @author zcq
     * @param menuId
     * @param menuOrder
     */
    public void updateMenuOrder(String menuId, Integer menuOrder) {
        String executeSQL = "UPDATE sys_menu SET menu_order=:menuOrder WHERE menu_id=:menuId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("menuId", menuId);
        queryParams.put("menuOrder", menuOrder);
        this.executeSQL(executeSQL, queryParams);
    }

}
