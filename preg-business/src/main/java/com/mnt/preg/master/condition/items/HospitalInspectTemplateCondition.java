
package com.mnt.preg.master.condition.items;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 医院收费套餐检索条件
 * 
 * @author dhs
 * @version 1.3
 * 
 *          变更履历：
 *          v1.0 2017-11-23 dhs 初版
 */
public class HospitalInspectTemplateCondition implements Serializable {

    private static final long serialVersionUID = -2294665144243882452L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String templateId;

    /** 套餐名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String templateName;

    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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
