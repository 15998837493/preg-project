
package com.mnt.preg.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.system.condition.ReferralDoctorCondition;
import com.mnt.preg.system.dao.ReferralDoctorDAO;
import com.mnt.preg.system.entity.ReferralDoctor;
import com.mnt.preg.system.pojo.ReferralDoctorPojo;

/**
 * 转诊医生配置
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：
 *          v1.5 2018-03-21 dhs 初版
 */
@Service
public class ReferralDoctorServiceImpl extends BaseService implements ReferralDoctorService {

    @Resource
    private ReferralDoctorDAO referralDoctorDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ReferralDoctorPojo> queryDoctors(ReferralDoctorCondition referralDoctor) {
        return referralDoctorDAO.queryReferralDoctor(referralDoctor);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public ReferralDoctorPojo saveReferralDoctor(ReferralDoctor referralDoctor) {
        String id = (String) referralDoctorDAO.save(referralDoctor);
        return referralDoctorDAO.getTransformerBean(id, ReferralDoctor.class, ReferralDoctorPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public ReferralDoctorPojo updateReferralDoctor(ReferralDoctor referralDoctor) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), referralDoctor.getId()));
        referralDoctorDAO.updateHQL(referralDoctor, conditionParams);
        return referralDoctorDAO.getTransformerBean(referralDoctor.getId(), ReferralDoctor.class,
                ReferralDoctorPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeReferralDoctor(String id) {
        referralDoctorDAO.removeDoctor(id);
    }

    @Override
    public Integer validCode(String filedValue, String property) {
        return this.validCode(property, filedValue, ReferralDoctor.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferralDoctorPojo> queryDoctorDept(ReferralDoctor referralDoctor) {
        return referralDoctorDAO.queryReferralDoctorDept(referralDoctor);
    }

}
