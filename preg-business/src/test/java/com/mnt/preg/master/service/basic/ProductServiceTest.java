
package com.mnt.preg.master.service.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.preg.BaseJunit;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.entity.basic.NutrientAmount;
import com.mnt.preg.master.entity.basic.Product;

/**
 * 商品Service测试
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-15 xdc 初版
 */
public class ProductServiceTest extends BaseJunit {

    /** 商品主键id */
    private String id;

    /** 商品查询条件 */
    private ProductCondition condition;

    /** 商品 */
    private Product product;

    @Before
    public void setUp() {
        condition = new ProductCondition();
        id = "402880b35fb2d91f015fb2de0d880000";
        product = new Product();
    }

    /**
     * 根据条件查询商品
     * 
     * @author xdc
     */
    @Test
    public void testQueryProduct() {
        assertNotNull(productService.queryProduct(condition));
    }

    /**
     * 根据条件查询商品
     * 
     * @author xdc
     */
    @Test
    public void testQueryProductByCondition() {
        assertNotNull(productService.queryProduct(condition));
    }

    @Test
    @Ignore
    public void testQueryProductAndElementsByCondition() {
        assertNotNull(productService.queryProductAndElementsByCondition(null));
    }

    /**
     * 添加商品
     * 
     * @author xdc
     */
    @Test
    public void testAddProduct() {
        assertNotNull(productService.addProduct(product));
    }

    /**
     * 更新商品信息
     * 
     * @author xdc
     */
    @Test
    public void testUpdateProduct() {
        product.setProductId(id);
        assertNotNull(productService.updateProduct(product));
    }

    @Test
    @Ignore
    public void testRemoveProduct() {
        productService.removeProduct(id);
    }

    /**
     * 根据id获取商品
     * 
     * @author xdc
     */
    @Test
    public void testGetProductById() {
        assertNotNull(productService.getProductById(id));
    }

    @Test
    @Ignore
    public void testQueryProductElementByProductId() {
        assertNotNull(productService.queryProductElementByProductId(id));
    }

    /**
     * 保存商品的元素信息
     * 
     * @author xdc
     */
    @Test(expected = ServiceException.class)
    public void testSaveProductElement() {
        List<NutrientAmount> pEList = new ArrayList<NutrientAmount>();
        productService.saveProductElement(pEList, id);
    }

    @Test
    @Ignore
    public void testDeleteProductElementByProductId() {
        productService.deleteProductElementByProductId(id);
    }

    /**
     * 查询商品的元素信息
     * 
     * @author xdc
     */
    @Test
    public void testQueryProductElement() {
        List<String> list = new ArrayList<String>();
        list.add(id);
        assertNotNull(productService.queryProductElement(list));
    }

    /**
     * 验证商品名称
     * 
     * @author xdc
     */
    @Test
    public void testValidProductName() {
        int i = productService.validProductName("备注测试");
        assertEquals(1, i);
    }
}
