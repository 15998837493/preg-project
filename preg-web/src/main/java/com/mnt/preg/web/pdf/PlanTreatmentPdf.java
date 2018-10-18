
package com.mnt.preg.web.pdf;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.mnt.preg.platform.pojo.DiagnosisBookingPojo;
import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanGroupPojo;
import com.mnt.preg.platform.pojo.PregIntervenePlanPojo;
import com.mnt.preg.platform.pojo.PregPlanCoursePojo;
import com.mnt.preg.platform.pojo.PregPlanDietPojo;
import com.mnt.preg.platform.pojo.PregPlanIntakeDetailPojo;
import com.mnt.preg.platform.pojo.PregPlanPointsPojo;
import com.mnt.preg.web.constants.PathConstant;
import com.mnt.preg.web.constants.PublicConstant;

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
public class PlanTreatmentPdf extends AbstractPdf<PregIntervenePlanGroupPojo> {

    /**
     * 设置干预方案报告的内容
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @throws DocumentException
     */
    @Override
    public void handler(PregIntervenePlanGroupPojo planGroupVo) throws DocumentException {
        List<String> itemList = planGroupVo.getDietItemList();

        document.newPage();
        String imagPath = readProperties().getProperty("resource.absolute.path")
                + "resource/template/image/cover/report-qr-code.png";
        utils.addImage(document, imagPath, 465f, 737f, 35);

        // 设置报告头
        float[] principleWidth = {0.38f, 0.62f};
        addContentTableHead0(utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 50f, 0), planGroupVo);

        // 信息栏
        float[] table0Width = new float[] {0.1f, 0.15f, 0.1f, 0.15f, 0.1f, 0.2f, 0.2f};
        addContentTable0(utils.createTable(table0Width, Element.ALIGN_CENTER, 100f, 10f, 0), planGroupVo);// 添加内容

        // 处方订单
        if (itemList.contains("P03001")) {
            List<DiagnosisPrescriptionPojo> extenderList = planGroupVo.getExtenderList();
            if (CollectionUtils.isNotEmpty(extenderList)) {
                addTitleTable("处方明细");// 添加标题
                setOrderDetail(document, extenderList);
            }
        }

