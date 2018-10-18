
package com.mnt.preg.examitem.service;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.mnt.preg.examitem.pojo.NutritiousReportPojo;

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
@Validated
public interface NutritiousSurveyService {

    /**
     * 营养快速调查分析
     * 
     * @author dhs
     * @param questionAllocId
     * @return
     */
    NutritiousReportPojo analyseNutritious(@NotBlank String questionAllocId);
}
