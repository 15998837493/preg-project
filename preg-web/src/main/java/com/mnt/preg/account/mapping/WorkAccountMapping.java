
package com.mnt.preg.account.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 工作量统计
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-2-5 xdc 初版
 */
@FrontCache(space = "WorkAccount")
public class WorkAccountMapping {

    /** 工作量统计页Excel导出 */
    public static final String CREATE_EXCEL_ACCOUNT = "/page/create_excel_account.action";

    /** 工作量对比页Excel导出 */
    public static final String CREATE_EXCEL_COMPARE = "/page/create_excel_compare.action";

    /** 工作量统计页 */
    public static final String WORK_ACCOUNT_PAGE = "/page/work_account_page.action";

    /** 工作量统计页（个人） */
    public static final String WORK_ACCOUNT_PERSON_PAGE = "/page/work_account_person_page.action";

    /** 工作量统计查询 */
    public static final String WORK_ACCOUNT_QUERY = "/page/work_account_query.action";

    /** 初诊人数和总就诊人数查询 */
    public static final String WORK_ACCOUNT_QUERY_TAB1 = "/page/work_account_query_tab1.action";

    /** 统计列表查询(部门) */
    public static final String STATISTICS_DATA_QUERY = "/page/statistics_data_query.action";

    /** 统计列表查询(个人) */
    public static final String STATISTICS_DATA_PERSON_QUERY = "/page/statistics_data_person_query.action";

    /** 工作量统计--诊断频次 */
    public static final String WORK_ACCOUNT_DISEASE_FREQUENCY = "/page/work_account_disease_frequency.action";

    /** 工作量统计--诊断频次 */
    public static final String WORK_ACCOUNT_PERSON_FREQUENCY = "/page/work_account_person_frequency.action";

    /** 工作量对比--诊断频次 */
    public static final String WORK_ACCOUNT_COMPARE_DISEASE_FREQUENCY = "/page/work_account_compare_disease_frequency.action";

    /** 工作量对比（个人）--诊断频次 */
    public static final String WORK_ACCOUNT_PERSON_DISEASE_FREQUENCY = "/page/work_account_person_disease_frequency.action";

    /** 接诊/初诊 人数对比统计查询（部门） */
    public static final String DIAGNOSIS_ACCOUNT_QUERY_ECHART = "/page/diagnosis_account_query_echart.action";

    /** 复诊率对比统计查询（部门） */
    public static final String FURTHER_RATE_QUERY_ECHART = "/page/further_rate_query_echart.action";

    /** 门诊量统计 */
    public static final String OUTPATIENT_DATA_QUERY = "/page/outpatient_data_query.action";

    /** 孕期初诊分布 （部门） */
    public static final String DISTRIBUTION_DATA_QUERY = "/page/distribution_data_query.action";

    /** 孕期初诊分布（个人） */
    public static final String DISTRIBUTION_DATA_QUERY_PERSON = "/page/distribution_data_query_person.action";

    /** 接诊/初诊 人数对比统计查询（个人） */
    public static final String DIAGNOSIS_ACCOUNT_QUERY_ECHART_PERSON = "/page/diagnosis_account_query_echart_person.action";

    /** 复诊率对比统计查询（个人） */
    public static final String FURTHER_RATE_QUERY_ECHART_PERSON = "/page/further_rate_query_echart_person.action";
}
