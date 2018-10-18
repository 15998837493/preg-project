
package com.mnt.preg.master.condition.items;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 
 * * 第三方体检项目字典检索条件
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-7 gss 初版
 */
public class ItemCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 项目ID */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemId;

    /** 项目ID */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "itemId")
    private List<String> itemIdList;

    /** 项目编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "itemCode")
    private List<String> itemCodeList;

    /** 项目名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String itemName;

    /** 项目分类（大） */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemType;

    /** 项目分类（小） */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemClassify;

    /** 排序 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    private String itemOrder;

    /** 诊断项目 */
    private String diseaseCode;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public List<String> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<String> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public List<String> getItemCodeList() {
        return itemCodeList;
    }

    public void setItemCodeList(List<String> itemCodeList) {
        this.itemCodeList = itemCodeList;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemClassify() {
        return itemClassify;
    }

    public void setItemClassify(String itemClassify) {
        this.itemClassify = itemClassify;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }

}
