
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregDietReportPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

public class PregnancyLifeStylePdf extends AbstractPdf<PregDietReportPojo> {

    /**
     * 生成报告
     * 
     * @author xdc
     * @throws DocumentException
     */
    @Override
    public void handler(PregDietReportPojo lifeStyleVo) throws DocumentException {
        Map<String, ExamItemPojo> resultMap = lifeStyleVo.getExamMap();
        // 设置报告头
        float[] titleWidth = {0.38f, 0.62f};
        addContentTableHead0(utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 40f, 0), lifeStyleVo);

        // 基本信息
        document.add(utils.createParagraph(" ", 7, utils.darkGrayColor, 0, 0, 0f));
        float[] table1Width = new float[] {0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f};
        addContentTable1(utils.createTable(table1Width, Element.ALIGN_LEFT, 100f, 10f, 0), lifeStyleVo);

        // 营养补充剂服用明细
        addTitleTable2("孕期膳食营养状态评估", Element.ALIGN_LEFT, 100);
        float[] table1Width1 = new float[] {0.19f, 0.126f, 0.126f, 0.126f, 0.126f, 0.126f, 0.126f};
        addContentTable2(utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 10f, 0), resultMap);

        // 产能营养素
        float[] table1Width2 = new float[] {0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
        addContentTable3(utils.createTable(table1Width2, Element.ALIGN_LEFT, 100f, 10f, 0), resultMap);
        // 膳食频率分析
        float[] table1Width3 = new float[] {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f};
        addContentTable4(utils.createTable(table1Width3, Element.ALIGN_LEFT, 100f, 10f, 0), resultMap);

        // 孕期重要膳食营养素评价
        addTitleTable2("孕期重要膳食营养素评价", Element.ALIGN_LEFT, 100);
        float[] table1Width4 = new float[] {0.2f, 0.8f};
        addContentTable5(utils.createTable(table1Width4, Element.ALIGN_LEFT, 100f, 10f, 0), resultMap);

        // 生活方式调查
        addTitleTable2("生活方式调查", Element.ALIGN_LEFT, 100);
        float[] table1Width5 = new float[] {0.2f, 0.8f};
        addContentTable6(utils.createTable(table1Width5, Element.ALIGN_LEFT, 100f, 10f, 0), resultMap);

        // // 时间和医师
        // float[] table1Width7 = new float[] {0.5f, 0.5f};
        // addContentTable7(utils.createTable(table1Width7, Element.ALIGN_LEFT, 100f, 10f, 0), lifeStyleVo);
        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(lifeStyleVo);
    }

    // -------------------------------------------------------------填充内容

    private void addContentTableHead0(PdfPTable table, PregDietReportPojo lifeStyleVo) throws DocumentException {
        addImgLogo(table, lifeStyleVo);
        PdfPCell cell = utils.baseCell("孕期膳食评价及生活方式调查报告", utils.reportFont);
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

    private void addContentTable1(PdfPTable table, PregDietReportPojo lifeStyleVo) throws DocumentException
    {
        // String itemString = resultMap.get("customerInfo").getItemString();
        // String[] values = itemString.split("<cell>");
        table.addCell(myCellRed("ID"));
        table.addCell(myCellWhite(lifeStyleVo.getCustomer().getCustPatientId()));
        table.addCell(myCellRed("姓名"));
        table.addCell(myCellWhite(lifeStyleVo.getCustomer().getCustName()));
        table.addCell(myCellRed("年龄"));
        table.addCell(myCellWhite(lifeStyleVo.getCustomer().getCustAge().toString()));
        table.addCell(myCellRed("孕前体重"));
        table.addCell(myCellWhite(lifeStyleVo.getCustomer().getCustWeight().toString()));
        table.addCell(myCellRed("身高"));
        table.addCell(myCellWhite(lifeStyleVo.getCustomer().getCustHeight().toString()));
        table.addCell(myCellRed("孕前BMI"));
        table.addCell(myCellWhite(lifeStyleVo.getCustomer().getCustBmi().toString()));
        table.addCell(myCellRed("孕周数"));
        table.addCell(myCellWhite(""));
        table.addCell(myCellRed("胎数"));
        table.addCell(myCellWhite(lifeStyleVo.getPregnancyArchives().getEmbryoNum()));
        getSupWeek(lifeStyleVo.getDiagnosis().getDiagnosisGestationalWeeks());
        document.add(table);
    }

    private void addTitleTable2(String titleName, int align, float recent) throws DocumentException {
        PdfPCell cell;
        float[] titleWidth = new float[] {0.02f, 0.95f};
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
        PdfPCell cell;
        table.addCell(myCellRed("实际一日总能量"));
        cell = myCellWhiteLeft(resultMap.get("B00500").getItemString());
        cell.setColspan(6);
        table.addCell(cell);
        table.addCell(myCellRed("推荐一日总能量"));
        cell = myCellWhiteLeft(resultMap.get("B00578").getItemString());
        cell.setColspan(6);
        table.addCell(cell);
        cell = myCellRed("就餐时间");
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell(myCellLightPink("早餐"));
        table.addCell(myCellLightPink("上午加餐"));
        table.addCell(myCellLightPink("午餐"));
        table.addCell(myCellLightPink("下午加餐"));
        table.addCell(myCellLightPink("晚餐"));
        table.addCell(myCellLightPink("宵夜"));

        for (int i = 0; i < 6; i++) {
            int mark = 79 + i;
            table.addCell(myCellWhite(resultMap.get("B005" + mark).getItemString()));
        }

        document.add(table);
    }

    private void addContentTable3(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
        PdfPCell cell = myCellRed("产能营养素分析（平均每天约为）");
        cell.setColspan(5);
        table.addCell(cell);
        table.addCell(myCellLightPink("类别"));
        table.addCell(myCellLightPink("实际摄入量"));
        table.addCell(myCellLightPink("推荐摄入量（范围）"));
        table.addCell(myCellLightPink("实际供能比（范围）"));
        table.addCell(myCellLightPink("推荐供能比（范围）"));

        table.addCell(myCellWhite("碳水化合物"));
        table.addCell(myCellWhite(resultMap.get("B00501").getItemString()));
        table.addCell(myCellWhite(resultMap.get("B00501").getItemRefValue() + resultMap.get("B00501").getItemUnit()));
        table.addCell(myCellWhite(resultMap.get("B00502").getItemString()));
        table.addCell(myCellWhite(resultMap.get("B00502").getItemRefValue()));

        table.addCell(myCellWhite("蛋白质"));
        table.addCell(myCellWhite(resultMap.get("B00503").getItemString()));
        table.addCell(myCellWhite(resultMap.get("B00503").getItemRefValue() + resultMap.get("B00503").getItemUnit()));
        table.addCell(myCellWhite(resultMap.get("B00504").getItemString()));
        table.addCell(myCellWhite(resultMap.get("B00504").getItemRefValue()));

        table.addCell(myCellWhite("脂肪"));
        table.addCell(myCellWhite(resultMap.get("B00505").getItemString()));
        table.addCell(myCellWhite(resultMap.get("B00505").getItemRefValue() + resultMap.get("B00505").getItemUnit()));
        table.addCell(myCellWhite(resultMap.get("B00506").getItemString()));
        table.addCell(myCellWhite(resultMap.get("B00506").getItemRefValue()));

        document.add(table);
    }

    private void addContentTable4(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
        PdfPCell cell = myCellRed("膳食频率分析（平均每天约为）");
        cell.setColspan(10);
        table.addCell(cell);
        table.addCell(myCellLightPink("类别"));
        for (int i = 0; i < 8; i++) {
            int mark = 29 + i;
            table.addCell(myCellLightPink(resultMap.get("B005" + mark).getItemName()));
        }
        table.addCell(myCellLightPink(resultMap.get("B00559").getItemName()));// 水

        table.addCell(myCellWhite("推荐"));
        for (int i = 0; i < 8; i++) {
            int mark = 29 + i;
            table.addCell(myCellWhite(resultMap.get("B005" + mark).getItemRefValue()));
        }
        table.addCell(myCellWhite(resultMap.get("B00559").getItemRefValue()));// 水

        table.addCell(myCellWhite("实际（约为）"));
        for (int i = 0; i < 8; i++) {
            int mark = 29 + i;
            String content = resultMap.get("B005" + mark).getItemString();
            table.addCell(myCellWhite(content));
        }
        String waiter = resultMap.get("B00559").getItemString();
        table.addCell(myCellWhite(waiter));

        // 粗杂粮，深绿色蔬菜，优质蛋白质，动物内脏，用油量
        String[] czlDeng = new String[] {"粗杂粮", "深色蔬菜", "优质蛋白质", "动物内脏", "用油量"};
        for (int i = 0; i < czlDeng.length; i++) {
            cell = myCellLightPink(czlDeng[i]);
            cell.setColspan(2);
            table.addCell(cell);
        }

        String czl = resultMap.get("B00575").getItemString();
        cell = myCellWhite(czl);
        cell.setColspan(2);
        table.addCell(cell);

        String lssc = resultMap.get("B00577").getItemString();
        cell = myCellWhite(lssc);
        cell.setColspan(2);
        table.addCell(cell);

        String yzdbz = resultMap.get("B00508").getItemString();
        cell = myCellWhite(yzdbz);
        cell.setColspan(2);
        table.addCell(cell);

        String dwnz = resultMap.get("B00585").getItemString();
        cell = myCellWhite(dwnz);
        cell.setColspan(2);
        table.addCell(cell);

        String yyl = resultMap.get("B00586").getItemString();
        cell = myCellWhite(yyl);
        cell.setColspan(2);
        table.addCell(cell);

        String[] hsDeng = new String[] {"海参", "燕窝", "蜂蜜", "菌类", "藻类"};
        for (int i = 0; i < hsDeng.length; i++) {
            cell = myCellLightPink(hsDeng[i]);
            cell.setColspan(2);
            table.addCell(cell);
        }

        String hs = resultMap.get("B00571").getItemString();
        cell = myCellWhite(hs);
        cell.setColspan(2);
        table.addCell(cell);

        String yw = resultMap.get("B00570").getItemString();
        cell = myCellWhite(yw);
        cell.setColspan(2);
        table.addCell(cell);

        cell = myCellWhite(resultMap.get("B00597").getItemString());
        cell.setColspan(2);
        table.addCell(cell);

        cell = myCellWhite(resultMap.get("B00595").getItemString());
        cell.setColspan(2);
        table.addCell(cell);

        cell = myCellWhite(resultMap.get("B00596").getItemString());
        cell.setColspan(2);
        table.addCell(cell);

        String[] bgdxDeng = new String[] {"饼干点心", "蛋糕", "起酥面包", "海苔", "糖果", "蜜饯", "巧克力", "碳酸饮料", "冷饮雪糕", "膨化食品"};
        for (int i = 0; i < bgdxDeng.length; i++) {
            cell = myCellLightPink(bgdxDeng[i]);
            table.addCell(cell);
        }

        String bgdx = resultMap.get("B00560").getItemString();
        cell = myCellWhite(bgdx);
        table.addCell(cell);

        String dg = resultMap.get("B00561").getItemString();
        cell = myCellWhite(dg);
        table.addCell(cell);

        String qsmb = resultMap.get("B00562").getItemString();
        cell = myCellWhite(qsmb);
        table.addCell(cell);

        String ht = resultMap.get("B00587").getItemString();
        cell = myCellWhite(ht);
        table.addCell(cell);

        String tg = resultMap.get("B00564").getItemString();
        cell = myCellWhite(tg);
        table.addCell(cell);

        String mj = resultMap.get("B00565").getItemString();
        cell = myCellWhite(mj);
        table.addCell(cell);

        String qkl = resultMap.get("B00566").getItemString();
        cell = myCellWhite(qkl);
        table.addCell(cell);

        String tsyl = resultMap.get("B00567").getItemString();
        cell = myCellWhite(tsyl);
        table.addCell(cell);

        String lyxg = resultMap.get("B00568").getItemString();
        cell = myCellWhite(lyxg);
        table.addCell(cell);

        String phsp = resultMap.get("B00569").getItemString();
        cell = myCellWhite(phsp);
        table.addCell(cell);

        document.add(table);
    }

    private void addContentTable5(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
        table.addCell(myCellRed("可能摄入不足的营养素有"));
        table.addCell(myCellWhiteLightGrayColor(resultMap.get("B00594").getItemString(), "注：未计算烹饪流失"));
        document.add(table);
    }

    private void addContentTable6(PdfPTable table, Map<String, ExamItemPojo> resultMap) throws DocumentException {
        for (int i = 0; i < 6; i++) {
            int mark = 88 + i;
            table.addCell(myCellRed(resultMap.get("B005" + mark).getItemName()));
            table.addCell(myCellMoreColor(resultMap.get("B005" + mark).getItemString()));
        }
        document.add(table);
    }

    // private void addContentTable7(PdfPTable table, PregDietReportPojo lifeStyleVo) throws DocumentException
    // {
    // table.addCell(myCellWhiteBorder(
    // "时间：" + JodaTimeTools.toString(lifeStyleVo.getDiagnosis().getDiagnosisDate(), JodaTimeTools.FORMAT_6),
    // PdfPCell.ALIGN_LEFT));
    // table.addCell(myCellWhiteBorder("医师：" + lifeStyleVo.getDiagnosis().getDiagnosisUserName(),
    // PdfPCell.ALIGN_RIGHT));
    // document.add(table);
    // }

    // -------------------------------------------------------------工具
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
        cell.setPaddingLeft(0);// 设置左边距
        cell.setPaddingRight(0);// 设置右边距
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        return cell;
    }

    private PdfPCell myCellLightPink(String content) {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 8, Font.BOLD, utils.lightThiredRedColor));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setBorderColor(utils.whiteColor);// 边颜色
        cell.setBorderWidth(0.5f);
        cell.setLeading(0, 1.5f);
        return cell;
    }

    // 结论列中，设置content2字体为灰色
    private PdfPCell myCellWhiteLightGrayColor(String content1, String content2) {
        float[] doubCell = new float[] {0.7f, 0.3f};
        PdfPTable titleTable = utils.createTable(doubCell, PdfPCell.ALIGN_CENTER, 0.000001f, 0, 0);
        // cell1设置
        PdfPCell cell1 = utils.baseCell(content1, utils.contentFont);
        cell1.setPaddingTop(1f);// 设置上边距
        cell1.setPaddingBottom(5f);// 设置下边距
        cell1.setFixedHeight(0);
        cell1.setLeading(1, 1.5f);
        cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell1.setBorder(PdfPCell.NO_BORDER);
        // cell2设置
        PdfPCell cell2 = new PdfPCell(new Paragraph(content2,
                new Font(utils.createFont(), 8, Font.BOLD, utils.lightGrayColor)));
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell2.setBorderColor(utils.darkGrayColor);
        cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);// 文本上下居中
        cell2.setBorder(PdfPCell.NO_BORDER);

        titleTable.addCell(cell1);
        titleTable.addCell(cell2);
        PdfPCell cell = new PdfPCell(titleTable);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);
        cell.setBorderWidth(0.5f);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        return cell;
    }

    private PdfPCell myCellWhiteLeft(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setBorderWidth(0.5f);
        return cell;
    }

    /**
     * 判断多行，如果多行且不确定行数则用此方法,单行输出54个字
     * 
     * @author xdc
     * @param content
     * @param index
     * @return
     */
    private PdfPCell myCellMoreColor(String content) {
        Paragraph contentPar = new Paragraph();
        if (content.indexOf("——") >= 0) {
            contentPar = new Paragraph(content, utils.contentFont);
        } else {
            String[] contentAim = content.split(";");
            for (int i = 0; i < contentAim.length; i++) {
                // 判断该项目是否有异常情况，有过有进一步分割
                if (contentAim[i].indexOf("%s") > -1) {
                    String[] demoAim = contentAim[i].split(",");
                    // 对异常项目经一部分割，将异常的内容标记红色
                    for (int j = 0; j < demoAim.length; j++) {
                        // 如果是最后一个字段的话改为“;”结束
                        String index = ",";
                        if (j == demoAim.length - 1) {
                            index = ";";
                        }
                        // 判断有异常的局部标记为红色
                        if (demoAim[j].indexOf("%s") > -1) {
                            demoAim[j] = demoAim[j].replaceAll("%s", "");
                            contentPar.add(new Paragraph(demoAim[j].trim() + index, new Font(utils.createFont(), 8,
                                    Font.NORMAL, new BaseColor(255, 0, 0))));
                        } else {
                            contentPar.add(new Paragraph(demoAim[j].trim() + index, utils.contentFont));
                        }
                    }
                } else {
                    contentPar.add(new Paragraph(contentAim[i].trim() + ";", utils.contentFont));
                }
            }
        }
        PdfPCell cell = new PdfPCell(contentPar);
        cell.setPaddingTop(1f);// 设置上边距
        cell.setPaddingBottom(7f);// 设置下边距
        cell.setPaddingLeft(3f);// 设置左边距
        cell.setPaddingRight(2f);// 设置右边距
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.lightPinkColor);// 边颜色
        cell.setHorizontalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBorderWidth(0.5f);
        cell.setFixedHeight(0);
        cell.setLeading(1, 1.5f);// 设置行间距
        return cell;
    }

    // private PdfPCell myCellWhiteBorder(String content, int align) {
    // PdfPCell cell = utils.baseCell(content, utils.contentFont);
    // cell.setHorizontalAlignment(align);
    // // cell.setPaddingLeft(10f);// 设置边距
    // // cell.setPaddingRight(10f);// 设置边距
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
