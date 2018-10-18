/**
 * StatisticController.java    1.0  2018年8月10日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.condition.BirthEndingBabyInfoCondition;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;
import com.mnt.preg.examitem.condition.InbodyCondition;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.examitem.service.PregInbodyService;
import com.mnt.preg.examitem.util.ExamItemUtil;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.pojo.items.ItemPojo;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.master.service.items.ItemService;
import com.mnt.preg.platform.condition.DiagnosisCondition;
import com.mnt.preg.platform.condition.DiagnosisHospitalItemCondition;
import com.mnt.preg.platform.condition.DiagnosisPrescriptionCondition;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.service.DiagnosisHospitalItemService;
import com.mnt.preg.platform.service.PregDiagnosisDiseaseService;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.statistic.condition.SearchCountCondition;
import com.mnt.preg.statistic.condition.StatisticForm;
import com.mnt.preg.statistic.excel.ExportExcel;
import com.mnt.preg.statistic.mapping.StatisticMapping;
import com.mnt.preg.statistic.mapping.StatisticPageName;
import com.mnt.preg.statistic.pojo.BirthEndingInfoPojo;
import com.mnt.preg.statistic.pojo.CustomerInfoPojo;
import com.mnt.preg.statistic.pojo.DiagnosisInfoPojo;
import com.mnt.preg.statistic.pojo.StatiscBirthResultPojo;
import com.mnt.preg.statistic.pojo.StatisticCustomerPojo;
import com.mnt.preg.statistic.pojo.StatisticDataPojo;
import com.mnt.preg.statistic.service.StatisticService;
import com.mnt.preg.system.entity.CodeInfo;
import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.service.UserService;
import com.mnt.preg.web.cache.CodeCache;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.pojo.WebResult;

/**
 * 数据统计平台Controller
 * 
 * @author lipeng
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月10日 lipeng 初版
 */
@Controller
public class StatisticController extends BaseController {

    @Autowired
    public HttpServletResponse response;

    @Resource
    private StatisticService statisticService;

    @Resource
    private PregDiagnosisService pregDiagnosisService;

    @Resource
    private PregInbodyService pregInbodyService;

    @Resource
    private ExamItemUtil examItemUtil;

    @Resource
    private DiagnosisHospitalItemService diagnosisHospitalItemService;

    @Resource
    private PregDiagnosisDiseaseService pregDiagnosisDiseaseService;

    @Resource
    private InterveneDiseaseService diseaseService;

    @Resource
    private UserService userService;

    @Resource
    private ItemService itemService;

    @Resource
    public CodeCache codeCache;

    /** 妊娠期表格模板路径 */
    private static String pregTemplatePath = StatisticController.class.getClassLoader().getResource("template/templatePreg.xlsx")
            .getPath();

    /** 产后表格模板路径 */
    private static String birthTemplatePath = StatisticController.class.getClassLoader()
            .getResource("template/templateBirth.xlsx")
            .getPath();

    /** 多选下拉列表 */
    private final static String dictName[] = {"bmiList", "birthNumList", "weightConditionList", "fuzhongConditionList",
            "levelList", "childbirthTypeList", "mazuiTypeList", "pregTimesList", "birthTimesList",
            "badPregHistoryList",
            "badBirthHistoryList",
            "whenDeadList", "newBirthSexList", "afvList", "afluidList", "diseaseHistoryList", "familyHistoryList",
            "pregComplicationsList"};

    private final static String codeName[] = {"BMISCOPE", "BIRTHNUM", "WEIGHTCONDITION", "FUZHONGCONDITION",
            "BIRTHINSPECTLEVEL", "CHILDBIRTHTYPE", "MAZUITYPE", "PREGTIMES", "BIRTHTIMES", "BADPREGHISTORY",
            "BADBIRTHHISTORY", "WHENDEAD", "NEWBIRTHSEX",
            "AFV", "AFLUID", "DISEASEHISTORY", "FAMILYHISTORY", "PREGCOMPLICATIONS"};

    /**
     * 
     * 超级查询页面初始化
     * 
     * @author lipeng
     * @param model
     * @return
     */
    @RequestMapping(value = StatisticMapping.STATISTIC_INIT)
    public String initStatistic(Model model) {
        // 获取所有的医生
        User condition = new User();
        condition.setUserType("doctor");
        model.addAttribute("userList", NetJsonUtils.listToJsonArray(userService.queryUsers(condition)));
        // 获取所有疾病诊断项目列表
        List<InterveneDiseasePojo> diseaseList = diseaseService.queryInterveneDisease(null);
        model.addAttribute("diseaseList", NetJsonUtils.listToJsonArray(diseaseList));
        // 获取所有检验项目列表
        List<ItemPojo> itemList = itemService.queryItemByCondition(null);
        model.addAttribute("inspectAllList", NetJsonUtils.listToJsonArray(itemList));

        // 分娩方位
        CodeInfo code = new CodeInfo();
        code.setParentCodeValue("BIRTHDIRECTION");
        List<CodePojo> birthPlaceList = codeService.queryCodeView(code);
        model.addAttribute("birthPlaceList", NetJsonUtils.listToJsonArray(birthPlaceList));
        // 下拉列表选项
        for (int i = 0; i < dictName.length; i++) {
            model.addAttribute(dictName[i],
                    NetJsonUtils.listToJsonArray(codeCache.getCodeListCache(codeName[i], codeName[i])));
        }
        return StatisticPageName.STATISTIC_VIEW;
    }

