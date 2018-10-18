
package com.mnt.preg.master.condition.preg;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 功能症状检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-1 zcq 初版
 */
public class SymptomsCondition implements Serializable {

    private static final long serialVersionUID = 1876656860359626335L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "id")
    private List<String> idList;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String sympCode;

    /** 器官类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String sympCategory;

    /** 器官名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String sympPart;

    /** 人群 */
    private String sympSex;

    /** 症状名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String sympName;

    /** 系统功能对应症状规则 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String sympFunction;

    /** 是否参与医学症状评分 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String sympIsScore;

    /** 删除标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
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

    public String getSympIsScore() {
        return sympIsScore;
    }

    public void setSympIsScore(String sympIsScore) {
        this.sympIsScore = sympIsScore;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
