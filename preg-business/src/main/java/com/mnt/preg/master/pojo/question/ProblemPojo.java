/*
 * FoodExtVo.java	 1.0   2015-1-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.question;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问题信息
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 gss 初版
 */
public class ProblemPojo implements Serializable {

    private static final long serialVersionUID = 3533537421320729186L;

    /** 问题编号 */
    @QueryFieldAnnotation
    private String problemId;

    /** 问题内容 */
    @QueryFieldAnnotation
    private String problemContent;

    /** 问题类型 代码表：1 单选 2 多选 3 是非题 4 简答 */
    @QueryFieldAnnotation
    private String problemType;

    /** 问题筛选性别 */
    @QueryFieldAnnotation
    private String problemSex;

    /** 问题分类 */
    @QueryFieldAnnotation
    private String problemCategory;

    /** 删除标识 */
    @QueryFieldAnnotation
    private Integer flag;

    /** 必答 */
    @QueryFieldAnnotation
    private Integer problemRequired;

    /** 提示 */
    @QueryFieldAnnotation
    private String problemHint;

    private String optionString;

    private String optionValidate;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getOptionString() {
        return optionString;
    }

    public void setOptionString(String optionString) {
        this.optionString = optionString;
    }

    public String getOptionValidate() {
        return optionValidate;
    }

    public void setOptionValidate(String optionValidate) {
        this.optionValidate = optionValidate;
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

}
