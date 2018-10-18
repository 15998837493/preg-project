/*
 * HospitalInspectPayConfigPojo.java    1.0  2017-11-17
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 收费项目与检查项目关联表Pojo
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-17 scd 初版
 */
public class HospitalInspectPayConfigPojo implements Serializable {

    private static final long serialVersionUID = -8045201559860563795L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 收费项目主键 */
    @QueryFieldAnnotation
    private String inspectId;

    /** 检查项目主键 */
    @QueryFieldAnnotation
    private String relationId;

    /** 检查项目名称 */
    @QueryFieldAnnotation(aliasName = "inspect")
    private String inspectName;

    /** 检查项目编码 */
    @QueryFieldAnnotation(aliasName = "inspect")
    private String inspectCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

}
