
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
 * 食谱模版明细表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 zcq 初版
 */
@Entity
@Table(name = "mas_diet_template_detail")
public class PregDietTemplateDetail extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    private String id;

    /** 所属食谱模板 */
    private String dietId;

    /** 食物天数 */
    @UpdateAnnotation
    private String foodDay;

    /** 食物餐次 */
    @UpdateAnnotation
    private String foodMeal;

    /** 食物编码 */
    @UpdateAnnotation
    private String foodId;

    /** 食物名称 */
    @UpdateAnnotation
    private String foodName;

    /** 食材 id */
    @UpdateAnnotation
    private String fmId;

    /** 食物成分名称 */
    @UpdateAnnotation
    private String foodMaterialName;

    /** 食物成分用量 */
    @UpdateAnnotation
    private String foodMaterialAmount;

    /** 能量范围 */
    @UpdateAnnotation
    private String amountLevel;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "diet_id")
    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    @Column(name = "food_day")
    public String getFoodDay() {
        return foodDay;
    }

    public void setFoodDay(String foodDay) {
        this.foodDay = foodDay;
    }

    @Column(name = "food_meal")
    public String getFoodMeal() {
        return foodMeal;
    }

    public void setFoodMeal(String foodMeal) {
        this.foodMeal = foodMeal;
    }

    @Column(name = "food_id")
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Column(name = "food_name")
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    @Column(name = "fm_id")
    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    @Column(name = "food_material_name")
    public String getFoodMaterialName() {
        return foodMaterialName;
    }

    public void setFoodMaterialName(String foodMaterialName) {
        this.foodMaterialName = foodMaterialName;
    }

    @Column(name = "food_material_amount")
    public String getFoodMaterialAmount() {
        return foodMaterialAmount;
    }

    public void setFoodMaterialAmount(String foodMaterialAmount) {
        this.foodMaterialAmount = foodMaterialAmount;
    }

    @Column(name = "amount_level")
    public String getAmountLevel() {
        return amountLevel;
    }

    public void setAmountLevel(String amountLevel) {
        this.amountLevel = amountLevel;
    }
}
