
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;
import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 疾病推断指标组合
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-18 zcq 初版
 */
public class DiseaseItemDeduceGroupPojo implements Serializable {

    private static final long serialVersionUID = 86133870272545294L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 疾病关联主键 */
    @QueryFieldAnnotation
    private String diseaseId;

    /** 指标主键列表 */
    @QueryFieldAnnotation
    private String itemIds;

    /** 指标名称列表 */
    @QueryFieldAnnotation
    private String itemNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }

}
