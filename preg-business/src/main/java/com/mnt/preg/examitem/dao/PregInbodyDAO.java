
package com.mnt.preg.examitem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.examitem.condition.InbodyCondition;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.statistic.pojo.DiagnosisInfoPojo;

/**
 * 人体成分分析DAO
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-22 zcq 初版
 */
@Repository
public class PregInbodyDAO extends HibernateTemplate {

    /**
     * 获取人体成分分析全部信息
     * 
     * @author zcq
     * @param bcaId
     * @return
     */
    public PregInBodyBcaPojo getInbodyByCondition(InbodyCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "BCA");
        String sql = "SELECT " + DaoUtils.getSQLFields(PregInBodyBcaPojo.class, "BCA")
                + "   FROM cus_inbody_bca AS BCA"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAliasFirst(sql, queryCondition.getQueryParams(), PregInBodyBcaPojo.class);
    }

    /**
     * 根据id删除人体成分记录
     * 
     * @author xdc
     * @param bcaId
     */
    public void deleteInbodyBcaById(String bcaId) {
        String sql = "DELETE FROM cus_inbody_bca WHERE bca_id=:bcaId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bcaId", bcaId);
        this.executeSQL(sql, paramMap);
    }

    /**
     * 检索人体成分分析历史记录
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<PregInBodyBcaPojo> queryInbodyHistory(InbodyCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "BCA");
        String sql = "SELECT " + DaoUtils.getSQLFields(PregInBodyBcaPojo.class, "BCA")
                + "   FROM cus_inbody_bca AS BCA"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), PregInBodyBcaPojo.class);
    }

    /**
     * 检索人体成分数据（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<PregInBodyBcaPojo> getStatisticInbodyByCondition(InbodyCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "BCA");
        String sql = "SELECT inspectPojo.diagnosis_id AS diagnosisId"
                + "   ,BCA.user_exam_date AS userExamDate"
                + "   ,BCA.wt AS wt,BCA.bfm AS bfm"
                + "   ,BCA.smm AS smm,BCA.protein AS protein"
                + "   ,BCA.mineral AS mineral,BCA.icw AS icw"
                + "   ,BCA.ecw AS ecw,BCA.wed AS wed"
                + "   FROM cus_inbody_bca AS BCA"
                + "   JOIN user_diagnosis_inspect AS inspectPojo ON "
                + "   (inspectPojo.result_code = BCA.bca_id AND inspectPojo.inspect_code = 'B00003' AND inspectPojo.inspect_status = 3)"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), PregInBodyBcaPojo.class);
    }

    /**
     * 检索人体成分数据（统计）
     * 
     * @author mlq
     * @param condition
     * @return
     */
    public List<DiagnosisInfoPojo> queryBaseInbodyByConsition(InbodyCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "inspectPojo");
        String sql = "SELECT inspectPojo.diagnosis_id AS diagnosisId,t2.item_string AS pbf, t3.item_string AS wbpa50"
                + " FROM (SELECT diagnosis_id,result_code,flag FROM user_diagnosis_inspect AS inspectPojo WHERE inspect_code = 'B00003' AND inspectPojo.inspect_status = 3) inspectPojo"
                + " JOIN (SELECT exam_id, exam_code, cust_id, cust_name FROM cus_result_record WHERE exam_category = 'B00003')t1 ON inspectPojo.result_code=t1.exam_code"
                + " JOIN (SELECT exam_id,item_string FROM cus_result_inbody WHERE item_code = 'BODY00013')t2 ON t1.exam_id = t2.exam_id"
                + " JOIN (SELECT exam_id,item_string FROM cus_result_inbody WHERE item_code = 'BODY00045')t3 ON t1.exam_id = t3.exam_id"
                + queryCondition.getQueryString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), DiagnosisInfoPojo.class);
    }

    /**
     * 查询已同步的数据
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<String> queryInbodyDatetimes(InbodyCondition condition) {
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "BCA");
        String sql = "SELECT BCA.datetimes FROM cus_inbody_bca AS BCA" + queryCondition.getQueryString();
        return this.SQLQuery(sql, queryCondition.getQueryParams());
    }

    /**
     * 查询数据是否已同步
     * 
     * @author zcq
     * @param datetimes
     * @return
     */
    public Integer checkInbodyDatetimes(String datetimes) {
        String sql = "SELECT COUNT(bca_id) FROM cus_inbody_bca WHERE datetimes=:datetimes AND flag=:flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("datetimes", datetimes);
        queryParams.put("flag", 1);
        return this.SQLCount(sql, queryParams);
    }
}
