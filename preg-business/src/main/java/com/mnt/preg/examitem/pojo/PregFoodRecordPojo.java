
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 日记记录信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public class PregFoodRecordPojo implements Serializable {

    private static final long serialVersionUID = -205607526597567742L;

    /** 主键评估编号 */
    @QueryFieldAnnotation
    private String foodRecordId;

    /** 客户主键 */
    @QueryFieldAnnotation
    private String custId;

    /** 客户年龄 */
    @QueryFieldAnnotation
    private Integer custAge;

    /** 客户性别 */
    @QueryFieldAnnotation
    private String custSex;

    /** 客户身高 */
    @QueryFieldAnnotation
    private BigDecimal custHeight;

    /** 客户体重 */
    @QueryFieldAnnotation
    private BigDecimal custWeight;

    /** 客户体力水平 */
    @QueryFieldAnnotation
    private String custPlevel;

    /** 客户饮水量 */
    @QueryFieldAnnotation
    private Integer mealsWater;

    /** 记录时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryFieldAnnotation
    private Date recordDatetime;

    /** 膳食回顾PDF报告 */
    @QueryFieldAnnotation
    private String dietPdfPath;

    /** 数据机构 */
    @QueryFieldAnnotation
    private String createInsId;

    /** 客户姓名 */
    @QueryFieldAnnotation(aliasName = "cust")
    private String custName;

    /** 客户编号 */
    @QueryFieldAnnotation(aliasName = "cust")
    private String custCode;

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

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    public BigDecimal getCustWeight() {
        return custWeight;
    }

    public void setCustWeight(BigDecimal custWeight) {
        this.custWeight = custWeight;
    }

    public String getCustPlevel() {
        return custPlevel;
    }

    public void setCustPlevel(String custPlevel) {
        this.custPlevel = custPlevel;
    }

    public Integer getMealsWater() {
        return mealsWater;
    }

    public void setMealsWater(Integer mealsWater) {
        this.mealsWater = mealsWater;
    }

    public Date getRecordDatetime() {
        return recordDatetime;
    }

    public void setRecordDatetime(Date recordDatetime) {
        this.recordDatetime = recordDatetime;
    }

    public String getDietPdfPath() {
        return dietPdfPath;
    }

    public void setDietPdfPath(String dietPdfPath) {
        this.dietPdfPath = dietPdfPath;
    }

    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

}
