/*
 * BaseEntity.java	 1.0   2014-11-3
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.main.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.enums.Flag;

/**
 * 基类
 * 
 * @author zy
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-11-3 zy 初版
 */
@MappedSuperclass
public class MappedEntity implements Serializable {

    private static final long serialVersionUID = -7151509730926448161L;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    @QueryConditionAnnotation(order = OrderConstants.DESC)
    private Date updateTime;

    /** 创建者 */
    private String createUserId;

    /** 更新者 */
    @UpdateAnnotation
    private String updateUserId;

    /** 逻辑删除标识 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @UpdateAnnotation
    private Integer flag = Flag.normal.getValue();

    /** 所属机构 */
    private String createInsId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "update_user_id")
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Column(name = "flag")
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Column(name = "create_ins_id")
    public String getCreateInsId() {
        return createInsId;
    }

    public void setCreateInsId(String createInsId) {
        this.createInsId = createInsId;
    }

}
