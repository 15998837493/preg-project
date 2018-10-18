/*
 * UserVo.java	 1.0   2014-12-9
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import java.io.Serializable;

/**
 * 登录信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-2 zcq 初版
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 3852713837060956775L;

    /** 用户信息 */
    private UserPojo userPojo;

    /** 登录令牌 */
    private String token;

    public UserPojo getUserPojo() {
        return userPojo;
    }

    public void setUserPojo(UserPojo userPojo) {
        this.userPojo = userPojo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
