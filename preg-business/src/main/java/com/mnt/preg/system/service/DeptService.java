
package com.mnt.preg.system.service;

import java.util.List;

import com.mnt.preg.system.entity.Dept;
import com.mnt.preg.system.pojo.DeptPojo;

/**
 * 组织结构事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 zcq 初版
 */
public interface DeptService {

    /**
     * 查询组织结构
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询组织结构</dd>
     * </dl>
     * 
     * @author zcq
     * @return 组织结构
     */
    List<DeptPojo> queryDept(Dept condition);

    /**
     * 获取组织结构
     * 
     * @author zcq
     * @param deptId
     * @return
     */
    DeptPojo getDept(String deptId);

    /**
     * 校验菜单主键是否可用
     * 
     * @author zcq
     * @param deptId
     *            主键
     * @return Integer
     */
    Integer checkDeptIdValid(String deptId);

    /**
     * 校验菜单名称是否可用
     * 
     * @author zcq
     * @param deptName
     *            主键
     * @return Integer
     */
    Integer checkDeptNameValid(String deptName);

    /**
     * 添加组织结构
     * 
     * @author zcq
     * @param dept
     * @return
     */
    String addDept(Dept dept);

    /**
     * 修改组织结构
     * 
     * @author zcq
     * @param dept
     */
    void updateDept(Dept dept);

    /**
     * 删除组织结构
     * 
     * @author zcq
     * @param dept
     */
    void deleteDept(String deptId);

    /**
     * 修改组织结构排序
     * 
     * @author zcq
     * @param deptIdList
     */
    void editDeptOrder(List<String> deptIdList);

}