    /**
     * 
     * [导出excel是否完成，完成则清session]
     * 
     * @author lipeng
     * @return
     */
    @RequestMapping(value = StatisticMapping.IS_EXPORTED)
    @ResponseBody
    public WebResult<Boolean> isExported() {
        WebResult<Boolean> result = new WebResult<Boolean>();
        Boolean isExported = (Boolean) request.getSession().getAttribute("isExported");
        result.setSuc(isExported);
        return result;
    }

    /**
     * 
     * [导出excel接口]
     * 
     * @author lipeng
     * @param dataKey
     */
    @RequestMapping(value = StatisticMapping.EXPORT_EXCEL)
    @ResponseBody
    public void downloadExcel(String dataKey) {
        request.getSession().setAttribute("isExported", false);
        byte[] bytes = (byte[]) statisticService.getExcelDataCache("byteData" + dataKey);
        String fileName = "孕期数据查询条件导出表" + JodaTimeTools.toString(new Date(), "yyyy-MM-dd") + ".xlsx";
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
            response.setContentLength(bytes.length);
            if (response.getOutputStream() != null) {
                response.getOutputStream().write(bytes);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                request.getSession().setAttribute("isExported", true);
                statisticService.removeExcelDataCache("byteData" + dataKey);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 
     * [根据查出的数据生成临时的字节流放缓存中]
     * 
     * @author lipeng
     * @param condition
     * @param excelPojoList
     * @param dataKey
     * @throws Exception
     */
    private void exportExcel(SearchCountCondition condition, List<StatisticDataPojo> excelPojoList, String dataKey)
            throws Exception {
        String xlsPath = pregTemplatePath;
        if (condition.getStage() == 2) {// 产后
            xlsPath = birthTemplatePath;
        }

        Map<String, CodePojo> dict = codeCache.getCodeMapCache();
        byte[] bytes = ExportExcel.loadExcel(excelPojoList, xlsPath, condition, dict);
        statisticService.addExcelDataCache("byteData" + dataKey, bytes);
    }

    /**
     * 
     * 超级查询-查询符合条件的人员列表
     * 
     * @author lipeng
     * @param statisticForm
     * @return
     */
    @RequestMapping(value = StatisticMapping.STATISTIC_QUERY)
    public @ResponseBody
    WebResult<List<StatisticCustomerPojo>> queryPersonList(StatisticForm statisticForm) {
        WebResult<List<StatisticCustomerPojo>> webResult = new WebResult<List<StatisticCustomerPojo>>();
        List<StatisticCustomerPojo> result = null;
        if (statisticForm.getPregPeriod() != null && !StringUtils.isEmpty(statisticForm.getPregPeriod())) {
            result = statisticService.queryCustomerList(statisticForm);
        }
        if (result == null) {
            result = new ArrayList<StatisticCustomerPojo>();
        }
        webResult.setData(result);
        webResult.setSuc(result);
        return webResult;
    }

    /**
     * 
     * 超级查询-分娩结局统计报表查询
     * 
     * @author lipeng
     * @param resultStartDate
     *            分娩时间-查询开始日期
     * @param resultEndDate
     *            分娩时间-查询结束日期
     * @return
     */
    @RequestMapping(value = StatisticMapping.STATISTIC_BIRTHRESULT)
    public @ResponseBody
    WebResult<StatiscBirthResultPojo> queryBirthResult(String resultStartDate, String resultEndDate) {
        WebResult<StatiscBirthResultPojo> webResult = new WebResult<StatiscBirthResultPojo>();
        StatiscBirthResultPojo pojo = statisticService.queryConfinedAccount(resultStartDate, resultEndDate);
        webResult.setSuc(pojo);
        return webResult;
    }

    /**
     * 查询条件导出Excel
     * 
     * @author mlq
     * @param condition
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = StatisticMapping.STATISTIC_EXPORT)
    public @ResponseBody
    WebResult<String> searchConditionExport(SearchCountCondition condition)
            throws Exception {
        WebResult<String> webResult = new WebResult<String>();

        // 最终数据集合
        List<StatisticDataPojo> dataList = new ArrayList<StatisticDataPojo>();
        // 阶段性选择
        int stage = condition.getStage();
        // 获取数据
        if (CollectionUtils.isNotEmpty(condition.getCustIds()) && stage > 0) {
            // 1,获取患者基本信息
            // 基本信息map集合（存在建档信息）
            Map<String, CustomerInfoPojo> archivesExsitParams = new HashMap<String, CustomerInfoPojo>();
            // 基本信息map集合（不存在建档信息）
            Map<String, CustomerInfoPojo> archivesEmptyParams = new HashMap<String, CustomerInfoPojo>();

            List<CustomerInfoPojo> customerInfoList = new ArrayList<CustomerInfoPojo>();
            customerInfoList = statisticService.queryCustomerInfoByCustIds(condition);
            for (CustomerInfoPojo customerInfoPojo : customerInfoList) {
                // 供分娩结局患者使用
                if (!archivesEmptyParams.containsKey(customerInfoPojo.getCustId())) {
                    archivesEmptyParams.put(customerInfoPojo.getCustId(), customerInfoPojo);
                }
                // 供接诊信息患者使用（不同的接诊信息可能对应不同的建档信息）
                if (!StringUtils.isEmpty(customerInfoPojo.getArchivesId())) {
                    archivesExsitParams.put(customerInfoPojo.getArchivesId(), customerInfoPojo);
                }
            }

            // 2,产后阶段 分娩信息为主（基础信息部分字段在分娩结局基础表中获取）
            Map<String, Object> birthEndingMap = new HashMap<String, Object>();
            List<BirthEndingInfoPojo> birthEndingList = null;// 分娩结局数据集合
            List<String> archivesIds = null;// 建档id集合（查询接诊信息应用）
            if (stage == 2) {
                birthEndingMap = getBirthEndingInfo(condition);
                archivesIds = (List<String>) birthEndingMap.get("archivesIds");
                if (CollectionUtils.isNotEmpty(archivesIds) && archivesIds.size() > 0) {
                    condition.setArchivesIds(archivesIds);
                }
                birthEndingList = (List<BirthEndingInfoPojo>) birthEndingMap.get("birthEndingList");
                condition.setBabyNumMax((Integer) birthEndingMap.get("babyNumMax"));
            } else {
                condition.setBabyNumMax(0);
            }

            // 3,获取患者对应的所有接诊信息
            Map<String, Object> diagnosisMap = new HashMap<String, Object>();
            List<DiagnosisInfoPojo> diagnosisInfoList = null;// 接诊信息数据集合
            Map<String, List<DiagnosisInfoPojo>> archivesDiagnosisParams = null;// 建档对应接诊信息map（接诊与分娩桥梁）
            diagnosisMap = getDiagnosisInfo(condition);
            diagnosisInfoList = (List<DiagnosisInfoPojo>) diagnosisMap.get("diagnosisInfoList");
            archivesDiagnosisParams = (Map<String, List<DiagnosisInfoPojo>>) diagnosisMap
                    .get("archivesDiagnosisParams");
            condition.setProductNumMax((Integer) diagnosisMap.get("productNumMax"));

            // 4,添加(关联)建档信息的最终数据
            StatisticDataPojo statisticDataPojo = null;
            // 妊娠阶段
            if (stage == 1 && CollectionUtils.isNotEmpty(diagnosisInfoList)) {
                CustomerInfoPojo customerInfoPojo = null;
                for (DiagnosisInfoPojo diagnosisInfoPojo : diagnosisInfoList) {
                    customerInfoPojo = archivesExsitParams.get(diagnosisInfoPojo.getArchivesId());
                    if (StringUtils.isEmpty(customerInfoPojo)) {
                        customerInfoPojo = archivesEmptyParams.get(diagnosisInfoPojo.getCustId());
                    }
                    if (!StringUtils.isEmpty(customerInfoPojo)) {// 处理历史数据人员丢失问题(基础信息为空)
                        // 数据组合
                        statisticDataPojo = new StatisticDataPojo();
                        statisticDataPojo.setCustomerInfoPojo(customerInfoPojo);
                        // 处理营养制剂名称
                        diagnosisInfoPojo = productNamesJoin(diagnosisInfoPojo);
                        statisticDataPojo.setDiagnosisPojo(diagnosisInfoPojo);
                        dataList.add(statisticDataPojo);
                    }
                }
            }

            // 产后阶段
            if (stage == 2 && CollectionUtils.isNotEmpty(birthEndingList)) {
                CustomerInfoPojo customerInfoPojo = null;
                for (BirthEndingInfoPojo birthEndingInfoPojo : birthEndingList) {
                    customerInfoPojo = archivesEmptyParams.get(birthEndingInfoPojo.getCustId());
                    if (!StringUtils.isEmpty(customerInfoPojo)) {// 处理历史数据人员丢失问题(基础信息为空)
                        // 数据组合
                        if (!StringUtils.isEmpty(birthEndingInfoPojo.getArchivesId())) {
                            // 获取当前建档id对应的所有接诊信息
                            diagnosisInfoList = archivesDiagnosisParams.get(birthEndingInfoPojo.getArchivesId());
                            if (CollectionUtils.isNotEmpty(diagnosisInfoList)) {
                                for (DiagnosisInfoPojo diagnosisInfoPojo2 : diagnosisInfoList) {
                                    statisticDataPojo = new StatisticDataPojo();
                                    statisticDataPojo.setCustomerInfoPojo(customerInfoPojo);
                                    // 处理营养制剂名称
                                    diagnosisInfoPojo2 = productNamesJoin(diagnosisInfoPojo2);
                                    statisticDataPojo.setDiagnosisPojo(diagnosisInfoPojo2);
                                    statisticDataPojo.setBirthEndingPojo(birthEndingInfoPojo);
                                    dataList.add(statisticDataPojo);
                                }
                            }
                        } else {
                            // 未关联建档的分娩信息
                            statisticDataPojo = new StatisticDataPojo();
                            statisticDataPojo.setCustomerInfoPojo(customerInfoPojo);
                            statisticDataPojo.setBirthEndingPojo(birthEndingInfoPojo);
                            dataList.add(statisticDataPojo);
                        }
                    }
                }
            }
        }

        // 导出excel
        try {
            // 获取所有检验项目列表
            List<ItemPojo> itemList = itemService.queryItemByCondition(null);

            String[] itemsId = condition.getItems().split(",");
            String itemsName = "";
            for (String itemId : itemsId) {
                for (ItemPojo itemPojo : itemList) {
                    if (itemId.equals(itemPojo.getItemId())) {
                        if (itemsName.length() > 0) {
                            itemsName += ",";
                        }
                        if (StringUtils.isEmpty(itemPojo.getItemUnit())) {
                            itemsName += itemPojo.getItemName();
                        } else {
                            itemsName += itemPojo.getItemName() + "(" + itemPojo.getItemUnit() + ")";
                        }
                        break;
                    }
                }
            }
            condition.setItemsName(itemsName);
            String cacheKey = UUID.randomUUID().toString();
            exportExcel(condition, dataList, cacheKey);
            webResult.setSuc(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            webResult.setError(e.getMessage());
        }
        return webResult;
    }

    /**
     * 获取分娩信息
     * 
     * @author mlq
     * @param condition
     * @return
     *         新生儿数量最大值，
     *         分娩结局数据集合，
     *         建档id集合（查询接诊信息应用）
     */
    public Map<String, Object> getBirthEndingInfo(SearchCountCondition condition) {
        Map<String, Object> birthEndingMap = new HashMap<String, Object>();
        Integer babyNumMax = 0;// 新生儿数量最大值
        List<String> archivesIds = new ArrayList<String>();// 建档id集合

        // 获取所有疾病数据
        List<InterveneDiseasePojo> diseaseList = diseaseService.queryInterveneDisease(null);
        Map<String, InterveneDiseasePojo> dMap = new HashMap<String, InterveneDiseasePojo>();
        if (CollectionUtils.isNotEmpty(diseaseList)) {
            for (InterveneDiseasePojo pojo : diseaseList) {
                dMap.put(pojo.getDiseaseId(), pojo);
            }
        }

        // 获取分娩信息
        List<String> birthIds = new ArrayList<String>();// 分娩id集合（查询附属信息使用）
        Map<String, BirthEndingInfoPojo> birthEndParams = new HashMap<String, BirthEndingInfoPojo>(); // 分娩信息map集合（实时更新分娩信息）
        List<BirthEndingInfoPojo> birthEndingList = new ArrayList<BirthEndingInfoPojo>();
        birthEndingList = statisticService.queryBirthEndingInfoByCustIds(condition);
        if (CollectionUtils.isNotEmpty(birthEndingList)) {
            String birthDiagnosis = "";// 入院诊断
            String basePrenatal = "";// 产前合并症
            String baseBirthing = "";// 产时合并症
            String baseAfterBirthing = "";// 产后合并症
            String baseMedical = "";// 内科合并症
            String disMotherDisagnosis = "";// 出院诊断/母
            for (BirthEndingInfoPojo birthEndingInfoPojo : birthEndingList) {
                // 记录分娩id集合
                birthIds.add(birthEndingInfoPojo.getBirthId());

                // 查询并发症数据
                birthDiagnosis = getSelectDiseases(birthEndingInfoPojo.getBirthDiagnosis(), dMap);
                basePrenatal = getSelectDiseases(birthEndingInfoPojo.getBaseComplicationPrenatal(), dMap);
                baseBirthing = getSelectDiseases(birthEndingInfoPojo.getBaseComplicationBirthing(), dMap);
                baseAfterBirthing = getSelectDiseases(birthEndingInfoPojo.getBaseComplicationAfterBirthing(),
                        dMap);
                baseMedical = getSelectDiseases(birthEndingInfoPojo.getBaseComplicationsMedical(), dMap);
                disMotherDisagnosis = getSelectDiseases(birthEndingInfoPojo.getDisMotherDisagnosis(), dMap);
                // 赋值
                birthEndingInfoPojo.setBirthDiagnosis(birthDiagnosis);
                birthEndingInfoPojo.setBaseComplicationPrenatal(basePrenatal);
                birthEndingInfoPojo.setBaseComplicationBirthing(baseBirthing);
                birthEndingInfoPojo.setBaseComplicationAfterBirthing(baseAfterBirthing);
                birthEndingInfoPojo.setBaseComplicationsMedical(baseMedical);
                birthEndingInfoPojo.setDisMotherDisagnosis(disMotherDisagnosis);

                // 记录接诊map集合（key值为分娩id）
                birthEndParams.put(birthEndingInfoPojo.getBirthId(), birthEndingInfoPojo);

                // 记录建档id集合
                if (!StringUtils.isEmpty(birthEndingInfoPojo.getArchivesId())) {
                    archivesIds.add(birthEndingInfoPojo.getArchivesId());
                }
            }
        }

        // 查询附属信息
        if (CollectionUtils.isNotEmpty(birthIds)) {
            List<BirthEndingBabyInfoPojo> babyList = null;
            // 3.1 查询新生儿信息集合
            BirthEndingBabyInfoCondition babyContion = new BirthEndingBabyInfoCondition();
            babyContion.setBirthIds(birthIds);
            babyList = birthBabyService.getBabyListByCondition(babyContion);

            if (CollectionUtils.isNotEmpty(babyList)) {
                int index = 0;// 分娩信息在集合中的位置
                BirthEndingInfoPojo birthEndingInfoPojo = null;
                List<BirthEndingBabyInfoPojo> babyListTemporaryList = null;
                String babyComplication = "";// 并发症
                String disBabyDiagnosis = "";// 出院诊断

                for (BirthEndingBabyInfoPojo birthEndingBabyInfoPojo : babyList) {
                    // 查找当前分娩信息所在集合位置
                    birthEndingInfoPojo = birthEndParams.get(birthEndingBabyInfoPojo.getBirthId());
                    index = birthEndingList.indexOf(birthEndingInfoPojo);

                    // 并发症信息
                    babyComplication = getSelectDiseases(birthEndingBabyInfoPojo.getBabyComplication(), dMap);
                    // 出院诊断
                    disBabyDiagnosis = getSelectDiseases(birthEndingBabyInfoPojo.getDisBabyDiagnosis(), dMap);

                    birthEndingBabyInfoPojo.setBabyComplication(babyComplication);
                    birthEndingBabyInfoPojo.setDisBabyDiagnosis(disBabyDiagnosis);

                    // 添加新生儿信息
                    babyListTemporaryList = birthEndingInfoPojo.getBabyList();
                    if (CollectionUtils.isEmpty(babyListTemporaryList)) {
                        babyListTemporaryList = new ArrayList<BirthEndingBabyInfoPojo>();
                    }
                    babyListTemporaryList.add(birthEndingBabyInfoPojo);
                    birthEndingInfoPojo.setBabyList(babyListTemporaryList);

                    // 替换分娩信息
                    birthEndParams.put(birthEndingBabyInfoPojo.getBirthId(), birthEndingInfoPojo);
                    birthEndingList.set(index, birthEndingInfoPojo);
                }
            }
            // 新生儿最大值
            BirthEndingBabyInfoPojo babyPojo = birthBabyService.getMaxBabyListByCondition(babyContion);
            if (!StringUtils.isEmpty(babyPojo) && !StringUtils.isEmpty(babyPojo.getBabyCount())) {
                BigInteger count = (BigInteger) babyPojo.getBabyCount();
                babyNumMax = count.intValue();
            }
        }

        birthEndingMap.put("babyNumMax", StringUtils.isEmpty(babyNumMax) ? 0 : babyNumMax);
        birthEndingMap.put("birthEndingList", birthEndingList);
        birthEndingMap.put("archivesIds", archivesIds);
        return birthEndingMap;
    }

    /**
     * 获取接诊信息
     * 
     * @author mlq
     * @param condition
     * @return
     *         营养制剂数量最大值，
     *         接诊数据集合，
     *         建档对应接诊信息map（接诊与分娩桥梁）
     */
    public Map<String, Object> getDiagnosisInfo(SearchCountCondition condition) {
        Map<String, Object> diagnosisMap = new HashMap<String, Object>();
        Integer productNumMax = 0;// 营养制剂数量最大值
        List<String> diagnosisIds = new ArrayList<String>();// 接诊id集合（查询附属信息使用）
        Map<String, DiagnosisInfoPojo> diagnosisParams = new HashMap<String, DiagnosisInfoPojo>(); // 接诊信息map集合（实时更新接诊信息）

        // 获取接诊信息
        List<DiagnosisInfoPojo> diagnosisInfoList = new ArrayList<DiagnosisInfoPojo>();
        condition.setCustomerIds(condition.getCustIds());
        condition.setCustIds(null);
        diagnosisInfoList = statisticService.queryDiagnosisInfoByCustIds(condition);
        if (CollectionUtils.isNotEmpty(diagnosisInfoList)) {
            for (DiagnosisInfoPojo diagnosisInfoPojo : diagnosisInfoList) {
                // 记录接诊id集合
                diagnosisIds.add(diagnosisInfoPojo.getDiagnosisId());

                // 记录接诊map集合（key值为接诊id）
                diagnosisParams.put(diagnosisInfoPojo.getDiagnosisId(), diagnosisInfoPojo);
            }
        }

        // 接诊附属信息查询
        if (CollectionUtils.isNotEmpty(diagnosisIds)) {
            int index = 0;
            DiagnosisInfoPojo diagnosisInfoPojo = null;

            // 2.1,查询营养制剂集合
            List<DiagnosisPrescriptionPojo> productList = null;
            List<DiagnosisPrescriptionPojo> productTemporaryList = null;

            // 数据查询
            DiagnosisPrescriptionCondition pCondition = new DiagnosisPrescriptionCondition();
            pCondition.setDiagnosisIds(diagnosisIds);
            productList = pregDiagnosisService.queryDiagnosisPrescriptionByCondition(pCondition);
            if (CollectionUtils.isNotEmpty(productList)) {
                for (DiagnosisPrescriptionPojo product : productList) {
                    // 查找当前接诊信息所在集合位置
                    diagnosisInfoPojo = diagnosisParams.get(product.getDiagnosisId());
                    index = diagnosisInfoList.indexOf(diagnosisInfoPojo);

                    // 添加营养制剂信息
                    productTemporaryList = diagnosisInfoPojo.getProductList();
                    if (CollectionUtils.isEmpty(productTemporaryList)) {
                        productTemporaryList = new ArrayList<DiagnosisPrescriptionPojo>();
                    }
                    productTemporaryList.add(product);
                    diagnosisInfoPojo.setProductList(productTemporaryList);

                    // 替换接诊信息
                    diagnosisParams.put(product.getDiagnosisId(), diagnosisInfoPojo);
                    diagnosisInfoList.set(index, diagnosisInfoPojo);
                }
            }

            // 记录营养制剂数量最大值
            DiagnosisPrescriptionPojo prePojo = pregDiagnosisService.getMaxProductListByCondition(pCondition);
            if (!StringUtils.isEmpty(prePojo) && !StringUtils.isEmpty(prePojo.getProductCount())) {
                BigInteger count = (BigInteger) prePojo.getProductCount();
                productNumMax = count.intValue();
            }

            // 2.2,查询人体成分数据集合
            List<PregInBodyBcaPojo> bcaList = null;
            // 数据查询
            InbodyCondition inbodyCondition = new InbodyCondition();
            inbodyCondition.setDiagnosisIds(diagnosisIds);
            bcaList = pregInbodyService.getStatisticInbodyByCondition(inbodyCondition);
            if (CollectionUtils.isNotEmpty(bcaList)) {
                for (PregInBodyBcaPojo inBodyPojo : bcaList) {
                    // 查找当前接诊信息所在集合位置
                    diagnosisInfoPojo = diagnosisParams.get(inBodyPojo.getDiagnosisId());
                    index = diagnosisInfoList.indexOf(diagnosisInfoPojo);

                    // 数据匹配
                    diagnosisInfoPojo.setExamDate(!StringUtils.isEmpty(inBodyPojo.getUserExamDate()) ? inBodyPojo
                            .getUserExamDate() : null);
                    diagnosisInfoPojo.setWt(!StringUtils.isEmpty(inBodyPojo.getWt()) ? inBodyPojo.getWt() : null);
                    diagnosisInfoPojo.setBfm(!StringUtils.isEmpty(inBodyPojo.getBfm()) ? inBodyPojo.getBfm() : null);
                    diagnosisInfoPojo.setSmm(!StringUtils.isEmpty(inBodyPojo.getSmm()) ? inBodyPojo.getSmm() : null);
                    diagnosisInfoPojo.setProtein(!StringUtils.isEmpty(inBodyPojo.getProtein()) ? inBodyPojo.getProtein() : null);
                    diagnosisInfoPojo.setMineral(!StringUtils.isEmpty(inBodyPojo.getMineral()) ? inBodyPojo.getMineral() : null);
                    diagnosisInfoPojo.setIcw(!StringUtils.isEmpty(inBodyPojo.getIcw()) ? inBodyPojo.getIcw() : null);
                    diagnosisInfoPojo.setEcw(!StringUtils.isEmpty(inBodyPojo.getEcw()) ? inBodyPojo.getEcw() : null);
                    diagnosisInfoPojo.setWed(!StringUtils.isEmpty(inBodyPojo.getWed()) ? inBodyPojo.getWed() : null);

                    // 替换接诊信息
                    diagnosisParams.put(inBodyPojo.getDiagnosisId(), diagnosisInfoPojo);
                    diagnosisInfoList.set(index, diagnosisInfoPojo);
                }

                // 2.2.1,查询人体成分体脂百分比、相位角数据
                List<DiagnosisInfoPojo> inbodyList = pregInbodyService.queryBaseInbodyByConsition(inbodyCondition);
                if (CollectionUtils.isNotEmpty(inbodyList)) {
                    for (DiagnosisInfoPojo diagnosisInbody : inbodyList) {
                        // 查找当前接诊信息所在集合位置
                        diagnosisInfoPojo = diagnosisParams.get(diagnosisInbody.getDiagnosisId());
                        index = diagnosisInfoList.indexOf(diagnosisInfoPojo);

                        // 添加体质百分比、相位角信息
                        diagnosisInfoPojo.setPbf(StringUtils.isEmpty(diagnosisInbody.getPbf()) ? "" : diagnosisInbody.getPbf());
                        diagnosisInfoPojo.setWbpa50(StringUtils.isEmpty(diagnosisInbody.getWbpa50()) ? ""
                                : diagnosisInbody
                                        .getWbpa50());

                        // 替换接诊信息
                        diagnosisParams.put(diagnosisInbody.getDiagnosisId(), diagnosisInfoPojo);
                        diagnosisInfoList.set(index, diagnosisInfoPojo);
                    }
                }
            }

            // 2.3,查询诊断项目数据集合
            List<PregDiagnosisPojo> diseaseList = null; // 诊断项目集合
            DiagnosisCondition diagnosisCondition = new DiagnosisCondition();
            diagnosisCondition.setDiagnosisIds(diagnosisIds);
            diseaseList = pregDiagnosisDiseaseService.queryStatisticDiseaseByCondition(diagnosisCondition);
            if (CollectionUtils.isNotEmpty(diseaseList)) {
                for (PregDiagnosisPojo pregDiagnosisPojo : diseaseList) {
                    // 查找当前接诊信息所在集合位置
                    diagnosisInfoPojo = diagnosisParams.get(pregDiagnosisPojo.getDiagnosisId());
                    index = diagnosisInfoList.indexOf(diagnosisInfoPojo);
                    diagnosisInfoPojo.setDiseaseNames(pregDiagnosisPojo.getDiagnosisDiseases());
                    // 替换接诊信息
                    diagnosisParams.put(pregDiagnosisPojo.getDiagnosisId(), diagnosisInfoPojo);
                    diagnosisInfoList.set(index, diagnosisInfoPojo);
                }
            }

            // 2.4,查询目标检验项目数据数据
            List<DiagnosisHospitalItemPojo> itemList = null;
            List<DiagnosisHospitalItemPojo> itemTemporaryList = null;

            // 所选检验项目id集合
            List<String> itemIds = new ArrayList<String>();
            if (!StringUtils.isEmpty(condition.getItems())) {
                String[] itemIds1 = condition.getItems().split(",");
                for (String itemId : itemIds1) {
                    itemIds.add(itemId);
                }
            }
            if (CollectionUtils.isNotEmpty(itemIds)) {// 导出选项处理 ==>孕期检验记录导出表
                // 查询所有检验项目信息（无序）
                DiagnosisHospitalItemCondition itemCondition = new DiagnosisHospitalItemCondition();
                itemCondition.setItemIds(itemIds);
                itemCondition.setDiagnosisIds(diagnosisIds);
                // itemCondition.setCustIds(condition.getCustIds());
                itemList = diagnosisHospitalItemService.queryStatisticHospitalItemByCondition(itemCondition);

                // 匹配接诊信息
                if (CollectionUtils.isNotEmpty(itemList)) {
                    for (DiagnosisHospitalItemPojo itemPojo : itemList) {
                        // 查找当前接诊信息所在集合位置
                        diagnosisInfoPojo = diagnosisParams.get(itemPojo.getDiagnosisId());
                        index = diagnosisInfoList.indexOf(diagnosisInfoPojo);

                        // 添加检验项目信息
                        if (!StringUtils.isEmpty(diagnosisInfoPojo)) {
                            itemTemporaryList = diagnosisInfoPojo.getItems();
                            if (CollectionUtils.isEmpty(itemTemporaryList)) {
                                itemTemporaryList = new ArrayList<DiagnosisHospitalItemPojo>();
                            }
                            itemTemporaryList.add(itemPojo);
                            diagnosisInfoPojo.setItems(itemTemporaryList);

                            // 替换接诊信息
                            diagnosisParams.put(itemPojo.getDiagnosisId(), diagnosisInfoPojo);
                            diagnosisInfoList.set(index, diagnosisInfoPojo);
                        }
                    }
                }
            }
        }

        // 记录接诊map集合（key值为建档id）
        List<DiagnosisInfoPojo> archivesDiagnosisList = null;// 接诊集合（key值为建档id）
        Map<String, List<DiagnosisInfoPojo>> archivesDiagnosisParams = new HashMap<String, List<DiagnosisInfoPojo>>(); // 接诊信息map集合（archivesId）
        if (condition.getStage() == 2) {
            for (DiagnosisInfoPojo diagnosisInfoPojo : diagnosisInfoList) {
                if (archivesDiagnosisParams.containsKey(diagnosisInfoPojo.getArchivesId())) {
                    archivesDiagnosisList = archivesDiagnosisParams.get(diagnosisInfoPojo.getArchivesId());
                } else {
                    archivesDiagnosisList = new ArrayList<DiagnosisInfoPojo>();
                }
                archivesDiagnosisList.add(diagnosisInfoPojo);
                archivesDiagnosisParams.put(diagnosisInfoPojo.getArchivesId(), archivesDiagnosisList);
            }
        }
        diagnosisMap.put("productNumMax", StringUtils.isEmpty(productNumMax) ? 0 : productNumMax);
        diagnosisMap.put("diagnosisInfoList", diagnosisInfoList);
        diagnosisMap.put("archivesDiagnosisParams", archivesDiagnosisParams);
        return diagnosisMap;
    }

    /**
     * 拼接营养制剂 名称，频次，单次剂量，单位
     * 
     * @author mlq
     * @param diagnosisInfoPojo
     * @return diagnosisInfoPojo
     */
    public DiagnosisInfoPojo productNamesJoin(DiagnosisInfoPojo diagnosisInfoPojo) {
        List<DiagnosisPrescriptionPojo> productList = diagnosisInfoPojo.getProductList();
        if (!StringUtils.isEmpty(productList)) {
            String currentNames = "";// 继服营养制剂
            String increasedNames = "";// 新增营养制剂
            String stopNames = "";// 停服营养制剂
            for (DiagnosisPrescriptionPojo product : productList) {
                // 规则： 名称，频次，单次剂量，单位
                String currentName = "";
                currentName = currentName + ";" + product.getProductName().trim() + ","
                        + product.getProductFrequency() + "," + product.getProductDosage() + ","
                        + product.getProductUnit();
                if (product.getStatus() != null) {
                    switch (product.getStatus()) {
                        case 1:
                            increasedNames = increasedNames + currentName;
                            break;
                        case 2:
                            currentNames = currentNames + currentName;
                            break;
                        case 3:
                            stopNames = stopNames + currentName;
                            break;

                        default:
                            break;
                    }
                }
            }
            // 记录各阶段营养制剂名称
            diagnosisInfoPojo.setCurrentProductNames(!StringUtils.isEmpty(currentNames) ? currentNames.substring(1) : "");
            diagnosisInfoPojo.setIncreasedProductNames(!StringUtils.isEmpty(increasedNames) ? increasedNames.substring(1) : "");
            diagnosisInfoPojo.setStopProductNames(!StringUtils.isEmpty(stopNames) ? stopNames.substring(1) : "");
        }
        return diagnosisInfoPojo;
    }

    /**
     * 获取疾病名称
     * 
     * @author mlq
     * @param diseaseIds
     *            所选疾病id
     * @param dMap
     *            所有疾病集合
     * @return diseasesNames
     */
    public String getSelectDiseases(String diseaseIds, Map<String, InterveneDiseasePojo> dMap) {
        String diseasesNames = "";
        if (StringUtils.isEmpty(diseaseIds)) {
            return diseasesNames;
        }
        String[] ids = diseaseIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            if ("".equals(id))
                continue;
            if (dMap.containsKey(id)) {
                diseasesNames = diseasesNames + "," + dMap.get(id).getDiseaseName();
            }
        }
        if (!StringUtils.isEmpty(diseasesNames)) {
            diseasesNames = diseasesNames.substring(1);
        }
        return diseasesNames;
    }

}
