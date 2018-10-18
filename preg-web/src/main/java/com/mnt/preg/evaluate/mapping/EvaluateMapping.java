
package com.mnt.preg.evaluate.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 诊疗中心mapping映射
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-13 zcq 初版
 */
@FrontCache(space = "Evaluate")
public class EvaluateMapping {

    public final static String DIAGNOSIS_EVALUATE_VIEW = "/page/evaluate/evaluate_customer_view.action";

    public final static String EVALUATE_GUIDE_PAGE = "/page/evaluate/guide_page.action";

    public final static String EVALUATE_RECEIVE_MAIN = "/page/evaluate/evaluate_receive_main.action";

    public final static String EVALUATE_SUMMARY_PAGE = "/page/evaluate/evaluate_summary_page.action";

    // 获取所选医生默认评价项目
    public final static String DOCTOR_INSPECT_LIST = "/page/evaluate/doctor_inspect_list.action";
}
