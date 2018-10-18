
package com.mnt.preg.examitem.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;

/**
 * 快速营养调查Service测试类
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-11-09 dhs 初版
 */
public class NutritiousSurveyServiceTest extends BaseJunit {

    /**
     * 营养调查数据分析
     * 
     * @author dhs
     * @return
     */
    @Test
    public void analyseNutritious() {
        NutritiousReportPojo pojo = nutritiousSurveyService.analyseNutritious("402880815c33c488015c33ca5f030006");
        assertNotNull(pojo);
    }
}
