
package com.mnt.preg.examitem.condition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 结果表检测指标检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-17 zcq 初版
 */
public class ExamItemCondition implements Serializable {

    private static final long serialVersionUID = -6319783025844304652L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 体检id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String examId;

    /** 体检id */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "examId")
    private List<String> examIdList;

    /** 项目编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String itemCode;

    /** 项目编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "itemCode")
    private List<String> itemCodeList;

    /** 项目名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String itemName;

    /** 项目类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemType;

    /** 项目归类 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemClassify;

    /** 检测时间 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    private Date examDatetime;

    /** 项目比较 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemCompare;

    // 标识
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    /** 结果表名称 */
    private String tableName;

    /** 返回码 */
    private String resultCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public List<String> getExamIdList() {
        return examIdList;
    }

    public void setExamIdList(List<String> examIdList) {
        this.examIdList = examIdList;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public List<String> getItemCodeList() {
        return itemCodeList;
    }

    public void setItemCodeList(List<String> itemCodeList) {
        this.itemCodeList = itemCodeList;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemClassify() {
        return itemClassify;
    }

    public void setItemClassify(String itemClassify) {
        this.itemClassify = itemClassify;
    }

    public Date getExamDatetime() {
        return examDatetime;
    }

    public void setExamDatetime(Date examDatetime) {
        this.examDatetime = examDatetime;
    }

    public String getItemCompare() {
        return itemCompare;
    }

    public void setItemCompare(String itemCompare) {
        this.itemCompare = itemCompare;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
