
package com.mnt.preg.examitem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 膳食评估饮食习惯
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-4-15 zcq 初版
 */
@Entity
@Table(name = "cus_food_pregnancy_habit")
public class PregHabit extends MappedEntity {

    private static final long serialVersionUID = 3072445668040737697L;

    /** 主键 */
    private String id;

    /** 评估编号 */
    private String foodRecordId;

    /** 三餐是否规律 */
    private String pregnancyRule;

    /** 用餐时间 */
    private String pregnancySpeed;

    /** 在外用餐频率 */
    private String pregnancyFrequency;

    /** 饮食喜好 */
    private String pregnancyLike;

    /** 用餐感受 */
    private String pregnancyFeel;

    /** 饮食避免 */
    private String pregnancyAvoid;

    /** 饮食推荐摄入量 */
    private String pregnancyRecommend;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "food_record_id")
    public String getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(String foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    @Column(name = "pregnancy_rule")
    public String getPregnancyRule() {
        return pregnancyRule;
    }

    public void setPregnancyRule(String pregnancyRule) {
        this.pregnancyRule = pregnancyRule;
    }

    @Column(name = "pregnancy_speed")
    public String getPregnancySpeed() {
        return pregnancySpeed;
    }

    public void setPregnancySpeed(String pregnancySpeed) {
        this.pregnancySpeed = pregnancySpeed;
    }

    @Column(name = "pregnancy_frequency")
    public String getPregnancyFrequency() {
        return pregnancyFrequency;
    }

    public void setPregnancyFrequency(String pregnancyFrequency) {
        this.pregnancyFrequency = pregnancyFrequency;
    }

    @Column(name = "pregnancy_like")
    public String getPregnancyLike() {
        return pregnancyLike;
    }

    public void setPregnancyLike(String pregnancyLike) {
        this.pregnancyLike = pregnancyLike;
    }

    @Column(name = "pregnancy_feel")
    public String getPregnancyFeel() {
        return pregnancyFeel;
    }

    public void setPregnancyFeel(String pregnancyFeel) {
        this.pregnancyFeel = pregnancyFeel;
    }

    @Column(name = "pregnancy_avoid")
    public String getPregnancyAvoid() {
        return pregnancyAvoid;
    }

    public void setPregnancyAvoid(String pregnancyAvoid) {
        this.pregnancyAvoid = pregnancyAvoid;
    }

    @Column(name = "pregnancy_recommend")
    public String getPregnancyRecommend() {
        return pregnancyRecommend;
    }

    public void setPregnancyRecommend(String pregnancyRecommend) {
        this.pregnancyRecommend = pregnancyRecommend;
    }

}
