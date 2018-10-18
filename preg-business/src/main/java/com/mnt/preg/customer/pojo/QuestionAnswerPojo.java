/*
 * QuestionAnswerVo.java	 1.0   2016-3-1
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 问卷答案记录表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 mnt_zhangjing 初版
 */
public class QuestionAnswerPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 答案编号 */
    @QueryFieldAnnotation
    private String answerId;

    /** 问卷记录编号 */
    @QueryFieldAnnotation
    private String questionAllocId;

    /** 问卷编号 */
    @QueryFieldAnnotation
    private String problemId;

    /** 选项编号 */
    @QueryFieldAnnotation
    private String problemOptionId;

    /** 填写内容 */
    @QueryFieldAnnotation
    private String answerContent;

    /** 选项内容 */
    private String optionContent;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemOptionId() {
        return problemOptionId;
    }

    public void setProblemOptionId(String problemOptionId) {
        this.problemOptionId = problemOptionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    @Override
    public String toString() {
        return "QuestionAnswerBo [answerId=" + answerId + ", questionAllocId=" + questionAllocId + ", problemId="
                + problemId
                + ", problemOptionId=" + problemOptionId + ", answerContent=" + answerContent + "]";
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

}
