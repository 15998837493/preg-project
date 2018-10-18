/*
 * MasInterveneDiseaseInspectCondition.java    1.0  2017-9-28
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 辅助检查项目检索条件
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-9-28 scd 初版
 */
public class MasInterveneDiseaseInspectCondition implements Serializable {

    private static final long serialVersionUID = -2081177282072836743L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 疾病关联主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diseaseId;

    /** 项目关联主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectId;

    /** 项目关联名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE, aliasName = "inspect")
    private String inspectName;

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

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

}
