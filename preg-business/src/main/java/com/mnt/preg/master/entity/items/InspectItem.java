
package com.mnt.preg.master.entity.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 机构检查项目表
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-6-8 gss 初版
 */

@Entity
@Table(name = "mas_inspect_item")
public class InspectItem extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    @UpdateAnnotation
    private String inspectId;

    /** 编码 */
    @UpdateAnnotation
    private String inspectCode;

    /** 名称 */
    @UpdateAnnotation
    private String inspectName;

    /** 适合性别 */
    @UpdateAnnotation
    private String inspectSex;

    /** 项目类别 代码表：10=问卷，11=设备，12录入，99=其他 */
    @UpdateAnnotation
    private String inspectCategory;

    /** 项目指标保存结果表 */
    @UpdateAnnotation
    private String inspectTable;

    /** 操作名称 */
    @UpdateAnnotation
    private String inspectConfigName;

    /** 项目全拼 */
    @UpdateAnnotation
    private String inspectQuanpin;

    /** 项目缩写 */
    @UpdateAnnotation
    private String inspectAcronym;

    /** 输出报告 */
    @UpdateAnnotation
    private Integer inspectReport;

    /** 状态 */
    @UpdateAnnotation
    private Integer inspectStatus;

    /** 项目来源 */
    @UpdateAnnotation
    private String inspectSource;

    /** 项目描述 */
    @UpdateAnnotation
    private String inspectDesc;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "inspect_id")
    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    @Column(name = "inspect_code")
    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    @Column(name = "inspect_name")
    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    @Column(name = "inspect_sex")
    public String getInspectSex() {
        return inspectSex;
    }

    public void setInspectSex(String inspectSex) {
        this.inspectSex = inspectSex;
    }

    @Column(name = "inspect_table")
    public String getInspectTable() {
        return inspectTable;
    }

    public void setInspectTable(String inspectTable) {
        this.inspectTable = inspectTable;
    }

    @Column(name = "inspect_config_name")
    public String getInspectConfigName() {
        return inspectConfigName;
    }

    public void setInspectConfigName(String inspectConfigName) {
        this.inspectConfigName = inspectConfigName;
    }

    @Column(name = "inspect_status")
    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    @Column(name = "inspect_category")
    public String getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(String inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    @Column(name = "inspect_quanpin")
    public String getInspectQuanpin() {
        return inspectQuanpin;
    }

    public void setInspectQuanpin(String inspectQuanpin) {
        this.inspectQuanpin = inspectQuanpin;
    }

    @Column(name = "inspect_acronym")
    public String getInspectAcronym() {
        return inspectAcronym;
    }

    public void setInspectAcronym(String inspectAcronym) {
        this.inspectAcronym = inspectAcronym;
    }

    @Column(name = "inspect_report")
    public Integer getInspectReport() {
        return inspectReport;
    }

    public void setInspectReport(Integer inspectReport) {
        this.inspectReport = inspectReport;
    }

    @Column(name = "inspect_source")
    public String getInspectSource() {
        return inspectSource;
    }

    public void setInspectSource(String inspectSource) {
        this.inspectSource = inspectSource;
    }

    @Column(name = "inspect_desc")
    public String getInspectDesc() {
        return inspectDesc;
    }

    public void setInspectDesc(String inspectDesc) {
        this.inspectDesc = inspectDesc;
    }

}
