
package com.mnt.preg.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.platform.condition.DiagnosisItemsCondition;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;

/**
 * 诊疗评价项目DAO（主表：user_diagnosis_inspect）
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-2-18 zcq 初版
 */
@Repository
public class PregDiagnosisItemDAO extends HibernateTemplate {

    /**
     * 查询接诊--评价项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PregDiagnosisItemsVo> queryDiagnosisItem(DiagnosisItemsCondition condition) {
        if (condition == null) {
            condition = new DiagnosisItemsCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "PregDiagnosisItemsVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(PregDiagnosisItemsVo.class, "PregDiagnosisItemsVo")
                + "            ,inspect.inspect_name AS inspectName"
                + "        FROM user_diagnosis_inspect AS PregDiagnosisItemsVo"
                + "        JOIN mas_inspect_item AS inspect ON inspect.inspect_code=PregDiagnosisItemsVo.inspect_code"
                + "            AND inspect.flag=:flag "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), PregDiagnosisItemsVo.class);
    }

    /**
     * 查询接诊--评价项目编码
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    public List<String> queryCurrentDiagnosisItem(String diagnosisId) {
        String sql = "SELECT inspect_code FROM user_diagnosis_inspect WHERE diagnosis_id=:diagnosisId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diagnosisId", diagnosisId);
        return this.SQLQuery(sql, paramMap);
    }

    /**
     * 更改评价项目状态
     * 
     * @author zcq
     * @param inspectCodeList
     * @param inspectStatus
     * @param diagnosisId
     * @param loginUser
     */
    public void updateDiagnosisItemInspectStatus(List<String> inspectCodeList, String inspectStatus, String diagnosisId) {
        if (CollectionUtils.isNotEmpty(inspectCodeList) && StringUtils.isNotBlank(inspectStatus)
                && StringUtils.isNotBlank(diagnosisId)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("inspectStatus", inspectStatus);
            paramMap.put("diagnosisId", diagnosisId);
            paramMap.put("inspectCodeList", inspectCodeList);

            String sql = "";
            if ("2".equals(inspectStatus)) {
                sql = "UPDATE user_diagnosis_inspect"
                        + "   SET inspect_status = :inspectStatus, result_code = '', inspect_datetime = null, inspect_user = '', inspect_user_name = ''"
                        + "   WHERE diagnosis_id = :diagnosisId"
                        + "       AND inspect_code = :inspectCode";
            } else {
                sql = "UPDATE user_diagnosis_inspect"
                        + "   SET inspect_status = :inspectStatus"
                        + "   WHERE diagnosis_id = :diagnosisId"
                        + "       AND inspect_code = :inspectCode";
            }

            this.executeSQL(sql, paramMap);
        }
    }

    /**
     * 删除接诊--评价项目列表
     * 
     * @author zcq
     * @param inspectIdList
     */
    public void deleteDiagnosisInspects(List<String> inspectIdList) {
        if (CollectionUtils.isNotEmpty(inspectIdList)) {
            String sql = "DELETE FROM user_diagnosis_inspect WHERE id IN (:inspectIdList)";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("inspectIdList", inspectIdList);
            this.executeSQL(sql, paramMap);
        }
    }

    /**
     * 删除接诊--评价项目列表
     * 
     * @author zcq
     * @param inspectCodeList
     * @param diagnosisId
     */
    public void deleteDiagnosisInspectsByInspectCode(List<String> inspectCodeList, String diagnosisId) {
        if (CollectionUtils.isNotEmpty(inspectCodeList) && StringUtils.isNotBlank(diagnosisId)) {
            String sql = "DELETE FROM user_diagnosis_inspect WHERE diagnosis_id = :diagnosisId AND inspect_code IN (:inspectCodeList)";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("inspectCodeList", inspectCodeList);
            paramMap.put("diagnosisId", diagnosisId);
            this.executeSQL(sql, paramMap);
        }
    }

    /**
     * 删除接诊--评价项目
     * 
     * @author zcq
     * @param diagnosisId
     * @param inspectItemList
     */
    public void deleteDiagnosisItems(String diagnosisId, List<String> inspectItemList) {
        if (CollectionUtils.isNotEmpty(inspectItemList)) {
            String sql = "DELETE FROM user_diagnosis_inspect WHERE diagnosis_id=:diagnosisId AND inspect_code NOT IN (:inspectItemList)";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("diagnosisId", diagnosisId);
            paramMap.put("inspectItemList", inspectItemList);
            this.executeSQL(sql, paramMap);
        }
    }

    /**
     * 根据接诊id查询其下收费项目对应的指标
     * 
     * @author xdc
     * @param diagnosisId
     * @return
     */
    public List<ItemPojo> queryQuotaItemByDiagnosisId(String diagnosisId) {
        String sql = "SELECT DISTINCT " + DaoUtils.getSQLFields(ItemPojo.class, "ItemPojo")
                + "       ,payConfig.inspect_id AS inspectPayId "
                + "   FROM mas_item AS ItemPojo "
                + "   JOIN mas_hospital_inspect_pay_config AS payConfig ON ItemPojo.item_id = payConfig.item_id "
                + "   JOIN user_diagnosis_quota_item AS quota ON quota.inspect_item_id = payConfig.inspect_id "
                + "   WHERE quota.diagnosis_id =:diagnosisId "
                + "       AND ItemPojo.is_main = :isMain "
                + "       AND ItemPojo.flag = :flag";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diagnosisId", diagnosisId);
        paramMap.put("isMain", "1");
        paramMap.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(sql, paramMap, ItemPojo.class);
    }
}
