
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 检查项目结果
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-28 zcq 初版
 */
public class ExamResultRecordPojo implements Serializable {

    private static final long serialVersionUID = -4928579249535314727L;

    /** 体检id */
    @QueryFieldAnnotation
    private String examId;

    /** 检查编码 */
    @QueryFieldAnnotation
    private String examCode;

    /** 检查类别 */
    @QueryFieldAnnotation
    private String examCategory;

    /** 检查日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date examDate;

    /** 报告路径 */
    @QueryFieldAnnotation
    private String examPdf;

    /** 客户id */
    @QueryFieldAnnotation
    private String custId;

    /** 客户编号 */
    private String custCode;

    /** 会员姓名 */
    @QueryFieldAnnotation
    private String custName;

    /** 会员性别 */
    @QueryFieldAnnotation
    private String custSex;

    /** 年龄 */
    @QueryFieldAnnotation
    private Integer custAge;

    /** 诊断项目编码 */
    @QueryFieldAnnotation
    private String diagnosisResultCodes;

    /** 诊断项目名称 */
    @QueryFieldAnnotation
    private String diagnosisResultNames;

    /** 检测项目编码 */
    private String inspectCode;

    /** 检测项目名称 */
    private String inspectName;

    /** 客户身份证号 */
    private String customerIcard;

    /** 客户电话号 */
    private String customerPhone;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getExamPdf() {
        return examPdf;
    }

    public void setExamPdf(String examPdf) {
        this.examPdf = examPdf;
    }

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

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public String getDiagnosisResultCodes() {
        return diagnosisResultCodes;
    }

    public void setDiagnosisResultCodes(String diagnosisResultCodes) {
        this.diagnosisResultCodes = diagnosisResultCodes;
    }

    public String getDiagnosisResultNames() {
        return diagnosisResultNames;
    }

    public void setDiagnosisResultNames(String diagnosisResultNames) {
        this.diagnosisResultNames = diagnosisResultNames;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getCustomerIcard() {
        return customerIcard;
    }

    public void setCustomerIcard(String customerIcard) {
        this.customerIcard = customerIcard;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

}
