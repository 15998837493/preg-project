
package com.mnt.preg.master.controller.question;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.entity.Customer;
import com.mnt.preg.customer.entity.QuesAllocation;
import com.mnt.preg.customer.entity.QuestionAnswer;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.customer.pojo.QuestionRecordPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.service.QuestionAnswerService;
import com.mnt.preg.examitem.util.QuestionConstant;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.master.entity.question.Problem;
import com.mnt.preg.master.entity.question.Question;
import com.mnt.preg.master.entity.question.QuestionProblemOptions;
import com.mnt.preg.master.entity.question.QuestionProblems;
import com.mnt.preg.master.form.question.ProblemForm;
import com.mnt.preg.master.mapping.QuestionMapping;
import com.mnt.preg.master.mapping.QuestionPageName;
import com.mnt.preg.master.pojo.question.OptionPojo;
import com.mnt.preg.master.pojo.question.ProblemPojo;
import com.mnt.preg.master.pojo.question.QuestionPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemOptionsPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemsParentPojo;
import com.mnt.preg.master.pojo.question.QuestionProblemsPojo;
import com.mnt.preg.master.pojo.question.QuestionRecordBo;
import com.mnt.preg.master.service.question.ProblemService;
import com.mnt.preg.master.service.question.QuestionService;
import com.mnt.preg.platform.entity.PregArchives;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregArchivesService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.util.FoodsFormulaUtil;
import com.mnt.preg.system.pojo.UserPojo;
import com.mnt.preg.system.service.UserAssistantService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.mapping.CommonPageName;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 问卷库Controller
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
@Controller
public class QuestionController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Resource
    private ProblemService problemService;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    @Resource
    private PregDiagnosisService diagnosisService;

    @Resource
    private CustomerService customerService;

    @Resource
    private PregArchivesService archivesService;

    @Resource
    private UserAssistantService userAssistantService;

    /**
     * 
     * 异步获取体检项目字典信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_QUESTION)
    public @ResponseBody
    WebResult<QuestionPojo> queryQuestion(Question condition) {
        WebResult<QuestionPojo> webResult = new WebResult<QuestionPojo>();
        List<QuestionPojo> questionListVo = questionService.queryQuestion(condition);
        webResult.setData(questionListVo);
        return webResult;
    }

    /**
     * 问卷问卷信息删除
     * 
     * @param id
     *            主键
     * @return
     * 
     */
    @RequestMapping(value = QuestionMapping.QUESTION_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeItem(HttpServletRequest request, @RequestParam String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isNotEmpty(id)) {
            webResult.setError("参数为空");
        }
        questionService.removeQuestion(id);
        webResult.setSuc(true, "成功");

        return webResult;
    }

    /**
     * 
     * 问卷增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = QuestionMapping.ADD_INIT_QUESTION)
    public String initAddQuestion(HttpServletRequest request, Model model) {
        return QuestionPageName.QUESTION_INIT_ADD;
    }

    /**
     * 问卷记录增加功能
     * 
     * @param questionForm
     * @return
     */
    @RequestMapping(value = QuestionMapping.ADD_QUESTION)
    public @ResponseBody
    WebResult<QuestionPojo> addQuestion(QuestionPojo questionVo) {
        WebResult<QuestionPojo> webResult = new WebResult<QuestionPojo>();
        String questionId = questionService.saveQuestion(questionVo);
        webResult.setSuc(questionService.getQuestionById(questionId));
        return webResult;
    }

    /**
     * 
     * 项目修改初始化
     * 
     * @param request
     * @param familyId
     * @param model
     * @return
     */
    @RequestMapping(value = QuestionMapping.UPDATE_INIT_QUESTION)
    public String questionUpdateInit(HttpServletRequest request, @RequestParam String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            model.addAttribute("error_msg", "主键不能为空");
            return CommonPageName.ERROR_PAGE;
        }
        QuestionPojo questionVo = new QuestionPojo();
        questionVo = questionService.getQuestionById(id);
        if (questionVo == null) {
            questionVo = new QuestionPojo();
        }
        model.addAttribute("question", questionVo);
        return QuestionPageName.QUESTION_UPDATE;
    }

    /**
     * 项目修改
     * 
     * @param itemForm
     *            修改表单数据
     * @return
     * 
     */
    @RequestMapping(value = QuestionMapping.UPDATE_QUESTION)
    public @ResponseBody
    WebResult<QuestionPojo> questionUpdate(QuestionPojo questionVo) {
        WebResult<QuestionPojo> webResult = new WebResult<QuestionPojo>();
        questionService.updateQuestion(questionVo, questionVo.getQuestionId());
        webResult.setSuc(questionService.getQuestionById(questionVo.getQuestionId()));
        return webResult;
    }

    /**
     * 
     * 问卷增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = QuestionMapping.ADD_INIT_QUESTION_CONFIG)
    public String initQuestionConfig(HttpServletRequest request, Model model, String id) {
        model.addAttribute("questionId", id);
        return QuestionPageName.QUESTION_INIT_CONFIG;
    }

    /**
     * 
     * 异步获取选项记录
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_QUESTION_PROBLEM_OPTION)
    public @ResponseBody
    List<QuestionProblemOptionsPojo> queryOption(String id) {
        if (StringUtils.isEmpty(id)) {
            return new ArrayList<QuestionProblemOptionsPojo>();
        }
        List<QuestionProblemOptionsPojo> optionListVo = new ArrayList<QuestionProblemOptionsPojo>();
        optionListVo = questionService.getQuestionProblemOptionsByProblemId(id);
        return optionListVo;
    }

    /**
     * 
     * 异步获取选项记录
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_OPTION_PROBLEMID)
    public @ResponseBody
    List<QuestionProblemOptionsPojo> queryQuestionProblemOption(QuestionProblemOptions condition) {
        List<QuestionProblemOptionsPojo> optionListVo = new ArrayList<QuestionProblemOptionsPojo>();
        if (StringUtils.isEmpty(condition.getProblemId())) {
            return optionListVo;
        }
        optionListVo = questionService.queryQuestionProblemOptions(condition);
        return optionListVo;
    }

    /**
     * 
     * 异步获取体检项目字典信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_QUESTION_PROBLEM)
    public @ResponseBody
    WebResult<Boolean> queryProblem(QuestionProblems condition) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<QuestionProblemsPojo> list = questionAnswerService.queryQuestionProblems(condition);
        webResult.setData(list);
        return webResult;
    }

    /**
     * 
     * 移动问卷问题
     * 
     * @author scd
     */
    @RequestMapping(value = QuestionMapping.MOVE_QUESTION_PROBLEM)
    public @ResponseBody
    WebResult<Boolean> upwardProblem(String editProblemId, String moveFlag) {
        WebResult<Boolean> resultEntity = new WebResult<Boolean>();
        // flag小于0为向上移动，大于0为向下移动
        if (!moveFlag.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?")) {
            resultEntity.setSuc(false, "移动标识不是数字类型！");
            return resultEntity;
        }
        Integer flag = Integer.parseInt(moveFlag);
        QuestionProblemsPojo problem = questionAnswerService.getQuestionProblemsById(editProblemId);
        if (problem == null) {
            resultEntity.setSuc(false, "移动的问题根本不存在！");
            return resultEntity;
        }
        if (problem.getProblemOrder() <= 1 && flag < 0) {
            resultEntity.setSuc(false, "已经是最上层了！");
            return resultEntity;
        }
        Integer maxOrder = questionAnswerService.getQuestionProblemsMaxOderNo(problem
                .getQuestionId());
        if (maxOrder == problem.getProblemOrder() && flag > 0) {
            resultEntity.setSuc(false, "已是最下层");
            return resultEntity;
        }

        QuestionProblemsPojo nearQuestionProblemsVo = new QuestionProblemsPojo();
        // 根据problemId查询问题
        QuestionProblemsPojo questionProblemsVo = questionAnswerService.getQuestionProblemsById(editProblemId);
        // 查询移动对象的上一个问题的基本信息,(problemOrder小于等于1和大于最大值时已经被拦截)
        nearQuestionProblemsVo = getPreviousProblem(questionProblemsVo.getQuestionId(),
                questionProblemsVo.getProblemOrder() + flag, flag);
        if (nearQuestionProblemsVo == null) {
            resultEntity.setSuc(false, "无法移动！");
            return resultEntity;
        }
        // 设置问题排序号
        Integer nearOrder = nearQuestionProblemsVo.getProblemOrder();
        Integer movingOrder = questionProblemsVo.getProblemOrder();
        questionProblemsVo.setProblemOrder(nearOrder);
        nearQuestionProblemsVo.setProblemOrder(movingOrder);
        // 修改
        questionAnswerService.updateQuestionProblems(questionProblemsVo, editProblemId);
        questionAnswerService.updateQuestionProblems(nearQuestionProblemsVo, nearQuestionProblemsVo.getProblemId());
        resultEntity.setSuc(true, "成功");
        return resultEntity;
    }

    /**
     * 
     * 查询移动对象的上一个问题的基本信息
     * 
     * @author scd
     * @param questionId
     * @param problemOrder
     * @return
     */
    public QuestionProblemsPojo getPreviousProblem(String questionId, Integer problemOrder, Integer flag) {
        // 查询移动对象的上一个问题的基本信息
        QuestionProblemsPojo nearQuestionProblemsVo = questionAnswerService.getQuestionProblemsByIdAndProblemOrder(
                questionId, problemOrder);
        if (nearQuestionProblemsVo == null) {
            if (problemOrder > 1) {
                problemOrder = problemOrder + flag;
                nearQuestionProblemsVo = getPreviousProblem(questionId, problemOrder, flag);
            }
        }
        return nearQuestionProblemsVo;
    }

    /**
     * 问卷问卷信息删除
     * 
     * @param id
     *            主键
     * @return
     * 
     */
    @RequestMapping(value = QuestionMapping.QUESTION_PROBLEM_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeQuestionProblem(HttpServletRequest request,
            @RequestParam String editProblemId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isEmpty(editProblemId)) {
            webResult.setError("参数为空");
        }
        questionAnswerService.removeQuestionProblems(editProblemId);
        webResult.setSuc(true, "成功");

        return webResult;
    }

    /**
     * 
     * 异步获取选项记录
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_QUESTION_OPTION)
    public @ResponseBody
    WebResult<QuestionProblemOptionsPojo> queryQuestionOption(String problemId) {
        WebResult<QuestionProblemOptionsPojo> webResult = new WebResult<QuestionProblemOptionsPojo>();
        QuestionProblemOptions condition = new QuestionProblemOptions();
        condition.setProblemId(problemId);
        webResult.setData(questionService.queryQuestionProblemOptions(condition));
        return webResult;
    }

    /**
     * 
     * 根据问卷编号及问卷问题id查询该问题排序号比它小的选择（单选、多选）题
     * 
     * @author gss
     * @param questionId
     *            问卷id
     * @param problemId
     *            问题编号
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_PROBLEM_LIST)
    public @ResponseBody
    WebResult<QuestionProblemsParentPojo> getQuestionProblemsParentList(Model model, String questionId,
            String problemId) {
        WebResult<QuestionProblemsParentPojo> webResult = new WebResult<QuestionProblemsParentPojo>();

        QuestionProblemsParentPojo questionProblemsParentListVo = new QuestionProblemsParentPojo();
        List<QuestionProblemOptionsPojo> questionProblemOptionsVos = null;
        List<QuestionProblemsPojo> questionProblemsListVo = questionAnswerService
                .queryQuestionProblemsForParentPage(questionId, problemId);
        if (questionProblemsListVo == null) {
            questionProblemsListVo = new ArrayList<QuestionProblemsPojo>();
        }
        if (CollectionUtils.isEmpty(questionProblemsListVo)) {
            questionProblemsParentListVo = new QuestionProblemsParentPojo();
        }
        // 当前问题的基本信息
        QuestionProblemsPojo questionProblemsVo = questionAnswerService.getQuestionProblemsById(problemId);
        if (questionProblemsVo != null && StringUtils.isNotEmpty(questionProblemsVo.getProblemParentId())) {
            questionProblemOptionsVos = questionService
                    .getQuestionProblemOptionsByProblemId(questionProblemsVo.getProblemParentId());
        }
        // 如果有选项选项的基本信息
        questionProblemsParentListVo.setQuestionProblemOptionsList(questionProblemOptionsVos);
        questionProblemsParentListVo.setQuestionProblemsVo(questionProblemsVo);
        questionProblemsParentListVo.setQuestionProblemsList(questionProblemsListVo);

        webResult.setSuc(questionProblemsParentListVo);
        return webResult;
    }

    /**
     * 
     * 根据问卷id查询问题选项列表
     * 
     * @author scd
     * @param problemId
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_PROBLEM_OPTION_LIST)
    public @ResponseBody
    WebResult<QuestionProblemsParentPojo> getoptionsList(String problemParentId, String problemCurrentId) {
        WebResult<QuestionProblemsParentPojo> webResult = new WebResult<QuestionProblemsParentPojo>();
        // 参数为空处理
        if (StringUtils.isEmpty(problemParentId) || StringUtils.isEmpty(problemCurrentId)) {
            webResult.setError("参数错误！");
            return webResult;
        }

        QuestionProblemsParentPojo questionProblemsParentListVo = new QuestionProblemsParentPojo();
        // 当前问题的基本信息
        QuestionProblemsPojo questionProblemsVo = questionAnswerService.getQuestionProblemsById(problemCurrentId);
        // 选择关联问题的选项
        List<QuestionProblemOptionsPojo> questionProblemOptionsVos = questionService
                .getQuestionProblemOptionsByProblemId(problemParentId);

        questionProblemsParentListVo.setQuestionProblemOptionsList(questionProblemOptionsVos);
        questionProblemsParentListVo.setQuestionProblemsVo(questionProblemsVo);
        webResult.setSuc(questionProblemsParentListVo);
        return webResult;
    }

    /**
     * 
     * 更新问题的父节点
     * 
     * @author gss
     * @param request
     * @param parentProblemId
     *            父问题id
     * @param problemId
     * @param checkedValues
     * @return
     */
    @RequestMapping(value = QuestionMapping.SAVE_PARENT_NODE)
    public @ResponseBody
    WebResult<Boolean> saveParentNode(String parentProblemId, String problemId, String checkedValues) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isNotEmpty(problemId) && StringUtils.isNotEmpty(checkedValues)) {
            // 首先更新问题父节点及规则
            QuestionProblemsPojo questionProblemsVo = questionAnswerService.getQuestionProblemsById(problemId);
            questionProblemsVo.setProblemRule(checkedValues.substring(0, checkedValues.length() - 1));
            questionProblemsVo.setProblemParentId(parentProblemId);
            questionProblemsVo.setProblemLevel(1);// 设置该问题为一级（1标识）而不是顶级（0标识）
            // questionProblemsVo.setFlag(PublicConstant.FLAG_DELETED);
            questionAnswerService.updateQuestionProblems(questionProblemsVo, problemId);
            // 更新父问题有子节点
            QuestionProblemsPojo questionParentProblemsVo = questionAnswerService
                    .getQuestionProblemsById(parentProblemId);
            questionParentProblemsVo.setProblemIsChildren(1);// 设置有子节点
            questionAnswerService.updateQuestionProblems(questionParentProblemsVo, parentProblemId);
            webResult.setSuc(true, "成功");
        } else {
            webResult.setError("请选择问题和选项！", false);
        }

        return webResult;
    }

    /**
     * 
     * 异步获取体检项目字典信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_PROBLEM_MODEL)
    public @ResponseBody
    WebResult<List<ProblemPojo>> queryProblemList(Problem condition) {
        WebResult<List<ProblemPojo>> webResult = new WebResult<List<ProblemPojo>>();
        List<ProblemPojo> problemListDto = new ArrayList<ProblemPojo>();
        problemListDto = problemService.queryProblem(condition);
        webResult.setData(problemListDto);
        return webResult;
    }

    /**
     * 
     * 添加问题库中的问题到问卷中
     * 
     * @author gss
     * @param problemId
     * @return
     */
    @RequestMapping(value = QuestionMapping.ADD_LIBPROBLEM_QUESTION)
    public @ResponseBody
    WebResult<Boolean> addLibproblemQuestion(String problemId, String questionId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 根据problemId查询问题
        ProblemPojo problemVo = problemService.getProblemById(problemId);
        QuestionProblemsPojo questionProblemsVo = TransformerUtils
                .transformerProperties(QuestionProblemsPojo.class, problemVo);
        questionProblemsVo.setProblemLevel(0);
        questionProblemsVo.setProblemIsChildren(0);
        questionProblemsVo.setProblemParentId("0");
        questionProblemsVo.setQuestionId(questionId);
        // 查询该问题选项的最大排序号
        Integer maxOrder = questionAnswerService.getQuestionProblemsMaxOderNo(questionId);
        if (maxOrder == null) {
            maxOrder = 0;
        }
        if (questionProblemsVo.getProblemOrder() == null) {
            questionProblemsVo.setProblemOrder(maxOrder + 1);
        }
        String questionProblemId = questionAnswerService.saveQuestionProblems(questionProblemsVo);
        // 查询问题对应的选项
        List<OptionPojo> optionVos = problemService.getOptionsByProblemId(problemVo.getProblemId());
        if (CollectionUtils.isNotEmpty(optionVos)) {
            for (OptionPojo optionVo : optionVos) {
                if (optionVo == null) {
                    continue;
                }
                QuestionProblemOptionsPojo questionProblemOptionsVo = TransformerUtils
                        .transformerProperties(QuestionProblemOptionsPojo.class, optionVo);
                questionProblemOptionsVo.setProblemId(questionProblemId);
                questionService.addQuestionProblemOptions(questionProblemOptionsVo);
            }
        }
        webResult.setSuc(true, "成功");
        return webResult;
    }

    /**
     * 问题记录增加功能
     * 
     * @param problemForm
     * @return
     */
    @RequestMapping(value = QuestionMapping.ADD_QUESTIONS_PROBLEM)
    public @ResponseBody
    WebResult<QuestionProblemsPojo> addProblem(HttpServletRequest request) {
        WebResult<QuestionProblemsPojo> webResult = new WebResult<QuestionProblemsPojo>();
        String fromParams = request.getParameter("fromParams");
        String optionParams = request.getParameter("optionParams");
        ProblemForm problemForm = (ProblemForm) NetJsonUtils.jsonToObject(fromParams, ProblemForm.class);
        @SuppressWarnings("unchecked")
        List<QuestionProblemOptionsPojo> options = NetJsonUtils
                .jsonArrayToList(optionParams, QuestionProblemOptionsPojo.class);
        QuestionProblemsPojo questionProblemsVo = TransformerUtils.transformerProperties(QuestionProblemsPojo.class,
                problemForm);
        // if (questionProblemsVo == null) {
        // webResult.setSuc(true, "成功");;
        // return webResult;
        // }
        questionProblemsVo.setProblemLevel(0);
        questionProblemsVo.setProblemIsChildren(0);
        questionProblemsVo.setProblemParentId("0");
        // 查询该问题选项的最大排序号
        Integer maxOrder = questionAnswerService.getQuestionProblemsMaxOderNo(questionProblemsVo.getQuestionId());
        if (maxOrder == null) {
            maxOrder = 0;
        }
        if (questionProblemsVo.getProblemOrder() == null) {
            questionProblemsVo.setProblemOrder(maxOrder + 1);
        }
        String problemId = questionAnswerService.saveQuestionProblems(questionProblemsVo);
        String optionType = "";
        if (questionProblemsVo.getProblemType().equals("1") || questionProblemsVo.getProblemType().equals("2")) {
            optionType = "1";
        } else if (questionProblemsVo.getProblemType().equals("3") || questionProblemsVo.getProblemType().equals("4")) {
            optionType = "2";
        }
        if (CollectionUtils.isNotEmpty(options)) {
            for (QuestionProblemOptionsPojo optionVo : options) {
                if (optionVo == null) {
                    continue;
                }
                if (StringUtils.isEmpty(optionVo.getOptionSex())) {
                    optionVo.setOptionSex(questionProblemsVo.getProblemSex());
                }
                optionVo.setOptionType(optionType);
                optionVo.setProblemId(problemId);
                questionService.addQuestionProblemOptions(optionVo);
            }
        }
        QuestionProblemsPojo questionProblemPojo = questionAnswerService.getQuestionProblemsById(problemId);
        webResult.setSuc(questionProblemPojo);
        return webResult;

    }

    /**
     * 
     * 问题增加页面初始化
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = QuestionMapping.ADD_INIT_QUESTION_PROBLEM)
    public String initAddProblem(HttpServletRequest request, Model model, String questionId) {
        model.addAttribute("questionId", questionId);
        return QuestionPageName.QUESTION_PROBLEM_INIT_ADD;
    }

    /**
     * 
     * 项目修改初始化
     * 
     * @param request
     * @param familyId
     * @param model
     * @return
     */
    @RequestMapping(value = QuestionMapping.UPDATE_INIT_QUESTION_PROBLEM)
    public String problemUpdateInit(HttpServletRequest request, String problemId, Model model) {
        if (StringUtils.isEmpty(problemId)) {
            model.addAttribute("error_msg", "主键不能为空");
            return CommonPageName.ERROR_PAGE;
        }
        QuestionProblemsPojo problemVo = new QuestionProblemsPojo();
        problemVo = questionAnswerService.getQuestionProblemsById(problemId);
        if (problemVo == null) {
            problemVo = new QuestionProblemsPojo();
        }
        List<QuestionProblemOptionsPojo> options = new ArrayList<QuestionProblemOptionsPojo>();
        options = questionService.getQuestionProblemOptionsByProblemId(problemId);
        if (options == null) {
            options = new ArrayList<QuestionProblemOptionsPojo>();
        }
        ProblemForm problemForm = TransformerUtils.transformerProperties(ProblemForm.class, problemVo);
        model.addAttribute("problem", problemForm);
        model.addAttribute("options", options);
        return QuestionPageName.QUESTION_PROBLEM_UPDATE;
    }

    /**
     * 项目修改
     * 
     * 修改表单数据
     * 
     * @return
     * 
     */
    @RequestMapping(value = QuestionMapping.UPDATE_QUESTION_PROBLEM)
    public @ResponseBody
    WebResult<QuestionProblemsPojo> problemUpdate(HttpServletRequest request) {
        WebResult<QuestionProblemsPojo> webResult = new WebResult<QuestionProblemsPojo>();
        String fromParams = request.getParameter("fromParams");
        String optionParams = request.getParameter("optionParams");
        ProblemForm problemForm = (ProblemForm) NetJsonUtils.jsonToObject(fromParams, ProblemForm.class);
        @SuppressWarnings("unchecked")
        List<QuestionProblemOptionsPojo> options = NetJsonUtils
                .jsonArrayToList(optionParams, QuestionProblemOptionsPojo.class);
        if (StringUtils.isNotEmpty(problemForm.getProblemId())) {
            QuestionProblemsPojo questionProblemsVo = TransformerUtils
                    .transformerProperties(QuestionProblemsPojo.class, problemForm);
            // 首先删除这改问题的所有选项
            questionService.deleteOptionByProblemId(questionProblemsVo.getProblemId());
            // 更新改问题
            this.questionAnswerService.updateQuestionProblems(questionProblemsVo, questionProblemsVo.getProblemId());
            // 保存改问题的选项
            String optionType = "";
            if (questionProblemsVo.getProblemType().equals("1") || questionProblemsVo.getProblemType().equals("2")) {
                optionType = "1";
            } else if (questionProblemsVo.getProblemType().equals("3")
                    || questionProblemsVo.getProblemType().equals("4")) {
                optionType = "2";
            }
            if (CollectionUtils.isNotEmpty(options)) {
                for (QuestionProblemOptionsPojo optionVo : options) {
                    if (optionVo == null) {
                        continue;
                    }
                    if (StringUtils.isEmpty(optionVo.getOptionSex())) {
                        optionVo.setOptionSex(questionProblemsVo.getProblemSex());
                    }
                    optionVo.setOptionType(optionType);
                    optionVo.setProblemId(questionProblemsVo.getProblemId());
                    questionService.addQuestionProblemOptions(optionVo);
                }
            }
            QuestionProblemsPojo questionProblemPojo = questionAnswerService.getQuestionProblemsById(questionProblemsVo
                    .getProblemId());
            webResult.setSuc(questionProblemPojo);
        }
        return webResult;
    }

    @RequestMapping(value = QuestionMapping.SAVE_QUESTION_ARCHIVE)
    public @ResponseBody
    WebResult<QuestionRecordPojo> saveQuestionInfo(String allocId, String diagnosisId,
            String questionAnswers) {
        WebResult<QuestionRecordPojo> webResult = new WebResult<QuestionRecordPojo>();
        if (StringUtils.isEmpty(allocId)) {
            webResult.setError(WebMsgConstant.fail_message);
            LOGGER.error("[问卷调查]保存问卷信息异常,分配编号为空");
            return webResult;
        }
        Gson gson = new Gson();
        List<QuestionAnswer> questionAnswerBos = gson.fromJson(questionAnswers,
                new TypeToken<List<QuestionAnswer>>(){}.getType());

        QuestionRecordBo questionRecordBo = new QuestionRecordBo();
        questionRecordBo.setQuestionAllocId(allocId);
        questionRecordBo.setDiagnosisId(diagnosisId);
        questionRecordBo.setSubmitDate(new Date());
        QuestionRecordPojo questionRecordDto = this.saveQuestionRecord(questionRecordBo, questionAnswerBos);
        webResult.setSuc(questionRecordDto, WebMsgConstant.success_message);
        return webResult;
    }

    /****************************************** 原business方法 *******************************************/

    public QuestionRecordPojo saveQuestionRecord(QuestionRecordBo questionRecordBo,
            List<QuestionAnswer> questionAnswerBos) {
        questionRecordBo.setState(QuestionConstant.QUESTION_STATUS_FINISH);
        // 为方便测试暂时设置
        questionRecordBo.setCreateInsId("C000000");
        // 1 查询问卷分配信息
        QuesAllocation quesAllocationVo = questionService.getAllocation(questionRecordBo.getQuestionAllocId());
        if (quesAllocationVo == null) {
            LOGGER.warn("【提交问卷】问卷分配明细未能查询到");
            throw new ServiceException(ResultCode.ERROR_90001);
        }
        // 2 保存问卷操作记录
        if (questionAnswerService.getQuestionnaireInfo(questionRecordBo.getQuestionAllocId()) != null) {
            // 如果存在则删除该操作记录
            questionAnswerService.deleteQuestionnaireInfo(questionRecordBo.getQuestionAllocId());
        }
        questionRecordBo.setAllocDatetime(quesAllocationVo.getAllocDatetime());
        questionRecordBo.setCustId(quesAllocationVo.getCustId());
        questionRecordBo.setExpiryDate(quesAllocationVo.getExpiryDate());
        questionRecordBo.setQuestionId(quesAllocationVo.getQuestionId());
        questionRecordBo.setUserId(quesAllocationVo.getUserId());
        // 删除问卷答案明细
        questionAnswerService.deleteQuestionAnswer(questionRecordBo.getQuestionAllocId());
        QuestionRecordPojo questionRecordVo = questionAnswerService.saveQuestionnaire(questionRecordBo);
        if (questionRecordVo != null && StringUtils.isNotEmpty(questionRecordVo.getQuestionAllocId())
                && CollectionUtils.isNotEmpty(questionAnswerBos)) {
            for (QuestionAnswer questionAnswerBo : questionAnswerBos) {
                questionAnswerBo.setQuestionAllocId(questionRecordVo.getQuestionAllocId());
                // 3 保存问卷答案记录
                questionAnswerService.saveQuestionAnswer(questionAnswerBo);
            }
        }
        // 4 删除问卷分配表中的信息
        // questionService.remove(questionRecordBo.getQuestionAllocId());
        // 5 若是诊疗问卷则生成PDF报告
        // TODO: zcq 孕期没有诊疗问卷，是否删除
        // if (StringUtils.isNotEmpty(quesAllocationVo.getQuestionId())
        // && (PublicConstant.ZL_QUESTION_ID).equals(quesAllocationVo.getQuestionId())) {
        // diagnosticReportBusiness.createDiagnosticReport(questionRecordBo.getQuestionAllocId());
        // }
        // 6 保存到数据分析结果表中
        // this.getQuestionAnalyseResult(questionRecordBo.getDiagnosisId(), questionRecordVo.getQuestionAllocId());
        // 保存数据
        /*
         * 根据初诊问卷答案，更新患者表、初诊建档表、接诊表
         */
        if (StringUtils.isNotEmpty(quesAllocationVo.getQuestionId())
                && "C000000CA00000000006".equals(quesAllocationVo.getQuestionId())) {
            // WebApplicationContext applicationContext = CacheProjectInfo.getInstance().getApplicationContext();
            // PregDiagnosisBusiness newlyDiagnosed = (PregDiagnosisBusiness) applicationContext
            // .getBean(PregDiagnosisBusiness.class);
            this.saveNewlyDiagnosedResult(questionRecordBo.getDiagnosisId(),
                    questionRecordBo.getQuestionAllocId());
        }
        return questionRecordVo;
    }

    public void saveNewlyDiagnosedResult(String diagnosisId, String allocId) {
        // 根据分配编码查询出问卷答案表
        Map<String, QuestionAnswerPojo> answerM = this.getQuestionAnswerMapByExamCode(allocId);
        Map<String, QuestionProblemOptionsPojo> optionsM = questionService.queryQuestionProblemOptionsAll();
        PregDiagnosisPojo diagnosisVo = diagnosisService.getDiagnosis(diagnosisId);
        // 客户表
        CustomerPojo customerVo = customerService.getCustomer(diagnosisVo.getDiagnosisCustomer());
        // 建档表
        PregArchives archives = new PregArchives();
        PregArchivesPojo archivesVo = archivesService.getLastPregnancyArchive(diagnosisVo.getDiagnosisCustomer());
        // 保存结论
        List<String> resultList = new ArrayList<String>();
        // 大约预产期重新添加问卷
        if (archivesVo == null
                || JodaTimeTools.compareDate(diagnosisVo.getDiagnosisDate(), archivesVo.getPregnancyDueDate()) > 0) {
            archives.setId(UUID.randomUUID().toString());
            // 年龄
            Integer age = 0;
            String birthday = this.getOptionContentByOptionId("110000200000259", answerM);
            if (StringUtils.isNotEmpty(birthday)) {
                age = JodaTimeTools.getYears(JodaTimeTools.toDate(birthday), new Date());
                archives.setCustAge(age);
            }
        } else {
            archives.setId(archivesVo.getId());
            // 年龄
            Integer age = 0;
            age = JodaTimeTools.getYears(customerVo.getCustBirthday(), archivesVo.getCreateDatetime());
            archives.setCustAge(age);
        }
        archives.setCustId(customerVo.getCustId());

        // 身高
        String height = this.getOptionContentByOptionId("11000022017072800008", answerM);
        if (StringUtils.isNotEmpty(height)) {
            customerVo.setCustHeight(new BigDecimal(height));
            archives.setHeight(new BigDecimal(height));
        } else {
            customerVo.setCustHeight(null);
            archives.setHeight(null);
        }
        // 体重
        String weight = this.getOptionContentByOptionId("11000022017072800009", answerM);
        if (StringUtils.isNotEmpty(weight)) {
            customerVo.setCustWeight(new BigDecimal(weight));
            archives.setWeight(new BigDecimal(weight));
        } else {
            customerVo.setCustWeight(null);
            archives.setWeight(null);
        }

        // 结论--bmi状况
        Double bmi = Double.valueOf(weight) / ((Double.valueOf(height) / 100) * (Double.valueOf(height) / 100));
        if (bmi <= 18.5) {
            resultList.add("孕前消瘦");
        } else if (bmi > 28) {
            resultList.add("孕前肥胖");
        } else if (bmi > 23.9) {
            resultList.add("孕前超重");
        }

        // 生日
        String birthday = this.getOptionContentByOptionId("110000200000259", answerM);
        if (StringUtils.isNotEmpty(birthday)) {
            customerVo.setCustBirthday(JodaTimeTools.toDate(birthday));
        }
        // 末次月经
        String lmp = this.getOptionContentByOptionId("110000200000258", answerM);
        if (StringUtils.isNotEmpty(lmp)) {
            Date lmpDate = JodaTimeTools.toDate(lmp);
            customerVo.setLmp(lmpDate);
            archives.setLmp(lmpDate);
            // 预产期
            archives.setPregnancyDueDate(JodaTimeTools.after_day(lmpDate, 280));
        } else {
            archives.setLmp(null);
        }
        // 妊娠情况
        String encyesisSituation = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91ba3c7a0001", optionsM);
        if (StringUtils.isNotEmpty(encyesisSituation)) {
            // 如果是自然受孕，获取受孕方式
            if ("自然受孕".equals(encyesisSituation)) {
                String shouYun = this.getquestionAnswerString(answerM, "402880f4650b0fbb01650b255b860009", optionsM);
                if (StringUtils.isNotEmpty(shouYun)) {
                    archives.setEncyesisSituation("自然受孕（" + shouYun + "）");
                }
            }
            // 如果是辅助生殖
            if ("辅助生殖".equals(encyesisSituation)) {
                archives.setEncyesisSituation(encyesisSituation);
            }
        } else {
            archives.setEncyesisSituation("");
        }

        // 结论--妊娠情况 辅助生殖
        if ("辅助生殖".equals(encyesisSituation)) {
            resultList.add("本次妊娠为辅助生殖");
        }

        // 妊娠胚胎数
        String embryoNum = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91bb56150002", optionsM);
        if (StringUtils.isNotEmpty(embryoNum)) {
            archives.setEmbryoNum(embryoNum);
        } else {
            archives.setEmbryoNum("");
        }

        // 结论--妊娠胎数 双胎；三胎以上
        if ("双胎".equals(embryoNum)) {
            resultList.add(embryoNum);
        } else if ("三胎及以上".equals(embryoNum)) {
            resultList.add(embryoNum);
        }

        // 孕前叶酸服用情况getOptionContentByOptionId
        String folicSituation = this.getOptionContentByOptionId("402880ef5a91a2b1015a91be5d9e0004", answerM);
        if (StringUtils.isNotEmpty(folicSituation)) {
            archives.setFolicSituation(folicSituation);
        } else {
            archives.setFolicSituation("");
        }

        // 结论--服用叶酸情况
        if (StringUtils.isNotEmpty(folicSituation)) {
            resultList.add("孕前服用叶酸" + folicSituation + "个月");
        }

        // 初潮年龄
        String menarcheAge = this.getOptionContentByOptionId("210100700000156", answerM);
        if (StringUtils.isNotEmpty(menarcheAge)) {
            archives.setMenarcheAge(menarcheAge);
        } else {
            archives.setMenarcheAge("");
        }
        // 孕早期存在情况
        String firstTrimesterSituation = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91bd0fed0003",
                optionsM);
        if (StringUtils.isNotEmpty(firstTrimesterSituation)) {
            archives.setFirstTrimesterSituation(firstTrimesterSituation);
        } else {
            archives.setFirstTrimesterSituation("");
        }

        // 结论--孕早期存在状况
        if (StringUtils.isNotEmpty(firstTrimesterSituation)) {
            resultList.add("孕早期" + firstTrimesterSituation);
        }

        // 平均每次月经持续天数
        String mensesDays = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91c158700006",
                optionsM);
        if (StringUtils.isNotEmpty(mensesDays)) {
            archives.setMensesDays(mensesDays);
        } else {
            archives.setMensesDays("");
        }

        // 结论--月经持续天数
        if ("大于7天".equals(mensesDays)) {
            resultList.add("月经持续天数" + mensesDays);
        }

        // 月经周期
        String mensesCycle = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91c276fd0007", optionsM);
        // this.getOptionContentByOptionId("402880ef5a91a2b1015a91c276fd0007",answerM);
        if (StringUtils.isNotEmpty(mensesCycle)) {
            archives.setMensesCycle(mensesCycle);
        } else {
            archives.setMensesCycle("");
        }

        // 结论--月经周期
        if ("≥33天".equals(mensesCycle)) {
            resultList.add("月经周期大于33天");
        }

        // 每次的月经量
        String mensesVolume = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91c34bbf0008",
                optionsM);
        if (StringUtils.isNotEmpty(mensesVolume)) {
            archives.setMensesVolume(mensesVolume);
        } else {
            archives.setMensesVolume("");
        }

        // 结论--月经量
        if ("多".equals(mensesVolume)) {
            resultList.add("月经量多");
        }

        // 痛经的程度
        String dysmenorrheaExtent = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91c42e400009",
                optionsM);
        if (StringUtils.isNotEmpty(dysmenorrheaExtent)) {
            archives.setDysmenorrheaExtent(dysmenorrheaExtent);
        } else {
            archives.setDysmenorrheaExtent("");
        }
        // 病史
        // 首先根据问题编号查询出病史及病史年限的问题选项,其次根据选项查询出选项相对应的年份的选项，最后根据年份的选项查询出具体年份
        List<QuestionAnswerPojo> history = this.getQuestionAnswerVoByProblemId("402880ef5a91a2b1015a91d2aa8b000e",
                answerM);
        // 判断是否有既往病史
        String disHistory = this.getquestionAnswerString(answerM, "402880b35ec68aac015ec78db0b80132", optionsM);
        if ("否".equals(disHistory)) {
            history = null;
        }
        StringBuffer bingshi = new StringBuffer();
        if (CollectionUtils.isNotEmpty(history)) {
            boolean otherDiseaseFlag = false;
            for (QuestionAnswerPojo answer : history) {
                if (answer == null) {
                    continue;
                }
                if ("210100700000199".equals(answer.getProblemOptionId())) {
                    otherDiseaseFlag = true;
                    continue;
                }
                // 获取选项的基本信息
                QuestionProblemOptionsPojo option = optionsM.get(answer.getProblemOptionId());
                if (option == null) {
                    continue;
                }
                String optionName = option.getOptionContent();
                if (this.disease2year().containsKey(option.getProblemOptionId())) {
                    List<QuestionAnswerPojo> yearOption = this.getQuestionAnswerVoByProblemId(
                            this.disease2year().get(option.getProblemOptionId()), answerM);
                    if (CollectionUtils.isNotEmpty(yearOption)) {
                        String yearNum = yearOption.get(0).getAnswerContent();
                        if (StringUtils.isNotEmpty(yearNum)) {
                            optionName = optionName + yearNum + "年";
                        }
                    }

                }
                bingshi.append(optionName).append("，");
            }
            if (otherDiseaseFlag) {
                // 其他疾病
                String anotherDisease = this.getOptionContentByOptionId("402880b35eb6f014015eb794d3710026", answerM);
                if (StringUtils.isNotBlank(anotherDisease)) {
                    bingshi.append(anotherDisease).append("，");
                }
            }
            if (bingshi.length() > 0) {
                String result = bingshi.toString().substring(0, bingshi.toString().length() - 1);
                archives.setDiseaseHistory(result);
                // 结论--病史
                resultList.add("病史:" + result);
            }
        } else {
            archives.setDiseaseHistory("");
        }
        // 家族病史
        String familyHistory = this.getquestionAnswerString(answerM, "402880f9653c86f901653ca01df80000", optionsM);
        String otherfamily = "";
        if (StringUtils.isNotEmpty(familyHistory) && familyHistory.contains("其他")) {
            otherfamily = this.getOptionContentByOptionId("402880f9657a5a2201657a5d309f0002", answerM);
            if (StringUtils.isNotEmpty(otherfamily)) {
                // 输入框内容替换（其他）字符
                familyHistory = familyHistory.replace("其他", "") + otherfamily;
            } else {
                familyHistory = familyHistory.replace("、其他", "");
                familyHistory = familyHistory.replace("其他", "");
            }
        }
        if (StringUtils.isNotEmpty(familyHistory)) {
            archives.setFamilyHistory(familyHistory);
        } else {
            archives.setFamilyHistory("");
        }

        // 结论--家族病史
        if (StringUtils.isNotEmpty(familyHistory)) {
            resultList.add("父母兄弟姐妹患有：" + familyHistory);
        }

        // 用药史
        String medicineHistory = this.getOptionContentByOptionId("210100700000222", answerM);
        if (StringUtils.isNotEmpty(medicineHistory)) {
            archives.setMedicineHistory(medicineHistory);
        } else {
            archives.setMedicineHistory("");
        }
        // 结论--用药史
        if (StringUtils.isNotEmpty(medicineHistory)) {
            resultList.add("用药史：" + medicineHistory);
        }
        // 过敏史

        // 药物过敏
        String allergyHistory = "";
        String medichineAllergy = this.getquestionAnswerString(answerM, "402880f4650b0fbb01650c0a34a50085", optionsM);
        String otherMedichine = "";
        // 如果用户选了其他，则显示其他的内容
        if (StringUtils.isNotEmpty(medichineAllergy) && medichineAllergy.contains("其他")) {
            // 获取对应输入框内容
            otherMedichine = this.getOptionContentByOptionId("402880f965890cff0165891072840004", answerM);
            if (StringUtils.isNotEmpty(otherMedichine)) {
                // int last = medichineAllergy.lastIndexOf("、");
                // medichineAllergy = medichineAllergy.substring(0, last) + "、" + otherMedichine;
                // 输入框内容替换（其他）字符
                medichineAllergy = medichineAllergy.replace("其他", "") + otherMedichine;
            } else {
                medichineAllergy = medichineAllergy.replace("、其他", "");
                medichineAllergy = medichineAllergy.replace("其他", "");
            }
        }
        if (StringUtils.isNotEmpty(medichineAllergy)) {
            if (StringUtils.isEmpty(allergyHistory)) {
                allergyHistory = allergyHistory + medichineAllergy;
            } else {
                allergyHistory = allergyHistory + "、" + medichineAllergy;
            }

        }

        // 食物有过敏
        String foodAllergy = this.getquestionAnswerString(answerM, "402880f4650b0fbb01650c3afa63008b", optionsM);
        String otherFood = "";
        if (StringUtils.isNotEmpty(foodAllergy) && foodAllergy.contains("其他")) {
            otherFood = this.getOptionContentByOptionId("402880f965890cff0165891bfc1e000e", answerM);
            if (StringUtils.isNotEmpty(otherFood)) {
                // 输入框内容替换（其他）字符
                foodAllergy = foodAllergy.replace("其他", "") + otherFood;
            } else {
                foodAllergy = foodAllergy.replace("、其他", "");
                foodAllergy = foodAllergy.replace("其他", "");
            }
        }
        if (StringUtils.isNotEmpty(foodAllergy)) {
            if (StringUtils.isEmpty(allergyHistory)) {
                allergyHistory = allergyHistory + foodAllergy;
            } else {
                allergyHistory = allergyHistory + "、" + foodAllergy;
            }
        }

        // 食物不耐受
        String foodBns = this.getquestionAnswerString(answerM, "402880f4650b0fbb01650c4202170096", optionsM);
        String otherFoodBns = "";
        if (StringUtils.isNotEmpty(foodBns) && foodBns.contains("其他")) {
            otherFoodBns = this.getOptionContentByOptionId("402880f965890cff0165891c2221001d", answerM);
            if (StringUtils.isNotEmpty(otherFoodBns)) {
                // 输入框内容替换（其他）字符
                foodBns = foodBns.replace("其他", "") + otherFoodBns;
            } else {
                foodBns = foodBns.replace("、其他", "");
                foodBns = foodBns.replace("其他", "");
            }
        }
        if (StringUtils.isNotEmpty(foodBns)) {
            if (StringUtils.isEmpty(allergyHistory)) {
                allergyHistory = allergyHistory + foodBns;
            } else {
                allergyHistory = allergyHistory + "、" + foodBns;
            }
        }

        // 其他过敏
        String otherAllergy = this.getquestionAnswerString(answerM, "402880f4650b0fbb01650c45a9a200a6", optionsM);
        String otherOtherAllergy = "";
        if (StringUtils.isNotEmpty(otherAllergy) && otherAllergy.contains("其他")) {
            otherOtherAllergy = this.getOptionContentByOptionId("402880f965890cff0165891c4a530022", answerM);
            if (StringUtils.isNotEmpty(otherOtherAllergy)) {
                // 输入框内容替换（其他）字符
                otherAllergy = otherAllergy.replace("其他", "") + otherOtherAllergy;
            } else {
                otherAllergy = otherAllergy.replace("、其他", "");
                otherAllergy = otherAllergy.replace("其他", "");
            }
        }

        if (StringUtils.isNotEmpty(otherAllergy)) {
            if (StringUtils.isEmpty(allergyHistory)) {
                allergyHistory = allergyHistory + otherAllergy;
            } else {
                allergyHistory = allergyHistory + "、" + otherAllergy;
            }
        }

        // 赋值
        if (StringUtils.isNotEmpty(allergyHistory)) {
            archives.setAllergyHistory(allergyHistory);
        } else {
            archives.setAllergyHistory(allergyHistory);
        }

        // 结论--过敏史
        if (StringUtils.isNotEmpty(allergyHistory)) {
            resultList.add("过敏史:" + allergyHistory);
        }

        // 手术等治疗历史
        String treatmentHistory = this.getOptionContentByOptionId("210100700000223", answerM);
        if (StringUtils.isNotEmpty(treatmentHistory)) {
            archives.setTreatmentHistory(treatmentHistory);
        } else {
            archives.setTreatmentHistory("");
        }

        // 结论--手术史
        if (StringUtils.isNotEmpty(treatmentHistory)) {
            resultList.add("手术史：" + treatmentHistory);
        }

        // 糖尿病病史或存在糖尿病前期状态
        String diabetesRelevant = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91e3ad350025",
                optionsM);
        if (StringUtils.isNotEmpty(diabetesRelevant)) {
            archives.setDiabetesRelevant(diabetesRelevant);
        } else {
            archives.setDiabetesRelevant("");
        }

        // 结论--糖尿病史
        if (StringUtils.isNotEmpty(diabetesRelevant)) {
            if (!"无".equals(diabetesRelevant)) {
                resultList.add("糖尿病前期状态：" + diabetesRelevant);
            }
        }

        // 孕次
        String pregnancyNum = this.getOptionContentByOptionId("210100700000228", answerM);
        if (StringUtils.isNotEmpty(pregnancyNum)) {
            archives.setPregnancyNum(pregnancyNum);
        } else {
            archives.setPregnancyNum("");
        }
        // 产次
        String birthNum = this.getOptionContentByOptionId("210100700000229", answerM);
        if (StringUtils.isNotEmpty(birthNum)) {
            archives.setBirthNum(birthNum);
        } else {
            archives.setBirthNum("");
        }

        // 结论--孕产情况
        if (StringUtils.isNotEmpty(pregnancyNum) && StringUtils.isNotEmpty(birthNum)) {
            resultList.add("孕" + pregnancyNum + "产" + birthNum);
        }
        // 自然流产
        String abortionNum = this.getOptionContentByOptionId("210100700000230", answerM);
        if (StringUtils.isNotEmpty(abortionNum)) {
            archives.setAbortionNum(abortionNum);
        } else {
            archives.setAbortionNum("");
        }

        // 结论--自然流产
        if (StringUtils.isNotEmpty(abortionNum)) {
            resultList.add("自然流产" + abortionNum + "次");
        }

        // 人工流产
        String inducedAbortionNum = this.getOptionContentByOptionId("11000022017092500004", answerM);
        if (StringUtils.isNotEmpty(inducedAbortionNum)) {
            archives.setInducedAbortionNum(inducedAbortionNum);
        } else {
            archives.setInducedAbortionNum("");
        }
        // 结论--人工流产
        if (StringUtils.isNotEmpty(inducedAbortionNum)) {
            resultList.add("人工流产" + inducedAbortionNum + "次");
        }

        // 胎停育
        String childStopNum = this.getOptionContentByOptionId("210100700000231", answerM);
        if (StringUtils.isNotEmpty(childStopNum)) {
            archives.setChildStopNum(childStopNum);
        } else {
            archives.setChildStopNum("");
        }

        // 结论--胎停育
        if (StringUtils.isNotEmpty(childStopNum)) {
            resultList.add("胎停育" + childStopNum + "次");
        }

        // 早产
        String pretermNum = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91e73e730029",
                optionsM);
        if (StringUtils.isNotEmpty(pretermNum)) {
            archives.setPretermNum(pretermNum);
        } else {
            archives.setPretermNum("");
        }

        // 结论--早产
        if ("是".equals(pretermNum)) {
            resultList.add("早产史");
        }

        // 中期引产
        String odinopoeiaNum = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91e812f1002a",
                optionsM);
        if (StringUtils.isNotEmpty(odinopoeiaNum)) {
            archives.setOdinopoeiaNum(odinopoeiaNum);
        } else {
            archives.setOdinopoeiaNum("");
        }

        // 结论--中期引产
        if ("是".equals(odinopoeiaNum)) {
            resultList.add("中期引产史");
        }

        // 足月分娩
        String childbirthNum = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a922ae8bf002e",
                optionsM);
        if (StringUtils.isNotEmpty(childbirthNum)) {
            archives.setChildbirthNum(childbirthNum);
        } else {
            archives.setChildbirthNum("");
        }
        // 巨大儿分娩
        String giantBabyNum = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91e8818e002b",
                optionsM);
        if (StringUtils.isNotEmpty(giantBabyNum)) {
            archives.setGiantBabyNum(giantBabyNum);
        } else {
            archives.setGiantBabyNum("");
        }

        // 结论--巨大儿分娩
        if ("是".equals(giantBabyNum)) {
            resultList.add("巨大儿分娩史");
        }

        // 是否有剖宫产
        String planePalaceNum = this.getquestionAnswerString(answerM, "402880f9653c86f901653ca26c9c0004", optionsM);
        if (StringUtils.isNotEmpty(planePalaceNum)) {
            archives.setPlanePalaceNum(planePalaceNum);
        } else {
            archives.setPlanePalaceNum("");
        }

        // 结论--是否有剖宫产
        if ("是".equals(planePalaceNum)) {
            resultList.add("剖宫产分娩史");
        }

        // 高碘或造影剂接触史
        String gaodianNum = this.getquestionAnswerString(answerM, "402880e860e955590160e9642b870000", optionsM);
        if (StringUtils.isNotEmpty(gaodianNum)) {
            archives.setIodineNum(gaodianNum);
        } else {
            archives.setIodineNum("");
        }

        // 结论--高碘或造影剂接触史
        if ("是".equals(gaodianNum)) {
            resultList.add("高碘或造影剂接触史");
        }

        // 畸形
        String malformationNum = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91e8ec52002c",
                optionsM);
        if (StringUtils.isNotEmpty(malformationNum)) {
            archives.setMalformationNum(malformationNum);
        } else {
            archives.setMalformationNum("");
        }

        // 结论--畸形
        if ("是".equals(malformationNum)) {
            resultList.add("畸形分娩史");
        }

        // 既往妊娠并发症
        String pregnancyComplications = this.getquestionAnswerString(answerM, "402880ef5a91a2b1015a91eb0c02002d",
                optionsM);
        if (StringUtils.isNotEmpty(pregnancyComplications)) {
            archives.setPregnancyComplications(pregnancyComplications);
        } else {
            archives.setPregnancyComplications("");
        }

        // 结论--既往妊娠并发症
        if (StringUtils.isNotBlank(pregnancyComplications) && !"无".equals(pregnancyComplications)) {
            resultList.add("既往妊娠并发症:"
                    + pregnancyComplications.replaceAll("、多胎妊娠", "").replaceAll("多胎妊娠、", "").replaceAll("、无", "")
                            .replaceAll("无、", ""));
        }

        // 永久诊断
        String permanentDisease = this.getOptionContentByOptionId("402880b35f2e4f25015f2e58842f0000", answerM);
        if (StringUtils.isNotEmpty(permanentDisease)) {
            archives.setPermanentDisease(permanentDisease);
        } else {
            archives.setPermanentDisease("");
        }

        archives.setPregnancyResult(StringUtils.join(resultList, "；"));

        Customer customer = TransformerUtils.transformerProperties(Customer.class, customerVo);
        // 孕前bmi
        if (customer.getCustHeight() != null && customer.getCustWeight() != null) {
            double weight1 = customer.getCustWeight().doubleValue();
            double height1 = customer.getCustHeight().doubleValue();
            double BMI = FoodsFormulaUtil.getBmi(weight1, height1);
            customer.setCustBmi(new BigDecimal(BMI));
            archives.setBmi(new BigDecimal(BMI));
        }
        customerService.updateCustomer(customer);
        archives.setQuestionAllocId(allocId);
        archives.setDiagnosisId(diagnosisId);

        UserPojo loginUser = this.getLoginUser().getUserPojo();
        if ("assistant".equals(loginUser.getUserType())) {
            // 建档医师---本次接诊医生
            if (StringUtils.isNotBlank(diagnosisVo.getDiagnosisUser())) {
                archives.setUserId(diagnosisVo.getDiagnosisUser());
                archives.setUserName(diagnosisVo.getDiagnosisUserName());
            } else {
                throw new ServiceException(ResultCode.ERROR_80014);
            }

            // List<UserPojo> assistantList = userAssistantService.queryDoctorOrAssistant(
            // this.getLoginUser().getUserPojo().getUserId());
            // if (CollectionUtils.isNotEmpty(assistantList)) {
            // archives.setUserId(assistantList.get(0).getUserId());
            // archives.setUserName(assistantList.get(0).getUserName());
            // } else {
            // throw new ServiceException(ResultCode.ERROR_80014);
            // }

        } else {
            archives.setUserId(loginUser.getUserId());
            archives.setUserName(loginUser.getUserName());
        }

        archivesService.deleteAndSavePregnancyArchives(archives);
        // 根据建档信息更新接诊表
        diagnosisService.completeDiagnosis(diagnosisId);
    }

    /**
     * 
     * 根据体检号查询患者的问卷答案
     * 
     * @author gss
     * @param 问卷分配号
     * @return
     */
    private Map<String, QuestionAnswerPojo> getQuestionAnswerMapByExamCode(String inspectCode) {
        if (StringUtils.isEmpty(inspectCode)) {
            return null;
        }
        List<QuestionAnswerPojo> questionAnswerVos = questionAnswerService.queryQuestionAnswer(inspectCode);
        if (CollectionUtils.isEmpty(questionAnswerVos)) {
            return null;
        }
        Map<String, QuestionAnswerPojo> questionAnswerVoMap = new HashMap<String, QuestionAnswerPojo>();
        for (QuestionAnswerPojo questionAnswerVo : questionAnswerVos) {
            if (questionAnswerVo == null) {
                continue;
            }
            questionAnswerVoMap.put(questionAnswerVo.getProblemOptionId() + "_" + questionAnswerVo.getProblemId(),
                    questionAnswerVo);
        }
        return questionAnswerVoMap;
    }

    /**
     * 根据选项id查询选项内容
     * 
     * @author xdc
     * @param optionId
     * @param map
     * @return
     */
    private String getOptionContentByOptionId(String optionId, Map<String, QuestionAnswerPojo> map) {
        QuestionAnswerPojo answerVo = this.getQuestionAnswerVoByOptionId(optionId, map);
        if (answerVo == null) {
            return null;
        }
        return answerVo.getAnswerContent();
    }

    /**
     * 
     * 根据选项id模糊查询患者问卷答案
     * 
     * @author gss
     * @param problemId
     * @param map
     *            问卷map
     * @return
     */
    private QuestionAnswerPojo getQuestionAnswerVoByOptionId(String optionId, Map<String, QuestionAnswerPojo> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<QuestionAnswerPojo> answerVos = new ArrayList<QuestionAnswerPojo>();
        Iterator<Entry<String, QuestionAnswerPojo>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, QuestionAnswerPojo> entry = (Map.Entry<String, QuestionAnswerPojo>) it.next();
            if (entry.getKey().indexOf(optionId) != -1) {
                answerVos.add(entry.getValue());
            }
        }
        return CollectionUtils.isEmpty(answerVos) ? null : answerVos.get(0);
    }

    /**
     * 查询选项的字符串
     * 
     * @author gss
     * @param questionAnswerVoMap
     *            问题答案
     * @param problemId
     *            问题编号
     * @param questionProblemOptionsVoMap
     *            问题选项
     * @return
     */
    private String getquestionAnswerString(Map<String, QuestionAnswerPojo> questionAnswerVoMap, String problemId,
            Map<String, QuestionProblemOptionsPojo> questionProblemOptionsVoMap) {
        if (questionAnswerVoMap == null || StringUtils.isEmpty(problemId)) {
            return null;
        }
        List<QuestionAnswerPojo> questionAnswerVos = this
                .getQuestionAnswerVoByProblemId(problemId, questionAnswerVoMap);
        StringBuffer stringBuffer = new StringBuffer();
        // 记录其他
        String qt = null;
        for (QuestionAnswerPojo questionAnswerVo : questionAnswerVos) {
            if (questionAnswerVo == null) {
                continue;
            }
            QuestionProblemOptionsPojo questionProblemOptionsVo = questionProblemOptionsVoMap.get(questionAnswerVo
                    .getProblemOptionId());
            if (questionProblemOptionsVo == null) {
                continue;
            }
            if (StringUtils.isEmpty(questionProblemOptionsVo.getOptionContent())) {
                continue;
            }
            if ("其他".equals(questionProblemOptionsVo.getOptionContent())) {
                qt = "其他、";
                continue;
            }
            stringBuffer.append(questionProblemOptionsVo.getOptionContent()).append("、");
        }
        if (stringBuffer.length() <= 0 && StringUtils.isEmpty(qt)) {
            return null;
        }
        if (StringUtils.isNotEmpty(qt)) {
            stringBuffer.append(qt);
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    /**
     * 根据问题id模糊查询患者问卷答案
     * 
     * @author gss
     * @param problemId
     * @param map
     *            问卷map
     * @return
     */
    private List<QuestionAnswerPojo> getQuestionAnswerVoByProblemId(String problemId,
            Map<String, QuestionAnswerPojo> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<QuestionAnswerPojo> answerVos = new ArrayList<QuestionAnswerPojo>();
        Iterator<Entry<String, QuestionAnswerPojo>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, QuestionAnswerPojo> entry = (Map.Entry<String, QuestionAnswerPojo>) it.next();
            if (entry.getKey().indexOf(problemId) != -1) {
                answerVos.add(entry.getValue());
            }
        }
        return answerVos;
    }

    /**
     * 疾病与选项对应关系
     * 
     * @author xdc
     * @return
     */
    private Map<String, String> disease2year() {
        Map<String, String> disease2year = new HashMap<String, String>();
        disease2year.put("210100700000180", "402880ef5a91a2b1015a91d61f63000f");// 心脏病
        disease2year.put("210100700000181", "402880ef5a91a2b1015a91d7a2ef0010");// 高血压
        disease2year.put("210100700000182", "402880ef5a91a2b1015a91d80f9b0011");// 高血脂
        disease2year.put("210100700000183", "402880ef5a91a2b1015a91d87da90012");// 甲状腺疾病
        disease2year.put("210100700000184", "402880ef5a91a2b1015a91d8f35d0013");// 反复外阴阴道假丝酵母念菌病（VVC）（两次及以上）
        disease2year.put("210100700000185", "402880ef5a91a2b1015a91dace1b0014");// 贫血
        disease2year.put("210100700000186", "402880ef5a91a2b1015a91db20cb0015");// 首次检查血红蛋白大于130g/L
        disease2year.put("210100700000187", "402880ef5a91a2b1015a91db83470016");// 多囊卵巢综合征（pcos）
        disease2year.put("210100700000188", "402880ef5a91a2b1015a91dbd1690017");// 肝胆疾病如胆结石胆囊炎/肝炎（丙肝）
        disease2year.put("210100700000189", "402880ef5a91a2b1015a91dc1f6a0018");// 非乙醇性肝硬变
        disease2year.put("210100700000190", "402880ef5a91a2b1015a91dc71a00019");// 口服避孕药导致的胆汁淤积症
        disease2year.put("210100700000191", "402880ef5a91a2b1015a91dcf281001a");// 乙肝表面抗原阳性HBsAg( )/乙肝病毒携带者
        disease2year.put("210100700000192", "402880ef5a91a2b1015a91dd341d001b");// 非乙醇性胰腺炎
        disease2year.put("210100700000193", "402880ef5a91a2b1015a91dd8881001c");// 便秘（功能性）
        disease2year.put("210100700000194", "402880ef5a91a2b1015a91ddd81d001d");// 胃炎
        disease2year.put("210100700000195", "402880ef5a91a2b1015a91de1e4c001e");// 消化道溃疡
        disease2year.put("210100700000196", "402880ef5a91a2b1015a91de6535001f");// 慢性肾病
        disease2year.put("210100700000197", "402880ef5a91a2b1015a91deb1db0020");// 子宫肌瘤
        disease2year.put("210100700000198", "402880ef5a91a2b1015a91deffbe0021");// 骨质疏松/骨质流失
        return disease2year;
    }

}
