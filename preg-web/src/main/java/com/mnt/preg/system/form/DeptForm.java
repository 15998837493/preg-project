/*
 * DeptForm.java	 1.0   2014-12-31
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.form;

/**
 * 添加分支表单数据
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-31 zcq 初版
 */
public class DeptForm {

    /** 部门编码 --顶级编码默认0000 */
    private String deptId;

    /** 部门父级编码 */
    private String deptParent;

    /** 部门名称 */
    private String deptName;

    /** 部门排序号 */
    private Integer deptOrder;

    /** 部门类型 */
    private String deptType;

    /** 部门级别 */
    private Integer deptGrade;

    /** 部门电话 */
    private String deptPhone;

    /** 部门状态 */
    private String deptStatus;

    /** 部门说明 */
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
