
package com.mnt.preg.platform.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;

/**
 * 诊断分析信息
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-7-3 zcq 初版
 */
public class PregDiagnosisAnalysisPojo implements Serializable {

    private static final long serialVersionUID = 7715614317930272952L;

    /** 客户信息 */
    private CustomerPojo customerVo;

    /** 孕期建档信息 */
    private PregArchivesPojo pregArchivesPojo;

    /** 接诊信息 */
    private PregDiagnosisPojo diagnosisVo;

    /** 检测项目信息 */
    private List<PregDiagnosisItemsVo> diagnosisItemList;

    public CustomerPojo getCustomerVo() {
        return customerVo;
    }

    public void setCustomerVo(CustomerPojo customerVo) {
        this.customerVo = customerVo;
    }

    public PregArchivesPojo getPregArchivesVo() {
        return pregArchivesPojo;
    }

    public void setPregArchivesVo(PregArchivesPojo pregArchivesPojo) {
        this.pregArchivesPojo = pregArchivesPojo;
    }

    public PregDiagnosisPojo getDiagnosisVo() {
        return diagnosisVo;
    }

    public void setDiagnosisVo(PregDiagnosisPojo diagnosisVo) {
        this.diagnosisVo = diagnosisVo;
    }

    public List<PregDiagnosisItemsVo> getDiagnosisItemList() {
        return diagnosisItemList;
    }

    public void setDiagnosisItemList(List<PregDiagnosisItemsVo> diagnosisItemList) {
        this.diagnosisItemList = diagnosisItemList;
    }

}
