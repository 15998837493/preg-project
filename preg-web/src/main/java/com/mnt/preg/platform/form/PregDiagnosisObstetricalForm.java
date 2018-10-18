
package com.mnt.preg.platform.form;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 产科信息
 * 
 * @author dhs
 * @version 1.6
 * 
 *          变更履历：
 *          v1.6 2018-6-20 dhs 初版
 */
public class PregDiagnosisObstetricalForm {

    /** 产科ID */
    private String obstetricalId;

    /** 宫高 */
    private BigDecimal obstetricalFundalHeight;

    /** 腹围 */
    private BigDecimal obstetricalAbdominalPerimeter;

    /** 胎儿状态 */
    private String obstetricalBaby;

    /** 选择日期（计算孕周） */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date obstetricalTopDate;

    /** 孕周 */
    private String obstetricalTopGestationalweeks;

    /** 宫高推荐值 */
    private String obstetricalFundalHeightResult;

    /** 腹围推荐值 */
    private String obstetricalAbdominalPerimeterResult;

    /** 选择日期（计算孕周） */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date obstetricalBottomDate;

    /** 孕周 */
    private String obstetricalBottomGestationalweeks;

    /** 胎儿体重10百分位 */
    private BigDecimal obstetricalBabyWeight;

    /** 胎儿股骨长 */
    private BigDecimal obstetricalBabyFemur;

    /** 胎儿双顶径 */
    private BigDecimal obstetricalBabyBdp;

    /** 胎儿腹围 */
    private BigDecimal obstetricalBabyAbdominalPerimeter;

    /** 胎儿体重10百分位推荐值 */
    private String obstetricalBabyWeightResult;

    /** 胎儿股骨长推荐值 */
    private String obstetricalBabyFemurResult;

    /** 胎儿双顶径推荐值 */
    private String obstetricalBabyBdpResult;

    /** 胎儿腹围推荐值 */
    private String obstetricalBabyAbdominalPerimeterResult;

    /** 羊水一象限 */
    private BigDecimal obstetricalAmnioticFluidOne;

    /** 羊水二象限 */
    private BigDecimal obstetricalAmnioticFluidTwo;

    /** 羊水三象限 */
    private BigDecimal obstetricalAmnioticFluidThree;

    /** 羊水四象限 */
    private BigDecimal obstetricalAmnioticFluidFour;

    /** 接诊ID */
    private String diagnosisId;

    /** 主诉 */
    private String obstetricalDiagnosisMain;

    /** 现体重 */
    private BigDecimal obstetricalDiagnosisWeight;

    /** 收缩压 */
    private BigDecimal obstetricalDiagnosisSystolic;

    /** 舒张压 */
    private BigDecimal obstetricalDiagnosisDiastolic;

    /** 妊娠风险级别 */
    private String obstetricalGestationLevel;

    public String getObstetricalId() {
        return obstetricalId;
    }

    public void setObstetricalId(String obstetricalId) {
        this.obstetricalId = obstetricalId;
    }

    public BigDecimal getObstetricalFundalHeight() {
        return obstetricalFundalHeight;
    }

    public void setObstetricalFundalHeight(BigDecimal obstetricalFundalHeight) {
        this.obstetricalFundalHeight = obstetricalFundalHeight;
    }

    public BigDecimal getObstetricalAbdominalPerimeter() {
        return obstetricalAbdominalPerimeter;
    }

    public void setObstetricalAbdominalPerimeter(BigDecimal obstetricalAbdominalPerimeter) {
        this.obstetricalAbdominalPerimeter = obstetricalAbdominalPerimeter;
    }

    public String getObstetricalBaby() {
        return obstetricalBaby;
    }

    public void setObstetricalBaby(String obstetricalBaby) {
        this.obstetricalBaby = obstetricalBaby;
    }

    public Date getObstetricalTopDate() {
        return obstetricalTopDate;
    }

    public void setObstetricalTopDate(Date obstetricalTopDate) {
        this.obstetricalTopDate = obstetricalTopDate;
    }

    public String getObstetricalTopGestationalweeks() {
        return obstetricalTopGestationalweeks;
    }

    public void setObstetricalTopGestationalweeks(String obstetricalTopGestationalweeks) {
        this.obstetricalTopGestationalweeks = obstetricalTopGestationalweeks;
    }

    public String getObstetricalFundalHeightResult() {
        return obstetricalFundalHeightResult;
    }

    public void setObstetricalFundalHeightResult(String obstetricalFundalHeightResult) {
        this.obstetricalFundalHeightResult = obstetricalFundalHeightResult;
    }

    public String getObstetricalAbdominalPerimeterResult() {
        return obstetricalAbdominalPerimeterResult;
    }

    public void setObstetricalAbdominalPerimeterResult(String obstetricalAbdominalPerimeterResult) {
        this.obstetricalAbdominalPerimeterResult = obstetricalAbdominalPerimeterResult;
    }

    public Date getObstetricalBottomDate() {
        return obstetricalBottomDate;
    }

    public void setObstetricalBottomDate(Date obstetricalBottomDate) {
        this.obstetricalBottomDate = obstetricalBottomDate;
    }

    public String getObstetricalBottomGestationalweeks() {
        return obstetricalBottomGestationalweeks;
    }

