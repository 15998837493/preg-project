
package com.mnt.preg.system.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 转诊医生配置
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：v1.5 2018-03-21 dhs 初版
 */
public class ReferralDoctorPojo implements Serializable {

    private static final long serialVersionUID = 8583661436878948001L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 医生工号 */
    @QueryFieldAnnotation
    private String doctorId;

    /** 医生姓名 */
    @QueryFieldAnnotation
    private String doctorName;

    /** 所属科室 */
    @QueryFieldAnnotation
    private String doctorDepartmentId;

    /** 所属科室名称 */
    @QueryFieldAnnotation
    private String doctorDepartmentName;

    /** 身份证号 */
    @QueryFieldAnnotation
    private String doctorIcard;

    /** 医生性别 */
    @QueryFieldAnnotation
    private String doctorSex;

    /** 出生日期 */
    @QueryFieldAnnotation
    private String doctorBirthday;

    /** 医生手机 */
    @QueryFieldAnnotation
    private String doctorPhone;

    /** 医生职位 */
    @QueryFieldAnnotation
    private String doctorJob;

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

    public String getDoctorDepartmentName() {
        return doctorDepartmentName;
    }

    public void setDoctorDepartmentName(String doctorDepartmentName) {
        this.doctorDepartmentName = doctorDepartmentName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
