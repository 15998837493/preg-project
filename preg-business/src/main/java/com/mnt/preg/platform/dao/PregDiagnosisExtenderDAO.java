
package com.mnt.preg.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.platform.condition.DiagnosisPrescriptionCondition;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisExtenderPojo;

/**
 * 诊疗补充剂DAO（主表：user_diagnosis_extender）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-2-18 zcq 初版
 */
@Repository
public class PregDiagnosisExtenderDAO extends HibernateTemplate {

    /**
     * 计量评估--检索补充剂信息
     * 
     * @author zcq
     * @param planId
     * @return
     */
    public List<PregDiagnosisExtenderPojo> queryDiagnosisExtenderByDiagnosisId(String diagnosisId) {
        if (StringUtils.isEmpty(diagnosisId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(PregDiagnosisExtenderPojo.class, "DiagnosisExtenderVo")
                + "   FROM user_diagnosis_extender AS DiagnosisExtenderVo"
                + "   WHERE DiagnosisExtenderVo.diagnosis_id=:diagnosisId"
                + "       AND DiagnosisExtenderVo.flag=:flag";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        paramsMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramsMap, PregDiagnosisExtenderPojo.class);
    }

    /**
     * 计量评估--删除接诊补充剂
     * 
     * @author zcq
     * @param diagnosisId
     */
    public void deleteDiagnosisExtender(String diagnosisId) {
        if (StringUtils.isEmpty(diagnosisId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        this.executeSQL("DELETE FROM user_diagnosis_extender WHERE diagnosis_id=:diagnosisId", paramsMap);
    }

    /**
     * 营养处方--检索补充剂信息
     * 
     * @author zcq
     * @param planId
     * @return
     */
    public List<DiagnosisPrescriptionPojo> queryDiagnosisPrescriptionByDiagnosisId(String diagnosisId) {
        if (StringUtils.isEmpty(diagnosisId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        String sql = "SELECT " + DaoUtils.getSQLFields(DiagnosisPrescriptionPojo.class, "DiagnosisPrescriptionPojo")
                + "   FROM user_diagnosis_prescription AS DiagnosisPrescriptionPojo"
                + "   WHERE DiagnosisPrescriptionPojo.diagnosis_id=:diagnosisId"
                + "       AND DiagnosisPrescriptionPojo.flag=:flag";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        paramsMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramsMap, DiagnosisPrescriptionPojo.class);
    }

    /**
     * 营养处方--检索补充剂信息（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<DiagnosisPrescriptionPojo> queryDiagnosisPrescriptionByCondition(
            DiagnosisPrescriptionCondition condition) {
        if (condition == null) {
            condition = new DiagnosisPrescriptionCondition();
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "diagnosisPrescriptionPojo");
        // sql语法
        String sql = "SELECT "
                + "   diagnosisPrescriptionPojo.product_name AS productName"
                + "   ,diagnosisPrescriptionPojo.product_frequency AS productFrequency"
                + "   ,diagnosisPrescriptionPojo.product_dosage AS productDosage"
                + "   ,diagnosisPrescriptionPojo.product_unit AS productUnit"
                + "   ,diagnosisPrescriptionPojo.status AS status"
                + "   ,diagnosisPrescriptionPojo.diagnosis_id AS diagnosisId"
                + "   FROM user_diagnosis_prescription AS diagnosisPrescriptionPojo "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiagnosisPrescriptionPojo.class);
    }

    /**
     * 营养处方--检索补充剂信息（最大值）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public DiagnosisPrescriptionPojo getMaxProductListByCondition(DiagnosisPrescriptionCondition condition) {
        if (condition == null) {
            condition = new DiagnosisPrescriptionCondition();
        }
        // 设置查询条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "diagnosisPrescriptionPojo");
        // sql语法
        String sql = "SELECT COUNT(diagnosisPrescriptionPojo.id) AS productCount"
                + "   FROM user_diagnosis_prescription AS diagnosisPrescriptionPojo "
                + queryCondition.getQueryString()
                + " GROUP BY diagnosisPrescriptionPojo.diagnosis_id"
                + " ORDER BY productCount DESC limit 1";
        return this.SQLQueryAliasFirst(sql, queryCondition.getQueryParams(), DiagnosisPrescriptionPojo.class);
    }

    /**
     * 营养处方--检索最近一次营养处方
     * 
     * @author zcq
     * @param custId
     * @return
     */
    public List<DiagnosisPrescriptionPojo> queryLastDiagnosisPrescription(String custId) {
        if (StringUtils.isEmpty(custId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        // 查询所有接诊信息
        String diagnosisSQL = "SELECT diagnosis_id "
                + "            FROM user_diagnosis"
                + "            WHERE diagnosis_customer=:custId"
                + "                AND diagnosis_date <:diagnosisDate"
                + "                AND flag=:flag"
                + "            ORDER BY diagnosis_date DESC";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("custId", custId);
        paramsMap.put("diagnosisDate", JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6));
        paramsMap.put("flag", 1);
        List<String> diagnosisIdList = this.SQLQuery(diagnosisSQL, paramsMap);

        if (CollectionUtils.isEmpty(diagnosisIdList)) {
            return null;
        }

        // 过滤最近一次有数据的接诊号
        String countSQL = "SELECT COUNT(id) FROM user_diagnosis_prescription WHERE diagnosis_id=:diagnosisId";
        String diagnosisId = "";
        for (String id : diagnosisIdList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("diagnosisId", id);
            if (this.SQLCount(countSQL, map) > 0) {
                diagnosisId = id;
                break;
            }
        }

        // 查询最近一次有数据的营养处方信息
        String sql = "SELECT " + DaoUtils.getSQLFields(DiagnosisPrescriptionPojo.class, "DiagnosisPrescriptionPojo")
                + "   FROM user_diagnosis_prescription AS DiagnosisPrescriptionPojo"
                + "   WHERE DiagnosisPrescriptionPojo.diagnosis_id=:diagnosisId";
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("diagnosisId", diagnosisId);

        return this.SQLQueryAlias(sql, queryMap, DiagnosisPrescriptionPojo.class);
    }

    /**
     * 营养处方--删除营养处方
     * 
     * @author zcq
     * @param diagnosisId
     */
    public void deleteDiagnosisPrescription(String diagnosisId) {
        if (StringUtils.isEmpty(diagnosisId)) {
            throw new ServiceException(ResultCode.ERROR_90013);
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        this.executeSQL("DELETE FROM user_diagnosis_prescription WHERE diagnosis_id=:diagnosisId", paramsMap);
    }

}
