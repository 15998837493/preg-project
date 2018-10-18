
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.Right;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.RightPojo;

/**
 * 权限DAO（主表：sys_right）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-3 zcq 初版
 */
@Repository
public class RightDao extends HibernateTemplate {

    /**
     * 查询权限
     */
    public List<RightPojo> queryRight(Right condition) {
        if (condition == null) {
            condition = new Right();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "RightPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(RightPojo.class, "RightPojo")
                + "   FROM sys_right AS RightPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), RightPojo.class);
    }

    /**
     * 查询功能菜单信息--根据【权限主键】
     * 
     * @author zcq
     * @param rightId
     *            职务主键
     * @return 功能菜单信息
     */
    public List<MenuPojo> queryMenuByRightId(String rightId) {
        String sql = "SELECT DISTINCT " + DaoUtils.getSQLFields(MenuPojo.class, "menu")
                + "   FROM sys_right_menu AS right_menu"
                + "       JOIN sys_menu AS menu ON menu.menu_id=right_menu.menu_id"
                + "           AND right_menu.right_id=:rightId"
                + "           AND menu.flag=:flag"
                + "   ORDER BY menu.menu_order DESC";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("rightId", rightId);
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, MenuPojo.class);
    }

    /**
     * 删除权限菜单关联表
     * 
     * @author zcq
     * @param rightId
     *            权限主键
     * @return Integer
     */
    public Integer deleteRightMenu(String rightId) {
        String sql = "DELETE FROM sys_right_menu WHERE right_id=:rightId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("rightId", rightId);
        return this.executeSQL(sql, queryParams);
    }

    /**
     * 校验权限名称是否可用
     * 
     * @author zcq
     * @param rightName
     * @return
     */
    public Integer checkRightNameValid(String rightName) {
        String sql = "SELECT COUNT(right_id) FROM sys_right WHERE right_name=:rightName AND flag=1";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("rightName", rightName);
        return this.SQLCount(sql, queryParams);
    }
}
