
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 患者接诊-辅助检查项目表
 * 
 * @author wsy
 * @version 1.1
 * 
 *          变更履历：
 *          v1.1 2017-4-6 wsy 初版
 */
@Entity
@Table(name = "user_diagnosis_quota_item")
public class DiagnosisQuotaItem extends MappedEntity {

    private static final long serialVersionUID = 7014171241589665855L;

    /** 主键 */
    @UpdateAnnotation
    private String id;

    /** 项目ID */
    @UpdateAnnotation
    private String inspectItemId;

    /** 项目名称 */
    @UpdateAnnotation
    private String inspectItemName;

    /** 接诊号 */
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

    @Column(name = "inspect_item_id")
    public String getInspectItemId() {
        return inspectItemId;
    }

    public void setInspectItemId(String inspectItemId) {
        this.inspectItemId = inspectItemId;
    }

    @Column(name = "inspect_item_name")
    public String getInspectItemName() {
        return inspectItemName;
    }

    public void setInspectItemName(String inspectItemName) {
        this.inspectItemName = inspectItemName;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
