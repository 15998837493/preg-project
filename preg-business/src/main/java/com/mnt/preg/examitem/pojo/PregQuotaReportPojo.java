
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.customer.pojo.CustomerPojo;
import com.mnt.preg.customer.pojo.PregArchivesPojo;
import com.mnt.preg.platform.pojo.DiagnosisQuotaItemVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 指标PDF
 * 
 * @author wsy
 * @version 1.0
 * 
 *          变更履历： v1.0 2017-3-15 wsy 初版
 */
public class PregQuotaReportPojo implements Serializable {

    private static final long serialVersionUID = 94741465221638824L;

    /** 本次接诊信息 */
    private PregDiagnosisPojo diagnosisVo;

    /** 干预会员信息 */
    private CustomerPojo customerVo;

    /** 孕期建档信息 */
    private PregArchivesPojo pregArchivesPojo;

    /** 指标信息 */
    private PregAuxAnalysisGroupPojo auxAnalysisVo;

    /** 辅助检测项目 */
    private List<DiagnosisQuotaItemVo> quotaItemList;

    /** 打印内容 */
    private List<String> codeItemList;

    public PregDiagnosisPojo getDiagnosisVo() {
        return diagnosisVo;
    }

    public void setDiagnosisVo(PregDiagnosisPojo diagnosisVo) {
        this.diagnosisVo = diagnosisVo;
    }

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

    public PregAuxAnalysisGroupPojo getAuxAnalysisVo() {
        return auxAnalysisVo;
    }

    public void setAuxAnalysisVo(PregAuxAnalysisGroupPojo auxAnalysisVo) {
        this.auxAnalysisVo = auxAnalysisVo;
    }

    public List<DiagnosisQuotaItemVo> getQuotaItemList() {
        return quotaItemList;
    }

    public void setQuotaItemList(List<DiagnosisQuotaItemVo> quotaItemList) {
        this.quotaItemList = quotaItemList;
    }

    public List<String> getCodeItemList() {
        return codeItemList;
    }

    public void setCodeItemList(List<String> codeItemList) {
        this.codeItemList = codeItemList;
    }

}
