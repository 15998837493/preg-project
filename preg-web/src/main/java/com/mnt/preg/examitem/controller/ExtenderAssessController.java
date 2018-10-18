
package com.mnt.preg.examitem.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.mapping.CustomerMapping;
import com.mnt.preg.customer.mapping.CustomerPageName;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.examitem.condition.ExamItemCondition;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.form.ExtenderAssessForm;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.ExtenderReportPojo;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.master.pojo.basic.ElementPojo;
import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;
import com.mnt.preg.master.pojo.basic.ProductPojo;
import com.mnt.preg.platform.entity.PregDiagnosisExtender;
import com.mnt.preg.platform.pojo.ExtenderAssessGroupPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisExtenderPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregPlanExtenderAssessDetailPojo;
import com.mnt.preg.platform.pojo.PregPlanExtenderAssessPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.util.FoodsFormulaUtil;
import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.service.CodeService;
import com.mnt.preg.web.constants.PublicConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.ExtenderAssessPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 剂量评估Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-23 zcq 初版
 */
@Controller
public class ExtenderAssessController extends BaseController {

    @Resource
    public ExamResultRecordUtil examResultRecordUtil;

    @Resource
    public PregDiagnosisService pregDiagnosisService;

    @Resource
    public CodeService codeService;

    @Resource
    public ExamItemUtil examItemUtil;

    /**
     * 第一步：计量评估--数据收集（问诊--数据编辑）
     * 
     * @author zcq
     * @param custId
     * @param inspectId
     * @param diagnosisId
     * @param model
     * @return
     */
    @RequestMapping(value = CustomerMapping.PLAN_EXTENDER_ASSESS)
    public String toExtenderAssessView(String custId, String id, String diagnosisId, Model model) {
        PregDiagnosisPojo diagnosisPojo = pregDiagnosisService.getDiagnosis(diagnosisId);
        String custName = customerService.getCustomer(custId).getCustName();
        List<PregDiagnosisExtenderPojo> initExtenderList = pregDiagnosisService
                .queryDiagnosisExtenderByDiagnosisId(diagnosisId);
        // 获取所有元素
        List<ElementPojo> elementAllList = elementService.queryNutrient(null);
        // 获取所有商品
        Map<String, ProductPojo> pMap = new HashMap<String, ProductPojo>();
        List<ProductPojo> pList = productService.queryProduct(null);
        if (CollectionUtils.isNotEmpty(pList)) {
            for (ProductPojo pojo : pList) {
                pMap.put(pojo.getProductId(), pojo);
            }
        }
        model.addAttribute("initExtenderList", NetJsonUtils.listToJsonArray(initExtenderList));
        model.addAttribute("elementAllList", NetJsonUtils.listToJsonArray(elementAllList));
        model.addAttribute("productAllMap", NetJsonUtils.objectToJson(pMap));
        model.addAttribute("custName", custName);
        model.addAttribute("custId", custId);
        model.addAttribute("inspectId", id);
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("gestationalWeeks",
                Integer.valueOf(diagnosisPojo.getDiagnosisGestationalWeeks().split("\\+")[0]) + 1);
        return CustomerPageName.PLAN_EXTENDER_ASSESS;
    }

