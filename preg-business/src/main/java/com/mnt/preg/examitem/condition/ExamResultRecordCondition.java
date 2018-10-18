
package com.mnt.preg.examitem.condition;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 检查项目结果表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-16 zcq 初版
 */
public class ExamResultRecordCondition implements Serializable {

    private static final long serialVersionUID = -6319783025844304652L;

    /** 体检id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String examId;

    /** 检查编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String examCode;

    /** 检查编码 */
    @QueryConditionAnnotation(name = "examCode", symbol = SymbolConstants.IN)
    private List<String> examCodeList;

    /** 检查类别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String examCategory;

    /** 检查日期 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.DESC)
    private String examDate;

    /** 开始诊疗日期 */
    @QueryConditionAnnotation(name = "examDate", symbol = SymbolConstants.GE)
    private String startDate;

    /** 结束诊疗日期 */
    @QueryConditionAnnotation(name = "examDate", symbol = SymbolConstants.LT)
    private String endDate;

    /** 客户id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String custId;

    /** 会员姓名 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String custName;

    // 标识
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public List<String> getExamCodeList() {
        return examCodeList;
    }

    public void setExamCodeList(List<String> examCodeList) {
        this.examCodeList = examCodeList;
    }

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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
