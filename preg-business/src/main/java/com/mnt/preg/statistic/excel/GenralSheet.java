/*
 * GenralExcel.java    1.0  2018年8月17日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.excel;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;
import com.mnt.preg.statistic.condition.SearchCountCondition;
import com.mnt.preg.statistic.pojo.StatisticDataPojo;

/**
 * 总表Sheet
 * 
 * @author zj
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月17日 zj 初版
 */
public class GenralSheet {

    /**
     * 
     * 导出总表Sheet方法
     * 
     * @author zj
     * @param wb
     * @param sheet
     * @param excelPojoList
     * @param cellStyle
     * @param cellStyle1
     * @param order
     */
    public static SXSSFWorkbook generalTable(Workbook wb, XSSFSheet sheet, List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, int order, SearchCountCondition condition) {
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
        XSSFRow row0 = (XSSFRow) sheet.createRow(0);
        row0.setHeight((short) 1000);
        CellRangeAddress craCondition = new CellRangeAddress(0, 0, 4, 14);
        sheet.addMergedRegion(craCondition);

        XSSFCell cell0 = row0.createCell(4);
        cell0.setCellStyle(cellStyleCondition);
        if (condition.getQueryCondition() != null) {
            cell0.setCellValue("查询条件："
                    + condition.getQueryCondition().replaceAll("换行符", "\r\n\r\n").replaceAll("小于", "<").replaceAll("大于", ">"));
        }
        int examItemCount = 0; // 孕期检查项目数量
        int startXPos = 0; // 开始补充的X坐标(孕期检验项目开始的X坐标)
        int stage = condition.getStage();
        if (stage == 1) {
            startXPos = 49;
        } else {
            startXPos = 49;
        }
        // 读取行
        XSSFRow row1 = (XSSFRow) sheet.getRow(1);

        int tempStep = 0;

        if (CollectionUtils.isEmpty(excelPojoList)) {
            return null;
        }
        // 孕期检查记录开始
        // 根据检查项目数量动态输出孕检部分表头
        String items[] = condition.getItemsName().split(",");
        if (StringUtils.isEmpty(condition.getItemsName())) {
            examItemCount = 2;
        } else {
            examItemCount = items.length * 2 + 2;
        }
        if (examItemCount != 0) {
            // 这个就是合并单元格
            // 参数说明：1：开始行 2：结束行 3：开始列 4：结束列
            // 比如我要合并 第二行到第四行的 第六列到第八列 sheet.addMergedRegion(new CellRangeAddress(1,3,5,7));
            CellRangeAddress cra = new CellRangeAddress(1, 2, startXPos, startXPos + examItemCount - 1);
            sheet.addMergedRegion(cra);
            // 使用RegionUtil类为合并后的单元格添加边框
            RegionUtil.setBorderBottom(1, cra, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra, sheet, wb); // 上边框

            // 创建单元格
            XSSFCell cell = row1.createCell(startXPos);
            cell.setCellStyle(cellStyle1);
            cell.setCellValue("孕期检验记录");

            XSSFRow row2 = (XSSFRow) sheet.getRow(3);
            XSSFCell cell20 = row2.createCell(startXPos);
            cell20.setCellValue("本次诊断");
            cell20.setCellStyle(cellStyle1);
            XSSFCell cell21 = row2.createCell(startXPos + 1);
            cell21.setCellValue("风险级别");
            cell21.setCellStyle(cellStyleTitle);

            int tempi = 1;
            // 根据检查项目数量动态输出孕检部分表头
            for (String item : items) {
                if (StringUtils.isEmpty(item)) {
                    continue;
                }
                tempi++;
                XSSFCell cell22 = row2.createCell(startXPos + tempi);
                if (item.indexOf("(") < 0) {
                    cell22.setCellValue(item + "检查时间");
                } else {
                    cell22.setCellValue(item.substring(0, item.indexOf("(")) + "检查时间");
                }
                cell22.setCellStyle(cellStyle1);
                tempi++;
                XSSFCell cell23 = row2.createCell(startXPos + tempi);
                cell23.setCellValue(item);
                cell23.setCellStyle(cellStyle1);
            }

        }
        // 孕期检查记录结束

        // 孕期医嘱表头开始
        int doctorAdviceXpos = startXPos + examItemCount; // 孕期医嘱开始的纵坐标
        CellRangeAddress cra1 = new CellRangeAddress(1, 2, doctorAdviceXpos, doctorAdviceXpos + 2);
        sheet.addMergedRegion(cra1);
        RegionUtil.setBorderBottom(1, cra1, sheet, wb); // 下边框
        RegionUtil.setBorderLeft(1, cra1, sheet, wb); // 左边框
        RegionUtil.setBorderRight(1, cra1, sheet, wb); // 有边框
        RegionUtil.setBorderTop(1, cra1, sheet, wb); // 上边框
        // 创建单元格
        XSSFCell cellm = row1.createCell(doctorAdviceXpos);
        cellm.setCellStyle(cellStyleTitle);
        cellm.setCellValue("孕期医嘱");
        XSSFRow row2 = (XSSFRow) sheet.getRow(3);

        XSSFCell cell20 = row2.createCell(doctorAdviceXpos + tempStep++);
        cell20.setCellValue("继服营养制剂");
        cell20.setCellStyle(cellStyleTitle);
        XSSFCell cell21 = row2.createCell(doctorAdviceXpos + tempStep++);
        cell21.setCellValue("新增营养制剂");
        cell21.setCellStyle(cellStyleTitle);
        XSSFCell cell22 = row2.createCell(doctorAdviceXpos + tempStep++);
        cell22.setCellValue("停服营养制剂");
        cell22.setCellStyle(cellStyleTitle);
        tempStep = 0;
        // 孕期医嘱表头结束

        // 分娩结果表头开始
        int newChildXpos = 0; // 新生儿开始坐标
        int newChildXpos1 = 0; // 新生儿开始坐标
        int inOutHospiXpos = 0; // 出入院情况坐标
        int newChildCount = 0; // 最大新生儿数
        int childbirthXpos = doctorAdviceXpos + 3; // 分娩结果开始的纵坐标
        if (stage == 2) {
            childbirthXpos = doctorAdviceXpos + 3; // 分娩结果开始的纵坐标

            XSSFRow row23 = (XSSFRow) sheet.getRow(2);

            CellRangeAddress cra31 = new CellRangeAddress(2, 2, childbirthXpos, childbirthXpos + 14);
            sheet.addMergedRegion(cra31);
            RegionUtil.setBorderBottom(1, cra31, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra31, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra31, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra31, sheet, wb); // 上边框

            XSSFCell cell30 = row23.createCell(childbirthXpos);
            cell30.setCellValue("基础信息");
            cell30.setCellStyle(cellStyle1);

            CellRangeAddress cra32 = new CellRangeAddress(2, 2, childbirthXpos + 15, childbirthXpos + 15 + 3);
            sheet.addMergedRegion(cra32);
            RegionUtil.setBorderBottom(1, cra32, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra32, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra32, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra32, sheet, wb); // 上边框

            XSSFCell cell31 = row23.createCell(childbirthXpos + 15);
            cell31.setCellValue("产程");
            cell31.setCellStyle(cellStyleTitle);

            CellRangeAddress cra33 = new CellRangeAddress(2, 2, childbirthXpos + 15 + 4, childbirthXpos + 15 + 4 + 2);
            sheet.addMergedRegion(cra33);
            RegionUtil.setBorderBottom(1, cra33, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra33, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra33, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra33, sheet, wb); // 上边框

            XSSFCell cell32 = row23.createCell(childbirthXpos + 15 + 4);
            cell32.setCellValue("出血量");
            cell32.setCellStyle(cellStyleTitle);

            CellRangeAddress cra34 = new CellRangeAddress(2, 2, childbirthXpos + 15 + 4 + 3,
                    childbirthXpos + 15 + 4 + 3 + 2);
            sheet.addMergedRegion(cra34);
            RegionUtil.setBorderBottom(1, cra34, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra34, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra34, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra34, sheet, wb); // 上边框

            XSSFCell cell33 = row23.createCell(childbirthXpos + 15 + 4 + 3);
            cell33.setCellValue("会阴胎盘");
            cell33.setCellStyle(cellStyleTitle);

            CellRangeAddress cra35 = new CellRangeAddress(2, 2, childbirthXpos + 15 + 4 + 3 + 3,
                    childbirthXpos + 15 + 4 + 3 + 3 + 8);
            sheet.addMergedRegion(cra35);
            RegionUtil.setBorderBottom(1, cra35, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra35, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra35, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra35, sheet, wb); // 上边框

            XSSFCell cell34 = row23.createCell(childbirthXpos + 15 + 4 + 3 + 3);
            cell34.setCellValue("手术及并发症");
            cell34.setCellStyle(cellStyleTitle);

            CellRangeAddress cra36 = new CellRangeAddress(2, 2, childbirthXpos + 15 + 4 + 3 + 3 + 9,
                    childbirthXpos + 15 + 4 + 3 + 3 + 9 + 3);
            sheet.addMergedRegion(cra36);
            RegionUtil.setBorderBottom(1, cra36, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra36, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra36, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra36, sheet, wb); // 上边框

            XSSFCell cell35 = row23.createCell(childbirthXpos + 15 + 4 + 3 + 3 + 9);
            cell35.setCellValue("产后情况");
            cell35.setCellStyle(cellStyleTitle);

            CellRangeAddress cra37 = new CellRangeAddress(2, 2, childbirthXpos + 15 + 4 + 3 + 3 + 9 + 4,
                    childbirthXpos + 15 + 4 + 3 + 3 + 9 + 4 + 6);
            sheet.addMergedRegion(cra37);
            RegionUtil.setBorderBottom(1, cra37, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra37, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra37, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra37, sheet, wb); // 上边框

            XSSFCell cell36 = row23.createCell(childbirthXpos + 15 + 4 + 3 + 3 + 9 + 4);
            cell36.setCellValue("分娩结局");
            cell36.setCellStyle(cellStyleTitle);

            newChildXpos = childbirthXpos + 15 + 4 + 3 + 3 + 9 + 4 + 7; // 新生儿开始坐标
            newChildXpos1 = childbirthXpos + 15 + 4 + 3 + 3 + 9 + 4 + 7; // 新生儿开始坐标
            inOutHospiXpos = 0; // 出入院情况坐标
            // 最大新生儿数
            newChildCount = (excelPojoList.get(0).getBirthEndingPojo() != null
                    && condition.getBabyNumMax() != null)
                    ? condition.getBabyNumMax()
                    : 0;
            for (int i = 0; i < newChildCount; i++) {
                CellRangeAddress cra38 = new CellRangeAddress(2, 2, newChildXpos,
                        newChildXpos + 20);
                sheet.addMergedRegion(cra38);
                RegionUtil.setBorderBottom(1, cra38, sheet, wb); // 下边框
                RegionUtil.setBorderLeft(1, cra38, sheet, wb); // 左边框
                RegionUtil.setBorderRight(1, cra38, sheet, wb); // 有边框
                RegionUtil.setBorderTop(1, cra38, sheet, wb); // 上边框

                XSSFCell cell37 = row23.createCell(newChildXpos);
                cell37.setCellValue("新生儿" + (i + 1));
                cell37.setCellStyle(cellStyleTitle);

                CellRangeAddress cra39 = new CellRangeAddress(2, 2, newChildXpos + 21,
                        newChildXpos + 21 + 2);
                sheet.addMergedRegion(cra39);
                RegionUtil.setBorderBottom(1, cra39, sheet, wb); // 下边框
                RegionUtil.setBorderLeft(1, cra39, sheet, wb); // 左边框
                RegionUtil.setBorderRight(1, cra39, sheet, wb); // 有边框
                RegionUtil.setBorderTop(1, cra39, sheet, wb); // 上边框

                XSSFCell cell38 = row23.createCell(newChildXpos + 21);
                cell38.setCellValue("新生儿死亡" + (i + 1));
                cell38.setCellStyle(cellStyleTitle);

                newChildXpos = newChildXpos + 24;

            }
            inOutHospiXpos = newChildXpos;

            CellRangeAddress cra39_1 = new CellRangeAddress(2, 2, inOutHospiXpos,
                    inOutHospiXpos + 4 + newChildCount * 2);
            sheet.addMergedRegion(cra39_1);
            RegionUtil.setBorderBottom(1, cra39_1, sheet, wb); // 下边框
            RegionUtil.setBorderLeft(1, cra39_1, sheet, wb); // 左边框
            RegionUtil.setBorderRight(1, cra39_1, sheet, wb); // 有边框
            RegionUtil.setBorderTop(1, cra39_1, sheet, wb); // 上边框

            XSSFCell cell39 = row23.createCell(inOutHospiXpos);
            cell39.setCellValue("出入院情况");
            cell39.setCellStyle(cellStyleTitle);

        }

        // 第三行表头（分娩结局）
        if (stage == 2) {
            thirdLineHead(wb, sheet, excelPojoList, cellStyle, cellStyle1, doctorAdviceXpos, childbirthXpos, newChildXpos1,
                    newChildCount, newChildXpos1, inOutHospiXpos, row1, condition, cellStyleTitle);
        }

        return generalTableContent(wb, sheet, excelPojoList, cellStyle, cellStyle1, startXPos, doctorAdviceXpos,
                childbirthXpos, newChildXpos1, inOutHospiXpos, condition);

    }

