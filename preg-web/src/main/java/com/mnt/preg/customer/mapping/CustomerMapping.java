/*
 * CustomerMapping.java	 1.0   2015-01-04
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 会员模块 操作mapping映射(根据模块分)
 * 
 * @author wangxin
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-01-04 wangxin 初版
 */
@FrontCache(space = "Customer")
public class CustomerMapping {

    /********************************* 会员基本管理 ************************************/

    public final static String ADD_CUST = "/page/cust/cust_add.action";
    
    public final static String ADD_CUST_ONLY = "/page/cust/cust_add_only.action";

    public final static String QUERY_CUST = "/page/cust/cust_query.action";

    public final static String QUERY_CUST_DIAGNOSIS_REGISTER = "/page/cust/cust_query_diagnosis_register.action";

    public final static String QUERY_CUST_BIRTHENDING = "/page/cust/cust_query_birthEnding.action";
    
    public final static String QUERY_CUST_DIAGNOSIS = "/page/cust/cust_query_diagnosis.action";

    public final static String QUERY_CUST_PREG_RECPRD = "/page/cust/cust_query_preg_record.action";

    public final static String QUERY_CUST_PREG_INFO = "/page/cust/cust_query_preg_info.action";

    public final static String INIT_UPDATE_CUST = "/page/cust/cust_init_update.action";

    public final static String UPDATE_CUST = "/page/cust/cust_update.action";

    public final static String REMOVE_CUST = "/page/cust/remove_cust.action";

    public final static String VALIDATE_PATIENTID = "/page/cust/validate_patientId.action";

    public final static String VALIDATE_SIKEId = "/page/cust/validate_sikid.action";

    public final static String VALIDATE_ICARD = "/page/cust/validate_icard.action";

    public final static String VALIDATE_PATIENT_ICARD = "/page/cust/validate_patient_icard.action";

    public final static String VALIDATE_SIKEID = "/page/cust/validate_sikeId.action";

    public final static String QUERY_FTP_DATA = "/page/cust/query_ftp_data.action";

    public final static String CUST_GUIDE_PAGE = "/page/cust/guide_page.action";

    public final static String CUST_GUIDE_GEREN = "/page/cust/guide_geren.action";

    public final static String CUST_GUIDE_BAOGAO = "/page/cust/guide_baogao.action";

    public final static String CUST_GUIDE_JILU = "/page/cust/guide_jilu.action";

    public final static String CUST_GUIDE_FANGAN = "/page/cust/guide_fangan.action";

    /********************************* 会员基本管理 ************************************/

    /********************************* 膳食评估 ************************************/
    public final static String INIT_MEALS_CUST_SURVEY = "/page/cust/meals_cust_survey_init.action";// 初始化会员信息调查

    public final static String INIT_MEALS_SURVEY = "/page/cust/meals_survey_init.action";// 初始化饮食调查

    public final static String INIT_LIFE_STYLE = "/page/cust/init_life_style.action";// 初始化生活方式营养调查

    public final static String RECORD_FOOD_QUERY = "/page/cust/record_food_query.action";// 初始化饮食调查

    public final static String RECORD_MEALS_SURVEY = "/page/cust/meals_survey_record.action";// 饮食记录

    public final static String MEALS_REPORT = "/page/cust/meals_report.action";// 报表

    /********************************* 膳食评估 ************************************/

    /********************************* 孕期膳食频率 ************************************/

    public final static String PREG_DIETARY_FREQUENCY = "/page/cust/preg_dietary_frequency.action";// 报表

    /*************************************** 短信模块请求路径 ***************************************/
    public static final String IMPORT_CUSTOMER_INFO = "/page/customer/import/import_customer_info.action";// 用户信息文件上传

    public static final String QUERY_IMPORT_RESULT = "/page/customer/import/query_import_result.action";// 查询上传结果信息列表

    /*************************************** 短信模块请求路径 ***************************************/

    /*************************************** 接诊单模块请求路径 ***************************************/

    public final static String RECEIVE_PLAN_ORDER_DETAIL = "/page/plan/receive_plan_order_detail.action";

    public final static String RECEIVE_PLAN_ORDER_SHOPING_DETAIL = "/page/plan/receive_plan_order_shoping_detail.action";

