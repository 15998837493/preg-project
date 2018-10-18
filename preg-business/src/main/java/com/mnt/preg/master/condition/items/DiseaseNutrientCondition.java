/*
 * DiseaseNutrientCondition.java    1.0  2017-10-13
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.condition.items;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 疾病元素关联表
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-13 scd 初版
 */
public class DiseaseNutrientCondition implements Serializable {

    private static final long serialVersionUID = 5743859162555459353L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 疾病编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseCode;

    /** 疾病编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "diseaseCode")
    private List<String> diseaseCodeList;

    /** 元素编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String nutrientId;

    /** 元素名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, aliasName = "nutrient")
    private String nutrientName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public List<String> getDiseaseCodeList() {
        return diseaseCodeList;
    }

    public void setDiseaseCodeList(List<String> diseaseCodeList) {
        this.diseaseCodeList = diseaseCodeList;
    }

    public String getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

}
