
package com.mnt.preg.platform.service;

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
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.dao.PregArchivesDAO;
import com.mnt.preg.platform.entity.PregArchives;

/**
 * 
 * 孕期建档管理实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 gss 初版
 */
@Service
public class PregArchivesServiceImpl extends BaseService implements PregArchivesService {

    @Resource
    private PregArchivesDAO pregArchivesDAO;

    @Override
    @Transactional(readOnly = true)
    public PregArchivesPojo getLastPregnancyArchive(String custId) {
        return pregArchivesDAO.getLastPregnancyArchive(custId);
    }

    // @Override
    // @Transactional(rollbackFor = HibernateException.class)
    // public String saveRegnancyArchives(PregArchives pregnancyArchives) {
    // if (pregnancyArchives == null) {
    // throw new CoreServiceException(ResultCode.ERROR_90013);
    // }
    // return (String) pregArchivesDAO.save(pregnancyArchives);
    // }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updatePregnancyArchives(PregArchives pregnancyArchives) {
        if (pregnancyArchives == null || StringUtils.isEmpty(pregnancyArchives.getId())) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), pregnancyArchives.getId()));

        int count = pregArchivesDAO.updateHQL(pregnancyArchives, conditionParams);
        if (count != 1) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
    }

    // @Override
    // @Transactional(readOnly = true)
    // public PregArchivesVo getPregnancyArchiveBydiagnosisId(String diagnosisId) {
    // return pregArchivesDAO.getPregnancyArchiveBydiagnosisId(diagnosisId);
    // }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteAndSavePregnancyArchives(PregArchives pregnancyArchives) {
        if (pregnancyArchives == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        if (StringUtils.isNotEmpty(pregnancyArchives.getId())) {
            pregArchivesDAO.deletePregnancyArchives(pregnancyArchives.getId());
        }
        pregArchivesDAO.save(pregnancyArchives);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregArchivesPojo> queryCustomerPregRecprd(String custId) {
        return pregArchivesDAO.queryCustomerPregRecprd(custId);
    }

    @Override
    @Transactional(readOnly = true)
    public PregArchivesPojo getPregArchivesPojoById(String id) {
        return pregArchivesDAO.getTransformerBean(id, PregArchives.class, PregArchivesPojo.class);
    }

}
