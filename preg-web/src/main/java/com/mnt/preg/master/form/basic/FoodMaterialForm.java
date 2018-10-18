
package com.mnt.preg.master.form.basic;

import java.math.BigDecimal;
import java.util.List;

import com.mnt.preg.master.pojo.basic.NutrientAmountPojo;

/**
 * 食材原料表单数据
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-2-3 scd 初版
 */
public class FoodMaterialForm {

	/** 食材ID */
	private String fmId;

	/** 食材名称 */
	private String fmName;

	/** 食品别名,号分割 */
	private String fmAlias;

	/** 图片 */
	private String fmPic;

	/** 图片原名称 */
	private String fmPicOld;

	/** GI值 */
	private BigDecimal fmGi;

	/** 是否属于优质蛋白质 */
	private String fmProtidFlag;

	/** 脂肪类型plant=植物，animal=动物 */
	private String fmFatType;

	/** 食材类型主键 */
	private String fmType;

	/** 嘌呤 */
	private BigDecimal fmPurin;

	/** 可食用部分 */
	private Integer fmEsculent;

	/** 水分 */
	private BigDecimal fmWater;

	/** 灰分 */
	private BigDecimal fmAsh;

	/** 保存元素 */
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

	public String getFmPicOld() {
		return fmPicOld;
	}

	public void setFmPicOld(String fmPicOld) {
		this.fmPicOld = fmPicOld;
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

	public List<NutrientAmountPojo> getProductElementList() {
		return productElementList;
	}

	public void setProductElementList(List<NutrientAmountPojo> productElementList) {
		this.productElementList = productElementList;
	}

}
