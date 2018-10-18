
package com.mnt.preg.master.form.basic;

import java.math.BigDecimal;
import java.util.List;

import com.mnt.preg.master.entity.basic.NutrientAmount;

/**
 * 
 * 商品库表单
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-5-20 gss 初版
 */
public class ProductForm {

	/** 商品编码 */
	private String productId;

	/** 商品代号 */
	private String productCode;

	/** 商品类别 */
	private String productCategory;

	/** 产品品名 */
	private String productPic;

	/** 产品品名 */
	private String productName;

	/** 产品助记符 */
	private String productMemoryMark;

	/** 商品名称（用于销售显示） */
	private String productGoodsName;

	/** 通用名称 */
	private String productCommonName;

	/** 使用属性 */
	private String productAttribute;

	/** 商品缩拼 */
	private String productAbPinyin;

	/** 商品全拼 */
	private String productFullPinyin;

	/** 是否药局处方 */
	private String productIsOfficina;

	/** 是否剂量评估 */
	private String productIsAssess;

	/** 是否能量评估 */
	private String productIsEnergy;

	/** 商品图标（暂不维护） */
	private String productIcon;

	/** 商品条码（暂不维护） */
	private String productBarcode;

	/** 商品地区 */
	private String productArea;

	/** 剂量单位 */
	private String productUnit;

	/** 剂量说明 */
	private String productDosageDesc;

	/** 单次剂量 */
	private BigDecimal productDosage;

	/** 用药方法 */
	private String productUsageMethod;

	/** 用药频次 */
	private String productFrequency;

	/** 整包单位 */
	private String productPackageUnit;

	/** 商品规格 */
	private String productStandard;

	/** 用药疗程 */
	private String productTreatment;

	/** 可用天数 */
	private String productDays;

	/** 成本价(单位：分) */
	private String productPurchasePricestr;

	/** 销售价(单位：分) */
	private String productSellPricestr;

	/** 商品品牌 */
	private String productBrand;

	/** 批准文号 */
	private String productLicense;

	/** 过敏源 */
	private String productAllergy;

	/** 用法用量（使用方法） */
	private String productUsage;

	/** 适合人群 */
	private String productUser;

	/** 注意事项 */
	private String productMatters;

	/** 功效说明 */
	private String productEffect;

	/** 供货商（暂不维护） */
	private String productSupplier;

	/** 商品积分（暂不维护） */
	private Integer productScore;

	/** 淘宝链接（暂不维护） */
	private String productTaobao;

	/** 商品相册（暂不维护） */
	private String productImages;

	/** 静态页面（暂不维护） */
	private String productPage;

	/** 保存元素 */
	private List<NutrientAmount> productElementList;

	/** 是否参与计算 */
	private int productCalculation;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductGoodsName() {
		return productGoodsName;
	}

	public void setProductGoodsName(String productGoodsName) {
		this.productGoodsName = productGoodsName;
	}

	public String getProductCommonName() {
		return productCommonName;
	}

	public void setProductCommonName(String productCommonName) {
		this.productCommonName = productCommonName;
	}

	public String getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(String productAttribute) {
		this.productAttribute = productAttribute;
	}

	public String getProductAbPinyin() {
		return productAbPinyin;
	}

	public void setProductAbPinyin(String productAbPinyin) {
		this.productAbPinyin = productAbPinyin;
	}

	public String getProductFullPinyin() {
		return productFullPinyin;
	}

	public void setProductFullPinyin(String productFullPinyin) {
		this.productFullPinyin = productFullPinyin;
	}

	public String getProductIsOfficina() {
		return productIsOfficina;
	}

	public void setProductIsOfficina(String productIsOfficina) {
		this.productIsOfficina = productIsOfficina;
	}

	public String getProductIsAssess() {
		return productIsAssess;
	}

	public void setProductIsAssess(String productIsAssess) {
		this.productIsAssess = productIsAssess;
	}

	public String getProductIsEnergy() {
		return productIsEnergy;
	}

