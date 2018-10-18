/*
 * ProductDiseasePageName.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 医嘱路径页面名称
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
@FrontCache(space = "ProductDiseasePage", type = "jsp")
public class ProductDiseasePageName {
    /** 营养制剂 */
    public static final String NUTRITION_MEDICAL_VIEW = "/productdisease/nutrition_medical_view";
    /** 执行清单 */
    public static final String EXECUTION_VIEW = "/productdisease/execution_view";
}
