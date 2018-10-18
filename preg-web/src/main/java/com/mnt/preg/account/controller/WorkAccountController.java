
package com.mnt.preg.account.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.account.condition.StatisticaListCondition;
import com.mnt.preg.account.condition.WorkAccountCondition;
import com.mnt.preg.account.mapping.WorkAccountMapping;
import com.mnt.preg.account.mapping.WorkAccountPageName;
import com.mnt.preg.account.pojo.StatisticaListPojo;
import com.mnt.preg.account.pojo.WorkAccountPojo;
import com.mnt.preg.account.service.WorkAccountService;
import com.mnt.preg.master.pojo.items.InterveneDiseasePojo;
import com.mnt.preg.master.service.items.InspectItemService;
import com.mnt.preg.master.service.items.InterveneDiseaseService;
import com.mnt.preg.system.entity.User;
import com.mnt.preg.system.pojo.UserPojo;
import com.mnt.preg.system.service.ReferralDoctorService;
import com.mnt.preg.system.service.UserAssistantService;
import com.mnt.preg.system.service.UserService;
import com.mnt.preg.web.controller.BaseController;
import com.mnt.preg.web.excel.AccountExcelData;
import com.mnt.preg.web.excel.WorkAccountExcel;
import com.mnt.preg.web.pojo.Echarts;
import com.mnt.preg.web.pojo.EchartsSeries;
import com.mnt.preg.web.pojo.WebResult;

@Controller
public class WorkAccountController extends BaseController {

    @Resource
    private WorkAccountService workService;

    @Resource
    private UserService userService;

    @Resource
    private InterveneDiseaseService diseaseService;

    @Resource
    private ReferralDoctorService referralDoctorService;

    @Resource
    private InspectItemService inspectItemService;

    @Resource
    private UserAssistantService userAssistantService;

