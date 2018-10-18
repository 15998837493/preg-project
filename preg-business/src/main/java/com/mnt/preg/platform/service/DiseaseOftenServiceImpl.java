/*
 * DiseaseOftenServiceImpl.java    1.0  2017-11-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.main.utils.AutomaticGenerationCoding;
import com.mnt.preg.platform.condition.DiseaseOftenCondition;
import com.mnt.preg.platform.dao.DiseaseOftenDAO;
import com.mnt.preg.platform.entity.DiseaseOften;

/**
 * 常用诊断项目业务实现类
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-21 scd 初版
 */
@Service
public class DiseaseOftenServiceImpl extends BaseService implements DiseaseOftenService {

    @Resource
    private DiseaseOftenDAO diseaseOftenDAO;

    @Override
    @Transactional(readOnly = true)
    public List<DiseaseOften> queryDiseaseOften(DiseaseOftenCondition diseaseOften) {
        return diseaseOftenDAO.queryDiseaseOften(diseaseOften);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addDiseaseOften(DiseaseOften diseaseOften) {
        return (String) diseaseOftenDAO.save(diseaseOften);
    }

    @Override
    @Transactional(readOnly = true)
    public DiseaseOften getDiseaseOften(String diseaseId) {
        return diseaseOftenDAO.get(DiseaseOften.class, diseaseId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public Boolean removeDiseaseOften(String diseaseId) {
        Integer count = diseaseOftenDAO.removeDiseaseOften(diseaseId);
        if (count >= 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public String getDiseaseCode(String insId, String userId) {
        insId = insId == null ? "" : insId;
        userId = userId == null ? "" : userId;
        String maxDiseaseCode = diseaseOftenDAO.getDiseaseCode(insId, userId);
        String newCode = AutomaticGenerationCoding.getInstance().getNextCashNumber(maxDiseaseCode, 6);
        return insId + userId + newCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkDiseaseOftenName(String diseaseName) {
        String userId = this.getLoginUser().getUserPojo().getUserId();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("createUserId", userId);
        conditionMap.put("diseaseName", diseaseName);
        if (this.validName(conditionMap, DiseaseOften.class) > 0) {
            return false;
        }
        return true;
    }
}
