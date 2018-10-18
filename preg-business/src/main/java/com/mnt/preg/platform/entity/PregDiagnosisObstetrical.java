
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
import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 产科
 * 
 * @author dhs
 * @version 1.6
 * 
 *          变更履历：
 *          v1.6 2018-6-15 dhs 初版
 */
@Entity
@Table(name = "user_diagnosis_obstetrical")
public class PregDiagnosisObstetrical extends MappedEntity {

    private static final long serialVersionUID = 5036309724518772206L;

    /** 产科ID */
    private String obstetricalId;

    /** 宫高 */
    @UpdateAnnotation
    private BigDecimal obstetricalFundalHeight;

    /** 腹围 */
    @UpdateAnnotation
    private BigDecimal obstetricalAbdominalPerimeter;

    /** 胎儿状态 */
    @UpdateAnnotation
    private String obstetricalBaby;

    /** 选择日期（计算孕周） */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date obstetricalTopDate;

    /** 孕周 */
    @UpdateAnnotation
    private String obstetricalTopGestationalweeks;

    /** 宫高推荐值 */
    @UpdateAnnotation
    private String obstetricalFundalHeightResult;

    /** 腹围推荐值 */
    @UpdateAnnotation
    private String obstetricalAbdominalPerimeterResult;

    /** 选择日期（计算孕周） */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date obstetricalBottomDate;

    /** 孕周 */
    @UpdateAnnotation
    private String obstetricalBottomGestationalweeks;

    /** 胎儿体重10百分位 */
    @UpdateAnnotation
    private BigDecimal obstetricalBabyWeight;

    /** 胎儿股骨长 */
    @UpdateAnnotation
    private BigDecimal obstetricalBabyFemur;

    /** 胎儿双顶径 */
    @UpdateAnnotation
    private BigDecimal obstetricalBabyBdp;

    /** 胎儿腹围 */
    @UpdateAnnotation
    private BigDecimal obstetricalBabyAbdominalPerimeter;

    /** 胎儿体重10百分位推荐值 */
    @UpdateAnnotation
    private String obstetricalBabyWeightResult;

    /** 胎儿股骨长推荐值 */
    @UpdateAnnotation
    private String obstetricalBabyFemurResult;

    /** 胎儿双顶径推荐值 */
    @UpdateAnnotation
    private String obstetricalBabyBdpResult;

    /** 胎儿腹围推荐值 */
    @UpdateAnnotation
    private String obstetricalBabyAbdominalPerimeterResult;

    /** 羊水一象限 */
    @UpdateAnnotation
    private BigDecimal obstetricalAmnioticFluidOne;

    /** 羊水二象限 */
    @UpdateAnnotation
    private BigDecimal obstetricalAmnioticFluidTwo;

    /** 羊水三象限 */
    @UpdateAnnotation
    private BigDecimal obstetricalAmnioticFluidThree;

    /** 羊水四象限 */
    @UpdateAnnotation
    private BigDecimal obstetricalAmnioticFluidFour;

    /** 接诊ID */
    @UpdateAnnotation
    private String diagnosisId;

    /** 主诉 */
    @UpdateAnnotation
    private String obstetricalDiagnosisMain;

    /** 收缩压 */
    @UpdateAnnotation
    private BigDecimal obstetricalDiagnosisSystolic;

