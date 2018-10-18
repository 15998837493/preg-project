
package com.mnt.preg.examitem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;

import com.mnt.preg.customer.dao.QuestionAnswerDAO;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.util.lifestyle.LifeStyleUtil;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.question.QuestionDAO;

/**
 * 营养快速调查
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
@Service
public class LifeStyleSurveyServiceImpl extends BaseService implements LifeStyleSurveyService {

    @Resource
    private QuestionAnswerDAO questionAnswerDAO;

    @Resource
    private QuestionDAO questionDAO;

    @Override
    public List<ExamItemPojo> analyseLifestyleServey(@NotBlank String questionAllocId) {
        // 结果表明细
        List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();
        // 获取问卷答案
        List<QuestionAnswerPojo> listInit = questionAnswerDAO.queryQuestionAnswerAndOptionContent(questionAllocId);
        // 根据问题将选项归类
        Map<String, List<QuestionAnswerPojo>> answerProMap = new HashMap<String, List<QuestionAnswerPojo>>();
        // 处理几个带有其他选项的问题，如果选了其他，那么选项的内容应该是对应问题的答案
        for (QuestionAnswerPojo answer : listInit) {
            // 问题id为主键的map
            List<QuestionAnswerPojo> ansList = answerProMap.get(answer.getProblemId());
            if (CollectionUtils.isEmpty(ansList)) {
                ansList = new ArrayList<QuestionAnswerPojo>();
                ansList.add(answer);
                answerProMap.put(answer.getProblemId(), ansList);
                continue;
            }
            ansList.add(answer);
        }
        // *ResultMap表示对某一部分结果的统计，map中有三个键值对正常，异常，建议
        // *建议集合中一开始添加的是建议对应的序号，然后排重以后获取建议内容
        Map<String, List<String>> biteResultMap = new HashMap<String, List<String>>();
        biteResultMap.put("正常", new ArrayList<String>());
        biteResultMap.put("异常", new ArrayList<String>());
        biteResultMap.put("建议", new ArrayList<String>());
        Map<String, List<String>> sportResultMap = new HashMap<String, List<String>>();
        sportResultMap.put("正常", new ArrayList<String>());
        sportResultMap.put("异常", new ArrayList<String>());
        sportResultMap.put("建议", new ArrayList<String>());
        Map<String, List<String>> sleepResultMap = new HashMap<String, List<String>>();
        sleepResultMap.put("正常", new ArrayList<String>());
        sleepResultMap.put("异常", new ArrayList<String>());
        sleepResultMap.put("建议", new ArrayList<String>());
        Map<String, List<String>> mentalityResultMap = new HashMap<String, List<String>>();
        mentalityResultMap.put("正常", new ArrayList<String>());
        mentalityResultMap.put("异常", new ArrayList<String>());
        mentalityResultMap.put("建议", new ArrayList<String>());
        Map<String, List<String>> drinkResultMap = new HashMap<String, List<String>>();
        drinkResultMap.put("正常", new ArrayList<String>());
        drinkResultMap.put("异常", new ArrayList<String>());
        drinkResultMap.put("建议", new ArrayList<String>());
        Map<String, List<String>> environmentResultMap = new HashMap<String, List<String>>();
        environmentResultMap.put("正常", new ArrayList<String>());
        environmentResultMap.put("异常", new ArrayList<String>());
        environmentResultMap.put("建议", new ArrayList<String>());
        for (String problemId : answerProMap.keySet()) {
            List<QuestionAnswerPojo> ansList = answerProMap.get(problemId);
            /**
             * 特殊处理，有几道题的答案不是直接显示选项（一般为多选）
             */
            if ("402880b360e936630160e948f7b5003e".equals(problemId)) {
                // 每餐大约几分饱
                List<String> resultNor = new ArrayList<String>();
                List<String> resultErr = new ArrayList<String>();
                for (QuestionAnswerPojo answer : ansList) {
                    String optionId = answer.getProblemOptionId();
                    if (LifeStyleUtil.biteMap.get(optionId) == 0) {
                        resultNor.add(answer.getOptionContent());
                    } else {
                        resultErr.add(answer.getOptionContent());
                        biteResultMap.get("建议").add(String.valueOf(LifeStyleUtil.biteMap.get(optionId)));
                    }
                }
                if (CollectionUtils.isNotEmpty(resultNor)) {
                    biteResultMap.get("正常").add("每餐大约：" + StringUtils.join(resultNor, "，").split("[：]")[0]);
                }
                if (CollectionUtils.isNotEmpty(resultErr)) {
                    biteResultMap.get("异常").add("每餐大约：" + StringUtils.join(resultErr, "，").split("[：]")[0]);
                }
                continue;
            } else if ("402880b360e936630160e94b1587003f".equals(problemId)) {
                // 烹饪用油
                List<String> resultNor = new ArrayList<String>();
                List<String> resultErr = new ArrayList<String>();
                for (QuestionAnswerPojo answer : ansList) {
                    String optionId = answer.getProblemOptionId();
                    if (LifeStyleUtil.biteMap.get(optionId) == 0) {
                        resultNor.add(answer.getOptionContent());
                    } else {
                        resultErr.add(answer.getOptionContent());
                        biteResultMap.get("建议").add(String.valueOf(LifeStyleUtil.biteMap.get(optionId)));
                    }
                }
                if (CollectionUtils.isNotEmpty(resultNor)) {
                    biteResultMap.get("正常").add("烹饪用油多为：" + StringUtils.join(resultNor, "，"));
                }
                if (CollectionUtils.isNotEmpty(resultErr)) {
                    biteResultMap.get("异常").add("烹饪用油多为：" + StringUtils.join(resultErr, "，"));
                }
                continue;
            } else if ("402880b360e936630160e95005d30041".equals(problemId)) {
                // 烹饪习惯
                List<String> resultNor = new ArrayList<String>();
                List<String> resultErr = new ArrayList<String>();
                for (QuestionAnswerPojo answer : ansList) {
                    String optionId = answer.getProblemOptionId();
                    if (LifeStyleUtil.biteMap.get(optionId) == 0) {
                        resultNor.add(answer.getOptionContent());
                    } else {
                        resultErr.add(answer.getOptionContent());
                        biteResultMap.get("建议").add(String.valueOf(LifeStyleUtil.biteMap.get(optionId)));
                    }
                }
                if (CollectionUtils.isNotEmpty(resultNor)) {
                    biteResultMap.get("正常").add("烹饪习惯：" + StringUtils.join(resultNor, "，"));
                }
                if (CollectionUtils.isNotEmpty(resultErr)) {
                    biteResultMap.get("异常").add("烹饪习惯：" + StringUtils.join(resultErr, "，"));
                }
                continue;
            } else if ("402880b360e936630160e95257340043".equals(problemId)) {
                // 运动锻炼方式
                List<String> resultNor = new ArrayList<String>();
                List<String> resultErr = new ArrayList<String>();
                for (QuestionAnswerPojo answer : ansList) {
                    String optionId = answer.getProblemOptionId();
                    if (LifeStyleUtil.sportMap.get(optionId) == 0) {
                        if ("11000022018011200157".equals(optionId)) {
                            if (StringUtils.isNotEmpty(answer.getAnswerContent())) {
                                resultNor.add(answer.getAnswerContent());
                            }
                        } else {
                            resultNor.add(answer.getOptionContent());
                        }
                    } else {
                        resultErr.add(answer.getOptionContent());
                        sportResultMap.get("建议").add(String.valueOf(LifeStyleUtil.sportMap.get(optionId)));
                    }
                }
                if (CollectionUtils.isNotEmpty(resultNor)) {
                    sportResultMap.get("正常").add("常用运动方式为：" + StringUtils.join(resultNor, "，"));
                }
                if (CollectionUtils.isNotEmpty(resultErr)) {
                    sportResultMap.get("异常").add("常用运动方式为：" + StringUtils.join(resultErr, "，"));
                }
                continue;
            } else if ("402880b360e936630160e966362a004f".equals(problemId)) {
                // 有害物质
                List<String> resultNor = new ArrayList<String>();
                List<String> resultErr = new ArrayList<String>();
                for (QuestionAnswerPojo answer : ansList) {
                    String optionId = answer.getProblemOptionId();
                    if (LifeStyleUtil.environmentMap.get(optionId) == 0) {
                        resultNor.add(answer.getOptionContent());
                    } else {
                        if ("11000022018011200266".equals(optionId)) {
                            if (StringUtils.isNotEmpty(answer.getAnswerContent())) {
                                resultErr.add(answer.getAnswerContent());
                            }
                        } else {
                            resultErr.add(answer.getOptionContent());
                        }
                        environmentResultMap.get("建议").add(String.valueOf(LifeStyleUtil.environmentMap.get(optionId)));
                    }
                }
                if (CollectionUtils.isNotEmpty(resultNor)) {
                    environmentResultMap.get("正常").add("经常会接触到：" + StringUtils.join(resultNor, "，"));
                }
                if (CollectionUtils.isNotEmpty(resultErr)) {
                    environmentResultMap.get("异常").add("经常会接触到：" + StringUtils.join(resultErr, "，"));
                }
                continue;
            } else if ("402880b360e936630160e960d2c2004b".equals(problemId)) {
                // 您每天平均睡眠时间：（不等于卧床时间）
                List<String> resultNor = new ArrayList<String>();
                List<String> resultErr = new ArrayList<String>();
                for (QuestionAnswerPojo answer : ansList) {
                    String optionId = answer.getProblemOptionId();
                    if (LifeStyleUtil.sleepMap.get(optionId) == 0) {
                        resultNor.add(answer.getOptionContent());
                    } else {
                        resultErr.add(answer.getOptionContent());
                        sleepResultMap.get("建议").add(String.valueOf(LifeStyleUtil.sleepMap.get(optionId)));
                    }
                }
                if (CollectionUtils.isNotEmpty(resultNor)) {
                    sleepResultMap.get("正常").add("平均睡眠时间" + StringUtils.join(resultNor, "，"));
                }
                if (CollectionUtils.isNotEmpty(resultErr)) {
                    sleepResultMap.get("异常").add("平均睡眠时间" + StringUtils.join(resultErr, "，"));
                }
                continue;
            } else if ("402880b360e936630160e95b84830048".equals(problemId)) {
                // 近期睡眠如何
                List<String> resultNor = new ArrayList<String>();
                List<String> resultErr = new ArrayList<String>();
                for (QuestionAnswerPojo answer : ansList) {
                    String optionId = answer.getProblemOptionId();
                    if (LifeStyleUtil.sleepMap.get(optionId) == 0) {
                        resultNor.add(answer.getOptionContent());
                    } else {
                        resultErr.add(answer.getOptionContent());
                        sleepResultMap.get("建议").add(String.valueOf(LifeStyleUtil.sleepMap.get(optionId)));
                    }
                }
                if (CollectionUtils.isNotEmpty(resultNor)) {
                    sleepResultMap.get("正常").add("近期睡眠" + StringUtils.join(resultNor, "，"));
                }
                if (CollectionUtils.isNotEmpty(resultErr)) {
                    sleepResultMap.get("异常").add("近期睡眠" + StringUtils.join(resultErr, "，"));
                }
                continue;
            }
            /**
             * 常规处理
             * 注：凡是特殊处理的optionId都是“其他选项”，需要显示填写的内容
             */
            for (QuestionAnswerPojo answer : ansList) {
                String optionId = answer.getProblemOptionId();
                if (LifeStyleUtil.biteMap.keySet().contains(optionId)) {
                    // 处理饮食
                    if (LifeStyleUtil.biteMap.get(optionId) == 0) {
                        biteResultMap.get("正常").add(answer.getOptionContent());
                    } else {
                        biteResultMap.get("异常").add(answer.getOptionContent());
                        biteResultMap.get("建议").add(String.valueOf(LifeStyleUtil.biteMap.get(optionId)));
                    }
                } else if (LifeStyleUtil.sportMap.keySet().contains(optionId)) {
                    // 处理运动
                    if (LifeStyleUtil.sportMap.get(optionId) == 0) {
                        if ("11000022018011200168".equals(optionId)) {
                            if (StringUtils.isNotEmpty(answer.getAnswerContent())) {
                                sportResultMap.get("正常").add(answer.getAnswerContent());
                            }
                        } else {
                            sportResultMap.get("正常").add(answer.getOptionContent());
                        }
                    } else {
                        sportResultMap.get("异常").add(answer.getOptionContent());
                        sportResultMap.get("建议").add(String.valueOf(LifeStyleUtil.sportMap.get(optionId)));
                    }
                } else if (LifeStyleUtil.sleepMap.keySet().contains(optionId)) {
                    // 处理睡眠
                    if (LifeStyleUtil.sleepMap.get(optionId) == 0) {
                        sleepResultMap.get("正常").add(answer.getOptionContent());
                    } else {
                        if ("11000022018011200182".equals(optionId)) {
                            if (StringUtils.isNotEmpty(answer.getAnswerContent())) {
                                sleepResultMap.get("异常").add(answer.getAnswerContent());
                            }
                        } else {
                            sleepResultMap.get("异常").add(answer.getOptionContent());
                        }
                        sleepResultMap.get("建议").add(String.valueOf(LifeStyleUtil.sleepMap.get(optionId)));
                    }
                } else if (LifeStyleUtil.mentalityMap.keySet().contains(optionId)) {
                    // 处理心里
                    if (LifeStyleUtil.mentalityMap.get(optionId) == 0) {
                        mentalityResultMap.get("正常").add(answer.getOptionContent());
                    } else {
                        mentalityResultMap.get("异常").add(answer.getOptionContent());
                        mentalityResultMap.get("建议").add(String.valueOf(LifeStyleUtil.mentalityMap.get(optionId)));
                    }
                } else if (LifeStyleUtil.drinkAndSomkeMap.keySet().contains(optionId)) {
                    // 处理烟酒
                    if (LifeStyleUtil.drinkAndSomkeMap.get(optionId) == 0) {
                        drinkResultMap.get("正常").add(answer.getOptionContent());
                    } else {
                        drinkResultMap.get("异常").add(answer.getOptionContent());
                        drinkResultMap.get("建议").add(String.valueOf(LifeStyleUtil.drinkAndSomkeMap.get(optionId)));
                    }
                } else if (LifeStyleUtil.environmentMap.keySet().contains(optionId)) {
                    // 处理环境
                    if (LifeStyleUtil.environmentMap.get(optionId) == 0) {
                        environmentResultMap.get("正常").add(answer.getOptionContent());
                    } else {
                        if ("11000022018011200267".equals(optionId)) {
                            environmentResultMap.get("异常").add("几乎不晒太阳");
                        } else if ("11000022018011200268".equals(optionId)) {
                            environmentResultMap.get("异常").add("很少晒太阳");
                        } else {
                            environmentResultMap.get("异常").add(answer.getOptionContent());
                        }
                        environmentResultMap.get("建议").add(String.valueOf(LifeStyleUtil.environmentMap.get(optionId)));
                    }
                }
            }
        }

        // 拼接每部分的内容添加到结果集
        detailList.add(getExamDataDetail("life0000001", "饮食正常", "", "cus_result_life_servey",
                StringUtils.join(biteResultMap.get("正常"), "；"), "", "0", ""));
        detailList.add(getExamDataDetail("life0000002", "饮食异常", "", "cus_result_life_servey",
                StringUtils.join(biteResultMap.get("异常"), "；"), StringUtils.join(biteResultMap.get("异常"), "；"),
                "4", ""));
        detailList.add(getExamDataDetail("life0000003", "饮食建议", "", "cus_result_life_servey",
                getSuggestInfo(biteResultMap.get("建议")), "", "0", ""));

        detailList.add(getExamDataDetail("life0000004", "运动正常", "", "cus_result_life_servey",
                StringUtils.join(sportResultMap.get("正常"), "；"), "", "0", ""));
        detailList.add(getExamDataDetail("life0000005", "运动异常", "", "cus_result_life_servey",
                StringUtils.join(sportResultMap.get("异常"), "；"), StringUtils.join(sportResultMap.get("异常"), "；"),
                "4", ""));
        detailList.add(getExamDataDetail("life0000006", "运动建议", "", "cus_result_life_servey",
                getSuggestInfo(sportResultMap.get("建议")), "", "0", ""));

        detailList.add(getExamDataDetail("life0000007", "睡眠正常", "", "cus_result_life_servey",
                StringUtils.join(sleepResultMap.get("正常"), "；"), "", "0", ""));
        detailList.add(getExamDataDetail("life0000008", "睡眠异常", "", "cus_result_life_servey",
                StringUtils.join(sleepResultMap.get("异常"), "；"), StringUtils.join(sleepResultMap.get("异常"), "；"),
                "4", ""));
        detailList.add(getExamDataDetail("life0000009", "睡眠建议", "", "cus_result_life_servey",
                getSuggestInfo(sleepResultMap.get("建议")), "", "0", ""));

        detailList.add(getExamDataDetail("life0000010", "心里正常", "", "cus_result_life_servey",
                StringUtils.join(mentalityResultMap.get("正常"), "；"), "", "0", ""));
        detailList.add(getExamDataDetail("life0000011", "心里异常", "", "cus_result_life_servey",
                StringUtils.join(mentalityResultMap.get("异常"), "；"),
                StringUtils.join(mentalityResultMap.get("异常"), "；"),
                "4", ""));
        detailList.add(getExamDataDetail("life0000012", "心里建议", "", "cus_result_life_servey",
                getSuggestInfo(mentalityResultMap.get("建议")), "", "0", ""));

        detailList.add(getExamDataDetail("life0000013", "烟酒正常", "", "cus_result_life_servey",
                StringUtils.join(drinkResultMap.get("正常"), "；"), "", "0", ""));
        detailList.add(getExamDataDetail("life0000014", "烟酒异常", "", "cus_result_life_servey",
                StringUtils.join(drinkResultMap.get("异常"), "；"), StringUtils.join(drinkResultMap.get("异常"), "；"),
                "4", ""));
        detailList.add(getExamDataDetail("life0000015", "烟酒建议", "", "cus_result_life_servey",
                getSuggestInfo(drinkResultMap.get("建议")), "", "0", ""));

        detailList.add(getExamDataDetail("life0000016", "环境正常", "", "cus_result_life_servey",
                StringUtils.join(environmentResultMap.get("正常"), "；"), "", "0", ""));
        detailList.add(getExamDataDetail("life0000017", "环境异常", "", "cus_result_life_servey",
                StringUtils.join(environmentResultMap.get("异常"), "；"),
                StringUtils.join(environmentResultMap.get("异常"), "；"), "4", ""));
        detailList.add(getExamDataDetail("life0000018", "环境建议", "", "cus_result_life_servey",
                getSuggestInfo(environmentResultMap.get("建议")), "", "0", ""));

        return detailList;
    }

    /**
     * 分配结果表明细
     * 
     * @author dhs
     * @param
     * @return
     */
    private ExamItemPojo getExamDataDetail(String itemCode, String itemName, String itemRefValue,
            String itemType, String itemString, String itemResult, String itemCompare, String itemUnit) {
        ExamItemPojo detail = new ExamItemPojo();
        detail.setItemCode(itemCode);
        detail.setItemName(itemName);
        detail.setItemRefValue(itemRefValue);
        detail.setItemType(itemType);
        detail.setItemString(itemString);
        detail.setItemResult(itemResult);
        detail.setItemCompare(itemCompare);
        detail.setItemUnit(itemUnit);
        return detail;
    }

    /**
     * 获取建议拼接的字符串
     * 
     * @author xdc
     * @param list
     * @return
     */
    public String getSuggestInfo(List<String> list) {
        String result = "";
        // 将内容去重
        if (CollectionUtils.isNotEmpty(list)) {
            HashSet<String> h = new HashSet<String>(list);
            list.clear();
            list.addAll(h);
            List<String> sugList = new ArrayList<String>();
            for (String str : list) {
                sugList.add(LifeStyleUtil.suggestMap.get(Integer.valueOf(str)));
            }
            return StringUtils.join(sugList, "");
        }

        return result;
    }
}
