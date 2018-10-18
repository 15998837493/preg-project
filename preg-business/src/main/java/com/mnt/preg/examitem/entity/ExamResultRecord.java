
package com.mnt.preg.examitem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 检查项目结果主表实体
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-20 zcq 初版
 */
@Entity
@Table(name = "cus_result_record")
public class ExamResultRecord extends MappedEntity {

    private static final long serialVersionUID = -4928579249535314727L;

    /** 体检id */
    private String examId;

    /** 检查编码 */
    private String examCode;

    /** 检查类别 */
    private String examCategory;

    /** 检查日期 */
    private Date examDate;

    /** 报告路径 */
    @UpdateAnnotation
    private String examPdf;

    /** 客户id */
    private String custId;

    /** 会员姓名 */
    private String custName;

    /** 会员性别 */
    private String custSex;

    /** 年龄 */
    private Integer custAge;

    /** 诊断项目编码 */
    @UpdateAnnotation
    private String diagnosisResultCodes;

    /** 诊断项目名称 */
    @UpdateAnnotation
    private String diagnosisResultNames;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "exam_id")
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @Column(name = "exam_code")
    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    @Column(name = "exam_category")
    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "exam_date")
    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    @Column(name = "exam_pdf")
    public String getExamPdf() {
        return examPdf;
    }

    public void setExamPdf(String examPdf) {
        this.examPdf = examPdf;
    }

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "cust_name")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Column(name = "cust_sex")
    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    @Column(name = "cust_age")
    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    @Column(name = "diagnosis_result_codes")
    public String getDiagnosisResultCodes() {
        return diagnosisResultCodes;
    }

    public void setDiagnosisResultCodes(String diagnosisResultCodes) {
        this.diagnosisResultCodes = diagnosisResultCodes;
    }

    @Column(name = "diagnosis_result_names")
    public String getDiagnosisResultNames() {
        return diagnosisResultNames;
    }

    public void setDiagnosisResultNames(String diagnosisResultNames) {
        this.diagnosisResultNames = diagnosisResultNames;
    }

}
