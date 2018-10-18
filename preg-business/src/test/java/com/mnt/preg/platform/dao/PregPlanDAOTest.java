
package com.mnt.preg.platform.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.condition.IntervenePlanCondition;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;
import com.mnt.preg.platform.pojo.PregPlanCoursePojo;
import com.mnt.preg.platform.pojo.PregPlanDietPojo;
import com.mnt.preg.platform.pojo.PregPlanIntakeDetailPojo;
import com.mnt.preg.platform.pojo.PregPlanPointsPojo;

/**
 * 诊疗DAO测试类
 * 
 * @author dhs
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-23 dhs 初版
 */
public class PregPlanDAOTest extends BaseJunit {

    /**
     * 条件检索膳食方案信息
     * 
     * @author dhs
     * @param planId
     * @return
     */
    @Test
    public void queryIntervenePlan() {
        IntervenePlanCondition condition = new IntervenePlanCondition();
        condition.setDiagnosisId("402880f45fe7d05c015fe7d8b5490000");
        List<PregIntervenePlanPojo> list = pregPlanDAO.queryIntervenePlan(condition);
        assertEquals(list.size(), 0);
    }

    /**
     * 查询干预方案--饮食原则
     * 
     * @author dhs
     * @param planId
     * @return
     */
    @Test
    public void queryPlanPointsByPlanId() {
        List<PregPlanPointsPojo> list = pregPlanDAO.queryPlanPointsByPlanId("402880f45fe7d05c015fe7d8b5490000");
        assertEquals(list.size(), 0);
    }

    /**
     * 查询干预方案--摄入量模版信息
     * 
     * @author dhs
     * @param planId
     * @return
     */
    @Test
    public void queryPlanIntakeDetailByPlanId() {
        List<PregPlanIntakeDetailPojo> list = pregPlanDAO
                .queryPlanIntakeDetailByPlanId("402880f45fe7d05c015fe7d8b5490000");
        assertEquals(list.size(), 0);
    }

    /**
     * 查询干预方案--食谱信息
     * 
     * @author dhs
     * @param planId
     * @return
     */
    @Test
    public void queryPlanDietDetailsByPlanId() {
        List<PregPlanDietPojo> list = pregPlanDAO.queryPlanDietDetailsByPlanId("402880f45fe7d05c015fe7d8b5490000");
        assertEquals(list.size(), 0);
    }

    /**
     * 查询干预方案--基础课程/诊断课程
     * 
     * @author dhs
     * @param planId
     * @return
     */
    @Test
    public void queryPlanCourseByPlanIdAndPregType() {
        List<PregPlanCoursePojo> list = pregPlanDAO.queryPlanCourseByPlanIdAndPregType(
                "402880f45fe7d05c015fe7d8b5490000", "0");
        assertEquals(list.size(), 0);
    }
}
