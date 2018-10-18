
package com.mnt.preg.master.controller.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.master.condition.items.DiseaseNutrientCondition;
import com.mnt.preg.master.condition.items.HospitalInspectPayCondition;
import com.mnt.preg.master.condition.items.InterveneDiseaseCondition;
import com.mnt.preg.master.condition.items.MasInterveneDiseaseInspectCondition;
import com.mnt.preg.master.condition.items.MasPrescriptionCondition;
import com.mnt.preg.master.entity.basic.Element;
import com.mnt.preg.master.entity.items.DiseaseNutrient;
import com.mnt.preg.master.entity.items.InterveneDiaeaseInsepectPay;
import com.mnt.preg.master.form.items.InterveneDiseaseForm;
import com.mnt.preg.master.mapping.basic.MasterPageName;
import com.mnt.preg.master.mapping.items.ItemsMapping;
import com.mnt.preg.master.pojo.basic.ElementPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.master.pojo.items.DiseaseNutrientPojo;
import com.mnt.preg.master.pojo.items.HospitalInspectPayPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.service.basic.ProductService;
import com.mnt.preg.master.service.items.HospitalInspectPayService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.platform.entity.DiseasePrescription;
import com.mnt.preg.platform.pojo.DiseasePrescriptionPojo;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;
import com.mnt.preg.web.pojo.ZTree;

/**
 * 干预重点Controller
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-3-11 gss 初版
 */
@Controller
public class InterveneDiseaseController extends BaseController {

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private ItemService itemService;

    @Resource
    private ProductService productService;

    @Resource
    private HospitalInspectPayService hospitalInspectPayService;

    // -----------------------------------------干预重点管理------------------------------------

