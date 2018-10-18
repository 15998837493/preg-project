
package com.mnt.preg.master.service.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.master.entity.basic.ProductCatalog;

/**
 * 商品类别service测试类
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-15 xdc 初版
 */
public class ProductCatalogServiceTest extends BaseJunit {

    /** 商品类别id */
    private String id;

    /** 商品类别 */
    private ProductCatalog catalog;

    @Before
    public void setUp() {
        catalog = new ProductCatalog();
        id = "001";
    }

    /**
     * 查询商品类别
     * 
     * @author xdc
     */
    @Test
    public void testQueryProductCatalog() {
        assertNotNull(productCatalogService.queryProductCatalog());
    }

    /**
     * 根据id获取商品类别
     * 
     * @author xdc
     */
    @Test
    public void testGetProductCatalog() {
        assertNotNull(productCatalogService.getProductCatalog(id));
    }

    /**
     * 验证商品类别id
     * 
     * @author xdc
     */
    @Test
    public void testCheckCatalogIdValid() {
        int i = productCatalogService.checkCatalogIdValid(id);
        assertEquals(1, i);
    }

    /**
     * 验证商品类别名称
     * 
     * @author xdc
     */
    @Test
    public void testCheckCatalogNameValid() {
        int i = productCatalogService.checkCatalogNameValid("OTC");
        assertEquals(1, i);
    }

    /**
     * 添加商品了类别
     * 
     * @author xdc
     */
    @Test
    public void testAddProductCatalog() {
        catalog.setCatalogId("999");
        catalog.setCatalogParentId("000");
        catalog.setCatalogName("Junit测试商品类别");
        productCatalogService.addProductCatalog(catalog);
    }

    /**
     * 更新商品了类别
     * 
     * @author xdc
     */
    @Test
    public void testUpdateProductCatalog() {
        catalog.setCatalogId(id);
        productCatalogService.updateProductCatalog(catalog);
    }

    /**
     * 删除商品类别
     * 
     * @author xdc
     */
    @Test
    public void testDeleteProductCatalog() {
        productCatalogService.deleteProductCatalog(id);
    }

    /**
     * 编辑商品类别顺序
     * 
     * @author xdc
     */
    @Test
    public void testEditProductCatalogOrder() {
        List<String> caList = new ArrayList<String>();
        caList.add("001");
        caList.add("002");
        productCatalogService.editProductCatalogOrder(caList);
    }

}
