
package com.mnt.preg.platform.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.entity.PregDiagnosis;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 诊疗服务测试类
 * 
 * @author dhs
 * @version 1.3.0
 * 
 */
public class PregDiagnosisServiceTest extends BaseJunit {

    /**
     * 获取接诊信息
     * 
     * @author dhs
     * @param diagnosisId
     * @return
     */
    @Test
    public void getDiagnosis() {
        PregDiagnosisPojo pregDiagnosis = diagnosisService.getDiagnosis("402880b35f716486015f71a26ad80079");
        assertNotNull(pregDiagnosis);
    }

    /**
     * 条件分页检索
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @Test
    public void queryDiagnosis() {
        DiagnosisCondition condition = new DiagnosisCondition();
        condition.setDiagnosisId("402880b35f716486015f71a26ad80079");
        List<PregDiagnosisPojo> list = diagnosisService.queryDiagnosis(condition);
        assertEquals(list.size(), 1);
    }

    /**
     * 修改体重为空
     * 
     * @author dhs
     * @param diagnosisId
     */
    @Test
    public void updateDiagnosisWeight() {
        diagnosisService.updateDiagnosisWeight("402880b35fb7fadb015fb81416a90000");
    }

    /**
     * 修改诊疗登记
     * 
     * @author dhs
     * @param userDiagnosis
     */
    @Test
    public void updateDiagnosis() {
        PregDiagnosis pre = new PregDiagnosis();
        BigDecimal bd = new BigDecimal("188");
        pre.setDiagnosisCustWeight(bd);
        pre.setDiagnosisId("402880b35fb9662f015fb96823fb0007");
        diagnosisService.updateDiagnosis(pre);
    }

    /**
     * 添加诊疗登记（测试类无法获取当前登陆者信息）
     * 
     * @author dhs
     * @param userDiagnosis
     * @return
     */
    @Ignore
    @Test
    public void addDiagnosis() {
        PregDiagnosis pre = new PregDiagnosis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = "2017-01-17";
        try {
            Date date = sdf.parse(strDate);
            pre.setDiagnosisDate(date);
            pre.setDiagnosisId("2142dsg12df");
            pre.setDiagnosisCustomer("402880f45fb2d9ed015fb2e51ce80000");
            pre.setDiagnosisUser("1000");
            pre.setDiagnosisStatus(2);
            diagnosisService.addDiagnosis(pre);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询接诊--检测项目
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @Test
    public void getDiseaseInspectResult() {
        assertEquals(diagnosisService.getDiseaseInspectResult("402880f45fc7762d015fc776ed720000").size(), 0);
    }

    /**
     * 取消预约
     * 
     * @author dhs
     * @param bookingId
     */
    @Test
    public void deleteBooking() {
        diagnosisService.deleteBooking("402880f45fc7762d015fc776ed720000");
    }
}
