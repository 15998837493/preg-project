
package com.mnt.preg.examitem.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.entity.QuestionAnswer;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.customer.pojo.QuestionRecordPojo;
import com.mnt.preg.customer.pojo.ReportQuestionOptionPojo;
import com.mnt.preg.master.entity.question.QuestionProblems;
import com.mnt.preg.master.pojo.question.QuestionProblemsPojo;
import com.mnt.preg.master.pojo.question.QuestionRecordBo;

/**
 * 
 * 问题库接口
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Validated
public interface QuestionAnswerService {

    /**
     * 
     * 增加问题库记录
     * 
     * @author gss
     * @param problemVo
     * @return 主键
     */
    String saveQuestionProblems(QuestionProblemsPojo questionQuestionProblemssVo);

    /**
     * 
     * 修改问题库记录
     * 
     * @author gss
     * @param problemVo
     * @param problemId
     *            主键
     */
    void updateQuestionProblems(QuestionProblemsPojo questionQuestionProblemssVo, String problemId);

    /**
     * 
     * 根据主键删除问题库记录
     * 
     * @author gss
     * @param problemId
     */
    void removeQuestionProblems(String problemId);

    /**
     * 
     * 问题库记录查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>问题库记录查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    List<QuestionProblemsPojo> queryQuestionProblems(QuestionProblems condition);

    /**
     * 
     * 根据problemId查询问题基本信息
     * 
     * @author gss
     * @param problemId
     * @return
     */
    QuestionProblemsPojo getQuestionProblemsById(String problemId);

    /**
     * 
     * 根据questionId查询问卷问题最大的排序号
     * 
     * @author gss
     * @param questionId
     * @return
     */
    Integer getQuestionProblemsMaxOderNo(String questionId);

    /**
     * 
     * 根据排序编号和问卷编号查询问题基本信息
     * 
     * @author gss
     * @param questionId
     * @param problemOrder
     * @return
     */
    QuestionProblemsPojo getQuestionProblemsByIdAndProblemOrder(String questionId, Integer problemOrder);

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
     * @return
     */
    List<QuestionProblemsPojo> queryQuestionProblemsForParentPage(String questionId,
            String excludeProblemId);

    /**
     * 
     * 查询问卷下的所有问题
     * 
     * @author mnt_zhangjing
     * @param questionId
     * @return 所有问题
     */
    List<QuestionProblemsPojo> queryProblemByQuesId(String questionId, String sex);

    /**
     * 
     * 根据父节点的id查询关联子问题的基本信息
     * 
     * @author gss
     * @param parentId
     * @return 父节点的id
     */
    List<QuestionProblemsPojo> queryProblemByParentId(String parentId);

    // **************************************************************************************

    /**
     * 
     * 保存问卷操作记录信息
     * 
     * @author mnt_zhangjing
     * @param questionRecordBo
     * @return QuestionnaireVo
     */
    QuestionRecordPojo saveQuestionnaire(QuestionRecordBo questionRecordBo);

    /**
     * 
     * 保存问卷答案记录信息
     * 
     * @author mnt_zhangjing
     * @param questionAnswerBo
     */
    void saveQuestionAnswer(QuestionAnswer questionAnswerBo);

    /**
     * 
     * 查询问卷操作记录
     * 
     * @author mnt_zhangjing
     * @param questionId
     * @param custId
     * @return QuestionnaireVo
     */
    QuestionRecordPojo getQuestionnaireInfo(String questionId, String custId);

    /**
     * 
     * 主键查询问卷操作记录
     * 
     * @author mnt_zhangjing
     * @param questionAllocId
     * @return QuestionnaireVo
     */
    QuestionRecordPojo getQuestionnaireInfo(String questionAllocId);

    /**
     * 删除问卷操作记录
     * 
     * @author xdc
     * @param questionAllocId
     */
    void deleteQuestionnaireInfo(String questionAllocId);

    /**
     * 
     * 查询问卷答案记录
     * 
     * @author mnt_zhangjing
     * @param recordId
     *            问卷操作记录主键
     * @return List<QuestionAnswerVo>
     */
    List<QuestionAnswerPojo> queryQuestionAnswer(String recordId);

    /**
     * 
     * 根据主键查询客户问卷记录
     * 
     * @author gss
     * @param questionAllocId
     *            分卷分配编码
     * @return
     */
    QuestionRecordPojo getQuestionRecordByRecordId(String questionAllocId);

    /**
     * 
     * 查询患者所选的问卷选项详细内容
     * 
     * @author gss
     * @param recordId
     *            主键
     * @return
     */
    List<ReportQuestionOptionPojo> getReportQuestionOption(String recordId);

    /**
     * 
     * 根据问卷分配号删除问卷答案明细
     * 
     * @author wsy
     * @param questionAllocId
     *            主键
     * @return
     */
    void deleteQuestionAnswer(String questionAllocId);

    /**
     * 
     * 根据问题id查询问卷答案明细
     * 
     * @author wsy
     * @param questionAllocId
     *            主键
     * @return
     */
    public List<QuestionAnswerPojo> queryQuestionByProblemId(String problemId, String allowId);

}
