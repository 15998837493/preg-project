
package com.mnt.preg.system.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * 用户FORM表单
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-16 zy 初版
 */
public class UserForm {

    /** 主键 */
    private String userId;

    /** 用户编码 */
    private String userCode;

    /** 用户密码 */
    private String userPassword;

    /** 姓名 */
    private String userName;

    /** 用户性别 */
    private String userSex;

    /** 出生日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;

    /** 邮箱 */
    private String userEmail;

    /** 身份证号 */
    private String userIcard;

    /** 手机号码 */
    private String userPhone;

    /** 用户头像 */
    private String userHeadSculpture;

    /** 用户头像 */
    private String userHeadSculptureOld;

    /** 用户类型 */
    private String userType;

    /** 用户状态 */
    private String userStatus;

    /** 用户描述 */
    private String userDesc;

    /** 用户备注 */
    private String userRemark;

    /** 职位 */
    private String roleId;

    /** 部门 */
    private String deptId;

    /** 是否出诊 */
    private Integer userIsWork;

    /** 最大接诊人数 */
    private Integer userMaxWork;

    /** 所属机构 */
    private String createInsId;

    private String userDiagnosis;

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

    public String getUserHeadSculptureOld() {
        return userHeadSculptureOld;
    }

    public void setUserHeadSculptureOld(String userHeadSculptureOld) {
        this.userHeadSculptureOld = userHeadSculptureOld;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

    public String getUserDiagnosis() {
        return userDiagnosis;
    }

    public void setUserDiagnosis(String userDiagnosis) {
        this.userDiagnosis = userDiagnosis;
    }

}
