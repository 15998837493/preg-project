
package com.mnt.preg.master.entity.preg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 健康要点信息表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-6-29 zcq 初版
 */
@Entity
@Table(name = "mas_intervene_disease_point")
public class PregInterveneDiseasePoints extends MappedEntity {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    private String id;

    /** 类型 */
    @UpdateAnnotation
    private String type;

    /** 疾病主键 */
    @UpdateAnnotation
    private String diseaseId;

    /** 健康要点主键 */
    @UpdateAnnotation
    private String pointId;

    /** 适用/禁止 */
    @UpdateAnnotation
    private String ident;

    /** 状态 */
    @UpdateAnnotation
    private Integer status;

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

    @Column(name = "disease_id")
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Column(name = "point_id")
    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
