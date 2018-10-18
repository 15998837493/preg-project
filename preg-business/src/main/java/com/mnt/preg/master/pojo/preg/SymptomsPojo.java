
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 功能症状信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
public class SymptomsPojo implements Serializable {

    private static final long serialVersionUID = 3202932227772407851L;

    /** 主键id */
    @QueryFieldAnnotation
    private String id;

    /** 编码 */
    @QueryFieldAnnotation
    private String sympCode;

    /** 器官类别 */
    @QueryFieldAnnotation
    private String sympCategory;

    /** 器官名称 */
    @QueryFieldAnnotation
    private String sympPart;

    /** 人群 */
    @QueryFieldAnnotation
    private String sympSex;

    /** 症状名称 */
    @QueryFieldAnnotation
    private String sympName;

    /** 系统功能对应症状规则 */
    @QueryFieldAnnotation
    private String sympFunction;

    /** 症状的判断 */
    @QueryFieldAnnotation
    private String sympJudge;

    /** 可能的患病结果 */
    @QueryFieldAnnotation
    private String sympDisease;

    /** 建议做的检查 */
    @QueryFieldAnnotation
    private String sympPositive;

    /** 是否参与医学症状评分 */
    @QueryFieldAnnotation
    private String sympIsScore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSympCode() {
        return sympCode;
    }

    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    public String getSympCategory() {
        return sympCategory;
    }

    public void setSympCategory(String sympCategory) {
        this.sympCategory = sympCategory;
    }

    public String getSympPart() {
        return sympPart;
    }

    public void setSympPart(String sympPart) {
        this.sympPart = sympPart;
    }

    public String getSympSex() {
        return sympSex;
    }

    public void setSympSex(String sympSex) {
        this.sympSex = sympSex;
    }

    public String getSympName() {
        return sympName;
    }

    public void setSympName(String sympName) {
        this.sympName = sympName;
    }

    public String getSympFunction() {
        return sympFunction;
    }

    public void setSympFunction(String sympFunction) {
        this.sympFunction = sympFunction;
    }

    public String getSympJudge() {
        return sympJudge;
    }

    public void setSympJudge(String sympJudge) {
        this.sympJudge = sympJudge;
    }

    public String getSympPositive() {
        return sympPositive;
    }

    public void setSympPositive(String sympPositive) {
        this.sympPositive = sympPositive;
    }

    public String getSympDisease() {
        return sympDisease;
    }

    public void setSympDisease(String sympDisease) {
        this.sympDisease = sympDisease;
    }

    public String getSympIsScore() {
        return sympIsScore;
    }

    public void setSympIsScore(String sympIsScore) {
        this.sympIsScore = sympIsScore;
    }

}
