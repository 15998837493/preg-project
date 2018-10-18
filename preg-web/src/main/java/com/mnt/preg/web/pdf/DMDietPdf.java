/*
 * DMDietPdf.java    1.0  2018-1-13
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 长沙市妇幼保健院妊娠糖尿病饮食记录表
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-13 scd 初版
 */
public class DMDietPdf extends AbstractPdf<Map<String, String>> {

    private String[] content = new String[] {" ", "空腹", "早餐", "加餐", "中餐", "加餐", "晚餐", "加餐"};

    private String info = "注：①估计油、盐的摄入量 ②运动的时间、运动方式 ③保健品名称及摄入量 ④请注明进食时间及进食量⑤不吃蛋糕、饼干、面包、粥、米粉、碱面、糯米类食物，饭后休息30分钟，散步30分钟。";

    @Override
    public void handler(Map<String, String> pdfData) throws DocumentException {
        // 设置报告头
        addContentTableHead0(utils.createTable(1, Element.ALIGN_RIGHT, 100f, 0, 0), pdfData);
        // 信息栏
        float[] table0Width = new float[] {0.3f, 0.7f};
        addContentTable0(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 7f, 0));// 添加内容
        // 表格
        float[] table1Width = new float[] {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f};
        addContentTable1(utils.createTable(table1Width, Element.ALIGN_CENTER, 100f, 7f, 0));// 添加内容
        // 表格
        float[] table2Width = new float[] {1f};
        addContentTable2(utils.createTable(table2Width, Element.ALIGN_CENTER, 100f, 7f, 0));// 添加内容
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
        PdfPCell cell = utils.baseCell("妊娠糖尿病饮食记录表", new Font(utils.createFont(), 20, Font.NORMAL,
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
        document.add(table);
    }

    private void addContentTable1(PdfPTable table) throws DocumentException {
        addContentTable11(table);
        addContentTable11(table);
        document.add(table);
    }

    private void addContentTable11(PdfPTable table) throws DocumentException {
        table.addCell(tableTitleCell("日期"));
        table.addCell(tableTitleCell("餐次"));
        table.addCell(tableTitleCell("主食"));
        table.addCell(tableTitleCell("肉类"));
        table.addCell(tableTitleCell("蔬菜"));

        table.addCell(tableTitleCell("水果"));
        table.addCell(tableTitleCell("奶类"));
        table.addCell(tableTitleCell("坚果"));
        table.addCell(tableTitleCell("血糖"));
        table.addCell(tableTitleCell("运动"));

        for (int i = 0; i <= content.length; i++) {
            if (i == 0) {
                table.addCell(tableInfoCell("", 1));
            } else if (i > 0 && i < content.length) {
                table.addCell(tableInfoCell(content[i], 0));
                table.addCell(tableInfoCell("", 0));
                table.addCell(tableInfoCell("", 0));
                table.addCell(tableInfoCell("", 0));

                table.addCell(tableInfoCell("", 0));
                table.addCell(tableInfoCell("", 0));
                table.addCell(tableInfoCell("", 0));
                table.addCell(tableInfoCell("", 0));
                table.addCell(tableInfoCell("", 0));
            }
        }

        table.addCell(tableInfoCell("油：                        盐：                               保健食品：", 2));
    }

    private void addContentTable2(PdfPTable table) throws DocumentException {
        table.addCell(tableEndCell(info));
        document.add(table);
    }

    /***************************************************** 工具方法 *****************************************************************/
    public PdfPCell infoCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setBorderColor(utils.whiteColor);
        return cell;
    }

    private PdfPCell tableTitleCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setLeading(0, 1.5f);// 设置行间距
        cell.setPaddingBottom(6f);// 设置下边距
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);// 居中
        return cell;
    }

    private PdfPCell tableInfoCell(String content, int type) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);// 居中
        if (type == 1) {
            cell.setRowspan(8);
        }
        if (type == 2) {
            cell.setColspan(9);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);// 居左
        }
        return cell;
    }

    private PdfPCell tableEndCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);// 居左
        cell.setLeading(0, 1.5f);// 设置行间距
        cell.setPaddingBottom(6f);// 设置下边距
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setBorderColor(utils.whiteColor);// 边颜色
        return cell;
    }
}
