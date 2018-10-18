
package com.mnt.preg.master.form.preg;

/**
 * 食物模版信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-21 zcq 初版
 */
public class DietTemplateForm {

    /** 模版编码 */
    private String dietId;

    /** 模版名称 */
    private String dietName;

    /** 模板类型 */
    private String dietType;

    /** 膳食结构 */
    private String foodStructureId;

    /** 模板介绍 */
    private String dietDesc;

    /** 孕期阶段 */
    private String pregnantStage;

    public String getPregnantStage() {
        return pregnantStage;
    }

    public void setPregnantStage(String pregnantStage) {
        this.pregnantStage = pregnantStage;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public String getFoodStructureId() {
        return foodStructureId;
    }

    public void setFoodStructureId(String foodStructureId) {
        this.foodStructureId = foodStructureId;
    }

    public String getDietDesc() {
        return dietDesc;
    }

    public void setDietDesc(String dietDesc) {
        this.dietDesc = dietDesc;
    }
}
