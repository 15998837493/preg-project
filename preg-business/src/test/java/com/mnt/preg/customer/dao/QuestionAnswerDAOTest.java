
package com.mnt.preg.customer.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;

/**
 * 
 * 问卷结果操作测试类
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-1 mnt_zhangjing 初版
 */
public class QuestionAnswerDAOTest extends BaseJunit {

    /**
     * 
     * 根据问题主键（problem_id）查询问卷问题答案表
     * 
     * @author dhs
     * @param recordId
     *            记录
     * @return
     */
    @Test
    public void queryAnswerByProblemId() {
        List<QuestionAnswerPojo> list = questionAnswerDAO.queryAnswerByProblemId("402880b35ec68aac015ec7b3dea9013a",
                "402880b35f750210015f7530f3020073");
        assertEquals(list.size(), 0);
    }
}
