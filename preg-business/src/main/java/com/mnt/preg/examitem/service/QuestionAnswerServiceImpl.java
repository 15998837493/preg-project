
package com.mnt.preg.examitem.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.preg.customer.dao.QuestionAnswerDAO;
import com.mnt.preg.customer.entity.QuestionAnswer;
import com.mnt.preg.customer.entity.QuestionRecord;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.customer.pojo.QuestionRecordPojo;
import com.mnt.preg.customer.pojo.ReportQuestionOptionPojo;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.question.ProblemDAO;
import com.mnt.preg.master.dao.question.QuestionDAO;
import com.mnt.preg.master.entity.question.QuestionProblems;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemsPojo;
import com.mnt.preg.master.pojo.question.QuestionRecordBo;

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
public class QuestionAnswerServiceImpl extends BaseService implements QuestionAnswerService {

    @Resource
    private ProblemDAO problemDAO;

    @Resource
    private QuestionDAO questionDAO;

    @Resource
    private QuestionAnswerDAO questionAnswerDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerServiceImpl.class);

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String saveQuestionProblems(QuestionProblemsPojo problemVo) {
        if (problemVo == null) {
            return null;
        }
        QuestionProblems problem = TransformerUtils.transformerProperties(QuestionProblems.class, problemVo);
        // problem.setProblemId(this.generateKey(QuestionProblems.class));
        return (String) questionDAO.save(problem);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateQuestionProblems(QuestionProblemsPojo problemVo, String problemId) {
        if (problemVo == null) {
            return;
        }
        LOGGER.info("【事物层】更新问题库记录");
        QuestionProblems problem = TransformerUtils.transformerProperties(QuestionProblems.class, problemVo);
        questionDAO.updateQuestionProblems(problem, problemId);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeQuestionProblems(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return;
        }
        LOGGER.info("【事物层】删除问题库记录:problemId" + problemId);
        questionDAO.deleteQuestionProblems(problemId);
        questionDAO.deleteOptionByProblemId(problemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionProblemsPojo> queryQuestionProblems(QuestionProblems condition) {
        if (condition == null)
            condition = new QuestionProblems();
        List<QuestionProblemsPojo> problemVos = questionDAO.queryQuestionProblems(condition);
        return problemVos;
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionProblemsPojo getQuestionProblemsById(String problemId) {
        if (StringUtils.isEmpty(problemId)) {
            return null;
        }
        LOGGER.info("【事物层】根据problemId查询问题基本信息:problemId" + problemId);
        return (QuestionProblemsPojo) questionDAO.getTransformerBean(problemId, QuestionProblems.class,
                QuestionProblemsPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionProblemsPojo getQuestionProblemsByIdAndProblemOrder(String questionId, Integer problemOrder) {
        if (StringUtils.isEmpty(questionId) || problemOrder == null) {
            return null;
        }
        LOGGER.info("【事物层】根据problemId查询问题基本信息:problemId" + questionId + ";problemOrder:" + problemOrder);
        return questionDAO.getQuestionProblemsByIdAndProblemOrder(questionId, problemOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getQuestionProblemsMaxOderNo(String questionId) {
        if (StringUtils.isEmpty(questionId)) {
            return null;
        }
        LOGGER.info("【事物层】根据questionId查询问卷问题最大的排序号:questionId" + questionId);
        return questionDAO.getQuestionProblemsMaxOderNo(questionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionProblemsPojo> queryQuestionProblemsForParentPage(String questionId,
            String excludeProblemId) {
        if (StringUtils.isEmpty(questionId) || StringUtils.isEmpty(excludeProblemId)) {
            return null;
        }
        QuestionProblems questionProblems = questionDAO.get(QuestionProblems.class, excludeProblemId);
        return questionDAO.queryQuestionProblemsForParentPage(questionId, questionProblems.getProblemOrder());
    }

    @Transactional(readOnly = true)
    public List<QuestionProblemsPojo> queryProblemByQuesId(String questionId, String sex) {
        return questionDAO.queryProblemByQuesId(questionId, sex);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionProblemsPojo> queryProblemByParentId(String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            return null;
        }
        return questionDAO.queryProblemByParentId(parentId);
    }

    // ******************************************************************************************************

    @Transactional(rollbackFor = HibernateException.class)
    public QuestionRecordPojo saveQuestionnaire(QuestionRecordBo questionRecordBo) {
        QuestionRecord questionRecord = TransformerUtils.transformerProperties(QuestionRecord.class, questionRecordBo);
        questionAnswerDAO.save(questionRecord);
        return TransformerUtils.transformerProperties(QuestionRecordPojo.class, questionRecordBo);
    }

    @Transactional(rollbackFor = HibernateException.class)
    public void saveQuestionAnswer(QuestionAnswer questionAnswerBo) {
        QuestionAnswer questionAnswer = TransformerUtils.transformerProperties(QuestionAnswer.class,
                questionAnswerBo);
        questionAnswerDAO.save(questionAnswer);
    }

    @Transactional(readOnly = true)
    public QuestionRecordPojo getQuestionnaireInfo(String questionId, String custId) {
        return questionAnswerDAO.getQuestionnaireInfo(questionId, custId);
    }

    @Transactional(readOnly = true)
    public QuestionRecordPojo getQuestionnaireInfo(String questionAllocId) {
        return questionAnswerDAO.getQuestionnaireInfo(questionAllocId);
    }

    @Transactional(rollbackFor = HibernateException.class)
    public void deleteQuestionnaireInfo(String questionAllocId) {
        questionAnswerDAO.deleteQuestionnaireInfo(questionAllocId);
    }

    @Transactional(readOnly = true)
    public List<QuestionAnswerPojo> queryQuestionAnswer(String recordId) {
        return questionAnswerDAO.queryQuestionAnswer(recordId);
    }

    @Transactional(readOnly = true)
    public List<QuestionAnswerPojo> queryQuestionByProblemId(String problemId, String allowId) {
        return questionAnswerDAO.queryAnswerByProblemId(problemId, allowId);
    }

    @Override
    public QuestionRecordPojo getQuestionRecordByRecordId(String questionAllocId) {
        return questionAnswerDAO.getQuestionRecordByRecordId(questionAllocId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportQuestionOptionPojo> getReportQuestionOption(String recordId) {
        LOGGER.info("【业务层】查询问卷记录：recordId=" + recordId);
        if (StringUtils.isEmpty(recordId)) {
            return null;
        }
        List<ReportQuestionOptionPojo> reportQuestionOptionVos = new ArrayList<ReportQuestionOptionPojo>();
        // 查询问卷问题答案表
        List<QuestionAnswerPojo> questionAnswerVos = questionAnswerDAO.queryQuestionAnswer(recordId);
        if (CollectionUtils.isEmpty(questionAnswerVos)) {
            return null;
        }
        for (QuestionAnswerPojo questionAnswerVo : questionAnswerVos) {
            if (questionAnswerVo == null) {
                continue;
            }
            QuestionProblemsPojo questionProblemsVo = questionDAO.getProblemByProblemId(questionAnswerVo
                    .getProblemId());
            if (questionProblemsVo == null) {
                continue;
            }
            QuestionProblemOptionsPojo questionProblemOptionsVo = problemDAO
                    .getProblemOptionByRecordId(questionAnswerVo
                            .getProblemOptionId());
            if (questionProblemOptionsVo == null) {
                continue;
            }
            reportQuestionOptionVos.add(this.transferToReportQuestionOptionVo(questionAnswerVo, questionProblemsVo,
                    questionProblemOptionsVo));
        }
        return reportQuestionOptionVos;
    }

    /**
     * 
     * 转换ReportQuestionOptionVo
     * 
     * @author Administrator
     * @param questionAnswerVo
     * @param questionProblemsVo
     * @param questionProblemOptionsVo
     * @return
     */
    private ReportQuestionOptionPojo transferToReportQuestionOptionVo(QuestionAnswerPojo questionAnswerVo,
            QuestionProblemsPojo questionProblemsVo, QuestionProblemOptionsPojo questionProblemOptionsVo) {
        ReportQuestionOptionPojo reportQuestionOptionVo = new ReportQuestionOptionPojo();
        if (StringUtils.isNotEmpty(questionAnswerVo.getAnswerContent())) {
            reportQuestionOptionVo.setAnswerContent(Integer.valueOf(questionAnswerVo.getAnswerContent()));
        }
        reportQuestionOptionVo.setAnswerId(questionAnswerVo.getAnswerId());
        reportQuestionOptionVo.setOptionContent(questionProblemOptionsVo.getOptionContent());
        reportQuestionOptionVo.setOptionType(questionProblemOptionsVo.getOptionType());
        reportQuestionOptionVo.setProblemContent(questionProblemsVo.getProblemContent());
        reportQuestionOptionVo.setProblemOptionId(questionAnswerVo.getProblemOptionId());
        reportQuestionOptionVo.setProblemType(questionProblemsVo.getProblemType());
        return reportQuestionOptionVo;
    }

    @Override
    public void deleteQuestionAnswer(String questionAllocId) {
        questionAnswerDAO.deleteQuestionAnswer(questionAllocId);
    }
}