    public void setObstetricalBottomGestationalweeks(String obstetricalBottomGestationalweeks) {
        this.obstetricalBottomGestationalweeks = obstetricalBottomGestationalweeks;
    }

    public BigDecimal getObstetricalBabyWeight() {
        return obstetricalBabyWeight;
    }

    public void setObstetricalBabyWeight(BigDecimal obstetricalBabyWeight) {
        this.obstetricalBabyWeight = obstetricalBabyWeight;
    }

    public BigDecimal getObstetricalBabyFemur() {
        return obstetricalBabyFemur;
    }

    public void setObstetricalBabyFemur(BigDecimal obstetricalBabyFemur) {
        this.obstetricalBabyFemur = obstetricalBabyFemur;
    }

    public BigDecimal getObstetricalBabyBdp() {
        return obstetricalBabyBdp;
    }

    public void setObstetricalBabyBdp(BigDecimal obstetricalBabyBdp) {
        this.obstetricalBabyBdp = obstetricalBabyBdp;
    }

    public BigDecimal getObstetricalBabyAbdominalPerimeter() {
        return obstetricalBabyAbdominalPerimeter;
    }

    public void setObstetricalBabyAbdominalPerimeter(BigDecimal obstetricalBabyAbdominalPerimeter) {
        this.obstetricalBabyAbdominalPerimeter = obstetricalBabyAbdominalPerimeter;
    }

    public String getObstetricalBabyWeightResult() {
        return obstetricalBabyWeightResult;
    }

    public void setObstetricalBabyWeightResult(String obstetricalBabyWeightResult) {
        this.obstetricalBabyWeightResult = obstetricalBabyWeightResult;
    }

    public String getObstetricalBabyFemurResult() {
        return obstetricalBabyFemurResult;
    }

    public void setObstetricalBabyFemurResult(String obstetricalBabyFemurResult) {
        this.obstetricalBabyFemurResult = obstetricalBabyFemurResult;
    }

    public String getObstetricalBabyBdpResult() {
        return obstetricalBabyBdpResult;
    }

    public void setObstetricalBabyBdpResult(String obstetricalBabyBdpResult) {
        this.obstetricalBabyBdpResult = obstetricalBabyBdpResult;
    }

    public String getObstetricalBabyAbdominalPerimeterResult() {
        return obstetricalBabyAbdominalPerimeterResult;
    }

    public void setObstetricalBabyAbdominalPerimeterResult(String obstetricalBabyAbdominalPerimeterResult) {
        this.obstetricalBabyAbdominalPerimeterResult = obstetricalBabyAbdominalPerimeterResult;
    }

    public BigDecimal getObstetricalAmnioticFluidOne() {
        return obstetricalAmnioticFluidOne;
    }

    public void setObstetricalAmnioticFluidOne(BigDecimal obstetricalAmnioticFluidOne) {
        this.obstetricalAmnioticFluidOne = obstetricalAmnioticFluidOne;
    }

    public BigDecimal getObstetricalAmnioticFluidTwo() {
        return obstetricalAmnioticFluidTwo;
    }

    public void setObstetricalAmnioticFluidTwo(BigDecimal obstetricalAmnioticFluidTwo) {
        this.obstetricalAmnioticFluidTwo = obstetricalAmnioticFluidTwo;
    }

    public BigDecimal getObstetricalAmnioticFluidThree() {
        return obstetricalAmnioticFluidThree;
    }

    public void setObstetricalAmnioticFluidThree(BigDecimal obstetricalAmnioticFluidThree) {
        this.obstetricalAmnioticFluidThree = obstetricalAmnioticFluidThree;
    }

    public BigDecimal getObstetricalAmnioticFluidFour() {
        return obstetricalAmnioticFluidFour;
    }

    public void setObstetricalAmnioticFluidFour(BigDecimal obstetricalAmnioticFluidFour) {
        this.obstetricalAmnioticFluidFour = obstetricalAmnioticFluidFour;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getObstetricalDiagnosisMain() {
        return obstetricalDiagnosisMain;
    }

    public void setObstetricalDiagnosisMain(String obstetricalDiagnosisMain) {
        this.obstetricalDiagnosisMain = obstetricalDiagnosisMain;
    }

    public BigDecimal getObstetricalDiagnosisWeight() {
        return obstetricalDiagnosisWeight;
    }

    public void setObstetricalDiagnosisWeight(BigDecimal obstetricalDiagnosisWeight) {
        this.obstetricalDiagnosisWeight = obstetricalDiagnosisWeight;
    }

    public BigDecimal getObstetricalDiagnosisSystolic() {
        return obstetricalDiagnosisSystolic;
    }

    public void setObstetricalDiagnosisSystolic(BigDecimal obstetricalDiagnosisSystolic) {
        this.obstetricalDiagnosisSystolic = obstetricalDiagnosisSystolic;
    }

    public BigDecimal getObstetricalDiagnosisDiastolic() {
        return obstetricalDiagnosisDiastolic;
    }

    public void setObstetricalDiagnosisDiastolic(BigDecimal obstetricalDiagnosisDiastolic) {
        this.obstetricalDiagnosisDiastolic = obstetricalDiagnosisDiastolic;
    }

    public String getObstetricalGestationLevel() {
        return obstetricalGestationLevel;
    }

    public void setObstetricalGestationLevel(String obstetricalGestationLevel) {
        this.obstetricalGestationLevel = obstetricalGestationLevel;
    }
}
