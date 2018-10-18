
package com.mnt.preg.master.controller.basic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.mnt.health.utils.maths.CreateRandomCode;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.entity.basic.NutrientAmount;
import com.mnt.preg.master.entity.basic.Product;
import com.mnt.preg.master.form.basic.ProductForm;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;
import com.mnt.preg.master.pojo.basic.ProductCatalogPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 商品相关Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-12 zcq 初版
 */
@Controller
public class ProductController extends BaseController {

    /**
     * 跳转到出诊疾病配置页面
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_VIEW)
    public String initProductView(Model model) {
        // 干预疾病信息
        List<ProductCatalogPojo> all = new ArrayList<ProductCatalogPojo>();
        List<ProductCatalogPojo> productCatalogDtos = productCatalogService.queryProductCatalog();
        if (CollectionUtils.isNotEmpty(productCatalogDtos)) {
            for (ProductCatalogPojo productCatalogDto : productCatalogDtos) {
                if (CollectionUtils.isNotEmpty(productCatalogDto.getChildList())) {
                    all.addAll(productCatalogDto.getChildList());
                }
            }
        }

        model.addAttribute("productCatalogs", all);
        return MasterPageName.PRODUCT_VIEW;
    }

    /**
     * 获取树信息
     * 
     * @author zcq
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
     * 
     * 商品增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.ADD_INIT_PRODUCT)
    public String initAddProduct(HttpServletRequest request, Model model, String productType) {
        List<ZTree<ProductCatalogPojo>> treeList = new ArrayList<ZTree<ProductCatalogPojo>>();
        treeList.add(new ZTree<ProductCatalogPojo>("000", "产品类别", true, "home"));
        model.addAttribute("productType", productType);
        // 查询商品类型
        List<ProductCatalogPojo> menuList = productCatalogService.queryProductCatalog();
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }

        // 查询商品
        List<ProductPojo> productDtos = productService.queryProduct(null);
        model.addAttribute("products", NetJsonUtils.listToJsonArray(productDtos));
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        return MasterPageName.PRODUCT_INIT_ADD;
    }

    /**
     * 分页检索商品信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_QUERY)
    public @ResponseBody
    WebResult<ProductPojo> queryProduct(ProductCondition condition) {
        WebResult<ProductPojo> webResult = new WebResult<ProductPojo>();
        webResult.setData(productService.queryProduct(condition));
        return webResult;
    }

    /**
     * 获取商品明细
     * 
     * @author zcq
     * @param productId
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_GET)
    public @ResponseBody
    WebResult<ProductPojo> getProduct(String productId) {
        WebResult<ProductPojo> webResult = new WebResult<ProductPojo>();
        ProductPojo product = productService.getProductById(productId);
        webResult.setSuc(product);
        return webResult;
    }

    /**
     * 添加商品信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_ADD)
    public @ResponseBody
    WebResult<ProductPojo> addProduct(ProductForm form) {
        WebResult<ProductPojo> webResult = new WebResult<ProductPojo>();
        Product productBo = TransformerUtils.transformerProperties(Product.class, form);
        // 图片上传
        String productPic = form.getProductPic();
        // 生成图片名称并移动图片到指定文件夹
        if (StringUtils.isNotEmpty(productPic)) {
            String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                    + productPic.substring(productPic.lastIndexOf("/") + 1);
            String suffix = productPic.substring(productPic.lastIndexOf("."));
            String picName = HanyuToPinyin.getInstance().getFullStringPinYin(form.getProductName());
            String destPath = getProductPic(picName, suffix);
            try {
                FileUtils.moveFile(new File(fromPath), new File(destPath));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            productBo.setProductPic(destPath.substring(destPath.lastIndexOf("/") + 1));// 重新设置图片路径
        }
        // 保存基本信息
        // 设计出售价
        if (StringUtils.isNotEmpty(form.getProductPurchasePricestr())) {
            productBo.setProductPurchasePrice((int) (Double.parseDouble(form
                    .getProductPurchasePricestr()) * 100));
        }
        // 设计进货价
        if (StringUtils.isNotEmpty(form.getProductSellPricestr())) {
            productBo.setProductSellPrice((int) (Double.parseDouble(form
                    .getProductSellPricestr()) * 100));
        }
        ProductPojo productDto = productService.addProduct(productBo);
        // 保存商品的元素信息
        String productId = productDto.getProductId();
        productService.deleteProductElementByProductId(productId);
        productService.saveProductElement(form.getProductElementList(), productId);
        webResult.setSuc(productDto, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 商品修改初始化
     * 
     * @param request
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.UPDATE_INIT_PRODUCT)
    public String updateProductInit(HttpServletRequest request, @RequestParam String id, Model model) {

        List<ZTree<ProductCatalogPojo>> treeList = new ArrayList<ZTree<ProductCatalogPojo>>();
        treeList.add(new ZTree<ProductCatalogPojo>("000", "产品类别", true, "home"));
        List<ProductCatalogPojo> menuList = productCatalogService.queryProductCatalog();
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        ProductPojo productDto = productService.getProductById(id);

        if (productDto != null) {
            ProductForm productForm = TransformerUtils.transformerProperties(ProductForm.class, productDto);
            // 设计出售价
            if (productDto.getProductPurchasePrice() != null) {
                productForm
                        .setProductPurchasePricestr(String.valueOf((double) productDto.getProductPurchasePrice() / 100));
            }
            // 设计进货价
            if (productDto.getProductSellPrice() != null) {
                productForm.setProductSellPricestr(String.valueOf((double) productDto.getProductSellPrice() / 100));
            }

            model.addAttribute("productForm", productForm);
        }
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(treeList));
        // 查询商品
        List<ProductPojo> productDtos = productService.queryProduct(null);
        model.addAttribute("products", NetJsonUtils.listToJsonArray(productDtos));
        return MasterPageName.PRODUCT_INIT_UPDATE;
    }

    /**
     * 修改商品信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_UPDATE)
    public @ResponseBody
    WebResult<ProductPojo> updateProduct(ProductForm form) {
        WebResult<ProductPojo> webResult = new WebResult<ProductPojo>();
        Product productBo = TransformerUtils.transformerProperties(Product.class, form);
        // 处理图片保存
        String productPic = productBo.getProductPic();
        // 图片名称
        ProductPojo productPojo = productService.getProductById(form.getProductId());
        String picName = productPojo.getProductPic();
        // 目标文件路径
        String destPath = publicConfig.getResourceAbsolutePath() + PathConstant.product_image + picName;
        // 若图片为空则新生成图片信息
        if ((StringUtils.isEmpty(picName) || StringUtils.isEmpty(picName.replaceAll("/", "")))
                && StringUtils.isNotEmpty(productPic)) {
            String suffix = productPic.substring(productPic.indexOf("."));
            picName = HanyuToPinyin.getInstance().getFullStringPinYin(form.getProductName());
            destPath = getProductPic(picName, suffix);
        }
        // 覆盖图片
        if (StringUtils.isNotEmpty(productPic) && productPic.indexOf("/") > -1) {
            // 源文件路径
            String fromPath = publicConfig.getResourceAbsolutePath() + PathConstant.template_image_temp
                    + productPic.substring(productPic.lastIndexOf("/") + 1);
            // 删除原目录下的图片
            // 移动新图片到目标文件夹下
            try {
                destPath = destPath.replace(destPath.substring(destPath.indexOf(".")),
                        fromPath.substring(fromPath.indexOf(".")));
                if (new File(fromPath).exists()) {
                    FileUtils.deleteQuietly(new File(destPath));
                    FileUtils.moveFile(new File(fromPath), new File(destPath));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            productBo.setProductPic(destPath.substring(destPath.lastIndexOf("/") + 1));
        }

        // 设计出售价
        if (StringUtils.isNotEmpty(form.getProductPurchasePricestr())) {
            productBo.setProductPurchasePrice((int) (Double.parseDouble(form
                    .getProductPurchasePricestr()) * 100));
        }
        // 设计进货价
        if (StringUtils.isNotEmpty(form.getProductSellPricestr())) {
            productBo.setProductSellPrice((int) (Double.parseDouble(form
                    .getProductSellPricestr()) * 100));
        }
        ProductPojo productDto = productService.updateProduct(productBo);
        productService.deleteProductElementByProductId(productDto.getProductId());
        productService.saveProductElement(form.getProductElementList(), productDto.getProductId());
        webResult.setSuc(productDto, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 删除商品信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.REMOVE_PRODUCT)
    public @ResponseBody
    WebResult<Boolean> removeProduct(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        productService.removeProduct(id);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 商品id是否重复检验
     * 
     * @author gss
     * @param productId
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_PRODUCTCODE_VALID)
    public @ResponseBody
    boolean checkProductCodeValid(String productCode) {
        boolean flag = true;
        ProductCondition condition = new ProductCondition();
        condition.setProductCode(productCode);
        List<ProductPojo> productDtos = productService.queryProduct(condition);
        if (CollectionUtils.isNotEmpty(productDtos)) {
            flag = false;
        }
        return flag;
    }

    /**
     * 
     * 商品元素维护初始化
     * 
     * @param request
     * @param familyId
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_ELEMENT_INIT)
    public String PregnancyCourseUpdateInit(HttpServletRequest request, @RequestParam String id, Model model) {
        ProductPojo productDto = productService.getProductById(id);
        if (productDto != null) {
            model.addAttribute("product", productDto);
        }
        List<NutrientAmountPojo> productElementDto = productService.queryProductElementByProductId(id);

        model.addAttribute("options", productElementDto);
        return MasterPageName.PRODUCT_ELEMENT_CONFIG;
    }

    /**
     * 商品元素维护
     * 
     * @param itemForm
     *            修改表单数据
     * @return
     * 
     */
    @RequestMapping(value = MasterMapping.PRODUCT_ELEMENT_ADD)
    public @ResponseBody
    WebResult<Boolean> updatePregnancyCourse(HttpServletRequest request) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        String optionParams = request.getParameter("optionParams");
        String fromParams = request.getParameter("fromParams");
        @SuppressWarnings("unchecked")
        List<NutrientAmountPojo> elementDtos = NetJsonUtils.jsonArrayToList(optionParams,
        		NutrientAmountPojo.class);
        ProductPojo productDto = (ProductPojo) NetJsonUtils.jsonToObject(fromParams,
                ProductPojo.class);
        List<NutrientAmount> productElements = TransformerUtils.transformerList(NutrientAmount.class, elementDtos);
        productService.saveProductElement(productElements, productDto.getProductId());
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 商品元素明细记录
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.QUERY_DETAIL_PRODUCT_ELEMENT)
    public @ResponseBody
    WebResult<String> queryProductElementData(String productId) {
        WebResult<String> webResult = new WebResult<String>();
        List<NutrientAmountPojo> elementDtos = productService.queryProductElementByProductId(productId);
        webResult.setData(elementDtos);
        ProductPojo productPojo = productService.getProductById(productId);
        webResult.setSuc(publicConfig.getResourceServerPath() + PathConstant.product_image
                + productPojo.getProductPic());
        return webResult;
    }

    /**
     * 检索商品元素列表信息
     * 
     * @author zcq
     * @param productId
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_ELEMENT_QUERY)
    public @ResponseBody
    WebResult<List<NutrientAmountPojo>> queryProductElementByProductId(String productId) {
        WebResult<List<NutrientAmountPojo>> webResult = new WebResult<List<NutrientAmountPojo>>();
        List<NutrientAmountPojo> elementList = productService.queryProductElementByProductId(productId);
        return webResult.setSuc(elementList);
    }

    /**
     * 验证商品名称是否重复
     * 
     * @author xdc
     * @param productName
     * @param productNameOld
     * @param operateType
     * @return
     */
    @RequestMapping(value = MasterMapping.PRODUCT_PRODUCTNAME_VALID)
    public @ResponseBody
    boolean validProductName(String productName, String productNameOld, String operateType) {
        if ("update".equals(operateType) && productName.equals(productNameOld)) {
            return true;
        } else {
            int count = productService.validProductName(productName);
            if (count == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取食谱图片路径信息
     * 
     * @author zcq
     * @param foodName
     * @return
     */
    private String getProductPic(String picName, String suffix) {
        // 目标文件路径
        String destPath = publicConfig.getResourceAbsolutePath() + PathConstant.product_image + picName + suffix;
        File newFile = new File(destPath);
        if (newFile.exists()) {
            destPath = getProductPic(picName + CreateRandomCode.getRandomCode(2), suffix);
        }
        return destPath;
    }
}
