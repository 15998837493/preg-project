/*
 * DietaryReviewController.java    1.0  2018-1-9
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.utils.beans.TransformerUtils;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.condition.FoodCondition;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.entity.PregFoodDetails;
import com.mnt.preg.examitem.entity.PregFoodFeelTime;
import com.mnt.preg.examitem.entity.PregFoodRecord;
import com.mnt.preg.examitem.form.DietRecordForm;
import com.mnt.preg.examitem.mapping.DietaryReviewMapping;
import com.mnt.preg.examitem.mapping.DietaryReviewPageName;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregDietAnalysePojo;
import com.mnt.preg.examitem.pojo.PregDietRecordPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.examitem.pojo.PregFoodDetailsPojo;
import com.mnt.preg.examitem.pojo.PregFoodFeelTimePojo;
import com.mnt.preg.examitem.service.PregDietFoodAnalyseService;
import com.mnt.preg.examitem.service.PregDietFoodRecordService;
import com.mnt.preg.examitem.util.DietaryReviewPregnancyDRIs;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.examitem.util.FoodScoreUtil;
import com.mnt.preg.master.service.basic.FoodService;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.service.CodeService;
import com.mnt.preg.web.constants.PublicConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.DietaryReviewPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 24小时膳食回顾
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-9 scd 初版
 */
@Controller
public class DietaryReviewController extends BaseController {

    @Resource
    private FoodService foodService;

    @Resource
    public CodeService codeService;

    @Resource
    public PregDiagnosisService pregDiagnosisService;

    @Resource
    public ExamResultRecordUtil examResultRecordUtil;

    @Resource
    public ExamItemUtil examItemUtil;

    @Resource
    public PregDietFoodAnalyseService pregDietFoodAnalyseService;

    @Resource
    public CustomerService customerService;

    @Resource
    public PregDietFoodRecordService pregDietFoodRecordService;

    /**
     * 跳转到饮食填写记录页面
     * 
     * @author zcq
     * @param foodRecordId
     * @param model
     * @return
     */
    @RequestMapping(value = DietaryReviewMapping.DIETARY_REVIEW)
    public String initDietaryReview(String custId, String id, String diagnosisId, Model model) {
        String foodRecordId = this.addFoodRecord(custId);
        model.addAttribute("foodRecordId", foodRecordId);
        model.addAttribute("inspectId", id);
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("mealsTypeVos",
                NetJsonUtils.listToJsonArray(codeCache.getCodeListCache("MEALSTYPE".toLowerCase(),
                        "MEALSTYPE".toLowerCase())));
        String custName = customerService.getCustomer(custId).getCustName();
        model.addAttribute("custName", custName);
        return DietaryReviewPageName.DIETARY_REVIEW;
    }

    public String addFoodRecord(String custId) {
        CustomerPojo customerVo = customerService.getCustomer(custId);
        PregFoodRecord foodRecord = TransformerUtils.transformerProperties(PregFoodRecord.class, customerVo);
        foodRecord.setCustAge(customerVo.getCustAge());
        foodRecord.setRecordDatetime(new Date());
        return pregDietFoodRecordService.addFoodRecord(foodRecord);
    }

    /**
     * 
     * 食物检索
     * 
     * @param condition
     * @return
     */
    @RequestMapping(value = DietaryReviewMapping.QUERY_FOOD)
    public @ResponseBody PageCondition queryFood(FoodCondition condition) {
    	condition.setFoodMealType("");
        return foodService.queryFoodBaseForPage(condition);
    }

