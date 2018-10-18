
package com.mnt.preg.examitem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;

/**
 * 
 * 患者接诊-辅助检查项目
 * 
 * @author wsy
 * @version 1.1
 * 
 *          变更履历：
 *          v1.1 2017-4-6 wsy 初版
 */
@Repository
public class DiagnosisQuotaItemDAO extends HibernateTemplate {

    /**
     * 根据主键删除记录
     * 
     * @author wsy
     * @param id
     * @return
     */
    public void deleteDiagnosisQuotaItem(String id) {
        String sql = "DELETE FROM user_diagnosis_quota_item WHERE id=:id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        this.executeSQL(sql, params);
    }

    /**
     * 根据接诊号删除记录
     * 
     * @author wsy
     * @param id
     * @return
     */
    public void deleteDiagnosisQuotaItemByDiagnosisId(String id) {
        String sql = "DELETE FROM user_diagnosis_quota_item WHERE diagnosis_id=:id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        this.executeSQL(sql, params);
    }

    /**
     * 查询患者辅助检测项目
     * 
     * @author wsy
     * @param condition
     * @return
     */
    public List<DiagnosisQuotaItemVo> queryDiagnosisQuotaItem(DiagnosisQuotaItemCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisQuotaItemVo");
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisQuotaItemVo.class, "DiagnosisQuotaItemVo")
                + "       ,inspect.results_suggest AS resultsSuggest"
                + "       ,inspect.review_hints AS reviewHints"
                + "   FROM user_diagnosis_quota_item AS DiagnosisQuotaItemVo"
                + "       JOIN mas_hospital_inspect_pay AS inspect ON DiagnosisQuotaItemVo.inspect_item_id=inspect.inspect_id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiagnosisQuotaItemVo.class);
    }

}
