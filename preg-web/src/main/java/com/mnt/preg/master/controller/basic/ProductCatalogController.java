
package com.mnt.preg.master.controller.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.entity.basic.Product;
import com.mnt.preg.master.entity.basic.ProductCatalog;
import com.mnt.preg.master.form.basic.ProductCatalogForm;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.basic.ProductCatalogPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 商品类别Controller
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 gss 初版
 */
@Controller
public class ProductCatalogController extends BaseController {

    /**
     * 跳转到类别和商品维护页面
     * 
     * @author gss
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_AND_CATALOG_QUERY)
    public String toProductAndCategoryView(Model model) {
        List<ZTree<ProductCatalogPojo>> treeList = new ArrayList<ZTree<ProductCatalogPojo>>();
        treeList.add(new ZTree<ProductCatalogPojo>("000", "产品类别", true, "home"));
        List<ProductCatalogPojo> menuList = productCatalogService.getProductCatalog_returnlist(null);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        // 不正常可选择的商品分类
        ZTree<ProductCatalogPojo> treeLast = new ZTree<ProductCatalogPojo>();
        treeLast.setId("000000000");
        treeLast.setpId("000");
        treeLast.setName("未分类商品");
        treeLast.setIconSkin("menu");
        treeList.add(treeLast);
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));

        model.addAttribute("elementList", NetJsonUtils.listToJsonArray(elementService.queryNutrient(null)));
        return MasterPageName.PRODUCT_AND_CATEGORY_VIEW;
    }

    /**
     * 异步获取商品类别
     * 
     * @author xdc
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_CATALOG_QUERY_ALL)
    @ResponseBody
    public WebResult<Boolean> getProductCatalogList() {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<ProductCatalogPojo> menuList = productCatalogService.getProductCatalog_returnlist(null);
        List<ProductCatalogPojo> cataList = getMenuTreeList(menuList);
        List<ZTree<ProductCatalogPojo>> gradingList = new ArrayList<ZTree<ProductCatalogPojo>>();
        if (CollectionUtils.isNotEmpty(cataList)) {
            gradingList.addAll(this.getDDLTrees(cataList));
        }
        webResult.setData(gradingList);
        return webResult;
    }

    /**
     * 把所有菜单按父子级分类
     * 
     * @author zcq
     * @return
     */
    private List<ProductCatalogPojo> getMenuTreeList(List<ProductCatalogPojo> menuList) {
        // 把所有菜单按父子级分类
        Map<String, List<ProductCatalogPojo>> menuTreeMap = new LinkedHashMap<String, List<ProductCatalogPojo>>();
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (ProductCatalogPojo menu : menuList) {
                String parentCode = menu.getCatalogParentId();
                if (!menuTreeMap.containsKey(parentCode)) {
                    menuTreeMap.put(parentCode, new ArrayList<ProductCatalogPojo>());
                }
                menuTreeMap.get(parentCode).add(menu);
            }
        }
        // 获取一级目录
        List<ProductCatalogPojo> oneMenuList = menuTreeMap.get("000");
        // 逐级填充完善菜单信息
        if (CollectionUtils.isNotEmpty(oneMenuList)) {
            // 递归填充childList
            for (ProductCatalogPojo oneMenu : oneMenuList) {
                menuRecursion(oneMenu, menuTreeMap);
            }
            // 去除没有子级的目录
            checkMenu(oneMenuList);
        }
        return oneMenuList;
    }

    /**
     * 递归填充childList
     * 
     * @author zcq
     * @param menu
     * @param menuMap
     */
    private void menuRecursion(ProductCatalogPojo menu, Map<String, List<ProductCatalogPojo>> menuMap) {
        List<ProductCatalogPojo> menuList = menuMap.get(menu.getCatalogId());
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (ProductCatalogPojo nextGradeMenu : menuList) {
                menuRecursion(nextGradeMenu, menuMap);
            }
            menu.setChildList(menuList);
        }
    }

    /**
     * 去除没有子级的目录
     * 
     * @author zcq
     * @param menuList
     */
    private void checkMenu(List<ProductCatalogPojo> menuList) {
        if (CollectionUtils.isNotEmpty(menuList)) {
            List<ProductCatalogPojo> tempList = new ArrayList<ProductCatalogPojo>();
            tempList.addAll(menuList);
            for (ProductCatalogPojo menu : menuList) {
                List<ProductCatalogPojo> childList = menu.getChildList();
                checkMenu(childList);
            }
            menuList.removeAll(menuList);
            menuList.addAll(tempList);
        }
    }

    /**
     * 跳转到功能菜单页面
     * 
     * @author gss
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_CATALOG_QUERY)
    public String toMenuView(Model model) {
        List<ZTree<ProductCatalogPojo>> treeList = new ArrayList<ZTree<ProductCatalogPojo>>();
        treeList.add(new ZTree<ProductCatalogPojo>("000", "产品类别", true, "home"));
        List<ProductCatalogPojo> menuList = productCatalogService.getProductCatalog_returnlist(null);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        model.addAttribute("elementList", NetJsonUtils.listToJsonArray(elementService.queryNutrient(null)));
        return MasterPageName.PRODUCT_CATEGORY_VIEW;
    }

    private List<ZTree<ProductCatalogPojo>> getDDLTrees(List<ProductCatalogPojo> menuList) {
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
                List<ZTree<ProductCatalogPojo>> childTreeList = this.getDDLTrees(productCatalog.getChildList());
                tree.setChildList(childTreeList);
            }
        }
        return treeList;
    }

    /**
     * 获取树信息
     * 
     * @author gss
     * @param model
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
     * 获取功能菜单信息
     * 
     * @author gss
     * @param menuId
     * @return
     */
    @RequestMapping(value = MasterMapping.CATALOG_INFO_GET)
    public @ResponseBody
    WebResult<ProductCatalogPojo> getMenu(String menuId) {
        WebResult<ProductCatalogPojo> webResult = new WebResult<ProductCatalogPojo>();
        webResult.setSuc(productCatalogService.getProductCatalog(menuId));
        return webResult;
    }

    /**
     * 添加功能菜单
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_CATALOG_ADD)
    public @ResponseBody
    WebResult<ZTree<ProductCatalogPojo>> addMenu(ProductCatalogForm form) {
        WebResult<ZTree<ProductCatalogPojo>> webResult = new WebResult<ZTree<ProductCatalogPojo>>();
        if (!"000".equals(form.getCatalogParentId())) {
            String catalogId = form.getCatalogParentId();
            // 该类别下所有的商品的类别清空
            ProductCondition pConditon = new ProductCondition();
            pConditon.setProductCategory(catalogId);
            List<ProductPojo> productList = productService.queryProduct(pConditon);
            if (CollectionUtils.isNotEmpty(productList)) {
                for (ProductPojo productPojo : productList) {
                    productPojo.setProductCategory(" ");
                    Product product = TransformerUtils.transformerProperties(Product.class, productPojo);
                    productService.updateProduct(product);
                }
            }
        }
        ProductCatalog productCatalogBo = TransformerUtils.transformerProperties(ProductCatalog.class, form);
        ProductCatalogPojo productCatalogDto = productCatalogService.addProductCatalog(productCatalogBo);
        ZTree<ProductCatalogPojo> tree = new ZTree<ProductCatalogPojo>();
        tree.setId(productCatalogDto.getCatalogId());
        tree.setpId(productCatalogDto.getCatalogParentId());
        tree.setName(productCatalogDto.getCatalogName());
        tree.setValue(productCatalogDto);
        tree.setIconSkin("menu");
        webResult.setSuc(tree);
        return webResult;
    }

    /**
     * 修改功能菜单
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_CATALOG_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateMenu(ProductCatalogForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        ProductCatalog productCatalogBo = TransformerUtils.transformerProperties(ProductCatalog.class, form);
        productCatalogService.updateProductCatalog(productCatalogBo);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 修改功能菜单排序
     * 
     * @author gss
     * @param menuIdList
     * @return
     */
    @RequestMapping(value = MasterMapping.CATALOG_ORDER_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateMenuOrder(String menuIds) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        productCatalogService.editProductCatalogOrder(Arrays.asList(menuIds.split(",")));
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 删除功能菜单
     * 
     * @author gss
     * @param menuId
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_CATALOG_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeMenu(String menuId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 该类别下所有的商品的类别清空
        ProductCondition pConditon = new ProductCondition();
        pConditon.setProductCategoryLike(menuId);
        List<ProductPojo> productList = productService.queryProduct(pConditon);
        if (CollectionUtils.isNotEmpty(productList)) {
            for (ProductPojo productPojo : productList) {
                productPojo.setProductCategory(" ");
                Product product = TransformerUtils.transformerProperties(Product.class, productPojo);
                productService.updateProduct(product);
            }
        }
        productCatalogService.deleteProductCatalog(menuId);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 验证商品名称不可以重复
     * 
     * @author xdc
     * @param catalogName
     * @param catalogNameOld
     * @param opernerCatalog
     * @return
     */
    @RequestMapping(value = MasterMapping.CATALOG_NAME_VALID)
    public @ResponseBody
    boolean checkMenuNameValid(String catalogName, String catalogNameOld, String opernerCatalog) {
        if ("update".equals(opernerCatalog) && catalogName.equals(catalogNameOld)) {
            return true;
        } else {
            int count = productCatalogService.checkCatalogNameValid(catalogName);
            if (count == 0) {
                return true;
            }
        }
        return false;
    }
}
