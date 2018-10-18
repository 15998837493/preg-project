
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.List;

import com.mnt.preg.platform.pojo.PregDiagnosisItemsVo;
import com.mnt.preg.platform.pojo.PregDiagnosisPojo;

/**
 * 剂量评估PDF
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2017-4-13 zcq 初版
 */
public class ExtenderReportPojo implements Serializable {

    private static final long serialVersionUID = 94741465221638824L;

    /** 计量评估指标结果信息 */
    List<ExamItemPojo> examItemList;

    /** 服用周期 */
    List<String> takingCycleList;

    /** 机构号 */
    String insId;

    /** 接诊信息 */
    private PregDiagnosisPojo diagnosis;

    /** 接诊项目信息 */
    private PregDiagnosisItemsVo diagnosisItem;

    public List<ExamItemPojo> getExamItemList() {
        return examItemList;
    }

    public void setExamItemList(List<ExamItemPojo> examItemList) {
        this.examItemList = examItemList;
    }

    public List<String> getTakingCycleList() {
        return takingCycleList;
    }

    public void setTakingCycleList(List<String> takingCycleList) {
        this.takingCycleList = takingCycleList;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public PregDiagnosisPojo getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(PregDiagnosisPojo diagnosis) {
        this.diagnosis = diagnosis;
    }

    public PregDiagnosisItemsVo getDiagnosisItem() {
        return diagnosisItem;
    }

    public void setDiagnosisItem(PregDiagnosisItemsVo diagnosisItem) {
        this.diagnosisItem = diagnosisItem;
    }

}
