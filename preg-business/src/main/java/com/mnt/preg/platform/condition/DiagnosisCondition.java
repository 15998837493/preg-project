
package com.mnt.preg.platform.condition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 登记检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-15 zcq 初版
 */
public class DiagnosisCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 门诊号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 客户 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisCustomer;

    /** 客户姓名 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String diagnosisCustName;

    /** 医生 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisUser;

    /** 出诊时间 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisOrg;

    /** 状态 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private Integer diagnosisStatus;

    /** 助理端就诊状态：1=等待接诊，2=接诊，3=完成 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private Integer diagnosisAssistantStatus;

    /** 助理端就诊状态：1=等待接诊，2=接诊，3=完成 */
    @QueryConditionAnnotation(symbol = SymbolConstants.NEQ, name = "diagnosisAssistantStatus")
    private Integer diagnosisAssistantStatusNot;

    /** 接诊时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC)
    private Date diagnosisDate;

    /** 出诊时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(name = "diagnosisDate", symbol = SymbolConstants.GE)
    private Date startDate;

    /** 出诊时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(name = "diagnosisDate", symbol = SymbolConstants.LT)
    private Date endDate;

    /** 创建时间 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    private Date createTime;

    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 门诊号集合 */
    @QueryConditionAnnotation(name = "diagnosisId", symbol = SymbolConstants.IN)
    private List<String> diagnosisIds;

    public DiagnosisCondition() {
        super();
    }

    public DiagnosisCondition(String diagnosisId) {
        super();
        this.diagnosisId = diagnosisId;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDiagnosisCustomer() {
        return diagnosisCustomer;
    }

    public void setDiagnosisCustomer(String diagnosisCustomer) {
        this.diagnosisCustomer = diagnosisCustomer;
    }

    public String getDiagnosisCustName() {
        return diagnosisCustName;
    }

    public void setDiagnosisCustName(String diagnosisCustName) {
        this.diagnosisCustName = diagnosisCustName;
    }

    public String getDiagnosisUser() {
        return diagnosisUser;
    }

    public void setDiagnosisUser(String diagnosisUser) {
        this.diagnosisUser = diagnosisUser;
    }

    public String getDiagnosisOrg() {
        return diagnosisOrg;
    }

    public void setDiagnosisOrg(String diagnosisOrg) {
        this.diagnosisOrg = diagnosisOrg;
    }

    public Integer getDiagnosisStatus() {
        return diagnosisStatus;
    }

    public void setDiagnosisStatus(Integer diagnosisStatus) {
        this.diagnosisStatus = diagnosisStatus;
    }

    public Integer getDiagnosisAssistantStatusNot() {
        return diagnosisAssistantStatusNot;
    }

    public void setDiagnosisAssistantStatusNot(Integer diagnosisAssistantStatusNot) {
        this.diagnosisAssistantStatusNot = diagnosisAssistantStatusNot;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getDiagnosisAssistantStatus() {
        return diagnosisAssistantStatus;
    }

    public void setDiagnosisAssistantStatus(Integer diagnosisAssistantStatus) {
        this.diagnosisAssistantStatus = diagnosisAssistantStatus;
    }

    public List<String> getDiagnosisIds() {
        return diagnosisIds;
    }

    public void setDiagnosisIds(List<String> diagnosisIds) {
        this.diagnosisIds = diagnosisIds;
    }

}
