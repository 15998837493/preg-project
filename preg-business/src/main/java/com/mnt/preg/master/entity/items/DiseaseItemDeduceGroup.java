
package com.mnt.preg.master.entity.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 疾病推断指标组合
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-18 zcq 初版
 */
@Entity
@Table(name = "mas_intervene_disease_item_deduce_group")
public class DiseaseItemDeduceGroup extends MappedEntity {

    private static final long serialVersionUID = 86133870272545294L;

    /** 主键 */
    @UpdateAnnotation
    private String id;

    /** 疾病关联主键 */
    @UpdateAnnotation
    private String diseaseId;

    /** 指标主键列表 */
    @UpdateAnnotation
    private String itemIds;

    /** 指标名称列表 */
    @UpdateAnnotation
    private String itemNames;

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

    @Column(name = "item_ids")
    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    @Column(name = "item_names")
    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }

}
