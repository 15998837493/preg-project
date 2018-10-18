/*
 * Questionnaire.java    1.0  2016-2-29
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
 * 操作问卷记录表
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-29 mnt_zhangjing 初版
 */
@Entity
@Table(name = "cus_question_record")
public class QuestionRecord extends MappedEntity {

    private static final long serialVersionUID = 1L;

    /** 问卷分配编号 */
    @UpdateAnnotation
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

    /** 问卷有效期 */
    @UpdateAnnotation
    private Date expiryDate;

    /** 客户编号（答题者） */
    @UpdateAnnotation
    private String custId;

    /** 提交日期 */
    @UpdateAnnotation
    private Date submitDate;

    /** 问卷状态 常量：1=完成，2=过期，3=作废，9=失败 */
    @UpdateAnnotation
    private Integer state;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "question_alloc_id")
    public String getQuestionAllocId() {
        return questionAllocId;
    }

    public void setQuestionAllocId(String questionAllocId) {
        this.questionAllocId = questionAllocId;
    }

    @Column(name = "cust_id")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "expiry_date")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "submit_date")
    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
