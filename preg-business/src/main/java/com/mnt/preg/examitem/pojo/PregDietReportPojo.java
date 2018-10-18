
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 膳食报告
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-14 zcq 初版
 */
public class PregDietReportPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 膳食评估记录 */
    private PregFoodRecordPojo foodRecord;

    /** 饮食习惯 */
    private PregHabitPojo pregnancyHabitVo;

    /** 饮食频率 */
    private List<PregFoodFrequencyPojo> foodFrequencyList;

    /** 用餐明细 */
    private List<PregFoodDetailsPojo> foodDetailsList;

    /** 用餐时间和感受 */
    private List<PregFoodFeelTimePojo> foodFeelTimeList;

    /** 每种元素的含量 */
    private PregDietAnalysePojo element;

    /** 餐次供能量 */
    private PregDietAnalysePojo energy;

    /** DRIs值 */
    private PregDietAnalysePojo dris;

    /** GI值 */
    private PregDietAnalysePojo gl;

    /** 蛋白质和脂肪重量 */
    private PregDietAnalysePojo protidAndFat;

    /** PDF报告路径 */
    private String dietReportPath;

    /** 体检号 */
    private String custExamCode;

    /** 体检指标 */
    private Map<String, ExamItemPojo> examMap;

    /** 接诊信息 */
    private PregDiagnosisPojo diagnosis;

    /** 接诊项目信息 */
    private PregDiagnosisItemsVo diagnosisItem;

    /** 患者信息 */
    private CustomerPojo customer;

    /** 建档信息 */
    private PregArchivesPojo pregnancyArchives;

    /** 机构编码 */
    private String insId;

    public PregFoodRecordPojo getFoodRecord() {
        return foodRecord;
    }

    public void setFoodRecord(PregFoodRecordPojo foodRecord) {
        this.foodRecord = foodRecord;
    }

    public PregHabitPojo getPregnancyHabitVo() {
        return pregnancyHabitVo;
    }

    public void setPregnancyHabitVo(PregHabitPojo pregnancyHabitVo) {
        this.pregnancyHabitVo = pregnancyHabitVo;
    }

    public List<PregFoodFrequencyPojo> getFoodFrequencyList() {
        return foodFrequencyList;
    }

    public void setFoodFrequencyList(List<PregFoodFrequencyPojo> foodFrequencyList) {
        this.foodFrequencyList = foodFrequencyList;
    }

    public List<PregFoodDetailsPojo> getFoodDetailsList() {
        return foodDetailsList;
    }

    public void setFoodDetailsList(List<PregFoodDetailsPojo> foodDetailsList) {
        this.foodDetailsList = foodDetailsList;
    }

    public PregDietAnalysePojo getElement() {
        return element;
    }

    public void setElement(PregDietAnalysePojo element) {
        this.element = element;
    }

    public PregDietAnalysePojo getEnergy() {
        return energy;
    }

    public void setEnergy(PregDietAnalysePojo energy) {
        this.energy = energy;
    }

    public PregDietAnalysePojo getDris() {
        return dris;
    }

    public void setDris(PregDietAnalysePojo dris) {
        this.dris = dris;
    }

    public PregDietAnalysePojo getGl() {
        return gl;
    }

    public void setGl(PregDietAnalysePojo gl) {
        this.gl = gl;
    }

    public PregDietAnalysePojo getProtidAndFat() {
        return protidAndFat;
    }

    public void setProtidAndFat(PregDietAnalysePojo protidAndFat) {
        this.protidAndFat = protidAndFat;
    }

    public String getDietReportPath() {
        return dietReportPath;
    }

    public void setDietReportPath(String dietReportPath) {
        this.dietReportPath = dietReportPath;
    }

    public String getCustExamCode() {
        return custExamCode;
    }

    public void setCustExamCode(String custExamCode) {
        this.custExamCode = custExamCode;
    }

    public Map<String, ExamItemPojo> getExamMap() {
        return examMap;
    }

    public void setExamMap(Map<String, ExamItemPojo> examMap) {
        this.examMap = examMap;
    }

    public PregDiagnosisPojo getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(PregDiagnosisPojo diagnosis) {
        this.diagnosis = diagnosis;
    }

    public CustomerPojo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerPojo customer) {
        this.customer = customer;
    }

    public PregArchivesPojo getPregnancyArchives() {
        return pregnancyArchives;
    }

    public void setPregnancyArchives(PregArchivesPojo pregnancyArchives) {
        this.pregnancyArchives = pregnancyArchives;
    }

    public List<PregFoodFeelTimePojo> getFoodFeelTimeList() {
        return foodFeelTimeList;
    }

    public void setFoodFeelTimeList(List<PregFoodFeelTimePojo> foodFeelTimeList) {
        this.foodFeelTimeList = foodFeelTimeList;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public PregDiagnosisItemsVo getDiagnosisItem() {
        return diagnosisItem;
    }

    public void setDiagnosisItem(PregDiagnosisItemsVo diagnosisItem) {
        this.diagnosisItem = diagnosisItem;
    }

}
