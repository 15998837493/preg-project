/*
 * UserServiceImpl.java	 1.0   2014-12-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.utils.Md5;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.main.enums.CRUDEnum;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.system.dao.PrimaryKeyDao;
import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.entity.UserDept;
import com.mnt.preg.system.entity.UserRole;
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
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Resource
    private PrimaryKeyDao primaryKeyDao;

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> queryUsers(User condition) {
        return userDao.queryUser(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> queryUsersAll(User condition) {
        return userDao.queryUsersAll(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUser(String userId) {
        return userDao.getUser(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUserByUserCode(String userCode) {
        return userDao.getUserByUserCode(userCode);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addUser(UserPojo userPojo) {
        User user = TransformerUtils.transformerProperties(User.class, userPojo);
        if (StringUtils.isEmpty(user.getUserCode())) {
            throw new ServiceException(ResultCode.ERROR_80004);
        }
        // 密码取系统参数默认密码
        if (StringUtils.isEmpty(user.getUserPassword())) {
            try {
                user.setUserPassword(Md5.getMD5Digest(this.getParamValue("user_default_password")));
            } catch (NoSuchAlgorithmException e) {
                throw new ServiceException(ResultCode.ERROR_90004);
            }
        }
        // 生成主键工号:顺序增加，起始10000
        user.setUserId(primaryKeyDao.getOrderNo("sys_user", "user_id"));
        // 检查用户信息
        this.checkUser(user, CRUDEnum.INSERT);
        // 用户保存
        String userId = (String) userDao.save(user);

        // 保存用户职位
        UserRole userRole = new UserRole();
        userRole.setRoleId(userPojo.getRoleId());
        userRole.setUserId(userId);
        userDao.save(userRole);

        return userId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateUser(User user) {
        String userId = user.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 检查用户信息
        this.checkUser(user, CRUDEnum.UPDATE);
        // 设置用户ID为更新用户检索条件
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("userId", HQLSymbol.EQ.toString(), userId));
        userDao.updateHQL(user, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateUserPojo(UserPojo userPojo) {
        // 修改用户信息
        User user = TransformerUtils.transformerProperties(User.class, userPojo);
        updateUser(user);
        // 修改职位
        String userId = user.getUserId();
        String roleId = userPojo.getRoleId();
        if (StringUtils.isNotEmpty(roleId)) {
            userDao.deleteUserRole(userId);
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userDao.save(userRole);
        }
        // 修改部门
        String deptId = userPojo.getDeptId();
        if (StringUtils.isNotEmpty(deptId)) {
            userDao.deleteUserDept(userId);
            UserDept userDept = new UserDept();
            userDept.setDeptId(deptId);
            userDept.setUserId(userId);
            userDao.save(userDept);
        }
    }

    /**
     * 检查用户注册信息中用户名、电子邮件、手机号是否与其他用户重复
     * 
     * @param user
     *            用户注册信息
     * @param crud
     *            当前操作类型
     */
    private void checkUser(User user, CRUDEnum crud) {
        int standard = 0; // 重复数据标准值
        if (crud.equals(CRUDEnum.INSERT)) {
            standard = 0; // 新增数据时不允许有重复
        } else if (crud.equals(CRUDEnum.UPDATE)) {
            standard = 1; // 修改时允许1条数据重复（当前操作数据本身）
        }
        // 用户名检查
        if (!StringUtils.isEmpty(user.getUserCode())
                && userDao.checkUserCodeValid(user.getUserCode()) > standard) {
            throw new ServiceException(ResultCode.ERROR_80001);
        }
        // 手机号检查
        if (!StringUtils.isEmpty(user.getUserPhone())
                && userDao.checkUserPhoneValid(user.getUserPhone()) > standard) {
            throw new ServiceException(ResultCode.ERROR_80002);
        }
        // 邮箱检查
        if (!StringUtils.isEmpty(user.getUserEmail())
                && userDao.checkUserEmailValid(user.getUserEmail()) > standard) {
            throw new ServiceException(ResultCode.ERROR_80003);
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void resetUserPwd(String userId) {
        User user = new User();
        user.setUserId(userId);
        try {
            // 设置默认密码
            user.setUserPassword(Md5.getMD5Digest(this.getParamValue("user_default_password")));
            // 更新
            this.updateUser(user);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(ResultCode.ERROR_90004);
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateUserPwd(String userId, String oldPsw, String newPsw) {
        UserPojo userPojo = this.getUser(userId);
        try {
            oldPsw = Md5.getMD5Digest(oldPsw);
            newPsw = Md5.getMD5Digest(newPsw);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(ResultCode.ERROR_90004);
        }
        if (userPojo.getUserPassword().equals(oldPsw)) {
            User uesr = new User();
            uesr.setUserId(userId);
            uesr.setUserPassword(newPsw);
            this.updateUser(uesr);
        } else {
            throw new ServiceException(ResultCode.ERROR_80006);
        }
    }
}
