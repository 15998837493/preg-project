
package com.mnt.preg.platform.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mnt.preg.master.entity.basic.FoodTree;
import com.mnt.preg.master.entity.preg.PregDietTemplateDetail;
import com.mnt.preg.master.pojo.basic.FoodMaterialListInfoPojo;
import com.mnt.preg.master.pojo.basic.TreePojo;
import com.mnt.preg.master.service.basic.TreeService;
import com.mnt.preg.platform.entity.*;
import com.mnt.preg.platform.pojo.*;
import com.mnt.preg.web.pojo.ZTree;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.customer.entity.PregCourseBooking;
import com.mnt.preg.customer.entity.PregCourseFeedback;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.customer.pojo.PregCourseFeedbackPojo;
import com.mnt.preg.customer.service.PregCourseBookingService;
import com.mnt.preg.customer.service.PregCourseFeebackService;
import com.mnt.preg.examitem.form.DiagnosisPrescriptionForm;
import com.mnt.preg.examitem.service.PregInbodyService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.master.condition.items.DiseaseNutrientCondition;
import com.mnt.preg.master.condition.preg.DietTemplateDetailCondition;
import com.mnt.preg.master.entity.items.HospitalInspectPay;
import com.mnt.preg.master.form.preg.IntakeForm;
import com.mnt.preg.master.mapping.items.ItemsMapping;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.master.pojo.items.DiseaseNutrientPojo;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.pojo.preg.PregDietTemplateDetailPojo;
import com.mnt.preg.master.service.basic.ElementService;
import com.mnt.preg.master.service.items.HospitalInspectPayService;
import com.mnt.preg.master.service.items.InspectItemService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.master.service.preg.PregDietTemplateService;
import com.mnt.preg.platform.condition.DiagnosisBookingCondition;
import com.mnt.preg.platform.condition.DiagnosisQuotaItemCondition;
import com.mnt.preg.platform.form.AuxiliaryCheckForm;
import com.mnt.preg.platform.form.IntervenePlanGroupForm;
import com.mnt.preg.platform.mapping.PlatFormPageName;
import com.mnt.preg.platform.mapping.PlatformMapping;
import com.mnt.preg.platform.service.HospitalInspectPayTemplateService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregPlanService;
import com.mnt.preg.system.pojo.SchedulePojo;
import com.mnt.preg.system.service.ScheduleService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 干预方案：1、膳食方案；2、营养处方；3、教育课程；
 *
 * @author zcq
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2015-6-13 zcq 初版
 */
@Controller
public class DiagnosisPlanController extends BaseController {

    @Resource
    public PregDiagnosisService pregDiagnosisService;

    @Resource
    public PregPlanService pregPlanService;

    @Resource
    private PregDietTemplateService pregDietTemplateService;

    @Resource
    public ExamItemUtil examItemUtil;

    @Resource
    public PregInbodyService pregInbodyService;

    @Resource
    private ItemService itemService;

    @Resource
    private InspectItemService inspectItemService;

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private HospitalInspectPayTemplateService hospitalInspectPayTemplateService;

    @Resource
    private HospitalInspectPayService hospitalInspectPayService;

    @Resource
    private ElementService elementService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private PregCourseBookingService courseBookingService;

    @Resource
    private PregCourseFeebackService courseFeebackService;

    @Resource
    private ExamResultRecordUtil examResultRecordUtil;

    @Resource
    private PregDiagnosisDiseaseService diagnosisDiseaseService;

    @Resource
    private TreeService treeService;

    // ***************************************【医嘱页面引导页】**********************************************

    /**
     * 获取医嘱主页面
     *
     * @param diagnosisId
     * @param model
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.PLAN_MAIN_VIEW)
    public String getPlanMainView(String diagnosisId, String custId, Model model) {
        // 辅助检查数据
        getPlanFuzhuView(diagnosisId, model);
        // 营养处方数据
        getPlanExtenderZhu(diagnosisId, custId, model);
        // 膳食方案数据
        planAdjust(diagnosisId, model);
        // 复查预约数据
        getPlanBookingPageYiZhu(diagnosisId, custId, model);
        // 预约课程
        getPlanCourseBooking(custId, model);

        return PlatFormPageName.PLAN_MAIN;
    }

    // ***************************************【辅助检查项目】**********************************************

    /**
     * 辅助检查
     *
     * @param diagnosisId 接诊号
     * @param model
     * @return view
     * @author scd
     */
    private void getPlanFuzhuView(String diagnosisId, Model model) {
        // 辅助检查项目集合
        DiagnosisQuotaItemCondition condition = new DiagnosisQuotaItemCondition();
        condition.setDiagnosisId(diagnosisId);
        List<DiagnosisQuotaItemVo> quotaItems = pregDiagnosisService.queryDiagnosisQuotaItem(condition);
        model.addAttribute("quotaItems", NetJsonUtils.listToJsonArray(quotaItems));
        // 辅助检查项目套餐集合
        List<HospitalInspectPayTemplate> queryAuxiliaryCheck = hospitalInspectPayTemplateService
                .queryAuxiliaryCheck(null);
        model.addAttribute("queryAuxiliaryCheck", NetJsonUtils.listToJsonArray(queryAuxiliaryCheck));

        // 医院收费项目集合
        List<HospitalInspectPayPojo> hospitals = hospitalInspectPayService
                .queryInspectPayByCondition(null);
        model.addAttribute("hospitals", NetJsonUtils.listToJsonArray(hospitals));
        // 诊断项目
        model.addAttribute("diagnosis", NetJsonUtils.objectToJson(pregDiagnosisService.getDiagnosis(diagnosisId)));
        List<PregDiagnosisDisease> diagnosisDisease = diagnosisDiseaseService
                .queryDiagnosisDiseaseByDiagnosisId(diagnosisId);
        if (CollectionUtils.isEmpty(diagnosisDisease)) {
            model.addAttribute("diagnosisDisease", NetJsonUtils.listToJsonArray(new ArrayList<PregDiagnosisDisease>()));
        } else {
            model.addAttribute("diagnosisDisease", NetJsonUtils.listToJsonArray(diagnosisDisease));
        }
    }

