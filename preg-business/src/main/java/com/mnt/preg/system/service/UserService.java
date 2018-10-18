
package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.pojo.UserPojo;

/**
 * 用户管理事务
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-12-18 zcq 初版
 */
@Validated
public interface UserService {

    /**
     * 条件检索用户信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<UserPojo> queryUsers(User condition);

    /**
     * 条件检索用户信息(不过滤状态)
     * 
     * @author dhs
     * @param condition
     * @return
     */
    List<UserPojo> queryUsersAll(User condition);

    /**
     * 根据主键查询用户
     * 
     * @author zcq
     * @param userId
     *            主键
     * @return 用户信息
     */
    UserPojo getUser(@NotBlank String userId);

    /**
     * 查询用户信息--根据【用户帐号】
     * 
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>查询用户信息</dd>
     * </dl>
     * 
     * @author zcq
     * @param userCode
     *            用户帐号
     * @return 用户信息
     */
    UserPojo getUserByUserCode(@NotBlank String userCode);

    /**
     * 
     * 根据手机号码查询用户信息
     * 
     * @author mnt_zhangjing
     * @param phone
     * @return
     */
    UserPojo getUserByPhone(@NotBlank String phone);

    /**
     * 
     * 增加用户
     * 
     * @author mnt_zhangjing
     * @param user
     * @param roleId
     * @return
     */
    String addUser(@Valid @NotNull UserPojo userPojo);

    /**
     * 修改用户
     * 
     * @author zcq
     * @param user
     *            用户信息
     */
    void updateUser(@Valid @NotNull User user);

    /**
     * 修改用户信息
     * 
     * @author zcq
     * @param userPojo
     */
    void updateUserPojo(@Valid @NotNull UserPojo userPojo);

    /**
     * 重置密码
     * 
     * @author zcq
     * @param userId
     */
    void resetUserPwd(String userId);

    /**
     * 修改密码
     * 
     * @author zcq
     * @param userId
     * @param oldPsw
     * @param newPsw
     */
    void updateUserPwd(String userId, String oldPsw, String newPsw);

}
