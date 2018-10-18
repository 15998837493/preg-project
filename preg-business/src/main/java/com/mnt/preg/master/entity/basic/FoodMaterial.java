/*
 * FoodMaterial.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.entity.basic;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 食材(原材料)信息表
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历： v1.0 2018-1-25 mnt115 初版
 */
@Entity
@Table(name = "mas_food_material")
public class FoodMaterial extends MappedEntity {

	private static final long serialVersionUID = -8904855642056131700L;

	// 主键
	@QueryConditionAnnotation(symbol = SymbolConstants.EQ)
	private String fmId;

	// 食材名称
	@UpdateAnnotation
	@QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
	private String fmName;

	// 食品别名,号分割
	@UpdateAnnotation
	private String fmAlias;

	// 食材全拼音
	@UpdateAnnotation
	private String fmASpell;

	// 食材缩写拼音fm_sspell
	@UpdateAnnotation
	private String fmSSpell;

	// 图片
	@UpdateAnnotation
	private String fmPic;

	// 嘌呤
	@UpdateAnnotation
	private BigDecimal fmPurin;

	// GI值
	@UpdateAnnotation
	private BigDecimal fmGi;

	// 是否属于优质蛋白质
	@UpdateAnnotation
	private String fmProtidFlag;

	// 脂肪类型plant=植物，animal=动物
	@UpdateAnnotation
	private String fmFatType;

	// 食材类型主键
	@UpdateAnnotation
	@QueryConditionAnnotation(symbol = SymbolConstants.EQ)
	private String fmType;

	// 可食用部分
	@UpdateAnnotation
	private Integer fmEsculent;

	// 水分
	@UpdateAnnotation
	private BigDecimal fmWater;

	// 灰分
	@UpdateAnnotation
	private BigDecimal fmAsh;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "fm_id")
	public String getFmId() {
		return fmId;
	}

	public void setFmId(String fmId) {
		this.fmId = fmId;
	}

	@Column(name = "fm_name")
	public String getFmName() {
		return fmName;
	}

	public void setFmName(String fmName) {
		this.fmName = fmName;
	}

	@Column(name = "fm_alias")
	public String getFmAlias() {
		return fmAlias;
	}

	public void setFmAlias(String fmAlias) {
		this.fmAlias = fmAlias;
	}

	@Column(name = "fm_aspell")
	public String getFmASpell() {
		return fmASpell;
	}

	public void setFmASpell(String fmASpell) {
		this.fmASpell = fmASpell;
	}

	@Column(name = "fm_sspell")
	public String getFmSSpell() {
		return fmSSpell;
	}

	public void setFmSSpell(String fmSSpell) {
		this.fmSSpell = fmSSpell;
	}

	@Column(name = "fm_pic")
	public String getFmPic() {
		return fmPic;
	}

	public void setFmPic(String fmPic) {
		this.fmPic = fmPic;
	}

	@Column(name = "fm_gi")
	public BigDecimal getFmGi() {
		return fmGi;
	}

	public void setFmGi(BigDecimal fmGi) {
		this.fmGi = fmGi;
	}

	@Column(name = "fm_protid_flag")
	public String getFmProtidFlag() {
		return fmProtidFlag;
	}

	public void setFmProtidFlag(String fmProtidFlag) {
		this.fmProtidFlag = fmProtidFlag;
	}

	@Column(name = "fm_fat_type")
	public String getFmFatType() {
		return fmFatType;
	}

	public void setFmFatType(String fmFatType) {
		this.fmFatType = fmFatType;
	}

	@Column(name = "fm_type")
	public String getFmType() {
		return fmType;
	}

	public void setFmType(String fmType) {
		this.fmType = fmType;
	}

	@Column(name = "fm_purin")
	public BigDecimal getFmPurin() {
		return fmPurin;
	}

	public void setFmPurin(BigDecimal fmPurin) {
		this.fmPurin = fmPurin;
	}

	@Column(name = "fm_esculent")
	public Integer getFmEsculent() {
		return fmEsculent;
	}

	public void setFmEsculent(Integer fmEsculent) {
		this.fmEsculent = fmEsculent;
	}

	@Column(name = "fm_water")
	public BigDecimal getFmWater() {
		return fmWater;
	}

	public void setFmWater(BigDecimal fmWater) {
		this.fmWater = fmWater;
	}

	@Column(name = "fm_ash")
	public BigDecimal getFmAsh() {
		return fmAsh;
	}

	public void setFmAsh(BigDecimal fmAsh) {
		this.fmAsh = fmAsh;
	}

}
