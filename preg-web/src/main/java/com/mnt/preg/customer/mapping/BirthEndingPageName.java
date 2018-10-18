
package com.mnt.preg.customer.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 分娩结局BIRTHENDING_DETAIL页面
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年9月10日 zcq 初版
 */
@FrontCache(space = "CustomerPage", type = "jsp")
public class BirthEndingPageName {

    public final static String PAGE_BIRTCHENDING_ADD = "/birthending/birthendingInfo/birthendingInfo_main";

    public final static String PAGE_BIRTCHENDING_DETAIL = "/birthending/birthendingInfo/birthendingInfo_detail";

}
