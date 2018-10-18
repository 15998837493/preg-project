
package com.mnt.preg.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.form.CodeForm;
import com.mnt.preg.system.mapping.BaseDataMapping;
import com.mnt.preg.system.mapping.SystemMapping;
import com.mnt.preg.system.mapping.SystemPageName;
import com.mnt.preg.system.pojo.ChinaPojo;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.service.CodeService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.mapping.CommonPageName;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 代码表Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-11 zcq 初版
 */
@Controller
public class CodeController extends BaseController {

    @Resource
    private CodeService codeService;

    /**
     * 检索代码表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_QUERY)
    public @ResponseBody
    WebResult<Boolean> queryCode(CodeInfo codeInfo) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        webResult.setData(codeService.queryCode(codeInfo));
        return webResult;
    }

    /**
     * 检索代码表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_VIEW_QUERY)
    public @ResponseBody
    WebResult<List<CodePojo>> queryCodeView(CodeInfo condition) {
        WebResult<List<CodePojo>> webResult = new WebResult<List<CodePojo>>();
        webResult.setData(codeService.queryCodeView(condition));
        return webResult;
    }

    /**
     * 跳转到代码表编辑页面
     * 
     * @author zcq
     * @param parentCodeKind
     * @param parentCodeValue
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_UPDATE_VIEW)
    public String toCodeUpdate(String codeKind, Model model) {
        List<ZTree<CodePojo>> treeList = new ArrayList<ZTree<CodePojo>>();
        CodeInfo condition = new CodeInfo();
        condition.setCodeKind(codeKind);
        List<CodePojo> codeList = codeService.queryCodeView(condition);
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo code : codeList) {
                if (code.getCodeType() != 3) {
                    treeList.add(getZTree(code));
                }
            }
        }
        if (CollectionUtils.isNotEmpty(treeList) && treeList.size() == 1
                && treeList.get(0).getValue().getCodeType() == 2) {
            model.addAttribute("codeType", 2);
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        return SystemPageName.CODE_UPDATE;
    }

    /**
     * 获取排序树
     * 
     * @author zcq
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_ORDER_TREE)
    public @ResponseBody
    WebResult<JSONArray> getCodeOrderTree(CodeInfo condition) {
        WebResult<JSONArray> webResult = new WebResult<JSONArray>();
        List<ZTree<CodePojo>> treeList = new ArrayList<ZTree<CodePojo>>();
        ZTree<CodePojo> tree = getZTree(codeService.getCode(condition.getCodeId()));
        tree.setIconSkin("mulu");
        treeList.add(tree);
        condition.setCodeId("");
        List<CodePojo> codeList = codeService.queryCodeView(condition);
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo code : codeList) {
                treeList.add(getZTree(code));
            }
        }
        webResult.setSuc(NetJsonUtils.listToJsonArray(treeList));
        return webResult;
    }

    /**
     * 添加代码表
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_ADD)
    public @ResponseBody
    WebResult<CodePojo> addCode(CodeForm form) {
        WebResult<CodePojo> webResult = new WebResult<CodePojo>();
        CodeInfo CodeInfo = TransformerUtils.transformerProperties(CodeInfo.class, form);
        if (CodeInfo.getCodeOrder() == null || StringUtils.isEmpty(CodeInfo.getCodeOrder().toString())) {
            Integer masOrder = codeService.getCodeMaxOrderByParentCodeKind("0000");
            CodeInfo.setCodeOrder(masOrder + 1);
        }
        webResult.setSuc(codeService.addCode(CodeInfo), WebMsgConstant.success_message);
        // 清空缓存中该代码表信息
        codeCache.removeCodeCache(form.getParentCodeKind() + form.getParentCodeValue() + "_list");
        codeCache.removeCodeCache("allCodeMap");
        return webResult;
    }

    /**
     * 修改代码表
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_UPDATE)
    public @ResponseBody
    WebResult<ZTree<CodePojo>> updateCode(CodeForm form) {
        WebResult<ZTree<CodePojo>> webResult = new WebResult<ZTree<CodePojo>>();
        CodeInfo codeInfo = TransformerUtils.transformerProperties(CodeInfo.class, form);
        codeService.updateCode(codeInfo);
        CodePojo codeDto = codeService.getCode(codeInfo.getCodeId());
        webResult.setSuc(getZTree(codeDto));
        // 清空缓存中该代码表信息
        codeCache.removeCodeCache(codeDto.getParentCodeKind() + codeDto.getParentCodeValue() + "_list");
        codeCache.removeCodeCache("allCodeMap");
        return webResult;
    }

    /**
     * 删除代码表
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeCode(String codeId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        codeService.removeCode(codeId);
        // 清空缓存中该代码表信息
        CodePojo codeVo = codeService.getCode(codeId);
        codeCache.removeCodeCache(codeVo.getParentCodeKind() + codeVo.getParentCodeValue() + "_list");
        codeCache.removeCodeCache("allCodeMap");
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 修改代码排序
     * 
     * @author zcq
     * @param deptIds
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_UPDATE_ORDER)
    public @ResponseBody
    WebResult<Boolean> updateCodeOrder(String codeIds) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<String> codeIdList = Arrays.asList(codeIds.split(","));
        codeService.editCodeOrder(codeIdList);
        // 清空缓存中该代码表信息
        CodePojo codeVo = codeService.getCode(codeIdList.get(0));
        codeCache.removeCodeCache(codeVo.getParentCodeKind() + codeVo.getParentCodeValue() + "_list");
        codeCache.removeCodeCache("allCodeMap");
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 校验代码类型是否可用
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_KIND_VALID)
    public @ResponseBody
    boolean checkCodeTypeValid(String codeKind) {
        boolean flag = true;
        CodeInfo condition = new CodeInfo();
        condition.setCodeKind(codeKind);
        Integer count = codeService.checkCodeValid(condition);
        if (count > 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 校验代码名称是否可用
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_NAME_VALID)
    public @ResponseBody
    boolean checkCodeNameValid(CodeForm form, String operateType) {
        boolean flag = true;
        CodeInfo condition = new CodeInfo();
        condition.setCodeKind(form.getCodeKind());
        condition.setCodeName(form.getCodeName());
        Integer count = codeService.checkCodeValid(condition);
        if ("add".equals(operateType) && count > 0) {
            flag = false;
        }
        if ("update".equals(operateType) && !form.getCodeName().equals(form.getCodeNameOld()) && count > 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 校验代码值是否可用
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_VALUE_VALID)
    public @ResponseBody
    boolean checkCodeValueValid(CodeForm form, String operateType) {
        boolean flag = true;
        CodeInfo condition = new CodeInfo();
        condition.setCodeKind(form.getCodeKind());
        condition.setCodeValue(form.getCodeValue());
        Integer count = codeService.checkCodeValid(condition);
        if ("add".equals(operateType) && count > 0) {
            flag = false;
        }
        if ("update".equals(operateType) && !form.getCodeValue().equals(form.getCodeValueOld()) && count > 0) {
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
    private ZTree<CodePojo> getZTree(CodePojo code) {
        ZTree<CodePojo> codeNode = new ZTree<CodePojo>();
        codeNode.setId(code.getCodeKind() + "_" + code.getCodeValue());
        codeNode.setpId(code.getParentCodeKind() + "_" + code.getParentCodeValue());
        codeNode.setName(code.getCodeName());
        codeNode.setValue(code);
        if (code.getCodeType() == 1) {
            codeNode.setIconSkin("mulu");
        } else {
            codeNode.setIconSkin("menu");
        }
        return codeNode;
    }

    // -----------------------------------------代码表缓存-------------------------------------------------

    /**
     * 获取代码表信息
     * 
     * @author zcq
     * @param codeKind
     * @param codeValue
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_CACHE_GET)
    public @ResponseBody
    WebResult<List<CodePojo>> getCodeListCache(String codeKind, String codeValue) {
        WebResult<List<CodePojo>> webResult = new WebResult<List<CodePojo>>();
        webResult.setSuc(codeCache.getCodeListCache(codeKind.toUpperCase(), codeValue.toUpperCase()));
        return webResult;
    }

    /**
     * 获取代码表转码信息
     * 
     * @author zcq
     * @param codeKind
     * @param codeValue
     * @return
     */
    @RequestMapping(value = SystemMapping.CODE_CACHE_ALL)
    public @ResponseBody
    Map<String, CodePojo> getAllCodeMap() {
        return codeCache.getCodeMapCache();
    }

