
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 机构信息表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_institution")
public class Institution extends MappedEntity {

    private static final long serialVersionUID = 2046284871986201899L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String insId;

    /** 机构名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insName;

    /** 机构地区 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insPlace;

    /** 机构类型 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insType;

    /** 机构等级 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insGrade;

    /** 机构地址 */
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insAddress;

    /** 机构邮编 */
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insPost;

    /** 机构电话 */
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insTel;

    /** 机构传真 */
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insFax;

    /** 机构网址 */
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insWeb;

    /** 机构LOGO */
    @QueryFieldAnnotation
    @UpdateAnnotation
    private String insLogo;

    /** 机构运行状态--0=停止，1=运行 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    @UpdateAnnotation
    private Integer insState;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "ins_id")
    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    @Column(name = "ins_name")
    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    @Column(name = "ins_place")
    public String getInsPlace() {
        return insPlace;
    }

    public void setInsPlace(String insPlace) {
        this.insPlace = insPlace;
    }

    @Column(name = "ins_type")
    public String getInsType() {
        return insType;
    }

    public void setInsType(String insType) {
        this.insType = insType;
    }

    @Column(name = "ins_grade")
    public String getInsGrade() {
        return insGrade;
    }

    public void setInsGrade(String insGrade) {
        this.insGrade = insGrade;
    }

    @Column(name = "ins_address")
    public String getInsAddress() {
        return insAddress;
    }

    public void setInsAddress(String insAddress) {
        this.insAddress = insAddress;
    }

    @Column(name = "ins_post")
    public String getInsPost() {
        return insPost;
    }

    public void setInsPost(String insPost) {
        this.insPost = insPost;
    }

    @Column(name = "ins_tel")
    public String getInsTel() {
        return insTel;
    }

    public void setInsTel(String insTel) {
        this.insTel = insTel;
    }

    @Column(name = "ins_fax")
    public String getInsFax() {
        return insFax;
    }

    public void setInsFax(String insFax) {
        this.insFax = insFax;
    }

    @Column(name = "ins_web")
    public String getInsWeb() {
        return insWeb;
    }

    public void setInsWeb(String insWeb) {
        this.insWeb = insWeb;
    }

    @Column(name = "ins_logo")
    public String getInsLogo() {
        return insLogo;
    }

    public void setInsLogo(String insLogo) {
        this.insLogo = insLogo;
    }

    @Column(name = "ins_state")
    public Integer getInsState() {
        return insState;
    }

    public void setInsState(Integer insState) {
        this.insState = insState;
    }
}
