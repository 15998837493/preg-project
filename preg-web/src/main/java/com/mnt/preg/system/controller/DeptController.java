
package com.mnt.preg.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.system.entity.Dept;
import com.mnt.preg.system.form.DeptForm;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.DeptPojo;
import com.mnt.preg.system.service.DeptService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 功能菜单Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 zcq 初版
 */
@Controller
public class DeptController extends BaseController {

    @Resource
    private DeptService deptService;

    /**
     * 跳转到功能菜单页面
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.DEPT_VIEW)
    public String toDeptView(Model model) {
        List<ZTree<DeptPojo>> treeList = new ArrayList<ZTree<DeptPojo>>();
        treeList.add(new ZTree<DeptPojo>("0000", "总部", true, "home"));
        List<DeptPojo> deptList;
        deptList = deptService.queryDept(null);
        if (CollectionUtils.isNotEmpty(deptList)) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (DeptPojo dept : deptList) {
                String deptParent = dept.getDeptParent();
                if (!map.containsKey(deptParent)) {
                    map.put(deptParent, 1);
                } else {
                    map.put(deptParent, (map.get(deptParent) + 1));
                }
            }
            for (DeptPojo dept : deptList) {
                String deptId = dept.getDeptId();
                ZTree<DeptPojo> tree = getZTreeEntity(dept);
                if (map.get(deptId) == null) {
                    tree.setIconSkin("dept");
                } else {
                    tree.setIconSkin("mulu");
                }
                treeList.add(tree);
            }
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        return SystemPageName.DEPT_VIEW;
    }

    /**
     * 获取功能菜单信息
     * 
     * @author zcq
     * @param deptId
     * @return
     */
    @RequestMapping(value = SystemMapping.DEPT_GET)
    public @ResponseBody
    WebResult<DeptPojo> getDept(String deptId) {
        WebResult<DeptPojo> webResult = new WebResult<DeptPojo>();
        DeptPojo deptPojo = deptService.getDept(deptId);
        webResult.setSuc(deptPojo);
        return webResult;
    }

    /**
     * 添加部门
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.DEPT_ADD)
    public @ResponseBody
    WebResult<ZTree<DeptPojo>> addDept(DeptForm form) {
        WebResult<ZTree<DeptPojo>> webResult = new WebResult<ZTree<DeptPojo>>();
        Dept dept = TransformerUtils.transformerProperties(Dept.class, form);
        String deptId = deptService.addDept(dept);
        DeptPojo deptPojo = deptService.getDept(deptId);
        webResult.setSuc(getZTreeEntity(deptPojo));
        return webResult;
    }

    /**
     * 修改功能菜单
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.DEPT_UPDATE)
    public @ResponseBody
    WebResult<ZTree<DeptPojo>> updateDept(DeptForm form) {
        WebResult<ZTree<DeptPojo>> webResult = new WebResult<ZTree<DeptPojo>>();
        Dept dept = TransformerUtils.transformerProperties(Dept.class, form);
        deptService.updateDept(dept);
        webResult.setSuc(getZTreeEntity(deptService.getDept(form.getDeptId())));
        return webResult;
    }

    /**
     * 修改功能菜单排序
     * 
     * @author zcq
     * @param deptIds
     * @return
     */
    @RequestMapping(value = SystemMapping.DEPT_UPDATE_ORDER)
    public @ResponseBody
    WebResult<Boolean> updateDeptOrder(String deptIds) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        deptService.editDeptOrder((Arrays.asList(deptIds.split(","))));
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 删除功能菜单
     * 
     * @author zcq
     * @param deptId
     * @return
     */
    @RequestMapping(value = SystemMapping.DEPT_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeDept(String deptId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        deptService.deleteDept(deptId);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 校验部门名称是否可用
     * 
     * @author zcq
     * @param deptName
     * @param deptNameOld
     * @param operateType
     * @return
     */
    @RequestMapping(value = SystemMapping.DEPT_NAME_VALID)
    public @ResponseBody
    boolean checkDeptNameValid(String deptName, String deptNameOld, String operateType) {
        boolean flag = true;
        int count = deptService.checkDeptNameValid(deptName);
        if ("add".equals(operateType) && count > 0) {
            flag = false;
        }
        if ("update".equals(operateType) && !deptName.equals(deptNameOld) && count > 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 封装成zTree的node
     * 
     * @author zcq
     * @param code
     * @return
     */
    private ZTree<DeptPojo> getZTreeEntity(DeptPojo deptpPojo) {
        ZTree<DeptPojo> codeNode = new ZTree<DeptPojo>();
        codeNode.setId(deptpPojo.getDeptId());
        codeNode.setpId(deptpPojo.getDeptParent());
        codeNode.setName(deptpPojo.getDeptName());
        codeNode.setValue(deptpPojo);
        codeNode.setIconSkin("dept");
        return codeNode;
    }

}
