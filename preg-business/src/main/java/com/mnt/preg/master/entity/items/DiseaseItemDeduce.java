
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
 * 疾病检测指标关联表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-13 zcq 初版
 */
@Entity
@Table(name = "mas_intervene_disease_item_deduce")
public class DiseaseItemDeduce extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 编号 */
    private String id;

    /** 疾病编码 */
    private String diseaseId;

    /** 指标编码 */
    private String itemId;

    /** 结论类型 */
    @UpdateAnnotation
    private String algorithm;

    /** 结论内容 */
    @UpdateAnnotation
    private String content;

    /** 适宜阶段 */
    @UpdateAnnotation
    private String suitableStages;

    /** 孕周范围 */
    @UpdateAnnotation
    private String weekBorder;

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

    @Column(name = "item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "disease_id")
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Column(name = "algorithm")
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "suitable_stages")
    public String getSuitableStages() {
        return suitableStages;
    }

    public void setSuitableStages(String suitableStages) {
        this.suitableStages = suitableStages;
    }

    @Column(name = "week_border")
    public String getWeekBorder() {
        return weekBorder;
    }

    public void setWeekBorder(String weekBorder) {
        this.weekBorder = weekBorder;
    }

}
