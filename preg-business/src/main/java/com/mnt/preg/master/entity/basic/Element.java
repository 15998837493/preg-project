
package com.mnt.preg.master.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

@Entity
@Table(name = "mas_nutrient")
public class Element extends MappedEntity {

    private static final long serialVersionUID = -3860857867851282617L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String id;

    /** 代码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, order = OrderConstants.ASC)
    @QueryFieldAnnotation
    private String nutrientId;

    /** 英文名称名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String nutrientNameEnglish;

    /** 名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String nutrientName;

    /** 单位 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String nutrientUnit;

    /** 类别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String nutrientType;

    /** 是否可评估(检测项目) */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String nutrientEvalOne;

    /** 是否可评估(开处方) */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String nutrientEvalTwo;

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "nutrient_id")
    public String getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

    @Column(name = "nutrient_name_english")
    public String getNutrientNameEnglish() {
        return nutrientNameEnglish;
    }

    public void setNutrientNameEnglish(String nutrientNameEnglish) {
        this.nutrientNameEnglish = nutrientNameEnglish;
    }

    @Column(name = "nutrient_name")
    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    @Column(name = "nutrient_unit")
    public String getNutrientUnit() {
        return nutrientUnit;
    }

    public void setNutrientUnit(String nutrientUnit) {
        this.nutrientUnit = nutrientUnit;
    }

    @Column(name = "nutrient_type")
    public String getNutrientType() {
        return nutrientType;
    }

    public void setNutrientType(String nutrientType) {
        this.nutrientType = nutrientType;
    }

    @Column(name = "nutrient_eval_one")
    public String getNutrientEvalOne() {
        return nutrientEvalOne;
    }

    public void setNutrientEvalOne(String nutrientEvalOne) {
        this.nutrientEvalOne = nutrientEvalOne;
    }

    @Column(name = "nutrient_eval_two")
    public String getNutrientEvalTwo() {
        return nutrientEvalTwo;
    }

    public void setNutrientEvalTwo(String nutrientEvalTwo) {
        this.nutrientEvalTwo = nutrientEvalTwo;
    }

}
