/*
 * ItemPageName.java    1.0  2017-6-26
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.mapping.items;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 系统页面名称
 * 
 * @author mnt115
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-26 mnt115 初版
 */
@FrontCache(space = "Item", type = "jsp")
public class ItemPageName {

    // 会员增加页
    public static final String ITEM_ADD = "/exam/item_add";

    // 会员增加页
    public static final String ITEM_UPDATE = "/exam/item_update";

    // 体检项目模板增加页
    public static final String INSPECT_ITEM_TEMPLET_ADD = "/exam/inspect_item_templet_add";

    // 医院体检项目模板一览页
    public static final String INSPECT_ITEM_TEMPLET_VIEW_DOCTOR = "/master/items/doctor_inspect_template_view";

    // 系统体检项目模板一览页
    public static final String INSPECT_ITEM_TEMPLET_VIEW_MNT = "/master/items/mnt_inspect_template_view";

    // 医院收费项目页面
    public static final String DOCTOR_INSPECT_ITEM = "/master/items/doctor_inspect_view";

    // 医院检查项目页面
    public static final String HOSPITAL_INSPECT_VIEW = "/master/items/hospital_inspect_view";

    // 医院信息页面
    public static final String HOSPITAL_VIEW = "/master/items/hospital_view";
}
