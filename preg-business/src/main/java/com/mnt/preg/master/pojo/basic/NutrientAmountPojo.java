
package com.mnt.preg.master.pojo.basic;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 商品元素表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-11-2 gss 初版
 */
public class NutrientAmountPojo implements Serializable {

	private static final long serialVersionUID = -3197741092525699829L;

	/** 编码 */
	@QueryFieldAnnotation
	private String id;

	/** 药物 */
	@QueryFieldAnnotation
	private String corresId;

	/** 营养素 */
	@QueryFieldAnnotation
	private String nutrientId;

	/** 药物营养素剂量 */
	@QueryFieldAnnotation
	private BigDecimal nutrientDosage;

	/** 成分备注 */
	@QueryFieldAnnotation
	private String nutrientRemark;

	/** 类型 */
	@QueryFieldAnnotation
	private String corresType;

	/** 元素名称 */
	private String nutrientName;

	/** 元素单位 */
	private String nutrientUnit;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCorresId() {
		return corresId;
	}

	public void setCorresId(String corresId) {
		this.corresId = corresId;
	}

	public String getNutrientId() {
		return nutrientId;
	}

	public void setNutrientId(String nutrientId) {
		this.nutrientId = nutrientId;
	}

	public BigDecimal getNutrientDosage() {
		return nutrientDosage;
	}

	public void setNutrientDosage(BigDecimal nutrientDosage) {
		this.nutrientDosage = nutrientDosage;
	}

	public String getNutrientRemark() {
		return nutrientRemark;
	}

	public void setNutrientRemark(String nutrientRemark) {
		this.nutrientRemark = nutrientRemark;
	}

	public String getCorresType() {
		return corresType;
	}

	public void setCorresType(String corresType) {
		this.corresType = corresType;
	}

	public String getNutrientName() {
		return nutrientName;
	}

	public void setNutrientName(String nutrientName) {
		this.nutrientName = nutrientName;
	}

	public String getNutrientUnit() {
		return nutrientUnit;
	}

	public void setNutrientUnit(String nutrientUnit) {
		this.nutrientUnit = nutrientUnit;
	}

}
