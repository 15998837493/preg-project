
package com.mnt.preg.master.mapping.basic;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 基础参数维护页面名称
 * 
 * @author zhj
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-16 zhj 初版
 */
@FrontCache(space = "MasterPage", type = "jsp")
public class MasterPageName {

    /********************************* 干预计划需求维护页面路径 ****************************************/

    /** 干预计划需求列表页面 */
    public static final String PLAN_DIETTEMPLATE_DETAIL = "/master/plan/diet_template_detail";

    /********************************* 干预计划需求维护页面路径 ****************************************/

    /************************************** 食物页面路径 ******************************************/
    /** 食材类型页面 */
    public static final String FOOD_MASTERIAL_TYPE_VIEW = "/master/food/material_type_view";

    /** 食材页面 */
    public static final String FOOD_MASTERIAL_VIEW = "/master/basic/food/food_material_view";

    /** 食材增加页面 */
    public static final String FOOD_MATERIAL_ADD = "/master/basic/food/food_material_add";

    /** 食材更改页面 */
    public static final String FOOD_MATERIAL_UPDATE = "/master/basic/food/food_material_update";

    /** 食谱页面 */
    public static final String FOOD_VIEW = "/master/basic/food/food_view";

    /** 食谱增加页面 */
    public static final String FOOD_ADD = "/master/basic/food/food_add";

    /** 食谱更改页面 */
    public static final String FOOD_UPDATE = "/master/basic/food/food_update";

    /************************************** 食物页面路径 ******************************************/

    /** 疾病增加页面 */
    public static final String DISEASE_ADD = "/master/disease/disease_add";

    /** 疾病指标页面 */
    public static final String DISEASE_QUOTA_VIEW = "/master/items/disease_item_quota";

    /** 疾病指标页面 */
    public static final String INSPECT_DISEASE_QUOTA_VIEW = "/master/items/disease_analysis_quota";

    /** 诊断营养处方页面 */
    public static final String DISEASE_PRESCRIPTION_VIEW = "/master/items/disease_prescription";

    /** 诊断项目配置辅助检查项目页面 */
    public static final String DISEASE_INSPECT_VIEW = "/master/items/disease_inspect";

    /** 诊断项目配置元素页面 */
    public static final String DISEASE_NUTRIENT_VIEW = "/master/items/disease_nutrient";

    /************************************** 疾病页面路径 ******************************************/

    /************************************ 检查项目管理 *********************************************/
    /** 检查项目添加 初始化页面 */
    public static final String INSPECTITEM_INIT_ADD = "/master/inspectitem/inspect_item_add";

    /** 检查项目更新 初始化页面 */
    public static final String INSPECTITEM_INIT_UPDATE = "/master/inspectitem/inspect_item_update";

    /** 检查项目配置 初始化页面 */
    public static final String INSPECTITEM_INIT_CONFIG = "/master/inspectitem/inspect_item_config";

    /************************************ 检查项目管理 *********************************************/
    /************************************ 检查项目与指标配置 *********************************************/
    /** 配置医院检查项目与指标 */
    public static final String DOCTOR_INSPECT_ITEM_CONFIG_VIEW = "/master/items/doctor_inspect_item_config";

    /** 检查项目指标配置 */
    public static final String MNT_DOCTOR_JINSPECT_ITEM_CONFIG_VIEW = "/master/items/doctor_inspect_item_config";

    /************************************ 商品管理 *********************************************/
    /** 商品添加 初始化页面 */
    public static final String PRODUCT_INIT_ADD = "/master/basic/product/product_add";

    /** 商品更新 初始化页面 */
    public static final String PRODUCT_INIT_UPDATE = "/master/basic/product/product_update";

    /** 商品一览页面 */
    public static final String PRODUCT_VIEW = "/master/basic/product/product_view";

    /** 商品元素页面 */
    public static final String PRODUCT_ELEMENT_CONFIG = "/master/basic/product/product_element_config";

    /************************************ 摄入量模版管理 *********************************************/
    /** 摄入量模版添加页面 */
    public static final String INTAKE_VIEW = "/master/plan/intake_view";

    /** 摄入量模版添加页面 */
    public static final String INTAKE_ADD = "/master/plan/intake_add";

    /** 摄入量模版添加页面 */
    public static final String INTAKE_EDIT = "/master/plan/intake_edit";

    /** 摄入量模版编辑页面 */
    public static final String INTAKE_UPDATE = "/master/plan/intake_update";

    /** 摄入量模版明细页面 */
    public static final String INTAKE_DETAIL = "/master/plan/intake_detail";

    /** 摄入量模版明细页面 */
    public static final String INTAKE_TYPE_VIEW = "/master/plan/intake_type_view";

    /************************************ 摄入量模版管理 *********************************************/

    /************************************ 副食模版管理 *********************************************/
    /** 副食模版明细页面 */
    public static final String INTAKE_SUBSIDIARY_DETAIL_VIEW = "/master/plan/intake_subsidiary_detail_view";

    /** 常用副食模板 */
    public static final String INTAKE_SUBSIDIARY_VIEW = "/master/plan/intake_subsidiary_view";

    /************************************ 副食模版管理 *********************************************/

    /************************************ 食物卡片 *********************************************/
    /** 健康要点添加 初始化页面 */
    public static final String INTERVENEPOINTS_INIT_VIEW = "/master/plan/diagnosis_course_view";

    /**************************************** 食物卡片模板信息 ************************************************/

    /** 孕期课程添加 页面 */
    public static final String PREGNANCYCOURSE_INIT_ADD = "/master/plan/pregnancy_course_add";

    /** 孕期课程明细页面 */
    public static final String PREGNANCYCOURSE_DETAIL_INIT = "/master/plan/course_detail_view";

    /** 课程添加初始化页面 */
    public static final String NUTRIENT_VIEW = "/master/basic/nutrient_view";

    /********************************************** 饮食原则 ******************************************/
    /** 饮食原则一览页面 */
    public static final String DIET_TENET_VIEW = "/master/plan/intervene_points_view";

    /********************************************** 基础课程 ******************************************/
    /** 诊断课程添加页面 */
    public static final String COURSE_INIT = "/master/plan/course_view";

    /********************************************** 商品类别 ******************************************/
    public static final String PRODUCT_CATEGORY_VIEW = "/master/basic/product/product_category_view";

    public static final String PRODUCT_AND_CATEGORY_VIEW = "/master/basic/product/product_and_category_view";
}
