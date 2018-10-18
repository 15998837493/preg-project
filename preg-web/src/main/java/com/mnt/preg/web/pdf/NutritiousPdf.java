
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.examitem.pojo.NutritiousReportPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 快速营养调查pdf报告
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
@Component
public class NutritiousPdf extends AbstractPdf<NutritiousReportPojo> {

    @Override
    public void handler(NutritiousReportPojo pdfData) throws DocumentException {

        // 设置报告头
        addContentTableHead0(document, pdfData);

        // 基本信息
        document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 0f));
        addContentTable1(utils.createTable(8, Element.ALIGN_LEFT, 100f, 10f, 0), pdfData);

        // 营养筛查情况
        addTitleTable2("营养筛查情况", Element.ALIGN_LEFT, 100);

        // 表格
        setTables(document, pdfData);

        // 膳食结构风险
        addTitleTable2("膳食结构风险", Element.ALIGN_LEFT, 100);

        addContent(document, pdfData.getFoodRisk());

        addTitleTable2("致病饮食风险", Element.ALIGN_LEFT, 100);

        addContent(document, pdfData.getIllRisk());

        addTitleTable2("营养咨询需求", Element.ALIGN_LEFT, 100);

        addContent(document, pdfData.getNutritionalRequirements());

        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(pdfData);

    }

    // **************************************************设置报告明细********************************************************

    private void addContentTableHead0(Document document, NutritiousReportPojo pdfData) throws DocumentException {
        float[] principleWidth = {0.38f, 0.62f};
        PdfPTable table = utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + pdfData.getInsId() + PublicConstant.logo2;
        Image img;
        PdfPCell cell = utils.baseCell("", utils.reportFont);
        try {
            File file = new File(basepath);
            if (file.exists()) {
                img = Image.getInstance(basepath);
                cell.setImage(img);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightThiredRedColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setPaddingBottom(5f);// 设置下边距
        cell.setPaddingTop(5f);// 设置上边距
        cell.setFixedHeight(25f);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);
        PdfPCell cell2 = utils.baseCell("孕期快速营养调查报告", utils.reportFont);
        cell2.setBackgroundColor(utils.lightThiredRedColor);
        cell2.setBorderColor(utils.lightThiredRedColor);
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell2.setFixedHeight(25f);
        cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell2);
        table.setSpacingAfter(5f);
        document.add(table);
    }

    private void addContentTable1(PdfPTable table, NutritiousReportPojo pdfData) throws DocumentException {
        table.addCell(myCellRed("ID"));
        table.addCell(myCellWhite(pdfData.getCustomer().getCustPatientId()));
        table.addCell(myCellRed("姓名"));
        table.addCell(myCellWhite(pdfData.getCustomer().getCustName()));
        table.addCell(myCellRed("年龄"));
        table.addCell(myCellWhite(pdfData.getDiagnosis().getDiagnosisCustAge().toString()));
        table.addCell(myCellRed("孕前体重"));
        table.addCell(myCellWhite(this.addUnit(pdfData.getPregnancyArchives().getWeight(), "kg")));
        table.addCell(myCellRed("身高"));
        table.addCell(myCellWhite(this.addUnit(pdfData.getDiagnosis().getDiagnosisCustHeight(), "cm")));
        table.addCell(myCellRed("孕前BMI"));
        table.addCell(myCellWhite(pdfData.getPregnancyArchives().getBmi().toString()));
        table.addCell(myCellRed("孕周数"));
        table.addCell(myCellWhite(""));
        getSupWeek(pdfData.getDiagnosis().getDiagnosisGestationalWeeks());
        table.addCell(myCellRed("胎数"));
        table.addCell(myCellWhite(pdfData.getPregnancyArchives().getEmbryoNum()));
        document.add(table);
    }

    private void addTitleTable2(String titleName, int align, float recent) throws DocumentException {
        PdfPCell cell;
        float[] titleWidth = null;
        if (align == Element.ALIGN_LEFT || align == Element.ALIGN_RIGHT) {
            titleWidth = new float[] {0.02f, 0.95f};
        } else {
            titleWidth = new float[] {0.02f, 0.95f};
        }
        PdfPTable titleTable = new PdfPTable(titleWidth);
        titleTable.setHorizontalAlignment(align);
        titleTable.setWidthPercentage(recent);
        titleTable.setSpacingAfter(5f);
        titleTable.addCell(titleCell(" "));
        cell = utils.baseCell(titleName, utils.infoFont);
        cell.setFixedHeight(18);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(utils.whiteColor);
        cell.setBorderWidth(0);
        cell.setPaddingLeft(10f);// 设置左边距
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    /**
     * 设置信息
     * 
     * @author dhs
     * @param document
     * @param orderDetailList
     * @throws DocumentException
     */
    private void setTables(Document document, NutritiousReportPojo nutritiousSurveyPojo)
            throws DocumentException {
        float[] principleWidth = {0.2f, 0.8f};
        PdfPTable table = utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(myCell("食欲食量", 0));
        table.addCell(myCell(nutritiousSurveyPojo.getEats(), 1));
        table.addCell(myCell("膳食偏好", 0));
        table.addCell(myCell(nutritiousSurveyPojo.getFoodPreference(), 1));
        table.addCell(myCell("膳食摄入", 0));
        table.addCell(myCell(nutritiousSurveyPojo.getFoodIntake(), 1));
        table.addCell(myCell("不良饮食喜好", 0));
        table.addCell(myCell(nutritiousSurveyPojo.getBadDietary(), 1));
        table.addCell(myCell("不良用餐习惯", 0));
        table.addCell(myCell(nutritiousSurveyPojo.getBadHabits(), 1));
        table.addCell(myCell("体能消耗问题", 0));
        table.addCell(myCell(nutritiousSurveyPojo.getStaminaRup(), 1));
        table.addCell(myCell("运动消耗问题", 0));
        table.addCell(myCell(nutritiousSurveyPojo.getSportRup(), 1));
        document.add(table);
    }

    /**
     * 添加内容
     * 
     * @author dhs
     * @param table
     * @throws DocumentException
     */
    private void addContent(Document document, String content) throws DocumentException {
        float[] principleWidth = {1f};
        PdfPTable table = utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(myCell(content, 2));
        document.add(table);
    }

    // ***************************************************工具方法**********************************************************

    /**
     * 自定义cell
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private PdfPCell myCell(String content, Integer type) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 9, Font.NORMAL, utils.darkGrayColor));
        cell.setBorderColor(utils.lightGrayColor);
        if (type == 0) {// 行高固定
            cell = utils.baseCell(content, new Font(utils.createFont(), 10, Font.NORMAL, utils.darkGrayColor));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setFixedHeight(30f);// 设置行高
        } else if (type == 1) {// 行高自适应
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setFixedHeight(0);// 设置行高自适应
            cell.setLeading(1, 1.2f);// 设置行间距
        } else if (type == 2) {// 行高自适应，无边线
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setFixedHeight(0);// 设置行高自适应
            cell.setLeading(1, 1.2f);// 设置行间距
            cell.setBorderColor(utils.whiteColor);
        }
        return cell;
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
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        return cell;
    }

    /**
     * 标题前方块
     * 
     * @author xdc
     * @param content
     * @return
     */
    private PdfPCell titleCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.infoFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.whiteColor);
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
     * 身高、体重之类的单位
     * 
     * @author dhs
     * @param bigDecimal
     * @param unit
     * @throws DocumentException
     */
    private String addUnit(BigDecimal bigDecimal, String unit) throws DocumentException {
        if (bigDecimal != null) {
            return bigDecimal + unit;
        } else {
            return "";
        }
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
    private void setPageLast(NutritiousReportPojo pdfData) throws DocumentException {
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
