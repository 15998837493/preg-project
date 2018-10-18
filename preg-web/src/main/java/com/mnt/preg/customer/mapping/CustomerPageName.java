/*
 * CustomerPageName.java	 1.0   2015-1-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 客户页面名称
 * 
 * @author zhj
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zhj 初版
 */
@FrontCache(space = "CustomerPage", type = "jsp")
public class CustomerPageName {

    // 患者建档信息页
    public static final String PREG_ARCHIVES_INFO = "/customer/preg_archives_info";

    // 会员增加页
    public static final String CUSTOMER_ADD = "/customer/customer_add";

    // 会员编辑页
    public static final String CUSTOMER_UPDATE = "/customer/customer_update";

    // 会员一览页
    public static final String CUSTOMER_VIEW = "/customer/customer_view";

    // 疾病史页
    public static final String DISEASE_HISTORY = "/customer/archives/disease_history";

    // 药物史列表页
    public static final String DOSE_HISTORY_VIEW = "/customer/archives/dose_history_view";

    // 手术列表页
    public static final String OPERATION_HURT_HISTORY_VIEW = "/customer/archives/operation_hurt_history_view";

    // 手术创伤增加页
    public static final String OPERATION_HURT_HISTORY_ADD = "/customer/view/operationhurt/operation_hurt_history_add";

    // 手术创伤修改页
    public static final String OPERATION_HURT_HISTORY_UPDATE = "/customer/view/operationhurt/operation_hurt_history_update";

    // 住院史列表页
    public static final String INHOSPITAL_HISTORY_VIEW = "/customer/archives/inhospital_history_view";

    // 住院史增加页
    public static final String INHOSPITAL_HISTORY_ADD = "/customer/view/inhospital/inhospital_history_add";

    // 住院史修改页
    public static final String INHOSPITAL_HISTORY_UPDATE = "/customer/view/inhospital/inhospital_history_update";

    // 档案管理--家族史
    public static final String FAMILY_HISTORY_VIEW_CUSTOMER = "/customer/archives/family_history_view";

    // 医学诊疗工具--问卷调查
    public static final String MNDT_QUESTION = "/cdnm/mndt/mndt_questionnaire";

    // 医学诊疗工具--问卷调查
    public static final String MNDT_QUESTION_LU = "/cdnm/lutmp/mndt_questionnaire";

    // 医学诊疗工具--会员信息调查
    public static final String MNDT_CUSTOMER = "/cdnm/mndt/mndt_customer";

    // 医学诊疗工具--检查身体信息是否完善
    public static final String MNDT_CHECK_CUSTOMER = "/cdnm/mndt/mndt_check_customer";

    // 医学诊疗工具--健康状况及病史—疾病史页
    public static final String DISEASE_HISTORY_MNDT = "/cdnm/mndt/disease_history";

    // 医学诊疗工具--健康状况及病史—药物史列表页
    public static final String DOSE_HISTORY_VIEW_MNDT = "/cdnm/mndt/dose_history_view";

    // 医学诊疗工具--身体功能评估--信息查询
    public static final String PHYSICAL_ASSESSMENT_VIEW = "/cdnm/mndt/physical_assessment_view";

    // 医学诊疗工具--会员信息填写
    public static final String CUSTOMER_UPDATE_MNDT = "/cdnm/mndt/customer_update";

    // 医学诊疗工具--膳食评估--饮食填写页面
    public static final String MEALS_SURVEY_MNDT = "/cdnm/mndt/meals_survey";

    // 医学诊疗工具--膳食评估--报表页面
    public static final String MEALS_REPORT_MNDT = "/cdnm/mndr/meals_report";

    // 医学诊疗工具--生活评估
    public static final String LIFESTYLE_VIEW = "/cdnm/mndt/lifeStyle";

    // 医学诊疗工具--膳食评估--会员信息调查
    public static final String MEALS_CUSTOMER = "/question/diet/meals_customer";

    // 医学诊疗工具--膳食评估--饮食填写页面
    public static final String MEALS_SURVEY = "/examitem/diet/diet_survey";

    // 医学诊疗工具--膳食及生活方式评估
    public static final String LIFE_STYLE = "/examitem/life/life_style";

    // 医学诊疗工具--膳食频率调查
    public static final String DIETARY_FREQUENCY = "/examitem/dietary_frequency/dietary_frequency";

