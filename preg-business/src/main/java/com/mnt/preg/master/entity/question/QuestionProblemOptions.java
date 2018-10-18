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
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 问题选项表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-29 gss 初版
 */

@Entity
@Table(name = "mas_question_problem_options")
public class QuestionProblemOptions extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 选项编号 */
    private String problemOptionId;

    /** 选项内容 */
    @UpdateAnnotation
    private String optionContent;

    /** 选项类型1=填空，2=选项 */
    @UpdateAnnotation
    private String optionType;

    /** 是否是默认选项 1=是，0=否 */
    @UpdateAnnotation
    private int optionDefault;

    /** 选项排序 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    @UpdateAnnotation
    private Integer optionOrder;

    /** 选项验证 */
    @UpdateAnnotation
    private String optionValidate;

    /** 是否是可填空 1=是，0=否 */
    @UpdateAnnotation
    private int optionWrite;

    /** 适合性别 */
    @UpdateAnnotation
    private String optionSex;

    /** 所属问题 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String problemId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "problem_option_id")
    public String getProblemOptionId() {
        return problemOptionId;
    }

    public void setProblemOptionId(String problemOptionId) {
        this.problemOptionId = problemOptionId;
    }

    @Column(name = "option_content")
    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    @Column(name = "option_type")
    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    @Column(name = "option_validate")
    public String getOptionValidate() {
        return optionValidate;
    }

    public void setOptionValidate(String optionValidate) {
        this.optionValidate = optionValidate;
    }

    @Column(name = "option_sex")
    public String getOptionSex() {
        return optionSex;
    }

    public void setOptionSex(String optionSex) {
        this.optionSex = optionSex;
    }

    @Column(name = "option_order")
    public Integer getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }

    @Column(name = "problem_id")
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Column(name = "option_default")
    public int getOptionDefault() {
        return optionDefault;
    }

    public void setOptionDefault(int optionDefault) {
        this.optionDefault = optionDefault;
    }

    @Column(name = "option_write")
    public int getOptionWrite() {
        return optionWrite;
    }

    public void setOptionWrite(int optionWrite) {
        this.optionWrite = optionWrite;
    }

    @Override
    public String toString() {
        return "QuestionProblemOptions [problemOptionId=" + problemOptionId + ", optionContent=" + optionContent
                + ", optionType=" + optionType + ", optionDefault=" + optionDefault + ", optionOrder=" + optionOrder
                + ", optionValidate=" + optionValidate + ", optionWrite=" + optionWrite + ", optionSex=" + optionSex
                + ", problemId=" + problemId + "]";
    }

}