    /**
     * 记录膳食评估用餐时间、感受和饮食明细
     * 
     * @param request
     * @param recordForm
     *            记录信息
     * @return
     */
    @RequestMapping(value = DietaryReviewMapping.DIETARY_RECORDS)
    public @ResponseBody WebResult<String> addDietRecord(DietRecordForm form) {
        WebResult<String> webResult = new WebResult<String>();
        PregDietRecordPojo recordBo = new PregDietRecordPojo();
        try {
            // 初始化recordBo
            recordBo.setDiagnosisId(form.getDiagnosisId());
            recordBo.setFoodRecordId(form.getFoodRecordId());
            recordBo.setFoodDetailsList(new ArrayList<PregFoodDetailsPojo>());// 膳食明细
            recordBo.setFoodFeelTimeList(new ArrayList<PregFoodFeelTimePojo>());// 用餐时间和感受
            // 记录饮食时间及感受
            List<PregFoodFeelTime> foodFeelTimeList = form.getFoodFeelTimeList();
            if (CollectionUtils.isNotEmpty(foodFeelTimeList)) {
                for (PregFoodFeelTime feelTime : foodFeelTimeList) {
                    PregFoodFeelTimePojo feellTimePojo = TransformerUtils.transformerProperties(
                            PregFoodFeelTimePojo.class, feelTime);
                    recordBo.getFoodFeelTimeList().add(feellTimePojo);
                }
            }
            // 记录饮食明细
            List<String> foodList = form.getFoodList();
            if (CollectionUtils.isNotEmpty(foodList)) {
                for (String foodValues : foodList) {
                    String[] foodArray = foodValues.split("_");
                    int foodAmount = Integer.valueOf(foodArray[2]);
                    if (foodAmount > 0) {
                        PregFoodDetailsPojo foodBo = new PregFoodDetailsPojo();
                        foodBo.setMealsId(foodArray[0]);
                        foodBo.setFoodId(foodArray[1]);
                        foodBo.setFoodAmount(foodAmount);
                        recordBo.getFoodDetailsList().add(foodBo);
                    }
                }
            }
            recordBo.setDiagnosisId(form.getDiagnosisId());
            this.saveDietRecord(recordBo);
            webResult.setSuc(form.getFoodRecordId());
        } catch (Exception e) {
        	e.printStackTrace();
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    public void saveDietRecord(PregDietRecordPojo dietRecordBo) {
        // 第一步：记录数据
        String foodRecordId = dietRecordBo.getFoodRecordId();
        // 保存用餐时间及感受
        List<PregFoodFeelTime> foodFeelTimeList = TransformerUtils.transformerList(PregFoodFeelTime.class,
                dietRecordBo.getFoodFeelTimeList());
        pregDietFoodRecordService.addFoodFeelTime(foodFeelTimeList, foodRecordId);
        // 保存用餐明细
        List<PregFoodDetails> foodDetailsList = TransformerUtils.transformerList(PregFoodDetails.class,
                dietRecordBo.getFoodDetailsList());
        pregDietFoodRecordService.addFoodDetails(foodDetailsList, foodRecordId);

        // 第二步：数据分析
        PregDietReportPojo dietReportVo = this.getDietAnalyseResult(foodRecordId, dietRecordBo.getDiagnosisId());

        // 第三步：保存结果记录（检测项目记录表）
        ExamResultRecord examResultRecord = new ExamResultRecord();
        examResultRecord.setCustId(dietReportVo.getFoodRecord().getCustId());
        examResultRecord.setExamCode(foodRecordId);
        examResultRecord.setExamDate(new Date());
        examResultRecord.setExamCategory(ExamItemConstant.EXAM_ITEM_CODE.B00005);
        String examId = examResultRecordUtil.addExamResultRecordForExam(examResultRecord,
                ExamItemConstant.EXAM_ITEM_TABLE.B00005);

        // 第四步：保存分析结果（指标结果表）
        List<ExamItemPojo> detailList = this.saveExamItem(dietReportVo);
        examItemUtil.addExamItems(ExamItemConstant.EXAM_ITEM_TABLE.B00005, examId, detailList);

        // 获取结论
        examResultRecordUtil.updateExamResultRecordForExam(examResultRecord,
                ExamItemConstant.EXAM_ITEM_TABLE.B00005);
    }

    /**
     * 查看膳食分析报告
     * 
     * @author zcq
     * @param foodRecordId
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = DietaryReviewMapping.DIETARY_REVIEW_PDF)
    public @ResponseBody WebResult<String> getDietReportPdf(String id) {
        WebResult<String> webResult = new WebResult<String>();
        webResult.setSuc(this.getDietReport(id));
        return webResult;
    }

    public String getDietReport(String inspectId) {
        // 第一步：实例化对象
        DietaryReviewPdf dietReportPdf = new DietaryReviewPdf(){

            // 诊断项目
            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = pregDiagnosisService.getDiagnosisItem(reportForm.getReportCode());
                String examCode = diagnosisItem.getResultCode();
                PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId());
                // 接诊信息
                dietReportVo.setDiagnosis(diagnosis);
                dietReportVo.setDiagnosisItem(diagnosisItem);
                // 建档信息
                dietReportVo.setPregnancyArchives(pregArchivesService.getLastPregnancyArchive(diagnosis
                        .getDiagnosisCustomer()));
                // 分析结果
                dietReportVo.setFoodRecord(pregDietFoodRecordService.getFoodRecord(examCode));
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(ExamItemConstant.EXAM_ITEM_TABLE.B00005,
                        examCode));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };
        // 第二步：创建PDF
        String dietPdfpath = dietReportPdf.create(inspectId);

        return dietPdfpath;
    }

    private PregDietReportPojo getDietAnalyseResult(String foodRecordId, String diagnosisId) {
        PregDietReportPojo dietReportVo = new PregDietReportPojo();
        // 体检信息
        PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisId);
        // 查询设置接诊信息
        dietReportVo.setDiagnosis(diagnosis);
        // 查询设置膳食评估记录信息
        dietReportVo.setFoodRecord(pregDietFoodRecordService.getFoodRecord(foodRecordId));
        // 查询设置用餐明细
        dietReportVo.setFoodDetailsList(pregDietFoodRecordService.queryFoodDetails(foodRecordId));
        // 查询设置饮食时间及感受
        dietReportVo.setFoodFeelTimeList(pregDietFoodRecordService.queryFoodFeelTime(foodRecordId));
        // 查询设置膳食分析信息
        dietReportVo.setElement(pregDietFoodAnalyseService.getElementSum(foodRecordId));
        dietReportVo.setEnergy(pregDietFoodAnalyseService.getEachMealEnergy(foodRecordId));
        dietReportVo.setProtidAndFat(pregDietFoodAnalyseService.getProtidAndFatType(foodRecordId));
        dietReportVo.setDris(pregDietFoodAnalyseService.getDietaryReviewPregnancyDRIs(
                diagnosis.getDiagnosisPregnantStage(), diagnosis.getDiagnosisCustHeight(),
                diagnosis.getDiagnosisCustWeight()));

        return dietReportVo;
    }

    private Map<String, String> codeMap = new HashMap<String, String>();

    /**
     * 
     * 膳食评估保存结果
     * 
     * @author scd
     * @param dietReportVo
     * @return
     */
    private List<ExamItemPojo> saveExamItem(PregDietReportPojo dietReportVo) {
        List<CodePojo> codeList = codeService.queryCodeAll();
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo code : codeList) {
                codeMap.put(code.getCodeKind() + code.getCodeValue(), code.getCodeName());
            }
        }
        // String examCode = dietReportVo.getFoodRecord().getFoodRecordId();
        List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();

        List<PregFoodFeelTimePojo> feelTimes = dietReportVo.getFoodFeelTimeList();
        List<PregFoodDetailsPojo> foodList = dietReportVo.getFoodDetailsList();// 饮食明细
        PregDietAnalysePojo element = dietReportVo.getElement();// 饮食中每种元素的量
        PregDietAnalysePojo energy = dietReportVo.getEnergy();// 餐次能量
        PregDietAnalysePojo protidAndFat = dietReportVo.getProtidAndFat();// 优质蛋白
        DietaryReviewPregnancyDRIs dirs = dietReportVo.getDris().getRPDris();// 标准值
        String pregnancyStage = dietReportVo.getDiagnosis().getDiagnosisPregnantStage();

        // 第一部分用餐情况
        dietDetail(detailList, foodList);// 饮食明细
        dietTime(detailList, feelTimes);// 饮食时间
        // 第二部分能量摄入
        String[] str1 = getResult1(dirs.getFoodEnergy(), transferString(energy.getFoodEnergy(), 0), "part1");
        detailList.add(getExamItem("NU0100013", "一日总能量", str1[0], str1[1], str1[2], str1[3], "kcal"));
        String[] str2 = getResult1(dirs.getEnergyBreak(), transferString(energy.getEnergyBreak(), 0), "part1");
        detailList.add(getExamItem("NU0100014", "早餐供能（含加餐）", str2[0], str2[1], str2[2], str2[3], "kcal"));
        String[] str3 = getResult1(dirs.getEnergyLunch(), transferString(energy.getEnergyLunch(), 0), "part1");
        detailList.add(getExamItem("NU0100015", "午餐供能（含加餐）", str3[0], str3[1], str3[2], str3[3], "kcal"));
        String[] str4 = getResult1(dirs.getEnergySupper(), transferString(energy.getEnergySupper(), 0), "part1");
        detailList.add(getExamItem("NU0100016", "晚餐供能（含加餐）", str4[0], str4[1], str4[2], str4[3], "kcal"));
        // 第三部分产能营养素摄入
        String[] str5 = getResult1("50~60", transferString(element.getFoodCbrPercent(), 1) + "%", "part2.1");
        detailList.add(getExamItem("NU0100017", "总碳水化合物", "50%-65%", str5[1], str5[2], str5[3], ""));
        String[] str6 = getResult1(dirs.getFoodProtid(), transferString(element.getFoodProtid(), 1), "part2.4");
        detailList.add(getExamItem("NU0100018", "总蛋白质", dirs.getFoodProtid(), str6[1], str6[2],
                str6[3], "g"));
        String[] str7 = getResult1("20~30", transferString(element.getFoodFatPercent(), 1) + "%", "part2.1");
        detailList.add(getExamItem("NU0100019", "总脂肪", "20%-30%", str7[1], str7[2], str7[3], ""));
        String[] str8 = getResult1(dirs.getFoodProtidYes(), transferString(protidAndFat.getFoodProtidYes(), 1),
                "part2.3");
        detailList.add(getExamItem("NU0100020", "优质蛋白质", dirs.getFoodProtidYes(), str8[1],
                str8[2], str8[3], "g"));
        String[] str9 = getResult1("8", transferString(protidAndFat.getFoodFatAnimal(), 1), "part2.2");
        detailList.add(getExamItem("NU0100021", "饱和脂肪酸", "<8g", str9[1], str9[2], str9[3], "g"));
        String[] str10 = getResult1("24", transferString(element.getFoodDf(), 1), "part2.3");
        detailList.add(getExamItem("NU0100022", "膳食纤维", "24g", str10[1], str10[2], str10[3], "g"));
        // 第四部分维生素摄入（当日）
        Map<String, String> elementMap = PublicConstant.pregExtenderElementMap().get(pregnancyStage);
        String[] str11 = getResult2(elementMap.get("E00005"), transferString(element.getFoodVa(), 0));
        detailList.add(getExamItem("NU0100023", "维生素A", str11[0], str11[1], str11[2], str11[3], "ugRAE"));
        String[] str12 = getResult2(elementMap.get("E00018"), transferString(element.getFoodVc(), 1));
        detailList.add(getExamItem("NU0100024", "维生素C", str12[0], str12[1], str12[2], str12[3], "mg"));
        String[] str13 = getResult2(elementMap.get("E00007"), transferString(element.getFoodVe(), 2));
        detailList.add(getExamItem("NU0100025", "维生素E", str13[0], str13[1], str13[2], str13[3],
                "mga-TE"));
        String[] str14 = getResult2(elementMap.get("E00009"), transferString(element.getFoodVb1(), 2));
        detailList.add(getExamItem("NU0100026", "硫胺素", str14[0], str14[1], str14[2], str14[3], "mg"));
        String[] str15 = getResult2(elementMap.get("E00010"), transferString(element.getFoodVb2(), 2));
        detailList.add(getExamItem("NU0100027", "核黄素", str15[0], str15[1], str15[2], str15[3], "mg"));
        String[] str16 = getResult2(elementMap.get("E00015"), transferString(element.getFoodAf(), 2));
        detailList.add(getExamItem("NU0100028", "烟酸", str16[0], str16[1], str16[2], str16[3], "mg"));
        // 第五部分矿物质摄入（当日）
        String[] str17 = getResult2(elementMap.get("E00019"), transferString(element.getFoodEca(), 0));
        detailList.add(getExamItem("NU0100029", "钙", str17[0], str17[1], str17[2], str17[3], "mg"));
        String[] str18 = getResult2(elementMap.get("E00021"), transferString(element.getFoodEk(), 0));
        detailList.add(getExamItem("NU0100030", "钾", str18[0], str18[1], str18[2], str18[3], "mg"));
        String[] str19 = getResult2(elementMap.get("E00022"), transferString(element.getFoodEna(), 0));
        detailList.add(getExamItem("NU0100031", "钠", str19[0], str19[1], str19[2], str19[3], "mg"));
        String[] str20 = getResult2(elementMap.get("E00023"), transferString(element.getFoodEmg(), 0));
        detailList.add(getExamItem("NU0100032", "镁", str20[0], str20[1], str20[2], str20[3], "mg"));
        String[] str21 = getResult2(elementMap.get("E00024"), transferString(element.getFoodEfe(), 1));
        detailList.add(getExamItem("NU0100033", "铁", str21[0], str21[1], str21[2], str21[3], "mg"));
        String[] str22 = getResult2(elementMap.get("E00026"), transferString(element.getFoodEzn(), 2));
        detailList.add(getExamItem("NU0100034", "锌", str22[0], str22[1], str22[2], str22[3], "mg"));

        return detailList;
    }

