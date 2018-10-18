/*
 * NutrientSheet.java    1.0  2018年8月17日
 *
 * 北京麦芽健康管理有限公司
 *
 */

package com.mnt.preg.statistic.excel;

import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.pojo.BirthEndingBabyInfoPojo;
import com.mnt.preg.statistic.condition.SearchCountCondition;
import com.mnt.preg.statistic.pojo.StatisticDataPojo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 营养制剂Sheet
 *
 * @author lgp
 * @version 1.0
 *          <p>
 *          变更履历：
 *          v1.0 2018年8月21日 lgp 初版
 */
public class NutrientSheet {

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
    public static void nutrientTable(Workbook wb, SXSSFSheet sheet, List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, int order, SearchCountCondition condition) {
        // ----------------------------第一行表头begin---------------------------------
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
        // ----------------------------第一行表头end---------------------------------
        // ----------------------------第二行表头begin---------------------------------
        SXSSFRow row = (SXSSFRow) sheet.createRow(1);
        // 基础信息
        int baseinfoPos = 12;
        if (condition.getStage() == 1) {
            baseinfoPos = 12;
        }
        drawTitleTogeter(wb, sheet, row, cellStyle, "基础信息", 1, 1, 0, baseinfoPos);

        // 接诊信息
        int jieZhenPos = baseinfoPos + 5;// 接诊结束坐标
        // 接诊信息
        drawTitleTogeter(wb, sheet, row, cellStyle1, "接诊信息", 1, 1, baseinfoPos + 1, jieZhenPos);

        // 孕期检验记录
        int yunJianCounts = 0;// 孕检动态量
        if (condition.getItemsName() != null && !"".equals(condition.getItemsName())) {
            yunJianCounts = condition.getItemsName().split(",").length;
        }
        int yunJianPos = jieZhenPos + 2 + yunJianCounts * 2;// 孕检结束坐标
        drawTitleTogeter(wb, sheet, row, cellStyle1, "孕期检验记录", 1, 1, jieZhenPos + 1, yunJianPos);

        // 营养处方信息
        int chuFangCounts = 0;// 营养处方动态量
        chuFangCounts = condition.getProductNumMax();
        int chuFangPos = yunJianPos;// 处方结束坐标
        if (chuFangCounts != 0) {
            chuFangPos = yunJianPos + chuFangCounts * 3;
            drawTitleTogeter(wb, sheet, row, cellStyle, "营养处方信息", 1, 1, yunJianPos + 1, chuFangPos);
        }

        // 分娩结局信息
        int babyNumMax = 0;
        if (condition.getStage() == 2) {
            if (condition.getBabyNumMax() != null) {
                babyNumMax = condition.getBabyNumMax();
            }
            int fenMianPos = chuFangPos + babyNumMax * 18 + 13;// 分娩结束坐标
            drawTitleTogeter(wb, sheet, row, cellStyle1, "分娩结局信息", 1, 1, chuFangPos + 1, fenMianPos);
        }
        // ----------------------------第二行表头end---------------------------------

        // ----------------------------第三行表头begin---------------------------------
        SXSSFRow row1 = (SXSSFRow) sheet.createRow(2);
        // 定义的游标
        int index = 0;
        // 基础信息子项
        drawTitle(row1, cellStyle, "序号", index++);
        drawTitle(row1, cellStyle1, "病案号", index++);
        drawTitle(row1, cellStyle1, "ID", index++);
        drawTitle(row1, cellStyle1, "姓名", index++);
        drawTitle(row1, cellStyle1, "年龄", index++);
        drawTitle(row1, cellStyle1, "身高", index++);
        drawTitle(row1, cellStyle1, "胎数", index++);
        drawTitle(row1, cellStyle1, "受孕方式", index++);
        if (condition.getStage() == 1) {
            drawTitle(row1, cellStyle1, "预产期", index++);
        }
        drawTitle(row1, cellStyle1, "孕前体重", index++);
        drawTitle(row1, cellStyle1, "孕前BMI", index++);
        drawTitle(row1, cellStyle1, "孕次", index++);
        drawTitle(row1, cellStyle1, "产次", index++);
        if (condition.getStage() == 2){
            drawTitle(row1, cellStyle1, "备注", index++);
        }

        // 接诊信息子项
        // 产科医师加单独的样式
        XSSFCellStyle styleOnly = (XSSFCellStyle) wb.createCellStyle();
        styleOnly.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleOnly.setFillPattern(CellStyle.SOLID_FOREGROUND);

        drawTitle(row1, cellStyle1, "营养就诊次数", index++);
        drawTitle(row1, cellStyle1, "营养门诊接诊时间", index++);
        drawTitle(row1, cellStyle1, "营养门诊", index++);
        drawTitle(row1, cellStyle1, "营养医师", index++);
        drawTitle(row1, styleOnly, "产科医师", index++);

        // 孕期检验记录子项
        drawTitle(row1, cellStyle1, "本次诊断", index++);
        drawTitle(row1, cellStyle, "风险级别", index++);
        for (int i = 0; i < yunJianCounts; i++) {
            String[] jianYan = condition.getItemsName().split(",");
            // 检验时间
            drawTitle(row1, cellStyle1, jianYan[i] + "检查时间", index++);
            // 检验项目
            drawTitle(row1, cellStyle1, jianYan[i], index++);
        }

        // 营养处方信息子项
        for (int i = 1; i <= chuFangCounts; i++) {
            drawTitle(row1, cellStyle, "营养制剂" + i, index++);
            drawTitle(row1, cellStyle, "频次", index++);
            drawTitle(row1, cellStyle, "单次剂量", index++);
        }

        if (condition.getStage() == 2) {// 1、妊娠 2、产后
            // 分娩结局信息子项
            drawTitle(row1, cellStyle1, "分娩日期", index++);
            drawTitle(row1, cellStyle1, "分娩孕周数", index++);
            drawTitle(row1, cellStyle1, "分娩方式", index++);
            drawTitle(row1, cellStyle1, "会阴情况", index++);
            drawTitle(row1, cellStyle1, "分娩备注", index++);
            for (int i = 1; i <= babyNumMax; i++) {
                drawTitle(row1, cellStyle1, "新生儿性别(" + i + ")", index++);
                drawTitle(row1, cellStyle1, "新生儿体重/g", index++);
                drawTitle(row1, cellStyle1, "新生儿身长/cm", index++);
                drawTitle(row1, cellStyle1, "胎儿窒息min", index++);
                drawTitle(row1, cellStyle1, "阿氏评分1分钟", index++);
                drawTitle(row1, cellStyle1, "阿氏评分5分钟", index++);
                drawTitle(row1, cellStyle1, "胎盘重g", index++);
                drawTitle(row1, cellStyle1, "胎盘长cm", index++);
                drawTitle(row1, cellStyle1, "胎盘宽cm", index++);
                drawTitle(row1, cellStyle1, "胎盘厚cm", index++);
                drawTitle(row1, cellStyle1, "羊水量", index++);
                drawTitle(row1, cellStyle1, "羊水性状", index++);
                drawTitle(row1, cellStyle1, "新生儿备注（" + i + "）", index++);
                drawTitle(row1, cellStyle1, "新生儿死亡(" + i + ")", index++);
                drawTitle(row1, cellStyle1, "死亡时间", index++);
                drawTitle(row1, cellStyle1, "死亡原因", index++);
            }
            drawTitle(row1, cellStyle1, "孕期总体重增长/kg", index++);
            drawTitle(row1, cellStyle1, "分娩前体重/kg", index++);
            drawTitle(row1, cellStyle1, "分娩后体重/kg", index++);
            drawTitle(row1, cellStyle1, "入院诊断", index++);
            drawTitle(row1, cellStyle1, "入院诊断备注", index++);
            drawTitle(row1, cellStyle1, "出院诊断/母", index++);
            for (int i = 1; i <= babyNumMax; i++) {
                drawTitle(row1, cellStyle1, "出院诊断/婴儿(" + i + ")", index++);
                drawTitle(row1, cellStyle1, "新生儿血糖/mg/dl(" + i + ")", index++);
            }
            drawTitle(row1, cellStyle1, "产后血红蛋白/g/L", index++);
            drawTitle(row1, cellStyle1, "出院诊断备注", index++);
        }
        // ----------------------------第三行表头end---------------------------------
        // 冻结表头
        sheet.createFreezePane(4, 3);
        // ----------------------------动态生成表内容---------------------------------

        nutrientTableContent(sheet, excelPojoList, cellStyle, cellStyle1, baseinfoPos, chuFangPos, condition);
    }

