
package com.mnt.preg.customer.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.system.entity.Schedule;
import com.mnt.preg.system.pojo.SchedulePojo;

/**
 * 孕期预约课程
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 xdc 初版
 */
@Validated
public interface PregCourseBookingService {

    /**
     * 根据条件查询预约课程
     * 
     * @author xdc
     * @param conditon
     * @return
     */
    List<PregCourseBookingPojo> queryCourseBooking(PregCourseBooking condition);

    /**
     * 根据id删除预约课程
     * 
     * @author xdc
     * @param id
     */
    void deleteCourseBookingById(String id);

    /**
     * 添加预约课程
     * 
     * @author xdc
     * @param courseBooking
     * @return
     */
    String addCourseBooking(PregCourseBooking courseBooking);

    /**
     * 条件查询一周配置表信息
     * 
     * @author zcq
     * @param condition
     * @return
     */
    List<SchedulePojo> queryScheduleByCondition(Schedule condition);
}
