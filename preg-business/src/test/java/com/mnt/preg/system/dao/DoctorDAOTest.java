package com.mnt.preg.system.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.system.entity.Doctor;
import com.mnt.preg.system.pojo.DoctorPojo;

/**
 * 医师出诊排班表测试类
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-1-8 zy 初版
 */
public class DoctorDAOTest extends BaseJunit {
    
    /**
     * 条件查询医师出诊排班表信息
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @Test
    public void queryDoctorByCondition() {
        Doctor condition = new Doctor();
        condition.setUserId("10000");
        List<DoctorPojo> user = doctorDAO.queryDoctorByCondition(condition);
        boolean flag = true;
        if (user.size() > 1) {
            flag = false;
        }
        assertTrue(flag);
    }
}
