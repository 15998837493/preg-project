
package com.mnt.preg.master.mapping.basic;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 数据管理模块 操作mapping映射(根据模块分)
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-15 zy 初版
 */
@FrontCache(space = "Master")
public class MasterMapping {

    /********************************* 食物管理 ************************************/
    public final static String QUERY_FOOD = "/page/master/food_query.action";

    public final static String FOOD_MATERIAL_TYPE_QUERY = "/page/food/food_material_type_query.action";

    public final static String FOOD_MATERIAL_QUERY = "/page/food/food_material_query.action";

    public final static String FOOD_MATERIAL_AJAX_QUERY = "/page/food/food_material_ajax_query.action";

    public final static String FOOD_MATERIAL_ADD_INIT = "/page/food/food_material_add_init.action";

    public final static String FOOD_MATERIAL_ADD = "/page/food/food_material_add.action";

    public final static String FOOD_MATERIAL_UPDATE_INIT = "/page/food/food_material_update_init.action";

    public final static String FOOD_MATERIAL_UPDATE = "/page/food/food_material_update.action";

    public final static String FOOD_MATERIAL_REMOVE = "/page/food/food_material_remove.action";

    public final static String FOOD_MATERIAL_ELEMENT_GET = "/page/food/food_material_element_get.action";

    public final static String FOOD_CUISINE_QUERY = "/page/food/food_cuisine_query.action";

    public final static String FOOD_CUISINE_AJAX_QUERY = "/page/food/food_cuisine_ajax_query.action";

    public final static String FOOD_CUISINE_ADD_INIT = "/page/food/food_cuisine_add_init.action";

    public final static String FOOD_CUISINE_ADD = "/page/food/food_cuisine_add.action";

    public final static String FOOD_CUISINE_UPDATE_INIT = "/page/food/food_cuisine_update_init.action";

    public final static String FOOD_CUISINE_UPDATE = "/page/food/food_cuisine_update.action";

    public final static String FOOD_CUISINE_REMOVE = "/page/food/food_cuisine_remove.action";

    public final static String IMPORT_FOOD_EXT = "/import_food_ext.action";

    public final static String FOOD_TYPE_QUERY = "/import_type_query.action";

    /********************************* 食物管理 ************************************/

    /********************************* 干预需求 ************************************/
    public final static String PLAN_DIETTEMPLATE_QUERY = "/page/master/diettemplate_query.action";

    public final static String PLAN_DIETTEMPLATE_GET = "/page/master/diettemplate_get.action";

    public final static String PLAN_DIETTEMPLATE_ADD_INIT = "/page/master/diettemplate_add_init.action";

    public final static String PLAN_DIETTEMPLATE_ADD = "/page/master/diettemplate_add.action";

    public final static String PLAN_DIETTEMPLATE_UPDATE = "/page/master/diettemplate_update.action";

    public final static String PLAN_DIETTEMPLATE_REMOVE = "/page/master/diettemplate_remove.action";

    public final static String PLAN_DIETTEMPLATEDETAIL_QUERY = "/page/master/diettemplatedetail_query.action";

    public final static String PLAN_DIETTEMPLATEDETAIL_INIT = "/page/master/diettemplatedetail_init.action";

    public final static String PLAN_DIETTEMPLATEDETAIL_GET = "/page/master/diettemplatedetail_get.action";

    public final static String PLAN_DIETTEMPLATEDETAIL_ADD = "/page/master/diettemplatedetail_add.action";

    public final static String PLAN_DIETTEMPLATEDETAIL_UPDATE = "/page/master/diettemplatedetail_foodDay_update.action";

    public final static String PLAN_DIETTEMPLATEDETAIL_REMOVE = "/page/master/diettemplatedetail_remove.action";

    public final static String PLAN_INTAKE_INIT = "/page/master/intake_init.action";

    public final static String PLAN_INTAKE_QUERY = "/page/master/intake_query.action";

    public final static String PLAN_INTAKE_EDIT_INIT = "/page/master/intake_edit_init.action";

    public final static String PLAN_INTAKE_ADD = "/page/master/intake_add.action";

    public final static String INTAKE_SUBSIDIARY_DETAILS_QUERY = "/page/master/intake_subsidiary_details_query.action";

