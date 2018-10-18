/*
 * MasInterveneDiseaseInspectConfig.java    1.0  2017-9-26
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.entity.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 诊断项目配置
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-9-26 scd 初版
 */
@Entity
@Table(name = "mas_intervene_disease_inspect_pay")
public class InterveneDiaeaseInsepectPay extends MappedEntity {

    private static final long serialVersionUID = 86133870272545294L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String id;

    /** 疾病关联主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String diseaseId;

    /** 项目关联主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String inspectId;

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

    @Column(name = "inspect_id")
    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

}