    /**
     * 添加辅助检查项目
     *
     * @param hospital
     * @param diagnosisId
     * @return
     * @author scd
     */
    @RequestMapping(value = PlatformMapping.ADD_AUXILIARY)
    public @ResponseBody
    WebResult<DiagnosisQuotaItem> addAuxiliary(String hospital, String diagnosisId) {
        WebResult<DiagnosisQuotaItem> WebResult = new WebResult<DiagnosisQuotaItem>();
        HospitalInspectPay hospitalInspect = (HospitalInspectPay) NetJsonUtils.jsonToObject(hospital,
                HospitalInspectPay.class);
        DiagnosisQuotaItem diagnosisQuotaItem = new DiagnosisQuotaItem();
        diagnosisQuotaItem.setInspectItemId(hospitalInspect.getInspectId());
        diagnosisQuotaItem.setInspectItemName(hospitalInspect.getInspectName());
        diagnosisQuotaItem.setDiagnosisId(diagnosisId);
        String id = pregDiagnosisService.addDiagnosisQuotaItem(diagnosisQuotaItem);
        DiagnosisQuotaItem inspectItem = pregDiagnosisService.getDiagnosisQuotaItemById(id);
        WebResult.setSuc(inspectItem);
        return WebResult;
    }

    /**
     * 添加辅助检查套餐
     *
     * @param form
     * @return
     * @author scd
     */
    @RequestMapping(value = PlatformMapping.ADD_HOSPITAL)
    public @ResponseBody
    WebResult<Boolean> addHospitalTemplate(AuxiliaryCheckForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        HospitalInspectPayTemplate auxiliary = TransformerUtils.transformerProperties(HospitalInspectPayTemplate.class,
                form);
        hospitalInspectPayTemplateService.addAuxiliaryCheck(auxiliary);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 异步检索辅助检查项目模板
     *
     * @param params
     * @return
     * @author scd
     */
    @RequestMapping(value = PlatformMapping.QUERY_HOSPITAL)
    public @ResponseBody
    WebResult<Boolean> queryHospitalTemplate(String params) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        List<HospitalInspectPayTemplate> list = hospitalInspectPayTemplateService.queryAuxiliaryCheck(null);
        webResult.setData(list);
        return webResult;
    }

    /**
     * 删除辅助检查项目
     *
     * @param ids
     * @return
     * @author scd
     */
    @RequestMapping(value = PlatformMapping.REMOVE_AUXILIARY)
    public @ResponseBody
    WebResult<Boolean> removeQuotaItem(String[] ids) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        for (String id : ids) {
            pregDiagnosisService.removeQuotaItem(id);
        }
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 验证辅助检查项目名称是否重复
     *
     * @param templetName
     * @return boolean
     * @author zcq
     * @date 2018/10/17 12:20
     */
    @RequestMapping(value = ItemsMapping.TEMPLETNAME_VALID)
    public @ResponseBody
    boolean checktempletNameValid(String templetName) {
        int index = hospitalInspectPayTemplateService.checktempletNameValid(templetName);
        if (index < 1) {
            return true;
        }
        return false;
    }