    public final static String INTAKE_SUBSIDIARY_DETAILS_LIST = "/page/master/intake_subsidiary_details_list.action";

    public final static String INTAKE_SUBSIDIARY_LIST_SELECT = "/page/master/intake_subsidiary_list_select.action";

    public final static String PLAN_INTAKE_UPDATE = "/page/master/intake_update.action";

    public final static String PLAN_INTAKE_REMOVE = "/page/master/intake_remove.action";

    public final static String PLAN_INTAKE_DETAIL_INIT = "/page/master/intake_detail_init.action";

    public final static String PLAN_INTAKE_VALID = "/page/master/intake_valid.action";

    public final static String PLAN_INTAKE_DETAIL_QUERY = "/page/master/intake_detail_query.action";

    public final static String PLAN_INTAKE_DETAIL_ADD = "/page/master/intake_detail_add.action";

    public final static String PLAN_INTAKE_DETAIL_UPDATE = "/page/master/intake_detail_update.action";

    public final static String PLAN_INTAKE_DETAIL_REMOVE = "/page/master/intake_detail_remove.action";

    public final static String PLAN_INTAKE_TYPE_QUERY = "/page/master/intake_type_query.action";

    public final static String PLAN_INTAKE_TYPE_INIT = "/page/master/intake_type_init.action";

    public final static String PLAN_INTAKE_TYPE_ADD = "/page/master/intake_type_add.action";

    public final static String PLAN_INTAKE_TYPE_UPDATE = "/page/master/intake_type_update.action";

    public final static String PLAN_INTAKE_TYPE_REMOVE = "/page/master/intake_type_remove.action";

    /** 编码验证(编辑) */
    public static final String PLAN_INTAKE_TYPE_VALIDATE = "/page/master/plan/plan_intake_type_validate.action";

    /** 编码验证编辑 (添加) */
    public static final String PLAN_INTAKE_TYPE_VALIDATE_ADD = "/page/master/plan/plan_intake_type_validate_add.action";

    /********************************* 干预需求 ************************************/

    /********************************* 疾病管理管理 ************************************/

    /** 疾病指标 */
    public static final String DISEASE_QUOTA_VIEW = "/page/disease/disease_quota_view.action";

    /********************************* 疾病管理管理 ************************************/
    /********************************* 检查项目维护 ************************************/
    /** 增加检查项目初始化页面 */
    public static final String ADD_INIT_INSPECTITEM = "/page/master/add_init_inspectItem.action";

    /** 检查项目主键验证 */
    public static final String INSPECTITEM_VALIDATE = "/page/master/inspectitem_validate.action";

    /** 增加检查项目 */
    public static final String ADD_INSPECTITEM = "/page/master/inspectItem/inspectItem_add.action";

    /** 修改检查项目初始化页面 */
    public static final String UPDATE_INIT_INSPECTITEM = "/page/master/update_init_inspectItem.action";

    /** 修改检查项目 */
    public static final String INSPECTITEM_UPDATE = "/page/master/inspectItem_update.action";

    /** 删除检查项目 */
    public static final String REMOVE_INSPECTITEM = "/page/master/remove_inspectItem.action";

    /** 查询检查项目 */
    public static final String INSPECTITEM_QUERY = "/page/master/inspectItem_query.action";

    /** 配置检查项目初始化页面 */

    /********************************* 检查项目维护 ************************************/
    /********************************* 商品维护 ************************************/
    /** 增加商品初始化页面 */
    public static final String ADD_INIT_PRODUCT = "/page/master/add_init_product.action";

    /** 增加商品 */
    public static final String PRODUCT_ADD = "/page/master/product_add.action";

    /** 修改商品初始化页面 */
    public static final String UPDATE_INIT_PRODUCT = "/page/master/update_init_product.action";

    /** 修改商品 */
    public static final String PRODUCT_UPDATE = "/page/master/product_update.action";

    /** 删除商品 */
    public static final String REMOVE_PRODUCT = "/page/master/remove_product.action";

    /** 查询商品 */
    public static final String PRODUCT_QUERY = "/page/master/product_query.action";

