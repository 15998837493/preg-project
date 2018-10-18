package com.mnt.preg.statistic.excel;

import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;
import com.mnt.preg.statistic.condition.SearchCountCondition;
import com.mnt.preg.statistic.pojo.StatisticDataPojo;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分娩结局Sheet
 *
 * @author lgp
 * @version 1.0
 * <p>
 * 变更履历：
 * v1.0 2018年8月20日 lgp 初版
 */
public class BirthEndingInfoSheet {

    /**
     * 绘制表头
     *
     * @param wb
     * @param sheet
     * @param excelPojoList
     * @param cellStyle
     * @param cellStyle1
     * @param order
     * @param condition
     */
    public static void birthEndingInfoTable(Workbook wb, SXSSFSheet sheet, List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, int order, SearchCountCondition condition) {
        //----------------------------第一行表头begin---------------------------------
        XSSFCellStyle cellStyleTitle = (XSSFCellStyle) wb.createCellStyle();
        // 设置样式对象，这里仅设置了边框属性
        cellStyleTitle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
        cellStyleTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
        cellStyleTitle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
        cellStyleTitle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设为右对齐
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
        // 新建font实体
        XSSFFont hssfFont = (XSSFFont) wb.createFont();
        // 设置删除线 strikeout（删除线）
        hssfFont.setStrikeout(false);
        // 设置是否斜体
        hssfFont.setItalic(false);
        // 字体大小
        hssfFont.setFontHeightInPoints((short) 11);
        // 粗体
        hssfFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        hssfFont.setFontName("宋体");
        cellStyleTitle.setFont(hssfFont);

        XSSFCellStyle cellStyleCondition = (XSSFCellStyle) wb.createCellStyle();
        // 设置样式对象，这里仅设置了边框属性
        cellStyleCondition.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
        cellStyleCondition.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
        cellStyleCondition.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
        cellStyleCondition.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
        cellStyleCondition.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 设为左对齐
        cellStyleCondition.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
        // 新建font实体
        XSSFFont conditionFont = (XSSFFont) wb.createFont();
        // 设置删除线 strikeout（删除线）
        conditionFont.setStrikeout(false);
        // 设置字体颜色
        conditionFont.setColor(HSSFColor.RED.index);
        // 设置是否斜体
        conditionFont.setItalic(false);
        // 字体大小
        conditionFont.setFontHeightInPoints((short) 11);
        conditionFont.setFontName("宋体");
        cellStyleCondition.setWrapText(true);
        cellStyleCondition.setFont(conditionFont);

        // 一、补充表头
        SXSSFRow row0 = (SXSSFRow) sheet.createRow(0);
        row0.setHeight((short) 1000);
        CellRangeAddress craCondition = new CellRangeAddress(0, 0, 4, 14);
        sheet.addMergedRegion(craCondition);

        SXSSFCell cell0 = (SXSSFCell) row0.createCell(4);
        cell0.setCellStyle(cellStyleCondition);
        if (condition.getQueryCondition() != null) {
            cell0.setCellValue("查询条件：" + condition.getQueryCondition().replaceAll("换行符", "\r\n\r\n").replaceAll("小于", "<")
                    .replaceAll("大于", ">"));
        }
        //----------------------------第一行表头end---------------------------------
        //----------------------------第二行表头begin---------------------------------
        SXSSFRow row = (SXSSFRow) sheet.createRow(1);
        //定义的游标
        int index = 0;
        //基础信息
        drawTitle(row, cellStyle, "序号", index++);
        drawTitle(row, cellStyle, "姓名", index++);
        drawTitle(row, cellStyle, "病案号", index++);
        drawTitle(row, cellStyle, "ID号", index++);
        drawTitle(row, cellStyle, "年龄", index++);
        drawTitle(row, cellStyle, "营养门诊", index++);
        drawTitle(row, cellStyle, "营养门诊医生", index++);
        drawTitle(row, cellStyle, "营养就诊次数", index++);
        drawTitle(row, cellStyle, "孕次", index++);
        drawTitle(row, cellStyle, "产次", index++);
        drawTitle(row, cellStyle, "受孕方式", index++);
        drawTitle(row, cellStyle, "备注", index++);
        drawTitle(row, cellStyle, "分娩日期", index++);
        drawTitle(row, cellStyle, "分娩孕周数", index++);
        drawTitle(row, cellStyle, "分娩方式", index++);
        drawTitle(row, cellStyle, "会阴情况", index++);
        drawTitle(row, cellStyle, "分娩备注", index++);

        //分娩结局
        //获取最大新生儿数量
        if (condition.getBabyNumMax() != null) {
            int babyNumMax = condition.getBabyNumMax();
            for (int i = 1; i <= babyNumMax; i++) {
                drawTitle(row, cellStyle, "新生儿性别" + i, index++);
                drawTitle(row, cellStyle, "新生儿体重/g", index++);
                drawTitle(row, cellStyle, "新生儿身长/cm", index++);
                drawTitle(row, cellStyle, "胎儿窒息min", index++);
                drawTitle(row, cellStyle, "阿氏评分1分钟", index++);
                drawTitle(row, cellStyle, "阿氏评分5分钟", index++);
                drawTitle(row, cellStyle, "胎盘重g", index++);
                drawTitle(row, cellStyle, "胎盘长cm", index++);
                drawTitle(row, cellStyle, "胎盘宽cm", index++);
                drawTitle(row, cellStyle, "胎盘厚cm", index++);
                drawTitle(row, cellStyle, "羊水量", index++);
                drawTitle(row, cellStyle, "羊水性状", index++);
                drawTitle(row, cellStyle, "新生儿备注(" + i + ")", index++);
                drawTitle(row, cellStyle1, "新生儿死亡(" + i + ")", index++);
                drawTitle(row, cellStyle1, "死亡时间", index++);
                drawTitle(row, cellStyle1, "死亡原因", index++);
            }
            drawTitle(row, cellStyle, "孕期总体重增长/kg", index++);
            drawTitle(row, cellStyle, "分娩前体重/kg", index++);
            drawTitle(row, cellStyle, "分娩后体重/kg", index++);
            drawTitle(row, cellStyle, "入院诊断", index++);
            drawTitle(row, cellStyle, "入院诊断备注", index++);
            drawTitle(row, cellStyle, "出院诊断/母", index++);
            for (int i = 1; i <= babyNumMax; i++) {
                drawTitle(row, cellStyle, "出院诊断/婴儿(" + i + ")", index++);
                drawTitle(row, cellStyle, "新生儿血糖/mg/dl(" + i + ")", index++);
            }
            drawTitle(row, cellStyle, "产后血红蛋白/g/L", index++);
            drawTitle(row, cellStyle, "出院诊断备注", index++);
        }
        //----------------------------第二行表头end---------------------------------
        //冻结表头
        sheet.createFreezePane(4, 2);
        //----------------------------动态生成表内容---------------------------------
        birthEndingInfoTableContent(sheet, excelPojoList, cellStyle, cellStyle1, condition);
    }

