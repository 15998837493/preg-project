
package com.mnt.preg.examitem.form;

import java.util.List;

import com.mnt.preg.examitem.entity.PregFoodFeelTime;
import com.mnt.preg.examitem.pojo.PregFoodFrequencyPojo;

/**
 * 膳食评估饮食记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public class DietRecordForm {

    /** 膳食评估编号 */
    private String foodRecordId;

    /** 膳食明细 */
    private List<String> foodList;

    /** 饮食摄入频率 */
    private List<PregFoodFrequencyPojo> foodFrequencyList;

    /** 用餐时间和感受 */
    private List<PregFoodFeelTime> foodFeelTimeList;

    /** 三餐是否规律 */
    private String pregnancyRule;

    /** 用餐时间 */
    private String pregnancySpeed;

    /** 在外用餐频率 */
    private String pregnancyFrequency;

    /** 饮食喜好 */
    private List<String> pregnancyLike;

    /** 用餐感受 */
    private List<String> pregnancyFeel;

    /** 饮食避免 */
    private List<String> pregnancyAvoid;

    /** 饮食推荐摄入量 */
    private List<String> pregnancyRecommend;

    /** 接诊单号 */
    private String diagnosisId;

    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    public List<String> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<String> foodList) {
        this.foodList = foodList;
    }

    public List<PregFoodFrequencyPojo> getFoodFrequencyList() {
        return foodFrequencyList;
    }

    public void setFoodFrequencyList(List<PregFoodFrequencyPojo> foodFrequencyList) {
        this.foodFrequencyList = foodFrequencyList;
    }

    public String getPregnancyRule() {
        return pregnancyRule;
    }

    public void setPregnancyRule(String pregnancyRule) {
        this.pregnancyRule = pregnancyRule;
    }

    public String getPregnancySpeed() {
        return pregnancySpeed;
    }

    public void setPregnancySpeed(String pregnancySpeed) {
        this.pregnancySpeed = pregnancySpeed;
    }

    public String getPregnancyFrequency() {
        return pregnancyFrequency;
    }

    public void setPregnancyFrequency(String pregnancyFrequency) {
        this.pregnancyFrequency = pregnancyFrequency;
    }

    public List<String> getPregnancyLike() {
        return pregnancyLike;
    }

    public void setPregnancyLike(List<String> pregnancyLike) {
        this.pregnancyLike = pregnancyLike;
    }

    public List<String> getPregnancyFeel() {
        return pregnancyFeel;
    }

    public void setPregnancyFeel(List<String> pregnancyFeel) {
        this.pregnancyFeel = pregnancyFeel;
    }

    public List<String> getPregnancyAvoid() {
        return pregnancyAvoid;
    }

    public void setPregnancyAvoid(List<String> pregnancyAvoid) {
        this.pregnancyAvoid = pregnancyAvoid;
    }

    public List<String> getPregnancyRecommend() {
        return pregnancyRecommend;
    }

    public void setPregnancyRecommend(List<String> pregnancyRecommend) {
        this.pregnancyRecommend = pregnancyRecommend;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public List<PregFoodFeelTime> getFoodFeelTimeList() {
        return foodFeelTimeList;
    }

    public void setFoodFeelTimeList(List<PregFoodFeelTime> foodFeelTimeList) {
        this.foodFeelTimeList = foodFeelTimeList;
    }

}
