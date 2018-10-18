
package com.mnt.preg.master.entity.preg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 代量食谱模版表
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 zcq 初版
 */
@Entity
@Table(name = "mas_diet_template")
public class PregDietTemplate extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 模版编码 */
    private String dietId;

    /** 模版名称 */
    @UpdateAnnotation
    private String dietName;

    /** 模版类型 */
    @UpdateAnnotation
    private String dietType;

    /** 膳食结构 */
    @UpdateAnnotation
    private String foodStructureId;

    /** 模版介绍 */
    @UpdateAnnotation
    private String dietDesc;

    /** 孕期阶段 */
    @UpdateAnnotation
    private String pregnantStage;

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

    @Column(name = "diet_type")
    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    @Column(name = "food_structure_id")
    public String getFoodStructureId() {
        return foodStructureId;
    }

    public void setFoodStructureId(String foodStructureId) {
        this.foodStructureId = foodStructureId;
    }

    @Column(name = "diet_desc")
    public String getDietDesc() {
        return dietDesc;
    }

    public void setDietDesc(String dietDesc) {
        this.dietDesc = dietDesc;
    }

}
