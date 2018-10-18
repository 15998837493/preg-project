
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 饮食习惯
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-15 zcq 初版
 */
public class PregHabitPojo implements Serializable {

    private static final long serialVersionUID = -205607526597567742L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 评估编号 */
    @QueryFieldAnnotation
    private String foodRecordId;

    /** 三餐是否规律 */
    @QueryFieldAnnotation
    private String pregnancyRule;

    /** 用餐时间 */
    @QueryFieldAnnotation
    private String pregnancySpeed;

    /** 在外用餐频率 */
    @QueryFieldAnnotation
    private String pregnancyFrequency;

    /** 饮食喜好 */
    @QueryFieldAnnotation
    private String pregnancyLike;

    /** 用餐感受 */
    @QueryFieldAnnotation
    private String pregnancyFeel;

    /** 饮食避免 */
    @QueryFieldAnnotation
    private String pregnancyAvoid;

    /** 饮食推荐摄入量 */
    @QueryFieldAnnotation
    private String pregnancyRecommend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
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

    public String getPregnancyLike() {
        return pregnancyLike;
    }

    public void setPregnancyLike(String pregnancyLike) {
        this.pregnancyLike = pregnancyLike;
    }

    public String getPregnancyFeel() {
        return pregnancyFeel;
    }

    public void setPregnancyFeel(String pregnancyFeel) {
        this.pregnancyFeel = pregnancyFeel;
    }

    public String getPregnancyAvoid() {
        return pregnancyAvoid;
    }

    public void setPregnancyAvoid(String pregnancyAvoid) {
        this.pregnancyAvoid = pregnancyAvoid;
    }

    public String getPregnancyRecommend() {
        return pregnancyRecommend;
    }

    public void setPregnancyRecommend(String pregnancyRecommend) {
        this.pregnancyRecommend = pregnancyRecommend;
    }

}
