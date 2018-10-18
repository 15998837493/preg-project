/*
 * PregnancyExamineInfoPojo.java    1.0  2018年8月13日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.pojo;

import java.util.List;

import com.mnt.health.core.annotation.QueryFieldAnnotation;
import com.mnt.preg.platform.pojo.DiagnosisHospitalItemPojo;

/**
 * [孕期检验记录]
 * 
 * @author mlq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018年8月13日 mlq 初版
 */
public class PregnancyExamineInfoPojo {

    /** 本次诊断 */
    @QueryFieldAnnotation
    private String diseaseNames;

    /** 风险级别 1绿、2黄、3橙、4红、5紫 */
    @QueryFieldAnnotation
    private String gestationLevel;

    /** 检验项目集合 */
    private List<DiagnosisHospitalItemPojo> items;

    public String getDiseaseNames() {
        return diseaseNames;
    }

    public void setDiseaseNames(String diseaseNames) {
        this.diseaseNames = diseaseNames;
    }

    public String getGestationLevel() {
        return gestationLevel;
    }

    public void setGestationLevel(String gestationLevel) {
        this.gestationLevel = gestationLevel;
    }

    public List<DiagnosisHospitalItemPojo> getItems() {
        return items;
    }

    public void setItems(List<DiagnosisHospitalItemPojo> items) {
        this.items = items;
    }

}
