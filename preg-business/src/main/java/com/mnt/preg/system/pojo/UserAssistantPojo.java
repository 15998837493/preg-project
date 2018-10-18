
package com.mnt.preg.system.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 医生助理关系表
 * 
 * @author dhs
 * @version 1.5
 * 
 *          变更履历： v1.5 2018-04-08 dhs 初版
 */
public class UserAssistantPojo implements Serializable {

    private static final long serialVersionUID = -6482991357686182088L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 医生主键 */
    @QueryFieldAnnotation
    private String doctorId;

    /** 助理主键 */
    @QueryFieldAnnotation
    private String assistantId;

    /** 医生姓名 */
    private String doctorName;

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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

}
