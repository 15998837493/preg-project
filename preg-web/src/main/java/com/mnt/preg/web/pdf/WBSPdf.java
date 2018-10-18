/*
 * WBSPdf.java    1.0  2018-1-12
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 长沙市妇幼保健院体重监测、血糖监测表
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-12 scd 初版
 */
@Component
public class WBSPdf extends AbstractPdf<Map<String, String>> {

    private String titleContent = "☆周二早上空腹7：30营养门诊监测血糖，10：00到产科5号房孕妇学校上课。\n" +
            "☆①周三早上7:30空腹到；②带水杯；③穿宽松的裤子；④到产科门诊5号房孕妇学校参加一日门诊；\n" +
            "注：①周二上课早餐后2小时到孕妇学校测血糖 ②中餐后2小时（两点半）到26号营养门诊测血糖③晚餐后2小" +
            "时到住院部五楼护士站测血糖④餐后血糖都是从进食第一口开始计时，数2小时准点测血糖⑤每周称一次体重、" +
            "每周选一天监测一天血糖并记录（特殊情况除外）";

    @Override
    public void handler(Map<String, String> pdfData) throws DocumentException {
        // 设置报告头
        addContentTableHead0(utils.createTable(1, Element.ALIGN_RIGHT, 100f, 0, 0), pdfData);
        // 信息栏
        float[] table0Width = new float[] {0.25f, 0.25f, 0.25f, 0.25f};
        addContentTable0(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 7f, 0));// 添加内容
        // 表格
        float[] table2Width = new float[] {0.1f, 0.1f, 0.35f, 0.35f, 0.1f};
        addContentTable1(utils.createTable(table2Width, Element.ALIGN_CENTER, 100f, 7f, 0));// 添加内容
    }

    /***************************************************** 填充内容 *****************************************************************/
    private void addContentTableHead0(PdfPTable table, Map<String, String> pdfData) throws DocumentException {
        // 添加图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + pdfData.get("insId") + PublicConstant.logo1;
        File file = new File(basepath);
        if (file.exists()) {
            Image img;
            PdfPCell cellImg = utils.baseCell("", new Font(utils.createFont(), 20, Font.NORMAL,
                    utils.darkGrayColor));
            try {
                img = Image.getInstance(basepath);
                cellImg.setImage(img);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cellImg.setBorderColor(utils.whiteColor);
            cellImg.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellImg.setFixedHeight(40f);
            cellImg.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.addCell(cellImg);
        }

        // 添加标题
        PdfPCell cell = utils.baseCell("体重监测、血糖监测表", new Font(utils.createFont(), 20, Font.NORMAL,
                utils.darkGrayColor));
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(40f);
        table.addCell(cell);
        table.setSpacingAfter(5f);
        document.add(table);
    }

    private void addContentTable0(PdfPTable table) throws DocumentException {

        table.addCell(infoCell("姓名："));
        table.addCell(infoCell("年龄："));
        table.addCell(infoCell("末次月经："));
        table.addCell(infoCell("孕前体重及身高："));
        table.addCell(infoCell("录入时间："));
        table.addCell(infoCell("体重："));
        table.addCell(infoCell("筛查时间："));
        table.addCell(infoCell("结果："));
        document.add(table);
    }

    private void addContentTable11(PdfPTable table) throws DocumentException {
        PdfPCell cell = utils.baseCell(titleContent, utils.contentFont);
        cell.setFixedHeight(0);
        cell.setLeading(0, 1.5f);// 设置行间距
        cell.setPaddingBottom(6f);// 设置下边距
        cell.setColspan(5);
        table.addCell(cell);
    }

    private void addContentTable1(PdfPTable table) throws DocumentException {
        addContentTable11(table);
        table.addCell(tableTitleCell("监测日期", PdfPCell.ALIGN_CENTER));
        table.addCell(tableTitleCell("体重（kg)", PdfPCell.ALIGN_CENTER));
        table.addCell(tableTitleCell("血糖", PdfPCell.ALIGN_LEFT));
        table.addCell(tableTitleCell("监测时间", PdfPCell.ALIGN_LEFT));
        table.addCell(tableTitleCell("备注", PdfPCell.ALIGN_CENTER));

        for (int i = 0; i < 14; i++) {
            table.addCell(tableInfoCell("", new int[] {2}));
            table.addCell(tableInfoCell("", new int[] {2}));
            table.addCell(tableInfoCell("空腹", new int[] {2, 4}));
            table.addCell(tableInfoCell("早餐2h", new int[] {2, 3}));
            table.addCell(tableInfoCell("", new int[] {2}));

            table.addCell(tableInfoCell("", new int[] {1}));
            table.addCell(tableInfoCell("", new int[] {1}));
            table.addCell(tableInfoCell("中餐2h", new int[] {1, 4}));
            table.addCell(tableInfoCell("晚餐2h", new int[] {1, 3}));
            table.addCell(tableInfoCell("", new int[] {1}));
        }
        document.add(table);
    }

    /***************************************************** 工具方法 *****************************************************************/
    public PdfPCell infoCell(String content) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 9, Font.NORMAL,
                utils.darkGrayColor));
        cell.setBorderColor(utils.whiteColor);
        return cell;
    }

    private PdfPCell tableTitleCell(String content, int align) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setLeading(0, 1.5f);// 设置行间距
        cell.setPaddingBottom(6f);// 设置下边距
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setHorizontalAlignment(align);// 居左
        return cell;
    }

    /**
     * 
     * 去掉边框 1.去掉上边框 2.去掉下边框 3.去掉左边边框 4.去掉有边框
     * 
     * @param content
     * @param border
     * @return
     */
    private PdfPCell tableInfoCell(String content, int[] border) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        for (int i = 0; i < border.length; i++) {
            if (border[i] == 1) {
                cell.setBorderWidthTop(0);
            }
            if (border[i] == 2) {
                cell.setBorderWidthBottom(0);
            }
            if (border[i] == 3) {
                cell.setBorderWidthLeft(0);
            }
            if (border[i] == 4) {
                cell.setBorderWidthRight(0);
            }
        }
        return cell;
    }
}
