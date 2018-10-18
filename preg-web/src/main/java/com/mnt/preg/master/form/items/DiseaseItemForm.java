
package com.mnt.preg.master.form.items;

import java.util.List;

/**
 * 疾病指标
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-13 zcq 初版
 */
public class DiseaseItemForm {

    /** 编码 */
    private String id;

    /** 疾病编码 */
    private String diseaseId;

    /** 指标编码 */
    private List<String> itemCodeList;

    /** 指标编码 */
    private String itemCode;

    /** 指标名称 */
    private String itemName;

    /** 指标类型 */
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<String> getItemCodeList() {
        return itemCodeList;
    }

    public void setItemCodeList(List<String> itemCodeList) {
        this.itemCodeList = itemCodeList;
    }

}
