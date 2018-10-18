/*
 * LoginServiceImpl.java	 1.0   2014-12-11
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mnt.preg.system.pojo.LoginUser;

/**
 * 登陆业务
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-11 zy 初版
 */
@Service
public class LoginServiceImpl implements LoginService {

    /**
     * 通过TOKEN获取登陆信息
     * 
     * @param token
     * @return
     */
    @Cacheable(value = "loginCache", key = "#token")
    public LoginUser getLogin(String token) {
        return null;
    }

    /**
     * 通过TOKEN加入登陆信息到缓存
     * 
     * @param loginVo
     * @return
     */
    @CachePut(value = "loginCache", key = "#loginVo.getToken()")
    public LoginUser addLogin(LoginUser loginVo) {
        return loginVo;
    }

    /**
     * 移除失效的缓存通过TOKEN
     * 
     * @param token
     */
    @CacheEvict(value = "loginCache", key = "#token")
    public void removeLogin(String token) {
    }

    /**
     * 清空所有缓存
     * 
     * @param token
     */
    @CacheEvict(value = "loginCache", allEntries = true)
    public void removeAllLogin() {
    }

}
