
package com.mnt.preg.examitem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 膳食及生活方式评估明细记录
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-6 mnt_zhangjing 初版
 */
@Entity
@Table(name = "cus_life_style_record_detail")
public class LifeStyleDetail extends MappedEntity {

    private static final long serialVersionUID = 3072445668040737697L;

    /** 主键 */
    private String id;

    /** 问题编号 */
    private String problemId;

    /** 答案编号 */
    private String optionId;

    /** 选项类型 1 填空类型 2 选项类型 */
    private String optionType;

    /** 填写内容（未使用） */
    private String optionContent;

    /** 频次信息 */
    private String frequency;

    /** 附加值 */
    private String addvalue;

    /** 记录主键 */
    private String recordId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "problem_id")
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Column(name = "option_id")
    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    @Column(name = "option_type")
    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    @Column(name = "option_content")
    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    @Column(name = "frequency")
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Column(name = "addvalue")
    public String getAddvalue() {
        return addvalue;
    }

    public void setAddvalue(String addvalue) {
        this.addvalue = addvalue;
    }

    @Column(name = "record_id")
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

}