    /**
     * 动态分娩结局制剂表内容
     *
     * @param sheet
     * @param excelPojoList
     * @param cellStyle
     * @param cellStyle1
     */
    public static void birthEndingInfoTableContent(SXSSFSheet sheet, List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, SearchCountCondition condition) {
        //数据行
        int rowIndexBegin = 2;
        int lineNo = 0; //第一列的序号

        if (CollectionUtils.isEmpty(excelPojoList)) {
            return;
        }
        //临时统计数据
        List<StatisticDataPojo> newExcelPojoList = new ArrayList<>();
        //临时营养门诊医师
        String ditionist = "";
        for (int i = 0;i < excelPojoList.size() - 1;i++){
            //如果这条记录和下一条记录的birthId相同，拼接营养门诊医生
            if (excelPojoList.get(i).getBirthEndingPojo().getBirthId() == excelPojoList.get(i+1).getBirthEndingPojo().getBirthId()){
                //获取这条记录的医师名
                String ditionist1 = (excelPojoList.get(i).getDiagnosisPojo()!=null && !StringUtils.isEmpty(excelPojoList.get(i).getDiagnosisPojo().getDiagnosisUserName()))
                        ?excelPojoList.get(i).getDiagnosisPojo().getDiagnosisUserName().toString():"";
                //如果这条医师名不包括在临时变量中，把医师名加入进去
                if (!ditionist.contains(ditionist1)){
                    ditionist += ditionist1 + "，";
                }
                //获取下一条记录的医师名
                String ditionist2 = (excelPojoList.get(i+1).getDiagnosisPojo()!=null && !StringUtils.isEmpty(excelPojoList.get(i+1).getDiagnosisPojo().getDiagnosisUserName()))
                        ?excelPojoList.get(i+1).getDiagnosisPojo().getDiagnosisUserName().toString():"";
                //如果下一条医师名不包括在临时变量中，把医师名加入进去
                if (!ditionist.contains(ditionist2)){
                    ditionist += ditionist2 + "，";
                }
                //如果判断的是最后两条，并且最后两条的birthId相同，直接把数据存到临时数组中
                if (i == excelPojoList.size() - 2){
                    StatisticDataPojo excelPojoTemp = excelPojoList.get(i);
                    excelPojoTemp.getDiagnosisPojo().setDiagnosisUserName(ditionist.substring(0,ditionist.length()-1));
                    newExcelPojoList.add(excelPojoTemp);
                }
            } else {
                StatisticDataPojo excelPojoTemp = excelPojoList.get(i);
                if (excelPojoTemp.getDiagnosisPojo() != null){
                    if (ditionist.length() != 0){
                        excelPojoTemp.getDiagnosisPojo().setDiagnosisUserName(ditionist.substring(0,ditionist.length()-1));
                    } else {
                        excelPojoTemp.getDiagnosisPojo().setDiagnosisUserName(ditionist);
                    }
                }
                //向临时统计数据中添加数据
                newExcelPojoList.add(excelPojoTemp);
                //如果倒数第二、第一条birthId不相同，把倒数第一条记录也加到临时数组里
                if (i == excelPojoList.size()-2){
                    newExcelPojoList.add(excelPojoList.get(i+1));
                }
                //清空临时变量
                ditionist = "";
            }
        }
        //对最后一条数据进行判断
//        if (excelPojoList.get(excelPojoList.size()-1).getBirthEndingPojo().getBirthId() == excelPojoList.get(excelPojoList.size()-2).getBirthEndingPojo().getBirthId()){
//            //获取最后一条数据的医师名
//            String names = (excelPojoList.get(excelPojoList.size()-1).getDiagnosisPojo() != null && !StringUtils.isEmpty(excelPojoList.get(excelPojoList.size()-1).getDiagnosisPojo().getDiagnosisUserName()))
//                    ?excelPojoList.get(excelPojoList.size()-1).getDiagnosisPojo().getDiagnosisUserName().toString():"";
//            //判断医师名是否在临时数组中最后一条数据医师名里
//            String nameTemp = newExcelPojoList.get(newExcelPojoList.size()-1).getDiagnosisPojo().getDiagnosisUserName();
//            if (!nameTemp.contains(names)){
//                nameTemp += names + "，";
//                newExcelPojoList.get(newExcelPojoList.size()-1).getDiagnosisPojo().setDiagnosisUserName(nameTemp);
//            }
//        } else {
//            newExcelPojoList.add(excelPojoList.get(excelPojoList.size()-1));
//        }
        //遍历数据
        for (StatisticDataPojo excelPoje : newExcelPojoList) {
            //创建行
            SXSSFRow row = (SXSSFRow) sheet.createRow(rowIndexBegin);
            //序号
            SXSSFCell cell0 = (SXSSFCell) row.createCell(0);
            cell0.setCellStyle(cellStyle);
            cell0.setCellValue(String.valueOf(++lineNo));
            //基础信息
            pregBaseInfo(excelPoje, row, cellStyle, 1);
            //分娩结局
            pregChildBirth(excelPoje, row, cellStyle, 17, condition);
            rowIndexBegin++;
        }
    }

