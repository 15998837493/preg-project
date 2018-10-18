
package com.mnt.preg.examitem.service;

import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.GuideListReportPojo;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;

/**
 * 
 * 引导单
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
@Validated
public interface GuideListService {

    /**
     * 引导单
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    GuideListReportPojo analyseGuideList(String diagnosisId, String allocId, PregDiagnosisItemsVo item,
            Map<String, ExamItemPojo> map,
            NutritiousReportPojo nutritiousReportPojo);
}
