
package com.mnt.preg.web.pdf;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 妊娠日记报告
 * 
 * @author dhs
 * @version 1.3.0
 * 
 *          变更履历：
 *          v1.3.0 2017-11-20 dhs 初版
 */
@Component
public class DiaryPdf extends AbstractPdf<PregDiagnosisPojo> {

    // 表格的行高
    private static final int rowHeight = 25;

    /**
     * 生成报告
     * 
     * @author xdc
     * @throws DocumentException
     */
    @Override
    public void handler(PregDiagnosisPojo pregDiagnosisPojo) throws DocumentException {
        // 设置页眉
        setPageTops(pregDiagnosisPojo);
        // 基本信息
        float[] table0Width = new float[] {0.05f, 0.2f, 0.25f, 0.2f, 0.05f, 0.25f};
        String diseasHeight = setMyMessage(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 5f, 0),
                pregDiagnosisPojo);// 添加内容
        // 话术
        // 标题
        boldFont(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), "妊娠日记（请检测每日体重，空腹+三餐后2小时血糖，记录饮食情况）");
        float[] tableWidth = {0.25f, 0.75f};
        // 第一句
        PdfPTable table_life = utils.createTable(tableWidth, Element.ALIGN_LEFT, 100f, 5f, 0);
        String[] str = {"1. 准备体重秤，每晨称体重：", "晨起空腹，排空二便后，着内衣裤称重；"};
        setDiffContent(table_life, str);
        // 第二句
        float[] tableWidth2 = {0.54f, 0.46f};
        PdfPTable table_life2 = utils.createTable(tableWidth2, Element.ALIGN_LEFT, 100f, 1f, 0);
        String[] str2 = {"2. 准备指血血糖仪，每周至少检测3天 空腹 + 三餐后2小时 血糖：", "空腹血糖指晨起空腹采血测量的血糖值（正常值：3.9-"};
        setDiffContent(table_life2, str2);
        // 第二句（下半部分）
        float[] tableWidth3 = {1f};
        PdfPTable table_life3 = utils.createTable(tableWidth3, Element.ALIGN_LEFT, 100f, 5f, 0);
        String[] str3 = {"5.3mmol/L）；餐后2小时血糖指从进餐第一口开始计时到2小时后采血测量的血糖值（正常值：4.4-6.7mmol/L）；"};
        setDiffContent(table_life3, str3);
        // 第三句
        float[] tableWidth4 = {0.39f, 0.61f};
        PdfPTable table_life4 = utils.createTable(tableWidth4, Element.ALIGN_LEFT, 100f, 5f, 0);
        String[] str4 = {"3. 准备食物称，详细记录每次进食（含加餐）：", "记录日期、进餐时间，详细记录食物内容、烹调方法及食量。"};
        setDiffContent(table_life4, str4);
        // 表格
        setTables(document, 21, 1, Integer.parseInt(diseasHeight), pregDiagnosisPojo);
        document.newPage();// 下一页

        // 页眉
        setPageTops(pregDiagnosisPojo);
        // 表格
        setTables(document, 20, 2, Integer.parseInt(diseasHeight), pregDiagnosisPojo);
        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(pregDiagnosisPojo);
    }

    // **************************************************设置报告明细********************************************************

    /**
     * 设置个人信息
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    private String setMyMessage(PdfPTable table, PregDiagnosisPojo pregDiagnosisPojo) throws DocumentException {
        PdfPCell cell_name = myCell(
                "姓名："
                        + (pregDiagnosisPojo.getDiagnosisCustName() == null ? ""
                                : pregDiagnosisPojo.getDiagnosisCustName()), 4);
        cell_name.setColspan(2);
        table.addCell(cell_name);
        table.addCell(myCell("孕周：", -1));
        PdfPCell cell = myCell("随诊日期：" + (pregDiagnosisPojo.getDiagnosisDate().toString().substring(0, 10)), 4);
        table.addCell(cell);
        PdfPCell cellCount = myCell("随诊次数：" + pregDiagnosisPojo.getDiagnosisRiseYunqi(), -1);
        cellCount.setColspan(2);
        table.addCell(cellCount);
        table.addCell(myCell("诊断", -1));
        PdfPCell cell2 = myCell(pregDiagnosisPojo.getDiagnosisDiseases(), 4);
        cell2.setColspan(5);
        table.addCell(cell2);
        PdfPCell cell_height = myCell(
                "身高："
                        + (pregDiagnosisPojo.getDiagnosisCustHeight() == null ? ""
                                : pregDiagnosisPojo.getDiagnosisCustHeight()), 4);
        cell_height.setColspan(2);
        table.addCell(cell_height);
        table.addCell(myCell(
                "孕前体重："
                        + (pregDiagnosisPojo.getDiagnosisArchiveWeight() == null ? "" : pregDiagnosisPojo
                                .getDiagnosisArchiveWeight()), -1));
        table.addCell(myCell(
                "体重："
                        + (pregDiagnosisPojo.getDiagnosisCustWeight() == null ? "" : pregDiagnosisPojo
                                .getDiagnosisCustWeight()), -1));
        PdfPCell cell3 = myCell("体重变化："
                + (pregDiagnosisPojo.getDiagnosisRiseWeek().isEmpty() ? "" : pregDiagnosisPojo.getDiagnosisRiseWeek()
                        .split("\\\\")[0] + "\\"), 4);
        cell3.setColspan(2);
        table.addCell(cell3);
        if (StringUtils.isNotBlank(pregDiagnosisPojo.getDiagnosisGestationalWeeks())) {
            getSupWeek(167, 13, -8, pregDiagnosisPojo.getDiagnosisGestationalWeeks());
        }
        PdfPCell c = myCell("医嘱", -1);
        table.addCell(c);
        String diagnosisDietRemark = "";// 医嘱
        if (pregDiagnosisPojo.getDiagnosisDietRemark() != null) {
            diagnosisDietRemark = pregDiagnosisPojo.getDiagnosisDietRemark();
        }
        PdfPCell cell4 = myCell(diagnosisDietRemark, 4);
        cell4.setColspan(5);
        table.addCell(cell4);
        document.add(table);
        float zhenduanHeight = table.getRowHeight(1);// 诊断高度
        float yizhuHeight = table.getRowHeight(3);// 医嘱高度
        float sum = zhenduanHeight + yizhuHeight;
        BigDecimal b = new BigDecimal(sum);
        int down = 146;// 只对医嘱1-11行进行格式处理
        if (yizhuHeight == 18) {
            down = 32;
        } else if ((int) yizhuHeight == 26) {
            down = 40;
        } else if ((int) yizhuHeight == 38) {
            down = 52;
        } else if ((int) yizhuHeight == 50) {
            down = 64;
        } else if ((int) yizhuHeight == 62) {
            down = 76;
        } else if ((int) yizhuHeight == 73) {
            down = 87;
        } else if ((int) yizhuHeight == 85) {
            down = 99;
        } else if ((int) yizhuHeight == 97) {
            down = 111;
        } else if ((int) yizhuHeight == 109) {
            down = 123;
        } else if ((int) yizhuHeight == 121) {
            down = 135;
        }
        int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();// 四舍五入（诊断+医嘱 高度）
        if (StringUtils.isNotBlank(pregDiagnosisPojo.getDiagnosisRiseWeek())) {
            getSupWeek2(pregDiagnosisPojo.getDiagnosisRiseWeek().split("\\\\")[0], pregDiagnosisPojo
                    .getDiagnosisRiseWeek().split("\\\\")[1], -27 - ((int) yizhuHeight - 18), down);// 体重变化 -26 41
        }
        return String.valueOf(f1);
    }

    /**
     * 设置妊娠日记
     * 
     * @author dhs
     * @param document
     * @param orderDetailList
     * @throws DocumentException
     */
    private void setTables(Document document, int num, int page, int height, PregDiagnosisPojo pregDiagnosisPojo)
            throws DocumentException {
        float[] principleWidth = {0.12f, 0.1f, 0.12f, 0.1f, 0.1f, 0.1f};
        int change = height - 36;
        PdfPTable table = utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(myCell("                         日期\n\n 空腹血糖", 7));
        table.addCell(myCell("晨重", 0));
        table.addCell(myCell("进餐时间", 0));
        table.addCell(myCell("食物内容", 0));
        table.addCell(myCell("餐后血糖", 0));
        table.addCell(myCell("餐后活动", 0));
        if (page == 1) {
            num = 540 - change;
            num = num / rowHeight;
        } else if (page == 2) {
            // num = 740 / rowHeight; 满屏表格
            num = 680 / rowHeight;
        }
        for (int x = 0; x < num; x++) {
            table.addCell(myCell("", 1));
            table.addCell(myCell("", 1));
            table.addCell(myCell("", 1));
            table.addCell(myCell("", 1));
            table.addCell(myCell("", 1));
            table.addCell(myCell("", 1));
        }
        document.add(table);
        if (StringUtils.isNotBlank(pregDiagnosisPojo.getDiagnosisRiseWeek())) {// 如果体重变化不为空，斜线的坐标就要往上调整一点点
            change -= 1;
        }
        if (page == 1) {// 第一页
            utils.drawLine(writer, 20, 604 - change, 124, 564 - change, 1, utils.lightGrayColor);
        } else if (page == 2) {// 第二页
            utils.drawLine(writer, 20, 802, 124, 762, 1, utils.lightGrayColor);
        }
    }

    /**
     * 设置页眉
     * 
     * @author dhs
     * @param document
     * @param orderDetailList
     * @throws DocumentException
     */
    private void setPageTops(PregDiagnosisPojo pregDiagnosisPojo) throws DocumentException {
        float[] tableWidthff = {1f};
        PdfPTable table_lifeff = utils.createTable(tableWidthff, Element.ALIGN_LEFT, 100f, 0, 0);
        String sikeId = pregDiagnosisPojo.getDiagnosisCustSikeId();
        int num = 170;
        StringBuffer buffer = new StringBuffer();
        if (sikeId == null) {
            sikeId = "";
        } else {
            if (!sikeId.isEmpty()) {
                num = 155 - (sikeId.length() - 6);
            }
        }
        for (int x = 0; x < num; x++) {
            buffer.append(" ");
        }
        setPageTop(table_lifeff, "日记编号：" + buffer + "病案号：" + sikeId);
    }

    // ***************************************************工具方法**********************************************************

    /**
     * 字体加粗
     * 
     * @author dhs
     * @param table
     * @throws DocumentException
     */
    private void boldFont(PdfPTable table, String content) throws DocumentException {
        PdfPCell cell = utils.baseCell(content, new Font(utils.createFont(), 12, Font.BOLD, utils.darkGrayColor));
        cell.setBackgroundColor(utils.whiteColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(25f);
        table.addCell(cell);
        table.setSpacingAfter(8f);
        document.add(table);
    }

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
        if (type == 0) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setFixedHeight(40f);// 设置行高
        } else if (type == 1) {
            cell.setFixedHeight(rowHeight);// 设置行高
        } else if (type == 3) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        } else if (type == 4) {
            cell.setFixedHeight(0);// 设置行高自适应
            cell.setLeading(1, 1.2f);// 设置行间距
        } else if (type == 5) {
            cell = utils.baseCell(content, new Font(utils.createFont(), 10, Font.NORMAL, utils.darkGrayColor));
            cell.setBorderColor(BaseColor.WHITE);
        } else if (type == 6) {
            cell = utils.baseCell(content, utils.contentFont);
            cell.setBorderColor(utils.whiteColor);// 边颜色
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);// 居左
            cell.setFixedHeight(0);// 设置行高为自适应
            cell.setLeading(0.5f, 1.5f);// 设置行间距
            cell.setPaddingBottom(6f);// 设置下边距
        } else if (type == 7) {
            cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
            cell.setFixedHeight(40f);// 设置行高
            cell.setPaddingTop(7f);
        }
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
        if (content.length == 1) {
            table.addCell(myCell(content[0], 5));
            document.add(table);
        } else {
            PdfPCell cell = utils.baseCell(content[0], new Font(utils.createFont(), 10, Font.BOLDITALIC,
                    utils.darkGrayColor));
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);
            table.addCell(myCell(content[1], 5));
            document.add(table);
        }
    }

    /**
     * 设置页眉
     * 
     * @author dhs
     * @param table
     * @param dietReportVo
     * @throws DocumentException
     */
    public void setPageTop(PdfPTable table, String content) throws DocumentException {
        table.addCell(myCell(content, 6));
        document.add(table);
    }

    /**
     * 生成角标形式的孕周(孕周)
     * 
     * @author dhs
     * @param content
     * @throws DocumentException
     */
    private void getSupWeek(int sta, int before, int after, String content) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String[] a = content.split("\\+");
        int len = a[0].length() > 1 ? 13 : 9;
        document.add(utils.createParagraph(a[0], 10, utils.darkGrayColor, sta, before, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.darkGrayColor, sta + len, -5, after));
    }

    /**
     * 生成角标形式的孕周(体重变化)
     * 
     * @author dhs
     * @param before
     *            content
     * @throws DocumentException
     */
    private void getSupWeek2(String before, String content, int up, int down) throws DocumentException {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String[] a = content.split("\\+");
        int staNum = 0;
        int before_length = before.length() - 2;// 最少为3,0.0kg;最多为6，体重有上限
        if (before_length == 3) {
            staNum = 464;
        } else if (before_length == 4) {
            staNum = 470;
        } else if (before_length == 5) {
            staNum = 475;
        } else if (before_length == 6) {
            staNum = 481;
        }
        int sta = staNum;
        int len = a[0].length() > 1 ? 13 : 9;
        int num = 0;
        if (a[0].length() == 2) {
            num = 3;
        } else if (a[0].length() == 3) {
            num = 8;
        } else if (a[0].length() == 4) {
            num = 15;
        }
        document.add(utils.createParagraph(a[0], 10, utils.darkGrayColor, sta, up, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.darkGrayColor, sta + len + num, -6, down));
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
    private void setPageLast(PregDiagnosisPojo pdfData) throws DocumentException {
        // PregDiagnosisPojo diagnosis = pdfData;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        String time = df.format(new Date());// 当前系统时间
        float[] table1Width1 = new float[] {0.5f, 0.5f};
        // PdfPTable table1 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 6f, 0);
        // TABLE1.ADDCELL(MYCELLWHITEBORDER(
        // "送检科室：" + (DIAGNOSIS.GETDIAGNOSISORG() == NULL ? "" : DIAGNOSIS.GETDIAGNOSISORG()),
        // PDFPCELL.ALIGN_LEFT,
        // 0));
        // IF (DIAGNOSIS.GETDIAGNOSISREFERRALDOCTORNAME() == NULL) {
        // TABLE1.ADDCELL(MYCELLWHITEBORDER("                                                  送检医生：",
        // PDFPCELL.ALIGN_CENTER, 0));
        // } ELSE {
        // TABLE1.ADDCELL(MYCELLWHITEBORDER("送检医生：" + DIAGNOSIS.GETDIAGNOSISREFERRALDOCTORNAME(),
        // PDFPCELL.ALIGN_RIGHT, 0));
        // }
        // table1.setSpacingAfter(-3f);
        // document.add(table1);
        PdfPTable table2 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 6f, 0);
        table2.addCell(myCellWhiteBorder(
                "报告医生：" + pdfData.getDiagnosisUserName(),
                PdfPCell.ALIGN_LEFT, 0));
        table2.addCell(myCellWhiteBorder("报告日期：" + time, PdfPCell.ALIGN_RIGHT, 0));
        document.add(table2);
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

}
