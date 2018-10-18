
package com.mnt.preg.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.system.condition.DoctorAssistantCondition;
import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.pojo.UserAssistantPojo;
import com.mnt.preg.system.pojo.UserPojo;

/**
 * 用户DAO（主表：usr_user）
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-8 zy 初版
 */
@Repository
public class UserDao extends HibernateTemplate {

    /**
     * 条件检索用户
     * 
     * @author zcq
     * @param condition
     *            检索条件
     * @return 用户信息列表
     */
    public List<UserPojo> queryUser(User condition) {
        if (condition == null) {
            condition = new User();
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "UserPojo");
        // 排除超级管理员
        queryCondition.setQueryString(queryCondition.getQueryString() + " AND UserPojo.user_code!=:adminCode");
        queryCondition.getQueryParams().put("adminCode", "admin");
        // 组织查询语句
        String querySQL = "SELECT DISTINCT " + DaoUtils.getSQLFields(UserPojo.class, "UserPojo") + ","
                + "             dept.dept_id AS deptId,"
                + "             dept.dept_name AS deptName,"
                + "             role.role_id AS roleId,"
                + "             role.role_name AS roleName"
                + "        FROM sys_user AS UserPojo"
                + "             LEFT JOIN sys_user_role AS user_role ON UserPojo.user_id=user_role.user_id"
                + "             LEFT JOIN sys_role AS role ON role.role_id=user_role.role_id"
                + "             LEFT JOIN sys_user_dept AS user_dept ON UserPojo.user_id=user_dept.user_id"
                + "             LEFT JOIN sys_dept AS dept ON dept.dept_id=user_dept.dept_id"
                + queryCondition.getQueryString()
                + "         AND UserPojo.user_status = '1'"
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), UserPojo.class);
    }

    /**
     * 条件检索用户(不过滤状态)
     * 
     * @author zcq
     * @param condition
     *            检索条件
     * @return 用户信息列表
     */
    public List<UserPojo> queryUsersAll(User condition) {
        if (condition == null) {
            condition = new User();
        }
        String configConditionSql = "";
        if ("doctorConfig".equals(condition.getUserType())) {// 人员管理-助理配置医生
            condition.setUserType("doctor");
            configConditionSql = " AND UserPojo.user_id not in(SELECT doctor_id FROM sys_user_assistant WHERE assistant_id != '"
                    + condition.getUserId() + "') ";
            condition.setUserId(null);
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "UserPojo");
        // 排除超级管理员
        queryCondition.setQueryString(queryCondition.getQueryString() + " AND UserPojo.user_code!=:adminCode"
                + configConditionSql);
        queryCondition.getQueryParams().put("adminCode", "admin");
        // 组织查询语句
        String querySQL = "SELECT DISTINCT " + DaoUtils.getSQLFields(UserPojo.class, "UserPojo") + ","
                + "             dept.dept_id AS deptId,"
                + "             dept.dept_name AS deptName,"
                + "             role.role_id AS roleId,"
                + "             role.role_name AS roleName"
                + "        FROM sys_user AS UserPojo"
                + "             LEFT JOIN sys_user_role AS user_role ON UserPojo.user_id=user_role.user_id"
                + "             LEFT JOIN sys_role AS role ON role.role_id=user_role.role_id"
                + "             LEFT JOIN sys_user_dept AS user_dept ON UserPojo.user_id=user_dept.user_id"
                + "             LEFT JOIN sys_dept AS dept ON dept.dept_id=user_dept.dept_id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), UserPojo.class);
    }

    /**
     * 获取用户信息--根据【主键/工号】
     * 
     * @author zcq
     * @param userId
     *            主键
     * @return 用户信息
     */
    public UserPojo getUser(String userId) {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(UserPojo.class, "UserPojo") + ","
                + "             dept.dept_id AS deptId,"
                + "             dept.dept_name AS deptName,"
                + "             role.role_id AS roleId,"
                + "             role.role_name AS roleName"
                + "        FROM sys_user AS UserPojo"
                + "             LEFT JOIN sys_user_role AS user_role ON UserPojo.user_id=user_role.user_id"
                + "             LEFT JOIN sys_role AS role ON role.role_id=user_role.role_id"
                + "             LEFT JOIN sys_user_dept AS user_dept ON UserPojo.user_id=user_dept.user_id"
                + "             LEFT JOIN sys_dept AS dept ON dept.dept_id=user_dept.dept_id"
                + "        WHERE UserPojo.user_id=:userId AND UserPojo.flag=:flag";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userId", userId);
        paramsMap.put("flag", 1);
        return this.SQLQueryAliasFirst(querySQL, paramsMap, UserPojo.class);
    }

    /**
     * 获取用户信息--根据【用户账号】
     * 
     * @author zcq
     * @param userCode
     *            账号
     * @return 用户信息
     */
    public UserPojo getUserByUserCode(String userCode) {
        String querySQL = "SELECT " + DaoUtils.getSQLFields(UserPojo.class, "UserPojo") + ","
                + "             dept.dept_id AS deptId,"
                + "             dept.dept_name AS deptName,"
                + "             role.role_id AS roleId,"
                + "             role.role_name AS roleName"
                + "        FROM sys_user AS UserPojo"
                + "             LEFT JOIN sys_user_role AS user_role ON UserPojo.user_id=user_role.user_id"
                + "             LEFT JOIN sys_role AS role ON role.role_id=user_role.role_id"
                + "             LEFT JOIN sys_user_dept AS user_dept ON UserPojo.user_id=user_dept.user_id"
                + "             LEFT JOIN sys_dept AS dept ON dept.dept_id=user_dept.dept_id"
                + "        WHERE UserPojo.user_code=:userCode AND UserPojo.flag=:flag";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userCode", userCode);
        paramsMap.put("flag", 1);
        return this.SQLQueryAliasFirst(querySQL, paramsMap, UserPojo.class);
    }

    /**
     * 
     * 根据手机号查询用户信息
     * 
     * @author mnt_zhangjing
     * @param userPhone
     * @return
     */
    public UserPojo getUserByPhone(String userPhone) {
        // SQL语句
        String querySQL = "SELECT " + DaoUtils.getSQLFields(UserPojo.class, "UserPojo") + ","
                + "             role.role_id AS roleId,"
                + "             role.role_name AS roleName"
                + "        FROM sys_user AS UserPojo"
                + "             LEFT JOIN sys_user_role AS user_role ON UserPojo.user_id=user_role.user_id"
                + "             LEFT JOIN sys_role AS role ON role.role_id=user_role.role_id"
                + "        WHERE UserPojo.user_phone=:userPhone AND UserPojo.flag=:flag";

        // SQL参数
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userPhone", userPhone);
        paramsMap.put("flag", Flag.normal.getValue());
        // 执行
        return this.SQLQueryAliasFirst(querySQL, paramsMap, UserPojo.class);
    }

    /**
     * 检查用户工号是否可用
     * 
     * @param userCode
     *            用户名
     * @return Integer 与当前传入信息一致的会员数据数，新增时： 0-验证有效 否则无效 修改时: 1-验证有效 否则无效
     */
    public Integer checkUserCodeValid(String userCode) {
        String countSQL = "SELECT COUNT(user_code) FROM sys_user WHERE user_code=:userCode AND flag=:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userCode", userCode);
        paramMap.put("flag", 1);
        return this.SQLCount(countSQL, paramMap);
    }

    /**
     * 检查用户邮箱是否可用
     * 
     * @author zcq
     * @param userEmail
     * @return
     */
    public Integer checkUserEmailValid(String userEmail) {
        String countSQL = "SELECT COUNT(user_email) FROM sys_user WHERE user_email=:userEmail AND flag=:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userEmail", userEmail);
        paramMap.put("flag", 1);
        return this.SQLCount(countSQL, paramMap);
    }

    /**
     * 检查用户手机是否可用
     * 
     * @author zcq
     * @param userPhone
     * @return
     */
    public Integer checkUserPhoneValid(String userPhone) {
        String countSQL = "SELECT COUNT(user_phone) FROM sys_user WHERE user_phone=:userPhone AND flag=:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userPhone", userPhone);
        paramMap.put("flag", 1);
        return this.SQLCount(countSQL, paramMap);
    }

    /**
     * 删除用户部门关联信息
     * 
     * @author zcq
     * @param userId
     * @return
     */
    public Integer deleteUserDept(String userId) {
        String sql = "DELETE FROM sys_user_dept WHERE user_id=:userId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return this.executeSQL(sql, paramMap);
    }

    /**
     * 删除用户职位关联信息
     * 
     * @author zcq
     * @param userId
     * @return
     */
    public Integer deleteUserRole(String userId) {
        String sql = "DELETE FROM sys_user_role WHERE user_id=:userId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return this.executeSQL(sql, paramMap);
    }

    /**
     * 条件查询医师助理关系表信息
     * 
     * @author dhs
     * @param condition
     * @return
     */
    public List<UserAssistantPojo> queryDoctorByCondition(DoctorAssistantCondition condition) {
        if (condition == null) {
            condition = new DoctorAssistantCondition();
        }
        String queryDoctorNameSQL = "";// 查询医生姓名
        if (StringUtils.isNotBlank(condition.getAssistantId())) { // 传入助理ID时查出医生姓名
            queryDoctorNameSQL = " ,(SELECT user_name FROM sys_user WHERE user_id = assistantPojo.doctor_id) AS doctorName ";
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "assistantPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(UserAssistantPojo.class, "assistantPojo")
                + queryDoctorNameSQL
                + "   FROM sys_user_assistant AS assistantPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), UserAssistantPojo.class);
    }

    /**
     * 物理删除
     * 
     * @author dhs
     * @param condition
     * @return
     */
    public void deleteUserAssistant(String id, String type) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        if ("doctor".equals(type)) {
            paramsMap.put("doctorId", id);
            this.executeSQL("DELETE FROM sys_user_assistant WHERE doctor_id=:doctorId", paramsMap);
        } else if ("assiss".equals(type)) {
            paramsMap.put("assistantId", id);
            this.executeSQL("DELETE FROM sys_user_assistant WHERE assistant_id=:assistantId", paramsMap);
        }
    }

    /**
     * 查询医生或助理
     * 
     * @author zcq
     * @param userId
     * @return
     */
    public List<UserPojo> queryDoctorOrAssistant(String userId) {
        UserPojo userPojo = getUser(userId);
        List<UserPojo> list = new ArrayList<UserPojo>();
        if (userPojo != null) {
            DoctorAssistantCondition condition = new DoctorAssistantCondition();
            if (("doctor").equals(userPojo.getUserType())) {// 医生
                condition.setDoctorId(userId);
                List<UserAssistantPojo> assistantList = this.queryDoctorByCondition(condition);
                if (CollectionUtils.isNotEmpty(assistantList)) {
                    for (UserAssistantPojo pojo : assistantList) {
                        list.add(getUser(pojo.getAssistantId()));
                    }
                }
            } else if (("assistant").equals(userPojo.getUserType())) {// 助理
                condition.setAssistantId(userId);
                List<UserAssistantPojo> assistantList = this.queryDoctorByCondition(condition);
                if (CollectionUtils.isNotEmpty(assistantList)) {
                    for (UserAssistantPojo pojo : assistantList) {
                        list.add(getUser(pojo.getDoctorId()));
                    }
                }
            }
        }
        return list;
    }

}
