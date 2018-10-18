
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 功能菜单表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends MappedEntity {

    private static final long serialVersionUID = 882586403279430166L;

    /** 主键 */
    @NotBlank
    private String menuId;

    /** 菜单名称 */
    @UpdateAnnotation
    private String menuName;

    /** 菜单地址 */
    @UpdateAnnotation
    private String menuUrl;

    /** 菜单图标 */
    @UpdateAnnotation
    private String menuIcon;

    /** 菜单类型--1=目录，2=菜单 */
    private Integer menuType;

    /** 菜单状态--0=停止，1=启用 */
    @UpdateAnnotation
    private Integer menuState;

    /** 菜单级别 */
    private Integer menuGrade;

    /** 菜单排序 */
    @UpdateAnnotation
    private Integer menuOrder;

    /** 菜单父级 */
    private String menuParent;

    /** 菜单备注 */
    @UpdateAnnotation
    private String menuRemark;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Column(name = "menu_name")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name = "menu_url")
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Column(name = "menu_icon")
    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    @Column(name = "menu_type")
    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    @Column(name = "menu_state")
    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
    }

    @Column(name = "menu_grade")
    public Integer getMenuGrade() {
        return menuGrade;
    }

    public void setMenuGrade(Integer menuGrade) {
        this.menuGrade = menuGrade;
    }

    @Column(name = "menu_order")
    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    @Column(name = "menu_parent")
    public String getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent;
    }

    @Column(name = "menu_remark")
    public String getMenuRemark() {
        return menuRemark;
    }

    public void setMenuRemark(String menuRemark) {
        this.menuRemark = menuRemark;
    }

}
