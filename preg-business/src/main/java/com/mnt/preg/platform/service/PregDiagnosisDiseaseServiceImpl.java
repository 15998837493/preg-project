/*
 * DiseaseOftenServiceImpl.java    1.0  2017-11-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.dao.PregDiagnosisDiseaseDAO;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 接诊诊断项目表维护
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-26 xdc 初版
 */
@Service
public class PregDiagnosisDiseaseServiceImpl extends BaseService implements PregDiagnosisDiseaseService {

    @Resource
    private PregDiagnosisDiseaseDAO diagnosisDiseaseDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public Boolean addDiagnosisDisease(PregDiagnosisDisease disease) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("diagnosisId", disease.getDiagnosisId());
        map.put("diseaseName", disease.getDiseaseName());
        if (validName(map, PregDiagnosisDisease.class) < 1) {
            diagnosisDiseaseDAO.save(disease);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteDiagnosisDisease(String diagnosisId, String diseaseName) {
        diagnosisDiseaseDAO.deleteDiagnosisDisease(diagnosisId, diseaseName);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateDiagnosisDisease(String diagnosisId, String diseaseName) {
        diagnosisDiseaseDAO.updateDiagnosisDisease(diagnosisId, diseaseName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisDisease> queryDiagnosisDiseaseByDiagnosisId(String diagnosisId) {
        return diagnosisDiseaseDAO.queryDiagnosisDiseaseByDiagnosisId(diagnosisId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisPojo> queryDiagnosisDiseaseByCondition(DiagnosisCondition condition) {
        return diagnosisDiseaseDAO.queryDiagnosisDiseaseByCondition(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PregDiagnosisPojo> queryStatisticDiseaseByCondition(DiagnosisCondition condition) {
        return diagnosisDiseaseDAO.queryStatisticDiseaseByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public List<PregDiagnosisDisease> saveDiagnosisDisease(List<PregDiagnosisDisease> diseaseList) {
        List<PregDiagnosisDisease> diseaseReList = new ArrayList<PregDiagnosisDisease>();
        for (PregDiagnosisDisease disease : diseaseList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("diagnosisId", disease.getDiagnosisId());
            map.put("diseaseName", disease.getDiseaseName());
            if (validName(map, PregDiagnosisDisease.class) < 1) {
                if ("2".equals(disease.getDiseaseStatus())) {
                    disease.setDiseaseStatus("1");// 所有历史诊断中的待确诊都忽略指标推断
                }
                this.addDiagnosisDisease(disease);
                diseaseReList.add(disease);
            }
        }
        return diseaseReList;
    }
}
