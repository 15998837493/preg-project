/*
 * StatisticaCondition.java    1.0  2018-2-5
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.account.condition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 业务统计scd部分
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-2-5 scd 初版
 */
public class StatisticaListCondition implements Serializable {

    private static final long serialVersionUID = 7387595880781142178L;

    /** 接诊号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 医生单选 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisUser;

    /** 医生多选 */
    @QueryConditionAnnotation(name = "diagnosisUser", symbol = SymbolConstants.IN)
    private List<String> diagnosisUsers;

    /** 接诊时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC)
    private Date diagnosisDate;

    /** 接诊时间 大于等于 */
    @QueryConditionAnnotation(name = "diagnosisDate", symbol = SymbolConstants.GE)
    private String startDate;

    /** 接诊时间 小于 */
    @QueryConditionAnnotation(name = "diagnosisDate", symbol = SymbolConstants.LT)
    private String endDate;

    /** 诊断 */
    private String diseaseId;

    /** 转诊医生 */
    // @QueryConditionAnnotation(name = "diagnosisReferralDoctor", symbol = SymbolConstants.IN)
    private List<String> diagnosisZhuanUser;

    /** 评价项目 */
    // @QueryConditionAnnotation(name = "inspectCode", aliasName = "inspect", symbol = SymbolConstants.IN)
    private List<String> diagnosisMasItems;

    /** 评价项目操作者 */
    // @QueryConditionAnnotation(name = "inspectUser", aliasName = "inspect", symbol = SymbolConstants.IN)
    private List<String> diagnosisMasItemAuthors;

    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<String> getDiagnosisUsers() {
        return diagnosisUsers;
    }

    public void setDiagnosisUsers(List<String> diagnosisUsers) {
        this.diagnosisUsers = diagnosisUsers;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiagnosisUser() {
        return diagnosisUser;
    }

    public void setDiagnosisUser(String diagnosisUser) {
        this.diagnosisUser = diagnosisUser;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public List<String> getDiagnosisZhuanUser() {
        return diagnosisZhuanUser;
    }

    public void setDiagnosisZhuanUser(List<String> diagnosisZhuanUser) {
        this.diagnosisZhuanUser = diagnosisZhuanUser;
    }

    public List<String> getDiagnosisMasItems() {
        return diagnosisMasItems;
    }

    public void setDiagnosisMasItems(List<String> diagnosisMasItems) {
        this.diagnosisMasItems = diagnosisMasItems;
    }

    public List<String> getDiagnosisMasItemAuthors() {
        return diagnosisMasItemAuthors;
    }

    public void setDiagnosisMasItemAuthors(List<String> diagnosisMasItemAuthors) {
        this.diagnosisMasItemAuthors = diagnosisMasItemAuthors;
    }

}
