
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 孕期膳食频率调查
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-28 xdc 初版
 */
@Component
public class DietaryFrequencyPdf extends AbstractPdf<PregDietReportPojo> {

    private Map<String, ExamItemPojo> examMap = new HashMap<String, ExamItemPojo>();

    /**
     * 生成报告
     * 
     * @author xdc
     * @throws DocumentException
     */
    @Override
    public void handler(PregDietReportPojo lifeStyleVo) throws DocumentException {
        document.newPage();
        examMap = lifeStyleVo.getExamMap();

        float[] titleWidth = {0.38f, 0.62f};
        addContentTableHead0(utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 40f, 0), lifeStyleVo);

        // 基本信息
        document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 0f));
        float[] table1Width = new float[] {0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f};
        addContentInfo(utils.createTable(table1Width, Element.ALIGN_LEFT, 100f, 11f, 0), lifeStyleVo);

        // 平均一日膳食分析
        addTitleTable("平均一日膳食分析（未计算烹饪流失）", PdfPCell.ALIGN_LEFT, 49);
        addContent1();

        // 膳食占比
        addTitleTable("膳食占比", PdfPCell.ALIGN_LEFT, 49);
        addContent2();

        // 左右分割线
        document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, -491f));

        // 平均一日总能量
        addTitleTable("平均一日总能量（膳食频率调查得出）", PdfPCell.ALIGN_RIGHT, 49);
        addContent3();

        // 产能营养素
        addTitleTable("产能营养素分析", PdfPCell.ALIGN_RIGHT, 49);
        addContent4();

        // 膳食中可能摄入不足的营养素
        addTitleTable("膳食中可能摄入不足的营养素有", PdfPCell.ALIGN_RIGHT, 49);
        addContent5();

        // 营养食品/医学食品/特膳食品
        addTitleTable("营养食品/医学食品/特膳食品", PdfPCell.ALIGN_RIGHT, 49);
        addContent6();

        // 摄入频率记录
        addTitleTable("摄入频率记录", PdfPCell.ALIGN_CENTER, 100);
        addContent7();

        // 设置报告头
        String content = "提示：本报告推荐值参考健康孕妇标准，如合并疾病，请遵循医嘱。";
        addContentTableHead8(utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0), content);

        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(lifeStyleVo);
    }

    /**
     * 标题
     * 
     * @author xdc
     * @param table
     * @throws DocumentException
     */
    private void addContentTableHead0(PdfPTable table, PregDietReportPojo lifeStyleVo) throws DocumentException {
        addImgLogo(table, lifeStyleVo);
        PdfPCell cell = utils.baseCell("孕期膳食频率调查报告", utils.reportFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightThiredRedColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(25f);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);
        table.setSpacingAfter(5f);
        document.add(table);
    }

    /**
     * 基本信息
     * 
     * @author xdc
     * @param table
     * @throws DocumentException
     */
    private void addContentInfo(PdfPTable table, PregDietReportPojo lifeStyleVo) throws DocumentException {
        table.addCell(myCell("ID", 0));
        table.addCell(myCell(lifeStyleVo.getCustomer().getCustPatientId(), 1));
        table.addCell(myCell("姓名", 0));
        table.addCell(myCell(lifeStyleVo.getCustomer().getCustName(), 1));
        table.addCell(myCell("年龄", 0));
        table.addCell(myCell("" + lifeStyleVo.getCustomer().getCustAge(), 1));
        table.addCell(myCell("孕前体重", 0));
        table.addCell(myCell(lifeStyleVo.getCustomer().getCustWeight().toString() + "kg", 1));
        table.addCell(myCell("身高", 0));
        table.addCell(myCell(lifeStyleVo.getCustomer().getCustHeight().toString() + "cm", 1));
        table.addCell(myCell("孕前BMI", 0));
        table.addCell(myCell(lifeStyleVo.getCustomer().getCustBmi().toString(), 1));
        table.addCell(myCell("孕周数", 0));
        table.addCell(myCell("", 1));
        table.addCell(myCell("胎数", 0));
        table.addCell(myCell(lifeStyleVo.getPregnancyArchives().getEmbryoNum(), 1));
        document.add(table);
        getSupWeek(lifeStyleVo.getDiagnosis().getDiagnosisGestationalWeeks());
    }

    /**
     * 平均一日膳食分析
     * 
     * @author xdc
     * @throws DocumentException
     */
    private void addContent1() throws DocumentException {
        PdfPTable table = utils.createTable(3, Element.ALIGN_LEFT, 49, 10f, 0);
        table.addCell(myCell("类别", 6));
        table.addCell(myCell("实际摄入量", 6));
        table.addCell(myCell("推荐摄入量", 6));

        for (int i = 1; i <= 11; i++) {
            // 一共13行，每行三个单位格，分别是 数据名（数据单位）、数据的值 + 数据的结论、数据的推荐摄入
            ExamItemPojo item = examMap.get("dietary000" + String.format("%03d", i));
            table.addCell(myCell(item.getItemName() + "(" + item.getItemUnit() + ")", 6));
            table.addCell(myCell(item.getItemString() + item.getItemResult(), 7));
            table.addCell(myCell(item.getItemRefValue(), 7));
        }
        document.add(table);
    }

    /**
     * 膳食占比
     * 
     * @author xdc
     * @throws DocumentException
     */
    private void addContent2() throws DocumentException {
        PdfPTable table = utils.createTable(3, Element.ALIGN_LEFT, 49, 10f, 0);
        table.addCell(myCell("类别", 6));
        table.addCell(myCell("实际摄入量", 6));
        table.addCell(myCell("推荐摄入量", 6));

        for (int i = 0; i < 4; i++) {
            // 一共4行，每行三个单位格，分别是 数据名（数据单位）、数据的值 + 数据的结论、数据的推荐摄入
            ExamItemPojo item = examMap.get("dietary000" + String.format("%03d", 14 + i));
            table.addCell(myCell(item.getItemName(), 6));
            table.addCell(myCell(item.getItemString() + item.getItemResult(), 7));
            table.addCell(myCell(item.getItemRefValue(), 7));
        }

        document.add(table);
    }

    /**
     * 平均一日总能量
     * 
     * @author xdc
     * @throws DocumentException
     */
    private void addContent3() throws DocumentException {
        PdfPTable table1 = utils.createTable(3, Element.ALIGN_RIGHT, 49, 10f, 0);
        table1.addCell(myCell("类别", 4));
        table1.addCell(myCell("实际总能量", 4));
        table1.addCell(myCell("推荐总能量", 4));

        ExamItemPojo item = examMap.get("dietary000018");
        table1.addCell(myCell("平均一日能量（kcal）", 4));
        table1.addCell(myCell(item.getItemString() + item.getItemResult(), 5));
        table1.addCell(myCell(item.getItemRefValue(), 5));
        document.add(table1);

        PdfPTable table2 = utils.createTable(3, Element.ALIGN_RIGHT, 49, 10f, 0);
        table2.addCell(myCell("普通食物能量占比", 4));
        table2.addCell(myCell("滋补品能量占比", 4));
        table2.addCell(myCell("零食能量占比", 4));

        table2.addCell(myCell(examMap.get("dietary000019").getItemString(), 5));
        table2.addCell(myCell(examMap.get("dietary000020").getItemString(), 5));
        table2.addCell(myCell(examMap.get("dietary000021").getItemString(), 5));
        document.add(table2);
    }

    /**
     * 产能营养素
     * 
     * @author xdc
     * @throws DocumentException
     */
    private void addContent4() throws DocumentException {
        PdfPTable table = utils.createTable(3, Element.ALIGN_RIGHT, 49, 10f, 0);
        table.addCell(myCell("类别", 4));
        table.addCell(myCell("实际摄入量", 4));
        table.addCell(myCell("推荐摄入量", 4));

        for (int i = 0; i < 6; i++) {
            // 一共6行，每行三个单位格，分别是 数据名（数据单位）、数据的值 + 数据的结论、数据的推荐摄入
            ExamItemPojo item = examMap.get("dietary000" + String.format("%03d", 22 + i));
            String unit = StringUtils.isEmpty(item.getItemUnit()) ? "" : "（" + item.getItemUnit() + "）";
            table.addCell(myCell(item.getItemName() + unit, 4));
            table.addCell(myCell(item.getItemString() + item.getItemResult(), 5));
            table.addCell(myCell(item.getItemRefValue(), 5));
        }

        document.add(table);
    }

    /**
     * 膳食中可能摄入不足的营养素
     * 
     * @author xdc
     * @throws DocumentException
     */
    private void addContent5() throws DocumentException {
        PdfPTable table = utils.createTable(1, Element.ALIGN_RIGHT, 49, 10f, 0);
        String index = examMap.get("dietary000028").getItemResult();
        table.addCell(myCell(StringUtils.isEmpty(index) ? "无" : index, 3));
        document.add(table);
    }

    /**
     * 营养食品/医学食品/特膳食品
     * 
     * @author xdc
     * @throws DocumentException
     */
    private void addContent6() throws DocumentException {
        PdfPTable table = utils.createTable(1, Element.ALIGN_RIGHT, 49, 10f, 0);
        String index = examMap.get("dietary000047").getItemString();
        table.addCell(myCell(StringUtils.isEmpty(index) ? "无" : index, 3));
        document.add(table);
    }

    /**
     * 摄入频率记录
     * 
     * @author xdc
     * @throws DocumentException
     */
    private void addContent7() throws DocumentException {
        PdfPTable table = utils.createTable(6, Element.ALIGN_RIGHT, 100, 5f, 0);
        // 每两行看做一大行，每大行中含两小行，一共3大行
        for (int i = 0; i < 3; i++) {
            // 每行6列，首先是标题，然后是内容
            for (int j = 0; j < 6; j++) {
                table.addCell(myCell(examMap.get("dietary000" + String.format("%03d", 29 + j + i * 6)).getItemName(), 6));
            }
            for (int j = 0; j < 6; j++) {
                table.addCell(myCell(examMap.get("dietary000" + String.format("%03d", 29 + j + i * 6)).getItemString(),
                        7));
            }
        }
        document.add(table);
    }

    private void addContentTableHead8(PdfPTable table, String content) throws DocumentException {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setFixedHeight(20f);
        table.addCell(cell);
        table.setSpacingAfter(0f);
        document.add(table);
    }

    /**************************************** 自定义工具 *******************************************/
    private void addImgLogo(PdfPTable table, PregDietReportPojo lifeStyleVo) throws DocumentException {
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + lifeStyleVo.getInsId() + PublicConstant.logo2;
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

    /**
     * 标题工具
     * 
     * @author xdc
     * @param titleName
     * @param align
     * @param recent
     * @throws DocumentException
     */
    private void addTitleTable(String titleName, int align, float recent) throws DocumentException {
        PdfPCell cell;
        float[] titleWidth = null;
        // 控制标题前面的方块大小
        if (recent == 100) {
            titleWidth = new float[] {0.025f, 0.95f};
        } else {
            titleWidth = new float[] {0.05f, 0.95f};
        }
        PdfPTable titleTable = new PdfPTable(titleWidth);
        titleTable.setHorizontalAlignment(align);
        titleTable.setWidthPercentage(recent);
        titleTable.setSpacingAfter(5f);
        titleTable.addCell(myCell(" ", 2));
        cell = utils.baseCell(titleName, utils.infoFont);
        cell.setFixedHeight(18f);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(utils.whiteColor);
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    /**
     * 通过type控制不同样式的cell
     * 
     * @author xdc
     * @param content
     * @param type
     * @return
     */
    private PdfPCell myCell(String content, int type) {
        PdfPCell cell = null;
        if (type == 0) {
            cell = utils.baseCell(content, utils.titleFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBackgroundColor(utils.lightThiredRedColor);
            cell.setBorderColor(utils.lightPinkColor);// 边颜色
            cell.setBorderWidth(0.5f);
        } else if (type == 1) {
            cell = utils.baseCell(content, utils.contentFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorderColor(utils.lightPinkColor);// 边颜色
            cell.setBorderWidth(0.5f);
        } else if (type == 2) {
            cell = utils.baseCell(content, utils.titleFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBackgroundColor(utils.lightThiredRedColor);
            cell.setBorderColor(utils.lightPinkColor);
        } else if (type == 3) {
            cell = utils.baseCell(content, utils.contentFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
            cell.setLeading(0, 1.5f);
            cell.setBackgroundColor(utils.whiteColor);
            cell.setBorderColor(utils.lightPinkColor);
            cell.setFixedHeight(42.5f);
        } else if (type == 4) {
            cell = utils.baseCell(content, utils.titleFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBackgroundColor(utils.lightThiredRedColor);
            cell.setBorderColor(utils.lightPinkColor);// 边颜色
            cell.setBorderWidth(0.5f);
            cell.setFixedHeight(24);
        } else if (type == 5) {
            cell = utils.baseCell(content, utils.contentFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorderColor(utils.lightPinkColor);// 边颜色
            cell.setBorderWidth(0.5f);
            cell.setFixedHeight(24);
        } else if (type == 6) {
            cell = utils.baseCell(content, utils.titleFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBackgroundColor(utils.lightThiredRedColor);
            cell.setBorderColor(utils.lightPinkColor);// 边颜色
            cell.setBorderWidth(0.5f);
            cell.setFixedHeight(25);
        } else if (type == 7) {
            cell = utils.baseCell(content, utils.contentFont);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorderColor(utils.lightPinkColor);// 边颜色
            cell.setBorderWidth(0.5f);
            cell.setFixedHeight(25);
        }
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
        document.add(utils.createParagraph(a[0], 10, utils.darkGrayColor, sta, -15, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.darkGrayColor, sta + len, -5, 15));
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
        PdfPTable table2 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 0, 0);
        table2.addCell(myCellWhiteBorder(
                "执行医生/助理：" + (diagnosisItem.getInspectUserName() == null ? "" : diagnosisItem.getInspectUserName()),
                PdfPCell.ALIGN_LEFT, 0));
        table2.addCell(myCellWhiteBorder("报告日期：" + time, PdfPCell.ALIGN_RIGHT, 0));
        document.add(table2);
    }
}
