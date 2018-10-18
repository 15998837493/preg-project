
package com.mnt.preg.master.form.basic;

/**
 * 孕期课程明细
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */
public class PregCourseDetailForm {

    /** 主键 */
    private String id;

    /** 主表id */
    private String pregId;

    /** 类别名称 */
    private String pregDeName;

    /** 编码 */
    private String pregDeCode;

    /** 排序 */
    private Integer pregDeOrder;

    /** 排序 */
    private String moveFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId;
    }

    public String getPregDeName() {
        return pregDeName;
    }

    public void setPregDeName(String pregDeName) {
        this.pregDeName = pregDeName;
    }

    public String getPregDeCode() {
        return pregDeCode;
    }

    public void setPregDeCode(String pregDeCode) {
        this.pregDeCode = pregDeCode;
    }

    public Integer getPregDeOrder() {
        return pregDeOrder;
    }

    public void setPregDeOrder(Integer pregDeOrder) {
        this.pregDeOrder = pregDeOrder;
    }

    public String getMoveFlag() {
        return moveFlag;
    }

    public void setMoveFlag(String moveFlag) {
        this.moveFlag = moveFlag;
    }

}
