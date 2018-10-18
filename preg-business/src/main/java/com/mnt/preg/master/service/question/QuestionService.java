
package com.mnt.preg.master.service.question;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.entity.QuesAllocation;
import com.mnt.preg.master.entity.question.Question;
import com.mnt.preg.master.entity.question.QuestionProblemOptions;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;
import com.mnt.preg.master.pojo.question.QuestionPojo;

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
public interface QuestionService {

    /**
     * 
     * 增加问题库记录
     * 
     * @author gss
     * @param questionVo
     * @return 主键
     */
    String saveQuestion(QuestionPojo questionVo);

    /**
     * 
     * 修改问题库记录
     * 
     * @author gss
     * @param questionVo
     * @param questionId
     *            主键
     */
    void updateQuestion(QuestionPojo questionVo, String questionId);

    /**
     * 
     * 根据主键删除问题库记录
     * 
     * @author gss
     * @param questionId
     */
    void removeQuestion(String questionId);

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
    List<QuestionPojo> queryQuestion(Question condition);

    /**
     * 
     * 根据questionId查询问题基本信息
     * 
     * @author gss
     * @param questionId
     * @return
     */
    QuestionPojo getQuestionById(String questionId);

    /**
     * 
     * 主键查询问卷信息
     * 
     * @author mnt_zhangjing
     * @param questionId
     * @return QuestionVo
     */
    public QuestionPojo getQuestion(String questionId);

    /**
     * 
     * 问卷分配
     * 
     * @author mnt_zhangjing
     * @param quesAllocationBo
     * @return 主键字符串
     */
    public String addAllocation(QuesAllocation quesAllocationBo);

    /**
     * 
     * 主键查询问卷信息
     * 
     * @author mnt_zhangjing
     * @param questionAllocId
     * @return QuesAllocationVo
     */
    public QuesAllocation getAllocation(String questionAllocId);

    /**
     * 
     * 删除对用的问卷分配信息
     * 
     * @author mnt_zhangjing
     * @param questionAllocId
     */
    public void remove(String questionAllocId);

    // ************************************************************************

    /**
     * 
     * 根据problemId查询选项集合
     * 
     * @author gss
     * @param problemId
     * @return
     */
    List<QuestionProblemOptionsPojo> getQuestionProblemOptionsByProblemId(String problemId);

    /**
     * 
     * 选项查询
     * <dl>
     * <dt>1 功能描述</dt>
     * <dd>问题库记录查询</dd>
     * </dl>
     * 
     * @author gss
     * @param condition
     *            查询条件
     */
    List<QuestionProblemOptionsPojo> queryQuestionProblemOptions(QuestionProblemOptions condition);

    /**
     * 
     * 增加选项记录
     * 
     * @author gss
     * @param optionVo
     * @return 主键
     */
    String addQuestionProblemOptions(QuestionProblemOptionsPojo optionVo);

    /**
     * 
     * 修改选项记录
     * 
     * @author gss
     * @param optionVo
     * @param optionId
     *            主键
     */
    void updateQuestionProblemOptions(QuestionProblemOptionsPojo optionVo, String optionId);

    /**
     * 
     * 根据problemId查询问题选项最大的排序号
     * 
     * @author gss
     * @param problemId
     * @return
     */
    Integer getQuestionOptionMaxOrderNo(String problemId);

    /**
     * 
     * 根据optionId查询选项基本信息
     * 
     * @author gss
     * @param optionId
     * @return
     */
    QuestionProblemOptionsPojo getQuestionOptionByOptionId(String optionId);

    /**
     * 根据问题ID和选项排序查询选项信息
     * 
     * @author gss
     * @param problemId
     * @param optionOrder
     * @return
     */
    QuestionProblemOptionsPojo getQuestionOptionByIdAndOptionOrder(String problemId, Integer optionOrder);

    /**
     * 
     * 删除问卷问题选项
     * 
     * @author gss
     * @param optionVos
     */
    void deleteQuestionProblemOptionsByOptionId(List<QuestionProblemOptionsPojo> optionVos);

    /**
     * 
     * 查询问题下的答案选项
     * 
     * @author mnt_zhangjing
     * @param problemId
     * @param sex
     * @return 答案选项
     */
    List<QuestionProblemOptionsPojo> queryOptionByProId(String problemId, String sex);

    /**
     * 
     * 根据问题id 查询选项列表
     * 
     * @author gss
     * @param problemId
     *            问题id
     * @return
     */
    List<QuestionProblemOptionsPojo> queryQuestionProblemOptionsByProblemId(String problemId);

    /**
     * 
     * 根据问卷问题id 删除问题选项
     * 
     * @author gss
     * @param problemId
     *            问题id
     */
    void deleteOptionByProblemId(String problemId);

    /**
     * 查询所有问题选项
     * 
     * @author gss
     * @return
     */
    public Map<String, QuestionProblemOptionsPojo> queryQuestionProblemOptionsAll();

    /**
     * 查询患者当次接诊的初诊建档问卷分配号
     * 
     * @param custId
     *            客户编号
     * @param diagnosisDate
     *            接诊日期
     * @return
     */
    public String getOnceAllocationID(String custId, Date diagnosisDate);
}
