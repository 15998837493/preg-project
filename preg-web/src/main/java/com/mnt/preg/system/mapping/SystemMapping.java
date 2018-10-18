
package com.mnt.preg.system.mapping;

/*
 * SystemMapping.java	 1.0   2014-12-18
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 系统管理模块 操作mapping映射(根据模块分)
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-24 zcq 初版
 */
@FrontCache(space = "System")
public class SystemMapping {

    /********************************* 角色管理模块 *********************************/
    /** 权限查询请求 */
    public static final String RIGHT_QUERY = "/page/right/query_right.action";

    /** 权限查询请求 */
    public static final String RIGHT_QUERY_VIEW = "/page/right/query_right_view.action";

    /** 权限增加页面初始化请求 */
    public static final String RIGHT_INIT_ADD = "/page/right/init_add_right.action";

    /** 权限增加请求 */
    public static final String RIGHT_ADD = "/page/right/add_right.action";

    /** 权限修改页面初始化请求 */
    public static final String RIGHT_INIT_UPDATE = "/page/right/init_update_right.action";

    /** 权限修改请求 */
    public static final String RIGHT_UPDATE = "/page/right/update_right.action";

    /** 权限删除请求 */
    public static final String RIGHT_DEL = "/page/right/del_right.action";

    /** 校验权限名称是否可用 */
    public static final String RIGHT_NAME_CHECK = "/page/right/right_name_check.action";

    /********************************* 角色管理模块 *********************************/

    /********************************** 职务管理功能模块 **********************************/
    /** 职务信息查询 */
    public static final String ROLE_INIT_VIEW = "/page/role/role_init_view.action";

    /** 职务信息查询 */
    public static final String ROLE_QUERY = "/page/role/role_query.action";

    /** 职务信息添加初始化请求 */
    public static final String ROLE_INIT_ADD = "/page/role/role_init_add.action";

    /** 职务添加请求 */
    public static final String ROLE_ADD = "/page/role/role_add.action";

    /** 职务更改初始化请求 */
    public static final String ROLE_INIT_UPDATE = "/page/role/role_init_update.action";

    /** 职务更改请求 */
    public static final String ROLE_UPDATE = "/page/role/role_update.action";

    /** 职务注销请求 */
    public static final String ROLE_REMOVE = "/page/role/role_remove.action";

    /** 校验职位名称是否可用 */
    public static final String ROLE_NAME_CHECK = "/page/role/role_name_check.action";

    /********************************** 职务管理功能模块 **********************************/

    /********************************** 功能菜单管理模块 **********************************/
    /** 功能菜单信息查询 */
    public static final String MENU_VIEW = "/page/menu/menu_view.action";

    /** 功能菜单信息查询 */
    public static final String MENU_QUERY = "/page/menu/menu_query.action";

    /** 获取功能菜单信息 */
    public static final String MENU_GET = "/page/menu/menu_get.action";

    /** 功能菜单添加请求 */
    public static final String MENU_ADD = "/page/menu/menu_add.action";

    /** 功能菜单更改请求 */
    public static final String MENU_UPDATE = "/page/menu/menu_update.action";

    /** 功能菜单排序更改请求 */
    public static final String MENU_UPDATE_ORDER = "/page/menu/menu_update_order.action";

    /** 功能菜单删除请求 */
    public static final String MENU_REMOVE = "/page/menu/menu_remove.action";

    /** 功能菜单名称是否可用 */
    public static final String MENU_NAME_VALID = "/page/menu/menu_name_valid.action";

    /********************************** 功能菜单管理模块 **********************************/

    /********************************** 组织结构管理模块 **********************************/
    /** 组织结构信息查询 */
    public static final String DEPT_VIEW = "/page/dept/dept_view.action";

    /** 组织结构信息查询 */
    public static final String DEPT_QUERY = "/page/dept/dept_query.action";

    /** 获取组织结构信息 */
    public static final String DEPT_GET = "/page/dept/dept_get.action";

    /** 组织结构添加请求 */
    public static final String DEPT_ADD = "/page/dept/dept_add.action";

    /** 组织结构更改请求 */
    public static final String DEPT_UPDATE = "/page/dept/dept_update.action";

    /** 组织结构排序更改请求 */
    public static final String DEPT_UPDATE_ORDER = "/page/dept/dept_update_order.action";

    /** 组织结构删除请求 */
    public static final String DEPT_REMOVE = "/page/dept/dept_remove.action";

