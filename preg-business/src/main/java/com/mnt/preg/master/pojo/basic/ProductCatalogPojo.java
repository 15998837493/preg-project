
package com.mnt.preg.master.pojo.basic;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

public class ProductCatalogPojo implements Serializable {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 类别编码 */
    @QueryFieldAnnotation
    private String catalogId;

    /** 类别名称 */
    @QueryFieldAnnotation
    private String catalogName;

    /** 类别拼音 */
    @QueryFieldAnnotation
    private String catalogPinyin;

    /** 类别父节点编码 */
    @QueryFieldAnnotation
    private String catalogParentId;

    /** 交换份单位 */
    @QueryFieldAnnotation
    private Integer catalogOrder;

    /** 子级菜单 */
    private List<ProductCatalogPojo> childList;

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

    public List<ProductCatalogPojo> getChildList() {
        return childList;
    }

    public void setChildList(List<ProductCatalogPojo> childList) {
        this.childList = childList;
    }

    /*
     * public List<ProductCatalogVo> getChildList() {
     * return childList;
     * }
     * 
     * public void setChildList(List<ProductCatalogVo> childList) {
     * this.childList = childList;
     * }
     */

}
