/**
 * 
 */

package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 代码表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-5-4 zcq 初版
 */
@Entity
@Table(name = "mas_code_info")
public class CodeInfo extends MappedEntity {

    private static final long serialVersionUID = -7239728317452922368L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String codeId;

    /** 代码类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String codeKind;

    /** 代码名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @UpdateAnnotation
    private String codeName;

    /** 代码值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String codeValue;

    /** 代码类型 目录=1，类型=2，选项=3 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer codeType;

    /** 代码排序号 */
    @QueryConditionAnnotation(order = OrderConstants.ASC)
    @UpdateAnnotation
    private Integer codeOrder;

    /** 代码级别 */
    @UpdateAnnotation
    private Integer codeGrade;

    /** 代码描述 */
    @UpdateAnnotation
    private String codeDesc;

    /** 备注 */
    @UpdateAnnotation
    private String codeRemark;

    /** 上级代码类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String parentCodeKind;

    /** 上级代码值 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private String parentCodeValue;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "code_id")
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @Column(name = "code_kind")
    public String getCodeKind() {
        return codeKind;
    }

    public void setCodeKind(String codeKind) {
        this.codeKind = codeKind;
    }

    @Column(name = "code_name")
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Column(name = "code_value")
    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    @Column(name = "code_type")
    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    @Column(name = "code_order")
    public Integer getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(Integer codeOrder) {
        this.codeOrder = codeOrder;
    }

    @Column(name = "code_grade")
    public Integer getCodeGrade() {
        return codeGrade;
    }

    public void setCodeGrade(Integer codeGrade) {
        this.codeGrade = codeGrade;
    }

    @Column(name = "code_desc")
    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    @Column(name = "code_remark")
    public String getCodeRemark() {
        return codeRemark;
    }

    public void setCodeRemark(String codeRemark) {
        this.codeRemark = codeRemark;
    }

    @Column(name = "parent_code_kind")
    public String getParentCodeKind() {
        return parentCodeKind;
    }

    public void setParentCodeKind(String parentCodeKind) {
        this.parentCodeKind = parentCodeKind;
    }

    @Column(name = "parent_code_value")
    public String getParentCodeValue() {
        return parentCodeValue;
    }

    public void setParentCodeValue(String parentCodeValue) {
        this.parentCodeValue = parentCodeValue;
    }

}
