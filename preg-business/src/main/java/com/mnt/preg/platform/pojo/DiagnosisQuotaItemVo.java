
package com.mnt.preg.platform.pojo;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 患者接诊-辅助检查项目表
 * 
 * @author wsy
 * @version 1.1
 * 
 *          变更履历：
 *          v1.1 2017-4-6 wsy 初版
 */
public class DiagnosisQuotaItemVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 项目ID */
    @QueryFieldAnnotation
    private String inspectItemId;

    /** 项目名称 */
    @QueryFieldAnnotation
    private String inspectItemName;

    /** 接诊号 */
    @QueryFieldAnnotation
    private String diagnosisId;

    /** 出结果时限 */
    private String resultsSuggest;

    /** 复查提示 */
    private String reviewHints;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectItemId() {
        return inspectItemId;
    }

    public void setInspectItemId(String inspectItemId) {
        this.inspectItemId = inspectItemId;
    }

    public String getInspectItemName() {
        return inspectItemName;
    }

    public void setInspectItemName(String inspectItemName) {
        this.inspectItemName = inspectItemName;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getResultsSuggest() {
        return resultsSuggest;
    }

    public void setResultsSuggest(String resultsSuggest) {
        this.resultsSuggest = resultsSuggest;
    }

    public String getReviewHints() {
        return reviewHints;
    }

    public void setReviewHints(String reviewHints) {
        this.reviewHints = reviewHints;
    }

}
