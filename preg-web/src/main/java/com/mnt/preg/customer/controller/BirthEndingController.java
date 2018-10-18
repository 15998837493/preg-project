
package com.mnt.preg.customer.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.JacksonUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.preg.customer.condition.BirthEndingCondition;
import com.mnt.preg.customer.entity.BirthEnding;
import com.mnt.preg.customer.entity.BirthEndingBabyInfo;
import com.mnt.preg.customer.entity.BirthEndingDischarged;
import com.mnt.preg.customer.form.BirthEndingBabyInfoForm;
import com.mnt.preg.customer.form.BirthEndingBaseInfoForm;
import com.mnt.preg.customer.form.BirthEndingDischargedForm;
import com.mnt.preg.customer.form.BirthEndingForm;
import com.mnt.preg.customer.mapping.BirthEndingMapping;
import com.mnt.preg.customer.mapping.BirthEndingPageName;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;
import com.mnt.preg.customer.pojo.BirthEndingBaseInfoPojo;
import com.mnt.preg.customer.pojo.BirthEndingDischargedPojo;
import com.mnt.preg.customer.pojo.BirthEndingPojo;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.main.constant.MessageConstant;
import com.mnt.preg.master.condition.items.HospitalCondition;
import com.mnt.preg.master.pojo.items.HospitalPojo;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.service.items.HospitalService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.platform.util.FoodsFormulaUtil;
import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.pojo.InstitutionPojo;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 分娩结局管理Controller
 * 
 * @author wjc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-01 wjc 初版
 */
