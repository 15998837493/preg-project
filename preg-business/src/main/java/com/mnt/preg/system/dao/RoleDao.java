
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.Role;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.RightPojo;
import com.mnt.preg.system.pojo.RolePojo;

/**
 * 职务DAO（主表：sys_role）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版
 */
@Repository
public class RoleDao extends HibernateTemplate {

    /**
     * 查询权限
     */
    public List<RolePojo> queryRole(Role condition) {
        if (condition == null) {
            condition = new Role();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "RolePojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(RolePojo.class, "RolePojo")
                + "   FROM sys_role AS RolePojo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), RolePojo.class);
    }

    /**
     * 查询权限信息--通过【职位主键】
     * 
     * @param roleId
     *            职务主键
     * @return 权限信息
     */
    public List<RightPojo> queryRightByRoleId(String roleId) {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(RightPojo.class, "RightPojo")
                + "        FROM sys_role_right AS role_right"
                + "             JOIN sys_right AS RightPojo ON role_right.right_id = RightPojo.right_id"
                + "                 AND role_right.role_id =:roleId"
                + "                 AND RightPojo.flag = :flag";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("roleId", roleId);
        paramsMap.put("flag", 1);
        return this.SQLQueryAlias(querySQL, paramsMap, RightPojo.class);
    }

    /**
     * 查询功能菜单信息--根据【职位主键】
     * 
     * @author zcq
     * @param roleId
     *            角色主键
     * @return 功能菜单信息
     */
    public List<MenuPojo> queryMenuByRoleId(String roleId, String insId) {
        String sql = "SELECT DISTINCT " + DaoUtils.getSQLFields(MenuPojo.class, "menu")
                + "   FROM sys_role_right AS role_right"
                + "       JOIN sys_right_menu AS right_menu ON role_right.right_id=right_menu.right_id"
                + "           AND role_right.role_id=:roleId"
                + "       JOIN sys_menu AS menu ON menu.menu_id=right_menu.menu_id"
                + "       JOIN sys_institution_menu AS ins_menu ON ins_menu.menu_id=right_menu.menu_id"
                + "           AND ins_menu.ins_id=:insId"
                + "           AND menu.flag=:flag"
                + "   ORDER BY menu.menu_order";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("roleId", roleId);
        paramsMap.put("insId", insId);
        paramsMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramsMap, MenuPojo.class);
    }

    /**
     * 删除职位权限关联表
     * 
     * @author zcq
     * @param roleId
     *            权限主键
     * @return Integer
     */
    public Integer deleteRoleRight(String roleId) {
        String sql = "DELETE FROM sys_role_right WHERE role_id=:roleId";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("roleId", roleId);
        return this.executeSQL(sql, paramsMap);
    }

    /**
     * 校验职位名称是否可用
     * 
     * @author zcq
     * @param roleName
     * @return
     */
    public Integer checkRoleNameValid(String roleName) {
        String sql = "SELECT COUNT(role_id) FROM sys_role WHERE role_name=:roleName";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("roleName", roleName);
        return this.SQLCount(sql, paramsMap);
    }
}
