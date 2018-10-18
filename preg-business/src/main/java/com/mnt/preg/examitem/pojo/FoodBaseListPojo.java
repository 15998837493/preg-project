
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 食物列表
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-21 zy 初版
 */
public class FoodBaseListPojo implements Serializable {

    private static final long serialVersionUID = 6408071713505243161L;

    private List<FoodBasePojo> foodBaseList;

    public List<FoodBasePojo> getFoodBaseList() {
        return foodBaseList;
    }

    public void setFoodBaseList(List<FoodBasePojo> foodBaseList) {
        this.foodBaseList = new ArrayList<FoodBasePojo>();
        this.foodBaseList.addAll(foodBaseList);
    }

}
