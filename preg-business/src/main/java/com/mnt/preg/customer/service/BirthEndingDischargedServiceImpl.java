/**
 * 
 */

package com.mnt.preg.customer.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.preg.customer.dao.BirthEndingDischargedDAO;
import com.mnt.preg.customer.entity.BirthEndingDischarged;
import com.mnt.preg.customer.pojo.BirthEndingDischargedPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;

/**
 * 出院诊断
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-07 wjc 初版
 */
@Service
public class BirthEndingDischargedServiceImpl extends BaseService implements BirthEndingDischargedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BirthEndingDischargedServiceImpl.class);

    @Resource
    private BirthEndingDischargedDAO dischargeDAO;// 出院诊断DAO

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateBirthDischarged(BirthEndingDischarged disInfo) {
        String updateUserId = this.getLoginUser().getUserPojo().getUserId();
        if (StringUtils.isBlank(updateUserId)) {
            throw new RuntimeException("请重新登录");
        }
        BirthEndingDischargedPojo pojo = dischargeDAO.getByDisId(disInfo.getDisId());

        disInfo.setCreateInsId(pojo.getCreateInsId());
        disInfo.setCreateTime(pojo.getCreateTime());
        disInfo.setCreateUserId(pojo.getCreateUserId());
        disInfo.setUpdateTime(new Date());
        disInfo.setUpdateUserId(updateUserId);
        disInfo.setFlag(pojo.getFlag());
        dischargeDAO.update(disInfo);

        // String disId = disInfo.getDisId();
        // List<HQLConditionParam> params = new ArrayList<HQLConditionParam>();
        // params.add(new HQLConditionParam("disId", SQLSymbol.EQ.toString(), disId));
        // dischargeDAO.updateHQL(disInfo, params);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingDischargedPojo saveBirthDischarged(BirthEndingDischarged discharge) {
        if (discharge == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 保存出院诊断信息
        dischargeDAO.save(discharge);
        return dischargeDAO.getTransformerBean(discharge.getDisId(), BirthEndingDischarged.class,
                BirthEndingDischargedPojo.class);

    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<BirthEndingDischargedPojo> getDischargedByBirthId(String birthId) {
        return dischargeDAO.getDischargedByBirthById(birthId);
    }

    @Override
    public void deleteDischargedByBabyId(String babyId) {
        dischargeDAO.deleteDischargedByBabyId(babyId);
    }

    @Override
    public void deleteDischargedByBirthId(String birthId) {
        dischargeDAO.deleteDischargedByBirthId(birthId);
    }

}
