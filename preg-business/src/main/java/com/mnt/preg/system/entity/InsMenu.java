
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 机构功能菜单表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_institution_menu")
public class InsMenu extends MappedEntity {

    private static final long serialVersionUID = 3745143870250023312L;

    /** 主键 */
    private String insMenuId;

    /** 机构编号 */
    private String insId;

    /** 菜单编号 */
    private String menuId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "ins_menu_id")
    public String getInsMenuId() {
        return insMenuId;
    }

    public void setInsMenuId(String insMenuId) {
        this.insMenuId = insMenuId;
    }

    @Column(name = "ins_id")
    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
