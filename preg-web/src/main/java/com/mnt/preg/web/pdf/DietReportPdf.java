
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.examitem.pojo.PregFoodRecordPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
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
public class DietReportPdf extends AbstractPdf<PregDietReportPojo> {

    private Map<String, ExamItemPojo> examMap = new HashMap<String, ExamItemPojo>();

    /**
     * 设置干预方案报告的内容
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @throws DocumentException
     */
    @Override
    public void handler(PregDietReportPojo dietReportVo) throws DocumentException {
        examMap = dietReportVo.getExamMap();

        // 设置报告头
        float[] titleWidth = {0.38f, 0.62f};
        addContentTableHead0(utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 40f, 0), dietReportVo);

        // 信息栏
        float[] table0Width = new float[] {0.25f, 0.25f, 0.25f, 0.25f};
        addContentTable0(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 7f, 0), dietReportVo);// 添加内容

        // 能量摄入（当日）
        addTitleTable("能量摄入（当日）", Element.ALIGN_LEFT, 49);// 添加标题
        float[] table1Width = new float[] {0.3f, 0.275f, 0.275f, 0.15f};
        addContentTable1(utils.createTable(table1Width, Element.ALIGN_LEFT, 49f, 10f, 0));// 添加内容

        // 产能营养素摄入（当日）
        addTitleTable("产能营养素摄入（当日）", Element.ALIGN_LEFT, 49);// 添加标题
        float[] table2Width = new float[] {0.2f, 0.2f, 0.2f, 0.2f, 0.15f};
        addContentTable2(utils.createTable(table2Width, Element.ALIGN_LEFT, 49f, 10f, 0));// 添加内容

        // 维生素摄入（当日）
        addTitleTable("维生素摄入（当日）", Element.ALIGN_LEFT, 49);// 添加标题
        float[] table3Width = new float[] {0.2f, 0.2f, 0.15f, 0.15f, 0.15f, 0.15f};
        addContentTable3(utils.createTable(table3Width, Element.ALIGN_LEFT, 49f, 10f, 0));// 添加内容

        // 矿物质摄入（当日）
        addTitleTable("矿物质摄入（当日）", Element.ALIGN_LEFT, 49);// 添加标题
        float[] table4Width = new float[] {0.15f, 0.25f, 0.15f, 0.15f, 0.1562f, 0.15f};
        addContentTable4(utils.createTable(table4Width, Element.ALIGN_LEFT, 49f, 10f, 0));// 添加内容

        // 升糖指数（当日）
        addTitleTable("升糖指数（当日）", Element.ALIGN_LEFT, 49);// 添加标题
        float[] table5Width = new float[] {0.3f, 0.2f, 0.25f, 0.15f};
        addContentTable5(utils.createTable(table5Width, Element.ALIGN_LEFT, 49f, 10f, 0));// 添加内容

        // --------------------------------------------------左右分割线------------------------------------------------
        document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, -704.5f));

        // 膳食摄入量
        addTitleTable("膳食摄入量", Element.ALIGN_RIGHT, 49);// 添加标题
        float[] table6Width = new float[] {0.25f, 0.25f, 0.25f, 0.25f};
        addContentTable6(utils.createTable(table6Width, Element.ALIGN_RIGHT, 49f, 8.5f, 0));// 添加内容

        // 孕期膳食营养
        addTitleTable("膳食摄入量", Element.ALIGN_RIGHT, 49);// 添加标题
        float[] table7Width = new float[] {0.25f, 0.5f, 0.25f};
        addContentTable7(utils.createTable(table7Width, Element.ALIGN_RIGHT, 49f, 8f, 0));// 添加内容

        // 用餐习惯
        addTitleTable("用餐习惯", Element.ALIGN_RIGHT, 49);// 添加标题
        float[] table8Width = new float[] {0.25f, 0.5f, 0.25f};
        addContentTable8(utils.createTable(table8Width, Element.ALIGN_RIGHT, 49f, 8f, 0));// 添加内容

        // 您的用餐情况
        addTitleTable("您的用餐情况", Element.ALIGN_RIGHT, 49);// 添加标题
        float[] table9Width = new float[] {0.25f, 0.75f};
        addContentTable9(utils.createTable(table9Width, Element.ALIGN_RIGHT, 49f, 9f, 0));// 添加内容

        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(dietReportVo);

    }

    // --------------------------------------------------设置填充内容------------------------------------------------
    private void addTitleTable(String titleName, int align, float recent) throws DocumentException {
        PdfPCell cell;
        float[] titleWidth = null;
        if (align == Element.ALIGN_LEFT || align == Element.ALIGN_RIGHT) {
            titleWidth = new float[] {0.05f, 0.95f};
        } else {
            titleWidth = new float[] {0.1f, 0.9f};
        }
        PdfPTable titleTable = new PdfPTable(titleWidth);
        titleTable.setHorizontalAlignment(align);
        titleTable.setWidthPercentage(recent);
        titleTable.setSpacingAfter(5f);
        titleTable.addCell(titleCell(" "));
        cell = utils.baseCell(titleName, utils.infoFont);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(utils.whiteColor);
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    private void addContentTableHead0(PdfPTable table, PregDietReportPojo dietReportVo) throws DocumentException {
        addImgLogo(table, dietReportVo);
        PdfPCell cell = utils.baseCell("膳食诊断报告", utils.reportFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightThiredRedColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(25f);
        table.addCell(cell);
        table.setSpacingAfter(5f);
        document.add(table);
    }

    private void addContentTable0(PdfPTable table, PregDietReportPojo dietReportVo) throws DocumentException {
        PregFoodRecordPojo foodRecord = dietReportVo.getFoodRecord();
        PregDiagnosisPojo diagnosis = dietReportVo.getDiagnosis();
        table.addCell(infoCell("检测日期："
                + JodaTimeTools.toString(foodRecord.getRecordDatetime(), JodaTimeTools.FORMAT_6)))
                .setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(infoCell("姓名：" + foodRecord.getCustName())).setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(infoCell("年龄：" + foodRecord.getCustAge())).setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(infoCell("孕周数：")).setHorizontalAlignment(
                Element.ALIGN_LEFT);
        getSupWeek(diagnosis.getDiagnosisGestationalWeeks());
        document.add(table);
    }

    private void addContentTable1(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        cell = myCell("");
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("EER"));
        table.addCell(titleCell("结论"));

        // 一日总能量
        table.addCell(myCell("一日总能量"));
        cell = myCell(examMap.get("NU0100001").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100001").getItemRefValue());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100001").getItemResult());
        table.addCell(cell);
        // 早餐供能
        table.addCell(myCell("早餐供能(含加餐)"));
        cell = myCell(examMap.get("NU0100002").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100002").getItemRefValue());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100002").getItemResult());
        table.addCell(cell);

        // 午餐供能
        table.addCell(myCell("午餐供能(含加餐)"));
        cell = myCell(examMap.get("NU0100003").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100003").getItemRefValue());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100003").getItemResult());
        table.addCell(cell);

        // 晚餐供能
        table.addCell(myCell("晚餐供能(含加餐)"));
        cell = myCell(examMap.get("NU0100004").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100004").getItemRefValue());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100004").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable2(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        cell = myCell("");
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("AMDR"));
        table.addCell(titleCell("RNI"));
        table.addCell(titleCell("结论"));
        // 一日总能量
        table.addCell(myCell("总碳水化合物"));
        cell = myCell(examMap.get("NU0100005").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100005").getItemRefValue().split("###")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100005").getItemRefValue().split("###")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100005").getItemResult());
        table.addCell(cell);
        // 总蛋白质
        table.addCell(myCell("总蛋白质"));
        cell = myCell(examMap.get("NU0100006").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100006").getItemRefValue().split("###")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100006").getItemRefValue().split("###")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100006").getItemResult());
        table.addCell(cell);
        // 总脂肪
        table.addCell(myCell("总脂肪"));
        cell = myCell(examMap.get("NU0100007").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100007").getItemRefValue().split("###")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100007").getItemRefValue().split("###")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100007").getItemResult());
        table.addCell(cell);
        // 优质蛋白质
        table.addCell(myCell("优质蛋白质"));
        cell = myCell(examMap.get("NU0100008").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100008").getItemRefValue().split("###")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100008").getItemRefValue().split("###")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100008").getItemResult());
        table.addCell(cell);
        // 饱和脂肪酸
        table.addCell(myCell("饱和脂肪酸"));
        cell = myCell(examMap.get("NU0100009").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100009").getItemRefValue().split("###")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100009").getItemRefValue().split("###")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100009").getItemResult());
        table.addCell(cell);
        // 膳食纤维
        table.addCell(myCell("膳食纤维"));
        cell = myCell(examMap.get("NU0100010").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100010").getItemRefValue().split("###")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100010").getItemRefValue().split("###")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100010").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable3(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        cell = myCell("");
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("RNI"));
        table.addCell(titleCell("AI"));
        table.addCell(titleCell("UL"));
        table.addCell(titleCell("结论"));
        // 维生素A
        table.addCell(myCell("维生素A"));
        cell = myCell(examMap.get("NU0100011").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100011").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100011").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100011").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100011").getItemResult());
        table.addCell(cell);
        // 维生素C
        table.addCell(myCell("维生素C"));
        cell = myCell(examMap.get("NU0100012").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100012").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100012").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100012").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100012").getItemResult());
        table.addCell(cell);
        // 维生素E
        table.addCell(myCell("维生素E"));
        cell = myCell(examMap.get("NU0100013").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100013").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100013").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100013").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100013").getItemResult());
        table.addCell(cell);
        // 硫胺素
        table.addCell(myCell("硫胺素"));
        cell = myCell(examMap.get("NU0100014").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100014").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100014").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100014").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100014").getItemResult());
        table.addCell(cell);
        // 核黄素
        table.addCell(myCell("核黄素"));
        cell = myCell(examMap.get("NU0100015").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100015").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100015").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100015").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100015").getItemResult());
        table.addCell(cell);
        // 烟酸
        table.addCell(myCell("烟酸"));
        cell = myCell(examMap.get("NU0100016").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100016").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100016").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100016").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100016").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable4(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("RNI"));
        table.addCell(titleCell("AI"));
        table.addCell(titleCell("UL"));
        table.addCell(titleCell("结论"));
        cell = myCell("");
        //
        table.addCell(myCell("钙"));
        cell = myCell(examMap.get("NU0100017").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100017").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100017").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100017").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100017").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCell("钾"));
        cell = myCell(examMap.get("NU0100018").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100018").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100018").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100018").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100018").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCell("钠"));
        cell = myCell(examMap.get("NU0100019").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100019").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100019").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100019").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100019").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCell("镁"));
        cell = myCell(examMap.get("NU0100020").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100020").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100020").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100020").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100020").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCell("铁"));
        cell = myCell(examMap.get("NU0100021").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100021").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100021").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100021").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100021").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCell("锌"));
        cell = myCell(examMap.get("NU0100022").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100022").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100022").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100022").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100022").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable5(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("参考范围"));
        table.addCell(titleCell("结论"));
        cell = myCell("");

        //
        table.addCell(myCell("早餐Gl值（含加餐）"));
        cell = myCell(examMap.get("NU0100023").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100023").getItemRefValue());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100023").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCell("午餐Gl值（含加餐）"));
        cell = myCell(examMap.get("NU0100024").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100024").getItemRefValue());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100024").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCell("晚餐Gl值（含加餐）"));
        cell = myCell(examMap.get("NU0100025").getItemString());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100025").getItemRefValue());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100025").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable6(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("推荐值"));
        table.addCell(titleCell("结论"));
        cell = myCellR("");

        String unitName = "";
        //
        unitName = examMap.get("NU0100026").getItemUnit();
        table.addCell(myCellR("饮水"));
        cell = myCell(examMap.get("NU0100026").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100026").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100026").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100027").getItemUnit();
        table.addCell(myCellR("谷类"));
        cell = myCell(examMap.get("NU0100027").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100027").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100027").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100028").getItemUnit();
        table.addCell(myCellR("薯类"));
        cell = myCell(examMap.get("NU0100028").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100028").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100028").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100029").getItemUnit();
        table.addCell(myCellR("蔬菜"));
        cell = myCell(examMap.get("NU0100029").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100029").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100029").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100030").getItemUnit();
        table.addCell(myCellR("水果"));
        cell = myCell(examMap.get("NU0100030").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100030").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100030").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100031").getItemUnit();
        table.addCell(myCellR("鱼肉蛋"));
        cell = myCell(examMap.get("NU0100031").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100031").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100031").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100032").getItemUnit();
        table.addCell(myCellR("大豆类"));
        cell = myCell(examMap.get("NU0100032").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100032").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100032").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100033").getItemUnit();
        table.addCell(myCellR("坚果"));
        cell = myCell(examMap.get("NU0100033").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100033").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100033").getItemResult());
        table.addCell(cell);
        //
        unitName = examMap.get("NU0100034").getItemUnit();
        table.addCell(myCellR("乳类"));
        cell = myCell(examMap.get("NU0100034").getItemString() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100034").getItemRefValue() + unitName);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100034").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable7(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("结论"));
        cell = myCellR("");

        //
        table.addCell(myCellR("叶酸"));
        cell = myCellInfo(examMap.get("NU0100035").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100035").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("铁"));
        cell = myCellInfo(examMap.get("NU0100036").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100036").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("碘"));
        cell = myCellInfo(examMap.get("NU0100037").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100037").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("ω-3脂肪酸"));
        cell = myCellInfo(examMap.get("NU0100038").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100038").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("粗粮占比"));
        cell = myCellInfo(examMap.get("NU0100039").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100039").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable8(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        table.addCell(titleCell("结论"));
        cell = myCellR("");

        //
        table.addCell(myCellR("用餐频率"));
        cell = myCellInfo(examMap.get("NU0100040").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100040").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("用餐时间"));
        cell = myCellInfo(examMap.get("NU0100041").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100041").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("用餐感受"));
        cell = myCellInfo(examMap.get("NU0100042").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100042").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("在外用餐"));
        cell = myCellInfo(examMap.get("NU0100043").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100043").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("不良饮食"));
        cell = myCellInfo(examMap.get("NU0100044").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100044").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("烟酒情况"));
        cell = myCellInfo(examMap.get("NU0100045").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100045").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("饱和脂肪酸"));
        cell = myCellInfo(examMap.get("NU0100046").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100046").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("用油量"));
        cell = myCellInfo(examMap.get("NU0100047").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100047").getItemResult());
        table.addCell(cell);
        //
        table.addCell(myCellR("用盐量"));
        cell = myCellInfo(examMap.get("NU0100048").getItemString());
        table.addCell(cell);
        cell = myCellR(examMap.get("NU0100048").getItemResult());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable9(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell(" "));
        table.addCell(titleCell("实际"));
        cell = myCellR("");

        //
        table.addCell(myCellR("早餐"));
        cell = myCellInfo(examMap.get("NU0100049").getItemString());
        table.addCell(cell);
        //
        table.addCell(myCellR("上午加餐"));
        cell = myCellInfo(examMap.get("NU0100050").getItemString());
        table.addCell(cell);
        //
        table.addCell(myCellR("午餐"));
        cell = myCellInfo(examMap.get("NU0100051").getItemString());
        table.addCell(cell);
        //
        table.addCell(myCellR("下午加餐"));
        cell = myCellInfo(examMap.get("NU0100052").getItemString());
        table.addCell(cell);
        //
        table.addCell(myCellR("晚餐"));
        cell = myCellInfo(examMap.get("NU0100053").getItemString());
        table.addCell(cell);
        //
        table.addCell(myCellR("夜宵"));
        cell = myCellInfo(examMap.get("NU0100054").getItemString());
        table.addCell(cell);

        document.add(table);
    }

    // ----------------------------------------------------工具------------------------------------------------
    private void addImgLogo(PdfPTable table, PregDietReportPojo dietReportVo) throws DocumentException {
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + dietReportVo.getInsId() + PublicConstant.logo2;
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

    public PdfPCell infoCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.infoFont);
        cell.setBorderColor(utils.lightThiredRedColor);
        return cell;
    }

    private PdfPCell titleCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.titleFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell myCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell myCellR(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setFixedHeight(17.2f);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell myCellInfo(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setFixedHeight(17.2f);
        return cell;
    }

    private void getSupWeek(String content) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String[] a = content.split("\\+");
        int sta = 460;
        int len = a[0].length() > 1 ? 13 : 9;
        document.add(utils.createParagraph(a[0], 10, utils.lightThiredRedColor, sta, 15, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.lightThiredRedColor, sta + len, -5, -8));
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
            table.setSpacingBefore(10f);
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
    private void setPageLast(PregDietReportPojo pdfData) throws DocumentException {
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
