
package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 收费项目检索条件
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-21 zcq 初版
 */
public class HospitalInspectPayCondition implements Serializable {

    private static final long serialVersionUID = -2294665144243882452L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectId;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectCode;

    /** 名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String inspectName;

    /** 适合性别 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String inspectSex;

    /** 出结果时限 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String resultsSuggest;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getInspectSex() {
        return inspectSex;
    }

    public void setInspectSex(String inspectSex) {
        this.inspectSex = inspectSex;
    }

    public String getResultsSuggest() {
        return resultsSuggest;
    }

    public void setResultsSuggest(String resultsSuggest) {
        this.resultsSuggest = resultsSuggest;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