    /** 组织结构名称是否可用 */
    public static final String DEPT_NAME_VALID = "/page/dept/dept_name_valid.action";

    /********************************** 功能菜单管理模块 **********************************/

    /********************************* 机构管理功能模块 *********************************/
    /** 获取公司列表的同时获取权限列表请求 */
    public static final String INS_VIEW = "/page/institution/ins_view.action";

    /** 机构添加请求 */
    public static final String INS_ADD = "/page/institution/ins_add.action";

    /** 机构更改请求 */
    public static final String INS_UPDATE = "/page/institution/ins_update.action";

    /** 机构注销请求 */
    public static final String INS_REMOVE = "/page/institution/ins_remove.action";

    /** 机构运行状态 */
    public static final String INS_STATE = "/page/institution/ins_state.action";

    /** 机构信息列表查询 */
    public static final String INS_QUERY = "/page/institution/ins_query.action";

    /** 机构信息修改初始化 */
    public static final String INS_UPDATE_INIT = "/page/institution/ins_update_init.action";

    /** 机构信息修改初始化 */
    public static final String INS_CONFIG = "/page/institution/ins_config.action";

    /** 获取机构功能菜单请求 */
    public static final String INS_MENU = "/page/institution/ins_menu.action";

    /** 获取机构打印选项请求 */
    public static final String INS_PRINT = "/page/institution/ins_print.action";

    /** 保存机构功能菜单请求 */
    public static final String INS_MENU_SAVE = "/page/institution/ins_menu_save.action";

    /** 保存机构打印选项请求 */
    public static final String INS_PRINT_SAVE = "/page/institution/ins_print_save.action";

    /** 校验机构编码是否存在请求 */
    public static final String INSID_CHECK = "/page/institution/ins_id_check.action";

    /** 校验机构编码是否存在请求 */
    public static final String INSNAME_CHECK = "/page/institution/ins_name_check.action";

    /********************************* 机构管理功能模块 *********************************/

    /********************************* 分支管理功能模块 *********************************/
    /** 分支查询请求 */
    public static final String DEPT_INIT = "/page/dept/dept_init.action";

    /** 分支添加请求 */
    public static final String DEPT_INFO_ADD = "/page/dept/dept_add.action";

    /** 检索部门职务请求 */
    public static final String DEPT_POSITION_QUERY = "/page/dept/dept_position_query.action";

    /** 分支更改请求 */
    public static final String DEPT_INFO_UPDATE = "/page/dept/dept_update.action";

    /** 分配职务请求 */
    public static final String DEPT_POSITION = "/page/dept/dept_position.action";

    /** 分支注销请求 */
    public static final String DEPT_INFO_REMOVE = "/page/dept/dept_remove.action";

    /** 获取树的子结点 */
    public static final String DEPT_TREE = "/page/dept/deptTree.action";

    /********************************* 分支管理功能模块 *********************************/

    /********************************* 用户管理功能模块 *********************************/

    /** 人员一览页 */
    public static final String USER_VIEW = "/page/user/user_view.action";

    /** 异步检索用户 */
    public static final String USER_QUERY = "/page/user/query_user.action";

    /** 检索部门职务列表请求 */
    public static final String QUERY_DEPT_POSITION = "/page/user/query_dept_position.action";

    /** 校验用户帐号请求 */
    public static final String USER_VALIDATE_CODE = "/page/user/validate_user_code.action";

    /** 初始化添加用户 */
    public static final String USER_INIT_ADD = "/page/user/init_user_add.action";

    /** 添加用户 */
    public static final String USER_ADD = "/page/user/add_user.action";

    /** 添加机构管理员 */
    public static final String USER_INS_ADD = "/page/institution/add_ins_user.action";// 添加机构管理员请求

    /** 初始化修改用户 */
    public static final String USER_INIT_UPDATE = "/page/user/init_update_user.action";

    /** 首页初始化修改用户 */
    public static final String USER_INIT_UPDATE_MAIN = "/page/user/init_update_user_mian.action";

    /** 修改用户 */
    public static final String USER_UPDATE = "/page/user/update_user.action";

    /** 删除用户 */
    public static final String USER_DEL = "/page/user/del_user.action";

    /** 重置密码 */
    public static final String USER_PSW_RESET = "/page/user/psw_reset_user.action";

    /** 修改密码初始化 */
    public static final String USER_INIT_UPSW = "/page/user/init_upsw_user.action";

