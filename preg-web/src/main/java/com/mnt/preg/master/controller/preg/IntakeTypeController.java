
package com.mnt.preg.master.controller.preg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.entity.preg.IntakeType;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.basic.ProductCatalogPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.master.pojo.preg.IntakeTypePojo;
import com.mnt.preg.master.service.preg.PregIntakeTypeService;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 
 * 摄入类型
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-27 wsy 初版
 */
@Controller
public class IntakeTypeController extends BaseController {

    @Resource
    private PregIntakeTypeService intakeTypeService;

    /**
     * 删除摄入量类型
     * 
     * @author zcq
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_TYPE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeIntakeType(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        intakeTypeService.removeIntakeType(id);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 修改摄入量类型
     * 
     * @author zcq
     * @param intakeDetailBo
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_TYPE_UPDATE)
    public @ResponseBody
    WebResult<IntakeTypePojo> updateIntakeType(IntakeType IntakeType) {
        WebResult<IntakeTypePojo> webResult = new WebResult<IntakeTypePojo>();
        webResult.setSuc(intakeTypeService.updateIntakeType(IntakeType), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 摄入量类型初始化
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_TYPE_INIT)
    public String initAddIntakeType(Model model) {
        List<CodePojo> codes = codeCache.getCodeListCache("INTAKETYPECATEGORY", "INTAKETYPECATEGORY");
        model.addAttribute("types", codes);
        List<ZTree<ProductCatalogPojo>> treeList = new ArrayList<ZTree<ProductCatalogPojo>>();
        // 查询商品类型
        List<ProductCatalogPojo> menuList = productCatalogService.getProductCatalog_returnlist(null);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        return MasterPageName.INTAKE_TYPE_VIEW;
    }

    /**
     * 营养素安全剂量评估--获取商品类别树
     * 
     * @param menuList
     * @return
     */
    private List<ZTree<ProductCatalogPojo>> getTrees(List<ProductCatalogPojo> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        List<ZTree<ProductCatalogPojo>> treeList = new ArrayList<ZTree<ProductCatalogPojo>>();
        for (ProductCatalogPojo productCatalog : menuList) {
            String catalogId = productCatalog.getCatalogId();
            ZTree<ProductCatalogPojo> tree = new ZTree<ProductCatalogPojo>();
            tree.setId(catalogId);
            tree.setpId(productCatalog.getCatalogParentId());
            tree.setName(productCatalog.getCatalogName());
            tree.setValue(productCatalog);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                tree.setIsParent(true);
                tree.setIconSkin("mulu");
            } else {
                tree.setIconSkin("menu");
            }
            treeList.add(tree);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                treeList.addAll(this.getTrees(productCatalog.getChildList()));
            }
        }
        return treeList;
    }

    /**
     * 分页检索摄入量类型
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_TYPE_QUERY)
    public @ResponseBody
    WebResult<IntakeTypePojo> queryIntakeTypeDetail(IntakeType condition) {
        WebResult<IntakeTypePojo> webResult = new WebResult<IntakeTypePojo>();
        webResult.setData(intakeTypeService.queryIntakeTypeByCondition(condition));
        return webResult;
    }

    /**
     * 添加摄入量类型
     * 
     * @author zcq
     * @param intakeDetailBo
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_TYPE_ADD)
    public @ResponseBody
    WebResult<IntakeTypePojo> addIntakeType(IntakeType IntakeType) {
        WebResult<IntakeTypePojo> webResult = new WebResult<IntakeTypePojo>();
        webResult.setSuc(intakeTypeService.addIntakeType(IntakeType), WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 验摄入量类型code重复(编辑)
     * 
     * @author gss
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_TYPE_VALIDATE)
    public @ResponseBody
    boolean queryIntakeTypeByCode(String code) {
        IntakeType condition = new IntakeType();
        condition.setCode(code);
        List<IntakeTypePojo> intakeTypes = intakeTypeService.queryIntakeTypeByCondition(condition);
        if (CollectionUtils.isEmpty(intakeTypes)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 验摄入量类型code重复(添加)
     * 
     * @author gss
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_TYPE_VALIDATE_ADD)
    public @ResponseBody
    boolean queryIntakeTypeByCodeAdd(String code) {
        IntakeType condition = new IntakeType();
        condition.setCode(code);
        List<IntakeTypePojo> intakeTypes = intakeTypeService.queryIntakeTypeByCondition(condition);
        if (CollectionUtils.isEmpty(intakeTypes)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询全部商品
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_QUERY_ALL)
    public @ResponseBody
    WebResult<ProductPojo> queryProduct(ProductCondition condition) {
        WebResult<ProductPojo> webResult = new WebResult<ProductPojo>();
        webResult.setData(productService.queryProduct(null));
        return webResult;
    }
}