    /**
     * 
     * 第三行表头(分娩结局部分)
     * 
     * @author zj
     * @param wb
     * @param sheet
     * @param excelPojoList
     * @param cellStyle
     * @param cellStyle1
     * @param pregExamPosi
     * @param doctorAdviceXpos
     * @param childbirthXpos
     * @param newChildXpos1
     * @param inOutHosXpos
     */
    public static void thirdLineHead(Workbook wb, Sheet sheet, List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, int doctorAdviceXpos,
            int childbirthXpos, int newChildXpos1, int newChildCount, int newChildXpos, int inOutHospiXpos,
            XSSFRow row1, SearchCountCondition condition, XSSFCellStyle cellStyleTitle) {
        XSSFRow row33 = (XSSFRow) sheet.getRow(3);
        int tempStep = 0;
        // 分娩信息 - 基础信息
        XSSFCell cell40 = row33.createCell(childbirthXpos + tempStep++);
        cell40.setCellValue("住院日期");
        cell40.setCellStyle(cellStyleTitle);

        XSSFCell cell41 = row33.createCell(childbirthXpos + tempStep++);
        cell41.setCellValue("住院号");
        cell41.setCellStyle(cellStyleTitle);

        XSSFCell cell42 = row33.createCell(childbirthXpos + tempStep++);
        cell42.setCellValue("产检医院");
        cell42.setCellStyle(cellStyleTitle);

        XSSFCell cell43 = row33.createCell(childbirthXpos + tempStep++);
        cell43.setCellValue("分娩医院");
        cell43.setCellStyle(cellStyleTitle);

        XSSFCell cell44 = row33.createCell(childbirthXpos + tempStep++);
        cell44.setCellValue("入院诊断");
        cell44.setCellStyle(cellStyleTitle);

        XSSFCell cell45 = row33.createCell(childbirthXpos + tempStep++);
        cell45.setCellValue("分娩日期");
        cell45.setCellStyle(cellStyle1);

        XSSFCell cell46 = row33.createCell(childbirthXpos + tempStep++);
        cell46.setCellValue("分娩孕周数");
        cell46.setCellStyle(cellStyle1);

        XSSFCell cell47 = row33.createCell(childbirthXpos + tempStep++);
        cell47.setCellValue("危重孕产妇");
        cell47.setCellStyle(cellStyleTitle);

        XSSFCell cell48 = row33.createCell(childbirthXpos + tempStep++);
        cell48.setCellValue("麻醉方式");
        cell48.setCellStyle(cellStyleTitle);

        XSSFCell cell49 = row33.createCell(childbirthXpos + tempStep++);
        cell49.setCellValue("分娩方式");
        cell49.setCellStyle(cellStyle1);

        XSSFCell cell49_1 = row33.createCell(childbirthXpos + tempStep++);
        cell49_1.setCellValue("剖宫产指征");
        cell49_1.setCellStyle(cellStyleTitle);

        XSSFCell cell49_2 = row33.createCell(childbirthXpos + tempStep++);
        cell49_2.setCellValue("分娩方位");
        cell49_2.setCellStyle(cellStyleTitle);

        XSSFCell cell49_3 = row33.createCell(childbirthXpos + tempStep++);
        cell49_3.setCellValue("孕期总体重增长");
        cell49_3.setCellStyle(cellStyleTitle);

        XSSFCell cell49_4 = row33.createCell(childbirthXpos + tempStep++);
        cell49_4.setCellValue("分娩前体重");
        cell49_4.setCellStyle(cellStyleTitle);

        XSSFCell cell49_5 = row33.createCell(childbirthXpos + tempStep++);
        cell49_5.setCellValue("分娩后体重");
        cell49_5.setCellStyle(cellStyleTitle);

        // 产程
        XSSFCell cell42_1 = row33.createCell(childbirthXpos + tempStep++);
        cell42_1.setCellValue("第一产程");
        cell42_1.setCellStyle(cellStyleTitle);

        XSSFCell cell42_2 = row33.createCell(childbirthXpos + tempStep++);
        cell42_2.setCellValue("第二产程");
        cell42_2.setCellStyle(cellStyleTitle);

        XSSFCell cell42_3 = row33.createCell(childbirthXpos + tempStep++);
        cell42_3.setCellValue("第三产程");
        cell42_3.setCellStyle(cellStyleTitle);

        XSSFCell cell42_4 = row33.createCell(childbirthXpos + tempStep++);
        cell42_4.setCellValue("总产程");
        cell42_4.setCellStyle(cellStyleTitle);

        // 出血量
        XSSFCell cell43_1 = row33.createCell(childbirthXpos + tempStep++);
        cell43_1.setCellValue("产时出血量");
        cell43_1.setCellStyle(cellStyleTitle);

        XSSFCell cell43_2 = row33.createCell(childbirthXpos + tempStep++);
        cell43_2.setCellValue("产后两小时出血量");
        cell43_2.setCellStyle(cellStyleTitle);

        XSSFCell cell43_3 = row33.createCell(childbirthXpos + tempStep++);
        cell43_3.setCellValue("总出血量");
        cell43_3.setCellStyle(cellStyleTitle);

        // 会阴胎盘
        XSSFCell cell44_1 = row33.createCell(childbirthXpos + tempStep++);
        cell44_1.setCellValue("阴检次数");
        cell44_1.setCellStyle(cellStyleTitle);

        XSSFCell cell44_2 = row33.createCell(childbirthXpos + tempStep++);
        cell44_2.setCellValue("会阴情况");
        cell44_2.setCellStyle(cellStyle1);

        XSSFCell cell44_3 = row33.createCell(childbirthXpos + tempStep++);
        cell44_3.setCellValue("胎盘情况");
        cell44_3.setCellStyle(cellStyleTitle);

        // 手术并发症
        XSSFCell cell45_1 = row33.createCell(childbirthXpos + tempStep++);
        cell45_1.setCellValue("手术");
        cell45_1.setCellStyle(cellStyleTitle);

        XSSFCell cell45_2 = row33.createCell(childbirthXpos + tempStep++);
        cell45_2.setCellValue("产前检查");
        cell45_2.setCellStyle(cellStyleTitle);

        XSSFCell cell45_3 = row33.createCell(childbirthXpos + tempStep++);
        cell45_3.setCellValue("中度贫血HB小于90g/L");
        cell45_3.setCellStyle(cellStyleTitle);

        XSSFCell cell45_4 = row33.createCell(childbirthXpos + tempStep++);
        cell45_4.setCellValue("妊娠高血压疾病");
        cell45_4.setCellStyle(cellStyleTitle);

        XSSFCell cell45_5 = row33.createCell(childbirthXpos + tempStep++);
        cell45_5.setCellValue("产前合并症");
        cell45_5.setCellStyle(cellStyleTitle);

        XSSFCell cell45_6 = row33.createCell(childbirthXpos + tempStep++);
        cell45_6.setCellValue("产时并发症");
        cell45_6.setCellStyle(cellStyleTitle);

        XSSFCell cell45_7 = row33.createCell(childbirthXpos + tempStep++);
        cell45_7.setCellValue("产后并发症");
        cell45_7.setCellStyle(cellStyleTitle);

        XSSFCell cell45_8 = row33.createCell(childbirthXpos + tempStep++);
        cell45_8.setCellValue("内科合并症");
        cell45_8.setCellStyle(cellStyleTitle);

        XSSFCell cell45_9 = row33.createCell(childbirthXpos + tempStep++);
        cell45_9.setCellValue("传染病检测情况");
        cell45_9.setCellStyle(cellStyleTitle);

        // 产后情况
        XSSFCell cell46_1 = row33.createCell(childbirthXpos + tempStep++);
        cell46_1.setCellValue("产后收缩压");
        cell46_1.setCellStyle(cellStyleTitle);

        XSSFCell cell46_2 = row33.createCell(childbirthXpos + tempStep++);
        cell46_2.setCellValue("产后舒张压");
        cell46_2.setCellStyle(cellStyleTitle);

        XSSFCell cell46_3 = row33.createCell(childbirthXpos + tempStep++);
        cell46_3.setCellValue("开奶时间");
        cell46_3.setCellStyle(cellStyleTitle);

        XSSFCell cell46_4 = row33.createCell(childbirthXpos + tempStep++);
        cell46_4.setCellValue("产后死亡");
        cell46_4.setCellStyle(cellStyleTitle);

        XSSFCell cell47_1 = row33.createCell(childbirthXpos + tempStep++);
        cell47_1.setCellValue("活产数");
        cell47_1.setCellStyle(cellStyleTitle);

        XSSFCell cell47_2 = row33.createCell(childbirthXpos + tempStep++);
        cell47_2.setCellValue("死胎数");
        cell47_2.setCellStyle(cellStyleTitle);

        XSSFCell cell47_3 = row33.createCell(childbirthXpos + tempStep++);
        cell47_3.setCellValue("死产数");
        cell47_3.setCellStyle(cellStyleTitle);

        XSSFCell cell47_4 = row33.createCell(childbirthXpos + tempStep++);
        cell47_4.setCellValue("围产儿数");
        cell47_4.setCellStyle(cellStyleTitle);

        XSSFCell cell47_5 = row33.createCell(childbirthXpos + tempStep++);
        cell47_5.setCellValue("孕28周前双/多胎宫内死亡胎数");
        cell47_5.setCellStyle(cellStyleTitle);

        XSSFCell cell47_6 = row33.createCell(childbirthXpos + tempStep++);
        cell47_6.setCellValue("孕28周前双/多胎宫内死亡原因");
        cell47_6.setCellStyle(cellStyleTitle);

        XSSFCell cell47_7 = row33.createCell(childbirthXpos + tempStep++);
        cell47_7.setCellValue("分娩备注");
        cell47_7.setCellStyle(cellStyleTitle);
        tempStep = 0;
        // 新生儿
        int newbabyTempstep = 0;
        for (int i = 0; i < newChildCount; i++) {

            XSSFCell cell48_1 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_1.setCellValue("性别");
            cell48_1.setCellStyle(cellStyle1);

            XSSFCell cell48_2 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_2.setCellValue("出生日期");
            cell48_2.setCellStyle(cellStyleTitle);

            XSSFCell cell48_3 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_3.setCellValue("体重/g");
            cell48_3.setCellStyle(cellStyle1);

            XSSFCell cell48_4 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_4.setCellValue("身长/cm");
            cell48_4.setCellStyle(cellStyle1);

            XSSFCell cell48_5 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_5.setCellValue("头围/cm");
            cell48_5.setCellStyle(cellStyleTitle);

            XSSFCell cell48_6 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_6.setCellValue("胸围/cm");
            cell48_6.setCellStyle(cellStyleTitle);

            XSSFCell cell48_7 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_7.setCellValue("阿氏评分1分钟");
            cell48_7.setCellStyle(cellStyle1);

            XSSFCell cell48_8 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_8.setCellValue("阿氏评分5分钟");
            cell48_8.setCellStyle(cellStyle1);

            XSSFCell cell48_9 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_9.setCellValue("阿氏评分10分钟");
            cell48_9.setCellStyle(cellStyleTitle);

            XSSFCell cell48_10 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_10.setCellValue("新生儿窒息min");
            cell48_10.setCellStyle(cellStyle1);

            XSSFCell cell48_9_1 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_9_1.setCellValue("出生缺陷");
            cell48_9_1.setCellStyle(cellStyleTitle);

            XSSFCell cell48_10_1 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_10_1.setCellValue("抢救");
            cell48_10_1.setCellStyle(cellStyleTitle);

            XSSFCell cell48_11 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_11.setCellValue("并发症");
            cell48_11.setCellStyle(cellStyleTitle);

            XSSFCell cell48_12 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_12.setCellValue("新生儿指导");
            cell48_12.setCellStyle(cellStyleTitle);

            XSSFCell cell48_13 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_13.setCellValue("胎盘重g");
            cell48_13.setCellStyle(cellStyle1);

            XSSFCell cell48_14 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_14.setCellValue("胎盘长cm");
            cell48_14.setCellStyle(cellStyle1);

            XSSFCell cell48_15 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_15.setCellValue("胎盘宽cm");
            cell48_15.setCellStyle(cellStyle1);

            XSSFCell cell48_16 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_16.setCellValue("胎盘厚cm");
            cell48_16.setCellStyle(cellStyle1);

            XSSFCell cell48_17 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_17.setCellValue("羊水量");
            cell48_17.setCellStyle(cellStyle1);

            XSSFCell cell48_18 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_18.setCellValue("羊水性状");
            cell48_18.setCellStyle(cellStyle1);

            XSSFCell cell48_19 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell48_19.setCellValue("新生儿备注");
            cell48_19.setCellStyle(cellStyle1);

            // 新生儿死亡
            XSSFCell cell49_1_1 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell49_1_1.setCellValue("新生儿死亡");
            cell49_1_1.setCellStyle(cellStyleTitle);

            XSSFCell cell49_1_2 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell49_1_2.setCellValue("死亡时间");
            cell49_1_2.setCellStyle(cellStyleTitle);

            XSSFCell cell49_1_3 = row33.createCell(newChildXpos + newbabyTempstep++);
            cell49_1_3.setCellValue("死亡原因");
            cell49_1_3.setCellStyle(cellStyleTitle);
        }

        // 出入院情况
        XSSFCell cell49_2_1 = row33.createCell(inOutHospiXpos + tempStep++);
        cell49_2_1.setCellValue("入院诊断");
        cell49_2_1.setCellStyle(cellStyle1);

        XSSFCell cell49_2_2 = row33.createCell(inOutHospiXpos + tempStep++);
        cell49_2_2.setCellValue("入院诊断备注");
        cell49_2_2.setCellStyle(cellStyle1);

        XSSFCell cell49_2_3 = row33.createCell(inOutHospiXpos + tempStep++);
        cell49_2_3.setCellValue("出院诊断/母");
        cell49_2_3.setCellStyle(cellStyle1);

        for (int i = 0; i < newChildCount; i++) {
            XSSFCell cell49_2_4 = row33.createCell(inOutHospiXpos + tempStep++);
            cell49_2_4.setCellValue("出院诊断/婴儿" + (i + 1));
            cell49_2_4.setCellStyle(cellStyle1);

            XSSFCell cell49_2_5 = row33.createCell(inOutHospiXpos + tempStep++);
            cell49_2_5.setCellValue("新生儿" + (i + 1) + "血糖/mg/dl");
            cell49_2_5.setCellStyle(cellStyle1);
        }

        XSSFCell cell49_2_6 = row33.createCell(inOutHospiXpos + tempStep++);
        cell49_2_6.setCellValue("产后血红蛋白/g/L");
        cell49_2_6.setCellStyle(cellStyle1);

        XSSFCell cell49_2_7 = row33.createCell(inOutHospiXpos + tempStep++);
        cell49_2_7.setCellValue("出院诊断备注");
        cell49_2_7.setCellStyle(cellStyle1);

        tempStep = 0;
        CellRangeAddress cra2 = new CellRangeAddress(1, 1, childbirthXpos, inOutHospiXpos + 4 + newChildCount * 2);
        sheet.addMergedRegion(cra2);
        RegionUtil.setBorderBottom(1, cra2, sheet, wb); // 下边框
        RegionUtil.setBorderLeft(1, cra2, sheet, wb); // 左边框
        RegionUtil.setBorderRight(1, cra2, sheet, wb); // 有边框
        RegionUtil.setBorderTop(1, cra2, sheet, wb); // 上边框
        // 创建单元格
        XSSFCell celln = row1.createCell(childbirthXpos);
        celln.setCellStyle(cellStyle1);
        celln.setCellValue("分娩结局");
        // 分娩结果表头结束

    }

