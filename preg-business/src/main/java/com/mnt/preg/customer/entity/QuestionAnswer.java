/*
 * QuestionAnswer.java    1.0  2016-2-29
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 答案记录表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-29 mnt_zhangjing 初版
 */
@Entity
@Table(name = "cus_question_answer")
public class QuestionAnswer extends MappedEntity {

    private static final long serialVersionUID = 1L;

    /** 答案编号 */
    private String answerId;

    /** 问卷记录编号 */
    @UpdateAnnotation
    private String questionAllocId;

    /** 问卷编号 */
    @UpdateAnnotation
    private String problemId;

    /** 选项编号 */
    @UpdateAnnotation
    private String proptid;

    /** 填写内容 */
    @UpdateAnnotation
    private String answerContent;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "answer_id")
    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    @Column(name = "problem_id")
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Column(name = "problem_option_id")
    public String getProptid() {
        return proptid;
    }

    public void setProptid(String proptid) {
        this.proptid = proptid;
    }

    @Column(name = "answer_content")
    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    @Column(name = "question_alloc_id")
    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

    @Override
    public String toString() {
        return "QuestionAnswer [answerId=" + answerId + ", questionAllocId=" + questionAllocId + ", problemId="
                + problemId
                + ", proptid=" + proptid + ", answerContent=" + answerContent + "]";
    }

}
