
package com.mnt.preg.system.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 打印选项检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-15 zcq 初版
 */
public class PrintCondition implements Serializable {

    private static final long serialVersionUID = -2537986051317146805L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String printId;

    /** 分类 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String printCategory;

    /** 排序 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    private Integer printOrder;

    /** 是否预览 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer printPreview;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 机构 */
    private String insId;

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    public String getPrintCategory() {
        return printCategory;
    }

    public void setPrintCategory(String printCategory) {
        this.printCategory = printCategory;
    }

    public Integer getPrintOrder() {
        return printOrder;
    }

    public void setPrintOrder(Integer printOrder) {
        this.printOrder = printOrder;
    }

    public Integer getPrintPreview() {
        return printPreview;
    }

    public void setPrintPreview(Integer printPreview) {
        this.printPreview = printPreview;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
