
package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 疾病推断指标组合
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-18 zcq 初版
 */
public class DiseaseItemDeduceGroupCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 疾病关联主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseId;

    /** 指标主键列表 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String itemIds;

    /** 指标名称列表 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String itemNames;

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

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }

}
