
package com.mnt.preg.web.controller;

import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;

import com.mnt.inbody.service.InBodyService;
import com.mnt.inbody.service.InBodyServiceImpl;
import com.mnt.preg.customer.service.BirthEndingBabyInfoService;
import com.mnt.preg.customer.service.BirthEndingBaseInfoService;
import com.mnt.preg.customer.service.BirthEndingDischargedService;
import com.mnt.preg.customer.service.BirthEndingService;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.customer.service.FtpService;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.main.utils.PublicConfig;
import com.mnt.preg.master.service.basic.ElementService;
import com.mnt.preg.master.service.basic.FoodService;
import com.mnt.preg.master.service.basic.ProductCatalogService;
import com.mnt.preg.master.service.basic.ProductService;
import com.mnt.preg.master.service.preg.SymptomsService;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.service.CodeService;
import com.mnt.preg.system.service.InstitutionService;
import com.mnt.preg.system.service.LoginService;
import com.mnt.preg.system.service.MenuService;
import com.mnt.preg.system.service.PrintService;
import com.mnt.preg.web.cache.CodeCache;
import com.mnt.preg.web.constants.SessionConstant;
import com.mnt.preg.web.mapping.CommonPageName;

/**
 * 
 * Controller层基础类
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
public class BaseController extends BaseService {

    @Autowired
    public HttpServletRequest request;

    @Resource
    public MessageSource messageSource;

    @Resource
    public PublicConfig publicConfig;

    @Resource
    public CodeCache codeCache;

    @Resource
    public CodeService codeService;

    @Resource
    public LoginService loginService;

    @Resource
    public ElementService elementService;

    @Resource
    public SymptomsService symptomsService;

    @Resource
    public FoodService foodService;

    @Resource
    public ProductService productService;

    @Resource
    public ProductCatalogService productCatalogService;

    @Resource
    public ElementService nutrientService;

    @Resource
    public CustomerService customerService;

    @Resource
    public PregArchivesService pregArchivesService;

    @Resource
    public FtpService ftpService;

    @Resource
    public InstitutionService institutionService;

    @Resource
    public MenuService menuService;

    @Resource
    public PrintService printService;
    
    @Resource
    public BirthEndingService birthEndingService;
    
    @Resource
    public BirthEndingDischargedService dischargedService;
    
    @Resource
    public BirthEndingBabyInfoService birthBabyService;
    
    @Resource
    public BirthEndingBaseInfoService birthBaseService;

    public InBodyService inBodyService = InBodyServiceImpl.intance();

    /**
     * 获取配置文件值
     * 
     * @param key
     *            键
     * @return 值
     */
    public String getConfig(String key) {
        Locale myLocale = Locale.getDefault();// 获得系统默认的国家/语言环境
        return messageSource.getMessage(key, null, myLocale);
    }

    /**
     * 获取后台登陆用户
     * 
     * @param request
     * @return
     */
    public LoginUser getLoginUser() {
        return (LoginUser) request.getSession().getAttribute(SessionConstant.LOGIN_USER);
    }

    /**
     * 获取当前登录用户的编号
     * 
     * @author zcq
     * @return
     */
    public String getLoginUserId() {
        return this.getLoginUser().getUserPojo().getUserId();
    }

    /**
     * 设置查询通用错误返回
     * 
     * @param model
     */
    public String getErrorPage(Model model, String errorMsg) {
        model.addAttribute("errormsg", errorMsg);
        return CommonPageName.ERROR_PAGE;
    }

    /**
     * 
     * 通过返回码取返回信息
     * 
     * @author zy
     * @param resultCode
     *            返回码
     * @return 返回信息
     */
    public String getMessage(String errCode, String... params) {
        Locale myLocale = Locale.getDefault();// 获得系统默认的国家/语言环境
        return messageSource.getMessage("ERROR_" + errCode, params, myLocale);
    }

    /**
     * 代码表转码
     * 
     * @author zcq
     * @param codeKind
     * @param codeValue
     * @return
     */
    public String transCode(String codeKind, String codeValue) {
        CodePojo codeInfo = null;
        Map<String, CodePojo> map = codeCache.getCodeMapCache();
        if (!map.isEmpty()) {
            codeInfo = map.get(codeKind.toUpperCase() + codeValue);
        }
        return codeInfo == null ? "" : codeInfo.getCodeName();
    }
    
}