    /**
     * 
     * 饮食明细
     * 
     * @author scd
     * @param examCode
     * @param detailList
     * @param foodList
     */
    public void dietDetail(List<ExamItemPojo> detailList, List<PregFoodDetailsPojo> foodList) {
        String breakStr = "";// 早餐
        String breakAddStr = "";// 上午加餐
        String lunchStr = "";// 午餐
        String lunchAddStr = "";// 下午加餐
        String supperStr = "";// 晚餐
        String supperAddStr = "";// 夜宵
        if (CollectionUtils.isNotEmpty(foodList)) {
            for (PregFoodDetailsPojo food : foodList) {
                String mealsId = food.getMealsId();
                String foodName = food.getFoodName();
                Integer foodAmout = food.getFoodAmount();
                if (FoodScoreUtil.BREAKFAST_MEALS_CODE.equals(mealsId)) {
                    breakStr += "；" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.MORNING_EXTRA_MEALS_CODE.equals(mealsId)) {
                    breakAddStr += "；" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.LUNCH_MEALS_CODE.equals(mealsId)) {
                    lunchStr += "；" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.AFTERNOON_EXTRA_MEALS_CODE.equals(mealsId)) {
                    lunchAddStr += "；" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.SUPPER_MEALS_CODE.equals(mealsId)) {
                    supperStr += "；" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.NIGHT_EXTRE_MEALS_CODE.equals(mealsId)) {
                    supperAddStr += "；" + foodName + foodAmout + "g";
                }
            }
        }
        detailList.add(getExamItem("NU0100001", "早餐", "", breakStr.replaceFirst("；", ""), "", "0", ""));
        detailList.add(getExamItem("NU0100002", "上午加餐", "", breakAddStr.replaceFirst("；", ""), "", "0",
                ""));
        detailList.add(getExamItem("NU0100003", "午餐", "", lunchStr.replaceFirst("；", ""), "", "0", ""));
        detailList.add(getExamItem("NU0100004", "下午加餐", "", lunchAddStr.replaceFirst("；", ""), "", "0",
                ""));
        detailList.add(getExamItem("NU0100005", "晚餐", "", supperStr.replaceFirst("；", ""), "", "0", ""));
        detailList
                .add(getExamItem("NU0100006", "夜宵", "", supperAddStr.replaceFirst("；", ""), "", "0", ""));
    }

