/*
 * UserViewVo.java	 1.0   2014-12-9
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 用户完整信息
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-9 zy 初版
 */
public class UserPojo implements Serializable {

    private static final long serialVersionUID = 7372754524068889403L;

    /** 主键 */
    @QueryFieldAnnotation
    private String userId;

    /** 用户编码 */
    @QueryFieldAnnotation
    private String userCode;

    /** 用户密码 */
    @QueryFieldAnnotation
    private String userPassword;

    /** 姓名 */
    @QueryFieldAnnotation
    private String userName;

    /** 用户性别 */
    @QueryFieldAnnotation
    private String userSex;

    /** 出生日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;

    /** 邮箱 */
    @QueryFieldAnnotation
    private String userEmail;

    /** 身份证号 */
    @QueryFieldAnnotation
    private String userIcard;

    /** 手机号码 */
    @QueryFieldAnnotation
    private String userPhone;

    /** 用户头像 */
    @QueryFieldAnnotation
    private String userHeadSculpture;

    /** 用户类型 */
    @QueryFieldAnnotation
    private String userType;

    /** 用户状态 */
    @QueryFieldAnnotation
    private String userStatus;

    /** 是否出诊 */
    @QueryFieldAnnotation
    private Integer userIsWork;

    /** 最大接诊人数 */
    @QueryFieldAnnotation
    private Integer userMaxWork;

    /** 用户描述 */
    @QueryFieldAnnotation
    private String userDesc;

    /** 用户备注 */
    @QueryFieldAnnotation
    private String userRemark;

    /** 所属机构 */
    @QueryFieldAnnotation
    private String createInsId;

    @QueryFieldAnnotation
    private String userDiagnosis;

    /** 用户性别名称 */
    private String userSexName;

    /** 所属部门编号 */
    private String deptId;

    /** 所属部门名称 */
    private String deptName;

    /** 职位编号 */
    private String roleId;

    /** 职位名称 */
    private String roleName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIcard() {
        return userIcard;
    }

    public void setUserIcard(String userIcard) {
        this.userIcard = userIcard;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserHeadSculpture() {
        return userHeadSculpture;
    }

    public void setUserHeadSculpture(String userHeadSculpture) {
        this.userHeadSculpture = userHeadSculpture;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserIsWork() {
        return userIsWork;
    }

    public void setUserIsWork(Integer userIsWork) {
        this.userIsWork = userIsWork;
    }

    public Integer getUserMaxWork() {
        return userMaxWork;
    }

    public void setUserMaxWork(Integer userMaxWork) {
        this.userMaxWork = userMaxWork;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getUserDiagnosis() {
        return userDiagnosis;
    }

    public void setUserDiagnosis(String userDiagnosis) {
        this.userDiagnosis = userDiagnosis;
    }

    public String getUserSexName() {
        return userSexName;
    }

    public void setUserSexName(String userSexName) {
        this.userSexName = userSexName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

}
