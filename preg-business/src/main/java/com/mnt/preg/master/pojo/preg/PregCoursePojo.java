
package com.mnt.preg.master.pojo.preg;

import java.io.Serializable;

import com.mnt.health.core.annotation.QueryFieldAnnotation;

/**
 * 
 * 孕期_课程
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-12 gss 初版
 */
public class PregCoursePojo implements Serializable {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    @QueryFieldAnnotation
    private String id;

    /** 编号 */
    @QueryFieldAnnotation
    private String pregId;

    /** 名称 */
    @QueryFieldAnnotation
    private String pregName;

    /** 孕周数范围开始 */
    @QueryFieldAnnotation
    private Integer pregWeekBegin;

    /** 孕周数范围结束 */
    @QueryFieldAnnotation
    private Integer pregWeekEnd;

    /** 孕期课程相关字符串 */
    private String pregDeString;

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

    public String getPregDeString() {
        return pregDeString;
    }

    public void setPregDeString(String pregDeString) {
        this.pregDeString = pregDeString;
    }

}
