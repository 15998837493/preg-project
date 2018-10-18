
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.customer.pojo.PregCourseBookingPojo;
import com.mnt.preg.examitem.pojo.PregDiagnosisSummaryReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalInspectReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisObstetricalPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 病历小结-PDF生成
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-10 zcq 初版
 */
@Component
public class PlanSummaryPdf extends AbstractPdf<PregDiagnosisSummaryReportPojo> {

    /**
     * 设置报告内容
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @throws DocumentException
     */
    @Override
    public void handler(PregDiagnosisSummaryReportPojo summaryVo) throws DocumentException {
        try {
            document.newPage();
            // 设置报告头
            float[] titleWidth = {0.34f, 0.66f};
            addContentTableHead0(utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 50f, 0),
                    JodaTimeTools.toString(summaryVo.getDiagnosisPojo().getDiagnosisDate(), JodaTimeTools.FORMAT_6)
                            + " 营养科", summaryVo);
            addTitleTable("个人信息");// 添加标题
            // 信息栏
            float[] table0Width = new float[] {0.1f, 0.15f, 0.1f, 0.15f, 0.1f, 0.15f, 0.1f, 0.15f};
            addContentTable0(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 5f, 0), summaryVo);// 添加内容
            // 个人信息
            setDiagnosisInfo(document, summaryVo.getDiagnosisPojo(), summaryVo.getDiagnosisObstetricalPojo());
            // 辅助检查异常结果
            List<DiagnosisHospitalInspectReportPojo> clinItemList = summaryVo.getExamItemList();
            if (CollectionUtils.isNotEmpty(clinItemList)) {
                List<DiagnosisHospitalInspectReportPojo> clinItemNewList = new ArrayList<DiagnosisHospitalInspectReportPojo>();
                for (DiagnosisHospitalInspectReportPojo inspectReportPojo : clinItemList) {
                    boolean clinItemFlag = false;
                    if (CollectionUtils.isNotEmpty(inspectReportPojo.getItemList())) {
                        for (DiagnosisHospitalItemPojo itemPojo : inspectReportPojo.getItemList()) {
                            if (StringUtils.isNotBlank(itemPojo.getItemValue())) {
                                clinItemFlag = true;
                                break;
                            }
                        }
                    }
                    if (clinItemFlag) {
                        clinItemNewList.add(inspectReportPojo);
                    }
                }
                if (CollectionUtils.isNotEmpty(clinItemNewList)) {
                    addTitleTable("辅助检查异常结果");// 添加标题
                    // 指标信息
                    setExamItem(document, clinItemNewList);
                }
            }
            // 诊断信息
            // setDiseaseName(document, summaryVo.getDiagnosisPojo());
            // 营养处方
            List<DiagnosisPrescriptionPojo> extenderList = summaryVo.getExtenderList();
            if (CollectionUtils.isNotEmpty(extenderList)
                    || StringUtils.isNotBlank(summaryVo.getDiagnosisPojo().getDiagnosisExtenderDesc())) {
                addTitleTable("营养处方");// 添加标题
                setOrderDetail(document, extenderList, summaryVo.getDiagnosisPojo());
            }
            // 膳食处方
            PregIntervenePlanPojo planVo = summaryVo.getPlanPojo();
            if (planVo != null
                    && (StringUtils.isNotBlank(planVo.getIntakeCaloric()) || StringUtils.isNotBlank(planVo
                            .getDietName()))) {
                addTitleTable("膳食处方");// 添加标题
                setDietInfo(document, planVo, summaryVo.getDiagnosisPojo());
            }
            // 复查与复诊预约
            List<DiagnosisBookingPojo> diagnosisBookingList = summaryVo.getDiagnosisList();
            List<DiagnosisQuotaItemVo> quotaItemList = summaryVo.getFuzhuList();
            if (CollectionUtils.isNotEmpty(diagnosisBookingList) || CollectionUtils.isNotEmpty(quotaItemList)) {
                addTitleTable("复查与复诊预约");// 添加标题
                setFuzhu(document, diagnosisBookingList, quotaItemList);
            }
            // 课程预约信息
            List<PregCourseBookingPojo> courseBookingList = summaryVo.getCourseBookingList();
            if (CollectionUtils.isNotEmpty(courseBookingList)) {
                addTitleTable("课程预约");// 添加标题
                setCourseBooking(document, courseBookingList);
            }

