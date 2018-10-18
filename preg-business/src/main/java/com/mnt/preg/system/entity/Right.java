
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 权限表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_right")
public class Right extends MappedEntity {

    private static final long serialVersionUID = 4520803907449167613L;

    /** 主键 */
    @NotEmpty
    private String rightId;

    /** 权限名称 */
    @UpdateAnnotation
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String rightName;

    /** 权限类型 */
    @UpdateAnnotation
    private String rightType;

    /** 权限备注 */
    @UpdateAnnotation
    private String rightRemark;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "right_id")
    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId;
    }

    @Column(name = "right_name")
    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    @Column(name = "right_type")
    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    @Column(name = "right_remark")
    public String getRightRemark() {
        return rightRemark;
    }

    public void setRightRemark(String rightRemark) {
        this.rightRemark = rightRemark;
    }

}