    // --------------------------------------------------【许道川】--------------------------------------------------
    /**
     * 查询工作量统计
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_PAGE)
    public String workAccountPage(Model model) {
        // 获取所有的医生
        User condition = new User();
        condition.setUserType("doctor");
        model.addAttribute("userList", NetJsonUtils.listToJsonArray(userService.queryUsers(condition)));
        model.addAttribute("zhuanDoctorList", NetJsonUtils.listToJsonArray(referralDoctorService.queryDoctors(null)));
        model.addAttribute("masItemNames", NetJsonUtils.listToJsonArray(inspectItemService.queryInspectItem(null)));
        List<UserPojo> userPojoList = userService.queryUsers(null);// 全部
        List<UserPojo> userPojos = new ArrayList<UserPojo>();// 医生/助理
        if (CollectionUtils.isNotEmpty(userPojoList)) {
            for (UserPojo user : userPojoList) {
                if ("assistant".equals(user.getUserType()) || "doctor".equals(user.getUserType())) {// 医生或助理
                    userPojos.add(user);
                }
            }
        }
        model.addAttribute("masItemAuthor", NetJsonUtils.listToJsonArray(userPojos));
        // 获取所有诊断项目列表
        List<InterveneDiseasePojo> diseaseList = diseaseService.queryInterveneDisease(null);
        model.addAttribute("diseaseList", NetJsonUtils.listToJsonArray(diseaseList));
        // 获取所有诊断项目map
        Map<String, InterveneDiseasePojo> diseaseMap = new HashMap<String, InterveneDiseasePojo>();
        for (InterveneDiseasePojo diseasePojo : diseaseList) {
            diseaseMap.put(diseasePojo.getDiseaseName(), diseasePojo);
        }
        model.addAttribute("diseaseMap", NetJsonUtils.objectToJson(diseaseMap));

        return WorkAccountPageName.WORK_ACCOUNT_VIEW;
    }

    /**
     * 查询工作量统计
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_QUERY)
    public @ResponseBody
    WebResult<WorkAccountPojo> queryWorkAccount(WorkAccountCondition condition) {
        WebResult<WorkAccountPojo> webResult = new WebResult<WorkAccountPojo>();
        webResult.setData(workService.queryWorkAccount(condition));
        return webResult;
    }

    /**
     * 初诊人数 vs 总就诊人数
     * 
     * @author xdc
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_QUERY_TAB1)
    public @ResponseBody
    WebResult<Echarts> queryWorkAccountTab1(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();
        Echarts echarts = new Echarts();
        List<WorkAccountPojo> list = workService.queryWorkAccount(condition);
        String[] xData = new String[list.size() - 1];// x轴数据
        String[] legendData = {"初诊人数", "总就诊人次"};// 图例
        Integer[] firstData = new Integer[list.size() - 1];// 初诊人数数据
        Integer[] renData = new Integer[list.size() - 1];// 总就诊人次数据
        List<EchartsSeries> listSeries = new ArrayList<EchartsSeries>();
        for (int x = 0; x < list.size() - 1; x++) {
            xData[x] = list.get(x).getMonth();
            firstData[x] = Integer.parseInt(list.get(x).getDiagnosisFirst().toString());
            renData[x] = Integer.parseInt(list.get(x).getDiagnosisRen().toString());
        }
        EchartsSeries echartsSeries = new EchartsSeries();
        echartsSeries.setName("初诊人数");
        echartsSeries.setType("bar");
        echartsSeries.setData(firstData);
        listSeries.add(echartsSeries);
        EchartsSeries echartsSeries2 = new EchartsSeries();
        echartsSeries2.setName("总就诊人次");
        echartsSeries2.setType("bar");
        echartsSeries2.setData(renData);
        listSeries.add(echartsSeries2);
        echarts.getTitle().put("text", "初诊人数 VS 总就诊人次");
        echarts.getLegend().put("data", legendData);
        echarts.setEchartsData(listSeries);
        echarts.setxData(xData);
        webResult.setSuc(echarts);
        return webResult;
    }

    /**
     * 查询工作量统计
     * 
     * @author xdc
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = WorkAccountMapping.CREATE_EXCEL_ACCOUNT)
    public @ResponseBody
    WebResult<String> createExcelAccount(WorkAccountCondition condition) throws Exception {
        WebResult<String> webResult = new WebResult<String>();
        // 交个excel处理的数据
        Map<String, Object> resutlMap = new HashMap<String, Object>();

        // part1 诊断汇总
        List<WorkAccountPojo> accountData = workService.queryWorkAccount(condition);
        List<String> part1List = new ArrayList<String>();
        for (WorkAccountPojo workAccountPojo : accountData) {
            part1List.add(workAccountPojo.toString());
        }
        resutlMap.put("condition", condition);
        resutlMap.put("part1", part1List);

        // part2 诊断频次
        List<String> frequencyDataList = condition.getDataList();
        if (CollectionUtils.isNotEmpty(frequencyDataList)) {
            List<String[]> rowList = new ArrayList<String[]>();
            String[] colNames = new String[] {"诊断", "频次"};
            String[] legends = frequencyDataList.get(0).split("###");
            for (int i = 0; i < frequencyDataList.size(); i++) {
                rowList.add((colNames[i] + "###" + frequencyDataList.get(i)).split("###"));
            }
            AccountExcelData frequencyExcel = new AccountExcelData();
            frequencyExcel.setTitle(condition.getTitle());
            frequencyExcel.setRowList(rowList);
            frequencyExcel.setDataCols(legends.length);
            frequencyExcel.setDataRows(1);
            frequencyExcel.setSheetNum(1);
            resutlMap.put("part2", frequencyExcel);
        }

        // sheet2 chart1
        AccountExcelData accountExcelData = new AccountExcelData();
        accountExcelData.setSheetNum(1);
        accountExcelData.setDataRows(2);
        accountExcelData.setDataCols(accountData.size() - 1);// 生成图表是不计算总计列
        accountExcelData.setTitle("初诊人数 vs. 总就诊人次");
        List<String[]> rowListChart1 = new ArrayList<String[]>();// 生成图表使用的数据
        rowListChart1.add(new String[accountData.size() + 1]);
        rowListChart1.add(new String[accountData.size() + 1]);
        rowListChart1.add(new String[accountData.size() + 1]);
        rowListChart1.get(0)[0] = "分类";
        rowListChart1.get(1)[0] = "初诊人数";
        rowListChart1.get(2)[0] = "就诊人次";
        for (int i = 0; i < accountData.size(); i++) {
            rowListChart1.get(0)[i + 1] = accountData.get(i).getMonth();
            rowListChart1.get(1)[i + 1] = accountData.get(i).getDiagnosisFirst().toString();
            rowListChart1.get(2)[i + 1] = accountData.get(i).getDiagnosisRen().toString();
        }
        accountExcelData.setRowList(rowListChart1);
        resutlMap.put("part3", accountExcelData);

        // sheet2 chart2
        condition.setDiagnosisUserList(Arrays.asList(condition.getDiagnosisUser().split(",")));
        List<Object[]> outpatients = workService.queryOutpatientCount(condition);
        List<String> monthList = workService.getOutpatientDate(condition.getStartDate(), condition.getEndDate());
        monthList.add(0, "姓名");
        monthList.add("总计");
        outpatients.add(0, monthList.toArray());
        AccountExcelData accountExcelData2 = new AccountExcelData();
        accountExcelData2.setSheetNum(1);
        accountExcelData2.setDataRows(outpatients.size() - 1);
        accountExcelData2.setDataCols(monthList.size() - 2);// 出去姓名和总计
        accountExcelData2.setTitle("不同出诊单元门诊量统计");
        List<String[]> rowList = new ArrayList<String[]>();
        for (Object[] objs : outpatients) {
            String[] strs = new String[objs.length];
            for (int i = 0; i < objs.length; i++) {
                strs[i] = objs[i].toString();
            }
            rowList.add(strs);
        }
        accountExcelData2.setRowList(rowList);
        resutlMap.put("part4", accountExcelData2);

        AccountExcelData accountExcelData3 = new AccountExcelData();
        accountExcelData3.setTitle("患者分布");
        accountExcelData3.setSheetNum(1);
        accountExcelData3.setDataRows(1);
        List<String[]> rowListPart5 = new ArrayList<String[]>();
        int arrLen = outpatients.size();
        String[] rowArr1 = new String[arrLen];
        String[] rowArr2 = new String[arrLen];
        for (int i = 0; i < outpatients.size(); i++) {
            Object[] objs = outpatients.get(i);
            rowArr1[i] = objs[0].toString();
            rowArr2[i] = objs[objs.length - 1].toString();
        }
        rowListPart5.add(rowArr1);
        rowListPart5.add(rowArr2);
        accountExcelData3.setDataCols(rowArr1.length - 1);
        accountExcelData3.setRowList(rowListPart5);
        resutlMap.put("part5", accountExcelData3);

        AccountExcelData accountExcelData4 = new AccountExcelData();
        accountExcelData4.setTitle("初诊患者孕期分布");
        accountExcelData4.setSheetNum(1);
        accountExcelData4.setDataRows(1);
        List<WorkAccountPojo> pregList = workService.gestationDistribution(condition);
        int preSum = 0, midSum = 0, lateSum = 0;
        for (WorkAccountPojo pojo : pregList) {
            // 计算合计
            preSum += Integer.parseInt(pojo.getPregStagePre().toString());
            midSum += Integer.parseInt(pojo.getPregStageMid().toString());
            lateSum += Integer.parseInt(pojo.getPregStageLate().toString());
        }
        List<String[]> rowListPart6 = new ArrayList<String[]>();
        rowListPart6.add(new String[] {"孕期阶段", "早孕期", "中孕期", "晚孕期"});
        rowListPart6.add(new String[] {"总计", preSum + "", midSum + "", lateSum + ""});
        accountExcelData4.setRowList(rowListPart6);
        accountExcelData4.setDataCols(3);
        resutlMap.put("part6", accountExcelData4);

        WorkAccountExcel creatExcel = new WorkAccountExcel();
        String filePaht = creatExcel.creatExcel(resutlMap, condition.getCreateType());
        webResult.setSuc(filePaht);
        return webResult;
    }

    /**
     * 查询工作量对比
     * 
     * @author xdc
     * @param condition
     * @return
     * @throws Exception
     */
    @RequestMapping(value = WorkAccountMapping.CREATE_EXCEL_COMPARE)
    public @ResponseBody
    WebResult<String> createExcelCompare(String dataList) throws Exception {
        WebResult<String> webResult = new WebResult<String>();
        if (StringUtils.isNotBlank(dataList)) {
            WorkAccountExcel excel = new WorkAccountExcel();
            webResult.setSuc(excel.excelCompare(dataList));
        } else {
            webResult.setError("请优先在页面上生成图表");
        }
        return webResult;
    }

