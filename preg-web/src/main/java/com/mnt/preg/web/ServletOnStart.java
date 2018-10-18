/**
 * 
 */

package com.mnt.preg.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.java_websocket.WebSocketImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mnt.preg.web.cache.CodeCache;
import com.mnt.preg.web.timertask.QuartzCenterJob;
import com.mnt.preg.web.utils.BaseCommon;
import com.mnt.preg.web.utils.WsServer;

/**
 * 
 * Servlet启动初始化
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class ServletOnStart extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletOnStart.class);

    private static final long serialVersionUID = 5494158583746262904L;

    /**
     * 通过web方式，系统初始化，启动相应的服务
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 加载顺序不能改变
        CacheProjectInfo projectInfo = CacheProjectInfo.getInstance();

        // SPRING 信息保存
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(servletConfig.getServletContext());
        projectInfo.setApplicationContext(applicationContext);// 设置SPRING属性
        LOGGER.info("加载Spring配置成功.");

        // 定时任务启动
        QuartzCenterJob centerJob = new QuartzCenterJob();
        centerJob.start();
        projectInfo.setQuartzCenterJob(centerJob);
        LOGGER.info("启动-定时任务加载成功！");

        // 代码表缓存
        CodeCache codeCache = applicationContext.getBean(CodeCache.class);
        codeCache.initCodeCache();
        LOGGER.info("启动-代码表缓存加载成功！");

        // websocket服务启动
        setWebsocketPort();
    }

    /**
     * websocket服务启动
     * 
     * @author zcq
     */
    private void setWebsocketPort() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config-web.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);

            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();

            BaseCommon baseCommon = (BaseCommon) servletContext.getAttribute("common");

            String systemVersion = p.getProperty("system.version");// 系统版本号
            baseCommon.setSystemVersion(systemVersion);

            String websocketPort = p.getProperty("websocket.port");// 服务端口号
            if (StringUtils.isNotBlank(websocketPort)) {
                Pattern pattern = Pattern.compile("^([1-9]\\d*)|0$");
                Matcher isNum = pattern.matcher(websocketPort);
                if (isNum.matches()) {
                    int port = Integer.valueOf(websocketPort); // 端口
                    WebSocketImpl.DEBUG = false;
                    WsServer s = new WsServer(port);
                    s.start();
                    baseCommon.setWebsocketPort(port);
                    LOGGER.info("启动websocket服务！端口号：【" + port + "】");
                } else {
                    LOGGER.error("启动websocket服务失败！端口号：【" + websocketPort + "】");
                }
            } else {
                LOGGER.error("websocket服务启动失败！端口号为空！");
            }

            servletContext.setAttribute("common", baseCommon);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
