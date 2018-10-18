
package com.mnt.preg.examitem.condition;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 膳食报告检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public class FoodRecordCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 主键评估编号 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String foodRecordId;

    /** 客户主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custId;

    /** 记录时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC, orderNumber = 1)
    private Date recordDatetime;

    /** 记录日期(范围选择：开始） */
    @QueryConditionAnnotation(name = "recordDatetime", symbol = SymbolConstants.GE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 记录日期(范围选择：结束） */
    @QueryConditionAnnotation(name = "recordDatetime", symbol = SymbolConstants.LT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 客户主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, aliasName = "cust")
    private String custCode;

    /** 客户主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, aliasName = "cust")
    private String custName;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getRecordDatetime() {
        return recordDatetime;
    }

    public void setRecordDatetime(Date recordDatetime) {
        this.recordDatetime = recordDatetime;
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

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
