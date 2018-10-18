/*
 * HospitalInspectCondition.java    1.0  2017-11-13
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 检查项目Condition
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-13 scd 初版
 */
public class HospitalInspectCondition implements Serializable {

    private static final long serialVersionUID = -7879782837225435050L;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
