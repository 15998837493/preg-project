
package com.mnt.preg.web.excel;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.jxcell.CellException;
import com.jxcell.CellFormat;
import com.jxcell.ChartFormat;
import com.jxcell.ChartShape;
import com.jxcell.RangeRef;
import com.jxcell.View;
import com.mnt.health.utils.json.NetJsonUtils;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.account.condition.WorkAccountCondition;
import com.mnt.preg.system.service.CodeServiceImpl;
import com.mnt.preg.web.CacheProjectInfo;
import com.mnt.preg.web.constants.ConstantPath;

@Component
public class WorkAccountExcel {

    // 图表类型
    private static final Map<String, Short> chartType = new HashMap<String, Short>();
    static {
        chartType.put("colnum", ChartShape.TypeColumn);
        chartType.put("bar", ChartShape.TypeBar);
    }

    @SuppressWarnings("unchecked")
    public String creatExcel(Map<String, Object> resutlMap, String type) throws Exception {
        View view = new View();
        view.setNumSheets(2);
        view.setSheetName(0, "工作表1");
        view.setSheetName(1, "工作表2");

        // 第一个Sheet
        view.setSheet(0);
        int initRow = 2;
        int initCol = 1;
        AccountExcelData part2 = (AccountExcelData) resutlMap.get("part2");
        initRow = createPart1Excel(initRow, initCol, view, (List<String>) resutlMap.get("part1"),
                (WorkAccountCondition) resutlMap.get("condition"), part2);

        // 第二个Sheet---第一个图表
        view.setSheet(1);
        int initRow2 = 2;

        AccountExcelData part3 = (AccountExcelData) resutlMap.get("part3");
        part3.setInitRow(initRow2);
        initRow2 = createTableAndChart(view, part3);

        // 判断如果如果是个人的则 不生成part4和part5
        if (!"person".equals(type)) {
            AccountExcelData part4 = (AccountExcelData) resutlMap.get("part4");
            part4.setInitRow(initRow2);
            initRow2 = createTableAndChart(view, part4);

            AccountExcelData part5 = (AccountExcelData) resutlMap.get("part5");
            part5.setInitRow(initRow2);
            initRow2 = createTableAndChartPIE(view, part5);
        }

        AccountExcelData part6 = (AccountExcelData) resutlMap.get("part6");
        part6.setInitRow(initRow2);
        initRow2 = createTableAndChartPIE(view, part6);

        part2.setInitRow(initRow2);
        initRow2 = createTableAndChart(view, part2);

        // 资源文件位置 + resource目录下的位置 + 机构号 + 生成日期 + 文件名
        CodeServiceImpl codeService = (CodeServiceImpl) CacheProjectInfo.getInstance().getApplicationContext()
                .getBean("codeServiceImpl");
        String fileName = "/compareExcel.xlsx";
        String insId = codeService.getInsId();
        String absolutePath = this.readProperties().getProperty("resource.absolute.path");
        String servicePath = this.readProperties().getProperty("resource.server.path");
        String resoursePath = ConstantPath.EXCEL_PATH + insId + "/"
                + JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6);
        new File(absolutePath + resoursePath).mkdirs();
        view.writeXLSX(absolutePath + resoursePath + fileName);
        return servicePath + resoursePath + fileName;
    }

    /**
     * 生成对比的excel统计表
     * 
     * @author xdc
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String excelCompare(String dataList) throws Exception {
        View view = new View();
        view.setSheetName(0, "工作表1");
        view.setNumSheets(1);
        int initRow = 2;
        Map<String, JSONObject> resultMap = NetJsonUtils.getMapFromJson(dataList);
        for (String key : resultMap.keySet()) {
            JSONObject jsonData = resultMap.get(key);
            AccountExcelData data = (AccountExcelData) NetJsonUtils.jsonToObject(jsonData.toString(),
                    AccountExcelData.class);
            List<String[]> strList = data.getRowList();
            List<String[]> rowList = new ArrayList<String[]>();
            for (Object array : strList) {
                List<String> list = (List<String>) array;
                if (CollectionUtils.isNotEmpty(list)) {
                    String[] strArray = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        strArray[i] = ((Object) list.get(i)).toString();
                    }
                    rowList.add(strArray);
                }
            }
            data.setRowList(rowList);
            data.setInitRow(initRow);
            view.setSheetName(data.getSheetNum(), data.getSheetName());
            view.setSheet(data.getSheetNum());
            if ("pie".equals(data.getChartType())) {
                initRow = this.createTableAndChartPIE(view, data);
            } else {
                initRow = this.createTableAndChart(view, data);
            }
        }

        // 资源文件位置 + resource目录下的位置 + 机构号 + 生成日期 + 文件名
        String fileName = "/compareExcel.xlsx";
        CodeServiceImpl codeService = (CodeServiceImpl) CacheProjectInfo.getInstance().getApplicationContext()
                .getBean("codeServiceImpl");
        String insId = codeService.getInsId();
        String absolutePath = this.readProperties().getProperty("resource.absolute.path");
        String servicePath = this.readProperties().getProperty("resource.server.path");
        String resoursePath = ConstantPath.EXCEL_PATH + insId + "/"
                + JodaTimeTools.getCurrentDate(JodaTimeTools.FORMAT_6);
        new File(absolutePath + resoursePath).mkdirs();
        view.writeXLSX(absolutePath + resoursePath + fileName);
        return servicePath + resoursePath + fileName;
    }

    // 返回最后的行号
    private int createPart1Excel(int initRow, int initCol, View view, List<String> data,
            WorkAccountCondition condition, AccountExcelData part2) throws Exception {
        int rowNum = initRow;
        view.setText(initRow, initCol, "时间：");
        view.setText(initRow, initCol + 1, condition.getStartDate());
        view.setText(initRow, initCol + 2, "至");
        CellFormat cf = view.getCellFormat(initRow, initCol + 2, initRow, initCol + 2);
        cf.setFontBold(true);
        cf.setHorizontalAlignment((short) 2);
        view.setCellFormat(cf, initRow, initCol + 2, initRow, initCol + 2);
        view.setText(initRow, initCol + 3, condition.getEndDate());

        view.setText(++initRow, initCol, "转诊医师：");
        if (StringUtils.isNotEmpty(condition.getDiagnosisZhuanUserName())) {
            int colNumUser = initCol;
            String[] userNameArr = condition.getDiagnosisZhuanUserName().split(",");
            for (String userName : userNameArr) {
                view.setText(initRow, ++colNumUser, userName);
            }
        }

        if (StringUtils.isNotEmpty(condition.getDiagnosisUserName())) {
            view.setText(++initRow, initCol, "孕期营养门诊医师：");
            int colNumUser = initCol;
            String[] userNameArr = condition.getDiagnosisUserName().split(",");
            for (String userName : userNameArr) {
                view.setText(initRow, ++colNumUser, userName);
            }
        }

        view.setText(++initRow, initCol, "诊断名称：");
        view.setText(initRow, initCol + 1, condition.getDiseaseName());

        view.setText(++initRow, initCol, "系统营养评价项目：");
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItemsName())) {
            int colNumUser = initCol;
            String[] userNameArr = condition.getDiagnosisMasItemsName().split(",");
            for (String userName : userNameArr) {
                view.setText(initRow, ++colNumUser, userName);
            }
        }

        view.setText(++initRow, initCol, "营养评价项目操作者：");
        if (StringUtils.isNotEmpty(condition.getDiagnosisMasItemAuthorsName())) {
            int colNumUser = initCol;
            String[] userNameArr = condition.getDiagnosisMasItemAuthorsName().split(",");
            for (String userName : userNameArr) {
                view.setText(initRow, ++colNumUser, userName);
            }
        }
        cf = view.getCellFormat(rowNum, initCol, initRow, initCol);
        cf.setFontBold(true);
        view.setCellFormat(cf, rowNum, initCol, initRow, initCol);

        view.setTextAsValue(++initRow, initCol, "统计汇总");
        view.setTextAsValue(initRow, initCol + 5, "初诊阶段分布");

        initRow++;
        int colCell = initCol;
        String[] part1Title = new String[] {"时间段", "就诊人数", "就诊人次", "初诊人数", "复诊率（%）", "孕早期", "占比（%）", "孕中期", "占比（%）",
                "孕晚期", "占比（%）"};
        for (String title : part1Title) {
            view.setTextAsValue(initRow, colCell++, title);
        }

        initRow++;
        List<List<String>> dataList = new ArrayList<List<String>>();
        for (String rowData : data) {
            String[] rowDateArr = rowData.split(",");
            dataList.add(Arrays.asList(rowDateArr));
        }
        initRow = createTableBase(initRow, initCol, view, dataList);

        view.setTextAsValue(initRow, initCol, "月平均就诊人次");
        // 获取最后一天数据的第3个单位格的数据
        int renCi = Integer.valueOf(dataList.get(dataList.size() - 1).get(2));
        view.setTextAsValue(initRow, initCol + 2, Math.round((float) renCi / (dataList.size() - 1)) + "");

        for (String[] cols : part2.getRowList()) {
            int colNum = initCol;
            initRow++;
            for (String str : cols) {
                view.setTextAsValue(initRow, colNum++, str);
            }
        }

        return initRow;
    }

    /********************************* 工具方法 *********************************/
    /**
     * 生成统计图表
     * 
     * @author xdc
     * @param view
     * @param excelData
     * @return
     * @throws Exception
     */
    public int createTableAndChart(View view, AccountExcelData excelData) throws Exception {
        view.setSheet(excelData.getSheetNum());
        String sheetName = excelData.getSheetName();
        int initCol = excelData.getInitCol();
        int colNum = excelData.getInitCol();
        int initRow = excelData.getInitRow();
        int rowNum = excelData.getInitRow();
        int rowspan = excelData.getRowspan();
        int colspan = excelData.getColspan();
        String title = excelData.getTitle();
        String dataType = excelData.getDataType();
        List<String[]> rowList = excelData.getRowList();
        // 创建表格
        if (CollectionUtils.isNotEmpty(rowList)) {
            for (String[] cols : rowList) {
                colNum = initCol;
                for (String str : cols) {
                    if (isNumeric(str)) {
                        view.setTextAsValue(rowNum, colNum++, str);
                    } else {
                        view.setText(rowNum, colNum++, str);
                    }
                }
                rowNum++;
            }
            int[] station = new int[] {initCol, rowNum + 1, colspan, rowspan};
            if ("row".equals(dataType)) {
                String startXColAscii = returnAscii(initCol);// x轴数据初始列
                String startYColAscii = returnAscii(initCol + 1);// y轴数据初始列
                String endYColAscii = returnAscii(initCol + excelData.getDataCols());// y轴数据结束列
                int startRow = initRow + 2;// 有效数据初始行下标值
                String[] dataArray = new String[excelData.getDataRows()];// 有效数据行数
                String[] legendArray = new String[excelData.getDataRows()];// legend标题数组
                String xData = sheetName + "!$" + startYColAscii + "$" + (initRow + 1) + ":$" + endYColAscii + "$"
                        + (initRow + 1);// x轴内容
                for (int i = 0; i < excelData.getDataRows(); i++) {
                    dataArray[i] = sheetName + "!$" + startYColAscii + "$" + (startRow + i) + ":$" + endYColAscii + "$"
                            + (startRow + i);
                    legendArray[i] = sheetName + "!$" + startXColAscii + "$" + (startRow + i)
                            + ":$" + startXColAscii + "$" + (startRow + i);
                }
                // 创建Excel图表
                creatEchartBase(station, view, "", "", xData, chartType.get(excelData.getChartType()), legendArray,
                        dataArray,
                        title);
            } else {
                int startXRow = initRow + 1;// x轴数据初始行
                int startYRow = initRow + 2;// y轴数据初始行
                int endYRow = startXRow + excelData.getDataRows();// y轴数据结束行
                String[] dataArray = new String[excelData.getDataCols()];
                String[] legendArray = new String[excelData.getDataCols()];
                String xData = sheetName + "!$" + returnAscii(initCol) + "$" + startYRow + ":$" + returnAscii(initCol)
                        + "$"
                        + endYRow;
                for (int i = 0; i < excelData.getDataCols(); i++) {
                    dataArray[i] = sheetName + "!$" + returnAscii(initCol + 1 + i) + "$" + startYRow + ":$"
                            + returnAscii(initCol + 1 + i) + "$" + endYRow;
                    legendArray[i] = sheetName + "!$" + returnAscii(initCol + 1 + i) + "$" + startXRow
                            + ":$" + returnAscii(initCol + 1 + i) + "$" + startXRow;
                }
                // 创建Excel图表
                creatEchartBase(station, view, "", "", xData, chartType.get(excelData.getChartType()), legendArray,
                        dataArray,
                        title);
            }
        }

        return rowNum + rowspan + 6;
    }

    /**
     * 生成统计图表
     * 
     * @author xdc
     * @param view
     * @param excelData
     * @return
     * @throws Exception
     */
    public int createTableAndChartPIE(View view, AccountExcelData excelData) throws Exception {
        view.setSheet(excelData.getSheetNum());
        int initCol = excelData.getInitCol();
        int colNum = excelData.getInitCol();
        int initRow = excelData.getInitRow();
        int rowNum = excelData.getInitRow();
        int rowspan = excelData.getRowspan();
        int colspan = excelData.getColspan();
        String title = excelData.getTitle();
        List<String[]> rowList = excelData.getRowList();
        // 创建表格
        if (CollectionUtils.isNotEmpty(rowList)) {
            for (String[] cols : rowList) {
                colNum = initCol;
                for (String str : cols) {
                    if (isNumeric(str)) {
                        view.setTextAsValue(rowNum, colNum++, str);
                    } else {
                        view.setText(rowNum, colNum++, str);
                    }
                }
                rowNum++;
            }
            int[] station = new int[] {initCol, rowNum + 1, colspan, rowspan};
            // 创建Excel图表(RangeRef参数【列下标，行下标，列下标，行下标】)
            creatEchartPIE(station, view, ChartShape.TypePie, title,
                    new RangeRef(initCol, initRow, excelData.getDataCols() + initCol, initRow + 1), true);
        }

        return rowNum + rowspan + 6;
    }

    /**
     * 
     * 生成一组表格
     * 
     * @author xdc
     * @param initX
     *            起始位置x
     * @param initY
     *            起始位置y
     * @param view
     *            视图
     * @param data
     * @throws CellException
     */
    public int createTableBase(int row, int col, View view, List<List<String>> data) throws CellException {
        int colCell = col;
        for (List<String> dataArr : data) {
            for (String str : dataArr) {
                // 标题 setTextAsValue(行，列，值)；
                if (isNumeric(str)) {
                    view.setTextAsValue(row, colCell++, str);
                } else {
                    view.setText(row, colCell++, str);
                }
            }
            row++;
            colCell = col;
        }
        return row;
    }

    /**
     * 生成图表的基础方法
     * 
     * @author xdc
     * @param row
     * @param col
     * @param height
     * @param width
     * @param view
     * @param xTitle
     * @param yTitle
     * @param xName
     * @param type
     * @param colNameList
     * @param dataList
     * @throws Exception
     */
    public static void creatEchartBase(int[] station, View view, String xTitle,
            String yTitle, String xName, short type,
            String[] colNameList, String[] dataList, String title) throws Exception {
        ChartShape chart = view.addChart(station[0], station[1], station[0] + station[2], station[1] + station[3]);
        chart.setChartType(type);
        for (int i = 0; i < colNameList.length; i++) {
            chart.addSeries();
            if (i == 0) {
                chart.setCategoryFormula(xName);
            }
            chart.setSeriesName(i, colNameList[i]);
            chart.setSeriesYValueFormula(i, dataList[i]);
        }

        // 设置横坐标标题
        chart.setAxisTitle(ChartShape.XAxis, 0, xTitle);
        ChartFormat cfXAxis = chart.getAxisFormat(ChartShape.XAxis, 0);
        cfXAxis.setFontSize(190);
        chart.setAxisFormat(ChartShape.XAxis, 0, cfXAxis);
        // 设置纵坐标标题
        chart.setAxisTitle(ChartShape.YAxis, 0, yTitle);
        // 一下是设置y轴的间隔的单位
        // chart.setScaleMajorUnitAuto(ChartShape.YAxis, 0, false);
        // chart.setScaleMajorUnit(ChartShape.YAxis, 0, 20);
        chart.setScaleMinorUnitAuto(ChartShape.YAxis, 0, false);
        chart.setScaleMinorUnit(ChartShape.YAxis, 0, 1);
        // 主格网
        ChartFormat cf = chart.getMajorGridFormat(ChartShape.YAxis, 0);
        cf.setLineStyle((short) 2);
        cf.setLineColor((new Color(255, 0, 0)).getRGB());
        cf.setLineAuto();
        chart.setMajorGridFormat(ChartShape.YAxis, 0, cf);

        // 图利位置
        chart.setLegendPosition(ChartFormat.LegendPlacementBottom);

        // 图利样式
        cf = chart.getLegendFormat();
        cf.setFontBold(true);
        cf.setFontSizeInPoints(8);
        chart.setTitle(title);
        chart.setLegendFormat(cf);
    }

    /**
     * 生成饼状图
     * 
     * @author xdc
     * @param station
     * @param view
     * @param type
     * @param rangeRef
     * @throws Exception
     */
    public void creatEchartPIE(int[] station, View view, short type, String title, RangeRef rangeRef,
            boolean rowOrCol)
            throws Exception {
        ChartShape chart = view.addChart(station[0], station[1], station[0] + station[2], station[1] + station[3]);
        chart.initData(rangeRef, rowOrCol);

        ChartFormat cf = chart.getMajorGridFormat(ChartShape.YAxis, 0);
        // 图利位置
        chart.setLegendPosition(ChartFormat.LegendPlacementBottom);

        // 图利样式
        cf = chart.getLegendFormat();
        cf.setFontBold(true);
        cf.setFontSizeInPoints(8);
        chart.setLegendFormat(cf);
        chart.setTitle(title);
        chart.setChartType(type);
    }

    /**
     * ascii码转字母
     * 
     * @author xdc
     * @param value
     * @return
     */
    private String returnAscii(int value) {
        value = value + 65;
        return (char) value + "";
    }

    /**
     * 读取配置文件
     * 
     * @author zcq
     */
    private Properties readProperties() {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("config-web.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }

    /**
     * 判断内容是否是数字
     * 
     * @author xdc
     * @param str
     * @return
     */
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]\\d*\\.?\\d*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
