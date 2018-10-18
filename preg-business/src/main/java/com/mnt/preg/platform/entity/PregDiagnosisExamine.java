
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
import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 体检数据详情
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-04-11 gss 初版
 */
@Entity
@Table(name = "user_diagnosis_examine")
public class PregDiagnosisExamine extends MappedEntity {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    private String id;

    /** 客户主键 */
    private String custId;

    /** 收费项目id */
    private String diagnosisId;

    /** 检测项目编码 */
    private String inspectId;

    /** 项目编码 */
    private String inspectName;

    /** 检验时间 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date samplingTime;

    /** 项目值类型 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reportTime;

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

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Column(name = "inspect_id")
    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    @Column(name = "inspect_name")
    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sampling_time")
    public Date getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Date samplingTime) {
        this.samplingTime = samplingTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "report_time")
    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

}
