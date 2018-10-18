/*
 * PositionForm.java	 1.0   2014-12-30
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.form;

import java.util.List;

/**
 * 职务添加数据表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-30 zcq 初版
 */
public class RoleForm {

    /** 主键 */
    private String roleId;

    /** 职位名称 */
    private String roleName;

    /** 职位名称 */
    private String roleNameOld;

    /** 职位类型 */
    private String roleType;

    /** 职位备注 */
    private String roleRemark;

    /** 职位权限列表 */
    private List<String> rightIdList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameOld() {
        return roleNameOld;
    }

    public void setRoleNameOld(String roleNameOld) {
        this.roleNameOld = roleNameOld;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public List<String> getRightIdList() {
        return rightIdList;
    }

    public void setRightIdList(List<String> rightIdList) {
        this.rightIdList = rightIdList;
    }

}
