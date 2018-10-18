
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
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.platform.condition.DiagnosisHospitalInspectReportCondition;
import com.mnt.preg.platform.condition.DiagnosisHospitalItemCondition;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectPayPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;

/**
 * 接诊实验室检验项目管理
 * 
 * @author mlq
 * 
 *         变更履历：
 *         2018-06-21 mlq
 */
@Repository
public class DiagnosisHospitalItemDAO extends HibernateTemplate {

    /**
     * 根据报告单id查询检验项目信息（携带检验项目上次结果结论）
     * 
     * @author mlq
     * @param reportId
     * @param inspectId
     * @return
     */
    public List<DiagnosisHospitalItemPojo> queryDiagnosisItems(String reportId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reportId", reportId);
        paramMap.put("flag", Flag.normal.getValue());
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalItemPojo.class, "item")
                + "   FROM user_diagnosis_hospital_item AS item"
                + "   WHERE item.report_id =:reportId AND item.flag =:flag ORDER BY item_order ASC";
        return this.SQLQueryAlias(sql, paramMap, DiagnosisHospitalItemPojo.class);
    }

    /**
     * 根据问诊id查询检验项目信息
     * 
     * @author mlq
     * @param diagnosisId
     * @return
     */
    public List<DiagnosisHospitalItemPojo> queryDiagnosisItemsByDiagnosisId(String diagnosisId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diagnosisId", diagnosisId);
        paramMap.put("flag", Flag.normal.getValue());
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalItemPojo.class, "item")
                + "   FROM user_diagnosis_hospital_item AS item"
                + "   RIGHT JOIN user_diagnosis_hospital_inspect_report AS report ON report.report_id = item.report_id"
                + "   WHERE item.flag =:flag AND report.diagnosis_id  =:diagnosisId";
        return this.SQLQueryAlias(sql, paramMap, DiagnosisHospitalItemPojo.class);
    }

    /**
     * 根据报告单id查询收费项目信息
     * 
     * @author mlq
     * @param reportId
     * @return
     */
    public List<DiagnosisHospitalInspectPayPojo> queryDiagnosisInspectPays(String reportId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reportId", reportId);
        paramMap.put("flag", Flag.normal.getValue());
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalInspectPayPojo.class, "inspectPay")
                + "   ,(SELECT COUNT(id) FROM user_diagnosis_hospital_item AS item "
                + "         WHERE item.report_id =:reportId AND item.inspect_id = inspectPay.inspect_id AND item.flag =:flag) AS numInspectItems"
                + "   FROM user_diagnosis_hospital_inspect_pay AS inspectPay"
                + "   WHERE inspectPay.report_id =:reportId AND inspectPay.flag =:flag";
        return this.SQLQueryAlias(sql, paramMap, DiagnosisHospitalInspectPayPojo.class);
    }

    /**
     * 查询报告单信息
     * 
     * @author mlq
     * @param diagnosisId
     * @return
     */
    public List<DiagnosisHospitalInspectReportPojo> queryDiagnosisReports(String diagnosisId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diagnosisId", diagnosisId);
        paramMap.put("flag", Flag.normal.getValue());
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalInspectReportPojo.class, "report")
                + "   FROM user_diagnosis_hospital_inspect_report AS report"
                + "   WHERE report.diagnosis_id =:diagnosisId AND report.flag =:flag";
        return this.SQLQueryAlias(sql, paramMap, DiagnosisHospitalInspectReportPojo.class);
    }

    /**
     * 更新报告单状态
     * 
     * @author mlq
     * @param diagnosisId
     * @param status
     */
    public void updateDiagnosisHospitalItemReportByDiagnosisId(String diagnosisId, int status) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "UPDATE user_diagnosis_hospital_inspect_report SET report_status=:status WHERE diagnosis_id=:diagnosisId";
        paramMap.put("diagnosisId", diagnosisId);
        paramMap.put("status", status);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除报告
     * 
     * @author mlq
     * @param reportId
     */
    public void deleteDiagnosisHospitalInspectReport(String reportId) {
        String sql = "DELETE FROM user_diagnosis_hospital_inspect_report WHERE report_id =:reportId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reportId", reportId);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除空的报告单
     * 
     * @author mlq
     * @param diagnosisId
     */
    public void deleteDiagnosisHospitalEmptyReport(String diagnosisId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "DELETE FROM user_diagnosis_hospital_inspect_report "
                + "WHERE report_id NOT IN (SELECT report_id FROM user_diagnosis_hospital_item GROUP BY report_id) ";

        if (StringUtils.isNotEmpty(diagnosisId)) {
            sql = sql + " AND diagnosis_id=:diagnosisId";
            paramMap.put("diagnosisId", diagnosisId);
        }
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除收费项目
     * 
     * @author mlq
     * @param reportId
     * @param inspectId
     */
    public void deleteDiagnosisHospitalInspectPay(String reportId, String inspectId) {
        String sql = "DELETE FROM user_diagnosis_hospital_inspect_pay WHERE report_id =:reportId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reportId", reportId);
        if (StringUtils.isNotEmpty(inspectId)) {
            sql = sql + " AND inspect_id=:inspectId";
            paramMap.put("inspectId", inspectId);
        }
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除空的收费项目
     * 
     * @author mlq
     * @param custId
     * @param diagnosisId
     */
    public void deleteDiagnosisHospitalEmptyPay(String custId, String diagnosisId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("custId", custId);
        paramMap.put("diagnosisId", diagnosisId);
        String sql = "DELETE FROM user_diagnosis_hospital_inspect_pay "
                + "WHERE report_id IN (SELECT report_id FROM user_diagnosis_hospital_inspect_report WHERE diagnosis_id = :diagnosisId) "
                + "AND inspect_id NOT IN (SELECT inspect_id FROM user_diagnosis_hospital_item WHERE cust_Id = :custId)";
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除检验项目
     * 
     * @author mlq
     * @param reportId
     * @param inspectId
     * @param itemId
     */
    public void deleteDiagnosisHospitalItem(String reportId, String inspectId, String itemId) {
        String sql = "DELETE FROM user_diagnosis_hospital_item WHERE report_id =:reportId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reportId", reportId);
        if (StringUtils.isNotEmpty(inspectId)) {
            sql = sql + " AND inspect_id=:inspectId";
            paramMap.put("inspectId", inspectId);
        }
        if (StringUtils.isNotEmpty(itemId)) {
            sql = sql + " AND item_id=:itemId";
            paramMap.put("itemId", itemId);
        }
        this.executeSQL(sql, paramMap);
    }

    /**
     * 根据主键删除检验项目
     * 
     * @author mlq
     * @param id
     */
    public void deleteDiagnosisHospitalItemById(String id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "DELETE FROM user_diagnosis_hospital_item WHERE id=:id";
        paramMap.put("id", id);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 删除未勾选的检验项目
     * 
     * @author mlq
     * @param custId
     * @param diagnosisId
     * @param ids
     */
    public void deleteDiagnosisHospitalItems(String custId, String diagnosisId, String ids) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("custId", custId);
        paramMap.put("diagnosisId", diagnosisId);
        paramMap.put("ids", ids.split(","));
        paramMap.put("reportStatus", 2);
        String sql = "DELETE FROM user_diagnosis_hospital_item "
                + "WHERE cust_Id=:custId "
                + "AND report_id IN (SELECT report_id FROM user_diagnosis_hospital_inspect_report WHERE diagnosis_id =:diagnosisId AND report_status =:reportStatus) "
                + "AND id NOT IN (:ids)";
        this.executeSQL(sql, paramMap);
    }

    /**
     * 查询上次接诊的所有收费项目
     * 
     * @author mlq
     * @param diagnosisId
     */
    public List<DiagnosisHospitalInspectPayPojo> queryLastDiagnosisInspects(String diagnosisId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("diagnosisId", diagnosisId);
        paramMap.put("flag", Flag.normal.getValue());
        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalInspectPayPojo.class, "inspect")
                + "   FROM user_diagnosis_hospital_inspect_pay AS inspect"
                + "   WHERE inspect.report_id IN (SELECT report_id FROM user_diagnosis_hospital_inspect_report WHERE diagnosis_Id =:diagnosisId AND flag =:flag)";
        return this.SQLQueryAlias(sql, paramMap, DiagnosisHospitalInspectPayPojo.class);
    }

    // ===================================================【历史检验数据查询】=======================================================

    /**
     * 条件检索历史检验项目
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<DiagnosisHospitalItemPojo> queryDiagnosisHospitalItemByCondition(
            DiagnosisHospitalItemCondition condition) {
        if (condition == null) {
            condition = new DiagnosisHospitalItemCondition();
        }

        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisHospitalItemPojo");
        String creatTime = JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6);
        if (StringUtils.isNotBlank(condition.getDiagnosisDate())) {
            creatTime = condition.getDiagnosisDate();
        }

        // 历史检验报告（传入reportId）包括本次
        if (StringUtils.isNotBlank(condition.getItemId())) {
            queryCondition.setQueryString(queryCondition.getQueryString()
                    + " AND DiagnosisHospitalItemPojo.create_time < :creatTime");
            queryCondition.getQueryParams().put("creatTime", creatTime);
        }

        // 数据统计分析（问诊id集合传入）
        if (CollectionUtils.isNotEmpty(condition.getDiagnosisIds())) {
            queryCondition.setQueryString(queryCondition.getQueryString()
                    + " GROUP BY DiagnosisHospitalInspectReportPojo.diagnosis_id,DiagnosisHospitalItemPojo.item_id");
        }

        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalItemPojo.class, "DiagnosisHospitalItemPojo")
                + "        ,DiagnosisHospitalInspectReportPojo.diagnosis_id AS diagnosisId"
                + "        ,DiagnosisHospitalInspectReportPojo.report_date AS reportDate"
                + "        ,DiagnosisHospitalInspectReportPojo.gestational_weeks AS gestationalWeeks"
                + "   FROM user_diagnosis_hospital_item AS DiagnosisHospitalItemPojo"
                + "   JOIN user_diagnosis_hospital_inspect_report AS DiagnosisHospitalInspectReportPojo"
                + "        ON DiagnosisHospitalInspectReportPojo.report_id = DiagnosisHospitalItemPojo.report_id"
                + queryCondition.getQueryString()
                + "   ORDER BY DiagnosisHospitalItemPojo.create_time DESC,reportDate DESC,DiagnosisHospitalItemPojo.item_order ASC";

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiagnosisHospitalItemPojo.class);
    }

    /**
     * 条件检索检验项目(统计)
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<DiagnosisHospitalItemPojo> queryStatisticHospitalItemByCondition(DiagnosisHospitalItemCondition condition) {
        if (condition == null) {
            condition = new DiagnosisHospitalItemCondition();
        }

        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "itemPojo");
        String sql = "SELECT itemPojo.diagnosis_id AS diagnosisId"
                + "        ,itemPojo.item_id AS itemId,itemPojo.item_name AS itemName"
                + "        ,reportPojo.report_date AS reportDate"
                + "        ,itemPojo.item_value AS itemValue"
                + "        FROM user_diagnosis_hospital_item AS itemPojo"
                + "        LEFT JOIN user_diagnosis_hospital_inspect_report AS reportPojo ON reportPojo.report_id = itemPojo.report_id"
                + queryCondition.getQueryString()
                + "   GROUP BY diagnosisId,itemId"
                + "   ORDER BY diagnosisId,itemId";

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiagnosisHospitalItemPojo.class);
    }

    /**
     * 条件检索检验报告单
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<DiagnosisHospitalInspectReportPojo> queryDiagnosisHospitalInspectReportByCondition(
            DiagnosisHospitalInspectReportCondition condition) {
        if (condition == null) {
            condition = new DiagnosisHospitalInspectReportCondition();
        }

        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "DiagnosisHospitalInspectReportPojo");
        if (StringUtils.isNotBlank(condition.getDiagnosisDate())) {
            queryCondition.setQueryString(queryCondition.getQueryString()
                    + " AND diagnosis.diagnosis_date LIKE :diagnosisDate");
            queryCondition.getQueryParams().put("diagnosisDate", condition.getDiagnosisDate() + "%");
        }

        String sql = "SELECT "
                + DaoUtils.getSQLFields(DiagnosisHospitalInspectReportPojo.class, "DiagnosisHospitalInspectReportPojo")
                + "        ,diagnosis.diagnosis_date AS diagnosisDate"
                + "        ,diagnosis.diagnosis_user_name AS diagnosisDoctor"
                + "   FROM user_diagnosis_hospital_inspect_report AS DiagnosisHospitalInspectReportPojo"
                + "   JOIN user_diagnosis AS diagnosis ON diagnosis.diagnosis_id=DiagnosisHospitalInspectReportPojo.diagnosis_id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();

        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiagnosisHospitalInspectReportPojo.class);
    }

}
