
package com.mnt.preg.master.form.items;

/**
 * 
 * 项目字典表提交表单
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-8 gss 初版
 */
public class ItemForm {

    /** 项目主键 */
    private String itemId;

    /** 项目编号 */
    private String itemCode;

    /** 项目名称 */
    private String itemName;

    /** 数据归类 */
    private String itemClassify;

    /** 项目性别 */
    private String itemSex;

    /** 参考值文本型 */
    private String itemRefValue;

    /** 项目标准单位 */
    private String itemUnit;

    /** 是否对比 否;1=是 */
    private Integer isContrast;

    /** 是否重要 否;1=是 */
    private Integer isMain;

    /** 关联编码 */
    private String itemRefCode;

    /** 项目分类分析 */
    private String itemType;

    /** 项目是否分析 */
    private String itemAnalyze;

    /** 结论算法 */
    private String resultJson;

    /** 检查项目主键 */
    private String inspectId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public String getItemSex() {
        return itemSex;
    }

    public void setItemSex(String itemSex) {
        this.itemSex = itemSex;
    }

    public String getItemRefValue() {
        return itemRefValue;
    }

    public void setItemRefValue(String itemRefValue) {
        this.itemRefValue = itemRefValue;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Integer getIsContrast() {
        return isContrast;
    }

    public void setIsContrast(Integer isContrast) {
        this.isContrast = isContrast;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public String getItemRefCode() {
        return itemRefCode;
    }

    public void setItemRefCode(String itemRefCode) {
        this.itemRefCode = itemRefCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemAnalyze() {
        return itemAnalyze;
    }

    public void setItemAnalyze(String itemAnalyze) {
        this.itemAnalyze = itemAnalyze;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

}
