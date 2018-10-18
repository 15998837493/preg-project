/*
 * QuestionnaireBo.java	 1.0   2016-3-1
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.question;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 问卷操作记录表
 * 
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 mnt_zhangjing 初版
 */
public class QuestionRecordBo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 问卷分配编号 */
    @NotEmpty(message = "{question.alloc.id.not.empty}")
    private String questionAllocId;

    /** 客户编号（答题者） */
    private String custId;

    /** 问卷编号 */
    private String questionId;

    /** 问卷分配用户（用户编号） */
    private String userId;

    /** 问卷分配时间 */
    private Date allocDatetime;

    /** 问卷有效期 */
    private Date expiryDate;

    /** 提交日期 */
    private Date submitDate;

    /** 问卷状态 常量：1=完成，2=过期，3=作废，9=失败 */
    private Integer state;

    /** 数据机构 */
    private String createInsId;

    /** 诊疗编号 */
    private String diagnosisId;

    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAllocDatetime() {
        return allocDatetime;
    }

    public void setAllocDatetime(Date allocDatetime) {
        this.allocDatetime = allocDatetime;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
