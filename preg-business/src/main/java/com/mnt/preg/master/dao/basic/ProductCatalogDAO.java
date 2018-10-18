
package com.mnt.preg.master.dao.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.entity.basic.ProductCatalog;
import com.mnt.preg.master.pojo.basic.ProductCatalogPojo;

/**
 * 商品类别DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-19 gss 初版
 */
@Repository
public class ProductCatalogDAO extends HibernateTemplate {

    /**
     * 查询商品类别信息
     * 
     * @return 商品类别信息
     */
    public List<ProductCatalogPojo> queryProductCatalog(ProductCatalog condition) {
        if (condition == null) {
            condition = new ProductCatalog();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ProductCatalogPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(ProductCatalogPojo.class, "ProductCatalogPojo")
                + "   FROM mas_product_catalog AS ProductCatalogPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ProductCatalogPojo.class);
    }

    /**
     * 逻辑删除商品类别
     * 
     * @author gss
     * @param catalogId
     */
    public void deleteProductCatalog(String catalogId) {
        String executeSQL = "UPDATE mas_product_catalog SET flag=:flag WHERE catalog_id LIKE:catalogId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("flag", 0);
        queryParams.put("catalogId", catalogId + "%");
        this.executeSQL(executeSQL, queryParams);
    }

    /**
     * 获取子级最大ID
     * 
     * @author gss
     * @param catalogId
     * @return
     */
    public String getSonMaxProductCatalogId(String catalogParent) {
        String sql = "SELECT MAX(catalog_id) FROM mas_product_catalog WHERE catalog_parent_id=:catalogParent";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("catalogParent", catalogParent);
        return this.SQLQueryFirst(sql, queryParams);
    }

    /**
     * 校验商品类别主键
     * 
     * @author gss
     * @param catalogId
     * @return
     */
    public Integer checkProductCatalogNameValid(String catalogName) {
        String countSQL = "SELECT COUNT(catalog_id) FROM mas_product_catalog WHERE catalog_name=:catalogName AND flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("catalogName", catalogName);
        queryParams.put("flag", 1);
        return this.SQLCount(countSQL, queryParams);
    }

    /**
     * 修改商品类别排序
     * 
     * @author gss
     * @param catalogId
     * @param productCatalogOrder
     */
    public void updateProductCatalogOrder(String catalogId, Integer catalogOrder) {
        String executeSQL = "UPDATE mas_product_catalog SET catalog_order=:catalogOrder WHERE catalog_id=:catalogId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("catalogId", catalogId);
        queryParams.put("catalogOrder", catalogOrder);
        this.executeSQL(executeSQL, queryParams);
    }

}
