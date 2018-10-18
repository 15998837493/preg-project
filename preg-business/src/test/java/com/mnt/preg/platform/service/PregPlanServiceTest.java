
package com.mnt.preg.platform.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 干预方案测试类
 * 
 * @author dhs
 * @version 1.3.0
 * 
 */
public class PregPlanServiceTest extends BaseJunit {

    /**
     * 妊娠日记pdf报告所需参数
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    @Test
    public void analysisDiary() {
        PregDiagnosisPojo pregDiagnosis = pregPlanService.analysisDiary("402880b35f716486015f71a26ad80079");
        assertNotNull(pregDiagnosis.getDiagnosisRiseWeek());
    }

    /**
     * 检验pdf报告是否有内容
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    @Test
    public void validPdf() {
        Map<String, Boolean> map = pregPlanService.validPdf("402880f45fe7d05c015fe7d8b5490000",
                "7eeb904a-0492-4198-ab05-2c87568a22fb");
        assertEquals(map.size(), 11);
    }
}