    // 医学诊疗工具--膳食评估--报表页面
    public static final String MEALS_REPORT = "/question/diet/meals_report";

    // 初始化创建干预计划页面
    public static final String INIT_CREATE_PLAN = "/cdnb/plan/create_plan";

    // 初始化饮食治疗处方页面
    public static final String INIT_CREATE_PLAN_TEMPLATE = "/cdnb/plan/create_plan_template";

    // 预约体检工作台页面
    public static final String APPOINT_WORKBENCH_PAGE = "/customer/yytj/workbench";

    public static final String APPOINT_ADD_CUSTOMER_PAGE = "/customer/yytj/customer_init";

    // 预约自测问题页面
    public static final String APPOINT_APPOINT_QUESTION_PAGE = "/customer/yytj/appoint_question";

    // 预约报告生成
    public static final String APPOINT_REPORT_PAGE = "/customer/yytj/appoint_report_page";

    // 干预监测用户一览页面
    public static final String INIT_MONITOR_CUSTOMER_VIEW = "/cdnb/monitor/customer_view";

    // 健康树
    public static final String HEALTHTREEVIEW = "/cdnm/mndr/health_tree_view";

    // 肥胖诊断报告
    public static final String FATDIAGNOSISREPORT = "/cdnm/mndr/fat_diagnosis_report";

    // 产品购买一览
    public static final String PRODUCT_INIT_VIEW = "/product/purchase/product_view";

    // 查询消费一览
    public static final String ORDER_VIEW = "/product/query/order_view";

    // 查询退款订单一览
    public static final String ORDER_REFUND_VIEW = "/product/refund/order_refund_view";

    // 选择摄入量
    public static final String RECIPES_VIEW = "/cdnb/plan/recipes_view";

    // 个性化食谱
    public static final String DIET_FOOD_VIEW = "/cdnb/plan/diet_view";

    // 编辑补充剂
    public static final String PLAN_EDIT_EXTENDER = "/cdnb/plan/edit_extender";

    // 医学营养治疗方案
    public static final String TREATMENT_SHOW_VIEW = "/cdnb/plan/treatment_view";

    // 邮件平台
    public static final String MAILPLATFORM = "/cdnb/plan/mail_platform_view";

    // 邮件平台
    public static final String MAILSEND = "/cdnb/plan/mailsend";

    // 未回复消息一览页面
    public static final String MESSAGE_VIEW = "/cdnb/message/msg_view";

    // 消息详情页面
    public static final String MESSAGE_DETAIL = "/cdnb/message/msg_detail";

    // 已发送消息详情页面
    public static final String MESSAGE_SEND_DETAIL = "/cdnb/message/msg_send_detail";

    // 未回复消息一览页面
    public static final String MESSAGE_WRITE = "/cdnb/message/msg_write";

    // 未点评日记一览页面
    public static final String DIARY_VIEW = "/cdnb/plan/diary_view";

    // 日记点评页面
    public static final String DIARY_DETAIL_VIEW = "/cdnb/plan/diary_assessment_report";

    // 营养慢病干预-干预执行-膳食报告--报表页面
    public static final String CDNB_MEALS_REPORT = "/cdnb/execute/menu/meals_assessment_report";

    // 干预记录
    public static final String PLAN_TRACK_VIEW = "/cdnb/manage/plan_track_view";

    // 健康状况及病史—疾病史页
    public static final String DISEASE_HISTORY_CDNB = "/cdnb/execute/menu/disease_history";

    // 健康状况及病史—药物史列表页
    public static final String DOSE_HISTORY_VIEW_CDNB = "/cdnb/execute/menu/dose_history_view";

    // 会员批量导入结果列表页面
    public static final String IMPORT_RESULT_VIEW = "/customer/view/import_result_view";

    // 帖子添加初始化页面
    public static final String TOPIC_ADD_INIT = "/customer/bbs/topic/topic_add";

    // 帖子修改初始化页面
    public static final String TOPIC_UPDATE_INIT = "/customer/bbs/topic/topic_eidt";

    // 读取帖子页面
    public static final String TOPIC_READ = "/customer/bbs/topic/topic_detail";

    // 主题添加页面
    public static final String THEME_ADD = "/customer/bbs/theme/theme_add";

