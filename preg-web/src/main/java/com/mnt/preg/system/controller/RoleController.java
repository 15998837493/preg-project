
package com.mnt.preg.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.system.entity.Role;
import com.mnt.preg.system.form.RoleForm;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.RightPojo;
import com.mnt.preg.system.pojo.RolePojo;
import com.mnt.preg.system.service.RightService;
import com.mnt.preg.system.service.RoleService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 职位管理Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-30 zcq 初版
 */
@Controller
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @Resource
    private RightService rightService;

    /**
     * 检索职位信息
     * 
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.ROLE_QUERY)
    public @ResponseBody
    WebResult<RolePojo> queryRole(Role condition) {
        WebResult<RolePojo> webResult = new WebResult<RolePojo>();
        webResult.setData(roleService.queryRole(condition));
        return webResult;
    }

    /**
     * 初始化职位添加页面请求
     * 
     * @return
     */
    @RequestMapping(value = SystemMapping.ROLE_INIT_VIEW)
    public String initAddRole(Model model) {
        List<RightPojo> rightList = rightService.queryRight(null);
        model.addAttribute("rightList", rightList);
        return SystemPageName.ROLE_VIEW;
    }

    /**
     * 职位添加请求
     * 
     * @param roleForm
     * @return
     */
    @RequestMapping(value = SystemMapping.ROLE_ADD)
    public @ResponseBody
    WebResult<RolePojo> addRole(RoleForm roleForm) {
        WebResult<RolePojo> webResult = new WebResult<RolePojo>();
        if (CollectionUtils.isEmpty(roleForm.getRightIdList())) {
            webResult.setError("未选择功能权限！");
            return webResult;
        }
        Role role = TransformerUtils.transformerProperties(Role.class, roleForm);
        String roleId = roleService.addRole(role, roleForm.getRightIdList());
        RolePojo rolePojo = roleService.getRole(roleId);
        webResult.setSuc(rolePojo);
        return webResult;
    }

    /**
     * 职位修改初始化请求
     * 
     * @author zcq
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.ROLE_INIT_UPDATE)
    public String initUpdateRole(String roleId, Model model) {
        RolePojo rolePojo = roleService.getRole(roleId);
        List<RightPojo> rightList = rightService.queryRight(null);
        model.addAttribute("rightList", rightList);
        model.addAttribute("currentRightList", NetJsonUtils.listToJsonArray(rolePojo.getRightList()));
        model.addAttribute("role", rolePojo);
        return SystemPageName.ROLE_UPDATE;
    }

    /**
     * 职位更改请求
     * 
     * @author zcq
     * @param roleForm
     * @return
     */
    @RequestMapping(value = SystemMapping.ROLE_UPDATE)
    public @ResponseBody
    WebResult<RolePojo> updateRole(RoleForm roleForm) {
        WebResult<RolePojo> webResult = new WebResult<RolePojo>();
        if (CollectionUtils.isEmpty(roleForm.getRightIdList())) {
            webResult.setError("请先选择权限！");
            return webResult;
        }
        Role role = TransformerUtils.transformerProperties(Role.class, roleForm);
        roleService.updateRole(role, roleForm.getRightIdList());
        RolePojo rolePojo = roleService.getRole(roleForm.getRoleId());
        webResult.setSuc(rolePojo);
        return webResult;
    }

    /**
     * 职位删除
     * 
     * @author zcq
     * @param roleId
     * @return
     */
    @RequestMapping(value = SystemMapping.ROLE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeRole(String roleId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        roleService.removeRole(roleId);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 校验职位名称是否可用
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.ROLE_NAME_CHECK)
    public @ResponseBody
    boolean checkRoleNameValid(String operateType, RoleForm form) {
        boolean flag = true;
        Integer count = roleService.checkRoleNameValid(form.getRoleName());
        if (count > 0) {
            if (!form.getRoleName().equals(form.getRoleNameOld())) {
                flag = false;
            }
        }
        return flag;
    }
}
