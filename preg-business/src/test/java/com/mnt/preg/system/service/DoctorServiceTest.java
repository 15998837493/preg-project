
package com.mnt.preg.system.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.system.entity.Doctor;
import com.mnt.preg.system.pojo.DoctorPojo;

/**
 * 医师排班复诊预约测试类
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-21 zcq 初版
 */
public class DoctorServiceTest extends BaseJunit {

    /**
     * 条件检索一周课程配置基础数据
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @Test
    public void queryDoctorByCondition() {
        Doctor condition = new Doctor();
        condition.setUserId("10000");
        List<DoctorPojo> user = doctorService.queryDoctorByCondition(condition);
        boolean flag = true;
        if (user.size() > 1) {
            flag = false;
        }
        assertTrue(flag);
    }

    /**
     * 
     * 查询预约人数
     * 
     * @author dhs
     * @param id
     */
    @Test
    public void queryBooks() {
        Date d;
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        DiagnosisCondition diagnosisCondition = new DiagnosisCondition();
        try {
            d = sim.parse("2017-10-10");
            diagnosisCondition.setDiagnosisDate(d);
            assertEquals(doctorService.queryBooks(diagnosisCondition).size(), 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
