
package com.mnt.preg.master.form.basic;

/**
 * 菜谱数据表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-2-5 zcq 初版
 */
public class FoodMaterialListForm {

    /** 菜谱ID */
    private String foodId;

    /** 食材ID */
    private String fmId;

    /** 食材类型 */
    private String fmlType;

    /** 食材重量 */
    private String fmlAmount;
    
    /** 主料/辅料 */
    private String fmlMaterial;

    /** 是否算入膳食结构 */
    private String fmlIsIntakeType;

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

    public String getFmlAmount() {
        return fmlAmount;
    }

    public void setFmlAmount(String fmlAmount) {
        this.fmlAmount = fmlAmount;
    }

    public String getFmlIsIntakeType() {
        return fmlIsIntakeType;
    }

    public void setFmlIsIntakeType(String fmlIsIntakeType) {
        this.fmlIsIntakeType = fmlIsIntakeType;
    }

    
    public String getFmlMaterial() {
        return fmlMaterial;
    }

    
    public void setFmlMaterial(String fmlMaterial) {
        this.fmlMaterial = fmlMaterial;
    }

}
