/*
 * FoodMaterialMapping.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.mapping.basic;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 食材Mapping
 * 
 * @author zj
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-8-1 scd 初版
 */
@FrontCache(space = "foodMaterial")
public class FoodMaterialMapping {

    /** 跳转到食材一览页面 */
    public final static String FOOD_MATERIAL_QUERY = "/page/food/food_material_query.action";

    /** 异步加载食材信息 */
    public final static String FOOD_MATERIAL_AJAX_QUERY = "/page/food/food_material_ajax_query.action";

    /** 添加食材 */
    public final static String FOOD_MATERIAL_ADD = "/page/food/food_material_add.action";

    /** 编辑食材 */
    public final static String FOOD_MATERIAL_UPDATE = "/page/food/food_material_update.action";

    /** 删除食材 */
    public final static String FOOD_MATERIAL_REMOVE = "/page/food/food_material_remove.action";

    /** 添加类别 */
    public static final String CATALOGINFO_ADD = "/page/master/add_catalog_info.action";

    /** 验证类别名称是否重复 */
    public static final String CATALOGINFO_NAME_VALID = "/page/master/catalog_info_name_valid.action";

    /** 验证食材类别中是否有食材配置 */
    public static final String CATALOG_CHECK_HAVE_FOOD = "/page/master/catalog_check_have_food.action";
    
    /** 验证结点下是否有子结点**/
    public static final String CATALOG_CHECK_HAVE_SUB = "/page/master/catalog_check_have_sub.action";

    /** 类别修改 */
    public static final String CATALOGINFO_UPDATE = "/page/master/catalog_info_update.action";

    /** 类别删除 */
    public static final String CATALOGINFO_REMOVE = "/page/master/catalog_info_remove.action";

    /** 修改类别排序 */
    public static final String CATALOGINFO_UPDATE_ORDER = "/page/master/catalog_info_update_order.action";

    /** 分级列表 */
    public final static String FOOD_TYPE_QUERY = "/import_type_query.action";
   
    /** 获取食材元素 */
    public static final String GET_FOOD_ELEMENT = "/page/master/get_food_ele.action";
   
    /** 获取食材基本信息 */
    public static final String GET_FM = "/page/master/get_fm.action";
}
