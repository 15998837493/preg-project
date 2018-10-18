
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.system.pojo.InstitutionPojo;

/**
 * 会员干预方案展示表信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-29 zcq 初版
 */
public class PregIntervenePlanGroupPojo implements Serializable {

    private static final long serialVersionUID = 1329300710124428725L;

    /** 干预方案主表信息 */
    private PregIntervenePlanPojo planVo;

    /** 干预方案--摄入量模版 */
    private List<PregPlanIntakeDetailPojo> planIntakeDetailList;

    /** 干预方案--食谱列表 */
    private List<PregPlanDietPojo> planDietList;

    /** 干预方案--饮食原则 */
    private List<PregPlanPointsPojo> planPointList;

    /** 干预方案--教育课程 */
    private List<PregPlanCoursePojo> planPregCourseList;

    /** 干预方案--诊断课程 */
    private List<PregPlanCoursePojo> planDiseaseCourseList;

    /** 干预方案--处方 */
    private List<DiagnosisPrescriptionPojo> extenderList;

    /** 干预会员信息 */
    private CustomerPojo customerVo;

    /** 孕期建档信息 */
    private PregArchivesPojo pregArchivesPojo;

    /** 机构信息 */
    private InstitutionPojo insInfo;

    /** 打印内容 */
    private List<String> dietItemList;

    /** 预约接诊信息 */
    private List<DiagnosisBookingPojo> diagnosisBookingList;

    /** 辅助检查信息 */
    private List<DiagnosisQuotaItemVo> fuzhuList;

    /** 课程预约信息 */
    private List<PregCourseBookingPojo> courseBookingList;

    /** 接诊信息 */
    private PregDiagnosisPojo diagnosis;

    public PregIntervenePlanPojo getPlanVo() {
        return planVo;
    }

    public void setPlanVo(PregIntervenePlanPojo planVo) {
        this.planVo = planVo;
    }

    public List<PregPlanIntakeDetailPojo> getPlanIntakeDetailList() {
        return planIntakeDetailList;
    }

    public void setPlanIntakeDetailList(List<PregPlanIntakeDetailPojo> planIntakeDetailList) {
        this.planIntakeDetailList = planIntakeDetailList;
    }

    public List<PregPlanDietPojo> getPlanDietList() {
        return planDietList;
    }

    public void setPlanDietList(List<PregPlanDietPojo> planDietList) {
        this.planDietList = planDietList;
    }

    public List<PregPlanPointsPojo> getPlanPointList() {
        return planPointList;
    }

    public void setPlanPointList(List<PregPlanPointsPojo> planPointList) {
        this.planPointList = planPointList;
    }

    public List<PregPlanCoursePojo> getPlanPregCourseList() {
        return planPregCourseList;
    }

    public void setPlanPregCourseList(List<PregPlanCoursePojo> planPregCourseList) {
        this.planPregCourseList = planPregCourseList;
    }

    public List<PregPlanCoursePojo> getPlanDiseaseCourseList() {
        return planDiseaseCourseList;
    }

    public void setPlanDiseaseCourseList(List<PregPlanCoursePojo> planDiseaseCourseList) {
        this.planDiseaseCourseList = planDiseaseCourseList;
    }

    public List<DiagnosisPrescriptionPojo> getExtenderList() {
        return extenderList;
    }

    public void setExtenderList(List<DiagnosisPrescriptionPojo> extenderList) {
        this.extenderList = extenderList;
    }

    public CustomerPojo getCustomerVo() {
        return customerVo;
    }

    public void setCustomerVo(CustomerPojo customerVo) {
        this.customerVo = customerVo;
    }

    public PregArchivesPojo getPregArchivesVo() {
        return pregArchivesPojo;
    }

    public void setPregArchivesVo(PregArchivesPojo pregArchivesPojo) {
        this.pregArchivesPojo = pregArchivesPojo;
    }

    public InstitutionPojo getInsInfo() {
        return insInfo;
    }

    public void setInsInfo(InstitutionPojo insInfo) {
        this.insInfo = insInfo;
    }

    public List<String> getDietItemList() {
        return dietItemList;
    }

    public void setDietItemList(List<String> dietItemList) {
        this.dietItemList = dietItemList;
    }

    public List<DiagnosisBookingPojo> getDiagnosisBookingList() {
        return diagnosisBookingList;
    }

    public void setDiagnosisBookingList(List<DiagnosisBookingPojo> diagnosisBookingList) {
        this.diagnosisBookingList = diagnosisBookingList;
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

    public PregArchivesPojo getPregArchivesPojo() {
        return pregArchivesPojo;
    }

    public void setPregArchivesPojo(PregArchivesPojo pregArchivesPojo) {
        this.pregArchivesPojo = pregArchivesPojo;
    }

    public PregDiagnosisPojo getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(PregDiagnosisPojo diagnosis) {
        this.diagnosis = diagnosis;
    }

}
