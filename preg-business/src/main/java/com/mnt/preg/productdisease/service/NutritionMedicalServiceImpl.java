/*
 * NutritionMedicalServiceImpl.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.productdisease.dao.NutritionMedicalDAO;
import com.mnt.preg.productdisease.entity.ProductDiseaseConfig;
import com.mnt.preg.productdisease.pojo.NutritionMedicalPojo;
import com.mnt.preg.productdisease.pojo.ProductDiseaseConfigPojo;

/**
 * 营养制剂服务实现类
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
@Service
public class NutritionMedicalServiceImpl extends BaseService implements NutritionMedicalService{

    @Resource
    NutritionMedicalDAO nutritionMedicalDao;
    
    /**
     * 医嘱路径配置 列表数据查询
     */
    @Override
    @Transactional(readOnly = true)
    public List<NutritionMedicalPojo> queryNutritionMedical(NutritionMedicalPojo nutritionMedicalPojo) {
        
        return nutritionMedicalDao.queryNutritionMedical(nutritionMedicalPojo);
    }
    
    /**
     * 医嘱路径配置 列表页面删除
     */
    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public void removeNutritionMedical(String productId, String productDiseaseId) {
        nutritionMedicalDao.removeNutritionMedical(productId, productDiseaseId);
    }
    
    /**
     * 医嘱路径配置 配置页面列表查询
     */
    @Override
    public List<ProductDiseaseConfigPojo> queryProductDiseaseConfig(String productId) {
        return nutritionMedicalDao.queryProductDiseaseConfig(productId);
    }
    
    /**
     * 医嘱路径配置 配置页面添加、修改
     */
    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public String addOrUpdateNutritionMedical(ProductDiseaseConfig pdc) throws ParseException {
        Date nowDateTime = JodaTimeTools.toDateTime(JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_2),JodaTimeTools.FORMAT_2);
        if("".equals(pdc.getProductDiseaseId())) {
            pdc.setProductDiseaseId(null);
            //pdc.setCreateUserId(getLoginUser().getUserPojo().getUserId());
            pdc.setCreateTime(nowDateTime);
            //pdc.setCreateInsId(getInsId());
            return (String) nutritionMedicalDao.save(pdc);
        }else {
            ProductDiseaseConfig oldPdc = nutritionMedicalDao.get(ProductDiseaseConfig.class, pdc.getProductDiseaseId());
            //oldPdc.setUpdateUserId(getLoginUser().getUserPojo().getUserId());
            oldPdc.setUpdateTime(nowDateTime);
            oldPdc.setDiseaseId(pdc.getDiseaseId());
            oldPdc.setProductDiseaseFrequency(pdc.getProductDiseaseFrequency());
            oldPdc.setProductDiseaseExecutionlist(pdc.getProductDiseaseExecutionlist());
            nutritionMedicalDao.update(oldPdc);
            return pdc.getProductDiseaseId();
        }
        
    }

}
