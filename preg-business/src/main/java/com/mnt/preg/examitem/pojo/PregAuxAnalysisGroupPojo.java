
package com.mnt.preg.examitem.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 诊断指标结果列表
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-21 zcq 初版
 */
public class PregAuxAnalysisGroupPojo implements Serializable {

    private static final long serialVersionUID = 4440509450417896843L;

    /** 指标列表 */
    private List<ExamItemPojo> clinItemList;

    /** 指标列表 */
    private List<ExamItemPojo> nutItemList;

    /** 人体成分指标列表 */
    private List<ExamItemPojo> inbodyItemList;

    /** 膳食回顾指标列表 */
    private List<ExamItemPojo> dietItemList;

    public List<ExamItemPojo> getClinItemList() {
        return clinItemList;
    }

    public void setClinItemList(List<ExamItemPojo> clinItemList) {
        this.clinItemList = clinItemList;
    }

    public List<ExamItemPojo> getNutItemList() {
        return nutItemList;
    }

    public void setNutItemList(List<ExamItemPojo> nutItemList) {
        this.nutItemList = nutItemList;
    }

    public List<ExamItemPojo> getInbodyItemList() {
        return inbodyItemList;
    }

    public void setInbodyItemList(List<ExamItemPojo> inbodyItemList) {
        this.inbodyItemList = inbodyItemList;
    }

    public List<ExamItemPojo> getDietItemList() {
        return dietItemList;
    }

    public void setDietItemList(List<ExamItemPojo> dietItemList) {
        this.dietItemList = dietItemList;
    }

}
