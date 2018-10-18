
package com.mnt.preg.system.pojo;

import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 权限信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版
 */
public class RightPojo {

    /** 主键 */
    @QueryFieldAnnotation
    private String rightId;

    /** 权限名称 */
    @QueryFieldAnnotation
    private String rightName;

    /** 权限类型 */
    @QueryFieldAnnotation
    private String rightType;

    /** 权限备注 */
    @QueryFieldAnnotation
    private String rightRemark;

    /** 权限菜单 */
    private List<MenuPojo> menuList;

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

    public List<MenuPojo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuPojo> menuList) {
        this.menuList = menuList;
    }

}
