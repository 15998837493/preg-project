
package com.mnt.preg.examitem.entity;

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
 * 人体成分分析主表BAC
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-7-18 zcq 初版
 */
@Entity
@Table(name = "cus_inbody_bca")
public class PregInbodyBca extends MappedEntity {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 编号 */
    private String bcaId;

    /** 检测数据编号 */
    private String datetimes;

    /** 编号 */
    private String custId;

    /** 报告路径 */
    @UpdateAnnotation
    private String inbodyPdfPath;

    /** 体重 */
    @UpdateAnnotation
    private BigDecimal wt;

    /** 孕周 */
    @UpdateAnnotation
    private String gestationalWeeks;

    /** 检测日期 */
    @UpdateAnnotation
    private Date userExamDate;

    /** 年龄 */
    @UpdateAnnotation
    private Integer age;

    /** 蛋白质 */
    @UpdateAnnotation
    private BigDecimal protein;

    /** 细胞内水分 */
    @UpdateAnnotation
    private BigDecimal icw;

    /** 细胞内水分 */
    @UpdateAnnotation
    private BigDecimal ecw;

    /** 细胞外水分比率 */
    @UpdateAnnotation
    private BigDecimal wed;

    /** 体脂肪 */
    @UpdateAnnotation
    private BigDecimal bfm;

    /** 骨骼肌 */
    @UpdateAnnotation
    private BigDecimal smm;

    /** 无机盐 */
    @UpdateAnnotation
    private BigDecimal mineral;

    /** 躯干肌肉（%） */
    @UpdateAnnotation
    private BigDecimal pilt;

    /** 右下肢肌肉（%） */
    @UpdateAnnotation
    private BigDecimal pilrl;

    /** 左下肢肌肉（%） */
    @UpdateAnnotation
    private BigDecimal pilll;

    /** 蛋白质下限 */
    @UpdateAnnotation
    private BigDecimal proteinMin;

    /** 蛋白质上限 */
    @UpdateAnnotation
    private BigDecimal proteinMax;

    /** 体脂肪下限 */
    @UpdateAnnotation
    private BigDecimal bfmMin;

    /** 体脂肪上限 */
    @UpdateAnnotation
    private BigDecimal bfmMax;

    /** 骨骼肌百分比 */
    @UpdateAnnotation
    private BigDecimal psmm;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "bca_id")
    public String getBcaId() {
        return bcaId;
    }

    public void setBcaId(String bcaId) {
        this.bcaId = bcaId;
    }

    @Column(name = "datetimes")
    public String getDatetimes() {
        return datetimes;
    }

    public void setDatetimes(String datetimes) {
        this.datetimes = datetimes;
    }

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "inbody_pdf_path")
    public String getInbodyPdfPath() {
        return inbodyPdfPath;
    }

    public void setInbodyPdfPath(String inbodyPdfPath) {
        this.inbodyPdfPath = inbodyPdfPath;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_exam_date")
    public Date getUserExamDate() {
        return userExamDate;
    }

    public void setUserExamDate(Date userExamDate) {
        this.userExamDate = userExamDate;
    }

    @Column(name = "smm")
    public BigDecimal getSmm() {
        return smm;
    }

    public void setSmm(BigDecimal smm) {
        this.smm = smm;
    }

    @Column(name = "bfm")
    public BigDecimal getBfm() {
        return bfm;
    }

    public void setBfm(BigDecimal bfm) {
        this.bfm = bfm;
    }

    @Column(name = "wt")
    public BigDecimal getWt() {
        return wt;
    }

    public void setWt(BigDecimal wt) {
        this.wt = wt;
    }

    @Column(name = "gestational_weeks")
    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    @Column(name = "protein")
    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    @Column(name = "icw")
    public BigDecimal getIcw() {
        return icw;
    }

    public void setIcw(BigDecimal icw) {
        this.icw = icw;
    }

    @Column(name = "wed")
    public BigDecimal getWed() {
        return wed;
    }

    public void setWed(BigDecimal wed) {
        this.wed = wed;
    }

    @Column(name = "mineral")
    public BigDecimal getMineral() {
        return mineral;
    }

    public void setMineral(BigDecimal mineral) {
        this.mineral = mineral;
    }

    @Column(name = "ecw")
    public BigDecimal getEcw() {
        return ecw;
    }

    public void setEcw(BigDecimal ecw) {
        this.ecw = ecw;
    }

    @Column(name = "pilt")
    public BigDecimal getPilt() {
        return pilt;
    }

    public void setPilt(BigDecimal pilt) {
        this.pilt = pilt;
    }

    @Column(name = "pilrl")
    public BigDecimal getPilrl() {
        return pilrl;
    }

    public void setPilrl(BigDecimal pilrl) {
        this.pilrl = pilrl;
    }

    @Column(name = "pilll")
    public BigDecimal getPilll() {
        return pilll;
    }

    public void setPilll(BigDecimal pilll) {
        this.pilll = pilll;
    }

    @Column(name = "protein_min")
    public BigDecimal getProteinMin() {
        return proteinMin;
    }

    public void setProteinMin(BigDecimal proteinMin) {
        this.proteinMin = proteinMin;
    }

    @Column(name = "protein_max")
    public BigDecimal getProteinMax() {
        return proteinMax;
    }

    public void setProteinMax(BigDecimal proteinMax) {
        this.proteinMax = proteinMax;
    }

    @Column(name = "bfm_min")
    public BigDecimal getBfmMin() {
        return bfmMin;
    }

    public void setBfmMin(BigDecimal bfmMin) {
        this.bfmMin = bfmMin;
    }

    @Column(name = "bfm_max")
    public BigDecimal getBfmMax() {
        return bfmMax;
    }

    public void setBfmMax(BigDecimal bfmMax) {
        this.bfmMax = bfmMax;
    }

    @Column(name = "psmm")
    public BigDecimal getPsmm() {
        return psmm;
    }

    public void setPsmm(BigDecimal psmm) {
        this.psmm = psmm;
    }

}
