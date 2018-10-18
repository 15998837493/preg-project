
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 快速营养调查pdf
 * 
 * @author dhs
 * @version 1.3.3
 * 
 *          变更履历：
 *          v1.3.3 2018-01-09 dhs 初版
 */
public class NutritiousReportPojo implements Serializable {

    private static final long serialVersionUID = -6269692454442625867L;

    /** 食欲食量 */
    private String eats;

    /** 膳食偏好 */
    private String foodPreference;

    /** 膳食摄入 */
    private String foodIntake;

    /** 不良饮食喜好 */
    private String badDietary;

    /** 不良用餐习惯 */
    private String badHabits;

    /** 体能消耗问题 */
    private String staminaRup;

    /** 运动消耗问题 */
    private String sportRup;

    /** 膳食结构风险 */
    private String foodRisk;

    /** 致病饮食风险 */
    private String illRisk;

    /** 营养咨询需求 */
    private String nutritionalRequirements;

    /** 接诊信息 */
    private PregDiagnosisPojo diagnosis;

    /** 患者信息 */
    private CustomerPojo customer;

    /** 建档信息 */
    private PregArchivesPojo pregnancyArchives;

    /** 接诊项目信息 */
    private PregDiagnosisItemsVo diagnosisItem;

    /** 妊娠期膳食摄入问题 */
    private String foodQuestion;

    /** 机构号 */
    private String insId;

    public String getEats() {
        return eats;
    }

    public void setEats(String eats) {
        this.eats = eats;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    public String getFoodIntake() {
        return foodIntake;
    }

    public void setFoodIntake(String foodIntake) {
        this.foodIntake = foodIntake;
    }

    public String getBadDietary() {
        return badDietary;
    }

    public void setBadDietary(String badDietary) {
        this.badDietary = badDietary;
    }

    public String getBadHabits() {
        return badHabits;
    }

    public void setBadHabits(String badHabits) {
        this.badHabits = badHabits;
    }

    public String getStaminaRup() {
        return staminaRup;
    }

    public void setStaminaRup(String staminaRup) {
        this.staminaRup = staminaRup;
    }

    public String getSportRup() {
        return sportRup;
    }

    public void setSportRup(String sportRup) {
        this.sportRup = sportRup;
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

    public String getNutritionalRequirements() {
        return nutritionalRequirements;
    }

    public void setNutritionalRequirements(String nutritionalRequirements) {
        this.nutritionalRequirements = nutritionalRequirements;
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

    public String getFoodQuestion() {
        return foodQuestion;
    }

    public void setFoodQuestion(String foodQuestion) {
        this.foodQuestion = foodQuestion;
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
