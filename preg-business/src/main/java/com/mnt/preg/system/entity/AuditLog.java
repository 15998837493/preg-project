/**
 * 
 */

package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 数据增、删、改日志表
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 下午1:22:17 zy 初版
 */
@Entity
@Table(name = "sys_audit_log")
public class AuditLog extends MappedEntity {

    private static final long serialVersionUID = 6159674975238702911L;

    private String auditLogId;

    private String action;

    private String detail;

    private String entityId;

    private String entityName;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "audit_id", unique = true, nullable = false)
    public String getAuditLogId() {
        return this.auditLogId;
    }

    public void setAuditLogId(String auditLogId) {
        this.auditLogId = auditLogId;
    }

    @Column(name = "audit_action")
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Column(name = "audit_detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Column(name = "audit_entity_id")
    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Column(name = "audit_entity_name")
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

}