    /** 修改密码 */
    public static final String USER_PSW_UPDATE = "/page/user/psw_update_user.action";

    /** 批量导入员工 */
    public static final String IMPORT_USER = "/import_user.action";

    /** 校验用户编码是否存在请求 */
    public static final String USER_CODE_CHECK = "/page/user/user_code_check.action";

    /** 我的会员 */
    public static final String USER_MY_CUSTOMERS = "/page/user/my_customers.action";

    /** 我的消息 */
    public static final String SYSTEM_NOTICE = "/page/user/system_notice_main.action";

    /** 初始化添加用户 */
    public static final String USER_MY_INFO = "/page/user/my_info.action";

    /** 修改用户 */
    public static final String USER_UPDATE_MYINFO = "/page/user/update_user_myinfo.action";

    /********************************* 用户管理功能模块 *********************************/

    /********************************* 系统参数功能模块 *********************************/
    /** 系统参数列表请求 */
    public static final String SYSTEM_PARAM_VIEW = "/page/params/system_param_view.action";

    /** 系统参数列表请求 */
    public static final String SYSTEM_PARAM_QUERY = "/page/params/system_param_query.action";

    /** 系统参数列表请求 */
    public static final String AJAX_SYSTEM_PARAM_VIEW = "/page/params/ajax_system_param_view.action";

    /** 初始化修改请求 */
    public static final String SYSTEM_PARAM_INIT_UPDATE = "/page/params/system_param_init_update.action";

    /** 修改请求 */
    public static final String SYSTEM_PARAM_UPDATE = "/page/params/system_param_update.action";

    /** 加载url */
    public static final String SYSTEM_URL_CODE = "/page/params/system_url_code.action";

    public static final String SYSTEM_CODE_SELECT = "/page/params/system_code_select.action";

    /********************************* 打印选项功能模块 *********************************/

    public static final String PRINT_QUERY = "/page/system/print_query.action";

    public static final String PRINT_MAPLIST_GET = "/page/system/print_maplist_get.action";

    /********************************* 系统参数功能模块 *********************************/

    /** 意见反馈 检索 */
    public static final String SYSTEM_FEEDBACK_QUERY = "/page/system/sys_feedback_query.action";

    /** 意见反馈 内容详情 */
    public static final String SYSTEM_FEEDBACK_DEATIL = "/page/system/sys_feedback_detail.action";

    /** 意见反馈 回复 */
    public static final String SYSTEM_FEEDBACK_REPLY = "/page/system/sys_feedback_reply.action";

    /** 意见反馈删除 */
    public static final String SYSTEM_FEEDBACK_DELETE = "/page/system/sys_feedback_delete.action";

    /********************************* 广告模块开始 *********************************/
    public static final String SYSTE_AD_QUERY = "/page/system/ad/ad_query.action";

    public static final String SYSTEM_AD_ADD = "/page/system/ad/ad_add.action";

    public static final String SYSTEM_AD_UPDATE = "/page/system/ad/ad_update.action";

    public static final String SYSTEM_AD_TO_UPDATEPAGE = "/page/system/ad/to_ad_update_page.action";

    public static final String SYSTEM_AD_REMOVE = "/page/system/ad/ad_remove.action";

    /********************************* 广告模块结束 *********************************/

    /********************************* 检查项目功能模块 *********************************/
    /** 检查项目查询 */
    public static final String EXAM_ITEM_QUERY = "/page/system/exam/exam_item_query.action";

    /** 添加检查项目 */
    public static final String EXAM_ITEM_ADD = "/page/system/exam/exam_item_add.action";

    /** 修改检查项目初始化 */
    public static final String EXAM_ITEM_UPDATE_INIT = "/page/system/exam/exam_item_update_init.action";

    /** 检查项目更新 */
    public static final String EXAM_ITEM_UPDATE = "/page/system/exam/exam_item_update.action";

    /** 删除检查项目 */
    public static final String EXAM_ITEM_DELETE = "/page/system/exam/exam_item_delete.action";

    /** 体检套餐查询 */
    public static final String EXAM_GROUP_QUERY = "/page/system/exam/exam_group_query.action";

    /** 添加体检套餐 */
    public static final String EXAM_GROUP_ADD = "/page/system/exam/exam_group_add.action";

    /** 转到套餐项目配置页面 */
    public static final String EXAM_GROUP_MANAGE_INIT = "/page/system/exam/exam_group_manage_init.action";

