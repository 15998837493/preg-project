
package com.mnt.preg.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.system.entity.Dept;
import com.mnt.preg.system.pojo.DeptPojo;

/**
 * 组织结构DAO（主表：sys_dept）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-3 zcq 初版
 */
@Repository
public class DeptDAO extends HibernateTemplate {

    /**
     * 查询组织结构信息
     * 
     * @return 组织结构信息
     */
    public List<DeptPojo> queryDept(Dept condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "dept");
        String sql = "SELECT " + DaoUtils.getSQLFields(DeptPojo.class, "dept")
                + "   FROM sys_dept AS dept "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DeptPojo.class);
    }

    /**
     * 逻辑删除组织结构
     * 
     * @author zcq
     * @param deptId
     */
    public void deleteDept(String deptId) {
        String executeSQL = "UPDATE sys_dept SET flag=:flag WHERE dept_id LIKE:deptId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("deptId", deptId + "%");
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 删除用户部门关联信息
     * 
     * @author zcq
     * @param deptId
     * @return
     */
    public Integer deleteDeptUser(String deptId) {
        String sql = "DELETE FROM sys_user_dept WHERE dept_id LIKE:deptId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deptId", deptId + "%");
        return this.executeSQL(sql, paramMap);
    }

    /**
     * 校验组织结构主键
     * 
     * @author zcq
     * @param deptId
     * @return
     */
    public Integer checkDeptIdValid(String deptId) {
        String countSQL = "SELECT COUNT(dept_id) FROM sys_dept WHERE dept_id=:deptId AND flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("deptId", deptId);
        queryParams.put("flag", 1);
        return this.SQLCount(countSQL, queryParams);
    }

    /**
     * 获取子级最大ID
     * 
     * @author zcq
     * @param deptId
     * @return
     */
    public String getSonMaxDeptId(String deptParent) {
        String sql = "SELECT MAX(dept_id) FROM sys_dept WHERE dept_parent=:deptParent";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("deptParent", deptParent);
        return this.SQLQueryFirst(sql, queryParams);
    }

    /**
     * 校验组织结构主键
     * 
     * @author zcq
     * @param deptId
     * @return
     */
    public Integer checkDeptNameValid(String deptName) {
        String countSQL = "SELECT COUNT(dept_id) FROM sys_dept WHERE dept_name=:deptName AND flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("deptName", deptName);
        queryParams.put("flag", 1);
        return this.SQLCount(countSQL, queryParams);
    }

    /**
     * 修改组织结构排序
     * 
     * @author zcq
     * @param deptId
     * @param deptOrder
     */
    public void updateDeptOrder(String deptId, Integer deptOrder) {
        String executeSQL = "UPDATE sys_dept SET dept_order=:deptOrder WHERE dept_id=:deptId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("deptId", deptId);
        queryParams.put("deptOrder", deptOrder);
        this.executeSQL(executeSQL, queryParams);
    }

}
