
package com.mnt.preg.master.form.items;

/**
 * 医院收费套餐表单
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.0 2017-11-23 dhs 初版
 */
public class HospitalInspectTemplateForm {

    /** 主键 */
    private String templateId;

    /** 套餐名称 */
    private String templateName;

    /** inspectId */
    private String inspectRelationCodes;

    /** 关联项目名称 */
    private String inspectRelationNames;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getInspectRelationCodes() {
        return inspectRelationCodes;
    }

    public void setInspectRelationCodes(String inspectRelationCodes) {
        this.inspectRelationCodes = inspectRelationCodes;
    }

    public String getInspectRelationNames() {
        return inspectRelationNames;
    }

    public void setInspectRelationNames(String inspectRelationNames) {
        this.inspectRelationNames = inspectRelationNames;
    }

}
