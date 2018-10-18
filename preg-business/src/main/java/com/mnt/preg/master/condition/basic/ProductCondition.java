
package com.mnt.preg.master.condition.basic;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 
 * 商品库表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */

public class ProductCondition implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 商品主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productCode;

    /** 商品类别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String productCategory;

    /** 商品类别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE_AFTER, name = "productCategory")
    private String productCategoryLike;

    /** 商品主键列表 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "productId")
    private List<String> productIdList;

    /** 商品名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String productName;

    /** 商品地区 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productArea;

    /** 是否剂量评估 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String productIsAssess;

    /** 删除标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCategoryLike() {
        return productCategoryLike;
    }

    public void setProductCategoryLike(String productCategoryLike) {
        this.productCategoryLike = productCategoryLike;
    }

    public List<String> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<String> productIdList) {
        this.productIdList = productIdList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public String getProductIsAssess() {
        return productIsAssess;
    }

    public void setProductIsAssess(String productIsAssess) {
        this.productIsAssess = productIsAssess;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
