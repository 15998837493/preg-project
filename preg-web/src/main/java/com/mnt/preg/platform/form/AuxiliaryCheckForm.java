/*
 * AuxiliaryCheckForm.java    1.0  2017-7-21
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.form;

import java.io.Serializable;

/**
 * 辅助检查项目套餐
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-21 scd 初版
 */
public class AuxiliaryCheckForm implements Serializable {

    private static final long serialVersionUID = 3858245539942821700L;

    /** 套餐主键 */
    private String templetId;

    /** 套餐名称 */
    private String templetName;

    /** 关联收费项目主键 */
    private String inspectIds;

    /** 关联收费项目名称 */
    private String inspectNames;

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    public String getTempletName() {
        return templetName;
    }

    public void setTempletName(String templetName) {
        this.templetName = templetName;
    }

    public String getInspectIds() {
        return inspectIds;
    }

    public void setInspectIds(String inspectIds) {
        this.inspectIds = inspectIds;
    }

    public String getInspectNames() {
        return inspectNames;
    }

    public void setInspectNames(String inspectNames) {
        this.inspectNames = inspectNames;
    }

}
