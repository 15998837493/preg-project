/*
 * FoodMaterialPojo.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.pojo.basic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 食材信息
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历： v1.0 2018-1-25 scd 初版
 */
public class FoodMaterialPojo implements Serializable {

	private static final long serialVersionUID = -8582191728645629665L;

	/** 主键 */
	@QueryFieldAnnotation
	private String fmId;

	/** 食材名称 */
	@QueryFieldAnnotation
	private String fmName;

	/** 食品别名,号分割 */
	@QueryFieldAnnotation
	private String fmAlias;

	/** 图片 */
	@QueryFieldAnnotation
	private String fmPic;

	/** GI值 */
	@QueryFieldAnnotation
	private BigDecimal fmGi;

	/** 是否属于优质蛋白质 */
	@QueryFieldAnnotation
	private String fmProtidFlag;

	/** 脂肪类型plant=植物，animal=动物 */
	@QueryFieldAnnotation
	private String fmFatType;

	/** 食材类型主键 */
	@QueryFieldAnnotation
	private String fmType;

	/** 嘌呤 */
	@QueryFieldAnnotation
	private BigDecimal fmPurin;

	/** 可食用部分 */
	@QueryFieldAnnotation
	private Integer fmEsculent;

	/** 水分 */
	@QueryFieldAnnotation
	private BigDecimal fmWater;

	/** 灰分 */
	@QueryFieldAnnotation
	private BigDecimal fmAsh;

	private String treeName;

	/** 食材元素信息 */
	private List<NutrientAmountPojo> productElementList;

	public String getFmId() {
		return fmId;
	}

	public void setFmId(String fmId) {
		this.fmId = fmId;
	}

	public String getFmName() {
		return fmName;
	}

	public void setFmName(String fmName) {
		this.fmName = fmName;
	}

	public String getFmAlias() {
		return fmAlias;
	}

	public void setFmAlias(String fmAlias) {
		this.fmAlias = fmAlias;
	}

	public String getFmPic() {
		return fmPic;
	}

	public void setFmPic(String fmPic) {
		this.fmPic = fmPic;
	}

	public BigDecimal getFmGi() {
		return fmGi;
	}

	public void setFmGi(BigDecimal fmGi) {
		this.fmGi = fmGi;
	}

	public String getFmProtidFlag() {
		return fmProtidFlag;
	}

	public void setFmProtidFlag(String fmProtidFlag) {
		this.fmProtidFlag = fmProtidFlag;
	}

	public String getFmFatType() {
		return fmFatType;
	}

	public void setFmFatType(String fmFatType) {
		this.fmFatType = fmFatType;
	}

	public String getFmType() {
		return fmType;
	}

	public void setFmType(String fmType) {
		this.fmType = fmType;
	}

	public List<NutrientAmountPojo> getProductElementList() {
		return productElementList;
	}

	public void setProductElementList(List<NutrientAmountPojo> productElementList) {
		this.productElementList = productElementList;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public BigDecimal getFmPurin() {
		return fmPurin;
	}

	public void setFmPurin(BigDecimal fmPurin) {
		this.fmPurin = fmPurin;
	}

	public Integer getFmEsculent() {
		return fmEsculent;
	}

	public void setFmEsculent(Integer fmEsculent) {
		this.fmEsculent = fmEsculent;
	}

	public BigDecimal getFmWater() {
		return fmWater;
	}

	public void setFmWater(BigDecimal fmWater) {
		this.fmWater = fmWater;
	}

	public BigDecimal getFmAsh() {
		return fmAsh;
	}

	public void setFmAsh(BigDecimal fmAsh) {
		this.fmAsh = fmAsh;
	}

}
