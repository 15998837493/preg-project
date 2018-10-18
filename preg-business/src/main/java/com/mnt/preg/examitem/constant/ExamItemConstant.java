
package com.mnt.preg.examitem.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 检测项目常量
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-5 zcq 初版
 */
public class ExamItemConstant {

    /** 检查项目编码 */
    public final static class EXAM_ITEM_CODE {

        /** 孕期膳食评价 */
        public final static String B00001 = "B00001";

        /** 孕期膳食及生活方式评估 */
        public final static String B00002 = "B00002";

        /** 孕期人体成分评估 */
        public final static String B00003 = "B00003";

        /** 营养素安全剂量评估 */
        public final static String B00004 = "B00004";

        /** 孕期24小时膳食回顾 */
        public final static String B00005 = "B00005";

        /** 孕期快速营养调查 */
        public final static String B00006 = "B00006";

        /** 孕期生活方式调查问卷 */
        public final static String B00007 = "B00007";

        /** 孕期膳食频率调查 */
        public final static String B00008 = "B00008";

    }

    /** 检查项目分析结果表 */
    public final static class EXAM_ITEM_TABLE {

        /** 孕期膳食评价 */
        public final static String B00001 = "cus_result_diet";

        /** 孕期膳食及生活方式评估 */
        public final static String B00002 = "cus_result_life";

        /** 孕期人体成分评估 */
        public final static String B00003 = "cus_result_inbody";

        /** 营养素安全剂量评估 */
        public final static String B00004 = "cus_result_extender";

        /** 孕期24小时膳食回顾 */
        public final static String B00005 = "cus_result_diet";

        /** 孕期快速营养调查 */
        public final static String B00006 = "cus_result_nutritious";

        /** 孕期生活方式调查问卷 */
        public final static String B00007 = "cus_result_life_servey";

        /** 孕期膳食频率调查 */
        public final static String B00008 = "cus_result_dietary_frequency";

        /** 营养评价单 */
        public static final String guide = "cus_result_guide";

    }

    /** 评估项目结果表集合 */
    public static final Map<String, String> inspectTableMap = new HashMap<String, String>();
    static {
        inspectTableMap.put(EXAM_ITEM_CODE.B00001, EXAM_ITEM_TABLE.B00001);
        inspectTableMap.put(EXAM_ITEM_CODE.B00002, EXAM_ITEM_TABLE.B00002);
        inspectTableMap.put(EXAM_ITEM_CODE.B00003, EXAM_ITEM_TABLE.B00003);
        inspectTableMap.put(EXAM_ITEM_CODE.B00004, EXAM_ITEM_TABLE.B00004);
        inspectTableMap.put(EXAM_ITEM_CODE.B00005, EXAM_ITEM_TABLE.B00005);
        inspectTableMap.put(EXAM_ITEM_CODE.B00006, EXAM_ITEM_TABLE.B00006);
        inspectTableMap.put(EXAM_ITEM_CODE.B00007, EXAM_ITEM_TABLE.B00007);
        inspectTableMap.put(EXAM_ITEM_CODE.B00008, EXAM_ITEM_TABLE.B00008);
    }

    /** 检查项目报告名称后缀 */
    public static final Map<String, String> itemReportMap = new HashMap<String, String>();
    static {
        itemReportMap.put("F00001", "front.pdf");
        itemReportMap.put("F00002", "back.pdf");
        itemReportMap.put("E00001", "examination.pdf");
        itemReportMap.put("E00002", "extender.pdf");
        itemReportMap.put("B00001", "diet.pdf");
        itemReportMap.put("B00002", "life.pdf");
        itemReportMap.put("B00003", "inbody.pdf");
        itemReportMap.put("B00004", "extender.pdf");
        itemReportMap.put("B00005", "dietary_review.pdf");
        itemReportMap.put("B00006", "nutritious_survey.pdf");
        itemReportMap.put("B00007", "lifesurvey.pdf");
        itemReportMap.put("B00008", "dietary.pdf");
        itemReportMap.put("P00001", "plan.pdf");
        itemReportMap.put("P00002", "quota.pdf");
        itemReportMap.put("P00003", "sport.pdf");
        itemReportMap.put("P00004", "summary.pdf");
        itemReportMap.put("P00005", "template.pdf");
        itemReportMap.put("P00006", "conclusion.pdf");
        itemReportMap.put("Q00007", "life_style.pdf");
        itemReportMap.put("Q00008", "symptoms.pdf");
        itemReportMap.put("Q00009", "health_history.pdf");
        itemReportMap.put("H00001", "health_summary.pdf");
        itemReportMap.put("P06001", "WBS.pdf");
        itemReportMap.put("P07001", "DMDiet.pdf");
        itemReportMap.put("P05001", "diaryPdf.pdf");
        itemReportMap.put("P08001", "guidePdf.pdf");
    }

}