            /*
             * document.add(utils.createParagraph(
             * "门诊日期：" + JodaTimeTools.toString(summaryVo.getDiagnosisPojo().getDiagnosisDate(),
             * JodaTimeTools.FORMAT_6), 8, utils.darkGrayColor, 0, 10, 0));
             * document.add(utils.createParagraph("医师：", 8, utils.darkGrayColor, 460f, 0, 0));
             */

            // 分界线
            setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
            // 页尾
            setPageLast(summaryVo);
        } catch (DocumentException e) {
            throw new DocumentException(e);
        }
    }

    // **************************************************设置报告明细********************************************************
    /**
     * 设置表头
     * 
     * @author zcq
     * @param table
     * @throws DocumentException
     */
    private void addContentTableHead0(PdfPTable table, String content, PregDiagnosisSummaryReportPojo summaryVo)
            throws DocumentException {
        // 添加图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + summaryVo.getInsId() + PublicConstant.logo1;

        Image img;
        PdfPCell cellImg = utils.baseCell("", new Font(utils.createFont(), 18, Font.NORMAL,
                utils.darkGrayColor));
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
        cellImg.setBorderColor(utils.whiteColor);
        cellImg.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellImg.setFixedHeight(40f);
        cellImg.setPaddingBottom(9f);// 设置下边距
        cellImg.setPaddingTop(9f);// 设置上边距
        cellImg.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cellImg);
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 20, Font.BOLD, utils.darkGrayColor));
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(40f);
        table.addCell(cell);
        table.setSpacingAfter(8f);
        document.add(table);
    }

    /**
     * 设置个人信息
     * 
     * @author zcq
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private void addContentTable0(PdfPTable table, PregDiagnosisSummaryReportPojo summaryVo) throws DocumentException {
        CustomerPojo custInfo = summaryVo.getCustomerPojo();
        PregDiagnosisPojo diagnosis = summaryVo.getDiagnosisPojo();
        PregArchivesPojo pregArchivesPojo = summaryVo.getPreArchivePojo();
        table.addCell(infoCell("病案号：", 0));
        table.addCell(infoCell(diagnosis.getDiagnosisCustSikeId(), 0));
        table.addCell(infoCell("ID：", 0));
        table.addCell(infoCell(custInfo.getCustPatientId(), 0));
        table.addCell(infoCell("姓名：", 0));
        table.addCell(infoCell(custInfo.getCustName(), 0));
        table.addCell(infoCell("胎数：", 0));
        table.addCell(infoCell(pregArchivesPojo.getEmbryoNum(), 0));
        table.addCell(infoCell("孕周数：", 0));
        table.addCell(infoCell("", 0));
        table.addCell(infoCell("年龄：", 0));
        table.addCell(infoCell("" + custInfo.getCustAge(), 0));
        table.addCell(infoCell("末次月经：", 0));
        PdfPCell lastYJ = infoCell(pregArchivesPojo.getLmp().toString(), 0);
        lastYJ.setColspan(3);
        table.addCell(lastYJ);
        document.add(table);
        getSupWeek(diagnosis.getDiagnosisGestationalWeeks(), 60);
    }

    /**
     * 生成角标形式的孕周
     * 
     * @author xdc
     * @param content
     * @throws DocumentException
     */
    private void getSupWeek(String content, float left) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String[] a = content.split("\\+");
        int len = a[0].length() > 1 ? 13 : 9;
        document.add(utils.createParagraph(a[0], 9, utils.darkGrayColor, left, -10, 0));
        document.add(utils.createParagraph("+" + a[1], 7, utils.darkGrayColor, left + len, -5, 20));
    }

    /**
     * 第一步：本次就诊信息
     * 
     * @author zcq
     * @param document
     * @param diagnosisVo
     * @throws DocumentException
     */
    private void setDiagnosisInfo(Document document, PregDiagnosisPojo diagnosisVo, PregDiagnosisObstetricalPojo obsPojo)
            throws DocumentException {
        float[] tableWidth = {0.14f, 0.86f};
        PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100f, 10f, 0);

        table.addCell(infoCell("诊断", 0));
        table.addCell(contentCell(diagnosisVo.getDiagnosisDiseases()));
        table.addCell(infoCell("营养主诉", 0));
        table.addCell(contentCell(obsPojo.getObstetricalDiagnosisMain()));
        table.addCell(infoCell("一般检查", 0));
        StringBuffer sf = new StringBuffer();
        if (diagnosisVo.getDiagnosisCustWeight() != null
                && diagnosisVo.getDiagnosisCustWeight().compareTo(new BigDecimal(0)) != 0) {
            sf.append("现体重：" + diagnosisVo.getDiagnosisCustWeight() + " kg");
        }
        if (obsPojo.getObstetricalDiagnosisSystolic() != null
                && obsPojo.getObstetricalDiagnosisSystolic().compareTo(new BigDecimal(0)) != 0) {
            sf.append(" 收缩压：" + obsPojo.getObstetricalDiagnosisSystolic() + " mmHg");
        }
        if (obsPojo.getObstetricalDiagnosisDiastolic() != null
                && obsPojo.getObstetricalDiagnosisDiastolic().compareTo(new BigDecimal(0)) != 0) {
            sf.append(" 舒张压：" + obsPojo.getObstetricalDiagnosisDiastolic() + " mmHg");
        }
        table.addCell(contentCell(sf.toString()));
        table.addCell(infoCell("本次营养评价结论", 0));
        table.addCell(contentCell(diagnosisVo.getDiagnosisInspectResult()));
        document.add(table);
    }

    /**
     * 第三步：实验室指标
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @param number
     * @throws DocumentException
     */
    private void setExamItem(Document document, List<DiagnosisHospitalInspectReportPojo> examItemList)
            throws DocumentException {
        if (CollectionUtils.isNotEmpty(examItemList)) {
            for (DiagnosisHospitalInspectReportPojo pojo : examItemList) {
                if (CollectionUtils.isNotEmpty(pojo.getItemList())) {
                    float[] tableWidth = {0.05f, 0.18f, 0.12f, 0.1f, 0.1f, 0.05f, 0.05f, 0.1f, 0.02f, 0.1f, 0.13f};
                    PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100f, 10f, 1);
                    String title_name = "检验报告：";
                    if (StringUtils.isNotBlank(pojo.getReportName())) {
                        title_name += pojo.getReportName();
                    }
                    PdfPCell name = longContentCell(title_name, 122);
                    name.setColspan(11);
                    table.addCell(name);
                    String title_date = "检验日期：";
                    if (pojo.getReportDate() != null) {
                        title_date += (pojo.getReportDate() + "").substring(0, 10);
                    } else {
                        if (StringUtils.isNotBlank(pojo.getGestationalWeeks())) {
                            if (pojo.getGestationalWeeks().split("\\+")[0].length() == 1) {
                                for (int x = 0; x < 19; x++) {
                                    title_date += " ";
                                }
                            } else {
                                for (int x = 0; x < 18; x++) {
                                    title_date += " ";
                                }
                            }

                        } else {
                            for (int x = 0; x < 10; x++) {
                                title_date += " ";
                            }
                        }
                    }
                    title_date += "  孕周：";
                    int week_length = 0;
                    if (StringUtils.isNotBlank(pojo.getGestationalWeeks())) {
                        title_date += pojo.getGestationalWeeks().split("\\+")[0];
                        week_length = pojo.getGestationalWeeks().split("\\+")[0].length();
                    }
                    PdfPCell date = longContentCell(title_date, 2);
                    date.setColspan(2);
                    table.addCell(date);
                    String title_day = "";
                    if (StringUtils.isNotBlank(pojo.getGestationalWeeks())) {
                        title_day += ("+" + pojo.getGestationalWeeks().split("\\+")[1]);
                    }
                    if (week_length == 1) {
                        table.addCell(tableCell(title_day, PdfPCell.ALIGN_LEFT, utils.darkGrayColor, 7));
                    } else if (week_length == 2) {
                        table.addCell(tableCell(title_day, PdfPCell.ALIGN_LEFT, utils.darkGrayColor, 4));
                    } else {
                        table.addCell(tableCell(title_day, PdfPCell.ALIGN_LEFT, utils.darkGrayColor, 4));
                    }

                    PdfPCell otherCell = longContentCell("", 1);
                    otherCell.setColspan(9);
                    table.addCell(otherCell);
                    table.addCell(infoCell("序号", 1));
                    PdfPCell jianyanCell = infoCell("检验项目", 1);
                    jianyanCell.setColspan(2);
                    table.addCell(jianyanCell);
                    table.addCell(infoCell("结果", 1));
                    table.addCell(infoCell("结论", 1));
                    PdfPCell weekCell = infoCell("上次孕周", 1);
                    weekCell.setColspan(2);
                    table.addCell(weekCell);
                    PdfPCell resultCell = infoCell("上次结果", 1);
                    resultCell.setColspan(2);
                    table.addCell(resultCell);
                    table.addCell(infoCell("单位", 1));
                    table.addCell(infoCell("参考范围", 1));
                    int count = 0;
                    for (DiagnosisHospitalItemPojo itemPojo : pojo.getItemList()) {
                        if (StringUtils.isNotBlank(itemPojo.getItemValue())) {
                            BaseColor color = null;
                            if ("↑".equals(itemPojo.getItemResult()) || "↓".equals(itemPojo.getItemResult())) {
                                color = utils.lightThiredRedColor;
                            }
                            String lastResult = "";
                            if (StringUtils.isNotBlank(itemPojo.getLastItemValue())) {
                                lastResult += itemPojo.getLastItemValue();
                            }
                            if (StringUtils.isNotBlank(itemPojo.getLastItemResult())) {
                                lastResult += itemPojo.getLastItemResult();
                            }
                            table.addCell(tableCell(String.valueOf(++count), PdfPCell.ALIGN_CENTER, null, 1));
                            PdfPCell itemNameCell = tableCell(itemPojo.getItemName(), PdfPCell.ALIGN_LEFT, color, 1);
                            itemNameCell.setColspan(2);
                            table.addCell(itemNameCell);
                            table.addCell(tableCell(itemPojo.getItemValue(), PdfPCell.ALIGN_LEFT, color, 1));
                            table.addCell(tableCell(itemPojo.getItemResult(), PdfPCell.ALIGN_LEFT, color, 1));
                            String week = "";
                            String day = "";
                            if (StringUtils.isNotBlank(itemPojo.getGestationalWeeks())) {
                                week = itemPojo.getGestationalWeeks().split("\\+")[0];
                                day = "+" + itemPojo.getGestationalWeeks().split("\\+")[1];
                            }
                            table.addCell(tableCell(week, PdfPCell.ALIGN_RIGHT, color, 3));
                            table.addCell(tableCell(day, PdfPCell.ALIGN_LEFT, color, 2));
                            table.addCell(tableCell(lastResult, PdfPCell.ALIGN_LEFT, color, 5));
                            table.addCell(tableCell("", PdfPCell.ALIGN_LEFT, color, 6));
                            table.addCell(tableCell(itemPojo.getItemUnit(), PdfPCell.ALIGN_LEFT, color, 1));
                            table.addCell(tableCell(itemPojo.getItemRefValue(), PdfPCell.ALIGN_LEFT, color, 1));
                        }
                    }
                    document.add(table);
                }
            }
        }
    }

    /**
     * 第四步：辅助检查项目
     * 
     * @author zcq
     * @param document
     * @param courseList
     * @throws DocumentException
     */
    private void setFuzhu(Document document, List<DiagnosisBookingPojo> diagnosisBookingList,
            List<DiagnosisQuotaItemVo> quotaItemList)
            throws DocumentException {
        float[] tableWidth = {0.14f, 0.86f};
        PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100f, 10f, 0);

        if (CollectionUtils.isNotEmpty(diagnosisBookingList) && CollectionUtils.isNotEmpty(quotaItemList)) {
            DiagnosisBookingPojo diagnosisPojo = diagnosisBookingList.get(diagnosisBookingList.size() - 1);
            String hints = "";
            String itemNames = "";
            String suggest = "";
            int days = 0;
            for (DiagnosisQuotaItemVo item : quotaItemList) {
                Integer sug = 0;
                if (StringUtils.isNotEmpty(item.getResultsSuggest())) {
                    sug = Integer.valueOf(item.getResultsSuggest());
                }
                days = (sug > days) ? sug : days;
                itemNames += "、" + item.getInspectItemName();
                if (StringUtils.isNotEmpty(item.getReviewHints())) {
                    hints += "、" + item.getReviewHints();
                }
            }
            String sugDate = "";
            if (days == 7) {
                sugDate = JodaTimeTools.before_day_str(diagnosisPojo.getBookingDate(), 7, JodaTimeTools.FORMAT_6);
                suggest = "提示，您的复查检测中包含一周出结果的项目，来院复检时间应不晚于 " + sugDate + "，" + hints.replaceFirst("、", "")
                        + " 下次随诊时请携带复查结果。";
            } else if (days == 1) {
                sugDate = JodaTimeTools.before_day_str(diagnosisPojo.getBookingDate(), 1, JodaTimeTools.FORMAT_6);
                suggest = "提示，您的复查检测中包含次日出结果的项目，来院复检时间应不晚于 " + sugDate + "，" + hints.replaceFirst("、", "")
                        + " 下次随诊时请携带复查结果。";
            } else {
                suggest = "提示，您的复查检测当日化验即可出结果，" + hints.replaceFirst("、", "") + " 来院时需先化验取结果后随诊。";
            }
            table.addCell(infoCell("复查检测", 0));
            table.addCell(infoCell(
                    "复诊建议：" + diagnosisPojo.getBookingSuggest() + " " + diagnosisPojo.getBookingRemark()
                            + "\n" + "复诊时间："
                            + JodaTimeTools.toString(diagnosisPojo.getBookingDate(), JodaTimeTools.FORMAT_6) + "\n"
                            + "复查检测："
                            + itemNames.replaceFirst("、", "") + "\n" + "复查时间：" + suggest, 2));
        } else if (CollectionUtils.isNotEmpty(quotaItemList)) {
            String itemNames = "";
            int days = 0;
            for (DiagnosisQuotaItemVo item : quotaItemList) {
                Integer sug = 0;
                if (StringUtils.isNotEmpty(item.getResultsSuggest())) {
                    sug = Integer.valueOf(item.getResultsSuggest());
                }
                days = (sug > days) ? sug : days;
                itemNames += "、" + item.getInspectItemName();
            }
            table.addCell(infoCell("复查检测", 0));
            table.addCell(infoCell("复查检测：" + itemNames.replaceFirst("、", ""), 2));
        } else {
            DiagnosisBookingPojo diagnosisPojo = diagnosisBookingList.get(diagnosisBookingList.size() - 1);
            table.addCell(infoCell("复查检测", 0));
            table.addCell(infoCell(
                    "复诊建议：" + diagnosisPojo.getBookingSuggest() + " " + diagnosisPojo.getBookingRemark()
                            + "\n" + "复诊时间："
                            + JodaTimeTools.toString(diagnosisPojo.getBookingDate(), JodaTimeTools.FORMAT_6), 2));
        }
        document.add(table);
    }

    /**
     * 课程预约信息
     * 
     * @author zcq
     * @param document
     * @param courseList
     * @throws DocumentException
     */
    private void setCourseBooking(Document document, List<PregCourseBookingPojo> courseList) throws DocumentException {
        float[] dietWidth = {0.2f, 0.2f, 0.6f};
        PdfPTable table = utils.createTable(dietWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(infoCell("课程日期", 1));
        table.addCell(infoCell("课程时间", 1));
        table.addCell(infoCell("课程内容", 1));

        for (PregCourseBookingPojo course : courseList) {
            table.addCell(infoCell(JodaTimeTools.toString(course.getBookingDate(), JodaTimeTools.FORMAT_6), 1));
            table.addCell(infoCell(course.getCourseTime(), 1));
            table.addCell(infoCell(course.getCourseContent(), 2));
        }

        document.add(table);
    }

    /**
     * 第五步：营养处方
     * 
     * @author zcq
     * @param document
     * @param orderDetailList
     * @throws DocumentException
     */
    private void setOrderDetail(Document document, List<DiagnosisPrescriptionPojo> extenderList,
            PregDiagnosisPojo diagnosisPojo)
            throws DocumentException {
        float[] principleWidth = {0.14f, 0.24f, 0.1f, 0.08f, 0.08f, 0.14f, 0.1f, 0.12f};
        PdfPTable table = utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        if (CollectionUtils.isNotEmpty(extenderList)) {
            table.addCell(infoCell("序号", 1));
            table.addCell(infoCell("名称", 1));
            table.addCell(infoCell("单次剂量", 1));
            table.addCell(infoCell("剂量说明", 1));
            table.addCell(infoCell("用法", 1));
            table.addCell(infoCell("频次", 1));
            table.addCell(infoCell("疗程", 1));
            table.addCell(infoCell("状态", 1));
            int count = 0;
            for (DiagnosisPrescriptionPojo detail : extenderList) {
                String status = "";
                BaseColor color = null;
                if (detail.getStatus() == 1) {
                    status = "新增";
                    color = utils.lightThiredRedColor;
                } else if (detail.getStatus() == 2) {
                    status = "继服";
                } else if (detail.getStatus() == 3) {
                    status = "停用";
                }
                table.addCell(myCell((++count) + ""));
                table.addCell(tableCell(detail.getProductName(), PdfPCell.ALIGN_LEFT, color, 1));
                table.addCell(tableCell(detail.getProductDosage() + " " + detail.getProductUnit(), PdfPCell.ALIGN_LEFT,
                        color, 1));
                table.addCell(tableCell(detail.getProductDosageDesc(), PdfPCell.ALIGN_CENTER, color, 1));
                table.addCell(tableCell(detail.getProductUsageMethod(), PdfPCell.ALIGN_CENTER, color, 1));
                table.addCell(tableCell(codeMap.get("PRODUCTFREQUENCY" + detail.getProductFrequency()),
                        PdfPCell.ALIGN_LEFT, color, 1));
                table.addCell(tableCell(detail.getProductTreatment(), PdfPCell.ALIGN_LEFT, color, 1));
                table.addCell(tableCell(status, PdfPCell.ALIGN_CENTER, color, 1));
            }
        }
        if (StringUtils.isNotEmpty(diagnosisPojo.getDiagnosisExtenderDesc())) {
            PdfPCell cell = contentCell(diagnosisPojo.getDiagnosisExtenderDesc());
            cell.setColspan(8);
            table.addCell(cell);
        }

        document.add(table);
    }

    /**
     * 第六步：膳食处方
     * 
     * @author zcq
     * @param document
     * @param dietList
     * @param number
     * @throws DocumentException
     */
    private void setDietInfo(Document document, PregIntervenePlanPojo planVo, PregDiagnosisPojo diagnosisPojo)
            throws DocumentException {
        float[] tableWidth = {0.14f, 0.3f, 0.17f, 0.39f};
        PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(infoCell("能量", 0));
        table.addCell(contentCell(planVo.getIntakeCaloric()));
        table.addCell(infoCell("食谱名称", 0));
        table.addCell(contentCell(planVo.getDietName()));

        table.addCell(infoCell("CPF", 0));
        String cpf = "";
        if (StringUtils.isNotBlank(planVo.getIntakeCbrPercent())) {
            cpf += planVo.getIntakeCbrPercent() + "；";
        }
        if (StringUtils.isNotBlank(planVo.getIntakeProteinPercent())) {
            cpf += planVo.getIntakeProteinPercent() + "；";
        }
        if (StringUtils.isNotBlank(planVo.getIntakeFatPercent())) {
            cpf += planVo.getIntakeFatPercent() + "；";
        }
        PdfPCell cell = contentCell(cpf);
        cell.setColspan(3);
        table.addCell(cell);

        if (StringUtils.isNotEmpty(diagnosisPojo.getDiagnosisDietRemark())) {
            cell = contentCell(diagnosisPojo.getDiagnosisDietRemark());
            cell.setColspan(4);
            table.addCell(cell);
        }

        document.add(table);
    }

    // ****************************************************工具方法*****************************************************

    private void addTitleTable(String titleName) throws DocumentException {
        PdfPCell cell;
        float[] titleWidth = new float[] {0.01f, 0.99f};
        PdfPTable titleTable = utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 5f, 0);
        titleTable.addCell(titleCell(" "));

        String space = "   ";
        if (titleName.length() > 5) {
            space = "    ";
        }
        StringBuilder content = new StringBuilder(titleName + space);
        int index = 50 - titleName.length();
        for (int i = 0; i < index; i++) {
            content.append("—");
        }

        cell = utils.baseCell(content.toString(), utils.infoFontBlack);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(utils.whiteColor);
        cell.setPaddingRight(0);
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    public PdfPCell infoCell(String content, int type) {
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 9, Font.NORMAL, utils.darkGrayColor));
        cell.setBorderColor(utils.lightGrayColor);
        if (type == 1) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }
        if (type == 2) {
            cell.setFixedHeight(0);
            cell.setLeading(1, 1.2f);
        }
        return cell;
    }

    private PdfPCell titleCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.titleFont);
        cell.setBackgroundColor(utils.darkGrayColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell contentCell(String content) {
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 9, Font.NORMAL, utils.darkGrayColor));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setLeading(0, 1.5f);// 设置行间距
        return cell;
    }

    private PdfPCell tableCell(String content, int location, BaseColor color, int type) {
        if (color == null) {
            color = utils.darkGrayColor;
        }
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 9, Font.NORMAL, color));
        if (type == 1) {

        } else if (type == 2) {
            cell = utils.baseCell(content, new Font(utils.createFont(), 7, Font.NORMAL, color));
            cell.setPaddingTop(-7f);// 设置下边距
            cell.setBorderColorLeft(utils.whiteColor);
            cell.disableBorderSide(4);
            cell.setPaddingLeft(-1f);
        } else if (type == 3) {
            cell.disableBorderSide(8);
        } else if (type == 4) {
            cell = utils.baseCell(content, new Font(utils.createFont(), 7, Font.BOLD, utils.darkGrayColor));
            cell.setPaddingTop(-9f);// 设置下边距
            cell.setBorderColorLeft(utils.whiteColor);
            cell.disableBorderSide(4);
            cell.disableBorderSide(8);
            cell.setPaddingLeft(-2f);
        } else if (type == 5) {
            cell.disableBorderSide(8);
        } else if (type == 6) {
            cell.disableBorderSide(4);
        } else if (type == 7) {
            cell = utils.baseCell(content, new Font(utils.createFont(), 7, Font.BOLD, utils.darkGrayColor));
            cell.setPaddingTop(-8f);// 设置下边距
            cell.setBorderColorLeft(utils.whiteColor);
            cell.disableBorderSide(4);
            cell.disableBorderSide(8);
            cell.setPaddingLeft(-6f);
        }
        cell.setHorizontalAlignment(location);
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setLeading(0, 1.5f);// 设置行间距
        return cell;
    }

    private PdfPCell myCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(0);// 设置行高为自适应
        return cell;
    }

    private PdfPCell longContentCell(String content, int type) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 8, Font.BOLD, utils.darkGrayColor));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setLeading(0, 1.5f);// 设置行间距
        if (type == 1) {
            cell.disableBorderSide(4);
        } else if (type == 2) {
            cell.disableBorderSide(8);
        } else if (type == 3) {
            cell.disableBorderSide(4);
            cell.disableBorderSide(8);
        }
        return cell;
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
    private void setPageLast(PregDiagnosisSummaryReportPojo pdfData) throws DocumentException {
        // PregDiagnosisPojo diagnosis = pdfData.getDiagnosisPojo();
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
                "报告医生：" + pdfData.getDiagnosisPojo().getDiagnosisUserName(),
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
