
package com.mnt.preg.master.entity.basic;

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

@Entity
@Table(name = "mas_product_catalog")
public class ProductCatalog extends MappedEntity {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 类别编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String catalogId;

    /** 类别名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String catalogName;

    /** 类别拼音 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String catalogPinyin;

    /** 类别父节点编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String catalogParentId;

    /** 交换份单位 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private Integer catalogOrder;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "catalog_id")
    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Column(name = "catalog_name")
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Column(name = "catalog_pinyin")
    public String getCatalogPinyin() {
        return catalogPinyin;
    }

    public void setCatalogPinyin(String catalogPinyin) {
        this.catalogPinyin = catalogPinyin;
    }

    @Column(name = "catalog_parent_id")
    public String getCatalogParentId() {
        return catalogParentId;
    }

    public void setCatalogParentId(String catalogParentId) {
        this.catalogParentId = catalogParentId;
    }

    @Column(name = "catalog_order")
    public Integer getCatalogOrder() {
        return catalogOrder;
    }

    public void setCatalogOrder(Integer catalogOrder) {
        this.catalogOrder = catalogOrder;
    }

}
