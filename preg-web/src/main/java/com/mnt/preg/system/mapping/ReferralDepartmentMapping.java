/*
 * ReferralDepartmentMapping.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 转诊科室 操作mapping映射(根据模块分)
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
@FrontCache(space = "referral")
public class ReferralDepartmentMapping {

    /** 查询转诊科室 */
    public static final String REFERRAL_QUERY = "/page/system/referral/referral_query.action";

    /** 根据名称或编码检索转诊科室 */
    public static final String REFERRAL_QUERY_BY_CONTENT = "/page/system/referral/referral_query_by_content.action";

    /** 添加转诊科室 */
    public static final String REFERRAL_SAVE = "/page/system/referral/referral_save.action";

    /** 修改转诊科室 */
    public static final String REFERRAL_UPDATE = "/page/system/referral/referral_update.action";

    /** 删除转诊科室 */
    public static final String REFERRAL_REMOVE = "/page/system/referral/referral_remove.action";

    /** 检查转诊科室名称是否重复 */
    public static final String REFERRAL_CHEACK_NAME = "/page/system/referral/referral_cheack_name.action";
}
