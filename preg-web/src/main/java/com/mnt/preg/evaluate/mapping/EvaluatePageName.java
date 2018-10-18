
package com.mnt.preg.evaluate.mapping;

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
@FrontCache(space = "EvaluatePage", type = "jsp")
public class EvaluatePageName {

    public static final String EVALUATE_CUSTOMER_VIEW = "/evaluate/evaluate_customer_view";

    public static final String EVALUATE_GUIDE_PAGE = "/evaluate/guide/guide_page";

    public static final String EVALUATE_RECEIVE_MAIN = "/evaluate/receive/receive_main";

    public static final String DISGNOSIS_SUMMARY_VIEW = "/evaluate/tool/tool_disgnosis_summary";
}
