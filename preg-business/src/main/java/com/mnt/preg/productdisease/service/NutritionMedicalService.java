/*
 * NutritionMedicalService.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.productdisease.entity.ProductDiseaseConfig;
import com.mnt.preg.productdisease.pojo.NutritionMedicalPojo;
import com.mnt.preg.productdisease.pojo.ProductDiseaseConfigPojo;

/**
 * 营养制剂服务
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
@Validated
public interface NutritionMedicalService {
    /**
     * 
     * 医嘱路径配置 列表数据查询
     *
     * @author lipeng
     * @param nutritionMedicalPojo
     * @return
     */
    List<NutritionMedicalPojo> queryNutritionMedical(NutritionMedicalPojo nutritionMedicalPojo);
    /**
     * 
     * 医嘱路径配置 配置页面列表查询*
     *
     * @author lipeng
     * @param productId
     * @return
     */
    List<ProductDiseaseConfigPojo> queryProductDiseaseConfig(String productId);
    /**
     * 
     * 医嘱路径配置 列表页面删除
     *
     * @author lipeng
     * @param productId
     * @param productDiseaseId
     */
    void removeNutritionMedical(String productId, String productDiseaseId);
    
    /**
     * 
     * 医嘱路径配置 配置页面添加、修改
     *
     * @author lipeng
     * @param pdc
     * @throws ParseException
     */
    String addOrUpdateNutritionMedical(ProductDiseaseConfig pdc) throws ParseException;
}
