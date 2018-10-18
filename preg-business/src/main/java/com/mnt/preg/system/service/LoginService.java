/*
 * LoginService.java	 1.0   2015-1-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import com.mnt.preg.system.pojo.LoginUser;

/**
 * 登陆业务
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zy 初版
 */
public interface LoginService {

    /**
     * 通过TOKEN获取登陆信息
     * 
     * @param token
     * @return
     */
    public LoginUser getLogin(String token);

    /**
     * 通过TOKEN加入登陆信息到缓存
     * 
     * @param loginVo
     * @return
     */
    public LoginUser addLogin(LoginUser loginUser);

    /**
     * 移除失效的缓存通过TOKEN
     * 
     * @param token
     */
    public void removeLogin(String token);

    /**
     * 清空所有缓存
     * 
     * @param token
     */
    public void removeAllLogin();
}
