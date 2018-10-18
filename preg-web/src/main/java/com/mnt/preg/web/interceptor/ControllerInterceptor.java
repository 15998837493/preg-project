/**
 * 
 */

package com.mnt.preg.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mnt.health.utils.thread.TokenManager;
import com.mnt.preg.web.constants.SessionConstant;

/**
 * 
 * WEB所有请求事件日志记录
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class ControllerInterceptor extends HandlerInterceptorAdapter {

    // 页面TITIL名称
    String titleName = "管理平台";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean bResult = false;
        String token = (String) request.getSession().getAttribute(SessionConstant.LOGIN_USER_TOKEN);
        // LoggerFactory.getLogger("[" + titleName + "]").info("登录用户IP地址：" + request.getRemoteAddr());
        if (StringUtils.isEmpty(token)) {
            // 去上传SESSION
            String sessionid = request.getParameter(SessionConstant.UPLOAD_FILE);
            if (null != sessionid && !"".equals(sessionid)) {// 本项目使用插件按钮为FLASH，其中SESSION需要传递参数
                return true;
            }
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                    .getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
                    "XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                System.out.println("-------------------------timeout.action---------------------------");
                response.sendRedirect(request.getContextPath() + "/timeout.action");
            } else {
                // AJAX返回
                try {
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("timeout");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bResult;
        }
        TokenManager.getCurrHashMap().put(Thread.currentThread(), token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}
