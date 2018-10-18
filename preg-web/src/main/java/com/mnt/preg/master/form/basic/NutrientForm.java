
package com.mnt.preg.master.form.basic;

/**
 * 元素信息
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-21 gss 初版
 */
public class NutrientForm {

    /** 编码 */
    private String nutrientId;

    /** 名称 */
    private String nutrientName;

    /** 单位 */
    private String nutrientUnit;

    /** 类别 */
    private String nutrientType;

    /** 是否可评估(检测项目) */
    private String nutrientEvalOne;

    /** 是否可评估(开处方、剂量) */
    private String nutrientEvalTwo;

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
