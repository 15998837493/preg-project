
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 转诊医生配置表
 * 
 * @author dhs
 * @version v1.5
 * 
 *          变更履历：
 *          v1.5 2018-03-21 dhs 初版
 */
@Entity
@Table(name = "sys_referral_doctor")
public class ReferralDoctor extends MappedEntity {

    private static final long serialVersionUID = -1908939742261732119L;

    /** 主键 */
    @NotEmpty
    private String id;

    /** 医生工号(唯一) */
    @NotEmpty
    private String doctorId;

    /** 医生姓名 */
    @UpdateAnnotation
    private String doctorName;

    /** 所属科室 ID */
    @UpdateAnnotation
    private String doctorDepartmentId;

    /** 所属科室 名称 */
    @UpdateAnnotation
    private String doctorDepartmentName;

    /** 身份证号 */
    @UpdateAnnotation
    private String doctorIcard;

    /** 医生性别 */
    @UpdateAnnotation
    private String doctorSex;

    /** 出生日期 */
    @UpdateAnnotation
    private String doctorBirthday;

    /** 医生手机 */
    @UpdateAnnotation
    private String doctorPhone;

    /** 医生职位 */
    @UpdateAnnotation
    private String doctorJob;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "doctor_id")
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Column(name = "doctor_name")
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Column(name = "doctor_department_id")
    public String getDoctorDepartmentId() {
        return doctorDepartmentId;
    }

    public void setDoctorDepartmentId(String doctorDepartmentId) {
        this.doctorDepartmentId = doctorDepartmentId;
    }

    @Column(name = "doctor_icard")
    public String getDoctorIcard() {
        return doctorIcard;
    }

    public void setDoctorIcard(String doctorIcard) {
        this.doctorIcard = doctorIcard;
    }

    @Column(name = "doctor_sex")
    public String getDoctorSex() {
        return doctorSex;
    }

    public void setDoctorSex(String doctorSex) {
        this.doctorSex = doctorSex;
    }

    @Column(name = "doctor_birthday")
    public String getDoctorBirthday() {
        return doctorBirthday;
    }

    public void setDoctorBirthday(String doctorBirthday) {
        this.doctorBirthday = doctorBirthday;
    }

    @Column(name = "doctor_phone")
    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    @Column(name = "doctor_job")
    public String getDoctorJob() {
        return doctorJob;
    }

    public void setDoctorJob(String doctorJob) {
        this.doctorJob = doctorJob;
    }

    @Column(name = "doctor_department_name")
    public String getDoctorDepartmentName() {
        return doctorDepartmentName;
    }

    public void setDoctorDepartmentName(String doctorDepartmentName) {
        this.doctorDepartmentName = doctorDepartmentName;
    }

}
