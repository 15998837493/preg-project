/**
 * 
 */

package com.mnt.preg.customer.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.customer.dao.PregAdjustRecordDAO;
import com.mnt.preg.customer.entity.PregAdjustRecord;
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
public class PregAdjustRecordServiceImpl extends BaseService implements PregAdjustRecordService {

    @Resource
    private PregAdjustRecordDAO pregAdjustRecordDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PregAdjustRecord> queryPregAdjustRecords(PregAdjustRecord condition) {
        return pregAdjustRecordDAO.queryPregAdjustRecords(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PregAdjustRecord getPregAdjustRecordsByDiagnosisId(String diagnosisId) {
        return pregAdjustRecordDAO.getPregAdjustRecordsByDiagnosisId(diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deletePregAdjustResords(String diagnosisId) {
        pregAdjustRecordDAO.deletePregAdjustResords(diagnosisId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updatePregAdjustResords(PregAdjustRecord pregAdjustRecord) {
        if (pregAdjustRecord == null) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("diagnosisId", HQLSymbol.EQ.toString(), pregAdjustRecord
                .getDiagnosisId()));
        pregAdjustRecordDAO.updateHQL(pregAdjustRecord, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void addPregAdjustResords(PregAdjustRecord pregAdjustRecord) {
        pregAdjustRecordDAO.save(pregAdjustRecord);
    }

}
