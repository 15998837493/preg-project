
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 机构检查项目表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-6-8 gss 初版
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class InspectItemPojo implements Serializable {

    private static final long serialVersionUID = 6907241399019407738L;

    /** 编码 */
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

    /** 项目类别 代码表：10=问卷，11=设备，12录入，99=其他 */
    @QueryFieldAnnotation
    private String inspectCategory;

    /** 项目指标保存结果表 */
    @QueryFieldAnnotation
    private String inspectTable;

    /** 操作名称 */
    @QueryFieldAnnotation
    private String inspectConfigName;

    /** 项目全拼 */
    @QueryFieldAnnotation
    private String inspectQuanpin;

    /** 项目缩写 */
    @QueryFieldAnnotation
    private String inspectAcronym;

    /** 输出报告 */
    @QueryFieldAnnotation
    private Integer inspectReport;

    /** 状态 */
    @QueryFieldAnnotation
    private Integer inspectStatus;

    /** 项目来源 */
    @QueryFieldAnnotation
    private String inspectSource;

    /** 项目描述 */
    @QueryFieldAnnotation
    private String inspectDesc;

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

    public String getInspectTable() {
        return inspectTable;
    }

    public void setInspectTable(String inspectTable) {
        this.inspectTable = inspectTable;
    }

    public String getInspectConfigName() {
        return inspectConfigName;
    }

    public void setInspectConfigName(String inspectConfigName) {
        this.inspectConfigName = inspectConfigName;
    }

    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(String inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getInspectQuanpin() {
        return inspectQuanpin;
    }

    public void setInspectQuanpin(String inspectQuanpin) {
        this.inspectQuanpin = inspectQuanpin;
    }

    public String getInspectAcronym() {
        return inspectAcronym;
    }

    public void setInspectAcronym(String inspectAcronym) {
        this.inspectAcronym = inspectAcronym;
    }

    public Integer getInspectReport() {
        return inspectReport;
    }

    public void setInspectReport(Integer inspectReport) {
        this.inspectReport = inspectReport;
    }

    public String getInspectSource() {
        return inspectSource;
    }

    public void setInspectSource(String inspectSource) {
        this.inspectSource = inspectSource;
    }

    public String getInspectDesc() {
        return inspectDesc;
    }

    public void setInspectDesc(String inspectDesc) {
        this.inspectDesc = inspectDesc;
    }

}