    /**
     * 删除辅助检查项目套餐
     *
     * @param templetId
     * @return
     * @author scd
     */
    @RequestMapping(value = ItemsMapping.TEMPLETNAME_REMOVE)
    public @ResponseBody
    WebResult<Boolean> removeTemplet(String templetId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        hospitalInspectPayTemplateService.removeTemplet(templetId);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 根据诊断项目获取项目的检查项目
     *
     * @param diseaseId
     * @return
     * @author scd
     */
    @RequestMapping(value = ItemsMapping.DISEASE_INSPECT_CONFIG)
    public @ResponseBody
    WebResult<HospitalInspectPayPojo> queryInspectConfig(String diseaseId) {
        WebResult<HospitalInspectPayPojo> webResult = new WebResult<HospitalInspectPayPojo>();
        List<HospitalInspectPayPojo> list = pregDiagnosisService.queryInspectConfig(diseaseId);
        webResult.setData(list);
        return webResult;
    }

    // ***************************************【营养制剂处方】**********************************************

    /**
     * 医嘱页面——补充剂嵌入页
     *
     * @param custId
     * @param model
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.PLAN_GET_EXTENDER_YIZHU)
    public void getPlanExtenderZhu(String diagnosisId, String custId, Model model) {
        // 第一步：获取本次接诊营养处方
        List<DiagnosisPrescriptionPojo> presList = pregDiagnosisService.queryDiagnosisPrescriptionByDiagnosisId(diagnosisId);
        // 第二步：若本次接诊营养处方为空，则获取最近一次营养处方
        if (CollectionUtils.isEmpty(presList)) {
            presList = pregDiagnosisService.queryLastDiagnosisPrescription(custId);
            // 第三步：保存为本次接诊营养处方
            if (CollectionUtils.isNotEmpty(presList)) {
                // 过滤
                Iterator<DiagnosisPrescriptionPojo> it = presList.iterator();
                while (it.hasNext()) {
                    DiagnosisPrescriptionPojo pojo = it.next();
                    Integer status = pojo.getStatus();
                    if (status == null || status == 1) {
                        pojo.setStatus(2);
                    } else if (status == 3) {
                        it.remove();
                    }
                }
                // 保存
                if (CollectionUtils.isNotEmpty(presList)) {
                    pregDiagnosisService.saveDiagnosisPrescription(diagnosisId, presList);
                }
            }
        }

        // 检索疾病相关连的元素列表
        PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisId);
        Map<String, DiseaseNutrientPojo> deMap = new HashMap<String, DiseaseNutrientPojo>();
        String diseaseCodes = diagnosis.getDiagnosisDiseaseCodes();
        if (StringUtils.isNotBlank(diseaseCodes)) {
            List<String> codeList = Arrays.asList(diseaseCodes.split(","));
            DiseaseNutrientCondition condition = new DiseaseNutrientCondition();
            condition.setDiseaseCodeList(codeList);
            List<DiseaseNutrientPojo> disElementList = interveneDiseaseService.queryDiseaseNutrient(condition);
            if (CollectionUtils.isNotEmpty(disElementList)) {
                for (DiseaseNutrientPojo pojo : disElementList) {
                    deMap.put(pojo.getNutrientId(), pojo);
                }
            }
        }

        // 获取所有商品
        Map<String, ProductPojo> pMap = new HashMap<String, ProductPojo>();
        List<ProductPojo> pList = productService.queryProduct(null);
        if (CollectionUtils.isNotEmpty(pList)) {
            for (ProductPojo pojo : pList) {
                pMap.put(pojo.getProductId(), pojo);
            }
        }

        model.addAttribute("custId", custId);
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("diagnosis", NetJsonUtils.objectToJson(pregDiagnosisService.getDiagnosis(diagnosisId)));
        model.addAttribute("diagnosisExtenderList", NetJsonUtils.listToJsonArray(presList));
        model.addAttribute("productAllMap", NetJsonUtils.objectToJson(pMap));
        model.addAttribute("diseEleAllMap", NetJsonUtils.objectToJson(deMap));
    }

    /**
     * 保存营养处方
     *
     * @param form
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.DIAGNOSIS_PRESCRIPTION_SAVE)
    @ResponseBody
    public WebResult<Boolean> saveDiagnosisPrescription(DiagnosisPrescriptionForm form) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        String diagnosisId = form.getDiagnosisId();
        List<DiagnosisPrescriptionPojo> extenderList = form.getExtenderList();
        if (extenderList != null) {
            Iterator<DiagnosisPrescriptionPojo> it = extenderList.iterator();
            while (it.hasNext()) {
                DiagnosisPrescriptionPojo pojo = it.next();
                if (pojo == null || StringUtils.isEmpty(pojo.getProductId())) {
                    it.remove();
                } else {
                    pojo.setDiagnosisId(diagnosisId);
                }
            }
            for (int x = 0; x < extenderList.size(); x++) {
                String productId = extenderList.get(x).getProductId();
                if (StringUtils.isNotBlank(productId)) {
                    ProductPojo productPojo = productService.getProductById(productId);
                    extenderList.get(x).setProductDosageDesc(productPojo.getProductDosageDesc());// 剂量说明
                }
            }
        }
        // 保存营养处方
        pregDiagnosisService.saveDiagnosisPrescription(diagnosisId, extenderList);

        return webResult.setSuc(true);
    }

    // ***************************************【膳食处方】**********************************************

    /**
     * 膳食方案--获取历史方案明细
     *
     * @param planId
     * @return
     * @author zcq
     * @date 2018/10/17 12:20
     */
    @RequestMapping(value = PlatformMapping.PLAN_INTAKE_GET)
    public @ResponseBody
    WebResult<List<PregPlanIntakeDetailPojo>> queryPlanIntakeDetailByPlanId(String planId) {
        WebResult<List<PregPlanIntakeDetailPojo>> webResult = new WebResult<List<PregPlanIntakeDetailPojo>>();
        webResult.setSuc(pregPlanService.queryPlanIntakeDetailByPlanId(planId));
        return webResult;
    }

    /**
     * 膳食方案--设置页面
     *
     * @param diagnosisId
     * @param model
     * @return void
     * @author zcq
     * @date 2018/10/17 12:20
     */
    @RequestMapping(value = PlatformMapping.RECEIVE_PLAN_ADJUST)
    private void planAdjust(String diagnosisId, Model model) {
        // 初始化数据
        PregInitPlanGroupPojo initPlanGroup = pregPlanService.getInitPlanGroup(diagnosisId);
        // 上次完成数据
        PregIntervenePlanGroupPojo adjustGroup = pregPlanService.getIntervenePlanGroup(diagnosisId);

        String foodRecommend = initPlanGroup.getFoodRecommend();
        PregIntervenePlanPojo planVo = adjustGroup.getPlanVo();
        if (planVo != null) {
            if (StringUtils.isNotEmpty(planVo.getFoodRecommend())) {
                foodRecommend = planVo.getFoodRecommend();
            }
            model.addAttribute("planVo", NetJsonUtils.objectToJson(adjustGroup.getPlanVo()));
        } else {
            model.addAttribute("planVo", "null");
        }
        // 诊断疾病
        Map<String, String> diseaseMap = new HashMap<String, String>();
        List<PregDiagnosisDisease> diagnosisDis = diagnosisDiseaseService
                .queryDiagnosisDiseaseByDiagnosisId(diagnosisId);
        for (PregDiagnosisDisease disease : diagnosisDis) {
            if (StringUtils.isNotEmpty(disease.getDiseaseCode())) {
                diseaseMap.put(disease.getDiseaseCode(), disease.getDiseaseName());
            }
        }
        model.addAttribute("diseaseMap", diseaseMap);
        // 诊断项目列表
        List<InterveneDiseasePojo> diseaseList = interveneDiseaseService.queryInterveneDisease(null);
        // 个人膳食模板列表
        List<PregUserIntakePojo> personIntakeList = pregPlanService.queryUserIntakeByUserId(this.getLoginUser()
                .getUserPojo().getUserId());
        // 基础数据
        model.addAttribute("intakeTypeList", NetJsonUtils.listToJsonArray(initPlanGroup.getIntakeTypeList()));
        model.addAttribute("intakeTypeQitList", initPlanGroup.getIntakeTypeList());
        model.addAttribute("historyPlanList", NetJsonUtils.listToJsonArray(initPlanGroup.getHistoryPlanList()));
        model.addAttribute("dietList", NetJsonUtils.listToJsonArray(initPlanGroup.getDietList()));
        model.addAttribute("productList", NetJsonUtils.listToJsonArray(initPlanGroup.getProductList()));
        model.addAttribute("diseaseList", NetJsonUtils.listToJsonArray(diseaseList));

        // 查询食谱类别
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getMaterialNodes()));

