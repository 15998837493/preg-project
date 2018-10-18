
package com.mnt.preg.master.entity.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 体检项目字典表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-5 gss 初版
 */
@Entity
@Table(name = "mas_item")
public class Item extends MappedEntity {

    private static final long serialVersionUID = 6374057429745991593L;

    /** 项目主键 */
    @UpdateAnnotation
    private String itemId;

    /** 项目编号 */
    @UpdateAnnotation
    private String itemCode;

    /** 项目名称 */
    @UpdateAnnotation
    private String itemName;

    /** 检验项目分类（大） */
    @UpdateAnnotation
    private String itemType;

    /** 检验项目分类（小） */
    @UpdateAnnotation
    private String itemClassify;

    /** 参考值文本型 */
    @UpdateAnnotation
    private String itemRefValue;

    /** 项目标准单位 */
    @UpdateAnnotation
    private String itemUnit;

    /** 是否对比 否;1=是 */
    @UpdateAnnotation
    private Integer isContrast;

    /** 是否重要 1：是 ，0：否 */
    @UpdateAnnotation
    private Integer isMain;

    /** 是否分析指标异常 */
    @QueryFieldAnnotation
    private String itemAnalyze;

    /** 排序 */
    @UpdateAnnotation
    private Integer itemOrder;

    /** 适宜性别 */
    @UpdateAnnotation
    private String itemSex;

    /** 结论算法 */
    @UpdateAnnotation
    private String resultJson;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "item_code")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name = "item_unit")
    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    @Column(name = "item_ref_value")
    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    @Column(name = "is_contrast")
    public Integer getIsContrast() {
        return isContrast;
    }

    public void setIsContrast(Integer isContrast) {
        this.isContrast = isContrast;
    }

    @Column(name = "is_main")
    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    @Column(name = "item_type")
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Column(name = "item_classify")
    public String getItemClassify() {
        return itemClassify;
    }

    public void setItemClassify(String itemClassify) {
        this.itemClassify = itemClassify;
    }

    @Column(name = "item_order")
    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    @Column(name = "result_json")
    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    @Column(name = "item_sex")
    public String getItemSex() {
        return itemSex;
    }

    public void setItemSex(String itemSex) {
        this.itemSex = itemSex;
    }

}
