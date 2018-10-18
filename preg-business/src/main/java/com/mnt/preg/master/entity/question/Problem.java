/*
 * Problem.java    1.0  2016-2-29
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
 * 问题表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-29 mnt_zhangjing 初版
 */

@Entity
@Table(name = "mas_problem")
public class Problem extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 问题编号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String problemId;

    /** 问题内容 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String problemContent;

    /** 问题类型 代码表：1 单选 2 多选 3 是非题 4 简答 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String problemType;

    /** 问题筛选性别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String problemSex;

    /** 问题分类 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String problemCategory;

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
