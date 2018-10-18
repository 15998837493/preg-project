
package com.mnt.preg.master.dao.question;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.mnt.preg.BaseJunit;

/**
 * 问卷DAO测试类
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 gss 初版
 */
public class QuestionDAOTest extends BaseJunit {

    /**
     * 查询患者当次接诊的初诊建档问卷分配号
     * 
     * @param custId
     *            客户编号
     * @param diagnosisDate
     *            接诊日期
     * @return
     */
    @Test
    public void getOnceAllocationID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = "2017-11-15";
        try {
            Date date = sdf.parse(strDate);
            assertEquals(questionDAO.getOnceAllocationID("402880b35f750210015f7530e46b006c", date),
                    "402880b35f750210015f7530f3020073");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
