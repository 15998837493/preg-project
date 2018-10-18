/*
 * ProductDiseaseConfigPojo.java    1.0  2018年8月3日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.pojo;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 执行清单pojo
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月3日 lipeng 初版
 */
public class ProductDiseaseConfigPojo {
    /** 主键 */
    @QueryFieldAnnotation
    private String productDiseaseId;
    /** 商品id */
    @QueryFieldAnnotation
    private String productId;
    /** 疾病id */
    @QueryFieldAnnotation
    private String diseaseId;
    /** 疾病名称 */
    private String productUserDisease;
    /** 频次 */
    @QueryFieldAnnotation
    private String productDiseaseFrequency;
    /** 执行清单 */
    @QueryFieldAnnotation
    private String productDiseaseExecutionlist;
    
    private String productUnit;
    
    
    public String getProductUnit() {
        return productUnit;
    }

    
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductDiseaseId() {
        return productDiseaseId;
    }
    
    public void setProductDiseaseId(String productDiseaseId) {
        this.productDiseaseId = productDiseaseId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getDiseaseId() {
        return diseaseId;
    }
    
    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }
    
    public String getProductUserDisease() {
        return productUserDisease;
    }
    
    public void setProductUserDisease(String productUserDisease) {
        this.productUserDisease = productUserDisease;
    }
    
    public String getProductDiseaseFrequency() {
        return productDiseaseFrequency;
    }
    
    public void setProductDiseaseFrequency(String productDiseaseFrequency) {
        this.productDiseaseFrequency = productDiseaseFrequency;
    }
    
    public String getProductDiseaseExecutionlist() {
        return productDiseaseExecutionlist;
    }
    
    public void setProductDiseaseExecutionlist(String productDiseaseExecutionlist) {
        this.productDiseaseExecutionlist = productDiseaseExecutionlist;
    }
}
