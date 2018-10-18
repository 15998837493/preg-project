
package com.mnt.preg.web.excel;

import java.util.List;

public class AccountExcelData {

    /** 初始行 */
    private int initRow = 1;

    /** 初始列 */
    private int initCol = 1;

    /** sheet序号 */
    private int sheetNum = 0;

    /** sheet名称 */
    private String sheetName = "工作表2";

    /** 图表高度 */
    private int rowspan = 17;

    /** 图表宽度 */
    private int colspan = 10;

    /** 生成图表时，列表中的有效数据行数 */
    private int dataRows;

    /** 生成图表时，列表中的有效数据列数 */
    private int dataCols;

    /** 生成图表时，列表中的有效数据方式，row：第一行作为图表的x轴；col：第一列作为x轴 */
    private String dataType = "row";

    /** 图表标题 */
    private String title;

    /** 图表类型 */
    private String chartType = "colnum";

    /** 数据展示列表 */
    private List<String[]> rowList;

    public int getInitRow() {
        return initRow;
    }

    public void setInitRow(int initRow) {
        this.initRow = initRow;
    }

    public int getInitCol() {
        return initCol;
    }

    public void setInitCol(int initCol) {
        this.initCol = initCol;
    }

    public int getSheetNum() {
        return sheetNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getDataRows() {
        return dataRows;
    }

    public void setDataRows(int dataRows) {
        this.dataRows = dataRows;
    }

    public int getDataCols() {
        return dataCols;
    }

    public void setDataCols(int dataCols) {
        this.dataCols = dataCols;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public List<String[]> getRowList() {
        return rowList;
    }

    public void setRowList(List<String[]> rowList) {
        this.rowList = rowList;
    }

}
