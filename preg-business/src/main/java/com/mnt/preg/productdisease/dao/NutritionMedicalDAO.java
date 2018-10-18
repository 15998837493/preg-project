/*
 * NutritionMedicalDAO.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.dao.basic.ProductDAO;
import com.mnt.preg.master.entity.basic.Product;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.productdisease.pojo.NutritionMedicalPojo;
import com.mnt.preg.productdisease.pojo.ProductDiseaseConfigPojo;

/**
 * 营养制剂Dao
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
@Repository
public class NutritionMedicalDAO extends HibernateTemplate {
    
    @Resource
    private ProductDAO productDAO;

    /**
     * 
     * 医嘱路径配置 列表数据查询
     *
     * @author lipeng
     * @param nutritionMedicalPojo
     * @return
     */
    public List<NutritionMedicalPojo> queryNutritionMedical(NutritionMedicalPojo nutritionMedicalPojo) {
        if (nutritionMedicalPojo == null) {
            nutritionMedicalPojo = new NutritionMedicalPojo();
        }
        nutritionMedicalPojo.setProductCalculation(0);
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(nutritionMedicalPojo, "NutritionMedicalPojo");
        String sql = "SELECT " + DaoUtils.getSQLFields(NutritionMedicalPojo.class, "NutritionMedicalPojo")
                + "   , catalog.catalog_name AS productCategoryName "
                + "   , disease.disease_name AS productUserDisease"
                + "   FROM mas_product AS NutritionMedicalPojo "
                + "   LEFT JOIN mas_catalog catalog ON NutritionMedicalPojo.product_category = catalog.catalog_id "
                + "   LEFT JOIN "
                + "        (SELECT product_disease_config.product_id, GROUP_CONCAT(intervene_disease.disease_id) AS disease_id, GROUP_CONCAT(intervene_disease.disease_name) AS disease_name FROM mas_product_disease_config product_disease_config, mas_intervene_disease intervene_disease "
                + "           WHERE product_disease_config.disease_id = intervene_disease.disease_id GROUP BY product_disease_config.product_id"
                + "         ) disease "
                + "   ON NutritionMedicalPojo.product_id = disease.product_id "
                + queryCondition.getQueryString();
        if(nutritionMedicalPojo.getProductUserDisease() != null && nutritionMedicalPojo.getProductUserDisease().length() > 0) {
            String productDisease[] = nutritionMedicalPojo.getProductUserDisease().split(",");
            for(String diseaseId : productDisease) {
                sql += " AND disease.disease_id LIKE '%" + diseaseId + "%'";
            }
        }
        System.out.println(sql);
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), NutritionMedicalPojo.class);
    }

    /**
     * 
     * 医嘱路径配置 列表页面删除
     *
     * @author lipeng
     * @param productId
     * @param productDiseaseId
     */
    public void removeNutritionMedical(String productId, String productDiseaseId) {
        String sql = "DELETE FROM mas_product_disease_config WHERE product_id = :productId ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        if(productDiseaseId != null && productDiseaseId.length() > 0) {
            sql += " AND product_disease_id = :productDiseaseId ";
            params.put("productDiseaseId", productDiseaseId);
        }
        this.executeSQL(sql, params);
    }
    
    /**
     * 
     * 医嘱路径配置 配置页面列表数据查询
     *
     * @author lipeng
     * @param productId
     * @return
     */
    public List<ProductDiseaseConfigPojo> queryProductDiseaseConfig(String productId){
        String sql = "SELECT " + DaoUtils.getSQLFields(ProductDiseaseConfigPojo.class, "ProductDiseaseConfigPojo")
                  +  "  , CASE WHEN disease.disease_name IS NULL THEN '' ELSE disease.disease_name END AS productUserDisease "
                  +  "FROM mas_product_disease_config ProductDiseaseConfigPojo "
                  +  "LEFT JOIN mas_intervene_disease disease "
                  +  "  ON ProductDiseaseConfigPojo.disease_id = disease.disease_id "
                  +  "WHERE "
                  +  "  ProductDiseaseConfigPojo.product_id = :productId "
                  +  "ORDER BY ProductDiseaseConfigPojo.create_time ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        List<ProductDiseaseConfigPojo> result = this.SQLQueryAlias(sql, params, ProductDiseaseConfigPojo.class);
        ProductPojo product = productDAO.getTransformerBean(productId, Product.class,
                ProductPojo.class);
        for(int i = 0; i < result.size(); i++) {
            result.get(i).setProductUnit(product.getProductUnit());
        }
        return result;
    }
}
