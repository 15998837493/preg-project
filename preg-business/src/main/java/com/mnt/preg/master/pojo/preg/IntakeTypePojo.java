
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 摄入量类型
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-10 zcq 初版
 */
public class IntakeTypePojo implements Serializable {

    private static final long serialVersionUID = 1403586173137058987L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 编码 */
    @QueryFieldAnnotation
    private String code;

    /** 名称 */
    @QueryFieldAnnotation
    private String name;

    /** 名称 */
    @QueryFieldAnnotation
    private String type;

    /** 名称 */
    @QueryFieldAnnotation
    private String unit;

    /** 每单位量（换算关系g） */
    @QueryFieldAnnotation
    private BigDecimal unitAmount;

    /** 每份热量 */
    @QueryFieldAnnotation
    private BigDecimal unitEnergy;

    /** 每份碳水化合物 */
    @QueryFieldAnnotation
    private BigDecimal unitCbr;

    /** 每份蛋白质 */
    @QueryFieldAnnotation
    private BigDecimal unitProtein;

    /** 每份脂肪 */
    @QueryFieldAnnotation
    private BigDecimal unitFat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getUnitEnergy() {
        return unitEnergy;
    }

    public void setUnitEnergy(BigDecimal unitEnergy) {
        this.unitEnergy = unitEnergy;
    }

    public BigDecimal getUnitCbr() {
        return unitCbr;
    }

    public void setUnitCbr(BigDecimal unitCbr) {
        this.unitCbr = unitCbr;
    }

    public BigDecimal getUnitProtein() {
        return unitProtein;
    }

    public void setUnitProtein(BigDecimal unitProtein) {
        this.unitProtein = unitProtein;
    }

    public BigDecimal getUnitFat() {
        return unitFat;
    }

    public void setUnitFat(BigDecimal unitFat) {
        this.unitFat = unitFat;
    }

}