    /**
     * 
     * 饮食时间
     * 
     * @author scd
     * @param examCode
     * @param detailList
     * @param feelTimes
     */
    public void dietTime(List<ExamItemPojo> detailList, List<PregFoodFeelTimePojo> feelTimes) {
        // 饮食时间
        String breakStrTime = "";// 早餐时间
        String breakAddStrTime = "";// 上午加餐时间
        String lunchStrTime = "";// 午餐时间
        String lunchAddStrTime = "";// 下午加餐时间
        String supperStrTime = "";// 晚餐时间
        String supperAddStrTime = "";// 夜宵时间
        if (CollectionUtils.isNotEmpty(feelTimes)) {
            for (PregFoodFeelTimePojo feelTime : feelTimes) {
                String mealsId = feelTime.getMealsId();
                if (FoodScoreUtil.BREAKFAST_MEALS_CODE.equals(mealsId)) {
                    breakStrTime = feelTime.getMealsTime();
                } else if (FoodScoreUtil.MORNING_EXTRA_MEALS_CODE.equals(mealsId)) {
                    breakAddStrTime = feelTime.getMealsTime();
                } else if (FoodScoreUtil.LUNCH_MEALS_CODE.equals(mealsId)) {
                    lunchStrTime = feelTime.getMealsTime();
                } else if (FoodScoreUtil.AFTERNOON_EXTRA_MEALS_CODE.equals(mealsId)) {
                    lunchAddStrTime = feelTime.getMealsTime();
                } else if (FoodScoreUtil.SUPPER_MEALS_CODE.equals(mealsId)) {
                    supperStrTime = feelTime.getMealsTime();
                } else if (FoodScoreUtil.NIGHT_EXTRE_MEALS_CODE.equals(mealsId)) {
                    supperAddStrTime = feelTime.getMealsTime();
                }
            }
        }
        detailList.add(getExamItem("NU0100007", "早餐时间", "", breakStrTime.replaceFirst("、", ""), "", "0", ""));
        detailList.add(getExamItem("NU0100008", "上午加餐时间", "", breakAddStrTime.replaceFirst("、", ""), "", "0",
                ""));
        detailList.add(getExamItem("NU0100009", "午餐时间", "", lunchStrTime.replaceFirst("、", ""), "", "0", ""));
        detailList.add(getExamItem("NU0100010", "下午加餐时间", "", lunchAddStrTime.replaceFirst("、", ""), "", "0",
                ""));
        detailList
                .add(getExamItem("NU0100011", "晚餐时间", "", supperStrTime.replaceFirst("、", ""), "", "0", ""));
        detailList
                .add(getExamItem("NU0100012", "夜宵时间", "", supperAddStrTime.replaceFirst("、", ""), "", "0", ""));
    }

