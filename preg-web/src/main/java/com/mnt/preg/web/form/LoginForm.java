/**
 * 
 */

package com.mnt.preg.web.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * 登陆FORM表单参数
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class LoginForm {

    // 登陆账号
    @NotNull(message = "用户名不能为空")
    @Length(min = 5, max = 20, message = "用户名长度必须在5-20之间")
    private String loginCode;

    // 登陆密码
    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String loginPsw;

    // 登陆验证码
    @NotNull(message = "验证码不能为空")
    @Length(min = 4, max = 4, message = "验证码长度必须是4位")
    private String checkCode;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getLoginPsw() {
        return loginPsw;
    }

    public void setLoginPsw(String loginPsw) {
        this.loginPsw = loginPsw;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

}
