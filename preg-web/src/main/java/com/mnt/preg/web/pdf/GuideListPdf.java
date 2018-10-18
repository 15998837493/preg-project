
package com.mnt.preg.web.pdf;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfCell;
import com.mnt.preg.examitem.pojo.GuideListReportPojo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 引导单pdf报告
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
@Component
public class GuideListPdf extends AbstractPdf<GuideListReportPojo> {

    @Override
    public void handler(GuideListReportPojo pdfData) throws DocumentException {
        // 标题
        setImgAndTitle("孕期营养评价结论引导单", pdfData);
        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 1f, 0), 5f, utils.lightGrayColor, "");
        // 基本信息
        float[] table0Width = new float[] {0.34f, 0.33f, 0.33f};
        setMyMessage(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 1f, 0), pdfData);// 添加内容
        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 1f, 0), 5f, utils.lightGrayColor, "");
        // 产科诊断
        float[] tableWidth = {0.11f, 0.89f};
        String[] str = {"产科诊断：", " 高危妊娠：是 / 否"};
        setDiffContent(utils.createTable(tableWidth, Element.ALIGN_LEFT, 100f, 5f, 0), str);
        setHr(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), 60f, utils.grayWhiteColor, "");
        float[] diseaseWidth = new float[] {0.5f, 0.5f};
        // 妊娠期代谢异常风险因素 妊娠期营养不良风险因素
        setDisease(utils.createTable(diseaseWidth, Element.ALIGN_CENTER, 100f, 1f, 0), pdfData, 1);// 添加内容
        // 人体成分异常结论：
        String[] str4 = {"人体成分异常结论："};
        setDiffContent(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), str4);
        setDetails(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), pdfData.getInbody());// 人体成分按文档要求：不做显示--，做了没有值显示正常(由service做判断，此处必有值)
        // 可能存在的膳食结构风险 可能存在的致病饮食风险
        setDisease(utils.createTable(diseaseWidth, Element.ALIGN_CENTER, 100f, 1f, 0), pdfData, 2);// 添加内容
        // 妊娠期膳食摄入问题：
        String[] str7 = {"妊娠期膳食摄入问题："};
        setDiffContent(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), str7);
        setDetails(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), getNull(pdfData.getFoodQuestion()));
        // 妊娠期体能消耗情况：
        String[] str8 = {"妊娠期体能消耗情况："};
        setDiffContent(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), str8);
        setDetails(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), getNull(pdfData.getRup()));
        // 营养咨询需求 过敏情况
        setDisease(utils.createTable(diseaseWidth, Element.ALIGN_CENTER, 100f, 1f, 0), pdfData, 3);// 添加内容
        // 处置建议：
        String[] str12 = {"处置建议："};
        setDiffContent(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), str12);
        setDetails(utils.createTable(1, Element.ALIGN_LEFT, 100f, 8f, 0), " ");
        // 转诊建议：
        String[] str11 = {"转诊建议："};
        setDiffContent(utils.createTable(1, Element.ALIGN_LEFT, 100f, 1f, 0), str11);
        setDetails(utils.createTable(1, Element.ALIGN_LEFT, 100f, 8f, 0), pdfData.getSuggest());// 此处必有值
        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(pdfData);
    }

    // **************************************************设置报告明细********************************************************

    /**
     * 设置图片+标题
     * 
     * @author dhs
     * @throws DocumentException
     */
    private void setImgAndTitle(String title, GuideListReportPojo pdfData) throws DocumentException {
        float[] principleWidth = {0.3f, 0.7f};
        PdfPTable table = utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + pdfData.getInsId() + PublicConstant.logo1;
        utils.addImage(document, basepath, 22f, 800f, 15);
        PdfPCell cell = utils.baseCell("", utils.reportFont);
        cell.setBorderColor(utils.whiteColor);
        cell.setVerticalAlignment(PdfCell.ALIGN_BOTTOM);
        table.addCell(cell);
        PdfPCell cell2 = utils.baseCell(title, new Font(utils.createFont(), 20, Font.NORMAL,
                utils.darkGrayColor));
        cell2.setBorderColor(utils.whiteColor);
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell2.setFixedHeight(28f);
        cell2.setVerticalAlignment(PdfCell.ALIGN_TOP);
        table.addCell(cell2);
        document.add(table);
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
            table.setSpacingBefore(15f);
        }
        document.add(table);
    }

    /**
     * 设置个人信息
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private void setMyMessage(PdfPTable table, GuideListReportPojo pdfData) throws DocumentException {
        table.addCell(myCell("姓名："
                + (pdfData.getCustomer().getCustName() == null ? "" : pdfData.getCustomer().getCustName()), 2));
        table.addCell(myCell("年龄："
                + (pdfData.getDiagnosis().getDiagnosisCustAge() == null ? "" : pdfData.getDiagnosis()
                        .getDiagnosisCustAge()), 2));
        table.addCell(myCell("科室：", 2));// 科室是自己填的，没有数据来源
        table.addCell(myCell("ID："
                + (pdfData.getCustomer().getCustPatientId() == null ? "" : pdfData.getCustomer().getCustPatientId()), 2));
        table.addCell(myCell("本次妊娠："
                + (pdfData.getPregnancyArchives().getEncyesisSituation() == null ? "" : pdfData.getPregnancyArchives()
                        .getEncyesisSituation()), 2));
        table.addCell(myCell("胎数："
                + (pdfData.getPregnancyArchives().getEmbryoNum() == null ? "" : pdfData.getPregnancyArchives()
                        .getEmbryoNum()), 2));
        table.addCell(myCell("身高：" + this.addUnit(pdfData.getDiagnosis().getDiagnosisCustHeight(), "cm"), 2));
        table.addCell(myCell("孕前体重：" + this.addUnit(pdfData.getPregnancyArchives().getWeight(), "kg"), 2));
        table.addCell(myCell("孕周数：", 2));
        getSupWeek(pdfData.getDiagnosis().getDiagnosisGestationalWeeks());
        document.add(table);
    }

    /**
     * 设置病情明细
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private void setDisease(PdfPTable table, GuideListReportPojo pdfData, int type) throws DocumentException {
        if (type == 1) {
            table.addCell(myCell("妊娠期代谢异常风险因素：", 6));
            table.addCell(myCell("妊娠期营养不良风险因素：", 6));
            table.addCell(myCell("病史：" + getNull(pdfData.getMetabolicBingshi()), 5));
            table.addCell(myCell("病史：" + getNull(pdfData.getNutritiousBingshi()), 5));
            table.addCell(myCell("既往妊娠并发症及分娩史：" + getNull(pdfData.getMetabolicHistory()), 5));
            table.addCell(myCell("既往妊娠并发症及分娩史：" + getNull(pdfData.getNutritiousHistory()), 5));
            table.setSpacingAfter(-8f);
        } else if (type == 2) {
            table.addCell(myCell("可能存在的膳食结构风险：", 6));
            table.addCell(myCell("可能存在的致病饮食风险：", 6));
            table.addCell(myCell(getNull(pdfData.getFoodRisk()), 5));
            table.addCell(myCell(getNull(pdfData.getIllRisk()), 5));
            table.setSpacingAfter(-8f);
            table.setSpacingBefore(8f);
        } else if (type == 3) {
            table.addCell(myCell("营养咨询需求：", 6));
            table.addCell(myCell("过敏情况：", 6));
            table.addCell(myCell(getNull(pdfData.getNutritionalRequirements()), 5));
            table.addCell(myCell(getNull(pdfData.getAllergic()), 5));
            table.setSpacingAfter(-8f);
            table.setSpacingBefore(8f);
        }
        document.add(table);
    }

    /**
     * 设置详细的病情
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private void setDetails(PdfPTable table, String content) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            content = "无";
        }
        table.addCell(myCell(content, 5));
        table.setSpacingAfter(-8f);
        document.add(table);
    }

    /**
     * 设置页尾
     * 
     * @author dhs
     * @throws DocumentException
     */
    private void setPageLast(GuideListReportPojo pdfData) throws DocumentException {
        // PregDiagnosisPojo diagnosis = pdfData.getDiagnosis();
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
        PdfPTable table2 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, -3f, 0);
        table2.addCell(myCellWhiteBorder(
                "报告医生：" + pdfData.getDiagnosis().getDiagnosisUserName(),
                PdfPCell.ALIGN_LEFT, 0));
        table2.addCell(myCellWhiteBorder("报告日期：" + time, PdfPCell.ALIGN_RIGHT, 0));
        document.add(table2);
        PdfPTable table3 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 6f, 0);
        table3.addCell(myCellWhiteBorder("此报告仅供临床医生参考，报告医生签字有效", PdfPCell.ALIGN_LEFT, 1));
        table3.addCell(myCellWhiteBorder("", PdfPCell.ALIGN_RIGHT, 0));
        document.add(table3);
    }

    // ***************************************************工具方法**********************************************************

    /**
     * 自定义cell
     * 
     * @author dhs
     */
    private PdfPCell myCell(String content, Integer type) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 9, Font.NORMAL, utils.darkGrayColor));
        cell.setBorderColor(utils.lightGrayColor);
        if (type == 0) {// 大标题，字体加粗放大居中
            cell = utils.baseCell(content, new Font(utils.createFont(), 22, Font.BOLD, utils.darkGrayColor));
            cell.setBorderColor(utils.whiteColor);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setFixedHeight(25f);
        } else if (type == 1) {// 小标题，字体放大居左
            cell = utils.baseCell(content, new Font(utils.createFont(), 20, Font.NORMAL, utils.darkGrayColor));
            cell.setBorderColor(utils.whiteColor);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setFixedHeight(25f);
        } else if (type == 2) {// 无边居中
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setBorderColor(utils.whiteColor);// 边颜色
        } else if (type == 3) {// 居左无边放大
            cell = utils.baseCell(content, new Font(utils.createFont(), 10, Font.NORMAL, utils.darkGrayColor));
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        } else if (type == 4) {// 居左无边放大加粗
            cell = utils.baseCell(content, new Font(utils.createFont(), 11, Font.BOLD, utils.darkGrayColor));
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        } else if (type == 5) {// 行高自适应
            cell = utils.baseCell(content, new Font(utils.createFont(), 10, Font.NORMAL, utils.darkGrayColor));
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setFixedHeight(0);// 设置行高自适应
            cell.setLeading(1, 1.2f);// 设置行间距
        } else if (type == 6) {// 与下边距缩短
            cell = utils.baseCell(content, new Font(utils.createFont(), 11, Font.BOLD, utils.darkGrayColor));
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setPaddingBottom(-5f);
        }
        return cell;
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
     * 设置同一行不同样式字体
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    public void setDiffContent(PdfPTable table, String[] content) throws DocumentException {
        table.setSpacingBefore(8);
        if (content.length == 1) {
            table.addCell(myCell(content[0], 6));
            document.add(table);
        } else {
            table.addCell(myCell(content[0], 4));
            table.addCell(myCell(content[1], 3));
            document.add(table);
        }
    }

    /**
     * 生成角标形式的孕周
     * 
     * @author dhs
     * @param content
     * @throws DocumentException
     */
    private void getSupWeek(String content) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String[] a = content.split("\\+");
        int sta = 410;
        int len = a[0].length() > 1 ? 13 : 9;
        document.add(utils.createParagraph(a[0], 10, utils.darkGrayColor, sta, 52, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.darkGrayColor, sta + len, -5, -44));
    }

    /**
     * 如果为空或者为null，设置为"无"
     * 
     * @author dhs
     * @param content
     * @throws DocumentException
     */
    private String getNull(String content) throws DocumentException {
        String str = content;
        if (StringUtils.isBlank(content)) {
            str = "无";
        }
        return str;
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

}
