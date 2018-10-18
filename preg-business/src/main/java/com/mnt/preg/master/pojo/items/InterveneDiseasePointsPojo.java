
package com.mnt.preg.master.pojo.items;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 干预要点与疾病关联表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-6-7 gss 初版
 */
public class InterveneDiseasePointsPojo extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 编号 */
    @QueryFieldAnnotation
    private String id;

    /** 类型 */
    @QueryFieldAnnotation
    private String type;

    /** 疾病主键 */
    @QueryFieldAnnotation
    private String diseaseId;

    /** 健康要点主键 */
    @QueryFieldAnnotation
    private String pointId;

    /** 适用/禁止 */
    @QueryFieldAnnotation
    private String ident;

    /** 状态 */
    @QueryFieldAnnotation
    private Integer status;

    private String diseaseName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

}
