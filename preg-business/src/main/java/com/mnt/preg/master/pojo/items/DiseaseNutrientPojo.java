/*
 * DiseaseNutrientPojo.java    1.0  2017-10-13
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 疾病元素关联表
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-13 scd 初版
 */
public class DiseaseNutrientPojo implements Serializable {

    private static final long serialVersionUID = -3890191808834169669L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 疾病编码 */
    @QueryFieldAnnotation
    private String diseaseCode;

    /** 元素编码 */
    @QueryFieldAnnotation
    private String nutrientId;

    /** 元素名称 */
    @QueryFieldAnnotation(aliasName = "nutrient")
    private String nutrientName;

    /** 英文名称 */
    @QueryFieldAnnotation(aliasName = "nutrient")
    private String nutrientNameEnglish;

    /** 单位 */
    @QueryFieldAnnotation(aliasName = "nutrient")
    private String nutrientUnit;

    /** 类别 */
    @QueryFieldAnnotation(aliasName = "nutrient")
    private String nutrientType;

    /** 是否可评估(检测项目) */
    @QueryFieldAnnotation(aliasName = "nutrient")
    private String nutrientEvalOne;

    /** 是否可评估(开处方) */
    @QueryFieldAnnotation(aliasName = "nutrient")
    private String nutrientEvalTwo;

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

    public String getNutrientNameEnglish() {
        return nutrientNameEnglish;
    }

    public void setNutrientNameEnglish(String nutrientNameEnglish) {
        this.nutrientNameEnglish = nutrientNameEnglish;
    }

    public String getNutrientUnit() {
        return nutrientUnit;
    }

    public void setNutrientUnit(String nutrientUnit) {
        this.nutrientUnit = nutrientUnit;
    }

    public String getNutrientType() {
        return nutrientType;
    }

    public void setNutrientType(String nutrientType) {
        this.nutrientType = nutrientType;
    }

    public String getNutrientEvalOne() {
        return nutrientEvalOne;
    }

    public void setNutrientEvalOne(String nutrientEvalOne) {
        this.nutrientEvalOne = nutrientEvalOne;
    }

    public String getNutrientEvalTwo() {
        return nutrientEvalTwo;
    }

    public void setNutrientEvalTwo(String nutrientEvalTwo) {
        this.nutrientEvalTwo = nutrientEvalTwo;
    }

}
