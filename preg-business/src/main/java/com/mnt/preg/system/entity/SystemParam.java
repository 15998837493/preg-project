
package com.mnt.preg.system.entity;

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
 * 系统参数表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_param")
public class SystemParam extends MappedEntity {

    private static final long serialVersionUID = 9167532570484118530L;

    /** 系统参数主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String paramId;

    /** 系统参数名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String paramName;

    /** 系统参数值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String paramValue;

    /** 系统参数类型--java8个基本类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String paramType;

    /** 参数说明 */
    @UpdateAnnotation
    private String paramRemark;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "param_id")
    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    @Column(name = "param_name")
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Column(name = "param_value")
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Column(name = "param_type")
    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    @Column(name = "param_remark")
    public String getParamRemark() {
        return paramRemark;
    }

    public void setParamRemark(String paramRemark) {
        this.paramRemark = paramRemark;
    }

}
