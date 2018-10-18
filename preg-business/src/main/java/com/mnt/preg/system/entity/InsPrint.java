
package com.mnt.preg.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 机构打印内容
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-2-25 zcq 初版
 */
@Entity
@Table(name = "sys_institution_print")
public class InsPrint extends MappedEntity {

    private static final long serialVersionUID = 3745143870250023312L;

    /** 主键 */
    private String insPrintId;

    /** 机构编号 */
    private String insId;

    /** 打印编号 */
    private String printId;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "ins_print_id")
    public String getInsPrintId() {
        return insPrintId;
    }

    public void setInsPrintId(String insPrintId) {
        this.insPrintId = insPrintId;
    }

    @Column(name = "ins_id")
    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    @Column(name = "print_id")
    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

}
