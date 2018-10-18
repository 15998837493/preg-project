/*
 * SessionConstant.java	 1.0   2014-12-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.constants;

/**
 * SESSION常量KEY
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class SessionConstant {

    // SESSION保存登陆用户
    public final static String LOGIN_USER = "SESSION_LOGIN_USER";

    // 登陆验证码
    public final static String LOGIN_USER_RAND_CODE = "SESSION_LOGIN_USER_RAND_CODE";

    // 登陆WS接口获取的TOKEN
    public final static String LOGIN_USER_TOKEN = "SESSION_LOGIN_USER_TOKEN";

    // 上传文件SESSION使用
    public final static String UPLOAD_FILE = "SESSION_UPLOAD_FILE";

}
