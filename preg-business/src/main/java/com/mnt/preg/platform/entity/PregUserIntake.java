
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 膳食执行清单模板表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-7-3 zcq 初版
 */
@Entity
@Table(name = "user_intake")
public class PregUserIntake extends MappedEntity {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 编码 */
    private String intakeId;

    /** 模板名称 */
    @UpdateAnnotation
    private String intakeName;

    /** 膳食模式 */
    @UpdateAnnotation
    private String intakeMode;

    /** 膳食结构 */
    @UpdateAnnotation
    private String foodStructureId;

    /** 营养食品（天） */
    @UpdateAnnotation
    private String intakeProductDesc;

    /** 套餐提示（2周） */
    @UpdateAnnotation
    private String intakePrompt;

    /** 食谱模板 */
    @UpdateAnnotation
    private String dietId;

    /** 食谱模板名称 */
    @UpdateAnnotation
    private String dietName;

    /** 热量上限 */
    @UpdateAnnotation
    private String intakeCaloricMax;

    /** 热量下限 */
    @UpdateAnnotation
    private String intakeCaloricMin;

    /** 实际热量 */
    @UpdateAnnotation
    private String intakeActualEnergy;

    /** 碳水化合物 */
    @UpdateAnnotation
    private String intakeCbr;

    /** 碳水化合物占比 */
    @UpdateAnnotation
    private String intakeCbrPercent;

    /** 蛋白质 */
    @UpdateAnnotation
    private String intakeProtein;

    /** 蛋白质占比 */
    @UpdateAnnotation
    private String intakeProteinPercent;

    /** 脂肪 */
    @UpdateAnnotation
    private String intakeFat;

    /** 脂肪占比 */
    @UpdateAnnotation
    private String intakeFatPercent;

    /** 摄入量描述 */
    @UpdateAnnotation
    private String intakeDesc;

    /** 孕期阶段 */
    @UpdateAnnotation
    private String pregnantStage;

    /** 诊断 */
    @UpdateAnnotation
    private String intakeMark;

    /** 备注 */
    @UpdateAnnotation
    private String intakeRemark;

    /** 数据归属类型,code:DATA_BELONG_TYPE */
    @UpdateAnnotation
    private String dataType;

    @Column(name = "data_type")
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(name = "intake_mark")
    public String getIntakeMark() {
        return intakeMark;
    }

    public void setIntakeMark(String intakeMark) {
        this.intakeMark = intakeMark;
    }

    @Column(name = "intake_remark")
    public String getIntakeRemark() {
        return intakeRemark;
    }

    public void setIntakeRemark(String intakeRemark) {
        this.intakeRemark = intakeRemark;
    }

    @Column(name = "pregnant_stage")
    public String getPregnantStage() {
        return pregnantStage;
    }

    public void setPregnantStage(String pregnantStage) {
        this.pregnantStage = pregnantStage;
    }

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "intake_id")
    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    @Column(name = "intake_name")
    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    @Column(name = "intake_desc")
    public String getIntakeDesc() {
        return intakeDesc;
    }

    public void setIntakeDesc(String intakeDesc) {
        this.intakeDesc = intakeDesc;
    }

    @Column(name = "food_structure_id")
    public String getFoodStructureId() {
        return foodStructureId;
    }

    public void setFoodStructureId(String foodStructureId) {
        this.foodStructureId = foodStructureId;
    }

    @Column(name = "intake_mode")
    public String getIntakeMode() {
        return intakeMode;
    }

    public void setIntakeMode(String intakeMode) {
        this.intakeMode = intakeMode;
    }

    @Column(name = "intake_product_desc")
    public String getIntakeProductDesc() {
        return intakeProductDesc;
    }

    public void setIntakeProductDesc(String intakeProductDesc) {
        this.intakeProductDesc = intakeProductDesc;
    }

    @Column(name = "intake_prompt")
    public String getIntakePrompt() {
        return intakePrompt;
    }

    public void setIntakePrompt(String intakePrompt) {
        this.intakePrompt = intakePrompt;
    }

    @Column(name = "intake_caloric_max")
    public String getIntakeCaloricMax() {
        return intakeCaloricMax;
    }

    public void setIntakeCaloricMax(String intakeCaloricMax) {
        this.intakeCaloricMax = intakeCaloricMax;
    }

    @Column(name = "intake_caloric_min")
    public String getIntakeCaloricMin() {
        return intakeCaloricMin;
    }

    public void setIntakeCaloricMin(String intakeCaloricMin) {
        this.intakeCaloricMin = intakeCaloricMin;
    }

    @Column(name = "intake_actual_energy")
    public String getIntakeActualEnergy() {
        return intakeActualEnergy;
    }

    public void setIntakeActualEnergy(String intakeActualEnergy) {
        this.intakeActualEnergy = intakeActualEnergy;
    }

    @Column(name = "intake_cbr")
    public String getIntakeCbr() {
        return intakeCbr;
    }

    public void setIntakeCbr(String intakeCbr) {
        this.intakeCbr = intakeCbr;
    }

    @Column(name = "intake_cbr_percent")
    public String getIntakeCbrPercent() {
        return intakeCbrPercent;
    }

    public void setIntakeCbrPercent(String intakeCbrPercent) {
        this.intakeCbrPercent = intakeCbrPercent;
    }

    @Column(name = "intake_protein")
    public String getIntakeProtein() {
        return intakeProtein;
    }

    public void setIntakeProtein(String intakeProtein) {
        this.intakeProtein = intakeProtein;
    }

    @Column(name = "intake_protein_percent")
    public String getIntakeProteinPercent() {
        return intakeProteinPercent;
    }

    public void setIntakeProteinPercent(String intakeProteinPercent) {
        this.intakeProteinPercent = intakeProteinPercent;
    }

    @Column(name = "intake_fat")
    public String getIntakeFat() {
        return intakeFat;
    }

    public void setIntakeFat(String intakeFat) {
        this.intakeFat = intakeFat;
    }

    @Column(name = "intake_fat_percent")
    public String getIntakeFatPercent() {
        return intakeFatPercent;
    }

    public void setIntakeFatPercent(String intakeFatPercent) {
        this.intakeFatPercent = intakeFatPercent;
    }

    @Column(name = "diet_id")
    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    @Column(name = "diet_name")
    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

}
