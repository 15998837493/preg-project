
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 人体成分分析信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-7-18 zcq 初版
 */
public class PregInBodyBcaPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 编号 */
    @QueryFieldAnnotation
    private String bcaId;

    /** 检测数据编号 */
    @QueryFieldAnnotation
    private String datetimes;

    /** 编号 */
    @QueryFieldAnnotation
    private String custId;

    /** 报告路径 */
    @QueryFieldAnnotation
    private String inbodyPdfPath;

    /** 体重 */
    @QueryFieldAnnotation
    private BigDecimal wt;

    /** 孕周 */
    @QueryFieldAnnotation
    private String gestationalWeeks;

    /** 检测日期 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date userExamDate;

    /** 年龄 */
    @QueryFieldAnnotation
    private Integer age;

    /** 蛋白质 */
    @QueryFieldAnnotation
    private BigDecimal protein;

    /** 细胞内水分 */
    @QueryFieldAnnotation
    private BigDecimal icw;

    /** 细胞外水分 */
    @QueryFieldAnnotation
    private BigDecimal ecw;

    /** 细胞外水分比率 */
    @QueryFieldAnnotation
    private BigDecimal wed;

    /** 脂肪 */
    @QueryFieldAnnotation
    private BigDecimal bfm;

    /** 骨骼肌 */
    @QueryFieldAnnotation
    private BigDecimal smm;

    /** 无机盐 */
    @QueryFieldAnnotation
    private BigDecimal mineral;

    /** 躯干肌肉（%） */
    @QueryFieldAnnotation
    private BigDecimal pilt;

    /** 右下肢肌肉（%） */
    @QueryFieldAnnotation
    private BigDecimal pilrl;

    /** 左下肢肌肉（%） */
    @QueryFieldAnnotation
    private BigDecimal pilll;

    /** 蛋白质下限 */
    @QueryFieldAnnotation
    private BigDecimal proteinMin;

    /** 蛋白质上限 */
    @QueryFieldAnnotation
    private BigDecimal proteinMax;

    /** 体脂肪下限 */
    @QueryFieldAnnotation
    private BigDecimal bfmMin;

    /** 体脂肪上限 */
    @QueryFieldAnnotation
    private BigDecimal bfmMax;

    /** 骨骼肌百分比 */
    @QueryFieldAnnotation
    private BigDecimal psmm;

    /** 接诊id */
    private String diagnosisId;

    private List<PregInBodyBcaPojo> historyList;

    private PregDiagnosisPojo diagnosis;

    /** 接诊项目信息 */
    private PregDiagnosisItemsVo diagnosisItem;

    private List<PregDiagnosisPojo> diagnosisList;

    /** 机构号 */
    private String insId;

    /** 建档信息 */
    private PregArchivesPojo pregVo;

    /** 客户信息 */
    private CustomerPojo customerVo;

    /** 体检指标 */
    private Map<String, ExamItemPojo> examMap;

    public String getBcaId() {
        return bcaId;
    }

    public void setBcaId(String bcaId) {
        this.bcaId = bcaId;
    }

    public String getDatetimes() {
        return datetimes;
    }

    public void setDatetimes(String datetimes) {
        this.datetimes = datetimes;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getInbodyPdfPath() {
        return inbodyPdfPath;
    }

    public void setInbodyPdfPath(String inbodyPdfPath) {
        this.inbodyPdfPath = inbodyPdfPath;
    }

    public BigDecimal getWt() {
        return wt;
    }

    public void setWt(BigDecimal wt) {
        this.wt = wt;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public Date getUserExamDate() {
        return userExamDate;
    }

    public void setUserExamDate(Date userExamDate) {
        this.userExamDate = userExamDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
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

    public BigDecimal getBfm() {
        return bfm;
    }

    public void setBfm(BigDecimal bfm) {
        this.bfm = bfm;
    }

    public BigDecimal getSmm() {
        return smm;
    }

    public void setSmm(BigDecimal smm) {
        this.smm = smm;
    }

    public BigDecimal getMineral() {
        return mineral;
    }

    public void setMineral(BigDecimal mineral) {
        this.mineral = mineral;
    }

    public List<PregInBodyBcaPojo> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<PregInBodyBcaPojo> historyList) {
        this.historyList = historyList;
    }

    public PregDiagnosisPojo getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(PregDiagnosisPojo diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<PregDiagnosisPojo> getDiagnosisList() {
        return diagnosisList;
    }

    public void setDiagnosisList(List<PregDiagnosisPojo> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    public PregArchivesPojo getPregVo() {
        return pregVo;
    }

    public void setPregVo(PregArchivesPojo pregVo) {
        this.pregVo = pregVo;
    }

    public CustomerPojo getCustomerVo() {
        return customerVo;
    }

    public void setCustomerVo(CustomerPojo customerVo) {
        this.customerVo = customerVo;
    }

    public Map<String, ExamItemPojo> getExamMap() {
        return examMap;
    }

    public void setExamMap(Map<String, ExamItemPojo> examMap) {
        this.examMap = examMap;
    }

    public BigDecimal getPilt() {
        return pilt;
    }

    public void setPilt(BigDecimal pilt) {
        this.pilt = pilt;
    }

    public BigDecimal getPilrl() {
        return pilrl;
    }

    public void setPilrl(BigDecimal pilrl) {
        this.pilrl = pilrl;
    }

    public BigDecimal getPilll() {
        return pilll;
    }

    public void setPilll(BigDecimal pilll) {
        this.pilll = pilll;
    }

    public BigDecimal getProteinMin() {
        return proteinMin;
    }

    public void setProteinMin(BigDecimal proteinMin) {
        this.proteinMin = proteinMin;
    }

    public BigDecimal getProteinMax() {
        return proteinMax;
    }

    public void setProteinMax(BigDecimal proteinMax) {
        this.proteinMax = proteinMax;
    }

    public BigDecimal getBfmMin() {
        return bfmMin;
    }

    public void setBfmMin(BigDecimal bfmMin) {
        this.bfmMin = bfmMin;
    }

    public BigDecimal getBfmMax() {
        return bfmMax;
    }

    public void setBfmMax(BigDecimal bfmMax) {
        this.bfmMax = bfmMax;
    }

    public BigDecimal getPsmm() {
        return psmm;
    }

    public void setPsmm(BigDecimal psmm) {
        this.psmm = psmm;
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

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
