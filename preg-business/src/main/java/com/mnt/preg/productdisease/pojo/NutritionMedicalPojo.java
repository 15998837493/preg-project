/*
 * NutritionMedicalPojo.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.pojo;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 营养制剂列表
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
public class NutritionMedicalPojo {
    /** 商品编码 */
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productId;

    /** 商品类别 */
    @QueryFieldAnnotation
    private String productCategory;

    /** 产品品名 */
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String productName;

    /** 商品品牌 */
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String productBrand;

    /** 适合人群 */
    @QueryFieldAnnotation
    private String productUser;
    
    /** 能量 */
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer productCalculation;

    /** 商品类别名 */
    private String productCategoryName;

    /** 适用人群（疾病）*/
    private String productUserDisease;
    
    /** 单位 */
    @QueryFieldAnnotation
    private String productUnit;
    
    public Integer getProductCalculation() {
        return productCalculation;
    }
    
    public void setProductCalculation(Integer productCalculation) {
        this.productCalculation = productCalculation;
    }
    
    public String getProductUnit() {
        return productUnit;
    }

    
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
    
    public String getProductUserDisease() {
        return productUserDisease;
    }

    public void setProductUserDisease(String productUserDisease) {
        this.productUserDisease = productUserDisease;
    }

    public String getProductId() {
        return productId;
    }

    
    public void setProductId(String productId) {
        this.productId = productId;
    }

    
    public String getProductCategory() {
        return productCategory;
    }

    
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    
    public String getProductName() {
        return productName;
    }

    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    
    public String getProductBrand() {
        return productBrand;
    }

    
    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    
    public String getProductUser() {
        return productUser;
    }

    
    public void setProductUser(String productUser) {
        this.productUser = productUser;
    }

    
    public String getProductCategoryName() {
        return productCategoryName;
    }

    
    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }
}
