/*
 * SystemParamVo.java	 1.0   2014-12-18
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 系统参数完整信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-18 zcq 初版
 */
public class SystemParamPojo implements Serializable {

    private static final long serialVersionUID = -1348541971603563186L;

    /** 系统参数主键 */
    @QueryFieldAnnotation
    private String paramId;

    /** 系统参数名称 */
    @QueryFieldAnnotation
    private String paramName;

    /** 系统参数值 */
    @QueryFieldAnnotation
    private String paramValue;

    /** 系统参数类型--java8个基本类型 */
    @QueryFieldAnnotation
    private String paramType;

    /** 参数说明 */
    @QueryFieldAnnotation
    private String paramRemark;

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamRemark() {
        return paramRemark;
    }

    public void setParamRemark(String paramRemark) {
        this.paramRemark = paramRemark;
    }

}
