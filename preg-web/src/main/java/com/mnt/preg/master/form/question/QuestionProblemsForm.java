
package com.mnt.preg.master.form.question;

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

public class QuestionProblemsForm {

    /* 问题编号 */

    private String problemId;

    /* 问题内容 */

    private String problemContent;

    /* 问题类型 */

    private String problemType;

    /* 问题筛选性别 */

    private String problemSex;

    /* 问题分类 */

    private String problemCategory;

    /* 问题节点排序 */

    private Integer problemOrder;

    /* 问题节点级别 */

    private Integer problemLevel;

    /* 上级编号 */

    private String problemParentId;

    /* 是否子节点 */

    private Integer problemIsChildren;

    /* 规则 */

    private String problemRule;

    /* 所属问卷 */

    private String questionId;

    /* 题库中的问题ID */
    private String libraryProblemId;

    /** 选项字符串 */
    private String optionString;

    /** 选项内容 */
    private String optionContent;

    /** 选项验证 **/
    private String optionValidate;

    /** 选项ID **/
    private String optionId;

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

    public String getLibraryProblemId() {
        return libraryProblemId;
    }

    public void setLibraryProblemId(String libraryProblemId) {
        this.libraryProblemId = libraryProblemId;
    }

    public String getOptionString() {
        return optionString;
    }

    public void setOptionString(String optionString) {
        this.optionString = optionString;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionValidate() {
        return optionValidate;
    }

    public void setOptionValidate(String optionValidate) {
        this.optionValidate = optionValidate;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

}
