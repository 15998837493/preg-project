
package com.mnt.preg.master.entity.basic;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * mas_产品（食材）元素表
 * 
 * @author zj
 * @version 1.0
 * 
 *          变更履历：v1.0 2018-8-3 zj 初版
 */
@Entity
@Table(name = "mas_nutrient_amount")
public class NutrientAmount extends MappedEntity {

	private static final long serialVersionUID = -3197741092525699829L;

	/** 编码 */
	private String id;

	/** 药物 */
	private String corresId;

	/** 营养素 */
	private String nutrientId;

	/** 药物营养素剂量 */
	private BigDecimal nutrientDosage;

	/** 成分备注 */
	private String nutrientRemark;

	/** 类型 */
	private String corresType;

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

	@Column(name = "corres_id")
	public String getCorresId() {
		return corresId;
	}

	public void setCorresId(String corresId) {
		this.corresId = corresId;
	}

	@Column(name = "nutrient_id")
	public String getNutrientId() {
		return nutrientId;
	}

	public void setNutrientId(String nutrientId) {
		this.nutrientId = nutrientId;
	}

	@Column(name = "nutrient_dosage")
	public BigDecimal getNutrientDosage() {
		return nutrientDosage;
	}

	public void setNutrientDosage(BigDecimal nutrientDosage) {
		this.nutrientDosage = nutrientDosage;
	}

	@Column(name = "nutrient_remark")
	public String getNutrientRemark() {
		return nutrientRemark;
	}

	public void setNutrientRemark(String nutrientRemark) {
		this.nutrientRemark = nutrientRemark;
	}

	@Column(name = "corres_type")
	public String getCorresType() {
		return corresType;
	}

	public void setCorresType(String corresType) {
		this.corresType = corresType;
	}

}