    /** 查询全部商品 */
    public static final String PRODUCT_QUERY_ALL = "/page/master/product_query_all.action";

    /** 查询商品 */
    public static final String PRODUCT_GET = "/page/master/product_get.action";

    /** 商品一览页面 */
    public static final String PRODUCT_VIEW = "/page/master/product_view.action";

    /** 商品编码是否重复检验 */
    public static final String PRODUCT_PRODUCTCODE_VALID = "/page/master/product_productcode_valid.action";

    /** 商品名称验证 */
    public static final String PRODUCT_PRODUCTNAME_VALID = "/page/master/product_productname_valid.action";

    /** 商品元素页面初始化 */
    public static final String PRODUCT_ELEMENT_INIT = "/page/master/product_element_init.action";

    /** 商品元素页面增加 */
    public static final String PRODUCT_ELEMENT_ADD = "/page/master/product_element_add.action";

    /** 查询商品元素 */
    public static final String QUERY_DETAIL_PRODUCT_ELEMENT = "/page/master/query_detail_product_element.action";

    public static final String PRODUCT_ELEMENT_QUERY = "/page/master/product_element_query.action";

    /********************************* 商品维护 ************************************/

    /********************************* 副食模版 ************************************/
    /** 副食模板信息检索 */
    public static final String INTAKE_SUBSIDIARY_QUERY = "/page/subsidiary/intake_subsidiary_query.action";

    /** 删除副食模板信息 */
    public static final String INTAKE_SUBSIDIARY_DELETE = "/page/subsidiary/intake_subsidiary_delete.action";

    /** 新增副食模板信息 */
    public static final String INTAKE_SUBSIDIARY_SAVE = "/page/subsidiary/intake_subsidiary_save.action";

    /** 更新副食模板信息 */
    public static final String INTAKE_SUBSIDIARY_UPDATE = "/page/subsidiary/intake_subsidiary_update.action";

    /** 模板名称重复验证 */
    public static final String NAME_REPEAT_VALIDATE = "/page/subsidiary/name_repeat_validate.action";

    /** 初始化副食模板详情页 */
    public static final String INIT_INTAKE_SUBSIDIARY_DETAIL_VIEW = "/page/plan/init_intake_subsidiary_detail_view.action";

    /** 初始化副食模板 */
    public static final String INIT_INTAKE_SUBSIDIARY_VIEW = "/page/plan/init_intake_subsidiary_view.action";

    /** 查询副食模板明细信息 */
    public static final String INTAKE_SUBSIDIARY_DETAIL_QUERY = "/page/subsidiary/intake_subsidiary_detail_query.action";

    /** 新增副食模板明细信息 */
    public static final String INTAKE_SUBSIDIARY_DETAIL_SAVE = "/page/subsidiary/intake_subsidiary_detail_save.action";

    /** 更新副食模板明细信息 */
    public static final String INTAKE_SUBSIDIARY_DETAIL_UPDATE = "/page/subsidiary/intake_subsidiary_detail_update.action";

    /** 删除副食模板信息 */
    public static final String INTAKE_SUBSIDIARY_DETAIL_DELETE = "/page/subsidiary/intake_subsidiary_detail_delete.action";

    /********************************* 副食模版 ************************************/
    /********************************* 健康要点 ************************************/
    /** 增加健康要点初始化页面 */
    public static final String QUERY_INIT_INTERVENEPOINTS = "/page/master/food/diagnosis_course_view.action";

    /** 增加健康要点 */
    public static final String INTERVENEPOINTS_ADD = "/page/master/intervene_points_add.action";

    /** 修改健康要点 */
    public static final String INTERVENEPOINTS_UPDATE = "/page/master/intervene_points_update.action";

    /** 删除健康要点 */
    public static final String REMOVE_INTERVENEPOINTS = "/page/master/remove_intervene_points.action";

    /** 查询健康要点 */
    public static final String INTERVENEPOINTS_QUERY = "/page/master/intervene_points_query.action";

    /********************************* 健康要点 ************************************/
    /********************************* 课程 ************************************/
    /** 课程信息检索 */
    public static final String COURSE_QUERY = "/page/plan/course_query.action";

