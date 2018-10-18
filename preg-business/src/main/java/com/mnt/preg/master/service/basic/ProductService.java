
package com.mnt.preg.master.service.basic;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.entity.basic.NutrientAmount;
import com.mnt.preg.master.entity.basic.Product;
import com.mnt.preg.master.pojo.basic.ElementPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;

/**
 * 
 * 商品表接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Validated
public interface ProductService {

    /**
     * 商品记录查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>商品记录查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    List<ProductPojo> queryProduct(ProductCondition condition);

    /**
     * 条件查询商品列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ProductPojo> queryProductByCondition(ProductCondition condition);

    /**
     * 条件查询商品列表
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ProductPojo> queryProductAndElementsByCondition(ProductCondition condition);

    /**
     * 
     * 增加商品记录
     * 
     * @author gss
     * @param product
     * @return ProductVo
     */
    ProductPojo addProduct(@Valid @NotNull Product product);

    /**
     * 
     * 修改商品记录
     * 
     * @author gss
     * @param product
     * @param ProductVo
     */
    ProductPojo updateProduct(Product product);

    /**
     * 
     * 根据主键删除商品记录
     * 
     * @author gss
     * @param id
     */
    void removeProduct(@NotBlank String id);

    /**
     * 
     * 根据主键查询商品表
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>根据主键查询商品表</dd>
     * </dl>
     * 
     * @author gss
     * @param id
     *            主键
     */
    ProductPojo getProductById(@NotBlank String id);

    /**
     * 
     * 根据主键查询商品元素明细
     * 
     * @author gss
     * @param productId
     * @return
     */
    public List<NutrientAmountPojo> queryProductElementByProductId(@NotBlank String productId);

    /**
     * 
     * 添加商品元素
     * 
     * @author gss
     * @param productElementVos
     */
    void saveProductElement(List<NutrientAmount> productElements, String productId);

    /**
     * 
     * 根据主键清空商品元素明细
     * 
     * @author gss
     * @param productId
     * @return
     */
    void deleteProductElementByProductId(String productId);

    /**
     * 查询产品元素含量
     * 
     * @author zcq
     * @param productIdList
     * @return
     */
    List<ElementPojo> queryProductElement(List<String> productIdList);

    /**
     * 验证名片名称是否重复
     * 
     * @author xdc
     * @param productName
     * @return
     */
    Integer validProductName(String productName);
}
