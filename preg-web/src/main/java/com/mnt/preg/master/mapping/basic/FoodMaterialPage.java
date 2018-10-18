/*
 * FoodMaterialPage.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.mapping.basic;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 食材Page
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 scd 初版
 */
@FrontCache(space = "foodMaterPage", type = "jsp")
public class FoodMaterialPage {

    /** 食材页面 */
    public static final String FOOD_MASTERIAL_VIEW = "/master/basic/food_material_view";
}
