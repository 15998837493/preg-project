
package com.mnt.preg.examitem.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mnt.preg.customer.mapping.CustomerPageName;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.service.CustomerService;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.entity.PregFoodDetails;
import com.mnt.preg.examitem.entity.PregFoodFrequency;
import com.mnt.preg.examitem.entity.PregFoodRecord;
import com.mnt.preg.examitem.entity.PregHabit;
import com.mnt.preg.examitem.form.DietRecordForm;
import com.mnt.preg.examitem.mapping.DietFoodMapping;
import com.mnt.preg.examitem.mapping.DietFoodPageName;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregDietAnalysePojo;
import com.mnt.preg.examitem.pojo.PregDietRecordPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.examitem.pojo.PregFoodDetailsPojo;
import com.mnt.preg.examitem.pojo.PregFoodFeelTimePojo;
import com.mnt.preg.examitem.pojo.PregFoodFrequencyPojo;
import com.mnt.preg.examitem.pojo.PregHabitPojo;
import com.mnt.preg.examitem.service.PregDietFoodAnalyseService;
import com.mnt.preg.examitem.service.PregDietFoodRecordService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.examitem.util.ExamResultRecordUtil;
import com.mnt.preg.examitem.util.FoodScoreUtil;
import com.mnt.preg.examitem.util.PregnancyDRIs;
import com.mnt.preg.master.service.basic.FoodService;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.service.CodeService;
import com.mnt.preg.web.constants.PublicConstant;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pdf.DietReportPdf;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 膳食评估Controller
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
@Controller
public class DietFoodController extends BaseController {

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
     * 初始化会员信息调查
     * 
     * @author zcq
     * @param custId
     * @param model
     * @return
     */
    @RequestMapping(value = DietFoodMapping.INIT_MEALS_CUST_SURVEY)
    public String initMealsCustSurvey(String custId, Model model) {
        try {
            CustomerPojo customerDto = customerService.getCustomer(custId);
            model.addAttribute("customerDto", NetJsonUtils.objectToJson(customerDto));
        } catch (Exception e) {
            return this.getErrorPage(model, "操作出错了，请重新操作");
        }
        return DietFoodPageName.MEALS_CUSTOMER;
    }

    /**
     * 跳转到饮食填写记录页面
     * 
     * @author zcq
     * @param foodRecordId
     * @param model
     * @return
     */
    @RequestMapping(value = DietFoodMapping.INIT_MEALS_SURVEY)
    public String intMealsSurvey(String custId, String id, String diagnosisId, Model model) {
        String foodRecordId = this.addFoodRecord(custId);
        model.addAttribute("foodRecordId", foodRecordId);
        model.addAttribute("inspectId", id);
        model.addAttribute("diagnosisId", diagnosisId);
        model.addAttribute("mealsTypeVos",
                NetJsonUtils.listToJsonArray(codeCache.getCodeListCache("MEALSTYPE".toLowerCase(),
                        "MEALSTYPE".toLowerCase())));
        String custName = customerService.getCustomer(custId).getCustName();
        model.addAttribute("custName", custName);
        return CustomerPageName.MEALS_SURVEY;
    }

    public String addFoodRecord(String custId) {
        CustomerPojo customerVo = customerService.getCustomer(custId);
        PregFoodRecord foodRecord = TransformerUtils.transformerProperties(PregFoodRecord.class, customerVo);
        foodRecord.setCustAge(customerVo.getCustAge());
        foodRecord.setRecordDatetime(new Date());
        return pregDietFoodRecordService.addFoodRecord(foodRecord);
    }

    /**
     * 食物检索
     * 
     * @param request
     * @param condition
     *            检索条件
     * @return
     */
    // @RequestMapping(value = DietFoodMapping.RECORD_FOOD_QUERY)
    // public @ResponseBody
    // PageCondition queryFood(FoodCondition condition) {
    // FoodCondition queryFoodBaseForPage = foodService.queryFoodBaseForPage(condition);
    // return queryFoodBaseForPage;
    // }

