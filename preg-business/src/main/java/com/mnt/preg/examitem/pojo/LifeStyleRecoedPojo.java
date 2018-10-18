
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * 膳食及生活方式记录总表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-3-9 mnt_zhangjing 初版
 */
public class LifeStyleRecoedPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 主键评估编号 */
    private String recordId;

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

    /** 记录时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDatetime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public Date getRecordDatetime() {
        return recordDatetime;
    }

    public void setRecordDatetime(Date recordDatetime) {
        this.recordDatetime = recordDatetime;
    }

}
