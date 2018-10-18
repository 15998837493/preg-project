/*
 * SystemPageName.java	 1.0   2015-1-5
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 系统页面名称
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-5 zy 初版
 */
@FrontCache(space = "InbodyPage", type = "jsp")
public class InBodyPageName {

    /** 人体成分客户一览页面 */
    public static final String INBODY_VIEW = "/examitem/inbody/inbody_view";

    /** 人体成分报告页面 */
    public static final String INBODY_REPORT = "/plan/inbody/inbody_report";

}