    /** 删除课程信息 */
    public static final String COURSE_DELETE = "/page/plan/course_delete.action";

    /** 新增课程信息 */
    public static final String COURSE_SAVE = "/page/plan/course_save.action";

    /** 更新课程信息 */
    public static final String COURSE_UPDATE = "/page/plan/course_update.action";

    /** 课程名称重复验证 */
    public static final String COURSE_NAME_VALIDATE = "/page/plan/course_name_validate.action";

    /********************************* 课程 ************************************/
    /********************************* 孕期课程 ************************************/
    /** 异步检索孕期课程库 */
    public static final String QUERY_PREGNANCYCOURSE = "/page/master/plan/query_pregnancy_course.action";

    /** 删除孕期课程库 */
    public static final String PREGNANCYCOURSE_REMOVE = "/page/master/plan/pregnancy_course_remove.action";

    /** 增加孕期课程库 初始化页面 */
    public static final String ADD_INIT_PREGNANCYCOURSE = "/page/master/plan/add_init_pregnancy_course.action";

    /** 增加孕期课程库 页面 */
    public static final String ADD_PREGNANCYCOURSE = "/page/master/plan/add_pregnancy_course.action";

    /** 修改孕期课程库 初始化页面 */
    public static final String UPDATE_INIT_PREGNANCYCOURSE = "/page/master/plan/update_init_pregnancy_course.action";

    /** 修改孕期课程库 页面 */
    public static final String UPDATE_PREGNANCYCOURSE = "/page/master/plan/update_pregnancy_course.action";

    /** 异步检索孕期课程明细 */
    public static final String QUERY_DETAIL_COURSE = "/page/master/plan/query_course_detail.action";

    /** 增加明细 */
    public static final String MOVE_PREGNANCYCOURSE_DETAIL = "/page/master/plan/move_pregnancycourse_detail.action";

    /** 初始化课程详情页 */
    public static final String PREGNANCYCOURSE_DETAIL_VIEW = "/page/master/plan/pregnancycourse_detail_view.action";

    /** 增加孕期课程明细 */
    public static final String PREGNANCYCOURSE_DETAIL_ADD = "/page/master/plan/pregnancycourse_detail_add.action";

    /** 更新孕期课程明细 */
    public static final String PREGNANCYCOURSE_DETAIL_UPDATE = "/page/master/plan/pregnancycourse_detail_update.action";

    /** 删除孕期课程明细 */
    public static final String PREGNANCYCOURSE_DETAIL_REMOVE = "/page/master/plan/pregnancycourse_detail_remove.action";

    /** 查询孕期课程明细 */
    public static final String PREGNANCYCOURSE_DETAIL_QUERY = "/page/master/plan/pregnancycourse_detail_query.action";

    /** 验证课程明细中代码是否重复 */
    public static final String PREGDECODE_VALIDATE = "/page/master/plan/pregde_code_validate.action";

    /** 验证课程主键是否重复 */
    public static final String PREGNANCYCOURSE_VALIDATE = "/page/master/plan/pregnancycourse_validate.action";

    /********************************* 孕期课程 ************************************/
    /********************************** 元素模块 **********************************/
    /**
     * 元素界面初始化界面路径和孕期项目一致
     */
    /** 元素初始化页面 */
    public static final String INIT_NUTRIENT = "/page/master/plan/init_nutrient.action";

    /** 增加元素 */
    public static final String ADD_NUTRIENT = "/page/master/plan/add_nutrient.action";

    /** 修改元素 */
    public static final String UPDATE_NUTRIENT = "/page/master/plan/update_nutrient.action";

    /** 删除元素 */
    public static final String REMOVE_NUTRIENT = "/page/master/plan/remove_nutrient.action";

    /** 元素信息查询 */
    public static final String QUERY_NUTRIENT = "/page/master/plan/code_nutrient.action";

    /** 元素主键验证 */
    public static final String NUTRIENT_VALIDATE = "/page/master/plan/nutrient_validate.action";

    /********************************** 元素模块 **********************************/
    /********************************** 饮食原则 **********************************/
    /** 饮食原则检索 */
    public static final String VIEW_DIET_TENET = "/page/master/food/diet_tenet_view.action";

