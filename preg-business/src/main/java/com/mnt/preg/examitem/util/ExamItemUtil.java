
package com.mnt.preg.examitem.util;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.examitem.condition.ExamItemCondition;
import com.mnt.preg.examitem.pojo.ExamItemPojo;

/**
 * 指标公共接口
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-1 zcq 初版
 */
@Validated
public interface ExamItemUtil {

    /**
     * 条件检索检测项目指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ExamItemPojo> queryExamItem(ExamItemCondition condition);

    /**
     * 通过检测项目返回码查询分析结果
     * 
     * @author zcq
     * @param tableName
     * @param resultCode
     * @return
     */
    Map<String, ExamItemPojo> queryExamItemMapByResultCode(String tableName, String resultCode);

    /**
     * 通过ExamId查询分析结果
     * 
     * @author zcq
     * @param tableName
     * @param examId
     * @return
     */
    Map<String, ExamItemPojo> queryExamItemMapByExamId(String tableName, String examId);

    /**
     * 查询异常指标
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ExamItemPojo> queryExamItemResult(ExamItemCondition condition);

    /**
     * 保存单表指标数据
     * 
     * @author zcq
     * @param tableName
     * @param examId
     * @param detailList
     */
    void addExamItems(String tableName, String examId, List<ExamItemPojo> detailList);

    /**
     * 保存检测指标
     * 
     * @author zcq
     * @param tableName
     * @param examDataDetail
     * @return
     */
    String addExamItem(String tableName, ExamItemPojo examDataDetail);

    /**
     * 删除指标数据
     * 
     * @author zcq
     * @param tableName
     * @param examCode
     */
    void deleteExamItem(String tableName, String examCode);

    /**
     * 删除指标数据
     * 
     * @author zcq
     * @param tableName
     * @param examCode
     */
    void deleteExamItemByExamIdAndItemCode(String tableName, String examCode, String itemCode);

}
