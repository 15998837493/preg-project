
package com.mnt.preg.system.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 转诊医生配置
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：v1.5 2018-03-27 dhs 初版
 */
public class ReferralDoctorCondition implements Serializable {

    private static final long serialVersionUID = -9131539413311511289L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 医生工号(唯一) */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorId;

    /** 医生姓名 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorName;

    /** 所属科室 ID */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorDepartmentId;

    /** 所属科室 名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorDepartmentName;

    /** 身份证号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorIcard;

    /** 医生性别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorSex;

    /** 出生日期 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorBirthday;

    /** 医生手机 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorPhone;

    /** 医生职位 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorJob;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorDepartmentId() {
        return doctorDepartmentId;
    }

    public void setDoctorDepartmentId(String doctorDepartmentId) {
        this.doctorDepartmentId = doctorDepartmentId;
    }

    public String getDoctorIcard() {
        return doctorIcard;
    }

    public void setDoctorIcard(String doctorIcard) {
        this.doctorIcard = doctorIcard;
    }

    public String getDoctorSex() {
        return doctorSex;
    }

    public void setDoctorSex(String doctorSex) {
        this.doctorSex = doctorSex;
    }

    public String getDoctorBirthday() {
        return doctorBirthday;
    }

    public void setDoctorBirthday(String doctorBirthday) {
        this.doctorBirthday = doctorBirthday;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorJob() {
        return doctorJob;
    }

    public void setDoctorJob(String doctorJob) {
        this.doctorJob = doctorJob;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDoctorDepartmentName() {
        return doctorDepartmentName;
    }

    public void setDoctorDepartmentName(String doctorDepartmentName) {
        this.doctorDepartmentName = doctorDepartmentName;
    }
}
