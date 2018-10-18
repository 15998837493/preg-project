
package com.mnt.preg.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.preg.system.condition.DoctorAssistantCondition;
import com.mnt.preg.system.entity.UserAssistant;
import com.mnt.preg.system.mapping.AssistantMapping;
import com.mnt.preg.system.pojo.UserAssistantPojo;
import com.mnt.preg.system.service.UserAssistantService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 
 * 医师助理
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：
 *          v1.5 2018-04-08 dhs 初版
 */
@Controller
public class UserAssistantController extends BaseController {

    @Resource
    private UserAssistantService userAssistantService;

    /**
     * 增加助理
     * 
     * @param user
     * @return WebResult
     */
    @RequestMapping(value = AssistantMapping.USER_ADD_ASSISSTANT)
    public @ResponseBody
    WebResult<Boolean> addUserAssisstant(UserAssistant user) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        userAssistantService.addUserAssistant(user);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 增加医生
     * 
     * @param user
     * @return WebResult
     */
    @RequestMapping(value = AssistantMapping.USER_ADD_DOCTOR)
    public @ResponseBody
    WebResult<Boolean> addUserDoctor(UserAssistant user) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        userAssistantService.addUserDoctor(user);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 查询
     * 
     * @param condition
     * @return WebResult
     */
    @RequestMapping(value = AssistantMapping.USER_QUERY)
    public @ResponseBody
    WebResult<List<UserAssistantPojo>> queryUser(DoctorAssistantCondition condition) {
        WebResult<List<UserAssistantPojo>> webResult = new WebResult<List<UserAssistantPojo>>();
        webResult.setData(userAssistantService.queryDoctorByCondition(condition));
        return webResult;
    }

    /**
     * 查询
     * 
     * @param userId
     * @return WebResult
     */
    @RequestMapping(value = AssistantMapping.USER_ASSISTANT_QUERY)
    public @ResponseBody
    WebResult<List<UserAssistantPojo>> queryDoctorOrAssistant(String userId) {
        WebResult<List<UserAssistantPojo>> webResult = new WebResult<List<UserAssistantPojo>>();
        webResult.setData(userAssistantService.queryDoctorOrAssistant(userId));
        return webResult;
    }
}
