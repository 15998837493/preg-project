/*
 * FoodMaterialListInfoVo.java	 1.0   2015-2-3
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.basic;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 食谱的食材组成
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-2-3 zcq 初版
 */
public class FoodMaterialListInfoPojo implements Serializable {

    private static final long serialVersionUID = 5569448661200187843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String fmlId;

    /** 菜谱ID */
    @QueryFieldAnnotation
    private String foodId;

    /** 食材ID */
    @QueryFieldAnnotation
    private String fmId;

    /** 食材类型 */
    @QueryFieldAnnotation
    private String fmlType;

    /** 在整体食谱中占有百分比 */
    @QueryFieldAnnotation
    private BigDecimal fmlPercent;

    /** 食材重量 */
    @QueryFieldAnnotation
    private Integer fmlAmount;

    /** 主料/辅料 */
    @QueryFieldAnnotation
    private String fmlMaterial;

    /** 是否算入膳食结构 */
    @QueryFieldAnnotation
    private String fmlIsIntakeType;

    /** 食材名称 */
    private String fmName;

    public String getFmlId() {
        return fmlId;
    }

    public void setFmlId(String fmlId) {
        this.fmlId = fmlId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    public String getFmlType() {
        return fmlType;
    }

    public void setFmlType(String fmlType) {
        this.fmlType = fmlType;
    }

    public BigDecimal getFmlPercent() {
        return fmlPercent;
    }

    public void setFmlPercent(BigDecimal fmlPercent) {
        this.fmlPercent = fmlPercent;
    }

    public Integer getFmlAmount() {
        return fmlAmount;
    }

    public void setFmlAmount(Integer fmlAmount) {
        this.fmlAmount = fmlAmount;
    }

    public String getFmlIsIntakeType() {
        return fmlIsIntakeType;
    }

    public void setFmlIsIntakeType(String fmlIsIntakeType) {
        this.fmlIsIntakeType = fmlIsIntakeType;
    }

    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    public String getFmlMaterial() {
        return fmlMaterial;
    }

    public void setFmlMaterial(String fmlMaterial) {
        this.fmlMaterial = fmlMaterial;
    }

}
