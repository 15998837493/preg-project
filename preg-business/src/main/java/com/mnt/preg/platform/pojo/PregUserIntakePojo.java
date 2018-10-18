
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 膳食执行清单模板
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-7-3 zcq 初版
 */
public class PregUserIntakePojo implements Serializable {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 编码 */
    @QueryFieldAnnotation
    private String intakeId;

    /** 模板名称 */
    @QueryFieldAnnotation
    private String intakeName;

    /** 膳食模式 */
    @QueryFieldAnnotation
    private String intakeMode;

    /** 食谱模板 */
    @QueryFieldAnnotation
    private String dietId;

    /** 食谱模板名称 */
    @QueryFieldAnnotation
    private String dietName;

    /** 膳食结构 */
    @QueryFieldAnnotation
    private String foodStructureId;

    /** 营养食品（天） */
    @QueryFieldAnnotation
    private String intakeProductDesc;

    /** 套餐提示（2周） */
    @QueryFieldAnnotation
    private String intakePrompt;

    /** 热量上限 */
    @QueryFieldAnnotation
    private Integer intakeCaloricMax;

    /** 热量下限 */
    @QueryFieldAnnotation
    private Integer intakeCaloricMin;

    /** 实际热量 */
    @QueryFieldAnnotation
    private String intakeActualEnergy;

    /** 碳水化合物 */
    @QueryFieldAnnotation
    private String intakeCbr;

    /** 碳水化合物占比 */
    @QueryFieldAnnotation
    private String intakeCbrPercent;

    /** 蛋白质 */
    @QueryFieldAnnotation
    private String intakeProtein;

    /** 蛋白质占比 */
    @QueryFieldAnnotation
    private String intakeProteinPercent;

    /** 脂肪 */
    @QueryFieldAnnotation
    private String intakeFat;

    /** 脂肪占比 */
    @QueryFieldAnnotation
    private String intakeFatPercent;

    /** 摄入量描述 */
    @QueryFieldAnnotation
    private String intakeDesc;

    /** 孕期阶段 */
    @QueryFieldAnnotation
    private String pregnantStage;

    /** 创建人编码 */
    @QueryFieldAnnotation
    private String createUserId;

    /** 创建人名字 */
    private String createUserName;

    /** 膳食模式 */
    private String intakeModeName;

    /** 诊断 */
    @QueryFieldAnnotation
    private String intakeMark;

    /** 备注 */
    @QueryFieldAnnotation
    private String intakeRemark;

    /** 数据归属类型,code:DATA_BELONG_TYPE */
    @QueryFieldAnnotation
    private String dataType;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getIntakeMark() {
        return intakeMark;
    }

    public void setIntakeMark(String intakeMark) {
        this.intakeMark = intakeMark;
    }

    public String getIntakeRemark() {
        return intakeRemark;
    }

    public void setIntakeRemark(String intakeRemark) {
        this.intakeRemark = intakeRemark;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public String getPregnantStage() {
        return pregnantStage;
    }

    public void setPregnantStage(String pregnantStage) {
        this.pregnantStage = pregnantStage;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getIntakeMode() {
        return intakeMode;
    }

    public void setIntakeMode(String intakeMode) {
        this.intakeMode = intakeMode;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getFoodStructureId() {
        return foodStructureId;
    }

    public void setFoodStructureId(String foodStructureId) {
        this.foodStructureId = foodStructureId;
    }

    public String getIntakeProductDesc() {
        return intakeProductDesc;
    }

    public void setIntakeProductDesc(String intakeProductDesc) {
        this.intakeProductDesc = intakeProductDesc;
    }

    public String getIntakePrompt() {
        return intakePrompt;
    }

    public void setIntakePrompt(String intakePrompt) {
        this.intakePrompt = intakePrompt;
    }

    public Integer getIntakeCaloricMax() {
        return intakeCaloricMax;
    }

    public void setIntakeCaloricMax(Integer intakeCaloricMax) {
        this.intakeCaloricMax = intakeCaloricMax;
    }

    public Integer getIntakeCaloricMin() {
        return intakeCaloricMin;
    }

    public void setIntakeCaloricMin(Integer intakeCaloricMin) {
        this.intakeCaloricMin = intakeCaloricMin;
    }

    public String getIntakeActualEnergy() {
        return intakeActualEnergy;
    }

    public void setIntakeActualEnergy(String intakeActualEnergy) {
        this.intakeActualEnergy = intakeActualEnergy;
    }

    public String getIntakeCbr() {
        return intakeCbr;
    }

    public void setIntakeCbr(String intakeCbr) {
        this.intakeCbr = intakeCbr;
    }

    public String getIntakeCbrPercent() {
        return intakeCbrPercent;
    }

    public void setIntakeCbrPercent(String intakeCbrPercent) {
        this.intakeCbrPercent = intakeCbrPercent;
    }

    public String getIntakeProtein() {
        return intakeProtein;
    }

    public void setIntakeProtein(String intakeProtein) {
        this.intakeProtein = intakeProtein;
    }

    public String getIntakeProteinPercent() {
        return intakeProteinPercent;
    }

    public void setIntakeProteinPercent(String intakeProteinPercent) {
        this.intakeProteinPercent = intakeProteinPercent;
    }

    public String getIntakeFat() {
        return intakeFat;
    }

    public void setIntakeFat(String intakeFat) {
        this.intakeFat = intakeFat;
    }

    public String getIntakeFatPercent() {
        return intakeFatPercent;
    }

    public void setIntakeFatPercent(String intakeFatPercent) {
        this.intakeFatPercent = intakeFatPercent;
    }

    public String getIntakeDesc() {
        return intakeDesc;
    }

    public void setIntakeDesc(String intakeDesc) {
        this.intakeDesc = intakeDesc;
    }

    public String getIntakeModeName() {
        return intakeModeName;
    }

    public void setIntakeModeName(String intakeModeName) {
        this.intakeModeName = intakeModeName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

}
