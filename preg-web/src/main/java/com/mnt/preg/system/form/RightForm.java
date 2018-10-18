/*
 * RoleForm.java	 1.0   2014-12-26
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.form;

import java.util.List;

/**
 * 角色添加数据表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-26 zcq 初版
 */
public class RightForm {

    /** 主键 */
    private String rightId;

    /** 权限名称 */
    private String rightName;

    /** 权限名称 */
    private String rightNameOld;

    /** 权限类型 */
    private String rightType;

    /** 权限备注 */
    private String rightRemark;

    /** 权限功能菜单列表 */
    private List<String> menuIdList;

    /** 权限字符串 */
    private String menuIdStr;

    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightNameOld() {
        return rightNameOld;
    }

    public void setRightNameOld(String rightNameOld) {
        this.rightNameOld = rightNameOld;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public String getRightRemark() {
        return rightRemark;
    }

    public void setRightRemark(String rightRemark) {
        this.rightRemark = rightRemark;
    }

    public List<String> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public String getMenuIdStr() {
        return menuIdStr;
    }

    public void setMenuIdStr(String menuIdStr) {
        this.menuIdStr = menuIdStr;
    }

}
