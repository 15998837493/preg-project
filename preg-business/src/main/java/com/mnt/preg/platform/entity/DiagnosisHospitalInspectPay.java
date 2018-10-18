
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 患者收费项目管理
 * 
 * @author mlq
 * 
 *         变更履历： 2018-06-21 mlq
 */
@Entity
@Table(name = "user_diagnosis_hospital_inspect_pay")
public class DiagnosisHospitalInspectPay extends MappedEntity {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    private String id;

    /** 检验报告id */
    private String reportId;

    /** 收费项目id */
    private String inspectId;

    /** 收费项目名称 */
    private String inspectName;

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

    @Column(name = "report_id")
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
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

}
