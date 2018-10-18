
package com.mnt.preg.system.pojo;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 组织结构信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-3 zcq 初版
 */

public class DeptPojo {

    /** 部门编码 --顶级编码默认0000 */
    @QueryFieldAnnotation
    private String deptId;

    /** 部门父级编码 */
    @QueryFieldAnnotation
    private String deptParent;

    /** 部门名称 */
    @QueryFieldAnnotation
    private String deptName;

    /** 部门排序号 */
    @QueryFieldAnnotation
    private Integer deptOrder;

    /** 部门类型 */
    @QueryFieldAnnotation
    private String deptType;

    /** 部门级别 */
    @QueryFieldAnnotation
    private Integer deptGrade;

    /** 部门电话 */
    @QueryFieldAnnotation
    private String deptPhone;

    /** 部门状态 */
    @QueryFieldAnnotation
    private String deptStatus;

    /** 部门说明 */
    @QueryFieldAnnotation
    private String deptRemark;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptParent() {
        return deptParent;
    }

    public void setDeptParent(String deptParent) {
        this.deptParent = deptParent;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptOrder() {
        return deptOrder;
    }

    public void setDeptOrder(Integer deptOrder) {
        this.deptOrder = deptOrder;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public Integer getDeptGrade() {
        return deptGrade;
    }

    public void setDeptGrade(Integer deptGrade) {
        this.deptGrade = deptGrade;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

}
