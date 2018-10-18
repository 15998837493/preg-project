/*
 * StatisticMapping.java    1.0  2018年8月10日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 数据统计平台mapping
 * 
 * @author lipeng
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月10日 lipeng 初版
 */
@FrontCache(space = "Statistic")
public class StatisticMapping {

    /** 数据统计平台页面初始化 */
    public static final String STATISTIC_INIT = "/statistic/statistic_init.action";

    /** 数据统计平台页面 人员列表查询 */
    public static final String STATISTIC_QUERY = "/statistic/statistic_query.action";

    /** 数据统计平台页面 分娩结局统计报表查询 */
    public static final String STATISTIC_BIRTHRESULT = "/statistic/statistic_birthResult.action";

    /** 数据统计平台页面 查询出数据 */
    public static final String STATISTIC_EXPORT = "/statistic/statistic_export.action";

    /** 数据统计平台页面 将查询出数据导出 */
    public static final String EXPORT_EXCEL = "/statistic/export_excel.action";

    /** 数据统计平台页面 导出excel进度 */
    public static final String IS_EXPORTED = "/statistic/is_exported.action";

}
