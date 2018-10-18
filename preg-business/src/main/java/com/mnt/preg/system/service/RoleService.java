
package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.entity.Role;
import com.mnt.preg.system.pojo.MenuPojo;
import com.mnt.preg.system.pojo.RightPojo;
import com.mnt.preg.system.pojo.RolePojo;

/**
 * 职务事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版
 */
@Validated
public interface RoleService {

    /**
     * 条件检索职务
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<RolePojo> queryRole(Role condition);

    /**
     * 查询权限列表信息--根据【职位主键】
     * 
     * @author zcq
     * @param roleId
     *            主键
     * @return 权限列表信息
     */
    List<RightPojo> queryRightByRoleId(@NotBlank String roleId);

    /**
     * 查询职务信息--根据【职务主键】
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询职务信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param roleId
     *            职务主键
     * @return 职务信息
     */
    RolePojo getRole(@NotBlank String roleId);

    /**
     * 添加职务信息
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>添加职务信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param role
     *            职务信息
     * @param rightIdList
     *            角色列表
     * @return 职务主键
     */
    String addRole(@Valid @NotNull Role role, List<String> rightIdList);

    /**
     * 修改职务信息
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>修改职务信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param role
     *            职务信息
     * @param rightIdList
     *            角色列表
     */
    void updateRole(@Valid @NotNull Role role, @NotEmpty List<String> rightIdList);

    /**
     * 删除职务
     * 
     * @author zcq
     * @param roleId
     */
    void removeRole(@NotNull String roleId);

    /**
     * 查询职位的功能菜单信息--根据【职位主键】
     * 
     * @author zcq
     * @param roleId
     *            职位主键
     * @return 职位的功能菜单信息
     */
    List<MenuPojo> queryMenuByRoleId(@NotBlank String roleId);

    /**
     * 检验职位名称是否可用
     * 
     * @author zcq
     * @param roleName
     * @return
     */
    Integer checkRoleNameValid(@NotBlank String roleName);
}
