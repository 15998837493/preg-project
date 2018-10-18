
package com.mnt.preg.platform.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 诊疗服务测试类
 * 
 * @author dhs
 * @version 1.3.0
 * 
 */
public class PregDiagnosisDAOTest extends BaseJunit {

    /**
     * 根据接诊id设置体重为空
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    @Test
    public void updateDiagnosisWeight() {
        diagnosisDAO.updateDiagnosisWeight("402880b35fb7fadb015fb81416a90000");
    }

    /**
     * 根据接诊编码获取接诊信息
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    @Test
    public void getDiagnosis() {
        PregDiagnosisPojo pregDiagnosis = diagnosisDAO.getDiagnosis("402880b35f716486015f71a26ad80079");
        assertNotNull(pregDiagnosis);
    }

    /**
     * 条件分页检索
     * 
     * @author dhs
     * @param DiagnosisCondition
     * @return
     */
    @Test
    public void queryDiagnosis() {
        DiagnosisCondition condition = new DiagnosisCondition();
        condition.setDiagnosisId("402880b35f716486015f71a26ad80079");
        List<PregDiagnosisPojo> list = diagnosisDAO.queryDiagnosis(condition);
        assertEquals(list.size(), 1);
    }

    /**
     * 取消预约
     * 
     * @author dhs
     * @param bookingId
     */
    @Test
    public void deleteBooking() {
        diagnosisDAO.deleteBooking("402880f45fc7762d015fc776ed720000");
    }
}
