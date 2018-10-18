/*
 * Question.java    1.0  2016-2-29
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.entity.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 问卷表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-29 mnt_zhangjing 初版
 */

@Entity
@Table(name = "mas_question")
public class Question extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 问卷编号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String questionId;

    /** 问卷名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String questionName;

    /** 问卷排序 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private Integer questionOrder;

    /** 问卷类别:主要用于问卷分析 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String questionCategory;

    /** 问卷类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String questionType;

    /** 问卷状态 1=使用，0=不使用 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String questionState;

    /** 问卷描述 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String questionDesc;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Column(name = "question_name")
    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    @Column(name = "question_order")
    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }

    @Column(name = "question_category")
    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    @Column(name = "question_type")
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Column(name = "question_state")
    public String getQuestionState() {
        return questionState;
    }

    public void setQuestionState(String questionState) {
        this.questionState = questionState;
    }

    @Column(name = "question_desc")
    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    @Override
    public String toString() {
        return "Question [questionId=" + questionId + ", questionName=" + questionName + ", questionOrder="
                + questionOrder + ", questionCategory=" + questionCategory + ", questionType=" + questionType
                + ", questionState=" + questionState + ", questionDesc=" + questionDesc + "]";
    }

}
