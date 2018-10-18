
package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

public class MasHospitalInspectItemConfigCondition implements Serializable {

    private static final long serialVersionUID = 6418000275568355093L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 项目主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectId;

    /** 指标主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String itemId;

    /** 指标名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, aliasName = "ItemPojo")
    private String itemName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
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

}
