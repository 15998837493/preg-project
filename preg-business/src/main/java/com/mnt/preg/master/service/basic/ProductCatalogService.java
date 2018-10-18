
package com.mnt.preg.master.service.basic;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.entity.basic.ProductCatalog;
import com.mnt.preg.master.pojo.basic.ProductCatalogPojo;

/**
 * 商品类别事务
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 gss 初版
 */
@Validated
public interface ProductCatalogService {

    /**
     * 查询商品类别
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询商品类别</dd>
     * </dl>
     * 
     * @author gss
     * @return 商品类别
     */
    List<ProductCatalogPojo> queryProductCatalog();

    /**
     * 获取商品类别
     * 
     * @author gss
     * @param catalogId
     * @return
     */
    ProductCatalogPojo getProductCatalog(String catalogId);

    /**
     * 校验菜单主键是否可用
     * 
     * @author gss
     * @param catalogId
     *            主键
     * @return Integer
     */
    Integer checkCatalogIdValid(String catalogId);

    /**
     * 校验菜单名称是否可用
     * 
     * @author gss
     * @param productCatalogName
     *            主键
     * @return Integer
     */
    Integer checkCatalogNameValid(String catalogName);

    /**
     * 添加商品类别
     * 
     * @author gss
     * @param productCatalog
     * @return
     */
    ProductCatalogPojo addProductCatalog(@Valid @NotNull ProductCatalog productCatalog);

    /**
     * 添加商品类别2(后添加，替代business层方法,用来获取catalogId)
     * 
     * @author gss
     * @param productCatalog
     * @return
     */
    String addProductCatalog_help(ProductCatalog catalog);

    /**
     * 修改商品类别
     * 
     * @author gss
     * @param productCatalog
     */
    void updateProductCatalog(ProductCatalog productCatalog);

    /**
     * 删除商品类别
     * 
     * @author gss
     * @param productCatalog
     */
    void deleteProductCatalog(@NotBlank String catalogId);

    /**
     * 修改商品类别排序
     * 
     * @author gss
     * @param productCatalogIdList
     */
    void editProductCatalogOrder(List<String> catalogIdList);

    /**
     * 获取目录结构
     * 
     * @author gss
     * @return 目录结构
     */
    List<ProductCatalogPojo> getProductCatalog_returnlist(ProductCatalog condition);

}
