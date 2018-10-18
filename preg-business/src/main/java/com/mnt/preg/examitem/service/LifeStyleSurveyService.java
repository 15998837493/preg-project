
package com.mnt.preg.examitem.service;

import java.util.List;

import com.mnt.preg.examitem.pojo.ExamItemPojo;

/**
 * 
 * 快速营养调查问卷
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
public interface LifeStyleSurveyService {

    /**
     * 营养快速调查分析
     * 
     * @author dhs
     * @param questionAllocId
     * @return
     */
    List<ExamItemPojo> analyseLifestyleServey(String questionAllocId);
}
