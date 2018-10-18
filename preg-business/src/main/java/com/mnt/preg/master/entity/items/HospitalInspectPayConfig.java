/*
 * HospitalInspectPayConfig.java    1.0  2017-11-14
 *
 * 沈阳成林健康科技有限公司
 * 
 */

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
 * 收费项目与检查项目关联表
 * 
 * @author scd
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-11-14 scd 初版
 */
@Entity
@Table(name = "mas_hospital_inspect_pay_config")
public class HospitalInspectPayConfig extends MappedEntity {

    private static final long serialVersionUID = -2601945117187662816L;

    /** 主键 */
    @UpdateAnnotation
    private String id;

    /** 收费项目主键 */
    @UpdateAnnotation
    private String inspectId;

    /** 检查项目主键 */
    @UpdateAnnotation
    private String itemId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "inspect_id")
    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    @Column(name = "item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
