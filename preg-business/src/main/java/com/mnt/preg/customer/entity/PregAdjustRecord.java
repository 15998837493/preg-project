
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
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 客户信息Bo
 * 
 * @author 王鑫
 * @version 1.0
 * 
 *          变更履历： v1.0 2014-12-09 王鑫 初版
 */
@Entity
@Table(name = "cus_pregnancy_adjust_record")
public class PregAdjustRecord extends MappedEntity {

    /** [属性说明] */
    private static final long serialVersionUID = -7407410996592788289L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String id;

    /** 建档id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String archivesId;

    /** 接诊id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 调整日期 */
    @UpdateAnnotation
    @QueryConditionAnnotation(order = OrderConstants.DESC)
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date adjustDate;

    /** 原末次月经 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lmpDateOld;

    /** 原孕周 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String gestationalWeeksOld;

    /** 原预产期 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dueDateOld;

    /** 新末次月经 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lmpDateNew;

    /** 新孕周 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String gestationalWeeksNew;

    /** 新预产期 */
    @UpdateAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @QueryFieldAnnotation
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dueDateNew;

    /** 调整理由 */
    @UpdateAnnotation
    @QueryFieldAnnotation
    private String adjustReason;

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

    @Column(name = "archives_id")
    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    @Column(name = "diagnosis_id")
    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "adjust_date")
    public Date getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(Date adjustDate) {
        this.adjustDate = adjustDate;
    }

    @Column(name = "gestational_weeks_old")
    public String getGestationalWeeksOld() {
        return gestationalWeeksOld;
    }

    public void setGestationalWeeksOld(String gestationalWeeksOld) {
        this.gestationalWeeksOld = gestationalWeeksOld;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date_old")
    public Date getDueDateOld() {
        return dueDateOld;
    }

    public void setDueDateOld(Date dueDateOld) {
        this.dueDateOld = dueDateOld;
    }

    @Column(name = "gestational_weeks_new")
    public String getGestationalWeeksNew() {
        return gestationalWeeksNew;
    }

    public void setGestationalWeeksNew(String gestationalWeeksNew) {
        this.gestationalWeeksNew = gestationalWeeksNew;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date_new")
    public Date getDueDateNew() {
        return dueDateNew;
    }

    public void setDueDateNew(Date dueDateNew) {
        this.dueDateNew = dueDateNew;
    }

    @Column(name = "adjust_reason")
    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "lmp_date_old")
    public Date getLmpDateOld() {
        return lmpDateOld;
    }

    public void setLmpDateOld(Date lmpDateOld) {
        this.lmpDateOld = lmpDateOld;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "lmp_date_new")
    public Date getLmpDateNew() {
        return lmpDateNew;
    }

    public void setLmpDateNew(Date lmpDateNew) {
        this.lmpDateNew = lmpDateNew;
    }

}
