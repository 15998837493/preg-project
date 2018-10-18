/**
 * 
 */

package com.mnt.preg.customer.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.preg.customer.dao.PregCourseBookingDAO;
import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.system.entity.Schedule;
import com.mnt.preg.system.pojo.SchedulePojo;

/**
 * 客户管理
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Service
public class PregCourseBookingServiceImpl extends BaseService implements PregCourseBookingService {

    @Resource
    private PregCourseBookingDAO courseBookingDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PregCourseBookingPojo> queryCourseBooking(PregCourseBooking condition) {
        return courseBookingDAO.queryCourseBooking(condition);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void deleteCourseBookingById(String id) {
        courseBookingDAO.deleteCourseBookingById(id);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addCourseBooking(PregCourseBooking courseBooking) {
        return (String) courseBookingDAO.save(courseBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchedulePojo> queryScheduleByCondition(Schedule condition) {
        return courseBookingDAO.queryScheduleByCondition(condition);
    }
}
