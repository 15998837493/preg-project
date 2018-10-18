/*
 * QuestionVo.java	 1.0   2016-2-29
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.question;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 
 * 问卷
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-29 mnt_zhangjing 初版
 */
public class QuestionPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 问卷编号 */
    @QueryFieldAnnotation
    private String questionId;

    /** 问卷名称 */
    @QueryFieldAnnotation
    private String questionName;

    /** 问卷排序 */
    @QueryFieldAnnotation
    private Integer questionOrder;

    /** 问卷类别:主要用于问卷分析 */
    @QueryFieldAnnotation
    private String questionCategory;

    /** 问卷类型 */
    @QueryFieldAnnotation
    private String questionType;

    /** 问卷状态 1=使用，0=不使用 */
    @QueryFieldAnnotation
    private String questionState;

    /** 问卷描述 */
    @QueryFieldAnnotation
    private String questionDesc;

    /** 删除标识 */
    @QueryFieldAnnotation
    private Integer flag;

    /** 问卷下所有问题 */
    private List<QuestionProblemsPojo> problemVos;

    /** 答案信息 */
    private List<QuestionAnswerPojo> answerVos;

    /** 分配号 */
    private String allowId;

    /** 登记信息 */
    private PregDiagnosisPojo diagnosisVo;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }

    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionState() {
        return questionState;
    }

    public void setQuestionState(String questionState) {
        this.questionState = questionState;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<QuestionProblemsPojo> getProblemVos() {
        return problemVos;
    }

    public void setProblemVos(List<QuestionProblemsPojo> problemVos) {
        this.problemVos = problemVos;
    }

    public List<QuestionAnswerPojo> getAnswerVos() {
        return answerVos;
    }

    public void setAnswerVos(List<QuestionAnswerPojo> answerVos) {
        this.answerVos = answerVos;
    }

    public String getAllowId() {
        return allowId;
    }

    public void setAllowId(String allowId) {
        this.allowId = allowId;
    }

    public PregDiagnosisPojo getDiagnosisVo() {
        return diagnosisVo;
    }

    public void setDiagnosisVo(PregDiagnosisPojo diagnosisVo) {
        this.diagnosisVo = diagnosisVo;
    }

}
