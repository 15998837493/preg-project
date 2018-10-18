
package com.mnt.preg.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 会员干预方案表信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-29 zcq 初版
 */
@Entity
@Table(name = "user_plan")
public class PregIntervenePlan extends MappedEntity {

    private static final long serialVersionUID = 1329300710124428725L;

    /** 方案主键 */
    private String planId;

    /** 摄入量主键 */
    @UpdateAnnotation
    private String intakeId;

    /** 摄入量名称 */
    @UpdateAnnotation
    private String intakeName;

    /** 热量范围 */
    @UpdateAnnotation
    private String intakeCaloric;

    /** 碳水化合物 */
    @UpdateAnnotation
    private String intakeCbr;

    /** 碳水化合百分比 */
    @UpdateAnnotation
    private String intakeCbrPercent;

    /** 蛋白质 */
    @UpdateAnnotation
    private String intakeProtein;

    /** 蛋白质百分比 */
    @UpdateAnnotation
    private String intakeProteinPercent;

    /** 脂肪 */
    @UpdateAnnotation
    private String intakeFat;

    /** 脂肪百分比 */
    @UpdateAnnotation
    private String intakeFatPercent;

    /** 食谱模板 */
    @UpdateAnnotation
    private String dietId;

    /** 食谱模板名称 */
    @UpdateAnnotation
    private String dietName;

    /** 食谱天数 */
    @UpdateAnnotation
    private String foodDays;

    /** 食物推荐 */
    @UpdateAnnotation
    private String foodRecommend;

    /** 实际热量 */
    @UpdateAnnotation
    private BigDecimal intakeActualEnergy;

    /** 干预疗程 */
    @UpdateAnnotation
    private Integer planWeeks;

    /** 干预备注 */
    @UpdateAnnotation
    private String planContent;

    /** 服务营养师 */
    @UpdateAnnotation
    private String userId;

    /** 服务营养师名称 */
    @UpdateAnnotation
    private String userName;

    /** 方案创建日期 */
    @UpdateAnnotation
    private Date createDate;

    /** 干预状态 */
    @UpdateAnnotation
    private String status;

    /** 接诊号 */
    private String diagnosisId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "plan_id")
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "plan_weeks")
    public Integer getPlanWeeks() {
        return planWeeks;
    }

    public void setPlanWeeks(Integer planWeeks) {
        this.planWeeks = planWeeks;
    }

    @Column(name = "intake_actual_energy")
    public BigDecimal getIntakeActualEnergy() {
        return intakeActualEnergy;
    }

    public void setIntakeActualEnergy(BigDecimal intakeActualEnergy) {
        this.intakeActualEnergy = intakeActualEnergy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
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

    @Column(name = "plan_content")
    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "intake_cbr")
    public String getIntakeCbr() {
        return intakeCbr;
    }

    public void setIntakeCbr(String intakeCbr) {
        this.intakeCbr = intakeCbr;
    }

    @Column(name = "intake_cbr_percent")
    public String getIntakeCbrPercent() {
        return intakeCbrPercent;
    }

    public void setIntakeCbrPercent(String intakeCbrPercent) {
        this.intakeCbrPercent = intakeCbrPercent;
    }

    @Column(name = "intake_protein")
    public String getIntakeProtein() {
        return intakeProtein;
    }

    public void setIntakeProtein(String intakeProtein) {
        this.intakeProtein = intakeProtein;
    }

    @Column(name = "intake_protein_percent")
    public String getIntakeProteinPercent() {
        return intakeProteinPercent;
    }

    public void setIntakeProteinPercent(String intakeProteinPercent) {
        this.intakeProteinPercent = intakeProteinPercent;
    }

    @Column(name = "intake_fat")
    public String getIntakeFat() {
        return intakeFat;
    }

    public void setIntakeFat(String intakeFat) {
        this.intakeFat = intakeFat;
    }

    @Column(name = "intake_fat_percent")
    public String getIntakeFatPercent() {
        return intakeFatPercent;
    }

    public void setIntakeFatPercent(String intakeFatPercent) {
        this.intakeFatPercent = intakeFatPercent;
    }

    @Column(name = "diet_id")
    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    @Column(name = "diet_name")
    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    @Column(name = "food_days")
    public String getFoodDays() {
        return foodDays;
    }

    public void setFoodDays(String foodDays) {
        this.foodDays = foodDays;
    }

    @Column(name = "food_recommend")
    public String getFoodRecommend() {
        return foodRecommend;
    }

    public void setFoodRecommend(String foodRecommend) {
        this.foodRecommend = foodRecommend;
    }

    @Column(name = "intake_caloric")
    public String getIntakeCaloric() {
        return intakeCaloric;
    }

    public void setIntakeCaloric(String intakeCaloric) {
        this.intakeCaloric = intakeCaloric;
    }

    @Column(name = "intake_id")
    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    @Column(name = "intake_name")
    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

}
