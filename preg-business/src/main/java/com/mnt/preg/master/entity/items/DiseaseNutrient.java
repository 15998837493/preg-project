/*
 * DiseaseItemNutrient.java    1.0  2017-10-13
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.entity.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 疾病元素关联表
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-13 scd 初版
 */
@Entity
@Table(name = "mas_intervene_disease_nutrient")
public class DiseaseNutrient extends MappedEntity {

    private static final long serialVersionUID = -6654598350936044034L;

    /** 主键 */
    private String id;

    /** 疾病编码 */
    private String diseaseCode;

    /** 元素编码 */
    private String nutrientId;

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

    @Column(name = "disease_code")
    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    @Column(name = "nutrient_id")
    public String getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

}