    /**
     * 
     * 动态生成总表的内容
     * 
     * @author zj
     * @param wb
     * @param sheet
     * @param excelPojoList
     * @param cellStyle
     * @param cellStyle1
     * @param pregExamPosi
     * @param doctorAdviceXpos
     * @param childbirthXpos
     */
    public static SXSSFWorkbook generalTableContent(Workbook wbx, XSSFSheet sheetx, List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, int pregExamPosi, int doctorAdviceXpos,
            int childbirthXpos, int newChildXpos1, int inOutHosXpos, SearchCountCondition condition) {
        int rowIndexBegin = 4;
        int stage = condition.getStage();
        SXSSFWorkbook wbs = new SXSSFWorkbook((XSSFWorkbook) wbx);
        if (CollectionUtils.isEmpty(excelPojoList)) {
            return wbs;
        }
        int lineNo = 0; // 第一列的序号
        SXSSFSheet sheet = (SXSSFSheet) wbs.getSheetAt(0);
        for (StatisticDataPojo excelPojo : excelPojoList) {

            SXSSFRow row = (SXSSFRow) sheet.getRow(rowIndexBegin);
            if (row == null) {
                row = (SXSSFRow) sheet.createRow(rowIndexBegin);
            }
            // row.setRowStyle(cellStyle);
            rowIndexBegin++;
            int clumnNo = 1;
            SXSSFCell childbirthCell_1 = (SXSSFCell) row.createCell(0);
            childbirthCell_1.setCellStyle(cellStyle);
            childbirthCell_1.setCellValue(String.valueOf(++lineNo));

            // 基础信息
            int reciveXpoi = pregBaseInfo(excelPojo, pregExamPosi, row, cellStyle, clumnNo, condition);

            // 接诊信息
            pregReciveDiag(excelPojo, pregExamPosi, row, cellStyle, reciveXpoi);

            // 生成孕期检验项目
            pregExamContent(excelPojo, pregExamPosi, row, cellStyle, newChildXpos1, condition);

            // 孕期医嘱内容
            pregDocAdvice(excelPojo, pregExamPosi, row, cellStyle, doctorAdviceXpos);

            if (stage == 2) {
                // 分娩结束内容
                pregChildBirth(excelPojo, pregExamPosi, row, cellStyle, childbirthXpos, newChildXpos1, condition.getBabyNumMax());

                // 出入院情况
                inOutHospital(excelPojo, pregExamPosi, row, cellStyle, inOutHosXpos, condition.getBabyNumMax());
            }
        }
        return wbs;
    }

