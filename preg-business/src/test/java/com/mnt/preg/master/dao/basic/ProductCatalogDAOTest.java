
package com.mnt.preg.master.dao.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.mnt.preg.BaseJunit;

/**
 * 商品类别DAO测试类
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-15 xdc 初版
 */
public class ProductCatalogDAOTest extends BaseJunit {

    /**
     * 测试商品类别检索
     * 
     * @author xdc
     */
    @Test
    public void testQueryProductCatalog() {
        assertNotNull(productCatalogDAO.queryProductCatalog(null));
    }

    /**
     * 删除商品类别
     * 
     * @author xdc
     */
    @Test
    public void testDeleteProductCatalog() {
        productCatalogDAO.deleteProductCatalog("001");
    }

    /**
     * 获取最大的子类别id
     * 
     * @author xdc
     */
    @Test
    public void testGetSonMaxProductCatalogId() {
        assertNull(productCatalogDAO.getSonMaxProductCatalogId("001"));
    }

    /**
     * 验证名称重复
     * 
     * @author xdc
     */
    @Test
    public void testCheckProductCatalogNameValid() {
        int i = productCatalogDAO.checkProductCatalogNameValid("OTC");
        assertEquals(1, i);
    }

    /**
     * 更新商品类别排序
     * 
     * @author xdc
     */
    @Test
    public void testUpdateProductCatalogOrder() {
        productCatalogDAO.updateProductCatalogOrder("001", 1);
    }
}
