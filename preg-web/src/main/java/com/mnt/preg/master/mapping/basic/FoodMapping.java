
package com.mnt.preg.master.mapping.basic;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 食谱库管理模块 操作mapping映射(根据模块分)
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历： v1.3 2018-1-30 dhs 初版
 */
@FrontCache(space = "Food")
public class FoodMapping {

    public final static String FOOD_CUISINE_QUERY = "/page/food/food_cuisine_query.action";

    public final static String FOOD_CUISINE_AJAX_QUERY = "/page/food/food_cuisine_ajax_query.action";

    public final static String FOOD_CUISINE_ADD_INIT = "/page/food/food_cuisine_add_init.action";

    public final static String FOOD_CUISINE_ADD = "/page/food/food_cuisine_add.action";

    public final static String FOOD_QUERY_MATERIAL = "/page/food/food_query_material.action";

    public static final String FOOD_CATALOG_QUERY_ALL = "/page/master/plan/food_catalog_query_all.action";

    public final static String FOOD_CUISINE_UPDATE_INIT = "/page/food/food_cuisine_update_init.action";

    public final static String FOOD_CUISINE_UPDATE = "/page/food/food_cuisine_update.action";

    public final static String FOOD_CUISINE_REMOVE = "/page/food/food_cuisine_remove.action";

    public final static String IMPORT_FOOD_EXT = "/import_food_ext.action";

    public final static String FOOD_CATALOGINFO_AMOUNT_VALID = "/page/food/food_cataloginfo_amount_valid.action";

    public final static String FOOD_CATALOGINFO_UPDATE = "/page/food/food_cataloginfo_update.action";

    public final static String FOOD_CATALOGINFO_DELETE = "/page/food/food_cataloginfo_delete.action";

    public final static String FOOD_CATALOGINFO_UPDATE_ORDER = "/page/food/food_cataloginfo_update_order.action";

    public final static String FOOD_CATALOGINFO_ADD = "/page/food/food_cataloginfo_add.action";

    public final static String FOOD_CATALOGINFO_NAME_VALID = "/page/food/food_cataloginfo_name_valid.action";
}
