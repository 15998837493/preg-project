/*
 * DeptForm.java	 1.0   2014-12-31
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.form;

/**
 * 系统参数表单数据
 * 
 * @author zhj
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-5 zhj 初版
 */
public class SystemParamForm {

    /** 系统参数主键 */
    private String paramId;

    /** 系统参数名称 */
    private String paramName;

    /** 系统参数值 */
    private String paramValue;

    /** 系统参数类型--java8个基本类型 */
    private String paramType;

    /** 参数说明 */
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
