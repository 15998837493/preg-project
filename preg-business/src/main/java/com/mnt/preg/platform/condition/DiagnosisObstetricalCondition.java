
package com.mnt.preg.platform.condition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 产科检索条件
 * 
 * @author dhs
 * @version 1.6
 * 
 *          变更履历：
 *          v1.6 2018-6-15 dhs 初版
 */
public class DiagnosisObstetricalCondition implements Serializable {

    private static final long serialVersionUID = 1387645307708056502L;

    /** 产科ID */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalId;

    /** 宫高 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalFundalHeight;

    /** 腹围 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalAbdominalPerimeter;

    /** 胎儿状态 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalBaby;

    /** 选择日期（计算孕周） */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Date obstetricalTopDate;

    /** 孕周 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalTopGestationalweeks;

    /** 宫高推荐值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalFundalHeightResult;

    /** 腹围推荐值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalAbdominalPerimeterResult;

    /** 选择日期（计算孕周） */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Date obstetricalBottomDate;

    /** 孕周 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalBottomGestationalweeks;

    /** 胎儿体重10百分位 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalBabyWeight;

    /** 胎儿股骨长 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalBabyFemur;

    /** 胎儿双顶径 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalBabyBdp;

    /** 胎儿腹围 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalBabyAbdominalPerimeter;

    /** 胎儿体重10百分位推荐值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalBabyWeightResult;

    /** 胎儿股骨长推荐值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalBabyFemurResult;

    /** 胎儿双顶径推荐值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalBabyBdpResult;

    /** 胎儿腹围推荐值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalBabyAbdominalPerimeterResult;

    /** 羊水一象限 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalAmnioticFluidOne;

    /** 羊水二象限 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalAmnioticFluidTwo;

    /** 羊水三象限 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalAmnioticFluidThree;

    /** 羊水四象限 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalAmnioticFluidFour;

    /** 接诊ID */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 主诉 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String obstetricalDiagnosisMain;

    /** 收缩压 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalDiagnosisSystolic;

    /** 舒张压 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private BigDecimal obstetricalDiagnosisDiastolic;

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

    public BigDecimal getObstetricalBabyBdp() {
        return obstetricalBabyBdp;
    }

    public void setObstetricalBabyBdp(BigDecimal obstetricalBabyBdp) {
        this.obstetricalBabyBdp = obstetricalBabyBdp;
    }

    public String getObstetricalBabyBdpResult() {
        return obstetricalBabyBdpResult;
    }

    public void setObstetricalBabyBdpResult(String obstetricalBabyBdpResult) {
        this.obstetricalBabyBdpResult = obstetricalBabyBdpResult;
    }

}
