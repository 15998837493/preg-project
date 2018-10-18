/*
 * MasCatalog.java    1.0  2017-10-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 类别管理
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 scd 初版
 */
@Entity
@Table(name = "mas_catalog")
public class CatalogInfo extends MappedEntity {

    private static final long serialVersionUID = 8316847946660730206L;

    /** 类别主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String catalogId;

    /** 类别分类 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String catalogType;

    /** 类别名称 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String catalogName;

    /** 类别父节点主键 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String catalogParentId;

    /** 类别排序 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, order = OrderConstants.ASC)
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

    @Column(name = "catalog_type")
    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    @Column(name = "catalog_name")
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
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
