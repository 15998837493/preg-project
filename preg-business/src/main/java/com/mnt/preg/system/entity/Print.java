
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import com.mnt.health.core.annotation.UpdateAnnotation;
import com.mnt.preg.main.entity.MappedEntity;

/**
 * 打印内容
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2018-1-26 zcq 初版
 */
@Entity
@Table(name = "sys_print")
public class Print extends MappedEntity {

    private static final long serialVersionUID = 882586403279430166L;

    /** 主键 */
    @NotBlank
    private String printId;

    /** 分类 */
    @UpdateAnnotation
    private String printCategory;

    /** 选项 */
    @UpdateAnnotation
    private String printItem;

    /** 排序 */
    @UpdateAnnotation
    private Integer printOrder;

    /** 是否预览 */
    @UpdateAnnotation
    private Integer printPreview;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "print_id")
    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    @Column(name = "print_category")
    public String getPrintCategory() {
        return printCategory;
    }

    public void setPrintCategory(String printCategory) {
        this.printCategory = printCategory;
    }

    @Column(name = "print_item")
    public String getPrintItem() {
        return printItem;
    }

    public void setPrintItem(String printItem) {
        this.printItem = printItem;
    }

    @Column(name = "print_order")
    public Integer getPrintOrder() {
        return printOrder;
    }

    public void setPrintOrder(Integer printOrder) {
        this.printOrder = printOrder;
    }

    @Column(name = "print_preview")
    public Integer getPrintPreview() {
        return printPreview;
    }

    public void setPrintPreview(Integer printPreview) {
        this.printPreview = printPreview;
    }

}