        // 历史数据
        PregDiagnosisPojo diagnosisPojo = adjustGroup.getDiagnosis();
        model.addAttribute("diagnosisVo", diagnosisPojo);
        model.addAttribute("diagnosisJson", NetJsonUtils.objectToJson(diagnosisPojo));
        model.addAttribute("intakeDetailList", NetJsonUtils.listToJsonArray(adjustGroup.getPlanIntakeDetailList()));
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("foodRecommend", foodRecommend);
        model.addAttribute("createUserId", this.getLoginUser().getUserPojo().getUserId());
        model.addAttribute("suggestIntakeEnergy", getSuggestIntakeEnergy(diagnosisPojo.getDiagnosisCustHeight(),
                diagnosisPojo.getDiagnosisPregnantStage()));
        model.addAttribute("personIntakeList", NetJsonUtils.listToJsonArray(personIntakeList));
    }

    /**
     * 获取食谱类别
     *
     * @return
     * @author mlq
     */
    private List<ZTree<TreePojo>> getMaterialNodes() {
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        treeList.add(new ZTree<TreePojo>("000", "食谱类别", true, "home"));
        FoodTree condition = new FoodTree();
        condition.setTreeKind("food");
        List<TreePojo> menuList = treeService.queryTreeByCondition(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            treeList.addAll(this.getTrees(menuList));
        }
        return treeList;
    }

    /**
     * 获取树
     *
     * @param menuList
     * @return
     */
    protected List<ZTree<TreePojo>> getTrees(List<TreePojo> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        List<ZTree<TreePojo>> treeList = new ArrayList<ZTree<TreePojo>>();
        for (TreePojo productCatalog : menuList) {
            String catalogId = productCatalog.getTreeId();
            ZTree<TreePojo> tree = new ZTree<TreePojo>();
            tree.setId(catalogId);
            tree.setpId(productCatalog.getParentTreeId());
            tree.setName(productCatalog.getTreeName());
            tree.setValue(productCatalog);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                tree.setIsParent(true);
                tree.setIconSkin("mulu");
            } else {
                tree.setIconSkin("menu");
            }
            treeList.add(tree);
            if (CollectionUtils.isNotEmpty(productCatalog.getChildList())) {
                treeList.addAll(this.getTrees(productCatalog.getChildList()));
            }
        }
        return treeList;
    }

    /**
     * 保存膳食处方备注
     *
     * @param diagnosisId contentRsult
     * @return
     * @throws UnsupportedEncodingException
     * @author dhs
     */
    // TODO:董宏生 此处需要确认，膳食处方备注改为没有前提条件就可以添加
    @RequestMapping(value = PlatformMapping.RECEIVE_PLAN_TEXT_ADD)
    public @ResponseBody
    WebResult<Boolean> addDiagnosisDietRemarkText(String diagnosisId, String contentRsult) {
        // 保存膳食处方备注信息
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        PregDiagnosis diagnosis = new PregDiagnosis();
        diagnosis.setDiagnosisId(diagnosisId);
        diagnosis.setDiagnosisDietRemark(contentRsult.trim());
        pregDiagnosisService.updateDiagnosis(diagnosis);
        return webResult.setSuc(true);
    }

    /**
     * 膳食方案
     *
     * @param form
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.RECEIVE_PLAN_ADD)
    public @ResponseBody
    WebResult<String> addIntervenePlan(IntervenePlanGroupForm form) {
        WebResult<String> webResult = new WebResult<String>();
        // 添加干预计划
        @SuppressWarnings("unchecked")
        List<PregPlanIntakeDetail> detailList = NetJsonUtils.jsonArrayToList(form.getDetailList(),
                PregPlanIntakeDetail.class);
        // 膳食方案主表信息
        PregIntervenePlan plan = TransformerUtils.transformerProperties(PregIntervenePlan.class, form);
        webResult.setSuc(pregPlanService.saveIntervenePlanGroup(plan, detailList));
        // 保存膳食处方备注信息
        PregDiagnosis diagnosis = new PregDiagnosis();
        diagnosis.setDiagnosisId(form.getDiagnosisId());
        diagnosis.setDiagnosisDietRemark(form.getDiagnosisDietRemark());
        pregDiagnosisService.updateDiagnosis(diagnosis);

        return webResult;
    }

    /**
     * 保存膳食方案--食谱
     *
     * @param form
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.RECEIVE_PLAN_DIET)
    public @ResponseBody
    WebResult<String> savePlanDiet(IntervenePlanGroupForm form) {
        WebResult<String> webResult = new WebResult<String>();
        // 膳食方案主表信息
        PregIntervenePlan plan = TransformerUtils.transformerProperties(PregIntervenePlan.class, form);
        // 膳食方案食谱
        String planId = pregPlanService.savePlanDiet(plan);

        return webResult.setSuc(planId);
    }

    /**
     * 查询个人膳食模板明细
     *
     * @param intakeId
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.PERSON_INTAKE_DETAIL_QUERY)
    public @ResponseBody
    WebResult<List<PregUserIntakeDetailPojo>> queryPersonIntakeDetail(String intakeId) {
        WebResult<List<PregUserIntakeDetailPojo>> webResult = new WebResult<List<PregUserIntakeDetailPojo>>();
        return webResult.setSuc(pregPlanService.queryUserIntakeDetailByIntakeId(intakeId));
    }

    /**
     * 删除个人膳食模板
     *
     * @param intakeId
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.PERSON_INTAKE_REMOVE)
    public @ResponseBody
    WebResult<Boolean> deletePersonIntake(String intakeId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregPlanService.deletePersonIntake(intakeId);
        return webResult.setSuc(true);
    }

    /**
     * 膳食方案--查询食谱列表
     *
     * @param dietId
     * @return
     * @author zcq
     */
    @RequestMapping(value = PlatformMapping.PLAN_DIETLIST_QUERY)
    public @ResponseBody
    WebResult<List<PregDietTemplateDetailPojo>> queryDietTemplateDetailNamesByDietId(
            String dietId) {
        WebResult<List<PregDietTemplateDetailPojo>> webResult = new WebResult<List<PregDietTemplateDetailPojo>>();
        webResult.setSuc(pregDietTemplateService.queryDietTemplateDetailNamesByDietId(dietId));
        return webResult;
    }

    /**
     * 膳食方案--获取食谱明细
     *
     * @param planId
     * @param foodDay
     * @return
     * @author zcq
     * @date 2018/10/17 12:19
     */
    @RequestMapping(value = PlatformMapping.PLAN_DIETDETAIL_GET)
    public @ResponseBody
    WebResult<Map<String, Map<String, List<PregPlanDietPojo>>>> queryPlanDiet(String planId, String foodDay) {
        WebResult<Map<String, Map<String, List<PregPlanDietPojo>>>> webResult = new WebResult<Map<String, Map<String, List<PregPlanDietPojo>>>>();
        if (StringUtils.isBlank(planId) || StringUtils.isBlank(foodDay)) {
            return null;
        }
        List<PregPlanDietPojo> dietDetailList = pregPlanService.queryPlanDietDetailsByPlanIdAndFoodDay(planId, foodDay);
        webResult.setSuc(getDietTemplateDetailMap(dietDetailList));
        return webResult;
    }

    /**
     * 添加食谱明细
     *
     * @param planDiet
     * @return com.mnt.preg.web.pojo.WebResult<java.lang.Boolean>
     * @author zcq
     * @date 2018/10/17 14:01
     */
    @RequestMapping(value = PlatformMapping.PLAN_DIETDETAIL_ADD)
    public @ResponseBody
    WebResult<Map<String, Map<String, List<PregPlanDietPojo>>>> addPlanDietDetail(PregPlanDiet planDiet) {
        WebResult<Map<String, Map<String, List<PregPlanDietPojo>>>> webResult = new WebResult<Map<String, Map<String, List<PregPlanDietPojo>>>>();

        List<FoodMaterialListInfoPojo> list = foodService.queryFoodMaterialListByFoodId(planDiet.getFoodId());
        // 查询菜谱对应食材(逐条添加)
        if (CollectionUtils.isNotEmpty(list)) {
            for (FoodMaterialListInfoPojo foodMaterialListInfoPojo : list) {
                PregPlanDiet detail = TransformerUtils.transformerProperties(PregPlanDiet.class,planDiet);
                detail.setFmId(foodMaterialListInfoPojo.getFmId());
                detail.setFoodMaterialName(foodMaterialListInfoPojo.getFmName());
                detail.setFoodMaterialAmount("0");
                detail.setFoodMealName(transCode("MEALSTYPE",planDiet.getFoodMeal()));
                detail.setFlag(1);
                pregPlanService.addPlanDietDetail(detail);
            }
        } else {// 添加单一菜品
           return webResult.setError("该食谱没有食材！");
        }

        List<PregPlanDietPojo> dietDetailList = pregPlanService.queryPlanDietDetailsByPlanIdAndFoodDay(planDiet.getPlanId(), planDiet.getFoodDay());
        return webResult.setSuc(getDietTemplateDetailMap(dietDetailList));
    }

    /**
     * 修改食谱明细
     *
     * @param planDiet
     * @return com.mnt.preg.web.pojo.WebResult<java.lang.Boolean>
     * @author zcq
     * @date 2018/10/17 14:02
     */
    @RequestMapping(value = PlatformMapping.PLAN_DIETDETAIL_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updatePlanDietDetail(PregPlanDiet planDiet) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregPlanService.updatePlanDietDetail(planDiet);
        return webResult.setSuc(true);
    }

    /**
     * 删除食谱明细
     *
     * @param planId
     * @param foodMeal
     * @param foodId
     * @return com.mnt.preg.web.pojo.WebResult<java.lang.Boolean>
     * @author zcq
     * @date 2018/10/17 14:58
     */
    @RequestMapping(value = PlatformMapping.PLAN_DIETDETAIL_DELETE)
    public @ResponseBody
    WebResult<Boolean> deletePlanDietDetail(String planId, String foodMeal, String foodId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        pregPlanService.deletePlanDietFood(planId, foodMeal, foodId);
        return webResult.setSuc(true);
    }


    /**
     * 添加个人膳食模板
     *
     * @param intakeForm
     * @return com.mnt.preg.web.pojo.WebResult<java.lang.String>
     * @author zcq
     * @date 2018/10/17 12:19
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = PlatformMapping.ADD_PERSON_INTAKE)
    public @ResponseBody
    WebResult<String> addIntake(IntakeForm intakeForm) {
        WebResult<String> webResult = new WebResult<String>();
        // 主表信息
        PregUserIntake userIntake = TransformerUtils.transformerProperties(PregUserIntake.class, intakeForm);
        // 明细信息
        List<PregUserIntakeDetail> userIntakeDetailList = NetJsonUtils.jsonArrayToList(intakeForm.getDetailsJson(),
                PregUserIntakeDetail.class);
        // 保存
        webResult.setSuc(pregPlanService.saveUserIntake(userIntake, userIntakeDetailList));
        return webResult;
    }

    /**
     * 校验个人模板名称是否重复
     *
     * @param intakeName
     * @param intakeNameOld
     * @param operateType
     * @return
     * @author zcq
     */
    @ResponseBody
    @RequestMapping(value = PlatformMapping.PERSON_INTAKENAME_VALIDATE)
    public boolean validPersonIntakeName(String intakeName, String intakeNameOld, String operateType) {
        boolean flag = true;
        if ("add".equals(operateType)) {
            if (pregPlanService.validatePersonIntakeName(intakeName) > 0) {
                flag = false;
            }
        }
        if ("update".equals(operateType)) {
            if (!intakeName.equals(intakeNameOld) && pregPlanService.validatePersonIntakeName(intakeName) > 0) {
                flag = false;
            }
        }
        return flag;
    }

    // ***************************************【课程预约】**********************************************

    /**
     * 获取预约课程信息
     *
     * @param custId
     * @param model
     * @return
     * @author xdc
     */
    private void getPlanCourseBooking(String custId, Model model) {
        // 获取所有的预约课程基础数据
        PregArchivesPojo archives = pregArchivesService.getLastPregnancyArchive(custId);
        PregCourseBooking condition = new PregCourseBooking();
        condition.setArchivesId(archives.getId());
        List<PregCourseBookingPojo> pregCourseBookList = courseBookingService.queryCourseBooking(condition);
        model.addAttribute("pregCourseBookList", NetJsonUtils.listToJsonArray(pregCourseBookList));
    }

    /**
     * 获取预约课程信息
     *
     * @return
     * @author zcq
     * @date 2018/10/17 12:18
     */
    @RequestMapping(value = PlatformMapping.QUERY_PREG_SCHEDULE_COURSE)
    public @ResponseBody
    WebResult<Map<String, List<SchedulePojo>>> queryPregScheduleCourse() {
        WebResult<Map<String, List<SchedulePojo>>> webResult = new WebResult<Map<String, List<SchedulePojo>>>();
        // 获取所有的预约课程基础数据
        List<SchedulePojo> scheduleList = courseBookingService.queryScheduleByCondition(null);
        Map<String, List<SchedulePojo>> scheduleMap = new HashMap<String, List<SchedulePojo>>();
        for (SchedulePojo schedulePojo : scheduleList) {
            if (scheduleMap.containsKey(schedulePojo.getScheduleWeek())) {
                scheduleMap.get(schedulePojo.getScheduleWeek()).add(schedulePojo);
            } else {
                scheduleMap.put(schedulePojo.getScheduleWeek(), new ArrayList<SchedulePojo>());
                scheduleMap.get(schedulePojo.getScheduleWeek()).add(schedulePojo);
            }
        }
        webResult.setSuc(scheduleMap);
        return webResult;
    }

    /**
     * 保存课程预约信息
     *
     * @param courseList
     * @return com.mnt.preg.web.pojo.WebResult<java.lang.Boolean>
     * @author zcq
     * @date 2018/10/17 12:18
     */
    @RequestMapping(value = PlatformMapping.PREG_COURSE_BOOKING_SAVE)
    public @ResponseBody
    WebResult<Boolean> savePregCourseBooking(String courseList) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<PregCourseBooking> bookingList = gson.fromJson(courseList,
                new TypeToken<List<PregCourseBooking>>() {
                }.getType());
        int index = 0;// 计数器
        for (PregCourseBooking courseBooking : bookingList) {
            if (courseBookingService.queryCourseBooking(courseBooking).size() <= 0) {
                courseBookingService.addCourseBooking(courseBooking);
                index++;
            }
        }
        PregCourseBooking condition = new PregCourseBooking();
        condition.setArchivesId(bookingList.get(0).getArchivesId());
        webResult.setData(courseBookingService.queryCourseBooking(condition));
        String message = "成功添加" + index + "个预约课程";
        if (index == 0) {
            message = "课程已经存在，不能重复添加";
        }
        webResult.setSuc(true, message);
        return webResult;
    }

    /**
     * 删除预约课程
     *
     * @param id
     * @return com.mnt.preg.web.pojo.WebResult<java.lang.Boolean>
     * @author zcq
     * @date 2018/10/17 12:17
     */
    @RequestMapping(value = PlatformMapping.PREG_COURSE_BOOKING_DEL)
    public @ResponseBody
    WebResult<Boolean> delPregCourseBooking(String id) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        courseBookingService.deleteCourseBookingById(id);
        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 查询课程反馈
     *
     * @param id
     * @return
     * @author xdc
     */
    @RequestMapping(value = PlatformMapping.PREG_BOOKING_FEEDBACK_QUERY)
    public @ResponseBody
    WebResult<PregCourseFeedbackPojo> queryPregBookingFeedback(String id) {
        WebResult<PregCourseFeedbackPojo> webResult = new WebResult<PregCourseFeedbackPojo>();
        PregCourseFeedback condition = new PregCourseFeedback();
        condition.setBookingId(id);
        List<PregCourseFeedbackPojo> feedback = courseFeebackService.queryCourseFeedback(condition);
        if (CollectionUtils.isNotEmpty(feedback)) {
            webResult.setValue(feedback.get(0));
        }
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 添加课程反馈
     *
     * @param courseFeedback
     * @return com.mnt.preg.web.pojo.WebResult<com.mnt.preg.customer.pojo.PregCourseFeedbackPojo>
     * @author zcq
     * @date 2018/10/17 12:00
     */
    @RequestMapping(value = PlatformMapping.PREG_BOOKING_FEEDBACK_ADD)
    public @ResponseBody
    WebResult<PregCourseFeedbackPojo> addPregBookingFeedback(PregCourseFeedback courseFeedback) {
        WebResult<PregCourseFeedbackPojo> webResult = new WebResult<PregCourseFeedbackPojo>();
        courseFeebackService.addCourseFeedback(courseFeedback);
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 更新课程反馈
     *
     * @param courseFeedback
     * @return com.mnt.preg.web.pojo.WebResult<com.mnt.preg.customer.pojo.PregCourseFeedbackPojo>
     * @author zcq
     * @date 2018/10/17 11:59
     */
    @RequestMapping(value = PlatformMapping.PREG_COURSE_BOOKING_UPDATE)
    public @ResponseBody
    WebResult<PregCourseFeedbackPojo> updatePregBookingFeedback(PregCourseFeedback courseFeedback) {
        WebResult<PregCourseFeedbackPojo> webResult = new WebResult<PregCourseFeedbackPojo>();
        courseFeebackService.updateCourseFeedback(courseFeedback);
        webResult.setResult(true);
        return webResult;
    }

    // ***************************************【复诊预约】**********************************************

    /**
     * 医嘱页面——复查预约嵌入页
     *
     * @param diagnosisId
     * @param custId
     * @param model
     * @return
     * @author dhs
     */
    private void getPlanBookingPageYiZhu(String diagnosisId, String custId, Model model) {
        DiagnosisBookingCondition condition = new DiagnosisBookingCondition();
        condition.setDiagnosisId(diagnosisId);
        List<DiagnosisBookingPojo> diagnosisBookingList = pregDiagnosisService.queryDiagnosisBookings(condition);
        String custName = customerService.getCustomer(custId).getCustName();
        model.addAttribute("custName", custName);
        model.addAttribute("diagnosisBookingList", NetJsonUtils.listToJsonArray(diagnosisBookingList));
    }

    // ***************************************【结束】**********************************************

    /**
     * 计算推荐热量
     *
     * @param custHeight
     * @return
     * @author zcq
     */
    private String getSuggestIntakeEnergy(BigDecimal custHeight, String pregStage) {
        int energy = custHeight.multiply(custHeight).multiply(BigDecimal.valueOf(21.9 * 30))
                .divide(BigDecimal.valueOf(10000), BigDecimal.ROUND_HALF_UP).intValue();
        if ("pregnancy_pre".equals(pregStage)) {

        } else if ("pregnancy_mid".equals(pregStage)) {
            energy = energy + 300;
        } else if ("pregnancy_late".equals(pregStage)) {
            energy = energy + 450;
        }

        String result = "";
        if (energy <= 1200) {
            result = "1000~1200";
        } else if (energy > 1200 && energy <= 1400) {
            result = "1200~1400";
        } else if (energy > 1400 && energy <= 1600) {
            result = "1400~1600";
        } else if (energy > 1600 && energy <= 1800) {
            result = "1600~1800";
        } else if (energy > 1800 && energy <= 2000) {
            result = "1800~2000";
        } else if (energy > 2000 && energy <= 2200) {
            result = "2000~2200";
        } else if (energy > 2200 && energy <= 2400) {
            result = "2200~2400";
        } else if (energy > 2400 && energy <= 2600) {
            result = "2400~2600";
        } else if (energy > 2600 && energy <= 2800) {
            result = "2600~2800";
        } else if (energy > 2800 && energy <= 3000) {
            result = "2800~3000";
        } else if (energy > 3000 && energy <= 3200) {
            result = "3000~3200";
        } else if (energy > 3200 && energy <= 3400) {
            result = "3200~3400";
        } else if (energy > 3400) {
            result = "3400~3600";
        }
        return result;
    }

    /**
     * 设置食谱明细
     *
     * @param dietList
     * @return
     * @author zcq
     */
    private Map<String, Map<String, List<PregPlanDietPojo>>> getDietTemplateDetailMap(List<PregPlanDietPojo> dietList) {
        Map<String, Map<String, List<PregPlanDietPojo>>> map = new LinkedHashMap<String, Map<String, List<PregPlanDietPojo>>>();
        for (PregPlanDietPojo detail : dietList) {
            String mealType = detail.getFoodMealName();
            if (StringUtils.isEmpty(mealType)) {
                mealType = "其他";// 特殊处理，其它：一天中 调味品，豆油 用量
            }
            if (!map.containsKey(mealType)) {
                map.put(mealType, new LinkedHashMap<String, List<PregPlanDietPojo>>());
            }
            String foodName = detail.getFoodName();
            if (!map.get(mealType).containsKey(foodName)) {
                map.get(mealType).put(foodName, new ArrayList<PregPlanDietPojo>());
            }
            map.get(mealType).get(foodName).add(detail);
        }
        return map;
    }
}
