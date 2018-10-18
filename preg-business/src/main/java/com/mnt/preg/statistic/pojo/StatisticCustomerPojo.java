
package com.mnt.preg.statistic.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * 数据统计--返回患者列表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月20日 zcq 初版
 */
public class StatisticCustomerPojo {

    /** 患者主键 */
    private String custId;

    /** 患者姓名 */
    private String custName;

    /** 病案号 */
    private String custSikeId;

    /** ID */
    private String custPatientId;

    /** 年龄 */
    private Object custAge;

    /** 患者来源（营养科） */
    private String custOrg;

    /** 接诊医生 */
    private String doctorName;

    /** 接诊次数 */
    private Object diagnosisCount;

    /** 孕次（妊娠次数） */
    private Object pregnancyNum;

    /** 产次（分娩次数） */
    private Object birthNum;

    /** 受孕方式（妊娠期） */
    private String encyesisSituation;

    /** 受孕方式（产后 1：自然受孕；2：辅助生殖） */
    private Integer birthTiresType;

    /** 受孕方式（产后 1：意外怀孕；2：计划妊娠） */
    private Integer birthTiresType2;

    /** 孕产期 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pregnancyDueDate;

    /** 分娩时间 */
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSikeId() {
        return custSikeId;
    }

    public void setCustSikeId(String custSikeId) {
        this.custSikeId = custSikeId;
    }

    public String getCustPatientId() {
        return custPatientId;
    }

    public void setCustPatientId(String custPatientId) {
        this.custPatientId = custPatientId;
    }

    public Object getCustAge() {
        return custAge;
    }

    public void setCustAge(Object custAge) {
        this.custAge = custAge;
    }

    public String getCustOrg() {
        return custOrg;
    }

    public void setCustOrg(String custOrg) {
        this.custOrg = custOrg;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Object getDiagnosisCount() {
        return diagnosisCount;
    }

    public void setDiagnosisCount(Object diagnosisCount) {
        this.diagnosisCount = diagnosisCount;
    }

    public Object getPregnancyNum() {
        return pregnancyNum;
    }

    public void setPregnancyNum(Object pregnancyNum) {
        this.pregnancyNum = pregnancyNum;
    }

    public Object getBirthNum() {
        return birthNum;
    }

    public void setBirthNum(Object birthNum) {
        this.birthNum = birthNum;
    }

    public String getEncyesisSituation() {
        return encyesisSituation;
    }

    public void setEncyesisSituation(String encyesisSituation) {
        this.encyesisSituation = encyesisSituation;
    }

    public Integer getBirthTiresType() {
        return birthTiresType;
    }

    public void setBirthTiresType(Integer birthTiresType) {
        this.birthTiresType = birthTiresType;
    }

    public Integer getBirthTiresType2() {
        return birthTiresType2;
    }

    public void setBirthTiresType2(Integer birthTiresType2) {
        this.birthTiresType2 = birthTiresType2;
    }

    public Date getPregnancyDueDate() {
        return pregnancyDueDate;
    }

    public void setPregnancyDueDate(Date pregnancyDueDate) {
        this.pregnancyDueDate = pregnancyDueDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
