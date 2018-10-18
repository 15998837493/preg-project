
package com.mnt.preg.system.pojo;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 打印内容
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-26 zcq 初版
 */
public class PrintPojo {

    /** 主键 */
    @QueryFieldAnnotation
    private String printId;

    /** 分类 */
    @QueryFieldAnnotation
    private String printCategory;

    /** 选项 */
    @QueryFieldAnnotation
    private String printItem;

    /** 排序 */
    @QueryFieldAnnotation
    private Integer printOrder;

    /** 是否预览 */
    @QueryFieldAnnotation
    private Integer printPreview;

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    public String getPrintCategory() {
        return printCategory;
    }

    public void setPrintCategory(String printCategory) {
        this.printCategory = printCategory;
    }

    public String getPrintItem() {
        return printItem;
    }

    public void setPrintItem(String printItem) {
        this.printItem = printItem;
    }

    public Integer getPrintOrder() {
        return printOrder;
    }

    public void setPrintOrder(Integer printOrder) {
        this.printOrder = printOrder;
    }

    public Integer getPrintPreview() {
        return printPreview;
    }

    public void setPrintPreview(Integer printPreview) {
        this.printPreview = printPreview;
    }

}
