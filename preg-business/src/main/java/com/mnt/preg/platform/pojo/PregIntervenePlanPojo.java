
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 会员干预方案表信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-29 zcq 初版
 */
public class PregIntervenePlanPojo implements Serializable {

    private static final long serialVersionUID = 1329300710124428725L;

    /** 方案主键 */
    @QueryFieldAnnotation
    private String planId;

    /** 摄入量主键 */
    @QueryFieldAnnotation
    private String intakeId;

    /** 摄入量名称 */
    @QueryFieldAnnotation
    private String intakeName;

    /** 热量上限 */
    @QueryFieldAnnotation
    private String intakeCaloric;

    /** 碳水化合物 */
    @QueryFieldAnnotation
    private String intakeCbr;

    /** 碳水化合百分比 */
    @QueryFieldAnnotation
    private String intakeCbrPercent;

    /** 蛋白质 */
    @QueryFieldAnnotation
    private String intakeProtein;

    /** 蛋白质百分比 */
    @QueryFieldAnnotation
    private String intakeProteinPercent;

    /** 脂肪 */
    @QueryFieldAnnotation
    private String intakeFat;

    /** 脂肪百分比 */
    @QueryFieldAnnotation
    private String intakeFatPercent;

    /** 食谱模板 */
    @QueryFieldAnnotation
    private String dietId;

    /** 食谱模板名称 */
    @QueryFieldAnnotation
    private String dietName;

    /** 食谱天数 */
    @QueryFieldAnnotation
    private String foodDays;

    /** 食物推荐 */
    @QueryFieldAnnotation
    private String foodRecommend;

    /** 实际热量 */
    @QueryFieldAnnotation
    private BigDecimal intakeActualEnergy;

    /** 干预疗程 */
    @QueryFieldAnnotation
    private Integer planWeeks;

    /** 干预备注 */
    @QueryFieldAnnotation
    private String planContent;

    /** 服务营养师 */
    @QueryFieldAnnotation
    private String userId;

    /** 服务营养师名称 */
    @QueryFieldAnnotation
    private String userName;

    /** 方案创建日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    /** 干预状态 */
    @QueryFieldAnnotation
    private String status;

    /** 接诊号 */
    @QueryFieldAnnotation
    private String diagnosisId;

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

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getFoodDays() {
        return foodDays;
    }

    public void setFoodDays(String foodDays) {
        this.foodDays = foodDays;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

}
