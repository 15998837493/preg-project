
package com.mnt.preg.master.form.preg;

import java.util.List;

import com.mnt.preg.master.pojo.preg.PregCourseDetailPojo;

/**
 * 孕期课程表单
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-3-20 gss 初版
 */
public class PregCourseForm {

    /** 主键 */
    private String id;

    /** 编号 */
    private String pregId;

    /** 名称 */
    private String pregName;

    /** 孕周数范围开始 */
    private Integer pregWeekBegin;

    /** 孕周数范围结束 */
    private Integer pregWeekEnd;

    List<PregCourseDetailPojo> pregnancyCourseDetailDtos;

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

    public String getPregName() {
        return pregName;
    }

    public void setPregName(String pregName) {
        this.pregName = pregName;
    }

    public Integer getPregWeekBegin() {
        return pregWeekBegin;
    }

    public void setPregWeekBegin(Integer pregWeekBegin) {
        this.pregWeekBegin = pregWeekBegin;
    }

    public Integer getPregWeekEnd() {
        return pregWeekEnd;
    }

    public void setPregWeekEnd(Integer pregWeekEnd) {
        this.pregWeekEnd = pregWeekEnd;
    }

    public List<PregCourseDetailPojo> getPregnancyCourseDetailDtos() {
        return pregnancyCourseDetailDtos;
    }

    public void setPregnancyCourseDetailDtos(List<PregCourseDetailPojo> pregnancyCourseDetailDtos) {
        this.pregnancyCourseDetailDtos = pregnancyCourseDetailDtos;
    }

}
