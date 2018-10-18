
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 检测指标
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-11 zcq 初版
 */
public class ExamItemPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 检测项目记录表主键 */
    @QueryFieldAnnotation
    private String examId;

    /** 项目编码 */
    @QueryFieldAnnotation
    private String itemCode;

    /** 项目名称 */
    @QueryFieldAnnotation
    private String itemName;

    /** 项目值类型 */
    @QueryFieldAnnotation
    private String itemType;

    /** 项目归类 */
    @QueryFieldAnnotation
    private String itemClassify;

    /** 项目值文本型 */
    @QueryFieldAnnotation
    private String itemString;

    /** 项目标准单位 */
    @QueryFieldAnnotation
    private String itemUnit;

    /** 项目与参考值对比结果 */
    @QueryFieldAnnotation
    private String itemCompare;

    /** 项目结果 */
    @QueryFieldAnnotation
    private String itemResult;

    /** 项目标准参考值 */
    @QueryFieldAnnotation
    private String itemRefValue;

    /** 检查医生 */
    private String itemDoctor;

    /** 检查时间 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private Date examDatetime;

    /** 指标检测时间 */
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private Date itemDatetime;

    /** 科室id */
    private String originDeptId;

    /** 科室名称 */
    private String originDeptName;

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public String getItemString() {
        return itemString;
    }

    public void setItemString(String itemString) {
        this.itemString = itemString;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemCompare() {
        return itemCompare;
    }

    public void setItemCompare(String itemCompare) {
        this.itemCompare = itemCompare;
    }

    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    public String getItemDoctor() {
        return itemDoctor;
    }

    public void setItemDoctor(String itemDoctor) {
        this.itemDoctor = itemDoctor;
    }

    public Date getExamDatetime() {
        return examDatetime;
    }

    public void setExamDatetime(Date examDatetime) {
        this.examDatetime = examDatetime;
    }

    public Date getItemDatetime() {
        return itemDatetime;
    }

    public void setItemDatetime(Date itemDatetime) {
        this.itemDatetime = itemDatetime;
    }

    public String getOriginDeptId() {
        return originDeptId;
    }

    public void setOriginDeptId(String originDeptId) {
        this.originDeptId = originDeptId;
    }

    public String getOriginDeptName() {
        return originDeptName;
    }

    public void setOriginDeptName(String originDeptName) {
        this.originDeptName = originDeptName;
    }

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    @Override
    public String toString() {
        return "ExamItemPojo [id=" + id + ", examId=" + examId + ", itemCode=" + itemCode + ", itemName=" + itemName
                + ", itemType=" + itemType + ", itemClassify=" + itemClassify + ", itemString=" + itemString
                + ", itemUnit=" + itemUnit + ", itemCompare=" + itemCompare + ", itemResult=" + itemResult
                + ", itemRefValue=" + itemRefValue + ", itemDoctor=" + itemDoctor + ", examDatetime=" + examDatetime
                + ", itemDatetime=" + itemDatetime + ", originDeptId=" + originDeptId + ", originDeptName="
                + originDeptName + "]";
    }

}
