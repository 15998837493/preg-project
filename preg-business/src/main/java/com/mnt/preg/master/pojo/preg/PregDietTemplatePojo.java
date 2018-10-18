
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 食谱模版表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 zcq 初版
 */
public class PregDietTemplatePojo implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 模版编码 */
    @QueryFieldAnnotation
    private String dietId;

    /** 模版名称 */
    @QueryFieldAnnotation
    private String dietName;

    /** 模版类型 */
    @QueryFieldAnnotation
    private String dietType;

    /** 膳食结构 */
    @QueryFieldAnnotation
    private String foodStructureId;

    /** 模版介绍 */
    @QueryFieldAnnotation
    private String dietDesc;

    /** 膳食结构 */
    private String foodStructure;

    /** 创建人名字 */
    private String createUserName;

    /** 孕期阶段 */
    @QueryFieldAnnotation
    private String pregnantStage;

    public String getPregnantStage() {
        return pregnantStage;
    }

    public void setPregnantStage(String pregnantStage) {
        this.pregnantStage = pregnantStage;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public String getFoodStructure() {
        return foodStructure;
    }

    public void setFoodStructure(String foodStructure) {
        this.foodStructure = foodStructure;
    }

}
