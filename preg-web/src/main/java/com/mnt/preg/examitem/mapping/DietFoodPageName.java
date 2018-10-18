/*
 * DietFoodPageName.java    1.0  2017-6-30
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 系统页面名称【根据模块分】
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-30 scd 初版
 */
@FrontCache(space = "DietFoodPage", type = "jsp")
public class DietFoodPageName {

    // 医学诊疗工具--膳食评估--会员信息调查
    public static final String MEALS_CUSTOMER = "/question/diet/meals_customer";
}
