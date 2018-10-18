
package com.mnt.preg.platform.form;

import java.math.BigDecimal;

/**
 * 膳食方案保存信息表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-12-3 zcq 初版
 */
public class IntervenePlanGroupForm {

    /** 方案主键 */
    private String planId;

    /** 摄入量主键 */
    private String intakeId;

    /** 摄入量名称 */
    private String intakeName;

    /** 热量范围 */
    private String intakeCaloric;

    /** 碳水化合物 */
    private String intakeCbr;

    /** 碳水化合百分比 */
    private String intakeCbrPercent;

    /** 蛋白质 */
    private String intakeProtein;

    /** 蛋白质百分比 */
    private String intakeProteinPercent;

    /** 脂肪 */
    private String intakeFat;

    /** 脂肪百分比 */
    private String intakeFatPercent;

    /** 食谱模板 */
    private String dietId;

    /** 食谱模板天数 */
    private String foodDays = "";

    /** 食谱模板名称 */
    private String dietName;

    /** 食物推荐 */
    private String foodRecommend;

    /** 实际热量 */
    private BigDecimal intakeActualEnergy;

    /** 干预疗程 */
    private Integer planWeeks;

    /** 干预备注 */
    private String planContent;

    /** 服务营养师 */
    private String userId;

    /** 服务营养师名称 */
    private String userName;

    /** 干预状态 */
    private String status;

    /** 接诊号 */
    private String diagnosisId;

    /** 膳食清单明细 */
    private String detailList;

    /** 膳食处方备注 */
    private String diagnosisDietRemark;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getIntakeCaloric() {
        return intakeCaloric;
    }

    public void setIntakeCaloric(String intakeCaloric) {
        this.intakeCaloric = intakeCaloric;
    }

    public String getIntakeCbr() {
        return intakeCbr;
    }

    public void setIntakeCbr(String intakeCbr) {
        this.intakeCbr = intakeCbr;
    }

    public String getIntakeCbrPercent() {
        return intakeCbrPercent;
    }

    public void setIntakeCbrPercent(String intakeCbrPercent) {
        this.intakeCbrPercent = intakeCbrPercent;
    }

    public String getIntakeProtein() {
        return intakeProtein;
    }

    public void setIntakeProtein(String intakeProtein) {
        this.intakeProtein = intakeProtein;
    }

    public String getIntakeProteinPercent() {
        return intakeProteinPercent;
    }

    public void setIntakeProteinPercent(String intakeProteinPercent) {
        this.intakeProteinPercent = intakeProteinPercent;
    }

    public String getIntakeFat() {
        return intakeFat;
    }

    public void setIntakeFat(String intakeFat) {
        this.intakeFat = intakeFat;
    }

    public String getIntakeFatPercent() {
        return intakeFatPercent;
    }

    public void setIntakeFatPercent(String intakeFatPercent) {
        this.intakeFatPercent = intakeFatPercent;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public String getFoodDays() {
        return foodDays;
    }

    public void setFoodDays(String foodDays) {
        this.foodDays = foodDays;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getFoodRecommend() {
        return foodRecommend;
    }

    public void setFoodRecommend(String foodRecommend) {
        this.foodRecommend = foodRecommend;
    }

    public BigDecimal getIntakeActualEnergy() {
        return intakeActualEnergy;
    }

    public void setIntakeActualEnergy(BigDecimal intakeActualEnergy) {
        this.intakeActualEnergy = intakeActualEnergy;
    }

    public Integer getPlanWeeks() {
        return planWeeks;
    }

    public void setPlanWeeks(Integer planWeeks) {
        this.planWeeks = planWeeks;
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDetailList() {
        return detailList;
    }

    public void setDetailList(String detailList) {
        this.detailList = detailList;
    }

    public String getDiagnosisDietRemark() {
        return diagnosisDietRemark;
    }

    public void setDiagnosisDietRemark(String diagnosisDietRemark) {
        this.diagnosisDietRemark = diagnosisDietRemark;
    }

}
