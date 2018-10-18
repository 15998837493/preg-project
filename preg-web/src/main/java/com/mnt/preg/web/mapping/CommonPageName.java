/*
 * CommonPageName.java	 1.0   2015-1-13
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 公共错误页面
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-13 zy 初版
 */
@FrontCache(space = "CommonPage", type = "jsp")
public class CommonPageName {

    // 错误页
    public static final String ERROR_PAGE = "/error";

}
