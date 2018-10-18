
package com.mnt.preg.web.pdf;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.examitem.pojo.ExamItemPojo;
import com.mnt.preg.examitem.pojo.PregAuxAnalysisGroupPojo;
import com.mnt.preg.examitem.pojo.PregQuotaReportPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

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
public class QuotaItemPdf extends AbstractPdf<PregQuotaReportPojo> {

    /**
     * 设置报告的内容
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @throws DocumentException
     */
    @Override
    public void handler(PregQuotaReportPojo quotaReportVo) throws DocumentException {
        List<String> itemList = quotaReportVo.getCodeItemList();
        try {
            document.newPage();
            String imagPath = readProperties().getProperty("resource.absolute.path")
                    + "resource/template/image/cover/report-qr-code.png";
            utils.addImage(document, imagPath, 465f, 737f, 35);

            // 设置报告头
            addContentTableHead0(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0));

            // 信息栏
            float[] table0Width = new float[] {0.1f, 0.15f, 0.1f, 0.15f, 0.1f, 0.2f, 0.2f};
            addContentTable0(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 10f, 0), quotaReportVo);// 添加内容

            // 本次就诊信息
            if (itemList.contains("P01001")) {
                addTitleTable("本次就诊信息");// 添加标题
                setDiagnosisInfo(document, quotaReportVo.getDiagnosisVo());
            }
            // 一般检查指标
            if (itemList.contains("P01002")) {
                addTitleTable("一般检查指标");// 添加标题
                setExamItem1(document, quotaReportVo.getDiagnosisVo());
            }
            // 实验室指标
            PregAuxAnalysisGroupPojo quotaItem = quotaReportVo.getAuxAnalysisVo();
            if (itemList.contains("P01003")) {
                List<ExamItemPojo> diagnosisItemList = quotaItem.getClinItemList();
                if (CollectionUtils.isNotEmpty(diagnosisItemList)) {
                    addTitleTable("实验室指标");// 添加标题
                    setExamItem(document, quotaItem.getClinItemList());
                }
            }
            // 膳食及生活方式评价指标
            if (itemList.contains("P01004")) {
                List<ExamItemPojo> diagnosisItemList = quotaItem.getDietItemList();
                if (CollectionUtils.isNotEmpty(diagnosisItemList)) {
                    addTitleTable("膳食及生活方式评价指标");// 添加标题
                    setExamItem(document, quotaItem.getDietItemList());
                }
            }
            // 人体成分指标
            if (itemList.contains("P01005")) {
                List<ExamItemPojo> diagnosisItemList = quotaItem.getInbodyItemList();
                if (CollectionUtils.isNotEmpty(diagnosisItemList)) {
                    addTitleTable("人体成分指标");// 添加标题
                    setExamItem(document, quotaItem.getInbodyItemList());
                }
            }
            // 辅助检查项目
            if (itemList.contains("P01006")) {
                List<DiagnosisQuotaItemVo> quotaItemList = quotaReportVo.getQuotaItemList();
                if (CollectionUtils.isNotEmpty(quotaItemList)) {
                    addTitleTable("辅助检查项目");// 添加标题
                    setFuzhu(document, quotaItemList);
                }
            }
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
    private void addContentTableHead0(PdfPTable table) throws DocumentException {
        PdfPCell cell = utils.baseCell("营养病例报告", utils.reportFont);
        cell.setBackgroundColor(utils.pinkColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(25f);
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
    private void addContentTable0(PdfPTable table, PregQuotaReportPojo quotaReportVo) throws DocumentException {
        CustomerPojo custInfo = quotaReportVo.getCustomerVo();
        PregDiagnosisPojo diagnosis = quotaReportVo.getDiagnosisVo();
        PregArchivesPojo pregArchivesPojo = quotaReportVo.getPregArchivesVo();
        table.addCell(infoCell("ID：", 1));
        table.addCell(infoCell(custInfo.getCustPatientId(), 0));
        table.addCell(infoCell("姓名：", 1));
        table.addCell(infoCell(custInfo.getCustName(), 0));
        table.addCell(infoCell("检测日期：", 1));
        table.addCell(infoCell(JodaTimeTools.toString(diagnosis.getDiagnosisDate(), JodaTimeTools.FORMAT_6), 0));

        PdfPCell cell = infoCell("", 0);
        cell.setRowspan(3);
        table.addCell(cell);

        table.addCell(infoCell("年龄：", 1));
        table.addCell(infoCell("" + custInfo.getCustAge(), 0));
        table.addCell(infoCell("身高：", 1));
        table.addCell(infoCell(custInfo.getCustHeight() + "（cm）", 0));
        table.addCell(infoCell("孕前体重：", 1));
        table.addCell(infoCell(custInfo.getCustWeight() + "（kg）", 0));

        table.addCell(infoCell("胎数：", 1));
        table.addCell(infoCell(pregArchivesPojo.getEmbryoNum(), 0));
        table.addCell(infoCell("孕周：", 1));
        table.addCell(infoCell("", 0));
        table.addCell(infoCell("孕前BMI：", 1));
        table.addCell(infoCell("" + pregArchivesPojo.getBmi(), 0));

        document.add(table);
        getSupWeek(diagnosis.getDiagnosisGestationalWeeks());
    }

    /**
     * 第一步：本次就诊信息
     * 
     * @author zcq
     * @param document
     * @param diagnosisVo
     * @throws DocumentException
     */
    private void setDiagnosisInfo(Document document, PregDiagnosisPojo diagnosisVo) throws DocumentException {
        float[] tableWidth = {0.25f, 0.75f};
        PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100f, 10f, 0);

        String diagnosisBaby = "";
//        if (StringUtils.isNotEmpty(diagnosisVo.getDiagnosisBaby())) {
//            diagnosisBaby += "胎儿状态：" + diagnosisVo.getDiagnosisBaby() + "  ";
//        }
//        if (StringUtils.isNotEmpty(diagnosisVo.getDiagnosisFetusday())
//                && StringUtils.isNotEmpty(diagnosisVo.getDiagnosisFetusweek())) {
//            diagnosisBaby += "周数：" + diagnosisVo.getDiagnosisFetusweek() + "周  " + diagnosisVo.getDiagnosisFetusday()
//                    + "天 ";
//        } else if (StringUtils.isNotEmpty(diagnosisVo.getDiagnosisFetusweek())) {
//            diagnosisBaby += "周数：" + diagnosisVo.getDiagnosisFetusweek() + "周  ";
//        } else if (StringUtils.isNotEmpty(diagnosisVo.getDiagnosisFetusday())) {
//            diagnosisBaby += "周数：" + diagnosisVo.getDiagnosisFetusday() + "天  ";
//        }

        String inbodyResult = "";
        if (diagnosisVo.getInbodyResult() != null) {
            inbodyResult = diagnosisVo.getInbodyResult().getDiagnosisResultNames();
        }

        String lifeResult = " ### ";
        if (diagnosisVo.getDietLifeResult() != null
                && StringUtils.isNotEmpty(diagnosisVo.getDietLifeResult().getDiagnosisResultNames())) {
            lifeResult = diagnosisVo.getDietLifeResult().getDiagnosisResultNames();
        }

        table.addCell(infoCell("诊断", 1));
        String diseaseNames = diagnosisVo.getDiagnosisDiseases();
        if (StringUtils.isNotEmpty(diseaseNames)) {
            String[] diseaseNameArray = diseaseNames.split("#");
            if (diseaseNameArray.length == 2) {
                if (StringUtils.isNotEmpty(diseaseNameArray[0])) {
                    diseaseNames = diseaseNames.replace("#", "、");
                } else {
                    diseaseNames = diseaseNames.replace("#", "");
                }
            }
        }
        table.addCell(contentCell(diseaseNames));
        table.addCell(infoCell("营养主诉", 1));
//        table.addCell(contentCell(diagnosisVo.getDiagnosisMain()));
        table.addCell(infoCell("胎儿发育状态调查", 1));
        table.addCell(contentCell(diagnosisBaby));
        table.addCell(infoCell("人体成分分析", 1));
        table.addCell(contentCell(inbodyResult));
        table.addCell(infoCell("膳食营养状态分析", 1));
        table.addCell(contentCell(lifeResult.split("###")[0]));
        table.addCell(infoCell("生活方式分析", 1));
        table.addCell(contentCell(lifeResult.split("###")[1]));

        document.add(table);
    }

    /**
     * 第二步：一般检查指标
     * 
     * @author zcq
     * @param document
     * @param diagnosisVo
     * @throws DocumentException
     */
    private void setExamItem1(Document document, PregDiagnosisPojo diagnosisVo) throws DocumentException {
        float[] tableWidth = {0.15f, 0.17f, 0.17f, 0.17f, 0.17f, 0.17f};
        PdfPTable table = utils.createTable(tableWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(titleCell("序号"));
        table.addCell(titleCell("指标名称"));
        table.addCell(titleCell("检查日期"));
        table.addCell(titleCell("检查结果"));
        table.addCell(titleCell("参考标准"));
        table.addCell(titleCell("单位"));

        String diagnosisDate = JodaTimeTools.toString(diagnosisVo.getDiagnosisDate(), JodaTimeTools.FORMAT_6);

        table.addCell(myCell("1"));
        table.addCell(myCell("体重"));
        table.addCell(myCell(diagnosisDate));
        table.addCell(myCell((diagnosisVo.getDiagnosisCustWeight() == null) ? "" : diagnosisVo.getDiagnosisCustWeight()
                + ""));
        table.addCell(myCell(""));
        table.addCell(myCell("kg"));

        table.addCell(myCell("2"));
        table.addCell(myCell("收缩压"));
        table.addCell(myCell(diagnosisDate));
        table.addCell(myCell((diagnosisVo.getDiagnosisSystolic() == null) ? "" : diagnosisVo.getDiagnosisSystolic()
                + ""));
        table.addCell(myCell(""));
        table.addCell(myCell("mmHg"));

        table.addCell(myCell("3"));
        table.addCell(myCell("舒张压"));
        table.addCell(myCell(diagnosisDate));
        table.addCell(myCell((diagnosisVo.getDiagnosisDiastolic() == null) ? "" : diagnosisVo.getDiagnosisDiastolic()
                + ""));
        table.addCell(myCell(""));
        table.addCell(myCell("mmHg"));

        document.add(table);
    }

    /**
     * 第三步：实验室指标
     * 第四步：膳食及生活方式指标
     * 第五步：人体成分指标
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @param number
     * @throws DocumentException
     */
    private void setExamItem(Document document, List<ExamItemPojo> examItemList) throws DocumentException {
        if (CollectionUtils.isNotEmpty(examItemList)) {
            // float[] tableWidth = {0.15f, 0.17f, 0.17f, 0.17f, 0.17f, 0.17f};
            PdfPTable table = utils.createTable(7, Element.ALIGN_CENTER, 100f, 10f, 0);
            table.addCell(titleCell("序号"));
            table.addCell(titleCell("指标名称"));
            table.addCell(titleCell("检查日期"));
            table.addCell(titleCell("检查结果"));
            table.addCell(titleCell("结论"));
            table.addCell(titleCell("参考标准"));
            table.addCell(titleCell("单位"));

            for (int i = 1; i <= examItemList.size(); i++) {
                ExamItemPojo item = examItemList.get(i - 1);
                table.addCell(myCell(i + ""));
                table.addCell(myCell(item.getItemName()));
                table.addCell(myCell(JodaTimeTools.toString(item.getExamDatetime(), JodaTimeTools.FORMAT_6)));
                table.addCell(myCell(item.getItemString()));
                table.addCell(myCell(item.getItemResult()));
                table.addCell(myCell(item.getItemRefValue()));
                table.addCell(myCell(item.getItemUnit()));
            }
            document.add(table);
        }
    }

    /**
     * 第六步：辅助检查项目
     * 
     * @author zcq
     * @param document
     * @param courseList
     * @throws DocumentException
     */
    private void setFuzhu(Document document, List<DiagnosisQuotaItemVo> quotaItemList) throws DocumentException {
        PdfPTable table = utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0);

        String itemNames = "";
        if (CollectionUtils.isNotEmpty(quotaItemList)) {
            for (DiagnosisQuotaItemVo item : quotaItemList) {
                itemNames += "、" + item.getInspectItemName();
            }
        }
        table.addCell(contentCell(itemNames.replaceFirst("、", "")));

        document.add(table);
    }

    // ****************************************************工具方法*****************************************************

    private void addTitleTable(String titleName) throws DocumentException {
        PdfPCell cell;
        float[] titleWidth = new float[] {0.05f, 0.95f};
        PdfPTable titleTable = utils.createTable(titleWidth, Element.ALIGN_CENTER, 100f, 5f, 0);
        titleTable.addCell(titleCell(" "));
        cell = utils.baseCell(titleName, utils.infoFont);
        cell.setBackgroundColor(utils.lightPinkColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(utils.whiteColor);
        titleTable.addCell(cell);
        document.add(titleTable);
    }

    public PdfPCell infoCell(String content, int type) {
        PdfPCell cell = utils.baseCell(content, utils.infoFont);
        cell.setBorderColor(utils.lightThiredRedColor);
        if (type == 1) {
            cell.setBackgroundColor(utils.lightPinkColor);
        }
        return cell;
    }

    private PdfPCell titleCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.titleFont);
        cell.setBackgroundColor(utils.pinkColor);
        cell.setBorderColor(utils.whiteColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell contentCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(0);// 设置行高为自适应
        cell.setLeading(0, 1.5f);// 设置行间距
        return cell;
    }

    private PdfPCell myCell(String content) {
        PdfPCell cell = utils.baseCell(content, utils.contentFont);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
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
        int sta = 200;
        int len = a[0].length() > 1 ? 13 : 9;
        document.add(utils.createParagraph(a[0], 10, utils.pinkColor, sta, -15, 0));
        document.add(utils.createParagraph("+" + a[1], 8, utils.pinkColor, sta + len, -5, 20));
    }
}
