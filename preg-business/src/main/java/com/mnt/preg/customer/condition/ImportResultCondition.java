/*
 * ImportResultCondition.java	 1.0   2015-9-8
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.condition;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 
 * 批量导入结果信息检索条件
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-9-8 mnt_zhangjing 初版
 */
public class ImportResultCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 记录日期(范围选择：开始） */
    @QueryConditionAnnotation(name = "importDate", symbol = SymbolConstants.GE)
    private Date recordDateFrom;

    /** 记录日期(范围选择：结束） */
    @QueryConditionAnnotation(name = "importDate", symbol = SymbolConstants.LE)
    private Date recordDateTo;

    /** 记录日期(范围选择：结束） */
    @QueryConditionAnnotation(symbol = OrderConstants.DESC)
    private Date importDate;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public Date getRecordDateFrom() {
        return recordDateFrom;
    }

    public void setRecordDateFrom(Date recordDateFrom) {
        this.recordDateFrom = recordDateFrom;
    }

    public Date getRecordDateTo() {
        return recordDateTo;
    }

    public void setRecordDateTo(Date recordDateTo) {
        this.recordDateTo = recordDateTo;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