    /**
     * 基础信息
     *
     * @param excelPojo
     * @param row
     * @param cellStyle
     * @param startCol
     */
    public static void pregBaseInfo(StatisticDataPojo excelPojo, SXSSFRow row, XSSFCellStyle cellStyle, int startCol) {
        //列游标
        int col = startCol;
        // 姓名
        String value3 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustName()))
                ? excelPojo.getCustomerInfoPojo().getCustName().toString() : "";
        drawContent(row, cellStyle, value3, col++);
        //病案号
        String value1 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustSikeId()))
                ? excelPojo.getCustomerInfoPojo().getCustSikeId().toString() : "";
        drawContent(row, cellStyle, value1, col++);
        //ID
        String value2 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustPatientId()))
                ? excelPojo.getCustomerInfoPojo().getCustPatientId().toString() : "";
        drawContent(row, cellStyle, value2, col++);
        //年龄--分娩结局
        String value4 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getCustAge()))
                ? excelPojo.getBirthEndingPojo().getCustAge().toString() : "";
        drawContent(row, cellStyle, value4, col++);
        // 营养门诊
        String value5 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisName()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisName().toString() : "";
        drawContent(row, cellStyle, value5, col++);
        // 营养门诊医生
        String value6 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisUserName()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisUserName().toString() : "";
        drawContent(row, cellStyle, value6, col++);
        // 营养随诊次数
        String value7 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisCount()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisCount().toString() : "";
        drawContent(row, cellStyle, value7, col++);
        // 孕次--分娩结局
        String value11 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getPregnancyNum()))
                ? excelPojo.getBirthEndingPojo().getPregnancyNum().toString() : "";
        drawContent(row, cellStyle, value11, col++);
        // 产次--分娩结局
        String value12 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthNum()))
                ? excelPojo.getBirthEndingPojo().getBirthNum().toString() : "";
        drawContent(row, cellStyle, value12, col++);
        // 受孕方式--分娩结局
        String value13 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthTiresType()))
                ? excelPojo.getBirthEndingPojo().getBirthTiresType().toString() : "";
        if ("1".equals(value13)) {
            String value14 = !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getEncyesisSituation()) ?
                    excelPojo.getBirthEndingPojo().getEncyesisSituation().toString() :
                    "";
            value13 = "自然受孕";
            if ("1".equals(value14)) {
                value13 = value13 + " 意外妊娠";
            } else {
                value13 = value13 + " 计划妊娠";
            }
        } else {
            value13 = "辅助生殖";
        }
        drawContent(row, cellStyle, value13, col++);
        // 基础信息备注
        String cbValue = (excelPojo.getBirthEndingPojo() != null && excelPojo.getBirthEndingPojo().getBirthBaseRemark() != null)
                ? excelPojo.getBirthEndingPojo().getBirthBaseRemark() : "";
        drawContent(row, cellStyle, cbValue, col++);
        // 分娩日期
        String cbValue6 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBirthDate() != null)
                ? JodaTimeTools.toString(excelPojo.getBirthEndingPojo().getBirthDate(), JodaTimeTools.FORMAT_6) : "";
        if (excelPojo.getBirthEndingPojo().getBaseTimeHour() != null){
            cbValue6 = cbValue6 + " " + excelPojo.getBirthEndingPojo().getBaseTimeHour().toString() + "时";
        }
        if (excelPojo.getBirthEndingPojo().getBaseTimeMinuters() != null){
            cbValue6 = cbValue6 + " " + excelPojo.getBirthEndingPojo().getBaseTimeMinuters().toString() + "分";
        }
        drawContent(row, cellStyle, cbValue6, col++);
        // 分娩孕周数
        String cbValue7 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthWeeks()))
                ? excelPojo.getBirthEndingPojo().getBirthWeeks().toString() : "";
        drawContent(row, cellStyle, cbValue7, col++);
        // 分娩方式
        String cbValue10_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseBirthType() != null)
                ? String.valueOf(excelPojo.getBirthEndingPojo().getBaseBirthType()) : "";
        String cbValue10 = null;
        if (cbValue10_1.equals("1")) {
            cbValue10 = "自然分娩";
        } else if (cbValue10_1.equals("2")) {
            cbValue10 = "吸引";
        } else if (cbValue10_1.equals("3")) {
            cbValue10 = "产钳";
        } else if (cbValue10_1.equals("4")) {
            cbValue10 = "臀助产";
        } else if (cbValue10_1.equals("5")) {
            cbValue10 = "剖宫产";
        } else if (cbValue10_1.equals("6")) {
            cbValue10 = "其他";
        }
        drawContent(row, cellStyle, cbValue10, col++);
        // 会阴情况
        String cbValue24_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBasePerineumState() != null)
                ? excelPojo.getBirthEndingPojo().getBasePerineumState().toString() : "";
        String cbValue24 = null;
        if (cbValue24_1.equals("1")) {
            cbValue24 = "完整";
        } else if (cbValue24_1.equals("2")) {
            cbValue24 = "裂伤";
            if (excelPojo.getBirthEndingPojo().getBasePerineumHurt() != null
                    && excelPojo.getBirthEndingPojo().getBasePerineumHurt() == 1) {
                cbValue24 = "Ⅰ°" + cbValue24;
            }
            if (excelPojo.getBirthEndingPojo().getBasePerineumHurt() != null
                    && excelPojo.getBirthEndingPojo().getBasePerineumHurt() == 2) {
                cbValue24 = "Ⅱ°" + cbValue24;
            }
            if (excelPojo.getBirthEndingPojo().getBasePerineumHurt() != null
                    && excelPojo.getBirthEndingPojo().getBasePerineumHurt() == 3) {
                cbValue24 = "Ⅲ°" + cbValue24;
            }
        } else if (cbValue24_1.equals("3")) {
            cbValue24 = "切开";
        } else {
            cbValue24 = "";
        }
        drawContent(row, cellStyle, cbValue24, col++);
        // 分娩信息备注
        String cbValue8 = (excelPojo.getBirthEndingPojo() != null && excelPojo.getBirthEndingPojo().getBaseRemark() != null)
                ? excelPojo.getBirthEndingPojo().getBaseRemark() : "";
        drawContent(row, cellStyle, cbValue8, col++);
    }

    /**
     * 婴儿信息
     *
     * @param excelPojo
     * @param row
     * @param cellStyle
     * @param startCol
     */
    public static void pregChildBirth(StatisticDataPojo excelPojo, SXSSFRow row, XSSFCellStyle cellStyle, int startCol,
            SearchCountCondition condition) {
        //-------新生儿信息
        if (excelPojo.getBirthEndingPojo() != null
                && !CollectionUtils.isEmpty(excelPojo.getBirthEndingPojo().getBabyList())) {
            for (BirthEndingBabyInfoPojo babyinfo : excelPojo.getBirthEndingPojo().getBabyList()) {
                //新生儿性别
                String baby1 = "";
                if (babyinfo != null && !StringUtils.isEmpty(babyinfo.getBabyGender())) {
                    baby1 = ExportExcel.dictMap.get("NEWBIRTHSEX" + babyinfo.getBabyGender()).getCodeName();
                }
                drawContent(row, cellStyle, baby1, startCol++);
                // 体重/g
                String baby2 = (babyinfo != null && babyinfo.getBabyWeight() != null)
                        ? babyinfo.getBabyWeight().toString() : "";
                drawContent(row, cellStyle, baby2, startCol++);
                // 身长/cm
                String baby3 = (babyinfo != null && babyinfo.getBabyLength() != null)
                        ? babyinfo.getBabyLength().toString() : "";
                drawContent(row, cellStyle, baby3, startCol++);
                // 新生儿窒息min
                String baby4 = (babyinfo != null && babyinfo.getBabyStifle() != null)
                        ? babyinfo.getBabyStifle().toString() : "";
                drawContent(row, cellStyle, baby4, startCol++);
                // 阿氏评分1分钟
                String baby5 = (babyinfo != null && babyinfo.getBabyAshesOneMinute() != null)
                        ? babyinfo.getBabyAshesOneMinute().toString() : "";
                drawContent(row, cellStyle, baby5, startCol++);
                // 阿氏评分5分钟
                String baby6 = (babyinfo != null && babyinfo.getBabyAshesFiveMinutes() != null)
                        ? babyinfo.getBabyAshesFiveMinutes().toString() : "";
                drawContent(row, cellStyle, baby6, startCol++);
                // 胎盘重g
                String baby7 = (babyinfo != null && babyinfo.getBabyPlacentaWeight() != null)
                        ? babyinfo.getBabyPlacentaWeight().toString() : "";
                drawContent(row, cellStyle, baby7, startCol++);
                // 胎盘长cm
                String baby8 = (babyinfo != null && babyinfo.getBabyPlacentaLength() != null)
                        ? babyinfo.getBabyPlacentaLength().toString() : "";
                drawContent(row, cellStyle, baby8, startCol++);
                // 胎盘宽cm
                String baby9 = (babyinfo != null && babyinfo.getBabyPlacentaWidth() != null)
                        ? babyinfo.getBabyPlacentaWidth().toString() : "";
                drawContent(row, cellStyle, baby9, startCol++);
                // 胎盘厚cm
                String baby10 = (babyinfo != null && babyinfo.getBabyPlacentaThick() != null)
                        ? babyinfo.getBabyPlacentaThick().toString() : "";
                drawContent(row, cellStyle, baby10, startCol++);
                // 羊水量
                String baby11 = "";
                if (babyinfo != null && !StringUtils.isEmpty(babyinfo.getBabyAmnioticFluidVol())) {
                    baby11 = ExportExcel.dictMap.get("AFV" + babyinfo.getBabyAmnioticFluidVol()).getCodeName();
                }
                drawContent(row, cellStyle, baby11, startCol++);
                // 羊水性状
                String baby12 = "";
                if (babyinfo != null && !StringUtils.isEmpty(babyinfo.getBabyAmnioticFluidTraits())) {
                    baby12 = ExportExcel.dictMap.get("AFLUID" + babyinfo.getBabyAmnioticFluidTraits()).getCodeName();
                }
                drawContent(row, cellStyle, baby12, startCol++);
                //新生儿备注
                String baby24 = (babyinfo != null && babyinfo.getBabyRemark() != null)
                        ? babyinfo.getBabyRemark().toString()
                        : "";
                drawContent(row, cellStyle, baby24, startCol++);
                // 新生儿死亡
                String baby21_1 = (babyinfo != null && babyinfo.getBabyIsDeath() != null)
                        ? babyinfo.getBabyIsDeath().toString()
                        : "";
                String baby21 = null;
                if (baby21_1.contentEquals("1")) {
                    baby21 = "是";
                } else if (baby21_1.contentEquals("0")) {
                    baby21 = "否";
                }
                drawContent(row, cellStyle, baby21, startCol++);
                //死亡时间
                String baby22 = (babyinfo != null && babyinfo.getBabyDeathTime() != null)
                        ? JodaTimeTools.toString(babyinfo.getBabyDeathTime(), JodaTimeTools.FORMAT_11)
                        : "";
                if (baby22.length() >= 10) {
                    baby22 = baby22.substring(0, 10);
                }
                if (babyinfo != null && babyinfo.getBabyDeathTimeHour() != null){
                    baby22 = baby22 + " " + babyinfo.getBabyDeathTimeHour().toString() + "时";
                }
                if (babyinfo != null && babyinfo.getBabyDeathTimeMinutes() != null){
                    baby22 = baby22 + "" + babyinfo.getBabyDeathTimeMinutes().toString() + "分";
                }
                drawContent(row, cellStyle, baby22, startCol++);
                //死亡原因
                String baby23 = (babyinfo != null && babyinfo.getBabyAmnioticFluidDeathReason() != null)
                        ? babyinfo.getBabyAmnioticFluidDeathReason().toString()
                        : "";
                drawContent(row, cellStyle, baby23, startCol++);
            }
            //婴儿数量不是最大，画空格
            for (int i = 0; i < (condition.getBabyNumMax() - excelPojo.getBirthEndingPojo().getBabyList().size()) * 16; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
            // 孕期总体重增长
            String cbValue13 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseGrowthPregnancying()))
                    ? excelPojo.getBirthEndingPojo().getBaseGrowthPregnancying().toString() : "";
            drawContent(row, cellStyle, cbValue13, startCol++);
            // 分娩前体重
            String value5 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseWeightBeforeBirth()))
                    ? excelPojo.getBirthEndingPojo().getBaseWeightBeforeBirth().toString() : "";
            drawContent(row, cellStyle, value5, startCol++);
            // 分娩后体重
            String value6 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseWeightAfterBirth()))
                    ? excelPojo.getBirthEndingPojo().getBaseWeightAfterBirth().toString() : "";
            drawContent(row, cellStyle, value6, startCol++);
            // 入院诊断
            String value7 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthDiagnosis()))
                    ? excelPojo.getBirthEndingPojo().getBirthDiagnosis().toString() : "";
            drawContent(row, cellStyle, value7, startCol++);
            // 入院诊断备注
            String value12 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthDiagRemark()))
                    ? excelPojo.getBirthEndingPojo().getBirthDiagRemark().toString() : "";
            drawContent(row, cellStyle, value12, startCol++);
            // 出院诊断母
            String value8 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getDisMotherDisagnosis()))
                    ? excelPojo.getBirthEndingPojo().getDisMotherDisagnosis().toString() : "";
            drawContent(row, cellStyle, value8, startCol++);
            //新生儿诊断和新生儿血糖
            for (int i = 0; i < excelPojo.getBirthEndingPojo().getBabyList().size(); i++) {
                // 出院诊断婴儿
                String value9 = (excelPojo.getBirthEndingPojo() != null
                        && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBabyList().get(i).getDisBabyDiagnosis()))
                        ? excelPojo.getBirthEndingPojo().getBabyList().get(i).getDisBabyDiagnosis().toString() : "";
                drawContent(row, cellStyle, value9, startCol++);
                // 新生儿血糖/mg/dl
                String value10 = (excelPojo.getBirthEndingPojo() != null
                        && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBabyList().get(i).getDisBabyBloodSuger()))
                        ? excelPojo.getBirthEndingPojo().getBabyList().get(i).getDisBabyBloodSuger().toString() : "";
                drawContent(row, cellStyle, value10, startCol++);
            }
            //婴儿数量不是最大，画空格
            for (int i = 0; i < (condition.getBabyNumMax() - excelPojo.getBirthEndingPojo().getBabyList().size()) * 2; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
            // 产后血红蛋白/g/L
            String value11 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getDisHemoglobin()))
                    ? excelPojo.getBirthEndingPojo().getDisHemoglobin().toString() : "";
            drawContent(row, cellStyle, value11, startCol++);
            // 出院诊断备注
            String value13 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getDisRemark()))
                    ? excelPojo.getBirthEndingPojo().getDisRemark().toString() : "";
            drawContent(row, cellStyle, value13, startCol++);
        } else {
            //没有婴儿信息，画空格
            for (int i = 0; i < condition.getBabyNumMax() * 18 + 8; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
        }
    }

    /**
     * 填写单元格内容
     *
     * @param row
     * @param cellStyle
     * @param value
     * @param colNum
     */
    public static void drawContent(SXSSFRow row, XSSFCellStyle cellStyle, String value, int colNum) {
        SXSSFCell cell = (SXSSFCell) row.createCell(colNum);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 绘制表头标题（单个单元格）
     *
     * @param cellStyle
     * @param titleName
     * @param colNum
     */
    public static void drawTitle(SXSSFRow row, XSSFCellStyle cellStyle, String titleName, int colNum) {
        SXSSFCell cell = (SXSSFCell) row.createCell(colNum);
        cell.setCellValue(titleName);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 绘制合并单元格的表头（合并单元格）
     *
     * @param wb
     * @param sheet
     * @param cellStyle
     * @param titleName
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     */
    public static void drawTitleTogeter(Workbook wb, SXSSFSheet sheet, SXSSFRow row, XSSFCellStyle cellStyle, String titleName,
            int startRow, int endRow, int startCol, int endCol) {
        //合并单元格
        // 参数说明：1：开始行 2：结束行 3：开始列 4：结束列
        CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, endRow, startCol, endCol);
        sheet.addMergedRegion(cellRangeAddress);
        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, wb); // 下边框
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, wb); // 左边框
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, wb); // 有边框
        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, wb); // 上边框
        //创建单元格
        SXSSFCell cell = (SXSSFCell) row.createCell(startCol);
        cell.setCellValue(titleName);
        cell.setCellStyle(cellStyle);
    }
}
