
package com.mnt.preg.master.form.basic;

/**
 * 
 * 商品类别
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-12 gss 初版
 */
public class ProductCatalogForm {

    /** 类别编码 */
    private String catalogId;

    /** 类别名称 */

    private String catalogName;

    /** 类别拼音 */

    private String catalogPinyin;

    /** 类别父节点编码 */

    private String catalogParentId;

    /** 交换份单位 */

    private Integer catalogOrder;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogPinyin() {
        return catalogPinyin;
    }

    public void setCatalogPinyin(String catalogPinyin) {
        this.catalogPinyin = catalogPinyin;
    }

    public String getCatalogParentId() {
        return catalogParentId;
    }

    public void setCatalogParentId(String catalogParentId) {
        this.catalogParentId = catalogParentId;
    }

    public Integer getCatalogOrder() {
        return catalogOrder;
    }

    public void setCatalogOrder(Integer catalogOrder) {
        this.catalogOrder = catalogOrder;
    }

}
