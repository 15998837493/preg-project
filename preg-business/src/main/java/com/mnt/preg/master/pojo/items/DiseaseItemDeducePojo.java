
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 指标推断疾病Pojo
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-17 zcq 初版
 */
public class DiseaseItemDeducePojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 编号 */
    @QueryFieldAnnotation
    private String id;

    /** 疾病编码 */
    @QueryFieldAnnotation
    private String diseaseId;

    /** 指标编码 */
    @QueryFieldAnnotation
    private String itemId;

    /** 结论类型 */
    @QueryFieldAnnotation
    private String algorithm;

    /** 结论内容 */
    @QueryFieldAnnotation
    private String content;

    /** 适宜阶段 */
    @QueryFieldAnnotation
    private String suitableStages;

    /** 孕周范围 */
    @QueryFieldAnnotation
    private String weekBorder;

    /** 指标编码 */
    private String itemCode;

    /** 指标名称 */
    private String itemName;

    /** 指标标准值 */
    private String itemRefValue;

    /** 指标标单位 */
    private String itemUnit;

    /** 检验项目分类 */
    private String itemClassify;

    /** 所属科室 */
    private String itemType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getSuitableStages() {
        return suitableStages;
    }

    public void setSuitableStages(String suitableStages) {
        this.suitableStages = suitableStages;
    }

    public String getWeekBorder() {
        return weekBorder;
    }

    public void setWeekBorder(String weekBorder) {
        this.weekBorder = weekBorder;
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

}
