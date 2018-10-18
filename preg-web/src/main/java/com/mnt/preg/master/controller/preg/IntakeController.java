
package com.mnt.preg.master.controller.preg;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.basic.ProductCondition;
import com.mnt.preg.master.condition.preg.IntakeCondition;
import com.mnt.preg.master.entity.preg.PregIntake;
import com.mnt.preg.master.entity.preg.PregIntakeDetail;
import com.mnt.preg.master.form.preg.IntakeForm;
import com.mnt.preg.master.mapping.basic.MasterMapping;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseaseGroupPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.pojo.preg.IntakeTypePojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplatePojo;
import com.mnt.preg.master.pojo.preg.PregInitIntakePojo;
import com.mnt.preg.master.pojo.preg.PregIntakeDetailPojo;
import com.mnt.preg.master.pojo.preg.PregIntakePojo;
import com.mnt.preg.master.service.basic.ProductService;
import com.mnt.preg.master.service.preg.PregDietTemplateService;
import com.mnt.preg.master.service.preg.PregIntakeService;
import com.mnt.preg.master.service.preg.PregIntakeTypeService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 
 * 膳食模板
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-27 wsy 初版
 */
@Controller
public class IntakeController extends BaseController {

    @Resource
    private PregDietTemplateService pregDietTemplateService;

    @Resource
    private PregIntakeService pregIntakeService;

    @Resource
    private ProductService productService;

    @Resource
    private PregIntakeTypeService pregIntakeTypeService;

