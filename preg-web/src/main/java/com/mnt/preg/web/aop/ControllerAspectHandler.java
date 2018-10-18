/*
 * ControllerAspectHandler.java    1.0  2015-6-3
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.aop;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.mnt.health.core.exception.ServiceException;

/**
 * Controller-aop
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-3 zy 初版
 */
@Component
@Aspect
public class ControllerAspectHandler {

    private static final String CONTROLLER_POINT_CUT = "execution (* com.mnt.preg.*.controller..*.*(..))";

    @Autowired
    private HttpServletRequest request;

    @Resource
    public MessageSource messageSource;

    /**
     * 调用前通知
     * 
     * @author zy
     * @param jp
     * @throws Throwable
     */
    @Before(value = CONTROLLER_POINT_CUT)
    public void controllerBefore(JoinPoint jp) throws Throwable {
        LoggerFactory.getLogger(jp.getTarget().getClass()).info("【请求url】：【" + request.getRequestURI() + "】");
        Object[] methodParams = jp.getArgs();
        String webMethodName = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        StringBuffer methodParamStr = new StringBuffer("【Controller】调用方法：【" + webMethodName + "】输入参数：");
        if (methodParams != null) {
            for (int i = 0; i < methodParams.length && methodParams[i] != null; i++) {
                String simpleName = methodParams[i].getClass().getSimpleName();
                methodParamStr.append("【" + simpleName + "：" + methodParams[i].toString() + "】");
            }
            LoggerFactory.getLogger(jp.getTarget().getClass()).info(methodParamStr.toString());
        }
    }

    @Around(value = CONTROLLER_POINT_CUT)
    public Object controllerAround(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        try {
            retVal = pjp.proceed();
        } catch (ServiceException e) {
            String errorCode = e.getErrorMessage();
            String errorMessage = getMessageByCode(errorCode);
            LoggerFactory.getLogger(pjp.getTarget().getClass()).error(
                    "调用类=" + pjp.getTarget().getClass().getName() + ",调用接口名="
                            + pjp.getSignature().getName() + ",调用接口返回码=" + errorCode + ",调用接口返回信息=" + errorMessage);
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return retVal;
        // long starttime = System.currentTimeMillis();
        // int resultCode = ResultCode.SUC;
        // Object retVal = null;
        // String resultMsg = "成功";
        // try {
        // retVal = pjp.proceed();
        // return retVal;
        // } finally {
        // // 方法执行时间
        // long processTime = System.currentTimeMillis() - starttime;
        // LoggerFactory.getLogger(pjp.getTarget().getClass()).info(
        // "调用类=" + pjp.getTarget().getClass().getName()
        // + ",调用接口名=" + pjp.getSignature().getName() + ",调用接口返回码=" + resultCode + ",调用接口返回信息="
        // + resultMsg + ",调用接口时间=" + processTime + "ms;");
        // }
    }

    /**
     * 调用返回后通知
     */
    @AfterReturning(value = CONTROLLER_POINT_CUT, returning = "result")
    public void afterReturn(JoinPoint jp, Object result) {
        String methodName = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        LoggerFactory.getLogger(jp.getTarget().getClass()).info(
                "【Controller】调用方法：【" + methodName + "】，方法的返回值：【" + ToStringBuilder.reflectionToString(result) + "】\n");
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
