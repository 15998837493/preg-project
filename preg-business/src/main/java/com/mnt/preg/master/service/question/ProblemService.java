
package com.mnt.preg.master.service.question;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.master.entity.question.Option;
import com.mnt.preg.master.entity.question.Problem;
import com.mnt.preg.master.pojo.question.OptionPojo;
import com.mnt.preg.master.pojo.question.ProblemPojo;

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
public interface ProblemService {

    /**
     * 
     * 增加问题库记录
     * 
     * @author gss
     * @param problemVo
     * @return 主键
     */
    String addProblem(ProblemPojo problemVo, List<OptionPojo> options);

    /**
     * 
     * 修改问题库记录
     * 
     * @author gss
     * @param problemVo
     * @param problemId
     *            主键
     */
    void updateProblem(ProblemPojo problemVo, List<OptionPojo> options);

    /**
     * 
     * 根据主键删除问题库记录
     * 
     * @author gss
     * @param problemId
     */
    void removeProblem(String problemId);

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
    List<ProblemPojo> queryProblem(Problem condition);

    /**
     * 
     * 根据problemId查询问题基本信息
     * 
     * @author gss
     * @param problemId
     * @return
     */
    ProblemPojo getProblemById(String problemId);

    /**
     * 
     * 根据问卷id查询问卷共有多少道题（只包含大标题例如：1,2,3,4不包含1.1,1.2等等）
     * 
     * @author gss
     * @param questionId
     *            问卷编码
     * @return
     */
    Integer getProblemNumByQuestionId(String questionId, String sex);

    // *******************************************************************************

    /**
     * 
     * 根据problemId查询选项集合
     * 
     * @author gss
     * @param problemId
     * @return
     */
    List<OptionPojo> getOptionsByProblemId(String problemId);

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
    List<OptionPojo> queryOption(Option condition);

    /**
     * 
     * 增加选项记录
     * 
     * @author gss
     * @param optionVo
     * @return 主键
     */
    String addOption(OptionPojo optionVo);

    /**
     * 
     * 修改选项记录
     * 
     * @author gss
     * @param optionVo
     * @param optionId
     *            主键
     */
    void updateOption(OptionPojo optionVo, String optionId);

    /**
     * 
     * 根据optionId查询选项基本信息
     * 
     * @author gss
     * @param optionId
     * @return
     */
    OptionPojo getOptionByOptionId(String optionId);

    /**
     * 
     * 根据problemId查询问题选项最大的排序号
     * 
     * @author gss
     * @param problemId
     * @return
     */
    Integer getOptionMaxOrderNo(String problemId);

    /**
     * 
     * 根据排序编号和问题编号查询选项基本信息
     * 
     * @author gss
     * @param problemId
     * @param optionOrder
     * @return
     */
    OptionPojo getOptionByIdAndOptionOrder(String problemId, Integer optionOrder);

    /**
     * 
     * 根据选项Id物理删除选项
     * 
     * @author gss
     * @param problemId
     * @param optionOrder
     * @return
     */
    void deleteOptionByOptionId(List<OptionPojo> optionVos);
}
