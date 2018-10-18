
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
@FrontCache(space = "WorkAccountPage", type = "jsp")
public class WorkAccountPageName {

    /** 工作量统计页 */
    public static final String WORK_ACCOUNT_VIEW = "/account/work_account_view";

    /** 工作量统计页（个人） */
    public static final String WORK_ACCOUNT_PERSON_VIEW = "/accountperson/work_account_view";
}
