/*
 * ImportResult.java   1.0   2015-9-7
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.mnt.preg.main.entity.MappedEntity;

/**
 * 
 * 批量导入结果类
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-9-7 mnt_zhangjing 初版
 */
@Entity
@Table(name = "cus_import_result")
public class ImportResult extends MappedEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String importId;

    /** 导入日期 */
    private Date importDate;

    /** 上传文件保存路径 */
    private String importFilePath;

    /** 导入信息总数 */
    private Integer importTotal;

    /** 导入成功信息数 */
    private Integer importSucTotal;

    /** 导入失败信息数 */
    private Integer importFalTotal;

    /** 未成功导入部分生成的xlsx存放路径 */
    private String falFilePath;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    @Column(name = "import_id", insertable = false, updatable = false)
    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    @Column(name = "import_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    @Column(name = "import_file_path")
    public String getImportFilePath() {
        return importFilePath;
    }

    public void setImportFilePath(String importFilePath) {
        this.importFilePath = importFilePath;
    }

    @Column(name = "import_total")
    public Integer getImportTotal() {
        return importTotal;
    }

    public void setImportTotal(Integer importTotal) {
        this.importTotal = importTotal;
    }

    @Column(name = "import_suc_total")
    public Integer getImportSucTotal() {
        return importSucTotal;
    }

    public void setImportSucTotal(Integer importSucTotal) {
        this.importSucTotal = importSucTotal;
    }

    @Column(name = "import_fal_total")
    public Integer getImportFalTotal() {
        return importFalTotal;
    }

    public void setImportFalTotal(Integer importFalTotal) {
        this.importFalTotal = importFalTotal;
    }

    @Column(name = "fal_file_path")
    public String getFalFilePath() {
        return falFilePath;
    }

    public void setFalFilePath(String falFilePath) {
        this.falFilePath = falFilePath;
    }

}