    /**
     * 第二步：计量评估--分析结果（问诊--剂量评估）
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = CustomerMapping.PLAN_EXTENDER_ANALYSIS)
    @ResponseBody
    public WebResult<ExtenderAssessGroupPojo> extenderAnalysis(ExtenderAssessForm form) {
        WebResult<ExtenderAssessGroupPojo> webResult = new WebResult<ExtenderAssessGroupPojo>();
        ExtenderAssessGroupPojo assessGroup = new ExtenderAssessGroupPojo();
        String diagnosisId = form.getDiagnosisId();
        List<PregDiagnosisExtenderPojo> extenderList = form.getExtenderList();

        // 第一步：数据过滤
        if (extenderList != null) {
            Iterator<PregDiagnosisExtenderPojo> it = extenderList.iterator();
            while (it.hasNext()) {
                PregDiagnosisExtenderPojo extender = it.next();
                if (extender == null || StringUtils.isEmpty(extender.getProductId())) {
                    it.remove();
                }
            }
        }
        if (CollectionUtils.isEmpty(extenderList)) {
            return webResult.setError("请先选择补充剂");
        }

        // 第二步：清空历史结果（包括1、剂量评估记录表，2、指标结果表，3、检查项目记录表，4、修改检查项目状态）
        pregDiagnosisService.resetDiagnosisItem(form.getInspectId());

        // 第三步：记录评估内容
        if (CollectionUtils.isNotEmpty(extenderList)) {
            for (PregDiagnosisExtenderPojo extenderPojo : extenderList) {
                extenderPojo.setDiagnosisId(diagnosisId);
                String cycleStart = extenderPojo.getCycleStart();
                String cycleEnd = extenderPojo.getCycleEnd();
                if (StringUtils.isNotEmpty(cycleStart) && StringUtils.isNotEmpty(cycleEnd)) {
                    extenderPojo.setTakingCycle(cycleStart + "," + cycleEnd);
                }
            }
            // 保存到评估记录表
            List<PregDiagnosisExtender> diagnosisExtenderList = TransformerUtils.transformerList(
                    PregDiagnosisExtender.class, extenderList);
            pregDiagnosisService.saveDiagnosisExtender(diagnosisId, diagnosisExtenderList);
        }

        // 第四步：分析数据（全部）
        Map<String, List<PregPlanExtenderAssessPojo>> resultMap = extenderAnalysisResult(diagnosisId, extenderList);
        assessGroup.setResultMap(resultMap);

        // 第五步：保存新结果
        Map<String, List<PregDiagnosisExtenderPojo>> listMap = new LinkedHashMap<String, List<PregDiagnosisExtenderPojo>>();
        if (resultMap != null && !CollectionUtils.sizeIsEmpty(resultMap)) {
            for (String stage : resultMap.keySet()) {
                List<PregPlanExtenderAssessPojo> list = resultMap.get(stage);
                if (stage.indexOf("备孕期") > -1) {
                    for (PregPlanExtenderAssessPojo assess : list) {
                        listMap.put(stage.substring(1, stage.length()) + "：" + assess.getTakingCycle(),
                                assess.getExtenderList());
                    }
                } else {
                    for (PregPlanExtenderAssessPojo assess : list) {
                        listMap.put(stage.substring(1, stage.length()) + "：" + assess.getTakingCycle().substring(1,
                                assess.getTakingCycle().length()), assess.getExtenderList());
                    }
                }
            }
        }
        assessGroup.setInspectCode(this.savePlanExtenerAssess(diagnosisId, listMap));

        return webResult.setSuc(assessGroup);
    }

    /**
     * 第三步：计量评估--生成报告（问诊--PDF报告）
     * 
     * @author zcq
     * @param inspectId
     * @param takingCycleList
     * @return
     */
    @RequestMapping(value = CustomerMapping.PLAN_EXTENDER_ASSESS_REPORT)
    public @ResponseBody
    WebResult<String> getExtenderAssessReport(String id, String takingCycleList) {
        WebResult<String> webResult = new WebResult<String>();
        if (StringUtils.isEmpty(takingCycleList)) {
            return webResult.setError("未选择服用周期");
        }
        // 第一步：数据准备
        String assessPdfpath = "";
        ReportForm reportForm = new ReportForm();
        reportForm.setInsId(this.getLoginUser().getUserPojo().getCreateInsId());
        reportForm.setReportCode(id);
        reportForm.setReportItem("B00004");
        List<String> codeList = new ArrayList<String>();
        for (String code : takingCycleList.split(",")) {
            if (code.indexOf("/") > -1) {
                codeList.add(code);
            } else {
                codeList.add(code.substring(1, code.length()));
            }
        }
        reportForm.setCodeList(codeList);

        // 第二步：实例化对象
        ExtenderAssessPdf assessPdf = new ExtenderAssessPdf(){

            @Override
            public ExtenderReportPojo beforeCreatePdf(ReportForm reportForm) {
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = pregDiagnosisService.getDiagnosisItem(reportForm
                        .getReportCode());
                PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                String resultCode = diagnosisItem.getResultCode();
                ExtenderReportPojo reportVo = new ExtenderReportPojo();
                ExamItemCondition examCondition = new ExamItemCondition();
                examCondition.setTableName(ExamItemConstant.EXAM_ITEM_TABLE.B00004);
                examCondition.setResultCode(resultCode);
                reportVo.setExamItemList(examItemUtil.queryExamItem(examCondition));// 指标结果
                reportVo.setTakingCycleList(reportForm.getCodeList());
                reportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                reportVo.setDiagnosis(diagnosis);
                reportVo.setDiagnosisItem(diagnosisItem);
                return reportVo;
            }

            @Override
            public void afterCreatePdf(ReportForm reportForm) {
                // 完善结果主表PDF报告路径
                PregDiagnosisItemsVo diagnosisItem = pregDiagnosisService.getDiagnosisItem(reportForm
                        .getReportCode());
                String resultCode = diagnosisItem.getResultCode();
                ExamResultRecord examResultRecord = new ExamResultRecord();
                examResultRecord.setExamCode(resultCode);
                examResultRecord.setExamPdf(reportForm.getReportPath());
                examResultRecordUtil.updateExamResultRecord(examResultRecord);
            }
        };

        // 第三步：创建PDF
        assessPdfpath = assessPdf.create(reportForm);

        return webResult.setSuc(assessPdfpath);
    }

    /**
     * 查询历史记录--补充剂历史数据
     * 
     * @author zcq
     * @param inspectCode
     * @param inspectItem
     * @return
     */
    @RequestMapping(value = CustomerMapping.PLAN_EXTENDER_ANALYSIS_HISTORY)
    @ResponseBody
    public WebResult<ExtenderAssessGroupPojo> getExtenderHistoryReport(String inspectCode, String inspectItem) {
        WebResult<ExtenderAssessGroupPojo> webResult = new WebResult<ExtenderAssessGroupPojo>();
        ExtenderAssessGroupPojo assessGroup = new ExtenderAssessGroupPojo();
        // 获取接诊项目信息
        PregDiagnosisItemsVo diagnosisItem = pregDiagnosisService.getDiagnosisItemsByInspectCodeAndInspectItem(
                inspectCode, inspectItem);
        if (diagnosisItem == null || StringUtils.isEmpty(diagnosisItem.getDiagnosisId())) {
            return webResult.setError("没有找到该接诊项目信息");
        }
        assessGroup.setInspectId(diagnosisItem.getId());
        // 查询剂量评估补充剂列表
        String diagnosisId = diagnosisItem.getDiagnosisId();
        List<PregDiagnosisExtenderPojo> extenderList = pregDiagnosisService
                .queryDiagnosisExtenderByDiagnosisId(diagnosisId);
        if (CollectionUtils.isEmpty(extenderList)) {
            return webResult.setError("没有补充剂数据");
        }
        // 分析数据（全部）
        Map<String, List<PregPlanExtenderAssessPojo>> resultMap = extenderAnalysisResult(diagnosisId, extenderList);
        assessGroup.setResultMap(resultMap);

        return webResult.setSuc(assessGroup);
    }

