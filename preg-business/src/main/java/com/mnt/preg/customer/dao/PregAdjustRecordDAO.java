
package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.customer.entity.PregAdjustRecord;

/**
 * 客户DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
@Repository
public class PregAdjustRecordDAO extends HibernateTemplate {

    /**
     * 查询孕周调整的历史记录
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<PregAdjustRecord> queryPregAdjustRecords(PregAdjustRecord condition) {
        if (condition == null) {
            condition = new PregAdjustRecord();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregAdjustRecord");
        String sql = "SELECT " + DaoUtils.getSQLFields(PregAdjustRecord.class, "PregAdjustRecord")
                + "   FROM cus_pregnancy_adjust_record AS PregAdjustRecord "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), PregAdjustRecord.class);
    }

    /**
     * 查询孕周调整的历史记录
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public PregAdjustRecord getPregAdjustRecordsByDiagnosisId(String diagnosisId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        String sql = "SELECT " + DaoUtils.getSQLFields(PregAdjustRecord.class, "PregAdjustRecord")
                + "   FROM cus_pregnancy_adjust_record AS PregAdjustRecord "
                + "   WHERE diagnosis_id=:diagnosisId";
        return this.SQLQueryAliasFirst(sql, paramsMap, PregAdjustRecord.class);
    }

    /**
     * 根据id删除诊断记录
     * 
     * @author xdc
     * @param diagnosisId
     */
    public void deletePregAdjustResords(String diagnosisId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("diagnosisId", diagnosisId);
        this.executeSQL("DELETE FROM cus_pregnancy_adjust_record WHERE diagnosis_id=:diagnosisId", paramsMap);
    }
}
