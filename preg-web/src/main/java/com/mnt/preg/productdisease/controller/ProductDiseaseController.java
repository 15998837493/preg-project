/*
 * ProductDiseaseController.java    1.0  2018年8月1日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.productdisease.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.productdisease.entity.ProductDiseaseConfig;
import com.mnt.preg.productdisease.mapping.ProductDiseaseMapping;
import com.mnt.preg.productdisease.mapping.ProductDiseasePageName;
import com.mnt.preg.productdisease.pojo.NutritionMedicalPojo;
import com.mnt.preg.productdisease.pojo.ProductDiseaseConfigPojo;
import com.mnt.preg.productdisease.service.NutritionMedicalService;
import com.mnt.preg.web.constants.WebMsgConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 医嘱路径Controller
 *
 * @author lipeng
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018年8月1日 lipeng 初版
 */
@Controller
public class ProductDiseaseController extends BaseController {
    
    @Resource
    private NutritionMedicalService nutritionMedicalService;
    @Resource
    private InterveneDiseaseService diseaseService;
    
    /**
     * 
     * 医嘱路径配置 列表页面初始化
     *
     * @author lipeng
     * @param model
     * @return
     */
    @RequestMapping(value = ProductDiseaseMapping.NUTRITION_MEDICAL)
    public String initNutritionMedical(Model model) {
        // 获取所有疾病诊断项目列表
        List<InterveneDiseasePojo> diseaseList = diseaseService.queryInterveneDisease(null);
        model.addAttribute("diseaseList", NetJsonUtils.listToJsonArray(diseaseList));
        return ProductDiseasePageName.NUTRITION_MEDICAL_VIEW;
    }
    
    /**
     * 
     * 执行清单配置 列表页面初始化
     *
     * @author lipeng
     * @param model
     * @return
     */
    @RequestMapping(value = ProductDiseaseMapping.EXECUTION)
    public String initExecution(Model model) {
        // 获取所有疾病诊断项目列表
        List<InterveneDiseasePojo> diseaseList = diseaseService.queryInterveneDisease(null);
        model.addAttribute("diseaseList", NetJsonUtils.listToJsonArray(diseaseList));
        return ProductDiseasePageName.EXECUTION_VIEW;
    }
    
    /**
     * 
     * 医嘱路径配置 列表数据查询
     *
     * @author lipeng
     * @param nutritionMedicalPojo
     * @return
     */
    @RequestMapping(value = ProductDiseaseMapping.QUERY_NUTRITION_MEDICAL)
    public @ResponseBody
    WebResult<List<NutritionMedicalPojo>> queryNutritionMedical(NutritionMedicalPojo nutritionMedicalPojo){
        List<NutritionMedicalPojo> data = nutritionMedicalService.queryNutritionMedical(nutritionMedicalPojo);
        WebResult<List<NutritionMedicalPojo>> webResult = new WebResult<List<NutritionMedicalPojo>>();
        webResult.setData(data);
        webResult.setSuc(data);
        return webResult;
    }
    
    /**
     * 
     * 医嘱路径配置 列表页面删除
     *
     * @author lipeng
     * @param productId
     * @param productDiseaseId
     * @return
     */
    @RequestMapping(value = ProductDiseaseMapping.REMOVE_NUTRITION_MEDICAL)
    public @ResponseBody
    WebResult<Boolean> removeNutritionMedical(String productId, String productDiseaseId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        nutritionMedicalService.removeNutritionMedical(productId, productDiseaseId);
        webResult.setSuc(true, WebMsgConstant.success_message);
        return webResult;
    }
    
    /**
     * 
     * 医嘱路径配置 配置页面添加、修改
     *
     * @author lipeng
     * @param pdc
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = ProductDiseaseMapping.ADDORUPDATE_NUTRITION_MEDICAL)
    public @ResponseBody
    WebResult<String> addOrUpdateNutritionMedical(ProductDiseaseConfig pdc) throws ParseException {
        WebResult<String> webResult = new WebResult<String>();

        String id = nutritionMedicalService.addOrUpdateNutritionMedical(pdc);
        webResult.setSuc(id, WebMsgConstant.success_message);

        return webResult;
    }
    
    /**
     * 
     * 医嘱路径配置 配置页面列表查询
     *
     * @author lipeng
     * @param productId
     * @return
     */
    @RequestMapping(value = ProductDiseaseMapping.QUERY_PRODUCT_DISEASE_CONFIG)
    public @ResponseBody
    WebResult<List<ProductDiseaseConfigPojo>> queryProductDiseaseConfig(String productId){
        List<ProductDiseaseConfigPojo> data = nutritionMedicalService.queryProductDiseaseConfig(productId);
        WebResult<List<ProductDiseaseConfigPojo>> webResult = new WebResult<List<ProductDiseaseConfigPojo>>();
        webResult.setData(data);
        webResult.setSuc(data);
        return webResult;
    }
}
