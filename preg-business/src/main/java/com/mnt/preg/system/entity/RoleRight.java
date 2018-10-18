
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 职位权限关联表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_role_right")
public class RoleRight extends MappedEntity {

    private static final long serialVersionUID = -6550239897675404423L;

    /** 主键 */
    private String roleRightId;

    /** 职位编号 */
    private String roleId;

    /** 权限编号 */
    private String rightId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "role_right_id")
    public String getRoleRightId() {
        return roleRightId;
    }

    public void setRoleRightId(String roleRightId) {
        this.roleRightId = roleRightId;
    }

    @Column(name = "role_id")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Column(name = "right_id")
    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId;
    }

}
