/*
 * ProductDiseaseConfig.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 营养制剂配置entity
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
@Entity
@Table(name = "mas_product_disease_config")
public class ProductDiseaseConfig extends MappedEntity{
    
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String productDiseaseId;
    /** 商品id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String productId;
    /** 疾病id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String diseaseId;
    /** 频次 */
    private String productDiseaseFrequency;
    /** 执行清单 */
    private String productDiseaseExecutionlist;
    
    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "product_disease_id")
    public String getProductDiseaseId() {
        return productDiseaseId;
    }
    
    public void setProductDiseaseId(String productDiseaseId) {
        this.productDiseaseId = productDiseaseId;
    }
    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    @Column(name = "disease_id")
    public String getDiseaseId() {
        return diseaseId;
    }
    
    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }
    @Column(name = "product_disease_frequency")
    public String getProductDiseaseFrequency() {
        return productDiseaseFrequency;
    }
    
    public void setProductDiseaseFrequency(String productDiseaseFrequency) {
        this.productDiseaseFrequency = productDiseaseFrequency;
    }
    @Column(name = "product_disease_executionlist")
    public String getProductDiseaseExecutionlist() {
        return productDiseaseExecutionlist;
    }
    
    public void setProductDiseaseExecutionlist(String productDiseaseExecutionlist) {
        this.productDiseaseExecutionlist = productDiseaseExecutionlist;
    }

}
