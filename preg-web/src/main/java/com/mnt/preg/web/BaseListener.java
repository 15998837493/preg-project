/**
 * 
 */

package com.mnt.preg.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mnt.preg.web.utils.BaseCommon;

/**
 * 
 * 监听类
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zy 初版
 */
public class BaseListener implements ServletContextListener {

    private ServletContext servletContext;

    public void contextDestroyed(ServletContextEvent contextEvent) {

    }

    public void contextInitialized(ServletContextEvent contextEvent) {

        servletContext = contextEvent.getServletContext();

        // 以下为页面常用常量，以便维护，初期基本使用即可，先不进行详细设计，预留为后期使用
        BaseCommon baseCommon = new BaseCommon();

        baseCommon.setBasepath(servletContext.getContextPath());

        // 初始化路径属性
        servletContext.setAttribute("common", baseCommon);

    }

}
