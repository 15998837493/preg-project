
package com.mnt.preg.examitem.form;

import java.util.List;

import com.mnt.preg.platform.pojo.DiagnosisPrescriptionPojo;

/**
 * 营养处方
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-10-21 zcq 初版
 */
public class DiagnosisPrescriptionForm {

    /** 诊疗主键 */
    private String diagnosisId;

    /** 补充剂列表 */
    private List<DiagnosisPrescriptionPojo> extenderList;

    /** 营养素补充提示 */
    private String extenderHint;

    public String getExtenderHint() {
        return extenderHint;
    }

    public void setExtenderHint(String extenderHint) {
        this.extenderHint = extenderHint;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public List<DiagnosisPrescriptionPojo> getExtenderList() {
        return extenderList;
    }

    public void setExtenderList(List<DiagnosisPrescriptionPojo> extenderList) {
        this.extenderList = extenderList;
    }

}
