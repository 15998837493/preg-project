/*
 * DiseaseOftenDAOTest.java    1.0  2017-11-22
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.condition.DiseaseOftenCondition;
import com.mnt.preg.platform.entity.DiseaseOften;

/**
 * DiseaseOftenDAOTest
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-22 scd 初版
 */
public class DiseaseOftenDAOTest extends BaseJunit {

    @Resource
    private DiseaseOftenDAO diseaseOftenDAO;

    private DiseaseOften diseaseOften;

    private DiseaseOftenCondition condition;

    @Before
    public void setUp() {
        diseaseOften = new DiseaseOften();
        diseaseOften.setDiseaseId("402880ea5fe278f3015fe2c55b3a0019");
        diseaseOften.setDiseaseName("高同型半胱氨酸血症");
        diseaseOften.setCreateInsId("1100002");
        diseaseOften.setCreateUserId("10000");

        condition = new DiseaseOftenCondition();
        condition.setDiseaseId("402880ea5fe278f3015fe2c55b3a0019");
        condition.setDiseaseName("高同型半胱氨酸血症");
        condition.setCreateUserId("10000");
    }

    /**
     * 
     * 条件检索常用诊断项目
     * 
     * @author scd
     * @param daiseaseOften
     * @return
     */
    @Test
    public void testQueryDiseaseOften() {
        List<DiseaseOften> list = diseaseOftenDAO.queryDiseaseOften(condition);
        assert (list.size() > 0);
    }

    /**
     * 
     * 逻辑删除常用诊断项目
     * 
     * @author scd
     * @param diseaseId
     * @return
     */
    @Test
    public void testRemoveDiseaseOften() {
        Integer count = diseaseOftenDAO.removeDiseaseOften(diseaseOften.getDiseaseId());
        assert (count == 1);
    }

    /**
     * 
     * 根据机构ID用户ID获取diseaseCode
     * 
     * @author scd
     * @param insId
     * @param userId
     * @return
     */
    @Test
    public void testGetDiseaseCode() {
        String code = diseaseOftenDAO.getDiseaseCode(diseaseOften.getCreateInsId(), diseaseOften.getCreateUserId());
        assert (code == "A00008");
    }
}
