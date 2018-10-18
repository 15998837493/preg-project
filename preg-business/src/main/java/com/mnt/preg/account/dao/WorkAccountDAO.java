
package com.mnt.preg.account.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.account.condition.StatisticaListCondition;
import com.mnt.preg.account.condition.WorkAccountCondition;
import com.mnt.preg.account.pojo.StatisticaListPojo;
import com.mnt.preg.account.pojo.WorkAccountPojo;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.main.results.ResultCode;

/**
 * 工作量统计
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-2-5 xdc 初版
 */
@Repository
public class WorkAccountDAO extends HibernateTemplate {

    // --------------------------------------------------【许道川】--------------------------------------------------

    /**
     * 工作量统计汇总 条件查询
     * 
     * @author xdc
     * @param condition
     * @return
     */
    public List<WorkAccountPojo> queryWorkAccount(WorkAccountCondition condition) {

        // 1. 判断是否有诊断项目，添加诊断项目检索条件
        String minDate = condition.getStartDate();
        String maxDate = condition.getEndDate();
        List<String> monthList = getMonthBetween(minDate, maxDate);
        String diseaseSql = "";
        if (StringUtils.isNotEmpty(condition.getDiseaseId())) {
            diseaseSql = " AND diag1.diagnosis_id IN (SELECT disease.diagnosis_id " +
                    " FROM user_diagnosis_disease AS disease " +
                    " WHERE disease.disease_id = '" +
                    condition.getDiseaseId() + "')";
        }
        String referralDoctorSql = "";
        String evenTableSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisZhuanUser())) {
            referralDoctorSql = " AND diag1.diagnosis_referral_doctor IN ("
                    + this.formatStr(condition.getDiagnosisZhuanUser()) + ")";
        }
        String diagnosisMasItemsSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItems())) {// 系统营养评价项目
            diagnosisMasItemsSql = " AND user_item.inspect_code IN ("
                    + this.formatStr(condition.getDiagnosisMasItems()) + ")";
            evenTableSql = " LEFT JOIN user_diagnosis_inspect AS user_item ON user_item.diagnosis_id = diag1.diagnosis_id";
        }
        String diagnosisMasItemAuthorSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItemAuthors())) {// 系统营养评价项目操作者
            diagnosisMasItemAuthorSql = " AND user_item.inspect_user IN ("
                    + condition.getDiagnosisMasItemAuthors() + ")";
        }
        // 添加总计数据
        String pregStageSql = "AND diag1.diagnosis_pregnant_stage IS NOT NULL";
        String totalWhere = evenTableSql + " WHERE diag1.diagnosis_date BETWEEN '" + condition.getStartDate()
                + "' AND DATE_ADD('" + condition.getEndDate()
                + "', INTERVAL 86399 SECOND) AND diag1.diagnosis_user IN ("
                + condition.getDiagnosisUser() + ") " + diseaseSql + referralDoctorSql + diagnosisMasItemsSql
                + diagnosisMasItemAuthorSql
                + pregStageSql;

        /*
         * 分析不同日期上下限情况：
         * 1.日期同一个月，并且是同一天
         * 2.日期在同一个月，但是不是同一天
         * 3.日期在不同月
         */
        if (monthList.size() == 1) {
            Map<String, Object> queryParams = new HashMap<String, Object>();
            String whereSql = evenTableSql
                    + " WHERE diagnosis_date BETWEEN :startDate AND DATE_ADD(:endDate, INTERVAL 86399 SECOND) AND diagnosis_user IN ("
                    + condition.getDiagnosisUser() + ") "
                    + diseaseSql + referralDoctorSql + diagnosisMasItemsSql + diagnosisMasItemAuthorSql + pregStageSql;
            queryParams.put("startDate", condition.getStartDate());
            queryParams.put("endDate", condition.getEndDate());
            String sql = getQuerySql(monthList.get(0), whereSql);
            return this.SQLQueryAlias(sql + " UNION " + getQuerySql("总计", totalWhere), queryParams,
                    WorkAccountPojo.class);
        } else {
            // 处理不在同一个月的情况
            List<String> querySql = new ArrayList<String>();
            for (int i = 0; i < monthList.size(); i++) {
                String whereSql = "";
                WorkAccountCondition conditionNew = TransformerUtils.transformerProperties(
                        WorkAccountCondition.class, condition);
                if (i == 0) {
                    // 设置当月的最大时间组成区间
                    whereSql = String
                            .format(
                                    evenTableSql
                                            + " WHERE diag1.diagnosis_date BETWEEN '%s' AND DATE_ADD('%s', INTERVAL 86399 SECOND) AND diagnosis_user IN (%s)",
                                    conditionNew.getStartDate(),
                                    JodaTimeTools.toString(
                                            getMonthMaxDate(JodaTimeTools.toDate(conditionNew.getStartDate())),
                                            JodaTimeTools.FORMAT_6),
                                    condition.getDiagnosisUser());
                } else if (i == monthList.size() - 1) {
                    // 设置当月的最小时间组成区间
                    whereSql = String
                            .format(
                                    evenTableSql
                                            + " WHERE diag1.diagnosis_date BETWEEN '%s' AND DATE_ADD('%s', INTERVAL 86399 SECOND) AND diagnosis_user IN (%s)",
                                    JodaTimeTools.toString(
                                            getMonthMinDate(JodaTimeTools.toDate(conditionNew.getEndDate())),
                                            JodaTimeTools.FORMAT_6),
                                    conditionNew.getEndDate(),
                                    condition.getDiagnosisUser());
                } else {
                    // 获取模糊时间
                    whereSql = evenTableSql + " WHERE diag1.diagnosis_date LIKE '"
                            + monthList.get(i) + "%' AND diagnosis_user IN ("
                            + condition.getDiagnosisUser() + ")";
                }
                querySql.add(getQuerySql(monthList.get(i), whereSql + diseaseSql + referralDoctorSql
                        + diagnosisMasItemsSql + diagnosisMasItemAuthorSql + pregStageSql));
            }
            querySql.add(getQuerySql("总计", totalWhere));
            String sql = StringUtils.join(querySql, " UNION ");
            System.out.println(sql);
            return this.SQLQueryAlias(sql, WorkAccountPojo.class);
        }
    }

    // --------------------------------------------------【许道川】--------------------------------------------------

    // --------------------------------------------------【尚成达】--------------------------------------------------

    /**
     * 
     * 获取统计列表历史数据（日期小于今日的）
     * 
     * @author dhs
     * @param condition
     * @return
     */
    public List<StatisticaListPojo> queryStatisticsDataHistory() {
        String sql = "SELECT DISTINCT "
                + DaoUtils.getSQLFields(StatisticaListPojo.class, "statisticaPojo")
                + "   ,(SELECT GROUP_CONCAT(disease.disease_name) FROM user_diagnosis_disease AS disease WHERE disease.diagnosis_id = statisticaPojo.diagnosis_id) AS names"
                + "   FROM user_diagnosis AS statisticaPojo "
                + "   LEFT JOIN cus_pregnancy_archives AS archivesPojo ON archivesPojo.id = statisticaPojo.archives_id"
                + "   LEFT JOIN user_diagnosis_inspect AS inspect ON inspect.diagnosis_id = statisticaPojo.diagnosis_id "
                + "   WHERE statisticaPojo.diagnosis_date < SUBSTRING(NOW(), 1, 10)"
                + "   AND statisticaPojo.flag = :flag"
                + "   AND statisticaPojo.diagnosis_gestational_weeks IS NOT NULL"
                + "   ORDER BY statisticaPojo.diagnosis_date DESC";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramMap, StatisticaListPojo.class);
    }

    /**
     * 
     * 获取统计列表数据
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<StatisticaListPojo> queryStatisticsData(StatisticaListCondition condition) {
        if (condition == null) {
            condition = new StatisticaListCondition();
        }

        // 设置诊断条件
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "statisticaPojo");
        if (StringUtils.isNotEmpty(condition.getDiseaseId())) {
            String diseaseSql = " AND statisticaPojo.diagnosis_id IN (SELECT disease.diagnosis_id " +
                    " FROM user_diagnosis_disease AS disease " +
                    " WHERE disease.disease_id = '" +
                    condition.getDiseaseId() + "')";
            queryCondition.setQueryString(queryCondition.getQueryString() + diseaseSql);
        }

        // 设置检查项目条件
        if (CollectionUtils.isNotEmpty(condition.getDiagnosisMasItems())) {
            String itemSql = " AND (SELECT COUNT(inspect.inspect_code) "
                    + "             FROM user_diagnosis_inspect AS inspect "
                    + "             WHERE inspect.diagnosis_id = statisticaPojo.diagnosis_id "
                    + "             AND inspect.inspect_code IN (\'"
                    + StringUtils.join(condition.getDiagnosisMasItems(), "\',\'") + "\') ) > 0 ";
            queryCondition.setQueryString(queryCondition.getQueryString() + itemSql);
        }

        // 设置检查项目操作者条件
        if (CollectionUtils.isNotEmpty(condition.getDiagnosisMasItemAuthors())) {
            String authorSql = " AND (SELECT COUNT(inspect.inspect_user) "
                    + "             FROM user_diagnosis_inspect AS inspect "
                    + "             WHERE inspect.diagnosis_id = statisticaPojo.diagnosis_id "
                    + "             AND inspect.inspect_user IN ( "
                    + StringUtils.join(condition.getDiagnosisMasItemAuthors(), ",")
                    + ")) > 0 ";
            queryCondition.setQueryString(queryCondition.getQueryString() + authorSql);
        }

        // 设置转诊医生条件
        if (CollectionUtils.isNotEmpty(condition.getDiagnosisZhuanUser())) {
            String userSql = " AND statisticaPojo.diagnosis_referral_doctor IN ("
                    + StringUtils.join(condition.getDiagnosisZhuanUser(), ",") + ") ";
            queryCondition.setQueryString(queryCondition.getQueryString() + userSql);
        }

        // 设置孕周数不为空的条件
        String pregWeek = " AND statisticaPojo.diagnosis_gestational_weeks IS NOT NULL";
        queryCondition.setQueryString(queryCondition.getQueryString() + pregWeek);

        String sql = "SELECT DISTINCT "
                + DaoUtils.getSQLFields(StatisticaListPojo.class, "statisticaPojo")
                + "   ,(SELECT GROUP_CONCAT(disease.disease_name) FROM user_diagnosis_disease AS disease WHERE disease.diagnosis_id = statisticaPojo.diagnosis_id) AS names"
                + "   FROM user_diagnosis AS statisticaPojo "
                + "   LEFT JOIN cus_pregnancy_archives AS archivesPojo ON archivesPojo.id = statisticaPojo.archives_id"
                + "   LEFT JOIN user_diagnosis_inspect AS inspect ON inspect.diagnosis_id = statisticaPojo.diagnosis_id "
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), StatisticaListPojo.class);
    }

    /**
     * 
     * 检索门诊量
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<Object[]> queryOutpatientCount(WorkAccountCondition condition) {
        String minDate = condition.getStartDate();
        String maxDate = condition.getEndDate();
        String diagnosisUser = condition.getDiagnosisUser();
        String diseaseId = condition.getDiseaseId();
        List<String> monthList = getMonthBetween(minDate, maxDate);
        String countSql = "";

        // 处理转诊医生, 检查项目, 检查项目操作者的条件
        String referralDoctorSql = "";
        String evenTableSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisZhuanUser())) {
            referralDoctorSql = " AND diagnosis.diagnosis_referral_doctor IN ("
                    + this.formatStr(condition.getDiagnosisZhuanUser()) + ")";
        }
        String diagnosisMasItemsSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItems())) {// 系统营养评价项目
            diagnosisMasItemsSql = " AND user_item.inspect_code IN ("
                    + this.formatStr(condition.getDiagnosisMasItems()) + ")";
            evenTableSql = " LEFT JOIN user_diagnosis_inspect AS user_item ON user_item.diagnosis_id = diagnosis.diagnosis_id";
        }
        String diagnosisMasItemAuthorSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItemAuthors())) {// 系统营养评价项目操作者
            diagnosisMasItemAuthorSql = " AND user_item.inspect_user IN ("
                    + condition.getDiagnosisMasItemAuthors() + ")";
        }

        String diseaseSql = "";
        if (StringUtils.isNotEmpty(condition.getDiseaseId())) {
            diseaseSql = " AND diagnosis.diagnosis_id IN (SELECT disease.diagnosis_id " +
                    " FROM user_diagnosis_disease AS disease " +
                    " WHERE disease.disease_id = '" + condition.getDiseaseId() + "')";
        }
        /*
         * 分析不同日期上下限情况：
         * 1.日期同一个月，并且是同一天
         * 2.日期在同一个月，但是不是同一天
         * 3.日期在不同月
         */
        if (monthList.size() == 1) {
            countSql = getOutpatientCountSql1(minDate, maxDate, diagnosisUser, diseaseId, condition);
        } else if (monthList.size() > 1) {
            // 处理不在同一个月的情况
            for (int i = 0; i < monthList.size(); i++) {
                if (i == 0) {
                    // 设置当月的最大时间组成区间
                    String lastDay = JodaTimeTools.toString(getMonthMaxDate(JodaTimeTools.toDate(minDate)),
                            JodaTimeTools.FORMAT_6);
                    countSql = countSql
                            + getOutpatientCountSql1(minDate, lastDay, diagnosisUser, diseaseId, condition);
                } else if (i == monthList.size() - 1) {
                    // 设置当月的最小时间组成区间
                    String firstDay = JodaTimeTools.toString(getMonthMinDate(JodaTimeTools.toDate(maxDate)),
                            JodaTimeTools.FORMAT_6);
                    countSql = countSql
                            + getOutpatientCountSql1(firstDay, maxDate, diagnosisUser, diseaseId, condition);
                } else {
                    // 获取模糊时间
                    countSql = countSql + getOutpatientCountSql2(monthList.get(i), diagnosisUser, diseaseId, condition);
                }
            }
        }

        // 设置条件
        String sql = "SELECT"
                + "(SELECT user_name FROM sys_user WHERE user_status = '1' AND user_id =" + diagnosisUser + "),"
                + countSql
                + " (SELECT COUNT(diagnosis.diagnosis_customer) FROM user_diagnosis AS diagnosis "
                + evenTableSql
                + " WHERE diagnosis.diagnosis_user =" + diagnosisUser
                + " AND diagnosis.diagnosis_date BETWEEN '" + minDate + "'"
                + " AND DATE_ADD('" + maxDate + "', INTERVAL 86399 SECOND)"
                + " AND diagnosis.diagnosis_gestational_weeks IS NOT NULL"
                + referralDoctorSql + diagnosisMasItemsSql + diagnosisMasItemAuthorSql + diseaseSql
                + " AND diagnosis.flag = '1') AS 'AllMonth'";

        return SQLQuery(sql);
    }

    /**
     * 
     * 获取门诊量统计sql模板(非整月)
     * 
     * @author scd
     * @param star
     * @param end
     * @param couurTime
     * @param type
     * @return
     */
    public String getOutpatientCountSql1(String star, String end, String userId, String diseaseId,
            WorkAccountCondition condition) {

        String referralDoctorSql = "";
        String evenTableSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisZhuanUser())) {
            referralDoctorSql = " AND diagnosis.diagnosis_referral_doctor IN ("
                    + this.formatStr(condition.getDiagnosisZhuanUser()) + ")";
        }
        String diagnosisMasItemsSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItems())) {// 系统营养评价项目
            diagnosisMasItemsSql = " AND user_item.inspect_code IN ("
                    + this.formatStr(condition.getDiagnosisMasItems()) + ")";
            evenTableSql = " LEFT JOIN user_diagnosis_inspect AS user_item ON user_item.diagnosis_id = diagnosis.diagnosis_id";
        }
        String diagnosisMasItemAuthorSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItemAuthors())) {// 系统营养评价项目操作者
            diagnosisMasItemAuthorSql = " AND user_item.inspect_user IN ("
                    + condition.getDiagnosisMasItemAuthors() + ")";
        }

        String diseaseSql = "";
        if (StringUtils.isNotEmpty(condition.getDiseaseId())) {
            diseaseSql = " AND diagnosis.diagnosis_id IN (SELECT disease.diagnosis_id " +
                    " FROM user_diagnosis_disease AS disease " +
                    " WHERE disease.disease_id = '" + condition.getDiseaseId() + "')";
        }

        String count = "(SELECT " +
                " COUNT(diagnosis.diagnosis_customer) FROM user_diagnosis AS diagnosis " +
                evenTableSql +
                " WHERE diagnosis.diagnosis_user = " + userId +
                " AND diagnosis.diagnosis_date BETWEEN '" + star + "' " +
                " AND DATE_ADD('" + end + "',INTERVAL 86399 SECOND)  " +
                " AND diagnosis.diagnosis_gestational_weeks IS NOT NULL" +
                referralDoctorSql + diagnosisMasItemsSql + diagnosisMasItemAuthorSql + diseaseSql +
                " AND diagnosis.flag='1'";
        if (StringUtils.isNotEmpty(diseaseId)) {
            count = count + diseaseSql;
        }
        count = count + ") AS '" + UUID.randomUUID().toString().substring(0, 5) + "_" + star + "',";

        return count;
    }

    /**
     * 
     * 获取门诊量统计sql模板(非整月)
     * 
     * @author scd
     * @param currentTime
     * @param userId
     * @param diseaseId
     * @return
     */
    public String getOutpatientCountSql2(String currentTime, String userId, String diseaseId,
            WorkAccountCondition condition) {
        String referralDoctorSql = "";
        String evenTableSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisZhuanUser())) {
            referralDoctorSql = " AND diagnosis.diagnosis_referral_doctor IN ("
                    + this.formatStr(condition.getDiagnosisZhuanUser()) + ")";
        }
        String diagnosisMasItemsSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItems())) {// 系统营养评价项目
            diagnosisMasItemsSql = " AND user_item.inspect_code IN ("
                    + this.formatStr(condition.getDiagnosisMasItems()) + ")";
            evenTableSql = " LEFT JOIN user_diagnosis_inspect AS user_item ON user_item.diagnosis_id = diagnosis.diagnosis_id";
        }
        String diagnosisMasItemAuthorSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItemAuthors())) {// 系统营养评价项目操作者
            diagnosisMasItemAuthorSql = " AND user_item.inspect_user IN ("
                    + condition.getDiagnosisMasItemAuthors() + ")";
        }

        String diseaseSql = "";
        if (StringUtils.isNotEmpty(condition.getDiseaseId())) {
            diseaseSql = " AND diagnosis.diagnosis_id IN (SELECT disease.diagnosis_id " +
                    " FROM user_diagnosis_disease AS disease " +
                    " WHERE disease.disease_id = '" + condition.getDiseaseId() + "')";
        }

        String count = "(SELECT " +
                " COUNT(diagnosis.diagnosis_customer) FROM user_diagnosis AS diagnosis " +
                evenTableSql +
                " WHERE diagnosis.diagnosis_user =" + userId +
                " AND   DATE_FORMAT(diagnosis.diagnosis_date,'%Y-%m')  = '" + currentTime + "'" +
                " AND diagnosis.diagnosis_gestational_weeks IS NOT NULL" +
                referralDoctorSql + diagnosisMasItemsSql + diagnosisMasItemAuthorSql + diseaseSql +
                " AND diagnosis.flag='1'";
        if (StringUtils.isNotEmpty(diseaseId)) {
            count = count + diseaseSql;
        }
        count = count + ") AS '" + UUID.randomUUID().toString().substring(0, 5) + "_" + currentTime + "',";
        return count;
    }

    /**
     * 
     * 孕期分布（部门）
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<WorkAccountPojo> gestationDistribution(WorkAccountCondition condition) {
        if (condition == null) {
            condition = new WorkAccountCondition();
        }

        String minDate = condition.getStartDate();
        String maxDate = condition.getEndDate();

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String appendSQL = "";

        // 设置单个医师
        String diagnosisUser = condition.getDiagnosisUser();// 医师列表
        if (StringUtils.isNotEmpty(diagnosisUser)) {
            appendSQL += " AND sysUser.user_id =:diagnosisUser";
            paramsMap.put("diagnosisUser", diagnosisUser);
        }

        // 设置诊断
        String diseaseId = condition.getDiseaseId();
        if (StringUtils.isNotEmpty(diseaseId)) {
            appendSQL += " AND diagnosis.diagnosis_id IN (SELECT disease.diagnosis_id " +
                    " FROM user_diagnosis_disease AS disease " +
                    " WHERE disease.disease_id = '" +
                    condition.getDiseaseId() + "')";
        }

        String sql = "SELECT "
                + "sysUser.user_name AS diagnosisUserName,"
                + getDistributionCountSql(minDate, maxDate, "pregnancy_pre", condition) + "AS pregStagePre,"
                + getDistributionCountSql(minDate, maxDate, "pregnancy_mid", condition) + "AS pregStageMid,"
                + getDistributionCountSql(minDate, maxDate, "pregnancy_late", condition) + "AS pregStageLate"
                + "   FROM sys_user AS sysUser  "
                + " LEFT JOIN user_diagnosis AS diagnosis ON diagnosis.diagnosis_user = sysUser.user_id"
                + " WHERE sysUser.flag ='1' "
                + " AND sysUser.user_status ='1' "
                + appendSQL
                + " GROUP BY sysUser.user_id";

        return this.SQLQueryAlias(sql, paramsMap, WorkAccountPojo.class);
    }

    /**
     * 
     * 获取孕期分布sql模板
     * 
     * @author scd
     * @param minDate
     * @param maxDate
     * @param pregState
     * @return
     */
    public String getDistributionCountSql(String minDate, String maxDate, String pregState,
            WorkAccountCondition condition) {
        String referralDoctorSql = "";
        String evenTableSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisZhuanUser())) {
            referralDoctorSql = " AND diag.diagnosis_referral_doctor IN ("
                    + this.formatStr(condition.getDiagnosisZhuanUser()) + ")";
        }
        String diagnosisMasItemsSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItems())) {// 系统营养评价项目
            diagnosisMasItemsSql = " AND user_item.inspect_code IN ("
                    + this.formatStr(condition.getDiagnosisMasItems()) + ")";
            evenTableSql = " LEFT JOIN user_diagnosis_inspect AS user_item ON user_item.diagnosis_id = diag.diagnosis_id";
        }
        String diagnosisMasItemAuthorSql = "";
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItemAuthors())) {// 系统营养评价项目操作者
            diagnosisMasItemAuthorSql = " AND user_item.inspect_user IN ("
                    + condition.getDiagnosisMasItemAuthors() + ")";
        }

        String diseaseSql = "";
        if (StringUtils.isNotEmpty(condition.getDiseaseId())) {
            diseaseSql = " AND diag.diagnosis_id IN (SELECT disease.diagnosis_id " +
                    " FROM user_diagnosis_disease AS disease " +
                    " WHERE disease.disease_id = '" + condition.getDiseaseId() + "')";
        }

        String countSql = "(SELECT COUNT(DISTINCT diag.diagnosis_customer) "
                + " FROM user_diagnosis AS diag "
                + evenTableSql
                + " WHERE diag.diagnosis_user = sysUser.user_id "
                + " AND diag.diagnosis_date BETWEEN '" + minDate + "'"
                + " AND DATE_ADD('" + maxDate + "', INTERVAL 86399 SECOND)"
                + " AND diag.diagnosis_pregnant_stage ='" + pregState + "'" +
                referralDoctorSql + diagnosisMasItemsSql + diagnosisMasItemAuthorSql + diseaseSql
                + " AND diag.diagnosis_type = 1)";
        return countSql;
    }

    // --------------------------------------------------【尚成达】--------------------------------------------------

    // --------------------------------------------------【董宏生】--------------------------------------------------
    /**
     * 查询在一段日期范围内符合条件的数量（人数：需要去重）
     * 
     * @author dhs
     * @return
     */
    public Integer queryCountDiagnosisScope(WorkAccountCondition condition) {
        String appendSql = "";
        if ("firstDiagnosis".equals(condition.getType())) {// 初诊
            appendSql = "   AND diagnosis_type = 1";
        }
        String sql = "SELECT COUNT(DISTINCT diagnosis_customer) "
                + "   FROM user_diagnosis"
                + "   WHERE diagnosis_date >= :startDate "
                + "   AND diagnosis_date < DATE_ADD('" + condition.getEndDate() + "', INTERVAL 1 DAY)"
                + "   AND flag=:flag"
                + "   AND diagnosis_user = :userId"
                + "   AND diagnosis_pregnant_stage IS NOT NULL"
                + appendSql;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", condition.getStartDate());
        paramMap.put("flag", Flag.normal.getValue());
        paramMap.put("userId", condition.getDiagnosisUser());
        return this.SQLCount(sql, paramMap);
    }

    /**
     * 部门：查询在一段日期范围内符合条件的数量（人数：需要去重）
     * 
     * @author dhs
     * @return
     */
    public List<WorkAccountPojo> queryCountDiagnosisScopeDept(WorkAccountCondition condition) {
        String appendSql = "";
        if ("firstDiagnosis".equals(condition.getType())) {// 初诊
            appendSql = "   AND diagnosis_type = 1";
        }
        String sql = "SELECT diagnosis_user AS diagnosisUser,COUNT(DISTINCT diagnosis_customer) AS diagnosisNum"
                + "   FROM user_diagnosis"
                + "   WHERE diagnosis_date >= :startDate"
                + "   AND diagnosis_date < DATE_ADD('" + condition.getEndDate() + "', INTERVAL 1 DAY)"
                + "   AND flag= :flag"
                + "   AND diagnosis_user IN (" + condition.getDiagnosisUser() + ")"
                + "   AND diagnosis_pregnant_stage IS NOT NULL"
                + appendSql
                + "   GROUP BY diagnosis_user";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", condition.getStartDate());
        paramMap.put("flag", Flag.normal.getValue());
        paramMap.put("userId", condition.getDiagnosisUser());
        return this.SQLQueryAlias(sql, paramMap, WorkAccountPojo.class);
    }

    // --------------------------------------------------【董宏生】--------------------------------------------------

    // --------------------------------------------------【张传强】--------------------------------------------------

    /**
     * 工作量统计--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public Integer countDiseaseFrequency(WorkAccountCondition condition) {
        if (condition == null || StringUtils.isBlank(condition.getDiseaseId())) {
            return null;
        }

        Map<String, Object> paramsMap = new HashMap<String, Object>();

        String appendSQL = "";
        String diseaseId = condition.getDiseaseId();// 疾病主键
        paramsMap.put("diseaseId", diseaseId);
        // 设置单个医师
        String diagnosisUser = condition.getDiagnosisUser();// 医师
        if (StringUtils.isNotBlank(diagnosisUser)) {
            appendSQL += " AND create_user_id = :diagnosisUser";
            paramsMap.put("diagnosisUser", diagnosisUser);
        }
        // 设置多个医师
        List<String> diagnosisUserList = condition.getDiagnosisUserList();// 医师列表
        if (CollectionUtils.isNotEmpty(diagnosisUserList)) {
            appendSQL += " AND create_user_id IN (:diagnosisUserList)";
            paramsMap.put("diagnosisUserList", diagnosisUserList);
        }
        // 设置时间段
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        if (StringUtils.isNotBlank(startDate)) {
            appendSQL += " AND create_time >= :startDate";
            paramsMap.put("startDate", startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            appendSQL += " AND create_time < :endDate";
            paramsMap.put("endDate",
                    JodaTimeTools.toString(JodaTimeTools.after_day(JodaTimeTools.toDate(endDate), 1),
                            JodaTimeTools.FORMAT_6));
        }

        String sql = "SELECT COUNT(disease_id) "
                + "   FROM user_diagnosis_disease "
                + "   WHERE disease_id = :diseaseId"
                + appendSQL;

        return this.SQLCount(sql, paramsMap);
    }

    /**
     * 工作量对比--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<WorkAccountPojo> countCompareDiseaseFrequency(WorkAccountCondition condition) {
        if (condition == null || CollectionUtils.isEmpty(condition.getDiseaseIdList())) {
            return null;
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String appendSQL = " WHERE flag = :flag";
        paramsMap.put("flag", Flag.normal.getValue());
        // 设置时间段
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        if (StringUtils.isNotBlank(startDate)) {
            appendSQL += " AND create_time >= :startDate";
            paramsMap.put("startDate", startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            appendSQL += " AND create_time < :endDate";
            paramsMap.put("endDate",
                    JodaTimeTools.toString(JodaTimeTools.after_day(JodaTimeTools.toDate(endDate), 1),
                            JodaTimeTools.FORMAT_6));
        }
        // 设置疾病列表
        List<String> diseaseIdList = condition.getDiseaseIdList();
        appendSQL += " AND disease_id IN (:diseaseIdList)";
        paramsMap.put("diseaseIdList", diseaseIdList);
        // 设置单个医师
        String diagnosisUser = condition.getDiagnosisUser();
        if (StringUtils.isNotBlank(diagnosisUser)) {
            appendSQL += " AND create_user_id = :diagnosisUser";
            paramsMap.put("diagnosisUser", diagnosisUser);
        }
        // 设置多个医师
        List<String> diagnosisUserList = condition.getDiagnosisUserList();// 医师列表
        if (CollectionUtils.isNotEmpty(diagnosisUserList)) {
            appendSQL += " AND create_user_id IN (:diagnosisUserList)";
            paramsMap.put("diagnosisUserList", diagnosisUserList);
        }

        String sql = "SELECT disease_id AS diseaseId"
                + "      ,create_user_id AS diagnosisUser"
                + "      ,COUNT(disease_id) AS diseaseFrequency"
                + "   FROM user_diagnosis_disease "
                + appendSQL
                + "   GROUP BY diseaseId,diagnosisUser";

        return this.SQLQueryAlias(sql, paramsMap, WorkAccountPojo.class);
    }

    // --------------------------------------------------【张传强】--------------------------------------------------

    /********************************************** 工具方法 ******************************************************/

    /**
     * 获取基础的查询sql
     * 
     * 思路：
     * 1.定义查询不同字段的sql
     * 2.在计算比率时，使用对应的sql计算值即可
     * 3.返回的sql查询month参数对应的月份的数据
     * 
     * @author xdc
     * @param month
     * @param whereSql
     * @return
     */
    private String getQuerySql(String month, String whereSql) {
        // 查询人数
        String diagnosisNumSql = " COUNT(DISTINCT diag1.diagnosis_customer) ";

        // 查询首诊人数
        String diagnosisFirstSql = " SELECT COUNT(DISTINCT diag1.diagnosis_customer) "
                + "                 FROM user_diagnosis AS diag1 "
                + whereSql
                + "                 AND diag1.diagnosis_type = 1 ";

        // 查询孕前期人数
        String pregStagePreSql = " SELECT COUNT(DISTINCT diag1.diagnosis_customer) "
                + "               FROM user_diagnosis AS diag1 "
                + whereSql
                + "               AND diag1.diagnosis_pregnant_stage = 'pregnancy_pre' AND diag1.diagnosis_type = 1";

        // 查询孕中期人数
        String pregStageMidSql = " SELECT COUNT(DISTINCT diag1.diagnosis_customer) "
                + "               FROM user_diagnosis AS diag1 "
                + whereSql
                + "               AND diag1.diagnosis_pregnant_stage = 'pregnancy_mid' AND diag1.diagnosis_type = 1";

        // 查询孕晚期人数
        String pregStageLateSql = " SELECT COUNT(DISTINCT diag1.diagnosis_customer) "
                + "               FROM user_diagnosis AS diag1 "
                + whereSql
                + "               AND diag1.diagnosis_pregnant_stage = 'pregnancy_late' AND diag1.diagnosis_type = 1";

        String sqlBase = "SELECT '" + month
                + "' AS 'month',"
                + diagnosisNumSql + " AS 'diagnosisNum',"
                + "          COUNT(DISTINCT diag1.diagnosis_id) AS 'diagnosisRen', "
                + "          (" + diagnosisFirstSql + ") AS 'diagnosisFirst',"
                + "          IFNULL(FORMAT(((" + diagnosisNumSql + ") - (" + diagnosisFirstSql + ")) / ("
                + diagnosisNumSql
                + ") * 100 , 0), 0) AS 'subsequentRate',"
                + "          (" + pregStagePreSql + ") AS 'pregStagePre',"
                + "          IFNULL(FORMAT((" + pregStagePreSql + ") / (" + diagnosisFirstSql
                + ") * 100 , 0), 0) AS 'pregStagePreRate',"
                + "          (" + pregStageMidSql + ") AS 'pregStageMid',"
                + "          IFNULL(FORMAT((" + pregStageMidSql + ") / (" + diagnosisFirstSql
                + ") * 100 , 0), 0) AS 'pregStageMidRate',"
                + "          (" + pregStageLateSql + ") AS 'pregStageLate',"
                + "          IFNULL(FORMAT((" + pregStageLateSql + ") / (" + diagnosisFirstSql
                + ") * 100 , 0), 0) AS 'pregStageLateRate'"
                + "      FROM user_diagnosis AS diag1"
                + whereSql;
        return sqlBase;
    }

    /**
     * 获取两个日期之间的月份差（含头含尾）
     * 
     * @author xdc
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        } catch (ParseException e) {
            throw new ServiceException(ResultCode.ERROR_99999);
        }
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 获取当前月的最小天数
     * 
     * @author xdc
     * @return
     */
    private Date getMonthMinDate(Date date) {
        // 获取前月的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前月最大天数
     * 
     * @author xdc
     * @return
     */
    private Date getMonthMaxDate(Date date) {
        // 获取前月的最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 格式化字符串 字符A，字符B -- "字符A"，"字符B"
     * 
     * @author dhs
     * @param String
     * @return
     */
    private String formatStr(String param) {
        String[] zhuanUserIdsArray = param.split(",");
        String zhuanUserIds = "";
        for (String strs : zhuanUserIdsArray) {
            zhuanUserIds += strs.replaceAll(strs, "'" + strs + "'") + ",";
        }
        return zhuanUserIds.substring(0, zhuanUserIds.length() - 1);
    }
}