    /** 体检套餐包含信息保存 */
    public static final String EXAM_GROUP_DETAIL_SAVE = "/page/system/exam/exam_group_detail_save.action";

    /********************************* 检查项目功能模块 *********************************/
    /********************************* 导检信息功能模块 *********************************/
    /** 体检房间修改页面初始化 */
    public static final String EXAM_ROOM_UPDATE_INIT = "/page/system/exam/exam_room_update_init.action";

    /** 体检房间查询 */
    public static final String EXAM_ROOM_QUERY = "/page/system/exam/exam_room_query.action";

    /** 添加体检房间 */
    public static final String EXAM_ROOM_ADD = "/page/system/exam/exam_room_add.action";

    /** 修改体检房间 */
    public static final String EXAM_ROOM_UPDATE = "/page/system/exam/exam_room_update.action";

    /** 删除体检房间 */
    public static final String EXAM_ROOM_DELETE = "/page/system/exam/exam_room_delete.action";

    /** 体检设备修改页面初始化 */
    public static final String EXAM_DEVICE_UPDATE_INIT = "/page/system/exam/exam_device_update_init.action";

    /** 体检设备查询 */
    public static final String EXAM_DEVICE_QUERY = "/page/system/exam/exam_device_query.action";

    /** 添加体检设备 */
    public static final String EXAM_DEVICE_ADD = "/page/system/exam/exam_device_add.action";

    /** 修改体检设备 */
    public static final String EXAM_DEVICE_UPDATE = "/page/system/exam/exam_device_update.action";

    /** 删除体检设备 */
    public static final String EXAM_DEVICE_DELETE = "/page/system/exam/exam_device_delete.action";

    /********************************* 导检信息功能模块 *********************************/

    /********************************* 消息公告功能模块 *********************************/

    /** 消息公告 */
    public static final String SYSTEM_NOTIECE_QUERY = "/page/notice/system_notice_query.action";

    /** 消息公告详细信息 */
    public static final String SYSTEM_NOTIECE_GET = "/page/notice/system_notice_get.action";

    /** 添加消息公告信息 */
    public static final String SYSTEM_NOTIECE_ADD = "/page/notice/system_notice_add.action";

    /** 添加消息公告信息初始化 */
    public static final String SYSTEM_NOTIECE_ADD_INIT = "/page/notice/system_notice_add_init.action";

    /** 修改检查项目(导检)初始化 */
    public static final String EXAM_ITEM_GUIDE_UPDATE_INIT = "/page/system/exam/guide_exam_item_update_init.action";

    /** 添加检查项目(导检)初始化 */
    public static final String EXAM_ITEM_GUIDE_ADD_INIT = "/page/system/exam/guide_exam_item_add_init.action";

    /** 转到项目对应房间配置页面 */
    public static final String EXAM_ITEM_ROOM_MANAGE_INIT = "/page/system/exam/exam_item_room_manage_init.action";

    /** 体检项目与房间对照保存 */
    public static final String EXAM_ITEM_ROOM_MANAGE_SAVE = "/page/system/exam/exam_item_room_manage_save.action";

    /********************************* 消息公告功能模块 *********************************/

    /********************************* 体检项目管理功能模块 *********************************/
    /** 检索体检项目字典表 */
    public static final String ITEM_QUERY = "/page/item/item_query.action";

    /** 获取体检项目字典表 */
    public static final String ITEM_GET = "/page/item/item_get.action";

    /** 添加体检项目字典表 */
    public static final String ITEM_ADD = "/page/item/item_add.action";

    /** 初始化修改体检项目字典表 */
    public static final String ITEM_UPDATE_INIT = "/page/item/item_update_init.action";

    /** 修改体检项目字典表 */
    public static final String ITEM_UPDATE = "/page/item/item_update.action";

    /** 删除体检项目字典表 */
    public static final String ITEM_REMOVE = "/page/item/item_remove.action";

    /** 判断该体检项目字典表是否存在 */
    public static final String ITEM_EXSIT = "/page/item/item_exsit.action";

    /** 检索体检项目来源 */
    public static final String ORIGIN_ITEM_QUERY = "/page/item/origin_item_query.action";

    /** 跳转到该项的来源信息 */
    public static final String ORIGIN_ITEM_FORWARD = "/page/item/origin_item_forward.action";

    /** 跳转到该项的来源信息 */
    public static final String ORIGIN_ITEM_ADD_FORWARD = "/page/item/origin_item_add_forward.action";

