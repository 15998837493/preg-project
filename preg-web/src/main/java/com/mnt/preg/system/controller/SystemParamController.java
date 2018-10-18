
package com.mnt.preg.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.system.entity.SystemParam;
import com.mnt.preg.system.form.SystemParamForm;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.SystemParamPojo;
import com.mnt.preg.system.service.SystemParamService;
import com.mnt.preg.web.anoton.FrontCacheParse;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 系统参数
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-8 zcq 初版
 */
@Controller
public class SystemParamController extends BaseController {

    @Resource
    private SystemParamService systemParamService;

    /**
     * 检索系统参数
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>检索系统参数</dd>
     * </dl>
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.SYSTEM_PARAM_QUERY)
    public @ResponseBody
    WebResult<SystemParamPojo> querySystemParam(SystemParam condition) {
        WebResult<SystemParamPojo> webResult = new WebResult<SystemParamPojo>();
        webResult.setData(systemParamService.querySystemParam(condition));
        return webResult;
    }

    /**
     * 修改信息初始化
     * 
     * @param paramCode
     *            参数编码
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.SYSTEM_PARAM_INIT_UPDATE)
    public @ResponseBody
    WebResult<SystemParamPojo> systemParamInitUpdate(String id) {
        WebResult<SystemParamPojo> webResult = new WebResult<SystemParamPojo>();
        webResult.setSuc(systemParamService.getSystemParam(id));
        return webResult;
    }

    /**
     * 修改信息
     * 
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.SYSTEM_PARAM_UPDATE)
    public String systemParamUpdate(SystemParamForm systemParamForm, Model model) {
        SystemParam systemParamBo = new SystemParam();
        BeanUtils.copyProperties(systemParamForm, systemParamBo);
        systemParamService.updateSystemParam(systemParamBo);
        return SystemPageName.SYSTEM_PARAM_VIEW;
    }

    /**
     * 保存URL到application中
     * 
     * @author zcq
     * @return 保存到缓存中
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = SystemMapping.SYSTEM_URL_CODE)
    public @ResponseBody
    Map<String, Map<String, String>> systemUrlCode() {
        ServletContext application = request.getSession().getServletContext();
        Map<String, Map<String, String>> result = null;
        if (application.getAttribute("URL") == null) {
            // 扫描注解设置缓存
            result = FrontCacheParse.parsing();
            // 资源服务器路径
            String resourceServerPath = publicConfig.getResourceServerPath();
            if (!StringUtils.isEmpty(resourceServerPath)) {
                Map<String, String> serverMap = new HashMap<String, String>();
                serverMap.put("path", resourceServerPath);
                result.put("resource.server", serverMap);
            }
            // 保存结果到application中
            application.setAttribute("URL", result);
        } else {
            result = (Map<String, Map<String, String>>) application.getAttribute("URL");
        }
        return result;
    }

}
