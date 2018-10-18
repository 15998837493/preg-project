package com.mnt.preg.master.form.items;

/**
 * 
 * 机构检查项目表单
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2016-6-8 gss 初版
 */

public class InspectItemForm {

	/** 主键 */
	private String inspectId;

	/** 编码 */
	private String inspectCode;

	/** 名称 */
	private String inspectName;

	/** 适合性别 */
	private String inspectSex;

	/** 项目类别 代码表：10=问卷，11=设备，12录入，99=其他 */
	private String inspectCategory;

	/** 项目指标保存结果表 */
	private String inspectTable;

	/** 操作名称 */
	private String inspectConfigName;

	/** 项目来源 */
	private String inspectSource;

	/** 项目描述 */
	private String inspectDesc;

	/** 项目缩写 */
	private String inspectPinying;

	/** 项目缩写 */
	private String inspectAcronym;

	/** 输出报告 */
	private Integer inspectReport;

	/** 状态 */
	private Integer inspectStatus;

	public String getInspectId() {
		return inspectId;
	}

	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}

	public String getInspectCode() {
		return inspectCode;
	}

	public void setInspectCode(String inspectCode) {
		this.inspectCode = inspectCode;
	}

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

	public String getInspectCategory() {
		return inspectCategory;
	}

	public void setInspectCategory(String inspectCategory) {
		this.inspectCategory = inspectCategory;
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

	public String getInspectPinying() {
		return inspectPinying;
	}

	public void setInspectPinying(String inspectPinying) {
		this.inspectPinying = inspectPinying;
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

	public Integer getInspectStatus() {
		return inspectStatus;
	}

	public void setInspectStatus(Integer inspectStatus) {
		this.inspectStatus = inspectStatus;
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
