/*
 * DiseaseOftenServiceTest.java    1.0  2017-11-22
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.platform.condition.DiseaseOftenCondition;
import com.mnt.preg.platform.entity.DiseaseOften;

/**
 * DiseaseOftenServiceTest
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-22 scd 初版
 */
public class DiseaseOftenServiceTest extends BaseJunit {

    @Resource
    private DiseaseOftenService diseaseOftenService;

    private DiseaseOften diseaseOften;

    private String maxCode = "110000210000A00009";

    private DiseaseOftenCondition condition;

    @Before
    public void setUp() {
        Date date = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDate.format(date);

        diseaseOften = new DiseaseOften();
        diseaseOften.setDiseaseId("402880ea5fe278f3015fe2c55b3a0019");
        diseaseOften.setDiseaseName("高同型半胱氨酸血症");

        diseaseOften.setCreateUserId("10000");
        diseaseOften.setUpdateTime(date);
        diseaseOften.setCreateTime(date);
        diseaseOften.setUpdateUserId("10000");
        diseaseOften.setCreateInsId("1100002");

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
     * @param diseaseOften
     * @return
     */
    @Test
    public void testQueryDiseaseOften() {
        List<DiseaseOften> list = diseaseOftenService.queryDiseaseOften(condition);
        assert (list.size() > 0);
    }

    /**
     * 
     * 添加常用诊断项目
     * 
     * @author scd
     * @param diseaseOften
     * @return
     */
    @Test
    public void testAddDiseaseOften() {
        try {
            String id = diseaseOftenService.addDiseaseOften(diseaseOften);
            assertNotNull(id);
        } catch (Exception e) {
        }
    }

    /**
     * 
     * 根据主键获取常用诊断项目
     * 
     * @author scd
     * @param diseaseId
     * @return
     */
    @Test
    public void testGetDiseaseOften() {
        try {
            DiseaseOften diseaseOftenPojo = diseaseOftenService.getDiseaseOften(diseaseOften.getDiseaseId());
            assertNotNull(diseaseOftenPojo);
        } catch (Exception e) {
        }
    }

    /**
     * 
     * 删除常用诊断项目
     * 
     * @author scd
     * @param diseaseId
     * @return
     */
    @Test
    public void testRemoveDiseaseOften() {
        try {
            Boolean flag = diseaseOftenService.removeDiseaseOften(diseaseOften.getDiseaseId());
            assertTrue(flag);
        } catch (Exception e) {
        }
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
        String code = diseaseOftenService.getDiseaseCode(diseaseOften.getCreateInsId(), diseaseOften.getCreateUserId());
        assertEquals(code, maxCode);
    }
}
