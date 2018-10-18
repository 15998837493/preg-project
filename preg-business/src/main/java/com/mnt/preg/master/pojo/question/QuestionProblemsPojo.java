/*
 * FoodExtVo.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.question;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问题信息
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 gss 初版
 */
public class QuestionProblemsPojo implements Serializable {

    private static final long serialVersionUID = 3533537421320729186L;

    /** 问题编号 */
    @QueryFieldAnnotation
    private String problemId;

    /* 问题内容 */
    @QueryFieldAnnotation
    private String problemContent;

    /* 问题类型 */
    @QueryFieldAnnotation
    private String problemType;

    /* 问题筛选性别 */
    @QueryFieldAnnotation
    private String problemSex;

    /* 问题分类 */
    @QueryFieldAnnotation
    private String problemCategory;

    /* 问题节点排序 */
    @QueryFieldAnnotation
    private Integer problemOrder;

    /* 问题节点级别 */
    @QueryFieldAnnotation
    private Integer problemLevel;

    /* 上级编号 */
    @QueryFieldAnnotation
    private String problemParentId;

    /* 是否子节点 */
    @QueryFieldAnnotation
    private Integer problemIsChildren;

    /* 规则 */
    @QueryFieldAnnotation
    private String problemRule;

    /* 所属问卷 */
    @QueryFieldAnnotation
    private String questionId;

    /* 删除标识 */
    @QueryFieldAnnotation
    private Integer flag;

    /** 必答 */
    @QueryFieldAnnotation
    private Integer problemRequired;

    /** 提示 */
    @QueryFieldAnnotation
    private String problemHint;

    /** 标题名称 */
    private String name;

    /*---------------------------问卷显示用------------------------*/
    /** 问题排序号 */
    private String orderNo;

    /** 判断是否为子节点 */
    private boolean isChild = false;

    /** 问题下答案选项 */
    private List<QuestionProblemOptionsPojo> optionVos;

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getProblemSex() {
        return problemSex;
    }

    public void setProblemSex(String problemSex) {
        this.problemSex = problemSex;
    }

    public String getProblemCategory() {
        return problemCategory;
    }

    public void setProblemCategory(String problemCategory) {
        this.problemCategory = problemCategory;
    }

    public Integer getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(Integer problemOrder) {
        this.problemOrder = problemOrder;
    }

    public Integer getProblemLevel() {
        return problemLevel;
    }

    public void setProblemLevel(Integer problemLevel) {
        this.problemLevel = problemLevel;
    }

    public String getProblemParentId() {
        return problemParentId;
    }

    public void setProblemParentId(String problemParentId) {
        this.problemParentId = problemParentId;
    }

    public Integer getProblemIsChildren() {
        return problemIsChildren;
    }

    public void setProblemIsChildren(Integer problemIsChildren) {
        this.problemIsChildren = problemIsChildren;
    }

    public String getProblemRule() {
        return problemRule;
    }

    public void setProblemRule(String problemRule) {
        this.problemRule = problemRule;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getProblemRequired() {
        return problemRequired;
    }

    public void setProblemRequired(Integer problemRequired) {
        this.problemRequired = problemRequired;
    }

    public String getProblemHint() {
        return problemHint;
    }

    public void setProblemHint(String problemHint) {
        this.problemHint = problemHint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionProblemOptionsPojo> getOptionVos() {
        return optionVos;
    }

    public void setOptionVos(List<QuestionProblemOptionsPojo> optionVos) {
        this.optionVos = optionVos;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean isChild) {
        this.isChild = isChild;
    }

}
