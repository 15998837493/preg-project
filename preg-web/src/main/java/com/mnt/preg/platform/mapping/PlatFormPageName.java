
package com.mnt.preg.platform.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 诊疗中心页面名称
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-13 zcq 初版
 */
@FrontCache(space = "PlatformPage", type = "jsp")
public class PlatFormPageName {

    // =================================【DiagnosisController】=========================================
    public static final String PLATFORM_CUSTOMER_VIEW = "/platform/platform_customer_view";

    // =================================【DiagnosisItemController】=========================================

    // =================================【DiagnosisOrderController】====================================
    public static final String PLAN_EXTENDER = "/platform/plan/plan_order";

    /** 医嘱录入嵌入页 */
    public static final String PLAN_EXTENDER_YIZHU = "/platform/plan/yizhu_plan_order";

    // =================================【DiagnosisPlanController】=====================================
    public static final String PLAN_MAIN = "/platform/plan/plan_main";

    public static final String PLAN_ADJUST = "/platform/plan/plan_adjust";

    public static final String PLAN_COURSE_VIEW = "/platform/plan/plan_course";

    // =================================【DiagnosisToolbarController】===================================
    public static final String PLAN_GUIDE_PAGE = "/platform/guide/guide_page";

    public static final String PLATFORM_RECEIVE_MAIN = "/platform/receive/receive_main";

    // 指标录入页
    public static final String CHECK_ITEMS_VIEW = "/platform/receive/check_items_fuzhu";

    public static final String PLAN_JIEZHEN = "/plan/platform/jiezhen";

    public static final String PLAN_CREATE = "/plan/receive/plan_create";

    /** 诊疗历史记录页面 */
    public static final String DIAGNOSIS_RECORD = "/platform/tool/tool_diagnosis_history";

    /** 本次诊断分析页面 */
    public static final String DIAGNOSIS_HISTORY = "/platform/tool/tool_diagnosis_analysis";

    /** 查询系统营养评价 */
    public static final String DIAGNOSIS_REPORT_VIEW = "/platform/tool/tool_diagnosis_report";

    /** 复查预约 */
    public static final String DIAGNOSIS_BOOKING = "/platform/tool/tool_diagnosis_booking";

    /** 医嘱页面-复查预约嵌入页 */
    public static final String DIAGNOSIS_BOOKING_YIZHU = "/platform/tool/yizhu_tool_diagnosis_booking";

    /** 营养病例小节页面 */
    public static final String DISGNOSIS_SUMMARY_VIEW = "/platform/tool/tool_disgnosis_summary";

    /** 初诊建档/编辑页面 */
    public static final String NEW_DIAGNOSIS_VIEW = "/platform/tool/tool_diagnosis_inital";

    /** 宫高腹围对照页面 */
    public static final String COMPARE_TOOL = "/platform/tool/tool_compare";

    /** 胎龄计算器页面 */
    public static final String CALCULATOR_TOOL = "/platform/tool/tool_calculator";
}
