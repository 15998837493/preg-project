
package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.mnt.preg.system.entity.Menu;
import com.mnt.preg.system.pojo.LoginUser;
import com.mnt.preg.system.pojo.MenuPojo;

/**
 * 功能菜单事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 zcq 初版
 */
public interface MenuService {

    /**
     * 查询功能菜单
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询功能菜单</dd>
     * </dl>
     * 
     * @author zcq
     * @return 功能菜单
     */
    List<MenuPojo> queryMenu();

    /**
     * 获取功能菜单
     * 
     * @author zcq
     * @param menuId
     * @return
     */
    MenuPojo getMenu(@NotBlank String menuId);

    /**
     * 校验菜单主键是否可用
     * 
     * @author zcq
     * @param menuId
     *            主键
     * @return Integer
     */
    Integer checkMenuIdValid(@NotBlank String menuId);

    /**
     * 校验菜单名称是否可用
     * 
     * @author zcq
     * @param menuName
     *            主键
     * @return Integer
     */
    Integer checkMenuNameValid(@NotBlank String menuName);

    /**
     * 添加功能菜单
     * 
     * @author zcq
     * @param menu
     * @return
     */
    String addMenu(@Valid @NotNull Menu menu);

    /**
     * 修改功能菜单
     * 
     * @author zcq
     * @param menu
     */
    void updateMenu(@Valid @NotNull Menu menu);

    /**
     * 删除功能菜单
     * 
     * @author zcq
     * @param menu
     */
    void deleteMenu(@NotBlank String menuId);

    /**
     * 修改功能菜单排序
     * 
     * @author zcq
     * @param menuIdList
     */
    void editMenuOrder(@NotEmpty List<String> menuIdList);

    /**
     * 获取目录结构
     * 
     * @author zcq
     * @return 目录结构
     */
    List<MenuPojo> getCatalogMenu();

    /**
     * 获取菜单--根据【职位主键】
     * 
     * @author zcq
     * @param roleId
     * @return 获取菜单信息
     */
    List<MenuPojo> getCatalogMenuByLoginUser(@Valid @NotNull LoginUser loginUser);

}
