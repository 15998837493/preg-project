
package com.mnt.preg.web.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 
 * 异常统一处理
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class ControllerExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionResolver.class);

    @Resource
    public MessageSource messageSource;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        LOGGER.error(ex.getMessage(), ex);// 日志输出

        // 设置返回报文头信息
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        String viewName = determineViewName(ex, request);
        if (viewName != null) {// JSP格式返回
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                    .getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
                    "XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                ModelAndView modelAndView = getModelAndView(viewName, ex, request);
                modelAndView.addObject("error_msg", "错误消息");
                return modelAndView;
            } else {
                // AJAX返回
                try {
                    String errorMessage = "错误消息！";
                    if (ex instanceof ServiceException) {
                        ServiceException e = (ServiceException) ex;
                        WebResult<String> webResult = new WebResult<String>();
                        webResult.setError("错误码：" + e.getErrorMessage() + "<br>"
                                + "<font color='red'>提示：" + getMessageByCode(e.getErrorMessage()) + "</font>");
                        errorMessage = NetJsonUtils.objectToJson(webResult);
                    } else if (StringUtils.isNotEmpty(ex.getMessage())) {
                        errorMessage = ex.getMessage();
                    }
                    PrintWriter writer = response.getWriter();
                    writer.write(errorMessage);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 通过返回码取返回信息
     * 
     * @author zcq
     * @param resultCode
     * @return
     */
    private String getMessageByCode(String resultCode) {
        Locale myLocale = Locale.getDefault();// 获得系统默认的国家/语言环境
        return messageSource.getMessage("ERROR_" + resultCode, null, myLocale);
    }
}