    // --------------------------------------------------【许道川】--------------------------------------------------

    // --------------------------------------------------【尚成达】--------------------------------------------------
    /**
     * 
     * 统计列表
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.STATISTICS_DATA_QUERY)
    public @ResponseBody
    WebResult<StatisticaListPojo> queryStatisticsData(StatisticaListCondition condition) {
        WebResult<StatisticaListPojo> webResult = new WebResult<StatisticaListPojo>();
        String endDate = JodaTimeTools.toString(
                JodaTimeTools.after_day(JodaTimeTools.toDate(condition.getEndDate()), 1),
                JodaTimeTools.FORMAT_6);
        condition.setEndDate(endDate);
        webResult.setData(workService.queryStatisticsData(condition));
        return webResult;
    }

    /**
     * 
     * 门诊量统计
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.OUTPATIENT_DATA_QUERY)
    public @ResponseBody
    WebResult<Echarts> queryOutpatientCount(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();
        webResult.setSuc(getOutPatientData(condition));
        return webResult;
    }

    /**
     * 
     * 准备门诊量统计数据
     * 
     * @author scd
     * @param condition
     * @return
     */
    public Echarts getOutPatientData(WorkAccountCondition condition) {
        Echarts echarts = new Echarts();
        // 饼状图数据
        List<Map<String, String>> mpieList = new ArrayList<Map<String, String>>();
        // 柱状图数据
        List<Object[]> outpatients = workService.queryOutpatientCount(condition);
        List<String> name = new ArrayList<String>();// 数据名称
        List<String> xData = new ArrayList<String>();// x轴数据
        List<EchartsSeries> seriesList = new ArrayList<EchartsSeries>();// 图表数据 柱形图
        // 准备数据名称以及图表数据
        if (CollectionUtils.isNotEmpty(outpatients)) {
            for (Object[] outpatient : outpatients) {
                if (outpatient.length > 0) {
                    name.add((String) outpatient[0]);
                }
            }
            // 图例名称可重复
            name = this.formatEqualStr(name);
            int indexBar = 0;// 柱状图
            int indexPie = 0;// 饼状图
            for (Object[] outpatient : outpatients) {
                EchartsSeries echartsSeries = new EchartsSeries();// 柱形图
                List<Integer> seriesData = new ArrayList<Integer>();// 柱形图x轴数据
                for (int i = 0; i < outpatient.length; i++) {
                    if (i == 0) {
                        echartsSeries.setName(name.get(indexBar));// 柱形图
                        indexBar++;
                    } else if (i < outpatient.length - 1) {
                        seriesData.add(Integer.parseInt(outpatient[i].toString()));
                    }
                }
                // 饼形图数据
                Map<String, String> mapData = new HashMap<>();
                mapData.put("name", name.get(indexPie));
                indexPie++;
                mapData.put("value", outpatient[outpatient.length - 1].toString());
                mpieList.add(mapData);
                // 柱形图数据
                echartsSeries.setType("bar");
                echartsSeries.setData(seriesData.toArray(new Integer[seriesData.size()]));
                seriesList.add(echartsSeries);
            }
        }

        // 准备x轴数据
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            List<String> months = workService.getOutpatientDate(startDate, endDate);
            if (CollectionUtils.isNotEmpty(months)) {
                for (String month : months) {
                    xData.add(month);
                }
            }
        }

