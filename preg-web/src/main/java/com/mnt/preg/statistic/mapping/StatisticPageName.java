/*
 * StatisticPageName.java    1.0  2018年8月10日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 数据统计平台页面
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月10日 lipeng 初版
 */
@FrontCache(space = "StatisticPage", type = "jsp")
public class StatisticPageName {
    /** 数据统计平台页面 */
    public static final String STATISTIC_VIEW = "/statistic/statistic_view";
}