        PregIntervenePlanPojo planPojo = planGroupVo.getPlanVo();
        if (planPojo != null) {
            // 饮食原则
            if (itemList.contains("P03002")) {
                List<PregPlanPointsPojo> pointsList = planGroupVo.getPlanPointList();
                if (CollectionUtils.isNotEmpty(pointsList)) {
                    String pregStage = codeMap.get("PREGNANT_STAGE"
                            + planGroupVo.getDiagnosis().getDiagnosisPregnantStage());
                    addTitleTable(pregStage + "饮食原则");// 添加标题
                    setPrinciple(document, pointsList);
                }
            }
            // 膳食方案明细
            if (itemList.contains("P03003")) {
                List<PregPlanIntakeDetailPojo> intakeDetailList = planGroupVo.getPlanIntakeDetailList();
                if (CollectionUtils.isNotEmpty(intakeDetailList)) {
                    addTitleTable("膳食方案明细");// 添加标题
                    setPlanInfo(document, planPojo, intakeDetailList);// 膳食方案明细
                }
                // 食物选择
                String foodRecommend = planPojo.getFoodRecommend();
                if (StringUtils.isNotEmpty(foodRecommend)) {
                    addTitleTable("食物选择");// 添加标题
                    setFoodRecommend(document, foodRecommend);
                }
            }
            // 一日膳食清单
            if (itemList.contains("P03004")) {
                List<PregPlanIntakeDetailPojo> intakeDetailList = planGroupVo.getPlanIntakeDetailList();
                if (CollectionUtils.isNotEmpty(intakeDetailList)) {
                    addTitleTable("膳食执行清单");// 添加标题
                    setDietIntake(document, intakeDetailList);// 一日膳食清单
                }
            }
            // 一周食谱
            if (itemList.contains("P03005")) {
                List<PregPlanDietPojo> dietList = planGroupVo.getPlanDietList();
                if (CollectionUtils.isNotEmpty(dietList)) {
                    addTitleTable("食谱");// 添加标题
                    setDietInfo(document, dietList);
                }
            }
        }
        // 复检复查提醒
        if (itemList.contains("P03007")) {
            List<DiagnosisBookingPojo> diagnosisBookingList = planGroupVo.getDiagnosisBookingList();
            List<DiagnosisQuotaItemVo> quotaItemList = planGroupVo.getFuzhuList();
            if (CollectionUtils.isNotEmpty(diagnosisBookingList) || CollectionUtils.isNotEmpty(quotaItemList)) {
                addTitleTable("复查复诊预约");// 添加标题
                setDiagnosisBooking(document, diagnosisBookingList, quotaItemList);
            }
        }
        // 教育课程
        if (itemList.contains("P03006")) {
            List<PregPlanCoursePojo> pregCourseList = planGroupVo.getPlanPregCourseList();
            if (CollectionUtils.isNotEmpty(pregCourseList)) {
                addTitleTable("教育课程");// 添加标题
                setPregCourse(document, pregCourseList);
            }
            List<PregPlanCoursePojo> disCourseList = planGroupVo.getPlanDiseaseCourseList();
            if (CollectionUtils.isNotEmpty(disCourseList)) {
                addTitleTable("诊断课程");// 添加标题
                setDiseaseCourse(document, disCourseList);
            }
        }
        // 课程预约提醒
        if (itemList.contains("P03008")) {
            List<PregCourseBookingPojo> courseBookingList = planGroupVo.getCourseBookingList();
            if (CollectionUtils.isNotEmpty(courseBookingList)) {
                List<PregCourseBookingPojo> courseBookingList2 = new ArrayList<PregCourseBookingPojo>();
                if (planGroupVo.getDiagnosis() != null) {
                    Date diagnosisDate = planGroupVo.getDiagnosis().getDiagnosisDate();
                    for (PregCourseBookingPojo pojo : courseBookingList) {
                        if (pojo.getBookingDate().compareTo(diagnosisDate) == 1) {// 过滤：预约日期必须大于接诊日期
                            courseBookingList2.add(pojo);
                        }
                    }
                    addTitleTable("课程预约");// 添加标题
                    setCourseBooking(document, courseBookingList2);
                }
            }
        }
        // 分界线
        setHr(utils.createTable(1, Element.ALIGN_CENTER, 100f, 50f, 0), 5f, utils.lightGrayColor, "end");
        // 页尾
        setPageLast(planGroupVo);
    }

    // **************************************************设置报告明细********************************************************

    /**
     * 设置表头
     * 
     * @author zcq
     * @param table
     * @throws DocumentException
     */
    private void addContentTableHead0(PdfPTable table, PregIntervenePlanGroupPojo planGroupVo) throws DocumentException {
        // 添加图片
        addImgLogo(table, planGroupVo);
        PdfPCell cell = utils.baseCell("     干预方案报告", utils.reportFont);
        cell.setBackgroundColor(utils.pinkColor);
        cell.setBorderColor(utils.pinkColor);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
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
    private void addContentTable0(PdfPTable table, PregIntervenePlanGroupPojo planGroupVo) throws DocumentException {
        PregDiagnosisPojo diagnosisPojo = planGroupVo.getDiagnosis();
        CustomerPojo custInfo = planGroupVo.getCustomerVo();
        PregDiagnosisPojo diagnosis = planGroupVo.getDiagnosis();
        PregArchivesPojo pregArchivesPojo = planGroupVo.getPregArchivesVo();
        table.addCell(infoCell("ID：", 1));
        table.addCell(infoCell(custInfo.getCustPatientId(), 0));
        table.addCell(infoCell("姓名：", 1));
        table.addCell(infoCell(custInfo.getCustName(), 0));
        table.addCell(infoCell("检测日期：", 1));
        table.addCell(infoCell(JodaTimeTools.toString(diagnosisPojo.getDiagnosisDate(), JodaTimeTools.FORMAT_6), 0));

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
     * 第一步：处方明细
     * 
     * @author zcq
     * @param document
     * @param orderDetailList
     * @throws DocumentException
     */
    private void setOrderDetail(Document document, List<DiagnosisPrescriptionPojo> extenderList)
            throws DocumentException {
        float[] principleWidth = {0.47f, 0.08f, 0.08f, 0.08f, 0.12f, 0.07f, 0.1f};
        PdfPTable table = utils.createTable(principleWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(titleCell("名称"));
        table.addCell(titleCell("单次剂量"));
        table.addCell(titleCell("剂量说明"));
        table.addCell(titleCell("用法"));
        table.addCell(titleCell("频次"));
        table.addCell(titleCell("疗程"));
        table.addCell(titleCell("规格"));
        for (DiagnosisPrescriptionPojo detail : extenderList) {
            table.addCell(contentCell(detail.getProductName()));
            table.addCell(contentCell(detail.getProductDosage() + " " + detail.getProductUnit()));
            table.addCell(myCell(detail.getProductDosageDesc()));
            table.addCell(myCell(detail.getProductUsageMethod()));
            table.addCell(contentCell(codeMap.get("PRODUCTFREQUENCY" + detail.getProductFrequency())));
            table.addCell(contentCell(detail.getProductTreatment()));
            table.addCell(myCell(detail.getProductStandard()));
        }
        document.add(table);
    }

    /**
     * 第二步：饮食原则
     * 
     * @author zcq
     * @param document
     * @param principleList
     * @param number
     * @throws DocumentException
     */
    private void setPrinciple(Document document, List<PregPlanPointsPojo> principleList)
            throws DocumentException {
        if (CollectionUtils.isNotEmpty(principleList)) {

            List<PregPlanPointsPojo> list1 = new ArrayList<PregPlanPointsPojo>();
            List<PregPlanPointsPojo> list2 = new ArrayList<PregPlanPointsPojo>();
            for (PregPlanPointsPojo principle : principleList) {
                String pointType = principle.getPointType();
                if ("preg".equals(pointType)) {
                    list1.add(principle);
                } else {
                    list2.add(principle);
                }
            }
            // 孕期原则
            if (CollectionUtils.isNotEmpty(list1)) {
                PdfPTable table = null;
                if (CollectionUtils.isNotEmpty(list2)) {
                    table = utils.createTable(1, Element.ALIGN_CENTER, 100f, 5f, 0);
                } else {
                    table = utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0);
                }
                for (PregPlanPointsPojo principle : list1) {
                    table.addCell(contentCell(principle.getPointDesc()));
                }
                document.add(table);
            }
            // 诊断原则
            if (CollectionUtils.isNotEmpty(list2)) {
                for (PregPlanPointsPojo principle : list2) {
                    addTitleTable(principle.getPointSubclass());// 添加标题
                    PdfPTable table2 = utils.createTable(1, Element.ALIGN_CENTER, 100f, 10f, 0);
                    table2.addCell(contentCell(principle.getPointDesc()));
                    document.add(table2);
                }
            }
        }
    }

    /**
     * 第三步：膳食清单--膳食方案
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @param number
     * @throws DocumentException
     */
    private void setPlanInfo(Document document, PregIntervenePlanPojo planVo,
            List<PregPlanIntakeDetailPojo> intakeDetailList) throws DocumentException {

        Map<String, Double> intakeMap = new HashMap<String, Double>();
        Map<String, Double> productMap = new HashMap<String, Double>();
        Map<String, String> unitMap = new HashMap<String, String>();
        for (PregPlanIntakeDetailPojo intakeDetail : intakeDetailList) {
            String intakeFoodType = intakeDetail.getIntakeFoodType();
            String intakeTypeName = intakeDetail.getIntakeTypeName();
            Double intakeCount = intakeDetail.getIntakeCount().doubleValue();
            if ("intake".equals(intakeFoodType)) {
                if (!intakeMap.containsKey(intakeTypeName)) {
                    intakeMap.put(intakeTypeName, intakeCount);
                } else {
                    intakeMap.put(intakeTypeName, intakeMap.get(intakeTypeName) + intakeCount);
                }
            } else {
                if (!productMap.containsKey(intakeTypeName)) {
                    productMap.put(intakeTypeName, intakeCount);
                    unitMap.put(intakeTypeName, intakeDetail.getIntakeUnit());
                } else {
                    productMap.put(intakeTypeName, productMap.get(intakeTypeName) + intakeCount);
                }
            }
        }
        StringBuilder intakeFood = new StringBuilder();
        for (String key : intakeMap.keySet()) {
            intakeFood.append("、 " + key + " " + intakeMap.get(key) + " 份");
        }
        StringBuilder productFood = new StringBuilder();
        for (String key : productMap.keySet()) {
            productFood.append("、 " + key + " " + productMap.get(key) + " " + unitMap.get(key));
        }
        float[] yuanzeWidth = {0.14f, 0.86f};
        PdfPTable intakeTable = utils.createTable(yuanzeWidth, Element.ALIGN_CENTER, 100f, 5f, 0);
        intakeTable.addCell(infoCell("普通食物", 1));
        intakeTable.addCell(contentCell(intakeFood.toString().replaceFirst("、", "")));
        document.add(intakeTable);
        PdfPTable productTable = utils.createTable(yuanzeWidth, Element.ALIGN_CENTER, 100f, 5f, 0);
        productTable.addCell(infoCell("营养食品", 1));
        productTable.addCell(contentCell(productFood.toString().replaceFirst("、", "")));
        document.add(productTable);

        PdfPTable table = utils.createTable(3, Element.ALIGN_CENTER, 100f, 5f, 0);
        table.addCell(titleCell("名称"));
        table.addCell(titleCell("计算值"));
        table.addCell(titleCell("占比"));

        table.addCell(myCell("能量"));
        table.addCell(myCell(planVo.getIntakeCaloric() + "（kcal）"));
        table.addCell(myCell(""));

        table.addCell(myCell("碳水化合物"));
        table.addCell(myCell(planVo.getIntakeCbr() + "（g）"));
        table.addCell(myCell(planVo.getIntakeCbrPercent()));

        table.addCell(myCell("蛋白质"));
        table.addCell(myCell(planVo.getIntakeProtein() + "（g）"));
        table.addCell(myCell(planVo.getIntakeProteinPercent()));

        table.addCell(myCell("脂肪"));
        table.addCell(myCell(planVo.getIntakeFat() + "（g）"));
        table.addCell(myCell(planVo.getIntakeFatPercent()));

        document.add(table);
    }

    /**
     * 第三步：膳食清单--摄入量明细
     * 
     * @author zcq
     * @param document
     * @param planGroupVo
     * @param number
     * @throws DocumentException
     */
    private void setDietIntake(Document document, List<PregPlanIntakeDetailPojo> intakeDetailList)
            throws DocumentException {
        NumberFormat nf = NumberFormat.getInstance();
        PdfPCell cell = null;

        float[] yuanzeWidth = {0.11f, 0.89f};
        PdfPTable table = utils.createTable(yuanzeWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        cell = titleCell("一日膳食清单");
        cell.setColspan(2);
        table.addCell(cell);

        Map<String, Map<String, List<PregPlanIntakeDetailPojo>>> map = setIntakeDetail(intakeDetailList);
        for (String mealType : map.keySet()) {
            table.addCell(myCell(mealType));// 餐次
            Map<String, String> detailMap = new HashMap<String, String>();
            for (String foodType : map.get(mealType).keySet()) {
                if (!detailMap.containsKey(foodType)) {
                    detailMap.put(foodType, "");
                }
                List<PregPlanIntakeDetailPojo> detailList = map.get(mealType).get(foodType);
                for (PregPlanIntakeDetailPojo detail : detailList) {
                    String type = detail.getIntakeType();
                    if ("intake".equals(type)) {
                        detailMap.put(foodType,
                                detailMap.get(foodType) + "、" + detail.getIntakeTypeName());
                    } else {
                        BigDecimal count = detail.getIntakeCount();
                        String intakeCount = "";
                        if (count != null) {
                            intakeCount = nf.format(count);
                        }
                        detailMap.put(foodType, detailMap.get(foodType) + "、" + detail.getIntakeTypeName() + " "
                                + intakeCount + "份 ");
                    }
                }
            }
            String detailName = "";
            for (String key : detailMap.keySet()) {
                String name = detailMap.get(key).replaceFirst("、", "");
                detailName += name + "\n";
            }
            table.addCell(contentCell(detailName));
        }

        document.add(table);
    }

    /**
     * 第三步 食物选择
     * 
     * @author xdc
     * @param document
     * @param planGroupVo
     * @throws DocumentException
     */
    private void setFoodRecommend(Document document, String foodRecommend) throws DocumentException {
        PdfPTable table = utils.createTable(1, Element.ALIGN_CENTER, 100f, 5f, 0);
        table.addCell(contentCell(foodRecommend));
        document.add(table);
    }

    /**
     * 第四步：食谱
     * 
     * @author zcq
     * @param document
     * @param dietList
     * @param number
     * @throws DocumentException
     */
    private void setDietInfo(Document document, List<PregPlanDietPojo> dietList) throws DocumentException {
        float[] dietWidth = {0.13f, 0.15f, 0.24f, 0.3f, 0.18f};
        PdfPTable table = utils.createTable(dietWidth, Element.ALIGN_CENTER, 100f, 10f, 1);
        table.addCell(titleCell("天数"));
        table.addCell(titleCell("餐次"));
        table.addCell(titleCell("食谱名称"));
        table.addCell(titleCell("食材"));
        table.addCell(titleCell("可食部重量（g）"));

        setDietTable(table, dietList);

        document.add(table);
    }

    /**
     * 复查复诊预约
     * 
     * @author zcq
     * @param document
     * @param diagnosisPojo
     * @param quotaItemList
     * @throws DocumentException
     */
    private void setDiagnosisBooking(Document document, List<DiagnosisBookingPojo> diagnosisBookingList,
            List<DiagnosisQuotaItemVo> quotaItemList) throws DocumentException {
        float[] tableWidth = {0.13f, 0.87f};
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
            table.addCell(myCell("复查检测"));
            table.addCell(contentCell("复诊建议：" + diagnosisPojo.getBookingSuggest() + " "
                    + diagnosisPojo.getBookingRemark() + "\n" + "复诊时间："
                    + JodaTimeTools.toString(diagnosisPojo.getBookingDate(), JodaTimeTools.FORMAT_6) + "\n" + "复查检测："
                    + itemNames.replaceFirst("、", "") + "\n" + "复查时间：" + suggest));
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
            table.addCell(myCell("复查检测"));
            table.addCell(contentCell("复查检测：" + itemNames.replaceFirst("、", "")));
        } else {
            DiagnosisBookingPojo diagnosisPojo = diagnosisBookingList.get(diagnosisBookingList.size() - 1);
            table.addCell(myCell("复查检测"));
            table.addCell(contentCell("复诊建议：" + diagnosisPojo.getBookingSuggest() + " "
                    + diagnosisPojo.getBookingRemark() + "\n" + "复诊时间："
                    + JodaTimeTools.toString(diagnosisPojo.getBookingDate(), JodaTimeTools.FORMAT_6)));
        }

        document.add(table);
    }

    /**
     * 第五步：基础课程
     * 
     * @author zcq
     * @param document
     * @param courseList
     * @throws DocumentException
     */
    private void setPregCourse(Document document, List<PregPlanCoursePojo> courseList) throws DocumentException {
        float[] dietWidth = {0.1f, 0.9f};
        PdfPTable table = utils.createTable(dietWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(titleCell("课程代码"));
        table.addCell(titleCell("课程名称"));

        for (PregPlanCoursePojo course : courseList) {
            table.addCell(myCell(course.getPregDeCode()));
            table.addCell(contentCell(course.getPregDeName()));
        }

        document.add(table);
    }

    /**
     * 第六步：诊断课程
     * 
     * @author zcq
     * @param document
     * @param courseList
     * @throws DocumentException
     */
    private void setDiseaseCourse(Document document, List<PregPlanCoursePojo> courseList) throws DocumentException {
        float[] dietWidth = {0.2f, 0.2f, 0.6f};
        PdfPTable table = utils.createTable(dietWidth, Element.ALIGN_CENTER, 100f, 10f, 0);
        table.addCell(titleCell("诊断"));
        table.addCell(titleCell("课程代码"));
        table.addCell(titleCell("课程名称"));

        Map<String, List<PregPlanCoursePojo>> map = new HashMap<String, List<PregPlanCoursePojo>>();
        for (PregPlanCoursePojo course : courseList) {
            String diseaseName = course.getDiseaseName();
            if (!map.containsKey(diseaseName)) {
                map.put(diseaseName, new ArrayList<PregPlanCoursePojo>());
            }
            map.get(diseaseName).add(course);
        }
        PdfPCell cell = null;
        for (String key : map.keySet()) {
            List<PregPlanCoursePojo> list = map.get(key);
            cell = contentCell(key);
            cell.setRowspan(list.size());
            table.addCell(cell);
            for (PregPlanCoursePojo course : list) {
                table.addCell(myCell(course.getPregDeName()));
                table.addCell(contentCell(course.getCourseDesc()));
            }
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
        PdfPTable table = utils.createTable(dietWidth, Element.ALIGN_CENTER, 100f, 10f, 1);
        table.addCell(titleCell("课程日期"));
        table.addCell(titleCell("课程时间"));
        table.addCell(titleCell("课程内容"));

        for (PregCourseBookingPojo course : courseList) {
            table.addCell(myCell(JodaTimeTools.toString(course.getBookingDate(), JodaTimeTools.FORMAT_6)));
            table.addCell(myCell(course.getCourseTime()));
            table.addCell(contentCell(course.getCourseContent()));
        }

        document.add(table);
    }

    // **************************************************工具类方法********************************************************
    private void addImgLogo(PdfPTable table, PregIntervenePlanGroupPojo planGroupVo) throws DocumentException {
        // 图片
        String basepath = readProperties().getProperty("resource.absolute.path")
                + PathConstant.template_logo + planGroupVo.getInsInfo().getInsId() + PublicConstant.logo2;
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
        cellImg.setBackgroundColor(utils.pinkColor);
        cellImg.setBorderColor(utils.pinkColor);
        cellImg.setFixedHeight(25f);
        cellImg.setPaddingBottom(5f);// 设置下边距
        cellImg.setPaddingTop(5f);// 设置上边距
        cellImg.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellImg.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cellImg);
    }

    /**
     * 设置膳食清单明细
     * 
     * @author zcq
     * @param intakeDetailList
     * @return
     */
    private Map<String, Map<String, List<PregPlanIntakeDetailPojo>>> setIntakeDetail(
            List<PregPlanIntakeDetailPojo> intakeDetailList) {
        Map<String, Map<String, List<PregPlanIntakeDetailPojo>>> map = new LinkedHashMap<String, Map<String, List<PregPlanIntakeDetailPojo>>>();
        // 膳食清单
        if (CollectionUtils.isNotEmpty(intakeDetailList)) {
            for (PregPlanIntakeDetailPojo detail : intakeDetailList) {
                String meal = detail.getIntakeMealtypeName();
                String intakeFoodType = detail.getIntakeFoodType();
                if (!map.containsKey(meal)) {
                    map.put(meal, new LinkedHashMap<String, List<PregPlanIntakeDetailPojo>>());
                }
                if (!map.get(meal).containsKey(intakeFoodType)) {
                    map.get(meal).put(intakeFoodType, new ArrayList<PregPlanIntakeDetailPojo>());
                }
                map.get(meal).get(intakeFoodType).add(detail);
            }
        }
        return map;
    }

    /**
     * 设置食谱信息
     * 
     * @author zcq
     * @param table
     * @param dietList
     */
    private void setDietTable(PdfPTable table, List<PregPlanDietPojo> dietList) {
        PdfPCell cell = null;
        NumberFormat nf = NumberFormat.getInstance();
        Map<String, Map<String, Map<String, List<PregPlanDietPojo>>>> map = getPlanDietMap(dietList);
        Pattern p = Pattern.compile("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$");
        Matcher m = null;
        String foodMaterialAmount = "";
        int count = 1;
        for (String foodDay : map.keySet()) {
            int foodDaySize = 0;
            for (String mealType : map.get(foodDay).keySet()) {
                for (String foodName : map.get(foodDay).get(mealType).keySet()) {
                    foodDaySize += map.get(foodDay).get(mealType).get(foodName).size();
                }
            }
            // cell = myCell(foodDay);
            cell = myCell("" + count++);
            cell.setRowspan(foodDaySize);
            table.addCell(cell);
            for (String mealType : map.get(foodDay).keySet()) {
                int mealTypeSize = 0;
                for (String foodName : map.get(foodDay).get(mealType).keySet()) {
                    mealTypeSize += map.get(foodDay).get(mealType).get(foodName).size();
                }
                cell = myCell(mealType);
                cell.setRowspan(mealTypeSize);
                table.addCell(cell);
                for (String foodName : map.get(foodDay).get(mealType).keySet()) {
                    List<PregPlanDietPojo> list = map.get(foodDay).get(mealType).get(foodName);
                    int foodNameSize = list.size();
                    cell = contentCell(foodName);
                    cell.setRowspan(foodNameSize);
                    table.addCell(cell);
                    for (PregPlanDietPojo diet : list) {
                        if (StringUtils.isNotEmpty(diet.getFoodMaterialAmount())) {
                            m = p.matcher(diet.getFoodMaterialAmount());
                            if (m.find()) {
                                foodMaterialAmount = m.group();
                            } else {
                                p = Pattern.compile("^[1-9]\\d*|0$");
                                m = p.matcher(diet.getFoodMaterialAmount());
                                if (m.find()) {
                                    foodMaterialAmount = m.group();
                                }
                            }
                        }
                        table.addCell(contentCell(diet.getFoodMaterialName()));
                        String amout = "";
                        if (StringUtils.isNotEmpty(foodMaterialAmount)) {
                            amout = nf.format(new BigDecimal(foodMaterialAmount));
                        }
                        table.addCell(myCell(amout));
                    }
                }
            }
        }
    }

    private Map<String, Map<String, Map<String, List<PregPlanDietPojo>>>> getPlanDietMap(List<PregPlanDietPojo> dietList) {
        Map<String, Map<String, Map<String, List<PregPlanDietPojo>>>> map = new LinkedHashMap<String, Map<String, Map<String, List<PregPlanDietPojo>>>>();
        for (PregPlanDietPojo detail : dietList) {
            String foodDay = detail.getFoodDay();
            if (!map.containsKey(foodDay)) {
                map.put(foodDay, new LinkedHashMap<String, Map<String, List<PregPlanDietPojo>>>());
            }
            String mealType = detail.getFoodMealName();
            if (!map.get(foodDay).containsKey(mealType)) {
                map.get(foodDay).put(mealType, new LinkedHashMap<String, List<PregPlanDietPojo>>());
            }
            String foodName = detail.getFoodName();
            if (!map.get(foodDay).get(mealType).containsKey(foodName)) {
                map.get(foodDay).get(mealType).put(foodName, new ArrayList<PregPlanDietPojo>());
            }
            map.get(foodDay).get(mealType).get(foodName).add(detail);
        }
        return map;
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
        cell.setLeading(1, 1.2f);// 设置行间距
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
    private void setPageLast(PregIntervenePlanGroupPojo pdfData) throws DocumentException {
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
        // table1.addCell(myCellWhiteBorder("                                                  送检医生：",PdfPCell.ALIGN_CENTER,
        // 0));
        // }else {
        // table1.addCell(myCellWhiteBorder("送检医生：" + diagnosis.getDiagnosisReferralDoctorName(),
        // PdfPCell.ALIGN_RIGHT, 0));
        // }
        // table1.setSpacingAfter(-3f);
        // document.add(table1);
        PdfPTable table2 = utils.createTable(table1Width1, Element.ALIGN_LEFT, 100f, 6f, 0);
        table2.addCell(myCellWhiteBorder(
                "报告医生：" + pdfData.getDiagnosis().getDiagnosisUserName(),
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
