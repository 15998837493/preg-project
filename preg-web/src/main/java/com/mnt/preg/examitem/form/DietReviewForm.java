
package com.mnt.preg.examitem.form;

/**
 * 膳食回顾记录
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-3-17 zcq 初版
 */
public class DietReviewForm {

    /** 接诊编号 */
    private String diagnosisId;

    /** 检查项目编号 */
    private String dietReviewId;

    /** 检查项目编码 */
    private String dietReviewCode;

    /** 膳食回顾内容 */
    private String dietReviewText;

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDietReviewText() {
        return dietReviewText;
    }

    public void setDietReviewText(String dietReviewText) {
        this.dietReviewText = dietReviewText;
    }

    public String getDietReviewId() {
        return dietReviewId;
    }

    public void setDietReviewId(String dietReviewId) {
        this.dietReviewId = dietReviewId;
    }

    public String getDietReviewCode() {
        return dietReviewCode;
    }

    public void setDietReviewCode(String dietReviewCode) {
        this.dietReviewCode = dietReviewCode;
    }

}
