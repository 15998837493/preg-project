
package com.mnt.preg.web.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Echarts插件
 * 
 * @author dhs
 * @version 1.4
 * 
 *          变更履历：
 *          v1.4 2018-2-6 dhs 初版
 */
public class Echarts implements Serializable {

    {
        /** 标题 */
        Map<String, Object> titleMap = new HashMap<String, Object>();
        titleMap.put("left", "center");
        titleMap.put("top", "18");
        title = titleMap;
        /** 名称 */
        Map<String, Object> legendMap = new HashMap<String, Object>();
        legendMap.put("left", "center");
        legendMap.put("top", "55");
        legend = legendMap;
        /** 位置坐标 */
        Map<String, Integer> gridBarMap = new HashMap<String, Integer>();
        gridBarMap.put("x", 40);
        gridBarMap.put("x2", 50);
        gridBarMap.put("y", 100);
        gridBarMap.put("y2", 40);
        gridBar = gridBarMap;
        /** 条形图位置坐标 */
        Map<String, Integer> gridLineMap = new HashMap<String, Integer>();
        gridLineMap.put("x", 60);
        gridLineMap.put("x2", 50);
        gridLineMap.put("y", 100);
        gridLineMap.put("y2", 40);
        gridLine = gridLineMap;
    }

    private static final long serialVersionUID = -3406488520140598548L;

    /** 标题 {"":"","":""} */
    private Map<String, Object> title;

    /** 图例 */
    private Map<String, Object> legend;

    /** 柱状图坐标 */
    private Map<String, Integer> gridBar;

    /** 条形图坐标 */
    private Map<String, Integer> gridLine;

    /** x轴滚动轴 */
    private Map<String, Object> xDataZoom;

    /** x轴数据 ["",""] */
    private String[] xData;

    /** y轴数据 ["",""] */
    private String[] yData;

    /** 图表数据 [{},{},{}] */
    private List<EchartsSeries> echartsData;

    /** 饼状图数据 [{},{},{}] */
    private List<Map<String, String>> pieData;

    /** 图表默认颜色 */
    private String[] color = {"#4682B4", "#C46627", "#888888", "#D39E00", "#375DA1", "#5B8E39", "#5B9BD5", "#ED7D31",
            "#A5A5A5", "#FFC000", "#4472C4", "#70AD47", "#ADC6E5", "#F4B9A4", "#CBCBCB"};

    /** x/y 轴最小间隔数 */
    private int minInterval = 1;

    public String[] getxData() {
        return xData;
    }

    public void setxData(String[] xData) {
        this.xData = xData;
    }

    public String[] getyData() {
        return yData;
    }

    public void setyData(String[] yData) {
        this.yData = yData;
    }

    public List<EchartsSeries> getEchartsData() {
        return echartsData;
    }

    public void setEchartsData(List<EchartsSeries> echartsData) {
        this.echartsData = echartsData;
    }

    public List<Map<String, String>> getPieData() {
        return pieData;
    }

    public void setPieData(List<Map<String, String>> pieData) {
        this.pieData = pieData;
    }

    public Map<String, Object> getTitle() {
        return title;
    }

    public void setTitle(Map<String, Object> title) {
        this.title = title;
    }

    public Map<String, Object> getLegend() {
        return legend;
    }

    public void setLegend(Map<String, Object> legend) {
        this.legend = legend;
    }

    public Map<String, Integer> getGridBar() {
        return gridBar;
    }

    public void setGridBar(Map<String, Integer> gridBar) {
        this.gridBar = gridBar;
    }

    public Map<String, Integer> getGridLine() {
        return gridLine;
    }

    public void setGridLine(Map<String, Integer> gridLine) {
        this.gridLine = gridLine;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }

    public Map<String, Object> getxDataZoom() {
        return xDataZoom;
    }

    public void setxDataZoom(Map<String, Object> xDataZoom) {
        this.xDataZoom = xDataZoom;
    }

}
