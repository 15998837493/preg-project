/*
 * SystemPageName.java	 1.0   2015-1-5
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 系统页面名称
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-5 zy 初版
 */
@FrontCache(space = "SystemPage", type = "jsp")
public class SystemPageName {

    /********************************* 权限管理页面路径 ****************************************/
    /** 权限列表页面 */
    public static final String RIGHT_VIEW = "/system/right/right_view";

    /** 权限添加页面 */
    public static final String RIGHT_ADD = "/system/right/right_add";

    /** 权限更改页面 */
    public static final String RIGHT_UPDATE = "/system/right/right_update";

    /********************************* 权限管理页面路径 ****************************************/

    /********************************* 职位管理页面路径 ****************************************/
    /** 职位一览页面 */
    public static final String ROLE_VIEW = "/system/role/role_view";

    /** 职位添加页面 */
    public static final String ROLE_ADD = "/system/role/role_add";

    /** 职位修改页面 */
    public static final String ROLE_UPDATE = "/system/role/role_update";

    /********************************* 职位管理页面路径 ****************************************/

    /********************************* 功能菜单页面路径 ****************************************/
    /** 功能菜单一览页面 */
    public static final String MENU_VIEW = "/system/menu/menu_view";

    /********************************* 功能菜单页面路径 ****************************************/

    /********************************* 组织结构页面路径 ****************************************/
    /** 组织结构一览页面 */
    public static final String DEPT_VIEW = "/system/dept/dept_view";

    /********************************* 组织结构页面路径 ****************************************/

    /********************************* 用户管理页面路径 ****************************************/
    /** 用户一览页面 */
    public static final String USER_VIEW = "/system/user/user_view";

    /** 下级用户添加页面 */
    public static final String USER_ADD = "/system/user/user_add";

    /** 下级用户修改页面 */
    public static final String USER_UPDATE = "/system/user/user_update";

    /** 下级用户修改页面 */
    public static final String USER_MYINFO = "/system/user/myinfo_view";

    /** 用户修改密码页面 */
    public static final String USER_PSW_UPDATE = "/system/user/user_psw_update";

    /** 下级用户修改页面 */
    public static final String WORK_DISEASE_VIEW = "/system/user/work_disease_view";

    /********************************* 用户管理页面路径 ****************************************/

    /********************************* 机构管理管理页面路径 ****************************************/

    /** 机构列表页面 */
    public static final String INS_VIEW = "/system/institution/ins_view";

    /** 机构添加页面 */
    public static final String INS_ADD = "/system/institution/ins_add";

    /** 机构修改页面 */
    public static final String INS_UPDATE = "/system/institution/ins_update";

    /** 机构修改页面 */
    public static final String INS_CONFIG = "/system/institution/ins_config";

    /********************************* 机构管理管理页面路径 ****************************************/

    /********************************* 部门管理页面路径 ****************************************/
    /** 分支一览操作页面 */
    public static final String DEPT_INFO_VIEW = "/system/dept/dept_view";

    /********************************* 部门管理页面路径 ****************************************/

    /********************************* 参数页面路径 ****************************************/
    /** 参数一览页面 */
    public static final String SYSTEM_PARAM_VIEW = "/system/params/param_view";

    /** 参数修改页面 */
    public static final String SYSTEM_PARAM_UPDATE = "/system/params/param_update";

    /********************************* 参数页面路径 ****************************************/

    /** 意见反馈内容详情页 */
    public static final String SYSTEM_FEEDBACK_DETAIL = "/system/feedback_detail";

    /** 检查项目更新页 */
    public static final String EXAM_UPDATE_PAGE = "/system/exam/exam_item_update";

    /** 套餐项目配置页面 */
    public static final String EXAM_GROUP_MANAGE_PAGE = "/system/exam/exam_group_manage";

    /** 体检房间更新页 */
    public static final String EXAM_ROOM_UPDATE_PAGE = "/system/exam/exam_room_update";

    /** 修改广告页面 */
    public static final String AD_UPDATE_PAGE = "/system/ad/ad_edit";

    /** 体检设备更新页 */
    public static final String EXAM_DEVICE_UPDATE_PAGE = "/system/exam/exam_device_update";

    /********************************* 消息公告页面路径 ****************************************/
    /** 消息公告页 */
    public static final String SYSTEM_NOTICE = "/system/notice/notice_view";

    /** 消息公告详情页 */
    public static final String SYSTEM_NOTICE_DETAIL = "/system/notice/notice_detail";

    /** 消息公告添加页 */
    public static final String SYSTEM_NOTICE_ADD = "/system/notice/notice_add";

    /** 检查项目(导检)更新页 */
    public static final String EXAM_UPDATE_GUIDE_PAGE = "/system/exam/guide_exam_item_update";

    /** 检查项目(导检)添加页 */
    public static final String EXAM_ADD_GUIDE_PAGE = "/system/exam/guide_exam_item_add";

    /** 体检项目房间配置页面 */
    public static final String EXAM_ITEM_ROOM_MANAGE_PAGE = "/system/exam/guide_item_room_manage";

    /********************************* 体检项目字典页面路径 ****************************************/

    /** 体检项目字典表信息修改 */
    public static final String ITEM_UDATE = "/system/exam/item_update";

    /** 体检项目来源信息修一览页 */
    public static final String ORIGIN_ITEM_VIEW = "/system/exam/origin_item_view";

    /** 体检项目来源信息修改 */
    public static final String ORIGIN_ITEM_ADD = "/system/exam/origin_item_add";

    /** 体检项目来源信息修改 */
    public static final String ORIGIN_ITEM_UPDATE = "/system/exam/origin_item_update";

    /********************************* 代码表页面路径 ****************************************/
    /** 代码表一览页面 */
    public static final String CODE_VIEW = "/system/code/code_view";

    /** 代码表添加页面 */
    public static final String CODE_ADD = "/system/code/code_add";

    /** 代码表修改页面 */
    public static final String CODE_UPDATE = "/system/code/code_update";

    /** 科室添加 页面 */
    public static final String DEPARTMENT_INIT_ADD = "/master/department_add";

    /** 科室添加 页面 */
    public static final String DEPARTMENT_INIT_UPDATE = "/master/department_update";

    /********************************* 代码表页面路径 ****************************************/
    /********************************** 干预重点模块 **********************************/
    /** 科室添加 页面 */
    public static final String INTERVENEDISEASE_INIT_ADD = "/master/plan/intervene_disease_add";

    /** 科室添加 页面 */
    public static final String INTERVENEDISEASE_INIT_UPDATE = "/master/plan/intervene_disease_update";

    /********************************** 干预重点模块 **********************************/
    /********************************** 膳食结构模块 **********************************/
    /** 膳食结构添加 页面 */
    public static final String FOODSTRUCTURE_INIT_ADD = "/master/plan/food_structure_add";

    /** 膳食结构添加 页面 */
    public static final String FOODSTRUCTURE_INIT_UPDATE = "/master/plan/food_structure_update";

    /********************************** 膳食结构模块 **********************************/
    /********************************** 一周课程配置模块 **********************************/
    /** 一周课程配置一览 页面 */
    public static final String SCHEDULE_INIT_VIEW = "/system/schedule/schedule_view";
    /********************************** 一周课程配置模块 **********************************/
}
