
package com.mnt.preg.master.entity.basic;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 商品库表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */

@Entity
@Table(name = "mas_product")
public class Product extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 商品编码 */
    private String productId;

    /** 商品代号 */
    @UpdateAnnotation
    private String productCode;

    /** 商品类别 */
    @UpdateAnnotation
    private String productCategory;

    /** 产品品名 */
    @UpdateAnnotation
    private String productName;

    /** 产品图片 */
    @UpdateAnnotation
    private String productPic;

    /** 产品助记符 */
    @UpdateAnnotation
    private String productMemoryMark;

    /** 商品名称（用于销售显示） */
    @UpdateAnnotation
    private String productGoodsName;

    /** 通用名称 */
    @UpdateAnnotation
    private String productCommonName;

    /** 使用属性 */
    @UpdateAnnotation
    private String productAttribute;

    /** 商品缩拼 */
    @UpdateAnnotation
    private String productAbPinyin;

    /** 商品全拼 */
    @UpdateAnnotation
    private String productFullPinyin;

    /** 是否药局处方 */
    @UpdateAnnotation
    private String productIsOfficina;

    /** 是否剂量评估 */
    @UpdateAnnotation
    private String productIsAssess;

    /** 是否能量评估 */
    @UpdateAnnotation
    private String productIsEnergy;

    /** 商品图标（暂不维护） */
    @UpdateAnnotation
    private String productIcon;

    /** 商品条码（暂不维护） */
    @UpdateAnnotation
    private String productBarcode;

    /** 商品地区 */
    @UpdateAnnotation
    private String productArea;

    /** 剂量单位 */
    @UpdateAnnotation
    private String productUnit;

    /** 剂量说明 */
    @UpdateAnnotation
    private String productDosageDesc;

    /** 单次剂量 */
    @UpdateAnnotation
    private BigDecimal productDosage;

    /** 用药方法 */
    @UpdateAnnotation
    private String productUsageMethod;

    /** 用药频次 */
    @UpdateAnnotation
    private String productFrequency;

    /** 整包单位 */
    @UpdateAnnotation
    private String productPackageUnit;

    /** 商品规格 */
    @UpdateAnnotation
    private String productStandard;

    /** 用药疗程 */
    @UpdateAnnotation
    private String productTreatment;

    /** 可用天数 */
    @UpdateAnnotation
    private String productDays;

    /** 成本价(单位：分) */
    @UpdateAnnotation
    private Integer productPurchasePrice;

    /** 销售价(单位：分) */
    @UpdateAnnotation
    private Integer productSellPrice;

    /** 商品品牌 */
    @UpdateAnnotation
    private String productBrand;

    /** 批准文号 */
    @UpdateAnnotation
    private String productLicense;

    /** 过敏源 */
    @UpdateAnnotation
    private String productAllergy;

    /** 用法用量（使用方法） */
    @UpdateAnnotation
    private String productUsage;

    /** 适合人群 */
    @UpdateAnnotation
    private String productUser;

    /** 注意事项 */
    @UpdateAnnotation
    private String productMatters;

    /** 功效说明 */
    @UpdateAnnotation
    private String productEffect;

    /** 供货商（暂不维护） */
    @UpdateAnnotation
    private String productSupplier;

    /** 商品积分（暂不维护） */
    @UpdateAnnotation
    private Integer productScore;

    /** 淘宝链接（暂不维护） */
    @UpdateAnnotation
    private String productTaobao;

    /** 商品相册（暂不维护） */
    @UpdateAnnotation
    private String productImages;

    /** 静态页面（暂不维护） */
    @UpdateAnnotation
    private String productPage;
    
    /** 是否参与计算*/
    @UpdateAnnotation
    private int productCalculation;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "product_icon")
    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    @Column(name = "product_category")
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    @Column(name = "product_standard")
    public String getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }

    @Column(name = "product_unit")
    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    @Column(name = "product_usage")
    public String getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(String productUsage) {
        this.productUsage = productUsage;
    }

    @Column(name = "product_frequency")
    public String getProductFrequency() {
        return productFrequency;
    }

    public void setProductFrequency(String productFrequency) {
        this.productFrequency = productFrequency;
    }

    @Column(name = "product_treatment")
    public String getProductTreatment() {
        return productTreatment;
    }

    public void setProductTreatment(String productTreatment) {
        this.productTreatment = productTreatment;
    }

    @Column(name = "product_package_unit")
    public String getProductPackageUnit() {
        return productPackageUnit;
    }

    public void setProductPackageUnit(String productPackageUnit) {
        this.productPackageUnit = productPackageUnit;
    }

    @Column(name = "product_images")
    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    @Column(name = "product_page")
    public String getProductPage() {
        return productPage;
    }

    public void setProductPage(String productPage) {
        this.productPage = productPage;
    }

    @Column(name = "product_effect")
    public String getProductEffect() {
        return productEffect;
    }

    public void setProductEffect(String productEffect) {
        this.productEffect = productEffect;
    }

    @Column(name = "product_taobao")
    public String getProductTaobao() {
        return productTaobao;
    }

    public void setProductTaobao(String productTaobao) {
        this.productTaobao = productTaobao;
    }

    @Column(name = "product_barcode")
    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    @Column(name = "product_goods_name")
    public String getProductGoodsName() {
        return productGoodsName;
    }

    public void setProductGoodsName(String productGoodsName) {
        this.productGoodsName = productGoodsName;
    }

    @Column(name = "product_ab_pinyin")
    public String getProductAbPinyin() {
        return productAbPinyin;
    }

    public void setProductAbPinyin(String productAbPinyin) {
        this.productAbPinyin = productAbPinyin;
    }

    @Column(name = "product_full_pinyin")
    public String getProductFullPinyin() {
        return productFullPinyin;
    }

    public void setProductFullPinyin(String productFullPinyin) {
        this.productFullPinyin = productFullPinyin;
    }

    @Column(name = "product_usage_method")
    public String getProductUsageMethod() {
        return productUsageMethod;
    }

    public void setProductUsageMethod(String productUsageMethod) {
        this.productUsageMethod = productUsageMethod;
    }

    @Column(name = "product_days")
    public String getProductDays() {
        return productDays;
    }

    public void setProductDays(String productDays) {
        this.productDays = productDays;
    }

    @Column(name = "product_matters")
    public String getProductMatters() {
        return productMatters;
    }

    public void setProductMatters(String productMatters) {
        this.productMatters = productMatters;
    }

    @Column(name = "product_purchase_price")
    public Integer getProductPurchasePrice() {
        return productPurchasePrice;
    }

    public void setProductPurchasePrice(Integer productPurchasePrice) {
        this.productPurchasePrice = productPurchasePrice;
    }

    @Column(name = "product_sell_price")
    public Integer getProductSellPrice() {
        return productSellPrice;
    }

    public void setProductSellPrice(Integer productSellPrice) {
        this.productSellPrice = productSellPrice;
    }

    @Column(name = "product_supplier")
    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

    @Column(name = "product_brand")
    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    @Column(name = "product_score")
    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    @Column(name = "product_license")
    public String getProductLicense() {
        return productLicense;
    }

    public void setProductLicense(String productLicense) {
        this.productLicense = productLicense;
    }

    @Column(name = "product_area")
    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    @Column(name = "product_is_officina")
    public String getProductIsOfficina() {
        return productIsOfficina;
    }

    public void setProductIsOfficina(String productIsOfficina) {
        this.productIsOfficina = productIsOfficina;
    }

    @Column(name = "product_is_assess")
    public String getProductIsAssess() {
        return productIsAssess;
    }

    public void setProductIsAssess(String productIsAssess) {
        this.productIsAssess = productIsAssess;
    }

    @Column(name = "product_dosage_desc")
    public String getProductDosageDesc() {
        return productDosageDesc;
    }

    public void setProductDosageDesc(String productDosageDesc) {
        this.productDosageDesc = productDosageDesc;
    }

    @Column(name = "product_code")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "product_common_name")
    public String getProductCommonName() {
        return productCommonName;
    }

    public void setProductCommonName(String productCommonName) {
        this.productCommonName = productCommonName;
    }

    @Column(name = "product_attribute")
    public String getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute;
    }

    @Column(name = "product_is_energy")
    public String getProductIsEnergy() {
        return productIsEnergy;
    }

    public void setProductIsEnergy(String productIsEnergy) {
        this.productIsEnergy = productIsEnergy;
    }

    @Column(name = "product_allergy")
    public String getProductAllergy() {
        return productAllergy;
    }

    public void setProductAllergy(String productAllergy) {
        this.productAllergy = productAllergy;
    }

    @Column(name = "product_user")
    public String getProductUser() {
        return productUser;
    }

    public void setProductUser(String productUser) {
        this.productUser = productUser;
    }

    @Column(name = "product_memory_mark")
    public String getProductMemoryMark() {
        return productMemoryMark;
    }

    public void setProductMemoryMark(String productMemoryMark) {
        this.productMemoryMark = productMemoryMark;
    }

    @Column(name = "product_pic")
    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    @Column(name = "product_dosage")
    public BigDecimal getProductDosage() {
        return productDosage;
    }

    public void setProductDosage(BigDecimal productDosage) {
        this.productDosage = productDosage;
    }

    @Column(name = "product_calculation")
	public int getProductCalculation() {
		return productCalculation;
	}

	public void setProductCalculation(int productCalculation) {
		this.productCalculation = productCalculation;
	}

}
