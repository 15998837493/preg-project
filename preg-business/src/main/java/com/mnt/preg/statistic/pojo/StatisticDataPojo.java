/*
 * StatisticDataPojo.java    1.0  2018年8月13日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.pojo;

import java.io.Serializable;

/**
 * [查询条件导出信息]
 * 
 * @author zj
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月13日 zj 初版
 */
public class StatisticDataPojo implements Serializable{

    /** [属性说明] */
    private static final long serialVersionUID = -1511530415825585892L;

    /** 基础信息 */
    private CustomerInfoPojo customerInfoPojo;

    /** 接诊信息汇总 */
    private DiagnosisInfoPojo diagnosisPojo;

    /** 分娩结局 */
    private BirthEndingInfoPojo birthEndingPojo;

    public DiagnosisInfoPojo getDiagnosisPojo() {
        return diagnosisPojo;
    }

    public void setDiagnosisPojo(DiagnosisInfoPojo diagnosisPojo) {
        this.diagnosisPojo = diagnosisPojo;
    }

    public BirthEndingInfoPojo getBirthEndingPojo() {
        return birthEndingPojo;
    }

    public void setBirthEndingPojo(BirthEndingInfoPojo birthEndingPojo) {
        this.birthEndingPojo = birthEndingPojo;
    }

    public CustomerInfoPojo getCustomerInfoPojo() {
        return customerInfoPojo;
    }

    public void setCustomerInfoPojo(CustomerInfoPojo customerInfoPojo) {
        this.customerInfoPojo = customerInfoPojo;
    }

}