    /**
     * 记录膳食评估用餐时间、感受和饮食明细
     * 
     * @param request
     * @param recordForm
     *            记录信息
     * @return
     */
    @RequestMapping(value = DietFoodMapping.RECORD_MEALS_SURVEY)
    public @ResponseBody WebResult<String> addDietRecord(DietRecordForm form) {
        WebResult<String> webResult = new WebResult<String>();
        PregDietRecordPojo recordBo = new PregDietRecordPojo();
        try {
            // BeanUtils.copyProperties(recordBo, form);
            // 初始化recordBo
            recordBo.setDiagnosisId(form.getDiagnosisId());
            recordBo.setFoodRecordId(form.getFoodRecordId());
            recordBo.setFoodFrequencyList(new ArrayList<PregFoodFrequencyPojo>());// 饮食频率
            recordBo.setFoodDetailsList(new ArrayList<PregFoodDetailsPojo>());// 膳食明细
            recordBo.setFoodFeelTimeList(new ArrayList<PregFoodFeelTimePojo>());// 用餐时间和感受
            // 记录饮食习惯
            PregHabitPojo foodHabitBo = new PregHabitPojo();
            foodHabitBo.setPregnancyRule(form.getPregnancyRule());
            foodHabitBo.setPregnancySpeed(form.getPregnancySpeed());
            foodHabitBo.setPregnancyFrequency(form.getPregnancyFrequency());
            List<String> likeList = form.getPregnancyLike();
            List<String> feelList = form.getPregnancyFeel();
            List<String> avoidList = form.getPregnancyAvoid();
            List<String> recommendList = form.getPregnancyRecommend();
            if (CollectionUtils.isNotEmpty(likeList)) {
                String likeStr = "";
                for (String pregnancyLike : likeList) {
                    likeStr += "," + pregnancyLike;
                }
                likeStr = likeStr.replaceFirst(",", "");
                foodHabitBo.setPregnancyLike(likeStr);
            }
            if (CollectionUtils.isNotEmpty(feelList)) {
                String feelStr = "";
                for (String pregnancyFeel : feelList) {
                    feelStr += "," + pregnancyFeel;
                }
                feelStr = feelStr.replaceFirst(",", "");
                foodHabitBo.setPregnancyFeel(feelStr);
            }
            if (CollectionUtils.isNotEmpty(avoidList)) {
                String avoidStr = "";
                for (String pregnancyAvoid : avoidList) {
                    avoidStr += "," + pregnancyAvoid;
                }
                avoidStr = avoidStr.replaceFirst(",", "");
                foodHabitBo.setPregnancyAvoid(avoidStr);
            }
            if (CollectionUtils.isNotEmpty(recommendList)) {
                String recommendStr = "";
                for (String pregnancyRecommend : recommendList) {
                    recommendStr += "," + pregnancyRecommend;
                }
                recommendStr = recommendStr.replaceFirst(",", "");
                foodHabitBo.setPregnancyRecommend(recommendStr);
            }
            recordBo.setPregnancyHabitBo(foodHabitBo);
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
            // 记录饮食摄入频率
            List<PregFoodFrequencyPojo> foodFrequencyList = form.getFoodFrequencyList();
            if (CollectionUtils.isNotEmpty(foodFrequencyList)) {
                for (PregFoodFrequencyPojo foodFrequencyBo : foodFrequencyList) {
                    recordBo.getFoodFrequencyList().add(foodFrequencyBo);
                }
            }
            recordBo.setDiagnosisId(form.getDiagnosisId());
            this.saveDietRecord(recordBo);
            webResult.setSuc(form.getFoodRecordId());
        } catch (Exception e) {
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 查看膳食分析报告
     * 
     * @author zcq
     * @param foodRecordId
     * @param diagnosisId
     * @return
     */
    @RequestMapping(value = DietFoodMapping.MEALS_REPORT)
    public @ResponseBody WebResult<String> getDietReportPdf(String id) {
        WebResult<String> webResult = new WebResult<String>();
        webResult.setSuc(this.getDietReport(id));
        return webResult;
    }

    /****************************************************** 原PregDietFoodBusness中内容 *************************************************************/
    public void saveDietRecord(PregDietRecordPojo dietRecordBo) {
        // 第一步：记录数据
        String foodRecordId = dietRecordBo.getFoodRecordId();
        // 保存饮食习惯
        PregHabit pregnancyHabit = TransformerUtils.transformerProperties(PregHabit.class,
                dietRecordBo.getPregnancyHabitBo());
        pregnancyHabit.setFoodRecordId(foodRecordId);
        pregDietFoodRecordService.addPregnancyHabit(pregnancyHabit);
        // 保存用餐明细
        List<PregFoodDetails> foodDetailsList = TransformerUtils.transformerList(PregFoodDetails.class,
                dietRecordBo.getFoodDetailsList());
        pregDietFoodRecordService.addFoodDetails(foodDetailsList, foodRecordId);
        // 保存饮食摄入频率
        List<PregFoodFrequency> foodFrequencyList = TransformerUtils.transformerList(PregFoodFrequency.class,
                dietRecordBo.getFoodFrequencyList());
        pregDietFoodRecordService.addFoodFrequency(foodFrequencyList, foodRecordId);

        // 第二步：数据分析
        PregDietReportPojo dietReportVo = this.getDietAnalyseResult(foodRecordId, dietRecordBo.getDiagnosisId());

        // 第三步：保存结果记录（检测项目记录表）
        ExamResultRecord examResultRecord = new ExamResultRecord();
        examResultRecord.setCustId(dietReportVo.getFoodRecord().getCustId());
        examResultRecord.setExamCode(foodRecordId);
        examResultRecord.setExamDate(new Date());
        examResultRecord.setExamCategory(ExamItemConstant.EXAM_ITEM_CODE.B00001);

        String examId = examResultRecordUtil.addExamResultRecordForExam(examResultRecord,
                ExamItemConstant.EXAM_ITEM_TABLE.B00001);

        // 第四步：保存分析结果（指标结果表）
        List<ExamItemPojo> detailList = this.saveExamItem(dietReportVo);
        examItemUtil.addExamItems(ExamItemConstant.EXAM_ITEM_TABLE.B00001, examId, detailList);
    }

    public String getDietReport(String inspectId) {
        // 第一步：实例化对象
        DietReportPdf dietReportPdf = new DietReportPdf(){

            @Override
            public PregDietReportPojo beforeCreatePdf(ReportForm reportForm) {
                PregDietReportPojo dietReportVo = new PregDietReportPojo();
                // 获取分析结果信息
                PregDiagnosisItemsVo diagnosisItem = pregDiagnosisService.getDiagnosisItem(reportForm.getReportCode());
                String foodRecordId = diagnosisItem.getResultCode();
                dietReportVo.setDiagnosis(pregDiagnosisService.getDiagnosis(diagnosisItem.getDiagnosisId()));// 接诊信息
                dietReportVo.setDiagnosisItem(diagnosisItem);
                dietReportVo.setFoodRecord(pregDietFoodRecordService.getFoodRecord(foodRecordId));// 分析结果
                dietReportVo.setExamMap(examItemUtil.queryExamItemMapByResultCode(
                        ExamItemConstant.EXAM_ITEM_TABLE.B00001,
                        foodRecordId));// 指标结果
                dietReportVo.setInsId(getLoginUser().getUserPojo().getCreateInsId());// 机构号
                return dietReportVo;
            }
        };
        // 第二步：创建PDF
        String dietPdfpath = dietReportPdf.create(inspectId);

        return dietPdfpath;
    }

    // -----------------------------------------------------自定义方法---------------------------------------------------

    private PregDietReportPojo getDietAnalyseResult(String foodRecordId, String diagnosisId) {
        PregDietReportPojo dietReportVo = new PregDietReportPojo();
        // 体检信息
        PregDiagnosisPojo diagnosis = pregDiagnosisService.getDiagnosis(diagnosisId);
        // 查询设置接诊信息
        dietReportVo.setDiagnosis(diagnosis);
        // 查询设置膳食评估记录信息
        dietReportVo.setFoodRecord(pregDietFoodRecordService.getFoodRecord(foodRecordId));
        // 查询设置饮食习惯
        PregHabitPojo pregnancyHabitVo = pregDietFoodRecordService.getPregnancyHabit(foodRecordId);
        dietReportVo.setPregnancyHabitVo(pregnancyHabitVo);
        // 查询设置饮食频率
        List<PregFoodFrequencyPojo> foodFrequencyList = pregDietFoodRecordService.queryFoodFrequency(foodRecordId);
        dietReportVo.setFoodFrequencyList(foodFrequencyList);
        // 查询设置用餐明细
        dietReportVo.setFoodDetailsList(pregDietFoodRecordService.queryFoodDetails(foodRecordId));
        // 查询设置膳食分析信息
        dietReportVo.setElement(pregDietFoodAnalyseService.getElementSum(foodRecordId));
        dietReportVo.setEnergy(pregDietFoodAnalyseService.getEachMealEnergy(foodRecordId));
        dietReportVo.setGl(pregDietFoodAnalyseService.getGL(foodRecordId));
        dietReportVo.setProtidAndFat(pregDietFoodAnalyseService.getProtidAndFatType(foodRecordId));
        dietReportVo.setDris(pregDietFoodAnalyseService.getPregnancyDRIs(diagnosis.getDiagnosisPregnantStage(),
                diagnosis.getDiagnosisCustHeight().doubleValue()));

        return dietReportVo;
    }

    private Map<String, String> codeMap = new HashMap<String, String>();

    private List<ExamItemPojo> saveExamItem(PregDietReportPojo dietReportVo) {
        List<CodePojo> codeList = codeService.queryCodeAll();
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo code : codeList) {
                codeMap.put(code.getCodeKind() + code.getCodeValue(), code.getCodeName());
            }
        }
        List<ExamItemPojo> detailList = new ArrayList<ExamItemPojo>();

        PregHabitPojo foodHabitVo = dietReportVo.getPregnancyHabitVo();// 饮食习惯
        List<PregFoodFrequencyPojo> foodFrequencyList = dietReportVo.getFoodFrequencyList();// 饮食频率
        List<PregFoodDetailsPojo> foodList = dietReportVo.getFoodDetailsList();// 饮食明细
        PregDietAnalysePojo element = dietReportVo.getElement();// 饮食中每种元素的量
        PregDietAnalysePojo energy = dietReportVo.getEnergy();// 餐次能量
        PregDietAnalysePojo protidAndFat = dietReportVo.getProtidAndFat();// 优质蛋白
        PregDietAnalysePojo gl = dietReportVo.getGl();// 餐次GL值
        PregnancyDRIs dirs = dietReportVo.getDris().getDris();// 标准值
        String pregnancyStage = dietReportVo.getDiagnosis().getDiagnosisPregnantStage();
        // 第一部分
        String[] str1 = getResult1(dirs.getFoodEnergy(), transferString(energy.getFoodEnergy(), 1), "part1");
        detailList.add(getExamItem("NU0100001", "一日总能量", str1[0], str1[1], str1[2], str1[3], "kcal"));
        String[] str2 = getResult1(dirs.getEnergyBreak(), transferString(energy.getEnergyBreak(), 1), "part1");
        detailList.add(getExamItem("NU0100002", "早餐供能（含加餐）", str2[0], str2[1], str2[2], str2[3], "kcal"));
        String[] str3 = getResult1(dirs.getEnergyLunch(), transferString(energy.getEnergyLunch(), 1), "part1");
        detailList.add(getExamItem("NU0100003", "午餐供能（含加餐）", str3[0], str3[1], str3[2], str3[3], "kcal"));
        String[] str4 = getResult1(dirs.getEnergySupper(), transferString(energy.getEnergySupper(), 1), "part1");
        detailList.add(getExamItem("NU0100004", "晚餐供能（含加餐）", str4[0], str4[1], str4[2], str4[3], "kcal"));
        // 第二部分
        String[] str5 = getResult1("50~60", transferString(element.getFoodCbrPercent(), 0) + "%", "part2.1");
        detailList.add(getExamItem("NU0100005", "总碳水化合物", "50%-65%###——", str5[1], str5[2], str5[3], ""));
        String[] str6 = getResult1(dirs.getFoodProtid(), transferString(element.getFoodProtid(), 1), "part2.1");
        detailList.add(getExamItem("NU0100006", "总蛋白质", "——###" + dirs.getFoodProtid(), str6[1], str6[2],
                str6[3], "g"));
        String[] str7 = getResult1("20~30", transferString(element.getFoodFatPercent(), 0) + "%", "part2.1");
        detailList.add(getExamItem("NU0100007", "总脂肪", "20%-30%###——", str7[1], str7[2], str7[3], ""));
        String[] str8 = getResult1(dirs.getFoodProtidYes(), transferString(protidAndFat.getFoodProtidYes(), 1),
                "part2.1");
        detailList.add(getExamItem("NU0100008", "优质蛋白质", "——###" + dirs.getFoodProtidYes(), str8[1],
                str8[2], str8[3], "g"));
        String[] str9 = getResult1("8", transferString(protidAndFat.getFoodFatAnimal(), 1), "part2.2");
        detailList.add(getExamItem("NU0100009", "饱和脂肪酸", "<8g###——", str9[1], str9[2], str9[3], "g"));
        String[] str10 = getResult1("24", transferString(element.getFoodDf(), 1), "part2.3");
        detailList.add(getExamItem("NU0100010", "膳食纤维", "——###24g", str10[1], str10[2], str10[3], "g"));
        // 第三部分
        Map<String, String> elementMap = PublicConstant.pregExtenderElementMap().get(pregnancyStage);
        String[] str11 = getResult2(elementMap.get("E00005"), transferString(element.getFoodVa(), 1));
        detailList.add(getExamItem("NU0100011", "维生素A", str11[0], str11[1], str11[2], str11[3], "ugRAE"));
        String[] str12 = getResult2(elementMap.get("E00018"), transferString(element.getFoodVc(), 1));
        detailList.add(getExamItem("NU0100012", "维生素C", str12[0], str12[1], str12[2], str12[3], "mg"));
        String[] str13 = getResult2(elementMap.get("E00007"), transferString(element.getFoodVe(), 1));
        detailList.add(getExamItem("NU0100013", "维生素E", str13[0], str13[1], str13[2], str13[3],
                "mga-TE"));
        String[] str14 = getResult2(elementMap.get("E00009"), transferString(element.getFoodVb1(), 1));
        detailList.add(getExamItem("NU0100014", "硫胺素", str14[0], str14[1], str14[2], str14[3], "mg"));
        String[] str15 = getResult2(elementMap.get("E00010"), transferString(element.getFoodVb2(), 1));
        detailList.add(getExamItem("NU0100015", "核黄素", str15[0], str15[1], str15[2], str15[3], "mg"));
        String[] str16 = getResult2(elementMap.get("E00015"), transferString(element.getFoodAf(), 1));
        detailList.add(getExamItem("NU0100016", "烟酸", str16[0], str16[1], str16[2], str16[3], "mg"));
        String[] str17 = getResult2(elementMap.get("E00019"), transferString(element.getFoodEca(), 1));
        detailList.add(getExamItem("NU0100017", "钙", str17[0], str17[1], str17[2], str17[3], "mg"));
        String[] str18 = getResult2(elementMap.get("E00021"), transferString(element.getFoodEk(), 1));
        detailList.add(getExamItem("NU0100018", "钾", str18[0], str18[1], str18[2], str18[3], "mg"));
        String[] str19 = getResult2(elementMap.get("E00022"), transferString(element.getFoodEna(), 1));
        detailList.add(getExamItem("NU0100019", "钠", str19[0], str19[1], str19[2], str19[3], "mg"));
        String[] str20 = getResult2(elementMap.get("E00023"), transferString(element.getFoodEmg(), 1));
        detailList.add(getExamItem("NU0100020", "镁", str20[0], str20[1], str20[2], str20[3], "mg"));
        String[] str21 = getResult2(elementMap.get("E00024"), transferString(element.getFoodEfe(), 1));
        detailList.add(getExamItem("NU0100021", "铁", str21[0], str21[1], str21[2], str21[3], "mg"));
        String[] str22 = getResult2(elementMap.get("E00026"), transferString(element.getFoodEzn(), 1));
        detailList.add(getExamItem("NU0100022", "锌", str22[0], str22[1], str22[2], str22[3], "mg"));
        // 第四部分
        String[] str23 = getResult1("10", transferString(gl.getBreakGl(), 0), "part2.2");
        detailList.add(getExamItem("NU0100023", "早餐GL值（含加餐）", "GL≤10", str23[1], str23[2], str23[3], ""));
        String[] str24 = getResult1("10", transferString(gl.getLunchGl(), 0), "part2.2");
        detailList.add(getExamItem("NU0100024", "午餐GL值（含加餐）", "GL≤10", str24[1], str24[2], str24[3], ""));
        String[] str25 = getResult1("10", transferString(gl.getSupperGl(), 0), "part2.2");
        detailList.add(getExamItem("NU0100025", "晚餐GL值（含加餐）", "GL≤10", str25[1], str25[2], str25[3], ""));
        // 第五部分
        if (CollectionUtils.isNotEmpty(foodFrequencyList)) {
            for (PregFoodFrequencyPojo frequency : foodFrequencyList) {
                BigDecimal dayIntake = BigDecimal.ZERO;
                String foodType = frequency.getFoodType();
                String averageIntakeType = frequency.getAverageIntakeType();
                BigDecimal intake = (frequency.getAverageIntake() == null) ? BigDecimal.ZERO : frequency
                        .getAverageIntake();
                BigDecimal amount = BigDecimal.valueOf((frequency.getAverageNumber() == null) ? 0 : frequency
                        .getAverageNumber());
                if ("day".equals(frequency.getFoodCycle())) {
                    dayIntake = intake.multiply(amount);
                }
                if ("week".equals(frequency.getFoodCycle())) {
                    dayIntake = intake.multiply(amount).divide(BigDecimal.valueOf(7), BigDecimal.ROUND_HALF_UP);
                }
                String resultIntake = transferString(BigDecimal.valueOf(dayIntake.doubleValue()), 1);
                String unitName = "g";
                if ("water".equals(foodType)) {
                    String[] str26 = getResult1(dirs.getWater(), resultIntake, "part2.1");
                    detailList.add(getExamItem("NU0100026", "饮水", str26[0], str26[1], str26[2],
                            str26[3], "ml"));
                } else if ("gulei".equals(foodType)) {
                    String[] str27 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "g";
                        str27 = getResult1(dirs.getGulei().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str27 = getResult1(dirs.getGulei().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100027", "谷类", str27[0], str27[1], str27[2],
                            str27[3], unitName));
                } else if ("shulei".equals(foodType)) {
                    String[] str28 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "g";
                        str28 = getResult1(dirs.getShulei().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str28 = getResult1(dirs.getShulei().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100028", "薯类", str28[0], str28[1], str28[2],
                            str28[3], unitName));
                } else if ("shucai".equals(foodType)) {
                    String[] str29 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "g";
                        str29 = getResult1(dirs.getShucai().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str29 = getResult1(dirs.getShucai().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100029", "蔬菜", str29[0], str29[1], str29[2],
                            str29[3], unitName));
                } else if ("shuiguo".equals(foodType)) {
                    String[] str30 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "g";
                        str30 = getResult1(dirs.getShuiguo().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str30 = getResult1(dirs.getShuiguo().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100030", "水果", str30[0], str30[1], str30[2],
                            str30[3], unitName));
                } else if ("yuroudan".equals(foodType)) {
                    String[] str31 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "g";
                        str31 = getResult1(dirs.getYuroudan().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str31 = getResult1(dirs.getYuroudan().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100031", "鱼肉蛋", str31[0], str31[1], str31[2],
                            str31[3], unitName));
                } else if ("dadou".equals(foodType)) {
                    String[] str32 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "g";
                        str32 = getResult1(dirs.getDadou().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str32 = getResult1(dirs.getDadou().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100032", "大豆类", str32[0], str32[1], str32[2],
                            str32[3], unitName));
                } else if ("jianguo".equals(foodType)) {
                    String[] str33 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "g";
                        str33 = getResult1(dirs.getJianguo().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str33 = getResult1(dirs.getJianguo().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100033", "坚果", str33[0], str33[1], str33[2],
                            str33[3], unitName));
                } else if ("rulei".equals(foodType)) {
                    String[] str34 = null;
                    if ("g".equals(averageIntakeType)) {
                        unitName = "ml";
                        str34 = getResult1(dirs.getRuzhipin().split("#")[0], resultIntake, "part2.1");
                    } else {
                        unitName = "份";
                        str34 = getResult1(dirs.getRuzhipin().split("#")[1], resultIntake, "part2.1");
                    }
                    detailList.add(getExamItem("NU0100034", "乳类", str34[0], str34[1], str34[2],
                            str34[3], unitName));
                }
            }
        }
        // 第六部分
        String habitRule = foodHabitVo.getPregnancyRule();
        String habitSpeed = foodHabitVo.getPregnancySpeed();
        String habitFrequency = foodHabitVo.getPregnancyFrequency();
        String habitLike = foodHabitVo.getPregnancyLike();
        String habitFeel = foodHabitVo.getPregnancyFeel();
        String habitAvoid = foodHabitVo.getPregnancyAvoid();
        String habitRecommend = foodHabitVo.getPregnancyRecommend();
        String[] str35 = getResult3(pregnancyStage, habitRecommend, 1);
        detailList.add(getExamItem("NU0100035", "叶酸", "", str35[0], str35[1], str35[2], ""));
        String[] str36 = getResult3(pregnancyStage, habitRecommend, 2);
        detailList.add(getExamItem("NU0100036", "铁", "", str36[0], str36[1], str36[2], ""));
        String[] str37 = getResult3(pregnancyStage, habitRecommend, 3);
        detailList.add(getExamItem("NU0100037", "碘", "", str37[0], str37[1], str37[2], ""));
        String[] str38 = getResult3(pregnancyStage, habitRecommend, 4);
        detailList.add(getExamItem("NU0100038", "ω-3脂肪酸", "", str38[0], str38[1], str38[2], ""));
        String[] str39 = getResult3(pregnancyStage, habitRecommend, 5);
        detailList.add(getExamItem("NU0100039", "粗粮占比", "", str39[0], str39[1], str39[2], ""));
        String[] str40 = getResult3(pregnancyStage, habitRule, 6);
        detailList.add(getExamItem("NU0100040", "用餐频率", "", str40[0], str40[1], str40[2], ""));
        String[] str41 = getResult3(pregnancyStage, habitSpeed, 7);
        detailList.add(getExamItem("NU0100041", "用餐时间", "", str41[0], str41[1], str41[2], ""));
        String[] str42 = getResult3(pregnancyStage, habitFeel, 8);
        detailList.add(getExamItem("NU0100042", "用餐感受", "", str42[0], str42[1], str42[2], ""));
        String[] str43 = getResult3(pregnancyStage, habitFrequency, 9);
        detailList.add(getExamItem("NU0100043", "在外用餐", "", str43[0], str43[1], str43[2], ""));
        String[] str44 = getResult3(pregnancyStage, habitLike, 10);
        detailList.add(getExamItem("NU0100044", "不良饮食", "", str44[0], str44[1], str44[2], ""));
        String[] str45 = getResult3(pregnancyStage, habitAvoid, 11);
        detailList.add(getExamItem("NU0100045", "烟酒情况", "", str45[0], str45[1], str45[2], ""));
        String[] str46 = getResult3(pregnancyStage, habitRecommend, 12);
        detailList.add(getExamItem("NU0100046", "饱和脂肪酸", "", str46[0], str46[1], str46[2], ""));
        String[] str47 = getResult3(pregnancyStage, habitAvoid, 13);
        detailList.add(getExamItem("NU0100047", "用油量", "", str47[0], str47[1], str47[2], ""));
        String[] str48 = getResult3(pregnancyStage, habitAvoid, 14);
        detailList.add(getExamItem("NU0100048", "用盐量", "", str48[0], str48[1], str48[2], ""));
        // 第七部分
        String breakStr = "";
        String breakAddStr = "";
        String lunchStr = "";
        String lunchAddStr = "";
        String supperStr = "";
        String supperAddStr = "";
        if (CollectionUtils.isNotEmpty(foodList)) {
            for (PregFoodDetailsPojo food : foodList) {
                String mealsId = food.getMealsId();
                String foodName = food.getFoodName();
                Integer foodAmout = food.getFoodAmount();
                if (FoodScoreUtil.BREAKFAST_MEALS_CODE.equals(mealsId)) {
                    breakStr += "、" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.MORNING_EXTRA_MEALS_CODE.equals(mealsId)) {
                    breakAddStr += "、" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.LUNCH_MEALS_CODE.equals(mealsId)) {
                    lunchStr += "、" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.AFTERNOON_EXTRA_MEALS_CODE.equals(mealsId)) {
                    lunchAddStr += "、" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.SUPPER_MEALS_CODE.equals(mealsId)) {
                    supperStr += "、" + foodName + foodAmout + "g";
                } else if (FoodScoreUtil.NIGHT_EXTRE_MEALS_CODE.equals(mealsId)) {
                    supperAddStr += "、" + foodName + foodAmout + "g";
                }
            }
        }
        detailList.add(getExamItem("NU0100049", "早餐", "", breakStr.replaceFirst("、", ""), "", "0", ""));
        detailList.add(getExamItem("NU0100050", "上午加餐", "", breakAddStr.replaceFirst("、", ""), "", "0",
                ""));
        detailList.add(getExamItem("NU0100051", "午餐", "", lunchStr.replaceFirst("、", ""), "", "0", ""));
        detailList.add(getExamItem("NU0100052", "下午加餐", "", lunchAddStr.replaceFirst("、", ""), "", "0",
                ""));
        detailList.add(getExamItem("NU0100053", "晚餐", "", supperStr.replaceFirst("、", ""), "", "0", ""));
        detailList
                .add(getExamItem("NU0100054", "夜宵", "", supperAddStr.replaceFirst("、", ""), "", "0", ""));
        // 新版增加
        detailList.add(getExamItem("NU0100055", "碳水化合物供能比", "55%~65%",
                transferString(element.getFoodCbrPercent(), 0) + "%", "", "", "%"));
        detailList.add(getExamItem("NU0100056", "碳水化合物摄入量", "", element.getFoodCbr(), "", "", "g"));
        detailList.add(getExamItem("NU0100057", "蛋白质供能比", "15%~20%",
                transferString(element.getFoodProtidPercent(), 0) + "%", "", "", "%"));
        detailList.add(getExamItem("NU0100058", "蛋白质摄入量", "", transferString(element.getFoodProtid(), 0),
                "", "", "%"));
        detailList.add(getExamItem("NU0100059", "脂肪供能比", "20%~30%",
                transferString(element.getFoodFatPercent(), 0) + "%", "", "", "%"));
        detailList.add(getExamItem("NU0100060", "脂肪摄入量", "", transferString(element.getFoodFat(), 0),
                "", "", "%"));

        return detailList;
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
                    result[2] = "不足";
                    result[3] = "4";
                } else if (val > max * 1.1) {
                    result[2] = "超出";
                    result[3] = "4";
                } else {
                    result[2] = "适宜";
                    result[3] = "0";
                }
            } else {
                double stand = Double.valueOf(standard);
                if (val < stand * 0.9) {
                    result[2] = "不足";
                    result[3] = "4";
                } else if (val > stand * 1.1) {
                    result[2] = "超出";
                    result[3] = "4";
                } else {
                    result[2] = "适宜";
                    result[3] = "0";
                }
            }
        }
        if ("part2.1".equals(type)) {
            if (standard.indexOf("~") > -1) {
                double min = Double.valueOf(standard.split("~")[0]);
                double max = Double.valueOf(standard.split("~")[1]);
                if (val < min) {
                    result[2] = "不足";
                    result[3] = "4";
                } else if (val >= min && val <= max) {
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "超出";
                    result[3] = "4";
                }
            } else {
                double stand = Double.valueOf(standard);
                if (val < stand) {
                    result[2] = "不足";
                    result[3] = "4";
                } else if (val == stand) {
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "超出";
                    result[3] = "4";
                }
            }
        } else if ("part2.2".equals(type)) {
            double stand = Double.valueOf(standard);
            if (val <= stand) {
                result[2] = "适宜";
                result[3] = "0";
            } else {
                result[2] = "超出";
                result[3] = "4";
            }
        } else if ("part2.3".equals(type)) {
            double stand = Double.valueOf(standard);
            if (val >= stand) {
                result[2] = "适宜";
                result[3] = "0";
            } else {
                result[2] = "不足";
                result[3] = "4";
            }
        } else if ("part5".equals(type)) {
            if (standard.indexOf("~") > -1) {
                double min = Double.valueOf(standard.split("~")[0]);
                double max = Double.valueOf(standard.split("~")[1]);
                if (val < min) {
                    result[2] = "不足";
                    result[3] = "4";
                } else if (val >= min && val <= max) {
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "超出";
                    result[3] = "4";
                }
            } else {
                double stand = Double.valueOf(standard);
                if (val < stand) {
                    result[2] = "不足";
                    result[3] = "4";
                } else if (val == stand) {
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "超出";
                    result[3] = "4";
                }
            }
        }
        return result;
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
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "超出";
                    result[3] = "4";
                }
            } else {
                if (val >= mathArray[0]) {
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "不足";
                    result[3] = "4";
                }
            }
        } else if (count == 2) {
            if (isUL == 1) {
                if (val < mathArray[0]) {
                    result[2] = "不足";
                    result[3] = "4";
                } else if (val <= mathArray[1] && val >= mathArray[0]) {
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "超出";
                    result[3] = "4";
                }
            } else {
                if (val >= mathArray[0]) {
                    result[2] = "适宜";
                    result[3] = "0";
                } else {
                    result[2] = "不足";
                    result[3] = "4";
                }
            }
        } else if (count == 3) {
            if (val < mathArray[0]) {
                result[2] = "不足";
                result[3] = "4";
            } else if (val <= mathArray[2] && val >= mathArray[0]) {
                result[2] = "适宜";
                result[3] = "0";
            } else {
                result[2] = "超出";
                result[3] = "4";
            }
        }
        return result;
    }

    private String[] getResult3(String pregnancy, String value, int number) {
        String[] result = {"", "", ""};
        if (StringUtils.isEmpty(value)) {
            result[1] = "";
            result[2] = "4";
            return result;
        }
        if (number == 1) {// 叶酸
            if (value.indexOf("4") > -1) {
                result = new String[] {"占比>2/3", "适宜", "0"};
            } else {
                result = new String[] {"占比不足2/3", "可能不足", "4"};
            }
        } else if (number == 2) {// 铁
            if (value.indexOf("1") > -1) {
                if ("pregnancy_pre".equals(pregnancy)) {
                    result = new String[] {"——", "——", "0"};
                } else {
                    result = new String[] {"1-2次/周", "适宜", "0"};
                }
            } else {
                if ("pregnancy_pre".equals(pregnancy)) {
                    result = new String[] {"——", "——", "0"};
                } else {
                    result = new String[] {"不足1-2次/周", "可能不足", "4"};
                }
            }
        } else if (number == 3) {// 碘
            if (value.indexOf("2") > -1) {
                result = new String[] {"1-2次/周", "适宜", "0"};
            } else {
                result = new String[] {"不足1-2次/周", "可能不足", "4"};
            }
        } else if (number == 4) {// ω-3
            if (value.indexOf("3") > -1) {
                result = new String[] {"2-3次/周", "适宜", "0"};
            } else {
                result = new String[] {"不足2-3次/周", "可能不足", "4"};
            }
        } else if (number == 5) {// 粗粮占比
            if (value.indexOf("5") > -1) {
                result = new String[] {"占比>1/3", "可能超出", "4"};
            } else {
                result = new String[] {"不足占比>1/3", "适宜", "0"};
            }
        } else if (number == 6) {// 用餐是否规律
            if (value.indexOf("1") > -1 || value.indexOf("3") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_RULE" + value), "适宜", "0"};
            } else {
                result = new String[] {codeMap.get("PREGNANCY_RULE" + value), "建议调整", "4"};
            }
        } else if (number == 7) {// 用餐时间
            if (value.indexOf("1") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_SPEED" + value), "过快", "4"};
            } else if (value.indexOf("2") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_SPEED" + value), "稍快", "4"};
            } else if (value.indexOf("3") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_SPEED" + value), "适宜", "0"};
            } else if (value.indexOf("4") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_SPEED" + value), "稍慢", "4"};
            } else if (value.indexOf("5") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_SPEED" + value), "过慢", "4"};
            }
        } else if (number == 8) {// 用餐感受
            if ("1".equals(value)) {
                result = new String[] {"五成饱", "建议七成饱", "4"};
            } else if ("2".equals(value)) {
                result = new String[] {"六成饱", "建议七成饱", "4"};
            } else if ("3".equals(value)) {
                result = new String[] {"七成饱", "适宜", "0"};
            } else if ("4".equals(value)) {
                result = new String[] {"八成饱", "建议七成饱", "4"};
            } else if ("5".equals(value)) {
                result = new String[] {"九成饱", "建议七成饱", "4"};
            } else if ("6".equals(value)) {
                result = new String[] {"十分饱", "建议七成饱", "4"};
            }
        } else if (number == 9) {// 在外用餐频率
            if (value.indexOf("1") > -1 || value.indexOf("2") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_FREQUENCY" + value), "适宜", "0"};
            } else if (value.indexOf("3") > -1 || value.indexOf("4") > -1) {
                result = new String[] {codeMap.get("PREGNANCY_FREQUENCY" + value), "建议减少", "4"};
            }
        } else if (number == 10) {// 不良饮食
            String[] freArray = value.split(",");
            String freStr = "";
            for (String fre : freArray) {
                freStr += "、" + codeMap.get("PREGNANCY_LIKE" + fre);
            }
            freStr = freStr.replaceFirst("、", "");
            if (value.indexOf("1") > -1) {
                result = new String[] {freStr, "适宜", "0"};
            } else {
                result = new String[] {freStr, "建议减少", "4"};
            }
        } else if (number == 11) {// 烟酒
            if (value.indexOf("1") > -1) {
                result = new String[] {"无", "适宜", "0"};
            } else {
                result = new String[] {"有", "尽量避免", "4"};
            }
        } else if (number == 12) {// 饱和脂肪酸
            if (value.indexOf("2") > -1) {
                result = new String[] {"无", "适宜", "0"};
            } else {
                result = new String[] {"有", "尽量避免", "4"};
            }
        } else if (number == 13) {// 用油量
            if (value.indexOf("3") > -1) {
                result = new String[] {"不足25g/天", "适宜", "0"};
            } else {
                result = new String[] {"超出25g/天", "建议减少", "4"};
            }
        } else if (number == 14) {// 用盐量
            if (value.indexOf("4") > -1) {
                result = new String[] {"不足6g/天", "适宜", "0"};
            } else {
                result = new String[] {"超出6g/天", "建议减少", "4"};
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
        detail.setItemString(itemString);
        detail.setItemResult(itemResult);
        detail.setItemCompare(itemCompare);
        detail.setItemUnit(itemUnit);
        return detail;
    }

    private String transferString(Object value, Integer scale) {
        String result = "";
        if (value == null) {
            return "0";
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

}
