
package com.mnt.preg.master.controller.question;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.entity.question.Option;
import com.mnt.preg.master.entity.question.Problem;
import com.mnt.preg.master.form.question.ProblemForm;
import com.mnt.preg.master.mapping.QuestionMapping;
import com.mnt.preg.master.mapping.QuestionPageName;
import com.mnt.preg.master.pojo.question.OptionPojo;
import com.mnt.preg.master.pojo.question.ProblemPojo;
import com.mnt.preg.master.service.question.ProblemService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.mapping.CommonPageName;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 问题库Controller
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
@Controller
public class ProblemController extends BaseController {

    @Resource
    private ProblemService problemService;

    /**
     * 
     * 异步获取体检项目字典信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = QuestionMapping.QUERY_PROBLEM)
    public @ResponseBody
    WebResult<Boolean> queryProblem(Problem condition) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<ProblemPojo> problemList = problemService.queryProblem(condition);
        webResult.setData(problemList);
        return webResult;
    }

    /**
     * 问卷问题信息删除
     * 
     * @param id
     *            主键
     * @return
     * 
     */
    @RequestMapping(value = QuestionMapping.PROBLEM_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeProblem(HttpServletRequest request, @RequestParam String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        if (StringUtils.isEmpty(id)) {
            webResult.setError("参数为空");
        }
        problemService.removeProblem(id);
        webResult.setSuc(true, "成功");
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
    @RequestMapping(value = QuestionMapping.ADD_INIT_PROBLEM)
    public String initAddProblem(HttpServletRequest request, Model model) {
        return QuestionPageName.PROBLEM_INIT_ADD;
    }

    /**
     * 问题记录增加功能
     * 
     * @param problemForm
     * @return
     */
    @RequestMapping(value = QuestionMapping.ADD_PROBLEM)
    public @ResponseBody
    WebResult<ProblemPojo> addProblem(HttpServletRequest request) {
        WebResult<ProblemPojo> webResult = new WebResult<ProblemPojo>();
        String fromParams = request.getParameter("fromParams");
        String optionParams = request.getParameter("optionParams");
        ProblemForm problemForm = (ProblemForm) NetJsonUtils.jsonToObject(fromParams, ProblemForm.class);
        @SuppressWarnings("unchecked")
        List<OptionPojo> options = NetJsonUtils.jsonArrayToList(optionParams, OptionPojo.class);
        ProblemPojo problemVo = TransformerUtils.transformerProperties(ProblemPojo.class, problemForm);
        if (problemVo.getProblemRequired() == null) {
            problemVo.setProblemRequired(0);
        }
        String problemId = problemService.addProblem(problemVo, options);
        ProblemPojo problemPojo = problemService.getProblemById(problemId);
        webResult.setSuc(problemPojo);
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
    @RequestMapping(value = QuestionMapping.UPDATE_INIT_PROBLEM)
    public String problemUpdateInit(String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            model.addAttribute("error_msg", "主键不能为空");
            return CommonPageName.ERROR_PAGE;
        }
        ProblemPojo problemVo = new ProblemPojo();
        problemVo = problemService.getProblemById(id);

        if (problemVo == null) {
            problemVo = new ProblemPojo();
        }
        List<OptionPojo> options = problemService.getOptionsByProblemId(problemVo.getProblemId());
        ProblemForm problemForm = TransformerUtils.transformerProperties(ProblemForm.class, problemVo);
        model.addAttribute("problem", problemForm);
        model.addAttribute("options", options);
        return QuestionPageName.PROBLEM_UPDATE;
    }

    /**
     * 项目修改
     * 
     * @param itemForm
     *            修改表单数据
     * @return
     * 
     */
    @RequestMapping(value = QuestionMapping.UPDATE_PROBLEM)
    public @ResponseBody
    WebResult<ProblemPojo> updateProblem(String fromParams, String optionParams) {
        WebResult<ProblemPojo> webResult = new WebResult<ProblemPojo>();
        ProblemForm problemForm = (ProblemForm) NetJsonUtils.jsonToObject(fromParams, ProblemForm.class);
        @SuppressWarnings("unchecked")
        List<OptionPojo> options = NetJsonUtils.jsonArrayToList(optionParams, OptionPojo.class);
        if (StringUtils.isNotEmpty(problemForm.getProblemId())) {
            ProblemPojo problemVo = TransformerUtils.transformerProperties(ProblemPojo.class, problemForm);
            problemService.updateProblem(problemVo, options);
            ProblemPojo problemPojo = problemService.getProblemById(problemVo.getProblemId());
            webResult.setSuc(problemPojo);
        }
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
    @RequestMapping(value = QuestionMapping.QUERY_OPTION)
    public @ResponseBody
    WebResult<OptionPojo> queryOption(String problemId) {
        WebResult<OptionPojo> webResult = new WebResult<OptionPojo>();
        Option condition = new Option();
        condition.setProblemId(problemId);
        List<OptionPojo> optionListVo = problemService.queryOption(condition);
        webResult.setData(optionListVo);
        return webResult;
    }
}
