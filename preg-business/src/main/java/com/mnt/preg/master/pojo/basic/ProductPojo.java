
package com.mnt.preg.master.pojo.basic;

import java.math.BigDecimal;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

public class ProductPojo extends MappedEntity {

	private static final long serialVersionUID = 6091020302197444285L;

	/** 商品编码 */
	@QueryFieldAnnotation
	private String productId;

	/** 商品代号 */
	@QueryFieldAnnotation
	private String productCode;

	/** 商品类别 */
	@QueryFieldAnnotation
	private String productCategory;

	/** 产品品名 */
	@QueryFieldAnnotation
	private String productName;

	/** 产品图片 */
	@QueryFieldAnnotation
	private String productPic;

	/** 产品助记符 */
	@QueryFieldAnnotation
	private String productMemoryMark;

	/** 商品名称（用于销售显示） */
	@QueryFieldAnnotation
	private String productGoodsName;

	/** 通用名称 */
	@QueryFieldAnnotation
	private String productCommonName;

	/** 使用属性 */
	@QueryFieldAnnotation
	private String productAttribute;

	/** 商品缩拼 */
	@QueryFieldAnnotation
	private String productAbPinyin;

	/** 商品全拼 */
	@QueryFieldAnnotation
	private String productFullPinyin;

	/** 是否药局处方 */
	@QueryFieldAnnotation
	private String productIsOfficina;

	/** 是否剂量评估 */
	@QueryFieldAnnotation
	private String productIsAssess;

	/** 是否能量评估 */
	@QueryFieldAnnotation
	private String productIsEnergy;

	/** 商品图标（暂不维护） */
	@QueryFieldAnnotation
	private String productIcon;

	/** 商品条码（暂不维护） */
	@QueryFieldAnnotation
	private String productBarcode;

	/** 商品地区 */
	@QueryFieldAnnotation
	private String productArea;

	/** 剂量单位 */
	@QueryFieldAnnotation
	private String productUnit;

	/** 剂量说明 */
	@QueryFieldAnnotation
	private String productDosageDesc;

	/** 单次剂量 */
	@QueryFieldAnnotation
	private BigDecimal productDosage;

	/** 用药方法 */
	@QueryFieldAnnotation
	private String productUsageMethod;

	/** 用药频次 */
	@QueryFieldAnnotation
	private String productFrequency;

	/** 整包单位 */
	@QueryFieldAnnotation
	private String productPackageUnit;

	/** 商品规格 */
	@QueryFieldAnnotation
	private String productStandard;

	/** 用药疗程 */
	@QueryFieldAnnotation
	private String productTreatment;

	/** 可用天数 */
	@QueryFieldAnnotation
	private String productDays;

	/** 成本价(单位：分) */
	@QueryFieldAnnotation
	private Integer productPurchasePrice;

	/** 销售价(单位：分) */
	@QueryFieldAnnotation
	private Integer productSellPrice;

	/** 商品品牌 */
	@QueryFieldAnnotation
	private String productBrand;

	/** 批准文号 */
	@QueryFieldAnnotation
	private String productLicense;

	/** 过敏源 */
	@QueryFieldAnnotation
	private String productAllergy;

	/** 用法用量（使用方法） */
	@QueryFieldAnnotation
	private String productUsage;

	/** 适合人群 */
	@QueryFieldAnnotation
	private String productUser;

	/** 注意事项 */
	@QueryFieldAnnotation
	private String productMatters;

	/** 功效说明 */
	@QueryFieldAnnotation
	private String productEffect;

	/** 供货商（暂不维护） */
	@QueryFieldAnnotation
	private String productSupplier;

	/** 商品积分（暂不维护） */
	@QueryFieldAnnotation
	private Integer productScore;

	/** 淘宝链接（暂不维护） */
	@QueryFieldAnnotation
	private String productTaobao;

	/** 商品相册（暂不维护） */
	@QueryFieldAnnotation
	private String productImages;

	/** 静态页面（暂不维护） */
	@QueryFieldAnnotation
	private String productPage;

	/** 商品类别名 */
	private String productCategoryName;

	/** 商品元素列表 */
	private List<NutrientAmountPojo> productElementList;

	/** 是否参与计算 */
	@QueryFieldAnnotation
	private int productCalculation;

	/*
	 * 以下五个字段为膳食模板、和（？）所需 update by wsy at 2017-2-25
	 */
	/** 每单位量（换算关系g） */
	private BigDecimal unitAmount = new BigDecimal(0);

	/** 每份热量 */
	private BigDecimal unitEnergy = new BigDecimal(0);

	/** 每份碳水化合物 */
	private BigDecimal unitCbr = new BigDecimal(0);

	/** 每份蛋白质 */
	private BigDecimal unitProtein = new BigDecimal(0);

	/** 每份脂肪 */
	private BigDecimal unitFat = new BigDecimal(0);

	public BigDecimal getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(BigDecimal unitAmount) {
		this.unitAmount = unitAmount;
	}

	public BigDecimal getUnitEnergy() {
		return unitEnergy;
	}

	public void setUnitEnergy(BigDecimal unitEnergy) {
		this.unitEnergy = unitEnergy;
	}

	public BigDecimal getUnitCbr() {
		return unitCbr;
	}

	public void setUnitCbr(BigDecimal unitCbr) {
		this.unitCbr = unitCbr;
	}

	public BigDecimal getUnitProtein() {
		return unitProtein;
	}

	public void setUnitProtein(BigDecimal unitProtein) {
		this.unitProtein = unitProtein;
	}

	public BigDecimal getUnitFat() {
		return unitFat;
	}

	public void setUnitFat(BigDecimal unitFat) {
		this.unitFat = unitFat;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
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

	public String getProductUsage() {
		return productUsage;
	}

	public void setProductUsage(String productUsage) {
		this.productUsage = productUsage;
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

	public Integer getProductPurchasePrice() {
		return productPurchasePrice;
	}

	public void setProductPurchasePrice(Integer productPurchasePrice) {
		this.productPurchasePrice = productPurchasePrice;
	}

	public Integer getProductSellPrice() {
		return productSellPrice;
	}

	public void setProductSellPrice(Integer productSellPrice) {
		this.productSellPrice = productSellPrice;
	}

	public String getProductSupplier() {
		return productSupplier;
	}

	public void setProductSupplier(String productSupplier) {
		this.productSupplier = productSupplier;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public Integer getProductScore() {
		return productScore;
	}

	public void setProductScore(Integer productScore) {
		this.productScore = productScore;
	}

	public String getProductLicense() {
		return productLicense;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
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

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
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

	public String getProductDosageDesc() {
		return productDosageDesc;
	}

	public void setProductDosageDesc(String productDosageDesc) {
		this.productDosageDesc = productDosageDesc;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public String getProductIsEnergy() {
		return productIsEnergy;
	}

	public void setProductIsEnergy(String productIsEnergy) {
		this.productIsEnergy = productIsEnergy;
	}

	public String getProductAllergy() {
		return productAllergy;
	}

	public void setProductAllergy(String productAllergy) {
		this.productAllergy = productAllergy;
	}

	public String getProductUser() {
		return productUser;
	}

	public void setProductUser(String productUser) {
		this.productUser = productUser;
	}

	public List<NutrientAmountPojo> getProductElementList() {
		return productElementList;
	}

	public void setProductElementList(List<NutrientAmountPojo> productElementList) {
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
