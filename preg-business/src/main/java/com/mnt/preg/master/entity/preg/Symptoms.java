
package com.mnt.preg.master.entity.preg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 功能症状表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-11-2 gss 初版
 */
@Entity
@Table(name = "mas_symptoms")
public class Symptoms extends MappedEntity {

    private static final long serialVersionUID = -1021479740026100488L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String id;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, order = OrderConstants.ASC)
    @QueryFieldAnnotation
    private String sympCode;

    /** 器官类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympCategory;

    /** 器官名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympPart;

    /** 人群 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympSex;

    /** 症状名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympName;

    /** 系统功能对应症状规则 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympFunction;

    /** 症状的判断 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympJudge;

    /** 建议做的检查 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympDisease;

    /** 可能的患病结果 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympPositive;

    /** 是否参与医学症状评分 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String sympIsScore;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "symp_code")
    public String getSympCode() {
        return sympCode;
    }

    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    @Column(name = "symp_category")
    public String getSympCategory() {
        return sympCategory;
    }

    public void setSympCategory(String sympCategory) {
        this.sympCategory = sympCategory;
    }

    @Column(name = "symp_part")
    public String getSympPart() {
        return sympPart;
    }

    public void setSympPart(String sympPart) {
        this.sympPart = sympPart;
    }

    @Column(name = "symp_sex")
    public String getSympSex() {
        return sympSex;
    }

    public void setSympSex(String sympSex) {
        this.sympSex = sympSex;
    }

    @Column(name = "symp_name")
    public String getSympName() {
        return sympName;
    }

    public void setSympName(String sympName) {
        this.sympName = sympName;
    }

    @Column(name = "symp_function")
    public String getSympFunction() {
        return sympFunction;
    }

    public void setSympFunction(String sympFunction) {
        this.sympFunction = sympFunction;
    }

    @Column(name = "symp_judge")
    public String getSympJudge() {
        return sympJudge;
    }

    public void setSympJudge(String sympJudge) {
        this.sympJudge = sympJudge;
    }

    @Column(name = "symp_disease")
    public String getSympDisease() {
        return sympDisease;
    }

    public void setSympDisease(String sympDisease) {
        this.sympDisease = sympDisease;
    }

    @Column(name = "symp_positive")
    public String getSympPositive() {
        return sympPositive;
    }

    public void setSympPositive(String sympPositive) {
        this.sympPositive = sympPositive;
    }

    @Column(name = "symp_is_score")
    public String getSympIsScore() {
        return sympIsScore;
    }

    public void setSympIsScore(String sympIsScore) {
        this.sympIsScore = sympIsScore;
    }

}
