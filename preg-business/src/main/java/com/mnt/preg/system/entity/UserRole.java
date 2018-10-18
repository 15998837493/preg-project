
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 用户职位关联表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_user_role")
public class UserRole extends MappedEntity {

    private static final long serialVersionUID = 9016377651015503019L;

    /** 主键 */
    private String userRoleId;

    /** 用户工号 */
    private String userId;

    /** 职位编号 */
    private String roleId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "user_role_id")
    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "role_id")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
