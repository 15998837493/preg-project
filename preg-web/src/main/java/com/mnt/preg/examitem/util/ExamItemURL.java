
package com.mnt.preg.examitem.util;

import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.examitem.mapping.DietaryReviewMapping;
import com.mnt.preg.examitem.mapping.InBodyMapping;
import com.mnt.preg.master.mapping.QuestionMapping;
import com.mnt.preg.web.anoton.FrontCache;

/**
 * 公共常量
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-11 zcq 初版
 */
@FrontCache(space = "ExamItemURL")
public class ExamItemURL {

    /** 检查项目编码操作路径 */
    public final static String Q00001_operate_url = QuestionMapping.ALLOCATION_QUESTION + "?questionId=Q00001";// 危险分层问卷

    public final static String Q00002_operate_url = QuestionMapping.ALLOCATION_QUESTION + "?questionId=Q00002";// 营养诊疗问卷

    public final static String Q00007_operate_url = QuestionMapping.ALLOCATION_QUESTION + "?questionId=Q00007";// 生活方式

    public final static String Q00009_operate_url = QuestionMapping.ALLOCATION_QUESTION + "?questionId=Q00009";// 健康史调查

    public final static String B00006_operate_url = QuestionMapping.ALLOCATION_QUESTION
            + "?questionId=C00000011000022018010900005";// 孕期快速营养调查

    public final static String B00007_operate_url = QuestionMapping.ALLOCATION_QUESTION
            + "?questionId=C00000011000022018011200076";// 孕期生活方式调查问卷

    public final static String B00001_operate_url = CustomerMapping.INIT_MEALS_SURVEY;// 膳食评价

    public final static String B00002_operate_url = CustomerMapping.INIT_LIFE_STYLE;// 膳食及生活方式

    public final static String B00003_operate_url = InBodyMapping.INBODY_VIEW;// 人体成分

    public final static String B00004_operate_url = CustomerMapping.PLAN_EXTENDER_ASSESS;// 营养素安全剂量评估

    public final static String B00005_operate_url = DietaryReviewMapping.DIETARY_REVIEW;// 24小时膳食回顾

    public final static String B00008_operate_url = CustomerMapping.PREG_DIETARY_FREQUENCY;// 孕期生活方式调查问卷

    /** 检查项目编码操作路径 */
    public final static String B00001_callback_url = CustomerMapping.MEALS_REPORT;// 膳食评价

    public final static String B00002_callback_url = QuestionMapping.LIFE_STYLE_REPOER;// 膳食及生活方式

    public final static String B00003_callback_url = InBodyMapping.INBODY_REPORT;// 人体成分

    public final static String B00004_callback_url = CustomerMapping.PLAN_EXTENDER_ASSESS_REPORT;// 营养素安全剂量评估

    public final static String B00006_callback_url = QuestionMapping.NUTRITIOUS_SURVEY_REPOER;// 孕期快速营养调查

    public final static String B00005_callback_url = DietaryReviewMapping.DIETARY_REVIEW_PDF;// 24小时膳食回顾

    public final static String B00007_callback_url = QuestionMapping.LIFE_STYLE_SERVEY_REPORT;// 膳食频率

    public final static String B00008_callback_url = QuestionMapping.DIETARY_FREQUENCY_REPOER;// 膳食频率

    public final static String guide_callback_url = CustomerMapping.EXAM_GUIDE_LIST;// 营养评价报告单

}
