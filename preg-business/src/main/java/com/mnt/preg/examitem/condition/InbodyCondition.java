
package com.mnt.preg.examitem.condition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 人体成分检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-7-18 zcq 初版
 */
public class InbodyCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 人体成分主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String bcaId;

    /** 客户主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custId;

    /** 检测日期 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userExamDate;

    /** 检测日期 */
    @QueryConditionAnnotation(symbol = SymbolConstants.GE, name = "userExamDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 检测日期 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LT, name = "userExamDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 接诊编号 */
    private String diagnosisId;

    /** 接诊id集合 */
    @QueryConditionAnnotation(name = "diagnosisId", aliasName = "inspectPojo", symbol = SymbolConstants.IN)
    private List<String> diagnosisIds;

    public String getBcaId() {
        return bcaId;
    }

    public void setBcaId(String bcaId) {
        this.bcaId = bcaId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getUserExamDate() {
        return userExamDate;
    }

    public void setUserExamDate(Date userExamDate) {
        this.userExamDate = userExamDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public List<String> getDiagnosisIds() {
        return diagnosisIds;
    }

    public void setDiagnosisIds(List<String> diagnosisIds) {
        this.diagnosisIds = diagnosisIds;
    }

}
