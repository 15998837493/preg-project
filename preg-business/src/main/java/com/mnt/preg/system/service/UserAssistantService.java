
package com.mnt.preg.system.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.system.condition.DoctorAssistantCondition;
import com.mnt.preg.system.entity.UserAssistant;
import com.mnt.preg.system.pojo.UserAssistantPojo;
import com.mnt.preg.system.pojo.UserPojo;

/**
 * 医生助理关系表
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：
 *          v1.5 2018-04-08 dhs 初版
 */
@Validated
public interface UserAssistantService {

    /**
     * 
     * 增加助理
     * 
     * @author dhs
     * @param user
     * @param roleId
     * @return
     */
    void addUserAssistant(@Valid @NotNull UserAssistant user);

    /**
     * 
     * 增加医生
     * 
     * @author dhs
     * @param user
     * @param roleId
     * @return
     */
    void addUserDoctor(@Valid @NotNull UserAssistant user);

    /**
     * 修改用户
     * 
     * @author dhs
     * @param user
     *            用户信息
     */
    void updateUserAssistant(@Valid @NotNull UserAssistant user, String type);

    /**
     * 物理删除用户
     * 
     * @author dhs
     * @param id
     *            用户信息
     */
    void deleteUserAssistant(String id, String type);

    /**
     * 查询用户
     * 
     * @author dhs
     * @param user
     *            用户信息
     */
    List<UserAssistantPojo> queryDoctorByCondition(DoctorAssistantCondition condition);

    /**
     * 查询医生/助理
     * 
     * @author dhs
     * @param user
     *            用户信息
     */
    List<UserPojo> queryDoctorOrAssistant(String userId);
}