    public static final String THEME_EDIT = "/customer/bbs/theme/theme_edit";

    // 注册体检会员页面
    public static final String CUSTOMER_PE_INIT = "/customer/jkpg/customer_init";

    public static final String CARD_SHOW = "/customer/jkpg/card_show";

    public static final String RECEIVE_MANAGE = "/customer/receive/receive_manage";

    public static final String RECEIVE_CUST = "/customer/receive/receive_customer";

    public static final String RECEIVE_MEALS = "/customer/receive/receive_meals";

    public static final String RECEIVE_MEALS_REPORT = "/customer/receive/receive_meals_report";

    public static final String PLAN_PRODUCT_VIEW = "/plan/receive/plan_product";

    public static final String PLAN_DAY_DIET = "/plan/receive/plan_day_diet";

    public static final String PLAN_DAY_DIET_MODEL = "/plan/receive/plan_day_diet_model";

    public static final String PLAN_DAY_DIET_EQUI = "/plan/receive/plan_day_diet_equi";

    public static final String PLAN_SHOW = "/plan/receive/plan_show";

    public static final String PLAN_INTAKE_VIEW = "/plan/receive/plan_intake_view";

    public static final String PLAN_BALANCE = "/plan/receive/plan_balance";

    public static final String RECEIVE_PLAN_MANAGE = "/customer/receive/service_plan_manage";

    public static final String RECEIVE_PLAN_SERVICE_VIEW = "/customer/receive/service_plan_view";

    public static final String PLAN_DIAGNOSIS_ORDER = "/plan/order/order_product";

    public static final String PLAN_DIAGNOSIS_ORDER_MANAGER = "/plan/order/order_view";

    public static final String PLAN_DIAGNOSIS_ORDER_ADD = "/plan/order/order_templet";

    public static final String PLAN_FUZHU = "/plan/platform/fuzhu";

    public static final String PLAN_YIZHU = "/plan/platform/yizhu";

    public static final String PLAN_EXTENDER_ASSESS = "/examitem/extender/extender_assess";

    public static final String PLAN_WANSHAN = "/plan/platform/wanshan";

    public static final String PLAN_UPLOAD = "/plan/platform/upload_plan";

    public static final String RECEIVE_REPORT = "/customer/receive/service_report_list";

    public static final String RECEIVE_SERVICE_DIET = "/customer/receive/service_assessment_report";

    public static final String RECEIVE_SERVICE_HEALTHTREE = "/customer/receive/service_healthtree_view";

    public static final String RECEIVE_SERVICE_PRIMARY = "/customer/receive/service_primary_view";

    public static final String CUSTOMER_GROUP_VIEW = "/customer/group/customer_group";

    // 会员编辑页
    public static final String MYCUSTOMER_EDIT = "/customer/mycustomer/customer_edit";

    // 疾病史页
    public static final String MYCUSTOMER_DISEASE_HISTORY = "/customer/mycustomer/disease_history";

    // 档案管理--家族史
    public static final String MYCUSTOMER_FAMILY_HISTORY_VIEW_CUSTOMER = "/customer/mycustomer/family_history_view";

    // 药物史列表页
    public static final String MYCUSTOMER_DOSE_HISTORY_VIEW = "/customer/mycustomer/dose_history_view";

    // 手术列表页
    public static final String MYCUSTOMER_OPERATION_HURT_HISTORY_VIEW = "/customer/mycustomer/operation_hurt_history_view";

    // 住院史列表页
    public static final String MYCUSTOMER_INHOSPITAL_HISTORY_VIEW = "/customer/mycustomer/inhospital_history_view";

    // 聊天页面
    public static final String CHAT_VIEW = "/customer/consult/chatmsg";

    public static final String CUST_GUIDEPAGE = "/customer/platform/guide_page";

    public static final String CUST_GEREN = "/customer/platform/cust_geren";

    public static final String CUST_BAOGAO = "/customer/platform/cust_baogao";

    public static final String CUST_JILU = "/customer/platform/cust_jilu";

    public static final String CUST_FANGAN = "/customer/platform/cust_fangan";

    public static final String PRODUCT_CATEGORY_VIEW = "/master/basic/product/product_category_view";

    // 指标录入查看页
    public static final String PLAN_QUOTA_SHOW = "/plan/quota/quota_show_view";

}
