/*
 * QuestionMapping.java	 1.0   2016-3-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.mapping;

import com.mnt.preg.web.anoton.FrontCache;

/**
 * 
 * 问卷模块
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-8 mnt_zhangjing 初版
 */
@FrontCache(space = "Question")
public class QuestionMapping {

    /** 分配问卷 */
    public static final String ALLOCATION_QUESTION = "/page/question/operate/allocation_question.action";

    /** 问卷调查初始化 */
    public static final String INIT_QUESTION = "/page/question/operate/init_question.action";

    /** 保存问卷调查答案 */
    public static final String SAVE_QUESTION = "/page/question/operate/save_question.action";

    /** 保存问卷调查答案 */
    public static final String SAVE_QUESTION_ARCHIVE = "/page/question/operate/save_question_archive.action";

    /** 异步检索问题库 */
    public static final String QUERY_PROBLEM = "/page/master/question/query_problem.action";

    /** 删除问题库 */
    public static final String PROBLEM_REMOVE = "/page/master/question/problem_remove.action";

    /** 增加问题库 初始化页面 */
    public static final String ADD_INIT_PROBLEM = "/page/master/question/add_init_problem.action";

    /** 增加问题库 页面 */
    public static final String ADD_PROBLEM = "/page/master/question/add_problem.action";

    /** 修改问题库 初始化页面 */
    public static final String UPDATE_INIT_PROBLEM = "/page/master/question/update_init_problem.action";

    /** 修改问题库 页面 */
    public static final String UPDATE_PROBLEM = "/page/master/question/update_problem.action";

    /** 异步检索问题选项 */
    public static final String QUERY_OPTION = "/page/master/question/query_option.action";

    /** 增加选项 */
    public static final String ADD_OPTION = "/page/master/question/add_option.action";

    /** 异步检索问卷库 */
    public static final String QUERY_QUESTION = "/page/master/question/query_question.action";

    /** 删除问卷库 */
    public static final String QUESTION_REMOVE = "/page/master/question/question_remove.action";

    /** 增加问卷库 初始化页面 */
    public static final String ADD_INIT_QUESTION = "/page/master/question/add_init_question.action";

    /** 增加问卷库 页面 */
    public static final String ADD_QUESTION = "/page/master/question/add_question.action";

    /** 修改问卷库 初始化页面 */
    public static final String UPDATE_INIT_QUESTION = "/page/master/question/update_init_question.action";

    /** 修改问卷库 页面 */
    public static final String UPDATE_QUESTION = "/page/master/question/update_question.action";

    /** 增加问卷库配置 初始化页面 */
    public static final String ADD_INIT_QUESTION_CONFIG = "/page/master/question/add_init_question_config.action";

    /** 根据问题ID查询选项 */
    public static final String QUERY_OPTION_PROBLEMID = "/page/master/question/query_option_problemid.action";

    /** 异步检索问题库 */
    public static final String QUERY_QUESTION_PROBLEM = "/page/master/question/query_question_problem.action";

    /** 异步检索问题选项 */
    public static final String QUERY_QUESTION_PROBLEM_OPTION = "/page/master/question/query_question_problem_option.action";

    /** 增加问题库 页面 */
    // TODO:gss 代码整理时需要校验是否有用
    public static final String ADD_QUESTION_PROBLEM = "/page/master/question/add_question_problem.action";

    /** 问题上下移动 */
    public static final String MOVE_QUESTION_PROBLEM = "/page/master/question/move_question_problem.action";

    /** 删除问卷库 */
    public static final String QUESTION_PROBLEM_REMOVE = "/page/master/question/question_problem_remove.action";

    /** 删除问卷库选项 */
    public static final String REMOVE_OPTION = "/page/master/question/remove_option.action";

    /** 选项上下移动 */
    public static final String MOVE_PROBLEM_OPTION = "/page/master/question/move_problem_option.action";

    /** 异步检索问题选项 */
    public static final String QUERY_QUESTION_OPTION = "/page/master/question/query_question_option.action";

    /** 增加问题选项 */
    public static final String ADD_QUESTION_OPTION = "/page/master/question/add_question_option.action";

    /** 删除问卷选项 */
    public static final String REMOVE_QUESTION_OPTION = "/page/master/question/remove_question_option.action";

    /** 选项上下移动 */
    public static final String MOVE_QUESTION_PROBLEM_OPTION = "/page/master/question/move_question_problem_option.action";

    /** 查询问题list */
    public static final String QUERY_PROBLEM_LIST = "/page/master/question/query_problem_list.action";

    /** 问题选项list */
    public static final String QUERY_PROBLEM_OPTION_LIST = "/page/master/question/query_problem_option_list.action";

    /** 保存父节点 */
    public static final String SAVE_PARENT_NODE = "/page/master/question/save_parent_node.action";

    /** 异步检索问题库 */
    public static final String QUERY_PROBLEM_MODEL = "/page/master/question/query_problem_model.action";

    /** 添加问题库中的问题到问卷中 */
    public static final String ADD_LIBPROBLEM_QUESTION = "/page/master/question/add_libproblem_question.action";

    /** 增加问卷问题页面 */
    public static final String ADD_QUESTIONS_PROBLEM = "/page/master/question/add_questions_problem.action";

    /** 增加问题库 初始化页面 */
    public static final String ADD_INIT_QUESTION_PROBLEM = "/page/master/question/add_init_question_problem.action";

    /** 修改问卷问题 初始化页面 */
    public static final String UPDATE_INIT_QUESTION_PROBLEM = "/page/master/question/update_init_question_problem.action";

    /** 修改问题库 页面 */
    public static final String UPDATE_QUESTION_PROBLEM = "/page/master/question/update_question_problem.action";

    /** 修改问题库 页面 */
    public static final String QUERY_NEWLY_DIAGNOSED_PDF = "/page/master/question/query_newly_diagnosed_pdf.action";

    /** 保存生活方式问卷调查答案 */
    public static final String SAVE_LIFE_STYLE_QUESTION = "/page/question/operate/save_lifeStyle_question.action";

    /** 生活方式问卷报告 */
    public static final String LIFE_STYLE_REPOER = "/page/question/operate/save_lifeStyle_report.action";

    /** 保存膳食频率调查答案 */
    public static final String SAVE_DIETARY_FREQUENCY_QUESTION = "/page/question/operate/save_dietary_frequency_question.action";

    /** 膳食频率调查报告 */
    public static final String DIETARY_FREQUENCY_REPOER = "/page/question/operate/dietary_frequency__report.action";

    /** 生活方式报告 */
    public static final String LIFE_STYLE_SERVEY_REPORT = "/page/question/operate/life_style_servey_report.action";

    /** 快速营养调查问卷 */
    public static final String NUTRITIOUS_SURVEY_REPOER = "/page/question/operate/nutritious_survey__report.action";
}
