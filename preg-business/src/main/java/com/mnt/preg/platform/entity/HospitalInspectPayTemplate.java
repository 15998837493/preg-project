/*
 * PregAuxiliary.java    1.0  2017-7-20
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.platform.entity;

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
 * 辅助检查套餐
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-7-20 scd 初版
 */
@Entity
@Table(name = "user_hospital_inspect_pay_template")
public class HospitalInspectPayTemplate extends MappedEntity {

    private static final long serialVersionUID = -5017409271605325101L;

    /** 套餐主键 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String templetId;

    /** 套餐名称 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String templetName;

    /** 关联收费项目主键 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String inspectIds;

    /** 关联收费项目名称 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String inspectNames;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "templet_id")
    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    @Column(name = "templet_name")
    public String getTempletName() {
        return templetName;
    }

    public void setTempletName(String templetName) {
        this.templetName = templetName;
    }

    @Column(name = "inspect_ids")
    public String getInspectIds() {
        return inspectIds;
    }

    public void setInspectIds(String inspectIds) {
        this.inspectIds = inspectIds;
    }

    @Column(name = "inspect_names")
    public String getInspectNames() {
        return inspectNames;
    }

    public void setInspectNames(String inspectNames) {
        this.inspectNames = inspectNames;
    }

}
