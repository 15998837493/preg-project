/*
 * UserInfo.java	 1.0   2014-12-11
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 用户信息表
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-11 zy 初版
 */
@Entity
@Table(name = "sys_user")
public class User extends MappedEntity {

    private static final long serialVersionUID = -3860857867851282617L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String userId;

    /** 用户编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String userCode;

    /** 用户密码 */
    @UpdateAnnotation
    private String userPassword;

    /** 姓名 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String userName;

    /** 用户性别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String userSex;

    /** 出生日期 */
    @UpdateAnnotation
    private Date userBirthday;

    /** 邮箱 */
    @UpdateAnnotation
    private String userEmail;

    /** 身份证号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String userIcard;

    /** 手机号码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String userPhone;

    /** 用户头像 */
    @UpdateAnnotation
    private String userHeadSculpture;

    /** 用户类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String userType;

    /** 用户状态 */
    @UpdateAnnotation
    private String userStatus;

    /** 是否出诊 */
    @UpdateAnnotation
    private Integer userIsWork;

    /** 最大接诊人数 */
    @UpdateAnnotation
    private Integer userMaxWork;

    /** 用户描述 */
    @UpdateAnnotation
    private String userDesc;

    /** 用户备注 */
    @UpdateAnnotation
    private String userRemark;

    /** 用户备注 */
    @UpdateAnnotation
    private String userDiagnosis;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Column(name = "user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_sex")
    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "user_birthday")
    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "user_icard")
    public String getUserIcard() {
        return userIcard;
    }

    public void setUserIcard(String userIcard) {
        this.userIcard = userIcard;
    }

    @Column(name = "user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Column(name = "user_head_sculpture")
    public String getUserHeadSculpture() {
        return userHeadSculpture;
    }

    public void setUserHeadSculpture(String userHeadSculpture) {
        this.userHeadSculpture = userHeadSculpture;
    }

    @Column(name = "user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "user_status")
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Column(name = "user_is_work")
    public Integer getUserIsWork() {
        return userIsWork;
    }

    public void setUserIsWork(Integer userIsWork) {
        this.userIsWork = userIsWork;
    }

    @Column(name = "user_max_work")
    public Integer getUserMaxWork() {
        return userMaxWork;
    }

    public void setUserMaxWork(Integer userMaxWork) {
        this.userMaxWork = userMaxWork;
    }

    @Column(name = "user_desc")
    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    @Column(name = "user_remark")
    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    @Column(name = "user_diagnosis")
    public String getUserDiagnosis() {
        return userDiagnosis;
    }

    public void setUserDiagnosis(String userDiagnosis) {
        this.userDiagnosis = userDiagnosis;
    }

}
