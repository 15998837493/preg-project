/*
 * InstitutionCondition.java	 1.0   2014-12-15
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.condition;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 机构检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-15 zcq 初版
 */
public class InstitutionCondition implements Serializable {

    private static final long serialVersionUID = -2537986051317146805L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String insId;

    /** 机构名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String insName;

    /** 机构地区 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String insPlace;

    /** 机构等级 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String insGrade;

    /** 机构类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String insType;

    /** 机构运行状态--0=停止，1=运行 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer insState;

    /** 创建时间排序 */
    @XmlTransient
    @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Date createTime;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getInsPlace() {
        return insPlace;
    }

    public void setInsPlace(String insPlace) {
        this.insPlace = insPlace;
    }

    public String getInsGrade() {
        return insGrade;
    }

    public void setInsGrade(String insGrade) {
        this.insGrade = insGrade;
    }

    public String getInsType() {
        return insType;
    }

    public void setInsType(String insType) {
        this.insType = insType;
    }

    public Integer getInsState() {
        return insState;
    }

    public void setInsState(Integer insState) {
        this.insState = insState;
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

}
