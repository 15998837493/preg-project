/**
 * 
 */

package com.mnt.preg.web;

import java.io.Serializable;

import org.springframework.web.context.WebApplicationContext;

import com.mnt.preg.web.timertask.QuartzCenterJob;

/**
 * 
 * 内存项目信息(当启动项目时，自动初始化)相关信息随运行而变化
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class CacheProjectInfo implements Serializable {

    private static final long serialVersionUID = 6109705473637741197L;

    private static CacheProjectInfo instance;// 单体实例

    private CacheProjectInfo() {
    }

    public static CacheProjectInfo getInstance() {
        if (instance == null) {
            instance = new CacheProjectInfo();
        }
        return instance;
    }

    /** SPRING信息 */
    private WebApplicationContext applicationContext;

    /** 环信服务令牌 */
    private String hxToken = "";

    /** web-service服务令牌 */
    private String token = "";

    /** 注册环信应用编号 */
    private String hxApplication = "";

    /** 环信获取token定时任务执行时间 */
    private String hxtokenTrigger;

    /** 定时任务 */
    private QuartzCenterJob quartzCenterJob;

    public WebApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getHxApplication() {
        return hxApplication;
    }

    public void setHxApplication(String hxApplication) {
        this.hxApplication = hxApplication;
    }

    public String getHxToken() {
        return hxToken;
    }

    public void setHxToken(String hxToken) {
        this.hxToken = hxToken;
    }

    public String getHxtokenTrigger() {
        return hxtokenTrigger;
    }

    public void setHxtokenTrigger(String hxtokenTrigger) {
        this.hxtokenTrigger = hxtokenTrigger;
    }

    public QuartzCenterJob getQuartzCenterJob() {
        return quartzCenterJob;
    }

    public void setQuartzCenterJob(QuartzCenterJob quartzCenterJob) {
        this.quartzCenterJob = quartzCenterJob;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
