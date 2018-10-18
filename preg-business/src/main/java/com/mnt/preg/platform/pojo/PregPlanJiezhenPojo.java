
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.preg.customer.pojo.CustomerPojo;

/**
 * 接诊页面初始化
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-24 zcq 初版
 */
public class PregPlanJiezhenPojo implements Serializable {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 接诊登记信息 */
    private PregDiagnosisPojo diagnosis;

    /** 患者信息 */
    private CustomerPojo customer;

    public PregDiagnosisPojo getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(PregDiagnosisPojo diagnosis) {
        this.diagnosis = diagnosis;
    }

    public CustomerPojo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerPojo customer) {
        this.customer = customer;
    }

}
