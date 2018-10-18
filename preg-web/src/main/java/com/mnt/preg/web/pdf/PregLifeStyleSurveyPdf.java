
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
 * 补充剂安全剂量评估报告
 * 
 * @author xdc
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-28 xdc 初版
 */
@Component
public class PregLifeStyleSurveyPdf extends AbstractPdf<PregDietReportPojo> {

    private Map<String, ExamItemPojo> examMap = new HashMap<String, ExamItemPojo>();

    /**
     * 生成报告
     * 
     * @author xdc
     * @throws DocumentException
     */
    @Override
    public void handler(PregDietReportPojo pdfData) throws DocumentException {
        document.newPage();
        examMap = pdfData.getExamMap();

        float[] titleWidth = {0.38f, 0.62f};
        addContentTableHead0(utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 40f, 0), pdfData);

        // 基本信息
        document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 0f));
        float[] table1Width = new float[] {0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f};
        addContentTable(utils.createTable(table1Width, Element.ALIGN_LEFT, 100f, 11f, 0), pdfData);

        // 添加主题内容
        String[] titleArr = new String[] {"饮食情况", "运动情况", "睡眠情况", "心理情况", "烟酒情况", "环境情况"};
        addContentMain(titleArr);

        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(pdfData);
    }

    /**
     * 标题
     * 
     * @author xdc
     * @param table
     * @throws DocumentException
     */
    private void addContentTableHead0(PdfPTable table, PregDietReportPojo pdfData) throws DocumentException {
        addImgLogo(table, pdfData);
        PdfPCell cell = utils.baseCell("孕期生活方式调查报告", utils.reportFont);
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
    private void addContentTable(PdfPTable table, PregDietReportPojo lifeStyleVo) throws DocumentException {
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
     * 添加主题内容
     * 
     * @author xdc
     * @param titleArr
     * @throws DocumentException
     */
    private void addContentMain(String[] titleArr) throws DocumentException {
        int index = 0;
        for (String title : titleArr) {
            addUnitTemplate(title, examMap.get("life0000" + String.format("%03d", ++index)).getItemString(),
                    examMap.get("life0000" + String.format("%03d", ++index)).getItemString(),
                    examMap.get("life0000" + String.format("%03d", ++index)).getItemString());
        }
    }

    /**
     * 生成标题，正常，异常，建议模块的模板
     * 
     * @author xdc
     * @param title
     * @param normal
     * @param exceptionString
     * @param suggestion
     * @throws DocumentException
     */
    private void addUnitTemplate(String title, String normal, String exceptString, String suggestion)
            throws DocumentException {
        addTitleTable(title, Element.ALIGN_CENTER, 100);
        PdfPTable table = utils.createTable(1, Element.ALIGN_CENTER, 100f, 40f, 0);
        table.addCell(myCell("正常：" + normal, 3));
        table.addCell(myCell("异常：" + exceptString, 3));
        table.addCell(myCell("建议：" + suggestion, 3));
        table.setSpacingAfter(20f);
        document.add(table);
    }

    /**************************************** 自定义工具 *******************************************/
    private void addImgLogo(PdfPTable table, PregDietReportPojo pdfData) throws DocumentException {
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + pdfData.getInsId() + PublicConstant.logo2;
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
        if (align == Element.ALIGN_RIGHT) {
            titleWidth = new float[] {0.05f, 0.95f};
        } else {
            titleWidth = new float[] {0.026f, 0.95f};
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
            cell.setHorizontalAlignment(PdfPCell.ALIGN_TOP);
            cell.setBorderColor(utils.whiteColor);// 边颜色
            cell.setBorderWidth(0);
            cell.setFixedHeight(0);
            cell.setLeading(0, 1.5f);// 设置文本行间距
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
