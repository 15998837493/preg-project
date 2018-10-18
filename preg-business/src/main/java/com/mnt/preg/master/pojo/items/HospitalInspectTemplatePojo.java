
package com.mnt.preg.master.pojo.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 医院收费套餐Pojo
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.3 2017-11-23 dhs 初版
 */
public class HospitalInspectTemplatePojo implements Serializable {

    private static final long serialVersionUID = -3819990804621658472L;

    /** 主键 */
    @QueryFieldAnnotation
    private String templateId;

    /** 套餐名称 */
    @QueryFieldAnnotation
    private String templateName;

    /** 逻辑删除标识 */
    private Integer flag;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
