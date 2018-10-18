
package com.mnt.preg.examitem.util;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.examitem.condition.ExamResultRecordCondition;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;

/**
 * 检测项目记录信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-6-2 zcq 初版
 */
@Validated
public interface ExamResultRecordUtil {

    /**
     * 根据检索条件查询检查项目结果记录
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<ExamResultRecordPojo> queryExamResultRecord(ExamResultRecordCondition condition);

    /**
     * 根据体检号查询检查项目
     * 
     * @author zcq
     * @param phyexamCode
     * @return
     */
    List<ExamResultRecordPojo> queryExamResultRecordByPhyexamCode(@NotBlank String phyexamCode);

    /**
     * 根据身份证号查询检查项目
     * 
     * @author zcq
     * @param custIcard
     * @param examCategory
     * @return
     */
    List<ExamResultRecordPojo> queryExamResultRecordByCustIcard(@NotBlank String custIcard,
            @NotBlank String examCategory);

    /**
     * 通过主键获取检测记录
     *
     * @author zcq
     * @param examId
     * @return
     */
    ExamResultRecordPojo getExamResultRecordByExamId(String examId);

    /**
     * 通过返回码和项目编码获取检测记录信息
     * 
     * @author zcq
     * @param examCode
     * @param examCategory
     * @return
     */
    ExamResultRecordPojo getExamResultRecordByExamCodeAndExamCategory(String examCode, String examCategory);

    /**
     * 保存检查项目结果记录
     * 
     * @author zcq
     * @param examResultRecord
     * @return
     */
    String addExamResultRecord(ExamResultRecord examResultRecord);

    /**
     * 保存检查项目结果记录（专用）
     * 
     * @author zcq
     * @param examResultRecord
     */
    String addExamResultRecordForExam(ExamResultRecord examResultRecord, String examTable);

    /**
     * 修改检查项目结果记录（专用）
     * 
     * @author zcq
     * @param examId
     * @return
     */
    void updateExamResultRecordForExam(ExamResultRecord examResultRecord, String examTable);

    /**
     * 修改结果记录
     * 
     * @author zcq
     * @param examResultRecord
     * @param examCategory
     */
    void updateExamResultRecord(ExamResultRecord examResultRecord);

    /**
     * 清空评价项目结论
     * 
     * @author zcq
     * @param examCode
     * @param examCategory
     */
    void removeExamResult(String examCode, String examCategory);

    /**
     * 删除检测记录
     * 
     * @author zcq
     * @param examCode
     * @param examCategory
     */
    Integer deleteExamResultRecordByExamCodeAndExamCategory(String examCode, String examCategory);

    /**
     * 校验检查项目使用次数
     * 
     * @author zcq
     * @param examCategory
     * @return
     */
    Integer getInspectItemUseCount(String examCategory);

}
