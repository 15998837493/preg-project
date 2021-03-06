
package com.mnt.preg.examitem.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 膳食评估记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-16 zcq 初版
 */
@Entity
@Table(name = "cus_food_record")
public class PregFoodRecord extends MappedEntity {

    private static final long serialVersionUID = 3072445668040737697L;

    /** 主键评估编号 */
    private String foodRecordId;

    /** 客户主键 */
    private String custId;

    /** 客户年龄 */
    private Integer custAge;

    /** 客户性别 */
    private String custSex;

    /** 客户身高 */
    private BigDecimal custHeight;

    /** 客户体重 */
    private BigDecimal custWeight;

    /** 客户体力水平 */
    private String custPlevel;

    /** 客户饮水量 */
    @UpdateAnnotation
    private Integer mealsWater;

    /** 记录时间 */
    private Date recordDatetime;

    /** 膳食回顾PDF报告路径 */
    @UpdateAnnotation
    private String dietPdfPath;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "food_record_id")
    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "cust_age")
    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    @Column(name = "cust_sex")
    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }

    @Column(name = "cust_height")
    public BigDecimal getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(BigDecimal custHeight) {
        this.custHeight = custHeight;
    }

    @Column(name = "cust_weight")
    public BigDecimal getCustWeight() {
        return custWeight;
    }

    public void setCustWeight(BigDecimal custWeight) {
        this.custWeight = custWeight;
    }

    @Column(name = "cust_plevel")
    public String getCustPlevel() {
        return custPlevel;
    }

    public void setCustPlevel(String custPlevel) {
        this.custPlevel = custPlevel;
    }

    @Column(name = "meals_water")
    public Integer getMealsWater() {
        return mealsWater;
    }

    public void setMealsWater(Integer mealsWater) {
        this.mealsWater = mealsWater;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "record_datetime")
    public Date getRecordDatetime() {
        return recordDatetime;
    }

    public void setRecordDatetime(Date recordDatetime) {
        this.recordDatetime = recordDatetime;
    }

    @Column(name = "diet_pdf_path")
    public String getDietPdfPath() {
        return dietPdfPath;
    }

    public void setDietPdfPath(String dietPdfPath) {
        this.dietPdfPath = dietPdfPath;
    }

}
