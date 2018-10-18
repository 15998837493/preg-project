/*
 * HospitalInspectPojo.java    1.0  2017-11-13
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 检查项目pojo
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-13 scd 初版
 */
public class HospitalInspectPojo implements Serializable {

    private static final long serialVersionUID = -6307829529982421170L;

    /** 主键 */
    @QueryFieldAnnotation
    private String inspectId;

    /** 编码 */
    @QueryFieldAnnotation
    private String inspectCode;

    /** 名称 */
    @QueryFieldAnnotation
    private String inspectName;

    /** 适合性别 */
    @QueryFieldAnnotation
    private String inspectSex;

    /** 逻辑删除标识 */
    private Integer flag;

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
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

    public String getInspectSex() {
        return inspectSex;
    }

    public void setInspectSex(String inspectSex) {
        this.inspectSex = inspectSex;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
