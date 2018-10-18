
package com.mnt.preg.master.form.question;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.master.pojo.question.OptionPojo;

/**
 * 问题信息表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-3-20 zcq 初版
 */
public class ProblemForm implements Serializable {

    private static final long serialVersionUID = -780641516317983499L;

    /** 问题编号 */
    private String problemId;

    /** 问题内容 */

    private String problemContent;

    /** 问题类型 代码表：1 单选 2 多选 3 是非题 4 简答 */

    private String problemType;

    /** 问题筛选性别 */

    private String problemSex;

    /** 问题分类 */

    private String problemCategory;

    /** 选项字符串 */
    private String optionString;

    /** 选项内容 */
    private String optionContent;

    /** 选项验证 **/
    private String optionValidate;

    /** 选项ID **/
    private String optionId;

    /** 必答 */
    private Integer problemRequired;

    /** 提示 */
    private String problemHint;

    List<OptionPojo> optionVos;

    private String questionId;

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

    public List<OptionPojo> getOptionVos() {
        return optionVos;
    }

    public void setOptionVos(List<OptionPojo> optionVos) {
        this.optionVos = optionVos;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

}
