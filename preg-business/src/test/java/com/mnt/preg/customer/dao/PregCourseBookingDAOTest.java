
package com.mnt.preg.customer.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;

/**
 * 诊疗DAO测试类
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-23 dhs 初版
 */
public class PregCourseBookingDAOTest extends BaseJunit {

    /**
     * 根据条件查询预约课程
     * 
     * @author dhs
     * @param planId
     * @return
     */
    @Test
    public void queryIntervenePlan() {
        PregCourseBooking condition = new PregCourseBooking();
        condition.setArchivesId("402880f45fe7d05c015fe7d8b5490000");
        List<PregCourseBookingPojo> list = pregCourseBookingDAO.queryCourseBooking(condition);
        assertEquals(list.size(), 0);
    }

}
