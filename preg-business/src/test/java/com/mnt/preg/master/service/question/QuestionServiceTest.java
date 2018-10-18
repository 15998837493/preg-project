
package com.mnt.preg.master.service.question;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.entity.QuesAllocation;

/**
 * 体检项目字典测试类
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
public class QuestionServiceTest extends BaseJunit {

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
        String strDate = "2012-3-1";
        try {
            Date date = sdf.parse(strDate);
            assertEquals(questionService.getOnceAllocationID("402880b35f750210015f7530e46b006c", date),
                    "402880b35f750210015f7530f3020073");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * 问卷分配(测试类无法获取登陆者信息)
     * 
     * @author dhs
     * @param quesAllocationBo
     * @return 主键字符串
     */
    @Ignore
    @Test
    public void addAllocation() {
        QuesAllocation quesAllow = new QuesAllocation();// 分配表
        quesAllow.setUserId("1000");// 用户id
        quesAllow.setCustId("402880b35f750210015f7530f3020073");// 患者id
        quesAllow.setQuestionId("C000000CA00000000006");
        questionService.addAllocation(quesAllow);// 分配号
    }
}