    // -----------------------------------------省市信息--------------------------------------------------

    /**
     * 获取区域信息
     * 
     * @author zcq
     * @param pId
     * @return
     */
    @RequestMapping(value = BaseDataMapping.GET_CHINA_AREA)
    public @ResponseBody
    WebResult<List<ChinaPojo>> getCity(Integer pId, String type) {
        WebResult<List<ChinaPojo>> webResult = new WebResult<List<ChinaPojo>>();
        webResult.setSuc(codeService.queryChina(pId, type));
        return webResult;
    }

    // -----------------------------------------科室管理------------------------------------

    /**
     * 
     * 科室增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.ADD_INIT_DEPARTMENT)
    public String initAddDepartment(HttpServletRequest request, Model model) {
        return SystemPageName.DEPARTMENT_INIT_ADD;
    }

    /**
     * 添加科室信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.ADD_DEPARTMENT)
    public @ResponseBody
    WebResult<Boolean> addDepartMent(CodeForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        CodeInfo CodeInfo = TransformerUtils.transformerProperties(CodeInfo.class, form);
        Integer order = codeService.getCodeMaxOrderByParentCodeKind("DEPARTMENT");
        CodeInfo.setCodeValue(Integer.toString(order + 1));
        CodeInfo.setCodeOrder(order + 1);
        codeService.addCode(CodeInfo);
        webResult.setSuc(true, "成功");
        codeCache.removeCodeCache(form.getParentCodeKind() + form.getParentCodeValue() + "_list");
        codeCache.removeCodeCache("allCodeMap");
        return webResult;
    }

    /**
     * 
     * 科室修改初始化
     * 
     * @param request
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = SystemMapping.UPDATE_INIT_DEPARTMENT)
    public String updateDepartMentInit(HttpServletRequest request, @RequestParam String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            model.addAttribute("error_msg", "主键不能为空");
            return CommonPageName.ERROR_PAGE;
        }
        CodePojo codeDto = new CodePojo();
        codeDto = codeService.getCode(id);
        if (codeDto == null) {
            codeDto = new CodePojo();
        }
        CodeForm codeForm = TransformerUtils.transformerProperties(CodeForm.class, codeDto);
        model.addAttribute("codeForm", codeForm);
        return SystemPageName.DEPARTMENT_INIT_UPDATE;
    }

    /**
     * 修改科室信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = SystemMapping.UPDATE_DEPARTMENT)
    public @ResponseBody
    WebResult<Boolean> updateDepartment(CodeForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        CodeInfo codeInfo = TransformerUtils.transformerProperties(CodeInfo.class, form);
        codeService.updateCode(codeInfo);
        webResult.setSuc(true, "成功");
        codeCache.removeCodeCache(form.getParentCodeKind() + form.getParentCodeValue() + "_list");
        codeCache.removeCodeCache("allCodeMap");
        return webResult;
    }

    /**
     * 
     * 删除科室信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = SystemMapping.REMOVE_DEPARTMENT)
    public @ResponseBody
    WebResult<Boolean> removeDepartment(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        codeService.removeCode(id);
        // 清空缓存中该代码表信息
        CodePojo codeVo = codeService.getCode(id);
        webResult.setSuc(true, "成功");
        codeCache.removeCodeCache(codeVo.getParentCodeKind() + codeVo.getParentCodeValue() + "_list");
        codeCache.removeCodeCache("allCodeMap");
        return webResult;
    }
}
