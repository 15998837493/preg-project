
package com.mnt.preg.master.condition.preg;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 食物检索条件
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
public class DietTemplateDetailCondition implements Serializable {

    private static final long serialVersionUID = 1876656860359626335L;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String dietId;

    /** 天数 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String foodDay;

    /** 餐次 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    private String foodMeal;

    /** 食物编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodId;

    /** 菜名 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String foodName;

    /** 天数 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "foodDay")
    private List<String> foodDayList;

    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getFoodDay() {
        return foodDay;
    }

    public void setFoodDay(String foodDay) {
        this.foodDay = foodDay;
    }

    public String getFoodMeal() {
        return foodMeal;
    }

    public void setFoodMeal(String foodMeal) {
        this.foodMeal = foodMeal;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public List<String> getFoodDayList() {
        return foodDayList;
    }

    public void setFoodDayList(List<String> foodDayList) {
        this.foodDayList = foodDayList;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
