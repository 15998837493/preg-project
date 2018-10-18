/*
 * ProductDiseaseMapping.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 医嘱路径mapping映射
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
@FrontCache(space = "ProductDisease")
public class ProductDiseaseMapping {
    
    /** 营养制剂医嘱列表页面 */
    public static final String NUTRITION_MEDICAL = "/productdisease/nutrition_medical.action";
    /** 营养制剂医嘱列表查询  */
    public static final String QUERY_NUTRITION_MEDICAL = "/productdisease/query_nutrition_medical.action";
    /** 营养制剂医嘱 删除  */
    public static final String REMOVE_NUTRITION_MEDICAL = "/productdisease/remove_nutrition_medical.action";
    /** 营养制剂医嘱 增加  */
    public static final String ADDORUPDATE_NUTRITION_MEDICAL = "/productdisease/addorupdate_nutrition_medical.action";
    /** 营养制剂医嘱 配置  */
    public static final String CONFIG_NUTRITION_MEDICAL = "/productdisease/config_nutrition_medical.action";
    /** 营养制剂医嘱 配置页列表数据  */
    public static final String QUERY_PRODUCT_DISEASE_CONFIG = "/productdisease/query_product_disease_config.action";
    
    /** 执行清单医嘱列表页面 */
    public static final String EXECUTION = "/productdisease/execution.action";
}
