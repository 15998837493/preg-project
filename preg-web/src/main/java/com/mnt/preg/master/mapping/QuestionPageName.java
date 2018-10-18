/*
 * QuestionPageName.java	 1.0   2016-3-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 
 * 问卷页面
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-8 mnt_zhangjing 初版
 */
@FrontCache(space = "QuestionPage", type = "jsp")
public class QuestionPageName {

    /** 问卷调查页面 */
    public static final String QUESTION_VIEW = "/examitem/questions/question_view";

    /** 慢病营养报告-初级病因评估报告查询 */
    public final static String PRIMARY_CAUSE_REPORT_QUERY = "/question/primary_cause_report_query";

    /** 医学营养诊断报告错误页面 */
    public static final String PRIMARY_CAUSE_REPORT_ERROR = "/question/primary_cause_report_error";

    /** 问题添加 页面 */
    public static final String PROBLEM_INIT_ADD = "/master/question/problem_options_config_add";

    /** 问题修改页面 */
    public static final String PROBLEM_UPDATE = "/master/question/problem_options_config_update";

    /** 问卷添加 页面 */
    public static final String QUESTION_INIT_ADD = "/master/question/question_add";

    /** 问卷修改页面 */
    public static final String QUESTION_UPDATE = "/master/question/question_update";

    /** 问卷添加 页面 */
    public static final String QUESTION_INIT_CONFIG = "/master/question/question_problem_config";

    /** 问卷问题添加 页面 */
    public static final String QUESTION_PROBLEM_INIT_ADD = "/master/question/question_problem_options_config_add";

    /** 问题修改页面 */
    public static final String QUESTION_PROBLEM_UPDATE = "/master/question/question_problem_options_config_update";
}
