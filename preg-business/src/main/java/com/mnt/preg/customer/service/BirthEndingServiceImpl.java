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
import com.mnt.preg.customer.condition.BirthEndingCondition;
import com.mnt.preg.customer.dao.BirthEndingBabyInfoDAO;
import com.mnt.preg.customer.dao.BirthEndingBaseInfoDAO;
import com.mnt.preg.customer.dao.BirthEndingDAO;
import com.mnt.preg.customer.dao.BirthEndingDischargedDAO;
import com.mnt.preg.customer.dao.CustomerDAO;
import com.mnt.preg.customer.entity.BirthEnding;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;
import com.mnt.preg.customer.pojo.BirthEndingDischargedPojo;
import com.mnt.preg.customer.pojo.BirthEndingPojo;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;

/**
 * 分娩结局管理
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 wjc 初版
 */
@Service
public class BirthEndingServiceImpl extends BaseService implements BirthEndingService {

    @Resource
    private BirthEndingDAO birthEndingDAO;// 分娩结局DAO

    @Resource
    private BirthEndingBabyInfoDAO birthBabyDAO;// 新生儿DAO

    @Resource
    private BirthEndingBaseInfoDAO birthBaseDAO;// 分娩信息DAO

    @Resource
    private BirthEndingDischargedDAO dischargeDAO;// 出院诊断DAO

    @Resource
    private CustomerDAO customerDAO;// 客户DAO

    @Override
    @Transactional(readOnly = true)
    public List<BirthEndingPojo> queryBirthEndingList(BirthEndingCondition condition) {
        if (condition == null) {
            condition = new BirthEndingCondition();
        }
        return birthEndingDAO.queryCustomer(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingPojo addBirthEnding(BirthEnding birthEnding) {
        birthEndingDAO.save(birthEnding);
        return birthEndingDAO.getTransformerBean(birthEnding.getBirthId(), BirthEnding.class, BirthEndingPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingPojo addBirthEnding(String custId) {
        CustomerPojo customerPojo = customerDAO.getCustomer(custId);
        BirthEnding birthEnding = new BirthEnding();
        birthEnding.setCustId(custId);
        birthEnding.setCustName(customerPojo.getCustName());
        birthEnding.setCreateTime(new Date());
        birthEnding.setUpdateTime(new Date());
        birthEnding.setFlag(1);
        birthEnding.setCreateUserId("");
        birthEnding.setUpdateUserId("");
        birthEnding.setCreateInsId("");
        birthEndingDAO.save(birthEnding);
        return birthEndingDAO.getTransformerBean(birthEnding.getBirthId(), BirthEnding.class, BirthEndingPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingPojo updateBirthEnding(BirthEnding birthEnding) {
        if (birthEnding == null || StringUtils.isEmpty(birthEnding.getBirthId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        try {
            String updateUserId = this.getLoginUser().getUserPojo().getUserId();
            if (StringUtils.isBlank(updateUserId)) {
                throw new RuntimeException("请重新登录");
            }
            BirthEndingPojo pojo = birthEndingDAO.getBirthById(birthEnding.getBirthId());

            birthEnding.setCreateInsId(pojo.getCreateInsId());
            birthEnding.setCreateTime(pojo.getCreateTime());
            birthEnding.setCreateUserId(pojo.getCreateUserId());
            birthEnding.setUpdateTime(new Date());
            birthEnding.setUpdateUserId(updateUserId);
            birthEnding.setFlag(1);

            birthEndingDAO.update(birthEnding);
        } catch (Exception e) {
            throw new HibernateException("更新分娩结局基本信息失败");
        }
        return birthEndingDAO.getTransformerBean(birthEnding.getBirthId(), BirthEnding.class, BirthEndingPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingPojo getBirthEndingById(String birthId) {
        return birthEndingDAO.getBirthById(birthId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteBirthEnding(String birthId) {
        if (StringUtils.isBlank(birthId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        BirthEndingBabyInfoPojo babyPojo = new BirthEndingBabyInfoPojo();
        babyPojo.setBirthId(birthId);
        BirthEndingDischargedPojo dischargePojo = new BirthEndingDischargedPojo();
        dischargePojo.setBirthId(birthId);

        birthEndingDAO.deleteResords(birthId);
        birthBabyDAO.deleteBabyByBirthId(birthId);
        birthBaseDAO.deleteResords(birthId);
        dischargeDAO.deleteDischargedByBirthId(birthId);
    }

}
