
package com.mnt.preg.platform.condition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 干预计划检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-30 zcq 初版
 */
public class IntervenePlanCondition implements Serializable {

    private static final long serialVersionUID = -4728716714510926206L;

    /** 方案主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String planId;

    /** 接诊号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 接诊号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "diagnosisId")
    private List<String> diagnosisIdList;

    /** 膳食结构 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodStructureId;

    /** 膳食结构名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String foodStructureName;

    /** 服务营养师 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String userId;

    /** 服务营养师名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String userName;

    /** 创建人 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String createUserId;

    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String createInsId;

    @XmlTransient
    @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Date createTime;

    /** 创建时间 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LT, name = "createDate", order = OrderConstants.DESC)
    private Date endDate;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 服务营养师 */
    private String custId;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public List<String> getDiagnosisIdList() {
        return diagnosisIdList;
    }

    public void setDiagnosisIdList(List<String> diagnosisIdList) {
        this.diagnosisIdList = diagnosisIdList;
    }

    public String getFoodStructureId() {
        return foodStructureId;
    }

    public void setFoodStructureId(String foodStructureId) {
        this.foodStructureId = foodStructureId;
    }

    public String getFoodStructureName() {
        return foodStructureName;
    }

    public void setFoodStructureName(String foodStructureName) {
        this.foodStructureName = foodStructureName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
