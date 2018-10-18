
package com.mnt.preg.master.entity.basic;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 食谱原材料清单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-2-3 zcq 初版
 */
@Entity
@Table(name = "mas_food_material_list")
public class FoodMaterialList extends MappedEntity {

    private static final long serialVersionUID = 1523365405347563268L;

    // 主键
    private String fmlId;

    // 菜谱ID
    @UpdateAnnotation
    private String foodId;

    // 食材ID
    @UpdateAnnotation
    private String fmId;

    // 食材类型
    @UpdateAnnotation
    private String fmlType;

    // 在整体食谱中占有百分比
    @UpdateAnnotation
    private BigDecimal fmlPercent;

    // 食材重量
    @UpdateAnnotation
    private Integer fmlAmount;

    // 主/辅 料
    @UpdateAnnotation
    private String fmlMaterial;

    // 是否算入膳食结构
    @UpdateAnnotation
    private String fmlIsIntakeType;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "fml_id")
    public String getFmlId() {
        return fmlId;
    }

    public void setFmlId(String fmlId) {
        this.fmlId = fmlId;
    }

    @Column(name = "food_id")
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Column(name = "fm_id")
    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    @Column(name = "fml_type")
    public String getFmlType() {
        return fmlType;
    }

    public void setFmlType(String fmlType) {
        this.fmlType = fmlType;
    }

    @Column(name = "fml_amount")
    public Integer getFmlAmount() {
        return fmlAmount;
    }

    public void setFmlAmount(Integer fmlAmount) {
        this.fmlAmount = fmlAmount;
    }

    @Column(name = "fml_percent")
    public BigDecimal getFmlPercent() {
        return fmlPercent;
    }

    public void setFmlPercent(BigDecimal fmlPercent) {
        this.fmlPercent = fmlPercent;
    }

    @Column(name = "fml_is_intake_type")
    public String getFmlIsIntakeType() {
        return fmlIsIntakeType;
    }

    public void setFmlIsIntakeType(String fmlIsIntakeType) {
        this.fmlIsIntakeType = fmlIsIntakeType;
    }

    @Column(name = "fml_material")
    public String getFmlMaterial() {
        return fmlMaterial;
    }

    public void setFmlMaterial(String fmlMaterial) {
        this.fmlMaterial = fmlMaterial;
    }

}
