/**
 * 
 */

package com.mnt.preg.web.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.Md5;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.system.pojo.InstitutionPojo;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.pojo.UserPojo;
import com.mnt.preg.system.service.InstitutionService;
import com.mnt.preg.system.service.MenuService;
import com.mnt.preg.system.service.UserAssistantService;
import com.mnt.preg.system.service.UserService;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.SessionConstant;
import com.mnt.preg.web.form.LoginForm;
import com.mnt.preg.web.mapping.LoginMapping;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 
 * 登陆Controller
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-15 zy 初版
 */
@Controller
public class LoginController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    @Resource
    private InstitutionService institutionService;

    @Resource
    private UserAssistantService userAssistantService;

    /**
     * 登陆操作请求(AJAX)
     * 
     * @param request
     *            请求
     * @param loginForm
     *            登陆请求参数
     * @return WebResult<Boolean>
     */
    @RequestMapping(value = LoginMapping.USER_LOGIN)
    public @ResponseBody
    WebResult<Boolean> userLogin(@ModelAttribute LoginForm loginForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // String checkCode = loginForm.getCheckCode();// 验证码
        // String randCode = (String) request.getSession().getAttribute(SessionConstant.LOGIN_USER_RAND_CODE);
        // 不区分大小
        // if (null == randCode || "".equals(randCode) || !checkCode.toUpperCase().equals(randCode.toUpperCase())) {
        // // 输入的验证码不正确
        // return webResult.setError("输入的验证码不正确");
        // }
        UserPojo userVo = userService.getUserByUserCode(loginForm.getLoginCode());

        if (userVo == null) {
            return webResult.setError("输入的账号不存在");
        }
        try {
            if (!userVo.getUserPassword().equals(Md5.getMD5Digest(loginForm.getLoginPsw()))) {
                return webResult.setError("用户密码不正确");
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("输入的密码加密失败[" + loginForm.getLoginPsw() + "]", e);
            return webResult.setError("用户密码不正确");
        }

        if ("assistant".equals(userVo.getUserType())) {
            List<UserPojo> assistantList = userAssistantService.queryDoctorOrAssistant(userVo.getUserId());
            if (CollectionUtils.isEmpty(assistantList)) {
                return webResult.setError("该助理未配置医生！");
            }
        }

        LoginUser loginUser = new LoginUser();
        // 设置用户信息
        loginUser.setUserPojo(userVo);
        // 设置用户菜单
        loginUser.setToken(UUID.randomUUID().toString());
        // 添加到缓存
        loginService.addLogin(loginUser);
        // 保存token值
        request.getSession().setAttribute(SessionConstant.LOGIN_USER_TOKEN, loginUser.getToken());
        // 加载当前登陆用户信息
        request.getSession().setAttribute(SessionConstant.LOGIN_USER, loginUser);
        // 设置SESSION超时时间
        request.getSession().setMaxInactiveInterval(7200);
        // 登陆成功
        webResult.setSuc(true);

        return webResult;
    }

    /**
     * 加载主体页面请求
     * 
     * @param request
     *            请求
     * @param model
     * @return 跳转页
     */
    @RequestMapping(value = LoginMapping.MAIN)
    public String main(Model model) {
        LoginUser loginInfo = this.getLoginUser();
        if (loginInfo == null) {
            return timeout(model);
        }
        UserPojo user = this.getLoginUser().getUserPojo();
        InstitutionPojo ins = institutionService.getIns(user.getCreateInsId());
        String insLogoPath = "";
        if (ins != null) {
            insLogoPath = publicConfig.getResourceServerPath() + PathConstant.ins_logo_image + ins.getInsLogo();
        }
        model.addAttribute("insLogoPath", insLogoPath);
        model.addAttribute("insInfo", ins);
        model.addAttribute("loginUser", this.getLoginUser());
        model.addAttribute("menuList", menuService.getCatalogMenuByLoginUser(this.getLoginUser()));
        model.addAttribute("userinfo", NetJsonUtils.objectToJson(user));
        return "/main/main";
    }

    /**
     * 退出系统操作请求
     * 
     * @param request
     *            请求
     * @return ReturnResult
     */
    @RequestMapping(value = LoginMapping.LOGOUT)
    public @ResponseBody
    WebResult<Boolean> logout() {
        WebResult<Boolean> result = new WebResult<Boolean>();
        request.getSession().invalidate();
        result.setSuc(true, "退出平台系统成功");
        return result;
    }

    /**
     * 超时提示页面
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = LoginMapping.TIMEOUT)
    public String timeout(Model model) {
        // Session销毁
        request.getSession().invalidate();
        model.addAttribute("redirect_url", request.getContextPath() + "/dlym.action");
        return "/main/timeout";
    }

}
