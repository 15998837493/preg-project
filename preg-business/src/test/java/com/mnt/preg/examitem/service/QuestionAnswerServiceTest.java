
package com.mnt.preg.examitem.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.pojo.QuestionAnswerPojo;

/**
 * 问卷问题答案测试类
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
public class QuestionAnswerServiceTest extends BaseJunit {

    /**
     * 
     * 根据问题id查询问卷答案明细
     * 
     * @author dhs
     * @param questionAllocId
     *            主键
     * @return
     */
    @Test
    public void queryQuestionByProblemId() {
        List<QuestionAnswerPojo> list = questionAnswerService.queryQuestionByProblemId(
                "402880b35ec68aac015ec7b3dea9013a",
                "402880b35f750210015f7530f3020073");
        assertEquals(list.size(), 0);
    }
}
