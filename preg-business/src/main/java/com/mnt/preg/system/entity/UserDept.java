
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 用户部门关联表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_user_dept")
public class UserDept extends MappedEntity {

    private static final long serialVersionUID = 4648904115934015996L;

    /** 主键 */
    private String userDeptId;

    /** 用户工号 */
    private String userId;

    /** 部门编号 */
    private String deptId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "user_dept_id")
    public String getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "dept_id")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

}
