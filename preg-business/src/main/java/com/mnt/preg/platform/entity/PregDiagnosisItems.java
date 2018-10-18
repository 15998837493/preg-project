
package com.mnt.preg.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 接诊项目
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-17 zcq 初版
 */
@Entity
@Table(name = "user_diagnosis_inspect")
public class PregDiagnosisItems extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 检查单号 */
    private String id;

    /** 评价项目编码 */
    @UpdateAnnotation
    private String inspectCode;

    /** 返回码 */
    @UpdateAnnotation
    private String resultCode;

    /** 检查时间 */
    @UpdateAnnotation
    private Date inspectDatetime;

    /** 检查医生 */
    @UpdateAnnotation
    private String inspectUser;

    /** 检查医生 */
    @UpdateAnnotation
    private String inspectUserName;

    /** 检查状态：1=未评估，2=助理未评估，3=完成，4=助理评估完成未发送 */
    @UpdateAnnotation
    private Integer inspectStatus;

    /** 重新评估提示语 */
    @UpdateAnnotation
    private String inspectPrompt;

    /** 所属门诊号 */
    @UpdateAnnotation
    private String diagnosisId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "inspect_code")
    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    @Column(name = "result_code")
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inspect_datetime")
    public Date getInspectDatetime() {
        return inspectDatetime;
    }

    public void setInspectDatetime(Date inspectDatetime) {
        this.inspectDatetime = inspectDatetime;
    }

    @Column(name = "inspect_user")
    public String getInspectUser() {
        return inspectUser;
    }

    public void setInspectUser(String inspectUser) {
        this.inspectUser = inspectUser;
    }

    @Column(name = "inspect_user_name")
    public String getInspectUserName() {
        return inspectUserName;
    }

    public void setInspectUserName(String inspectUserName) {
        this.inspectUserName = inspectUserName;
    }

    @Column(name = "inspect_status")
    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    @Column(name = "inspect_prompt")
    public String getInspectPrompt() {
        return inspectPrompt;
    }

    public void setInspectPrompt(String inspectPrompt) {
        this.inspectPrompt = inspectPrompt;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
