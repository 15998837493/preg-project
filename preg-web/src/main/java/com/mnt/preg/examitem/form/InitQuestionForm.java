/*
 * InitQuestionForm.java	 1.0   2016-3-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.form;

/**
 * 
 * 问卷调查初始化表单
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-8 mnt_zhangjing 初版
 */
public class InitQuestionForm {

    /** 问卷编号 */
    private String questionId;

    /** 问卷答题客户（客户编号） */
    private String custId;

    /** 接诊信息主键 */
    private String diagnosisId;

    /** 接诊检测项目主键 */
    private String id;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
