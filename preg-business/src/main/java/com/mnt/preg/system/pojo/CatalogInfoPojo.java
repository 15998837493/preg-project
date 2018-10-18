/*
 * MasCatalogPojo.java    1.0  2017-10-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;

/**
 * 类别管理Pojo
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 scd 初版
 */
public class CatalogInfoPojo implements Serializable {

    private static final long serialVersionUID = 8212652350516341096L;

    /** 类别主键 */
    @QueryFieldAnnotation
    private String catalogId;

    /** 类别分类 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String catalogType;

    /** 类别名称 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String catalogName;

    /** 类别父节点主键 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String catalogParentId;

    /** 类别排序 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    private Integer catalogOrder;

    /** 子级菜单 */
    private List<CatalogInfoPojo> childList;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
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

    public List<CatalogInfoPojo> getChildList() {
        return childList;
    }

    public void setChildList(List<CatalogInfoPojo> childList) {
        this.childList = childList;
    }

}