    /**
     * 
     * 基础数据信息输出
     * 
     * @author zj
     * @param excelPojo
     * @param pregExamPosi
     * @param row
     * @param cellStyle
     * @param clumnNo
     */
    public static int pregBaseInfo(StatisticDataPojo excelPojo, int pregExamPosi, SXSSFRow row,
            XSSFCellStyle cellStyle, int clumnNo, SearchCountCondition condition) {
        int stage = condition.getStage();
        int xPoi = 0;
        // 病人ID
        SXSSFCell childbirthCellBase_1 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_1.setCellStyle(cellStyle);
        String baseValue1 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustPatientId()))
                ? excelPojo.getCustomerInfoPojo().getCustPatientId().toString()
                : "";
        childbirthCellBase_1.setCellValue(baseValue1);
        // 病案号
        SXSSFCell childbirthCellBase_2 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_2.setCellStyle(cellStyle);
        String baseValue2 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustSikeId()))
                ? excelPojo.getCustomerInfoPojo().getCustSikeId().toString()
                : "";
        childbirthCellBase_2.setCellValue(baseValue2);
        // 姓名
        SXSSFCell childbirthCellBase_3 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_3.setCellStyle(cellStyle);
        String baseValue3 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustName()))
                ? excelPojo.getCustomerInfoPojo().getCustName().toString()
                : "";
        childbirthCellBase_3.setCellValue(baseValue3);

        // 出生日期
        SXSSFCell childbirthCellBase_4 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_4.setCellStyle(cellStyle);
        String baseValue4 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustBirthday()))
                ? excelPojo.getCustomerInfoPojo().getCustBirthday().toString()
                : "";
        childbirthCellBase_4.setCellValue(baseValue4);
        // 年龄
        SXSSFCell childbirthCellBase_5 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_5.setCellStyle(cellStyle);
        String baseValue5 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustAge()))
                ? excelPojo.getCustomerInfoPojo().getCustAge().toString()
                : "";
        childbirthCellBase_5.setCellValue(baseValue5);
        // 身高
        SXSSFCell childbirthCellBase_6 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_6.setCellStyle(cellStyle);
        String baseValue6 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustHeight()))
                ? excelPojo.getCustomerInfoPojo().getCustHeight().toString()
                : "";
        childbirthCellBase_6.setCellValue(baseValue6);
        // 胎数
        SXSSFCell childbirthCellBase_7 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_7.setCellStyle(cellStyle);
        String baseValue7 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getEmbryoNum()))
                ? excelPojo.getCustomerInfoPojo().getEmbryoNum().toString()
                : "";
        childbirthCellBase_7.setCellValue(baseValue7);
        // 受孕方式
        SXSSFCell childbirthCellBase_8 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_8.setCellStyle(cellStyle);
        String baseValue8 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getEncyesisSituation()))
                ? excelPojo.getCustomerInfoPojo().getEncyesisSituation().toString()
                : "";
        childbirthCellBase_8.setCellValue(baseValue8);
        // 预产期
        if (stage == 1) {
            SXSSFCell childbirthCellBase_9 = (SXSSFCell) row.createCell(clumnNo++);
            childbirthCellBase_9.setCellStyle(cellStyle);
            String baseValue9 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getPregnancyDueDate()))
                    ? excelPojo.getCustomerInfoPojo().getPregnancyDueDate().toString()
                    : "";
            childbirthCellBase_9.setCellValue(baseValue9);
        }

        // 孕前体重
        SXSSFCell childbirthCellBase_10 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_10.setCellStyle(cellStyle);
        String baseValue10 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getWeight()))
                ? excelPojo.getCustomerInfoPojo().getWeight().toString()
                : "";
        childbirthCellBase_10.setCellValue(baseValue10);
        // 孕前BMI
        SXSSFCell childbirthCellBase_11 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_11.setCellStyle(cellStyle);
        String baseValue11 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getBmi()))
                ? excelPojo.getCustomerInfoPojo().getBmi().toString()
                : "";
        childbirthCellBase_11.setCellValue(baseValue11);

        if (stage == 2) {
            baseValue4 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getCustBirthday()))
                    ? excelPojo.getBirthEndingPojo().getCustBirthday().toString()
                    : "";
            childbirthCellBase_4.setCellValue(baseValue4);

            baseValue5 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getCustAge()))
                    ? excelPojo.getBirthEndingPojo().getCustAge().toString()
                    : "";
            childbirthCellBase_5.setCellValue(baseValue5);

            baseValue6 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getCustHeight()))
                    ? excelPojo.getBirthEndingPojo().getCustHeight().toString()
                    : "";
            childbirthCellBase_6.setCellValue(baseValue6);

            baseValue7 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getEmbryoNum()))
                    ? excelPojo.getBirthEndingPojo().getEmbryoNum().toString()
                    : "";
            childbirthCellBase_7.setCellValue(baseValue7);

            baseValue8 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthTiresType()))
                    ? excelPojo.getBirthEndingPojo().getBirthTiresType().toString()
                    : "";
            if ("1".equals(baseValue8)) {
                String baseValue88 = (excelPojo.getBirthEndingPojo() != null
                        && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getEncyesisSituation()))
                        ? excelPojo.getBirthEndingPojo().getEncyesisSituation().toString()
                        : "";
                if ("1".equals(baseValue88)) {
                    baseValue8 = "自然受孕 意外妊娠";
                } else if ("2".equals(baseValue88)) {
                    baseValue8 = "自然受孕 计划妊娠";
                } else {
                    baseValue8 = "自然受孕";
                }
            } else if ("2".equals(baseValue8)) {
                baseValue8 = "辅助生殖";
            }
            childbirthCellBase_8.setCellValue(baseValue8);

            baseValue10 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getWeight()))
                    ? excelPojo.getBirthEndingPojo().getWeight().toString()
                    : "";
            childbirthCellBase_10.setCellValue(baseValue10);

            baseValue11 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBmi()))
                    ? excelPojo.getBirthEndingPojo().getBmi().toString()
                    : "";
            childbirthCellBase_11.setCellValue(baseValue11);
        }
        // 孕前病史
        SXSSFCell childbirthCellBase_12 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_12.setCellStyle(cellStyle);
        String baseValue12 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getDiseaseHistory()))
                ? excelPojo.getCustomerInfoPojo().getDiseaseHistory().toString()
                : "";
        childbirthCellBase_12.setCellValue(baseValue12);
        // 家族史
        SXSSFCell childbirthCellBase_13 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_13.setCellStyle(cellStyle);
        String baseValue13 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getFamilyHistory()))
                ? excelPojo.getCustomerInfoPojo().getFamilyHistory().toString()
                : "";
        childbirthCellBase_13.setCellValue(baseValue13);
        // 用药史
        SXSSFCell childbirthCellBase_14 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_14.setCellStyle(cellStyle);
        String baseValue14 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getMedicineHistory()))
                ? excelPojo.getCustomerInfoPojo().getMedicineHistory().toString()
                : "";
        childbirthCellBase_14.setCellValue(baseValue14);
        // 过敏史
        SXSSFCell childbirthCellBase_15 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_15.setCellStyle(cellStyle);
        String baseValue15 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getAllergyHistory()))
                ? excelPojo.getCustomerInfoPojo().getAllergyHistory().toString()
                : "";
        childbirthCellBase_15.setCellValue(baseValue15);
        // 手术史
        SXSSFCell childbirthCellBase_16 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_16.setCellStyle(cellStyle);
        String baseValue16 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getTreatmentHistory()))
                ? excelPojo.getCustomerInfoPojo().getTreatmentHistory().toString()
                : "";
        childbirthCellBase_16.setCellValue(baseValue16);
        // 高碘接触史
        SXSSFCell childbirthCellBase_17 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_17.setCellStyle(cellStyle);
        String baseValue17 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getIodineNum()))
                ? excelPojo.getCustomerInfoPojo().getIodineNum().toString()
                : "";
        childbirthCellBase_17.setCellValue(baseValue17);
        // 糖尿病史或糖尿病前期状态
        SXSSFCell childbirthCellBase_18 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_18.setCellStyle(cellStyle);
        String baseValue18 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getDiabetesRelevant()))
                ? excelPojo.getCustomerInfoPojo().getDiabetesRelevant().toString()
                : "";
        childbirthCellBase_18.setCellValue(baseValue18);
        // 孕次
        SXSSFCell childbirthCellBase_19 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_19.setCellStyle(cellStyle);
        String baseValue19 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getPregnancyNum()))
                ? excelPojo.getCustomerInfoPojo().getPregnancyNum().toString()
                : "";
        childbirthCellBase_19.setCellValue(baseValue19);
        // 产次
        SXSSFCell childbirthCellBase_20 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_20.setCellStyle(cellStyle);
        String baseValue20 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getBirthNum()))
                ? excelPojo.getCustomerInfoPojo().getBirthNum().toString()
                : "";
        childbirthCellBase_20.setCellValue(baseValue20);
        // 人工流产
        SXSSFCell childbirthCellBase_21 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_21.setCellStyle(cellStyle);
        String baseValue21 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getInducedAbortionNum()))
                ? excelPojo.getCustomerInfoPojo().getInducedAbortionNum().toString()
                : "";
        childbirthCellBase_21.setCellValue(baseValue21);
        // 自然流产
        SXSSFCell childbirthCellBase_22 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_22.setCellStyle(cellStyle);
        String baseValue22 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getAbortionNum()))
                ? excelPojo.getCustomerInfoPojo().getAbortionNum().toString()
                : "";
        childbirthCellBase_22.setCellValue(baseValue22);
        // 胎停育
        SXSSFCell childbirthCellBase_23 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_23.setCellStyle(cellStyle);
        String baseValue23 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getChildStopNum()))
                ? excelPojo.getCustomerInfoPojo().getChildStopNum().toString()
                : "";
        childbirthCellBase_23.setCellValue(baseValue23);
        // 早产
        SXSSFCell childbirthCellBase_24 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_24.setCellStyle(cellStyle);
        String baseValue24 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getPretermNum()))
                ? excelPojo.getCustomerInfoPojo().getPretermNum().toString()
                : "";
        childbirthCellBase_24.setCellValue(baseValue24);
        // 中晚期引产
        SXSSFCell childbirthCellBase_25 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_25.setCellStyle(cellStyle);
        String baseValue25 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getOdinopoeiaNum()))
                ? excelPojo.getCustomerInfoPojo().getOdinopoeiaNum().toString()
                : "";
        childbirthCellBase_25.setCellValue(baseValue25);
        // 巨大儿分娩史
        SXSSFCell childbirthCellBase_26 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_26.setCellStyle(cellStyle);
        String baseValue26 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getGiantBabyNum()))
                ? excelPojo.getCustomerInfoPojo().getGiantBabyNum().toString()
                : "";
        childbirthCellBase_26.setCellValue(baseValue26);
        // 畸形
        SXSSFCell childbirthCellBase_27 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_27.setCellStyle(cellStyle);
        String baseValue27 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getMalformationNum()))
                ? excelPojo.getCustomerInfoPojo().getMalformationNum().toString()
                : "";
        childbirthCellBase_27.setCellValue(baseValue27);
        // 既往妊娠并发症
        SXSSFCell childbirthCellBase_28 = (SXSSFCell) row.createCell(clumnNo++);
        childbirthCellBase_28.setCellStyle(cellStyle);
        String baseValue28 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getPregnancyComplications()))
                ? excelPojo.getCustomerInfoPojo().getPregnancyComplications().toString()
                : "";
        childbirthCellBase_28.setCellValue(baseValue28);

        // 备注（基础信息备注）
        if (stage == 2) {
            SXSSFCell childbirthCellBase_29 = (SXSSFCell) row.createCell(clumnNo++);
            childbirthCellBase_29.setCellStyle(cellStyle);
            String baseValue29 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthBaseRemark()))
                    ? excelPojo.getBirthEndingPojo().getBirthBaseRemark().toString()
                    : "";
            childbirthCellBase_29.setCellValue(baseValue29);
        }
        xPoi = clumnNo;
        return xPoi;
    }

    /**
     * 
     * 接诊内容输出
     * 
     * @author zj
     * @param excelPojo
     * @param pregExamPosi
     * @param row
     * @param cellStyle
     * @param clumnNo
     */
    public static void pregReciveDiag(StatisticDataPojo excelPojo, int pregExamPosi, SXSSFRow row,
            XSSFCellStyle cellStyle, int reciveXpos) {
        int clumnNo = 0;
        // 营养随诊次数
        SXSSFCell childbirthCellBase_29 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_29.setCellStyle(cellStyle);
        String baseValue29 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisCount()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisCount().toString()
                : "";
        childbirthCellBase_29.setCellValue(baseValue29);
        // 营养门诊接诊时间
        SXSSFCell childbirthCellBase_30 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_30.setCellStyle(cellStyle);
        String baseValue30 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisDate()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisDate().toString()
                : "";
        if (baseValue30.length() >= 10) {
            baseValue30 = baseValue30.substring(0, 10);
        }
        childbirthCellBase_30.setCellValue(baseValue30);
        // 一日门诊
        SXSSFCell childbirthCellBase_31 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_31.setCellStyle(cellStyle);
        childbirthCellBase_31.setCellValue("");
        // MDT门诊
        SXSSFCell childbirthCellBase_32 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_32.setCellStyle(cellStyle);
        childbirthCellBase_32.setCellValue("");
        // 营养门诊
        SXSSFCell childbirthCellBase_33 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_33.setCellStyle(cellStyle);
        String baseValue33 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisName()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisName().toString()
                : "";
        childbirthCellBase_33.setCellValue(baseValue33);
        // 营养门诊医生
        SXSSFCell childbirthCellBase_34 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_34.setCellStyle(cellStyle);
        String baseValue34 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisUserName()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisUserName().toString()
                : "";
        childbirthCellBase_34.setCellValue(baseValue34);
        // // 产科门诊医生
        // SXSSFCell childbirthCellBase_35 = row.createCell(reciveXpos + clumnNo++);
        // childbirthCellBase_35.setCellStyle(cellStyle);
        // String baseValue35 = (excelPojo.getDiagnosisPojo() != null
        // && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisReferralDoctorName()))
        // ? excelPojo.getDiagnosisPojo().getDiagnosisReferralDoctorName().toString() : "";
        // childbirthCellBase_35.setCellValue(baseValue35);
        // 产科医师
        SXSSFCell childbirthCellBase_36 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_36.setCellStyle(cellStyle);
        String baseValue36 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisReferralDoctorName()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisReferralDoctorName().toString()
                : "";
        childbirthCellBase_36.setCellValue(baseValue36);
        // 孕周数
        SXSSFCell childbirthCellBase_37 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_37.setCellStyle(cellStyle);
        String baseValue37 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisGestationalWeeks()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisGestationalWeeks().toString()
                : "";
        childbirthCellBase_37.setCellValue(baseValue37);
        // 当前体重
        SXSSFCell childbirthCellBase_38 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_38.setCellStyle(cellStyle);
        String baseValue38 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisCustWeight()))
                ? excelPojo.getDiagnosisPojo().getDiagnosisCustWeight().toString()
                : "";
        childbirthCellBase_38.setCellValue(baseValue38);
        // 检测时间
        SXSSFCell childbirthCellBase_39 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_39.setCellStyle(cellStyle);
        String baseValue39 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getExamDate()))
                ? excelPojo.getDiagnosisPojo().getExamDate().toString()
                : "";
        if (baseValue39.length() >= 10) {
            baseValue39 = baseValue39.substring(0, 10);
        }
        childbirthCellBase_39.setCellValue(baseValue39);
        // 瘦体重
        SXSSFCell childbirthCellBase_40 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_40.setCellStyle(cellStyle);
        String baseValue40 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getWt()))
                ? excelPojo.getDiagnosisPojo().getWt().toString()
                : "";
        childbirthCellBase_40.setCellValue(baseValue40);
        // 体脂肪
        SXSSFCell childbirthCellBase_41 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_41.setCellStyle(cellStyle);
        String baseValue41 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getBfm()))
                ? excelPojo.getDiagnosisPojo().getBfm().toString()
                : "";
        childbirthCellBase_41.setCellValue(baseValue41);
        // 体脂百分比
        SXSSFCell childbirthCellBase_42 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_42.setCellStyle(cellStyle);
        String baseValue42 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getPbf()))
                ? excelPojo.getDiagnosisPojo().getPbf().toString()
                : "";
        childbirthCellBase_42.setCellValue(baseValue42);
        // 骨骼肌
        SXSSFCell childbirthCellBase_43 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_43.setCellStyle(cellStyle);
        String baseValue43 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getSmm()))
                ? excelPojo.getDiagnosisPojo().getSmm().toString()
                : "";
        childbirthCellBase_43.setCellValue(baseValue43);
        // 蛋白质
        SXSSFCell childbirthCellBase_44 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_44.setCellStyle(cellStyle);
        String baseValue44 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getProtein()))
                ? excelPojo.getDiagnosisPojo().getProtein().toString()
                : "";
        childbirthCellBase_44.setCellValue(baseValue44);
        // 无机盐
        SXSSFCell childbirthCellBase_45 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_45.setCellStyle(cellStyle);
        String baseValue45 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getMineral()))
                ? excelPojo.getDiagnosisPojo().getMineral().toString()
                : "";
        childbirthCellBase_45.setCellValue(baseValue45);
        // 细胞内水分
        SXSSFCell childbirthCellBase_46 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_46.setCellStyle(cellStyle);
        String baseValue46 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getIcw()))
                ? excelPojo.getDiagnosisPojo().getIcw().toString()
                : "";
        childbirthCellBase_46.setCellValue(baseValue46);
        // 细胞外水分
        SXSSFCell childbirthCellBase_47 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_47.setCellStyle(cellStyle);
        String baseValue47 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getEcw()))
                ? excelPojo.getDiagnosisPojo().getEcw().toString()
                : "";
        childbirthCellBase_47.setCellValue(baseValue47);
        // 细胞外水分比率
        SXSSFCell childbirthCellBase_48 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_48.setCellStyle(cellStyle);
        String baseValue48 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getWed()))
                ? excelPojo.getDiagnosisPojo().getWed().toString()
                : "";
        childbirthCellBase_48.setCellValue(baseValue48);
        // 相位角
        SXSSFCell childbirthCellBase_49 = (SXSSFCell) row.createCell(reciveXpos + clumnNo++);
        childbirthCellBase_49.setCellStyle(cellStyle);
        String baseValue49 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getWbpa50()))
                ? excelPojo.getDiagnosisPojo().getWbpa50().toString()
                : "";
        childbirthCellBase_49.setCellValue(baseValue49);

    }

    /**
     * 
     * 生成孕期检验项目内容（公用）
     * 
     * @author zj
     * @param excelPojo
     * @param pregExamXPosi
     * @param row
     * @param cellStyle
     */
    public static void pregExamContent(StatisticDataPojo excelPojo, int pregExamXPosi, SXSSFRow row,
            XSSFCellStyle cellStyle, int newChildXpos1, SearchCountCondition condition) {
        // 本次诊断
        SXSSFCell cellExam1 = (SXSSFCell) row.createCell(pregExamXPosi);
        cellExam1.setCellStyle(cellStyle);
        String cellExamValue = (excelPojo.getDiagnosisPojo() != null
                && excelPojo.getDiagnosisPojo().getDiseaseNames() != null)
                ? excelPojo.getDiagnosisPojo().getDiseaseNames()
                : "";
        cellExam1.setCellValue(cellExamValue);
        // 风险级别
        SXSSFCell cellExam2 = (SXSSFCell) row.createCell(pregExamXPosi + 1);
        cellExam2.setCellStyle(cellStyle);
        String cellExamValue2 = (excelPojo.getDiagnosisPojo() != null
                && excelPojo.getDiagnosisPojo().getGestationLevel() != null)
                ? excelPojo.getDiagnosisPojo().getGestationLevel().toString()
                : "";
        cellExam2.setCellValue(cellExamValue2);

        int tempi = 1;
        // 根据表头动态输出孕检部分内容
        String items[] = condition.getItems().split(",");
        if (items.length > 0) {
            for (String itemId : items) {
                if (StringUtils.isEmpty(itemId)) {
                    break;
                }
                tempi++;
                SXSSFCell cell22 = (SXSSFCell) row.createCell(pregExamXPosi + tempi);
                cell22.setCellStyle(cellStyle);
                tempi++;
                SXSSFCell cell23 = (SXSSFCell) row.createCell(pregExamXPosi + tempi);
                cell23.setCellStyle(cellStyle);
                if (excelPojo.getDiagnosisPojo() != null && !(CollectionUtils.isEmpty(excelPojo.getDiagnosisPojo().getItems()))) {
                    for (DiagnosisHospitalItemPojo itemPojo : excelPojo.getDiagnosisPojo().getItems()) {
                        if (itemId.equals(itemPojo.getItemId())) {
                            String pregDate = (itemPojo != null && !StringUtils.isEmpty(itemPojo.getReportDate()))
                                    ? itemPojo.getReportDate().toString()
                                    : "";
                            if (pregDate.length() >= 10) {
                                pregDate = pregDate.substring(0, 10);
                            }
                            cell22.setCellValue(pregDate);
                            String pregValue = (itemPojo != null && !StringUtils.isEmpty(itemPojo.getItemValue()))
                                    ? itemPojo.getItemValue()
                                    : "";
                            cell23.setCellValue(pregValue);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * 分娩内容输出
     * 
     * @author zj
     * @param excelPojo
     * @param pregExamPosi
     * @param row
     * @param cellStyle
     * @param childbirthXpos
     * @param newChildXpos1
     */
    public static void pregChildBirth(StatisticDataPojo excelPojo, int pregExamPosi, SXSSFRow row,
            XSSFCellStyle cellStyle, int childbirthXpos, int newChildXpos1, int maxBabyNum) {
        // 住院日期
        SXSSFCell childbirthCell1 = (SXSSFCell) row.createCell(childbirthXpos);
        childbirthCell1.setCellStyle(cellStyle);
        String cbValue1 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthHospitalDate()))
                ? excelPojo.getBirthEndingPojo().getBirthHospitalDate().toString()
                : "";
        childbirthCell1.setCellValue(cbValue1);

        // 住院号
        SXSSFCell childbirthCell2 = (SXSSFCell) row.createCell(childbirthXpos + 1);
        childbirthCell2.setCellStyle(cellStyle);
        String cbValue2 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthPatientId()))
                ? excelPojo.getBirthEndingPojo().getBirthPatientId().toString()
                : "";
        childbirthCell2.setCellValue(cbValue2);

        // 产检医院
        SXSSFCell childbirthCell3 = (SXSSFCell) row.createCell(childbirthXpos + 2);
        childbirthCell3.setCellStyle(cellStyle);
        String cbValue3 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthPregHospital()))
                ? excelPojo.getBirthEndingPojo().getBirthPregHospital().toString()
                : "";
        childbirthCell3.setCellValue(cbValue3);

        // 分娩医院
        SXSSFCell childbirthCell4 = (SXSSFCell) row.createCell(childbirthXpos + 3);
        childbirthCell4.setCellStyle(cellStyle);
        String cbValue4 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthHospital()))
                ? excelPojo.getBirthEndingPojo().getBirthHospital().toString()
                : "";
        childbirthCell4.setCellValue(cbValue4);

        // 入院诊断
        SXSSFCell childbirthCell5 = (SXSSFCell) row.createCell(childbirthXpos + 4);
        childbirthCell5.setCellStyle(cellStyle);
        String cbValue5 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthDiagnosis()))
                ? excelPojo.getBirthEndingPojo().getBirthDiagnosis().toString()
                : "";
        childbirthCell5.setCellValue(cbValue5);

        // 分娩日期
        SXSSFCell childbirthCell6 = (SXSSFCell) row.createCell(childbirthXpos + 5);
        childbirthCell6.setCellStyle(cellStyle);
        String cbValue6 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBirthDate() != null)
                ? JodaTimeTools.toString(excelPojo.getBirthEndingPojo().getBirthDate(), JodaTimeTools.FORMAT_6)
                        + (excelPojo.getBirthEndingPojo().getBaseTimeHour() != null ? " "
                                + excelPojo.getBirthEndingPojo().getBaseTimeHour() + "时" : "")
                        + (excelPojo.getBirthEndingPojo().getBaseTimeMinuters() != null ?
                                excelPojo.getBirthEndingPojo().getBaseTimeMinuters() + "分" : "")
                : "";
        childbirthCell6.setCellValue(cbValue6);

        // 分娩孕周数
        SXSSFCell childbirthCell7 = (SXSSFCell) row.createCell(childbirthXpos + 6);
        childbirthCell7.setCellStyle(cellStyle);
        String cbValue7 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthWeeks()))
                ? excelPojo.getBirthEndingPojo().getBirthWeeks().toString()
                : "";
        childbirthCell7.setCellValue(cbValue7);

        // 危重孕产妇
        SXSSFCell childbirthCell8 = (SXSSFCell) row.createCell(childbirthXpos + 7);
        childbirthCell8.setCellStyle(cellStyle);
        String cbValue8 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseIscritical()))
                ? excelPojo.getBirthEndingPojo().getBaseIscritical().toString()
                : "";
        String cbValue8_1 = "";
        if (cbValue8.equals("1")) {
            cbValue8_1 = "是";
        } else if (cbValue8.equals("0")) {
            cbValue8_1 = "否";
        }
        childbirthCell8.setCellValue(cbValue8_1);
        // 麻醉方式
        SXSSFCell childbirthCell9 = (SXSSFCell) row.createCell(childbirthXpos + 8);
        childbirthCell9.setCellStyle(cellStyle);
        String cbValue9_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseHocusType() != null)
                ? String.valueOf(excelPojo.getBirthEndingPojo().getBaseHocusType())
                : "";
        String cbValue9 = "";
        if (cbValue9_1.length() > 0 && ExportExcel.dictMap.get("MAZUITYPE" + cbValue9_1) != null) {
            cbValue9 = ExportExcel.dictMap.get("MAZUITYPE" + cbValue9_1).getCodeName();
        }
        // if (cbValue9_1.equals("1")) {
        // cbValue9 = "局部麻醉";
        // } else if (cbValue9_1.equals("2")) {
        // cbValue9 = "全身麻醉";
        // } else if (cbValue9_1.equals("3")) {
        // cbValue9 = "椎管内麻醉";
        // }
        childbirthCell9.setCellValue(cbValue9);
        // 分娩方式
        SXSSFCell childbirthCell10 = (SXSSFCell) row.createCell(childbirthXpos + 9);
        childbirthCell10.setCellStyle(cellStyle);
        String cbValue10_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseBirthType() != null)
                ? String.valueOf(excelPojo.getBirthEndingPojo().getBaseBirthType())
                : "";
        String cbValue10 = "";
        String[] birthTypeArray = cbValue10_1.split(",");
        if (birthTypeArray.length > 0) {
            for (int i = 0; i < birthTypeArray.length; i++) {
                if (cbValue10.length() > 0) {
                    cbValue10 += ",";
                }
                if (birthTypeArray[i].length() > 0 && ExportExcel.dictMap.get("CHILDBIRTHTYPE" + birthTypeArray[i]) != null) {
                    cbValue10 += ExportExcel.dictMap.get("CHILDBIRTHTYPE" + birthTypeArray[i]).getCodeName();
                }
            }
        }
        // if (cbValue10_1.equals("1")) {
        // cbValue10 = "自然分娩";
        // } else if (cbValue10_1.equals("2")) {
        // cbValue10 = "吸引";
        // } else if (cbValue10_1.equals("3")) {
        // cbValue10 = "产钳";
        // } else if (cbValue10_1.equals("4")) {
        // cbValue10 = "臀助产";
        // } else if (cbValue10_1.equals("5")) {
        // cbValue10 = "剖宫产";
        // } else if (cbValue10_1.equals("6")) {
        // cbValue10 = "其他";
        // }
        childbirthCell10.setCellValue(cbValue10);
        // 剖宫产指征
        SXSSFCell childbirthCell11 = (SXSSFCell) row.createCell(childbirthXpos + 10);
        childbirthCell11.setCellStyle(cellStyle);
        String cbValue11 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBasePgcIndication()))
                ? excelPojo.getBirthEndingPojo().getBasePgcIndication().toString()
                : "";
        childbirthCell11.setCellValue(cbValue11);
        // 分娩方位
        SXSSFCell childbirthCell12 = (SXSSFCell) row.createCell(childbirthXpos + 11);
        childbirthCell12.setCellStyle(cellStyle);
        String cbValue12 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseBirthDirection()))
                ? excelPojo.getBirthEndingPojo().getBaseBirthDirection().toString()
                : "";
        childbirthCell12.setCellValue(cbValue12);
        // 孕期总体重增长
        SXSSFCell childbirthCell13 = (SXSSFCell) row.createCell(childbirthXpos + 12);
        childbirthCell13.setCellStyle(cellStyle);
        String cbValue13 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseGrowthPregnancying()))
                ? excelPojo.getBirthEndingPojo().getBaseGrowthPregnancying().toString()
                : "";
        childbirthCell13.setCellValue(cbValue13);
        // 分娩前体重
        SXSSFCell childbirthCell14 = (SXSSFCell) row.createCell(childbirthXpos + 13);
        childbirthCell14.setCellStyle(cellStyle);
        String cbValue14 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseWeightBeforeBirth()))
                ? excelPojo.getBirthEndingPojo().getBaseWeightBeforeBirth().toString()
                : "";
        childbirthCell14.setCellValue(cbValue14);
        // 分娩后体重
        SXSSFCell childbirthCell15 = (SXSSFCell) row.createCell(childbirthXpos + 14);
        childbirthCell15.setCellStyle(cellStyle);
        String cbValue15 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseWeightAfterBirth()))
                ? excelPojo.getBirthEndingPojo().getBaseWeightAfterBirth().toString()
                : "";
        childbirthCell15.setCellValue(cbValue15);
        // 第一产程
        SXSSFCell childbirthCell16 = (SXSSFCell) row.createCell(childbirthXpos + 15);
        childbirthCell16.setCellStyle(cellStyle);
        String cbValue16 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseChildBirthFist()))
                ? excelPojo.getBirthEndingPojo().getBaseChildBirthFist().toString()
                : "";
        childbirthCell16.setCellValue(cbValue16);
        // 第二产程
        SXSSFCell childbirthCell17 = (SXSSFCell) row.createCell(childbirthXpos + 16);
        childbirthCell17.setCellStyle(cellStyle);
        String cbValue17 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseChildBirthSecond()))
                ? excelPojo.getBirthEndingPojo().getBaseChildBirthSecond().toString()
                : "";
        childbirthCell17.setCellValue(cbValue17);
        // 第三产程
        SXSSFCell childbirthCell18 = (SXSSFCell) row.createCell(childbirthXpos + 17);
        childbirthCell18.setCellStyle(cellStyle);
        String cbValue18 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseChildBirthThrid()))
                ? excelPojo.getBirthEndingPojo().getBaseChildBirthThrid().toString()
                : "";
        childbirthCell18.setCellValue(cbValue18);

        // 总产程
        SXSSFCell childbirthCell19 = (SXSSFCell) row.createCell(childbirthXpos + 18);
        childbirthCell19.setCellStyle(cellStyle);
        String cbValue19 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseChildBirthSum()))
                ? excelPojo.getBirthEndingPojo().getBaseChildBirthSum().toString()
                : "";
        childbirthCell19.setCellValue(cbValue19);

        // 产时出血量
        SXSSFCell childbirthCell20 = (SXSSFCell) row.createCell(childbirthXpos + 19);
        childbirthCell20.setCellStyle(cellStyle);
        String cbValue20 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseBloodVolBirthing()))
                ? excelPojo.getBirthEndingPojo().getBaseBloodVolBirthing().toString()
                : "";
        childbirthCell20.setCellValue(cbValue20);
        // 产后两小时出血量
        SXSSFCell childbirthCell21 = (SXSSFCell) row.createCell(childbirthXpos + 20);
        childbirthCell21.setCellStyle(cellStyle);
        String cbValue21 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseBloodVolAfterBirth()))
                ? excelPojo.getBirthEndingPojo().getBaseBloodVolAfterBirth().toString()
                : "";
        childbirthCell21.setCellValue(cbValue21);
        // 总出血量
        SXSSFCell childbirthCell22 = (SXSSFCell) row.createCell(childbirthXpos + 21);
        childbirthCell22.setCellStyle(cellStyle);
        String cbValue22 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseBloodVolSum()))
                ? excelPojo.getBirthEndingPojo().getBaseBloodVolSum().toString()
                : "";
        childbirthCell22.setCellValue(cbValue22);
        // 阴检次数
        SXSSFCell childbirthCell23 = (SXSSFCell) row.createCell(childbirthXpos + 22);
        childbirthCell23.setCellStyle(cellStyle);
        String cbValue23 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBasePerineumCheckTimes()))
                ? excelPojo.getBirthEndingPojo().getBasePerineumCheckTimes().toString()
                : "";
        childbirthCell23.setCellValue(cbValue23);
        // 会阴情况
        SXSSFCell childbirthCell24 = (SXSSFCell) row.createCell(childbirthXpos + 23);
        childbirthCell24.setCellStyle(cellStyle);
        String cbValue24_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBasePerineumState() != null)
                ? excelPojo.getBirthEndingPojo().getBasePerineumState().toString()
                : "";
        String cbValue24 = "";
        if (cbValue24_1.equals("1")) {
            cbValue24 = "完整";
        } else if (cbValue24_1.equals("2")) {
            String cbValue24_11 = (excelPojo.getBirthEndingPojo() != null
                    && excelPojo.getBirthEndingPojo().getBasePerineumHurt() != null)
                    ? excelPojo.getBirthEndingPojo().getBasePerineumHurt().toString()
                    : "";
            if ("1".equals(cbValue24_11)) {
                cbValue24 = "Ⅰ°裂伤";
            } else if ("2".equals(cbValue24_11)) {
                cbValue24 = "Ⅱ°裂伤";
            } else if ("3".equals(cbValue24_11)) {
                cbValue24 = "Ⅲ°裂伤";
            } else {
                cbValue24 = "裂伤";
            }
        } else if (cbValue24_1.equals("3")) {
            cbValue24 = "切开";
        }
        childbirthCell24.setCellValue(cbValue24);
        // 胎盘情况
        SXSSFCell childbirthCell25 = (SXSSFCell) row.createCell(childbirthXpos + 24);
        childbirthCell25.setCellStyle(cellStyle);
        String cbValue25_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBasePerineumPlacenta() != null)
                ? excelPojo.getBirthEndingPojo().getBasePerineumPlacenta().toString()
                : "";
        String cbValue25 = null;
        if (cbValue25_1.equals("1")) {
            cbValue25 = "手剥";
        } else if (cbValue25_1.equals("2")) {
            cbValue25 = "沾水";
        } else if (cbValue25_1.equals("3")) {
            cbValue25 = "自然脱落";
        } else {
            cbValue25 = "";
        }
        childbirthCell25.setCellValue(cbValue25);

        // 手术
        SXSSFCell childbirthCell26 = (SXSSFCell) row.createCell(childbirthXpos + 25);
        childbirthCell26.setCellStyle(cellStyle);
        String cbValue26_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseSurgeryType() != null)
                ? excelPojo.getBirthEndingPojo().getBaseSurgeryType().toString()
                : "";
        String cbValue26 = "";
        String value[] = cbValue26_1.split(",");
        for (int i = 0; i < value.length; i++) {
            if (cbValue26.length() > 0) {
                cbValue26 += ",";
            }
            if (value[i].equals("1")) {
                cbValue26 += "引产：";
                String cbValue26_11 = (excelPojo.getBirthEndingPojo() != null
                        && excelPojo.getBirthEndingPojo().getBaseSurgeryDetail() != null)
                        ? excelPojo.getBirthEndingPojo().getBaseSurgeryDetail().toString()
                        : "";
                if ("1".equals(cbValue26_11)) {
                    cbValue26 += "改良药物";
                } else if ("2".equals(cbValue26_11)) {
                    cbValue26 += "剥膜";
                } else if ("3".equals(cbValue26_11)) {
                    cbValue26 += "点滴";
                } else if ("4".equals(cbValue26_11)) {
                    cbValue26 += "破膜";
                } else if ("5".equals(cbValue26_11)) {
                    cbValue26 += "其他";
                } else {
                    cbValue26 = "引产";
                }
            } else if (value[i].equals("2")) {
                cbValue26 += "产后刮宫";
            } else if (value[i].equals("3")) {
                cbValue26 += "手转胎头";
            } else if (value[i].equals("4")) {
                cbValue26 += "点滴加强";
            } else if (value[i].equals("5")) {
                cbValue26 += "其他：";
                String cbValue26_11 = (excelPojo.getBirthEndingPojo() != null
                        && excelPojo.getBirthEndingPojo().getBaseSurgeryDetail2() != null)
                        ? excelPojo.getBirthEndingPojo().getBaseSurgeryDetail2().toString()
                        : "";
                if (cbValue26_11.length() == 0) {
                    cbValue26 = "其他";
                } else {
                    cbValue26 += cbValue26_11;
                }
            }
        }
        childbirthCell26.setCellValue(cbValue26);

        // 产前检查
        SXSSFCell childbirthCell27 = (SXSSFCell) row.createCell(childbirthXpos + 26);
        childbirthCell27.setCellStyle(cellStyle);
        String cbValue27_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationPrenatalCal() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationPrenatalCal().toString()
                : "";
        String cbValue27 = null;
        if (cbValue27_1.equals("1")) {
            cbValue27 = "有";
        } else if (cbValue27_1.equals("0")) {
            cbValue27 = "无";
        } else {
            cbValue27 = "";
        }
        childbirthCell27.setCellValue(cbValue27);

        // 中度贫血HB小于90g/L
        SXSSFCell childbirthCell28 = (SXSSFCell) row.createCell(childbirthXpos + 27);
        childbirthCell28.setCellStyle(cellStyle);
        String cbValue28_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationAnemia() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationAnemia().toString()
                : "";
        String cbValue28 = null;
        if (cbValue28_1.equals("1")) {
            cbValue28 = "是";
        } else if (cbValue28_1.equals("0")) {
            cbValue28 = "否";
        } else {
            cbValue28 = "";
        }
        childbirthCell28.setCellValue(cbValue28);
        // 妊娠高血压疾病
        SXSSFCell childbirthCell29 = (SXSSFCell) row.createCell(childbirthXpos + 28);
        childbirthCell29.setCellStyle(cellStyle);
        String cbValue29_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationHypertension() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationHypertension().toString()
                : "";
        String cbValue29 = null;
        if (cbValue29_1.equals("1")) {
            cbValue29 = "是";
        } else if (cbValue29_1.equals("0")) {
            cbValue29 = "否";
        } else {
            cbValue29 = "";
        }
        childbirthCell29.setCellValue(cbValue29);
        // 产前合并症
        SXSSFCell childbirthCell30 = (SXSSFCell) row.createCell(childbirthXpos + 29);
        childbirthCell30.setCellStyle(cellStyle);
        String cbValue30 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationPrenatal() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationPrenatal().toString()
                : "";
        childbirthCell30.setCellValue(cbValue30);
        // 产时并发症
        SXSSFCell childbirthCell31 = (SXSSFCell) row.createCell(childbirthXpos + 30);
        childbirthCell31.setCellStyle(cellStyle);
        String cbValue31 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationBirthing() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationBirthing().toString()
                : "";
        childbirthCell31.setCellValue(cbValue31);
        // 产后并发症
        SXSSFCell childbirthCell32 = (SXSSFCell) row.createCell(childbirthXpos + 31);
        childbirthCell32.setCellStyle(cellStyle);
        String cbValue32 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationAfterBirthing() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationAfterBirthing().toString()
                : "";
        childbirthCell32.setCellValue(cbValue32);
        // 内科合并症
        SXSSFCell childbirthCell33 = (SXSSFCell) row.createCell(childbirthXpos + 32);
        childbirthCell33.setCellStyle(cellStyle);
        String cbValue33 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationsMedical() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationsMedical().toString()
                : "";
        childbirthCell33.setCellValue(cbValue33);
        // 传染病检测情况
        SXSSFCell childbirthCell34 = (SXSSFCell) row.createCell(childbirthXpos + 33);
        childbirthCell34.setCellStyle(cellStyle);
        String cbValue34 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseComplicationDisease() != null)
                ? excelPojo.getBirthEndingPojo().getBaseComplicationDisease().toString()
                : "";
        childbirthCell34.setCellValue(cbValue34);
        // 产后收缩压
        SXSSFCell childbirthCell35 = (SXSSFCell) row.createCell(childbirthXpos + 34);
        childbirthCell35.setCellStyle(cellStyle);
        String cbValue35 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseAfterBirthingSsy() != null)
                ? excelPojo.getBirthEndingPojo().getBaseAfterBirthingSsy().toString()
                : "";
        childbirthCell35.setCellValue(cbValue35);

        // 产后舒张压
        SXSSFCell childbirthCell36 = (SXSSFCell) row.createCell(childbirthXpos + 35);
        childbirthCell36.setCellStyle(cellStyle);
        String cbValue36 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseAfterBirthingSzy() != null)
                ? excelPojo.getBirthEndingPojo().getBaseAfterBirthingSzy().toString()
                : "";
        childbirthCell36.setCellValue(cbValue36);
        // 开奶时间
        SXSSFCell childbirthCell37 = (SXSSFCell) row.createCell(childbirthXpos + 36);
        childbirthCell37.setCellStyle(cellStyle);
        String cbValue37 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseAfterBirthingBreastMilkl() != null)
                ? excelPojo.getBirthEndingPojo().getBaseAfterBirthingBreastMilkl().toString()
                : "";
        if (cbValue37.length() >= 10) {
            cbValue37 = cbValue37.substring(0, 10);
        }
        childbirthCell37.setCellValue(cbValue37);

        // 产后死亡
        SXSSFCell childbirthCell38 = (SXSSFCell) row.createCell(childbirthXpos + 37);
        childbirthCell38.setCellStyle(cellStyle);
        String cbValue38_1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseBirthBirthingDetail() != null)
                ? excelPojo.getBirthEndingPojo().getBaseBirthBirthingDetail().toString()
                : "";
        String cbValue38 = "";
        if (cbValue38_1.length() > 0 && ExportExcel.dictMap.get("WHENDEAD" + cbValue38_1) != null) {
            cbValue38 = ExportExcel.dictMap.get("WHENDEAD" + cbValue38_1).getCodeName();
        }
        // if (cbValue38_1.equals("1")) {
        // cbValue38 = "产时";
        // } else if (cbValue38_1.equals("2")) {
        // cbValue38 = "产后";
        // } else {
        // cbValue38 = "";
        // }
        childbirthCell38.setCellValue(cbValue38);

        // 活产数
        SXSSFCell childbirthCell39 = (SXSSFCell) row.createCell(childbirthXpos + 38);
        childbirthCell39.setCellStyle(cellStyle);
        String cbValue39 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseBirthEndingLiveBirths() != null)
                ? excelPojo.getBirthEndingPojo().getBaseBirthEndingLiveBirths().toString()
                : "";
        childbirthCell39.setCellValue(cbValue39);
        // 死胎数
        SXSSFCell childbirthCell40 = (SXSSFCell) row.createCell(childbirthXpos + 39);
        childbirthCell40.setCellStyle(cellStyle);
        String cbValue40 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseBirthEndingDeathBabys() != null)
                ? excelPojo.getBirthEndingPojo().getBaseBirthEndingDeathBabys().toString()
                : "";
        childbirthCell40.setCellValue(cbValue40);
        // 死产数
        SXSSFCell childbirthCell41 = (SXSSFCell) row.createCell(childbirthXpos + 40);
        childbirthCell41.setCellStyle(cellStyle);
        String cbValue41 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseBirthEndingDeathBirths() != null)
                ? excelPojo.getBirthEndingPojo().getBaseBirthEndingDeathBirths().toString()
                : "";
        childbirthCell41.setCellValue(cbValue41);
        // 围产儿数
        SXSSFCell childbirthCell42 = (SXSSFCell) row.createCell(childbirthXpos + 41);
        childbirthCell42.setCellStyle(cellStyle);
        String cbValue42 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseBirthEndingPerinatal() != null)
                ? excelPojo.getBirthEndingPojo().getBaseBirthEndingPerinatal().toString()
                : "";
        childbirthCell42.setCellValue(cbValue42);
        // 孕28周前双/多胎宫内死亡胎数
        SXSSFCell childbirthCell43 = (SXSSFCell) row.createCell(childbirthXpos + 42);
        childbirthCell43.setCellStyle(cellStyle);
        String cbValue43 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseDeathBefor28() != null)
                ? excelPojo.getBirthEndingPojo().getBaseDeathBefor28().toString()
                : "";
        childbirthCell43.setCellValue(cbValue43);
        // 孕28周前双/多胎宫内死亡原因
        SXSSFCell childbirthCell44 = (SXSSFCell) row.createCell(childbirthXpos + 43);
        childbirthCell44.setCellStyle(cellStyle);
        String cbValue44 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseDeathReasonBefor28() != null)
                ? excelPojo.getBirthEndingPojo().getBaseDeathReasonBefor28().toString()
                : "";
        childbirthCell44.setCellValue(cbValue44);

        // 分娩信息备注
        SXSSFCell childbirthCell45 = (SXSSFCell) row.createCell(childbirthXpos + 44);
        childbirthCell45.setCellStyle(cellStyle);
        String cbValue45 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBaseRemark() != null)
                ? excelPojo.getBirthEndingPojo().getBaseRemark().toString()
                : "";
        childbirthCell45.setCellValue(cbValue45);

        // ========================== 新生儿开始 =======================================
        for (int i = 0; i < maxBabyNum * 24; i++) {
            SXSSFCell childbirthCell_baby = (SXSSFCell) row.createCell(newChildXpos1 + i);
            childbirthCell_baby.setCellStyle(cellStyle);
        }
        if (excelPojo.getBirthEndingPojo() != null
                && !CollectionUtils.isEmpty(excelPojo.getBirthEndingPojo().getBabyList())) {
            int cStep = 0;
            for (BirthEndingBabyInfoPojo babyinfo : excelPojo.getBirthEndingPojo().getBabyList()) {

                // 性别
                SXSSFCell childbirthCell_baby1 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby1.setCellStyle(cellStyle);
                String baby1 = (babyinfo != null && !StringUtils.isEmpty(babyinfo.getBabyGender()))
                        ? babyinfo.getBabyGender()
                        : "";
                childbirthCell_baby1.setCellValue(baby1);
                if (baby1.length() > 0 && ExportExcel.dictMap.get("NEWBIRTHSEX" + baby1) != null) {
                    childbirthCell_baby1.setCellValue(ExportExcel.dictMap.get("NEWBIRTHSEX" + baby1).getCodeName());
                }
                cStep++;
                // 出生日期
                SXSSFCell childbirthCell_baby2 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby2.setCellStyle(cellStyle);
                String baby2 = (babyinfo != null && babyinfo.getBabyBirthTime() != null)
                        ? JodaTimeTools.toString(babyinfo.getBabyBirthTime(), JodaTimeTools.FORMAT_6)
                                + (babyinfo.getBabyBirthTimeHour() != null ? " " + babyinfo.getBabyBirthTimeHour() + "时" : "")
                                + (babyinfo.getBabyBirthTimeMinutes() != null ? babyinfo.getBabyBirthTimeMinutes() + "分" : "")
                        : "";
                childbirthCell_baby2.setCellValue(baby2);
                cStep++;
                // 体重/g
                SXSSFCell childbirthCell_baby3 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby3.setCellStyle(cellStyle);
                String baby3 = (babyinfo != null && babyinfo.getBabyWeight() != null)
                        ? babyinfo.getBabyWeight().toString()
                        : "";
                childbirthCell_baby3.setCellValue(baby3);
                cStep++;
                // 身长/cm
                SXSSFCell childbirthCell_baby4 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby4.setCellStyle(cellStyle);
                String baby4 = (babyinfo != null && babyinfo.getBabyLength() != null)
                        ? babyinfo.getBabyLength().toString()
                        : "";
                childbirthCell_baby4.setCellValue(baby4);
                cStep++;
                // 头围/cm
                SXSSFCell childbirthCell_baby5 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby5.setCellStyle(cellStyle);
                String baby5 = (babyinfo != null && babyinfo.getBabyHeadCircum() != null)
                        ? babyinfo.getBabyHeadCircum().toString()
                        : "";
                childbirthCell_baby5.setCellValue(baby5);
                cStep++;
                // 胸围/cm
                SXSSFCell childbirthCell_baby6 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby6.setCellStyle(cellStyle);
                String baby6 = (babyinfo != null && babyinfo.getBabyBust() != null)
                        ? babyinfo.getBabyBust().toString()
                        : "";
                childbirthCell_baby6.setCellValue(baby6);
                cStep++;
                // 阿氏评分1分钟
                SXSSFCell childbirthCell_baby7 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby7.setCellStyle(cellStyle);
                String baby7 = (babyinfo != null && babyinfo.getBabyAshesOneMinute() != null)
                        ? babyinfo.getBabyAshesOneMinute().toString()
                        : "";
                childbirthCell_baby7.setCellValue(baby7);
                cStep++;
                // 阿氏评分5分钟
                SXSSFCell childbirthCell_baby8 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby8.setCellStyle(cellStyle);
                String baby8 = (babyinfo != null && babyinfo.getBabyAshesFiveMinutes() != null)
                        ? babyinfo.getBabyAshesFiveMinutes().toString()
                        : "";
                childbirthCell_baby8.setCellValue(baby8);
                cStep++;
                // 阿氏评分10分钟
                SXSSFCell childbirthCell_baby9 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby9.setCellStyle(cellStyle);
                String baby9 = (babyinfo != null && babyinfo.getBabyAshesTenMinutes() != null)
                        ? babyinfo.getBabyAshesTenMinutes().toString()
                        : "";
                childbirthCell_baby9.setCellValue(baby9);
                cStep++;
                // 新生儿窒息min
                SXSSFCell childbirthCell_baby10 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby10.setCellStyle(cellStyle);
                String baby10 = (babyinfo != null && babyinfo.getBabyStifle() != null)
                        ? babyinfo.getBabyStifle().toString()
                        : "";
                childbirthCell_baby10.setCellValue(baby10);
                cStep++;
                // 出生缺陷
                SXSSFCell childbirthCell_baby11 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby11.setCellStyle(cellStyle);
                String baby11 = (babyinfo != null && babyinfo.getBabyDefect() != null)
                        ? babyinfo.getBabyDefect().toString()
                        : "";
                childbirthCell_baby11.setCellValue(baby11);
                cStep++;
                // 抢救
                SXSSFCell childbirthCell_baby12 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby12.setCellStyle(cellStyle);
                String baby12 = (babyinfo != null && babyinfo.getBabyRescue() != null)
                        ? babyinfo.getBabyRescue().toString()
                        : "";
                if ("1".equals(baby12)) {
                    baby12 = "有";
                } else if ("0".equals(baby12)) {
                    baby12 = "无";
                }
                childbirthCell_baby12.setCellValue(baby12);
                cStep++;
                // 并发症
                SXSSFCell childbirthCell_baby13 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby13.setCellStyle(cellStyle);
                String baby13 = (babyinfo != null && babyinfo.getBabyComplication() != null)
                        ? babyinfo.getBabyComplication().toString()
                        : "";
                childbirthCell_baby13.setCellValue(baby13);
                cStep++;
                // 新生儿指导
                SXSSFCell childbirthCell_baby14 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby14.setCellStyle(cellStyle);
                String baby14 = (babyinfo != null && babyinfo.getBabyGuid() != null)
                        ? babyinfo.getBabyGuid().toString()
                        : "";
                childbirthCell_baby14.setCellValue(baby14);
                cStep++;
                // 胎盘重g
                SXSSFCell childbirthCell_baby15 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby15.setCellStyle(cellStyle);
                String baby15 = (babyinfo != null && babyinfo.getBabyPlacentaWeight() != null)
                        ? babyinfo.getBabyPlacentaWeight().toString()
                        : "";
                childbirthCell_baby15.setCellValue(baby15);
                cStep++;
                // 胎盘长cm
                SXSSFCell childbirthCell_baby16 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby16.setCellStyle(cellStyle);
                String baby16 = (babyinfo != null && babyinfo.getBabyPlacentaLength() != null)
                        ? babyinfo.getBabyPlacentaLength().toString()
                        : "";
                childbirthCell_baby16.setCellValue(baby16);
                cStep++;
                // 胎盘宽cm
                SXSSFCell childbirthCell_baby17 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby17.setCellStyle(cellStyle);
                String baby17 = (babyinfo != null && babyinfo.getBabyPlacentaWidth() != null)
                        ? babyinfo.getBabyPlacentaWidth().toString()
                        : "";
                childbirthCell_baby17.setCellValue(baby17);
                cStep++;
                // 胎盘厚cm
                SXSSFCell childbirthCell_baby18 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby18.setCellStyle(cellStyle);
                String baby18 = (babyinfo != null && babyinfo.getBabyPlacentaThick() != null)
                        ? babyinfo.getBabyPlacentaThick().toString()
                        : "";
                childbirthCell_baby18.setCellValue(baby18);
                cStep++;
                // 羊水量
                SXSSFCell childbirthCell_baby19 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby19.setCellStyle(cellStyle);
                String baby19 = (babyinfo != null && babyinfo.getBabyAmnioticFluidVol() != null)
                        ? babyinfo.getBabyAmnioticFluidVol().toString()
                        : "";
                if (baby19.length() > 0 && ExportExcel.dictMap.get("AFV" + baby19) != null) {
                    baby19 = ExportExcel.dictMap.get("AFV" + baby19).getCodeName();
                }
                // if ("1".equals(baby19)) {
                // baby19 = "多";
                // }else if ("2".equals(baby19)) {
                // baby19 = "中";
                // }else if ("3".equals(baby19)) {
                // baby19 = "少";
                // }
                childbirthCell_baby19.setCellValue(baby19);
                cStep++;

                // 羊水性状
                SXSSFCell childbirthCell_baby20 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby20.setCellStyle(cellStyle);
                String baby20 = (babyinfo != null && babyinfo.getBabyAmnioticFluidTraits() != null)
                        ? babyinfo.getBabyAmnioticFluidTraits().toString()
                        : "";
                if (baby20.length() > 0 && ExportExcel.dictMap.get("AFLUID" + baby20) != null) {
                    baby20 = ExportExcel.dictMap.get("AFLUID" + baby20).getCodeName();
                }
                // if ("1".equals(baby20)) {
                // baby20 = "清";
                // }else if("2".equals(baby20)) {
                // baby20 = "浊";
                // }
                childbirthCell_baby20.setCellValue(baby20);
                cStep++;

                // 新生儿备注
                SXSSFCell childbirthCell_baby21 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby21.setCellStyle(cellStyle);
                String baby21 = (babyinfo != null && babyinfo.getBabyRemark() != null)
                        ? babyinfo.getBabyRemark().toString()
                        : "";
                childbirthCell_baby21.setCellValue(baby21);
                cStep++;

                if (babyinfo != null && babyinfo.getBabyIsDeath() != null && babyinfo.getBabyIsDeath() == 1) {
                    childbirthCell_baby1.setCellValue("");
                    childbirthCell_baby2.setCellValue("");
                    childbirthCell_baby3.setCellValue("");
                    childbirthCell_baby4.setCellValue("");
                    childbirthCell_baby5.setCellValue("");
                    childbirthCell_baby6.setCellValue("");
                    childbirthCell_baby7.setCellValue("");
                    childbirthCell_baby8.setCellValue("");
                    childbirthCell_baby9.setCellValue("");
                    childbirthCell_baby10.setCellValue("");
                    childbirthCell_baby11.setCellValue("");
                    childbirthCell_baby12.setCellValue("");
                    childbirthCell_baby13.setCellValue("");
                    childbirthCell_baby14.setCellValue("");
                    childbirthCell_baby15.setCellValue("");
                    childbirthCell_baby16.setCellValue("");
                    childbirthCell_baby17.setCellValue("");
                    childbirthCell_baby18.setCellValue("");
                    childbirthCell_baby19.setCellValue("");
                    childbirthCell_baby20.setCellValue("");
                    childbirthCell_baby21.setCellValue("");
                }
                // 新生儿死亡
                SXSSFCell childbirthCell_baby22 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby22.setCellStyle(cellStyle);
                String baby22_1 = (babyinfo != null && babyinfo.getBabyIsDeath() != null)
                        ? babyinfo.getBabyIsDeath().toString()
                        : "";
                String baby22 = null;
                if (baby22_1.contentEquals("1")) {
                    baby22 = "是";
                } else if (baby22_1.contentEquals("0")) {
                    baby22 = "否";
                }
                childbirthCell_baby22.setCellValue(baby22);
                cStep++;
                // 死亡时间
                SXSSFCell childbirthCell_baby23 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby23.setCellStyle(cellStyle);
                String baby23 = (babyinfo != null && babyinfo.getBabyDeathTime() != null)
                        ? JodaTimeTools.toString(babyinfo.getBabyDeathTime(), JodaTimeTools.FORMAT_6)
                                + (babyinfo.getBabyDeathTimeHour() != null ? " " + babyinfo.getBabyDeathTimeHour() + "时" : "")
                                + (babyinfo.getBabyDeathTimeMinutes() != null ? babyinfo.getBabyDeathTimeMinutes() + "分" : "")
                        : "";
                if (baby23.length() >= 10) {
                    baby23 = baby23.substring(0, 10);
                }
                childbirthCell_baby23.setCellValue(baby23);
                cStep++;

                // 死亡原因
                SXSSFCell childbirthCell_baby24 = (SXSSFCell) row.createCell(newChildXpos1 + cStep);
                childbirthCell_baby24.setCellStyle(cellStyle);
                String baby24 = (babyinfo != null && babyinfo.getBabyAmnioticFluidDeathReason() != null)
                        ? babyinfo.getBabyAmnioticFluidDeathReason().toString()
                        : "";
                childbirthCell_baby24.setCellValue(baby24);
                cStep++;

                if (babyinfo != null && babyinfo.getBabyIsDeath() != null && babyinfo.getBabyIsDeath() != 1) {
                    childbirthCell_baby22.setCellValue("");
                    childbirthCell_baby23.setCellValue("");
                    childbirthCell_baby24.setCellValue("");
                }
            }
        }
    }

    /**
     * 
     * 生成孕期医嘱内容
     * 
     * @author zj
     * @param excelPojo
     * @param pregExamPosi
     * @param row
     * @param cellStyle
     * @param doctorAdviceXpos
     */
    public static void pregDocAdvice(StatisticDataPojo excelPojo, int pregExamPosi, SXSSFRow row,
            XSSFCellStyle cellStyle, int doctorAdviceXpos) {
        int clumnNo = 0;
        // 本次营养制剂
        SXSSFCell childbirthCellAdv_1 = (SXSSFCell) row.createCell(doctorAdviceXpos + clumnNo++);
        childbirthCellAdv_1.setCellStyle(cellStyle);
        String advValue1 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getCurrentProductNames()))
                ? excelPojo.getDiagnosisPojo().getCurrentProductNames().toString()
                : "";
        childbirthCellAdv_1.setCellValue(advValue1);
        // 新增营养制剂
        SXSSFCell childbirthCellAdv_2 = (SXSSFCell) row.createCell(doctorAdviceXpos + clumnNo++);
        childbirthCellAdv_2.setCellStyle(cellStyle);
        String advValue2 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getIncreasedProductNames()))
                ? excelPojo.getDiagnosisPojo().getIncreasedProductNames().toString()
                : "";
        childbirthCellAdv_2.setCellValue(advValue2);
        // 停服营养制剂
        SXSSFCell childbirthCellAdv_3 = (SXSSFCell) row.createCell(doctorAdviceXpos + clumnNo++);
        childbirthCellAdv_3.setCellStyle(cellStyle);
        String advValue3 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getStopProductNames()))
                ? excelPojo.getDiagnosisPojo().getStopProductNames().toString()
                : "";
        childbirthCellAdv_3.setCellValue(advValue3);
    }

    /**
     * 
     * 出入院情况输出
     * 
     * @author zj
     * @param excelPojo
     * @param pregExamPosi
     * @param row
     * @param cellStyle
     * @param inOutHosXpos
     */
    public static void inOutHospital(StatisticDataPojo excelPojo, int pregExamPosi, SXSSFRow row,
            XSSFCellStyle cellStyle, int inOutHosXpos, int babyMaxNum) {
        int clumnNo = 0;
        // 入院诊断
        SXSSFCell childbirthCellAdv_1 = (SXSSFCell) row.createCell(inOutHosXpos + clumnNo++);
        childbirthCellAdv_1.setCellStyle(cellStyle);
        String advValue1 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthDiagnosis()))
                ? excelPojo.getBirthEndingPojo().getBirthDiagnosis().toString()
                : "";
        childbirthCellAdv_1.setCellValue(advValue1);

        // 入院诊断（备注）
        SXSSFCell childbirthCellAdv_2 = (SXSSFCell) row.createCell(inOutHosXpos + clumnNo++);
        childbirthCellAdv_2.setCellStyle(cellStyle);
        String advValue2 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthDiagRemark()))
                ? excelPojo.getBirthEndingPojo().getBirthDiagRemark().toString()
                : "";
        childbirthCellAdv_2.setCellValue(advValue2);

        // 出院诊断母
        SXSSFCell childbirthCellAdv_3 = (SXSSFCell) row.createCell(inOutHosXpos + clumnNo++);
        childbirthCellAdv_3.setCellStyle(cellStyle);
        String advValue3 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getDisMotherDisagnosis()))
                ? excelPojo.getBirthEndingPojo().getDisMotherDisagnosis().toString()
                : "";
        childbirthCellAdv_3.setCellValue(advValue3);

        for (int i = 0; i < babyMaxNum * 2; i++) {
            SXSSFCell childbirthCell_baby = (SXSSFCell) row.createCell(inOutHosXpos + 3 + i);
            childbirthCell_baby.setCellStyle(cellStyle);
        }
        if (excelPojo.getBirthEndingPojo() != null
                && !CollectionUtils.isEmpty(excelPojo.getBirthEndingPojo().getBabyList())) {
            for (BirthEndingBabyInfoPojo babyinfo : excelPojo.getBirthEndingPojo().getBabyList()) {
                // 出院诊断婴儿
                SXSSFCell childbirthCellAdv_4 = (SXSSFCell) row.createCell(inOutHosXpos + clumnNo++);
                childbirthCellAdv_4.setCellStyle(cellStyle);
                String advValue4 = StringUtils.isEmpty(babyinfo.getDisBabyDiagnosis())
                        ? ""
                        : babyinfo.getDisBabyDiagnosis().toString();
                childbirthCellAdv_4.setCellValue(advValue4);
                // 新生儿血糖/mg/dl
                SXSSFCell childbirthCellAdv_5 = (SXSSFCell) row.createCell(inOutHosXpos + clumnNo++);
                childbirthCellAdv_5.setCellStyle(cellStyle);
                String advValue5 = StringUtils.isEmpty(babyinfo.getDisBabyBloodSuger())
                        ? ""
                        : babyinfo.getDisBabyBloodSuger().toString();
                childbirthCellAdv_5.setCellValue(advValue5);
            }
        }

        // 产后血红蛋白/g/L
        SXSSFCell childbirthCellAdv_6 = (SXSSFCell) row.createCell(inOutHosXpos + 3 + babyMaxNum * 2);
        childbirthCellAdv_6.setCellStyle(cellStyle);
        String advValue6 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getDisHemoglobin()))
                ? excelPojo.getBirthEndingPojo().getDisHemoglobin().toString()
                : "";
        childbirthCellAdv_6.setCellValue(advValue6);

        // 出院诊断（备注）
        SXSSFCell childbirthCellAdv_7 = (SXSSFCell) row.createCell(inOutHosXpos + 4 + babyMaxNum * 2);
        childbirthCellAdv_7.setCellStyle(cellStyle);
        String advValue7 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getDisRemark()))
                ? excelPojo.getBirthEndingPojo().getDisRemark().toString()
                : "";
        childbirthCellAdv_7.setCellValue(advValue7);
    }

}
