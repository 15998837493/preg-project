/*
 * CustomerMapping.java	 1.0   2015-01-04
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 会员模块 操作mapping映射(根据模块分)
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-01-04 wjc 初版
 */
@FrontCache(space = "BirthEnding")
public class BirthEndingMapping {

    /********************************* 分娩结局管理 ************************************/

    public final static String BIRTHENDING_ADD = "/page/cust/birthending_add.action";

    public final static String BIRTHENDING_SAVE = "/page/cust/birthending_save.action";

    public final static String BIRTHENDING_UPDATE = "/page/cust/birthending_update.action";

    public final static String BIRTHENDING_DELETE = "/page/cust/birthending_delete.action";

    public final static String BIRTHENDING_GETLIST = "/page/cust/birthending_getList.action";

    public final static String BIRTHENDING_DETAIL = "/page/cust/birthending_detail.action";

    public final static String BIRTHENDING_GET_DETAIL_BYBIRTHID = "/page/cust/birthending_detail_byBirthId.action";

    public final static String BIRTHENDING_GET_UNLINK_PREGRECORD = "/page/cust/birthending_unlink_preg_record.action";

    /********************************* 分娩_出院诊断 ************************************/
    public final static String BIRTHENDING_DISCHARGE_UPDATE = "/page/cust/birthending_discharge_update.action";

    public final static String BIRTHENDING_DISCHARGE_SAVE = "/page/cust/birthending_discharge_save.action";

    /********************************* 分娩_新生儿 ************************************/
    public final static String BIRTHENDING_BABYINFO_SEARCH = "/page/cust/birthending_babyInfo_serch.action";

    public final static String BIRTHENDING_BABYINFO_UPDATE = "/page/cust/birthending_babyInfo_update.action";

    public final static String BIRTHENDING_BABYINFO_SAVE = "/page/cust/birthending_babyInfo_save.action";

    public final static String BIRTHENDING_BABYINFO_DELETE = "/page/cust/birthending_babyInfo_delete.action";

    /********************************* 分娩_分娩信息 ************************************/
    public final static String BIRTHENDING_BASEINFO_ADD = "/page/cust/birthending_baseInfo_add.action";

}
