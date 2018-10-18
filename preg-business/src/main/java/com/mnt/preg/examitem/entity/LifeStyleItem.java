/*
 * LifeStyleItem.java   1.0   2016-2-25
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.examitem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 膳食及生活方式评估分析
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-6 mnt_zhangjing 初版
 */
@Entity
@Table(name = "cus_result_life")
public class LifeStyleItem extends MappedEntity {

    private static final long serialVersionUID = 6839045999896289187L;

    /* 主键 */
    private String id;

    /* 体检id */
    private String examId;

    /* 项目编码 */
    private String itemCode;

    /* 项目名称 */
    private String itemName;

    /* 项目值类型 */
    private String itemType;

    /* 项目分类类型 */
    private String itemClassify;

    /* 项目值文本型 */
    private String itemString;

    /* 项目标准单位 */
    private String itemUnit;

    /* 项目与参考值对比结果 */
    private String itemCompare;

    /* 项目分析结果 */
    private String itemResult;

    /* 项目标准参考值 */
    private String itemRefValue;

    /* 检查医生 */
    private String itemDoctor;

    /* 检查时间 */
    private Date examDatetime;

    /* 科室名称 */
    private String originDeptName;

    /* 科室ID */
    private String originDeptId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "exam_id")
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "exam_datetime")
    public Date getExamDatetime() {
        return examDatetime;
    }

    public void setExamDatetime(Date examDatetime) {
        this.examDatetime = examDatetime;
    }

    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name = "item_type")
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Column(name = "item_string")
    public String getItemString() {
        return itemString;
    }

    public void setItemString(String itemString) {
        this.itemString = itemString;
    }

    @Column(name = "item_unit")
    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    @Column(name = "item_compare")
    public String getItemCompare() {
        return itemCompare;
    }

    public void setItemCompare(String itemCompare) {
        this.itemCompare = itemCompare;
    }

    @Column(name = "item_ref_value")
    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    @Column(name = "item_doctor")
    public String getItemDoctor() {
        return itemDoctor;
    }

    public void setItemDoctor(String itemDoctor) {
        this.itemDoctor = itemDoctor;
    }

    @Column(name = "item_result")
    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    @Column(name = "origin_dept_name")
    public String getOriginDeptName() {
        return originDeptName;
    }

    public void setOriginDeptName(String originDeptName) {
        this.originDeptName = originDeptName;
    }

    @Column(name = "origin_dept_id")
    public String getOriginDeptId() {
        return originDeptId;
    }

    public void setOriginDeptId(String originDeptId) {
        this.originDeptId = originDeptId;
    }

    @Column(name = "item_classify")
    public String getItemClassify() {
        return itemClassify;
    }

    public void setItemClassify(String itemClassify) {
        this.itemClassify = itemClassify;
    }

}
