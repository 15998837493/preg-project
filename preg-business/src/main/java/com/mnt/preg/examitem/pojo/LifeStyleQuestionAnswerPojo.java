/*
 *LifeStyle QuestionAnswerBo.java	 1.0   2017-3-6
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.pojo;

import java.io.Serializable;

/**
 * 
 * 生活方式调查问卷答案记录
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-6 mnt_zhangjing 初版
 */
public class LifeStyleQuestionAnswerPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 答案编号 */
    private String optionId;

    /** 问卷编号 */
    private String problemId;

    /** 选项类型 1 填空类型 2 选项类型 */
    private String optionType;

    /** 填写内容（未使用） */
    private String optionContent;

    /** 频次信息 */
    private String frequency;

    /** 附加值 */
    private String addvalue;

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getAddvalue() {
        return addvalue;
    }

    public void setAddvalue(String addvalue) {
        this.addvalue = addvalue;
    }

}
