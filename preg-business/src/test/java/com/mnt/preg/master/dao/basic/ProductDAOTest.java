
package com.mnt.preg.master.dao.basic;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.mnt.preg.BaseJunit;

/**
 * 产品DAO测试类
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-15 xdc 初版
 */
public class ProductDAOTest extends BaseJunit {

    /**
     * 测试条件检索商品
     * 
     * @author xdc
     */
    @Test
    public void testQueryProduct() {
        assertNotNull(productDAO.queryProduct(null));
    }

    /**
     * 根据id查询商品元素
     * 
     * @author xdc
     */
    @Test
    @Ignore
    public void testQueryProductElementByProductId() {
        assertNotNull(productDAO.queryProductElementByProductId("402880b35fb2d91f015fb2de0d880000"));
    }

    /**
     * 根据id查询商品元素
     * 
     * @author xdc
     */
    @Test
    @Ignore
    public void testDeleteProductElementByProductId() {
        productDAO.deleteProductElementByProductId("402880b35fb2d91f015fb2de0d880000");
    }

    /**
     * 根据商品id查询元素
     * 
     * @author xdc
     */
    @Test
    public void testQueryProductElement() {
        List<String> list = new ArrayList<String>();
        list.add("402880b35fb2d91f015fb2de0d880000");
        assertNotNull(productDAO.queryProductElement(list));
    }
}
