
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.master.pojo.preg.IntakeTypePojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplatePojo;
import com.mnt.preg.master.pojo.preg.PregIntakePojo;

/**
 * 干预方案初始化页面信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-3 zcq 初版
 */
public class PregInitPlanGroupPojo implements Serializable {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 膳食模版 */
    private List<PregIntakePojo> intakeList;

    /** 历史方案列表 */
    private List<PregIntervenePlanPojo> historyPlanList;

    /** 产品列表 */
    private List<ProductPojo> productList;

    /** 食谱列表 */
    private List<PregDietTemplatePojo> dietList;

    /** 膳食类型列表 */
    private List<IntakeTypePojo> intakeTypeList;

    /** 食物推荐 */
    private String foodRecommend;

    public List<PregIntakePojo> getIntakeList() {
        return intakeList;
    }

    public void setIntakeList(List<PregIntakePojo> intakeList) {
        this.intakeList = intakeList;
    }

    public List<PregIntervenePlanPojo> getHistoryPlanList() {
        return historyPlanList;
    }

    public void setHistoryPlanList(List<PregIntervenePlanPojo> historyPlanList) {
        this.historyPlanList = historyPlanList;
    }

    public List<ProductPojo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductPojo> productList) {
        this.productList = productList;
    }

    public List<PregDietTemplatePojo> getDietList() {
        return dietList;
    }

    public void setDietList(List<PregDietTemplatePojo> dietList) {
        this.dietList = dietList;
    }

    public List<IntakeTypePojo> getIntakeTypeList() {
        return intakeTypeList;
    }

    public void setIntakeTypeList(List<IntakeTypePojo> intakeTypeList) {
        this.intakeTypeList = intakeTypeList;
    }

    public String getFoodRecommend() {
        return foodRecommend;
    }

    public void setFoodRecommend(String foodRecommend) {
        this.foodRecommend = foodRecommend;
    }

}
