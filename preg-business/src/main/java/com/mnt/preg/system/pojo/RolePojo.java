/*
 * RoleVo.java	 1.0   2014-12-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 角色信息
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-8 zy 初版
 */
public class RolePojo {

    /** 主键 */
    @QueryFieldAnnotation
    private String roleId;

    /** 职位名称 */
    @QueryFieldAnnotation
    private String roleName;

    /** 职位类型 */
    @QueryFieldAnnotation
    private String roleType;

    /** 职位备注 */
    @QueryFieldAnnotation
    private String roleRemark;

    /** 权限列表 */
    private List<RightPojo> rightList;

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

    public List<RightPojo> getRightList() {
        return rightList;
    }

    public void setRightList(List<RightPojo> rightList) {
        this.rightList = rightList;
    }

}
