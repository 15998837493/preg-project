
package com.mnt.preg.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.HanyuToPinyin;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.form.UserForm;
import com.mnt.preg.system.form.UserPasswordForm;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.mapping.UserMapping;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.pojo.RolePojo;
import com.mnt.preg.system.pojo.UserPojo;
import com.mnt.preg.system.service.RoleService;
import com.mnt.preg.system.service.UserService;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.SessionConstant;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 
 * 人员管理
 * 
 * @author zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-4-27 zhangjing 初版
 */
@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 跳转到人员管理一览页
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = UserMapping.USER_VIEW)
    public String toUserView(Model model) {
        List<RolePojo> roleList = roleService.queryRole(null);// 查询职务列表
        model.addAttribute("roleList", NetJsonUtils.listToJsonArray(roleList));
        return SystemPageName.USER_VIEW;
    }

    /**
     * 查询用户信息
     * 
     * @param request
     * @param condition
     * @param pageCondition
     * @return
     * 
     */
    @RequestMapping(value = UserMapping.USER_QUERY)
    public @ResponseBody
    WebResult<UserPojo> queryUser(User condition) {
        WebResult<UserPojo> webResult = new WebResult<UserPojo>();
        webResult.setData(userService.queryUsersAll(condition));
        return webResult;
    }

    /**
     * 验证帐户ID是否存在
     * 
     * @param userCode
     *            用户编码
     * @return boolean
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.USER_VALIDATE_CODE)
    public boolean validUserCode(String userCode, String operateType) {
        boolean flag = true;
        if ("add".equals(operateType)) {
            UserPojo userPojo = userService.getUserByUserCode(userCode);
            if (userPojo != null) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 增加账户操作请求
     * 
     * @param UserForm
     *            用户表单
     * @param model
     * @return WebResult
     */
    @RequestMapping(value = UserMapping.USER_ADD)
    public @ResponseBody
    WebResult<UserPojo> addUser(UserPojo userPojo) {
        WebResult<UserPojo> webResult = new WebResult<UserPojo>();
        try {
            String headPic = userPojo.getUserHeadSculpture();
            // 设置头像
            if (StringUtils.isNotEmpty(headPic)) {
                userPojo.setUserHeadSculpture(saveImage(userPojo, null));
            }
            // 设置机构
            String userId = userService.addUser(userPojo);
            webResult.setSuc(userService.getUser(userId));
        } catch (Exception e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 保存图片信息
     * 
     * @author zcq
     * @param userBo
     * @throws IOException
     */
    private String saveImage(UserPojo userPojo, String headPicOld) throws IOException {
        String headPic = userPojo.getUserHeadSculpture();
        // 图片名称
        String picName = HanyuToPinyin.getInstance().getFullStringPinYin(userPojo.getUserName()) + "_"
                + userPojo.getUserCode() + "_" + JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_9)
                + headPic.substring(headPic.lastIndexOf("."));
        // 源文件路径
        String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                + headPic.substring(headPic.lastIndexOf("/") + 1);
        // 目标文件路径
        String destPath = publicConfig.getResourceAbsolutePath() + PathConstant.user_head_image;
        // 删除原目录下的图片
        if (StringUtils.isNotEmpty(headPicOld)) {
            FileUtils.deleteQuietly(new File(destPath + headPicOld));
        }
        // 移动新图片到目标文件夹下
        FileUtils.moveFile(new File(fromPath), new File(destPath + picName));

        return picName;
    }

    /**
     * 我的资料
     * 
     * @author zcq
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = UserMapping.USER_MY_INFO)
    public String myInfo(String userId, Model model) {
        UserPojo userPojo = userService.getUser(userId);
        List<RolePojo> roleList = roleService.queryRole(null);// 查询职务列表
        model.addAttribute("userVo", userPojo);// 用户信息
        model.addAttribute("roleList", roleList);
        return SystemPageName.USER_MYINFO;
    }

    /**
     * 修改我的资料
     * 
     * @author zcq
     * @param userForm
     * @param model
     * @return
     */
    @RequestMapping(value = UserMapping.USER_UPDATE_MYINFO)
    public @ResponseBody
    WebResult<UserPojo> updateMyInfo(UserPojo usrePojo) {
        WebResult<UserPojo> webResult = new WebResult<UserPojo>();
        User user = TransformerUtils.transformerProperties(User.class, usrePojo);
        userService.updateUser(user);
        // 加载当前登陆用户信息
        LoginUser loginUser = this.getLoginUser();
        UserPojo userDto = userService.getUser(usrePojo.getUserId());
        loginUser.setUserPojo(userDto);
        request.getSession().setAttribute(SessionConstant.LOGIN_USER, loginUser);
        loginService.removeLogin((String) request.getSession().getAttribute(SessionConstant.LOGIN_USER_TOKEN));
        loginService.addLogin(loginUser);
        webResult.setSuc(userDto);
        return webResult;
    }

    /**
     * 更新操作
     * 
     * @param HttpServletRequest
     *            请求
     * @param HttpServletResponse
     *            响应
     * @param UserForm
     *            用户提交表单
     * @throws Exception
     */
    @RequestMapping(value = UserMapping.USER_UPDATE)
    public @ResponseBody
    WebResult<UserPojo> updateUser(UserForm userForm) {
        WebResult<UserPojo> webResult = new WebResult<UserPojo>();
        UserPojo userPojo = TransformerUtils.transformerProperties(UserPojo.class, userForm);
        try {
            String headPicOld = userForm.getUserHeadSculptureOld();
            String headPic = userPojo.getUserHeadSculpture();
            // 设置头像
            if (!headPicOld.equals(headPic)) {
                userPojo.setUserHeadSculpture(saveImage(userPojo, headPicOld));
            }
            userService.updateUserPojo(userPojo);
            webResult.setSuc(userService.getUser(userForm.getUserId()));
        } catch (IOException e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 密码重置功能
     * 
     * @param HttpServletRequest
     *            请求
     * @param HttpServletResponse
     *            响应
     * @param userId
     *            用户ID
     * @throws Exception
     */
    @RequestMapping(value = UserMapping.USER_PSW_RESET)
    public @ResponseBody
    WebResult<Boolean> resetUserPwd(@RequestParam String userId, Model model) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        userService.resetUserPwd(userId);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 删除用户
     * 
     * @param userId
     *            用户ID
     * @param model
     * @return WebResult<Boolean>
     */
    // @RequestMapping(value = UserMapping.USER_DEL)
    // public @ResponseBody
    // WebResult<Boolean> delUser(@RequestParam String userId, Model model) {
    // WebResult<Boolean> webResult = new WebResult<Boolean>();
    // userService.removeUser(userId);
    // webResult.setSuc(true, WebMsgConstant.success_message);
    // return webResult;
    // }

    /**
     * 密码修改页初始化请求
     * 
     * @param model
     * @return 跳转页
     */
    @RequestMapping(value = UserMapping.USER_INIT_UPSW)
    public String initUpdUserPwd(Model model) {
        return SystemPageName.USER_PSW_UPDATE;
    }

    /**
     * 密码修改功能
     * 
     * @param UPswUserForm
     *            密码修改表单
     * @param model
     * @return WebResult<Boolean>
     */
    @RequestMapping(value = UserMapping.USER_PSW_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateUserPassword(UserPasswordForm upForm, Model model) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        userService.updateUserPwd(this.getLoginUserId(), upForm.getOldpsw(), upForm.getNewpsw());
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

}
