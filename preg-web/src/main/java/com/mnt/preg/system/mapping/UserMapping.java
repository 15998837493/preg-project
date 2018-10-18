
package com.mnt.preg.system.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 
 * 用户请求映射
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-4-26 mnt_zhangjing 初版
 */
@FrontCache(space = "User")
public class UserMapping {

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

}
