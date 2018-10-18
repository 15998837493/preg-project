
package com.mnt.preg.system.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 代码表信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-9 zcq 初版
 */
public class CodePojo implements Serializable {

    private static final long serialVersionUID = -7769704368917670054L;

    /** 主键 */
    @QueryFieldAnnotation
    private String codeId;

    /** 代码类型 */
    @QueryFieldAnnotation
    private String codeKind;

    /** 代码名称 */
    @QueryFieldAnnotation
    private String codeName;

    /** 代码值 */
    @QueryFieldAnnotation
    private String codeValue;

    /** 代码类型 */
    @QueryFieldAnnotation
    private Integer codeType;

    /** 代码排序号 */
    @QueryFieldAnnotation
    private Integer codeOrder;

    /** 代码级别 */
    @QueryFieldAnnotation
    private Integer codeGrade;

    /** 代码描述 */
    @QueryFieldAnnotation
    private String codeDesc;

    /** 备注 */
    @QueryFieldAnnotation
    private String codeRemark;

    /** 上级代码类型 */
    @QueryFieldAnnotation
    private String parentCodeKind;

    /** 上级代码值 */
    @QueryFieldAnnotation
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

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
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

    @Override
    public String toString() {
        return "CodeVo [codeId=" + codeId + ", codeKind=" + codeKind + ", codeName=" + codeName + ", codeValue="
                + codeValue + ", codeType=" + codeType + ", codeOrder=" + codeOrder + ", codeGrade=" + codeGrade
                + ", codeDesc=" + codeDesc + ", codeRemark=" + codeRemark + ", parentCodeKind=" + parentCodeKind
                + ", parentCodeValue=" + parentCodeValue + "]";
    }

}
