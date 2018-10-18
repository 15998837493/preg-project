
package com.mnt.preg.master.pojo.question;

import java.io.Serializable;
import java.util.List;

/**
 * 问题库列表
 * O
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 gss 初版
 */
public class QuestionProblemsParentPojo implements Serializable {

    private static final long serialVersionUID = -5520672959999931120L;

    private List<QuestionProblemsPojo> questionProblemsList;

    private QuestionProblemsPojo questionProblemsVo;

    private List<QuestionProblemOptionsPojo> questionProblemOptionsList;

    public List<QuestionProblemsPojo> getQuestionProblemsList() {
        return questionProblemsList;
    }

    public void setQuestionProblemsList(List<QuestionProblemsPojo> questionProblemsList) {
        this.questionProblemsList = questionProblemsList;
    }

    public QuestionProblemsPojo getQuestionProblemsVo() {
        return questionProblemsVo;
    }

    public void setQuestionProblemsVo(QuestionProblemsPojo questionProblemsVo) {
        this.questionProblemsVo = questionProblemsVo;
    }

    public List<QuestionProblemOptionsPojo> getQuestionProblemOptionsList() {
        return questionProblemOptionsList;
    }

    public void setQuestionProblemOptionsList(List<QuestionProblemOptionsPojo> questionProblemOptionsList) {
        this.questionProblemOptionsList = questionProblemOptionsList;
    }

}
