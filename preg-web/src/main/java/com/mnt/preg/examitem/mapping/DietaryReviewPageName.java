/*
 * DietaryReviewPageName.java    1.0  2018-1-9
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 24小时膳食回顾
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-9 scd 初版
 */
@FrontCache(space = "DietaryReviewPage", type = "jsp")
public class DietaryReviewPageName {

    // 医学诊疗工具--膳食评估--饮食填写页面
    public static final String DIETARY_REVIEW = "/examitem/dietaryrecall/dietary_review";
}
