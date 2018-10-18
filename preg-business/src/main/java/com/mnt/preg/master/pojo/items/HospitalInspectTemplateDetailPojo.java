
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 医院收费套餐Pojo
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.3 2017-11-23 dhs 初版
 */
public class HospitalInspectTemplateDetailPojo implements Serializable {

    private static final long serialVersionUID = -6053373263038217238L;

    /** 收费套餐主键 */
    @QueryFieldAnnotation
    private String inspectRelationId;

    /** 收费项目主键 */
    @QueryFieldAnnotation
    private String inspectRelationName;

    public String getInspectRelationId() {
        return inspectRelationId;
    }

    public void setInspectRelationId(String inspectRelationId) {
        this.inspectRelationId = inspectRelationId;
    }

    public String getInspectRelationName() {
        return inspectRelationName;
    }

    public void setInspectRelationName(String inspectRelationName) {
        this.inspectRelationName = inspectRelationName;
    }

}
