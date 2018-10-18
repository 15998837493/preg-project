
package com.mnt.preg.platform.condition;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 结果表检测指标检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-17 zcq 初版
 */
public class DiagnosisQuotaItemCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 体检id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String diagnosisId;

    /** 体检id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectItemId;

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getInspectItemId() {
        return inspectItemId;
    }

    public void setInspectItemId(String inspectItemId) {
        this.inspectItemId = inspectItemId;
    }

}
