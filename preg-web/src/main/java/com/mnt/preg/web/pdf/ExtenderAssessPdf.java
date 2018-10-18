
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.ExtenderReportPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

/**
 * 补充剂安全剂量评估报告
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-28 xdc 初版
 */
@Component
public class ExtenderAssessPdf extends AbstractPdf<ExtenderReportPojo> {

    @Override
    public void handler(ExtenderReportPojo extenderReportVo) throws DocumentException {
        List<ExamItemPojo> itemList = extenderReportVo.getExamItemList();
        if (CollectionUtils.isNotEmpty(itemList)) {
            List<String> cycleList = extenderReportVo.getTakingCycleList();
            Map<String, Map<String, ExamItemPojo>> pareMap = new TreeMap<String, Map<String, ExamItemPojo>>();
            Map<String, Map<String, ExamItemPojo>> pregMap = new TreeMap<String, Map<String, ExamItemPojo>>();
            Map<Integer, Map<String, Map<String, ExamItemPojo>>> pregSortMap = new TreeMap<Integer, Map<String, Map<String, ExamItemPojo>>>();
            Map<String, ExamItemPojo> baseInfoMap = new HashMap<String, ExamItemPojo>();
            // 分组服用周期
            if (CollectionUtils.isNotEmpty(itemList)) {
                for (ExamItemPojo itemPojo : itemList) {
                    String itemClassify = itemPojo.getItemClassify();
                    String itemCode = itemPojo.getItemCode();
                    if (StringUtils.isEmpty(itemClassify)) {
                        baseInfoMap.put(itemCode, itemPojo);
                    } else if (CollectionUtils.isNotEmpty(cycleList) && cycleList.contains(itemClassify.split("：")[1])) {
                        if (itemClassify.indexOf("备孕期") > -1) {// 备孕期 TODO:需要优化
                            if (!pareMap.containsKey(itemClassify)) {
                                pareMap.put(itemClassify, new HashMap<String, ExamItemPojo>());
                            }
                            pareMap.get(itemClassify).put(itemCode, itemPojo);
                        } else {
                            if (!pregMap.containsKey(itemClassify)) {
                                pregMap.put(itemClassify, new HashMap<String, ExamItemPojo>());
                            }
                            pregMap.get(itemClassify).put(itemCode, itemPojo);
                        }
                    } else if (CollectionUtils.isEmpty(cycleList)) {
                        if (itemClassify.indexOf("备孕期") > -1) {// 备孕期 TODO:需要优化
                            if (!pareMap.containsKey(itemClassify)) {
                                pareMap.put(itemClassify, new HashMap<String, ExamItemPojo>());
                            }
                            pareMap.get(itemClassify).put(itemCode, itemPojo);
                        } else {
                            if (!pregMap.containsKey(itemClassify)) {
                                pregMap.put(itemClassify, new HashMap<String, ExamItemPojo>());
                            }
                            pregMap.get(itemClassify).put(itemCode, itemPojo);
                        }
                    }
                }
            }

            // 设置报告头
            float[] titleWidth = {0.38f, 0.62f};
            addContentTableHead0(utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 40f, 0), extenderReportVo);

            // 基本信息
            document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 0f));
            addContentTable1(utils.createTable(10, Element.ALIGN_LEFT, 100f, 10f, 0), baseInfoMap);

            String cycleName = "";
            // 备孕期
            if (pareMap != null && !CollectionUtils.sizeIsEmpty(pareMap)) {
                for (String cycle : pareMap.keySet()) {
                    if (cycle.split("：")[1].split("~")[0].equals(cycle.split("：")[1].split("~")[1])) {
                        cycleName = "（" + cycle.split("：")[0] + "：" + cycle.split("：")[1].split("~")[0] + " 月）";
                    } else {
                        cycleName = "（" + cycle + " 月）";
                    }
                    // 营养补充剂服用明细
                    addTitleTable2("营养补充剂服用明细" + cycleName, Element.ALIGN_LEFT, 100);
                    float[] table1Width1 = new float[] {0.07f, 0.4f, 0.12f, 0.08f, 0.08f, 0.12f, 0.13f};
                    addContentTable2(utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 5f, 0),
                            pareMap.get(cycle));

                    // 营养素剂量评估
                    // addTitleTable2("营养素剂量评估", Element.ALIGN_LEFT, 100);
                    float[] table1Width2 = new float[] {0.17f, 0.15f, 0.22f, 0.15f, 0.16f, 0.15f};
                    addContentTable3(utils.createTable(table1Width2, Element.ALIGN_LEFT, 100f, 12f, 1),
                            pareMap.get(cycle));
                }
            }

            // 孕期
            if (pregMap != null && !CollectionUtils.sizeIsEmpty(pregMap)) {
                for (String cycle : pregMap.keySet()) {
                    Map<String, Map<String, ExamItemPojo>> map = new HashMap<String, Map<String, ExamItemPojo>>();
                    map.put(cycle, pregMap.get(cycle));
                    pregSortMap.put(Integer.valueOf(cycle.split("：")[1].split("~")[0]), map);
                }
                for (Integer key : pregSortMap.keySet()) {
                    for (String cycle : pregSortMap.get(key).keySet()) {
                        if (cycle.split("：")[1].split("~")[0].equals(cycle.split("：")[1].split("~")[1])) {
                            cycleName = "（" + cycle.split("：")[0] + "：" + "第 " + cycle.split("：")[1].split("~")[0]
                                    + " 周）";
                        } else {
                            cycleName = "（" + cycle + " 周）";
                        }
                        // 营养补充剂服用明细
                        addTitleTable2("营养补充剂服用明细" + cycleName, Element.ALIGN_LEFT, 100);
                        float[] table1Width1 = new float[] {0.07f, 0.4f, 0.12f, 0.08f, 0.08f, 0.12f, 0.13f};
                        addContentTable2(utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 5f, 0),
                                pregMap.get(cycle));

                        // 营养素剂量评估
                        // addTitleTable2("营养素剂量评估", Element.ALIGN_LEFT, 100);
                        float[] table1Width2 = new float[] {0.17f, 0.15f, 0.22f, 0.15f, 0.16f, 0.15f};
                        addContentTable3(utils.createTable(table1Width2, Element.ALIGN_LEFT, 100f, 12f, 1),
                                pregMap.get(cycle));
                    }
                }
            }

            document.add(utils.createParagraph("注：RNI为推荐摄入量；AI为适宜摄入量；UL可耐受最大摄入量；DRIS为参考摄入量；（RNI、AI、UL、EAR的统称）", 6,
                    utils.deepGrayColor, 0, 8f, 12f));

            // 时间和医师
            // float[] table1Width4 = new float[] {0.5f, 0.5f};
            // addContentTable4(utils.createTable(table1Width4, Element.ALIGN_LEFT, 100f, 10f, 0), baseInfoMap);
            // 分界线
            setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
            // 页尾
            setPageLast(extenderReportVo);
        }
    }

    // -------------------------------------------------------------填充内容

    private void addContentTableHead0(PdfPTable table, ExtenderReportPojo extenderReportVo) throws DocumentException {
        addImgLogo(table, extenderReportVo);
        PdfPCell cell = utils.baseCell("营养素安全剂量评估报告", utils.reportFont);
        cell.setBackgroundColor(utils.lightThiredRedColor);
        cell.setBorderColor(utils.lightThiredRedColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(25f);
        // cell.setLeading(0, 0);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);
        table.setSpacingAfter(5f);
        document.add(table);
    }

    private void addContentTable1(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
        String itemString = resultMap.get("customerInfo").getItemString();
        String[] values = itemString.split("<cell>", -1);
        table.addCell(myCellRed("ID"));
        table.addCell(myCellWhite(values[0]));
        table.addCell(myCellRed("姓名"));
        table.addCell(myCellWhite(values[1]));
        table.addCell(myCellRed("年龄"));
        table.addCell(myCellWhite(values[2]));
        table.addCell(myCellRed("孕前体重"));
        table.addCell(myCellWhite(values[3]));
        table.addCell(myCellRed("身高"));
        table.addCell(myCellWhite(values[4]));
        table.addCell(myCellRed("孕前BMI"));
        table.addCell(myCellWhite(values[5]));
        table.addCell(myCellRed("末次月经"));
        table.addCell(myCellWhite(values[8]));
        table.addCell(myCellRed("孕周数"));
        table.addCell(myCellWhite(""));
        table.addCell(myCellRed("预产期"));
        table.addCell(myCellWhite(values[7]));
        getSupWeek(values[6]);
        table.addCell(myCellRed("胎数"));
        table.addCell(myCellWhite(values[9]));
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

    private void addContentTable2(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
        table.addCell(myCellRed("编号"));
        table.addCell(myCellRed("品名"));
        table.addCell(myCellRed("单次剂量"));
        table.addCell(myCellRed("剂量说明"));
        table.addCell(myCellRed("用法"));
        table.addCell(myCellRed("频次"));
        table.addCell(myCellRed("规律服用等级"));
        String[] products = resultMap.get("productListInfo").getItemString().split("<row>", -1);
        for (String productStr : products) {
            if (StringUtils.isEmpty(productStr))
                continue;
            String[] productInfo = productStr.split("<cell>", -1);
            table.addCell(myCellWhite(productInfo[0]));
            table.addCell(myCellWhiteLeft(productInfo[1]));
            table.addCell(myCellWhite(productInfo[2]));
            table.addCell(myCellWhite((productInfo[3] == null || "null".equals(productInfo[3])) ? "" : productInfo[3]));
            table.addCell(myCellWhite(productInfo[4]));
            table.addCell(myCellWhite(productInfo[5]));
            table.addCell(myCellWhite(productInfo[6]));
        }
        document.add(table);
    }

    private void addContentTable3(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
        table.addCell(myCellRed("元素/剂量单位"));
        table.addCell(myCellRed("补充剂来源分析"));
        table.addCell(myCellRed("补充剂成分备注"));
        table.addCell(myCellRed("结果"));
        table.addCell(myCellRed("结论"));
        table.addCell(myCellRed("DRIS"));
        for (String key : resultMap.keySet()) {
            if ("productListInfo".equals(key))
                continue;
            if ("customerInfo".equals(key))
                continue;
            if ("footerInfo".equals(key))
                continue;
            String[] values = resultMap.get(key).getItemString().split("<cell>", -1);
            // 1
            table.addCell(myCellWhiteLeft(values[0]));
            int rowIndex = values[1].split("<br>").length;
            // 2
            table.addCell(myCellWhite(values[1].replaceAll("<br>", "\n"), rowIndex));
            // 3
            rowIndex = values[2].split("<br>").length;
            table.addCell(myCellWhiteLeft(values[2].replaceAll("<br>", "\n"), rowIndex));
            // 4
            table.addCell(myCellWhite(values[3]));
            // 5
            table.addCell(resultCell(values[4]));
            // 6
            rowIndex = values[5].split("<br>").length;
            table.addCell(myCellWhite(values[5].replaceAll("<br>", "\n"), rowIndex));
        }
        document.add(table);
    }

    // private void addContentTable4(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
    // String[] strs = resultMap.get("footerInfo").getItemString().split("<cell>", -1);
    // table.addCell(myCellWhiteBorder("时间：" + strs[1], PdfPCell.ALIGN_LEFT));
    // table.addCell(myCellWhiteBorder("医师：" + strs[0], PdfPCell.ALIGN_RIGHT));
    // document.add(table);
    // }

    // -------------------------------------------------------------工具
    private void addImgLogo(PdfPTable table, ExtenderReportPojo extenderReportVo) throws DocumentException {
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + extenderReportVo.getInsId() + PublicConstant.logo2;
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

    public BaseColor redColor = new BaseColor(255, 0, 0);// 红色箭头

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
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        return cell;
    }

    /**
     * 设置结果
     * 
     * @author zcq
     * @param content
     * @return
     */
    private PdfPCell resultCell(String content) {
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setPaddingLeft(10f);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        if (StringUtils.isNotEmpty(content)) {
            Paragraph paragraph = new Paragraph();
            if (content.indexOf("↓") > -1) {
                Phrase phrase1 = new Phrase(1f, content.replace("↓", ""), utils.contentFont);
                Phrase phrase2 = new Phrase(1f, "↓", new Font(utils.createFont(), 9, Font.NORMAL,
                        BaseColor.BLUE));
                paragraph.add(phrase1);
                paragraph.add(phrase2);
            } else if (content.indexOf("↑") > -1) {
                Phrase phrase1 = new Phrase(1f, content.replace("↑", ""), utils.contentFont);
                Phrase phrase2 = new Phrase(1f, "↑", new Font(utils.createFont(), 9, Font.NORMAL,
                        utils.pinkColor));
                paragraph.add(phrase1);
                paragraph.add(phrase2);
            }
            cell = new PdfPCell(paragraph);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setPaddingLeft(10f);
            cell.setBackgroundColor(utils.whiteColor);
            cell.setBorderColor(utils.lightPinkColor);// 边颜色
            cell.setBorderWidth(0.5f);
        }
        return cell;
    }

    private PdfPCell myCellWhite(String content, int height) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setPaddingLeft(10f);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        if (height > 1) {
            cell.setFixedHeight(0);
            cell.setLeading(0, 1.5f);
        } else {
            cell.setFixedHeight(18f);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        }
        return cell;
    }

    private PdfPCell myCellWhiteLeft(String content) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, utils.contentFont));
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightGrayColor);// 边颜色
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderWidth(0.1f);// 设置边框宽度
        cell.setPaddingTop(0f);// 设置上边距
        cell.setPaddingBottom(3f);// 设置下边距
        cell.setPaddingLeft(3f);// 设置左边距
        cell.setPaddingRight(2f);// 设置右边距
        cell.setFixedHeight(18f);// 设置行高
        cell.setLeading(0, 1.5f);
        cell.setFixedHeight(0);
        return cell;
    }

    // 多行文本靠左格
    private PdfPCell myCellWhiteLeft(String content, int height) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setPaddingLeft(10f);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        if (height > 1) {
            cell.setFixedHeight(0);
            cell.setLeading(0, 1.5f);
        } else {
            cell.setFixedHeight(18f);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        }
        return cell;
    }

    // private PdfPCell myCellWhiteBorder(String content, int align) {
    // PdfPCell cell = utils.baseCell(content, utils.contentFont);
    // cell.setHorizontalAlignment(align);
    // cell.setPaddingLeft(0);// 设置边距
    // cell.setPaddingRight(0);// 设置边距
    // cell.setBackgroundColor(utils.whiteColor);
    // cell.setBorderColor(utils.whiteColor);// 边颜色
    // cell.setBorderWidth(0.5f);
    // return cell;
    // }

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
        int sta = 295;
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
    private void setPageLast(ExtenderReportPojo pdfData) throws DocumentException {
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
