/*
 * DietFoodmapping.java    1.0  2017-6-30
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 系统管理模块 操作mapping映射(根据模块分)
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-30 scd 初版
 */
@FrontCache(space = "DietFood")
public class DietFoodMapping {

    /********************************* 膳食评估 ************************************/
    public final static String INIT_MEALS_CUST_SURVEY = "/page/cust/meals_cust_survey_init.action";// 初始化会员信息调查

    public final static String INIT_MEALS_SURVEY = "/page/cust/meals_survey_init.action";// 初始化饮食调查

    public final static String INIT_LIFE_STYLE = "/page/cust/init_life_style.action";// 初始化生活方式营养调查

    public final static String RECORD_FOOD_QUERY = "/page/cust/record_food_query.action";// 初始化饮食调查

    public final static String RECORD_MEALS_SURVEY = "/page/cust/meals_survey_record.action";// 饮食记录

    public final static String MEALS_REPORT = "/page/cust/meals_report.action";// 报表

    /********************************* 膳食回顾 ************************************/
    /** 膳食回顾表单提交 */
    public final static String DIET_REVIEW = "/page/cust/diet_review.action";

}
