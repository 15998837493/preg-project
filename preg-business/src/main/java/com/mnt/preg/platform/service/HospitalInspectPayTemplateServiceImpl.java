/*
 * AuxiliaryCheckServiceImpl.java    1.0  2017-7-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.dao.HospitalInspectPayTemplateDAO;
import com.mnt.preg.platform.entity.HospitalInspectPayTemplate;

/**
 * 辅助检查项目套餐
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-20 scd 初版
 */
@Service
public class HospitalInspectPayTemplateServiceImpl extends BaseService implements HospitalInspectPayTemplateService {

    @Resource
    private HospitalInspectPayTemplateDAO hospitalInspectPayTemplateDAO;

    @Override
    @Transactional(readOnly = true)
    public List<HospitalInspectPayTemplate> queryAuxiliaryCheck(HospitalInspectPayTemplate condition) {
        return hospitalInspectPayTemplateDAO.queryAuxiliaryCheckByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addAuxiliaryCheck(HospitalInspectPayTemplate auxiliary) {
        return (String) hospitalInspectPayTemplateDAO.save(auxiliary);
    }

    @Override
    @Transactional(readOnly = true)
    public int checktempletNameValid(String templetName) {
        return this.validCode("templetName", templetName, HospitalInspectPayTemplate.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeTemplet(String templetId) {
        HospitalInspectPayTemplate templet = hospitalInspectPayTemplateDAO.get(HospitalInspectPayTemplate.class,
                templetId);
        hospitalInspectPayTemplateDAO.delete(templet);
    }
}
