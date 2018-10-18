
package com.mnt.preg.platform.condition;

import java.io.Serializable;
import java.util.List;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 营养制剂检索条件
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-08-22 mlq 初版
 */
public class DiagnosisPrescriptionCondition implements Serializable {

    private static final long serialVersionUID = -8713805225682464170L;

    /** 所属门诊号集合 */
    @QueryConditionAnnotation(name = "diagnosisId", symbol = SymbolConstants.IN)
    private List<String> diagnosisIds;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public List<String> getDiagnosisIds() {
        return diagnosisIds;
    }

    public void setDiagnosisIds(List<String> diagnosisIds) {
        this.diagnosisIds = diagnosisIds;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
