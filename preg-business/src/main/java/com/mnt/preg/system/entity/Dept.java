/**
 * 
 */

package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 部门检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-26 zcq 初版
 */
@Entity
@Table(name = "sys_dept")
public class Dept extends MappedEntity {

    private static final long serialVersionUID = 4339324297096867377L;

    /** 部门编码 --顶级编码默认0000 */
    @UpdateAnnotation
    private String deptId;

    /** 部门父级编码 */
    @UpdateAnnotation
    private String deptParent;

    /** 部门名称 */
    @UpdateAnnotation
    private String deptName;

    /** 部门排序号 */
    @UpdateAnnotation
    private Integer deptOrder;

    /** 部门类型 */
    @UpdateAnnotation
    private String deptType;

    /** 部门级别 */
    @UpdateAnnotation
    private Integer deptGrade;

    /** 部门电话 */
    @UpdateAnnotation
    private String deptPhone;

    /** 部门状态 */
    @UpdateAnnotation
    private String deptStatus;

    /** 部门说明 */
    @UpdateAnnotation
    private String deptRemark;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "dept_id")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Column(name = "dept_parent")
    public String getDeptParent() {
        return deptParent;
    }

    public void setDeptParent(String deptParent) {
        this.deptParent = deptParent;
    }

    @Column(name = "dept_name")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name = "dept_order")
    public Integer getDeptOrder() {
        return deptOrder;
    }

    public void setDeptOrder(Integer deptOrder) {
        this.deptOrder = deptOrder;
    }

    @Column(name = "dept_type")
    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    @Column(name = "dept_grade")
    public Integer getDeptGrade() {
        return deptGrade;
    }

    public void setDeptGrade(Integer deptGrade) {
        this.deptGrade = deptGrade;
    }

    @Column(name = "dept_phone")
    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    @Column(name = "dept_status")
    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

    @Column(name = "dept_remark")
    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

}
