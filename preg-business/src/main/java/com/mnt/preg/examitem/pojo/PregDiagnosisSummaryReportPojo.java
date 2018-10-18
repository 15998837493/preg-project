
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;

/**
 * 病历小结
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-4-13 zcq 初版
 */
public class PregDiagnosisSummaryReportPojo implements Serializable {

    private static final long serialVersionUID = 94741465221638824L;

    /** 接诊信息 */
    private PregDiagnosisPojo diagnosisPojo;

    /** 问诊信息 */
    private PregDiagnosisObstetricalPojo diagnosisObstetricalPojo;

    /** 客户信息 */
    private CustomerPojo customerPojo;

    /** 孕期建档信息 */
    private PregArchivesPojo preArchivePojo;

    /** 辅助检查指标信息 */
    private List<DiagnosisHospitalInspectReportPojo> examItemList;

    /** 营养处方信息 */
    private List<DiagnosisPrescriptionPojo> extenderList;

    /** 膳食方案信息 */
    private PregIntervenePlanPojo planPojo;

    /** 预约接诊信息 */
    private List<DiagnosisBookingPojo> diagnosisList;

    /** 辅助检查信息 */
    private List<DiagnosisQuotaItemVo> fuzhuList;

    /** 课程预约信息 */
    private List<PregCourseBookingPojo> courseBookingList;

    /** 机构号 */
    private String insId;

    public PregDiagnosisPojo getDiagnosisPojo() {
        return diagnosisPojo;
    }

    public void setDiagnosisPojo(PregDiagnosisPojo diagnosisPojo) {
        this.diagnosisPojo = diagnosisPojo;
    }

    public CustomerPojo getCustomerPojo() {
        return customerPojo;
    }

    public void setCustomerPojo(CustomerPojo customerPojo) {
        this.customerPojo = customerPojo;
    }

    public PregArchivesPojo getPreArchivePojo() {
        return preArchivePojo;
    }

    public void setPreArchivePojo(PregArchivesPojo preArchivePojo) {
        this.preArchivePojo = preArchivePojo;
    }

    public List<DiagnosisPrescriptionPojo> getExtenderList() {
        return extenderList;
    }

    public void setExtenderList(List<DiagnosisPrescriptionPojo> extenderList) {
        this.extenderList = extenderList;
    }

    public PregIntervenePlanPojo getPlanPojo() {
        return planPojo;
    }

    public void setPlanPojo(PregIntervenePlanPojo planPojo) {
        this.planPojo = planPojo;
    }

    public List<DiagnosisQuotaItemVo> getFuzhuList() {
        return fuzhuList;
    }

    public void setFuzhuList(List<DiagnosisQuotaItemVo> fuzhuList) {
        this.fuzhuList = fuzhuList;
    }

    public List<PregCourseBookingPojo> getCourseBookingList() {
        return courseBookingList;
    }

    public void setCourseBookingList(List<PregCourseBookingPojo> courseBookingList) {
        this.courseBookingList = courseBookingList;
    }

    public List<DiagnosisBookingPojo> getDiagnosisList() {
        return diagnosisList;
    }

    public void setDiagnosisList(List<DiagnosisBookingPojo> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public PregDiagnosisObstetricalPojo getDiagnosisObstetricalPojo() {
        return diagnosisObstetricalPojo;
    }

    public void setDiagnosisObstetricalPojo(PregDiagnosisObstetricalPojo diagnosisObstetricalPojo) {
        this.diagnosisObstetricalPojo = diagnosisObstetricalPojo;
    }

    public List<DiagnosisHospitalInspectReportPojo> getExamItemList() {
        return examItemList;
    }

    public void setExamItemList(List<DiagnosisHospitalInspectReportPojo> examItemList) {
        this.examItemList = examItemList;
    }

}