    /**
     * 分析剂量评估生成的结论（问诊--剂量评估--结论，临时的解决方案）
     * 
     * @author zcq
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = CustomerMapping.PLAN_EXTENDER_ANALYSIS_RESULT)
    @ResponseBody
    public WebResult<Map<String, String>> getExtenderAnalysisResult(String diagnosisId) {
        WebResult<Map<String, String>> webResult = new WebResult<Map<String, String>>();
        Map<String, String> inspectResultMap = new LinkedHashMap<String, String>();// 存放分析结论
        List<PregDiagnosisExtenderPojo> extenderList = pregDiagnosisService
                .queryDiagnosisExtenderByDiagnosisId(diagnosisId);
        if (CollectionUtils.isNotEmpty(extenderList)) {
            // 分析数据（全部）
            Map<String, List<PregPlanExtenderAssessPojo>> resultMap = extenderAnalysisResult(diagnosisId, extenderList);
            // 分组
            Map<String, List<PregDiagnosisExtenderPojo>> listMap = new LinkedHashMap<String, List<PregDiagnosisExtenderPojo>>();
            if (resultMap != null && !CollectionUtils.sizeIsEmpty(resultMap)) {
                for (String stage : resultMap.keySet()) {
                    List<PregPlanExtenderAssessPojo> list = resultMap.get(stage);
                    if (stage.indexOf("备孕期") > -1) {
                        for (PregPlanExtenderAssessPojo assess : list) {
                            listMap.put(stage.substring(1, stage.length()) + "：" + assess.getTakingCycle(),
                                    assess.getExtenderList());
                        }
                    } else {
                        for (PregPlanExtenderAssessPojo assess : list) {
                            listMap.put(stage.substring(1, stage.length()) + "：" + assess.getTakingCycle().substring(1,
                                    assess.getTakingCycle().length()), assess.getExtenderList());
                        }
                    }
                }
            }
            // 分析结论
            String cycleName = "";
            for (String cycle : listMap.keySet()) {
                if (cycle.indexOf("备孕期") > -1) {
                    if (cycle.split("：")[1].split("~")[0].equals(cycle.split("：")[1].split("~")[1])) {
                        cycleName = "<font color='red'>" + cycle.split("：")[0] + "："
                                + cycle.split("：")[1].split("~")[0] + " 月</font>";
                    } else {
                        cycleName = "<font color='red'>" + cycle + " 月</font>";
                    }
                } else {
                    if (cycle.split("：")[1].split("~")[0].equals(cycle.split("：")[1].split("~")[1])) {
                        cycleName = "<font color='red'>" + cycle.split("：")[0] + "：" + "第 "
                                + cycle.split("：")[1].split("~")[0] + " 周</font>";
                    } else {
                        cycleName = "<font color='red'>" + cycle + " 周</font>";
                    }
                }
                PregPlanExtenderAssessPojo assess = this.getExtenderAnalysisResult(diagnosisId, listMap.get(cycle));
                Map<String, PregPlanExtenderAssessDetailPojo> analyseMap = assess.getElementMap();
                List<PregDiagnosisExtenderPojo> cycleExtenderList = assess.getExtenderList();
                if (CollectionUtils.isNotEmpty(cycleExtenderList)) {
                    String productNames = "";
                    for (PregDiagnosisExtenderPojo extender : cycleExtenderList) {
                        productNames += "、" + extender.getProductName() + "（服用" + extender.getRegularity() + "）";
                    }
                    inspectResultMap.put(cycleName, productNames.replaceFirst("、", "") + " ");
                    String detailStr = "";
                    for (String code : analyseMap.keySet()) {
                        PregPlanExtenderAssessDetailPojo detail = analyseMap.get(code);
                        // 添加结论
                        String resultName = detail.getElement().getNutrientId();
                        if (StringUtils.isEmpty(resultName)) {
                            resultName = detail.getElement().getNutrientName();
                        }
                        String eMuch = detail.getElementAI().split(",")[0];
                        detailStr += "；" + resultName + "：" + eMuch;
                        detailStr = "<font color='gray'>" + detailStr.replaceFirst("；", "") + "</font>";
                        detailStr = detailStr.replace("↓", "<font color='blue'>↓ </font>").replace("↑",
                                "<font color='red'>↑ </font>");
                    }
                    inspectResultMap.put(cycleName, inspectResultMap.get(cycleName) + detailStr);
                }
            }
        }
        return webResult.setSuc(inspectResultMap);
    }

    /************************************************** 计量评估--（营养处方） ********************************************/

    /**
     * 计量评估--分析结果（营养处方在用）
     * 
     * @author zcq
     * @param form
     * @return
     */
    @RequestMapping(value = CustomerMapping.PLAN_GET_EXTENDERANALYSISDATA)
    @ResponseBody
    public WebResult<PregPlanExtenderAssessPojo> getExtenderAnalysisData(ExtenderAssessForm form) {
        WebResult<PregPlanExtenderAssessPojo> webResult = new WebResult<PregPlanExtenderAssessPojo>();
        String diagnosisId = form.getDiagnosisId();
        List<PregDiagnosisExtenderPojo> extenderList = form.getExtenderList();
        if (extenderList != null) {
            Iterator<PregDiagnosisExtenderPojo> it = extenderList.iterator();
            while (it.hasNext()) {
                PregDiagnosisExtenderPojo extender = it.next();
                if (extender == null || StringUtils.isEmpty(extender.getProductId())) {
                    it.remove();
                }
            }
        }
        webResult.setSuc(this.getExtenderAnalysisResult(diagnosisId, extenderList));
        return webResult;
    }

    /************************************************** 自定义工具方法 ********************************************/

    /**
     * 计量评估--计算分组分析（问诊--剂量评估）
     * 
     * @author zcq
     * @param diagnosisId
     * @param extenderList
     * @return
     */
    private Map<String, List<PregPlanExtenderAssessPojo>> extenderAnalysisResult(String diagnosisId,
            List<PregDiagnosisExtenderPojo> extenderList) {
        // 返回页面结论
        Map<String, List<PregPlanExtenderAssessPojo>> resultMap = new TreeMap<String, List<PregPlanExtenderAssessPojo>>();

        // 获取接诊信息
        PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisId);
        // 获取孕期建档信息
        PregArchivesPojo archives = pregArchivesService.getLastPregnancyArchive(diagnosis.getDiagnosisCustomer());

