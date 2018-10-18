
package com.mnt.preg.examitem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.examitem.condition.ExamResultRecordCondition;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.main.enums.Flag;

/**
 * 评价项目结果记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-16 zcq 初版
 */
@Repository
public class ExamResultRecordDAO extends HibernateTemplate {

    /**
     * 根据检索条件查询评价项目结果记录
     * 
     * @author zcq
     * @param condition
     * @return
     */
    public List<ExamResultRecordPojo> queryExamResultRecord(ExamResultRecordCondition condition) {
        if (condition == null) {
            condition = new ExamResultRecordCondition();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "InspectionResultVo");
        String sql = "SELECT "
                + DaoUtils.getSQLFields(ExamResultRecordPojo.class, "InspectionResultVo")
                + "       ,inspectItem.inspect_code AS inspectCode "
                + "       ,inspectItem.inspect_name AS inspectName"
                + "       ,customer.cust_code AS custCode"
                + "   FROM cus_result_record AS InspectionResultVo"
                + "   JOIN mas_inspect_item AS inspectItem ON InspectionResultVo.exam_category=inspectItem.inspect_code"
                + "   JOIN cus_customer AS customer ON InspectionResultVo.cust_id = customer.cust_id"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(sql, queryCondition.getQueryParams(), ExamResultRecordPojo.class);
    }

    /**
     * 评价项目信息集合--查询【接诊编码】
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    public List<ExamResultRecordPojo> queryExamResultRecordByDiagnosisId(@NotBlank String diagnosisId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(ExamResultRecordPojo.class, "InspectionResultVo")
                + "   FROM cus_result_record AS InspectionResultVo"
                + "   WHERE InspectionResultVo.exam_code IN ("
                + "       SELECT result_code"
                + "       FROM user_diagnosis_inspect"
                + "       WHERE diagnosis_id=:diagnosisId)";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("diagnosisId", diagnosisId);
        return this.SQLQueryAlias(sql, queryParams, ExamResultRecordPojo.class);
    }

    /**
     * 根据体检号查询评价项目
     * 
     * @author zcq
     * @param phyexamCode
     * @return
     */
    public List<ExamResultRecordPojo> queryExamResultRecordByPhyexamCode(@NotBlank String phyexamCode) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(ExamResultRecordPojo.class, "InspectionResultVo")
                + "       ,inspectItem.inspect_code AS inspectCode "
                + "       ,inspectItem.inspect_name AS inspectName"
                + "   FROM user_diagnosis AS diagnosis"
                + "   JOIN user_diagnosis_inspect AS diagnosisItem ON diagnosis.diagnosis_id=diagnosisItem.diagnosis_id"
                + "       AND diagnosis.diagnosis_code=:diagnosisCode AND diagnosis.flag=:flag"
                + "   JOIN cus_result_record AS InspectionResultVo ON  diagnosisItem.inspect_code=InspectionResultVo.exam_code"
                + "   JOIN mas_inspect_item AS inspectItem ON InspectionResultVo.exam_category=inspectItem.inspect_code";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("diagnosisCode", phyexamCode);
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(sql, queryParams, ExamResultRecordPojo.class);
    }

    /**
     * 根据身份证号查询评价项目
     * 
     * @author zcq
     * @param custIcard
     * @param examCategory
     * @return
     */
    public List<ExamResultRecordPojo> queryExamResultRecordByCustIcard(@NotBlank String custIcard,
            @NotBlank String examCategory) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(ExamResultRecordPojo.class, "InspectionResultVo")
                + "       ,inspectItem.inspect_code AS inspectCode "
                + "       ,inspectItem.inspect_name AS inspectName"
                + "   FROM cus_customer AS customer"
                + "   JOIN cus_result_record AS InspectionResultVo ON customer.cust_id=InspectionResultVo.cust_id"
                + "       AND customer.cust_icard=:custIcard"
                + "       AND InspectionResultVo.exam_category=:examCategory"
                + "       AND !ISNULL(InspectionResultVo.exam_pdf)"
                + "       AND InspectionResultVo.exam_pdf !=''"
                + "   JOIN mas_inspect_item AS inspectItem ON InspectionResultVo.exam_category=inspectItem.inspect_code";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("custIcard", custIcard);
        queryParams.put("examCategory", examCategory);
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(sql, queryParams, ExamResultRecordPojo.class);
    }

    /**
     * 清空评价项目结论
     * 
     * @author zcq
     * @param examCode
     * @param examCategory
     */
    public void removeExamResult(String examCode, String examCategory) {
        if (StringUtils.isNotBlank(examCode) && StringUtils.isNotBlank(examCategory)) {
            String sql = "UPDATE cus_result_record"
                    + "   SET diagnosis_result_codes = '', diagnosis_result_names = ''"
                    + "   WHERE exam_code = :examCode AND exam_category = :examCategory";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("examCode", examCode);
            paramMap.put("examCategory", examCategory);
            this.executeSQL(sql, paramMap);
        }
    }

    /**
     * 删除检测记录
     * 
     * @author zcq
     * @param examCode
     * @param examCategory
     */
    public Integer deleteExamResultRecordByExamCodeAndExamCategory(@NotBlank String examCode,
            @NotBlank String examCategory) {
        String sql = "DELETE FROM cus_result_record WHERE exam_code=:examCode AND exam_category=:examCategory";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("examCode", examCode);
        paramMap.put("examCategory", examCategory);
        return this.executeSQL(sql, paramMap);
    }

    /**
     * 校验评价项目使用次数
     * 
     * @author zcq
     * @param examCategory
     * @return
     */
    public Integer getInspectItemUseCount(@NotBlank String examCategory) {
        String sql = "SELECT COUNT(exam_category) FROM cus_result_record WHERE exam_category=:examCategory";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("examCategory", examCategory);
        return this.SQLCount(sql, paramMap);
    }

}
