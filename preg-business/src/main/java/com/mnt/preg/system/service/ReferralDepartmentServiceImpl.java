/*
 * ReferralDepartmentServiceImpl.java    1.0  2018-3-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

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
import com.mnt.preg.main.utils.AutomaticGenerationCoding;
import com.mnt.preg.system.condition.ReferralDepartmentCondition;
import com.mnt.preg.system.dao.ReferralDepartmentDAO;
import com.mnt.preg.system.entity.ReferralDepartment;
import com.mnt.preg.system.pojo.ReferralDepartmentPojo;

/**
 * 转诊科室Service实现类
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-3-21 scd 初版
 */
@Service
public class ReferralDepartmentServiceImpl extends BaseService implements ReferralDepartmentService {

    @Resource
    private ReferralDepartmentDAO referralDepartmentDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ReferralDepartmentPojo> queryReferralDepartment(ReferralDepartmentCondition condition) {
        return referralDepartmentDAO.queryReferralDepartment(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveReferralDepartment(ReferralDepartment referralDepartment) {
        return (String) referralDepartmentDAO.save(referralDepartment);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateReferralDepartment(ReferralDepartment referralDepartment) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("referral_id", HQLSymbol.EQ.toString(), referralDepartment
                .getReferralId()));
        referralDepartmentDAO.updateHQL(referralDepartment, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeReferralDepartment(String referralId) {
        referralDepartmentDAO.removeReferralDepartment(referralId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkReferralDepartmentName(String referralName) {
        return this.validCode("referralName", referralName, ReferralDepartment.class);
    }

    @Override
    @Transactional(readOnly = true)
    public String getReferralDepartmentCode() {
        String referralCode = referralDepartmentDAO.getReferralDepartmentMaxCode();
        return AutomaticGenerationCoding.getInstance().getNextCashNumber(referralCode, 6);
    }

    @Override
    @Transactional(readOnly = true)
    public ReferralDepartmentPojo getReferralDepartment(String referralId) {
        return referralDepartmentDAO.getReferralDepartmentById(referralId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferralDepartmentPojo> queryReferralByContent(ReferralDepartmentCondition condition) {
        return referralDepartmentDAO.queryReferralByContent(condition);
    }
}