    /** 获取体检项目来源 */
    public static final String ORIGIN_ITEM_GET = "/page/item/origin_item_get.action";

    /** 添加体检项目来源 */
    public static final String ORIGIN_ITEM_ADD = "/page/item/origin_item_add.action";

    /** 初始化修改体检项目来源 */
    public static final String ORIGIN_ITEM_UPDATE_INIT = "/page/item/origin_item_update_init.action";

    /** 修改体检项目来源 */
    public static final String ORIGIN_ITEM_UPDATE = "/page/item/origin_item_update.action";

    /** 删除体检项目来源 */
    public static final String ORIGIN_ITEM_REMOVE = "/page/item/origin_item_remove.action";

    /** 判断该体检项来源是否存在 */
    public static final String ORIGIN_ITEM_EXSIT = "/page/item/origin_item_exsit.action";

    /********************************* 体检项目管理功能模块 *********************************/

    /********************************** 代码表管理模块 **********************************/
    /** 代码表信息查询 */
    public static final String CODE_QUERY = "/page/code/code_query.action";

    /** 代码表信息查询 */
    public static final String CODE_VIEW_QUERY = "/page/code/code_view_query.action";

    /** 代码表缓存 */
    public static final String CODE_CACHE_GET = "/page/code/code_cache_get.action";

    /** 代码表转码 */
    public static final String CODE_CACHE_ALL = "/page/code/code_cache_all.action";

    /** 代码表信息查询 */
    public static final String CODE_UPDATE_VIEW = "/page/code/code_update_view.action";

    /** 代码表信息查询 */
    public static final String CODE_ORDER_TREE = "/page/code/code_order_tree.action";

    /** 获取代码表信息 */
    public static final String CODE_GET = "/page/code/code_get.action";

    /** 代码表添加请求 */
    public static final String CODE_ADD = "/page/code/code_add.action";

    /** 代码表更改请求 */
    public static final String CODE_UPDATE = "/page/code/code_update.action";

    /** 代码表排序更改请求 */
    public static final String CODE_UPDATE_ORDER = "/page/code/code_update_order.action";

    /** 代码表删除请求 */
    public static final String CODE_REMOVE = "/page/code/code_remove.action";

    /** 代码类型是否可用 */
    public static final String CODE_KIND_VALID = "/page/code/code_kind_valid.action";

    /** 代码表名称是否可用 */
    public static final String CODE_NAME_VALID = "/page/code/code_name_valid.action";

    /** 代码值是否可用 */
    public static final String CODE_VALUE_VALID = "/page/code/code_value_valid.action";

    /** 增加科室初始化页面 */
    public static final String ADD_INIT_DEPARTMENT = "/page/master/add_init_department.action";

    /** 增加科室 */
    public static final String ADD_DEPARTMENT = "/page/master/add_department.action";

    /** 修改科室初始化页面 */
    public static final String UPDATE_INIT_DEPARTMENT = "/page/master/update_init_department.action";

    /** 修改科室 */
    public static final String UPDATE_DEPARTMENT = "/page/master/update_department.action";

    /** 删除科室 */
    public static final String REMOVE_DEPARTMENT = "/page/master/remove_department.action";

    /********************************** 代码表管理模块 **********************************/
    /********************************** 干预重点模块 **********************************/

    /** 验证干预重点编码 */
    public static final String DISEASECODE_VALID = "/page/master/plan/disease_code_valid.action";

    /** 增加干预重点初始化页面 */
    public static final String ADD_INIT_INTERVENEDISEASE = "/page/master/plan/add_init_intervene_disease.action";

    /** 增加干预重点 */
    public static final String ADD_INTERVENEDISEASE = "/page/master/plan/add_intervene_disease.action";

    /** 修改干预重点初始化页面 */
    public static final String UPDATE_INIT_INTERVENEDISEASE = "/page/master/plan/update_init_intervene_disease.action";

    /** 修改干预重点 */
    public static final String UPDATE_INTERVENEDISEASE = "/page/master/plan/update_intervene_disease.action";

    /** 删除干预重点 */
    public static final String REMOVE_INTERVENEDISEASE = "/page/master/plan/remove_intervene_disease.action";

    /** 代码表信息查询 */
    public static final String QUERY_INTERVENEDISEASE = "/page/master/plan/code_intervene_disease.action";

