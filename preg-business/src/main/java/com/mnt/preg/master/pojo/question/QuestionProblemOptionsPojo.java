/*
 * Option.java    1.0  2016-5-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.question;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 答案选项表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */
public class QuestionProblemOptionsPojo implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 选项编号 */
    @QueryFieldAnnotation
    private String problemOptionId;

    /** 选项内容 */
    @QueryFieldAnnotation
    private String optionContent;

    /** 选项类型1=填空，2=选项 */
    @QueryFieldAnnotation
    private String optionType;

    /** 是否是默认选项 1=是，0=否 */
    @QueryFieldAnnotation
    private int optionDefault;

    /** 选项排序 */
    @QueryFieldAnnotation
    private Integer optionOrder;

    /** 选项验证 */
    @QueryFieldAnnotation
    private String optionValidate;

    /** 是否是可填空 1=是，0=否 */
    @QueryFieldAnnotation
    private int optionWrite;

    /** 适合性别 */
    @QueryFieldAnnotation
    private String optionSex;

    /** 所属问题 */
    @QueryFieldAnnotation
    private String problemId;

    /** 删除标识 */
    @QueryFieldAnnotation
    private Integer flag;

    /*** 问卷页面显示用 ****/
    /** 子问题字符串以,分割 */
    private String childProblemIds;

    public String getProblemOptionId() {
        return problemOptionId;
    }

    public void setProblemOptionId(String problemOptionId) {
        this.problemOptionId = problemOptionId;
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

    public Integer getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }

    public String getOptionValidate() {
        return optionValidate;
    }

    public void setOptionValidate(String optionValidate) {
        this.optionValidate = optionValidate;
    }

    public String getOptionSex() {
        return optionSex;
    }

    public void setOptionSex(String optionSex) {
        this.optionSex = optionSex;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public int getOptionDefault() {
        return optionDefault;
    }

    public void setOptionDefault(int optionDefault) {
        this.optionDefault = optionDefault;
    }

    public int getOptionWrite() {
        return optionWrite;
    }

    public void setOptionWrite(int optionWrite) {
        this.optionWrite = optionWrite;
    }

    public String getChildProblemIds() {
        return childProblemIds;
    }

    public void setChildProblemIds(String childProblemIds) {
        this.childProblemIds = childProblemIds;
    }

}
