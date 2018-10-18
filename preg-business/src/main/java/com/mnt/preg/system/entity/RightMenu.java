
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 权限菜单关联表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_right_menu")
public class RightMenu extends MappedEntity {

    private static final long serialVersionUID = 2845583248491803552L;

    /** 主键 */
    private String rightMenuId;

    /** 权限编号 */
    private String rightId;

    /** 菜单编号 */
    private String menuId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "right_menu_id")
    public String getRightMenuId() {
        return rightMenuId;
    }

    public void setRightMenuId(String rightMenuId) {
        this.rightMenuId = rightMenuId;
    }

    @Column(name = "right_id")
    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId;
    }

    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
