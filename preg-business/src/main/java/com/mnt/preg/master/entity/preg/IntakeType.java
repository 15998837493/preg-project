
package com.mnt.preg.master.entity.preg;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 摄入类型
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-8-26 zcq 初版
 */
@Entity
@Table(name = "mas_intake_type")
public class IntakeType extends MappedEntity {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    private String id;

    /** 编码 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private String code;

    /** 名称 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String name;

    /** 类型 */
    @UpdateAnnotation
    private String type;

    /** 单位 */
    @UpdateAnnotation
    private String unit;

    /** 每单位量（换算关系g） */
    @UpdateAnnotation
    private BigDecimal unitAmount;

    /** 每份热量 */
    @UpdateAnnotation
    private BigDecimal unitEnergy;

    /** 每份碳水化合物 */
    @UpdateAnnotation
    private BigDecimal unitCbr;

    /** 每份蛋白质 */
    @UpdateAnnotation
    private BigDecimal unitProtein;

    /** 每份脂肪 */
    @UpdateAnnotation
    private BigDecimal unitFat;

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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "unit_amount")
    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "unit_energy")
    public BigDecimal getUnitEnergy() {
        return unitEnergy;
    }

    public void setUnitEnergy(BigDecimal unitEnergy) {
        this.unitEnergy = unitEnergy;
    }

    @Column(name = "unit_cbr")
    public BigDecimal getUnitCbr() {
        return unitCbr;
    }

    public void setUnitCbr(BigDecimal unitCbr) {
        this.unitCbr = unitCbr;
    }

    @Column(name = "unit_protein")
    public BigDecimal getUnitProtein() {
        return unitProtein;
    }

    public void setUnitProtein(BigDecimal unitProtein) {
        this.unitProtein = unitProtein;
    }

    @Column(name = "unit_fat")
    public BigDecimal getUnitFat() {
        return unitFat;
    }

    public void setUnitFat(BigDecimal unitFat) {
        this.unitFat = unitFat;
    }

}
