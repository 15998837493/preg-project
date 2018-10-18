
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 默认接诊项目
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-17 zcq 初版
 */
public class UserInspectItemPojo implements Serializable {

    private static final long serialVersionUID = -3639328480269825061L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 评价项目ID */
    @QueryFieldAnnotation
    private String inspectId;

    /** 评价项目类型：vist=复诊类型，first=首诊类型 */
    @QueryFieldAnnotation
    private String inspectType;

    /** 评价项目编码 */
    @QueryFieldAnnotation
    private String inspectCode;

    /** 评价项目名称 */
    @QueryFieldAnnotation
    private String inspectName;

    /** 检查状态 */
    @QueryFieldAnnotation
    private Integer inspectCheck;

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

    public String getInspectType() {
        return inspectType;
    }

    public void setInspectType(String inspectType) {
        this.inspectType = inspectType;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public Integer getInspectCheck() {
        return inspectCheck;
    }

    public void setInspectCheck(Integer inspectCheck) {
        this.inspectCheck = inspectCheck;
    }

}
