
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.Date;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 接诊项目
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-17 zcq 初版
 */
public class PregDiagnosisItemsVo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 检查单号 */
    @QueryFieldAnnotation
    private String id;

    /** 评价项目编码 */
    @QueryFieldAnnotation
    private String inspectCode;

    /** 返回码 */
    @QueryFieldAnnotation
    private String resultCode;

    /** 检查时间 */
    @QueryFieldAnnotation
    private Date inspectDatetime;

    /** 检查医生 */
    @QueryFieldAnnotation
    private String inspectUser;

    /** 检查医生 */
    @QueryFieldAnnotation
    private String inspectUserName;

    /** 检查状态：1=未评估，2=助理未评估，3=完成，4=助理评估完成未发送 */
    @QueryFieldAnnotation
    private Integer inspectStatus;

    /** 重新评估提示语 */
    @QueryFieldAnnotation
    private String inspectPrompt;

    /** 所属门诊号 */
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 评价项目名称 */
    private String inspectName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Date getInspectDatetime() {
        return inspectDatetime;
    }

    public void setInspectDatetime(Date inspectDatetime) {
        this.inspectDatetime = inspectDatetime;
    }

    public String getInspectUser() {
        return inspectUser;
    }

    public void setInspectUser(String inspectUser) {
        this.inspectUser = inspectUser;
    }

    public String getInspectUserName() {
        return inspectUserName;
    }

    public void setInspectUserName(String inspectUserName) {
        this.inspectUserName = inspectUserName;
    }

    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getInspectPrompt() {
        return inspectPrompt;
    }

    public void setInspectPrompt(String inspectPrompt) {
        this.inspectPrompt = inspectPrompt;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

}
