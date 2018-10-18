
package com.mnt.preg.master.entity.items;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 收费项目信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-6-21 zcq 初版
 */
@Entity
@Table(name = "mas_hospital_inspect_pay")
public class HospitalInspectPay extends MappedEntity {

    private static final long serialVersionUID = 769459242691662048L;

    /** 主键 */
    @UpdateAnnotation
    private String inspectId;

    /** 编码 */
    @UpdateAnnotation
    private String inspectCode;

    /** 名称 */
    @UpdateAnnotation
    private String inspectName;

    /** 适合性别 */
    @UpdateAnnotation
    private String inspectSex;

    /** 出结果时限 */
    @UpdateAnnotation
    private String resultsSuggest;

    /** 复查提示 */
    @UpdateAnnotation
    private String reviewHints;

    /** 项目价格 */
    @UpdateAnnotation
    private BigDecimal inspectPrice;

    /** 项目描述 */
    @UpdateAnnotation
    private String inspectDesc;

    /** 项目排序 */
    @UpdateAnnotation
    private Integer inspectSort;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "inspect_id")
    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
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

    @Column(name = "inspect_sex")
    public String getInspectSex() {
        return inspectSex;
    }

    public void setInspectSex(String inspectSex) {
        this.inspectSex = inspectSex;
    }

    @Column(name = "inspect_price")
    public BigDecimal getInspectPrice() {
        return inspectPrice;
    }

    public void setInspectPrice(BigDecimal inspectPrice) {
        this.inspectPrice = inspectPrice;
    }

    @Column(name = "inspect_desc")
    public String getInspectDesc() {
        return inspectDesc;
    }

    public void setInspectDesc(String inspectDesc) {
        this.inspectDesc = inspectDesc;
    }

    @Column(name = "results_suggest")
    public String getResultsSuggest() {
        return resultsSuggest;
    }

    public void setResultsSuggest(String resultsSuggest) {
        this.resultsSuggest = resultsSuggest;
    }

    @Column(name = "review_hints")
    public String getReviewHints() {
        return reviewHints;
    }

    public void setReviewHints(String reviewHints) {
        this.reviewHints = reviewHints;
    }

    @Column(name = "inspect_sort")
    public Integer getInspectSort() {
        return inspectSort;
    }

    public void setInspectSort(Integer inspectSort) {
        this.inspectSort = inspectSort;
    }

    @Override
    public String toString() {
        return "HospitalInspectPay [inspectId=" + inspectId + ", inspectCode=" + inspectCode + ", inspectName=" + inspectName
                + ", inspectSex=" + inspectSex + ", resultsSuggest=" + resultsSuggest + ", reviewHints=" + reviewHints
                + ", inspectPrice=" + inspectPrice + ", inspectDesc=" + inspectDesc + ", inspectSort=" + inspectSort + "]";
    }

}
