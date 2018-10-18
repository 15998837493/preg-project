
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 患者收费项目管理
 * 
 * @author mlq
 * 
 *         变更履历： 2018-06-21 mlq
 */
public class DiagnosisHospitalInspectPayPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 检验报告id */
    @QueryFieldAnnotation
    private String reportId;

    /** 收费项目id */
    @QueryFieldAnnotation
    private String inspectId;

    /** 收费项目名称 */
    @QueryFieldAnnotation
    private String inspectName;

    /** 已添加的检验项目个数 */
    private Object numInspectItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public Object getNumInspectItems() {
        return numInspectItems;
    }

    public void setNumInspectItems(Object numInspectItems) {
        this.numInspectItems = numInspectItems;
    }

}
