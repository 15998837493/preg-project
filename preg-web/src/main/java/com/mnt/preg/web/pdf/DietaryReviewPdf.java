
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
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
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
public class DietaryReviewPdf extends AbstractPdf<PregDietReportPojo> {

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
        float[] table0Width = new float[] {0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f};
        addContentTable0(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 7f, 0), dietReportVo);// 添加内容
        // 您的用餐情况
        addTitleTable("您的用餐情况（当日）", Element.ALIGN_RIGHT, 100);// 添加标题
        float[] table1Width = new float[] {0.2f, 0.6f, 0.3f};
        addContentTable1(utils.createTable(table1Width, Element.ALIGN_CENTER, 100f, 7f, 0));// 添加内容
        // 能量摄入（当日）
        addTitleTable("能量摄入（当日）", Element.ALIGN_LEFT, 49);// 添加标题
        float[] table2Width = new float[] {0.4f, 0.2f, 0.4f};
        addContentTable2(utils.createTable(table2Width, Element.ALIGN_LEFT, 49f, 10f, 0));// 添加内容

        // 产能营养素摄入（当日）
        addTitleTable("产能营养素摄入（当日）", Element.ALIGN_LEFT, 49);// 添加标题
        float[] table3Width = new float[] {0.4f, 0.25f, 0.35f};
        addContentTable3(utils.createTable(table3Width, Element.ALIGN_LEFT, 49f, 10f, 0));// 添加内容

        document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, -386f));

        // 维生素摄入（当日）
        addTitleTable("维生素摄入（当日）", Element.ALIGN_RIGHT, 49);// 添加标题
        float[] table4Width = new float[] {0.4f, 0.15f, 0.15f, 0.15f, 0.15f};
        addContentTable4(utils.createTable(table4Width, Element.ALIGN_RIGHT, 49f, 10f, 0));// 添加内容
        // 矿物质摄入（当日）
        addTitleTable("矿物质摄入（当日）", Element.ALIGN_RIGHT, 49);// 添加标题
        float[] table5Width = new float[] {0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
        addContentTable5(utils.createTable(table5Width, Element.ALIGN_RIGHT, 49f, 10f, 0));// 添加内容
        // 设置报告头
        String content = "提示：本报告推荐值参考健康孕妇标准，如合并疾病，请遵循医嘱。";
        addContentTableHead6(utils.createTable(1, Element.ALIGN_CENTER, 100f, 40f, 0), content);
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
        titleTable.addCell(titleCell(" ", 0));
        cell = utils.baseCell(titleName, utils.infoFont);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(utils.whiteColor);
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    private void addContentTableHead0(PdfPTable table, PregDietReportPojo dietReportVo) throws DocumentException {
        addImgLogo(table, dietReportVo);
        PdfPCell cell = utils.baseCell("孕期24小时膳食回顾报告", utils.reportFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightThiredRedColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(25f);
        table.addCell(cell);
        table.setSpacingAfter(5f);
        document.add(table);
    }

    /**
     * 设置个人信息
     * 
     * @author xdc
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private void addContentTable0(PdfPTable table, PregDietReportPojo dietReportVo) throws DocumentException {
        PregDiagnosisPojo diagnosis = dietReportVo.getDiagnosis();
        PregArchivesPojo pregnancyArchives = dietReportVo.getPregnancyArchives();
        table.addCell(infoCell("ID", 1));
        table.addCell(infoCell(diagnosis.getDiagnosisCustPatientId(), 2));
        table.addCell(infoCell("姓名", 1));
        table.addCell(infoCell(diagnosis.getDiagnosisCustName(), 2));
        table.addCell(infoCell("年龄", 1));
        table.addCell(infoCell(diagnosis.getDiagnosisCustAge() + "", 2));
        table.addCell(infoCell("孕前体重", 1));
        table.addCell(infoCell(pregnancyArchives.getWeight() + "kg", 2));

        table.addCell(infoCell("身高", 1));
        table.addCell(infoCell(diagnosis.getDiagnosisCustHeight() + "cm", 2));
        table.addCell(infoCell("孕前BMI", 1));
        table.addCell(infoCell(pregnancyArchives.getBmi() + "", 2));

        table.addCell(infoCell("孕周数", 1));
        table.addCell(infoCell("", 2));

        String embryoNum = pregnancyArchives.getEmbryoNum();
        table.addCell(infoCell("胎数", 1));
        table.addCell(infoCell(StringUtils.isEmpty(embryoNum) ? "" : embryoNum, 2));

        document.add(table);
        getSupWeek(diagnosis.getDiagnosisGestationalWeeks());
    }

    private void addContentTable1(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell("餐次", 20));
        table.addCell(titleCell("用餐情况", 20));
        table.addCell(titleCell("用餐时间", 20));

        // 早餐
        String breakStr = examMap.get("NU0100001").getItemString();
        table.addCell(myCellR("早餐", 40));
        cell = myCellInfo(breakStr);
        table.addCell(cell);
        cell = myCellInfo(StringUtils.isEmpty(breakStr) ? "" : examMap.get("NU0100007")
                .getItemString());
        table.addCell(cell);
        // 上午加餐
        String breakAddStr = examMap.get("NU0100002").getItemString();
        table.addCell(myCellR("上午加餐", 20));
        cell = myCellInfo(breakAddStr);
        table.addCell(cell);
        cell = myCellInfo(StringUtils.isEmpty(breakAddStr) ? "" : examMap.get("NU0100008").getItemString());
        table.addCell(cell);
        // 午餐
        String lunchStr = examMap.get("NU0100003").getItemString();
        table.addCell(myCellR("午餐", 40));
        cell = myCellInfo(lunchStr);
        table.addCell(cell);
        cell = myCellInfo(StringUtils.isEmpty(lunchStr) ? "" : examMap.get("NU0100009").getItemString());
        table.addCell(cell);
        // 下午加餐
        String lunchAddStr = examMap.get("NU0100004").getItemString();
        table.addCell(myCellR("下午加餐", 20));
        cell = myCellInfo(lunchAddStr);
        table.addCell(cell);
        cell = myCellInfo(StringUtils.isEmpty(lunchAddStr) ? "" : examMap.get("NU0100010").getItemString());
        table.addCell(cell);
        // 晚餐
        String supperStr = examMap.get("NU0100005").getItemString();
        table.addCell(myCellR("晚餐", 40));
        cell = myCellInfo(supperStr);
        table.addCell(cell);
        cell = myCellInfo(StringUtils.isEmpty(supperStr) ? "" : examMap.get("NU0100011").getItemString());
        table.addCell(cell);
        // 夜宵
        String supperAddStr = examMap.get("NU0100006").getItemString();
        table.addCell(myCellR("夜宵", 20));
        cell = myCellInfo(supperAddStr);
        table.addCell(cell);
        cell = myCellInfo(StringUtils.isEmpty(supperAddStr) ? "" : examMap.get("NU0100012").getItemString());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable2(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell("类别", 40));
        table.addCell(titleCell("实际摄入", 40));
        table.addCell(titleCell("推荐摄入", 40));

        // 一日总能量
        table.addCell(myCellTittle("一日总能量(kcal)", 30));
        cell = myCell(examMap.get("NU0100013").getItemString() + examMap.get("NU0100013").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100013").getItemRefValue());
        table.addCell(cell);
        // 早餐供能
        table.addCell(myCellTittle("早餐(含加餐)(kcal)", 30));
        cell = myCell(examMap.get("NU0100014").getItemString() + examMap.get("NU0100014").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100014").getItemRefValue());
        table.addCell(cell);
        // 午餐供能
        table.addCell(myCellTittle("午餐(含加餐)(kcal)", 30));
        cell = myCell(examMap.get("NU0100015").getItemString() + examMap.get("NU0100015").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100015").getItemRefValue());
        table.addCell(cell);
        // 晚餐供能
        table.addCell(myCellTittle("晚餐(含加餐)(kcal)", 30));
        cell = myCell(examMap.get("NU0100016").getItemString() + examMap.get("NU0100016").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100016").getItemRefValue());
        table.addCell(cell);
        document.add(table);
    }

    private void addContentTable3(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell("类别", 40));
        table.addCell(titleCell("实际摄入", 40));
        table.addCell(titleCell("推荐摄入", 40));

        table.addCell(myCellTittle("总碳水化合物占比", 0));
        cell = myCell(examMap.get("NU0100017").getItemString() + examMap.get("NU0100017").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100017").getItemRefValue());
        table.addCell(cell);
        // 总蛋白质
        table.addCell(myCellTittle("总蛋白(g)", 0));
        cell = myCell(examMap.get("NU0100018").getItemString() + examMap.get("NU0100018").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100018").getItemRefValue());
        table.addCell(cell);
        // 总脂肪
        table.addCell(myCellTittle("总脂肪占比", 0));
        cell = myCell(examMap.get("NU0100019").getItemString() + examMap.get("NU0100019").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100019").getItemRefValue());
        table.addCell(cell);
        // 优质蛋白质
        table.addCell(myCellTittle("优质蛋白质(g)", 0));
        cell = myCell(examMap.get("NU0100020").getItemString() + examMap.get("NU0100020").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100020").getItemRefValue());
        table.addCell(cell);
        // 饱和脂肪酸
        table.addCell(myCellTittle("饱和脂肪酸(g)", 0));
        cell = myCell(examMap.get("NU0100021").getItemString() + examMap.get("NU0100021").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100021").getItemRefValue());
        table.addCell(cell);
        // 膳食纤维
        table.addCell(myCellTittle("膳食纤维(g)", 0));
        cell = myCell(examMap.get("NU0100022").getItemString() + examMap.get("NU0100022").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100022").getItemRefValue());
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable4(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell("类别", 20));
        table.addCell(titleCell("实际摄入", 20));
        table.addCell(infoCell("推荐摄入", 3));

        table.addCell(titleCell("", 20));
        table.addCell(titleCell("", 20));
        table.addCell(titleCell("RNI", 20));
        table.addCell(titleCell("AI", 20));
        table.addCell(titleCell("UL", 20));
        // 维生素A
        table.addCell(myCell("维生素A(ugRAE)"));
        cell = myCell(examMap.get("NU0100023").getItemString() + examMap.get("NU0100023").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100023").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100023").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100023").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        // 维生素C
        table.addCell(myCell("维生素C(mg)"));
        cell = myCell(examMap.get("NU0100024").getItemString() + examMap.get("NU0100024").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100024").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100024").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100024").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        // 维生素E
        table.addCell(myCell("维生素E(mga-TE)"));
        cell = myCell(examMap.get("NU0100025").getItemString() + examMap.get("NU0100025").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100025").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100025").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100025").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        // 硫胺素
        table.addCell(myCell("硫胺素(mg)"));
        cell = myCell(examMap.get("NU0100026").getItemString() + examMap.get("NU0100026").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100026").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100026").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100026").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        // 核黄素
        table.addCell(myCell("核黄素(mg)"));
        cell = myCell(examMap.get("NU0100027").getItemString() + examMap.get("NU0100027").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100027").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100027").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100027").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        // 烟酸
        table.addCell(myCell("烟酸(mg)"));
        cell = myCell(examMap.get("NU0100028").getItemString() + examMap.get("NU0100028").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100028").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100028").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100028").getItemRefValue().split("~")[2]);
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable5(PdfPTable table) throws DocumentException {
        PdfPCell cell;
        table.addCell(titleCell("类别", 20));
        table.addCell(titleCell("实际摄入", 20));
        table.addCell(infoCell("推荐摄入", 3));

        table.addCell(titleCell("", 20));
        table.addCell(titleCell("", 20));
        table.addCell(titleCell("RNI", 20));
        table.addCell(titleCell("AI", 20));
        table.addCell(titleCell("UL", 20));
        //
        table.addCell(myCell("钙(mg)"));
        cell = myCell(examMap.get("NU0100029").getItemString() + examMap.get("NU0100029").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100029").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100029").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100029").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        //
        table.addCell(myCell("钾(mg)"));
        cell = myCell(examMap.get("NU0100030").getItemString() + examMap.get("NU0100030").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100030").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100030").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100030").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        //
        table.addCell(myCell("钠(mg)"));
        cell = myCell(examMap.get("NU0100031").getItemString() + examMap.get("NU0100031").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100031").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100031").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100031").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        //
        table.addCell(myCell("镁(mg)"));
        cell = myCell(examMap.get("NU0100032").getItemString() + examMap.get("NU0100032").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100032").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100032").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100032").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        //
        table.addCell(myCell("铁(mg)"));
        cell = myCell(examMap.get("NU0100033").getItemString() + examMap.get("NU0100033").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100033").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100033").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100033").getItemRefValue().split("~")[2]);
        table.addCell(cell);
        //
        table.addCell(myCell("锌(mg)"));
        cell = myCell(examMap.get("NU0100034").getItemString() + examMap.get("NU0100034").getItemResult());
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100034").getItemRefValue().split("~")[0]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100034").getItemRefValue().split("~")[1]);
        table.addCell(cell);
        cell = myCell(examMap.get("NU0100034").getItemRefValue().split("~")[2]);
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTableHead6(PdfPTable table, String content) throws DocumentException {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setFixedHeight(25f);
        table.addCell(cell);
        table.setSpacingAfter(5f);
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

    public PdfPCell infoCell(String content, Integer type) {
        PdfPCell cell = null;
        if (type == 1) {
            cell = utils.baseCell(content, utils.titleFont);
            cell.setBackgroundColor(utils.lightThiredRedColor);
            cell.setBorderColor(utils.whiteColor);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }
        if (type == 2) {
            cell = utils.baseCell(content, utils.contentFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }
        if (type == 3) {
            cell = utils.baseCell(content, utils.titleFont);
            cell.setBackgroundColor(utils.lightThiredRedColor);
            cell.setBorderColor(utils.whiteColor);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setFixedHeight(20);// 设置行高为自适应
            cell.setColspan(3);
        }

        return cell;
    }

    private PdfPCell titleCell(String content, float height) {
        PdfPCell cell = utils.baseCell(content, utils.titleFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(height);// 设置行高为自适应
        return cell;
    }

    private PdfPCell myCellTittle(String content, float height) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(height);// 设置行高为自适应
        cell.setLeading(0.5f, 1.5f);// 设置行间距
        cell.setPaddingBottom(6f);// 设置下边距
        return cell;
    }

    private PdfPCell myCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setLeading(0.5f, 1.5f);// 设置行间距
        cell.setPaddingBottom(6f);// 设置下边距
        return cell;
    }

    private PdfPCell myCellR(String content, float height) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setFixedHeight(height);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell myCellInfo(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setFixedHeight(0);
        cell.setLeading(0.5f, 1.5f);// 设置行间距
        cell.setPaddingBottom(3f);// 设置下边距
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        return cell;
    }

    private void getSupWeek(String content) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String[] a = content.split("\\+");
        int sta = 375;
        int len = a[0].length() > 1 ? 13 : 9;
        document.add(utils.createParagraph(a[0], 10, utils.lightThiredRedColor, sta, -10, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.lightThiredRedColor, sta + len, -5, 13));
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
}
