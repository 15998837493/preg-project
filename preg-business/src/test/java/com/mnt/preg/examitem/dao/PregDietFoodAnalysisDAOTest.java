/*
 * PregDietFoodAnalysisDAOTest.java    1.0  2018年8月28日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.examitem.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mnt.preg.BaseJunit;
import com.mnt.preg.examitem.pojo.PregDietAnalysePojo;


/**
 * [关于类内容的描述]
 *
 * @author zj
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月28日 zj 初版
 */
public class PregDietFoodAnalysisDAOTest extends BaseJunit{

    @Autowired
    private PregDietFoodAnalysisDAO pregDietFoodAnalysisDAO;

    @Test
    public void testGetFoodElementSum() {        
        String foodRecordId = "402880ee65845ccd016584e0d30c00b3";
        List<PregDietAnalysePojo> list = pregDietFoodAnalysisDAO.getFoodElementSum(foodRecordId);
        assertEquals(1, list.size());
    }


//    @Test
    public void testGetEachMealEnergy() {
        String foodRecordId = "402880f55b9f406b015b9f42b680000e";
        List<PregDietAnalysePojo> list = pregDietFoodAnalysisDAO.getEachMealEnergy(foodRecordId);
        assertEquals(2, list.size());
    }


//    @Test
    public void testGetFoodProtidSum() {
        String foodRecordId = "402880ee658530a7016585bf46e10038";
        List<PregDietAnalysePojo> list = pregDietFoodAnalysisDAO.getFoodProtidSum(foodRecordId);
        assertEquals(1, list.size());
    }

//    @Test
    public void testGetFoodFatSum() {
        String foodRecordId = "402880ee658530a7016585bf46e10038";
        List<PregDietAnalysePojo> list = pregDietFoodAnalysisDAO.getFoodFatSum(foodRecordId);
        assertEquals(1, list.size());
    }


//    @Test
    public void testGetGL() {
        
    }

}