    /**
     * 干预疾病编码验证
     * 
     * @author xdc
     * @param diseaseId
     * @param diseaseOldId
     * @param type
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASECODE_VALID)
    public @ResponseBody
    boolean diseaseCodeValid(String diseaseCode, String diseaseOldCode, String type) {
        boolean typeIndex = "add".equals(type);
        if (!typeIndex && diseaseCode.equals(diseaseOldCode)) {
            return true;
        }
        int index = interveneDiseaseService.checkDiseaseCodeValid(diseaseCode);
        if (index < 1) {
            return true;
        }
        return false;
    }

    /**
     * 干预疾病编码验证
     * 
     * @author scd
     * @param diseaseId
     * @param diseaseOldId
     * @param type
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASENAME_VALID)
    public @ResponseBody
    boolean diseaseNameValid(String diseaseName, String diseaseOldName, String type) {
        boolean typeIndex = "add".equals(type);
        if (!typeIndex && diseaseName.equals(diseaseOldName)) {
            return true;
        }
        int index = interveneDiseaseService.checkDiseaseNameValid(diseaseName);
        if (index < 1) {
            return true;
        }
        return false;
    }

    /**
     * 添加干预重点信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = ItemsMapping.ADD_INTERVENEDISEASE)
    public @ResponseBody
    WebResult<InterveneDiseasePojo> addInterveneDisease(InterveneDiseaseForm form) {
        WebResult<InterveneDiseasePojo> webResult = new WebResult<InterveneDiseasePojo>();
        try {
            InterveneDiseasePojo interveneDiseaseDto = TransformerUtils.transformerProperties(
                    InterveneDiseasePojo.class, form);
            String diseaseCode = interveneDiseaseService.getMaxDiseaseCode();
            interveneDiseaseDto.setDiseaseCode(diseaseCode);
            String diseaseId = interveneDiseaseService.saveInterveneDisease(interveneDiseaseDto);
            webResult.setSuc(interveneDiseaseService.getInterveneDiseaseById(diseaseId));
        } catch (Exception e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 修改干预重点信息
     * 
     * @author gss
     * @param form
     * @return
     */
    @RequestMapping(value = ItemsMapping.UPDATE_INTERVENEDISEASE)
    public @ResponseBody
    WebResult<InterveneDiseasePojo> updateInterveneDisease(InterveneDiseaseForm form) {
        WebResult<InterveneDiseasePojo> webResult = new WebResult<InterveneDiseasePojo>();
        try {
            InterveneDiseasePojo interveneDiseaseDto = TransformerUtils.transformerProperties(
                    InterveneDiseasePojo.class,
                    form);
            interveneDiseaseService.updateInterveneDisease(interveneDiseaseDto, form.getDiseaseId());
            webResult.setSuc(interveneDiseaseService.getInterveneDiseaseById(form.getDiseaseId()));
        } catch (Exception e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 
     * 删除干预重点信息
     * 
     * @author gss
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.REMOVE_INTERVENEDISEASE)
    public @ResponseBody
    WebResult<Boolean> removeInterveneDisease(String diseaseId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        try {
            interveneDiseaseService.removeInterveneDisease(diseaseId);
            webResult.setSuc(true, "成功");
        } catch (Exception e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 检索干预重点信息
     * 
     * @author gss
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.QUERY_INTERVENEDISEASE)
    public @ResponseBody
    WebResult<InterveneDiseasePojo> queryCode(InterveneDiseaseCondition condition) {
        WebResult<InterveneDiseasePojo> webResult = new WebResult<InterveneDiseasePojo>();
        try {
            webResult.setData(interveneDiseaseService.queryInterveneDisease(condition));
        } catch (Exception e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 
     * 验证干预疾病code重复
     * 
     * @author gss
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.INTERVENEDISEASE_CODE_VALIDATE)
    public @ResponseBody
    boolean queryInterveneDiseaseByCode(String name) {
        InterveneDiseasePojo interveneDiseaseDto = interveneDiseaseService.getInterveneDiseaseById(name);
        if (interveneDiseaseDto == null) {
            return true;
        } else {
            return false;
        }
    }

    // ----------------------------------------- 诊断项目配置------------------------------------

    /**
     * 获取干预疾病树
     * 
     * @author xdc
     * @return
     */
    private List<ZTree<InterveneDiseasePojo>> getInterveneDiseaseTreeList() {
        List<ZTree<InterveneDiseasePojo>> treeList = new ArrayList<ZTree<InterveneDiseasePojo>>();
        treeList.add(new ZTree<InterveneDiseasePojo>("0000", "诊断项目", true, "home", true));
        List<InterveneDiseasePojo> interveneList = interveneDiseaseService.queryInterveneDisease(null);
        if (CollectionUtils.isNotEmpty(interveneList)) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (InterveneDiseasePojo intervene : interveneList) {
                String type = intervene.getDiseaseType();
                if (!map.containsKey(type) && type != null && !type.equals("")) {
                    ZTree<InterveneDiseasePojo> tree = new ZTree<InterveneDiseasePojo>();
                    tree.setId(type);
                    tree.setpId("0000");
                    tree.setName(transCode("diseaseType", type));
                    tree.setValue(intervene);
                    tree.setIconSkin("mulu");
                    treeList.add(tree);
                    map.put(type, 1);
                }
                if (type == null || type.equals("")) {
                    type = "0000";
                }
                ZTree<InterveneDiseasePojo> tree = new ZTree<InterveneDiseasePojo>();
                tree.setId(intervene.getDiseaseCode());
                tree.setpId(type);
                tree.setName(intervene.getDiseaseName());
                tree.setValue(intervene);
                tree.setIconSkin("dept");
                treeList.add(tree);
            }
        }
        return treeList;
    }

    /************************************************** 诊断营养处方配置 *************************************************************/
    /**
     * 跳转到出诊疾病配置页面
     * 
     * @author zcq
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_PRESCRIPTION_VIEW)
    public String toDiseasePrescription(Model model) {
        // 获取诊断项目
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getInterveneDiseaseTreeList()));
        // 获取产品类别树
        List<ProductPojo> catalogList = productService.queryProduct(null);
        model.addAttribute("catalogList", NetJsonUtils.listToJsonArray(catalogList));
        return MasterPageName.DISEASE_PRESCRIPTION_VIEW;
    }

    /**
     * 
     * 添加营养处方
     * 
     * @param productId
     * @param diseaseId
     * @return
     */
    @RequestMapping(value = ItemsMapping.ADD_PRESCRIPTION)
    public @ResponseBody
    WebResult<DiseasePrescriptionPojo> addDiseasePrescription(String productId, String diseaseId) {
        WebResult<DiseasePrescriptionPojo> webResult = new WebResult<DiseasePrescriptionPojo>();
        ProductPojo product = productService.getProductById(productId);
        DiseasePrescription Prescription = TransformerUtils.transformerProperties(DiseasePrescription.class,
                product);
        Prescription.setDiseaseId(diseaseId);
        String prescriptionId = interveneDiseaseService.addPrescription(Prescription);
        DiseasePrescriptionPojo prescriptionPojo = interveneDiseaseService.getPrescription(prescriptionId);
        this.setProductNameByRule(prescriptionPojo);
        webResult.setSuc(prescriptionPojo);
        return webResult;
    }

    /**
     * 
     * 检索营养处方
     * 
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.QUERY_PRESCRIPTION)
    public @ResponseBody
    WebResult<DiseasePrescriptionPojo> queryDiseasePrescription(MasPrescriptionCondition condition) {
        WebResult<DiseasePrescriptionPojo> webResult = new WebResult<DiseasePrescriptionPojo>();
        try {
            List<DiseasePrescriptionPojo> list = interveneDiseaseService.queryMasPrescription(condition);
            for (DiseasePrescriptionPojo mas : list) {
                this.setProductNameByRule(mas);
            }
            webResult.setData(list);
        } catch (Exception e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 删除营养处方
     * 
     * @param prescriptionId
     * @return
     */
    @RequestMapping(value = ItemsMapping.DELETE_PRESCRIPTION)
    public @ResponseBody
    WebResult<String> removeDiseasePrescription(String prescriptionId) {
        WebResult<String> webResult = new WebResult<String>();
        interveneDiseaseService.deletePrescription(prescriptionId);
        webResult.setSuc(prescriptionId);
        return webResult;
    }

    /**
     * 根据规则设置productName
     * 优先级：产品名称>(商品名称==通用名称)
     * 
     * @author dhs
     * @param DiseasePrescriptionPojo
     */
    private void setProductNameByRule(DiseasePrescriptionPojo mas) {
        if (mas.getProductName().isEmpty() && !mas.getProductGoodsName().isEmpty()
                && !mas.getProductCommonName().isEmpty()) {
            mas.setProductName(mas.getProductGoodsName() + " " + mas.getProductCommonName());
        } else if (mas.getProductName().isEmpty() && !mas.getProductGoodsName().isEmpty()) {
            mas.setProductName(mas.getProductGoodsName());
        } else if (mas.getProductName().isEmpty() && !mas.getProductCommonName().isEmpty()) {
            mas.setProductName(mas.getProductCommonName());
        }
    }

    /************************************************** 诊断辅助检查配置 *************************************************************/
    /**
     * 跳转到诊断项目配置页面
     * 
     * @author scd
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_INSPECT_VIEW)
    public String toDiseaseInspect(Model model) {
        // 获取诊断项目
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getInterveneDiseaseTreeList()));
        // 获取机构检查项目
        HospitalInspectPayCondition condition = new HospitalInspectPayCondition();
        List<HospitalInspectPayPojo> inspects = hospitalInspectPayService
                .queryInspectPayByCondition(condition);
        model.addAttribute("inspects", NetJsonUtils.listToJsonArray(inspects));
        return MasterPageName.DISEASE_INSPECT_VIEW;
    }

    /**
     * 
     * 检索辅助检查项目
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.QUERY_INSPECT)
    public @ResponseBody
    WebResult<HospitalInspectPayPojo> queryDiseaseInspect(MasInterveneDiseaseInspectCondition condition) {
        WebResult<HospitalInspectPayPojo> webResult = new WebResult<HospitalInspectPayPojo>();
        List<HospitalInspectPayPojo> list = interveneDiseaseService.queryInspectConfig(condition);
        webResult.setData(list);
        return webResult;
    }

    /**
     * 添加辅助检查项目关联的指标
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.ADD_INSPECT)
    public @ResponseBody
    WebResult<HospitalInspectPayPojo> addInspectItemConfig(InterveneDiaeaseInsepectPay config, Model model) {
        WebResult<HospitalInspectPayPojo> webResult = new WebResult<HospitalInspectPayPojo>();
        String id = interveneDiseaseService.addInspectConfig(config);
        HospitalInspectPayPojo inspect = interveneDiseaseService.getInspectConfigById(id);
        webResult.setSuc(inspect);
        return webResult;
    }

    /**
     * 删除营养处方
     * 
     * @param prescriptionId
     * @return
     */
    @RequestMapping(value = ItemsMapping.DELETE_INSPECT)
    public @ResponseBody
    WebResult<String> removeDiseaseInspect(String id) {
        WebResult<String> webResult = new WebResult<String>();
        interveneDiseaseService.deleteInspect(id);
        webResult.setSuc(id);
        return webResult;
    }

    /************************************************** 诊断元素配置 *************************************************************/
    /**
     * 跳转到诊断项目配置页面
     * 
     * @author scd
     * @return
     */
    @RequestMapping(value = ItemsMapping.DISEASE_NUTRIENT_VIEW)
    public String toDiseaseNutrient(Model model) {
        // 获取诊断项目
        model.addAttribute("treeNodes", NetJsonUtils.listToJsonArray(getInterveneDiseaseTreeList()));
        // 获取元素
        Element condition = new Element();
        List<ElementPojo> elements = elementService.queryNutrient(condition);
        model.addAttribute("elements", NetJsonUtils.listToJsonArray(elements));
        return MasterPageName.DISEASE_NUTRIENT_VIEW;
    }

    /**
     * 
     * 检索疾病关联的元素
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.QUERY_NUTRIENT)
    public @ResponseBody
    WebResult<DiseaseNutrientPojo> queryDiseaseNutrient(DiseaseNutrientCondition condition) {
        WebResult<DiseaseNutrientPojo> webResult = new WebResult<DiseaseNutrientPojo>();
        webResult.setData(interveneDiseaseService.queryDiseaseNutrient(condition));
        return webResult;
    }

    /**
     * 添加疾病关联的元素
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = ItemsMapping.ADD_NUTRIENT)
    public @ResponseBody
    WebResult<DiseaseNutrientPojo> addDiseaseNutrientConfig(DiseaseNutrient config, Model model) {
        WebResult<DiseaseNutrientPojo> webResult = new WebResult<DiseaseNutrientPojo>();
        String id = interveneDiseaseService.addDiseaseNutrientConfig(config);
        DiseaseNutrientPojo nutrient = interveneDiseaseService.getNutrientConfigById(id);
        webResult.setSuc(nutrient);
        return webResult;
    }

    /**
     * 删除疾病关联的元素
     * 
     * @author scd
     * @param id
     * @return
     */
    @RequestMapping(value = ItemsMapping.DELETE_NUTRIENT)
    public @ResponseBody
    WebResult<String> removeDiseaseNutrient(String id) {
        WebResult<String> webResult = new WebResult<String>();
        DiseaseNutrientPojo nutrient = interveneDiseaseService.getNutrientConfigById(id);
        interveneDiseaseService.deleteNutrient(id);
        webResult.setSuc(nutrient.getNutrientId());
        return webResult;
    }
}