        // 填充数据 柱形图数据
        Map<String, Object> legend = echarts.getLegend();
        legend.put("data", name.toArray(new String[name.size()]));
        echarts.getTitle().put("text", "不同医师门诊量");
        echarts.setxData(xData.toArray(new String[xData.size()]));
        echarts.setEchartsData(seriesList);
        echarts.setPieData(mpieList);
        echarts.getGridBar().put("y", 120);
        // echarts.getGridBar().put("x", 40);
        return echarts;
    }

    /**
     * 
     * 获取孕期初诊患者分布数据
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.DISTRIBUTION_DATA_QUERY)
    public @ResponseBody
    WebResult<List<Echarts>> gestationDistribution(WorkAccountCondition condition) {
        WebResult<List<Echarts>> webResult = new WebResult<List<Echarts>>();
        List<WorkAccountPojo> pregList = workService.gestationDistribution(condition);
        List<Echarts> echarts = getPregDistributionData(pregList);
        webResult.setData(echarts);
        return webResult;
    }

    /**
     * 
     * 组装数据
     * 
     * @param pregList
     * @return
     */
    public List<Echarts> getPregDistributionData(List<WorkAccountPojo> pregList) {
        String[] pregStage = new String[] {"孕早期", "孕中期", "孕晚期"};
        String sumName = "总计";

        Integer preSum = 0;
        Integer midSum = 0;
        Integer lateSum = 0;
        // 准备数据模板
        List<Echarts> echartsList = new ArrayList<Echarts>();
        if (CollectionUtils.isNotEmpty(pregList)) {
            for (WorkAccountPojo pojo : pregList) {
                // 计算合计
                preSum = preSum + Integer.parseInt(pojo.getPregStagePre().toString());
                midSum = midSum + Integer.parseInt(pojo.getPregStageMid().toString());
                lateSum = lateSum + Integer.parseInt(pojo.getPregStageLate().toString());
                // 饼形图数据模板
                List<Map<String, String>> pieData = new ArrayList<Map<String, String>>();
                Map<String, String> preMap = new HashMap<String, String>();
                Echarts echarts = new Echarts();
                // 填充数据--标题
                echarts.getTitle().put("text", pojo.getDiagnosisUserName());
                // 填充数据--名称
                echarts.getLegend().put("data", pregStage);
                // 填充数据--孕早期
                preMap.put("name", pregStage[0]);
                preMap.put("value", pojo.getPregStagePre().toString());
                pieData.add(preMap);
                // 填充数据--孕中期
                Map<String, String> midMap = new HashMap<String, String>();
                midMap.put("name", pregStage[1]);
                midMap.put("value", pojo.getPregStageMid().toString());
                pieData.add(midMap);
                // 填充数据--孕晚期
                Map<String, String> lateMap = new HashMap<String, String>();
                lateMap.put("name", pregStage[2]);
                lateMap.put("value", pojo.getPregStageLate().toString());
                pieData.add(lateMap);

                echarts.setPieData(pieData);
                echartsList.add(echarts);
            }

            // 饼形图数据模板 --总计
            List<Map<String, String>> pieSumData = new ArrayList<Map<String, String>>();
            Map<String, String> preSumMap = new HashMap<String, String>();
            Echarts echartSum = new Echarts();
            // 填充数据--标题
            echartSum.getTitle().put("text", sumName);
            // 填充数据--名称
            echartSum.getLegend().put("data", pregStage);
            // 填充数据--孕早期
            preSumMap.put("name", pregStage[0]);
            preSumMap.put("value", preSum.toString());
            pieSumData.add(preSumMap);
            // 填充数据--孕中期
            Map<String, String> midSumMap = new HashMap<String, String>();
            midSumMap.put("name", pregStage[1]);
            midSumMap.put("value", midSum.toString());
            pieSumData.add(midSumMap);
            // 填充数据--孕晚期
            Map<String, String> lateSumMap = new HashMap<String, String>();
            lateSumMap.put("name", pregStage[2]);
            lateSumMap.put("value", lateSum.toString());
            pieSumData.add(lateSumMap);

            echartSum.setPieData(pieSumData);
            echartsList.add(echartSum);
        }
        return echartsList;
    }

    // --------------------------------------------------【尚成达】--------------------------------------------------

    // --------------------------------------------------【董宏生】--------------------------------------------------

    /**
     * 就诊人数/初诊人数
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.DIAGNOSIS_ACCOUNT_QUERY_ECHART)
    public @ResponseBody
    WebResult<Echarts> queryDiagnosisPersonCountEcharts(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();
        Echarts echarts = new Echarts();
        List<EchartsSeries> echartList = new ArrayList<EchartsSeries>();
        WorkAccountCondition lastCondition = new WorkAccountCondition();
        lastCondition.setStartDate(condition.getStartDate2());
        lastCondition.setEndDate(condition.getEndDate2());
        lastCondition.setType(condition.getType());
        lastCondition.setDiagnosisUser(condition.getDiagnosisUser());
        List<String> userName = condition.getDiagnosisUserNameList();// 全部的医师姓名
        List<WorkAccountPojo> list = workService.queryCountDiagnosisScopeDept(condition);
        List<WorkAccountPojo> list2 = workService.queryCountDiagnosisScopeDept(lastCondition);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = new LinkedMap();// 医师Id:人数 (第一段时间)
        @SuppressWarnings("unchecked")
        Map<String, Object> map2 = new LinkedMap();// 医师Id:人数 (第二段时间)
        if (condition.getDiagnosisUser().contains(",")
                && condition.getDiagnosisUser().split(",").length == condition.getDiagnosisUserList().size()) {
            for (String allUserId : condition.getDiagnosisUserList()) {
                map.put(allUserId, 0);
                map2.put(allUserId, 0);
            }
        } else {
            String[] strings = condition.getDiagnosisUser().split(",");
            for (String chooseUserId : strings) {
                map.put(chooseUserId, 0);
                map2.put(chooseUserId, 0);
            }
            userName = condition.getChooseDiagnosisUserName();
        }
        // 图例名称可重复
        userName = this.formatEqualStr(userName);

        for (String key : map.keySet()) {
            for (WorkAccountPojo work : list) {
                if (work.getDiagnosisUser().equals(key)) {
                    map.put(work.getDiagnosisUser(), work.getDiagnosisNum());
                }
            }
            for (WorkAccountPojo work : list2) {
                if (work.getDiagnosisUser().equals(key)) {
                    map2.put(work.getDiagnosisUser(), work.getDiagnosisNum());
                }
            }
        }
        int x = 0;
        for (String key : map.keySet()) {
            EchartsSeries echartsSeries = new EchartsSeries();
            Integer[] array = new Integer[2];
            array[0] = Integer.parseInt(map.get(key).toString());
            array[1] = Integer.parseInt(map2.get(key).toString());
            echartsSeries.setName(userName.get(x));
            echartsSeries.setType("bar");
            echartsSeries.setData(array);
            echartList.add(echartsSeries);
            x++;
        }
        String[] xDatas = new String[2];
        xDatas[0] = this.formatDate(condition.getStartDate(), condition.getEndDate());
        xDatas[1] = this.formatDate(condition.getStartDate2(), condition.getEndDate2());
        if ("firstDiagnosis".equals(condition.getType())) {
            echarts.getTitle().put("text", "初诊人数对比");
        } else {
            echarts.getTitle().put("text", "就诊人数对比");
        }
        echarts.getLegend().put("data", userName.toArray());
        echarts.getGridLine().put("x", 140);
        echarts.setxData(xDatas);
        echarts.setEchartsData(echartList);
        webResult.setSuc(echarts);
        return webResult;
    }

    /**
     * 复诊率（复诊率 = （就诊人数-初诊人数）* 100%）
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.FURTHER_RATE_QUERY_ECHART)
    public @ResponseBody
    WebResult<Echarts> queryFurtherRateEcharts(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();
        Echarts echarts = new Echarts();
        List<EchartsSeries> echartList = new ArrayList<EchartsSeries>();
        WorkAccountCondition lastCondition = new WorkAccountCondition();
        lastCondition.setStartDate(condition.getStartDate2());
        lastCondition.setEndDate(condition.getEndDate2());
        lastCondition.setDiagnosisUser(condition.getDiagnosisUser());
        WorkAccountCondition rateCondition = new WorkAccountCondition();
        rateCondition.setStartDate(condition.getStartDate());
        rateCondition.setEndDate(condition.getEndDate());
        rateCondition.setType("firstDiagnosis");
        rateCondition.setDiagnosisUser(condition.getDiagnosisUser());
        WorkAccountCondition rateCondition2 = new WorkAccountCondition();
        rateCondition2.setStartDate(condition.getStartDate2());
        rateCondition2.setEndDate(condition.getEndDate2());
        rateCondition2.setType("firstDiagnosis");
        rateCondition2.setDiagnosisUser(condition.getDiagnosisUser());
        List<String> userName = condition.getDiagnosisUserNameList();// 全部的医师姓名
        List<WorkAccountPojo> list = workService.queryCountDiagnosisScopeDept(condition);// 就诊人数（第一个时间断）
        List<WorkAccountPojo> list2 = workService.queryCountDiagnosisScopeDept(lastCondition);// 就诊人数（第二个时间断）
        List<WorkAccountPojo> list3 = workService.queryCountDiagnosisScopeDept(rateCondition);// 初诊人数（第一个时间断）
        List<WorkAccountPojo> list4 = workService.queryCountDiagnosisScopeDept(rateCondition2);// 初诊人数（第二个时间断）
        @SuppressWarnings("unchecked")
        Map<String, Object> map = new LinkedMap();// 医师Id:人数 (就诊人数第一段时间)
        @SuppressWarnings("unchecked")
        Map<String, Object> map2 = new LinkedMap();// 医师Id:人数 (就诊人数第二段时间)
        @SuppressWarnings("unchecked")
        Map<String, Object> map3 = new LinkedMap();// 医师Id:人数 (初诊人数第一段时间)
        @SuppressWarnings("unchecked")
        Map<String, Object> map4 = new LinkedMap();// 医师Id:人数 (初诊人数第二段时间)
        if (condition.getDiagnosisUser().contains(",")
                && condition.getDiagnosisUser().split(",").length == condition.getDiagnosisUserList().size()) {
            for (String allUserId : condition.getDiagnosisUserList()) {
                map.put(allUserId, 0);
                map2.put(allUserId, 0);
                map3.put(allUserId, 0);
                map4.put(allUserId, 0);
            }
        } else {
            String[] strings = condition.getDiagnosisUser().split(",");
            for (String chooseUserId : strings) {
                map.put(chooseUserId, 0);
                map2.put(chooseUserId, 0);
                map3.put(chooseUserId, 0);
                map4.put(chooseUserId, 0);
            }
            userName = condition.getChooseDiagnosisUserName();
        }
        // 图例名称可重复
        userName = this.formatEqualStr(userName);

        for (String key : map.keySet()) {
            for (WorkAccountPojo work : list) {
                if (work.getDiagnosisUser().equals(key)) {
                    map.put(work.getDiagnosisUser(), work.getDiagnosisNum());
                }
            }
            for (WorkAccountPojo work : list2) {
                if (work.getDiagnosisUser().equals(key)) {
                    map2.put(work.getDiagnosisUser(), work.getDiagnosisNum());
                }
            }
            for (WorkAccountPojo work : list3) {
                if (work.getDiagnosisUser().equals(key)) {
                    map3.put(work.getDiagnosisUser(), work.getDiagnosisNum());
                }
            }
            for (WorkAccountPojo work : list4) {
                if (work.getDiagnosisUser().equals(key)) {
                    map4.put(work.getDiagnosisUser(), work.getDiagnosisNum());
                }
            }
        }
        int x = 0;
        for (String key : map.keySet()) {
            EchartsSeries echartsSeries = new EchartsSeries();
            Integer[] array = new Integer[2];
            array[0] = Integer.parseInt(this.getReturnVisit(Integer.parseInt(map3.get(key).toString()),
                    Integer.parseInt(map.get(key).toString())));
            array[1] = Integer.parseInt(this.getReturnVisit(Integer.parseInt(map4.get(key).toString()),
                    Integer.parseInt(map2.get(key).toString())));
            echartsSeries.setName(userName.get(x));
            echartsSeries.setType("bar");
            echartsSeries.setData(array);
            echartList.add(echartsSeries);
            x++;
        }
        String[] xDatas = new String[2];
        xDatas[0] = this.formatDate(condition.getStartDate(), condition.getEndDate());
        xDatas[1] = this.formatDate(condition.getStartDate2(), condition.getEndDate2());
        echarts.getTitle().put("text", "复诊率");
        echarts.getLegend().put("data", userName.toArray());
        echarts.getGridLine().put("x", 140);
        echarts.setxData(xDatas);
        echarts.setEchartsData(echartList);
        webResult.setSuc(echarts);
        return webResult;
    }

    /**
     * 格式化日期 1994-01-15 1994-01-16 -> 19940115-19940116
     * 
     * @author dhs
     * @param
     * @return
     */
    private String formatDate(String date1, String date2) {
        return date1.replaceAll("-", "") + "-" + date2.replaceAll("-", "");
    }

    /**
     * 复诊率 = (就诊人数-初诊人数) / 就诊人数 * 100%
     * 
     * @author dhs
     * @param custId
     * @param bookingDate
     * @return
     */
    private String getReturnVisit(int firstCount, int diagnosisCount) {
        if (diagnosisCount == 0 || firstCount == diagnosisCount) {
            return "0";
        } else {
            int returnCount = diagnosisCount - firstCount;
            float num = (float) returnCount / (float) diagnosisCount * 100;
            DecimalFormat decimalFormat = new DecimalFormat("0");// 四舍五入保留整数
            int INum = Integer.parseInt(decimalFormat.format(num));
            return String.valueOf(INum);
        }
    }

    // --------------------------------------------------【董宏生】--------------------------------------------------

    // --------------------------------------------------【张传强】--------------------------------------------------
    /**
     * 工作量统计（部门）--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_DISEASE_FREQUENCY)
    public @ResponseBody
    WebResult<Integer> countDiseaseFrequency(WorkAccountCondition condition) {
        WebResult<Integer> webResult = new WebResult<Integer>();
        webResult.setSuc(workService.countDiseaseFrequency(condition));
        return webResult;
    }

    /**
     * 工作量统计（个人）--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_PERSON_FREQUENCY)
    public @ResponseBody
    WebResult<Integer> countPersonFrequency(WorkAccountCondition condition) {
        WebResult<Integer> webResult = new WebResult<Integer>();
        condition.setDiagnosisUser(this.getLoginUserId());
        webResult.setSuc(workService.countDiseaseFrequency(condition));
        return webResult;
    }

    /**
     * 工作量对比（部门）--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_COMPARE_DISEASE_FREQUENCY)
    public @ResponseBody
    WebResult<Echarts> countCompareDiseaseFrequency(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();

        List<String> diseaseIdList = condition.getDiseaseIdList();
        List<String> diseaseNameList = condition.getDiseaseNameList();
        List<String> userIdList = condition.getDiagnosisUserList();
        List<String> userNameList = condition.getDiagnosisUserNameList();

        // 图例名称可重复
        userNameList = this.formatEqualStr(userNameList);

        // 查询结果
        List<WorkAccountPojo> resultList = workService.countCompareDiseaseFrequency(condition);
        // 遍历结果
        Map<String, String> userMap = new HashMap<String, String>();
        for (int i = 0; i < userIdList.size(); i++) {
            userMap.put(userIdList.get(i), userNameList.get(i));
        }

        Map<String, Map<String, Integer>> resultMap = new HashMap<String, Map<String, Integer>>();
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (WorkAccountPojo account : resultList) {
                String diseaseId = account.getDiseaseId();
                String userId = account.getDiagnosisUser();
                Object count = account.getDiseaseFrequency();
                if (account.getDiseaseFrequency() == null) {
                    count = "0";
                }
                if (!resultMap.containsKey(userId)) {
                    resultMap.put(userId, new HashMap<String, Integer>());
                }
                resultMap.get(userId).put(diseaseId, Integer.valueOf(count.toString()));
            }
        }

        Map<String, Integer[]> userNumMap = new HashMap<String, Integer[]>();
        for (int i = 0; i < userIdList.size(); i++) {
            Integer[] array = new Integer[diseaseIdList.size()];
            for (int a = 0; a < diseaseIdList.size(); a++) {
                array[a] = 0;
            }
            String userId = userIdList.get(i);
            Map<String, Integer> tempMap = resultMap.get(userId);
            if (tempMap != null) {
                for (int j = 0; j < diseaseIdList.size(); j++) {
                    String diseaseId = diseaseIdList.get(j);
                    if (tempMap.get(diseaseId) != null) {
                        array[j] = tempMap.get(diseaseId);
                    }
                }
            }
            userNumMap.put(userId, array);
        }

        List<EchartsSeries> echartList = new ArrayList<EchartsSeries>();
        for (String userId : userNumMap.keySet()) {
            EchartsSeries echartsSeries = new EchartsSeries();
            echartsSeries.setName(userMap.get(userId));
            echartsSeries.setType("bar");
            echartsSeries.setData(userNumMap.get(userId));
            echartsSeries.setId(userId);
            echartList.add(echartsSeries);
        }

        // 设置echart结果数据
        Echarts echarts = new Echarts();
        echarts.getTitle().put("text", "诊断频次");
        echarts.getLegend().put("data", userNameList.toArray(new String[userNameList.size()]));
        echarts.setxData(diseaseNameList.toArray(new String[diseaseNameList.size()])); // 设置 x轴数据
        echarts.setEchartsData(echartList);// 设置 y轴数据
        echarts.getGridBar().put("y2", 70);// 设置下边距
        if (CollectionUtils.isNotEmpty(diseaseNameList)) {// 设置滚动轴
            if (diseaseNameList.size() > 3) {// 超过2条记录时，设置滚动轴
                Map<String, Object> dataZoomMap = new HashMap<String, Object>();
                dataZoomMap.put("type", "slider");
                dataZoomMap.put("show", true);
                dataZoomMap.put("startValue", 0);
                dataZoomMap.put("endValue", 2);
                echarts.setxDataZoom(dataZoomMap);
            }
        }
        return webResult.setSuc(echarts);
    }

    /**
     * 工作量对比（个人）--诊断频次
     * 
     * @author zcq
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_PERSON_DISEASE_FREQUENCY)
    public @ResponseBody
    WebResult<Echarts> countPersonDiseaseFrequency(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();

        List<String> diseaseIdList = condition.getDiseaseIdList();
        List<String> diseaseNameList = condition.getDiseaseNameList();
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        String startDate2 = condition.getStartDate2();
        String endDate2 = condition.getEndDate2();

        Map<String, String> diseaseMap = new HashMap<String, String>();
        for (int i = 0; i < diseaseIdList.size(); i++) {
            diseaseMap.put(diseaseIdList.get(i), diseaseNameList.get(i));
        }

        // 时间段
        String dateTime1 = startDate.replace("-", "/") + " ~ " + endDate.replace("-", "/");
        String dateTime2 = startDate2.replace("-", "/") + " ~ " + endDate2.replace("-", "/");
        String[] dateArray = null;

        Map<String, Integer[]> diseaseNumMap = new HashMap<String, Integer[]>();
        if (CollectionUtils.isNotEmpty(diseaseIdList)) {
            dateArray = new String[] {dateTime1, dateTime2};

            // 查询第一段时间
            condition.setDiagnosisUser(this.getLoginUserId());
            List<WorkAccountPojo> resultList1 = workService.countCompareDiseaseFrequency(condition);
            // 查询第二段时间
            condition.setStartDate(startDate2);
            condition.setEndDate(endDate2);
            List<WorkAccountPojo> resultList2 = workService.countCompareDiseaseFrequency(condition);

            Map<String, Map<String, Integer>> resultMap = new HashMap<String, Map<String, Integer>>();
            if (CollectionUtils.isNotEmpty(resultList1)) {
                for (WorkAccountPojo account : resultList1) {
                    String diseaseId = account.getDiseaseId();
                    Object count = account.getDiseaseFrequency();
                    if (account.getDiseaseFrequency() == null) {
                        count = "0";
                    }
                    if (!resultMap.containsKey(dateTime1)) {
                        resultMap.put(dateTime1, new HashMap<String, Integer>());
                    }
                    resultMap.get(dateTime1).put(diseaseId, Integer.valueOf(count.toString()));
                }
            }
            if (CollectionUtils.isNotEmpty(resultList2)) {
                for (WorkAccountPojo account : resultList2) {
                    String diseaseId = account.getDiseaseId();
                    Object count = account.getDiseaseFrequency();
                    if (account.getDiseaseFrequency() == null) {
                        count = "0";
                    }
                    if (!resultMap.containsKey(dateTime2)) {
                        resultMap.put(dateTime2, new HashMap<String, Integer>());
                    }
                    resultMap.get(dateTime2).put(diseaseId, Integer.valueOf(count.toString()));
                }
            }

            Map<String, Integer> tempMap1 = resultMap.get(dateArray[0]);
            Map<String, Integer> tempMap2 = resultMap.get(dateArray[1]);
            for (String diseaseId : diseaseIdList) {
                Integer[] array = new Integer[] {0, 0};
                if (tempMap1 != null && tempMap1.get(diseaseId) != null) {
                    array[0] = tempMap1.get(diseaseId);
                }
                if (tempMap2 != null && tempMap2.get(diseaseId) != null) {
                    array[1] = tempMap2.get(diseaseId);
                }
                diseaseNumMap.put(diseaseId, array);
            }

        }

        List<EchartsSeries> echartList = new ArrayList<EchartsSeries>();
        for (String diseaseId : diseaseNumMap.keySet()) {
            EchartsSeries echartsSeries = new EchartsSeries();
            echartsSeries.setName(diseaseMap.get(diseaseId));
            echartsSeries.setType("bar");
            echartsSeries.setData(diseaseNumMap.get(diseaseId));
            echartsSeries.setId(diseaseId);
            echartList.add(echartsSeries);
        }

        // 设置echart结果数据
        Echarts echarts = new Echarts();
        echarts.getTitle().put("text", "诊断频次");
        echarts.getLegend().put("data", diseaseNameList.toArray(new String[diseaseNameList.size()]));
        echarts.setxData(dateArray); // 设置 x轴数据
        echarts.setEchartsData(echartList);// 设置 y轴数据

        return webResult.setSuc(echarts);
    }

    // --------------------------------------------------【张传强】--------------------------------------------------

    /******************************************** 【工作量统计（个人）】 ****************************************************/

    /**
     * 查询工作量统计（个人）
     * 
     * @author zcq
     * @param model
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.WORK_ACCOUNT_PERSON_PAGE)
    public String workAccountPersonPage(Model model) {
        // 获取所有的医生
        User condition = new User();
        condition.setUserType("doctor");
        model.addAttribute("userList", NetJsonUtils.listToJsonArray(userService.queryUsers(condition)));
        // 获取所有诊断项目列表
        List<InterveneDiseasePojo> diseaseList = diseaseService.queryInterveneDisease(null);
        model.addAttribute("diseaseList", NetJsonUtils.listToJsonArray(diseaseList));
        // 获取所有诊断项目map
        Map<String, InterveneDiseasePojo> diseaseMap = new HashMap<String, InterveneDiseasePojo>();
        for (InterveneDiseasePojo diseasePojo : diseaseList) {
            diseaseMap.put(diseasePojo.getDiseaseName(), diseasePojo);
        }

        UserPojo loginUser = this.getLoginUser().getUserPojo();
        List<UserPojo> userPojos = userAssistantService.queryDoctorOrAssistant(loginUser.getUserId());
        userPojos.add(loginUser);
        model.addAttribute("masItemAuthor", NetJsonUtils.listToJsonArray(userPojos));
        model.addAttribute("masItemNames", NetJsonUtils.listToJsonArray(inspectItemService.queryInspectItem(null)));
        model.addAttribute("diseaseMap", NetJsonUtils.objectToJson(diseaseMap));
        model.addAttribute("zhuanDoctorList", NetJsonUtils.listToJsonArray(referralDoctorService.queryDoctors(null)));
        model.addAttribute("userId", this.getLoginUserId());
        return WorkAccountPageName.WORK_ACCOUNT_PERSON_VIEW;
    }

    // --------------------------------------------------【尚成达】--------------------------------------------------
    /**
     * 
     * 统计列表（个人）
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.STATISTICS_DATA_PERSON_QUERY)
    public @ResponseBody
    WebResult<StatisticaListPojo> queryStatisticsDataPErson(StatisticaListCondition condition) {
        WebResult<StatisticaListPojo> webResult = new WebResult<StatisticaListPojo>();
        String endDate = JodaTimeTools.toString(
                JodaTimeTools.after_day(JodaTimeTools.toDate(condition.getEndDate()), 1),
                JodaTimeTools.FORMAT_6);
        condition.setEndDate(endDate);
        String userId = getLoginUserId();
        condition.setDiagnosisUser(userId);
        webResult.setData(workService.queryStatisticsData(condition));
        return webResult;
    }

    /**
     * 
     * 获取孕期初诊患者分布数据（个人）
     * 
     * @author scd
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.DISTRIBUTION_DATA_QUERY_PERSON)
    public @ResponseBody
    WebResult<List<Echarts>> gestationDistributionPerson(WorkAccountCondition condition) {
        WebResult<List<Echarts>> webResult = new WebResult<List<Echarts>>();
        List<Echarts> echarts = getPregDistributionDataPerson(condition);
        webResult.setData(echarts);
        return webResult;
    }

    /**
     * 
     * 组装孕期分布（个人）
     * 
     * @author scd
     * @param condition
     * @return
     */
    public List<Echarts> getPregDistributionDataPerson(WorkAccountCondition condition) {
        List<Echarts> echarts = new ArrayList<Echarts>();
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        String startDate2 = condition.getStartDate2();
        String endDate2 = condition.getEndDate2();
        String userId = getLoginUserId();
        // 设置检索条件--第一个时间段
        condition.setDiagnosisUser(userId);
        List<WorkAccountPojo> pregList1 = workService.gestationDistribution(condition);
        if (CollectionUtils.isNotEmpty(pregList1)) {
            WorkAccountPojo pojo1 = pregList1.get(0);
            Echarts echarts1 = getEcharts(startDate, endDate, pojo1);
            echarts.add(echarts1);
        } else {
            Echarts echarts1 = getEcharts(startDate, endDate, null);
            echarts.add(echarts1);
        }
        // 设置检索条件--第二个时间段
        condition.setStartDate(startDate2);
        condition.setEndDate(endDate2);
        List<WorkAccountPojo> pregList2 = workService.gestationDistribution(condition);
        if (CollectionUtils.isNotEmpty(pregList2)) {
            WorkAccountPojo pojo2 = pregList2.get(0);
            Echarts echarts2 = getEcharts(startDate2, endDate2, pojo2);
            echarts.add(echarts2);
        } else {
            Echarts echarts2 = getEcharts(startDate2, endDate2, null);
            echarts.add(echarts2);
        }
        return echarts;
    }

    /**
     * 
     * 获取echarts模板
     * 
     * @author scd
     * @param startDate
     * @param endDate
     * @param pojo
     * @return
     */
    public Echarts getEcharts(String startDate, String endDate, WorkAccountPojo pojo) {
        String pregStagePre = "孕早期";
        String pregStageMid = "孕中期";
        String pregStageLate = "孕晚期";
        String[] names = new String[] {"孕早期", "孕中期", "孕晚期"};
        // 饼形图数据模板 --总计
        List<Map<String, String>> pieData = new ArrayList<Map<String, String>>();
        Map<String, String> preMap = new HashMap<String, String>();
        Echarts echart = new Echarts();
        // 填充数据--標題
        echart.getTitle().put("text", startDate + "~" + endDate);
        // 填充数据--名称
        echart.getLegend().put("data", names);
        // 填充数据--孕早期
        String preNum = "0";
        if (pojo != null) {
            preNum = pojo.getPregStagePre().toString();
        }
        preMap.put("name", pregStagePre);
        preMap.put("value", preNum);
        pieData.add(preMap);
        // 填充数据--孕中期
        String midNum = "0";
        if (pojo != null) {
            midNum = pojo.getPregStageMid().toString();
        }
        Map<String, String> midMap = new HashMap<String, String>();
        midMap.put("name", pregStageMid);
        midMap.put("value", midNum);
        pieData.add(midMap);
        // 填充数据--孕晚期
        String lateNum = "0";
        if (pojo != null) {
            lateNum = pojo.getPregStageLate().toString();
        }
        Map<String, String> lateMap = new HashMap<String, String>();
        lateMap.put("name", pregStageLate);
        lateMap.put("value", lateNum);
        pieData.add(lateMap);
        echart.setPieData(pieData);
        return echart;
    }

    // --------------------------------------------------【尚成达】--------------------------------------------------

    /**
     * 就诊人数/初诊人数
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.DIAGNOSIS_ACCOUNT_QUERY_ECHART_PERSON)
    public @ResponseBody
    WebResult<Echarts> queryDiagnosisPersonCountEchartsPerson(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();
        Echarts echarts = new Echarts();
        List<EchartsSeries> echartList = new ArrayList<EchartsSeries>();
        List<String> userName = condition.getDiagnosisUserNameList();
        WorkAccountCondition lastCondition = new WorkAccountCondition();
        lastCondition.setStartDate(condition.getStartDate2());
        lastCondition.setEndDate(condition.getEndDate2());
        lastCondition.setType(condition.getType());
        EchartsSeries echartsSeries = new EchartsSeries();
        Integer[] array = new Integer[2];
        condition.setDiagnosisUser(condition.getDiagnosisUser());
        lastCondition.setDiagnosisUser(condition.getDiagnosisUser());
        array[0] = workService.queryCountDiagnosisScope(condition);
        array[1] = workService.queryCountDiagnosisScope(lastCondition);
        echartsSeries.setName(userName.get(0));
        echartsSeries.setType("bar");
        echartsSeries.setData(array);
        echartList.add(echartsSeries);
        String[] xDatas = new String[2];
        xDatas[0] = this.formatDate(condition.getStartDate(), condition.getEndDate());
        xDatas[1] = this.formatDate(condition.getStartDate2(), condition.getEndDate2());
        if ("firstDiagnosis".equals(condition.getType())) {
            echarts.getTitle().put("text", "初诊人数对比");
        } else {
            echarts.getTitle().put("text", "就诊人数对比");
        }
        echarts.getLegend().put("data", userName.toArray());
        echarts.getGridLine().put("x", 140);
        echarts.setxData(xDatas);
        echarts.setEchartsData(echartList);
        webResult.setSuc(echarts);
        return webResult;
    }

    /**
     * 复诊率
     * 
     * @author dhs
     * @param condition
     * @return
     */
    @RequestMapping(value = WorkAccountMapping.FURTHER_RATE_QUERY_ECHART_PERSON)
    public @ResponseBody
    WebResult<Echarts> queryFurtherRateEchartsPerson(WorkAccountCondition condition) {
        WebResult<Echarts> webResult = new WebResult<Echarts>();
        Echarts echarts = new Echarts();
        List<EchartsSeries> echartList = new ArrayList<EchartsSeries>();
        List<String> userName = condition.getDiagnosisUserNameList();
        WorkAccountCondition lastCondition = new WorkAccountCondition();
        lastCondition.setStartDate(condition.getStartDate2());
        lastCondition.setEndDate(condition.getEndDate2());
        WorkAccountCondition rateCondition = new WorkAccountCondition();// 初诊人数（第一个时间断）
        rateCondition.setStartDate(condition.getStartDate());
        rateCondition.setEndDate(condition.getEndDate());
        rateCondition.setType("firstDiagnosis");
        WorkAccountCondition rateCondition2 = new WorkAccountCondition();// 初诊人数（第二个时间断）
        rateCondition2.setStartDate(condition.getStartDate2());
        rateCondition2.setEndDate(condition.getEndDate2());
        rateCondition2.setType("firstDiagnosis");
        EchartsSeries echartsSeries = new EchartsSeries();
        Integer[] array = new Integer[2];
        condition.setDiagnosisUser(condition.getDiagnosisUser());
        lastCondition.setDiagnosisUser(condition.getDiagnosisUser());
        rateCondition.setDiagnosisUser(condition.getDiagnosisUser());
        rateCondition2.setDiagnosisUser(condition.getDiagnosisUser());
        int allCount1 = workService.queryCountDiagnosisScope(condition);
        int allCount2 = workService.queryCountDiagnosisScope(lastCondition);
        int returnVisitCount1 = workService.queryCountDiagnosisScope(rateCondition);
        int returnVisitCount2 = workService.queryCountDiagnosisScope(rateCondition2);
        array[0] = Integer.parseInt(this.getReturnVisit(returnVisitCount1, allCount1));
        array[1] = Integer.parseInt(this.getReturnVisit(returnVisitCount2, allCount2));
        echartsSeries.setName(userName.get(0));
        echartsSeries.setType("bar");
        echartsSeries.setData(array);
        echartList.add(echartsSeries);
        String[] xDatas = new String[2];
        xDatas[0] = this.formatDate(condition.getStartDate(), condition.getEndDate());
        xDatas[1] = this.formatDate(condition.getStartDate2(), condition.getEndDate2());
        echarts.getTitle().put("text", "复诊率");
        echarts.getLegend().put("data", userName.toArray());
        echarts.getGridLine().put("x", 140);
        echarts.setxData(xDatas);
        echarts.setEchartsData(echartList);
        webResult.setSuc(echarts);
        return webResult;
    }

    // ----------------------------------------------工具方法--------------------------------------------------

    /**
     * 一个集合中，如果有相同的元素，加上标记使其不同
     * 格式：,1 ,2 ,3
     * 
     * @author dhs
     * @param condition
     * @return
     * @return
     */
    public List<String> formatEqualStr(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        int count = 0;
        for (int x = 0; x < list.size() - 1; x++) {
            for (int y = x + 1; y < list.size(); y++) {
                count++;
                if (list.get(x).equals(list.get(y))) {
                    list.set(y, list.get(y) + "," + count);
                }
            }
        }
        return list;
    }

}
