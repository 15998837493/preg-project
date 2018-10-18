/*
 * DietaryReviewMapping.java    1.0  2018-1-9
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
@FrontCache(space = "DietaryReview")
public class DietaryReviewMapping {

    /** 24小时膳食回顾 */
    public final static String DIETARY_REVIEW = "/page/cust/dietary_review.action";

    /** 24小时膳食回顾重新评估 */
    public final static String DIETARY_REVIEW_REPORT = "/page/cust/dietary_review_report.action";

    public final static String DIETARY_RECORDS = "/page/cust/dietary_records.action";// 饮食记录

    public final static String DIETARY_REVIEW_PDF = "/page/cust/dietary_review_pdf.action";// 报表

    public final static String QUERY_FOOD = "/page/cust/query_food.action";// 初始化饮食调查

}
