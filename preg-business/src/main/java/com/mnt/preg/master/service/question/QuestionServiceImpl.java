
package com.mnt.preg.master.service.question;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.customer.entity.QuesAllocation;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.question.ProblemDAO;
import com.mnt.preg.master.dao.question.QuestionDAO;
import com.mnt.preg.master.entity.question.Question;
import com.mnt.preg.master.entity.question.QuestionProblemOptions;
import com.mnt.preg.master.pojo.question.QuestionPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;

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
public class QuestionServiceImpl extends BaseService implements QuestionService {

    @Resource
    private ProblemDAO problemDAO;

    @Resource
    private QuestionDAO questionDAO;

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveQuestion(QuestionPojo questionVo) {
        if (questionVo == null) {
            return null;
        }
        Question question = TransformerUtils.transformerProperties(Question.class, questionVo);
        return (String) questionDAO.save(question);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateQuestion(QuestionPojo questionVo, String questionId) {
        if (questionVo == null) {
            return;
        }
        Question question = TransformerUtils.transformerProperties(Question.class, questionVo);
        questionDAO.updateQuestion(question, questionId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeQuestion(String questionId) {
        if (StringUtils.isEmpty(questionId)) {
            return;
        }
        questionDAO.deleteQuestionByQuestionId(questionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionPojo> queryQuestion(Question condition) {
        List<QuestionPojo> questionVos = questionDAO.queryQuestion(condition);
        return questionVos;
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionPojo getQuestionById(String questionId) {
        if (StringUtils.isEmpty(questionId)) {
            return null;
        }
        return (QuestionPojo) questionDAO.getTransformerBean(questionId, Question.class, QuestionPojo.class);
    }

    @Transactional(readOnly = true)
    public QuestionPojo getQuestion(String questionId) {
        Question question = questionDAO.get(Question.class, questionId);
        if (question == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        QuestionPojo questionVo = TransformerUtils.transformerProperties(QuestionPojo.class, question);
        return questionVo;
    }

    @Transactional(rollbackFor = HibernateException.class)
    public String addAllocation(QuesAllocation quesAllocationBo) {
        QuesAllocation quesAllocation = TransformerUtils.transformerProperties(QuesAllocation.class, quesAllocationBo);
        return (String) questionDAO.save(quesAllocation);
    }

    @Transactional(readOnly = true)
    public QuesAllocation getAllocation(String questionAllocId) {
        QuesAllocation quesAllocation = questionDAO.get(QuesAllocation.class, questionAllocId);
        if (quesAllocation == null) {
            throw new ServiceException(ResultCode.ERROR_99998);
        }
        QuesAllocation quesAllocationVo = TransformerUtils.transformerProperties(QuesAllocation.class,
                quesAllocation);
        return quesAllocationVo;
    }

    @Transactional(rollbackFor = HibernateException.class)
    public void remove(String questionAllocId) {
        questionDAO.deleteQuesAllocation(questionAllocId);
    }

    // ******************************************************************************************************************

    @Override
    @Transactional(readOnly = true)
    public List<QuestionProblemOptionsPojo> getQuestionProblemOptionsByProblemId(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return null;
        }
        return questionDAO.getQuestionProblemOptionsByProblemId(problemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionProblemOptionsPojo> queryQuestionProblemOptions(QuestionProblemOptions condition) {
        List<QuestionProblemOptionsPojo> optionVos = questionDAO.queryQuestionProblemOptions(condition);
        return optionVos;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addQuestionProblemOptions(QuestionProblemOptionsPojo optionVo) {
        if (optionVo == null) {
            return null;
        }
        QuestionProblemOptions option = TransformerUtils.transformerProperties(QuestionProblemOptions.class, optionVo);
        return (String) questionDAO.save(option);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateQuestionProblemOptions(QuestionProblemOptionsPojo optionVo, String optionId) {
        if (optionVo == null) {
            return;
        }
        QuestionProblemOptions option = TransformerUtils.transformerProperties(QuestionProblemOptions.class, optionVo);
        questionDAO.updateQuestionProblemOptions(option, optionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getQuestionOptionMaxOrderNo(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return 0;
        }
        return questionDAO.getOptionMaxOderNo(problemId);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionProblemOptionsPojo getQuestionOptionByOptionId(String optionId) {
        if (StringUtils.isEmpty(optionId)) {
            return null;
        }
        return (QuestionProblemOptionsPojo) questionDAO.getTransformerBean(optionId,
                QuestionProblemOptions.class,
                QuestionProblemOptionsPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionProblemOptionsPojo getQuestionOptionByIdAndOptionOrder(String problemId, Integer optionOrder) {
        if (StringUtils.isEmpty(problemId) && optionOrder == null) {
            return null;
        }
        return questionDAO.getQuestionOptionByIdAndOptionOrder(problemId, optionOrder);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteQuestionProblemOptionsByOptionId(List<QuestionProblemOptionsPojo> optionVos) {
        if (CollectionUtils.isEmpty(optionVos)) {
            return;
        }
        for (QuestionProblemOptionsPojo optionVo : optionVos) {
            if (optionVo == null) {
                continue;
            }
            problemDAO.deleteOptionByOptionId(optionVo.getProblemOptionId());
        }
    }

    @Transactional(readOnly = true)
    public List<QuestionProblemOptionsPojo> queryOptionByProId(String problemId, String sex) {
        return questionDAO.queryOptionByProId(problemId, sex);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionProblemOptionsPojo> queryQuestionProblemOptionsByProblemId(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return null;
        }
        return questionDAO.queryQuestionProblemOptionsByProblemId(problemId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteOptionByProblemId(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return;
        }
        questionDAO.deleteOptionByProblemId(problemId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, QuestionProblemOptionsPojo> queryQuestionProblemOptionsAll() {
        Map<String, QuestionProblemOptionsPojo> questionProblemOptionsVoMap = new HashMap<String, QuestionProblemOptionsPojo>();
        List<QuestionProblemOptionsPojo> problemOptionsVos = questionDAO.queryQuestionProblemOptionsAll();
        if (CollectionUtils.isEmpty(problemOptionsVos)) {
            return null;
        }
        for (QuestionProblemOptionsPojo questionProblemOptionsVo : problemOptionsVos) {
            if (questionProblemOptionsVo == null) {
                continue;
            }
            questionProblemOptionsVoMap.put(questionProblemOptionsVo.getProblemOptionId(), questionProblemOptionsVo);
        }
        return questionProblemOptionsVoMap;
    }

    @Override
    public String getOnceAllocationID(String custId, Date diagnosisDate) {
        return questionDAO.getOnceAllocationID(custId, diagnosisDate);
    }
}
