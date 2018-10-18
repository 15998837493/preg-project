
package com.mnt.preg.master.dao.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.pojo.basic.ElementPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;

/**
 * 商品表DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
@Repository
public class ProductDAO extends HibernateTemplate {

    /**
     * 根据查询条件查询商品表记录
     * 
     * @param condition
     *            查询条件
     * @return List<ItemVo> 商品表信息列表
     */
    public List<ProductPojo> queryProduct(ProductCondition condition) {
        if (condition == null) {
            condition = new ProductCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ProductVo");
        String sql = "SELECT "
                + DaoUtils.getSQLFields(ProductPojo.class, "ProductVo")
                + "       ,productCatalog.catalog_name AS productCategoryName "
                + "   FROM mas_product AS ProductVo "
                + "       LEFT JOIN mas_product_catalog AS productCatalog ON ProductVo.product_category = productCatalog.catalog_id "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ProductPojo.class);
    }

    /**
     * 商品主键查询商品关联元素
     * 
     * @author zcq
     * @param productId
     * @return
     */
    @Cacheable(value = "productElementCache", key = "#productId")
    public List<NutrientAmountPojo> queryProductElementByProductId(String productId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(NutrientAmountPojo.class, "ProductElementVo")
                + "      ,nutrient_name AS nutrientName"
                + "      ,nutrient_unit AS nutrientUnit"
                + "   FROM mas_nutrient_amount AS ProductElementVo"
                + "   JOIN mas_nutrient AS nutrient "
                + "       ON nutrient.nutrient_id=ProductElementVo.nutrient_id"
                + "   WHERE ProductElementVo.corres_id= :productId "
                + "       AND ProductElementVo.flag= :flag "
                + "       AND nutrient.flag= :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("productId", productId);
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, NutrientAmountPojo.class);
    }

    /**
     * 物理删除商品关联元素
     * 
     * @author zcq
     * @param productId
     */
    @CacheEvict(value = "productElementCache", key = "#productId")
    public void deleteProductElementByProductId(String productId) {
        String sql = "DELETE FROM mas_nutrient_amount WHERE corres_id = :productId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        this.executeSQL(sql, params);
    }

    /**
     * 查询产品元素含量
     * 
     * @author zcq
     * @param productIdList
     * @return
     */
    public List<ElementPojo> queryProductElement(List<String> productIdList) {
        if (CollectionUtils.isEmpty(productIdList)) {
            return null;
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(ElementPojo.class, "NutrientVo")
                + "       ,proEle.product_nutrient_dosage AS nutrientDosage"
                // + "       ,product.product_dosage AS productDosage" TODO:暂时删除单次剂量 请传强周知。 ---郭仕帅
                + "       ,product.product_frequency AS productFrequency"
                + "       ,product.product_name AS productName"
                + "       ,product.corres_id AS productId"
                + "   FROM mas_nutrient AS NutrientVo"
                + "       JOIN mas_nutrient_amount AS proEle ON NutrientVo.nutrient_id=proEle.nutrient_id"
                + "           AND proEle.corres_id IN(:productIdList)"
                + "           AND NutrientVo.flag=:flag"
                + "       JOIN mas_product AS product ON proEle.corres_id=product.product_id"
                + "   ORDER BY NutrientVo.nutrient_id ASC";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("productIdList", productIdList);
        queryParams.put("flag", 1);
        return this.SQLQueryAlias(sql, queryParams, ElementPojo.class);
    }
}
