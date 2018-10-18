
package com.mnt.preg.examitem.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.health.core.utils.HQLSymbol;
import com.mnt.preg.examitem.condition.ExamItemCondition;
import com.mnt.preg.examitem.condition.ExamResultRecordCondition;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.dao.ExamItemDAO;
import com.mnt.preg.examitem.dao.ExamResultRecordDAO;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.master.dao.preg.SymptomsDAO;

/**
 * 体检项目结果记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-25 zcq 初版
 */
@Service
public class ExamResultRecordUtilImpl extends BaseService implements ExamResultRecordUtil {

    @Resource
    private ExamItemDAO examItemDAO;

    @Resource
    private ExamResultRecordDAO examResultRecordDAO;

    @Resource
    private SymptomsDAO symptomsDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ExamResultRecordPojo> queryExamResultRecord(ExamResultRecordCondition condition) {
        return examResultRecordDAO.queryExamResultRecord(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResultRecordPojo> queryExamResultRecordByPhyexamCode(String phyexamCode) {
        return examResultRecordDAO.queryExamResultRecordByPhyexamCode(phyexamCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResultRecordPojo> queryExamResultRecordByCustIcard(String custIcard, String examCategory) {
        return examResultRecordDAO.queryExamResultRecordByCustIcard(custIcard, examCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResultRecordPojo getExamResultRecordByExamId(String examId) {
        return examResultRecordDAO.getTransformerBean(examId, ExamResultRecord.class, ExamResultRecordPojo.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResultRecordPojo getExamResultRecordByExamCodeAndExamCategory(String examCode, String examCategory) {
        if (StringUtils.isEmpty(examCode) || StringUtils.isEmpty(examCategory)) {
            return null;
        }
        ExamResultRecordCondition condition = new ExamResultRecordCondition();
        condition.setExamCode(examCode);
        condition.setExamCategory(examCategory);
        List<ExamResultRecordPojo> list = examResultRecordDAO.queryExamResultRecord(condition);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addExamResultRecord(ExamResultRecord examResultRecord) {
        if (examResultRecord.getExamDate() == null) {
            examResultRecord.setExamDate(new Date());
        }
        return (String) examResultRecordDAO.save(examResultRecord);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addExamResultRecordForExam(ExamResultRecord examResultRecord, String tableName) {
        ExamItemCondition examItencondition = new ExamItemCondition();
        examItencondition.setTableName(tableName);
        examItencondition.setItemCompare("4");
        examItencondition.setResultCode(examResultRecord.getExamCode());
        List<ExamItemPojo> itemResultList = examItemDAO.queryExamItemResultByCondition(examItencondition);
        if (CollectionUtils.isNotEmpty(itemResultList)) {
            List<String> codeList = new ArrayList<String>();
            List<String> resultList = new ArrayList<String>();
            for (ExamItemPojo detailVo : itemResultList) {
                codeList.add(detailVo.getItemCode());
                resultList.add(detailVo.getItemResult());
            }
            examResultRecord.setDiagnosisResultCodes(StringUtils.join(codeList, ","));// 根据异常指标查询诊断分析编码
            examResultRecord.setDiagnosisResultNames(StringUtils.join(resultList, "、"));// 根据异常指标查询诊断分析结果
        }
        return (String) examResultRecordDAO.save(examResultRecord);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateExamResultRecordForExam(ExamResultRecord examResultRecord, String examTable) {
        if (ExamItemConstant.EXAM_ITEM_TABLE.B00005.equals(examTable)) {
            List<String> diagnosisResultNames = new ArrayList<String>();// 结论
            ExamItemCondition condition = new ExamItemCondition();
            condition.setTableName(ExamItemConstant.EXAM_ITEM_TABLE.B00005);
            condition.setExamId(examResultRecord.getExamId());
            List<ExamItemPojo> dietaryReviews = examItemDAO.queryExamItem(condition);
            if (dietaryReviews.size() > 0) {
                for (ExamItemPojo itemPojo : dietaryReviews) {
                    // 截取itemCode最后两位并判断是不是数字类型字符串
                    String itemCode = itemPojo.getItemCode();
                    String compare = itemCode.substring(itemCode.length() - 2, itemCode.length());
                    // 判断是否为数字的正则
                    Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
                    Matcher isNum = pattern.matcher(compare);
                    // 判断是否位数字，数字是否大于12，结果是否为空，是否为异常。
                    if (isNum.matches() && Integer.parseInt(compare) > 12
                            && StringUtils.isNotEmpty(itemPojo.getItemString())
                            && "4".equals(itemPojo.getItemCompare())) {
                        diagnosisResultNames.add(itemPojo.getItemName() + itemPojo.getItemString()
                                + itemPojo.getItemResult());
                    }
                }
                examResultRecord.setDiagnosisResultNames(StringUtils.join(diagnosisResultNames, "、"));// 根据异常指标查询诊断分析结果
            }
        } else if (ExamItemConstant.EXAM_ITEM_TABLE.B00008.equals(examTable)) {
            ExamItemCondition examItencondition = new ExamItemCondition();
            examItencondition.setTableName(examTable);
            examItencondition.setItemCompare("4");
            examItencondition.setResultCode(examResultRecord.getExamCode());
            List<ExamItemPojo> itemResultList = examItemDAO.queryExamItemResultByCondition(examItencondition);
            if (CollectionUtils.isNotEmpty(itemResultList)) {
                List<String> codeList = new ArrayList<String>();
                List<String> resultList = new ArrayList<String>();
                for (ExamItemPojo detailVo : itemResultList) {
                    codeList.add(detailVo.getItemCode());
                    resultList.add(detailVo.getItemName() + ":" + detailVo.getItemString() + detailVo.getItemResult());
                }
                examResultRecord.setDiagnosisResultCodes(StringUtils.join(codeList, ","));// 根据异常指标查询诊断分析编码
                examResultRecord.setDiagnosisResultNames(StringUtils.join(resultList, "、"));// 根据异常指标查询诊断分析结果
            }
        } else if (ExamItemConstant.EXAM_ITEM_TABLE.B00003.equals(examTable)) {
            ExamItemCondition examItencondition = new ExamItemCondition();
            examItencondition.setTableName(examTable);
            examItencondition.setItemCompare("4");
            examItencondition.setResultCode(examResultRecord.getExamCode());
            List<ExamItemPojo> itemResultList = examItemDAO.queryExamItemResultByCondition(examItencondition);
            if (CollectionUtils.isNotEmpty(itemResultList)) {
                List<String> codeList = new ArrayList<String>();
                List<String> resultList = new ArrayList<String>();
                for (ExamItemPojo detailVo : itemResultList) {
                    // 细胞外水分比率不需要显示数值
                    if ("BODY00017".equals(detailVo.getItemCode())) {
                        codeList.add(detailVo.getItemCode());
                        resultList.add(detailVo.getItemResult());
                    }
                    if ("BODY00046".equals(detailVo.getItemCode())) {
                        codeList.add(detailVo.getItemCode());
                        resultList.add(detailVo.getItemResult());
                    } else {
                        codeList.add(detailVo.getItemCode());
                        resultList.add(detailVo.getItemResult() + detailVo.getItemString());
                    }
                }
                examResultRecord.setDiagnosisResultCodes(StringUtils.join(codeList, ","));// 根据异常指标查询诊断分析编码
                examResultRecord.setDiagnosisResultNames(StringUtils.join(resultList, "、"));// 根据异常指标查询诊断分析结果
            }
        } else {
            ExamItemCondition examItencondition = new ExamItemCondition();
            examItencondition.setTableName(examTable);
            examItencondition.setItemCompare("4");
            examItencondition.setResultCode(examResultRecord.getExamCode());
            List<ExamItemPojo> itemResultList = examItemDAO.queryExamItemResultByCondition(examItencondition);
            if (CollectionUtils.isNotEmpty(itemResultList)) {
                List<String> codeList = new ArrayList<String>();
                List<String> resultList = new ArrayList<String>();
                for (ExamItemPojo detailVo : itemResultList) {
                    codeList.add(detailVo.getItemCode());
                    resultList.add(detailVo.getItemResult());
                }
                examResultRecord.setDiagnosisResultCodes(StringUtils.join(codeList, ","));// 根据异常指标查询诊断分析编码
                examResultRecord.setDiagnosisResultNames(StringUtils.join(resultList, "、"));// 根据异常指标查询诊断分析结果
            }
        }
        // 修改结论
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        conditionParams.add(new HQLConditionParam("examId", HQLSymbol.EQ.toString(), examResultRecord.getExamId()));
        examItemDAO.updateHQL(examResultRecord, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void updateExamResultRecord(ExamResultRecord examResultRecord) {
        List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
        // 主键
        String examId = examResultRecord.getExamId();
        if (StringUtils.isNotBlank(examId)) {
            conditionParams.add(new HQLConditionParam("examId", HQLSymbol.EQ.toString(), examId));
        }
        // 结果码
        String examCode = examResultRecord.getExamCode();
        if (StringUtils.isNotBlank(examCode)) {
            conditionParams.add(new HQLConditionParam("examCode", HQLSymbol.EQ.toString(), examCode));
        }
        examItemDAO.updateHQL(examResultRecord, conditionParams);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeExamResult(String examCode, String examCategory) {
        examResultRecordDAO.removeExamResult(examCode, examCategory);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public Integer deleteExamResultRecordByExamCodeAndExamCategory(String examCode, String examCategory) {
        return examResultRecordDAO.deleteExamResultRecordByExamCodeAndExamCategory(examCode, examCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getInspectItemUseCount(String examCategory) {
        return examResultRecordDAO.getInspectItemUseCount(examCategory);
    }

}