    /** 饮食原则检索 */
    public static final String QUERY_DIET_TENET = "/page/master/food/diet_tenet.action";

    /** 饮食原则添加 */
    public static final String ADD_DIET_TENET = "/page/master/food/add_diet_tenet.action";

    /** 饮食原则修改 */
    public static final String UPDATE_DIET_TENET = "/page/master/food/update_diet_tenet.action";

    /** 饮食原则删除 */
    public static final String REMOVE_DIET_TENET = "/page/master/food/remove_diet_tenet.action";

    /*************************************** 代量食谱模板 ******************************************************/
    /** 查询模板天数 */
    public static final String QUERY_FOODDAY_BY_DIETID = "/page/master/diettemplate_query_foodday.action";

    /** 查询模板明细 */
    public static final String QUERY_FOODDAY_BY_DIETIDANDFOODDAY = "/page/master/diettemplate_query_detail.action";

    /** 移除模板天数 */
    public static final String REMOVE_FOODDAY_BY_DIETIDANDFOODDAY = "/page/master/remove_foodDay_detail.action";

    /** 验证食谱的是否重复 */
    public static final String CHECK_DIET_NAME_VALID = "/page/master/check_diet_name_valid.action";

    /********************************* 当前病历（指标） ************************************/

    public final static String PLAN_GET_ORDER_MANAGER = "/page/plan/PLAN_GET_ORDER_MANAGER.action";

    /** 查询商品类型信息请求 */
    public static final String PRODUCT_CATALOG_QUERY = "/page/master/plan/product_catalog_query.action";

    public static final String PRODUCT_AND_CATALOG_QUERY = "/page/master/plan/product_and_catalog_query.action";

    /** 查询所有的商品类别 */
    public static final String PRODUCT_CATALOG_QUERY_ALL = "/page/master/plan/product_catalog_query_all.action";

    /** 检验商品类别名称是否可用请求 */
    public static final String CATALOG_NAME_VALID = "/page/master/plan/catalog_name_valid.action";

    /** 更改商品类型顺序请求 */
    public static final String CATALOG_ORDER_UPDATE = "/page/master/plan/catalog_order_update.action";

    /** 获取商品类型信息请求 */
    public static final String CATALOG_INFO_GET = "/page/master/plan/catalog_info_get.action";

    /** 商品类别增加请求 */
    public static final String PRODUCT_CATALOG_ADD = "/page/master/plan/product_catalog_add.action";

    /** 商品类别更改请求 */
    public static final String PRODUCT_CATALOG_UPDATE = "/page/master/plan/product_catalog_update.action";

    /** 删除商品类型信息请求 */
    public static final String PRODUCT_CATALOG_REMOVE = "/page/master/plan/product_catalog_remove.action";

    public final static String PLAN_ADD_ORDERSHOP = "/page/plan/plan_add_ordershop.action";

    public final static String PLAN_REMOVE_ORDERSHOP = "/page/plan/plan_remove_ordershop.action";

    public final static String PLAN_UPDATE_ORDERSHOP = "/page/plan/plan_update_ordershop.action";

    public final static String PLAN_ADD_ORDER = "/page/plan/plan_add_order.action";

    public final static String PLAN_DIAGNOSIS_INSPECTITEM_QUERY = "/page/plan/plan_diagnosis_inspectitem_query.action";

    /********************************* 功能症状 ************************************/
    /** 检索功能症状信息 */
    public static final String QUERY_SYMPTOMS_d = "/page/master/health/symptoms_query_d.action";

    /** 查询主键是否重复 */
    public static final String SYMPTOMS_VALIDATE = "/page/master/health/symptoms_queryId.action";

    /** 添加功能症状信息 */
    public static final String ADD_SYMPTOMS = "/page/master/health/symptoms_add.action";

    /** 删除功能症状信息 */
    public static final String REMOVE_Symptoms = "/page/master/health/symptoms_delete.action";

    /** 修改功能症状信息 */
    public static final String UPDATE_Symptoms = "/page/master/health/symptoms_update.action";

    /** 初始化功能症状调查信息 */
    public static final String INIT_SYMP = "/page/master/health/init_symp.action";

}
