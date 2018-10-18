/*
 * DiseaseOftenDAO.java    1.0  2017-11-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.entity.PregDiagnosisDisease;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 接诊诊断项目表
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-12-26 xdc 初版
 */
@Repository
public class PregDiagnosisDiseaseDAO extends HibernateTemplate {

    /**
     * 查询指定条件下的诊断项目
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    public List<PregDiagnosisDisease> queryDiagnosisDiseaseByDiagnosisId(String diagnosisId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(PregDiagnosisDisease.class, "Disease")
                + "   FROM user_diagnosis_disease AS Disease "
                + "   WHERE Disease.diagnosis_id=:diagnosisId ";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("diagnosisId", diagnosisId);
        return this.SQLQueryAlias(sql, queryParams, PregDiagnosisDisease.class);
    }

    /**
     * 条件接诊的诊断项目
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    public List<PregDiagnosisPojo> queryDiagnosisDiseaseByCondition(DiagnosisCondition condition) {
        if (condition == null) {
            condition = new DiagnosisCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisVo");
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(PregDiagnosisPojo.class, "DiagnosisVo")
                + "      ,(SELECT GROUP_CONCAT(dis.disease_name ORDER BY dis.disease_code DESC separator '、') "
                + "        FROM user_diagnosis_disease AS dis WHERE dis.diagnosis_id = DiagnosisVo.diagnosis_id "
                + "        ) AS diagnosisDiseases "
                + "        FROM user_diagnosis AS DiagnosisVo"
                + queryCondition.getQueryString()
                // + queryCondition.getOrderString();
                + " ORDER BY DiagnosisVo.diagnosis_date DESC";
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisPojo.class);
    }

    /**
     * 条件接诊的诊断项目（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<PregDiagnosisPojo> queryStatisticDiseaseByCondition(DiagnosisCondition condition) {
        if (condition == null) {
            condition = new DiagnosisCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisVo");
        String querySQL = "SELECT DiagnosisVo.diagnosis_id AS diagnosisId"
                + "      ,(SELECT GROUP_CONCAT(dis.disease_name ORDER BY dis.disease_code DESC separator '、') "
                + "        FROM user_diagnosis_disease AS dis WHERE dis.diagnosis_id = DiagnosisVo.diagnosis_id "
                + "        ) AS diagnosisDiseases "
                + "        FROM user_diagnosis AS DiagnosisVo"
                + queryCondition.getQueryString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisPojo.class);
    }

    /**
     * 根据接诊id和诊断项目名称，删除诊断项目
     * 
     * @author xdc
     * @param diagnosisId
     * @param diseaseName
     */
    public void deleteDiagnosisDisease(String diagnosisId, String diseaseName) {
        String sql = "DELETE FROM user_diagnosis_disease WHERE diagnosis_id=:diagnosisId AND disease_name=:diseaseName";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        paramsMap.put("diseaseName", diseaseName);
        this.executeSQL(sql, paramsMap);
    }

    /**
     * 更新诊断项目状态
     * 
     * @author xdc
     * @param diagnosisId
     * @param diseaseName
     */
    public void updateDiagnosisDisease(String diagnosisId, String diseaseName) {
        String sql = "UPDATE user_diagnosis_disease SET disease_status = '确诊',type='0' WHERE diagnosis_id=:diagnosisId AND disease_name=:diseaseName";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        paramsMap.put("diseaseName", diseaseName);
        this.executeSQL(sql, paramsMap);
    }
}
