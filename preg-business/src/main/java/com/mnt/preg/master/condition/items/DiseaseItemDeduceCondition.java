
package com.mnt.preg.master.condition.items;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

public class DiseaseItemDeduceCondition implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 疾病id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseId;

    /** 检验项目id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemId;

    /** 检验项目名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, aliasName = "item")
    private String itemName;

    /** 适宜阶段 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String suitableStages;

    /** 孕周范围 */
    private String weekBorder;

    /** 检验项目id集合 */
    @QueryConditionAnnotation(symbol = SymbolConstants.IN, name = "itemId")
    private List<String> itemIdList;

    /** 标志 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public List<String> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<String> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
