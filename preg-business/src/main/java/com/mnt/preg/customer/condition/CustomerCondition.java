/*
 * @(#)CustomerCondition.java    1.0  2015/01/04
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.condition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 
 * 会员检索条件
 * 
 * @author wangxin
 * @version 1.0
 * 
 *          变更履历： v1.0 2015/01/04 wangxin 初版
 */
public class CustomerCondition extends PageCondition implements Serializable {

    private static final long serialVersionUID = -6319783025844304652L;

    // 会员ID WEBSERVICE发布字段被忽略
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custId;

    // 会员账户
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custCode;

    // 身份证号
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custIcard;

    // 会员姓名
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String custName;

    // 会员性别
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custSex;

    // 会员手机号
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String custPhone;

    // 会员邮箱
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String custEmail;

    // 干预状态
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String planStatus;

    // 最小年龄
    @QueryConditionAnnotation(name = "custAge", symbol = SymbolConstants.GE)
    private Integer minAge;

    // 最大年龄
    @QueryConditionAnnotation(name = "custAge", symbol = SymbolConstants.LT)
    private Integer maxAge;

    @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Date createTime;

    // @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Date updateTime;

    // 登记页面检索条件
    private String content;

    // 标识
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    // 会员集合
    @QueryConditionAnnotation(name = "custId", symbol = SymbolConstants.IN)
    private List<String> custIds;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCustIcard() {
        return custIcard;
    }

    public void setCustIcard(String custIcard) {
        this.custIcard = custIcard;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getCustIds() {
        return custIds;
    }

    public void setCustIds(List<String> custIds) {
        this.custIds = custIds;
    }

}
