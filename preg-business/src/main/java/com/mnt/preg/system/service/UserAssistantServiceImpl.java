
package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.system.condition.DoctorAssistantCondition;
import com.mnt.preg.system.dao.UserDao;
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
@Service
public class UserAssistantServiceImpl extends BaseService implements UserAssistantService {

    @Resource
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addUserAssistant(UserAssistant user) {
        if (StringUtils.isNotBlank(user.getAssistantId())) {
            if (",".equals(user.getAssistantId().substring(0, 1))) {
                user.setAssistantId(user.getAssistantId().substring(1));
            } else if (",".equals(user.getAssistantId().substring(user.getAssistantId().length() - 1))) {
                user.setAssistantId(user.getAssistantId().substring(0, user.getAssistantId().length() - 1));
            }
        }
        userDao.deleteUserAssistant(user.getDoctorId(), "doctor");
        userDao.save(user);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addUserDoctor(UserAssistant user) {
        if (StringUtils.isNotBlank(user.getDoctorId())) {
            if (",".equals(user.getDoctorId().substring(0, 1))) {
                user.setDoctorId(user.getDoctorId().substring(1));
            } else if (",".equals(user.getDoctorId().substring(user.getDoctorId().length() - 1))) {
                user.setDoctorId(user.getDoctorId().substring(0, user.getDoctorId().length() - 1));
            }
        }
        if (StringUtils.isNotBlank(user.getDoctorId())) {
            String[] strings = user.getDoctorId().split(",");
            userDao.deleteUserAssistant(user.getAssistantId(), "assiss");
            for (String str : strings) {
                UserAssistant userAssis = new UserAssistant();
                userAssis.setAssistantId(user.getAssistantId());
                userAssis.setDoctorId(str);
                userDao.save(userAssis);
            }
        } else {
            userDao.deleteUserAssistant(user.getAssistantId(), "assiss");
        }
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateUserAssistant(UserAssistant user, String type) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        if ("doctor".equals(type)) {
            conditionParams.add(new HQLConditionParam("doctorId", HQLSymbol.EQ.toString(), user.getDoctorId()));
        } else if ("assiss".equals(type)) {
            conditionParams.add(new HQLConditionParam("assistantId", HQLSymbol.EQ.toString(), user.getAssistantId()));
        }
        userDao.updateHQL(user, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteUserAssistant(String id, String type) {
        userDao.deleteUserAssistant(id, type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAssistantPojo> queryDoctorByCondition(DoctorAssistantCondition condition) {
        return userDao.queryDoctorByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> queryDoctorOrAssistant(String userId) {
        UserPojo userPojo = userDao.getUser(userId);
        List<UserPojo> list = new ArrayList<UserPojo>();
        if (userPojo != null) {
            DoctorAssistantCondition condition = new DoctorAssistantCondition();
            if (("doctor").equals(userPojo.getUserType())) {// 医生
                condition.setDoctorId(userId);
                List<UserAssistantPojo> assistantList = this.queryDoctorByCondition(condition);
                if (CollectionUtils.isNotEmpty(assistantList)) {
                    for (UserAssistantPojo pojo : assistantList) {
                        list.add(userDao.getUser(pojo.getAssistantId()));
                    }
                }
            } else if (("assistant").equals(userPojo.getUserType())) {// 助理
                condition.setAssistantId(userId);
                List<UserAssistantPojo> assistantList = this.queryDoctorByCondition(condition);
                if (CollectionUtils.isNotEmpty(assistantList)) {
                    for (UserAssistantPojo pojo : assistantList) {
                        list.add(userDao.getUser(pojo.getDoctorId()));
                    }
                }
            }
        }
        return list;
    }

}
