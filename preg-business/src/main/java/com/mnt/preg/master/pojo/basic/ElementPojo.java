
package com.mnt.preg.master.pojo.basic;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 元素表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-11-2 gss 初版
 */
public class ElementPojo implements Serializable {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 编码 */
    @QueryFieldAnnotation
    private String nutrientId;

    /** 英文名称名称 */
    @QueryFieldAnnotation
    private String nutrientNameEnglish;

    /** 名称 */
    @QueryFieldAnnotation
    private String nutrientName;

    /** 单位 */
    @QueryFieldAnnotation
    private String nutrientUnit;

    /** 类别 */
    @QueryFieldAnnotation
    private String nutrientType;

    /** 是否可评估(检测项目) */
    @QueryFieldAnnotation
    private String nutrientEvalOne;

    /** 是否可评估(开处方) */
    @QueryFieldAnnotation
    private String nutrientEvalTwo;

    /** 元素量 */
    private BigDecimal nutrientDosage;

    /** 单次用量 */
    private BigDecimal productDosage;

    /** 用药频次 */
    private String productFrequency;

    /** 补充剂名称 */
    private String productName;

    /** 产品主键 */
    private String productId;

    /** 剂量单位名称 */
    private String nutrientUnitName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

    public String getNutrientNameEnglish() {
        return nutrientNameEnglish;
    }

    public void setNutrientNameEnglish(String nutrientNameEnglish) {
        this.nutrientNameEnglish = nutrientNameEnglish;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
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

    public BigDecimal getNutrientDosage() {
        return nutrientDosage;
    }

    public void setNutrientDosage(BigDecimal nutrientDosage) {
        this.nutrientDosage = nutrientDosage;
    }

    public BigDecimal getProductDosage() {
        return productDosage;
    }

    public void setProductDosage(BigDecimal productDosage) {
        this.productDosage = productDosage;
    }

    public String getProductFrequency() {
        return productFrequency;
    }

    public void setProductFrequency(String productFrequency) {
        this.productFrequency = productFrequency;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNutrientUnitName() {
        return nutrientUnitName;
    }

    public void setNutrientUnitName(String nutrientUnitName) {
        this.nutrientUnitName = nutrientUnitName;
    }

}
