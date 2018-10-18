
package com.mnt.preg.examitem.form;

import java.util.List;

import com.mnt.preg.platform.pojo.PregDiagnosisExtenderPojo;

/**
 * 选择产品表单数据
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：v1.0 2015-1-19 zcq 初版
 */
public class ExtenderAssessForm {

    /** 诊疗主键 */
    private String diagnosisId;

    /** 补充剂列表 */
    private List<PregDiagnosisExtenderPojo> extenderList;

    /** 所选服用周期 */
    private List<String> takingCycleList;

    /** 计量评估返回码 */
    private String inspectId;

    /** 营养素补充提示（1.3.0版本弃用） */
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

    public List<String> getTakingCycleList() {
        return takingCycleList;
    }

    public void setTakingCycleList(List<String> takingCycleList) {
        this.takingCycleList = takingCycleList;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public List<PregDiagnosisExtenderPojo> getExtenderList() {
        return extenderList;
    }

    public void setExtenderList(List<PregDiagnosisExtenderPojo> extenderList) {
        this.extenderList = extenderList;
    }

}
