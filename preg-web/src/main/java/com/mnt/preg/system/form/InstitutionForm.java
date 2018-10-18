/*
 * InstitutionForm.java  1.0   2014-12-30
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.system.form;

/**
 * 添加机构表单数据
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2014-12-30 zcq 初版
 */
public class InstitutionForm {

    /** 主键 */
    private String insId;

    /** 机构名称 */
    private String insName;

    /** 机构名称 */
    private String insNameOld;

    /** 机构地区 */
    private String insPlace;

    /** 机构类型 */
    private String insType;

    /** 机构等级 */
    private String insGrade;

    /** 机构地址 */
    private String insAddress;

    /** 机构邮编 */
    private String insPost;

    /** 机构电话 */
    private String insTel;

    /** 机构传真 */
    private String insFax;

    /** 机构网址 */
    private String insWeb;

    /** 机构LOGO */
    private String insLogo;

    /** 机构运行状态--0=停止，1=运行 */
    private Integer insState;

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getInsNameOld() {
        return insNameOld;
    }

    public void setInsNameOld(String insNameOld) {
        this.insNameOld = insNameOld;
    }

    public String getInsPlace() {
        return insPlace;
    }

    public void setInsPlace(String insPlace) {
        this.insPlace = insPlace;
    }

    public String getInsType() {
        return insType;
    }

    public void setInsType(String insType) {
        this.insType = insType;
    }

    public String getInsGrade() {
        return insGrade;
    }

    public void setInsGrade(String insGrade) {
        this.insGrade = insGrade;
    }

    public String getInsAddress() {
        return insAddress;
    }

    public void setInsAddress(String insAddress) {
        this.insAddress = insAddress;
    }

    public String getInsPost() {
        return insPost;
    }

    public void setInsPost(String insPost) {
        this.insPost = insPost;
    }

    public String getInsTel() {
        return insTel;
    }

    public void setInsTel(String insTel) {
        this.insTel = insTel;
    }

    public String getInsFax() {
        return insFax;
    }

    public void setInsFax(String insFax) {
        this.insFax = insFax;
    }

    public String getInsWeb() {
        return insWeb;
    }

    public void setInsWeb(String insWeb) {
        this.insWeb = insWeb;
    }

    public String getInsLogo() {
        return insLogo;
    }

    public void setInsLogo(String insLogo) {
        this.insLogo = insLogo;
    }

    public Integer getInsState() {
        return insState;
    }

    public void setInsState(Integer insState) {
        this.insState = insState;
    }

}
