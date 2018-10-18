
package com.mnt.preg.master.dao.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.mnt.preg.master.entity.question.Option;
import com.mnt.preg.master.entity.question.Problem;
import com.mnt.health.core.hibernate.HibernateTemplate;
import com.mnt.health.core.utils.DaoUtils;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.health.core.utils.QueryCondition;
import com.mnt.preg.master.pojo.question.OptionPojo;
import com.mnt.preg.master.pojo.question.ProblemPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;

/**
 * 问题DAO
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 gss 初版
 */
@Repository
public class ProblemDAO extends HibernateTemplate {

    /**
     * 根据查询条件查询问题记录
     * 
     * @param condition
     *            查询条件
     * @return List<ProblemVo> 信息列表
     */
    public List<ProblemPojo> queryProblem(Problem condition) {
        if (condition == null) {
            condition = new Problem();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "ProblemVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(ProblemPojo.class, "ProblemVo")
                + "        FROM mas_problem AS ProblemVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), ProblemPojo.class);
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
    public void updateProblem(Problem problem, String id) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("problem_id", HQLSymbol.EQ.toString(), id));
        this.updateHQL(problem, conditionParams);
    }

    /**
     * 
     * 根据问卷id查询问卷共有多少道题（只包含大标题例如：1,2,3,4不包含1.1,1.2等等）
     * 
     * @author gss
     * @param questionId
     *            问卷编码
     * @return
     */
    public Integer getProblemNumByQuestionId(String questionId, String sex) {
        String countSQL = "SELECT COUNT(problem_id) "
                + "        FROM mas_question_problems "
                + "        WHERE question_id=:questionId "
                + "            AND flag=:flag "
                + "            AND problem_level=:problemLevel"
                + "            AND (problem_sex='all' OR problem_sex=:problemSex)";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("questionId", questionId);
        queryParams.put("problemSex", sex);
        queryParams.put("flag", 1);
        queryParams.put("problemLevel", 0);
        return this.SQLCount(countSQL, queryParams);
    }

    /**
     * 
     * 根据问题选项编号查询问题选项表
     * 
     * @author gss
     * @param recordId
     *            问题选项编号
     * @return ProblemOptionVo
     */
    public QuestionProblemOptionsPojo getProblemOptionByRecordId(String recordId) {
        String sql = "SELECT "
                + DaoUtils.getSQLFields(QuestionProblemOptionsPojo.class, "QuestionProblemOptionsVo")
                + " FROM mas_question_problem_options AS QuestionProblemOptionsVo"
                + " WHERE problem_option_id= :recordId";
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("recordId", recordId);
        return this.SQLQueryAliasFirst(sql, queryParams, QuestionProblemOptionsPojo.class);
    }

    /**
     * 根据问题Id查询问题对照的选项列表
     * 
     * @param problemId
     *            问题Id
     * @return List<OptionVo>
     */
    public List<OptionPojo> getOptionsByProblemId(String problemId) {
        String sql = "SELECT " + DaoUtils.getSQLFields(OptionPojo.class, "OptionVo")
                + "   FROM mas_option AS OptionVo"
                + "   WHERE OptionVo.problem_id=:problemId "
                + "   AND OptionVo.flag=1 "
                + "   ORDER BY OptionVo.option_order ASC ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("problemId", problemId);
        return this.SQLQueryAlias(sql, paramMap, OptionPojo.class);
    }

    /**
     * 根据查询条件查询选项记录
     * 
     * @param condition
     *            查询条件
     * @return List<OptionVo> 信息列表
     */
    public List<OptionPojo> queryOption(Option condition) {
        if (condition == null) {
            condition = new Option();
        }
        QueryCondition queryCondition = DaoUtils.getQueryConditionSQL(condition, "OptionVo");
        String querySQL = "SELECT " + DaoUtils.getSQLFields(OptionPojo.class, "OptionVo")
                + "        FROM mas_option AS OptionVo"
                + queryCondition.getQueryString()
                + queryCondition.getOrderString();
        return this.SQLQueryAlias(querySQL, queryCondition.getQueryParams(), OptionPojo.class);
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
    public void updateOption(Option option, String id) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("option_id", HQLSymbol.EQ.toString(), id));
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
                + "   FROM mas_option AS OptionVo"
                + "   WHERE OptionVo.problem_id=:problemId";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("problemId", problemId);
        List<OptionPojo> optionVos = this.SQLQueryAlias(sql, paramMap, OptionPojo.class);
        if (CollectionUtils.isEmpty(optionVos)) {
            return 0;
        }
        return optionVos.get(0).getOptionOrder();
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
    public OptionPojo getOptionByIdAndOptionOrder(String problemId, Integer optionOrder) {
        String sql = "SELECT " + DaoUtils.getSQLFields(OptionPojo.class, "OptionVo")
                + "   FROM mas_option AS OptionVo"
                + "   WHERE OptionVo.problem_id=:problemId"
                + "   AND OptionVo.option_order=:optionOrder";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("problemId", problemId);
        paramMap.put("optionOrder", optionOrder);
        List<OptionPojo> optionVos = this.SQLQueryAlias(sql, paramMap, OptionPojo.class);
        if (CollectionUtils.isEmpty(optionVos)) {
            return null;
        }
        return optionVos.get(0);
    }

    /**
     * 
     * 物理删除选项
     * 
     * @author gss
     * @param optionId
     */
    public void deleteOptionByOptionId(String optionId) {
        String sql = "DELETE FROM mas_option where option_id = :optionId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("optionId", optionId);

        this.executeSQL(sql, params);
    }

    /**
     * 
     * 删除问题记录
     * 
     * @author dhs
     * @param problemId
     */
    public void deleteProblemByProblemId(String problemId) {
        String sql = "DELETE FROM mas_problem where problem_id = :problemId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("problemId", problemId);
        this.executeSQL(sql, params);
    }
}
