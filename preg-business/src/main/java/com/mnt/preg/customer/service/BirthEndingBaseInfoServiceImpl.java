/**
 * 
 */

package com.mnt.preg.customer.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.customer.dao.BirthEndingBaseInfoDAO;
import com.mnt.preg.customer.dao.BirthEndingDAO;
import com.mnt.preg.customer.entity.BirthEndingBaseInfo;
import com.mnt.preg.customer.pojo.BirthEndingBaseInfoPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;

/**
 * 分娩结局_分娩信息
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 wjc 初版
 */
@Service
public class BirthEndingBaseInfoServiceImpl extends BaseService implements BirthEndingBaseInfoService {

    @Resource
    private BirthEndingBaseInfoDAO birthBaseDAO;

    @Resource
    private BirthEndingDAO birthEndingDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingBaseInfoPojo saveBirthBabyInfo(BirthEndingBaseInfoPojo birthPojo) {
        BirthEndingBaseInfo birthBaseInfo = TransformerUtils.transformerProperties(BirthEndingBaseInfo.class, birthPojo);
        if (birthBaseInfo == null || StringUtils.isBlank(birthBaseInfo.getBirthId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        birthBaseDAO.save(birthBaseInfo);
        return birthBaseDAO.getTransformerBean(birthBaseInfo.getBaseId(), BirthEndingBaseInfo.class,
                BirthEndingBaseInfoPojo.class);

    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public BirthEndingBaseInfoPojo getBaseByBirthId(String birthId) {
        return birthBaseDAO.getBaseByBirthById(birthId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteBirthBaseByBirthId(String birthId) {
        birthBaseDAO.deleteResords(birthId);
    }

}
