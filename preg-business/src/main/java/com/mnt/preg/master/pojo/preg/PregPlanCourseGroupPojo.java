
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;
import java.util.List;

/**
 * 方案课程
 * 
 * @author zcq
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-11-24 zcq 初版
 */
public class PregPlanCourseGroupPojo implements Serializable {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 孕期基础课程 */
    private List<PregCourseDetailPojo> pregCourseList;

    /** 诊断教育课程 */
    private List<PregCourseDetailPojo> diseaseCourseList;

    public List<PregCourseDetailPojo> getPregCourseList() {
        return pregCourseList;
    }

    public void setPregCourseList(List<PregCourseDetailPojo> pregCourseList) {
        this.pregCourseList = pregCourseList;
    }

    public List<PregCourseDetailPojo> getDiseaseCourseList() {
        return diseaseCourseList;
    }

    public void setDiseaseCourseList(List<PregCourseDetailPojo> diseaseCourseList) {
        this.diseaseCourseList = diseaseCourseList;
    }

}