    /** 舒张压 */
    @UpdateAnnotation
    private BigDecimal obstetricalDiagnosisDiastolic;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "obstetrical_id")
    public String getObstetricalId() {
        return obstetricalId;
    }

    public void setObstetricalId(String obstetricalId) {
        this.obstetricalId = obstetricalId;
    }

    @Column(name = "obstetrical_fundal_height")
    public BigDecimal getObstetricalFundalHeight() {
        return obstetricalFundalHeight;
    }

    public void setObstetricalFundalHeight(BigDecimal obstetricalFundalHeight) {
        this.obstetricalFundalHeight = obstetricalFundalHeight;
    }

    @Column(name = "obstetrical_abdominal_perimeter")
    public BigDecimal getObstetricalAbdominalPerimeter() {
        return obstetricalAbdominalPerimeter;
    }

    public void setObstetricalAbdominalPerimeter(BigDecimal obstetricalAbdominalPerimeter) {
        this.obstetricalAbdominalPerimeter = obstetricalAbdominalPerimeter;
    }

    @Column(name = "obstetrical_baby")
    public String getObstetricalBaby() {
        return obstetricalBaby;
    }

    public void setObstetricalBaby(String obstetricalBaby) {
        this.obstetricalBaby = obstetricalBaby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "obstetrical_top_date")
    public Date getObstetricalTopDate() {
        return obstetricalTopDate;
    }

    public void setObstetricalTopDate(Date obstetricalTopDate) {
        this.obstetricalTopDate = obstetricalTopDate;
    }

    @Column(name = "obstetrical_top_gestationalweeks")
    public String getObstetricalTopGestationalweeks() {
        return obstetricalTopGestationalweeks;
    }

    public void setObstetricalTopGestationalweeks(String obstetricalTopGestationalweeks) {
        this.obstetricalTopGestationalweeks = obstetricalTopGestationalweeks;
    }

    @Column(name = "obstetrical_fundal_height_result")
    public String getObstetricalFundalHeightResult() {
        return obstetricalFundalHeightResult;
    }

    public void setObstetricalFundalHeightResult(String obstetricalFundalHeightResult) {
        this.obstetricalFundalHeightResult = obstetricalFundalHeightResult;
    }

    @Column(name = "obstetrical_abdominal_perimeter_result")
    public String getObstetricalAbdominalPerimeterResult() {
        return obstetricalAbdominalPerimeterResult;
    }

    public void setObstetricalAbdominalPerimeterResult(String obstetricalAbdominalPerimeterResult) {
        this.obstetricalAbdominalPerimeterResult = obstetricalAbdominalPerimeterResult;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "obstetrical_bottom_date")
    public Date getObstetricalBottomDate() {
        return obstetricalBottomDate;
    }

    public void setObstetricalBottomDate(Date obstetricalBottomDate) {
        this.obstetricalBottomDate = obstetricalBottomDate;
    }

    @Column(name = "obstetrical_bottom_gestationalweeks")
    public String getObstetricalBottomGestationalweeks() {
        return obstetricalBottomGestationalweeks;
    }

    public void setObstetricalBottomGestationalweeks(String obstetricalBottomGestationalweeks) {
        this.obstetricalBottomGestationalweeks = obstetricalBottomGestationalweeks;
    }

    @Column(name = "obstetrical_baby_weight")
    public BigDecimal getObstetricalBabyWeight() {
        return obstetricalBabyWeight;
    }

    public void setObstetricalBabyWeight(BigDecimal obstetricalBabyWeight) {
        this.obstetricalBabyWeight = obstetricalBabyWeight;
    }

    @Column(name = "obstetrical_baby_femur")
    public BigDecimal getObstetricalBabyFemur() {
        return obstetricalBabyFemur;
    }

    public void setObstetricalBabyFemur(BigDecimal obstetricalBabyFemur) {
        this.obstetricalBabyFemur = obstetricalBabyFemur;
    }

    @Column(name = "obstetrical_baby_Bdp")
    public BigDecimal getObstetricalBabyBdp() {
        return obstetricalBabyBdp;
    }

    public void setObstetricalBabyBdp(BigDecimal obstetricalBabyBdp) {
        this.obstetricalBabyBdp = obstetricalBabyBdp;
    }

    @Column(name = "obstetrical_baby_abdominal_perimeter")
    public BigDecimal getObstetricalBabyAbdominalPerimeter() {
        return obstetricalBabyAbdominalPerimeter;
    }

    public void setObstetricalBabyAbdominalPerimeter(BigDecimal obstetricalBabyAbdominalPerimeter) {
        this.obstetricalBabyAbdominalPerimeter = obstetricalBabyAbdominalPerimeter;
    }

    @Column(name = "obstetrical_baby_weight_result")
    public String getObstetricalBabyWeightResult() {
        return obstetricalBabyWeightResult;
    }

    public void setObstetricalBabyWeightResult(String obstetricalBabyWeightResult) {
        this.obstetricalBabyWeightResult = obstetricalBabyWeightResult;
    }

    @Column(name = "obstetrical_baby_femur_result")
    public String getObstetricalBabyFemurResult() {
        return obstetricalBabyFemurResult;
    }

    public void setObstetricalBabyFemurResult(String obstetricalBabyFemurResult) {
        this.obstetricalBabyFemurResult = obstetricalBabyFemurResult;
    }

    @Column(name = "obstetrical_baby_Bdp_result")
    public String getObstetricalBabyBdpResult() {
        return obstetricalBabyBdpResult;
    }

    public void setObstetricalBabyBdpResult(String obstetricalBabyBdpResult) {
        this.obstetricalBabyBdpResult = obstetricalBabyBdpResult;
    }

    @Column(name = "obstetrical_baby_abdominal_perimeter_result")
    public String getObstetricalBabyAbdominalPerimeterResult() {
        return obstetricalBabyAbdominalPerimeterResult;
    }

    public void setObstetricalBabyAbdominalPerimeterResult(String obstetricalBabyAbdominalPerimeterResult) {
        this.obstetricalBabyAbdominalPerimeterResult = obstetricalBabyAbdominalPerimeterResult;
    }

    @Column(name = "obstetrical_amniotic_fluid_one")
    public BigDecimal getObstetricalAmnioticFluidOne() {
        return obstetricalAmnioticFluidOne;
    }

    public void setObstetricalAmnioticFluidOne(BigDecimal obstetricalAmnioticFluidOne) {
        this.obstetricalAmnioticFluidOne = obstetricalAmnioticFluidOne;
    }

    @Column(name = "obstetrical_amniotic_fluid_two")
    public BigDecimal getObstetricalAmnioticFluidTwo() {
        return obstetricalAmnioticFluidTwo;
    }

    public void setObstetricalAmnioticFluidTwo(BigDecimal obstetricalAmnioticFluidTwo) {
        this.obstetricalAmnioticFluidTwo = obstetricalAmnioticFluidTwo;
    }

    @Column(name = "obstetrical_amniotic_fluid_three")
    public BigDecimal getObstetricalAmnioticFluidThree() {
        return obstetricalAmnioticFluidThree;
    }

    public void setObstetricalAmnioticFluidThree(BigDecimal obstetricalAmnioticFluidThree) {
        this.obstetricalAmnioticFluidThree = obstetricalAmnioticFluidThree;
    }

    @Column(name = "obstetrical_amniotic_fluid_four")
    public BigDecimal getObstetricalAmnioticFluidFour() {
        return obstetricalAmnioticFluidFour;
    }

    public void setObstetricalAmnioticFluidFour(BigDecimal obstetricalAmnioticFluidFour) {
        this.obstetricalAmnioticFluidFour = obstetricalAmnioticFluidFour;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "obstetrical_diagnosis_systolic")
    public BigDecimal getObstetricalDiagnosisSystolic() {
        return obstetricalDiagnosisSystolic;
    }

    public void setObstetricalDiagnosisSystolic(BigDecimal obstetricalDiagnosisSystolic) {
        this.obstetricalDiagnosisSystolic = obstetricalDiagnosisSystolic;
    }

    @Column(name = "obstetrical_diagnosis_diastolic")
    public BigDecimal getObstetricalDiagnosisDiastolic() {
        return obstetricalDiagnosisDiastolic;
    }

    public void setObstetricalDiagnosisDiastolic(BigDecimal obstetricalDiagnosisDiastolic) {
        this.obstetricalDiagnosisDiastolic = obstetricalDiagnosisDiastolic;
    }

    @Column(name = "obstetrical_diagnosis_main")
    public String getObstetricalDiagnosisMain() {
        return obstetricalDiagnosisMain;
    }

    public void setObstetricalDiagnosisMain(String obstetricalDiagnosisMain) {
        this.obstetricalDiagnosisMain = obstetricalDiagnosisMain;
    }

}
