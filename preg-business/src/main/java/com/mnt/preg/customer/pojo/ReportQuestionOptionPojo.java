
package com.mnt.preg.customer.pojo;

import java.io.Serializable;

/**
 * 
 * 问卷操作记录表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-3 gss 初版
 */
public class ReportQuestionOptionPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    // 问题选项表id
    private String problemOptionId;

    // 问卷分配编号
    private String questionAllocId;

    // 答案编号
    private String answerId;

    // 问题内容
    private String problemContent;

    // 问题类型1 单选 2 多选 3 简答 4 是非题
    private String problemType;

    // 选项内容
    private String optionContent;

    // 选项类型
    private String optionType;

    // 答案内容
    private Integer answerContent;

    public String getProblemOptionId() {
        return problemOptionId;
    }

    public void setProblemOptionId(String problemOptionId) {
        this.problemOptionId = problemOptionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
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

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public Integer getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(Integer answerContent) {
        this.answerContent = answerContent;
    }

    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

}
