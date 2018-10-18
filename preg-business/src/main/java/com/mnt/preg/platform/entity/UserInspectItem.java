
package com.mnt.preg.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 默认接诊项目
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-6-17 zcq 初版
 */
@Entity
@Table(name = "user_inspect_item")
public class UserInspectItem extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主键 */
    private String id;

    /** 评价项目ID */
    @UpdateAnnotation
    private String inspectId;

    /** 评价项目类型：vist=复诊类型，first=首诊类型 */
    @UpdateAnnotation
    private String inspectType;

    /** 评价项目编码 */
    @UpdateAnnotation
    private String inspectCode;

    /** 评价项目名称 */
    @UpdateAnnotation
    private String inspectName;

    /** 检查状态 */
    @UpdateAnnotation
    private Integer inspectCheck;

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

    @Column(name = "inspect_type")
    public String getInspectType() {
        return inspectType;
    }

    public void setInspectType(String inspectType) {
        this.inspectType = inspectType;
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

    @Column(name = "inspect_check")
    public Integer getInspectCheck() {
        return inspectCheck;
    }

    public void setInspectCheck(Integer inspectCheck) {
        this.inspectCheck = inspectCheck;
    }

}
