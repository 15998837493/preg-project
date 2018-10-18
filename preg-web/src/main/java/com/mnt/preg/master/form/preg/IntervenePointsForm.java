
package com.mnt.preg.master.form.preg;

/**
 * 
 * 健康要点表单
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */
public class IntervenePointsForm {

    /** 主键 */
    private String pointId;

    /** 大类 */
    private String pointType;

    /** 生活类别 */
    private String lifeSubclass;

    /** 食物类别类别 */
    private String foodSubclass;

    /** 改善原则 */
    private String betterInfo;

    /** 要点描述 */
    private String pointDesc;

    /** 要点方法举例 */
    private String pointExample;

    /** 要点排序 */
    private Integer pointOrder;

    /** 干预疾病重点 */
    private String interveneDiseaseIds;

    /** 干预疾病重点 */
    private String interveneDiseaseNames;

    /** 干预疾病重点 */
    private String pregStage;

    /** 课程名称Id */
    private String courseName;

    private String pointSubclass;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getPointDesc() {
        return pointDesc;
    }

    public void setPointDesc(String pointDesc) {
        this.pointDesc = pointDesc;
    }

    public String getPointExample() {
        return pointExample;
    }

    public void setPointExample(String pointExample) {
        this.pointExample = pointExample;
    }

    public Integer getPointOrder() {
        return pointOrder;
    }

    public void setPointOrder(Integer pointOrder) {
        this.pointOrder = pointOrder;
    }

    public String getLifeSubclass() {
        return lifeSubclass;
    }

    public void setLifeSubclass(String lifeSubclass) {
        this.lifeSubclass = lifeSubclass;
    }

    public String getFoodSubclass() {
        return foodSubclass;
    }

    public void setFoodSubclass(String foodSubclass) {
        this.foodSubclass = foodSubclass;
    }

    public String getInterveneDiseaseIds() {
        return interveneDiseaseIds;
    }

    public void setInterveneDiseaseIds(String interveneDiseaseIds) {
        this.interveneDiseaseIds = interveneDiseaseIds;
    }

    public String getBetterInfo() {
        return betterInfo;
    }

    public void setBetterInfo(String betterInfo) {
        this.betterInfo = betterInfo;
    }

    public String getPregStage() {
        return pregStage;
    }

    public void setPregStage(String pregStage) {
        this.pregStage = pregStage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInterveneDiseaseNames() {
        return interveneDiseaseNames;
    }

    public void setInterveneDiseaseNames(String interveneDiseaseNames) {
        this.interveneDiseaseNames = interveneDiseaseNames;
    }

    public String getPointSubclass() {
        return pointSubclass;
    }

    public void setPointSubclass(String pointSubclass) {
        this.pointSubclass = pointSubclass;
    }

}