    /**
     * 动态生成营养制剂表内容
     */
    public static void nutrientTableContent(SXSSFSheet sheet, List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, int baseinfoPos, int chuFangPos, SearchCountCondition condition) {
        // 数据行
        int rowIndexBegin = 3;
        int lineNo = 0; // 第一列的序号

        if (CollectionUtils.isEmpty(excelPojoList)) {
            return;
        }
        // 遍历数据
        for (StatisticDataPojo excelPoje : excelPojoList) {
            // 创建行
            SXSSFRow row = (SXSSFRow) sheet.createRow(rowIndexBegin);
            // int clumnNo = 1;
            // 序号
            SXSSFCell cell0 = (SXSSFCell) row.createCell(0);
            cell0.setCellStyle(cellStyle);
            cell0.setCellValue(String.valueOf(++lineNo));
            // 基础信息
            pregBaseInfo(excelPoje, row, cellStyle, 1, condition);
            // 接诊内容
            pregReciveDiag(excelPoje, row, cellStyle, baseinfoPos + 1, condition);
            // 分娩结局信息
            if (condition.getStage() == 2) {
                pregChildBirth(excelPoje, row, cellStyle, chuFangPos + 1, condition);
            }
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
    public static void pregBaseInfo(StatisticDataPojo excelPojo, SXSSFRow row, XSSFCellStyle cellStyle, int startCol,
            SearchCountCondition condition) {
        // 列游标
        int col = startCol;
        // 病案号
        String value1 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustSikeId()))
                        ? excelPojo.getCustomerInfoPojo().getCustSikeId().toString() : "";
        drawContent(row, cellStyle, value1, col++);
        // ID
        String value2 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustPatientId()))
                        ? excelPojo.getCustomerInfoPojo().getCustPatientId().toString() : "";
        drawContent(row, cellStyle, value2, col++);
        // 姓名
        String value3 = (excelPojo.getCustomerInfoPojo() != null
                && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustName()))
                        ? excelPojo.getCustomerInfoPojo().getCustName().toString() : "";
        drawContent(row, cellStyle, value3, col++);
        // 妊娠阶段
        if (condition.getStage() == 1) {
            // 年龄
            String value4 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustAge()))
                            ? excelPojo.getCustomerInfoPojo().getCustAge().toString() : "";
            drawContent(row, cellStyle, value4, col++);
            // 身高
            String value5 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getCustHeight()))
                            ? excelPojo.getCustomerInfoPojo().getCustHeight().toString() : "";
            drawContent(row, cellStyle, value5, col++);
            // 胎数
            String value6 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getEmbryoNum()))
                            ? excelPojo.getCustomerInfoPojo().getEmbryoNum().toString() : "";
            drawContent(row, cellStyle, value6, col++);
            // 受孕方式
            String value7 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getEncyesisSituation()))
                            ? excelPojo.getCustomerInfoPojo().getEncyesisSituation().toString() : "";
            drawContent(row, cellStyle, value7, col++);
            // 预产期--建档信息
            String value8 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getPregnancyDueDate()))
                            ? excelPojo.getCustomerInfoPojo().getPregnancyDueDate().toString() : "";
            drawContent(row, cellStyle, value8, col++);
            // 孕前体重
            String value9 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getWeight()))
                            ? excelPojo.getCustomerInfoPojo().getWeight().toString() : "";
            drawContent(row, cellStyle, value9, col++);
            // 孕前BMI
            String value10 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getBmi()))
                            ? excelPojo.getCustomerInfoPojo().getBmi().toString() : "";
            drawContent(row, cellStyle, value10, col++);
            // 孕次
            String value11 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getPregnancyNum()))
                            ? excelPojo.getCustomerInfoPojo().getPregnancyNum().toString() : "";
            drawContent(row, cellStyle, value11, col++);
            // 产次
            String value12 = (excelPojo.getCustomerInfoPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getCustomerInfoPojo().getBirthNum()))
                            ? excelPojo.getCustomerInfoPojo().getBirthNum().toString() : "";
            drawContent(row, cellStyle, value12, col++);
        }
        // 产后阶段
        if (condition.getStage() == 2) {
            // 年龄--分娩结局
            String value4 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getCustAge()))
                            ? excelPojo.getBirthEndingPojo().getCustAge().toString() : "";
            drawContent(row, cellStyle, value4, col++);
            // 身高--分娩结局
            String value5 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getCustHeight()))
                            ? excelPojo.getBirthEndingPojo().getCustHeight().toString() : "";
            drawContent(row, cellStyle, value5, col++);
            // 胎数--分娩结局
            String value6 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getEmbryoNum()))
                            ? excelPojo.getBirthEndingPojo().getEmbryoNum().toString() : "";
            drawContent(row, cellStyle, value6, col++);
            // 受孕方式--分娩结局
            String value7 = "";
            if (excelPojo.getBirthEndingPojo() != null && excelPojo.getBirthEndingPojo().getBirthTiresType() != null) {
                int flag = excelPojo.getBirthEndingPojo().getBirthTiresType();
                if (flag == 1) {
                    value7 = "自然受孕";
                } else {
                    value7 = "辅助生殖";
                }
            }
            if (excelPojo.getBirthEndingPojo() != null && excelPojo.getBirthEndingPojo().getEncyesisSituation() != null) {
                int flag = excelPojo.getBirthEndingPojo().getEncyesisSituation();
                if (flag == 1) {
                    value7 = value7 + " 意外妊娠";
                } else {
                    value7 = value7 + " 计划妊娠";
                }
            }
            drawContent(row, cellStyle, value7, col++);
            // 孕前体重--分娩结局
            String value9 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getWeight()))
                            ? excelPojo.getBirthEndingPojo().getWeight().toString() : "";
            drawContent(row, cellStyle, value9, col++);
            // 孕前BMI--分娩结局
            String value10 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBmi()))
                            ? excelPojo.getBirthEndingPojo().getBmi().toString() : "";
            drawContent(row, cellStyle, value10, col++);
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
            // 备注
            String value13 = (excelPojo.getBirthEndingPojo() != null
                    && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthBaseRemark()))
                    ? excelPojo.getBirthEndingPojo().getBirthBaseRemark().toString() : "";
            drawContent(row, cellStyle, value13, col++);
        }
    }

    /**
     * 诊断信息
     *
     * @param excelPojo
     * @param row
     * @param cellStyle
     * @param startCol
     */
    public static void pregReciveDiag(StatisticDataPojo excelPojo, SXSSFRow row, XSSFCellStyle cellStyle, int startCol,
            SearchCountCondition condition) {
        // 最大营养处方数
        int chuFangNum = condition.getProductNumMax();
        // 营养就诊次数
        String value1 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisCount()))
                        ? excelPojo.getDiagnosisPojo().getDiagnosisCount().toString() : "";
        drawContent(row, cellStyle, value1, startCol++);
        // 营养门诊接诊时间
        String value2 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisDate()))
                        ? JodaTimeTools.toString(excelPojo.getDiagnosisPojo().getDiagnosisDate(), "yyyy-MM-dd") : "";
        drawContent(row, cellStyle, value2, startCol++);
        // 营养门诊
        String value3 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisName()))
                        ? excelPojo.getDiagnosisPojo().getDiagnosisName().toString() : "";
        drawContent(row, cellStyle, value3, startCol++);
        // 营养门诊医生
        String value4 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisUserName()))
                        ? excelPojo.getDiagnosisPojo().getDiagnosisUserName().toString() : "";
        drawContent(row, cellStyle, value4, startCol++);
        // 产科医师
        String value5 = (excelPojo.getDiagnosisPojo() != null
                && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getDiagnosisReferralDoctorName()))
                        ? excelPojo.getDiagnosisPojo().getDiagnosisReferralDoctorName().toString() : "";
        drawContent(row, cellStyle, value5, startCol++);
        // 本次诊断
        String value6 = (excelPojo.getDiagnosisPojo() != null
                && excelPojo.getDiagnosisPojo().getDiseaseNames() != null)
                        ? excelPojo.getDiagnosisPojo().getDiseaseNames() : "";
        drawContent(row, cellStyle, value6, startCol++);
        // 风险级别
        String value7 = (excelPojo.getDiagnosisPojo() != null
                && excelPojo.getDiagnosisPojo().getGestationLevel() != null)
                        ? excelPojo.getDiagnosisPojo().getGestationLevel() : "";
        drawContent(row, cellStyle, value7, startCol++);
        // 检验项目
        String[] jianYanItems = {};
        if (condition.getItemsName() != null && !"".equals(condition.getItemsName())) {
            jianYanItems = condition.getItems().split(",");
        }
        for (int i = 0; i < jianYanItems.length; i++) {
            if (excelPojo.getDiagnosisPojo() != null && excelPojo.getDiagnosisPojo().getItems() != null) {
                boolean flag = false;//检查项目匹配成功的标记
                for (int j = 0; j < excelPojo.getDiagnosisPojo().getItems().size(); j++) {
                    if (jianYanItems[i].equals(excelPojo.getDiagnosisPojo().getItems().get(j).getItemId())) {
                        flag = true;
                        // 时间
                        String value0 = JodaTimeTools
                                .toString(excelPojo.getDiagnosisPojo().getItems().get(j).getReportDate(), "yyyy-MM-dd");
                        // 项目
                        String value = excelPojo.getDiagnosisPojo().getItems().get(j).getItemValue();
                        // 检查时间
                        drawContent(row, cellStyle, value0, startCol++);
                        // 检查项目
                        drawContent(row, cellStyle, value, startCol++);
                    }
                }
                if (!flag){
                    drawContent(row, cellStyle, "", startCol++);
                    drawContent(row, cellStyle, "", startCol++);
                }
            } else {
                drawContent(row, cellStyle, "", startCol++);
                drawContent(row, cellStyle, "", startCol++);
            }
        }
        // 营养处方
        // 获取当前对象的营养处方数量
        int chufangCount = 0;
        if (excelPojo.getDiagnosisPojo() != null && excelPojo.getDiagnosisPojo().getProductList() != null) {
            chufangCount = excelPojo.getDiagnosisPojo().getProductList().size();
        }
        if (chufangCount != 0) {
            // 绘制处方内容
            for (int i = 0; i < chufangCount; i++) {
                String productionName = (excelPojo.getDiagnosisPojo().getProductList().get(i).getProductName() != null
                        && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getProductList().get(i).getProductName()))
                                ? excelPojo.getDiagnosisPojo().getProductList().get(i).getProductName().toString() : "";
                String productFrequency = (excelPojo.getDiagnosisPojo().getProductList().get(i).getProductFrequency() != null
                        && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getProductList().get(i).getProductFrequency()))
                                ? excelPojo.getDiagnosisPojo().getProductList().get(i).getProductFrequency().toString() : "";
                String productDosage = (excelPojo.getDiagnosisPojo().getProductList().get(i).getProductDosage() != null
                        && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getProductList().get(i).getProductDosage()))
                                ? excelPojo.getDiagnosisPojo().getProductList().get(i).getProductDosage().toString() : "";
                String productUnit = (excelPojo.getDiagnosisPojo().getProductList().get(i).getProductUnit() != null
                        && !StringUtils.isEmpty(excelPojo.getDiagnosisPojo().getProductList().get(i).getProductUnit()))
                                ? excelPojo.getDiagnosisPojo().getProductList().get(i).getProductUnit().toString() : "";
                // 营养制剂
                drawContent(row, cellStyle, productionName, startCol++);
                // 频次
                drawContent(row, cellStyle, productFrequency, startCol++);
                // 剂量
                drawContent(row, cellStyle, productDosage + productUnit, startCol++);
            }
            // 绘制处方后面的空表格
            for (int i = 0; i < (chuFangNum - chufangCount) * 3; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
        } else {
            // 绘制空表格
            for (int i = 0; i < chuFangNum * 3; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
        }
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
        // 分娩日期
        String value1 = (excelPojo.getBirthEndingPojo() != null
                && excelPojo.getBirthEndingPojo().getBirthDate() != null)
                        ? JodaTimeTools.toString(excelPojo.getBirthEndingPojo().getBirthDate(), JodaTimeTools.FORMAT_6) : "";
        if (excelPojo.getBirthEndingPojo().getBaseTimeHour() != null){
            value1 = value1 + " " + excelPojo.getBirthEndingPojo().getBaseTimeHour().toString() + "时";
        }
        if (excelPojo.getBirthEndingPojo().getBaseTimeMinuters() != null){
            value1 = value1 + " " + excelPojo.getBirthEndingPojo().getBaseTimeMinuters().toString() + "分";
        }
        drawContent(row, cellStyle, value1, startCol++);
        // 分娩孕周数
        String value2 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBirthWeeks()))
                        ? excelPojo.getBirthEndingPojo().getBirthWeeks().toString() : "";
        drawContent(row, cellStyle, value2, startCol++);
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
        drawContent(row, cellStyle, cbValue10, startCol++);
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
        drawContent(row, cellStyle, cbValue24, startCol++);
        // 分娩备注
        String value3 = (excelPojo.getBirthEndingPojo() != null
                && !StringUtils.isEmpty(excelPojo.getBirthEndingPojo().getBaseRemark()))
                ? excelPojo.getBirthEndingPojo().getBaseRemark().toString() : "";
        drawContent(row, cellStyle, value3, startCol++);
        // -------新生儿信息
        if (excelPojo.getBirthEndingPojo() != null
                && !CollectionUtils.isEmpty(excelPojo.getBirthEndingPojo().getBabyList())) {
            for (BirthEndingBabyInfoPojo babyinfo : excelPojo.getBirthEndingPojo().getBabyList()) {
                // 新生儿性别
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
                // 新生儿备注
                String baby13 = (babyinfo != null && babyinfo.getBabyRemark() != null)
                        ? babyinfo.getBabyRemark().toString() : "";
                drawContent(row, cellStyle, baby13, startCol++);
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
                // 死亡时间
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
                // 死亡原因
                String baby23 = (babyinfo != null && babyinfo.getBabyAmnioticFluidDeathReason() != null)
                        ? babyinfo.getBabyAmnioticFluidDeathReason().toString()
                        : "";
                drawContent(row, cellStyle, baby23, startCol++);
            }
            // 婴儿数量不是最大，画空格
            for (int i = 0; i < (condition.getBabyNumMax() - excelPojo.getBirthEndingPojo().getBabyList().size()) * 16; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
        } else {
            // 没有婴儿信息，画空格
            for (int i = 0; i < condition.getBabyNumMax() * 16; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
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
        // 新生儿诊断和新生儿血糖
        if (excelPojo.getBirthEndingPojo() != null
                && !CollectionUtils.isEmpty(excelPojo.getBirthEndingPojo().getBabyList())) {
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
            // 婴儿数量不是最大，画空格
            for (int i = 0; i < (condition.getBabyNumMax() - excelPojo.getBirthEndingPojo().getBabyList().size()) * 2; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
        } else {
            // 没有婴儿信息，画空格
            for (int i = 0; i < condition.getBabyNumMax() * 2; i++) {
                drawContent(row, cellStyle, "", startCol++);
            }
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
        // 合并单元格
        // 参数说明：1：开始行 2：结束行 3：开始列 4：结束列
        CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, endRow, startCol, endCol);
        sheet.addMergedRegion(cellRangeAddress);
        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, wb); // 下边框
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, wb); // 左边框
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, wb); // 有边框
        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, wb); // 上边框
        // 创建单元格
        SXSSFCell cell = (SXSSFCell) row.createCell(startCol);
        cell.setCellValue(titleName);
        cell.setCellStyle(cellStyle);
    }
}
