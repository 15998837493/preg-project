/*
 * CatalogInfoForm.java    1.0  2017-10-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.form;

/**
 * 类别管理form
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-20 scd 初版
 */
public class CatalogInfoForm {

    /** 类别主键 */
    private String catalogId;

    /** 类别分类 */
    private String catalogType;

    /** 类别名称 */
    private String catalogName;

    /** 类别父节点主键 */
    private String catalogParentId;

    /** 类别排序 */
    private Integer catalogOrder;

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

}
