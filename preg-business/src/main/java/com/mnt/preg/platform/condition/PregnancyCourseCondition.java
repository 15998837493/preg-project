
package com.mnt.preg.platform.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 
 * 孕期_课程检索条件
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-9-12 gss 初版
 */
public class PregnancyCourseCondition implements Serializable {

    private static final long serialVersionUID = -3197741092525699829L;

    /** 主键 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String pregId;

    /** 名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String pregName;

    /** 孕周数范围开始 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LE)
    private Integer pregWeekBegin;

    /** 孕周数范围结束 */
    @QueryConditionAnnotation(symbol = SymbolConstants.GE)
    private Integer pregWeekEnd;

    /** 标识 */
    @XmlTransient
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private Integer flag = 1;

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

}
