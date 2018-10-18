
package com.mnt.preg.master.service.question;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.question.ProblemDAO;
import com.mnt.preg.master.dao.question.QuestionDAO;
import com.mnt.preg.master.entity.question.Option;
import com.mnt.preg.master.entity.question.Problem;
import com.mnt.preg.master.pojo.question.OptionPojo;
import com.mnt.preg.master.pojo.question.ProblemPojo;
import com.mnt.health.utils.beans.TransformerUtils;

/**
 * 体检项目字典接口实现
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Service
public class ProblemServiceImpl extends BaseService implements ProblemService {

    @Resource
    private ProblemDAO problemDAO;

    @Resource
    private QuestionDAO questionDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProblemServiceImpl.class);

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addProblem(ProblemPojo problemVo, List<OptionPojo> options) {
        // 保存问题
        Problem problem = TransformerUtils.transformerProperties(Problem.class, problemVo);
        String problemId = (String) problemDAO.save(problem);
        // 保存选项
        String optionType = "";
        if (problemVo.getProblemType().equals("1") || problemVo.getProblemType().equals("2")) {
            optionType = "1";
        } else if (problemVo.getProblemType().equals("3") || problemVo.getProblemType().equals("4")) {
            optionType = "2";
        }
        if (CollectionUtils.isNotEmpty(options)) {
            for (OptionPojo optionVo : options) {
                if (optionVo == null) {
                    continue;
                }
                if (StringUtils.isEmpty(optionVo.getOptionSex())) {
                    optionVo.setOptionSex(problemVo.getProblemSex());
                }
                optionVo.setOptionType(optionType);
                optionVo.setProblemId(problemId);
                this.addOption(optionVo);
            }
        }

        return problemId;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateProblem(ProblemPojo problemVo, List<OptionPojo> options) {
        String problemId = problemVo.getProblemId();
        Problem problem = TransformerUtils.transformerProperties(Problem.class, problemVo);
        problemDAO.updateProblem(problem, problemId);
        // 首先删除这改问题的所有选项
        List<OptionPojo> optionVos = this.getOptionsByProblemId(problemVo.getProblemId());
        if (CollectionUtils.isNotEmpty(optionVos)) {
            this.deleteOptionByOptionId(optionVos);
        }
        // 保存改问题的选项
        String optionType = "";
        if (problemVo.getProblemType().equals("1") || problemVo.getProblemType().equals("2")) {
            optionType = "1";
        } else if (problemVo.getProblemType().equals("3") || problemVo.getProblemType().equals("4")) {
            optionType = "2";
        }
        if (CollectionUtils.isNotEmpty(options)) {
            for (OptionPojo optionVo : options) {
                if (optionVo == null) {
                    continue;
                }
                if (StringUtils.isEmpty(optionVo.getOptionSex())) {
                    optionVo.setOptionSex(problemVo.getProblemSex());
                }
                optionVo.setOptionType(optionType);
                optionVo.setProblemId(problemVo.getProblemId());
                this.addOption(optionVo);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeProblem(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return;
        }
        problemDAO.deleteProblemByProblemId(problemId);
        LOGGER.info("【事物层】删除问题库记录:problemId" + problemId);
        // TODO problemDAO.deleteProblem(problemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProblemPojo> queryProblem(Problem condition) {
        if (condition == null) {
            condition = new Problem();
        }
        List<ProblemPojo> problemVos = problemDAO.queryProblem(condition);
        return problemVos;
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemPojo getProblemById(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return null;
        }
        LOGGER.info("【事物层】根据problemId查询问题基本信息:problemId" + problemId);
        return (ProblemPojo) problemDAO.getTransformerBean(problemId, Problem.class, ProblemPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getProblemNumByQuestionId(String questionId, String sex) {
        if (StringUtils.isEmpty(questionId)) {
            return 0;
        }
        return problemDAO.getProblemNumByQuestionId(questionId, sex);
    }

    // ***********************************************************************************

    @Override
    @Transactional(readOnly = true)
    public List<OptionPojo> getOptionsByProblemId(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return null;
        }
        LOGGER.info("【事物层】根据problemId查询问题对应的选项列表:problemId" + problemId);
        return problemDAO.getOptionsByProblemId(problemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OptionPojo> queryOption(Option condition) {
        if (condition == null) {
            condition = new Option();
        }
        List<OptionPojo> optionVos = problemDAO.queryOption(condition);
        return optionVos;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addOption(OptionPojo optionVo) {
        if (optionVo == null) {
            return null;
        }
        LOGGER.info("【事物层】保存选项记录");
        Option option = TransformerUtils.transformerProperties(Option.class, optionVo);
        return (String) problemDAO.save(option);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateOption(OptionPojo optionVo, String optionId) {
        if (optionVo == null) {
            return;
        }
        LOGGER.info("【事物层】更新选项记录");
        Option option = TransformerUtils.transformerProperties(Option.class, optionVo);
        problemDAO.updateOption(option, optionId);
    }

    @Override
    @Transactional(readOnly = true)
    public OptionPojo getOptionByOptionId(String optionId) {
        if (StringUtils.isEmpty(optionId)) {
            return null;
        }
        LOGGER.info("【事物层】根据主键查询选项记录");
        return (OptionPojo) problemDAO.getTransformerBean(optionId, Option.class, OptionPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getOptionMaxOrderNo(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return 0;
        }
        LOGGER.info("【事物层】根据problemId查询问卷选项最大的排序号:problemId" + problemId);
        return problemDAO.getOptionMaxOderNo(problemId);
    }

    @Override
    @Transactional(readOnly = true)
    public OptionPojo getOptionByIdAndOptionOrder(String problemId, Integer optionOrder) {
        if (StringUtils.isEmpty(problemId) && optionOrder == null) {
            return null;
        }
        LOGGER.info("【事物层】根据problemId查询问题基本信息:problemId" + problemId + ";problemOrder:" + optionOrder);
        return problemDAO.getOptionByIdAndOptionOrder(problemId, optionOrder);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteOptionByOptionId(List<OptionPojo> optionVos) {
        if (CollectionUtils.isEmpty(optionVos)) {
            return;
        }
        for (OptionPojo optionVo : optionVos) {
            if (optionVo == null) {
                continue;
            }
            problemDAO.deleteOptionByOptionId(optionVo.getOptionId());
        }
    }
}
