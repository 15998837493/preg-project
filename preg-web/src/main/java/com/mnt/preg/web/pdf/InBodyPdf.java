
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregInBodyBcaPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 干预方案-PDF生成
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-10 zcq 初版
 */
@Component
public class InBodyPdf extends AbstractPdf<PregInBodyBcaPojo> {

    private Map<String, ExamItemPojo> examMap = new HashMap<String, ExamItemPojo>();

    /**
     * 设置人体成分报告内容
     * 
     * @author zcq
     * @param inBodyVo
     * @throws DocumentException
     */
    @Override
    public void handler(PregInBodyBcaPojo inBodyVo) throws DocumentException {
        try {
            examMap = inBodyVo.getExamMap();

            document.newPage();

            float[] titleWidth = {0.38f, 0.62f};
            addContentTableHead0(utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 40f, 0), inBodyVo);

            // 基本信息
            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 0f));
            float[] table1Width = new float[] {0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f};
            addContentTable(utils.createTable(table1Width, Element.ALIGN_LEFT, 100f, 11f, 0), inBodyVo);

            // 人体成分分析
            addTitleTable("人体成分分析", Element.ALIGN_LEFT, 66);// 添加标题
            addContentTable1(utils.createTable(new float[] {0.18f, 0.164f, 0.164f, 0.164f, 0.164f, 0.164f},
                    Element.ALIGN_LEFT, 66f, 11f, 0), inBodyVo);// 添加内容

            // 肌肉脂肪分析
            addTitleTable("肌肉脂肪分析", Element.ALIGN_LEFT, 66);// 添加标题
            addContentTable2(utils.createTable(new float[] {0.167f, 0.833f}, Element.ALIGN_LEFT, 66f, -50f, 0),
                    inBodyVo);//

            // 细胞外水分比率分析
            addTitleTable("细胞外水分比率分析", Element.ALIGN_LEFT, 66);// 添加标题
            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, -23f));
            addContentTable3(utils.createTable(new float[] {0.167f, 0.833f}, Element.ALIGN_LEFT, 66f, 11f, 0), inBodyVo);//

            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 32f));

            // 人体成分测试历史记录

            addTitleTable("人体成分测试历史变化记录", Element.ALIGN_LEFT, 66);// 添加标题
            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 3f));
            float[] tableWidth = new float[] {0.185f, 0.185f, 0.16f, 0.16f, 0.16f, 0.16f,};
            addContentTable4(utils.createTable(tableWidth, Element.ALIGN_LEFT, 66f, 10f, 0), inBodyVo);// 添加内容

            // --------------------------------------左右分界线------------------
            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, -695f));

            // 孕期体重增长范围
            addTitleTable("孕期体重增长范围", Element.ALIGN_RIGHT, 31);// 添加标题
            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 3f));
            addContentTable6(utils.createTable(20, Element.ALIGN_RIGHT, 30f, 9f, 0), inBodyVo);// 添加内容

            // 体重增长建议
            addTitleTable("体重增长建议", Element.ALIGN_RIGHT, 31);// 添加标题
            addContentTable7(10f, 384f, 17f, inBodyVo);// 添加内容

            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 6f));
            // 节段脂肪分析
            addTitleTable("节段脂肪分析", Element.ALIGN_RIGHT, 31);// 添加标题
            addContentTable8(10f, 384f, 20f, inBodyVo);//
            // 节段脂肪分析网格线s
            addContentTable11();

            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 2f));
            // 研究项目
            addTitleTable("研究项目", Element.ALIGN_RIGHT, 31);// 添加标题
            float[] tableWidth1 = new float[] {0.34f, 0.33f, 0.33f};
            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, -4f));
            addContentTable9(utils.createTable(tableWidth1, Element.ALIGN_RIGHT, 32f, 12f, 0));// 添加内容

            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, -5f));
            // 全身相位角
            addTitleTable("全身相位角", Element.ALIGN_RIGHT, 31);// 添加标题
            document.add(utils.createParagraph("φ(°)50kHz      |      " + examMap.get("BODY00045").getItemString(),
                    8, utils.darkGrayColor, 385f, 10f, 8f));

            // 体成分分析
            addTitleTable("体成分分析", Element.ALIGN_RIGHT, 31);// 添加标题
            addContentTable10(inBodyVo);

            // 签名信息
            // document.add(utils.createParagraph(
            // "时间：" + JodaTimeTools.toString(inBodyVo.getUserExamDate(), JodaTimeTools.FORMAT_2), 8,
            // utils.darkGrayColor, 0, 20f, 0));
            // document.add(utils.createParagraph("医师：", 8, utils.darkGrayColor, 460f, 0, 0));

            // 分界线
            setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
            // 页尾
            setPageLast(inBodyVo);

        } catch (DocumentException e) {
            throw new DocumentException(e);
        }
    }

    // ========================================设置填充内容==============================================

    private void addContentTableHead0(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {
        addImgLogo(table, inBodyVo);
        PdfPCell cell = utils.baseCell("孕期人体体成分分析报告", utils.reportFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightThiredRedColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(25f);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);
        table.setSpacingAfter(5f);
        document.add(table);
    }

    private void addContentTable(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {
        table.addCell(myCellRed("ID"));
        table.addCell(myCellWhite(inBodyVo.getCustomerVo().getCustPatientId()));
        table.addCell(myCellRed("姓名"));
        table.addCell(myCellWhite(inBodyVo.getCustomerVo().getCustName()));
        table.addCell(myCellRed("年龄"));
        table.addCell(myCellWhite(inBodyVo.getCustomerVo().getCustAge().toString()));
        table.addCell(myCellRed("孕前体重"));
        table.addCell(myCellWhite(inBodyVo.getCustomerVo().getCustWeight().toString() + "kg"));
        table.addCell(myCellRed("身高"));
        table.addCell(myCellWhite(inBodyVo.getCustomerVo().getCustHeight().toString() + "cm"));
        table.addCell(myCellRed("孕前BMI"));
        table.addCell(myCellWhite(inBodyVo.getCustomerVo().getCustBmi().toString()));
        table.addCell(myCellRed("孕周数"));
        table.addCell(myCellWhite(""));
        table.addCell(myCellRed("胎数"));
        table.addCell(myCellWhite(inBodyVo.getPregVo().getEmbryoNum()));
        getSupWeek(inBodyVo.getGestationalWeeks());
        document.add(table);
    }

    private void addTitleTable(String titleName, int align, float recent) throws DocumentException {
        PdfPCell cell;
        float[] titleWidth = null;
        if (align == Element.ALIGN_RIGHT) {
            titleWidth = new float[] {0.05f, 0.95f};
        } else {
            titleWidth = new float[] {0.026f, 0.95f};
        }
        PdfPTable titleTable = new PdfPTable(titleWidth);
        titleTable.setHorizontalAlignment(align);
        titleTable.setWidthPercentage(recent);
        titleTable.setSpacingAfter(5f);
        titleTable.addCell(titleCell(" "));
        cell = utils.baseCell(titleName, utils.infoFont);
        cell.setFixedHeight(18f);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(utils.whiteColor);
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    private void addContentTable1(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {

        PdfPCell cell;
        table.addCell(titleCell(" "));
        table.addCell(titleCell("测量值"));
        table.addCell(titleCell("身体总水分"));
        table.addCell(titleCell("肌肉量"));
        table.addCell(titleCell("去脂体重"));
        table.addCell(titleCell("体重"));
        // 身体总水分
        table.addCell(myCellWhiteDoubleContent("细胞内水分（L）", "细胞外水分（L）", 0));
        cell = myCellWhiteDoubleContent(examMap.get("BODY00001").getItemString(), examMap.get("BODY00002")
                .getItemString(), 1);
        cell.setBorderColor(utils.whiteColor);
        cell.setBackgroundColor(utils.lightPinkColor);
        table.addCell(cell);
        cell = myCell(examMap.get("BODY00003").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("BODY00004").getItemString() + "\n" + "("
                + examMap.get("BODY00004").getItemRefValue() + ")");
        cell.setRowspan(2);
        cell.setBorderWidthTop(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(Rectangle.NO_BORDER);
        cell.setBorderWidthLeft(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = myCell(examMap.get("BODY00005").getItemString() + "\n" + "("
                + examMap.get("BODY00005").getItemRefValue() + ")");
        cell.setRowspan(3);
        cell.setBorderWidthLeft(Rectangle.NO_BORDER);
        cell.setBorderWidthTop(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = myCell(examMap.get("BODY00006").getItemString() + "\n" + "("
                + examMap.get("BODY00006").getItemRefValue() + ")");
        cell.setRowspan(4);
        cell.setBorderWidthTop(Rectangle.NO_BORDER);
        cell.setBorderWidthLeft(Rectangle.NO_BORDER);
        table.addCell(cell);
        // 蛋白质
        table.addCell(myCell("蛋白质（kg）"));
        cell = myCell(examMap.get("BODY00007").getItemString() + "\n" + "("
                + examMap.get("BODY00007").getItemRefValue() + ")");
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setBorderColor(utils.whiteColor);
        table.addCell(cell);
        cell = myCell("");
        cell.setBorderWidthRight(Rectangle.NO_BORDER);
        cell.setBorderWidthTop(Rectangle.NO_BORDER);
        cell.setBorderWidthLeft(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(Rectangle.NO_BORDER);
        table.addCell(cell);
        // 无机盐
        table.addCell(myCell("无机盐（kg）"));
        cell = myCell(examMap.get("BODY00008").getItemString() + "\n" + "("
                + examMap.get("BODY00008").getItemRefValue() + ")");
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setBorderColor(utils.whiteColor);
        table.addCell(cell);
        cell = myCell("");
        cell.setColspan(2);
        cell.setBorderWidthRight(Rectangle.NO_BORDER);
        cell.setBorderWidthLeft(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(Rectangle.NO_BORDER);
        table.addCell(cell);
        // 体脂肪
        table.addCell(myCell("体脂肪（kg）"));
        cell = myCell(examMap.get("BODY00009").getItemString() + "\n" + "("
                + examMap.get("BODY00009").getItemRefValue() + ")");
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setBorderColor(utils.whiteColor);
        table.addCell(cell);
        cell = myCell("");
        cell.setColspan(3);
        cell.setBorderWidthRight(Rectangle.NO_BORDER);
        cell.setBorderWidthLeft(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);
    }

    private void addContentTable2(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {
        // 画线并标注
        setTable2Line(inBodyVo);
        // 添加表头
        float[] table2Widths_head = {0.168f, 0.211f, 0.136f, 0.485f};
        PdfPTable table2_head = utils.createTable(table2Widths_head, Element.ALIGN_LEFT, 66f, 0f, 0);
        table2_head.addCell(titleCell(" "));
        table2_head.addCell(titleCell("低标准"));
        table2_head.addCell(titleCell("标准"));
        table2_head.addCell(titleCell("超标准"));
        document.add(table2_head);

        // 坐标尺
        setTable2Rows(table, "体重（kg）");// 体重
        setTable2Rows(table, "骨骼肌（kg）");// 骨骼肌
        setTable2Rows(table, "体脂肪（kg）");// 体脂肪
        setTable2Rows(table, "体脂百分比（%）");// 体脂百分比
        document.add(table);

        float rowHeight = table.getRowHeight(3);
        setTable2SJB("55", 70, 15, -(rowHeight * 4f - 60f), rowHeight);
        setTable2SJB("70", 80, 10, 0, rowHeight);
        document.add(utils.createParagraph("40", 7, utils.lightGrayColor, 80f, 0, 0));
        for (int i = 0; i < 10; i++) {
            if (i >= 2) {
                document.add(utils.createParagraph(String.valueOf(100 + (i - 2) * 60), 7, utils.lightGrayColor,
                        101f + (i * 25f), 0, 0));
            } else {
                document.add(utils.createParagraph(String.valueOf(60 + i * 20), 7, utils.lightGrayColor,
                        104f + (i * 24f), 0, 0));
            }
        }
        document.add(utils.createParagraph("%", 7, utils.lightGrayColor, 105f + (10 * 25f), 0, rowHeight + 2f));
        setTable2SJBPerc("8.0", 13.0f, 5.0f, 0, rowHeight);

    }

    private void setTable2Line(PregInBodyBcaPojo inBodyVo) throws DocumentException {

        float[] widths = new float[4];
        // 体重
        Float weight = Float.parseFloat(examMap.get("BODY00014").getItemString());
        if (weight > 205) {
            weight = 205.0f;
        }
        widths[0] = (weight - 40f) * 1.67f;
        document.add(utils.createParagraph(examMap.get("BODY00010").getItemString(), 8, utils.darkGrayColor,
                70f + widths[0], 39f, -28f));
        if (weight == 205.0) {
            document.add(utils.createParagraph("(" + examMap.get("BODY00010").getItemString() + "%)", 7,
                    utils.lightPinkColor, 60f + widths[0], 36f, -36f));
        }
        // 骨骼肌
        Float muslue = Float.parseFloat(examMap.get("BODY00015").getItemString());
        if (muslue > 170) {
            muslue = 170f;
        }
        widths[1] = (muslue - 60f) * 2.51f;
        document.add(utils.createParagraph(examMap.get("BODY00011").getItemString() + "", 8,
                utils.darkGrayColor, 70f + widths[1], 62f, -49f));
        if (muslue == 170) {
            document.add(utils.createParagraph("(" + examMap.get("BODY00015").getItemString() + "%)", 7,
                    utils.lightPinkColor, 60f + widths[1], 57f, -57f));
        }
        // 体脂肪
        Float fat = Float.parseFloat(examMap.get("BODY00016").getItemString());
        if (fat > 520) {
            fat = 520f;
        }
        if (fat < 100) {
            widths[2] = (fat - 20) * 1.255f;
        } else {
            // 100.4是100之前线的长度
            widths[2] = 100.4f + (fat - 100) * 0.42f;
        }
        document.add(utils.createParagraph(examMap.get("BODY00012").getItemString() + "", 8,
                utils.darkGrayColor, 70f + widths[2], 83f, -70f));
        if (fat == 520) {
            document.add(utils.createParagraph("(" + examMap.get("BODY00016").getItemString() + "%)", 7,
                    utils.lightPinkColor, 60f + widths[2], 78f, -78f));
        }

        // 体脂百分比
        Float perc = Float.parseFloat(examMap.get("BODY00013").getItemString());
        if (perc > 58) {
            perc = 58f;
        }
        widths[3] = (perc - 3.0f) * 5;
        document.add(utils.createParagraph(examMap.get("BODY00013").getItemString() + "", 8,
                utils.darkGrayColor, 70f + widths[3], 104f, -139f));
        // 划线
        drawLineBatch(widths, 500f, 34f, 4, utils.brownColor);
    }

    private void setTable2Rows(PdfPTable table, String title) {
        PdfPCell cell;
        table.addCell(myCell(title));
        PdfPTable contentTable = utils.createTable(12, Element.ALIGN_CENTER, 50f, 0f, 0);
        cell = myCell("");
        cell.setBorderColor(utils.lightPinkColor);
        cell.setFixedHeight(25f);
        for (int i = 0; i < 12; i++) {
            contentTable.addCell(cell);
        }
        cell = new PdfPCell(contentTable);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);// 文本上下居中
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setPaddingLeft(2f);// 设置左边距
        cell.setPaddingRight(2f);// 设置右边距
        table.addCell(cell);
    }

    private void setTable2SJB(String init, int fisrt, int space, float before, float after)
            throws DocumentException
    {
        document.add(utils.createParagraph(init, 7, utils.lightGrayColor, 80f, before, 0));
        for (int i = 0; i < 10; i++) {
            int temp = fisrt + i * space;
            if (temp >= 100) {
                document.add(utils.createParagraph(String.valueOf(temp), 7, utils.lightGrayColor, 101f + (i * 25f), 0,
                        0));
            } else {
                document.add(utils.createParagraph(String.valueOf(temp), 7, utils.lightGrayColor, 104f + (i * 24f), 0,
                        0));
            }
        }
        document.add(utils.createParagraph("%", 7, utils.lightGrayColor, 104f + (10 * 25f), 0, after));
    }

    private void setTable2SJBPerc(String init, float fisrt, float space, float before, float after)
            throws DocumentException
    {
        document.add(utils.createParagraph(init, 7, utils.lightGrayColor, 78f, before, 0));
        for (int i = 0; i < 10; i++) {
            float temp = fisrt + i * space;
            if (temp >= 10) {
                document.add(utils.createParagraph(String.valueOf(temp), 7, utils.lightGrayColor, 99f + (i * 25f), 0,
                        0));
            } else {
                document.add(utils.createParagraph(String.valueOf(temp), 7, utils.lightGrayColor, 102f + (i * 24f), 0,
                        0));
            }
        }
        document.add(utils.createParagraph("%", 7, utils.lightGrayColor, 104f + (10 * 25f), 0, after));
    }

    private void addContentTable3(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {
        // 画线并标注
        setTable3Line(inBodyVo);
        // 添加表头
        float[] table2Widths_head = {0.168f, 0.211f, 0.136f, 0.485f};
        PdfPTable table2_head = utils.createTable(table2Widths_head, Element.ALIGN_LEFT, 66f, 0f, 0);
        table2_head.addCell(titleCell(" "));
        table2_head.addCell(titleCell("低标准"));
        table2_head.addCell(titleCell("标准"));
        table2_head.addCell(titleCell("超标准"));
        document.add(table2_head);

        setTable2Rows(table, "细胞外水分比率");// 细胞外水分
        document.add(table);

        // 坐标尺,数值
        float rowHeight = table.getRowHeight(1);
        setTable3SJBPerc("0.320", 0.340f, 0.02f, -33f, rowHeight + 1);

    }

    private void setTable3SJBPerc(String init, float fisrt, float space, float before, float after)
            throws DocumentException
    {
        document.add(utils.createParagraph(init, 7, utils.lightGrayColor, 70f, before, 0));
        for (int i = 0; i < 10; i++) {
            if (i > 2) {
                space = 0.01f;
                fisrt = 0.36f;
            }
            float temp = fisrt + i * space;
            String tempStr = String.valueOf(temp);
            if (tempStr.length() > 3) {
                tempStr = tempStr.substring(0, 4);
            }
            document.add(utils.createParagraph(tempStr + "0", 7, utils.lightGrayColor, 95f + (i * 25f), 0, 0));
            if (tempStr.length() == 3) {
                document.add(utils.createParagraph(tempStr + "00", 7, utils.lightGrayColor, 95f + (i * 25f), 0, 0));
            }
        }
        document.add(utils.createParagraph("%", 7, utils.lightGrayColor, 105f + (10 * 25f), 0, after));
    }

    private void setTable3Line(PregInBodyBcaPojo inBodyVo) throws DocumentException {
        // 初始位置：83f
        // 终点位置：384f
        float[] widths = new float[1];
        // 数值
        // 体重
        Float weight = Float.parseFloat(examMap.get("BODY00017").getItemString());;
        if (weight > 0.38) {
            widths[0] = (weight - 0.32f) / 0.01f * 18.1f;
        } else {
            widths[0] = (weight - 0.32f) / 0.02f * 35f;
        }
        document.add(utils.createParagraph(examMap.get("BODY00017").getItemString() + "", 7, utils.darkGrayColor,
                70f + widths[0], 63f, -39f));
        drawLineBatch(widths, 312f, 0, 4, utils.brownColor);
    }

    private void addContentTable4(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {

        PdfPCell cell;
        cell = myCell("");

        table.addCell(myCellHisAdd("时间"));
        String[] wt = examMap.get("BODY00018").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }

        table.addCell(myCellHisAdd("间隔时间（周）"));
        wt = examMap.get("BODY00019").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }

        //
        table.addCell(myCellHisAdd("体重（kg）"));
        wt = examMap.get("BODY00020").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }
        //
        table.addCell(myCellHisAdd("蛋白质（kg）"));
        wt = examMap.get("BODY00021").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }
        //
        table.addCell(myCellHisAdd("脂肪（kg）"));
        wt = examMap.get("BODY00022").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }
        //
        table.addCell(myCellHisAdd("骨骼肌（kg）"));
        wt = examMap.get("BODY00023").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }
        //
        table.addCell(myCellHisAdd("无机盐（kg）"));
        wt = examMap.get("BODY00024").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }
        //
        table.addCell(myCellHisAdd("细胞内水分（L）"));
        wt = examMap.get("BODY00025").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }

        table.addCell(myCellHisAdd("细胞外水分（L）"));
        wt = examMap.get("BODY00026").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }
        cell = myCellHisAdd("细胞外水分比率（%）");
        cell.setFixedHeight(22f);
        table.addCell(cell);
        wt = examMap.get("BODY00027").getItemString().split("#");
        for (int i = 0; i < wt.length; i++) {
            if ("——".equals(wt[i])) {
                wt[i] = "";
            }
            if (i == 0) {
                cell = myCellHisTotal(wt[i]);
            } else {
                cell = myCellHis(wt[i]);
            }
            table.addCell(cell);
        }
        document.add(table);

    }

    private void addContentTable7(float heigh, float width, float space, PregInBodyBcaPojo inbodyVo)
            throws DocumentException {

        document.add(utils.createParagraph("每周体重适宜增长范围：", 8, utils.darkGrayColor, width, heigh, 0));
        String inbody00038 = examMap.get("BODY00029").getItemString();
        document.add(utils.createParagraph(StringUtils.isEmpty(inbody00038) ? "" : inbody00038, 8,
                utils.darkGrayColor, width + 90, 0, 0));

        document.add(utils.createParagraph("目前整体体重增长：", 8, utils.darkGrayColor, width, space, 0));
        document.add(utils.createParagraph(examMap.get("BODY00030").getItemString().toString() + "kg", 8,
                utils.darkGrayColor, width + 70, 0, 0));

        document.add(utils.createParagraph("整体孕期体重增长范围：", 8, utils.darkGrayColor, width, space, 0));
        document.add(utils.createParagraph(examMap.get("BODY00031").getItemString().toString(), 8,
                utils.darkGrayColor, width + 90, 0, 0));
    }

    private void addContentTable8(float heigh, float width, float space, PregInBodyBcaPojo inBodyVo)
            throws DocumentException {
        document.add(utils.createParagraph("右上肢:", 8, utils.darkGrayColor, width, heigh, 0));
        document.add(utils.createParagraph("(" + examMap.get("BODY00032").getItemString() + "kg" + ")", 8,
                utils.lightGrayColor, width + 27, 0, 0));
        document.add(utils.createParagraph("左上肢:", 8, utils.darkGrayColor, width, space, 0));
        document.add(utils.createParagraph("(" + examMap.get("BODY00034").getItemString() + "kg" + ")", 8,
                utils.lightGrayColor, width + 27, 0, 0));
        document.add(utils.createParagraph("躯干:", 8, utils.darkGrayColor, width, space, 0));
        document.add(utils.createParagraph("(" + examMap.get("BODY00036").getItemString() + "kg" + ")", 8,
                utils.lightGrayColor, width + 27, 0, 0));
        document.add(utils.createParagraph("右下肢:", 8, utils.darkGrayColor, width, space, 0));
        document.add(utils.createParagraph("(" + examMap.get("BODY00038").getItemString() + "kg" + ")", 8,
                utils.lightGrayColor, width + 27, 0, 0));
        document.add(utils.createParagraph("左下肢:", 8, utils.darkGrayColor, width, space, 0));
        document.add(utils.createParagraph("(" + examMap.get("BODY00040").getItemString() + "kg" + ")", 8,
                utils.lightGrayColor, width + 27, 0, 0));
        // 划线、标注
        setTable8Line(width, space, inBodyVo);

    }

    private void setTable8Line(float width, float space, PregInBodyBcaPojo inBodyVo) throws DocumentException {
        // 数值
        float[] widths = new float[5];
        // 数值
        // 右上肢
        widths[0] = Float.parseFloat(examMap.get("BODY00032").getItemString());
        if (widths[0] > 9.7f) {
            widths[0] = 9.7f;
        }
        document.add(utils.createParagraph(examMap.get("BODY00033").getItemString() + "%", 7, utils.lightGrayColor,
                width + 73f + 60f + widths[0], -79f, 0));
        // 左上肢
        widths[1] = Float.parseFloat(examMap.get("BODY00034").getItemString());
        if (widths[1] > 9.7f) {
            widths[1] = 9.7f;
        }
        document.add(utils.createParagraph(examMap.get("BODY00035").getItemString() + "%", 7, utils.lightGrayColor,
                width + 73f + 60f + widths[1], space, 0));
        // 躯干
        widths[2] = Float.parseFloat(examMap.get("BODY00036").getItemString());
        if (widths[2] > 12f) {
            widths[2] = 12f;
        }
        document.add(utils.createParagraph(examMap.get("BODY00037").getItemString() + "%", 7, utils.lightGrayColor,
                width + 73f + 60f + widths[2], space, 0));
        // 右下肢
        widths[3] = Float.parseFloat(examMap.get("BODY00038").getItemString());
        if (widths[3] > 9.7f) {
            widths[3] = 9.7f;
        }
        document.add(utils.createParagraph(examMap.get("BODY00039").getItemString() + "%", 7, utils.lightGrayColor,
                width + 73f + 60f + widths[3], space, 0));
        // 左下肢
        widths[4] = Float.parseFloat(examMap.get("BODY00040").getItemString());
        if (widths[4] > 9.7f) {
            widths[4] = 9.7f;
        }
        document.add(utils.createParagraph(examMap.get("BODY00041").getItemString() + "%", 7, utils.lightGrayColor,
                width + 73f + 60f + widths[4], space, 5));
        drawLineBatchR(width, widths, 418f, space, 4, backGroudColor);
    }

    private void addContentTable9(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        cell = myCell("");

        //
        table.addCell(myCellObj("细胞内水分:"));
        cell = myCellObj(examMap.get("BODY00042").getItemString() + "L");
        table.addCell(cell);
        cell = myCellObj1("(" + examMap.get("BODY00042").getItemRefValue() + ")");
        table.addCell(cell);
        //
        table.addCell(myCellObj("细胞外水分:"));
        cell = myCellObj(examMap.get("BODY00043").getItemString() + "L");
        table.addCell(cell);
        cell = myCellObj1("(" + examMap.get("BODY00043").getItemRefValue() + ")");
        table.addCell(cell);
        //
        table.addCell(myCellObj("身体细胞量:"));
        cell = myCellObj(examMap.get("BODY00044").getItemString() + "kg");
        table.addCell(cell);
        cell = myCellObj1("(" + examMap.get("BODY00044").getItemRefValue() + ")");
        table.addCell(cell);

        table.setSpacingAfter(5);

        document.add(table);
    }

    private void addContentTable6(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {
        document.add(utils.createParagraph("体重增长（千克）", 7, utils.darkGrayColor, 383f, 4f, 5f));

        PdfPCell cell;
        cell = myCell("");
        float cellHeight = 7f;
        cell.setFixedHeight(cellHeight);
        for (int i = -5; i < 18; i++) {
            for (int j = 0; j < 20; j++) {
                table.addCell(cell);
            }
        }
        document.add(table);

        // 添加y轴坐标
        document.add(utils.createParagraph("18", 5, utils.darkGrayColor, 381f, -169f, 7f));
        for (int i = 0; i < 22; i++) {
            int index = 17 - i;
            if (index < 0) {
                document.add(utils.createParagraph(String.valueOf(index), 5, utils.darkGrayColor, 381f, 0, 7f));
            } else if (index < 10) {
                document.add(utils.createParagraph(String.valueOf(index), 5, utils.darkGrayColor, 383f, 0, 7f));
            } else {
                document.add(utils.createParagraph(String.valueOf(index), 5, utils.darkGrayColor, 381f, 0, 7f));
            }
        }
        document.add(utils.createParagraph("-5", 5, utils.darkGrayColor, 381f, 0, 9f));
        // 添加x轴坐标
        document.add(utils.createParagraph("0", 5, utils.darkGrayColor, 387f, -4f, 0));
        for (int i = 0; i < 20; i++) {
            if (i > 8) {
                document.add(utils.createParagraph(String.valueOf(2 * i), 5, utils.darkGrayColor,
                        387f + i * 8.17f, 0, 0));
            } else {
                document.add(utils.createParagraph(String.valueOf(2 * i), 5, utils.darkGrayColor,
                        387.5f + i * 8.14f, 0, 0));
            }
        }
        document.add(utils.createParagraph("40", 5, utils.darkGrayColor, 549f, 0, 10f));
        document.add(utils.createParagraph("孕周（周）", 7, utils.darkGrayColor, 456f, 0, 10f));
        // 判断bmi标准
        BigDecimal bmi = inBodyVo.getPregVo().getBmi();
        float bmiF = bmi.floatValue();
        float[] maxHs = new float[2];
        float[] equHs = new float[2];
        float[] minHs = new float[2];

        // 画基准折线
        if (bmiF < 18.5) {
            maxHs[0] = 2f * cellHeight;
            maxHs[1] = 18f * cellHeight;
            equHs[0] = 1.5f * cellHeight;
            equHs[1] = 16f * cellHeight;
            minHs[0] = 1f * cellHeight;
            minHs[1] = 12.5f * cellHeight;
        } else if (bmiF < 25f) {
            maxHs[0] = 2f * cellHeight;
            maxHs[1] = 16f * cellHeight;
            equHs[0] = 1.5f * cellHeight;
            equHs[1] = 12.5f * cellHeight;
            minHs[0] = 0.5f * cellHeight;
            minHs[1] = 11.5f * cellHeight;
        } else if (bmiF < 30f) {
            maxHs[0] = 1.5f * cellHeight;
            maxHs[1] = 11.5f * cellHeight;
            equHs[0] = 1f * cellHeight;
            equHs[1] = 8f * cellHeight;
            minHs[0] = 0.5f * cellHeight;
            minHs[1] = 7f * cellHeight;
        } else {
            maxHs[0] = 1.5f * cellHeight;
            maxHs[1] = 9f * cellHeight;
            equHs[0] = 1f * cellHeight;
            equHs[1] = 7.5f * cellHeight;
            minHs[0] = 0f * cellHeight;
            minHs[1] = 5f * cellHeight;
        }

        drawLineTable6(new int[] {12, 40}, maxHs, 4.1625f, 581f, utils.brownColor);
        drawLineTable6(new int[] {12, 40}, equHs, 4.1625f, 581f, utils.brownColor);
        drawLineTable6(new int[] {12, 40}, minHs, 4.1625f, 581f, utils.brownColor);

        String[] weeks = examMap.get("BODY00028").getItemString().split("#");;
        String[] wtIncome = examMap.get("BODY00028").getItemResult().split("#");;
        int listSize = weeks.length;

        int[] pWeeks = new int[listSize];
        float[] wtRose = new float[listSize];

        for (int i = 0; i < pWeeks.length; i++) {
            pWeeks[i] = Integer.valueOf(weeks[i]);
            wtRose[i] = Float.valueOf(wtIncome[i]);
            if (wtRose[i] < -5) {
                wtRose[i] = -5;
            } else if (wtRose[i] > 18) {
                wtRose[i] = 18;
            }
        }

        drawLineTable7(pWeeks, wtRose, 4.1625f, 581f, backGroudColor);
    }

    //
    private void drawLineTable6(int[] weeks, float[] heights, float spaceX, float height, BaseColor color)
            throws DocumentException {
        if (heights != null && heights.length > 0) {
            float leftMargin = 408.5f;// 左边距
            for (int i = 0; i < heights.length; i++) {// 遍历高度长的数组
                float x1 = leftMargin + weeks[i] * spaceX;
                float x2 = x1;
                float y1 = height + heights[i];
                float y2 = y1;
                if (i == 0) {
                    utils.drawLine(writer, leftMargin, height, x1, y1, 1f, color);
                    utils.drawLine(writer, leftMargin - 1, height - 1, leftMargin + 1, height + 1, 3f, color);
                }
                if (i < heights.length - 1) {
                    x2 = leftMargin + weeks[i + 1] * spaceX;
                    y2 = height + heights[i + 1];
                }
                utils.drawLine(writer, x1, y1, x2, y2, 1f, color);
                utils.drawLine(writer, x1 - 1, y1 - 1, x1 + 1, y1 + 1, 3f, color);
                if (i != 0 && i == heights.length - 1) {
                    utils.drawLine(writer, x2 - 1, y2 - 1, x2 + 1, y2 + 1, 3f, color);
                }
            }
        }
    }

    private void drawLineTable7(int[] weeks, float[] heights, float spaceX, float height, BaseColor color)
            throws DocumentException {
        if (heights != null && heights.length > 0) {
            float leftMargin = 408.5f;// 左边距
            for (int i = 0; i < heights.length; i++) {// 遍历高度长的数组
                heights[i] = heights[i] * 7.1f;
                float x1 = leftMargin + weeks[i] * spaceX;
                float x2 = x1;
                float y1 = height + heights[i];
                float y2 = y1;
                if (i == 0) {
                    utils.drawLine(writer, leftMargin, height, x1, y1, 1f, color);
                    utils.drawLine(writer, leftMargin - 1, height - 1, leftMargin + 1, height + 1, 3f, color);
                }
                if (i < heights.length - 1) {
                    x2 = leftMargin + weeks[i + 1] * spaceX;
                    y2 = height + heights[i + 1] * 7.1f;
                }
                utils.drawLine(writer, x1, y1, x2, y2, 1f, color);
                utils.drawLine(writer, x1 - 1, y1 - 1, x1 + 1, y1 + 1, 3f, color);
                if (i != 0 && i == heights.length - 1) {
                    utils.drawLine(writer, x2 - 1, y2 - 1, x2 + 1, y2 + 1, 3f, color);
                }
            }
        }
    }

    private void addContentTable10(PregInBodyBcaPojo inbodyVo) throws DocumentException {
        document.add(utils.createParagraph("体重增长：", 8, utils.darkGrayColor, 385f, 9f, 0));
        document.add(utils.createParagraph("过快", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("过缓", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        document.add(utils.createParagraph("骨骼肌：", 8, utils.darkGrayColor, 385f, 11f, 0));
        document.add(utils.createParagraph("增加", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("下降", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        document.add(utils.createParagraph("蛋白质：", 8, utils.darkGrayColor, 385f, 11f, 0));
        document.add(utils.createParagraph("增加", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("下降", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        document.add(utils.createParagraph("无机盐：", 8, utils.darkGrayColor, 385f, 11f, 0));
        document.add(utils.createParagraph("增加", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("下降", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        document.add(utils.createParagraph("脂肪：", 8, utils.darkGrayColor, 385f, 11f, 0));
        document.add(utils.createParagraph("增加", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("下降", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        document.add(utils.createParagraph("细胞外水分：", 8, utils.darkGrayColor, 385f, 11f, 0));
        document.add(utils.createParagraph("增加", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("下降", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        document.add(utils.createParagraph("细胞内水分：", 8, utils.darkGrayColor, 385f, 11f, 0));
        document.add(utils.createParagraph("增加", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("下降", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        document.add(utils.createParagraph("细胞外水分比率：", 8, utils.darkGrayColor, 385f, 11f, 0));
        document.add(utils.createParagraph("轻微浮肿", 8, utils.darkGrayColor, 475f, 0, 0));
        document.add(utils.createParagraph("浮肿", 8, utils.darkGrayColor, 535f, 0, 5.5f));

        int[] result = new int[8];
        // 体重
        String infoWt = examMap.get("BODY00046").getItemResult();
        if ("体重增长过快".equals(infoWt)) {
            result[0] = 1;
        } else if (infoWt.equals("体重增长过缓")) {
            result[0] = 2;
        } else {
            result[0] = 0;
        }
        // 骨骼肌
        String smmInfo = examMap.get("BODY00047").getItemResult();
        if ("骨骼肌增加".equals(smmInfo)) {
            result[1] = 1;
        } else {
            result[1] = 2;
        }
        // 蛋白质
        String perInfo = examMap.get("BODY00048").getItemResult();
        if ("蛋白质增加".equals(perInfo)) {
            result[2] = 1;
        } else {
            result[2] = 2;
        }
        // 无机盐
        String mineralInfo = examMap.get("BODY00049").getItemResult();
        if ("无机盐增加".equals(mineralInfo)) {
            result[3] = 1;
        } else {
            result[3] = 2;
        }
        // 脂肪
        String bfmInfo = examMap.get("BODY00050").getItemResult();
        if ("体脂肪增加".equals(bfmInfo)) {
            result[4] = 1;
        } else {
            result[4] = 2;
        }
        // 细胞外水分
        String ecwInfo = examMap.get("BODY00051").getItemResult();
        if ("细胞外水分增加".equals(ecwInfo)) {
            result[5] = 1;
        } else {
            result[5] = 2;
        }
        // 细胞内水分
        String icwInfo = examMap.get("BODY00052").getItemResult();
        if ("细胞内水分增加".equals(icwInfo)) {
            result[6] = 1;
        } else {
            result[6] = 2;
        }
        // 细胞外水分比率
        String wedInfo = examMap.get("BODY00053").getItemResult();
        if ("浮肿".equals(wedInfo)) {
            result[7] = 2;
        } else if ("轻微".equals(wedInfo)) {
            result[7] = 1;
        } else {
            result[7] = 3;
        }
        if (inbodyVo.getHistoryList().size() < 2) {
            for (int i = 1; i < result.length - 1; i++) {
                result[i] = 3;
            }
        }
        writerCheckBox(172f, "1", 8, new int[] {2, 2, 2, 2, 2, 2, 2, 2}, result);
    }

    private void addContentTable11() {
        int beginY = 300;
        int endY = 420;
        utils.drawLine(writer, 463, beginY, 463, endY, 1f, utils.whiteColor);
        utils.drawLine(writer, 478, beginY, 478, endY, 1f, utils.whiteColor);
        utils.drawLine(writer, 493, beginY, 493, endY, 1f, utils.whiteColor);
        utils.drawLine(writer, 508, beginY, 508, endY, 1f, utils.whiteColor);
        utils.drawLine(writer, 523, beginY, 523, endY, 1f, utils.whiteColor);

    }

    // =======================================工具方法==================================
    private void addImgLogo(PdfPTable table, PregInBodyBcaPojo inBodyVo) throws DocumentException {
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + inBodyVo.getInsId() + PublicConstant.logo2;
        Image img;

        PdfPCell cellImg = utils.baseCell("", utils.reportFont);
        try {
            File file = new File(basepath);
            if (file.exists()) {
                img = Image.getInstance(basepath);
                cellImg.setImage(img);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cellImg.setBackgroundColor(utils.lightThiredRedColor);
        cellImg.setBorderColor(utils.lightThiredRedColor);
        cellImg.setFixedHeight(25f);
        cellImg.setPaddingBottom(5f);// 设置下边距
        cellImg.setPaddingTop(5f);// 设置上边距
        cellImg.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellImg.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cellImg);
    }

    /** 表格首列背景色 */
    private BaseColor backGroudColor = new BaseColor(68, 106, 178);// 背景颜色

    /**
     * 批量画线
     * 
     * @author zcq
     * @param widths
     * @param initY
     * @param space
     * @param count
     * @param color
     */
    private void drawLineBatch(float[] widths, float initY, float space, int count, BaseColor color) {
        for (int i = 0; i < widths.length; i++) {
            utils.drawLine(writer, 83, initY - i * space, 83 + widths[i], initY - i * space, 3f, color);
        }
    }

    /**
     * 画多选框
     * 
     * @author zcq
     * @param y
     * @param count
     * @param rows
     * @param cols
     * @param result
     */
    private void writerCheckBox(float y, String count, int rows, int[] cols, int[] result) {
        PdfFormField field;
        float x = 480f;
        float spaceX = 60f;
        float spaceY = 16.5f;
        boolean flag = false;
        if ("3".equals(count)) {
            x = 456f;
            spaceX = 30f;
        }
        for (int i = 0; i < rows; i++) {
            float yy = y - i * spaceY;
            for (int j = 0; j < cols[i]; j++) {
                flag = false;
                float xx = x + j * spaceX;
                if (cols[i] == 1) {
                    xx = x + spaceX;
                }
                if ("4".equals(count) && j == 1) {
                    xx = x + 33f;
                }
                if ("3".equals(count) && j == 2) {
                    xx = x + 67.5f;
                }
                if (result[i] == (j + 1)) {
                    flag = true;
                }
                field = utils.createCheckBox(writer, count + i + j, flag, new Rectangle(xx, yy, xx + 7.5f, yy + 7.5f));
                writer.addAnnotation(field);
            }
        }
    }

    // ---------------------------------------------------------特殊工具
    private PdfPCell titleCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.titleFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightPinkColor);
        return cell;
    }

    private PdfPCell myCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(utils.lightPinkColor);
        cell.setFixedHeight(33f);
        return cell;
    }

    private PdfPCell myCellHisAdd(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(18f);
        cell.setBorderColor(utils.lightPinkColor);
        return cell;
    }

    private PdfPCell myCellHis(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(utils.lightPinkColor);
        return cell;
    }

    private PdfPCell myCellHisTotal(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setFixedHeight(20f);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setBorderColor(utils.whiteColor);
        return cell;
    }

    private PdfPCell myCellObj(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(utils.whiteColor);// 边颜色
        cell.setFixedHeight(20);
        cell.setPaddingLeft(0);
        return cell;
    }

    private PdfPCell myCellObj1(String content) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 8, Font.NORMAL, utils.lightGrayColor));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(utils.whiteColor);
        return cell;
    }

    private void drawLineBatchR(float width, float[] widths, float initY, float space, int count, BaseColor color) {
        for (int i = 0; i < widths.length; i++) {
            utils.drawLine(writer, width + 80, initY - i * space, width + 83 + 60 + widths[i], initY - i * space, 3f,
                    color);
        }
    }

    private PdfPCell myCellRed(String content) {
        PdfPCell cell = utils.baseCell(content, utils.titleFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        return cell;
    }

    private PdfPCell myCellWhite(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        // cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        return cell;
    }

    private PdfPCell myCellWhiteDoubleContent(String content1, String content2, int borderColor) {
        float[] doubCell = new float[] {1};
        PdfPTable titleTable = utils.createTable(doubCell, PdfPCell.ALIGN_CENTER, 0.000001f, 0, 0);
        // cell1设置
        PdfPCell cell1 = utils.baseCell(content1, utils.contentFont);
        cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell1.setFixedHeight(16.5f);
        cell1.setBorderColor(utils.lightPinkColor);
        // cell2设置
        PdfPCell cell2 = utils.baseCell(content2, utils.contentFont);
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);// 文本上下居中
        cell2.setFixedHeight(16.5f);
        cell2.setBorderColor(utils.lightPinkColor);
        if (borderColor == 1) {
            cell1.setBorderColor(utils.whiteColor);
            cell2.setBorderColor(utils.whiteColor);
        }
        titleTable.addCell(cell1);
        titleTable.addCell(cell2);
        PdfPCell cell = new PdfPCell(titleTable);
        cell.setFixedHeight(33f);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);
        cell.setBorderWidth(0.5f);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        return cell;
    }

    /**
     * 生成角标形式的孕周
     * 
     * @author xdc
     * @param content
     * @throws DocumentException
     */
    private void getSupWeek(String content) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String[] a = content.split("\\+");
        int sta = 375;
        int len = a[0].length() > 1 ? 13 : 9;
        document.add(utils.createParagraph(a[0], 10, utils.darkGrayColor, sta, 35, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.darkGrayColor, sta + len, -5, -27));
    }

    /**
     * 设置分界线
     * 
     * @author dhs
     * @throws DocumentException
     */
    private void setHr(PdfPTable table, float height, BaseColor color, String type) throws DocumentException {
        PdfPCell cell = utils.baseCell("", utils.reportFont);
        cell.setBackgroundColor(color);
        cell.setBorderColor(utils.whiteColor);
        cell.setFixedHeight(height);
        table.addCell(cell);
        table.setSpacingAfter(1f);
        if ("end".equals(type)) {
            table.setSpacingBefore(5f);
        }
        document.add(table);
    }

    /**
     * 页尾
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private PdfPCell myCellWhiteBorder(String content, int align, int type) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        if (type == 1) {
            cell = utils.baseCell(content, new Font(utils.createFont(), 7, Font.NORMAL, utils.darkGrayColor));
        }
        cell.setHorizontalAlignment(align);
        cell.setPaddingLeft(0);// 设置边距
        cell.setPaddingRight(0);// 设置边距
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.whiteColor);// 边颜色
        cell.setBorderWidth(0.5f);
        return cell;
    }

    /**
     * 设置页尾
     * 
     * @author dhs
     * @throws DocumentException
     */
    private void setPageLast(PregInBodyBcaPojo pdfData) throws DocumentException {
        // PregDiagnosisPojo diagnosis = pdfData.getDiagnosis();
        PregDiagnosisItemsVo diagnosisItem = pdfData.getDiagnosisItem();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        String time = df.format(new Date());// 当前系统时间
        float[] table1Width1 = new float[] {0.5f, 0.5f};
        // PdfPTable table1 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 6f, 0);
        // table1.addCell(myCellWhiteBorder(
        // "送检科室：" + (diagnosis.getDiagnosisOrg() == null ? "" : diagnosis.getDiagnosisOrg()),
        // PdfPCell.ALIGN_LEFT,
        // 0));
        // if (diagnosis.getDiagnosisReferralDoctorName() == null) {
        // table1.addCell(myCellWhiteBorder("                                                  送检医生：",
        // PdfPCell.ALIGN_CENTER, 0));
        // } else {
        // table1.addCell(myCellWhiteBorder("送检医生：" + diagnosis.getDiagnosisReferralDoctorName(),
        // PdfPCell.ALIGN_RIGHT, 0));
        // }
        // table1.setSpacingAfter(-3f);
        // document.add(table1);
        PdfPTable table2 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 6f, 0);
        table2.addCell(myCellWhiteBorder(
                "执行医生/助理：" + (diagnosisItem.getInspectUserName() == null ? "" : diagnosisItem.getInspectUserName()),
                PdfPCell.ALIGN_LEFT, 0));
        table2.addCell(myCellWhiteBorder("报告日期：" + time, PdfPCell.ALIGN_RIGHT, 0));
        document.add(table2);
    }
}
