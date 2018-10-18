/*
 * FoodMaterialCondition.java    1.0  2018-1-25
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.master.condition.basic;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.pojo.PageCondition;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 食材库检索条件
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-25 scd 初版
 */
public class FoodMaterialCondition extends PageCondition {

    private static final long serialVersionUID = -5199683495436138553L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String fmId;

    /** 食材名称 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String fmName;

    /** 普通食品类别 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String fmGeneralType;

    /** 食材类型主键 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String fmType;

    /** 食材类型主键模糊查询条件 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE_AFTER, name = "fmType")
    private String fmTypeLike;

    /** 删除标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;
    
    private String treeName;

    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    public String getFmGeneralType() {
        return fmGeneralType;
    }

    public void setFmGeneralType(String fmGeneralType) {
        this.fmGeneralType = fmGeneralType;
    }

    public String getFmType() {
        return fmType;
    }

    public void setFmType(String fmType) {
        this.fmType = fmType;
    }

    public String getFmTypeLike() {
        return fmTypeLike;
    }

    public void setFmTypeLike(String fmTypeLike) {
        this.fmTypeLike = fmTypeLike;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    
    public String getTreeName() {
        return treeName;
    }

    
    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }
    
    

}
