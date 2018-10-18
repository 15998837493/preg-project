
package com.mnt.preg.master.condition.preg;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.core.annotation.QueryConditionAnnotation;
import com.mnt.health.core.utils.OrderConstants;
import com.mnt.health.core.utils.SymbolConstants;

/**
 * 孕期_课程明细检索条件
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2016-5-20 gss 初版
 */
public class PregnancyCourseDetailCondition implements Serializable {

    private static final long serialVersionUID = 6091020302197444285L;

    /** 主表id */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ)
    private String pregId;

    /** 类别名称 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String pregDeName;

    /** 编码 */
    @QueryConditionAnnotation(symbol = SymbolConstants.LIKE)
    private String pregDeCode;

    /** 排序 */
    @QueryConditionAnnotation(symbol = SymbolConstants.EQ, order = OrderConstants.ASC)
    private Integer pregDeOrder;

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

}
