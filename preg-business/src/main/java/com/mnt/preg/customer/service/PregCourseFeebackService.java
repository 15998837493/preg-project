
package com.mnt.preg.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.entity.PregCourseFeedback;
import com.mnt.preg.customer.pojo.PregCourseFeedbackPojo;

/**
 * 孕期预约课程反馈
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 xdc 初版
 */
@Validated
public interface PregCourseFeebackService {

    /**
     * 根据条件查询预约课程反馈
     * 
     * @author xdc
     * @param conditon
     * @return
     */
    List<PregCourseFeedbackPojo> queryCourseFeedback(PregCourseFeedback condition);

    /**
     * 根据id获取预约课程反馈
     * 
     * @author xdc
     * @param id
     * @return
     */
    PregCourseFeedbackPojo getCourseFeedbackById(String id);

    /**
     * 添加预约课程反馈
     * 
     * @author xdc
     * @param courseBooking
     * @return
     */
    PregCourseFeedbackPojo addCourseFeedback(PregCourseFeedback courseFeedback);

    /**
     * 添加预约课程反馈
     * 
     * @author xdc
     * @param courseBooking
     * @return
     */
    void updateCourseFeedback(PregCourseFeedback courseFeedback);

}
