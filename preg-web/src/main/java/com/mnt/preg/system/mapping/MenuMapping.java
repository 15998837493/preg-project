
package com.mnt.preg.system.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 
 * 功能菜单
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-4 zy 初版
 */
@FrontCache(space = "Menu")
public class MenuMapping {

    /** 功能菜单信息查询 */
    public static final String MENU_VIEW = "/page/menu/menu_view.action";

    /** 功能菜单信息查询 */
    public static final String MENU_QUERY = "/menu/menu_query.action";

    /** 获取功能菜单信息 */
    public static final String MENU_GET = "/menu/menu_get.action";

    /** 功能菜单添加请求 */
    public static final String MENU_ADD = "/menu/menu_add.action";

    /** 功能菜单更改请求 */
    public static final String MENU_UPDATE = "/menu/menu_update.action";

    /** 功能菜单排序更改请求 */
    public static final String MENU_UPDATE_ORDER = "/menu/menu_update_order.action";

    /** 功能菜单删除请求 */
    public static final String MENU_REMOVE = "/menu/menu_remove.action";

    /** 功能菜单名称是否可用 */
    public static final String MENU_NAME_VALID = "/menu/menu_name_valid.action";

}
