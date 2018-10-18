/*
 * QuesAllocation.java    1.0  2016-3-8
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 问卷分配表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-8 mnt_zhangjing 初版
 */

@Entity
@Table(name = "cus_question_allocation")
public class QuesAllocation extends MappedEntity {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 问卷分配编号 */
    private String questionAllocId;

    /** 问卷编号 */
    @UpdateAnnotation
    private String questionId;

    /** 问卷分配用户（用户编号） */
    @UpdateAnnotation
    private String userId;

    /** 问卷分配时间 */
    @UpdateAnnotation
    private Date allocDatetime;

    /** 问卷答题客户（客户编号） */
    @UpdateAnnotation
    private String custId;

    /** 问卷有效期 */
    private Date expiryDate;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "question_alloc_id")
    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "alloc_datetime")
    public Date getAllocDatetime() {
        return allocDatetime;
    }

    public void setAllocDatetime(Date allocDatetime) {
        this.allocDatetime = allocDatetime;
    }

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "QuesAllocation [questionAllocId=" + questionAllocId + ", questionId=" + questionId + ", userId="
                + userId + ", allocDatetime=" + allocDatetime + ", custId=" + custId + ", expiryDate=" + expiryDate
                + "]";
    }

}
