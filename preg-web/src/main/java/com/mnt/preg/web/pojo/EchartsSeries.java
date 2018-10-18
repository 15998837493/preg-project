/*
 * EchartsData.java    1.0  2018-2-6
 *
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.web.pojo;

import java.io.Serializable;

/**
 * 
 * Echarts series数据
 * 
 * @author scd
 * @version 1.4
 * 
 *          变更履历：
 *          v1.4 2018-2-6 scd 初版
 */
public class EchartsSeries implements Serializable {

    private static final long serialVersionUID = -2433539997663787804L;

    /** 唯一识别码 */
    private String id;

    /** 名称 */
    private String name;

    /** 类型 */
    private String type;

    /** 曲线是否圆滑 */
    private boolean smooth;

    /** 数据[] */
    private Integer[] data;

    /** 柱状图最低高度 */
    private int barMinHeight = 5;

    /** 柱状图最大宽度 */
    private int barMaxWidth = 50;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSmooth() {
        return smooth;
    }

    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
    }

    public Integer[] getData() {
        return data;
    }

    public void setData(Integer[] data) {
        this.data = data;
    }

    public int getBarMinHeight() {
        return barMinHeight;
    }

    public void setBarMinHeight(int barMinHeight) {
        this.barMinHeight = barMinHeight;
    }

    public int getBarMaxWidth() {
        return barMaxWidth;
    }

    public void setBarMaxWidth(int barMaxWidth) {
        this.barMaxWidth = barMaxWidth;
    }

}