@Controller
public class BirthEndingController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BirthEndingController.class);

    @Resource
    private InterveneDiseaseService interveneDiseaseService;

    @Resource
    private HospitalService hospitalService;

    /**
     * 
     * 分娩结局录入页面
     * 
     * @author scd
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_ADD)
    public String birtchendingAdd(BirthEndingCondition birthCondition, Model model) {
        LOGGER.info("birtchendingAdd custId : " + birthCondition.getCustId());
        CustomerPojo customerPojo = customerService.getCustomer(birthCondition.getCustId());
        List<PregArchivesPojo> archivesList = pregArchivesService.queryCustomerPregRecprd(birthCondition.getCustId());
        List<BirthEndingPojo> birthEndingList = birthEndingService.queryBirthEndingList(birthCondition);
        List<InterveneDiseasePojo> diseaseList = interveneDiseaseService.queryInterveneDisease(null);

        CodeInfo code = new CodeInfo();
        code.setParentCodeValue("BIRTHDIRECTION");
        List<CodePojo> codeList = codeService.queryCodeView(code);

        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        InstitutionPojo insPojo = institutionService.getIns(insId);
        List<HospitalPojo> hospitalList = hospitalService.queryHospital(new HospitalCondition());
        // 获取所有疾病
        Map<String, InterveneDiseasePojo> dMap = new HashMap<String, InterveneDiseasePojo>();
        if (CollectionUtils.isNotEmpty(diseaseList)) {
            for (InterveneDiseasePojo pojo : diseaseList) {
                dMap.put(pojo.getDiseaseId(), pojo);
            }
        }
        // 删除已选的建档记录
        for (BirthEndingPojo birthEnding : birthEndingList) {
            for (PregArchivesPojo archives : archivesList) {
                if (archives.getId().equals(birthEnding.getArchivesId())) {
                    archivesList.remove(archives);
                    break;
                }
            }
        }
        // 获取所有医院
        Map<String, HospitalPojo> hMap = new HashMap<String, HospitalPojo>();
        if (CollectionUtils.isNotEmpty(hospitalList)) {
            for (HospitalPojo pojo : hospitalList) {
                hMap.put(pojo.getHospitalId(), pojo);
            }
        }

        // 分娩方位字典
        Map<String, CodePojo> bdMap = new HashMap<String, CodePojo>();
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo pojo : codeList) {
                bdMap.put(pojo.getCodeId(), pojo);
            }
        }

        model.addAttribute("insPojo", NetJsonUtils.objectToJson(insPojo));
        model.addAttribute("hospitalList", NetJsonUtils.objectToJson(hMap));
        model.addAttribute("codeList", NetJsonUtils.objectToJson(bdMap));
        model.addAttribute("birthEndingPojo", CollectionUtils.isNotEmpty(birthEndingList) ? birthEndingList : null);
        model.addAttribute("archivesList", archivesList);
        model.addAttribute("customerPojo", customerPojo);
        model.addAttribute("diseaseList", NetJsonUtils.objectToJson(dMap));
        return BirthEndingPageName.PAGE_BIRTCHENDING_ADD;
    }

    /**
     * 
     * 分娩结局查看页面
     * 
     * @author scd
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_DETAIL)
    public String birtchendingDetail(BirthEndingCondition birthCondition, Model model) {
        LOGGER.info("birtchendingDetail custId : " + birthCondition.getCustId());
        CustomerPojo customerPojo = customerService.getCustomer(birthCondition.getCustId());
        BirthEndingPojo birthEndingPojo = birthEndingService.getBirthEndingById(birthCondition.getBirthId());
        BirthEndingBaseInfoPojo birthBasePojo = birthBaseService.getBaseByBirthId(birthCondition.getBirthId());
        List<BirthEndingBabyInfoPojo> babyList = birthBabyService.getBabyListByBirthId(birthCondition.getBirthId());
        List<BirthEndingDischargedPojo> dischargedList = dischargedService.getDischargedByBirthId(birthCondition.getBirthId());
        List<InterveneDiseasePojo> diseaseList = interveneDiseaseService.queryInterveneDisease(null);

        for (InterveneDiseasePojo disease : diseaseList) {
            // 入院诊断
            if (birthEndingPojo != null && StringUtils.isNotBlank(birthEndingPojo.getBirthDiagnosis())
                    && birthEndingPojo.getBirthDiagnosis().indexOf(disease.getDiseaseId()) != -1) {
                birthEndingPojo.setBirthDiagnosis(
                        birthEndingPojo.getBirthDiagnosis().replace(disease.getDiseaseId(), disease.getDiseaseName()));
            }
            // 产前合并症
            if (birthBasePojo != null && StringUtils.isNotBlank(birthBasePojo.getBaseComplicationPrenatal())
                    && birthBasePojo.getBaseComplicationPrenatal().indexOf(disease.getDiseaseId()) != -1) {
                birthBasePojo.setBaseComplicationPrenatal(
                        birthBasePojo.getBaseComplicationPrenatal().replace(disease.getDiseaseId(), disease.getDiseaseName()));
            }
            // 产时并发症
            if (birthBasePojo != null && StringUtils.isNotBlank(birthBasePojo.getBaseComplicationBirthing())
                    && birthBasePojo.getBaseComplicationBirthing().indexOf(disease.getDiseaseId()) != -1) {
                birthBasePojo.setBaseComplicationBirthing(
                        birthBasePojo.getBaseComplicationBirthing().replace(disease.getDiseaseId(), disease.getDiseaseName()));
            }
            // 产后并发症
            if (birthBasePojo != null && StringUtils.isNotBlank(birthBasePojo.getBaseComplicationAfterBirthing())
                    && birthBasePojo.getBaseComplicationAfterBirthing().indexOf(disease.getDiseaseId()) != -1) {
                birthBasePojo.setBaseComplicationAfterBirthing(birthBasePojo.getBaseComplicationAfterBirthing()
                        .replace(disease.getDiseaseId(), disease.getDiseaseName()));
            }
            // 内科合并症
            if (birthBasePojo != null && StringUtils.isNotBlank(birthBasePojo.getBaseComplicationsMedical())
                    && birthBasePojo.getBaseComplicationsMedical().indexOf(disease.getDiseaseId()) != -1) {
                birthBasePojo.setBaseComplicationsMedical(
                        birthBasePojo.getBaseComplicationsMedical().replace(disease.getDiseaseId(), disease.getDiseaseName()));
            }
            // 新生儿：并发症
            for (BirthEndingBabyInfoPojo baby : babyList) {
                if (baby != null && StringUtils.isNotBlank(baby.getBabyComplication())
                        && baby.getBabyComplication().indexOf(disease.getDiseaseId()) != -1) {
                    baby.setBabyComplication(
                            baby.getBabyComplication().replace(disease.getDiseaseId(), disease.getDiseaseName()));
                }
            }
            for (BirthEndingDischargedPojo dischargedPojo : dischargedList) {
                // 出院诊断_母亲
                if (dischargedPojo != null && StringUtils.isNotBlank(dischargedPojo.getDisMotherDisagnosis())
                        && dischargedPojo.getDisMotherDisagnosis().indexOf(disease.getDiseaseId()) != -1) {
                    dischargedPojo.setDisMotherDisagnosis(
                            dischargedPojo.getDisMotherDisagnosis().replace(disease.getDiseaseId(), disease.getDiseaseName()));
                }
                // 出院诊断_新生儿
                if (dischargedPojo != null && StringUtils.isNotBlank(dischargedPojo.getDisBabyDiagnosis())
                        && dischargedPojo.getDisBabyDiagnosis().indexOf(disease.getDiseaseId()) != -1) {
                    dischargedPojo.setDisBabyDiagnosis(
                            dischargedPojo.getDisBabyDiagnosis().replace(disease.getDiseaseId(), disease.getDiseaseName()));
                }
            }
        }

        model.addAttribute("customerPojo", customerPojo);
        model.addAttribute("birthEndingPojo", birthEndingPojo);
        model.addAttribute("birthBasePojo", birthBasePojo);
        model.addAttribute("birthBabyList", babyList);
        model.addAttribute("dischargedList", dischargedList);
        return BirthEndingPageName.PAGE_BIRTCHENDING_DETAIL;
    }

    /**
     * 
     * 分娩结局 编辑
     * 
     * @author scd
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_GET_DETAIL_BYBIRTHID)
    public @ResponseBody
    WebResult<JSONObject> getBirthendingDetail(@NotNull String birthId) {
        LOGGER.info("getBirthendingDetail birthId : " + birthId);
        BirthEndingPojo birthEndingPojo = birthEndingService.getBirthEndingById(birthId);
        BirthEndingBaseInfoPojo birthBasePojo = birthBaseService.getBaseByBirthId(birthId);
        List<BirthEndingBabyInfoPojo> babyList = birthBabyService.getBabyListByBirthId(birthId);
        List<BirthEndingDischargedPojo> dischargedList = dischargedService.getDischargedByBirthId(birthId);
        JSONObject json = new JSONObject();
        WebResult<JSONObject> webResult = new WebResult<JSONObject>();

        json.put("birthEndingPojo", NetJsonUtils.objectToJson(birthEndingPojo));
        json.put("birthBasePojo", birthBasePojo == null ? null : JacksonUtils.obj2jsonIgnoreNull(birthBasePojo));
        json.put("birthBabyList", JacksonUtils.obj2jsonIgnoreNull(babyList));
        json.put("dischargedList", JacksonUtils.obj2jsonIgnoreNull(dischargedList));
        webResult.setSuc(json);

        return webResult;
    }

    // /**
    // *
    // * 分娩结局保存
    // *
    // * @author scd
    // * @param id
    // * @param model
    // * @return
    // */
    // @RequestMapping(value = BirthEndingMapping.BIRTHENDING_SAVE)
    // @ResponseBody
    // public WebResult<BirthEndingPojo> birthEndingSave(BirthEndingForm birthForm, Model model) {
    // WebResult<BirthEndingPojo> webResult = new WebResult<BirthEndingPojo>();
    // BirthEnding birthEnding = TransformerUtils.transformerProperties(BirthEnding.class, birthForm);
    // CustomerPojo customerPojo = customerService.getCustomer(birthForm.getCustId());
    // String insId = this.getLoginUser().getUserPojo().getCreateInsId();
    // InstitutionPojo insPojo = institutionService.getIns(insId);
    //
    // if (StringUtils.isBlank(birthEnding.getCreateUserName())) {
    // birthEnding.setCreateUserName(this.getLoginUser().getUserPojo().getUserName());
    // }
    // if (StringUtils.isBlank(birthEnding.getCustName())) {
    // birthEnding.setCustName(customerPojo.getCustName());
    // }
    // if (birthEnding.getBirthWeight() != null && birthEnding.getBirthHeight() != null) {
    // Double bmi = FoodsFormulaUtil.getBmi(birthEnding.getBirthWeight().doubleValue(), birthEnding.getBirthHeight()
    // .doubleValue());
    // birthEnding.setCustBmi(new BigDecimal(bmi));
    // }
    //
    // if (birthEnding.getBirthIsThisHospital() != null && birthEnding.getBirthIsThisHospital() == 1) {
    // birthEnding.setBirthHospital(insPojo.getInsName());
    // }
    // if (birthEnding.getBirthIsPregHospital() != null && birthEnding.getBirthIsPregHospital() == 1) {
    // birthEnding.setBirthPregHospital(insPojo.getInsName());
    // }
    // birthEnding.setCreateTime(new Date());
    // BirthEndingPojo birthEndingPojo = birthEndingService.addBirthEnding(birthEnding);
    // webResult.setSuc(birthEndingPojo);
    // return webResult;
    //
    // }

    /**
     * 分娩结局保存-重构
     * 
     * @param custId
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_SAVE)
    @ResponseBody
    public WebResult<BirthEndingPojo> birthEndingSave(String custId) {
        WebResult<BirthEndingPojo> webResult = new WebResult<BirthEndingPojo>();
        BirthEndingPojo birthEndingPojo = birthEndingService.addBirthEnding(custId);
        webResult.setSuc(birthEndingPojo);
        return webResult;
    }

    /**
     * 
     * 分娩结局更新
     * 
     * @param birthCondition
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_UPDATE)
    public @ResponseBody
    WebResult<BirthEndingPojo> updateBirthEnding(BirthEndingForm birthForm) {
        WebResult<BirthEndingPojo> webResult = new WebResult<BirthEndingPojo>();
        BirthEnding birthEnding = TransformerUtils.transformerProperties(BirthEnding.class, birthForm);
        String insId = this.getLoginUser().getUserPojo().getCreateInsId();
        InstitutionPojo insPojo = institutionService.getIns(insId);

        if (StringUtils.isBlank(birthEnding.getCreateUserName())) {
            birthEnding.setCreateUserName(this.getLoginUser().getUserPojo().getUserName());
        }
        if (birthEnding.getBirthIsThisHospital() != null && birthEnding.getBirthIsThisHospital() == 1) {
            birthEnding.setBirthHospital(insPojo.getInsName());
        }
        if (birthEnding.getBirthIsPregHospital() != null && birthEnding.getBirthIsPregHospital() == 1) {
            birthEnding.setBirthPregHospital(insPojo.getInsName());
        }
        if (birthEnding.getBirthWeight() != null && birthEnding.getBirthHeight() != null) {
            Double bmi = FoodsFormulaUtil.getBmi(birthEnding.getBirthWeight().doubleValue(), birthEnding.getBirthHeight()
                    .doubleValue());
            birthEnding.setCustBmi(new BigDecimal(bmi));
        }
        BirthEndingPojo birthEndingPojo = birthEndingService.updateBirthEnding(birthEnding);
        webResult.setSuc(birthEndingPojo);
        return webResult;
    }

    /**
     * 
     * 分娩结局通过birthId删除分娩记录
     * 
     * @author scd
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_DELETE)
    @ResponseBody
    public WebResult<BirthEndingPojo> birthEndingDelete(String birthId) {
        WebResult<BirthEndingPojo> webResult = new WebResult<BirthEndingPojo>();
        birthEndingService.deleteBirthEnding(birthId);
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 
     * 分娩结局通过custId获取分娩记录
     * 
     * @author scd
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_GETLIST)
    @ResponseBody
    public WebResult<BirthEndingPojo> birthEndingSave(BirthEndingCondition birth) {
        WebResult<BirthEndingPojo> webResult = new WebResult<BirthEndingPojo>();
        webResult.setData(birthEndingService.queryBirthEndingList(birth));
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 
     * 获取没有被关联的建档记录
     * 
     * @author scd
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_GET_UNLINK_PREGRECORD)
    @ResponseBody
    public WebResult<PregArchivesPojo> getUnlinkPregRecord(BirthEndingCondition birth) {
        WebResult<PregArchivesPojo> webResult = new WebResult<PregArchivesPojo>();

        List<PregArchivesPojo> archivesList = pregArchivesService.queryCustomerPregRecprd(birth.getCustId());
        List<BirthEndingPojo> birthEndingList = birthEndingService.queryBirthEndingList(birth);
        // 删除已选的建档记录
        for (BirthEndingPojo birthEnding : birthEndingList) {
            for (PregArchivesPojo archives : archivesList) {
                if (archives.getId().equals(birthEnding.getArchivesId())) {
                    archivesList.remove(archives);
                    break;
                }
            }
        }
        webResult.setData(archivesList);
        webResult.setResult(true);
        return webResult;
    }

    /**
     * 添加/更新分娩信息
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_BASEINFO_ADD)
    public @ResponseBody
    WebResult<BirthEndingBaseInfoPojo> addBaseInfo(BirthEndingBaseInfoForm baseInfoForm) {
        BirthEndingBaseInfoPojo baseInfo = TransformerUtils.transformerProperties(BirthEndingBaseInfoPojo.class, baseInfoForm);
        WebResult<BirthEndingBaseInfoPojo> webResult = new WebResult<BirthEndingBaseInfoPojo>();
        birthBaseService.deleteBirthBaseByBirthId(baseInfoForm.getBirthId());
        webResult.setSuc(birthBaseService.saveBirthBabyInfo(baseInfo));
        return webResult;
    }

    /**
     * 
     * 新生儿获取
     * 
     * @param birthId
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_BABYINFO_SEARCH)
    public @ResponseBody
    WebResult<JSONObject> searchBabyInfo(String birthId) {
        WebResult<JSONObject> webResult = new WebResult<JSONObject>();
        // 获取新生儿信息和出院诊断信息
        List<BirthEndingBabyInfoPojo> babyList = birthBabyService.getBabyListByBirthId(birthId);
        List<BirthEndingDischargedPojo> dischargedList = dischargedService.getDischargedByBirthId(birthId);

        JSONObject json = new JSONObject();
        json.put("babyList", JacksonUtils.obj2jsonIgnoreNull(babyList));
        json.put("disList", JacksonUtils.obj2jsonIgnoreNull(dischargedList));
        webResult.setSuc(json);
        return webResult;
    }

    /**
     * 
     * 新生儿新增
     * 
     * @param from
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_BABYINFO_SAVE)
    public @ResponseBody
    WebResult<BirthEndingBabyInfoPojo> saveBabyInfo(BirthEndingBabyInfoForm from) {
        WebResult<BirthEndingBabyInfoPojo> webResult = new WebResult<BirthEndingBabyInfoPojo>();

        BirthEndingBabyInfo babyInfo = TransformerUtils.transformerProperties(BirthEndingBabyInfo.class, from);
        BirthEndingBabyInfoPojo babyInfoPojo = birthBabyService.saveBirthBabyInfo(babyInfo);
        webResult.setSuc(babyInfoPojo);
        return webResult;
    }

    /**
     * 
     * 新生儿更新
     * 
     * @param from
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_BABYINFO_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateBabyInfo(BirthEndingBabyInfoForm from) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();

        BirthEndingBabyInfo babyInfo = TransformerUtils.transformerProperties(BirthEndingBabyInfo.class, from);
        birthBabyService.updateBirthBabyInfo(babyInfo);
        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

    /**
     * 
     * 新生儿删除
     * 
     * @param babyId
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_BABYINFO_DELETE)
    public @ResponseBody
    WebResult<Boolean> deleteBabyInfo(String babyId) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        // 删除新生儿
        birthBabyService.deleteBabyById(babyId);
        // 同时删除新生儿诊断
        dischargedService.deleteDischargedByBabyId(babyId);

        webResult.setSuc(true);
        return webResult;
    }

    /**
     * 
     * 出院诊断新增
     * 
     * @param from
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_DISCHARGE_SAVE)
    public @ResponseBody
    WebResult<BirthEndingDischargedPojo> saveDischarge(BirthEndingDischargedForm from) {
        WebResult<BirthEndingDischargedPojo> webResult = new WebResult<BirthEndingDischargedPojo>();

        // 判断母亲诊断信息是否存在，不存在添加
        List<BirthEndingDischargedPojo> dischargedList = dischargedService.getDischargedByBirthId(from.getBirthId());
        if (CollectionUtils.isEmpty(dischargedList)) {
            BirthEndingDischarged disMotherInfo = TransformerUtils.transformerProperties(BirthEndingDischarged.class, from);
            disMotherInfo.setBabyId("");
            dischargedService.saveBirthDischarged(disMotherInfo);
        }

        // 添加新生儿诊断
        BirthEndingDischarged disBabyInfo = TransformerUtils.transformerProperties(BirthEndingDischarged.class, from);
        BirthEndingDischargedPojo disInfoPojo = dischargedService.saveBirthDischarged(disBabyInfo);

        webResult.setSuc(disInfoPojo);
        return webResult;
    }

    /**
     * 
     * 出院诊断更新
     * 
     * @param from
     * @return
     */
    @RequestMapping(value = BirthEndingMapping.BIRTHENDING_DISCHARGE_UPDATE)
    public @ResponseBody
    WebResult<Boolean> updateBabyInfo(BirthEndingDischargedForm from) {
        WebResult<Boolean> webResult = new WebResult<Boolean>();
        BirthEndingDischarged disInfo = TransformerUtils.transformerProperties(BirthEndingDischarged.class, from);

        // 修改母亲诊断信息 同时更新新生儿诊断信息
        if (StringUtils.isEmpty(disInfo.getBabyId())) {
            BirthEndingDischarged disBaby = new BirthEndingDischarged();
            List<BirthEndingDischargedPojo> disList = dischargedService.getDischargedByBirthId(from.getBirthId());
            if (CollectionUtils.isNotEmpty(disList)) {
                for (BirthEndingDischargedPojo disPojo : disList) {
                    disBaby = TransformerUtils.transformerProperties(BirthEndingDischarged.class, disPojo);
                    disBaby.setDisMotherDisagnosis(disInfo.getDisMotherDisagnosis());
                    disBaby.setDisHemoglobin(disInfo.getDisHemoglobin());
                    disBaby.setDisRemark(disInfo.getDisRemark());
                    dischargedService.updateBirthDischarged(disBaby);
                }
            } else {// 添加母亲诊断信息
                dischargedService.saveBirthDischarged(disInfo);
            }
        } else {// 更新新生儿诊断
            dischargedService.updateBirthDischarged(disInfo);
        }
        webResult.setSuc(true, MessageConstant.success_message);
        return webResult;
    }

}