        if (CollectionUtils.isNotEmpty(extenderList)) {
            Map<String, PregDiagnosisExtenderPojo> extenderMap = new HashMap<String, PregDiagnosisExtenderPojo>();
            // 一级分组（key:月份/周数，value:补充剂ID_单次剂量）
            Map<String, String> oneLevelPareMap = new TreeMap<String, String>();// 备孕期
            Map<Integer, String> oneLevelPregMap = new TreeMap<Integer, String>();// 孕期
            for (PregDiagnosisExtenderPojo extenderPojo : extenderList) {
                String extenderId = extenderPojo.getProductId();// 补充剂主键（产品）
                String pregnancy = extenderPojo.getPregnancy();// 所属孕期
                String takingCycle = extenderPojo.getTakingCycle();// 服用周期
                String dosage = extenderPojo.getProductDosage().toString();// 单次剂量
                String frequency = extenderPojo.getProductFrequency();// 频次
                String judgeFlag = extenderId + "_" + dosage + "_" + frequency;
                extenderMap.put(judgeFlag, extenderPojo);// 记录补充剂到Map中
                if ("孕期".equals(pregnancy)) {// 孕期
                    if (StringUtils.isEmpty(takingCycle)) {
                        takingCycle = extenderPojo.getCycleStart() + "," + extenderPojo.getCycleEnd();
                    }
                    for (Integer week : this.getWeeks(takingCycle)) {
                        if (!oneLevelPregMap.containsKey(week)) {
                            oneLevelPregMap.put(week, judgeFlag);
                        } else {
                            oneLevelPregMap.put(week, oneLevelPregMap.get(week) + "," + judgeFlag);
                        }
                    }
                } else {// 备孕期
                    for (String month : this.getMonths(archives.getLmp(), takingCycle)) {
                        if (!oneLevelPareMap.containsKey(month)) {
                            oneLevelPareMap.put(month, judgeFlag);
                        } else {
                            oneLevelPareMap.put(month, oneLevelPareMap.get(month) + "," + judgeFlag);
                        }
                    }
                }
            }
            // 二级分组合并（key:月份/周数，value:补充剂ID_单次剂量）
            String lastKey = "";
            String lastValue = "";
            Map<String, String> twoLevelPareMap = new LinkedHashMap<String, String>();// 备孕期
            Map<String, String> twoLevelPregMap = new LinkedHashMap<String, String>();// 孕期
            for (String month : oneLevelPareMap.keySet()) {// 备孕期
                if (twoLevelPareMap.size() > 0 && StringUtils.isNotEmpty(lastValue)
                        && lastValue.equals(oneLevelPareMap.get(month))) {
                    twoLevelPareMap.remove(lastKey);
                    twoLevelPareMap.put(lastKey.split("~")[0] + "~" + month, oneLevelPareMap.get(month));
                    lastKey = lastKey.split("~")[0] + "~" + month;
                    lastValue = oneLevelPareMap.get(month);
                } else {
                    twoLevelPareMap.put(month + "~" + month, oneLevelPareMap.get(month));
                    lastKey = month + "~" + month;
                    lastValue = oneLevelPareMap.get(month);
                }
            }
            lastKey = "";
            lastValue = "";
            for (Integer week : oneLevelPregMap.keySet()) {// 孕期
                if (twoLevelPregMap.size() > 0 && StringUtils.isNotEmpty(lastValue)
                        && lastValue.equals(oneLevelPregMap.get(week))
                        && (Integer.valueOf(lastKey.split("~")[1]) + 1) == week) {
                    twoLevelPregMap.remove(lastKey);
                    twoLevelPregMap.put(lastKey.split("~")[0] + "~" + week, oneLevelPregMap.get(week));
                    lastKey = lastKey.split("~")[0] + "~" + week;
                    lastValue = oneLevelPregMap.get(week);
                } else {
                    twoLevelPregMap.put(week + "~" + week, oneLevelPregMap.get(week));
                    lastKey = week + "~" + week;
                    lastValue = oneLevelPregMap.get(week);
                }
            }
            // 分组计算评估
            for (String key : twoLevelPareMap.keySet()) {// 备孕期
                List<PregDiagnosisExtenderPojo> pareList = new ArrayList<PregDiagnosisExtenderPojo>();
                for (String id : twoLevelPareMap.get(key).split(",")) {
                    pareList.add((PregDiagnosisExtenderPojo) extenderMap.get(id).clone());
                }
                if (!resultMap.containsKey(key)) {
                    resultMap.put(key, new ArrayList<PregPlanExtenderAssessPojo>());
                }

                PregPlanExtenderAssessPojo assess = getExtenderAnalysisResult(diagnosisId, pareList);

                if (!resultMap.containsKey("0备孕期")) {
                    resultMap.put("0备孕期", new ArrayList<PregPlanExtenderAssessPojo>());
                }
                PregPlanExtenderAssessPojo assessClone = (PregPlanExtenderAssessPojo) assess.clone();
                assessClone.setTakingCycle(key);
                resultMap.get("0备孕期").add(assessClone);
            }
            for (String key : twoLevelPregMap.keySet()) {// 孕期
                List<PregDiagnosisExtenderPojo> pregList = new ArrayList<PregDiagnosisExtenderPojo>();
                for (String id : twoLevelPregMap.get(key).split(",")) {
                    pregList.add((PregDiagnosisExtenderPojo) extenderMap.get(id).clone());
                }

                PregPlanExtenderAssessPojo assess = getExtenderAnalysisResult(diagnosisId, pregList);

                Map<String, String> stageMap = getPregnancyStage(key);

                for (String stage : stageMap.keySet()) {
                    if (!resultMap.containsKey(stageMap.get(stage))) {
                        resultMap.put(stageMap.get(stage), new ArrayList<PregPlanExtenderAssessPojo>());
                    }
                    PregPlanExtenderAssessPojo assessClone = (PregPlanExtenderAssessPojo) assess.clone();
                    assessClone.setTakingCycle(stage.substring(0, stage.length()));
                    resultMap.get(stageMap.get(stage)).add(assessClone);
                }
            }
        }
        return resultMap;
    }

    /**
     * 计量评估--计算分析（营养处方在用）
     * 
     * @author zcq
     * @param diagnosisId
     * @param extenderList
     * @return
     */
    private PregPlanExtenderAssessPojo getExtenderAnalysisResult(String diagnosisId,
            List<PregDiagnosisExtenderPojo> extenderList) {
        // 分析结果
        PregPlanExtenderAssessPojo assess = new PregPlanExtenderAssessPojo();

        /** 第一步：获取基础数据 */
        // 获取接诊信息
        PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisId);
        assess.setDiagnosis(diagnosis);
        // 获取客户信息
        CustomerPojo customer = customerService.getCustomer(diagnosis.getDiagnosisCustomer());
        assess.setCustomer(customer);
        // 获取孕期建档信息
        PregArchivesPojo archives = pregArchivesService.getLastPregnancyArchive(customer.getCustId());
        assess.setArchives(archives);
        // 获取补充剂DIRS信息 并 转成对象
        Map<String, PregPlanExtenderAssessDetailPojo> elementMap = new LinkedHashMap<String, PregPlanExtenderAssessDetailPojo>();
        String pregStage = diagnosis.getDiagnosisPregnantStage();// 孕期阶段
        Map<String, String> pregDrisMap = PublicConstant.pregExtenderElementMap().get(pregStage);
        for (String code : pregDrisMap.keySet()) {
            String[] value = pregDrisMap.get(code).split("~");
            PregPlanExtenderAssessDetailPojo detail = new PregPlanExtenderAssessDetailPojo();
            detail.setElementRNI(value[0]);
            detail.setElementAI(value[1]);
            detail.setElementUL(value[2]);
            elementMap.put(code, detail);
        }
        // 获取补充剂DIRS信息 并 转成对象--（单独设置a-亚麻酸）
        double eer = FoodsFormulaUtil.getPregnancyEnergy(diagnosis.getDiagnosisPregnantStage(), diagnosis
                .getDiagnosisCustHeight().doubleValue());
        PregPlanExtenderAssessDetailPojo pead = new PregPlanExtenderAssessDetailPojo();
        pead.setElementRNI("——");
        pead.setElementAI((new BigDecimal(eer).multiply(new BigDecimal(6)))
                .divide(new BigDecimal(9), BigDecimal.ROUND_HALF_UP).setScale(1, BigDecimal.ROUND_HALF_UP).toString());
        pead.setElementUL("——");
        elementMap.put("E00032", pead);

        /** 第二步：计算每个商品元素含量 累加在一起 */
        Map<String, PregPlanExtenderAssessDetailPojo> resultMap = new HashMap<String, PregPlanExtenderAssessDetailPojo>();
        // 遍历被评估的商品 ->查询每个商品的元素信息 ->单次剂量*元素含量*用药频次 ->记录结果 ->记录元素含量 与 元素来源①
        if (CollectionUtils.isNotEmpty(extenderList)) {
            // 计算补充剂剂量
            for (int i = 0; i < extenderList.size(); i++) {
                PregDiagnosisExtenderPojo extenderPojo = extenderList.get(i);
                // 设置商品序号名
                extenderPojo.setIndexName(getIndexName(i + 1));
                // 查询商品元素含量
                List<NutrientAmountPojo> elementList = productService.queryProductElementByProductId(extenderPojo
                        .getProductId());
                // 遍历单个元素信息列表，累加元素含量，记录元素来源
                for (NutrientAmountPojo eleVo : elementList) {
                    // 单次剂量
                    BigDecimal prodosage = extenderPojo.getProductDosage();
                    if (prodosage == null) {
                        prodosage = BigDecimal.ZERO;
                    }
                    // 元素含量
                    BigDecimal extdosage = eleVo.getNutrientDosage();
                    if (extdosage == null) {
                        extdosage = BigDecimal.ZERO;
                    }
                    // 用药频次
                    BigDecimal freq = new BigDecimal(PublicConstant.fregMap.get(extenderPojo.getProductFrequency()));
                    // 单次总剂量 = 单次剂量*元素含量*用药频次
                    BigDecimal dosage = extdosage.multiply(prodosage).multiply(freq)
                            .setScale(1, BigDecimal.ROUND_HALF_UP);
                    // 如果计算后的元素在结果集合中不存在，创建一个新的
                    if (!resultMap.containsKey(eleVo.getNutrientId())) {
                        resultMap.put(eleVo.getNutrientId(), new PregPlanExtenderAssessDetailPojo());
                    }
                    // 取出已存在的明细对象
                    PregPlanExtenderAssessDetailPojo detailVo = resultMap.get(eleVo.getNutrientId());
                    // 累加元素含量
                    detailVo.setElementValue(detailVo.getElementValue().add(dosage));
                    // 记录元素来源
                    if (dosage.doubleValue() != 0.0) {
                        String elementRemark = eleVo.getNutrientRemark();
                        // 添加到元素来源列表
                        detailVo.getSrcList().add(extenderPojo.getIndexName() + " " + dosage);
                        // 1、记录补充剂成分备注 2、添加商品元素备注
                        if (StringUtils.isNotEmpty(eleVo.getNutrientRemark())) {
                            detailVo.getSrcEleDescList().add(extenderPojo.getIndexName() + elementRemark);
                        }
                        detailVo.getDetailStrList()
                                .add(extenderPojo.getIndexName()
                                        + " "
                                        + extenderPojo.getProductName()
                                        + "："
                                        + (StringUtils.isEmpty(elementRemark) ? eleVo.getNutrientName() : elementRemark)
                                        + " （" + dosage
                                        + " " + transCode("PRODUCTUNIT", eleVo.getNutrientUnit()) + "）");
                    }
                }
            }
        }

        /** 第三步：每个元素与Dris比对，生成结果 */
        // 设置符合条件的元素
        List<ElementPojo> elementList = elementService.queryNutrient(null);// 查询元素信息
        Map<String, ElementPojo> judgeMap = new HashMap<String, ElementPojo>();
        if (CollectionUtils.isNotEmpty(elementList)) {
            for (ElementPojo nutrientVo : elementList) {
                // 判断是否为评估元素
                if ("1".equals(nutrientVo.getNutrientEvalTwo())) {
                    judgeMap.put(nutrientVo.getNutrientId(), nutrientVo);
                }
            }
        }
        // 过滤 并 设置结果信息
        Iterator<Entry<String, PregPlanExtenderAssessDetailPojo>> it = resultMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, PregPlanExtenderAssessDetailPojo> entry = it.next();
            String key = entry.getKey();
            PregPlanExtenderAssessDetailPojo detVo = resultMap.get(key);
            // 默认结果为空
            detVo.setRdis("");
            // 如果值为零,去除此元素,continue
            if (detVo.getElementValue().doubleValue() == 0.0) {
                it.remove();
                continue;
            }
            // 过滤元素信息
            if (!judgeMap.containsKey(key)) {
                it.remove();
                continue;
            }
            // 赋值元素信息
            detVo.setElement(judgeMap.get(key));
            // 数据源elementMap
            if (elementMap.containsKey(key)) {
                PregPlanExtenderAssessDetailPojo srcVo = elementMap.get(key);

                String rni = srcVo.getElementRNI();
                String ai = srcVo.getElementAI();
                String ul = srcVo.getElementUL();

                detVo.setElementRNI(rni);
                detVo.setElementAI(ai);
                detVo.setElementUL(ul);

                /*
                 * 比对公式 结果除以DRIS中的RNI，如果没有RNI除以AI
                 */

                BigDecimal denominator = null;
                String drisStr = "";
                // 确定被除数
                if (!"——".equals(rni)) {
                    denominator = new BigDecimal(rni);
                    drisStr = "RNI";
                } else if (!"——".equals(ai)) {
                    denominator = new BigDecimal(ai);
                    drisStr = "AI";
                }

                // 校验分母，用元素值除以此值，判断是否大于1
                if (denominator != null && denominator.doubleValue() != 0.0) {
                    double compareValue = detVo.getElementValue().divide(denominator, 1, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();
                    String result = compareValue + "";
                    if (compareValue > 1) {
                        detVo.setRdis(result + "倍" + drisStr + " ↑");
                    } else if (compareValue < 1) {
                        detVo.setRdis(result + "倍" + drisStr + " ↓");
                    }
                }
                // 兼容性冗余
                detVo.setExtenderElement(detVo.getElementValue() + "");
            }
        }
        // 排序 默认按照key升序
        Map<String, PregPlanExtenderAssessDetailPojo> sortedMap = new TreeMap<String, PregPlanExtenderAssessDetailPojo>();
        // 如需自定义排序，请使用以下构造方法
        // Map<String, PlanExtenderAssessDetailVo> sortedMap = new TreeMap<String, PlanExtenderAssessDetailVo>(
        // new Comparator<String>(){
        // public int compare(String key1, String key2) {
        // return key1.compareTo(key2);
        // }
        // });
        assess.setExtenderList(extenderList);
        // 营养素剂量评估分析（与pdf保持一致）
        Map<String, PregPlanExtenderAssessDetailPojo> finalMap = new HashMap<String, PregPlanExtenderAssessDetailPojo>();
        for (String code : resultMap.keySet()) {
            PregPlanExtenderAssessDetailPojo detail = resultMap.get(code);
            // 元素/剂量单位
            // String str1 = detail.getElement().getNutrientName() + "/" + queryCodeNameByKindAndValue("PRODUCTUNIT",
            // detail.getElement().getNutrientUnit());
            // 补充剂来源分析
            String str2 = generateSrcStr(detail.getSrcList());
            // 补充剂成分备注
            String str3 = generateSrcStr(detail.getSrcEleDescList());
            // 结果
            String str4 = detail.getElementValue() + "";
            // 结论e
            String str5 = detail.getRdis();
            // DRIS
            String str6 = generateDRISInfo(detail);
            PregPlanExtenderAssessDetailPojo pre = new PregPlanExtenderAssessDetailPojo();
            // ElementPojo e = new ElementPojo();
            // e.setNutrientName(str1);// 设置元素/剂量单位
            // e.setNutrientUnitName(detail.getElement().getNutrientEvalTwo());
            // e.setNutrientEvalTwo(detail.getElement().getNutrientEvalTwo());
            pre.setElement(detail.getElement());
            pre.setDietElement(str2);// 设置补充剂来源分析
            pre.setExtenderElement(str3);// 设置补充剂成分备注
            pre.setElementRNI(str4);// 设置结果
            pre.setElementAI(str5);// 设置结论
            pre.setElementUL(str6);// 设置DRIS
            pre.setDetailStrList(detail.getDetailStrList());
            finalMap.put(code, pre);
        }
        sortedMap.putAll(finalMap);
        assess.setElementMap(sortedMap);
        return assess;
    }

    /**
     * 保存补充剂方案剂量评估结果
     * 
     * @author zcq
     * @param diagnosisId
     * @param extenderList
     * @return
     */
    private String savePlanExtenerAssess(String diagnosisId, Map<String, List<PregDiagnosisExtenderPojo>> listMap) {
        String examCode = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();
        Map<String, String> inspectResultMap = new LinkedHashMap<String, String>();// 存放分析结论
        int flag = 0;
        for (String cycle : listMap.keySet()) {
            PregPlanExtenderAssessPojo assess = this.getExtenderAnalysisResult(diagnosisId, listMap.get(cycle));
            Map<String, PregPlanExtenderAssessDetailPojo> analyseMap = assess.getElementMap();

            for (String code : analyseMap.keySet()) {
                PregPlanExtenderAssessDetailPojo detail = analyseMap.get(code);
                String itemString;// 结论 每行6格数据 以'<cell>'分割
                // 元素/剂量单位
                String str1 = detail.getElement().getNutrientName() + "（" +
                        queryCodeNameByKindAndValue("PRODUCTUNIT", detail.getElement().getNutrientUnit()) + "）";
                // String str1 = detail.getElement().getNutrientName();
                // 补充剂来源分析
                // String str2 = generateSrcStr(detail.getSrcList());
                String str2 = detail.getDietElement();
                // 补充剂成分备注
                // String str3 = generateSrcStr(detail.getSrcEleDescList());
                String str3 = detail.getExtenderElement();
                // 结果
                // String str4 = detail.getElementValue() + "";
                String str4 = detail.getElementRNI();
                // 结论
                // String str5 = detail.getRdis();
                String str5 = detail.getElementAI();
                // DRIS
                // String str6 = generateDRISInfo(detail);
                String str6 = detail.getElementUL();
                itemString = str1 + "<cell>" + str2 + "<cell>" + str3 + "<cell>" + str4 + "<cell>" + str5 + "<cell>"
                        + str6;

                String eMuch = str5.split(",")[0];
                String rName = detail.getElement().getNutrientName();

                ExamItemPojo examDetail = new ExamItemPojo();
                examDetail.setItemClassify(cycle);// 服用周期
                examDetail.setExamId(examCode);// 接诊号
                examDetail.setItemCode(code);// 结果编码：元素编码、其它（基本信息key、被评估数据key）
                examDetail.setItemName(detail.getElement().getNutrientName());
                examDetail.setItemString(itemString);// 结果
                examDetail.setItemResult(rName + eMuch);
                examDetail.setItemDoctor(this.getLoginUser().getUserPojo().getUserName());
                examDetail.setItemType(ExamItemConstant.EXAM_ITEM_TABLE.B00004);
                detailList.add(examDetail);
                // 添加结论
                String resultName = detail.getElement().getNutrientId();
                if (StringUtils.isEmpty(resultName)) {
                    resultName = detail.getElement().getNutrientName();
                }
                if (!inspectResultMap.containsKey(cycle)) {
                    inspectResultMap.put(cycle, resultName + "：" + eMuch);
                } else {
                    inspectResultMap.put(cycle, inspectResultMap.get(cycle) + "；" + resultName + "：" + eMuch);
                }
            }

            /*
             * 被评估的商品信息 不确定行 每行以<row>分割
             * 每行有7格数据 以<cell>分割
             */
            List<String> dataList = new ArrayList<String>();
            if (assess.getExtenderList() != null) {
                for (int i = 0; i < assess.getExtenderList().size(); i++) {
                    PregDiagnosisExtenderPojo deBo = assess.getExtenderList().get(i);
                    String rowDataStr = "";
                    // 编号
                    String exStr1 = deBo.getIndexName();
                    // 品名
                    String exStr2 = deBo.getProductName();
                    // 单次剂量+单位
                    String exStr3 = deBo.getProductDosage() + " " + deBo.getProductUnit();
                    // 剂量说明
                    String exStr4 = deBo.getProductDosageDesc();
                    // 用法
                    String exStr5 = deBo.getProductUsageMethod();
                    // 频次
                    String exStr6 = this.transCode("PRODUCTFREQUENCY", deBo.getProductFrequency());
                    // 规律服用天数
                    String exStr7 = StringUtils.isEmpty(deBo.getRegularity()) ? "——" : deBo.getRegularity();

                    rowDataStr = exStr1 + "<cell>" + exStr2 + "<cell>" + exStr3 + "<cell>" + exStr4 + "<cell>"
                            + exStr5 + "<cell>" + exStr6 + "<cell>" + exStr7;
                    dataList.add(rowDataStr);
                }
            }
            // 构建存储对象
            ExamItemPojo examProductDetail = new ExamItemPojo();
            examProductDetail.setExamId(examCode);// 接诊号
            examProductDetail.setItemClassify(cycle);// 服用周期
            examProductDetail.setItemCode("productListInfo");// 固定编码 productListInfo
            examProductDetail.setItemName("评估商品信息");// 固定名称
            examProductDetail.setItemString(StringUtils.join(dataList, "<row>"));// 结果
            examProductDetail.setItemDoctor(this.getLoginUser().getUserPojo().getUserName());
            examProductDetail.setItemType(ExamItemConstant.EXAM_ITEM_TABLE.B00004);
            detailList.add(examProductDetail);

            if (flag == 0) {
                // 患者基本信息 8格数据 以'<cell>'分割
                String itemString;
                // ID
                String str1 = assess.getCustomer().getCustPatientId();
                // 姓名
                String str2 = assess.getDiagnosis().getDiagnosisCustName();
                // 年龄
                String str3 = assess.getDiagnosis().getDiagnosisCustAge().intValue() + "";
                // 孕前体重
                String str4 = assess.getCustomer().getCustWeight().toString() + " kg";
                // 身高
                String str5 = assess.getCustomer().getCustHeight().toString() + " cm";
                // 孕前BMI
                String str6 = assess.getCustomer().getCustBmi().toString() + "";
                // 孕周数
                String str7 = assess.getDiagnosis().getDiagnosisGestationalWeeks();
                // 预产期
                String str8 = "——";
                if (assess.getArchives().getPregnancyDueDate() != null) {
                    str8 = sdf.format(assess.getArchives().getPregnancyDueDate());
                }
                // 构建存储对象
                ExamItemPojo examDetail = new ExamItemPojo();
                examDetail.setExamId(examCode);// 接诊号
                examDetail.setItemCode("customerInfo");// 固定编码 customerInfo
                examDetail.setItemName("患者基本信息");// 固定名称
                itemString = str1 + "<cell>" + str2 + "<cell>" + str3 + "<cell>" + str4 + "<cell>" + str5 + "<cell>"
                        + str6 + "<cell>" + str7 + "<cell>" + str8 + "<cell>"
                        + sdf.format(assess.getArchives().getLmp())
                        + "<cell>" + assess.getArchives().getEmbryoNum();
                examDetail.setItemString(itemString);// 结果
                examDetail.setItemDoctor(this.getLoginUser().getUserPojo().getUserName());
                examDetail.setItemType(ExamItemConstant.EXAM_ITEM_TABLE.B00004);
                detailList.add(examDetail);
            }
            flag++;
        }

        // 页尾信息
        ExamItemPojo examProductDetail = new ExamItemPojo();
        examProductDetail.setExamId(examCode);// 接诊号
        examProductDetail.setItemCode("footerInfo");// 固定编码 footerInfo
        examProductDetail.setItemName("页尾信息");// 固定名称
        // sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String footerInfo = this.getLoginUser().getUserPojo().getUserName() + "<cell>"
                + sdf.format(new Date()) + "<cell>";
        examProductDetail.setItemString(footerInfo);// 结果
        examProductDetail.setItemDoctor(this.getLoginUser().getUserPojo().getUserName());
        examProductDetail.setItemType(ExamItemConstant.EXAM_ITEM_TABLE.B00004);
        detailList.add(examProductDetail);

        // 保存结果记录
        PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisId);
        CustomerPojo customer = customerService.getCustomer(diagnosis.getDiagnosisCustomer());
        ExamResultRecord examResultRecord = TransformerUtils.transformerProperties(ExamResultRecord.class, customer);
        examResultRecord.setExamCode(examCode);
        examResultRecord.setExamDate(new Date());
        examResultRecord.setExamCategory(ExamItemConstant.EXAM_ITEM_CODE.B00004);
        // TODO:zcq待解决，明细太多保存不了，临时解决方案，每次都把结论分析一遍
        // inspectionResult.setDiagnosisResultNames(StringUtils.join(inspectResult, "；"));
        String examId = examResultRecordUtil.addExamResultRecord(examResultRecord);

        // 保存 患者基本信息、评估商品、评估结果列表
        examItemUtil.addExamItems(ExamItemConstant.EXAM_ITEM_TABLE.B00004, examId, detailList);
        return examCode;
    }

    /**
     * 生成补充剂来源数据
     * 
     * @param srcList
     * @return
     */
    private String generateSrcStr(List<String> srcList) {
        String result = "";
        // pdf每行15个字，如果单行注释超出14长度 再用<br>分割
        List<String> tmpList;
        if (srcList != null) {
            tmpList = new ArrayList<>();
            for (String tempStr : srcList) {
                StringBuffer s1 = new StringBuffer(tempStr);
                int index;
                for (index = 14; index < s1.length(); index += 18) {
                    s1.insert(index, "<br>");
                }
                tempStr = s1.toString();
                tmpList.add(tempStr);
            }
            result += StringUtils.join(tmpList, "<br>");// 代表换行
        }
        return result;
    }

    /**
     * RNI AI UL
     * 
     * @author xdc
     * @return
     */
    private String generateDRISInfo(PregPlanExtenderAssessDetailPojo detai) {
        List<String> resultList = new ArrayList<>();
        if (StringUtils.isNotEmpty(detai.getElementRNI()) && !"——".equals(detai.getElementRNI())) {
            resultList.add("RNI:" + detai.getElementRNI());
        }
        if (StringUtils.isNotEmpty(detai.getElementAI()) && !"——".equals(detai.getElementAI())) {
            resultList.add("AI:" + detai.getElementAI());
        }
        if (StringUtils.isNotEmpty(detai.getElementUL()) && !"——".equals(detai.getElementUL())) {
            resultList.add("UL:" + detai.getElementUL());
        }
        return StringUtils.join(resultList, "<br>");
    }

    /**
     * 获取编码
     * 
     * @author xdc
     * @return
     */
    private String queryCodeNameByKindAndValue(String kind, String value) {
        String result = "——";
        if (StringUtils.isEmpty(kind))
            kind = "thisIsNull";
        if (StringUtils.isEmpty(value))
            value = "thisIsNull";
        CodeInfo condition = new CodeInfo();
        condition.setCodeKind(kind);
        condition.setCodeValue(value);
        List<CodePojo> list = codeService.queryCode(condition);
        if (list.size() > 0) {
            result = list.get(0).getCodeName();
        }
        return result;
    }

    /**
     * 获取周数
     * 
     * @author zcq
     * @param takingCycle
     * @return
     */
    private List<Integer> getWeeks(String takingCycle) {
        List<Integer> result = new ArrayList<Integer>();
        if (StringUtils.isNotEmpty(takingCycle)) {
            String[] cycle = takingCycle.split(",");
            Integer start = Integer.valueOf(cycle[0]);
            int end = Integer.valueOf(cycle[1]);
            while (start <= end) {
                result.add(start++);
            }
        }
        return result;
    }

    /**
     * 获取月份
     * 
     * @author zcq
     * @param lmp
     * @param takingCycle
     * @return
     */
    private List<String> getMonths(Date lmp, String takingCycle) {
        List<String> result = new ArrayList<String>();
        if (lmp != null && StringUtils.isNotEmpty(takingCycle)) {
            double cycle = Double.valueOf(takingCycle);
            Double days = Double.valueOf(cycle * 30);
            Date startDate = JodaTimeTools.before_day(lmp, days.intValue());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");// 格式化为年月
            try {
                startDate = sdf.parse(JodaTimeTools.toString(startDate, JodaTimeTools.FORMAT_10));
                lmp = sdf.parse(JodaTimeTools.toString(lmp, JodaTimeTools.FORMAT_10));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(startDate);
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(lmp);
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            // 遍历月份
            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        }
        return result;
    }

    /**
     * 获取孕期
     * 
     * @author zcq
     * @param weeks
     * @return
     */
    private Map<String, String> getPregnancyStage(String weeks) {
        Map<String, String> result = new TreeMap<String, String>();
        String[] week = weeks.split("~");
        int start = Integer.valueOf(week[0]);
        int end = Integer.valueOf(week[1]);
        if (end < 13) {
            result.put("1" + weeks, "1孕早期");
        } else if (start >= 13 && end < 28) {
            result.put("2" + weeks, "2孕中期");
        } else if (start >= 28) {
            result.put("3" + weeks, "3孕晚期");
        } else if (start < 13 && end >= 13 && end < 28) {
            result.put("1" + start + "~" + 12, "1孕早期");
            result.put("2" + 13 + "~" + end, "2孕中期");
        } else if (start < 28 && start >= 13 && end >= 28) {
            result.put("2" + start + "~" + 27, "2孕中期");
            result.put("3" + 28 + "~" + end, "3孕晚期");
        } else if (start < 13 && end >= 28) {
            result.put("1" + start + "~" + 12, "1孕早期");
            result.put("2" + 13 + "~" + 27, "2孕中期");
            result.put("3" + 28 + "~" + end, "3孕晚期");
        }
        return result;
    }

    /**
     * 获取标号
     * 
     * @author zcq
     * @param number
     * @return
     */
    private String getIndexName(Integer number) {
        String result = "";
        String[] indexNames = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩", "⑪", "⑫", "⑬", "⑭", "⑮", "⑯", "⑰",
                "⑱", "⑲", "⑳"};
        if (number >= 0 && number <= 20) {
            result = indexNames[number - 1];
        } else {
            result = "(" + number + ")";
        }
        return result;
    }

}
