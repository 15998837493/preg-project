/*
 * InBodyInfoPojo.java    1.0  2018年8月13日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * [人体成分信息]
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月13日 mlq 初版
 */
public class InBodyInfoPojo {

    /** 检测时间 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date examDate;

    /** 现体重 */
    @QueryFieldAnnotation
    private BigDecimal wt;

    /** 体脂肪 */
    @QueryFieldAnnotation
    private BigDecimal bfm;

    /** 体脂百分比 */
    @QueryFieldAnnotation
    private BigDecimal pbf;

    /** 骨骼肌 */
    @QueryFieldAnnotation
    private BigDecimal smm;

    /** 蛋白质 */
    @QueryFieldAnnotation
    private BigDecimal protein;

    /** 无机盐 */
    @QueryFieldAnnotation
    private BigDecimal mineral;

    /** 细胞内水分 */
    @QueryFieldAnnotation
    private BigDecimal icw;

    /** 细胞外水分 */
    @QueryFieldAnnotation
    private BigDecimal ecw;

    /** 细胞外水分比率 */
    @QueryFieldAnnotation
    private BigDecimal wed;

    /** 相位角 */
    @QueryFieldAnnotation
    private BigDecimal wbpa50;

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public BigDecimal getWt() {
        return wt;
    }

    public void setWt(BigDecimal wt) {
        this.wt = wt;
    }

    public BigDecimal getBfm() {
        return bfm;
    }

    public void setBfm(BigDecimal bfm) {
        this.bfm = bfm;
    }

    public BigDecimal getPbf() {
        return pbf;
    }

    public void setPbf(BigDecimal pbf) {
        this.pbf = pbf;
    }

    public BigDecimal getSmm() {
        return smm;
    }

    public void setSmm(BigDecimal smm) {
        this.smm = smm;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getMineral() {
        return mineral;
    }

    public void setMineral(BigDecimal mineral) {
        this.mineral = mineral;
    }

    public BigDecimal getIcw() {
        return icw;
    }

    public void setIcw(BigDecimal icw) {
        this.icw = icw;
    }

    public BigDecimal getEcw() {
        return ecw;
    }

    public void setEcw(BigDecimal ecw) {
        this.ecw = ecw;
    }

    public BigDecimal getWed() {
        return wed;
    }

    public void setWed(BigDecimal wed) {
        this.wed = wed;
    }

    public BigDecimal getWbpa50() {
        return wbpa50;
    }

    public void setWbpa50(BigDecimal wbpa50) {
        this.wbpa50 = wbpa50;
    }

}
