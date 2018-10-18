
package com.mnt.preg.system.condition;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 医师助理检索条件
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历：v1.5 2018-04-08 dhs 初版
 */
public class DoctorAssistantCondition implements Serializable {

    private static final long serialVersionUID = -6256029598735870498L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String id;

    /** 医生 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String doctorId;

    /** 助理 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String assistantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }
}
