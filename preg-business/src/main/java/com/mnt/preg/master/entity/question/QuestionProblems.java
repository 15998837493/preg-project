
package com.mnt.preg.master.entity.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 问卷问题明细表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-29 gss 初版
 */

@Entity
@Table(name = "mas_question_problems")
public class QuestionProblems extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /* 问题编号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String problemId;

    /* 问题内容 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String problemContent;

    /* 问题类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String problemType;

    /* 问题筛选性别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String problemSex;

    /* 问题分类 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String problemCategory;

    /* 问题节点排序 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    @UpdateAnnotation
    private Integer problemOrder;

    /* 问题节点级别 */
    @UpdateAnnotation
    private Integer problemLevel;

    /* 上级编号 */
    @UpdateAnnotation
    private String problemParentId;

    /* 是否子节点 */
    @UpdateAnnotation
    private Integer problemIsChildren;

    /* 规则 */
    @UpdateAnnotation
    private String problemRule;

    /* 所属问卷 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String questionId;

    /** 必答 */
    @UpdateAnnotation
    private Integer problemRequired;

    /** 提示 */
    @UpdateAnnotation
    private String problemHint;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "problem_id")
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Column(name = "problem_content")
    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    @Column(name = "problem_type")
    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    @Column(name = "problem_sex")
    public String getProblemSex() {
        return problemSex;
    }

    public void setProblemSex(String problemSex) {
        this.problemSex = problemSex;
    }

    @Column(name = "problem_category")
    public String getProblemCategory() {
        return problemCategory;
    }

    public void setProblemCategory(String problemCategory) {
        this.problemCategory = problemCategory;
    }

    @Column(name = "problem_order")
    public Integer getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(Integer problemOrder) {
        this.problemOrder = problemOrder;
    }

    @Column(name = "problem_level")
    public Integer getProblemLevel() {
        return problemLevel;
    }

    public void setProblemLevel(Integer problemLevel) {
        this.problemLevel = problemLevel;
    }

    @Column(name = "problem_parent_id")
    public String getProblemParentId() {
        return problemParentId;
    }

    public void setProblemParentId(String problemParentId) {
        this.problemParentId = problemParentId;
    }

    @Column(name = "problem_is_children")
    public Integer getProblemIsChildren() {
        return problemIsChildren;
    }

    public void setProblemIsChildren(Integer problemIsChildren) {
        this.problemIsChildren = problemIsChildren;
    }

    @Column(name = "problem_rule")
    public String getProblemRule() {
        return problemRule;
    }

    public void setProblemRule(String problemRule) {
        this.problemRule = problemRule;
    }

    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Column(name = "problem_required")
    public Integer getProblemRequired() {
        return problemRequired;
    }

    public void setProblemRequired(Integer problemRequired) {
        this.problemRequired = problemRequired;
    }

    @Column(name = "problem_hint")
    public String getProblemHint() {
        return problemHint;
    }

    public void setProblemHint(String problemHint) {
        this.problemHint = problemHint;
    }

}
