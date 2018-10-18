
package com.mnt.preg.customer.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 处方模板检索条件
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-2-23 gss 初版
 */
public class OrderTempletCondition implements Serializable {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 模板类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String templetType;

    /** 模板名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String templetName;

    /** 处方诊断 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String orderDiagnosis;

    /** 处方备注 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String orderRemark;

    /** 撰写医生 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String createDoctor;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getTempletType() {
        return templetType;
    }

    public void setTempletType(String templetType) {
        this.templetType = templetType;
    }

    public String getOrderDiagnosis() {
        return orderDiagnosis;
    }

    public void setOrderDiagnosis(String orderDiagnosis) {
        this.orderDiagnosis = orderDiagnosis;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getCreateDoctor() {
        return createDoctor;
    }

    public void setCreateDoctor(String createDoctor) {
        this.createDoctor = createDoctor;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getTempletName() {
        return templetName;
    }

    public void setTempletName(String templetName) {
        this.templetName = templetName;
    }

}
