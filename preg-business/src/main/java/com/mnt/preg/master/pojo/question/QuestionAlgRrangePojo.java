
package com.mnt.preg.master.pojo.question;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 问卷算法取值范围Vo
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：v1.0 2016-3-8 gss 初版
 */
public class QuestionAlgRrangePojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 功能类型 */
    @QueryFieldAnnotation
    private Integer functionType;

    /** 等级名称 */
    @QueryFieldAnnotation
    private String gradeName;

    /** 取值范围最小值 */
    @QueryFieldAnnotation
    private Integer gradeRangeMin;

    /** 取值范围最大值 */
    @QueryFieldAnnotation
    private Integer gradeRangeMax;

    /** 等级结论 */
    @QueryFieldAnnotation
    private String gradeConclusion;

    /** 营养师建议 */
    @QueryFieldAnnotation
    private String advice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getGradeRangeMin() {
        return gradeRangeMin;
    }

    public void setGradeRangeMin(Integer gradeRangeMin) {
        this.gradeRangeMin = gradeRangeMin;
    }

    public Integer getGradeRangeMax() {
        return gradeRangeMax;
    }

    public void setGradeRangeMax(Integer gradeRangeMax) {
        this.gradeRangeMax = gradeRangeMax;
    }

    public String getGradeConclusion() {
        return gradeConclusion;
    }

    public void setGradeConclusion(String gradeConclusion) {
        this.gradeConclusion = gradeConclusion;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

}