    /**
     * 膳食模板列表页action
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_INIT)
    public String initIntake(Model model) {
        PregInitIntakePojo initIntake = new PregInitIntakePojo();
        // 设置干预重点
        // TODO: 张传强--基础数据维护，调用业务数据的 service ？！
        // List<InterveneDiseaseVo> interveneDiseaseList = planService.queryInterveneDiseaseByCondition(null);
        List<InterveneDiseasePojo> interveneDiseaseList = new ArrayList<InterveneDiseasePojo>();
        List<InterveneDiseaseGroupPojo> diseaseList = new ArrayList<InterveneDiseaseGroupPojo>();
        Map<String, List<InterveneDiseasePojo>> diseaseMap = new TreeMap<String, List<InterveneDiseasePojo>>();
        if (CollectionUtils.isNotEmpty(interveneDiseaseList)) {
            for (InterveneDiseasePojo planDisease : interveneDiseaseList) {
                String type = planDisease.getDiseaseType();
                if (!diseaseMap.containsKey(type)) {
                    diseaseMap.put(type, new ArrayList<InterveneDiseasePojo>());
                }
                diseaseMap.get(type).add(planDisease);
            }
            for (String key : diseaseMap.keySet()) {
                InterveneDiseaseGroupPojo diseaseGroup = new InterveneDiseaseGroupPojo();
                diseaseGroup.setType(key.toString());
                diseaseGroup.setInterveneDiseaseList(diseaseMap.get(key));
                diseaseList.add(diseaseGroup);
            }
        }
        initIntake.setDiseaseList(diseaseList);
        initIntake.setDietTemplateList(pregDietTemplateService.queryDietTemplateByCondition(null));
        model.addAttribute("initIntake", initIntake);
        return MasterPageName.INTAKE_VIEW;
    }

    /**
     * 分页检索摄入量模版
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_QUERY)
    public @ResponseBody
    WebResult<List<PregIntakePojo>> queryIntake(IntakeCondition condition) {
        WebResult<List<PregIntakePojo>> webResult = new WebResult<List<PregIntakePojo>>();
        List<PregIntakePojo> list = pregIntakeService.queryIntake(condition);
        webResult.setData(list);
        webResult.setSuc(list);
        return webResult;
    }

    /**
     * 膳食模版添加初始化
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_EDIT_INIT)
    public String initAddIntake(String intakeId, Model model) {
        PregIntakePojo intake = new PregIntakePojo();
        List<PregIntakeDetailPojo> detailDtos = new ArrayList<PregIntakeDetailPojo>();
        if (!StringUtils.isEmpty(intakeId)) {
            IntakeCondition condition = new IntakeCondition();
            condition.setIntakeId(intakeId);
            intake = pregIntakeService.queryIntake(condition).get(0);
            detailDtos = pregIntakeService.queryIntakeDetailByIntakeId(intakeId);
        }
        model.addAttribute("intakeDTO", NetJsonUtils.objectToJson(intake));
        model.addAttribute("intakeDetails", JSONArray.fromObject(detailDtos));
        // 食谱列表
        List<PregDietTemplatePojo> dietTemplateList = pregDietTemplateService.queryDietTemplate(null);
        model.addAttribute("dietTemplateList", JSONArray.fromObject(dietTemplateList));
        // 商品列表
        ProductCondition productCondition = new ProductCondition();
        List<ProductPojo> productList = productService
                .queryProductAndElementsByCondition(productCondition);
        model.addAttribute("productList", JSONArray.fromObject(productList));
        // 摄入类型列表
        List<IntakeTypePojo> intakeTypeList = pregIntakeTypeService.queryIntakeTypeByCondition(null);
        model.addAttribute("intakeTypeList", JSONArray.fromObject(intakeTypeList));

        return MasterPageName.INTAKE_EDIT;
    }

    /**
     * 添加摄入量模版
     * 
     * @author zcq
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_ADD)
    public @ResponseBody
    WebResult<String> addIntake(IntakeForm intakeForm) {
        WebResult<String> webResult = new WebResult<String>();
        String intakeId = "";
        PregIntake intakeBo = TransformerUtils.transformerProperties(PregIntake.class, intakeForm);
        // 如果有id执行更新操作
        if (!StringUtils.isEmpty(intakeForm.getIntakeId())) {
            intakeId = intakeForm.getIntakeId();
            pregIntakeService.updateIntake(intakeBo);
            pregIntakeService.removeIntakeDetailByIntakeId(intakeId);
        }
        // 如果没有id执行新增
        else {
            intakeId = pregIntakeService.addIntake(intakeBo);
        }
        List<PregIntakeDetail> list = NetJsonUtils.jsonArrayToList(intakeForm.getDetailsJson(), PregIntakeDetail.class);
        for (PregIntakeDetail detailBo : list) {
            detailBo.setIntakeId(intakeId);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            for (PregIntakeDetail bo : list) {
                pregIntakeService.addIntakeDetail(bo);
            }
        }
        webResult.setSuc(intakeId);
        return webResult;
    }

    /**
     * 添加摄入量模版明细
     * 
     * @author zcq
     * @param intakeDetailBo
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_DETAIL_ADD)
    public @ResponseBody
    WebResult<String> addIntakeDetail(PregIntakeDetail intakeDetailBo) {
        WebResult<String> webResult = new WebResult<String>();
        webResult.setSuc(pregIntakeService.addIntakeDetail(intakeDetailBo));
        return webResult;
    }

    /**
     * 修改摄入量模版明细
     * 
     * @author zcq
     * @param intakeDetailBo
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_DETAIL_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateIntakeDetail(PregIntakeDetail intakeDetailBo) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregIntakeService.updateIntakeDetail(intakeDetailBo);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 删除摄入量模版明细
     * 
     * @author zcq
     * @param id
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_DETAIL_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeIntakeDetail(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregIntakeDetail intakeDetail = new PregIntakeDetail();
        intakeDetail.setId(id);
        intakeDetail.setFlag(0);
        pregIntakeService.updateIntakeDetail(intakeDetail);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 检索摄入量模版明细
     * 
     * @author zcq
     * @param intakeId
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_DETAIL_QUERY)
    public @ResponseBody
    WebResult<List<PregIntakeDetailPojo>> queryIntakeDetail(String intakeId) {
        WebResult<List<PregIntakeDetailPojo>> webResult = new WebResult<List<PregIntakeDetailPojo>>();
        webResult.setSuc(pregIntakeService.queryIntakeDetailByIntakeId(intakeId));
        return webResult;
    }

    /**
     * 删除摄入量模版
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeIntake(String intakeId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregIntake intake = new PregIntake();
        intake.setIntakeId(intakeId);
        intake.setFlag(0);
        pregIntakeService.updateIntake(intake);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 编辑摄入量模版
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateIntake(IntakeForm intakeForm) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregIntake intakeBo = TransformerUtils.transformerProperties(PregIntake.class, intakeForm);
        pregIntakeService.updateIntake(intakeBo);
        pregIntakeService.removeIntakeDetailByIntakeId(intakeForm.getIntakeId());
        this.getIntakeDetailBo(intakeForm, intakeForm.getIntakeId());
        if (StringUtils.isNotEmpty(intakeForm.getSwjc_qt())) {
            PregIntakeDetail detailBo = new PregIntakeDetail();
            detailBo.setIntakeId(intakeBo.getIntakeId());
            detailBo.setIntakeMealtype("C00000CA000000000002");
            detailBo.setIntakeType("F01999");
            detailBo.setIntakeDesc(intakeForm.getSwjc_qt());
            pregIntakeService.addIntakeDetail(detailBo);

        }
        if (StringUtils.isNotEmpty(intakeForm.getXwjc_qt())) {
            PregIntakeDetail detailBo = new PregIntakeDetail();
            detailBo.setIntakeId(intakeBo.getIntakeId());
            detailBo.setIntakeMealtype("C00000CA000000000004");
            detailBo.setIntakeType("F01999");
            detailBo.setIntakeDesc(intakeForm.getXwjc_qt());
            pregIntakeService.addIntakeDetail(detailBo);

        }
        if (StringUtils.isNotEmpty(intakeForm.getSqjc_qt())) {
            PregIntakeDetail detailBo = new PregIntakeDetail();
            detailBo.setIntakeId(intakeBo.getIntakeId());
            detailBo.setIntakeMealtype("C00000CA000000000006");
            detailBo.setIntakeType("F01999");
            detailBo.setIntakeDesc(intakeForm.getSqjc_qt());
            pregIntakeService.addIntakeDetail(detailBo);

        }
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    private void getIntakeDetailBo(IntakeForm intakeForm, String intakeId) {
        Field[] fields = intakeForm.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (!(fieldName.contains("c00000CA"))) {
                continue;
            }
            String string = (String) this.getFieldValueByName(fields[i].getName(), intakeForm);
            if (StringUtils.isEmpty(string)) {
                continue;
            }
            BigDecimal decimal = new BigDecimal(string); // 数值
            PregIntakeDetail detailBo = new PregIntakeDetail();
            String intakeMealtype = fieldName.substring(0, fieldName.indexOf("_")).toUpperCase();
            String intakeType = fieldName.substring(fieldName.indexOf("_") + 1);
            detailBo.setIntakeId(intakeId);
            detailBo.setIntakeMealtype(intakeMealtype);
            detailBo.setIntakeType(intakeType);
            detailBo.setIntakeCount(decimal);
            pregIntakeService.addIntakeDetail(detailBo);
        }
    }

    /**
     * 根据属性名获取属性值
     * */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 膳食模板明细
     * 
     * @author zcq
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_DETAIL_INIT)
    public String initIntakeDetail(IntakeCondition condition, Model model) {
        PregIntakePojo intake = pregIntakeService.queryIntake(condition).get(0);
        List<IntakeTypePojo> intakeTypeList = pregIntakeTypeService.queryIntakeTypeByCondition(null);
        model.addAttribute("intake", intake);
        model.addAttribute("intakeTypeList", intakeTypeList);
        return MasterPageName.INTAKE_DETAIL;
    }

    /**
     * 膳食模板名称排重
     * 
     * @author zcq
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = MasterMapping.PLAN_INTAKE_VALID)
    public @ResponseBody
    boolean intakeValid(String intakeId, String intakeName) {
        List<PregIntakePojo> list = pregIntakeService.queryIntake(null);
        if (intakeId.trim().isEmpty()) {// 添加
            for (PregIntakePojo intake : list) {
                if (intakeName.trim().equals(intake.getIntakeName())) {
                    return false;
                }
            }
        } else {// 修改
            IntakeCondition condition = new IntakeCondition();
            condition.setIntakeId(intakeId);
            List<PregIntakePojo> pojo = pregIntakeService.queryIntake(condition);
            if (intakeName.trim().equals(pojo.get(0).getIntakeName())) {
                return true;
            } else {
                for (PregIntakePojo intake : list) {
                    if (intakeName.trim().equals(intake.getIntakeName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
