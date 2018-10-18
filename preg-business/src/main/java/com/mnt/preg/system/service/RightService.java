
package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.entity.Right;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.RightPojo;

/**
 * 权限管理事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 zcq 初版
 */
@Validated
public interface RightService {

    /**
     * 查询权限信息--通用检索
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询权限信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param condition
     *            条件
     * @return 权限信息
     */
    List<RightPojo> queryRight(Right right);

    /**
     * 查询权限信息--根据【权限主键】
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询权限信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param rightId
     *            权限主键
     * @return 权限信息
     */
    RightPojo getRight(@NotBlank String rightId);

    /**
     * 添加权限信息
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>添加权限信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param right
     *            权限信息
     * @param menuIdList
     *            菜单列表
     * @return 权限主键
     */
    String addRight(Right right, List<String> menuIdList);

    /**
     * 移除权限
     * 
     * @author xdc
     * @param rigthId
     * @return
     */
    void removeRight(@NotBlank String rigthId);

    /**
     * 修改权限信息
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>修改权限信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param right
     *            权限信息
     * @param menuIdList
     *            菜单列表
     */
    void updateRight(@Valid @NotNull Right right, @NotEmpty List<String> menuIdList);

    /**
     * 检验权限名称是否可用
     * 
     * @author zcq
     * @param rightName
     * @return
     */
    Integer checkRightNameValid(@NotBlank String rightName);

    /**
     * 查询权限的功能菜单信息--根据【权限主键】
     * 
     * @author zcq
     * @param rightId
     *            权限主键
     * @return 权限的功能菜单信息
     */
    List<MenuPojo> queryMenuByRightId(@NotBlank String rightId);

}
