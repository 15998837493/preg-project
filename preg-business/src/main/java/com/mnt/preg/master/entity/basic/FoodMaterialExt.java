
package com.mnt.preg.master.entity.basic;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;


/**
 * 
 * 食材扩展（食材元素）实体类
 *
 * @author zj
 * @version 1.0 
 *
 * 变更履历：
 *   v1.0 2018-4-13 zj 初版
 */
@Entity
@Table(name = "mas_food_material_ext")
public class FoodMaterialExt extends MappedEntity {

    private static final long serialVersionUID = 1564669757226521714L;

    // 主键
    @QueryFieldAnnotation
    private String fmId;

    //酒精度 alcohol
    @UpdateAnnotation
    private BigDecimal fmAlcoholVol;
    
    //酒精度 Weight%(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmAlcoholWeight;
    
    //可食用部分(%)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private Integer fmEsculent;
    
    //能量(kcal)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmEnergy;
    
    //脂肪(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmFat;
    
    //蛋白质(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmProtid;
    
    //碳水化合物(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmCbr;
    
    //水分 Water(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmWater;
    
    //总膳食纤维(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmTotalDietaryFiber;
    
    //可溶性膳食纤维(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmSolubieDietaryFiber;
    
    //不溶性膳食纤维(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmInsolubleDietaryFiber;
    
    //胆固醇(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmCholesterol;
    
    //灰分(g)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmAsh;
    
    //维生素A(μgRE)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmVitaminA;
    
    //胡萝卜素(μg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmCarotene;
    
    //视黄醇(μg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmRetinol;
    
    //硫胺素(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmThiamin;
    
    //核黄素(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmRiboflavin;
    
    //维生素B6(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmVitaminB6;
    
    //维生素B12(μg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmVitaminB12;

    //叶酸(μg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmFolate;
    
    //烟酸(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmNiacin;
    
    //维生素C(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmVitaminC;
    
    //总维生素E(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmTotalVitaminE;

    //维生素E(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmVitaminE;
    
    //钙(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementCa;
    
    //磷(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementP;

    //钾(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementK;
    
    //钠(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementNa;
    
    //镁(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementMg;
    
    //铁(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementFe;
    
    //锌(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementZn;

    //硒(μg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementSe;
    
    //铜(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementCu;
    
    //锰(mg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementMn;
    