    /** 验证干预疾病主键是否重复 */
    public static final String INTERVENEDISEASE_CODE_VALIDATE = "/page/master/plan/intervenedisease_code_validate.action";

    /********************************** 干预重点模块 **********************************/
    /********************************** 膳食结构模块 **********************************/
    /** 增加膳食结构初始化页面 */
    public static final String ADD_INIT_FOODSTRUCTURE = "/page/master/plan/add_init_food_structure.action";

    /** 增加膳食结构 */
    public static final String ADD_FOODSTRUCTURE = "/page/master/plan/add_food_structure.action";

    /** 修改膳食结构初始化页面 */
    public static final String UPDATE_INIT_FOODSTRUCTURE = "/page/master/plan/update_init_food_structure.action";

    /** 修改膳食结构 */
    public static final String UPDATE_FOODSTRUCTURE = "/page/master/plan/update_food_structure.action";

    /** 删除膳食结构 */
    public static final String REMOVE_FOODSTRUCTURE = "/page/master/plan/remove_food_structure.action";

    /** 膳食结构信息查询 */
    public static final String QUERY_FOODSTRUCTURE = "/page/master/plan/code_food_structure.action";

    /** 编码验证 */
    public static final String FOODSTRUCTURE_CODE_VALIDATE = "/page/master/plan/foodstructure_code_validate.action";

    /********************************** 一周课程配置模块 **********************************/
    /** 页面初始化 */
    public static final String SCHEDULE_VIEW = "/page/system/schedule_view.action";

    /** 查询数据 */
    public static final String SCHEDULE_QUERY = "/page/system/schedule_query.action";

    /** 添加数据 */
    public static final String SCHEDULE_ADD = "/page/system/schedule_add.action";

    /** 修改数据 */
    public static final String SCHEDULE_UPDATE = "/page/system/schedule_update.action";

    /** 删除数据 */
    public static final String SCHEDULE_DELETE = "/page/system/schedule_delete.action";

    /********************************** 一周课程配置模块END **********************************/

    /********************************** 医师出诊排班配置模块 **********************************/
    /** 页面初始化 */
    public static final String DOCTOR_VIEW = "/page/system/doctor_view.action";

    /** MODEL回显 */
    public static final String DOCTOR_MODEL_VIEW = "/page/system/doctor_model_view.action";

    /** 编辑 */
    public static final String DOCTOR_ADD = "/page/system/doctor_add.action";

    /** 查询 */
    public static final String DOCTOR_QUERY = "/page/system/doctor_query.action";

    /********************************** 医师出诊排班配置模块END **********************************/

    /********************************** 转诊医生配置模块 **********************************/
    /** 页面初始化 */
    public static final String REFERRAL_DOCTOR_VIEW = "/page/system/referral_doctor_view.action";

    /** 添加 */
    public static final String REFERRAL_DOCTOR_ADD = "/page/system/referral_doctor_add.action";

    /** 修改 */
    public static final String REFERRAL_DOCTOR_UPDATE = "/page/system/referral_doctor_update.action";

    /** 删除 */
    public static final String REFERRAL_DOCTOR_DELETE = "/page/system/referral_doctor_delete.action";

    /** 验证身份证号是否重复 */
    public static final String REFERRAL_DOCTOR_ICARD_VALIDATE = "/page/system/referral_doctor_icard_validate.action";

    /** 检索转诊医生和其科室 */
    public static final String REFERRAL_DOCTOR_DEPT = "/page/system/referral_doctor_dept.action";

    /********************************** 转诊医生配置模块END **********************************/

    /************************************ 公共方法 *****************************************/
    /** 查询指定表指定字段的去重数据 */
    public static final String QUERY_DISTINCT_STRING = "/page/master/QUERY_DISTINCT_STRING.action";

    /************************************ 类别管理 *****************************************/
    /** 添加类别 */
    public static final String CATALOGINFO_ADD = "/page/master/add_catalog_info.action";

    /** 验证类别名称是否重复 */
    public static final String CATALOGINFO_NAME_VALID = "/page/master/catalog_info_name_valid.action";

    /** 类别修改 */
    public static final String CATALOGINFO_UPDATE = "/page/master/catalog_info_update.action";

    /** 类别删除 */
    public static final String CATALOGINFO_REMOVE = "/page/master/catalog_info_remove.action";

    /** 修改类别排序 */
    public static final String CATALOGINFO_UPDATE_ORDER = "/page/master/catalog_info_update_order.action";
}
