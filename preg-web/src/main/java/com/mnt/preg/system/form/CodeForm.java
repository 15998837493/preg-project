
package com.mnt.preg.system.form;

import java.io.Serializable;

/**
 * 代码表表单
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-11 zcq 初版
 */
public class CodeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String codeId;

    /** 代码类型 */
    private String codeKind;

    /** 代码名称 */
    private String codeName;

    /** 代码名称 */
    private String codeNameOld;

    /** 代码值 */
    private String codeValue;

    /** 代码值 */
    private String codeValueOld;

    /** 代码类型 */
    private Integer codeType;

    /** 代码排序号 */
    private Integer codeOrder;

    /** 代码级别 */
    private Integer codeGrade;

    /** 代码描述 */
    private String codeDesc;

    /** 备注 */
    private String codeRemark;

    /** 上级代码类型 */
    private String parentCodeKind;

    /** 上级代码值 */
    private String parentCodeValue;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeKind() {
        return codeKind;
    }

    public void setCodeKind(String codeKind) {
        this.codeKind = codeKind;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeNameOld() {
        return codeNameOld;
    }

    public void setCodeNameOld(String codeNameOld) {
        this.codeNameOld = codeNameOld;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeValueOld() {
        return codeValueOld;
    }

    public void setCodeValueOld(String codeValueOld) {
        this.codeValueOld = codeValueOld;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(Integer codeOrder) {
        this.codeOrder = codeOrder;
    }

    public Integer getCodeGrade() {
        return codeGrade;
    }

    public void setCodeGrade(Integer codeGrade) {
        this.codeGrade = codeGrade;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getCodeRemark() {
        return codeRemark;
    }

    public void setCodeRemark(String codeRemark) {
        this.codeRemark = codeRemark;
    }

    public String getParentCodeKind() {
        return parentCodeKind;
    }

    public void setParentCodeKind(String parentCodeKind) {
        this.parentCodeKind = parentCodeKind;
    }

    public String getParentCodeValue() {
        return parentCodeValue;
    }

    public void setParentCodeValue(String parentCodeValue) {
        this.parentCodeValue = parentCodeValue;
    }

}
