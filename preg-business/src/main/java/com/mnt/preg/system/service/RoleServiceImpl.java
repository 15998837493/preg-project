/*
 * PositionServiceImpl.java	 1.0   2014-12-18
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.system.dao.PrimaryKeyDao;
import com.mnt.preg.system.dao.RoleDao;
import com.mnt.preg.system.entity.Role;
import com.mnt.preg.system.entity.RoleRight;
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
@Service
public class RoleServiceImpl extends BaseService implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private PrimaryKeyDao primaryKeyDao;

    @Resource
    private LoginService loginService;

    @Override
    @Transactional(readOnly = true)
    public List<RolePojo> queryRole(Role condition) {
        List<RolePojo> roleList = roleDao.queryRole(condition);
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (RolePojo role : roleList) {
                String roleId = role.getRoleId();
                role.setRightList(roleDao.queryRightByRoleId(roleId));
            }
        }
        return roleList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RightPojo> queryRightByRoleId(String roleId) {
        return roleDao.queryRightByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public RolePojo getRole(String roleId) {
        RolePojo roleVo = roleDao.getTransformerBean(roleId, Role.class, RolePojo.class);
        roleVo.setRightList(roleDao.queryRightByRoleId(roleId));
        return roleVo;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addRole(Role role, List<String> rightIdList) {
        // 生成主键：顺序号
        String roleId = primaryKeyDao.getOrderNo("sys_role", "role_id");
        role.setRoleId(roleId);
        // 保存角色组
        roleDao.save(role);
        for (String rightId : rightIdList) {
            RoleRight roleRight = new RoleRight();
            roleRight.setRightId(rightId);
            roleRight.setRoleId(roleId);
            // 保存关联职务功能
            roleDao.save(roleRight);
        }
        return roleId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateRole(Role role, List<String> rightIdList) {
        String roleId = role.getRoleId();
        // 设置职务ID为更新职务检索条件
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("roleId", HQLSymbol.EQ.toString(), roleId));
        roleDao.updateHQL(role, conditionParams);
        // 删除原角色组功能列表
        roleDao.deleteRoleRight(roleId);
        if (CollectionUtils.isNotEmpty(rightIdList)) {
            // 保存新角色功能列表
            for (String rightId : rightIdList) {
                RoleRight roleRight = new RoleRight();
                roleRight.setRightId(rightId);
                roleRight.setRoleId(roleId);
                // 保存关联职务功能
                roleDao.save(roleRight);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeRole(String roleId) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setFlag(Flag.deleted.getValue());
        this.updateRole(role, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuPojo> queryMenuByRoleId(String roleId) {
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        return roleDao.queryMenuByRoleId(roleId, insId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkRoleNameValid(String roleName) {
        return roleDao.checkRoleNameValid(roleName);
    }
}
