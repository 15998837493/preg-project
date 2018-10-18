
package com.mnt.preg.examitem.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 系统管理模块 操作mapping映射(根据模块分)
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-24 zcq 初版
 */
@FrontCache(space = "Inbody")
public class InBodyMapping {

    /** 人体成分页面请求 */
    public static final String INBODY_VIEW = "/page/inbody/inbody_view.action";

    /** 人体成分客户查询请求 */
    public static final String INBODY_QUERY = "/page/inbody/inbody_query.action";

    /** 人体成分报告请求 */
    public static final String INBODY_ADD = "/page/inbody/inbody_add.action";

    /** 人体成分报告请求 */
    public static final String INBODY_REPORT = "/page/inbody/inbody_report.action";

}
