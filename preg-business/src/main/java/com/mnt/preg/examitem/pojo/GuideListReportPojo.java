
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 引导单pdf
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
public class GuideListReportPojo implements Serializable {

    private static final long serialVersionUID = -5312456448668236216L;

    /** 代谢异常风险因素_病史 */
    private String metabolicBingshi;

    /** 代谢异常风险因素_既往妊娠并发症及分娩史 */
    private String metabolicHistory;

    /** 营养不良风险因素_病史 */
    private String nutritiousBingshi;

    /** 营养不良风险因素_既往妊娠并发症及分娩史 */
    private String nutritiousHistory;

    /** 人体成分结论 */
    private String inbody;

    /** 体能消耗情况 */
    private String rup;

    /** 膳食结构风险 */
    private String foodRisk;

    /** 致病饮食风险 */
    private String illRisk;

    /** 妊娠期膳食摄入问题 */
    private String foodQuestion;

    /** 营养咨询需求 */
    private String nutritionalRequirements;

    /** 过敏情况 */
    private String allergic;

    /** 转诊建议 */
    private String suggest;

    /** 接诊信息 */
    private PregDiagnosisPojo diagnosis;

    /** 患者信息 */
    private CustomerPojo customer;

    /** 建档信息 */
    private PregArchivesPojo pregnancyArchives;

    /** 机构号 */
    private String insId;

    public String getMetabolicBingshi() {
        return metabolicBingshi;
    }

    public void setMetabolicBingshi(String metabolicBingshi) {
        this.metabolicBingshi = metabolicBingshi;
    }

    public String getMetabolicHistory() {
        return metabolicHistory;
    }

    public void setMetabolicHistory(String metabolicHistory) {
        this.metabolicHistory = metabolicHistory;
    }

    public String getNutritiousBingshi() {
        return nutritiousBingshi;
    }

    public void setNutritiousBingshi(String nutritiousBingshi) {
        this.nutritiousBingshi = nutritiousBingshi;
    }

    public String getNutritiousHistory() {
        return nutritiousHistory;
    }

    public void setNutritiousHistory(String nutritiousHistory) {
        this.nutritiousHistory = nutritiousHistory;
    }

    public String getInbody() {
        return inbody;
    }

    public void setInbody(String inbody) {
        this.inbody = inbody;
    }

    public String getRup() {
        return rup;
    }

    public void setRup(String rup) {
        this.rup = rup;
    }

    public String getFoodRisk() {
        return foodRisk;
    }

    public void setFoodRisk(String foodRisk) {
        this.foodRisk = foodRisk;
    }

    public String getIllRisk() {
        return illRisk;
    }

    public void setIllRisk(String illRisk) {
        this.illRisk = illRisk;
    }

    public String getFoodQuestion() {
        return foodQuestion;
    }

    public void setFoodQuestion(String foodQuestion) {
        this.foodQuestion = foodQuestion;
    }

    public String getNutritionalRequirements() {
        return nutritionalRequirements;
    }

    public void setNutritionalRequirements(String nutritionalRequirements) {
        this.nutritionalRequirements = nutritionalRequirements;
    }

    public String getAllergic() {
        return allergic;
    }

    public void setAllergic(String allergic) {
        this.allergic = allergic;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
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

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

}
