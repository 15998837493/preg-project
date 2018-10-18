
package com.mnt.preg.system.pojo;

import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 功能菜单信息
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-8 zy 初版
 */
public class MenuPojo {

    /** 主键 */
    @QueryFieldAnnotation
    private String menuId;

    /** 菜单名称 */
    @QueryFieldAnnotation
    private String menuName;

    /** 菜单地址 */
    @QueryFieldAnnotation
    private String menuUrl;

    /** 菜单图标 */
    @QueryFieldAnnotation
    private String menuIcon;

    /** 菜单类型--1=目录，2=菜单 */
    @QueryFieldAnnotation
    private Integer menuType;

    /** 菜单状态--0=停止，1=启用 */
    @QueryFieldAnnotation
    private Integer menuState;

    /** 菜单级别 */
    @QueryFieldAnnotation
    private Integer menuGrade;

    /** 菜单排序 */
    @QueryFieldAnnotation
    private Integer menuOrder;

    /** 菜单父级 */
    @QueryFieldAnnotation
    private String menuParent;

    /** 菜单备注 */
    @QueryFieldAnnotation
    private String menuRemark;

    /** 子级菜单 */
    private List<MenuPojo> childList;

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

    public List<MenuPojo> getChildList() {
        return childList;
    }

    public void setChildList(List<MenuPojo> childList) {
        this.childList = childList;
    }

}
