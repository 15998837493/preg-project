/*
 * LoginMapping.java	 1.0   2014-12-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 登陆MAPPING映射路径
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
@FrontCache(space = "Login")
public class LoginMapping {

    /** 跳转登录 */
    public static final String DLYM = "/dlym.action";

    /** 登陆 */
    public static final String USER_LOGIN = "/userLogin.action";

    /** 主页信息加载 */
    public static final String MAIN = "/main.action";

    /** 登出 */
    public static final String LOGOUT = "/logout.action";

    /** 上传 */
    public static final String UPLOAD = "/upload.action";

    /** 超时 */
    public static final String TIMEOUT = "/timeout.action";
}
