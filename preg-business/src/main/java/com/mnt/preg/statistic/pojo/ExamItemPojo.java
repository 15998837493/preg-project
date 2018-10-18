/*
 * ExamItemPojo.java    1.0  2018年8月14日
 *
 * 北京麦芽健康管理有限公司
 * 
 */

package com.mnt.preg.statistic.pojo;

/**
 * 体检项目
 *
 * @author zj
 * @version 1.0
 *
 *          变更履历：
 *          v1.0 2018年8月14日 zj 初版
 */
public class ExamItemPojo {

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 检查时间
     */
    private String itemDate;

    /**
     * 项目单位
     */
    private String itemUnit;

    /**
     * 值
     */
    private String itemValue;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

}
