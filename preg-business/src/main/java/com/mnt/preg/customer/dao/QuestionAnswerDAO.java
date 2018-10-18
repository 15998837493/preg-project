/*
 * QuestionAnswerDAO.java	 1.0   2016-3-1
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.customer.pojo.QuestionRecordPojo;

/**
 * 
 * 问卷结果操作
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 mnt_zhangjing 初版
 */
@Repository
public class QuestionAnswerDAO extends HibernateTemplate {

    /**
     * 
     * 查询问卷操作记录
     * 
     * @author mnt_zhangjing
     * @param questionId
     * @param custId
     * @return QuestionnaireVo
     */
    public QuestionRecordPojo getQuestionnaireInfo(String questionId, String custId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionRecordPojo.class, "QuestionRecordVo")
                + " FROM cus_question_record AS QuestionRecordVo"
                + " WHERE cust_id= :custId AND question_id= :questionId  AND flag= :flag ORDER BY operator_date DESC";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionId", questionId);
        queryParams.put("custId", custId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, QuestionRecordPojo.class);
    }

    /**
     * 
     * 主键查询问卷操作记录
     * 
     * @author mnt_zhangjing
     * @param questionId
     * @param custId
     * @return QuestionnaireVo
     */
    public QuestionRecordPojo getQuestionnaireInfo(String questionAllocId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionRecordPojo.class, "QuestionRecordVo")
                + " FROM cus_question_record AS QuestionRecordVo"
                + " WHERE question_alloc_id= :questionAllocId  AND flag= :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionAllocId", questionAllocId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, QuestionRecordPojo.class);
    }

    /**
     * 删除查询问卷操作记录
     * 
     * @author xdc
     * @param questionAllocId
     * @return
     */
    public void deleteQuestionnaireInfo(String questionAllocId) {
        String sql = "DELETE FROM cus_question_record"
                + "   WHERE question_alloc_id= :questionAllocId  AND flag= :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionAllocId", questionAllocId);
        queryParams.put("flag", 1);
        this.executeSQL(sql, queryParams);
    }

    /**
     * 
     * 根据问卷主键查询问卷问题答案表
     * 
     * @author gss
     * @param recordId
     *            记录
     * @return
     */
    public QuestionRecordPojo queryQuestionRecordByRecordId(String recordId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionRecordPojo.class, "QuestionRecordVo")
                + " FROM cus_question_record AS QuestionRecordVo"
                + " WHERE question_record_id= :recordId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("recordId", recordId);
        return this.SQLQueryAliasFirst(sql, queryParams, QuestionRecordPojo.class);
    }

    /**
     * 
     * 根据问卷主键查询问卷问题答案表
     * 
     * @author gss
     * @param recordId
     *            记录
     * @return
     */
    public List<QuestionAnswerPojo> queryQuestionAnswer(String questionAllocId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionAnswerPojo.class, "QuestionAnswerVo")
                + " FROM cus_question_answer AS QuestionAnswerVo"
                + " WHERE question_alloc_id= :questionAllocId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionAllocId", questionAllocId);
        return this.SQLQueryAlias(sql, queryParams, QuestionAnswerPojo.class);
    }

    /**
     * 
     * 根据问卷主键查询问卷问题答案表
     * 
     * @author xdc
     * @param recordId
     *            记录
     * @return
     */
    public List<QuestionAnswerPojo> queryQuestionAnswerAndOptionContent(String questionAllocId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(QuestionAnswerPojo.class, "QuestionAnswerVo")
                + "   ,options.option_content as optionContent"
                + "   FROM cus_question_answer AS QuestionAnswerVo "
                + "   LEFT JOIN mas_question_problem_options AS options ON options.problem_option_id = QuestionAnswerVo.problem_option_id "
                + " WHERE question_alloc_id= :questionAllocId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionAllocId", questionAllocId);
        return this.SQLQueryAlias(sql, queryParams, QuestionAnswerPojo.class);
    }

    /**
     * 
     * 根据问题主键（problem_id）查询问卷问题答案表
     * 
     * @author gss
     * @param recordId
     *            记录
     * @return
     */
    public List<QuestionAnswerPojo> queryAnswerByProblemId(String problemId, String questionAllocId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionAnswerPojo.class, "QuestionAnswerVo")
                + " FROM cus_question_answer AS QuestionAnswerVo"
                + " WHERE problem_id= :problemId AND question_alloc_id= :questionAllocId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("problemId", problemId);
        queryParams.put("questionAllocId", questionAllocId);
        return this.SQLQueryAlias(sql, queryParams, QuestionAnswerPojo.class);
    }

    /**
     * 
     * 根据问卷分配编码查询客户问卷记录
     * 
     * @author gss
     * @param questionAllocId
     *            主键
     * @return
     */
    public QuestionRecordPojo getQuestionRecordByRecordId(String questionAllocId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionRecordPojo.class, "QuestionRecordVo")
                + " FROM cus_question_record AS QuestionRecordVo"
                + " WHERE question_alloc_id= :questionAllocId  AND flag= :flag";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionAllocId", questionAllocId);
        queryParams.put("flag", 1);
        return this.SQLQueryAliasFirst(sql, queryParams, QuestionRecordPojo.class);
    }

    /**
     * 
     * 根据问卷分配号删除问卷答案明细
     * 
     * @author wsy
     * @param questionAllocId
     *            主键
     * @return
     */
    public void deleteQuestionAnswer(String questionAllocId) {
        String sql = "DELETE FROM cus_question_answer WHERE question_alloc_id=:questionAllocId";
        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("questionAllocId", questionAllocId);
        sqlQuery.executeUpdate();
    }

}
