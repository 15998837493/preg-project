
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 膳食评估记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public class PregDietRecordPojo implements Serializable {

    private static final long serialVersionUID = -5769711758422129048L;

    /** 膳食评估编号 */
    private String foodRecordId;

    /** 饮食习惯 */
    private PregHabitPojo pregnancyHabitBo;

    /** 饮食频率 */
    private List<PregFoodFrequencyPojo> foodFrequencyList;

    /** 膳食明细 */
    private List<PregFoodDetailsPojo> foodDetailsList;

    /** 用餐时间和感受 */
    private List<PregFoodFeelTimePojo> foodFeelTimeList;

    /** 接诊单号 */
    private String diagnosisId;

    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    public PregHabitPojo getPregnancyHabitBo() {
        return pregnancyHabitBo;
    }

    public void setPregnancyHabitBo(PregHabitPojo pregnancyHabitBo) {
        this.pregnancyHabitBo = pregnancyHabitBo;
    }

    public List<PregFoodFrequencyPojo> getFoodFrequencyList() {
        return foodFrequencyList;
    }

    public void setFoodFrequencyList(List<PregFoodFrequencyPojo> foodFrequencyList) {
        this.foodFrequencyList = foodFrequencyList;
    }

    public List<PregFoodDetailsPojo> getFoodDetailsList() {
        return foodDetailsList;
    }

    public void setFoodDetailsList(List<PregFoodDetailsPojo> foodDetailsList) {
        this.foodDetailsList = foodDetailsList;
    }

    public List<PregFoodFeelTimePojo> getFoodFeelTimeList() {
        return foodFeelTimeList;
    }

    public void setFoodFeelTimeList(List<PregFoodFeelTimePojo> foodFeelTimeList) {
        this.foodFeelTimeList = foodFeelTimeList;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}