    public final static String RECEIVE_PLAN_ORDER = "/page/plan/receive_plan_order.action";

    public final static String ORDER_UPDATE = "/page/plan/order_update.action";

    public static final String PREGNANCY_ADD_VIEW = "/page/plan/pregnancy_add_view.action";

    public static final String DIAGNOSIS_CHECK_CUSTID = "/page/plan/diagnosis_check_custid.action";

    public final static String PLAN_GET_ORDER = "/page/plan/get_order.action";

    public final static String PLAN_GET_ORDER_MANAGER = "/page/plan/PLAN_GET_ORDER_MANAGER.action";

    public final static String DIAGNOSIS_INITAL_VIEW_GET = "/page/plan/NEWLY_DIAGNOSED_VIEW_GET.action";

    public final static String PLAN_ADD_ORDER_INIT = "/page/plan/plan_add_order_init.action";

    public final static String PLAN_ADD_ORDER = "/page/plan/plan_add_order.action";

    public final static String PLAN_ADD_ORDERSHOP = "/page/plan/plan_add_ordershop.action";

    public final static String PLAN_REMOVE_ORDERSHOP = "/page/plan/plan_remove_ordershop.action";

    public final static String PLAN_UPDATE_ORDERSHOP = "/page/plan/plan_update_ordershop.action";

    public final static String PLAN_GET_PINGJIA = "/page/plan/get_pingjia.action";

    public final static String PLAN_CHECK_PLANSTATUS = "/page/plan/check_planstatus.action";

    public final static String PLAN_GET_EXTENDERANALYSISDATA = "/plan/plan/get_extenderanalysisdata.action";

    public final static String PLAN_EXTENDER_ANALYSIS = "/plan/plan/extender_analysis.action";

    public final static String PLAN_EXTENDER_ANALYSIS_RESULT = "/plan/plan/extender_analysis_result.action";

    public final static String PLAN_EXTENDER_ANALYSIS_HISTORY = "/plan/plan/extender_analysis_history.action";

    public final static String PLAN_EXTENDER_ORDER_ADD = "/page/plan/extender_order_add.action";

    public final static String PLAN_EXTENDER_ASSESS_REPORT = "/page/plan/extender_assess_report.action";

    public final static String PLAN_EXTENDER_ASSESS = "/page/plan/extender_assess_item.action";

    public final static String EXAM_GUIDE_LIST = "/page/examitem/guide_list.action";

    public final static String PLAN_QUOTA_GATHER = "/page/plan/plan_quota_gather.action";// 指标录入

    public final static String PLAN_QUOTA_SHOW = "/page/plan/plan_quota_show.action";// 指标查看页面

    /********************************* 当前病历（指标） ************************************/

    public static final String ITEM_TEMPLET_QUERY = "/page/master/item_templet_query.action";

    /** 商品类别增加请求 */
    public static final String PRODUCT_CATALOG_ADD = "/page/master/plan/product_catalog_add.action";

    /** 商品类别更改请求 */
    public static final String PRODUCT_CATALOG_UPDATE = "/page/master/plan/product_catalog_update.action";

    /** 检验商品类别名称是否可用请求 */
    public static final String CATALOG_NAME_VALID = "/page/master/plan/catalog_name_valid.action";

    /** 更改商品类型顺序请求 */
    public static final String CATALOG_ORDER_UPDATE = "/page/master/plan/catalog_order_update.action";

    /** 获取商品类型信息请求 */
    public static final String CATALOG_INFO_GET = "/page/master/plan/catalog_info_get.action";

    /** 删除商品类型信息请求 */
    public static final String PRODUCT_CATALOG_REMOVE = "/page/master/plan/product_catalog_remove.action";

    public static final String QUOTA_ITEM_SAVE = "/page/diagnosis/quota_item_save.action";

    public static final String QUOTA_ITEM_DELETE = "/page/diagnosis/quota_item_delete.action";

    public static final String QUOTA_ITEM_QUERY = "/page/diagnosis/quota_item_query.action";

    /** 查询某人某项指标的历史记录 */
    public static final String GET_ITEM_HISTORY_CODE = "/page/diagnosis/get_item_history_code.action";
}
