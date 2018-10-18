/*
 * ImportResultVo.java   1.0   2015-9-7
 * 
 * 沈阳成林健康科技有限公司
 * 
 */

package com.mnt.preg.customer.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 批量导入存放结果 VO
 * 
 * @author mnt_zhangjing
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-9-7 mnt_zhangjing 初版
 */
public class ImportResultPojo implements Serializable {

    private static final long serialVersionUID = -3809597126531735012L;

    /** 主键 */
    @QueryFieldAnnotation
    private String importId;

    /** 导入日期 */
    @QueryFieldAnnotation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date importDate;

    /** 上传文件保存路径 */
    @QueryFieldAnnotation
    private String importFilePath;

    /** 导入信息总数 */
    @QueryFieldAnnotation
    private Integer importTotal;

    /** 导入成功信息数 */
    @QueryFieldAnnotation
    private Integer importSucTotal;

    /** 导入失败信息数 */
    @QueryFieldAnnotation
    private Integer importFalTotal;

    /** 未成功导入部分生成的xlsx存放路径 */
    @QueryFieldAnnotation
    private String falFilePath;

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getImportFilePath() {
        return importFilePath;
    }

    public void setImportFilePath(String importFilePath) {
        this.importFilePath = importFilePath;
    }

    public Integer getImportTotal() {
        return importTotal;
    }

    public void setImportTotal(Integer importTotal) {
        this.importTotal = importTotal;
    }

    public Integer getImportSucTotal() {
        return importSucTotal;
    }

    public void setImportSucTotal(Integer importSucTotal) {
        this.importSucTotal = importSucTotal;
    }

    public Integer getImportFalTotal() {
        return importFalTotal;
    }

    public void setImportFalTotal(Integer importFalTotal) {
        this.importFalTotal = importFalTotal;
    }

    public String getFalFilePath() {
        return falFilePath;
    }

    public void setFalFilePath(String falFilePath) {
        this.falFilePath = falFilePath;
    }

}
