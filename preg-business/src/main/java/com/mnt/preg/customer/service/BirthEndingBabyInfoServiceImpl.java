/**
 * 
 */

package com.mnt.preg.customer.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.preg.customer.condition.BirthEndingBabyInfoCondition;
import com.mnt.preg.customer.dao.BirthEndingBabyInfoDAO;
import com.mnt.preg.customer.dao.BirthEndingDischargedDAO;
import com.mnt.preg.customer.entity.BirthEndingBabyInfo;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;

/**
 * 客户管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Service
public class BirthEndingBabyInfoServiceImpl extends BaseService implements BirthEndingBabyInfoService {

    @Resource
    private BirthEndingBabyInfoDAO birthBabyDAO;

    @Resource
    private BirthEndingDischargedDAO birthDischargedDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingBabyInfoPojo saveBirthBabyInfo(BirthEndingBabyInfo babyInfo) {
        if (babyInfo == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 保存客户信息
        birthBabyDAO.save(babyInfo);

        return birthBabyDAO.getTransformerBean(babyInfo.getBabyId(), BirthEndingBabyInfo.class,
                BirthEndingBabyInfoPojo.class);

    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateBirthBabyInfo(BirthEndingBabyInfo babyInfo) {
        String updateUserId = this.getLoginUser().getUserPojo().getUserId();
        if (StringUtils.isBlank(updateUserId)) {
            throw new RuntimeException("请重新登录");
        }
        BirthEndingBabyInfoPojo pojo = birthBabyDAO.getById(babyInfo.getBabyId());

        babyInfo.setCreateInsId(pojo.getCreateInsId());
        babyInfo.setCreateTime(pojo.getCreateTime());
        babyInfo.setCreateUserId(pojo.getCreateUserId());
        babyInfo.setUpdateTime(new Date());
        babyInfo.setUpdateUserId(updateUserId);
        babyInfo.setFlag(pojo.getFlag());
        birthBabyDAO.update(babyInfo);

        // String babyId = babyInfo.getBabyId();
        // List<HQLConditionParam> params = new ArrayList<HQLConditionParam>();
        // params.add(new HQLConditionParam("babyId", SQLSymbol.EQ.toString(), babyId));
        // birthBabyDAO.updateHQL(babyInfo, params);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<BirthEndingBabyInfoPojo> getBabyListByBirthId(String birthId) {
        List<BirthEndingBabyInfoPojo> birthBabyList = birthBabyDAO.getBabyListByBirthId(birthId);
        return birthBabyList;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<BirthEndingBabyInfoPojo> getBabyListByCondition(BirthEndingBabyInfoCondition condition) {
        return birthBabyDAO.getBabyListByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingBabyInfoPojo getMaxBabyListByCondition(BirthEndingBabyInfoCondition condition) {
        return birthBabyDAO.getMaxBabyListByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteBabyById(String babyId) {
        birthBabyDAO.deleteBabyById(babyId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteBabyByBirthId(String birthId) {
        birthBabyDAO.deleteBabyByBirthId(birthId);
    }

}
