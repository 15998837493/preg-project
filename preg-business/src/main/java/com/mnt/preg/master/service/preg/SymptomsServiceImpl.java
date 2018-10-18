
package com.mnt.preg.master.service.preg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.condition.preg.SymptomsCondition;
import com.mnt.preg.master.dao.preg.SymptomsDAO;
import com.mnt.preg.master.entity.preg.Symptoms;
import com.mnt.preg.master.pojo.preg.SymptomsPojo;

/**
 * 
 * 功能症状表具体实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Service
public class SymptomsServiceImpl implements SymptomsService {

    /** 功能症状表（mas_symptoms） */
    @Resource
    private SymptomsDAO symptomsDAO;

    @Override
    @Transactional(readOnly = true)
    public List<SymptomsPojo> querySymptoms(SymptomsCondition condition) {
        List<SymptomsPojo> symptomspojo = symptomsDAO.querySymptoms(condition);
        return symptomspojo;
    }

    @Override
    @Transactional(readOnly = true)
    public SymptomsPojo getSymptomsBySymptomsId(String id) {
        return symptomsDAO.getSymptomsBySymptomsId(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public SymptomsPojo saveSymptoms(Symptoms symptoms) {
        String id = (String) symptomsDAO.save(symptoms);
        return symptomsDAO.getTransformerBean(id, Symptoms.class, SymptomsPojo.class);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeSymptoms(String id) {
        // 根据主键id查询
        Symptoms symptoms = symptomsDAO.get(Symptoms.class, id);
        if (symptoms == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        symptoms.setFlag(0);
        this.updateSymptoms(symptoms);
        symptomsDAO.removeSymptomsBySymptomsId(symptoms.getSympCode());
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public SymptomsPojo updateSymptoms(Symptoms symptoms) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("id", HQLSymbol.EQ.toString(), symptoms.getId()));
        symptomsDAO.updateHQL(symptoms, conditionParams);
        return symptomsDAO.getTransformerBean(symptoms.getId(), Symptoms.class, SymptomsPojo.class);
    }

}
