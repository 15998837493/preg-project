
package com.mnt.preg.system.form;

/**
 * 功能菜单表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-2 zcq 初版
 */
public class MenuForm {

    /** 主键 */
    private String menuId;

    /** 菜单名称 */
    private String menuName;

    /** 菜单地址 */
    private String menuUrl;

    /** 菜单图标 */
    private String menuIcon;

    /** 菜单类型--1=目录，2=菜单 */
    private Integer menuType;

    /** 菜单状态--0=停止，1=启用 */
    private Integer menuState;

    /** 菜单级别 */
    private Integer menuGrade;

    /** 菜单排序 */
    private Integer menuOrder;

    /** 菜单父级 */
    private String menuParent;

    /** 菜单备注 */
    private String menuRemark;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
    }

    public Integer getMenuGrade() {
        return menuGrade;
    }

    public void setMenuGrade(Integer menuGrade) {
        this.menuGrade = menuGrade;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent;
    }

    public String getMenuRemark() {
        return menuRemark;
    }

    public void setMenuRemark(String menuRemark) {
        this.menuRemark = menuRemark;
    }

}