    private String transferString(Object value, Integer scale) {
        String result = "";
        if (value == null) {
            BigDecimal val = new BigDecimal(0);
            return result = val.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }
        if (value instanceof BigDecimal) {
            BigDecimal val = BigDecimal.valueOf(Double.valueOf(value.toString()));
            result = val.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } else if (value instanceof Integer) {
            result = value.toString();
        } else {
            result = value.toString();
        }
        return result;
    }

    /**
     * 获取结论
     * 
     * @author zcq
     * @param pregnancy
     * @param value
     * @return
     */
    private String[] getResult1(String standard, String value, String type) {
        String[] result = {standard, value, "", ""};
        value = value.replace("%", "");
        double val = Double.valueOf(value);
        if ("part1".equals(type)) {
            if (standard.indexOf("~") > -1) {
                double min = Double.valueOf(standard.split("~")[0]);
                double max = Double.valueOf(standard.split("~")[1]);
                if (val < min * 0.9) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val > max * 1.1) {
                    result[2] = "↑";
                    result[3] = "4";
                } else {
                    result[2] = "";// 适宜
                    result[3] = "0";
                }
            } else {
                double stand = Double.valueOf(standard);
                if (val < stand * 0.9) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val > stand * 1.1) {
                    result[2] = "↑";
                    result[3] = "4";
                } else {
                    result[2] = "";
                    result[3] = "0";
                }
            }
        }
        if ("part2.1".equals(type)) {
            if (standard.indexOf("~") > -1) {
                double min = Double.valueOf(standard.split("~")[0]);
                double max = Double.valueOf(standard.split("~")[1]);
                if (val < min) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val >= min && val <= max) {
                    result[2] = "";
                    result[3] = "0";
                } else {
                    result[2] = "↑";
                    result[3] = "4";
                }
            } else {
                double stand = Double.valueOf(standard);
                if (val < stand) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val == stand) {
                    result[2] = "";
                    result[3] = "0";
                } else {
                    result[2] = "↑";
                    result[3] = "4";
                }
            }
        } else if ("part2.2".equals(type)) {
            double stand = Double.valueOf(standard);
            if (val <= stand) {
                result[2] = "";
                result[3] = "0";
            } else {
                result[2] = "↑";
                result[3] = "4";
            }
        } else if ("part2.3".equals(type)) {
            double stand = Double.valueOf(standard);
            if (val >= stand) {
                result[2] = "";
                result[3] = "0";
            } else {
                result[2] = "↓";
                result[3] = "4";
            }
        } else if ("part2.4".equals(type)) {
            if (standard.indexOf("~") > -1) {
                double min = Double.valueOf(standard.split("~")[0]);
                double max = Double.valueOf(standard.split("~")[1]);
                if (val < min) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val >= min && val <= max) {
                    result[2] = "";
                    result[3] = "0";
                }
            }
        } else if ("part5".equals(type)) {
            if (standard.indexOf("~") > -1) {
                double min = Double.valueOf(standard.split("~")[0]);
                double max = Double.valueOf(standard.split("~")[1]);
                if (val < min) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val >= min && val <= max) {
                    result[2] = "";
                    result[3] = "0";
                } else {
                    result[2] = "↑";
                    result[3] = "4";
                }
            } else {
                double stand = Double.valueOf(standard);
                if (val < stand) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val == stand) {
                    result[2] = "";
                    result[3] = "0";
                } else {
                    result[2] = "↑";
                    result[3] = "4";
                }
            }
        }
        return result;
    }

    private ExamItemPojo getExamItem(String itemCode, String itemName, String itemRefValue,
            String itemString, String itemResult, String itemCompare, String itemUnit) {
        ExamItemPojo detail = new ExamItemPojo();
        detail.setItemCode(itemCode);
        detail.setItemName(itemName);
        detail.setItemRefValue(itemRefValue);
        detail.setItemType(ExamItemConstant.EXAM_ITEM_TABLE.B00005);
        detail.setItemString(itemString);
        detail.setItemResult(itemResult);
        detail.setItemCompare(itemCompare);
        detail.setItemUnit(itemUnit);
        return detail;
    }

    private String[] getResult2(String standard, String value) {
        String[] result = {standard, value, "", ""};
        double val = Double.valueOf(value);
        int count = 0;
        int isUL = 0;
        double[] mathArray = new double[3];
        String[] resultArray = standard.split("~");
        if (!"——".equals(resultArray[0])) {
            mathArray[count++] = Double.valueOf(resultArray[0]);
        }
        if (!"——".equals(resultArray[1])) {
            mathArray[count++] = Double.valueOf(resultArray[1]);
        }
        if (!"——".equals(resultArray[2])) {
            isUL = 1;
            mathArray[count++] = Double.valueOf(resultArray[2]);
        }
        if (count == 1) {
            if (isUL == 1) {
                if (val <= mathArray[0]) {
                    result[2] = "";// 适宜
                    result[3] = "0";
                } else {
                    result[2] = "↑";
                    result[3] = "4";
                }
            } else {
                if (val >= mathArray[0]) {
                    result[2] = "";
                    result[3] = "0";
                } else {
                    result[2] = "↓";
                    result[3] = "4";
                }
            }
        } else if (count == 2) {
            if (isUL == 1) {
                if (val < mathArray[0]) {
                    result[2] = "↓";
                    result[3] = "4";
                } else if (val <= mathArray[1] && val >= mathArray[0]) {
                    result[2] = "";
                    result[3] = "0";
                } else {
                    result[2] = "↑";
                    result[3] = "4";
                }
            } else {
                if (val >= mathArray[0]) {
                    result[2] = "";
                    result[3] = "0";
                } else {
                    result[2] = "↓";
                    result[3] = "4";
                }
            }
        } else if (count == 3) {
            if (val < mathArray[0]) {
                result[2] = "↓";
                result[3] = "4";
            } else if (val <= mathArray[2] && val >= mathArray[0]) {
                result[2] = "";
                result[3] = "0";
            } else {
                result[2] = "↑";
                result[3] = "4";
            }
        }
        return result;
    }
}
