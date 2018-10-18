
package com.mnt.preg.master.dao.question;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.main.enums.Flag;
import com.mnt.preg.master.entity.question.Question;
import com.mnt.preg.master.entity.question.QuestionProblemOptions;
import com.mnt.preg.master.entity.question.QuestionProblems;
import com.mnt.preg.master.pojo.question.OptionPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemsPojo;
import com.mnt.preg.master.pojo.question.QuestionPojo;

/**
 * 问卷DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 gss 初版
 */
@Repository
public class QuestionDAO extends HibernateTemplate {

    /**
     * 根据查询条件查询问卷记录
     * 
     * @param condition
     *            查询条件
     * @return List<QuestionVo> 信息列表
     */
    public List<QuestionPojo> queryQuestion(Question condition) {
        if (condition == null) {
            condition = new Question();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "QuestionVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(QuestionPojo.class, "QuestionVo")
                + "        FROM mas_question AS QuestionVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), QuestionPojo.class);
    }

    /**
     * 
     * 更新问卷记录
     * 
     * @author gss
     * @param Question
     * @param id
     *            主键
     */
    public void updateQuestion(Question question, String id) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("question_id", HQLSymbol.EQ.toString(), id));
        this.updateHQL(question, conditionParams);
    }

    public int deleteQuesAllocation(String questionAllocId) {

        String sql = "DELETE FROM cus_question_allocation WHERE question_alloc_id= :questionAllocId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionAllocId", questionAllocId);
        return this.executeSQL(sql, queryParams);
    }

    // **********************************************QuestionProblems****************************************

    /**
     * 根据查询条件查询问题记录
     * 
     * @param condition
     *            查询条件
     * @return List<ProblemVo> 信息列表
     */
    public List<QuestionProblemsPojo> queryQuestionProblems(QuestionProblems condition) {
        if (condition == null) {
            condition = new QuestionProblems();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "QuestionProblemsVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(QuestionProblemsPojo.class, "QuestionProblemsVo")
                + "        FROM mas_question_problems AS QuestionProblemsVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), QuestionProblemsPojo.class);
    }

    /**
     * 
     * 更新问题记录
     * 
     * @author gss
     * @param problem
     * @param id
     *            主键
     */
    public void updateQuestionProblems(QuestionProblems questionProblems, String id) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("problem_id", HQLSymbol.EQ.toString(), id));
        this.updateHQL(questionProblems, conditionParams);
    }

    public Integer getQuestionProblemsMaxOderNo(String questionId) {
        String sql = "SELECT MAX(problem_order) AS problemOrder"
                + "   FROM mas_question_problems AS QuestionProblemsVo"
                + "   WHERE QuestionProblemsVo.question_id=:questionId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("questionId", questionId);
        List<QuestionProblemsPojo> questionProblemsVos = this.SQLQueryAlias(sql, paramMap, QuestionProblemsPojo.class);
        if (CollectionUtils.isEmpty(questionProblemsVos)) {
            return null;
        }
        return questionProblemsVos.get(0).getProblemOrder();
    }

    /**
     * 
     * 根据排序编号和问卷编号查询问题基本信息
     * 
     * @author gss
     * @param questionId
     * @param problemOrder
     * @return
     */
    public QuestionProblemsPojo getQuestionProblemsByIdAndProblemOrder(String questionId, Integer problemOrder) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemsPojo.class, "QuestionProblemsVo")
                + "   FROM mas_question_problems AS QuestionProblemsVo"
                + "   WHERE QuestionProblemsVo.question_id=:questionId"
                + "   AND QuestionProblemsVo.problem_order=:problemOrder";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("questionId", questionId);
        paramMap.put("problemOrder", problemOrder);
        List<QuestionProblemsPojo> questionProblemsVos = this.SQLQueryAlias(sql, paramMap, QuestionProblemsPojo.class);
        if (CollectionUtils.isEmpty(questionProblemsVos)) {
            return null;
        }
        return questionProblemsVos.get(0);
    }

    /**
     * 
     * 问卷问题查询
     * 
     * @author gss
     * @param questionId
     *            问卷ID
     * @param excludeType
     *            排除的问题类型
     * @param excludeProblemId
     *            排除的问题ID
     */
    public List<QuestionProblemsPojo> queryQuestionProblemsForParentPage(String questionId,
            Integer problemOrder) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemsPojo.class, "QuestionProblemsVo")
                + "   FROM mas_question_problems AS QuestionProblemsVo"
                + "   WHERE QuestionProblemsVo.question_id=:questionId"
                + "   AND QuestionProblemsVo.problem_type NOT IN('3','4')"
                + "   AND QuestionProblemsVo.problem_order<:problemOrder"
                + "   AND QuestionProblemsVo.flag=:flag "
                + "   ORDER BY QuestionProblemsVo.problem_order ASC";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("questionId", questionId);
        paramMap.put("problemOrder", problemOrder);
        paramMap.put("flag", 1);
        List<QuestionProblemsPojo> questionProblemsVos = this.SQLQueryAlias(sql, paramMap, QuestionProblemsPojo.class);
        if (CollectionUtils.isEmpty(questionProblemsVos)) {
            return null;
        }
        return questionProblemsVos;
    }

    /**
     * 
     * 查询问卷下的所有问题
     * 
     * @author mnt_zhangjing
     * @param questionId
     * @param sex
     * @return List<ProblemVo>
     */
    public List<QuestionProblemsPojo> queryProblemByQuesId(String questionId, String sex) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemsPojo.class, "QuestionProblemsVo")
                + "   FROM mas_question_problems QuestionProblemsVo"
                + "   WHERE QuestionProblemsVo.question_id= :questionId"
                + "       AND (QuestionProblemsVo.problem_sex= :sex OR QuestionProblemsVo.problem_sex= :all)"
                + "       AND QuestionProblemsVo.flag= :flag"
                + "   ORDER BY QuestionProblemsVo.problem_order";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionId", questionId);
        queryParams.put("sex", sex);
        queryParams.put("all", "all");
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(sql, queryParams, QuestionProblemsPojo.class);
    }

    /**
     * 
     * 根据问题id查询问题基本信息
     * 
     * @author gss
     * @param problemId
     *            问题Id
     * @return ProblemVo
     */
    public QuestionProblemsPojo getProblemByProblemId(String problemId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(QuestionProblemsPojo.class, "QuestionProblemsVo")
                + " FROM mas_question_problems AS QuestionProblemsVo"
                + " WHERE problem_id= :problemId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("problemId", problemId);
        return this.SQLQueryAliasFirst(sql, queryParams, QuestionProblemsPojo.class);
    }

    /**
     * 删除问卷问题
     * 
     * @author gss
     * @param problemId
     *            问题id
     * @return
     */
    public Integer deleteQuestionProblems(String problemId) {
        String sql = "DELETE FROM mas_question_problems WHERE problem_id=:problemId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("problemId", problemId);
        return this.executeSQL(sql, paramMap);
    }

    /**
     * 
     * 根据父节点的id查询关联子问题的基本信息
     * 
     * @author gss
     * @param parentId
     * @return 父节点的id
     */
    public List<QuestionProblemsPojo> queryProblemByParentId(String parentId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(QuestionProblemsPojo.class, "QuestionProblemsVo")
                + " FROM mas_question_problems AS QuestionProblemsVo"
                + " WHERE problem_parent_id= :parentId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("parentId", parentId);
        return this.SQLQueryAlias(sql, queryParams, QuestionProblemsPojo.class);
    }

    // **********************************************QuestionProblemOptions****************************************
    /**
     * 根据问题Id查询问题对照的选项列表
     * 
     * @param problemId
     *            问题Id
     * @return List<QuestionProblemOptionsVo>
     */
    public List<QuestionProblemOptionsPojo> getQuestionProblemOptionsByProblemId(String problemId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + "   FROM mas_question_problem_options AS QuestionProblemOptionsVo"
                + "   WHERE QuestionProblemOptionsVo.problem_id=:problemId"
                + "   AND QuestionProblemOptionsVo.flag=:flag";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("problemId", problemId);
        paramMap.put("flag", 1);
        return this.SQLQueryAlias(sql, paramMap, QuestionProblemOptionsPojo.class);
    }

    /**
     * 根据查询条件查询选项记录
     * 
     * @param condition
     *            查询条件
     * @return List<QuestionProblemOptionsVo> 信息列表
     */
    public List<QuestionProblemOptionsPojo> queryQuestionProblemOptions(QuestionProblemOptions condition) {
        if (condition == null) {
            condition = new QuestionProblemOptions();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "QuestionProblemOptionsVo");
        String querySQL = "SELECT "
                + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + "        FROM mas_question_problem_options AS QuestionProblemOptionsVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), QuestionProblemOptionsPojo.class);
    }

    /**
     * 
     * 更新选项记录
     * 
     * @author gss
     * @param option
     * @param id
     *            主键
     */
    public void updateQuestionProblemOptions(QuestionProblemOptions option, String id) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("problem_option_id", HQLSymbol.EQ.toString(), id));
        this.updateHQL(option, conditionParams);
    }

    /**
     * 
     * 查询问题选项最大的值
     * 
     * @author gss
     * @param problemId
     * @return
     */
    public Integer getOptionMaxOderNo(String problemId) {
        String sql = "SELECT MAX(option_order) AS optionOrder"
                + "   FROM mas_question_problem_options AS QuestionProblemOptionsVo"
                + "   WHERE QuestionProblemOptionsVo.problem_id=:problemId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("problemId", problemId);
        List<QuestionProblemOptionsPojo> questionProblemOptionsVos = this.SQLQueryAlias(sql, paramMap,
                QuestionProblemOptionsPojo.class);
        if (CollectionUtils.isEmpty(questionProblemOptionsVos)) {
            return 0;
        }
        return questionProblemOptionsVos.get(0).getOptionOrder();
    }

    /**
     * 
     * 根据主键查询
     * 
     * @author dhs
     * @param optionId
     * @return
     */
    public QuestionProblemOptionsPojo getOptionById(String optionId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + "   FROM mas_question_problem_options AS QuestionProblemOptionsVo"
                + "   WHERE QuestionProblemOptionsVo.problem_option_id=:optionId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("optionId", optionId);
        return this.SQLQueryAliasFirst(sql, paramMap, QuestionProblemOptionsPojo.class);
    }

    /**
     * 
     * 根据排序编号和问题编号查询选项基本信息
     * 
     * @author gss
     * @param problemId
     * @param optionOrder
     * @return
     */
    public QuestionProblemOptionsPojo getQuestionOptionByIdAndOptionOrder(String problemId, Integer optionOrder) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + "   FROM mas_question_problem_options AS QuestionProblemOptionsVo"
                + "   WHERE QuestionProblemOptionsVo.problem_id=:problemId"
                + "   AND QuestionProblemOptionsVo.option_order=:optionOrder";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("problemId", problemId);
        paramMap.put("optionOrder", optionOrder);
        List<QuestionProblemOptionsPojo> questionProblemOptionsVos = this.SQLQueryAlias(sql, paramMap,
                QuestionProblemOptionsPojo.class);
        if (CollectionUtils.isEmpty(questionProblemOptionsVos)) {
            return null;
        }
        return questionProblemOptionsVos.get(0);
    }

    /**
     * 
     * 物理删除选项
     * 
     * @author gss
     * @param optionId
     */
    public void deleteOptionByOptionId(String optionId) {
        String sql = "DELETE FROM mas_question_problem_options where problem_option_id = :optionId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("optionId", optionId);

        this.executeSQL(sql, params);
    }

    /**
     * 
     * 删除问卷
     * 
     * @author gss
     * @param optionId
     */
    public void deleteQuestionByQuestionId(String questionId) {
        String sql = "DELETE FROM mas_question where question_id = :questionId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("questionId", questionId);

        this.executeSQL(sql, params);
    }

    /**
     * 
     * 根据问题Id物理删除选项
     * 
     * @author gss
     * @param problemId
     *            问题id
     */
    public void deleteOptionByProblemId(String problemId) {
        String sql = "DELETE FROM mas_question_problem_options where problem_id = :problemId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("problemId", problemId);

        this.executeSQL(sql, params);
    }

    /**
     * 查询问题下的答案选项
     * 
     * @author mnt_zhangjing
     * @param problemId
     * @param sex
     * @return List<OptionVo>
     */
    public List<QuestionProblemOptionsPojo> queryOptionByProId(String problemId, String sex) {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + "   FROM mas_question_problem_options  QuestionProblemOptionsVo "
                + "   WHERE QuestionProblemOptionsVo.problem_id= :problemId"
                + "       AND (QuestionProblemOptionsVo.option_sex= :sex OR QuestionProblemOptionsVo.option_sex= :all)"
                + "       AND QuestionProblemOptionsVo.flag= :flag"
                + "   ORDER BY QuestionProblemOptionsVo.option_order";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("problemId", problemId);
        queryParams.put("sex", sex);
        queryParams.put("all", "all");
        queryParams.put("flag", Flag.normal.getValue());
        return this.SQLQueryAlias(sql, queryParams, QuestionProblemOptionsPojo.class);
    }

    /**
     * 
     * 根据答案选项Id查询答案选项
     * 
     * @author gss
     * @param optionId
     *            答案选项Id
     * @return OptionVo
     */
    public OptionPojo getOptionByOptionId(String optionId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(OptionPojo.class, "OptionVo")
                + " FROM mas_option AS OptionVo"
                + " WHERE option_id= :optionId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("optionId", optionId);
        return this.SQLQueryAliasFirst(sql, queryParams, OptionPojo.class);
    }

    /**
     * 
     * 根据问题id 查询问卷问题列表
     * 
     * @author gss
     * @param problemId
     *            问题id
     * @return
     */
    public List<QuestionProblemOptionsPojo> queryQuestionProblemOptionsByProblemId(String problemId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + " FROM mas_question_problem_options AS QuestionProblemOptionsVo"
                + " WHERE problem_id= :problemId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("problemId", problemId);
        return this.SQLQueryAlias(sql, queryParams, QuestionProblemOptionsPojo.class);
    }

    /**
     * 查询所有选项
     * 
     * @author gss
     * @return
     */
    public List<QuestionProblemOptionsPojo> queryQuestionProblemOptionsAll() {
        String sql = "SELECT " + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + "   FROM mas_question_problem_options AS QuestionProblemOptionsVo";
        return this.SQLQueryAlias(sql, null, QuestionProblemOptionsPojo.class);
    }

    /**
     * 查询患者当次接诊的初诊建档问卷分配号
     * 
     * @param custId
     *            客户编号
     * @param diagnosisDate
     *            接诊日期
     * @return
     */
    public String getOnceAllocationID(String custId, Date diagnosisDate) {
        /*
         * lmp 末次月经 为空--还未填写初诊问卷
         * 接诊日期与末次月经相差天数--小于40周
         */
        String result = null;
        String sql = "SELECT t.question_alloc_id " +
                "     FROM cus_pregnancy_archives t " +
                "       JOIN cus_question_allocation t1 ON t.question_alloc_id = t1.question_alloc_id" +
                "     WHERE t.cust_id = :custId" +
                "       AND (t.lmp IS NULL OR datediff(:diagnosisDate, t.lmp) <= 280) ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("custId", custId);
        map.put("diagnosisDate", diagnosisDate);
        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("custId", custId);
        sqlQuery.setDate("diagnosisDate", diagnosisDate);
        @SuppressWarnings("unchecked")
        List<String> list = sqlQuery.list();
        if (list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }
}