	public void setProductIsEnergy(String productIsEnergy) {
		this.productIsEnergy = productIsEnergy;
	}

	public String getProductIcon() {
		return productIcon;
	}

	public void setProductIcon(String productIcon) {
		this.productIcon = productIcon;
	}

	public String getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getProductDosageDesc() {
		return productDosageDesc;
	}

	public void setProductDosageDesc(String productDosageDesc) {
		this.productDosageDesc = productDosageDesc;
	}

	public String getProductUsageMethod() {
		return productUsageMethod;
	}

	public void setProductUsageMethod(String productUsageMethod) {
		this.productUsageMethod = productUsageMethod;
	}

	public String getProductFrequency() {
		return productFrequency;
	}

	public void setProductFrequency(String productFrequency) {
		this.productFrequency = productFrequency;
	}

	public String getProductPackageUnit() {
		return productPackageUnit;
	}

	public void setProductPackageUnit(String productPackageUnit) {
		this.productPackageUnit = productPackageUnit;
	}

	public String getProductStandard() {
		return productStandard;
	}

	public void setProductStandard(String productStandard) {
		this.productStandard = productStandard;
	}

	public String getProductTreatment() {
		return productTreatment;
	}

	public void setProductTreatment(String productTreatment) {
		this.productTreatment = productTreatment;
	}

	public String getProductDays() {
		return productDays;
	}

	public void setProductDays(String productDays) {
		this.productDays = productDays;
	}

	public String getProductPurchasePricestr() {
		return productPurchasePricestr;
	}

	public void setProductPurchasePricestr(String productPurchasePricestr) {
		this.productPurchasePricestr = productPurchasePricestr;
	}

	public String getProductSellPricestr() {
		return productSellPricestr;
	}

	public void setProductSellPricestr(String productSellPricestr) {
		this.productSellPricestr = productSellPricestr;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductLicense() {
		return productLicense;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
	}

	public String getProductAllergy() {
		return productAllergy;
	}

	public void setProductAllergy(String productAllergy) {
		this.productAllergy = productAllergy;
	}

	public String getProductUsage() {
		return productUsage;
	}

	public void setProductUsage(String productUsage) {
		this.productUsage = productUsage;
	}

	public String getProductUser() {
		return productUser;
	}

	public void setProductUser(String productUser) {
		this.productUser = productUser;
	}

	public String getProductMatters() {
		return productMatters;
	}

	public void setProductMatters(String productMatters) {
		this.productMatters = productMatters;
	}

	public String getProductEffect() {
		return productEffect;
	}

	public void setProductEffect(String productEffect) {
		this.productEffect = productEffect;
	}

	public String getProductSupplier() {
		return productSupplier;
	}

	public void setProductSupplier(String productSupplier) {
		this.productSupplier = productSupplier;
	}

	public Integer getProductScore() {
		return productScore;
	}

	public void setProductScore(Integer productScore) {
		this.productScore = productScore;
	}

	public String getProductTaobao() {
		return productTaobao;
	}

	public void setProductTaobao(String productTaobao) {
		this.productTaobao = productTaobao;
	}

	public String getProductImages() {
		return productImages;
	}

	public void setProductImages(String productImages) {
		this.productImages = productImages;
	}

	public String getProductPage() {
		return productPage;
	}

	public void setProductPage(String productPage) {
		this.productPage = productPage;
	}

	public List<NutrientAmount> getProductElementList() {
		return productElementList;
	}

	public void setProductElementList(List<NutrientAmount> productElementList) {
		this.productElementList = productElementList;
	}

	public String getProductMemoryMark() {
		return productMemoryMark;
	}

	public void setProductMemoryMark(String productMemoryMark) {
		this.productMemoryMark = productMemoryMark;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public BigDecimal getProductDosage() {
		return productDosage;
	}

	public void setProductDosage(BigDecimal productDosage) {
		this.productDosage = productDosage;
	}

	public int getProductCalculation() {
		return productCalculation;
	}

	public void setProductCalculation(int productCalculation) {
		this.productCalculation = productCalculation;
	}

}
