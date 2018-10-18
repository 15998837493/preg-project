/*
 * ExportExcel.java    1.0  2018-8-13
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.statistic.excel;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mnt.preg.statistic.condition.SearchCountCondition;
import com.mnt.preg.statistic.pojo.StatisticDataPojo;
import com.mnt.preg.system.pojo.CodePojo;

/**
 * ExportExcel
 * 
 * @author zj
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-8-13 zj 初版
 */
public class ExportExcel {

    public static Map<String, CodePojo> dictMap = null;

    /**
     * 
     * 导出统计表excel接口
     *
     * @author zj
     * @param excelPojoList
     * @param xlsPath
     * @return
     * @throws Exception
     */
    public static byte[] loadExcel(List<StatisticDataPojo> excelPojoList, String xlsPath, SearchCountCondition condition,
            Map<String, CodePojo> dict)
            throws Exception {
        dictMap = dict;
        FileInputStream fileIn = new FileInputStream(xlsPath);
        // 根据指定的文件输入流导入Excel从而产生Workbook对象
        Workbook wb = new XSSFWorkbook(fileIn);

        // 获取样式对象
        XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
        // 设置样式对象，这里仅设置了边框属性
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设为右对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
        // cellStyle.setWrapText(true);

        XSSFCellStyle cellStyle1 = (XSSFCellStyle) wb.createCellStyle();
        // 设置样式对象，这里仅设置了边框属性
        cellStyle1.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle1.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle1.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle1.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle1.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 设为右对齐
        cellStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直

        // 新建font实体
        XSSFFont hssfFont = (XSSFFont) wb.createFont();
        // 设置字体颜色
        hssfFont.setColor(HSSFColor.RED.index);
        // 设置删除线 strikeout（删除线）
        hssfFont.setStrikeout(false);
        // 设置是否斜体
        hssfFont.setItalic(false);
        // 字体大小
        hssfFont.setFontHeightInPoints((short) 11);
        // 粗体
        hssfFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        hssfFont.setFontName("宋体");
        cellStyle1.setFont(hssfFont);

        String[] conditionArray = condition.getConditions().split(",");
        SXSSFWorkbook wbs = null;
        for (int j = 0; j < conditionArray.length; j++) {
            // 获取Excel文档中的sheet
            XSSFSheet sheet = ((XSSFWorkbook) wb).getSheetAt(Integer.parseInt(conditionArray[j]) - 1);
            wbs = createTableContent(wb, wbs, sheet, excelPojoList, cellStyle, cellStyle1,
                    Integer.parseInt(conditionArray[j]) - 1, condition, dict);
        }
        for (int i = 4; i >= 0; i--) {
            Boolean hasTag = false;
            for (int j = 0; j < conditionArray.length; j++) {
                if (String.valueOf(i + 1).equals(conditionArray[j])) {
                    hasTag = true;
                    break;
                }
            }
            if (!hasTag) {
                wbs.removeSheetAt(i);
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            wbs.write(out);
            // wbs.close();
            out.close();
        } catch (IOException ae) {
            ae.printStackTrace();
        }
        return out.toByteArray();

    }

    /**
     * 
     * [导出excel具体方法]
     *
     * @author lipeng
     * @param wb
     * @param wbs
     * @param sheet
     * @param excelPojoList
     * @param cellStyle
     * @param cellStyle1
     * @param order
     * @param condition
     * @param dict
     * @return
     * @throws Exception
     */
    private static SXSSFWorkbook createTableContent(Workbook wb, SXSSFWorkbook wbs, XSSFSheet sheet,
            List<StatisticDataPojo> excelPojoList,
            XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1, int order, SearchCountCondition condition,
            Map<String, CodePojo> dict)
            throws Exception {
        // 整体实际用的SXSSFWorkbook，总导出表因为用了模板，所以先用XSSFWorkbook 修改表头，然后再转换成SXSSFWorkbook
        if (order == 0) {
            // 导出 总表
            wbs = GenralSheet.generalTable(wb, sheet, excelPojoList, cellStyle, cellStyle1, order, condition);

        } else if (order == 1) {

        } else if (order == 2) {

        } else if (order == 3) {
            if (wbs == null) {
                wbs = new SXSSFWorkbook((XSSFWorkbook) wb);
            }
            // 营养制剂导出表
            NutrientSheet.nutrientTable(wbs, (SXSSFSheet) wbs.getSheetAt(3), excelPojoList, cellStyle, cellStyle1, order,
                    condition);
        } else {
            if (wbs == null) {
                wbs = new SXSSFWorkbook((XSSFWorkbook) wb);
            }
            // 分娩结局导出表
            BirthEndingInfoSheet.birthEndingInfoTable(wbs, (SXSSFSheet) wbs.getSheetAt(4), excelPojoList, cellStyle, cellStyle1,
                    order, condition);
        }
        return wbs;

    }

}