    //碘(μg)
    @UpdateAnnotation
    @QueryFieldAnnotation
    private BigDecimal fmElementI;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "fm_id")
    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    @Column(name = "fm_alcohol_vol")
    public BigDecimal getFmAlcoholVol() {
        return fmAlcoholVol;
    }
    
    public void setFmAlcoholVol(BigDecimal fmAlcoholVol) {
        this.fmAlcoholVol = fmAlcoholVol;
    }

    @Column(name = "fm_alcohol_weight")
    public BigDecimal getFmAlcoholWeight() {
        return fmAlcoholWeight;
    }
    
    public void setFmAlcoholWeight(BigDecimal fmAlcoholWeight) {
        this.fmAlcoholWeight = fmAlcoholWeight;
    }

    @Column(name = "fm_esculent")   
    public Integer getFmEsculent() {
        return fmEsculent;
    }
    
    public void setFmEsculent(Integer fmEsculent) {
        this.fmEsculent = fmEsculent;
    }

    @Column(name = "fm_energy")
    public BigDecimal getFmEnergy() {
        return fmEnergy;
    }
    
    public void setFmEnergy(BigDecimal fmEnergy) {
        this.fmEnergy = fmEnergy;
    }

    @Column(name = "fm_fat")    
    public BigDecimal getFmFat() {
        return fmFat;
    }
   
    public void setFmFat(BigDecimal fmFat) {
        this.fmFat = fmFat;
    }

    @Column(name = "fm_protid")   
    public BigDecimal getFmProtid() {
        return fmProtid;
    }
    
    public void setFmProtid(BigDecimal fmProtid) {
        this.fmProtid = fmProtid;
    }

    @Column(name = "fm_cbr")    
    public BigDecimal getFmCbr() {
        return fmCbr;
    }
    
    public void setFmCbr(BigDecimal fmCbr) {
        this.fmCbr = fmCbr;
    }

    @Column(name = "fm_water")    
    public BigDecimal getFmWater() {
        return fmWater;
    }
   
    public void setFmWater(BigDecimal fmWater) {
        this.fmWater = fmWater;
    }

    @Column(name = "fm_total_dietary_fiber")    
    public BigDecimal getFmTotalDietaryFiber() {
        return fmTotalDietaryFiber;
    }
    
    public void setFmTotalDietaryFiber(BigDecimal fmTotalDietaryFiber) {
        this.fmTotalDietaryFiber = fmTotalDietaryFiber;
    }

    @Column(name = "fm_solubie_dietary_fiber")    
    public BigDecimal getFmSolubieDietaryFiber() {
        return fmSolubieDietaryFiber;
    }
   
    public void setFmSolubieDietaryFiber(BigDecimal fmSolubieDietaryFiber) {
        this.fmSolubieDietaryFiber = fmSolubieDietaryFiber;
    }

    @Column(name = "fm_insoluble_dietary_fiber")   
    public BigDecimal getFmInsolubleDietaryFiber() {
        return fmInsolubleDietaryFiber;
    }
    
    public void setFmInsolubleDietaryFiber(BigDecimal fmInsolubleDietaryFiber) {
        this.fmInsolubleDietaryFiber = fmInsolubleDietaryFiber;
    }

    @Column(name = "fm_cholesterol")   
    public BigDecimal getFmCholesterol() {
        return fmCholesterol;
    }
   
    public void setFmCholesterol(BigDecimal fmCholesterol) {
        this.fmCholesterol = fmCholesterol;
    }

    @Column(name = "fm_ash")
    public BigDecimal getFmAsh() {
        return fmAsh;
    }
    
    public void setFmAsh(BigDecimal fmAsh) {
        this.fmAsh = fmAsh;
    }

    @Column(name = "fm_vitamin_a")    
    public BigDecimal getFmVitaminA() {
        return fmVitaminA;
    }
    
    public void setFmVitaminA(BigDecimal fmVitaminA) {
        this.fmVitaminA = fmVitaminA;
    }

    @Column(name = "fm_carotene")    
    public BigDecimal getFmCarotene() {
        return fmCarotene;
    }
    
    public void setFmCarotene(BigDecimal fmCarotene) {
        this.fmCarotene = fmCarotene;
    }

    @Column(name = "fm_retinol")    
    public BigDecimal getFmRetinol() {
        return fmRetinol;
    }
    
    public void setFmRetinol(BigDecimal fmRetinol) {
        this.fmRetinol = fmRetinol;
    }

    @Column(name = "fm_thiamin") 
    public BigDecimal getFmThiamin() {
        return fmThiamin;
    }
    
    public void setFmThiamin(BigDecimal fmThiamin) {
        this.fmThiamin = fmThiamin;
    }

    @Column(name = "fm_riboflavin")   
    public BigDecimal getFmRiboflavin() {
        return fmRiboflavin;
    }

    public void setFmRiboflavin(BigDecimal fmRiboflavin) {
        this.fmRiboflavin = fmRiboflavin;
    }

    @Column(name = "fm_vitamin_b6")    
    public BigDecimal getFmVitaminB6() {
        return fmVitaminB6;
    }
   
    public void setFmVitaminB6(BigDecimal fmVitaminB6) {
        this.fmVitaminB6 = fmVitaminB6;
    }

    @Column(name = "fm_vitamin_b12")   
    public BigDecimal getFmVitaminB12() {
        return fmVitaminB12;
    }
   
    public void setFmVitaminB12(BigDecimal fmVitaminB12) {
        this.fmVitaminB12 = fmVitaminB12;
    }

    @Column(name = "fm_folate")   
    public BigDecimal getFmFolate() {
        return fmFolate;
    }
    
    public void setFmFolate(BigDecimal fmFolate) {
        this.fmFolate = fmFolate;
    }

    @Column(name = "fm_niacin")    
    public BigDecimal getFmNiacin() {
        return fmNiacin;
    }

    public void setFmNiacin(BigDecimal fmNiacin) {
        this.fmNiacin = fmNiacin;
    }

    @Column(name = "fm_vitamin_c")    
    public BigDecimal getFmVitaminC() {
        return fmVitaminC;
    }
   
    public void setFmVitaminC(BigDecimal fmVitaminC) {
        this.fmVitaminC = fmVitaminC;
    }

    @Column(name = "fm_total_vitamin_e")    
    public BigDecimal getFmTotalVitaminE() {
        return fmTotalVitaminE;
    }
    
    public void setFmTotalVitaminE(BigDecimal fmTotalVitaminE) {
        this.fmTotalVitaminE = fmTotalVitaminE;
    }

    @Column(name = "fm_vitamin_e")    
    public BigDecimal getFmVitaminE() {
        return fmVitaminE;
    }
   
    public void setFmVitaminE(BigDecimal fmVitaminE) {
        this.fmVitaminE = fmVitaminE;
    }

    @Column(name = "fm_element_ca")    
    public BigDecimal getFmElementCa() {
        return fmElementCa;
    }
    
    public void setFmElementCa(BigDecimal fmElementCa) {
        this.fmElementCa = fmElementCa;
    }

    @Column(name = "fm_element_p")    
    public BigDecimal getFmElementP() {
        return fmElementP;
    }
    
    public void setFmElementP(BigDecimal fmElementP) {
        this.fmElementP = fmElementP;
    }

    @Column(name = "fm_element_k")   
    public BigDecimal getFmElementK() {
        return fmElementK;
    }
    
    public void setFmElementK(BigDecimal fmElementK) {
        this.fmElementK = fmElementK;
    }

    @Column(name = "fm_element_na")   
    public BigDecimal getFmElementNa() {
        return fmElementNa;
    }
   
    public void setFmElementNa(BigDecimal fmElementNa) {
        this.fmElementNa = fmElementNa;
    }

    @Column(name = "fm_element_mg")    
    public BigDecimal getFmElementMg() {
        return fmElementMg;
    }
  
    public void setFmElementMg(BigDecimal fmElementMg) {
        this.fmElementMg = fmElementMg;
    }

    @Column(name = "fm_element_fe")    
    public BigDecimal getFmElementFe() {
        return fmElementFe;
    }
 
    public void setFmElementFe(BigDecimal fmElementFe) {
        this.fmElementFe = fmElementFe;
    }

    @Column(name = "fm_element_zn")   
    public BigDecimal getFmElementZn() {
        return fmElementZn;
    }
  
    public void setFmElementZn(BigDecimal fmElementZn) {
        this.fmElementZn = fmElementZn;
    }

    @Column(name = "fm_element_se")  
    public BigDecimal getFmElementSe() {
        return fmElementSe;
    }
    
    public void setFmElementSe(BigDecimal fmElementSe) {
        this.fmElementSe = fmElementSe;
    }

    @Column(name = "fm_element_cu")   
    public BigDecimal getFmElementCu() {
        return fmElementCu;
    }
   
    public void setFmElementCu(BigDecimal fmElementCu) {
        this.fmElementCu = fmElementCu;
    }

    @Column(name = "fm_element_mn")   
    public BigDecimal getFmElementMn() {
        return fmElementMn;
    }

    public void setFmElementMn(BigDecimal fmElementMn) {
        this.fmElementMn = fmElementMn;
    }

    @Column(name = "fm_element_i")    
    public BigDecimal getFmElementI() {
        return fmElementI;
    }
    
    public void setFmElementI(BigDecimal fmElementI) {
        this.fmElementI = fmElementI;
    }
    
}
