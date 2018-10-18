
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.utils.pdf.PdfUtils;
import com.mnt.health.utils.pdf.ReportForm;
import com.mnt.health.utils.times.JodaTimeTools;
import com.mnt.preg.examitem.constant.ExamItemConstant;
import com.mnt.preg.examitem.entity.ExamResultRecord;
import com.mnt.preg.examitem.pojo.ExamResultRecordPojo;
import com.mnt.preg.examitem.util.ExamResultRecordUtilImpl;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.service.PregDiagnosisService;
import com.mnt.preg.platform.service.PregDiagnosisServiceImpl;
import com.mnt.preg.system.pojo.CodePojo;
import com.mnt.preg.system.service.CodeServiceImpl;
import com.mnt.preg.web.CacheProjectInfo;
import com.mnt.preg.web.constants.ConstantPath;

/**
 * PDF抽象类
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-4-7 zcq 初版
 */
public abstract class AbstractPdf<T> {

    public Document document;

    public PdfWriter writer;

    public PdfUtils utils;

    public Map<String, String> codeMap = new HashMap<String, String>();

    /**
     * 创建PDF报告（通用）
     * 
     * @author zcq
     * @param reportForm
     * @return
     */
    public String create(ReportForm reportForm) {
        CodeServiceImpl codeService = (CodeServiceImpl) CacheProjectInfo.getInstance().getApplicationContext()
                .getBean("codeServiceImpl");
        // 设置代码表
        List<CodePojo> codeList = codeService.queryCodeAll();
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo code : codeList) {
                codeMap.put(code.getCodeKind() + code.getCodeValue(), code.getCodeName());
            }
        }

        String absolutePath = this.readProperties().getProperty("resource.absolute.path");
        String pdfPath = reportForm.getReportPath();
        String reportCode = reportForm.getReportCode();
        String reportItem = reportForm.getReportItem();
        String createDate = reportForm.getCreateDate();
        String insId = codeService.getInsId();
        reportForm.setInsId(insId);
        if (StringUtils.isEmpty(pdfPath)) {
            String reportName = reportCode + "_" + ExamItemConstant.itemReportMap.get(reportItem);
            if (StringUtils.isEmpty(createDate)) {
                createDate = JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_6);
            }
            reportForm.setReportName(reportName);

            // 生成路径
            String dietReportPath = ConstantPath.PDF_PATH + insId + "/" + reportItem + "/"
                    + createDate + "/";// 报告路径
            new File(absolutePath + dietReportPath).mkdirs();
            pdfPath = dietReportPath + reportName;
            reportForm.setReportPath(pdfPath);

            // 创建PDF
            this.createPdf(reportForm);
        } else {
            String path = absolutePath + pdfPath;
            if (!new File(path).exists()) {
                new File(path.substring(0, path.lastIndexOf("/"))).mkdirs();
                // 创建PDF
                this.createPdf(reportForm);
            }
        }

        return reportForm.getReportPath();
    }

    /**
     * 检测项目创建PDF报告（专用）
     * 
     * @author zcq
     * @param reportCode
     * @return
     */
    public String create(String reportCode) {
        ExamResultRecordUtilImpl resultRecordService = (ExamResultRecordUtilImpl) CacheProjectInfo.getInstance()
                .getApplicationContext().getBean("examResultRecordUtilImpl");
        PregDiagnosisService pregDiagnosisService = (PregDiagnosisServiceImpl) CacheProjectInfo.getInstance()
                .getApplicationContext().getBean("pregDiagnosisServiceImpl");
        CodeServiceImpl codeService = (CodeServiceImpl) CacheProjectInfo.getInstance().getApplicationContext()
                .getBean("codeServiceImpl");
        // 设置代码表
        List<CodePojo> codeList = codeService.queryCodeAll();
        if (CollectionUtils.isNotEmpty(codeList)) {
            for (CodePojo code : codeList) {
                codeMap.put(code.getCodeKind() + code.getCodeValue(), code.getCodeName());
            }
        }
        // 获取分析结果信息
        PregDiagnosisItemsVo diagnosisItem = pregDiagnosisService.getDiagnosisItem(reportCode);
        String examCode = diagnosisItem.getResultCode();
        String examCategory = diagnosisItem.getInspectCode();

        ExamResultRecordPojo inpsection = resultRecordService.getExamResultRecordByExamCodeAndExamCategory(examCode,
                examCategory);
        String absolutePath = this.readProperties().getProperty("resource.absolute.path");
        String examPdf = inpsection.getExamPdf();
        ReportForm reportForm = new ReportForm();
        reportForm.setReportCode(reportCode);
        reportForm.setReportPath(examPdf);
        reportForm.setInsId(codeService.getInsId());

        if (StringUtils.isEmpty(examPdf)) {
            String reportName = examCode + "_" + ExamItemConstant.itemReportMap.get(inpsection.getExamCategory());
            String createDate = "";
            if (inpsection.getExamDate() == null) {
                createDate = JodaTimeTools.toString(new Date(), JodaTimeTools.FORMAT_6);
            } else {
                createDate = JodaTimeTools.toString(inpsection.getExamDate(), JodaTimeTools.FORMAT_6);
            }
            reportForm.setReportName(reportName);

            // 生成路径
            String dietReportPath = ConstantPath.PDF_PATH + resultRecordService.getInsId() + "/" + examCategory + "/"
                    + createDate + "/";// 报告路径
            new File(absolutePath + dietReportPath).mkdirs();
            examPdf = dietReportPath + reportName;
            reportForm.setReportPath(examPdf);

            // 创建PDF
            this.createPdf(reportForm);

            // 完善结果主表PDF报告路径
            ExamResultRecord examResultRecord = new ExamResultRecord();
            examResultRecord.setExamCode(examCode);
            examResultRecord.setExamPdf(examPdf);
            resultRecordService.updateExamResultRecord(examResultRecord);
        } else {
            String path = absolutePath + examPdf;
            if (!new File(path).exists()) {
                new File(path.substring(0, path.lastIndexOf("/"))).mkdirs();
                // 创建PDF
                this.createPdf(reportForm);
            }
        }

        return reportForm.getReportPath();
    }

    /**
     * 创建PDF报告
     * 
     * @author zcq
     * @param reportForm
     */
    public void createPdf(ReportForm reportForm) {
        String absolutePath = this.readProperties().getProperty("resource.absolute.path");
        FileOutputStream out = null;
        try {
            // 第一步：数据准备
            T t = this.beforeCreatePdf(reportForm);

            // 第二步：初始化 document 和 write
            utils = PdfUtils.getIns();// 获取工具类实例
            document = new Document();// 创建PDF
            document.setMargins(utils.marginLeft, utils.marginRight, utils.marginTop, utils.marginBottom);// 设置PDF边距
            if (reportForm == null) {
                throw new DocumentException("输入参数reportForm是空的");
            }
            out = new FileOutputStream(absolutePath + reportForm.getReportPath());// 文件流
            writer = PdfWriter.getInstance(document, out);// 创建模板
            // writer.setPageEvent(new PdfHeaderFooter(reportForm));// 设置页眉与页脚
            document.open();// 打开

            // 第三步：PDF数据处理
            this.handler(t);

            // 第四步：PDF创建完，后续关联数据处理
            this.afterCreatePdf(reportForm);

        } catch (FileNotFoundException e) {
            throw new ServiceException(ResultCode.ERROR_60002);
        } catch (DocumentException e) {
            throw new ServiceException(ResultCode.ERROR_60003);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                reportForm.setReportPath("");
                if (e.getMessage().indexOf("The document has no pages") > -1) {
                    System.out.println("生成PDF报告时没有检索到内容！");
                } else {
                    throw new ServiceException(e.getMessage());
                }
            }
        }
    }

    /**
     * 生成PDF
     * 
     * @author zcq
     * @param pdfData
     * @throws DocumentException
     */
    public abstract void handler(T pdfData) throws DocumentException;

    /**
     * 数据准备
     * 
     * @author zcq
     * @param reportForm
     * @return
     */
    public T beforeCreatePdf(ReportForm reportForm) {
        return null;
    }

    /**
     * 后续关联数据处理
     * 
     * @author zcq
     * @param reportForm
     */
    public void afterCreatePdf(ReportForm reportForm) {

    }

    /**
     * 读取配置文件
     * 
     * @author zcq
     */
    public Properties readProperties() {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("config-web.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }
}
